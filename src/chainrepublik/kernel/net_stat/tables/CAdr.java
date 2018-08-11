package chainrepublik.kernel.net_stat.tables;

import java.sql.ResultSet;
import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CAdr extends CTable
{
    public CAdr()
    {
        // Constructor
        super("adr");
    }
    
    // Create
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
           // Status
           System.out.print("Creating table adr...");
           
           // Create table
           UTILS.DB.executeUpdate("CREATE TABLE adr (ID BIGINT(20) AUTO_INCREMENT PRIMARY KEY, "
                                               + "adr VARCHAR(500) NOT NULL DEFAULT '', "
                                               + "cou VARCHAR(10) NOT NULL DEFAULT '', "
                                               + "name VARCHAR(100) NOT NULL DEFAULT '', "
                                               + "description VARCHAR(1000) NOT NULL DEFAULT '', "
                                               + "ref_adr VARCHAR(500) NOT NULL DEFAULT '', "
                                               + "node_adr VARCHAR(500) NOT NULL DEFAULT '', "
                                               + "balance DOUBLE(20,8) NOT NULL DEFAULT 0, "
                                               + "pic VARCHAR(1000) NOT NULL DEFAULT '', "
                                               + "block BIGINT(20) NOT NULL DEFAULT 0, "
                                               + "pol_inf DOUBLE(10,2) NOT NULL DEFAULT 0, "
                                               + "energy DOUBLE(10,4) NOT NULL DEFAULT 0, "
                                               + "energy_block DOUBLE(10,4) NOT NULL DEFAULT 0, "
                                               + "loc VARCHAR(5) NOT NULL DEFAULT '', "
                                               + "pol_endorsed BIGINT(20) NOT NULL DEFAULT 0, "
                                               + "created BIGINT(20) NOT NULL DEFAULT 0, "
                                               + "premium BIGINT(20) NOT NULL DEFAULT 0, "
                                               + "travel BIGINT(20) NOT NULL DEFAULT 0, "
                                               + "travel_cou VARCHAR(10) NOT NULL DEFAULT 0, "
                                               + "work BIGINT(20) NOT NULL DEFAULT 0, "
                                               + "expires BIGINT(20) NOT NULL DEFAULT 0, "
                                               + "mil_unit BIGINT(20) NOT NULL DEFAULT 0, "
                                               + "pol_party BIGINT(20) NOT NULL DEFAULT 0, "
                                               + "war_points BIGINT(20) NOT NULL DEFAULT 0)");
       
            // Indexes
            UTILS.DB.executeUpdate("CREATE UNIQUE INDEX adr_adr ON adr(adr)");
            UTILS.DB.executeUpdate("CREATE INDEX adr_cou ON adr(cou)");
	    UTILS.DB.executeUpdate("CREATE INDEX adr_travel ON adr(travel)");
            UTILS.DB.executeUpdate("CREATE INDEX adr_name ON adr(name)");
            UTILS.DB.executeUpdate("CREATE INDEX adr_ref_adr ON adr(ref_adr)");
            UTILS.DB.executeUpdate("CREATE INDEX adr_node_adr ON adr(node_adr)");
            UTILS.DB.executeUpdate("CREATE INDEX adr_loc ON adr(loc)");
            UTILS.DB.executeUpdate("CREATE INDEX adr_created ON adr(created)");
            UTILS.DB.executeUpdate("CREATE INDEX adr_pol_party ON adr(pol_party)");
            UTILS.DB.executeUpdate("CREATE INDEX adr_mil_unit ON adr(mil_unit)");
            
            // Populate
            if (!reorg)
               this.populate();
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public boolean hasTableRecords(String table, String col, String adr) throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM "+table+" "
                                          + "WHERE "+col+"='"+adr+"'");
        
        if (!UTILS.DB.hasData(rs))
            return false;
        else
            return true;
    }
    
    public boolean hasRecords(String adr) throws Exception
    {
        // Ads ?
        if (this.hasTableRecords("ads", "adr", adr)) 
            return true;
        
        // Assets
        if (this.hasTableRecords("assets", "adr", adr)) 
            return true;
        
        // Assets owners
        if (this.hasTableRecords("assets_owners", "owner", adr)) 
            return true;
        
        // Del votes
        if (this.hasTableRecords("del_votes", "adr", adr)) 
            return true;
        
        // Escrowed
        if (this.hasTableRecords("escrowed", "sender_adr", adr)) 
            return true;
        
        if (this.hasTableRecords("escrowed", "rec_adr", adr)) 
            return true;
        
        if (this.hasTableRecords("escrowed", "escrower", adr)) 
            return true;
        
        // Assets markets
        if (this.hasTableRecords("assets_mkts", "adr", adr)) 
            return true;
        
        // Assets markets pos
        if (this.hasTableRecords("assets_mkts_pos", "adr", adr)) 
            return true;
        
        // Comments
        if (this.hasTableRecords("comments", "adr", adr)) 
            return true;
        
        // Votes
        if (this.hasTableRecords("votes", "adr", adr)) 
            return true;
        
        // Tweets
        if (this.hasTableRecords("tweets", "adr", adr)) 
            return true;
        
        // Tweets follow
        if (this.hasTableRecords("tweets_follow", "adr", adr)) 
            return true;
        
        // Ok to delete
        return false;
    }
    
    public void expired(long block) throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM adr "
                                          + "WHERE balance<=0.0001 "
                                            + "AND cou=''");
        
        while (rs.next())
           if (!this.hasRecords(rs.getString("adr")))
               UTILS.DB.executeUpdate("DELETE FROM adr "
                                          + "WHERE adr='"+rs.getString("adr")+"'");
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Load checkpoint
       loadCheckpoint(chk_hash);
    }
    
    public void populate() throws Exception
    {
        // Default
        UTILS.DB.executeUpdate("INSERT INTO adr SET adr='default', balance='9000000', block='0', cou='RO'");
        
        
        // Main addresses
        UTILS.DB.executeUpdate("INSERT INTO adr SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEEpn9tcO9qb55dKKTCroSy6fa8mhyhChMYLdJer+WYnVR8Is9l1864vi9Z9eVXTkk3xo1ARNfY+fM0DWI0Wo7+g==', balance='100000', block='0', cou='RO', name='devteam1', expires=1000000");
        UTILS.DB.executeUpdate("INSERT INTO adr SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEnynu3qRdoQkslCGdgajJJL0cyToq2CNrOcPKRBY+bqlAC16dfWS9pqjtc9cxhcDL88QlxsXYSqSxn54Lwkq2LQ==', balance='100000', block='0', cou='RO', name='devteam2', expires=1000000");
        UTILS.DB.executeUpdate("INSERT INTO adr SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEiNlB+/HYqGqFm/jF3jLePnD+YZ/6iIH/OzMh+aKAyAzD4KAk4bocCm38x2sFx1rxKQQYY6JKTbssUKsZKmk/xw==', balance='100000', block='0', cou='RO', name='devteam3', expires=1000000");
        UTILS.DB.executeUpdate("INSERT INTO adr SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAERipiohgRY+exIa8naisHpe/1ZNfPzjsdN0V5kPI9gMyVMcrJbfS/RxuOqVHMgmqp7B3omAG0Vb+elXxyIrWU7g==', balance='100000', block='0', cou='RO', name='devteam4', expires=1000000");
        UTILS.DB.executeUpdate("INSERT INTO adr SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEIfhPhsjZ06QbAf45FMmDQuznOw/I9ob7ZR9bCUmxLAakhmzoz06mUylMa5o1iX3PvMV7DwDYZxOR18q89ljtyg==', balance='100000', block='0', cou='RO', name='devteam5', expires=1000000");
        UTILS.DB.executeUpdate("INSERT INTO adr SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEuTaBUEiPRcc3E9Re9UzVlo0VYkksRPp/DO7IMxxC/0JLaFqcs01gSbBC/VMVq8Jbl8SBf8NiE8nmTvHwxJABOA==', balance='100000', block='0', cou='RO', name='devteam6', expires=1000000");
        UTILS.DB.executeUpdate("INSERT INTO adr SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAE8jW0Y6JCgLwv30tZFSWejL13jJ2MJLPs/77P5/yKcdJik6Q3abSr4cuyXP6aQa52FBc7ptOYsG6NwHO5P9wyxA==', balance='100000', block='0', cou='RO', name='devteam7', expires=1000000");
        UTILS.DB.executeUpdate("INSERT INTO adr SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEoKdl/E8VU9XAXWPI7+ct/aBnNCyJoRzoYgIuNjAlbcSE6WFXtoyiYET6j5XWaODXP4PPhcQX5EHRoEPKpZTq0w==', balance='100000', block='0', cou='RO', name='devteam8', expires=1000000");
        UTILS.DB.executeUpdate("INSERT INTO adr SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEBMSQ0Fso7nFoGiA+bBb0FtNApYqVXMRLIwAXuie04DZ8dXbsVktdqqzqUeHdACBXr1saEPWm0ueNnmoilq8lMg==', balance='100000', block='0', cou='RO', name='devteam9', expires=1000000");
        UTILS.DB.executeUpdate("INSERT INTO adr SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAE5QJZYCQz+/vegCpTenLEyNtlBTx2xyLaFqsGestQp5iE/jl2JJ0Rq7YQ2OtyPREHXboiqFxTOu51kEyMi8f1hg==', balance='100000', block='0', cou='RO', name='devteam10', expires=1000000");
    }
}
