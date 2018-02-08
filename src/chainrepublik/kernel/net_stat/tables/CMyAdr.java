package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CMyAdr extends CTable
{
    public CMyAdr()
    {
        super("my_adr");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist("my_adr"))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE my_adr(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
				    		     + "userID BIGINT NOT NULL DEFAULT 0, "
				    		     + "adr VARCHAR(250) NOT NULL DEFAULT '',"
				    		     + "description VARCHAR(100) NOT NULL DEFAULT 'No description provided')");
	    
            // Indexes
	    UTILS.DB.executeUpdate("CREATE INDEX my_adr_adr ON my_adr(adr)");
	    UTILS.DB.executeUpdate("CREATE INDEX my_adr_userID ON my_adr(userID)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
}
