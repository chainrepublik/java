package chainrepublik.network.packets.exchange;

import chainrepublik.kernel.CAddress;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CRemoveExOffertPayload extends CPayload
{
    // Receiver
    long exID;
        
    // Serial
    private static final long serialVersionUID = 100L;
	
    public CRemoveExOffertPayload(String adr, 
			          long exID)  throws Exception
   {
        // Constructor
        super(adr);
	         
        // Receiver
        this.exID=exID;
	
        // Hash
	hash=UTILS.BASIC.hash(this.getHash()+
		              this.exID);
    }
	

    public void check(CBlockPayload block) throws Exception
    {
        // Constructor
        super.check(block);
            
        // Check energy
        this.checkEnergy();
	    	
	
        // Check hash
	String h=UTILS.BASIC.hash(this.getHash()+
		                  this.exID);
	
        // Check hash
        if (!this.hash.equals(h))
	   throw new Exception("Invalid hash - CMesPayload.java");
    }
	   
	public void commit(CBlockPayload block) throws Exception
	{
	    // Superclass
	    super.commit(block);
            
            // Position type
            UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
        }
    }

