package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CSync extends CTable
{
    public CSync()
    {
        super("sync");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE sync(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                       +"status VARCHAR(50) NOT NULL DEFAULT '', "
                                                       +"peer VARCHAR(20) NOT NULL DEFAULT '', "
                                                       +"type VARCHAR(50) NOT NULL DEFAULT '', "
                                                       +"tab VARCHAR(100) NOT NULL DEFAULT '', "
                                                       +"start BIGINT NOT NULL DEFAULT '0', "
                                                       +"end BIGINT NOT NULL DEFAULT '0', "
                                                       +"tstamp BIGINT NOT NULL DEFAULT '0')");
            
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX sync_staus ON sync(status)");
            UTILS.DB.executeUpdate("CREATE INDEX sync_peer ON sync(peer)");
            UTILS.DB.executeUpdate("CREATE INDEX sync_type ON sync(type)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
}