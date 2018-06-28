package chainrepublik.network.packets.politics.orgs;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CLeavePartyPayload extends CPayload
{
    
    public CLeavePartyPayload(String adr) throws Exception
    {
        // Superclass
	super(adr);
        
        // Hash
 	hash=UTILS.BASIC.hash(this.getHash());
     
    }
    
    
    public void check(CBlockPayload block) throws Exception
    {
   	// Super class
   	super.check(block);
        
        // Check energy
        this.checkEnergy();
        
        // Address party
        long party=Long.parseLong(UTILS.BASIC.getAdrData(this.target_adr, "pol_party"));
        
        // Not in a party ?
        if (party==0)
            throw new Exception("Not in a party, CLeavePartyPayload.java, 58");
        
        // Hash
 	String h=UTILS.BASIC.hash(this.getHash());
        
        // Hash match ?
        if (!h.equals(this.hash))
            throw new Exception("Invalid hash, CLeavePartyPayload.java, 58");
    }
    
    public void commit(CBlockPayload block) throws Exception
    {
        // Superclass
        super.commit(block);
        
        // Insert endorser
        UTILS.DB.executeUpdate("UPDATE adr "
                                + "SET pol_party=0 "
                              + "WHERE adr='"+this.target_adr+"'");
        
        // Reset political influence
        UTILS.DB.executeUpdate("UPDATE adr "
                                + "SET pol_inf=0, "
                                    + "pol_endorsed=0 "
                              + "WHERE adr='"+this.target_adr+"'");
        
        // Removed endorsers
        UTILS.DB.executeUpdate("DELETE FROM endorsers "
                                   + "WHERE endorsed='"+this.target_adr+"' "
                                      + "OR endorser='"+this.target_adr+"'");
        
        // Position type
        UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
    }
    
}
