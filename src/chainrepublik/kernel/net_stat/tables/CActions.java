package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CActions extends CTable
{
    public CActions()
    {
        // Constructor
        super("actions");
    }   
    
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table actions...");
           
            // Create table
            UTILS.DB.executeUpdate("CREATE TABLE actions (ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                        + "userID BIGINT NOT NULL DEFAULT '0', "
                                                        + "act VARCHAR(500) NOT NULL DEFAULT '0', "
                                                        + "country VARCHAR(10) NOT NULL DEFAULT 'RO', "
                                                        + "IP VARCHAR(20) NOT NULL DEFAULT '127.0.0.1', "
                                                        + "url VARCHAR(500) NOT NULL DEFAULT '', "
                                                        + "tstamp BIGINT NOT NULL DEFAULT '0', "
                                                        + "tID VARCHAR(100) NOT NULL DEFAULT '0', "
                                                        + "mID VARCHAR(45) NOT NULL DEFAULT '0')");
        
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX actions_userID ON actions(userID)");
            UTILS.DB.executeUpdate("CREATE INDEX actions_country ON actions(country)");
	    UTILS.DB.executeUpdate("CREATE INDEX actions_IP ON actions(IP)");
            UTILS.DB.executeUpdate("CREATE INDEX actions_tID ON actions(tID)");
            UTILS.DB.executeUpdate("CREATE INDEX actions_mID ON actions(mID)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void expired(long block) throws Exception
    {
        UTILS.DB.executeUpdate("DELETE FROM actions "
                                   + "WHERE tstamp<="+(UTILS.BASIC.tstamp()-864000));
    }
}
