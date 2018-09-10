package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;
import java.sql.ResultSet;

public class CEvents extends CTable
{
    public CEvents()
    {
        super("events");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE events (ID BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                                                       + "adr VARCHAR(250) NOT NULL DEFAULT '', "
                                                       + "evt VARCHAR(1000) NOT NULL DEFAULT '', "
                                                       + "viewed BIGINT(20) NOT NULL DEFAULT 0, "
                                                       + "block BIGINT(20) NOT NULL DEFAULT 0)");
           
            // Indexes
	    UTILS.DB.executeUpdate("CREATE INDEX events_adr ON events(adr)");
            UTILS.DB.executeUpdate("CREATE INDEX events_viewed ON events(viewed)");
            UTILS.DB.executeUpdate("CREATE INDEX events_block ON events(block)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void expired(long block) throws Exception
    {
         UTILS.DB.executeUpdate("DELETE FROM events "
                                    + "WHERE block<"+(block-14400));
    }
     
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Load checkpoint
       loadCheckpoint(chk_hash);
    }
}
