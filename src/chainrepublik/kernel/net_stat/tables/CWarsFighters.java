package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;
import java.sql.ResultSet;

public class CWarsFighters extends CTable
{
   public CWarsFighters()
   {
       super("wars_fighters");
   }
   
   public void create() throws Exception
   {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE wars_fighters (ID BIGINT(20) AUTO_INCREMENT PRIMARY KEY, "
                                                             + "warID BIGINT(20) NOT NULL DEFAULT 0, "
                                                             + "adr VARCHAR(250) NOT NULL DEFAULT '', "
                                                             + "type VARCHAR(5) NOT NULL DEFAULT 'AT', "
                                                             + "damage BIGINT NOT NULL DEFAULT 0, "
                                                             + "weapon VARCHAR(100) NOT NULL DEFAULT '', "
                                                             + "lawID BIGINT NOT NULL DEFAULT 0, "
                                                             + "block BIGINT(20) NOT NULL DEFAULT 0)");
            
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX wars_fighters_warID ON wars_fighters(warID)");
            UTILS.DB.executeUpdate("CREATE INDEX wars_fighters_adr ON wars_fighters(adr)");
            UTILS.DB.executeUpdate("CREATE INDEX wars_fighters_lawID ON wars_fighters(lawID)");
            UTILS.DB.executeUpdate("CREATE INDEX wars_fighters_type ON wars_fighters(type)");
            UTILS.DB.executeUpdate("CREATE INDEX wars_fighters_block ON wars_fighters(block)");
            
            // Confirm
            System.out.println("Done.");
        }
   }  
   
    public void expired(long block) throws Exception
    {
       // Load expired
       UTILS.DB.executeUpdate("DELETE "
                              + "FROM wars_fighters "
                             + "WHERE block<='"+(block-144000)+"'");
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Meesage
       System.out.println("Reorganizing wars_fighters...");
        
       // Delete
       UTILS.DB.executeUpdate("DELETE FROM wars_fighters "
                                  + "WHERE block>"+block);
       
       // Meesage
       System.out.println("Done");
    }
}
