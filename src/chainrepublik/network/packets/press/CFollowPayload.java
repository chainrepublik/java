// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.press;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;


public class CFollowPayload extends CPayload 
{
   // Tweet ID
   String follow_adr;
   
   // Months
   long days;
   
   // Serial
   private static final long serialVersionUID = 1;
   
   public CFollowPayload(String adr, 
		         String follow_adr,
                         long days) throws Exception
   {
	  // Superclass
	   super(adr);
	   
	   // Follow address
           this.follow_adr=follow_adr;
           
           // Months
           this.days=days;
   
	   // Hash
 	   hash=UTILS.BASIC.hash(this.getHash()+
 			         this.follow_adr+
                                 this.days);
 	   
 	   // Sign
           this.sign();
   }
   
   public void check(CBlockPayload block) throws Exception
   {
       // Super class
       super.check(block);
       
        // Check energy
       this.checkEnergy();
       
       // Follow address same as target
       if (this.follow_adr.equals(this.target_adr))
           throw new Exception("Invalid follow address - CFollowPayload.java");
   	
       // Follow address valid
       if (!UTILS.BASIC.isRegistered(this.follow_adr))
          throw new Exception("Invalid follow address - CFollowPayload.java");
       
       // Already follow ?
       ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM tweets_follow "
                                         + "WHERE adr='"+this.target_adr+"' "
                                           + "AND follows='"+this.follow_adr+"'");
       
       // Has data
       if (UTILS.DB.hasData(rs))
           throw new Exception("Already following - CFollowPayload.java");
       
       // Days
       if (this.days<30)
           throw new Exception("Invalid period - CFollowPayload.java");
             
       // Check Hash
       String h=UTILS.BASIC.hash(this.getHash()+
 			         this.follow_adr+
                                 this.days);
	  
       if (!h.equals(this.hash)) 
   	   throw new Exception("Invalid hash - CFollowPayload.java");
   }
   
   public void commit(CBlockPayload block) throws Exception
   {
       // Super
       super.commit(block);
       
       // Insert 
       UTILS.DB.executeUpdate("INSERT INTO tweets_follow "
                                    + "SET adr='"+this.target_adr+"', "
                                        + "follows='"+this.follow_adr+"', "
                                        + "block='"+this.block+"', "
                                        + "expires='"+(this.block+this.days*1440)+"'");
       
       // Position type
       UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
    }
}
