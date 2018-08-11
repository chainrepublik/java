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
        
        // Citizen address ?
        if (!UTILS.BASIC.isCitAdr(this.target_adr, this.block))
           throw new Exception("Only citizens can do this action - CWorkPayload.java, 68");
        
        // Referer
        if (!UTILS.BASIC.isAdr(this.ref))
	   throw new Exception("Invalid referer - CSendRefPayload.java, line 49");
        
        // Receiver
        if (!UTILS.BASIC.isAdr(this.rec))
	   throw new Exception("Invalid receiver - CSendRefPayload.java, line 49");
        
        // Check adr
        if (this.target_adr.equals(this.rec) || 
            this.target_adr.equals(this.ref) || 
            this.ref.equals(this.rec))
        throw new Exception("Invalid addressess - CSendRefPayload.java, line 49");
        
	// Owns afiliate ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM adr "
                                          + "WHERE adr='"+this.ref+"' "
                                            + "AND ref_adr='"+this.target_adr+"' "
                                            + "AND cou<>''");
        
        // Has data ?
        if (!UTILS.DB.hasData(rs))
           throw new Exception("Invalid afiliate - CSendRefPayload.java, line 60");
        
        // Check hash
	String h=UTILS.BASIC.hash(this.getHash()+
		                  this.rec+
                                  this.ref);
	
        // Check hash
        if (!this.hash.equals(h))
	   throw new Exception("Invalid hash - CSendRefPayload.java, line 69");
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

