package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CPeers extends CTable
{
    public CPeers()
    {
        super("peers");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist("peers"))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
           
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE peers(ID BIGINT AUTO_INCREMENT PRIMARY KEY," 
                                                      +"peer VARCHAR(30),"
                                                      +"port BIGINT NOT NULL DEFAULT 0,"
                                                      +"in_traffic BIGINT NOT NULL DEFAULT 0,"
                                                      +"out_traffic BIGINT NOT NULL DEFAULT 0,"
                                                      +"last_seen BIGINT NOT NULL DEFAULT 0,"
                                                      +"ver VARCHAR(10) NOT NULL DEFAULT '',"
                                                      +"tstamp BIGINT NOT NULL DEFAULT 0)");
            
             // Indexes
	     UTILS.DB.executeUpdate("CREATE INDEX peers_peer ON peers(peer)");
             UTILS.DB.executeUpdate("CREATE INDEX peers_port ON peers(port)");
             UTILS.DB.executeUpdate("CREATE INDEX peers_tstamp ON peers(tstamp)");
             
             // Confirm
            System.out.println("Done.");
        }
    }
}