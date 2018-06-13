package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CDividends extends CTable
{
   public CDividends()
   {
       super("dividends");
   }
   
   public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create 
            UTILS.DB.executeUpdate("CREATE TABLE dividends (ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                         + "comID BIGINT(20) NOT NULL, "
                                                         + "amount DOUBLE(10, 2) DEFAULT NULL, "
                                                         + "block BIGINT(20) DEFAULT NULL)");
            
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX dividends_comID ON dividends(comID)");
            UTILS.DB.executeUpdate("CREATE INDEX dividends_block ON dividends(block)");
            
            // Confirm
            System.out.println("Done.");
        }
    }   
   
   public void expired(long block) throws Exception
   {
       UTILS.DB.executeUpdate("DELETE FROM dividends "
                                  + "WHERE block<"+(block-100000));
   }
   
   public void reorganize(long block, String chk_hash) throws Exception
    {
       // Meesage
       System.out.println("Reorganizing delegates_log...");
        
       // Delete
       UTILS.DB.executeUpdate("DELETE FROM dividends "
                                  + "WHERE block>"+block);
       
       // Meesage
        System.out.print("Done");
    }
}
