package chainrepublik.network.packets.politics.misc;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CBuyCouPayload extends CPayload
{
   // Country
   String cou;
 
   public CBuyCouPayload(String adr, 
                         String cou) throws Exception
    {
        // Superclass
	super(adr);
        
        // Type
        this.cou=cou;
        
        // Hash
 	hash=UTILS.BASIC.hash(this.getHash()+
 			      this.cou);
    }
    
    public void check(CBlockPayload block) throws Exception
    {
   	// Super class
   	super.check(block);
        
        // Energy
        this.checkEnergy();
        
        // Citizen address ?
        if (!UTILS.BASIC.isCitAdr(this.target_adr, this.block))
           throw new Exception("Only citizens can do this action - CWorkPayload.java, 68");
        
        // Registered
        if (!UTILS.BASIC.isRegistered(this.target_adr, this.block))
            throw new Exception("Target address is not registered, CRentPayload.java, 102");
        
        // Country is for sale ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM countries "
                                          + "WHERE private='YES' "
                                            + "AND owner='default' "
                                            + "AND code='"+this.cou+"'");
        
        // Has records ?
        if (!UTILS.DB.hasData(rs))
            throw new Exception("Country is not for sale, CRentPayload.java, 102");
        
        // Address owns another country ?
        rs=UTILS.DB.executeQuery("SELECT * "
                                 + "FROM countries "
                                + "WHERE adr='"+this.target_adr+"'");
        
        // Has records
        if (UTILS.DB.hasData(rs))
            throw new Exception("Address already owns a country, CRentPayload.java, 102");
        
        // Finds price
        rs=UTILS.DB.executeQuery("SELECT COUNT(*) AS total "
                                 + "FROM countries "
                                + "WHERE private='YES' "
                                  + "AND owner<>'default'");
        
        // Next
        rs.next();
        
        // Number
        long no=rs.getLong("total");
        
        // Price
        long price=50*no;
        
        // Min price
        if (price<50)
            price=50;
        
        // Funds ?
        if (UTILS.ACC.getBalance(this.target_adr, "CRC", block)<price)
            throw new Exception("Insuficient funds, CRentPayload.java, 102");
        
        // Transfer
        UTILS.ACC.newTransfer(this.target_adr, 
                              "default", 
                              price, 
                              "CRC", 
                              "You have bought a private country", 
                              "", 
                              0, 
                              hash, 
                              this.block, 
                              false, 
                              "", 
                              "");
        
        // Check hash
        String h=UTILS.BASIC.hash(this.getHash()+
 			          this.cou);
        
        // Hash match ?
        if (!h.equals(this.hash))
           throw new Exception("Invalid hash, CConsumeItemPayload.java, 102");
    }
    
    public void commit(CBlockPayload block) throws Exception
    {
         // Superclass
         super.commit(block);
         
        // Move country
        UTILS.DB.executeUpdate("UPDATE countries "
                                + "SET owner='"+this.target_adr+"' "
                              + "WHERE code='"+this.cou+"'");
        
        // Change cit
        UTILS.DB.executeUpdate("UPDATE adr "
                                + "SET cou='"+this.cou+"', "
                                    + "loc='"+this.cou+"', "
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
