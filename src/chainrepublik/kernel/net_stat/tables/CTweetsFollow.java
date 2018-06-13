package chainrepublik.kernel.net_stat.tables;

import java.sql.ResultSet;
import java.sql.Statement;
import chainrepublik.kernel.json.JSONArray;
import chainrepublik.kernel.json.JSONObject;
import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CTweetsFollow extends CTable
{
    public CTweetsFollow()
    {
        super("tweets_follow");
    }
    
     public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE tweets_follow(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                                +"adr VARCHAR(250) NOT NULL DEFAULT '', "
                                                                +"follows VARCHAR(250) NOT NULL DEFAULT '', "
                                                                +"expires BIGINT NOT NULL DEFAULT '0', "
                                                                +"block BIGINT NOT NULL DEFAULT '0')");
            
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX tweets_follow_adr ON tweets_follow(adr)");
            UTILS.DB.executeUpdate("CREATE INDEX tweets_follow_follows ON tweets_follow(follows)");
            UTILS.DB.executeUpdate("CREATE UNIQUE INDEX tweets_follow_adr_follows ON tweets_follow(adr, follows)");
            UTILS.DB.executeUpdate("CREATE INDEX tweets_follow_block ON tweets_follow(block)");
            UTILS.DB.executeUpdate("CREATE INDEX tweets_follow_expires ON tweets_follow(expires)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void expired(long block) throws Exception
    {
       // Load expired
       UTILS.DB.executeUpdate("DELETE "
                              + "FROM tweets_follow "
                             + "WHERE expires<='"+block+"'");
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Meesage
       System.out.println("Reorganizing tweets_follow...");
        
       // Delete
       UTILS.DB.executeUpdate("DELETE FROM tweets_follow "
                                  + "WHERE block>"+block);
       
       // Meesage
       System.out.print("Done");
    }
    
}