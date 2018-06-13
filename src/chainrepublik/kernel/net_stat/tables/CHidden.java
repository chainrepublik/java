package chainrepublik.kernel.net_stat.tables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import chainrepublik.kernel.json.JSONArray;
import chainrepublik.kernel.json.JSONObject;
import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CHidden extends CTable
{
    public CHidden()
    {
        super("hidden");
    }
    
    // Create
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table hidden...");
           
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE hidden(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                      + "contentID BIGINT(20) NOT NULL DEFAULT 0, "
                                                      + "hidden BIGINT(20) NOT NULL DEFAULT 0)");
				
            // Indexes
	    UTILS.DB.executeUpdate("CREATE INDEX hidden_contentID ON hidden(contentID)");
            UTILS.DB.executeUpdate("CREATE INDEX hidden_hidden ON hidden(hidden)"); 
            
            // Confirm
            System.out.println("Done.");
        }
    }
}
