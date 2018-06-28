// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.trans;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CFeePayload extends CTransPayload 
{
    // Serial
    private static final long serialVersionUID = 100L;
   
   	  
    public  CFeePayload(String src, double amount, String expl_src) throws Exception
    {
         // Constructor
         super(src, 
    	       "default", 
    	       amount,
               "CRC",
               "",
    	       ""); 
         
         // Set message
         this.setExpl(expl_src, expl_src);
         
          // Amount
          if (amount<UTILS.SETTINGS.min_fee)
            this.amount=UTILS.SETTINGS.min_fee;
    }
    
    public void setExpl(String expl_src, String expl_dest) throws Exception
    {
            // Source expl
            this.expl_src=expl_src;
            
            // Dest expl
            this.expl_dest=expl_dest;
            
            // New hash
            this.hash=UTILS.BASIC.hash(this.getHash()+
		                        this.dest+
                                        this.amount+
                                        this.cur+
                                        this.escrower+
                                        this.expl_src+
                                        this.expl_dest); 
           
    }
    
    public void check(CBlockPayload block) throws Exception
    {
    	 // Super
    	super.check(block);
    	    
    	// Destination
    	if (!this.dest.equals("default"))
           throw new Exception("Invalid recipient - CFeePayload.java");
    	
    	// Amount
    	if (amount<0.0001) 
    	   throw new Exception("Invalid fee amount - CFeePayload.java");
        
        // Currency
    	if (!this.cur.equals("CRC")) 
           throw new Exception("Invalid currency - CFeePayload.java");
        
        // Can spend ?
        if (!UTILS.BASIC.canSpend(this.target_adr))
            throw new Exception("Network fee address can't spend funds - CFeePayload.java");
    }
}