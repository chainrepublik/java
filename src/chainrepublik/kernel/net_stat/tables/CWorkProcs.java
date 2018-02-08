package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;
import java.sql.ResultSet;

public class CWorkProcs extends CTable
{
   public CWorkProcs()
   {
       super("work_procs");
   }
   
   public void create() throws Exception
   {
        if (!this.tableExist("work_procs"))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE work_procs (ID BIGINT(20) AUTO_INCREMENT PRIMARY KEY, "
                                                          + "adr VARCHAR(250) NOT NULL DEFAULT '', "
                                                          + "comID BIGINT(20) NOT NULL DEFAULT '0', "
                                                          + "workplaceID BIGINT(20) NOT NULL DEFAULT '0', "
                                                          + "start BIGINT(20) NOT NULL DEFAULT '0', "
                                                          + "end BIGINT(20) NOT NULL DEFAULT '0', "
                                                          + "salary DOUBLE(20,8) NOT NULL DEFAULT '0.00000000', "
                                                          + "prod_1 VARCHAR(50) NOT NULL DEFAULT '', "
                                                          + "prod_1_qty DOUBLE(20,8) NOT NULL DEFAULT '0.00000000', "
                                                          + "prod_2 VARCHAR(50) NOT NULL DEFAULT '', "
                                                          + "prod_2_qty DOUBLE(20,8) NOT NULL DEFAULT '0.00000000', "
                                                          + "prod_3 VARCHAR(50) NOT NULL DEFAULT '', "
                                                          + "prod_3_qty DOUBLE(20,8) NOT NULL DEFAULT '0.00000000', "
                                                          + "prod_4 VARCHAR(50) NOT NULL DEFAULT '', "
                                                          + "prod_4_qty DOUBLE(20,8) NOT NULL DEFAULT '0.00000000', "
                                                          + "prod_5 VARCHAR(50) NOT NULL DEFAULT '', "
                                                          + "prod_5_qty DOUBLE(20,8) NOT NULL DEFAULT '0.00000000', "
                                                          + "prod_6 VARCHAR(50) NOT NULL DEFAULT '', "
                                                          + "prod_6_qty DOUBLE(20,8) NOT NULL DEFAULT '0.00000000', "
                                                          + "prod_7 VARCHAR(50) NOT NULL DEFAULT '', "
                                                          + "prod_7_qty DOUBLE(20,8) NOT NULL DEFAULT '0.00000000', "
                                                          + "prod_8 VARCHAR(50) NOT NULL DEFAULT '', "
                                                          + "prod_8_qty DOUBLE(20,8) NOT NULL DEFAULT '0.00000000', "
                                                          + "output_prod VARCHAR(50) NOT NULL DEFAULT '', "
                                                          + "output_qty DOUBLE(20,8) NOT NULL DEFAULT '0.00000000', "
                                                          + "block BIGINT(20) NOT NULL DEFAULT '0')");
            
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX work_procs_adr ON work_procs(adr)");
            UTILS.DB.executeUpdate("CREATE INDEX work_procs_comID ON work_procs(comID)");
            UTILS.DB.executeUpdate("CREATE INDEX work_procs_workplaceID ON work_procs(workplaceID)");
            UTILS.DB.executeUpdate("CREATE INDEX work_procs_start ON work_procs(start)");
            UTILS.DB.executeUpdate("CREATE INDEX work_procs_end ON work_procs(end)");
            UTILS.DB.executeUpdate("CREATE INDEX work_procs_block ON work_procs(block)");
            
            // Confirm
            System.out.println("Done.");
        }
   }   
   
    public void expired(long block) throws Exception
    {
       // Load expired
       UTILS.DB.executeUpdate("DELETE "
                              + "FROM work_procs "
                             + "WHERE block<'"+(block-1440)+"'");
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
       UTILS.DB.executeUpdate("DELETE FROM work_procs "
                                  + "WHERE block>"+block);
    }
}
