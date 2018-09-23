package chainrepublik.network.packets.adr;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CUpdateProfilePayload extends CPayload
{
   // Avatar
   String img;
   
   // Description
   String description;
   
   // Serial
   private static final long serialVersionUID = 100L;
   
   public CUpdateProfilePayload(String adr,
                                String img,
                                String description) throws Exception
   {
	// Superclass
	super(adr);
	   
	// Avatar
	this.img=img;
           
        // Description
	this.description=description;
	   
	// Hash
 	hash=UTILS.BASIC.hash(this.getHash()+
		              this.img+
                              this.description);
   }
   
   public void check(CBlockPayload block) throws Exception
   {
   	// Super class
   	super.check(block);
        
         // Check energy
        this.checkEnergy();
        
        // Check for null
        if (this.img==null ||
            this.description==null)
            throw new Exception("Null assertion failed - CUpdateProfilePayload.java, 68");
   	
        // Registered ?
        if (!UTILS.BASIC.isRegistered(this.target_adr, this.block))
            throw new Exception("Address is not regisstered - CUpdateProfilePayload.java");
        
        // Citizen address ?
        if (!UTILS.BASIC.isCitAdr(this.target_adr, this.block))
           throw new Exception("Only citizens can do this action - CWorkPayload.java, 68");
        
        // Avatar 
        if (!this.img.equals(""))
             if (!UTILS.BASIC.isPic(this.img))
               throw new Exception("Invalid pic - CUpdateProfilePayload.java");
        
        // Description
        if (!this.description.equals(""))
             if (!UTILS.BASIC.isDesc(this.description))
              throw new Exception("Invalid description - CUpdateProfilePayload.java");
        
        // Check hash
 	String h=UTILS.BASIC.hash(this.getHash()+
 			          this.img+
                                  this.description);
 	    
        if (!this.hash.equals(h)) 
            throw new Exception("Invalid hash - CUpdateProfilePayload.java");
 }
   
   public void commit(CBlockPayload block) throws Exception
   {
       // Superclass
       super.commit(block);
       
       // Change profile
      UTILS.DB.executeUpdate("UPDATE adr "
                              + "SET pic='"+UTILS.BASIC.base64_encode(this.img)+"', "
                                  + "description='"+UTILS.BASIC.base64_encode(this.description)+"' "
                           + "WHERE adr='"+this.target_adr+"'");
      
      // Clear trans
       UTILS.ACC.clearTrans(this.hash, "ID_ALL", this.block);
    }
}
