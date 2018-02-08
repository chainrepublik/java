package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CTransPool extends CTable
{
    public CTransPool()
    {
        super("trans_pool");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist("trans_pool"))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE trans_pool (ID BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                                                         + "src VARCHAR(250) NOT NULL DEFAULT '', "
                                                         + "amount DOUBLE(10,4) NOT NULL DEFAULT '0.0000', "
                                                         + "src_balance DOUBLE(10,4) NOT NULL DEFAULT '0.0000', "
                                                         + "block BIGINT(20) NOT NULL DEFAULT '0', "
                                                         + "cur VARCHAR(50) DEFAULT NULL, "
                                                         + "hash VARCHAR(100) DEFAULT NULL)");
            
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX trans_pool_src ON trans(src)");
            UTILS.DB.executeUpdate("CREATE INDEX trans_pool_block ON trans(block)");
            UTILS.DB.executeUpdate("CREATE INDEX trans_pool_cur ON trans(cur)");
            UTILS.DB.executeUpdate("CREATE INDEX trans_pool_hash ON trans(hash)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
}
