package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CRecPackets extends CTable
{
    public CRecPackets()
    {
        super("rec_packets");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE rec_packets(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
	 					          + "tip VARCHAR(100) NOT NULL DEFAULT '', "
	 					          + "fromIP  VARCHAR(100) NOT NULL DEFAULT '', "
	 					          + "tstamp  BIGINT NOT NULL DEFAULT 0, "
                                                          + "passed VARCHAR(10) NOT NULL DEFAULT '', "
                                                          + "reason VARCHAR(1000) NOT NULL DEFAULT '', "
                                                          + "hash VARCHAR(100) NOT NULL DEFAULT '')");
	    
            // Index
            UTILS.DB.executeUpdate("CREATE INDEX rec_packets_tip ON rec_packets(tip)");
            UTILS.DB.executeUpdate("CREATE INDEX rec_packets_fromIP ON rec_packets(fromIP)");
            UTILS.DB.executeUpdate("CREATE INDEX rec_packets_tstamp ON rec_packets(tstamp)");
            UTILS.DB.executeUpdate("CREATE INDEX rec_packets_hash ON rec_packets(hash)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void expired(long block) throws Exception
   {
       UTILS.DB.executeUpdate("DELETE FROM rec_packets "
                                  + "WHERE tstamp<"+(UTILS.BASIC.tstamp()-1440));
   }
}