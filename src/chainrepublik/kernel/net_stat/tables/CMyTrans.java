package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CMyTrans extends CTable
{
    public CMyTrans()
    {
        super("my_trans");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist("my_trans"))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE my_trans(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
	                                               + "userID BIGINT NOT NULL DEFAULT 0, "
                                                       + "adr VARCHAR(250) NOT NULL DEFAULT '', "
                                                       + "adr_assoc VARCHAR(250) NOT NULL DEFAULT '', "
	 	 	                               + "amount DOUBLE(20, 8) NOT NULL DEFAULT 0, "
                                                       + "invested DOUBLE(20, 8) NOT NULL DEFAULT 0, "
	 	 	 			       + "cur VARCHAR(50) NOT NULL DEFAULT '', "
	 	 	 			       + "expl VARCHAR(250) NOT NULL DEFAULT '', "
	 	 	 			       + "escrower VARCHAR(250) NOT NULL DEFAULT '', "
	 	 	 			       + "hash VARCHAR(100) NOT NULL DEFAULT '', "
                                                       + "tID BIGINT NOT NULL DEFAULT 0, "
                                                       + "block BIGINT NOT NULL DEFAULT 0, "
                                                       + "block_hash VARCHAR(100) NOT NULL DEFAULT '', "
                                                       + "tstamp BIGINT NOT NULL DEFAULT 0, "
	 	 	 			       + "cartID BIGINT NOT NULL DEFAULT 0, "
                                                       + "mes VARCHAR(2000) NOT NULL DEFAULT '')");
	    
            // Indexes            
	    UTILS.DB.executeUpdate("CREATE INDEX mt_userID ON my_trans(userID)");
	    UTILS.DB.executeUpdate("CREATE INDEX mt_adr ON my_trans(adr)");
            UTILS.DB.executeUpdate("CREATE INDEX mt_hash ON my_trans(hash)");
            UTILS.DB.executeUpdate("CREATE INDEX mt_block ON my_trans(block)");
            UTILS.DB.executeUpdate("CREATE INDEX mt_cur ON my_trans(cur)");
            UTILS.DB.executeUpdate("CREATE INDEX mt_block_hash ON my_trans(block_hash)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void expired(long block) throws Exception
    {
       UTILS.DB.executeUpdate("DELETE FROM my_trans "
                                  + "WHERE block<"+(block-144000));
    }
   
   public void reorganize(long block, String chk_hash) throws Exception
   {
       UTILS.DB.executeUpdate("DELETE FROM my_trans "
                                  + "WHERE block>"+block);
   }
}
