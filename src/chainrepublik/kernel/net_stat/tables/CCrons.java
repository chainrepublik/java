package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CCrons extends CTable
{
    public CCrons() 
    {
        // Constructor
        super("crons");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE crons(ID BIGINT(20) AUTO_INCREMENT PRIMARY KEY, "
                                                     + "cron VARCHAR(45) DEFAULT '', "
                                                     + "last_run BIGINT(20) NOT NULL DEFAULT 0, "
                                                     + "next_run BIGINT(20) NOT NULL DEFAULT 0, "
                                                     + "inter BIGINT(20) NOT NULL DEFAULT 0)");
	    
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX cron_runs_cron ON crons(cron)");
            
            // Populate
            if (!this.reorg)
            this.populate();
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    
    public void populate() throws Exception
    {
        UTILS.DB.executeUpdate("INSERT INTO crons SET cron='ID_CRON_1M', inter='60', last_run='0', next_run='0'");
        UTILS.DB.executeUpdate("INSERT INTO crons SET cron='ID_CRON_5M', inter='300', last_run='0', next_run='0'");
        UTILS.DB.executeUpdate("INSERT INTO crons SET cron='ID_CRON_10M', inter='600', last_run='0', next_run='0'");
        UTILS.DB.executeUpdate("INSERT INTO crons SET cron='ID_CRON_1H', inter='3600', last_run='0', next_run='0'");
        UTILS.DB.executeUpdate("INSERT INTO crons SET cron='ID_CRON_1D', inter='86400', last_run='0', next_run='0'");
    }
}
