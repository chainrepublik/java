// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.adr;

import chainrepublik.kernel.UTILS;
import java.sql.ResultSet;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CAdrRegisterPayload extends CPayload 
{
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
   }
   
   public void check(CBlockPayload block) throws Exception
   {
   	// Super class
   	super.check(block);
        
        // Check for null
        if (this.cou==null ||
            this.name==null ||
            this.description==null ||
            this.ref_adr==null ||
            this.node_adr==null ||
            this.pic==null)
        throw new Exception("Null assertion failed - CAdrChgCitPayload.java, 68");
   	  
        // Already registered ?
        if (UTILS.BASIC.isRegistered(this.target_adr, this.block))
           throw new Exception("Address is already registered - CAdrRegisterPayload.java");
        
        // Country
        if (!UTILS.BASIC.isCountry(cou))
             throw new Exception("Invalid country - CAdrRegisterPayload.java");
          
        // Name
        if (!name.matches("^[0-9a-zA-Z]{5,30}$"))
           throw new Exception("Invalid name - CAdrRegisterPayload.java");
        
        // Name exist ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM adr "
                                          + "WHERE name='"+this.name+"'");
        
        // Has data
        if (UTILS.DB.hasData(rs))
            throw new Exception("Name already exist - CAdrRegisterPayload.java");
          
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
        {
             if (!UTILS.BASIC.isAdr(this.ref_adr))
                throw new Exception("Invalid ref adr - CAdrRegisterPayload.java");
             
             if (this.target_adr.equals(ref_adr))
                throw new Exception("Invalid ref adr - CAdrRegisterPayload.java"); 
        }
        
        // Node adr
        if (!this.node_adr.equals(""))
             if (!UTILS.BASIC.isAdr(this.node_adr))
               throw new Exception("Invalid node adr - CAdrRegisterPayload.java");
        
        // Days
        if (this.days<365)
            throw new Exception("Minimum value for days is 365 - CAdrRegisterPayload.java");
        
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
       
       // Address exist ?
       ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM adr "
                                         + "WHERE adr='"+this.target_adr+"'");
       
       // No address
       if (!UTILS.DB.hasData(rs))
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
       
       else
       UTILS.DB.executeUpdate("UPDATE adr "
                               + "SET cou='"+this.cou+"', "
                                   + "loc='"+this.cou+"', "
   	    	                   + "name='"+this.name+"', "
                                   + "description='"+UTILS.BASIC.base64_encode(this.description)+"', "
   	                           + "ref_adr='"+this.ref_adr+"', "
   	                           + "node_adr='"+this.node_adr+"', "
   	    	                   + "pic='"+UTILS.BASIC.base64_encode(this.pic)+"', "
                                   + "expires='"+(this.block+this.days*1440)+"', "
                                   + "block='"+this.block+"', "
      	                           + "created='"+this.block+"' "
                             + "WHERE adr='"+this.target_adr+"'");
    }
  
}
