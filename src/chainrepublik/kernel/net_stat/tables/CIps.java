package chainrepublik.kernel.net_stat.tables;
import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CIps extends CTable
{
    public CIps()
    {
        super("ips");
    }
    
    // Create
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table ips...");
           
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE ips(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                     + "IP VARCHAR(20) NOT NULL DEFAULT '', "
                                                     + "status VARCHAR(250) NOT NULL DEFAULT '')");
				
            // Indexes
	    UTILS.DB.executeUpdate("CREATE INDEX ips_IP ON ips(IP)");
            UTILS.DB.executeUpdate("CREATE INDEX ips_status ON ips(status)"); 
            
            // Confirm
            System.out.println("Done.");
        }
    }
}
