package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;
import java.sql.ResultSet;

public class CTaxes extends CTable
{
    public CTaxes()
    {
        super("taxes");
    }
    
    // Create 
    public void create() throws Exception
    {
        if (!this.tableExist("taxes"))
        {
            // Status
            System.out.print("Creating table "+this.name+" (this could take a while) ...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE taxes (ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                    + "cou VARCHAR(5) NOT NULL DEFAULT '', "
                                                    + "tax VARCHAR(50) NOT NULL DEFAULT '', "
                                                    + "value FLOAT(9, 2) NOT NULL DEFAULT 0, "
                                                    + "prod VARCHAR(100) NOT NULL DEFAULT '', "
                                                    + "op VARCHAR(20) NOT NULL DEFAULT '', "
                                                    + "block BIGINT NOT NULL DEFAULT 0)");
	
            // Indexes
	    UTILS.DB.executeUpdate("CREATE INDEX taxes_cou ON taxes(cou)");
            UTILS.DB.executeUpdate("CREATE INDEX taxes_tax ON taxes(tax)"); 
            UTILS.DB.executeUpdate("CREATE INDEX taxes_prod ON taxes(prod)"); 
            UTILS.DB.executeUpdate("CREATE INDEX taxes_op ON taxes(op)"); 
            UTILS.DB.executeUpdate("CREATE INDEX taxes_block ON taxes(block)"); 
            
            // Populate
            this.populate();
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Load checkpoint
       loadCheckpoint(chk_hash);
    }
    
    public void populate() throws Exception
    {
        // Load countries
        ResultSet rs_cou=UTILS.DB.executeQuery("SELECT * FROM countries");
        
        // Insert salary and rent tax
        while (rs_cou.next())
        {
           // Salary tax
           UTILS.DB.executeUpdate("INSERT INTO taxes "
                                        + "SET cou='"+rs_cou.getString("code")+"', "
                                            + "tax='ID_SALARY_TAX', "
                                            + "value=10, "
                                            + "block=0");
           
           // Rent tax
           UTILS.DB.executeUpdate("INSERT INTO taxes "
                                        + "SET cou='"+rs_cou.getString("code")+"', "
                                            + "tax='ID_RENT_TAX', "
                                            + "value=10, "
                                            + "block=0");
           
           // Rewards tax
           UTILS.DB.executeUpdate("INSERT INTO taxes "
                                        + "SET cou='"+rs_cou.getString("code")+"', "
                                            + "tax='ID_REWARDS_TAX', "
                                            + "value=10, "
                                            + "block=0");
           
           // Dividends tax
           UTILS.DB.executeUpdate("INSERT INTO taxes "
                                        + "SET cou='"+rs_cou.getString("code")+"', "
                                            + "tax='ID_DIVIDENDS_TAX', "
                                            + "value=10, "
                                            + "block=0");
        }
        
        // Reload countries
        rs_cou=UTILS.DB.executeQuery("SELECT * FROM countries");
        
        // Insert taxes
        while (rs_cou.next())
        {
            // Load products
            ResultSet rs_prod=UTILS.DB.executeQuery("SELECT * FROM tipuri_produse");
        
            // Sale tax
            while (rs_prod.next())
            {
                UTILS.DB.executeUpdate("INSERT INTO taxes "
                                             + "SET cou='"+rs_cou.getString("code")+"', "
                                                 + "tax='ID_SALE_TAX', "
                                                 + "prod='"+rs_prod.getString("prod")+"', "
                                                 + "value=5, "
                                                 + "block=0");
            }
        }
    }
    
}

