package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CSysStats extends CTable
{
   public CSysStats()
    {
        super("sys_stats");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create 
            UTILS.DB.executeUpdate("CREATE TABLE sys_stats (ID BIGINT(20) AUTO_INCREMENT PRIMARY KEY, "
                                                         + "users BIGINT(11) NOT NULL DEFAULT 0, "
                                                         + "signups_24h BIGINT(11) NOT NULL DEFAULT 0, "
                                                         + "signups_7d BIGINT(11) NOT NULL DEFAULT 0, "
                                                         + "signups_30d BIGINT(11) NOT NULL DEFAULT 0, "
                                                         + "actives_24 BIGINT(11) NOT NULL DEFAULT 0, "
                                                         + "actives_7d BIGINT(11) NOT NULL DEFAULT 0, "
                                                         + "actives_30d BIGINT(11) NOT NULL DEFAULT 0, "
                                                         + "new_adr_24h BIGINT(11) DEFAULT NULL DEFAULT 0, "
                                                         + "new_adr_7d BIGINT(11) DEFAULT NULL DEFAULT 0, "
                                                         + "new_adr_30d BIGINT(11) DEFAULT NULL DEFAULT 0, "
                                                         + "total_energy BIGINT(11) NOT NULL DEFAULT 0, "
                                                         + "avg_energy BIGINT(11) NOT NULL DEFAULT 0, "
                                                         + "total_aff BIGINT(11) NOT NULL DEFAULT 0, "
                                                         + "avg_aff BIGINT(11) NOT NULL DEFAULT 0, "
                                                         + "total_war_points BIGINT(11) NOT NULL DEFAULT 0, "
                                                         + "avg_war_points BIGINT(11) NOT NULL DEFAULT 0, "
                                                         + "total_pol_inf BIGINT(11) NOT NULL DEFAULT 0, "
                                                         + "avg_pol_inf BIGINT(11) NOT NULL DEFAULT 0, "
                                                         + "total_pol_end BIGINT(11) NOT NULL DEFAULT 0, "
                                                         + "avg_pol_end BIGINT(11) NOT NULL DEFAULT 0)");
           
            // Confirm
            System.out.println("Done.");
        }
    }    
    
    public void populate() throws Exception
    {
        UTILS.DB.executeUpdate("INSERT INTO sys_stats SET users=0");
    }
}
