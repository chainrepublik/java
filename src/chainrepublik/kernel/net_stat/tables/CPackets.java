package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CPackets extends CTable
{
    public CPackets()
    {
        super("packets");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist("packets"))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
           
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE packets(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
			 	 		       + "packet_hash VARCHAR(250) NOT NULL DEFAULT '', "
			 	 	 	       + "par_1_name VARCHAR(100) NOT NULL DEFAULT '', "
			 	 	 	       + "par_1_val TEXT, "
                                                       + "par_2_name VARCHAR(100) NOT NULL DEFAULT '', "
			 	 	 	       + "par_2_val TEXT, "
                                                       + "par_3_name VARCHAR(100) NOT NULL DEFAULT '', "
			 	 	 	       + "par_3_val TEXT, "
                                                       + "par_4_name VARCHAR(100) NOT NULL DEFAULT '', "
			 	 	 	       + "par_4_val TEXT, "
                                                       + "par_5_name VARCHAR(100) NOT NULL DEFAULT '', "
			 	 	 	       + "par_5_val TEXT, "
                                                       + "par_6_name VARCHAR(100) NOT NULL DEFAULT '', "
			 	 	 	       + "par_6_val TEXT, "
                                                       + "par_7_name VARCHAR(100) NOT NULL DEFAULT '', "
			 	 	 	       + "par_7_val TEXT, "
                                                       + "par_8_name VARCHAR(100) NOT NULL DEFAULT '', "
			 	 	 	       + "par_8_val TEXT, "
                                                       + "par_9_name VARCHAR(100) NOT NULL DEFAULT '', "
			 	 	 	       + "par_9_val TEXT, "
                                                       + "par_10_name VARCHAR(100) NOT NULL DEFAULT '', "
			 	 	 	       + "par_10_val TEXT, "
                                                       + "par_11_name VARCHAR(100) NOT NULL DEFAULT '', "
			 	 	 	       + "par_11_val TEXT, "
                                                       + "par_12_name VARCHAR(100) NOT NULL DEFAULT '', "
			 	 	 	       + "par_12_val TEXT, "
                                                       + "par_13_name VARCHAR(100) NOT NULL DEFAULT '', "
			 	 	 	       + "par_13_val TEXT, "
                                                       + "par_14_name VARCHAR(100) NOT NULL DEFAULT '', "
			 	 	 	       + "par_14_val TEXT, "
                                                       + "par_15_name VARCHAR(100) NOT NULL DEFAULT '', "
			 	 	 	       + "par_15_val TEXT, "
                                                       + "par_16_name VARCHAR(100) NOT NULL DEFAULT '', "
			 	 	 	       + "par_16_val TEXT, "
                                                       + "par_17_name VARCHAR(100) NOT NULL DEFAULT '', "
			 	 	 	       + "par_17_val TEXT, "
                                                       + "par_18_name VARCHAR(100) NOT NULL DEFAULT '', "
			 	 	 	       + "par_18_val TEXT, "
                                                       + "par_19_name VARCHAR(100) NOT NULL DEFAULT '', "
			 	 	 	       + "par_19_val TEXT, "
                                                       + "par_20_name VARCHAR(100) NOT NULL DEFAULT '', "
			 	 	 	       + "par_20_val TEXT, "
                                                       + "block BIGINT NOT NULL DEFAULT 0, "
                                                       + "tstamp BIGINT NOT NULL DEFAULT 0, "
                                                       + "confirms BIGINT NOT NULL DEFAULT 0, "
                                                       + "block_hash VARCHAR(100) NOT NULL DEFAULT '', "
                                                       + "payload_hash VARCHAR(100) NOT NULL DEFAULT '', "
                                                       + "payload_size BIGINT NOT NULL DEFAULT 0, "
                                                       + "packet_type VARCHAR(100) NOT NULL DEFAULT '', "
                                                       + "fee_src VARCHAR(250) NOT NULL DEFAULT '', "
                                                       + "target_adr VARCHAR(250) NOT NULL DEFAULT '', "
                                                       + "fee_amount DOUBLE(20, 8) NOT NULL DEFAULT 0, "
                                                       + "fee_hash VARCHAR(250) NOT NULL DEFAULT '')");
	    
            // Indexes
	    UTILS.DB.executeUpdate("CREATE INDEX packets_packet_hash ON packets(packet_hash)");
            UTILS.DB.executeUpdate("CREATE INDEX packets_block ON packets(block)");
            UTILS.DB.executeUpdate("CREATE INDEX packets_block_hash ON packets(block_hash)");
            UTILS.DB.executeUpdate("CREATE INDEX packets_payload_hash ON packets(payload_hash)");
            UTILS.DB.executeUpdate("CREATE INDEX packets_fee_hash ON packets(fee_hash)");
            UTILS.DB.executeUpdate("CREATE INDEX packets_fee_src ON packets(fee_src)");
            UTILS.DB.executeUpdate("CREATE INDEX packets_target_adr ON packets(target_adr)");
            UTILS.DB.executeUpdate("CREATE INDEX packets_packet_type ON packets(packet_type)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void expired(long block) throws Exception
    {
        UTILS.DB.executeUpdate("DELETE FROM packets "
                                   + "WHERE block<"+(block-1440));
    }
}