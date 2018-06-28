package chainrepublik.network.packets.adr;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CAdrChgCitPayload extends CPayload
{
   // Country
   String cou;
   
   // Serial
   private static final long serialVersionUID = 100L;
   
   public CAdrChgCitPayload(String adr,
                              String cou) throws Exception
   {
	   // Superclass
	   super(adr);
	   
	   // Country
	   this.cou=cou;
	   
	   // Hash
 	   hash=UTILS.BASIC.hash(this.getHash()+
 			         cou);
   }
   
   public void check(CBlockPayload block) throws Exception
   {
   	// Super class
   	super.check(block);
        
         // Check energy
        this.checkEnergy();
   	
        // Registered ?
        if (!UTILS.BASIC.isRegistered(this.target_adr))
            throw new Exception("Address is not regisstered - CAdrRegisterPayload.java");
        
        // Country
        if (!UTILS.BASIC.isCountry(cou))
             throw new Exception("Invalid name - CAdrRegisterPayload.java");
        
        // Location 
        if (!UTILS.BASIC.getAdrData(this.target_adr, "loc").equals(this.cou))
            throw new Exception("Address has to move first - CAdrRegisterPayload.java");
        
        // Check hash
 	String h=UTILS.BASIC.hash(this.getHash()+
 			          cou);
 	    
        if (!this.hash.equals(h)) 
            throw new Exception("Invalid hash - CAdrRegisterPayload.java");
 }
   
   public void commit(CBlockPayload block) throws Exception
   {
       // Superclass
       super.commit(block);
       
       
      // Change cit
      UTILS.DB.executeUpdate("UPDATE adr "
                              + "SET cou='"+this.cou+"', "
                                  + "pol_inf=0, "
                                  + "premium=0 "
                            + "WHERE adr='"+this.target_adr+"'");
      
      // Clear trans
      UTILS.ACC.clearTrans(this.hash, "ID_ALL", this.block);
    }
}