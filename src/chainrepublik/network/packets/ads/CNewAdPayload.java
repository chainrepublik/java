// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.ads;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;


public class CNewAdPayload extends CPayload 
{
   // Title
   public String title;
	
   // Message
   public String mes;
	
   // Link
   public String link;
   
   // Market bid
   public double bid;
   
   // Expires
   public long hours;
	
   public CNewAdPayload(String adr,
		        String title, 
		        String mes, 
		        String link,
                        long hours, 
		        double bid) throws Exception
   {
	  // Superclass
	   super(adr);
	   
	   // Title
	   this.title=title;
		
	   // Message
	   this.mes=mes;
		
	   // Link
	   this.link=link;
	   
           // Expires
	   this.hours=hours;
           
	   // Market bid
	   this.bid=bid;
	   
	   // Hash
 	   hash=UTILS.BASIC.hash(this.getHash()+
 			         this.title+
 			         this.mes+
 			         this.link+
 			         this.bid+
                                 this.hours);
 	   
 	   //Sign
           this.sign();
   }
   
   public void check(CBlockPayload block) throws Exception
   {
           // Super class
   	  super.check(block);
          
           // Check energy
           this.checkEnergy();
   	  
   	  // Check hours
  	  if (this.hours<1) 
  	    throw new Exception("Invalid period - CNewAdPayload.java");
   	   
          // Bid
          if (this.bid<0.0001)
             throw new Exception("Invalid bid - CNewAdPayload.java");
          
  	  // Check Title 
          if (!UTILS.BASIC.isString(this.title))
            throw new Exception("Invalid title - CNewAdPayload.java");
            
          // Check title length
          if (this.title.length()<5 || this.title.length()>30)
              throw new Exception("Invalid title - CNewAdPayload.java");
   	  
          // Check message 
          if (!UTILS.BASIC.isString(this.mes))
            throw new Exception("Invalid message - CNewAdPayload.java");
            
          // Check message length
          if (this.mes.length()<50 || this.mes.length()>70)
              throw new Exception("Invalid message - CNewAdPayload.java");
          
	  // Check link
	  if (!UTILS.BASIC.isLink(this.link))
	     throw new Exception("Invalid link - CNewAdPayload.java");
          
          
	   // Check Hash
	   String h=UTILS.BASIC.hash(this.getHash()+
 			             this.title+
 			             this.mes+
 			             this.link+
 			             this.bid+
                                     this.hours);
	  
          // Hash
   	  if (!h.equals(this.hash)) 
             throw new Exception("Invalid hash - CNewAdPayload.java");
   	 
 	
   }
   
   public void commit(CBlockPayload block) throws Exception
   {
       // Superclass
       super.commit(block);
       
       // Commit
       UTILS.DB.executeUpdate("INSERT INTO ads "
                                    + "SET adr='"+this.target_adr+"', "
   		   		        + "title='"+UTILS.BASIC.base64_encode(this.title)+"', "
   		                        + "message='"+UTILS.BASIC.base64_encode(this.mes)+"', "
   		   	                + "link='"+UTILS.BASIC.base64_encode(this.link)+"', "
                                        + "mkt_bid='"+UTILS.FORMAT_4.format(this.bid)+"', "
   		   	                + "expire='"+(this.block+(this.hours*60))+"', "
   		      	                + "block='"+this.block+"'");
       
       // Clear trans
       UTILS.ACC.clearTrans(this.hash, "ID_ALL", this.block);
    }
}
