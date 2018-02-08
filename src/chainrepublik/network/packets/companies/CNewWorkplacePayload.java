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
           
        // Sign
        this.sign();
    }
    
    public void check(CBlockPayload block) throws Exception
   {
   	// Super class
   	super.check(block);
        
        // Company address
        if (!UTILS.BASIC.isCompanyAdr(comID, this.target_adr))
           throw new Exception("Invalid company address, CNewWorkplacePayload.java, 52");
        
        // Workplace ID
        if (UTILS.BASIC.isID(this.workID))
           throw new Exception("Invalid workplace ID, CNewWorkplacePayload.java, 56");
        
        // Days
        if (days<30)
            throw new Exception("Invalid days, CNewWorkplacePayload.java, 60");
        
        // Company owns a production licence ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM stocuri "
                                          + "WHERE adr='"+this.target_adr+"' "
                                            + "AND tip LIKE '%ID_LIC_PROD%'");
        
        // Has data ?
        if (!UTILS.DB.hasData(rs))
           throw new Exception("Company has no production licence active, CNewWorkplacePayload.java, 60");
        
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
       
       // Company data
       ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM companies "
                                         + "WHERE comID='"+this.comID+"'");
       
       // Next
       rs.next();
       
       // Default licence
       rs=UTILS.DB.executeQuery("SELECT * "
                                + "FROM stocuri "
                               + "WHERE adr='"+this.target_adr+"' "
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
    }
}
