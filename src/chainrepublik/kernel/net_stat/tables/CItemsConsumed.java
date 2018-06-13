package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;
import java.sql.ResultSet;

public class CItemsConsumed extends CTable
{
    public CItemsConsumed()
    {
        super("items_consumed");
    }
    
    // Create
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE items_consumed(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
			 	 		         + "adr VARCHAR(250) NOT NULL DEFAULT '', "
			 	 	 	         + "tip VARCHAR(250) NOT NULL DEFAULT '', "
			 	 	 	         + "block BIGINT NOT NULL DEFAULT 0)");
	
            // Indexes
	    UTILS.DB.executeUpdate("CREATE INDEX items_consumed_adr ON items_consumed(adr)");
            UTILS.DB.executeUpdate("CREATE INDEX items_consumed_tip ON items_consumed(tip)");
            UTILS.DB.executeUpdate("CREATE INDEX items_consumed_block ON items_consumed(block)"); 
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void expired(long block) throws Exception
    {
       UTILS.DB.executeUpdate("DELETE FROM items_consumed "
                             + "WHERE block<"+(block-1440));
    }
   
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Meesage
       System.out.println("Reorganizing items_consumed...");
        
       // Delete
       UTILS.DB.executeUpdate("DELETE FROM items_consumed "
                                  + "WHERE block>"+block);
       
       // Meesage
       System.out.print("Done");
       
    }
}

