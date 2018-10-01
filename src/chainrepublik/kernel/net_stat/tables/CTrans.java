package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CTrans extends CTable
{
    public CTrans()
    {
        super("trans");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist("trans"))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE trans (ID BIGINT(20) AUTO_INCREMENT PRIMARY KEY, "
                                                     + "src VARCHAR(250) NOT NULL DEFAULT '', "
                                                     + "amount DOUBLE(20,8) NOT NULL DEFAULT '0.00000000', "
                                                     + "invested DOUBLE(20,8) NOT NULL DEFAULT '0.00000000', "
                                                     + "cur VARCHAR(50) NOT NULL DEFAULT '', "
                                                     + "escrower VARCHAR(250) NOT NULL DEFAULT '', "
                                                     + "hash VARCHAR(100) NOT NULL DEFAULT '', "
                                                     + "block BIGINT(20) NOT NULL DEFAULT '0', "
                                                     + "block_hash VARCHAR(100) NOT NULL DEFAULT '', "
                                                     + "status VARCHAR(20) NOT NULL DEFAULT '', "
                                                     + "mes VARCHAR(1000) NOT NULL DEFAULT '', "
                                                     + "tstamp BIGINT(20) NOT NULL DEFAULT '0', "
                                                     + "expl VARCHAR(1000) DEFAULT NULL)");
            
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX trans_src ON trans(src)");
            UTILS.DB.executeUpdate("CREATE INDEX trans_cur ON trans(cur)");
            UTILS.DB.executeUpdate("CREATE INDEX trans_escrower ON trans(escrower)");
            UTILS.DB.executeUpdate("CREATE INDEX trans_hash ON trans(hash)");
            UTILS.DB.executeUpdate("CREATE INDEX trans_block ON trans(block)");
            UTILS.DB.executeUpdate("CREATE INDEX trans_block_hash ON trans(block_hash)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void expired(long block) throws Exception
    {
        UTILS.DB.executeUpdate("DELETE FROM trans "
                                   + "WHERE block<"+(block-144400));
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
        // Meesage
       System.out.println("Reorganizing trans...");
        
       // Delete
       UTILS.DB.executeUpdate("DELETE FROM trans "
                                  + "WHERE block>"+block);
       
       // Meesage
       System.out.print("Done");
    }
}