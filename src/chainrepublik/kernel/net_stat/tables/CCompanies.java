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
                                                    + "pic VARCHAR(500) NOT NULL DEFAULT '',"
                                                    + "code VARCHAR(10000) NOT NULL DEFAULT '',"
                                                    + "sealed VARCHAR(10) NOT NULL DEFAULT 'ID_NO')");
	
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
        UTILS.DB.executeUpdate("DELETE "
                               + "FROM companies "
                              + "WHERE expires<="+block);
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Load checkpoint
       loadCheckpoint(chk_hash);
    }
    
}

