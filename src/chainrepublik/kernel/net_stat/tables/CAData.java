package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CAData extends CTable
{
    public CAData()
    {
        // Constructor
        super("adata");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
           // Status
           System.out.print("Creating table adata...");
           
           // Create
           UTILS.DB.executeUpdate("CREATE TABLE adata (ID BIGINT(20) AUTO_INCREMENT PRIMARY KEY, "
                                                    + "aID BIGINT(20) NOT NULL DEFAULT 0, "
                                                    + "s1 VARCHAR(1000) NOT NULL DEFAULT '', "
                                                    + "s2 VARCHAR(1000) NOT NULL DEFAULT '', "
                                                    + "s3 VARCHAR(1000) NOT NULL DEFAULT '', "
                                                    + "s4 VARCHAR(1000) NOT NULL DEFAULT '', "
                                                    + "s5 VARCHAR(1000) NOT NULL DEFAULT '', "
                                                    + "s6 VARCHAR(1000) NOT NULL DEFAULT '', "
                                                    + "s7 VARCHAR(1000) NOT NULL DEFAULT '', "
                                                    + "s8 VARCHAR(1000) NOT NULL DEFAULT '', "
                                                    + "s9 VARCHAR(1000) NOT NULL DEFAULT '', "
                                                    + "s10 VARCHAR(1000) NOT NULL DEFAULT '', "
                                                    + "n1 DOUBLE(20,8) NOT NULL DEFAULT 0, "
                                                    + "n2 DOUBLE(20,8) NOT NULL DEFAULT 0, "
                                                    + "n3 DOUBLE(20,8) NOT NULL DEFAULT 0, "
                                                    + "n4 DOUBLE(20,8) NOT NULL DEFAULT 0, "
                                                    + "n5 DOUBLE(20,8) NOT NULL DEFAULT 0, "
                                                    + "n6 DOUBLE(20,8) NOT NULL DEFAULT 0, "
                                                    + "n7 DOUBLE(20,8) NOT NULL DEFAULT 0, "
                                                    + "n8 DOUBLE(20,8) NOT NULL DEFAULT 0, "
                                                    + "n9 DOUBLE(20,8) NOT NULL DEFAULT 0, "
                                                    + "n10 DOUBLE(20,8) NOT NULL DEFAULT 0, "
                                                    + "tab VARCHAR(50) NOT NULL DEFAULT 0, "
                                                    + "expires BIGINT NOT NULL DEFAULT 0)");
           
           // Indexes
           UTILS.DB.executeUpdate("CREATE UNIQUE INDEX adata_aID ON adata(aID)");
           UTILS.DB.executeUpdate("CREATE UNIQUE INDEX adata_tab ON adata(tab)");
           UTILS.DB.executeUpdate("CREATE UNIQUE INDEX adata_expires ON adata(expires)");
           
           // Confirm
           System.out.println("Done.");
        }
    }
    
   
    public void expired(long block) throws Exception
    {
        // Expired
        UTILS.DB.executeUpdate("DELETE FROM adata "
                                   + "WHERE expires>="+block);
    }
    
   public void reorganize(long block, String chk_hash) throws Exception
   {
       // Load checkpoint
       this.loadCheckpoint(chk_hash);
   }
}
