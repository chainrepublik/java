package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;
import java.sql.ResultSet;

public class CWars extends CTable
{
   public CWars()
   {
       super("wars");
   }
   
   public void create() throws Exception
   {
        if (!this.tableExist("wars"))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE wars (ID BIGINT(20) AUTO_INCREMENT PRIMARY KEY, "
                                                    + "warID BIGINT(20) NOT NULL DEFAULT 0, "
                                                    + "attacker VARCHAR(10) NOT NULL DEFAULT '', "
                                                    + "defender VARCHAR(10) NOT NULL DEFAULT '', "
                                                    + "target VARCHAR(10) NOT NULL DEFAULT '', "
                                                    + "attacker_points VARCHAR(45) NOT NULL DEFAULT 0, "
                                                    + "defender_points VARCHAR(45) NOT NULL DEFAULT 0, "
                                                    + "status VARCHAR(20) NOT NULL DEFAULT 'ID_PENDING', "
                                                    + "lawID BIGINT NOT NULL DEFAULT 0, "
                                                    + "block BIGINT(20) NOT NULL DEFAULT 0)");
            
            // Indexes
            UTILS.DB.executeUpdate("CREATE UNIQUE INDEX wars_warID ON wars(warID)");
            UTILS.DB.executeUpdate("CREATE INDEX wars_attacker ON wars(attacker)");
            UTILS.DB.executeUpdate("CREATE INDEX wars_defender ON wars(defender)");
            UTILS.DB.executeUpdate("CREATE INDEX wars_target ON wars(target)");
            UTILS.DB.executeUpdate("CREATE INDEX wars_lawID ON wars(lawID)");
            UTILS.DB.executeUpdate("CREATE INDEX wars_block ON wars(block)");
            
            // Confirm
            System.out.println("Done.");
        }
   }   
   
    public void expired(long block) throws Exception
    {
       // Load wars
       ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM wars "
                                         + "WHERE block<="+(block-144000));
       
       // Parse
       while (rs.next())
       {
          // War ID
          long warID=rs.getLong("warID");
          
          // Delete
          UTILS.DB.executeUpdate("DELETE "
                                 + "FROM wars "
                                + "WHERE warID='"+warID+"'");
          
          // Remove fighters
          UTILS.DB.executeUpdate("DELETE FROM wars_fighters "
                                     + "WHERE warID='"+warID+"'");
       }
       
       
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Load checkpoint
       loadCheckpoint(chk_hash);
    }
}
