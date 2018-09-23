// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.press;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class CUnfollowPayload extends CPayload 
{
   // Tweet ID
   String unfollow_adr;
   
   // Serial
   private static final long serialVersionUID = 100L;
   
   public CUnfollowPayload(String adr, 
                           String unfollow_address) throws Exception
   {
	  // Superclass
	   super(adr);
	   
	   // Follow address
           this.unfollow_adr=unfollow_address;
   
	   // Hash
 	   hash=UTILS.BASIC.hash(this.getHash()+
 			         this.unfollow_adr);
 	  
   }
   
   public void check(CBlockPayload block) throws Exception
   {
        // Super class
        super.check(block);
        
        // Check for null
        if (this.unfollow_adr==null)
            throw new Exception("Null assertion failed - CUnfollowPayload.java, 68");
        
         // Check energy
       this.checkEnergy();
       
       // Citizen address ?
        if (!UTILS.BASIC.isCitAdr(this.target_adr, this.block))
           throw new Exception("Only citizens can do this action - CUnfollowPayload.java, 68");
   	
   	// Follow address valid
        if (!UTILS.BASIC.isAdr(this.unfollow_adr))
           throw new Exception("Invalid address - CUnfollowPayload.java"); 
            
        // Address is followed ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM tweets_follow "
                                          + "WHERE adr='"+this.target_adr+"' "
                                            + "AND follows='"+this.unfollow_adr+"'");
             
        if (!UTILS.DB.hasData(rs))
            throw new Exception("Invalid address - CUnfollowPayload.java"); 
             
        // Check Hash
	String h=UTILS.BASIC.hash(this.getHash()+
 	                          this.unfollow_adr);
	  
   	if (!h.equals(this.hash)) 
           throw new Exception("Invalid hash - CUnfollowPayload.java"); 
   }
   
   public void commit(CBlockPayload block) throws Exception
   {
        // Super
       super.commit(block);
       
        // Unfollow
        UTILS.DB.executeUpdate("DELETE FROM tweets_follow "
                                     + "WHERE adr='"+this.target_adr+"' "
                                       + "AND follows='"+this.unfollow_adr+"'");
        
        // Position type
        UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
    }
}
