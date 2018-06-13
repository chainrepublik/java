package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CLaws extends CTable
{
    public CLaws()
    {
        super("laws");
    }
    
    // Create
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            if (!this.tableExist("laws"))
            {
                // Status
                System.out.print("Creating table "+this.name+"...");
            
                UTILS.DB.executeUpdate("CREATE TABLE laws (ID BIGINT(20) AUTO_INCREMENT PRIMARY KEY, "
                                                   + "lawID BIGINT(20) DEFAULT NULL, "
                                                   + "adr VARCHAR(250) NOT NULL DEFAULT '', "
                                                   + "type VARCHAR(100) NOT NULL DEFAULT '', "
                                                   + "par_1 VARCHAR(2500) NOT NULL DEFAULT '', "
                                                   + "par_2 VARCHAR(2500) NOT NULL DEFAULT '', "
                                                   + "par_3 VARCHAR(2500) NOT NULL DEFAULT '', "
                                                   + "par_4 VARCHAR(2500) NOT NULL DEFAULT '', "
                                                   + "par_5 VARCHAR(2500) NOT NULL DEFAULT '', "
                                                   + "expl VARCHAR(2500) NOT NULL DEFAULT '', "
                                                   + "status VARCHAR(25) NOT NULL DEFAULT '', "
                                                   + "country VARCHAR(45) NOT NULL DEFAULT '', "
                                                   + "voted_yes BIGINT(20) NOT NULL DEFAULT '0', "
                                                   + "voted_no BIGINT(20) NOT NULL DEFAULT '0', "
                                                   + "block BIGINT(20) NOT NULL DEFAULT '0')");
           
                // Indexes
                UTILS.DB.executeUpdate("CREATE INDEX laws_adr ON laws(adr)");
                UTILS.DB.executeUpdate("CREATE INDEX laws_type ON laws(type)");
                UTILS.DB.executeUpdate("CREATE INDEX laws_status ON laws(status)");
                UTILS.DB.executeUpdate("CREATE INDEX laws_country ON laws(country)");
                UTILS.DB.executeUpdate("CREATE UNIQUE INDEX laws_lawID ON laws(lawID)");
                UTILS.DB.executeUpdate("CREATE INDEX laws_block ON laws(block)");
                
                // Confirm
                System.out.println("Done.");
            }
        }
    }
    
    public void expired(long block) throws Exception
    {
       UTILS.DB.executeUpdate("DELETE FROM laws "
                             + "WHERE block<"+(block-144000));
    }
   
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Load checkpoint
       loadCheckpoint(chk_hash);
    }
}


