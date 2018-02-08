package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CCronRuns extends CTable
{
    public CCronRuns() 
    {
        // Constructor
        super("cron_runs");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist("cron_runs"))
        {
            // Status
            System.out.print("Creating table cron_runs...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE cron_runs(ID BIGINT(20) AUTO_INCREMENT PRIMARY KEY, "
                                                         + "cronID BIGINT(20) NOT NULL DEFAULT 0, "
                                                         + "tstamp BIGINT(20) NOT NULL DEFAULT 0, "
                                                         + "start BIGINT(20) NOT NULL DEFAULT 0, "
                                                         + "status VARCHAR(45) NOT NULL DEFAULT 0, "
                                                         + "end BIGINT(20) NOT NULL DEFAULT 0, "
                                                         + "duration BIGINT(20) NOT NULL DEFAULT 0)");
	    
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX cron_runs_cronID ON cron_runs(cronID)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void expired(long block) throws Exception
    {
        UTILS.DB.executeUpdate("DELETE FROM cron_runs "
                                   + "WHERE end<"+(UTILS.BASIC.tstamp()-86400));
    }
}
