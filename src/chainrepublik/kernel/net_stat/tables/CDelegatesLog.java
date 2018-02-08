package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CDelegatesLog extends CTable
{
   public CDelegatesLog()
   {
       super("delegates_log");
   }
   
   public void create() throws Exception
   {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE delegates_log (ID BIGINT(20) AUTO_INCREMENT PRIMARY KEY, "
                                                            + "delegate VARCHAR(500) NOT NULL DEFAULT '', "
                                                            + "power BIGINT(20) NOT NULL DEFAULT '0', "
                                                            + "block BIGINT(20) NOT NULL DEFAULT '0')");
            
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX delegates_delegate ON delegates(delegate)");
            UTILS.DB.executeUpdate("CREATE INDEX delegates_block ON delegates(block)");
            
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
