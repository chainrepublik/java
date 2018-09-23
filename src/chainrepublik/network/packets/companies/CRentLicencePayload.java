package chainrepublik.network.packets.companies;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CRentLicencePayload extends CPayload
{
    // Company ID
    long comID;
    
    // Workplace ID
    String lic;
    
    // Days
    long days;
    
    // Prod stoc ID
    long prod_stocID;
    
    // Licence stoc ID
    long lic_stocID;
    
    // Serial
    private static final long serialVersionUID = 100L;
    
    public CRentLicencePayload(String adr, 
                               long comID, 
                               String lic, 
                               long days) throws Exception
    {
        // Superclass
	super (adr);
        
        // Company ID
        this.comID=comID;
    
        // Workplace ID
        this.lic=lic;
    
        // Days
        this.days=days;
        
        // Prod stoc ID
        this.prod_stocID=UTILS.BASIC.getID();
        
        // Licence stoc ID
        this.lic_stocID=UTILS.BASIC.getID();
        
        // Hash
 	hash=UTILS.BASIC.hash(this.getHash()+
 			      this.comID+
 			      this.lic+
 			      this.days+
                              this.prod_stocID+
                              this.lic_stocID);
        
    }
    
    public void check(CBlockPayload block) throws Exception
   {
   	// Super class
   	super.check(block);
        
        // Check for null
        if (this.lic==null)
            throw new Exception("Null assertion failed - CRentLicencePayload.java, 68");
        
        // Energy
        this.checkEnergy();
        
        // Citizen address ?
        if (!UTILS.BASIC.isCitAdr(this.target_adr, this.block))
           throw new Exception("Only citizens can do this action - CRentLicencePayload.java, 68");
        
        // Company address
        String com_adr=UTILS.BASIC.getComAdr(this.comID);
        
        // Company address
        if (!UTILS.BASIC.isComOwner(this.target_adr, comID))
           throw new Exception("Address is not company owner, CRentLicencePayload.java, 53");
        
        // Licence
        if (!UTILS.BASIC.isStringID(this.lic))
           throw new Exception("Invalid licence, CRentLicencePayload.java, 63");
        
        // Already has this licence ?
        if (UTILS.BASIC.hasProd(com_adr, this.lic))
            throw new Exception("Company has this licence already, CRentLicencePayload.java, 63");
        
        // Valid ID
        if (UTILS.BASIC.isID(this.prod_stocID) || 
            UTILS.BASIC.isID(this.lic_stocID))
           throw new Exception("Invalid stoc ID, CRentLicencePayload.java, 72");
        
        // Days
        if (days<30)
            throw new Exception("Invalid days, CRentLicencePayload.java, 67");
        
        // Company type
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM tipuri_licente "
                                          + "WHERE tip='"+this.lic+"' "
                                            + "AND com_type='"+UTILS.BASIC.getComType(this.comID)+"'");
        
        // Has data
        if (!UTILS.DB.hasData(rs))
            throw new Exception("Invalid licence, CRentLicencePayload.java, 63");  
        
        // Load details
        rs.next();
        
        // Price
        double price=UTILS.CONST.lic_price*days;
        
        // Funds ?
        if (UTILS.ACC.getBalance(com_adr, "CRC", block)<price)
            throw new Exception("Insuficient funds, CRentLicencePayload.java, 77");
        
         // Take money
        UTILS.ACC.newTransfer(com_adr, 
                              "default", 
                              price, 
                              "CRC", 
                              "You rented a licence for "+days+" days", 
                              "", 
                              0,
                              this.hash, 
                              this.block,
                              false,
                              "",
                              "");
        
        // Hash
        String h=UTILS.BASIC.hash(this.getHash()+
 			          this.comID+
 			          this.lic+
 			          this.days+
                                  this.prod_stocID+
                                  this.lic_stocID);
        
        // Hash match ?
        if (!h.equals(this.hash))
            throw new Exception("Invalid hash, CRentLicencePayload.java, 77");
        
       
   }
    
    public void commit(CBlockPayload block) throws Exception
    {
       // Superclass
       super.commit(block);
       
        // Company address
        String com_adr=UTILS.BASIC.getComAdr(this.comID);
       
       // Load licence data
       ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM tipuri_licente "
                                         + "WHERE tip='"+this.lic+"'");
       
       // Next
       rs.next();
       
       // Product
       String prod=rs.getString("prod");
       
       // Has stoc ?
       rs=UTILS.DB.executeQuery("SELECT * "
                                + "FROM stocuri "
                               + "WHERE adr='"+com_adr+"' "
                                 + "AND tip='"+prod+"'");
       
       // Insert prod
       if (!UTILS.DB.hasData(rs))
       UTILS.DB.executeUpdate("INSERT INTO stocuri "
                                    + "SET adr='"+com_adr+"', "
                                        + "tip='"+prod+"', "
                                        + "qty=0, "
                                        + "stocID='"+this.prod_stocID+"', "
                                        + "expires=0, "
                                        + "block='"+this.block+"'");
       
       // Insert licence
       UTILS.DB.executeUpdate("INSERT INTO stocuri "
                                    + "SET adr='"+com_adr+"', "
                                         + "tip='"+this.lic+"', "
                                         + "qty=1, "
                                         + "stocID='"+this.lic_stocID+"', "
                                         + "expires='"+(this.block+1440*days)+"', "
                                         + "block='"+this.block+"'");
       
       // Clear transations
       UTILS.ACC.clearTrans(this.hash, "ID_ALL", this.block);
    }
}
