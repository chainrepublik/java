package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CLawsVotes extends CTable
{
   public CLawsVotes()
   {
       super("laws_votes");
   }
   
   public void create() throws Exception
   {
        if (!this.tableExist("laws_votes"))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE laws_votes (ID BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                                                          + "adr VARCHAR(250) NOT NULL DEFAULT '', "
                                                          + "lawID VARCHAR(100) NOT NULL DEFAULT '', "
                                                          + "type VARCHAR(250) NOT NULL DEFAULT '', "
                                                          + "points VARCHAR(250) NOT NULL DEFAULT '', "
                                                          + "block BIGINT(20) NOT NULL DEFAULT '0')");
           
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX laws_votes_adr ON laws_votes(adr)");
            UTILS.DB.executeUpdate("CREATE INDEX laws_votes_lawID ON laws_votes(lawID)");
            UTILS.DB.executeUpdate("CREATE INDEX laws_votes_type ON laws_votes(type)");
            UTILS.DB.executeUpdate("CREATE INDEX laws_votes_block ON laws_votes(block)");
            UTILS.DB.executeUpdate("CREATE INDEX laws_votes_adr_lawID ON laws_votes(adr, lawID)");
            
            // Confirm
            System.out.println("Done.");
        }
   }   
   
   public void expired(long block) throws Exception
   {
       UTILS.DB.executeUpdate("DELETE FROM laws_votes "
                                  + "WHERE block<"+(block-144000));
   }
   
   public void reorganize(long block, String chk_hash) throws Exception
   {
       UTILS.DB.executeUpdate("DELETE FROM laws_votes "
                                  + "WHERE block>"+block);
   }
}
