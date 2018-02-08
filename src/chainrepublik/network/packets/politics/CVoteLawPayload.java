package chainrepublik.network.packets.politics;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CVoteLawPayload extends CPayload
{
    // Address
    String adr;
    
    // Law ID
    long lawID;
    
    // Vote
    String vote_type;
    
    public CVoteLawPayload(String adr, 
                          long lawID, 
                          String vote_type) throws Exception
    {
        // Superclass
	super(adr);
        
        // Law ID
        this.lawID=lawID;
        
        // Votye type
        this.vote_type=vote_type;
        
        // Hash
 	hash=UTILS.BASIC.hash(this.getHash()+
                              this.lawID+
                              this.vote_type);
           
        // Sign
        this.sign();
    }
    
    public void check(CBlockPayload block) throws Exception
    {
   	// Super class
   	super.check(block);
        
         // Check energy
       this.checkEnergy();
        
        // Registered voter ?
        if (!UTILS.BASIC.isRegistered(this.target_adr))
            throw new Exception("Target address is not registered, CVoteLawPayload.java, 102");
        
        // Voter country
        String adr_cou=UTILS.BASIC.getAdrData(this.target_adr, "cou");
        
        // Check lawID
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM laws "
                                          + "WHERE lawID='"+this.lawID+"' "
                                            + "AND status='ID_PENDING'");
        
        // Next
        rs.next();
        
        // Same country
        if (!rs.getString("cou").equals(adr_cou))
            throw new Exception("Voter is not authorized to vote this law, CVoteLawPayload.java, 102");
        
        // Voter is a governor ?
        if (!UTILS.BASIC.isGovernor(this.target_adr, adr_cou))
            throw new Exception("Voter not a governor, CVoteLawPayload.java, 102");
        
        // Already voted ?
        rs=UTILS.DB.executeQuery("SELECT * "
                                 + "FROM laws_votes "
                                + "WHERE adr='"+this.target_adr+"' "
                                  + "AND lawID='"+this.lawID+"'");
        
        // Has data ?
        if (!UTILS.DB.hasData(rs))
            throw new Exception("You already voted this proposal, CVoteLawPayload.java, 102");
    }
    
    public void commit(CBlockPayload block) throws Exception
    {
        // Superclass
        super.commit(block);
        
        // Insert vote
        UTILS.DB.executeUpdate("INSERT INTO laws_votes "
                                     + "SET lawID='"+this.lawID+"', "
                                         + "adr='"+this.target_adr+"', "
                                         + "vote_type='"+this.vote_type+"', "
                                         + "block='"+this.block+"'");
        
        // Pol influence
        double pol_inf=Double.parseDouble(UTILS.BASIC.getAdrData(this.target_adr, "pol_inf"));
        
        // Update votes
        if (this.vote_type.equals("ID_YES"))
        UTILS.DB.executeUpdate("UPDATE laws "
                                + "SET voted_yes=voted_yes+"+pol_inf+" "
                              + "WHERE lawID='"+this.lawID+"'");
        
        else
        UTILS.DB.executeUpdate("UPDATE laws "
                                + "SET voted_no=voted_no+"+pol_inf+" "
                              + "WHERE lawID='"+this.lawID+"'");
        
        // Position type
        UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
    }
    
}
