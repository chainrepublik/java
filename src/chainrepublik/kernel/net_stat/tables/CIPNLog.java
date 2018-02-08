package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CIPNLog extends CTable
{
    public CIPNLog()
    {
        super("ipn_log");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE ipn_log (ID BIGINT AUTO_INCREMENT PRIMARY KEY,"
                                                        +"ipnID BIGINT NOT NULL DEFAULT 0,"
                                                        +"adr VARCHAR(250) NOT NULL DEFAULT '',"
                                                        +"tip VARCHAR(10) NOT NULL DEFAULT 'ID_WEB',"
                                                        +"data VARCHAR(1000) NOT NULL DEFAULT '',"
                                                        +"amount DOUBLE NOT NULL DEFAULT 0,"
                                                        +"cur VARCHAR(50) NOT NULL DEFAULT 'CRC',"
                                                        +"txid VARCHAR(100) NOT NULL DEFAULT '',"
                                                        +"status VARCHAR(10) NOT NULL DEFAULT 'ID_OK',"
                                                        +"tstamp BIGINT NOT NULL DEFAULT 0)");
	    
            // Indexes	 
	    UTILS.DB.executeUpdate("CREATE INDEX ipn_id ON ipn_log(ipnID)");
	    UTILS.DB.executeUpdate("CREATE INDEX ipn_iadr ON ipn_log(adr)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
}
