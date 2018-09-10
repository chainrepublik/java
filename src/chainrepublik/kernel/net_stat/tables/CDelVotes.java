package chainrepublik.kernel.net_stat.tables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import chainrepublik.kernel.json.JSONArray;
import chainrepublik.kernel.json.JSONObject;
import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CDelVotes extends CTable
{
    public CDelVotes()
    {
        super("del_votes");
    }
    
    // Create
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE del_votes(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                        + "delegate VARCHAR(500) NOT NULL DEFAULT '', "
                                                        + "adr VARCHAR(500) NOT NULL DEFAULT '', "
				  	                + "type VARCHAR(25) NOT NULL DEFAULT 'ID_UP', "
                                                        + "power BIGINT NOT NULL DEFAULT '0', "
				    	                + "block BIGINT NOT NULL DEFAULT 0)");
	
            // Indexes
	    UTILS.DB.executeUpdate("CREATE INDEX del_votes_delegate ON del_votes(delegate)");
            UTILS.DB.executeUpdate("CREATE INDEX del_votes_adr ON del_votes(adr)");
            UTILS.DB.executeUpdate("CREATE INDEX del_votes_type ON del_votes(type)");
            UTILS.DB.executeUpdate("CREATE INDEX del_votes_block ON del_votes(block)");   
            
            // Populate
            if (!this.reorg)
                this.populate();
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Load checkpoint
       loadCheckpoint(chk_hash);
    }
    
    public void populate() throws Exception
    {
        UTILS.DB.executeUpdate("INSERT INTO del_votes "
                                     + "SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEEpn9tcO9qb55dKKTCroSy6fa8mhyhChMYLdJer+WYnVR8Is9l1864vi9Z9eVXTkk3xo1ARNfY+fM0DWI0Wo7+g==', "
                                         + "delegate='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAENQYiiWVz9Qpn4/GKCqWXmtvmpTA0BGJyiuoRirsDNf7KGIP3rYxDrZ85ieW905o/IDIW0ZLi3GMpisYhGVvV+w==', "
                                         + "type='ID_UP', "
                                         + "block='0'");
        
        UTILS.DB.executeUpdate("INSERT INTO del_votes "
                                     + "SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEnynu3qRdoQkslCGdgajJJL0cyToq2CNrOcPKRBY+bqlAC16dfWS9pqjtc9cxhcDL88QlxsXYSqSxn54Lwkq2LQ==', "
                                         + "delegate='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEuUHnJKieTLy2cfm4bCCLSrbfShFlhkp/bkCOqAA/SMnz4Utlqxo2S8SuNczYKOLYsJYRqT2WLIId2/jlhfsT9Q==', "
                                         + "type='ID_UP', "
                                         + "block='0'");
        
        UTILS.DB.executeUpdate("INSERT INTO del_votes "
                                     + "SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEiNlB+/HYqGqFm/jF3jLePnD+YZ/6iIH/OzMh+aKAyAzD4KAk4bocCm38x2sFx1rxKQQYY6JKTbssUKsZKmk/xw==', "
                                         + "delegate='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEi/crmAw+sTWpVYM6Ip/qNv2qrscmV2TYsnbm16td8EJw53xA22iQG+mv3PF5gmLnCI++hOT9e+x0Q75/S4ioGw==', "
                                         + "type='ID_UP', "
                                         + "block='0'");
        
        UTILS.DB.executeUpdate("INSERT INTO del_votes "
                                     + "SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAERipiohgRY+exIa8naisHpe/1ZNfPzjsdN0V5kPI9gMyVMcrJbfS/RxuOqVHMgmqp7B3omAG0Vb+elXxyIrWU7g==', "
                                         + "delegate='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAE8s+v5EXWj0T4YX+dJF7aKb+iNC2AWv1+iJVsxhzwKjyrgtpRsi6q3TSYj66Plc5s9AOefeQu0FM+KzFMswiUiQ==', "
                                         + "type='ID_UP', "
                                         + "block='0'");
        
        UTILS.DB.executeUpdate("INSERT INTO del_votes "
                                     + "SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEIfhPhsjZ06QbAf45FMmDQuznOw/I9ob7ZR9bCUmxLAakhmzoz06mUylMa5o1iX3PvMV7DwDYZxOR18q89ljtyg==', "
                                         + "delegate='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEm5WmQquyZmZWHnQ1g/cODYk/pElXYo2GYcn8Jedmd/m7OzAAgm0ZEalt33rz8zxqZY7POxQhWYfNhY3ZlskiJQ==', "
                                         + "type='ID_UP', "
                                         + "block='0'");
        
        UTILS.DB.executeUpdate("INSERT INTO del_votes "
                                     + "SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEuTaBUEiPRcc3E9Re9UzVlo0VYkksRPp/DO7IMxxC/0JLaFqcs01gSbBC/VMVq8Jbl8SBf8NiE8nmTvHwxJABOA==', "
                                         + "delegate='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEedXcbVME/VJ5ck9nd3DMOy0B4IYpovtNJDCXO3ePwxA1MLEgECpiyMYgrQBTlwGA5dCBHR0PPFhC6b7eEiaorA==', "
                                         + "type='ID_UP', "
                                         + "block='0'");
        
        UTILS.DB.executeUpdate("INSERT INTO del_votes "
                                     + "SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAE8jW0Y6JCgLwv30tZFSWejL13jJ2MJLPs/77P5/yKcdJik6Q3abSr4cuyXP6aQa52FBc7ptOYsG6NwHO5P9wyxA==', "
                                         + "delegate='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEedXcbVME/VJ5ck9nd3DMOy0B4IYpovtNJDCXO3ePwxA1MLEgECpiyMYgrQBTlwGA5dCBHR0PPFhC6b7eEiaorA==', "
                                         + "type='ID_UP', "
                                         + "block='0'");
        
        UTILS.DB.executeUpdate("INSERT INTO del_votes "
                                     + "SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEoKdl/E8VU9XAXWPI7+ct/aBnNCyJoRzoYgIuNjAlbcSE6WFXtoyiYET6j5XWaODXP4PPhcQX5EHRoEPKpZTq0w==', "
                                         + "delegate='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEvdrva/vWBMfc3giSIcWVZj0D7xXodYowBSZkQQG5hCRX9snkEil7qN1X23IdfkgbJXaTUvS483UvzBacRLufow==', "
                                         + "type='ID_UP', "
                                         + "block='0'");
        
        UTILS.DB.executeUpdate("INSERT INTO del_votes "
                                     + "SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEBMSQ0Fso7nFoGiA+bBb0FtNApYqVXMRLIwAXuie04DZ8dXbsVktdqqzqUeHdACBXr1saEPWm0ueNnmoilq8lMg==', "
                                         + "delegate='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAE5S/1BsSAJ4ctdxNtwpkDG0eVJ3QzqnBNRJhB2mEKMvxyiqHPP5nOLJeBfwn/OIlmgghzYz1n368mGG7YoyGlPQ==', "
                                         + "type='ID_UP', "
                                         + "block='0'");
         
        UTILS.DB.executeUpdate("INSERT INTO del_votes "
                                     + "SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAE5QJZYCQz+/vegCpTenLEyNtlBTx2xyLaFqsGestQp5iE/jl2JJ0Rq7YQ2OtyPREHXboiqFxTOu51kEyMi8f1hg==', "
                                         + "delegate='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEm3KATgcjwryKRJui5r19HshIh1HqgNC5rRk16N0voQvtOk9dpYMHk6kZ82XbRLWzP+zw/pW6rtc27vSv65NPxA==', "
                                         + "type='ID_UP', "
                                         + "block='0'");
    }
}
