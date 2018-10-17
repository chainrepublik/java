package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CWth extends CTable
{
    public CWth()
    {
        super("wth");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist("wth"))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE wth(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                  + "user VARCHAR(250) NOT NULL DEFAULT '', "
                                                  + "amount BIGINT NOT NULL DEFAULT 0, "
                                                  + "method VARCHAR(50) NOT NULL DEFAULT '', "
                                                  + "tstamp BIGINT NOT NULL DEFAULT 0)");
				
            // Indexes
	    UTILS.DB.executeUpdate("CREATE INDEX wth_user ON wth(user)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
  
}
