package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;
import java.sql.ResultSet;

public class CBonuses extends CTable
{
    public CBonuses()
    {
        super("bonuses");
    }
    
    // Create 
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table bonuses (this could take a while)...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE bonuses (ID bigint(20) AUTO_INCREMENT PRIMARY KEY, "
                                                    + "bonus varchar(45) NOT NULL DEFAULT '', "
                                                    + "cou varchar(5) NOT NULL DEFAULT '', "
                                                    + "title varchar(45) NOT NULL DEFAULT '', "
                                                    + "expl varchar(1000) NOT NULL DEFAULT '', "
                                                    + "amount float(9,4) NOT NULL DEFAULT '0.0000', "
                                                    + "prod varchar(100) NOT NULL DEFAULT '', "
                                                    + "block BIGINT NOT NULL DEFAULT 0)");
            
            // Indexes
	    UTILS.DB.executeUpdate("CREATE INDEX bonuses_bonus ON bonuses(bonus)");
            UTILS.DB.executeUpdate("CREATE INDEX bonuses_country ON bonuses(cou)"); 
            UTILS.DB.executeUpdate("CREATE INDEX bonuses_prod ON bonuses(prod)"); 
            UTILS.DB.executeUpdate("CREATE INDEX bonuses_block ON bonuses(block)"); 
            
            // Populate
            this.populate();
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void populate() throws Exception
    {
        // Load countries
        ResultSet rs_cou=UTILS.DB.executeQuery("SELECT * FROM countries");
        
        // Insert taxes
        while (rs_cou.next())
        {
            // Load products
            ResultSet rs_prod=UTILS.DB.executeQuery("SELECT * FROM tipuri_produse");
        
            // Sale tax
            while (rs_prod.next())
            {
                UTILS.DB.executeUpdate("INSERT INTO bonuses "
                                             + "SET cou='"+rs_cou.getString("code")+"', "
                                                 + "bonus='ID_BUY_BONUS', "
                                                 + "prod='"+rs_prod.getString("prod")+"', "
                                                 + "amount=0, "
                                                 + "block=0");
            }
        }
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Load checkpoint
       loadCheckpoint(chk_hash);
    }
}

