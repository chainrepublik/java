package chainrepublik.kernel.net_stat.tables;
import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;
import java.sql.ResultSet;

public class CCompanies extends CTable
{
    public CCompanies()
    {
        super("companies");
    }
    
    // Create
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table companies...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE companies(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                    + "adr VARCHAR(250) NOT NULL DEFAULT '', "
                                                    + "comID BIGINT(250) NOT NULL DEFAULT 0, "
                                                    + "tip VARCHAR(50) NOT NULL DEFAULT '', "
                                                    + "name VARCHAR(250) NOT NULL DEFAULT '', "
				 	            + "symbol VARCHAR(10) NOT NULL DEFAULT '', "
				    	            + "description VARCHAR(1000) NOT NULL DEFAULT '',"
                                                    + "expires BIGINT NOT NULL DEFAULT 0,"
                                                    + "code VARCHAR(10000) NOT NULL DEFAULT '')");
	
           // Indexes
	   UTILS.DB.executeUpdate("CREATE INDEX companies_adr ON companies(adr)");
           UTILS.DB.executeUpdate("CREATE UNIQUE INDEX companies_comID ON companies(comID)");
           UTILS.DB.executeUpdate("CREATE INDEX companies_tip ON companies(tip)");  
           UTILS.DB.executeUpdate("CREATE UNIQUE INDEX companies_symbol ON companies(symbol)"); 
           
           // Confirm
           System.out.println("Done.");
        }
    }
    
    public void expired(long block) throws Exception
    {
        // Load expired companies
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM companies "
                                          + "WHERE expires<="+block);
        
        if (UTILS.DB.hasData(rs))
        {
            while (rs.next())
            {
                // Company address
                String adr=rs.getString("adr");
                
                // Company ID
                long comID=rs.getLong("comID");
                
                // Symbol
                String symbol=rs.getString("symbol");
                
                // Remove company
                UTILS.DB.executeUpdate("DELETE FROM companies "
                                           + "WHERE adr='"+adr+"'");
                
                // Remove workplaces
                UTILS.DB.executeUpdate("DELETE FROM workplaces "
                                           + "WHERE comID='"+comID+"'");
                
                // Remove stoc
                UTILS.DB.executeUpdate("DELETE FROM stocuri "
                                           + "WHERE adr='"+adr+"'");
                
                // Remove shares owners
                UTILS.DB.executeUpdate("DELETE FROM assets_owners "
                                           + "WHERE symbol='"+symbol+"'");
                
                
                // Remove asset markets
                ResultSet rs_mkt=UTILS.DB.executeQuery("SELECT * "
                                                       + "FROM assets_mkts "
                                                      + "WHERE asset='"+symbol+"'");
                
                // Has data ?
                if (UTILS.DB.hasData(rs_mkt))
                {
                    // Next
                    rs_mkt.next();
                    
                    // Market ID
                    long mktID=rs_mkt.getLong("mktID");
                    
                    // Remove market
                    UTILS.DB.executeUpdate("DELETE FROM assets_mkts "
                                               + "WHERE mktID='"+mktID+"'");
                    
                    // Remove market orders
                    UTILS.DB.executeUpdate("DELETE FROM assets_mkts_pos "
                                               + "WHERE mktID='"+mktID+"'");
                }
                
                // Remove shares asset
                UTILS.DB.executeUpdate("DELETE FROM assets "
                                           + "WHERE symbol='"+symbol+"'");
                
                // Address has funds ?
                double balance=UTILS.ACC.getBalance(adr, "CRC");
                
                // Funds ?
                if (balance>0.0001)
                {
                    // Move funds to default address
                    UTILS.DB.executeUpdate("UPDATE adr "
                                            + "SET balance=balance+"+balance+" "
                                          + "WHERE adr='default'");
                    
                    // Remove address
                    UTILS.DB.executeUpdate("DELETE FROM adr "
                                          + "WHERE adr='"+adr+"'");
                }
                
            }
        }
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Load checkpoint
       loadCheckpoint(chk_hash);
    }
    
}

