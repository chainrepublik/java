package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;
import java.sql.ResultSet;

public class CStocuri extends CTable
{
    public CStocuri()
    {
        super("stocuri");
    }
    
    // Create
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            UTILS.DB.executeUpdate("CREATE TABLE stocuri (ID bigint(20) AUTO_INCREMENT PRIMARY KEY, "
                                                    + "adr varchar(250) NOT NULL DEFAULT '', "
                                                    + "tip varchar(100) NOT NULL DEFAULT '', "
                                                    + "qty float(20,4) NOT NULL DEFAULT '0.0000', "
                                                    + "expires bigint(20) NOT NULL DEFAULT '0', "
                                                    + "cap bigint(20) NOT NULL DEFAULT '0', "
                                                    + "used float(20,4) NOT NULL DEFAULT '0.0000', "
                                                    + "invested float(20,4) NOT NULL DEFAULT '0.0000', "
                                                    + "sale_qty float(20,4) NOT NULL DEFAULT '0.0000', "
                                                    + "rented_to varchar(250) DEFAULT '', "
                                                    + "rented_expires bigint(20) NOT NULL DEFAULT '0', "
                                                    + "rent_price float(20,4) NOT NULL DEFAULT '0.0000', "
                                                    + "in_use bigint(20) NOT NULL DEFAULT '0', "
                                                    + "stocID bigint(20) NOT NULL DEFAULT '0', "
                                                    + "energy float(10,4) DEFAULT '0.0000',"
                                                    + "block bigint(20) NOT NULL DEFAULT '0')");
	
            // Indexes
	    UTILS.DB.executeUpdate("CREATE INDEX stocuri_adr ON stocuri(adr)");
            UTILS.DB.executeUpdate("CREATE INDEX stocuri_tip ON stocuri(tip)");
            UTILS.DB.executeUpdate("CREATE INDEX stocuri_expires ON stocuri(expires)");
            UTILS.DB.executeUpdate("CREATE INDEX stocuri_rented_to ON stocuri(rented_to)");
            UTILS.DB.executeUpdate("CREATE INDEX stocuri_rented_expires ON stocuri(rented_expires)");
            UTILS.DB.executeUpdate("CREATE INDEX stocuri_stocID ON stocuri(stocID)");
            UTILS.DB.executeUpdate("CREATE INDEX stocuri_in_use ON stocuri(in_use)");
            UTILS.DB.executeUpdate("CREATE INDEX stocuri_block ON stocuri(block)"); 
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void expired(long block) throws Exception
    {
       // Load data
       ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM stocuri "
                                         + "WHERE expires<="+block+" "
                                           + "AND expires>0");
       
       // Loop
       while (rs.next())
       {
           // Remove
           UTILS.DB.executeUpdate("DELETE FROM stocuri "
                                      + "WHERE stocID='"+rs.getLong("stocID")+"'");
           
           // Energy product ?
           if (UTILS.BASIC.isEnergyProd(rs.getString("tip")))
               UTILS.BASIC.refreshEnergy(rs.getString("adr"));
       }
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Load checkpoint
       loadCheckpoint(chk_hash);
    }
}



