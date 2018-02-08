package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CIPN  extends CTable
{
    public CIPN()
    {
        super("ipn");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE ipn(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
	 	 				  + "adr VARCHAR(250) NOT NULL DEFAULT '', "
	 	 				  + "email VARCHAR(100), "
	 	 				  + "web_pass VARCHAR(100), "
	 	 				  + "web_link VARCHAR(1000), "
	 	 				  + "tstamp BIGINT NOT NULL DEFAULT 0)");
	    
            // Indexes
	    UTILS.DB.executeUpdate("CREATE INDEX ipn_adr ON ipn(adr)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
}
