package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;
import java.sql.ResultSet;

public class CAssetsMktsTrades extends CTable
{
    public CAssetsMktsTrades()
    {
        super("assets_mkts_trades");
    }
    
    // Create
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table assets_mkts_trades...");
            
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE assets_mkts_trades (ID BIGINT(20) AUTO_INCREMENT PRIMARY KEY, "
                                                                  + "mktID BIGINT(20) NOT NULL DEFAULT '0', "
                                                                  + "orderID BIGINT(20) NOT NULL DEFAULT '0', "
                                                                  + "buyer VARCHAR(250) NOT NULL DEFAULT '', "
                                                                  + "seller VARCHAR(250) NOT NULL DEFAULT '', "
                                                                  + "block BIGINT NOT NULL DEFAULT 0, "
                                                                  + "qty DOUBLE(20,8) NOT NULL DEFAULT 0, "
                                                                  + "price DOUBLE(20,8) NOT NULL DEFAULT 0)");
	 	 	   
            // Indexes
	    UTILS.DB.executeUpdate("CREATE INDEX assets_mkts_trades_mktID ON assets_mkts_trades(mktID)");
            UTILS.DB.executeUpdate("CREATE INDEX assets_mkts_trades_orderID ON assets_mkts_trades(orderID)");  
            UTILS.DB.executeUpdate("CREATE INDEX assets_mkts_trades_buyer ON assets_mkts_trades(buyer)");  
            UTILS.DB.executeUpdate("CREATE INDEX assets_mkts_trades_block ON assets_mkts_trades(block)");  
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void expired(long block) throws Exception
    {
        UTILS.DB.executeUpdate("DELETE FROM assets_mkts_trades "
                                   + "WHERE block<"+(block-14400));
    }
    
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
        // Meesage
        System.out.println("Reorganizing assets_mkts_trades...");
        
        // Remove
        UTILS.DB.executeUpdate("DELETE FROM assets_mkts_trades "
                                   + "WHERE block>"+block);
        
        // Meesage
        System.out.print("Done");
    }
}

