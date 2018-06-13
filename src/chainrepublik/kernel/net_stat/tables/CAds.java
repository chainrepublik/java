package chainrepublik.kernel.net_stat.tables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import chainrepublik.kernel.json.JSONArray;
import chainrepublik.kernel.json.JSONObject;
import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CAds extends CTable
{
    public CAds()
    {
        super("ads");
    }
    
    // Create
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table ads...");
           
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE ads(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                     + "country VARCHAR(2) NOT NULL DEFAULT '', "
                                                     + "adr VARCHAR(250) NOT NULL DEFAULT '', "
				    		     + "title VARCHAR(250) NOT NULL DEFAULT '', "
				    		     + "message VARCHAR(1000) NOT NULL DEFAULT '',"
                                                     + "link VARCHAR(500) NOT NULL DEFAULT '',"
                                                     + "mkt_bid DOUBLE(9,4) NOT NULL DEFAULT 0,"
                                                     + "expires BIGINT NOT NULL DEFAULT 0,"
                                                     + "block BIGINT NOT NULL DEFAULT 0)");
				
            // Indexes
	    UTILS.DB.executeUpdate("CREATE INDEX ads_adr ON ads(adr)");
            UTILS.DB.executeUpdate("CREATE INDEX ads_block ON ads(block)"); 
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void expired(long block) throws Exception
    {
       UTILS.DB.executeUpdate("DELETE FROM ads "
                                  + "WHERE expires<="+block);
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Load checkpoint
       UTILS.DB.executeUpdate("DELETE FROM ads "
                                  + "WHERE block>"+block);
    }
}
