package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CBlocksPool extends CTable
{
    public CBlocksPool() 
    {
        // Constructor
        super("blocks_pool");
    }  
    
    public void create() throws Exception
    {
        if (!this.tableExist("blocks_pool"))
        {
            // Status
            System.out.print("Creating table blocks_pool...");
            
            // Create table
            UTILS.DB.executeUpdate("CREATE TABLE blocks_pool(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
			 	 		          + "hash VARCHAR(100), "
			 	 	 	          + "block BIGINT NOT NULL DEFAULT 0, "
			 	 	 	          + "tstamp BIGINT NOT NULL DEFAULT 0)");
	    
            // Indexes
	    UTILS.DB.executeUpdate("CREATE UNIQUE INDEX blocks_pool_hash ON blocks_pool(hash)");
	    UTILS.DB.executeUpdate("CREATE INDEX blocks_pool_block ON blocks_pool(block)");
            
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
