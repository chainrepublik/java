// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.adr;

import chainrepublik.kernel.UTILS;
import java.sql.ResultSet;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CAdrRegisterPayload extends CPayload 
{
   // Target address
   String adr;
   
   // Country
   String cou;
   
   // Name
   String name;
   
   // Description
   String description;
   
   // Ref address
   String ref_adr;
   
   // Node address
   String node_adr;
   
   // Pic
   String pic;
   
   // Days
   long days;
  
   // Serial
   private static final long serialVersionUID = 100L;
   
   public CAdrRegisterPayload(String adr,
                              String cou,
                              String name, 
		              String description,
                              String ref_adr, 
                              String node_adr, 
                              String pic,
                              long days) throws Exception
   {
	   // Superclass
	   super(adr);
	   
	   // Country
	   this.cou=cou;
	   
	   // Name
	   this.name=name;
           
           // Description 
	   this.description=description;
	   
	   // Ref adr
	   this.ref_adr=ref_adr;
	   
	   // Node address
	   this.node_adr=node_adr;
	   
	   // Pic
	   this.pic=pic;
           
           // Days
	   this.days=days;
	   
	   // Hash
 	   hash=UTILS.BASIC.hash(this.getHash()+
 			         cou+
 			         name+
                                 description+
 			         ref_adr+
 			         node_adr+
 			         pic+
                                 days);
           
           // Sign
           this.sign();
   }
   
   public void check(CBlockPayload block) throws Exception
   {
   	// Super class
   	super.check(block);
   	  
        // Already registered ?
        if (UTILS.BASIC.isRegistered(this.target_adr))
           throw new Exception("Address is already registered - CAdrRegisterPayload.java");
        
        // Country
        if (!UTILS.BASIC.isCountry(cou))
             throw new Exception("Invalid name - CAdrRegisterPayload.java");
          
        // Name
        if (!this.name.equals(""))
             if (!UTILS.BASIC.isTitle(this.name))
                throw new Exception("Invalid name - CAdrRegisterPayload.java");
          
        // Description
        if (!this.description.equals(""))
             if (!UTILS.BASIC.isDesc(this.description))
              throw new Exception("Invalid description - CAdrRegisterPayload.java");
          
        // Pic 
        if (!this.pic.equals(""))
             if (!UTILS.BASIC.isPic(this.pic))
               throw new Exception("Invalid pic - CAdrRegisterPayload.java");
          
        // Ref adr 
        if (!this.ref_adr.equals(""))
             if (!UTILS.BASIC.isAdr(this.ref_adr))
               throw new Exception("Invalid ref adr - CAdrRegisterPayload.java");
          
        // Node adr
        if (!this.node_adr.equals(""))
             if (!UTILS.BASIC.isAdr(this.node_adr))
               throw new Exception("Invalid node adr - CAdrRegisterPayload.java");
        
        // Check hash
 	String h=UTILS.BASIC.hash(this.getHash()+
 			         cou+
 			         name+
                                 description+
 			         ref_adr+
 			         node_adr+
 			         pic+
                                 days);
 	    
        if (!this.hash.equals(h)) 
            throw new Exception("Invalid hash - CAdrRegisterPayload.java");
 }
   
   public void commit(CBlockPayload block) throws Exception
   {
       // Superclass
       super.commit(block);
       
       // Insert
       UTILS.DB.executeUpdate("INSERT INTO adr "
                                         + "SET adr='"+this.target_adr+"', "
   	                                     + "cou='"+this.cou+"', "
                                             + "loc='"+this.cou+"', "
   	    	                             + "name='"+this.name+"', "
                                             + "description='"+UTILS.BASIC.base64_encode(this.description)+"', "
   	    	                             + "ref_adr='"+this.ref_adr+"', "
   	       	                             + "node_adr='"+this.node_adr+"', "
   	    	                             + "pic='"+UTILS.BASIC.base64_encode(this.pic)+"', "
                                             + "expires='"+(this.block+this.days*1440)+"', "
   	       	                             + "created='"+this.block+"'");
    }
  
}
