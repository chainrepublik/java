package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CStatusLog extends CTable
{
    public CStatusLog()
    {
        super("status_log");
    }
    
    public void create() throws Exception
    {
       if (!this.tableExist(this.name))
       {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE status_log (ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                           + "total_mem BIGINT NOT NULL DEFAULT 0, "
                                                           + "free_mem BIGINT NOT NULL DEFAULT 0, "
                                                           + "threads BIGINT NOT NULL DEFAULT 0, "
                                                           + "tstamp BIGINT NOT NULL DEFAULT 0)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
}
