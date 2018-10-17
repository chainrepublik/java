package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CPeersPool extends CTable
{
    public CPeersPool()
    {
        super("peers_pool");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist("peers_pool"))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
           
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE peers_pool(ID BIGINT AUTO_INCREMENT PRIMARY KEY," 
                                                           +"peer VARCHAR(30),"
                                                           +"port BIGINT NOT NULL DEFAULT 0,"
                                                           +"con_att_no BIGINT NOT NULL DEFAULT 0,"
                                                           +"con_att_last BIGINT NOT NULL DEFAULT 0,"
                                                           +"accept_con VARCHAR(10) NOT NULL DEFAULT 'ID_NO',"
                                                           +"last_seen BIGINT NOT NULL DEFAULT 0,"
                                                           +"banned BIGINT NOT NULL DEFAULT 0)");
            
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX peers_pool_peer ON peers_pool(peer)");
            UTILS.DB.executeUpdate("CREATE INDEX peers_pool_port ON peers_pool(port)");
            UTILS.DB.executeUpdate("CREATE INDEX peers_pool_last_seen ON peers_pool(last_seen)");
            
            // Populate
           if (!this.reorg)
              this.populate();
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void populate() throws Exception
    {
         // Insert primary nodes
         UTILS.DB.executeUpdate("INSERT INTO peers_pool SET peer='209.250.249.184', port='10000'");
         UTILS.DB.executeUpdate("INSERT INTO peers_pool SET peer='104.207.130.140', port='10000'");
         UTILS.DB.executeUpdate("INSERT INTO peers_pool SET peer='199.247.10.91', port='10000'");
    }
}