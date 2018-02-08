package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

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
            System.out.print("Creating table blocks_pool...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE bonuses (ID bigint(20) AUTO_INCREMENT PRIMARY KEY, "
                                                    + "bonus varchar(45) NOT NULL DEFAULT '', "
                                                    + "country varchar(5) NOT NULL DEFAULT '', "
                                                    + "title varchar(45) NOT NULL DEFAULT '', "
                                                    + "expl varchar(1000) NOT NULL DEFAULT '', "
                                                    + "amount float(9,4) NOT NULL DEFAULT '0.0000', "
                                                    + "cur varchar(10) NOT NULL DEFAULT '', "
                                                    + "block BIGINT NOT NULL DEFAULT 0)");
            
            // Indexes
	    UTILS.DB.executeUpdate("CREATE INDEX bonuses_bonus ON bonuses(bonus)");
            UTILS.DB.executeUpdate("CREATE INDEX bonuses_country ON bonuses(country)"); 
            UTILS.DB.executeUpdate("CREATE INDEX bonuses_cur ON bonuses(cur)"); 
            UTILS.DB.executeUpdate("CREATE INDEX bonuses_block ON bonuses(block)"); 
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Load checkpoint
       loadCheckpoint(chk_hash);
    }
}

