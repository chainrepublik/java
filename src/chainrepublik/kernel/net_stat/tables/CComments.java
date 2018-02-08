package chainrepublik.kernel.net_stat.tables;

import java.sql.ResultSet;
import java.sql.Statement;
import chainrepublik.kernel.json.JSONArray;
import chainrepublik.kernel.json.JSONObject;
import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CComments extends CTable
{
    public CComments()
    {
        super("comments");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table comments...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE comments(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                            +"adr VARCHAR(250) NOT NULL DEFAULT '',"
                                                            +"parent_type VARCHAR(25) NOT NULL DEFAULT '', "
                                                            +"parentID BIGINT NOT NULL DEFAULT 0, "
                                                            +"comID BIGINT NOT NULL DEFAULT 0, "
                                                            +"mes VARCHAR(5000) NOT NULL DEFAULT '', "
                                                            +"block BIGINT(20) NOT NULL DEFAULT 0, "
                                                            +"expires BIGINT DEFAULT 0)");
            
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX comments_adr ON comments(adr)");
            UTILS.DB.executeUpdate("CREATE INDEX comments_parent_type ON comments(parent_type)");
            UTILS.DB.executeUpdate("CREATE INDEX comments_parentID ON comments(parentID)");
            UTILS.DB.executeUpdate("CREATE UNIQUE INDEX comments_comID ON comments(comID)");
            UTILS.DB.executeUpdate("CREATE INDEX comments_block ON comments(block)");
            UTILS.DB.executeUpdate("CREATE INDEX comments_expires ON comments(expires)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void expired(long block) throws Exception
    {
        UTILS.DB.executeUpdate("DELETE FROM comments "
                                   + "WHERE expires<="+block);
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Load checkpoint
       loadCheckpoint(chk_hash);
    }
    
}   
