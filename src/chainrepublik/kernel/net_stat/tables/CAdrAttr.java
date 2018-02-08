package chainrepublik.kernel.net_stat.tables;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.commons.io.FileUtils;
import chainrepublik.kernel.json.JSONArray;
import chainrepublik.kernel.json.JSONObject;
import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CAdrAttr extends CTable
{
    public CAdrAttr()
    {
        // Constructor
        super("adr_attr");
    }
    
    // Create
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table adr_attr...");
           
            // Create table
            UTILS.DB.executeUpdate("CREATE TABLE adr_attr(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
				                  + "adr VARCHAR(500) NOT NULL DEFAULT '', "
				                  + "attr VARCHAR(50) NOT NULL DEFAULT '', "
		                                  + "s1 VARCHAR(500) NOT NULL DEFAULT '', "
                                                  + "s2 VARCHAR(500) NOT NULL DEFAULT '', "
                                                  + "s3 VARCHAR(500) NOT NULL DEFAULT '', "
                                                  + "l1 BIGINT NOT NULL DEFAULT 0, "
                                                  + "l2 BIGINT NOT NULL DEFAULT 0, "
                                                  + "l3 BIGINT NOT NULL DEFAULT 0, "
                                                  + "d1 FLOAT (20,8) NOT NULL DEFAULT 0, "
                                                  + "d2 FLOAT (20,8) NOT NULL DEFAULT 0, "
                                                  + "d3 FLOAT (20,8) NOT NULL DEFAULT 0, "
                                                  + "expires BIGINT NOT NULL DEFAULT 0)");
	
            // Indexes 
            UTILS.DB.executeUpdate("CREATE UNIQUE INDEX adr_attr_adr ON adr_attr(adr)");
            UTILS.DB.executeUpdate("CREATE UNIQUE INDEX adr_attr_attr ON adr_attr(attr)");
	    UTILS.DB.executeUpdate("CREATE INDEX adr_attr_expires ON adr_attr(expires)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void expired(long block) throws Exception
    {
        UTILS.DB.executeUpdate("DELETE "
                               + "FROM adr_attr "
                              + "WHERE expires<="+block);
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Load checkpoint
       loadCheckpoint(chk_hash);
    }
}
