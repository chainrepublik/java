package chainrepublik.kernel.net_stat.tables;

import java.sql.ResultSet;
import java.sql.Statement;
import chainrepublik.kernel.json.JSONArray;
import chainrepublik.kernel.json.JSONObject;
import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CVotes extends CTable
{
    public CVotes()
    {
        super("votes");
    }
    
    
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE votes(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                      +"target_type VARCHAR(20) NOT NULL DEFAULT '0', "
                                                      +"targetID BIGINT NOT NULL DEFAULT '0', "
                                                      +"type VARCHAR(25) NOT NULL DEFAULT '', "
                                                      +"adr VARCHAR(250) NOT NULL DEFAULT '', "
                                                      +"power DOUBLE(9,2) NOT NULL DEFAULT 0, "
                                                      +"block BIGINT NOT NULL DEFAULT '0')");
        
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX votes_target_type ON votes(target_type)");
            UTILS.DB.executeUpdate("CREATE INDEX votes_targetID ON votes(targetID)");
            UTILS.DB.executeUpdate("CREATE INDEX votes_adr ON votes(adr)");
            UTILS.DB.executeUpdate("CREATE INDEX votes_block ON votes(block)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
     public void expired(long block) throws Exception
    {
       // Load expired
       UTILS.DB.executeUpdate("DELETE "
                              + "FROM votes "
                             + "WHERE block<='"+(block-1440)+"'");
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Meesage
       System.out.println("Reorganizing votes...");
        
       // Delete
       UTILS.DB.executeUpdate("DELETE FROM votes "
                                  + "WHERE block>"+block);
       
       // Meesage
       System.out.print("Done");
    }
    
}

