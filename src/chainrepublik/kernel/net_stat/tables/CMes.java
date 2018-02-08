package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CMes extends CTable
{
    public CMes()
    {
        super("mes");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist("mes"))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE mes(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                  + "from_adr VARCHAR(250) NOT NULL DEFAULT '', "
                                                  + "to_adr VARCHAR(250) NOT NULL DEFAULT '', "
                                                  + "subject VARCHAR(250) NOT NULL DEFAULT '', "
                                                  + "mes VARCHAR(5000) NOT NULL DEFAULT '', "
                                                  + "readed VARCHAR(20) NOT NULL DEFAULT '', "
                                                  + "tstamp BIGINT NOT NULL DEFAULT 0)");
				
            // Indexes
	    UTILS.DB.executeUpdate("CREATE INDEX mes_from_adr ON mes(from_adr)");
            UTILS.DB.executeUpdate("CREATE INDEX mes_to_adr ON mes(to_adr)");
            UTILS.DB.executeUpdate("CREATE INDEX mes_readed ON mes(readed)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
  
}
