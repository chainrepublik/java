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
        if (!UTILS.BASIC.isRegistered(this.target_adr, this.block))
            throw new Exception("Address is not regisstered - CAdrChgCitPayload.java");
        
        // Country
        if (!UTILS.BASIC.isCountry(cou))
            throw new Exception("Invalid country - CAdrChgCitPayload.java");
        
        // Same country ?
        if (!this.cou.equals(UTILS.BASIC.getAdrData(this.target_adr, "cou")))
            throw new Exception("Invalid country - CAdrChgCitPayload.java");
        
        // Location 
        if (!UTILS.BASIC.getAdrData(this.target_adr, "loc").equals(this.cou))
            throw new Exception("Address has to move first - CAdrChgCitPayload.java");
        
        // Owner of a private country ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM countries "
                                          + "WHERE owner='"+this.target_adr+"'");
        
        // Has data ?
        if (UTILS.DB.hasData(rs))
        {
            // Next
            rs.next();
            
            // Move to owned country ?
            if (!this.cou.equals(rs.getString("code")))
                throw new Exception("You can't change your citizenship - CAdrChgCitPayload.java");
        }
        
        // Check hash
 	String h=UTILS.BASIC.hash(this.getHash()+
 			          cou);
 	    
        if (!this.hash.equals(h)) 
            throw new Exception("Invalid hash - CAdrChgCitPayload.java");
 }
   
   public void commit(CBlockPayload block) throws Exception
   {
        // Superclass
        super.commit(block);
       
        // Change cit
        UTILS.DB.executeUpdate("UPDATE adr "
                              + "SET cou='"+this.cou+"', "
                                  + "pol_inf=0, "
                                  + "pol_party=0, "
                                  + "mil_unit=0, "
                                  + "pol_endorsed=0 "
                            + "WHERE adr='"+this.target_adr+"'");
      
        // Remove endorsements
        UTILS.DB.executeUpdate("DELETE FROM endorsers "
                                   + "WHERE endorser='"+this.target_adr+"' "
                                      + "OR endorsed='"+this.target_adr+"'");
      
        // Clear trans
        UTILS.ACC.clearTrans(this.hash, "ID_ALL", this.block);
    }
}