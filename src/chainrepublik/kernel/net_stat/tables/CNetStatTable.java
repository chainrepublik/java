package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CNetStatTable extends CTable
{
    public CNetStatTable()
    {
        super("net_stat");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist("net_stat"))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE net_stat(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
				    		       + "last_block BIGINT NOT NULL DEFAULT 0, "
				    		       + "last_block_hash VARCHAR(250) NOT NULL DEFAULT '',"
                                                       + "block_confirm_min_balance DOUBLE(20,4) DEFAULT 1,"
                                                       + "net_dif VARCHAR(100) NOT NULL DEFAULT '0',"
                                                       + "delegate VARCHAR(500) NOT NULL DEFAULT '',"
                                                       + "sql_log_status VARCHAR(100) NOT NULL DEFAULT '',"
                                                       + "sync_target BIGINT NOT NULL DEFAULT 0, "
                                                       + "sync_start BIGINT NOT NULL DEFAULT 0, "
                                                       + "last_tstamp BIGINT NOT NULL DEFAULT 0)");
            
            // Populate
            if (!this.reorg)
               this.populate();
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void populate() throws Exception
    {
        UTILS.DB.executeUpdate("INSERT INTO net_stat "
                                         + "SET last_block='0', "
                                             + "last_block_hash='0000000000000000000000000000000000000000000000000000000000000000', "
                                             + "net_dif='00000000fc8eb4c424a4ab9659f606c254071192c1abd28ca94ee63f88323bbf'");
	
        
    }
}