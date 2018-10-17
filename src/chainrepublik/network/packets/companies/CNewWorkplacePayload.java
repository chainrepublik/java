package chainrepublik.network.packets.companies;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CNewWorkplacePayload extends CPayload
{
    // Company ID
    long comID;
    
    // Workplace ID
    long workID;
    
    // Days
    long days;
    
    // Serial
    private static final long serialVersionUID = 100L;
    
    public CNewWorkplacePayload(String adr, 
                                long comID, 
                                long days) throws Exception
    {
        // Superclass
	super(adr);
        
        // Company ID
        this.comID=comID;
    
        // Workplace ID
        this.workID=UTILS.BASIC.getID();
    
        // Days
        this.days=days;
        
        // Hash
 	hash=UTILS.BASIC.hash(this.getHash()+
 			      this.comID+
 			      this.workID+
 			      this.days);
          
    }
    
    public void check(CBlockPayload block) throws Exception
   {
   	// Super class
   	super.check(block);
        
        // Energy
        this.checkEnergy();
        
        // Company addrews
        String com_adr=UTILS.BASIC.getComAdr(this.comID);
        
        // Citizen address ?
        if (!UTILS.BASIC.isCitAdr(this.target_adr, this.block))
           throw new Exception("Only citizens can do this action - CWorkPayload.java, 68");
        
        // Company address
        if (!UTILS.BASIC.isComOwner(this.target_adr, this.comID))
           throw new Exception("Address is not company owner, CNewWorkplacePayload.java, 52");
        
        // Workplace ID
        if (UTILS.BASIC.isID(this.workID))
           throw new Exception("Invalid workplace ID, CNewWorkplacePayload.java, 56");
        
        // Days
        if (days<30)
            throw new Exception("Invalid days, CNewWorkplacePayload.java, 60");
        
        // Company owns a production licence ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM stocuri "
                                          + "WHERE adr='"+com_adr+"' "
                                            + "AND tip LIKE '%ID_LIC_PROD%'");
        
        // Has data ?
        if (!UTILS.DB.hasData(rs))
           throw new Exception("Company has no production licence active, CNewWorkplacePayload.java, 60");
        
        // Has funds ?
        if (UTILS.ACC.getBalance(com_adr, "CRC", block)<UTILS.CONST.wp_price*this.days)
            throw new Exception("Insuficient funds, CNewWorkplacePayload.java, 60");
        
         // Take money
        UTILS.ACC.newTransfer(com_adr, 
                              "default", 
                              UTILS.CONST.wp_price*this.days, 
                              "CRC", 
                              "You rented a workplace for "+days+" days", 
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
 			          this.workID+
 			          this.days);
        
        // Hash match ?
        if (!h.equals(this.hash))
            throw new Exception("Invalid hash, CNewWorkplacePayload.java, 70");
   }
    
  
    public void commit(CBlockPayload block) throws Exception
    {
       // Superclass
       super.commit(block);
       
        // Company address
        String com_adr=UTILS.BASIC.getComAdr(this.comID);
       
       // Company data
       ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM companies "
                                         + "WHERE comID='"+this.comID+"'");
       
       // Next
       rs.next();
       
       // Default licence
       rs=UTILS.DB.executeQuery("SELECT * "
                                + "FROM stocuri "
                               + "WHERE adr='"+com_adr+"' "
                                 + "AND tip LIKE '%ID_LIC_PROD%'");
       
       // Next
       rs.next();
       
       // Default product
       rs=UTILS.DB.executeQuery("SELECT * "
                                + "FROM tipuri_licente "
                               + "WHERE tip='"+rs.getString("tip")+"'");
       
       // Next
       rs.next();
       
       // Product
       String prod=rs.getString("prod");
       
       // Insert workplace
       UTILS.DB.executeUpdate("INSERT INTO workplaces "
                                    + "SET comID='"+this.comID+"', "
                                        + "workplaceID='"+this.workID+"', "
                                        + "expires='"+(this.block+this.days*1440)+"', "
                                        + "status='ID_SUSPENDED', "
                                        + "prod='"+prod+"', "
                                        + "block='"+this.block+"'");
       
       // Clear trans
       UTILS.ACC.clearTrans(this.hash, "ID_ALL", this.block);
    }
}
