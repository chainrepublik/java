package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;
import java.sql.ResultSet;

public class CEndorsers extends CTable
{
     public CEndorsers()
     {
         super("endorsers");
     }
     
     public void create() throws Exception
     {
         if (!this.tableExist(this.name))
         {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            UTILS.DB.executeUpdate("CREATE TABLE endorsers (ID BIGINT(20) AUTO_INCREMENT PRIMARY KEY, "
                                                         + "endorser VARCHAR(250) NOT NULL DEFAULT '', "
                                                         + "endorsed VARCHAR(250) DEFAULT NULL DEFAULT '', "
                                                         + "type VARCHAR(20) DEFAULT NULL DEFAULT '', "
                                                         + "power DOUBLE(20,2) DEFAULT NULL DEFAULT 0, "
                                                         + "block BIGINT(20) DEFAULT NULL DEFAULT 0)");
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX endorsers_endorser ON endorsers(endorser)");
            UTILS.DB.executeUpdate("CREATE INDEX endorsers_endorsed ON endorsers(endorsed)");
            UTILS.DB.executeUpdate("CREATE INDEX endorsers_type ON endorsers(type)");
            UTILS.DB.executeUpdate("CREATE INDEX endorsers_block ON endorsers(block)");
            UTILS.DB.executeUpdate("CREATE UNIQUE INDEX endorsers_endorser_adr ON endorsers(endorser, endorsed)");
            
            // Confirm
            System.out.println("Done.");
        }
     }
     
     public void expired(long block) throws Exception
     {
         ResultSet rs=UTILS.DB.executeQuery("SELECT endo.* "
                                            + "FROM endorsers AS endo "
                                            + "JOIN adr ON adr.adr=endo.endorser "
                                           + "WHERE adr.pol_inf<25");
         
         while (rs.next())
             UTILS.DB.executeUpdate("DELETE FROM endorsers "
                                        + "WHERE endorser='"+rs.getString("endorser")+"'");
    }
     
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Load checkpoint
       UTILS.DB.executeUpdate("DELETE FROM endorsers "
                                  + "WHERE block>"+block);
    }
}
