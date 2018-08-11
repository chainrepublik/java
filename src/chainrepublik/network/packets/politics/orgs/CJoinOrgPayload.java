package chainrepublik.network.packets.politics.orgs;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CJoinOrgPayload extends CPayload
{
    // Party ID
    long orgID;
    
    public CJoinOrgPayload(String adr, 
                             long orgID) throws Exception
    {
        // Superclass
	super(adr);
        
        // Type
        this.orgID=orgID;
        
        // Hash
 	hash=UTILS.BASIC.hash(this.getHash()+
                              this.orgID);
    }
    
    
    public void check(CBlockPayload block) throws Exception
    {
   	// Super class
   	super.check(block);
        
        // Check energy
        this.checkEnergy();
        
        // Citizen address ?
        if (!UTILS.BASIC.isCitAdr(this.target_adr, this.block))
           throw new Exception("Only citizens can do this action - CWorkPayload.java, 68");
        
        // Target address country
        String cou=UTILS.BASIC.getAdrData(this.target_adr, "cou");
        
        // Org exist and is in the same country ?
        ResultSet org_rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM orgs "
                                              + "WHERE orgID='"+this.orgID+"' "
                                                + "AND country='"+cou+"'");
        
        // Has data ?
        if (!UTILS.DB.hasData(org_rs))
            throw new Exception("Invalid org ID, CJoinOrgPayload.java, 51");
        
        // Next
        org_rs.next();
        
        // Political party ?
        if (org_rs.getString("type").equals("ID_POL_PARTY"))
        {
            // Address party
            long party=Long.parseLong(UTILS.BASIC.getAdrData(this.target_adr, "pol_party"));
        
            // Already in a party ?
            if (party==this.orgID || party>0)
               throw new Exception("Already in a party, CJoinOrgPayload.java, 58");   

            // Minimum political influence
            double pol_inf=Double.parseDouble(UTILS.BASIC.getAdrData(this.target_adr, "pol_inf"));           
        
            // Minimum 100
            if (pol_inf<100)
                throw new Exception("Minimum political influence is 100, CJoinOrgPayload.java, 58");
        }
        else
        {
            // Address party
            long mil_unit=Long.parseLong(UTILS.BASIC.getAdrData(this.target_adr, "mil_unit"));
        
            // Already in a military unit ?
            if (mil_unit==this.orgID || mil_unit>0)
               throw new Exception("Already in an military unit, CJoinOrgPayload.java, 58");   

            // Minimum war_points
            double war_points=Double.parseDouble(UTILS.BASIC.getAdrData(this.target_adr, "war_points"));           
        
            // Minimum 100
            if (war_points<100)
                throw new Exception("Minimum war points is 100, CJoinOrgPayload.java, 58");
        }
        
        // Hash
 	String h=UTILS.BASIC.hash(this.getHash()+
 		  	          this.orgID);
        
        // Hash match ?
        if (!h.equals(this.hash))
            throw new Exception("Invalid hash, CJoinOrgPayload.java, 113");
    }
    
    public void commit(CBlockPayload block) throws Exception
    {
        // Superclass
        super.commit(block);
        
        // Org exist and is in the same country ?
        ResultSet org_rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM orgs "
                                              + "WHERE orgID='"+this.orgID+"'");
        
        // Next
        org_rs.next();
        
        // Political party ?
        if (org_rs.getString("type").equals("ID_POL_PARTY"))
        UTILS.DB.executeUpdate("UPDATE adr "
                                + "SET pol_party='"+orgID+"' "
                              + "WHERE adr='"+this.target_adr+"'");
        
        else
        UTILS.DB.executeUpdate("UPDATE adr "
                                + "SET mil_unit='"+orgID+"' "
                              + "WHERE adr='"+this.target_adr+"'");
        
        // Position type
        UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
    }
    
}
