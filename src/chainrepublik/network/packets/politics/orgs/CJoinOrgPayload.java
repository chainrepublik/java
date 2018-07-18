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
        
        // Target address country
        String cou=UTILS.BASIC.getAdrData(this.target_adr, "cou");
        
        // Party exist and is in the same country ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM orgs "
                                          + "WHERE orgID='"+this.orgID+"' "
                                            + "AND country='"+cou+"' "
                                            + "AND type='ID_POL_PARTY'");
        
        // Has data ?
        if (!UTILS.DB.hasData(rs))
            throw new Exception("Invalid party ID, CJoinPartyPayload.java, 51");
        
        // Address party
        long party=Long.parseLong(UTILS.BASIC.getAdrData(this.target_adr, "pol_party"));
        
        // Already in a party ?
        if (party==this.orgID || party>0)
            throw new Exception("Already in a party, CJoinPartyPayload.java, 58");
        
        // Minimum political influence
        double pol_inf=Double.parseDouble(UTILS.BASIC.getAdrData(this.target_adr, "pol_inf"));
        
        // Minimum 100
        if (pol_inf<100)
           throw new Exception("Minimum political influence is 100, CJoinPartyPayload.java, 58");
        
        // Hash
 	String h=UTILS.BASIC.hash(this.getHash()+
 		  	          this.orgID);
        
        // Hash match ?
        if (!h.equals(this.hash))
            throw new Exception("Invalid hash, CJoinPartyPayload.java, 113");
    }
    
    public void commit(CBlockPayload block) throws Exception
    {
        // Superclass
        super.commit(block);
        
        // Insert endorser
        UTILS.DB.executeUpdate("UPDATE adr "
                                + "SET pol_party='"+orgID+"' "
                              + "WHERE adr='"+this.target_adr+"'");
        
        // Position type
        UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
    }
    
}
