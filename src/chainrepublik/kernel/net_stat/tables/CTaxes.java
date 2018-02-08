package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CTaxes extends CTable
{
    public CTaxes()
    {
        super("taxes");
    }
    
    // Create 
    public void create() throws Exception
    {
        if (!this.tableExist("taxes"))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE taxes (ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                    + "cou VARCHAR(5) NOT NULL DEFAULT '', "
                                                    + "tax VARCHAR(50) NOT NULL DEFAULT '', "
                                                    + "value FLOAT(9, 2) NOT NULL DEFAULT 0, "
                                                    + "prod VARCHAR(100) NOT NULL DEFAULT '', "
                                                    + "op VARCHAR(20) NOT NULL DEFAULT '', "
                                                    + "block BIGINT NOT NULL DEFAULT 0)");
	
            // Indexes
	    UTILS.DB.executeUpdate("CREATE INDEX taxes_cou ON taxes(cou)");
            UTILS.DB.executeUpdate("CREATE INDEX taxes_tax ON taxes(tax)"); 
            UTILS.DB.executeUpdate("CREATE INDEX taxes_prod ON taxes(prod)"); 
            UTILS.DB.executeUpdate("CREATE INDEX taxes_op ON taxes(op)"); 
            UTILS.DB.executeUpdate("CREATE INDEX taxes_block ON taxes(block)"); 
            
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

