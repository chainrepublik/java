package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class COrgsProps  extends CTable
{
   public COrgsProps()
    {
        // Constructor
        super("orgs_props");
    }   
    
    public void create() throws Exception
    {
        if (!this.tableExist("orgs_props"))
        {
           // Status
           System.out.print("Creating table "+this.name+"...");
            
           UTILS.DB.executeUpdate("CREATE TABLE orgs_props (ID BIGINT(11) AUTO_INCREMENT  PRIMARY KEY, "
                                                         + "adr VARCHAR(250) NOT NULL DEFAULT '', "
                                                         + "orgID BIGINT(11) NOT NULL DEFAULT '0', "
                                                         + "propID BIGINT(11) NOT NULL DEFAULT '0', "
                                                         + "prop_type VARCHAR(25) NOT NULL DEFAULT '', "
                                                         + "expl VARCHAR(1000) NOT NULL DEFAULT '', "
                                                         + "par_1s VARCHAR(11) NOT NULL DEFAULT '', "
                                                         + "par_2s VARCHAR(11) NOT NULL DEFAULT '', "
                                                         + "par_3s VARCHAR(11) NOT NULL DEFAULT '', "
                                                         + "par_1f DOUBLE(20,4) NOT NULL DEFAULT 0, "
                                                         + "par_2f DOUBLE(20,4) NOT NULL DEFAULT 0, "
                                                         + "par_3f DOUBLE(20,4) NOT NULL DEFAULT 0, "
                                                         + "block BIGINT(11) DEFAULT '0',"
                                                         + "status VARCHAR(10) DEFAULT '',"
                                                         + "yes BIGINT(11) DEFAULT '0', "
                                                         + "no BIGINT(11) DEFAULT '0')");
           
           // Indexes
           UTILS.DB.executeUpdate("CREATE INDEX orgs_props_orgID ON orgs_props(orgID)");
           UTILS.DB.executeUpdate("CREATE UNIQUE INDEX orgs_props_propID ON orgs_props(propID)");
           UTILS.DB.executeUpdate("CREATE INDEX orgs_props_status ON orgs_props(status)");
           UTILS.DB.executeUpdate("CREATE INDEX orgs_props_block ON orgs_props(block)");
           UTILS.DB.executeUpdate("CREATE INDEX orgs_adr ON orgs_props(adr)");
           
           // Confirm
            System.out.println("Done.");
        }
    }    
    
    public void expired(long block) throws Exception
    {
       UTILS.DB.executeUpdate("DELETE FROM orgs_props "
                                  + "WHERE block<"+(block-144000));
    }
   
   public void reorganize(long block, String chk_hash) throws Exception
   {
       // Load checkpoint
       loadCheckpoint(chk_hash);
   }
}
