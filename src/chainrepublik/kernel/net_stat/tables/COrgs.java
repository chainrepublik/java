package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class COrgs  extends CTable
{
    public COrgs()
    {
        // Constructor
        super("orgs");
    }   
    
    public void create() throws Exception
    {
        if (!this.tableExist("orgs"))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            UTILS.DB.executeUpdate("CREATE TABLE orgs (ID BIGINT(11) AUTO_INCREMENT PRIMARY KEY, "
                                                    + "orgID BIGINT(11) NOT NULL DEFAULT '0', "
                                                    + "type VARCHAR(25) NOT NULL DEFAULT 'ID_POL_PARTY', "
                                                    + "mil_unit_level BIGINT(11) NOT NULL DEFAULT '0', "
                                                    + "country VARCHAR(11) NOT NULL DEFAULT '', "
                                                    + "adr VARCHAR(250) NOT NULL DEFAULT '', "
                                                    + "name VARCHAR(100) NOT NULL DEFAULT '', "
                                                    + "description VARCHAR(1000) NOT NULL DEFAULT '')");
           
           // Indexes
           UTILS.DB.executeUpdate("CREATE UNIQUE INDEX orgs_orgID ON orgs(orgID)");
           UTILS.DB.executeUpdate("CREATE INDEX orgs_adr ON orgs(adr)");
           UTILS.DB.executeUpdate("CREATE INDEX orgs_type ON orgs(type)");
           UTILS.DB.executeUpdate("CREATE INDEX orgs_country ON orgs(country)");
         
           
           // Confirm
           System.out.println("Done.");
        }
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Load checkpoint
       loadCheckpoint(chk_hash);
    }
}
