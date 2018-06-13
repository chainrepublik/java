
package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CRefStats extends CTable
{
    public CRefStats()
    {
        super("ref_stats");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE ref_stats(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                              +"userID BIGINT NOT NULL DEFAULT '0', "
                                                              +"year BIGINT NOT NULL DEFAULT 0, "
                                                              +"month BIGINT NOT NULL DEFAULT 0, "
                                                              +"day BIGINT NOT NULL DEFAULT 0, "
                                                              +"hits BIGINT NOT NULL DEFAULT 0, "
                                                              +"signups BIGINT NOT NULL DEFAULT 0, "
                                                              +"tstamp BIGINT NOT NULL DEFAULT 0)");
            
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX ref_stats_userID ON ref_stats(userID)");
            UTILS.DB.executeUpdate("CREATE INDEX ref_stats_year ON ref_stats(year)");
            UTILS.DB.executeUpdate("CREATE INDEX ref_stats_month ON ref_stats(month)");
            UTILS.DB.executeUpdate("CREATE INDEX ref_stats_day ON ref_stats(day)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
}