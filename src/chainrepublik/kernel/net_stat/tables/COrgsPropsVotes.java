package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class COrgsPropsVotes extends CTable
{
   public COrgsPropsVotes()
    {
        // Constructor
        super("orgs_props_votes");
    }   
    
    public void create() throws Exception
    {
        if (!this.tableExist("orgs_props_votes"))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE orgs_props_votes (ID BIGINT(11) AUTO_INCREMENT PRIMARY KEY, "
                                                                + "propID BIGINT(11) NOT NULL DEFAULT 0, "
                                                                + "adr VARCHAR(250) NOT NULL DEFAULT '', "
                                                                + "vote_type VARCHAR(10) NOT NULL DEFAULT '', "
                                                                + "power BIGINT(11) NOT NULL DEFAULT 0, "
                                                                + "block BIGINT(11) NOT NULL DEFAULT 0)");
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX orgs_props_votes_propID ON orgs_props_votes(propID)");
            UTILS.DB.executeUpdate("CREATE INDEX orgs_props_votes_adr ON orgs_props_votes(adr)");
            UTILS.DB.executeUpdate("CREATE INDEX orgs_props_votes_vote_type ON orgs_props_votes(vote_type)");
            UTILS.DB.executeUpdate("CREATE INDEX orgs_props_votes_block ON orgs_props_votes(block)");
            
            // Confirm
            System.out.println("Done.");
        }
    }       
    
    public void expired(long block) throws Exception
    {
       UTILS.DB.executeUpdate("DELETE FROM orgs_props_votes "
                                  + "WHERE block<"+(block-1440));
    }
   
   public void reorganize(long block, String chk_hash) throws Exception
   {
       UTILS.DB.executeUpdate("DELETE FROM orgs_props_votes "
                                  + "WHERE block>"+block);
   }
}
