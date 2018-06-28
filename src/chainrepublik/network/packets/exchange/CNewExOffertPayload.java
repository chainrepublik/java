package chainrepublik.network.packets.exchange;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CNewExOffertPayload extends CPayload
{
    // Receiver
    long exID;
    
    // MArket side
    String side;
    
    // Price type
    String price_type;
    
    // Margin
    long margin;
    
    // Price
    double price;
    
    // Min trade
    double min;
    
    // Max trade
    double max;
    
    // Method
    String method;
    
    // Details
    String details;
    
    // Payment info
    String pay_info;
    
    // Contact
    String contact;
    
    // Days
    long days;
        
    // Serial
    private static final long serialVersionUID = 100L;
	
    public CNewExOffertPayload(String adr, 
			       String side,
                               String price_type,
                               long margin,
                               double price,
                               double min,
                               double max,
                               String method,
                               String details,
                               String pay_info,
                               String contact,
                               long days,
                               long block)  throws Exception
   {
        // Constructor
        super(adr);
        
        // Ex ID
        this.exID=UTILS.BASIC.getID();
	
        // Market side
        this.side=side;
        
        // Price type
        this.price_type=price_type;
        
        // Margin
        this.margin=margin;
        
        // Price
        this.price=price;
        
        // Min
        this.min=min;
        
        // Max
        this.max=max;
        
        // Method
        this.method=method;
        
        // Details
        this.details=details;
        
        // Pay info
        this.pay_info=pay_info;
        
        // Contact
        this.contact=contact;
        
        // Days
        this.days=days;
	
        // Hash
	hash=UTILS.BASIC.hash(this.getHash()+
                              this.exID+
                              this.side+
                              this.price_type+
                              this.margin+
                              this.price+
                              this.min+
                              this.max+
                              this.method+
                              this.details+
                              this.pay_info+
                              this.contact+
                              this.days+
		              this.exID);
    }
	

    public void check(CBlockPayload block) throws Exception
    {
        // Constructor
        super.check(block);
            
        // Check energy
        this.checkEnergy();
	    	
	// Ex ID
        if (UTILS.BASIC.isID(this.exID))
            throw new Exception("Invalid exchange ID, CNewExOffertPayload.java, 113");
        
        // Market side
        if (!this.side.equals("ID_BUY") && 
            !this.side.equals("ID_SELL"))
            throw new Exception("Invalid side, CNewExOffertPayload.java, 113");   
        
        // Price type
        if (!this.price_type.equals("ID_LIVE") && 
            !this.price_type.equals("ID_FIXED"))
            throw new Exception("Invalid price type, CNewExOffertPayload.java, 113");   
        
        // Margin
        if (this.price_type.equals("ID_FIXED") && 
            this.margin!=0)
            throw new Exception("Invalid margin, CNewExOffertPayload.java, 113");   
        
        // Margin
        if (this.price_type.equals("ID_LIVE") && 
            this.price!=0)
            throw new Exception("Invalid margin, CNewExOffertPayload.java, 113");   
        
        // Fixed price ?
        if (this.price_type.equals("ID_LIVE"))
            if (this.margin<0 || 
                this.margin>25)
                throw new Exception("Invalid margin, CNewExOffertPayload.java, 113");   
        
        // Fixed price
        if (this.price_type.equals("ID_FIXED"))
            if (this.price<0.01)
               throw new Exception("Invalid price, CNewExOffertPayload.java, 113");
        
        // Check hash
	String h=UTILS.BASIC.hash(this.getHash()+
                                  this.exID+
                                  this.side+
                                  this.price_type+
                                  this.margin+
                                  this.price+
                                  this.min+
                                  this.max+
                                  this.method+
                                  this.details+
                                  this.pay_info+
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

