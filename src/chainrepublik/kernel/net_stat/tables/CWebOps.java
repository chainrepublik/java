package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;
import java.sql.ResultSet;

public class CWebOps extends CTable
{
    public CWebOps()
    {
        super("web_ops");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist("web_ops"))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE web_ops(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
			 	 		        + "userID BIGINT  NOT NULL DEFAULT 0, "
			 	 			+ "fee_adr VARCHAR(250)  NOT NULL DEFAULT '', "
			 	 			+ "target_adr VARCHAR(250)  NOT NULL DEFAULT '', "
			 	 			+ "op VARCHAR(50)  NOT NULL DEFAULT '', "
			 	 			+ "days BIGINT NOT NULL DEFAULT 0, "
			 	 			+ "par_1 TEXT, "
			 	 			+ "par_2 TEXT, "
			 	 			+ "par_3 TEXT, "
			 	 			+ "par_4 TEXT, "
			 	 			+ "par_5 TEXT, "
			 	 			+ "par_6 TEXT, "
			 	 			+ "par_7 TEXT, "
			 	 			+ "par_8 TEXT, "
			 	 			+ "par_9 TEXT, "
			 	 			+ "par_10 TEXT, "
                                                        + "par_11 TEXT, "
                                                        + "par_12 TEXT, "
                                                        + "par_13 TEXT, "
                                                        + "par_14 TEXT, "
                                                        + "par_15 TEXT, "
                                                        + "par_16 TEXT, "
                                                        + "par_17 TEXT, "
                                                        + "par_18 TEXT, "
                                                        + "par_19 TEXT, "
                                                        + "par_20 TEXT, "
                                                        + "par_21 TEXT, "
                                                        + "par_22 TEXT, "
                                                        + "par_23 TEXT, "
                                                        + "par_24 TEXT, "
                                                        + "par_25 TEXT, "
                                                        + "resp_1 TEXT, "
                                                        + "resp_2 TEXT, "
			 	 			+ "packet_sign VARCHAR(250) NOT NULL DEFAULT '', "
			 	 			+ "packet_hash VARCHAR(250) NOT NULL DEFAULT '', "
			 	 			+ "status VARCHAR(50) NOT NULL DEFAULT 'ID_PENDING', "
			 	 			+ "response VARCHAR(500) NOT NULL DEFAULT '', "
			 	 			+ "tstamp BIGINT NOT NULL DEFAULT 0, "
			 	 			+ "tID VARCHAR(50) NOT NULL DEFAULT '')");
	    
            // Indexes
	    UTILS.DB.executeUpdate("CREATE INDEX web_userID ON web_ops(userID)");
	    UTILS.DB.executeUpdate("CREATE INDEX web_op ON web_ops(op)");
	    UTILS.DB.executeUpdate("CREATE INDEX web_status ON web_ops(status)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
     public void expired(long block) throws Exception
    {
       // Load expired
       UTILS.DB.executeUpdate("DELETE "
                              + "FROM web_ops "
                             + "WHERE tstamp<'"+(UTILS.BASIC.tstamp()-86400)+"'");
    }
}

