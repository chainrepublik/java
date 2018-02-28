package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;
import java.sql.ResultSet;

public class CRentContracts extends CTable
{
   public CRentContracts()
   {
       super("rent_contracts");
   }
   
   public void create() throws Exception
   {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE rent_contracts (ID BIGINT(20) AUTO_INCREMENT PRIMARY KEY, "
                                                             + "stocID VARCHAR(250) NOT NULL DEFAULT '', "
                                                             + "from_adr VARCHAR(250) NOT NULL DEFAULT '', "
                                                             + "to_adr VARCHAR(250) NOT NULL DEFAULT '', "
                                                             + "price DOUBLE(20,4) NOT NULL DEFAULT '0.0000', "
                                                             + "expires BIGINT(20) NOT NULL DEFAULT '0', "
                                                             + "block BIGINT(20) NOT NULL DEFAULT '0')");
            
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX rent_contracts_prod ON rent_contracts(stocID)");
            UTILS.DB.executeUpdate("CREATE INDEX rent_contracts_from_adr ON rent_contracts(from_adr)");
            UTILS.DB.executeUpdate("CREATE INDEX rent_contracts_to_adr ON rent_contracts(to_adr)");
            UTILS.DB.executeUpdate("CREATE INDEX rent_contracts_expires ON rent_contracts(expires)");
            UTILS.DB.executeUpdate("CREATE INDEX rent_contracts_block ON rent_contracts(block)");
            
            // Confirm
            System.out.println("Done.");
        }
   }   
   
    public void expired(long block) throws Exception
    {
       // Load expired contracts
       ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                            + "FROM rent_contracts "
                                           + "WHERE expires>0 "
                                             + "AND expires="+block);
         
         // Loop
         while (rs.next())
         {
             // Update stocuri
             UTILS.DB.executeUpdate("UPDATE stocuri "
                                     + "SET rented_to='', "
                                         + "rented_expires=0, "
                                         + "in_use=0 "
                                   + "WHERE stocID='"+rs.getLong("stocID")+"'");
             
             // Refresh energy
             UTILS.BASIC.refreshEnergy(rs.getString("to_adr"));
             
             // Event
             UTILS.BASIC.newEvent(rs.getString("to_adr"), "One of your rent contracts has expired. Check your portofolio.", block);
         }
    }
   
    public void reorganize(long block, String chk_hash) throws Exception
    {
       UTILS.DB.executeUpdate("DELETE FROM rewards "
                                  + "WHERE block>"+block);
    }
}

