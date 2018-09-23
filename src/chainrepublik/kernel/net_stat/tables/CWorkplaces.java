package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;
import java.sql.ResultSet;

public class CWorkplaces extends CTable
{
    public CWorkplaces()
    {
        super("workplaces");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE workplaces(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                           +"workplaceID BIGINT NOT NULL DEFAULT 0, "
                                                           +"comID BIGINT(20) NOT NULL DEFAULT 0, "
                                                           +"expires BIGINT NOT NULL DEFAULT 0, "
                                                           +"status VARCHAR(25) NOT NULL DEFAULT '', "
                                                           +"wage DOUBLE(20, 4) NOT NULL DEFAULT 0, "
                                                           +"prod VARCHAR(50) NOT NULL DEFAULT '', "
                                                           +"work_ends BIGINT NOT NULL DEFAULT 0, "
                                                           +"block BIGINT NOT NULL DEFAULT 0)");
        
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX workplaces_comID ON workplaces(comID)");
            UTILS.DB.executeUpdate("CREATE UNIQUE INDEX workplaces_wID ON workplaces(workplaceID)");
            UTILS.DB.executeUpdate("CREATE INDEX workplaces_status ON workplaces(status)");
            UTILS.DB.executeUpdate("CREATE INDEX workplaces_prod ON workplaces(prod)");
            UTILS.DB.executeUpdate("CREATE INDEX workplaces_expires ON workplaces(expires)");
            UTILS.DB.executeUpdate("CREATE INDEX workplaces_block ON workplaces(block)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
     public void expired(long block) throws Exception
     {
        // Load expired
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM workplaces "
                                          + "WHERE expires<"+block);
        
        while (rs.next())
        {
            // Company address
            String com_adr=UTILS.BASIC.getComAdr(rs.getLong("comID"));
            
            // Workplace ID
            long wID=rs.getLong("workplaceID");
            
            // Remove
            UTILS.DB.executeUpdate("DELETE "
                                   + "FROM workplaces "
                                  + "WHERE workplaceID='"+wID+"'");   
            
            // Event
            UTILS.BASIC.newEvent(com_adr, "One of company workplaces expired.", block);
        }
        
       
     }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Load checkpoint
       loadCheckpoint(chk_hash);
    }
}


