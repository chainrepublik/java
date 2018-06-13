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
            UTILS.DB.executeUpdate("CREATE INDEX delegates_log_delegate ON delegates_log(delegate)");
            UTILS.DB.executeUpdate("CREATE INDEX delegates_log_block ON delegates_log(block)");
            
            // Confirm
            System.out.println("Done.");
        }
   }
   
   public void expired(long block) throws Exception
    {
       UTILS.DB.executeUpdate("DELETE FROM delegates_log "
                                  + "WHERE block<"+(block-1440));
    }
   
   public void reorganize(long block, String chk_hash) throws Exception
   {
       // Meesage
       System.out.println("Reorganizing delegates_log...");
        
       // Load checkpoint
       UTILS.DB.executeUpdate("DELETE FROM delegates_log "
                                  + "WHERE block>"+block);
       
       // Meesage
        System.out.print("Done");
   }
}
