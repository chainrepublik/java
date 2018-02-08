package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CVotesStats extends CTable
{
    public CVotesStats()
    {
        super("votes_stats");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist("votes_stats"))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE votes_stats (ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                          + "target_type VARCHAR(50) NOT NULL DEFAULT '', "
                                                          + "targetID BIGINT NOT NULL DEFAULT '0', "
                                                          + "upvotes_24 BIGINT NOT NULL DEFAULT '0', "
                                                          + "upvotes_power_24 DOUBLE(9,2) NOT NULL DEFAULT '0.00', "
                                                          + "downvotes_24 BIGINT NOT NULL DEFAULT '0', "
                                                          + "downvotes_power_24 DOUBLE(9,2) NOT NULL DEFAULT '0.00', "
                                                          + "comments BIGINT(20) NOT NULL DEFAULT 0, "
                                                          + "tstamp BIGINT NOT NULL DEFAULT '0', "
                                                          + "pay DOUBLE(9,4) NOT NULL DEFAULT 0)");
           
           // Indexes
           UTILS.DB.executeUpdate("CREATE INDEX votes_stats_target_type ON votes_stats(target_type)");
           UTILS.DB.executeUpdate("CREATE INDEX votes_stats_targetID ON votes_stats(targetID)");
           
           // Confirm
           System.out.println("Done.");
        }
    }
}

