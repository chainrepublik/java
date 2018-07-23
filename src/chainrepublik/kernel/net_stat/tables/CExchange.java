package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;
import java.sql.ResultSet;

public class CExchange extends CTable
{
     public CExchange()
     {
        super("exchange");
     }
    
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table exchange...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE exchange(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                       +"exID BIGINT NOT NULL DEFAULT 0, "
                                                       +"adr VARCHAR(250) NOT NULL DEFAULT '', "
                                                       +"side VARCHAR(10) NOT NULL DEFAULT '', "
                                                       +"price_type VARCHAR(20) NOT NULL DEFAULT '', "
                                                       +"margin BIGINT NOT NULL DEFAULT 0, "
                                                       +"price DOUBLE(20, 2) NOT NULL DEFAULT 0, "
                                                       +"min DOUBLE(20, 2) NOT NULL DEFAULT 0, "
                                                       +"max DOUBLE(20, 2) NOT NULL DEFAULT 0, "
                                                       +"method VARCHAR(250) NOT NULL DEFAULT '', "
                                                       +"details VARCHAR(1000) NOT NULL DEFAULT '', "
                                                       +"pay_info VARCHAR(1000) NOT NULL DEFAULT '', "
                                                       +"contact VARCHAR(1000) NOT NULL DEFAULT '', "
                                                       +"expires BIGINT NOT NULL DEFAULT 0, "
                                                       +"block BIGINT NOT NULL DEFAULT 0)");
            
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX exchange_extID ON exchange(extID)");
            UTILS.DB.executeUpdate("CREATE INDEX exchange_adr ON exchange(adr)");
            UTILS.DB.executeUpdate("CREATE INDEX exchange_method ON exchange(method)");
            UTILS.DB.executeUpdate("CREATE INDEX exchange_block ON exchange(block)");  
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void expired(long block) throws Exception
    {
       // Remove expiresd
       UTILS.DB.executeUpdate("DELETE FROM exchange "
                                  + "WHERE expires<"+block+" "
                                    + "AND expires>0");
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Load checkpoint
       this.loadCheckpoint(chk_hash);
    }
}
