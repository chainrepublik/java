package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CRewards extends CTable
{
    public CRewards()
    {
        super("rewards");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE rewards (ID BIGINT(20) AUTO_INCREMENT PRIMARY KEY, "
                                                       + "adr VARCHAR(250) NOT NULL DEFAULT '', "
                                                       + "target_type VARCHAR(50) NOT NULL DEFAULT '', "
                                                       + "targetID BIGINT(20) NOT NULL DEFAULT 0, "
                                                       + "reward VARCHAR(100) NOT NULL DEFAULT '', "
                                                       + "amount DOUBLE(9,4) NOT NULL DEFAULT 0, "
                                                       + "block BIGINT(20) NOT NULL DEFAULT '0', "
                                                       + "par_f DOUBLE(20,4) NOT NULL DEFAULT 0, "
                                                       + "par_s VARCHAR(50) NOT NULL DEFAULT '')");
            
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX rewards_adr ON rewards(adr)");
            UTILS.DB.executeUpdate("CREATE INDEX rewards_target_type ON rewards(target_type)");
            UTILS.DB.executeUpdate("CREATE INDEX rewards_targetID ON rewards(targetID)");
            UTILS.DB.executeUpdate("CREATE INDEX rewards_reward ON rewards(reward)");
            UTILS.DB.executeUpdate("CREATE INDEX rewards_block ON rewards(block)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
   public void expired(long block) throws Exception
   {
       UTILS.DB.executeUpdate("DELETE FROM rewards "
                                  + "WHERE block<"+(block-14400));
   }
   
   public void reorganize(long block, String chk_hash) throws Exception
   {
       UTILS.DB.executeUpdate("DELETE FROM rewards "
                                  + "WHERE block>"+block);
   }
}
