package chainrepublik.network.packets.politics.orgs;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CLeaveOrgPayload extends CPayload
{
    // Org ID
    long orgID;
    
    // Serial
    private static final long serialVersionUID = 100L;
    
    public CLeaveOrgPayload(String adr, long orgID) throws Exception
    {
        // Superclass
	super(adr);
        
        // Org ID
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
        
        // Org ID valid ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM orgs "
                                          + "WHERE orgID='"+this.orgID+"'");
        
        // Has data
        if (!UTILS.DB.hasData(rs))
           throw new Exception("Invalid orgID, CLeavePartyPayload.java, 58");
        
        // Address party
        long party=Long.parseLong(UTILS.BASIC.getAdrData(this.target_adr, "pol_party"));
        
        // Address mil_unit
        long mil_unit=Long.parseLong(UTILS.BASIC.getAdrData(this.target_adr, "mil_unit"));
        
        // Not in a party ?
        if (party!=this.orgID && 
            mil_unit!=this.orgID)
            throw new Exception("Invalid orgID, CLeavePartyPayload.java, 58");
        
        // Hash
 	String h=UTILS.BASIC.hash(this.getHash()+
                                  this.orgID);
        
        // Hash match ?
        if (!h.equals(this.hash))
            throw new Exception("Invalid hash, CLeavePartyPayload.java, 58");
    }
    
    public void commit(CBlockPayload block) throws Exception
    {
        // Superclass
        super.commit(block);
        
        // Load org data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM orgs "
                                          + "WHERE orgID='"+this.orgID+"'");
        
        // Next
        rs.next();
        
        // Pol party
        if (rs.getString("type").equals("ID_POL_PARTY"))
        {
            // Change cit
            UTILS.DB.executeUpdate("UPDATE adr "
                                    + "SET pol_inf=0, "
                                        + "pol_party=0, "
                                        + "pol_endorsed=0 "
                                  + "WHERE adr='"+this.target_adr+"'");
      
           // Remove endorsements
           UTILS.DB.executeUpdate("DELETE FROM endorsers "
                                      + "WHERE endorser='"+this.target_adr+"' "
                                         + "OR endorsed='"+this.target_adr+"'");
        }
        else
        {
            UTILS.DB.executeUpdate("UPDATE adr "
                                    + "SET mil_unit=0, "
                                        + "war_points=0 "
                                  + "WHERE adr='"+this.target_adr+"'");
        }
        
        // Position type
        UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
    }
    
}
