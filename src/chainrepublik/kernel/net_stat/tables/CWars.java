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
                                                    + "warID BIGINT(20) DEFAULT NULL, "
                                                    + "attacker VARCHAR(10) NOT NULL, "
                                                    + "defender VARCHAR(45) DEFAULT NULL, "
                                                    + "attacker_points VARCHAR(45) DEFAULT NULL, "
                                                    + "defender_points VARCHAR(45) DEFAULT NULL, "
                                                    + "status VARCHAR(20) DEFAULT '', "
                                                    + "block BIGINT(20) DEFAULT NULL)");
            
            // Indexes
            UTILS.DB.executeUpdate("CREATE UNIQUE INDEX wars_warID ON wars(warID)");
            UTILS.DB.executeUpdate("CREATE INDEX wars_attacker ON wars(attacker)");
            UTILS.DB.executeUpdate("CREATE INDEX wars_defender ON wars(defender)");
            UTILS.DB.executeUpdate("CREATE INDEX wars_block ON wars(block)");
            
            // Confirm
            System.out.println("Done.");
        }
   }   
   
    public void expired(long block) throws Exception
    {
       // Load expired
       UTILS.DB.executeUpdate("DELETE "
                              + "FROM wars "
                             + "WHERE block<='"+(block-144000)+"'");
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Load checkpoint
       loadCheckpoint(chk_hash);
    }
}
