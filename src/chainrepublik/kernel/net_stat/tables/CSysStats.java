package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;
import java.sql.ResultSet;

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
                                                          + "cou VARCHAR(2) NOT NULL DEFAULT '', "
                                                          + "users BIGINT(11) NOT NULL DEFAULT 0, "
                                                          + "signups_24h BIGINT(11) NOT NULL DEFAULT 0, "
                                                          + "signups_7d BIGINT(11) NOT NULL DEFAULT 0, "
                                                          + "signups_30d BIGINT(11) NOT NULL DEFAULT 0, "
                                                          + "companies BIGINT(11) NOT NULL DEFAULT 0, "
                                                          + "workplaces BIGINT(11) NOT NULL DEFAULT 0, "
                                                          + "total_energy BIGINT(11) NOT NULL DEFAULT 0, "
                                                          + "avg_energy BIGINT(11) DEFAULT NULL DEFAULT 0, "
                                                          + "total_war_points BIGINT(11) DEFAULT NULL DEFAULT 0, "
                                                          + "avg_war_points BIGINT(11) DEFAULT NULL DEFAULT 0, "
                                                          + "total_pol_inf BIGINT(11) NOT NULL DEFAULT 0, "
                                                          + "avg_pol_inf BIGINT(11) NOT NULL DEFAULT 0)");
            
            // Populate
            this.populate();
           
            // Confirm
            System.out.println("Done.");
        }
    }    
    
    public void populate() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * FROM countries");
       
        while (rs.next())
           UTILS.DB.executeUpdate("INSERT INTO sys_stats SET cou='"+rs.getString("code")+"'");
    }
}
