// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.press;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class CVotePayload extends CPayload 
{
   // Target type
   String target_type;
   
   // Tweet ID
   long targetID;
   
   // Type
   String type;
   
   // Serial
   private static final long serialVersionUID = 100L;
   	
   public CVotePayload(String adr, 
                       String target_type,
                       long targetID,
                       String type) throws Exception
   {
	  // Superclass
	   super(adr);
	   
           // Target type
           this.target_type=target_type;
           
	   // Target ID
           this.targetID=targetID;
           
           // Type
           this.type=type;
           
           // Hash
 	   hash=UTILS.BASIC.hash(this.getHash()+
 			         this.target_type+
                                 this.targetID+
                                 this.type);
 	
   }
   
   public void check(CBlockPayload block) throws Exception
   {
       // Super class
       super.check(block);
       
        // Check energy
       this.checkEnergy(1);
       
       // Type
       if (!this.type.equals("ID_UP") && 
           !this.type.equals("ID_DOWN"))
       throw new Exception("Invalid type - CVotePayload.java");
       
       // Target type
       if (!this.target_type.equals("ID_TWEET") && 
           !this.target_type.equals("ID_COM"))
       throw new Exception("Invalid target type - CVotePayload.java");
       
        // Target valid
        if (!UTILS.BASIC.targetValid(this.target_type, this.targetID))
          throw new Exception("Invalid target - CVotePayload.java");
      
        // Already liked ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM votes "
                                          + "WHERE adr='"+this.target_adr+"' "
                                            + "AND target_type='"+this.target_type+"' "
                                            + "AND targetID='"+this.targetID+"'");
            
        if (UTILS.DB.hasData(rs))
           throw new Exception("Already voted  - CVotePayload.java");
       
       // No more than 100 votes in 24 hours
       rs=UTILS.DB.executeQuery("SELECT COUNT(*) AS total "
                                + "FROM votes "
                               + "WHERE adr='"+this.target_adr+"'");
       rs.next();
       
       // Total
       if (rs.getLong("total")>=100)
           throw new Exception("Maximum votes reached - CVotePayload.java");
       
       // Load target data
       if (this.target_type.equals("ID_TWEET"))
       {
           rs=UTILS.DB.executeQuery("SELECT * "
                                    + "FROM tweets "
                                   + "WHERE tweetID='"+this.targetID+"'");
           
           // Next
           rs.next();
           
           // Creation block
           if (this.block-rs.getLong("block")>1440)
               throw new Exception("This article can't be voted anymore - CVotePayload.java");
       }
       
       // Load target data
       if (this.target_type.equals("ID_COM"))
       {
           rs=UTILS.DB.executeQuery("SELECT * "
                                    + "FROM comments "
                                   + "WHERE comID='"+this.targetID+"'");
           
           // Next
           rs.next();
           
           // Creation block
           if (this.block-rs.getLong("block")>1440)
               throw new Exception("This commnet can't be voted anymore - CVotePayload.java");
       }
       
        // Check Hash
        String h=UTILS.BASIC.hash(this.getHash()+
 			          this.target_type+
                                  this.targetID+
                                  this.type);
	  
   	if (!h.equals(this.hash)) 
   	    throw new Exception("Invalid hash  - CVotePayload.java");
   }
   
   public void commit(CBlockPayload block) throws Exception
   {
        ResultSet rs=null;
       
        // Super
        super.commit(block);
        
        // Load tweet data
        if (this.target_type.equals("ID_TWEET"))
            rs=UTILS.DB.executeQuery("SELECT * "
                                     + "FROM tweets "
                                    + "WHERE tweetID='"+this.targetID+"'");
        
        // Load comment data
        if (this.target_type.equals("ID_COM"))
             rs=UTILS.DB.executeQuery("SELECT * "
                                     + "FROM comments "
                                    + "WHERE comID='"+this.targetID+"'");
        
        // Next
        rs.next();
        
        // Percent 
        double p=(this.block-rs.getLong("block"))*0.069;
        
        // Energy
        double e=Double.parseDouble(UTILS.BASIC.getAdrData(this.target_adr, "energy"));
        
        // Power
        double power=UTILS.BASIC.round(e-e*p/100, 2);
       
        // Like
        if (power>0.1)
        UTILS.DB.executeUpdate("INSERT INTO votes "
                                     + "SET adr='"+this.target_adr+"', "
                                         + "target_type='"+this.target_type+"', "
                                         + "targetID='"+this.targetID+"', "
                                         + "type='"+this.type+"', "
                                         + "block='"+this.block+"', "
                                         + "power='"+power+"'");
        
       // Clear
       UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
    }
    
}
