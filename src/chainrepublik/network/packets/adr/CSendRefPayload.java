package chainrepublik.network.packets.adr;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CSendRefPayload extends CPayload
{
    // Receiver
    String rec;
    
    // Ref
    String ref;
        
    // Serial
    private static final long serialVersionUID = 100L;
	
    public CSendRefPayload(String adr, 
			   String rec,
                           String ref)  throws Exception
   {
        // Constructor
        super(adr);
	         
        // Receiver
        this.rec=rec;
        
        // Ref
        this.ref=ref;
	
        // Hash
	hash=UTILS.BASIC.hash(this.getHash()+
		              this.rec+
                              this.ref);
    }
	

    public void check(CBlockPayload block) throws Exception
    {
        // Constructor
        super.check(block);
            
        // Check energy
        this.checkEnergy();
	    	
	// Check receiver
        if (!UTILS.BASIC.isAdr(this.rec) || 
            !UTILS.BASIC.isRegistered(this.rec))
            throw new Exception("Invalid receiver - CMesPayload.java");
        
        
        // Registered
        if (!UTILS.BASIC.isAdr(this.ref) || 
            !UTILS.BASIC.isRegistered(this.ref))
            throw new Exception("Invalid afiliate - CMesPayload.java");
        
        // Owns afiliate ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM adr "
                                          + "WHERE adr='"+this.ref+"' "
                                            + "AND ref_adr='"+this.target_adr+"'");
        
        // Has data ?
        if (!UTILS.DB.hasData(rs))
           throw new Exception("Invalid afiliate - CMesPayload.java");
        
        // Check hash
	String h=UTILS.BASIC.hash(this.getHash()+
		                  this.rec+
                                  this.ref);
	
        // Check hash
        if (!this.hash.equals(h))
	   throw new Exception("Invalid hash - CMesPayload.java");
    }
	   
	public void commit(CBlockPayload block) throws Exception
	{
	    // Superclass
	    super.commit(block);
            
            // Remove
            UTILS.DB.executeUpdate("UPDATE adr "
                                    + "SET ref_adr='"+this.rec+"' "
                                  + "WHERE adr='"+this.ref+"'");
            
            // Event
            UTILS.BASIC.newEvent(this.rec, 
                                 UTILS.BASIC.getAdrData(this.target_adr, "name")+" send you an affiliate ("+UTILS.BASIC.getAdrData(this.ref, "name")+")", 
                                 this.block);
            
            // Position type
            UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
        }
    }

