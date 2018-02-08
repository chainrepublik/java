package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CRefsAdrStats extends CTable
{
    public CRefsAdrStats()
    {
        // Constructor
        super("refs_adr_stats");
    }
    
    // Create
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
           // Create table
           UTILS.DB.executeUpdate("CREATE TABLE refs_adr_stats ("
                                + "ID BIGINT(11) unsigned NOT NULL AUTO_INCREMENT, "
                                + "adr VARCHAR(250) DEFAULT NULL, "
                                + "day BIGINT(11) DEFAULT NULL, "
                                + "month BIGINT(11) DEFAULT NULL, "
                                + "year BIGINT(11) DEFAULT NULL, "
                                + "block BIGINT(11) DEFAULT NULL)");
	
            // Indexes 
            UTILS.DB.executeUpdate("CREATE INDEX refs_adr_stats_adr ON refs_adr_stats(adr)");
            UTILS.DB.executeUpdate("CREATE INDEX refs_adr_stats_date ON refs_adr_stats(day, month, year)");
	    UTILS.DB.executeUpdate("CREATE INDEX refs_adr_stats_block ON refs_adr_stats(block)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void expired(long block) throws Exception
    {
        UTILS.DB.executeUpdate("DELETE "
                               + "FROM refs_adr_stats "
                              + "WHERE block<="+(block-500000));
    }
}