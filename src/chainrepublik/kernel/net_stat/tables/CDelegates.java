package chainrepublik.kernel.net_stat.tables;

import java.sql.ResultSet;
import chainrepublik.kernel.json.JSONArray;
import chainrepublik.kernel.json.JSONObject;
import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CDelegates extends CTable
{
    public CDelegates()
    {
        // Constructor
        super("delegates");
    }
    
    // Create
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create table
            UTILS.DB.executeUpdate("CREATE TABLE delegates(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
				                         + "delegate VARCHAR(500) NOT NULL DEFAULT '', "
				                         + "power BIGINT NOT NULL DEFAULT 0, "
                                                         + "dif VARCHAR(100) NOT NULL DEFAULT '', "
                                                         + "block BIGINT NOT NULL DEFAULT 0)");
        
            // Indexes
            UTILS.DB.executeUpdate("CREATE UNIQUE INDEX del_delegate ON delegates(delegate)");
            UTILS.DB.executeUpdate("CREATE INDEX del_block ON delegates(block)");
            
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
