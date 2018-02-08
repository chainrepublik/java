package chainrepublik.kernel.net_stat.tables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import chainrepublik.kernel.json.JSONArray;
import chainrepublik.kernel.json.JSONObject;
import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CAssetsOwners extends CTable 
{
    public CAssetsOwners()
    {
        super("assets_owners");
    }
    
    // Create
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table assets_owners...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE assets_owners(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                             +"owner VARCHAR(250) NOT NULL DEFAULT '', "
                                                             +"symbol VARCHAR(10) NOT NULL DEFAULT '', "
                                                             +"qty DOUBLE(20,8) NOT NULL DEFAULT 0)");
            
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX assets_owners_owner ON assets_owners(owner)");
            UTILS.DB.executeUpdate("CREATE INDEX assets_owners_symbol ON assets_owners(symbol)");
            UTILS.DB.executeUpdate("CREATE UNIQUE INDEX assets_owners_owner_symbol ON assets_owners(owner, symbol)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Load checkpoint
       loadCheckpoint(chk_hash);
    }
    
}



