package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class COutReq extends CTable
{
    public COutReq()
    {
        // Constructor
        super("out_req");
    }   
    
    public void create() throws Exception
    {
        if (!this.tableExist("out_req"))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create table
            UTILS.DB.executeUpdate("CREATE TABLE out_req (ID BIGINT(20) AUTO_INCREMENT PRIMARY KEY, "
                                                       + "tip VARCHAR(50) NOT NULL DEFAULT '', "
                                                       + "dest VARCHAR(100) NOT NULL DEFAULT '', "
                                                       + "subject VARCHAR(500) NOT NULL DEFAULT '', "
                                                       + "message VARCHAR(2500) NOT NULL DEFAULT '', "
                                                       + "URL VARCHAR(1000) NOT NULL DEFAULT '',"
                                                       + "adr VARCHAR(500) NOT NULL DEFAULT '',"
                                                       + "tstamp BIGINT NOT NULL DEFAULT 0, "
                                                       + "status VARCHAR(20) DEFAULT 'NO')");
            
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX out_req_tip ON out_req(tip)");
            UTILS.DB.executeUpdate("CREATE INDEX out_req_adr ON out_req(adr)");
            UTILS.DB.executeUpdate("CREATE INDEX out_req_dest ON out_req(dest)");
            UTILS.DB.executeUpdate("CREATE INDEX out_req_status ON out_req(status)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void expired() throws Exception
    {
        UTILS.DB.executeUpdate("DELETE FROM out_req "
                                   + "WHERE tstamp<"+(UTILS.BASIC.tstamp()-864000));
    }
}
