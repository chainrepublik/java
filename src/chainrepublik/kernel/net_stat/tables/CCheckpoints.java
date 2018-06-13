package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CCheckpoints extends CTable
{
    public CCheckpoints() 
    {
        // Constructor
        super("checkpoints");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist("checkpoints"))
        {
            // Status
            System.out.print("Creating table checkpoints...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE checkpoints(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                              +"block BIGINT(20) NOT NULL DEFAULT 0, "
                                                              +"hash VARCHAR(100) NOT NULL DEFAULT '')");
             
            // Indexes
            UTILS.DB.executeUpdate("CREATE UNIQUE INDEX checkpoints_block ON checkpoints(block)");
            UTILS.DB.executeUpdate("CREATE UNIQUE INDEX checkpoints_hash ON checkpoints(hash)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
        // Meesage
        System.out.println("Reorganizing checkpoints...");
        
        // Remove
        UTILS.DB.executeUpdate("DELETE FROM checkpoints WHERE block>"+block);
        
        // Meesage
        System.out.println("Done");
    }
}
