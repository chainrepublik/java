package chainrepublik.network.packets.politics.congress;

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
       
        // Vote type
        if (!this.vote_type.equals("ID_YES") &&
            !this.vote_type.equals("ID_NO"))
        throw new Exception("Invalid vote type, CVoteLawPayload.java, 102");
        
        // Voter country
        String adr_cou=UTILS.BASIC.getAdrData(this.target_adr, "cou");
        
        // Check lawID
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM laws "
                                          + "WHERE lawID='"+this.lawID+"' "
                                            + "AND country='"+adr_cou+"' "
                                            + "AND status='ID_VOTING'");
        
        // Has data ?
        if (!UTILS.DB.hasData(rs))
            throw new Exception("Invalid law ID, CVoteLawPayload.java, 102");
       
        // Voter is a governor ?
        if (!UTILS.BASIC.isGovernor(this.target_adr, adr_cou))
            throw new Exception("Voter not a governor, CVoteLawPayload.java, 102");
        
        // Already voted ?
        rs=UTILS.DB.executeQuery("SELECT * "
                                 + "FROM laws_votes "
                                + "WHERE adr='"+this.target_adr+"' "
                                  + "AND lawID='"+this.lawID+"'");
        
        // Has data ?
        if (UTILS.DB.hasData(rs))
            throw new Exception("You already voted this proposal, CVoteLawPayload.java, 102");
        
        // Hash
        String h=UTILS.BASIC.hash(this.getHash()+
                                  this.lawID+
                                  this.vote_type);
        
        // Hash check ?
        if (!h.equals(this.hash))
            throw new Exception("Invalid hash, CVoteLawPayload.java, 102");
    }
    
    public void commit(CBlockPayload block) throws Exception
    {
        // Superclass
        super.commit(block);
        
        // Pol influence
        double pol_endorsed=Double.parseDouble(UTILS.BASIC.getAdrData(this.target_adr, "pol_endorsed"));
        
        // Country
        String cou=UTILS.BASIC.getAdrData(this.target_adr, "cou");
        
        // Load total political endorement
        ResultSet rs=UTILS.DB.executeQuery("SELECT *  "
                                           + "FROM adr "
                                          + "WHERE cou='"+cou+"' "
                                            + "AND pol_endorsed>0 "
                                       + "ORDER BY pol_endorsed DESC "
                                          + "LIMIT 0,25");
        
        // Parse
        double total=0;
        while (rs.next()) 
                total=total+rs.getDouble("pol_endorsed");
        
        // Pol endorsed over 10%
        if (pol_endorsed>total/10)
            pol_endorsed=Math.round(total/10);
        
        // Insert vote
        UTILS.DB.executeUpdate("INSERT INTO laws_votes "
                                     + "SET lawID='"+this.lawID+"', "
                                         + "adr='"+this.target_adr+"', "
                                         + "type='"+this.vote_type+"', "
                                         + "points='"+pol_endorsed+"', "
                                         + "block='"+this.block+"'");
        
        // Update laws
        if (this.vote_type.equals("ID_YES"))
            UTILS.DB.executeUpdate("UPDATE laws "
                                    + "SET voted_yes=voted_yes+"+pol_endorsed+" "
                                  + "WHERE lawID="+this.lawID);
        else
           UTILS.DB.executeUpdate("UPDATE laws "
                                    + "SET voted_no=voted_no+"+pol_endorsed+" "
                                  + "WHERE lawID="+this.lawID);
        
        // Position type
        UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
    }
    
}
