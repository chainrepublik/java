package chainrepublik.kernel.net_stat.tables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import chainrepublik.kernel.json.JSONArray;
import chainrepublik.kernel.json.JSONObject;
import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CAssets extends CTable
{
    public CAssets()
    {
        super("assets");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table assets...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE assets(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                       +"assetID BIGINT NOT NULL DEFAULT 0, "
                                                       +"adr VARCHAR(250) NOT NULL DEFAULT '', "
                                                       +"symbol VARCHAR(10) NOT NULL DEFAULT '', "
                                                       +"title VARCHAR(250) NOT NULL DEFAULT '', "
                                                       +"description VARCHAR(1000) NOT NULL DEFAULT '', "
                                                       +"how_buy VARCHAR(1000) NOT NULL DEFAULT '', "
                                                       +"how_sell VARCHAR(1000) NOT NULL DEFAULT '', "
                                                       +"web_page VARCHAR(250) NOT NULL DEFAULT '', "
                                                       +"pic VARCHAR(250) NOT NULL DEFAULT '', "
                                                       +"expires BIGINT NOT NULL DEFAULT 0, "
                                                       +"qty BIGINT NOT NULL DEFAULT 0, "
                                                       +"trans_fee_adr VARCHAR(250) NOT NULL DEFAULT '', "
                                                       +"trans_fee DOUBLE(9,2) NOT NULL DEFAULT 0)");
            
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX assets_adr ON assets(adr)");
            UTILS.DB.executeUpdate("CREATE UNIQUE INDEX assets_ID ON assets(assetID)");
            UTILS.DB.executeUpdate("CREATE UNIQUE INDEX assets_symbol ON assets(symbol)");
            UTILS.DB.executeUpdate("CREATE INDEX assets_expires ON assets(expires)");  
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void expiresd(long block) throws Exception
    {
       // Load expiresd
       ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                             + "FROM assets "
                            + "WHERE expires<"+block+" "
                              + "AND expires>0");
       
       while (rs.next())
       {
           // Owners
           UTILS.DB.executeUpdate("DELETE FROM assets_owners "
                                       + "WHERE asset='"+rs.getString("symbol")+"'");
           
           // Remove asset
           UTILS.DB.executeUpdate("DELETE FROM assets "
                                      + "WHERE symbol='"+rs.getString("symbol")+"'");
       }
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Load checkpoint
       this.loadCheckpoint(chk_hash);
    }
}
