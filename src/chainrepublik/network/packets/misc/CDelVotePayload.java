package chainrepublik.network.packets.misc;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CDelVotePayload extends CPayload
{
    // Delegate
    String delegate;

    // Type
    String type;
    
    public CDelVotePayload(String adr, 
                           String delegate,
                           String type) throws Exception
    {
        // Constructor
        super(adr);
       
        // Address
        this.delegate=delegate;
    
        // Days
        this.type=type;
    
        // Hash
        hash=UTILS.BASIC.hash(this.getHash()+
			      this.delegate+
			      this.type);
	
    }
    
    public void check(CBlockPayload block) throws Exception
    {
        // Constructor
        super.check(block);
        
         // Check energy
       this.checkEnergy();
        
        // Delegate valid
        if (!UTILS.BASIC.isAdr(this.delegate))
            throw new Exception("Invalid delegate address");
        
        // Balance
        if (UTILS.ACC.getBalance(this.target_adr, "CRC")<10)
            throw new Exception("Minimum balance is 10 CRC");
        
        // Type
        if (!this.type.equals("ID_UP") && 
            !this.type.equals("ID_DOWN"))
        throw new Exception("Invalid type");
        
        // Already voted ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM del_votes "
                                          + "WHERE adr='"+this.target_adr+"' "
                                            + "AND delegate='"+this.delegate+"' "
                                            + "AND type='"+this.type+"'");
        
        // Has data ?
        if (UTILS.DB.hasData(rs))
            throw new Exception("You aready voted this delegate"); 
        
        // Max 10 votes
        rs=UTILS.DB.executeQuery("SELECT COUNT(*) AS total "
                                 + "FROM del_votes "
                                + "WHERE adr='"+this.target_adr+"'");
        
        // Next
        rs.next();
        
        // Max 10 votes ?
        if (rs.getLong("total")>=10)
             throw new Exception("Already 10 votes");
        
        // Hash
        String h=UTILS.BASIC.hash(this.getHash()+
			          this.delegate+
			          this.type);
        
        // Hash match
        if (!this.hash.equals(h))
            throw new Exception("Invalid hash");
    }
    
    public void commit(CBlockPayload block) throws Exception
    {
        // Superclass
	super.commit(block);
        
        // Already voted ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM del_votes "
                                          + "WHERE delegate='"+this.delegate+"' "
                                            + "AND adr='"+this.target_adr+"'");
        
        // Has data
        if (UTILS.DB.hasData(rs))
        UTILS.DB.executeUpdate("DELETE FROM del_votes "
                                   + "WHERE adr='"+this.target_adr+"'");
        else
        UTILS.DB.executeUpdate("INSERT INTO del_votes "
                                     + "SET delegate='"+this.delegate+"', "
                                         + "adr='"+this.target_adr+"', "
                                         + "type='"+this.type+"', "
                                         + "block='"+this.block+"'");
        
        // Position type
        UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
    }
}
