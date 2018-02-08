package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CErrorLog extends CTable
{
    public CErrorLog()
    {
        super("err_log");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
         {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE err_log(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
			 	 		       + "type VARCHAR(250) NOT NULL DEFAULT '', "
			 	 	 	       + "mes VARCHAR(1000) NOT NULL DEFAULT '', "
			 	 	 	       + "tstamp BIGINT NOT NULL DEFAULT 0)");
	    
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void expired() throws Exception
    {
        UTILS.DB.executeQuery("DELETE FROM err_log "
                                  + "WHERE tstamp<"+(UTILS.BASIC.tstamp()-864000));
    }
}
