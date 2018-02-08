package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CConLog extends CTable
{
    public CConLog() 
    {
        // Constructor
        super("con_log");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist("con_log"))
        {
            // Status
            System.out.print("Creating table con_log...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE con_log(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
			 	 		       + "IP VARCHAR(30) NOT NULL DEFAULT '', "
                                                       + "port BIGINT NOT NULL DEFAULT 0, "
			 	 	 	       + "tstamp BIGINT NOT NULL DEFAULT 0)");
	    
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX con_log_ip ON con_log(IP)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void expired(long block) throws Exception
    {
        UTILS.DB.executeUpdate("DELETE FROM con_log "
                                   + "WHERE tstamp<"+(UTILS.BASIC.tstamp()-864000));
    }
}
