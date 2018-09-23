// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.press;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class CCommentPayload extends CPayload 
{
   // Parent type
   String parent_type;
   
   // Parent ID
   long parentID;
   
   // Message
   String mes;
   
   // Comment ID
   long comID;
   
   // Serial
   private static final long serialVersionUID = 100;
   	
   public CCommentPayload(String adr, 
                          String parent_type,
		          long parentID,
                          String mes) throws Exception
   {
	  // Superclass
	   super(adr);
	   
	   // Parent type
           this.parent_type=parent_type;
   
           // Parent ID
           this.parentID=parentID;
           
           // Message
           this.mes=mes;
           
           // Row ID
           this.comID=UTILS.BASIC.getID();
   
	   // Hash
 	   hash=UTILS.BASIC.hash(this.getHash()+
 			         this.parent_type+
                                 this.parentID+
                                 this.comID+
                                 this.mes);
 }
   
   public void check(CBlockPayload block) throws Exception
   {
      // Super class
      super.check(block);
      
      // Check for null
      if (this.parent_type==null ||
          this.mes==null)
      throw new Exception("Null assertion failed - CTweetMesPayload.java, 68");
      
       // Check energy
       this.checkEnergy(1);
       
       // Citizen address ?
        if (!UTILS.BASIC.isCitAdr(this.target_adr, this.block))
           throw new Exception("Only citizens can do this action - CTweetMesPayload.java, 68");
      
      // ID
      if (UTILS.BASIC.isID(comID))
          throw new Exception("Invalid ID - CTweetMesPayload.java");
      
      // Check Message
      if (!UTILS.BASIC.isDesc(mes, 1000))
	  throw new Exception("Invalid message - CTweetMesPayload.java");
      
      // Target valid
      if (!UTILS.BASIC.targetValid(this.parent_type, this.parentID))
            throw new Exception("Invalid target type - CTweetMesPayload.java");
      
      // Already commented ?
      ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                         + "FROM comments "
                                        + "WHERE parent_type='"+this.parent_type+"' "
                                          + "AND parentID='"+this.parentID+"' "
                                          + "AND adr='"+this.target_adr+"'");
          
      if (UTILS.DB.hasData(rs))
         throw new Exception("Already commented - CTweetMesPayload.java");
            
      // Check Hash
      String h=UTILS.BASIC.hash(this.getHash()+
 			        this.parent_type+
                                this.parentID+
                                this.comID+
                                this.mes);
	  
      if (!h.equals(this.hash)) 
   	 throw new Exception("Invalid hash - CTweetMesPayload.java");
     
   }
   
   public void commit(CBlockPayload block) throws Exception
   {
       // Super
       super.commit(block);
       
       // Commit
       UTILS.DB.executeUpdate("INSERT INTO comments "
                                    + "SET adr='"+this.target_adr+"',"
                                        + "parent_type='"+this.parent_type+"', "
                                        + "parentID='"+this.parentID+"', "
                                        + "comID='"+this.comID+"', "
   		   		        + "mes='"+UTILS.BASIC.base64_encode(this.mes)+"', "
                                        + "expires='"+(this.block+43200)+"', "
   		   		        + "block='"+this.block+"'");
       
        // Position type
        UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
       
    }
}
