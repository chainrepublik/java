package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CBlocks extends CTable
{
    public CBlocks() 
    {
        // Constructor
        super("blocks");
    }   
    
    public void create() throws Exception
    {
        if (!this.tableExist("blocks"))
        {
            // Status
            System.out.print("Creating table blocks...");
            
            // Create table
            UTILS.DB.executeUpdate("CREATE TABLE blocks(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
			 	 		     + "hash VARCHAR(100), "
			 	 	 	     + "block BIGINT NOT NULL DEFAULT 0, "
			 	 	 	     + "prev_hash VARCHAR(250) NOT NULL DEFAULT '', "
			 	 	 	     + "signer VARCHAR(250) NOT NULL DEFAULT '', "
                                                     + "packets BIGINT NOT NULL DEFAULT 0, "
                                                     + "tstamp BIGINT NOT NULL DEFAULT 0, "
                                                     + "nonce BIGINT NOT NULL DEFAULT 0, "
                                                     + "size BIGINT NOT NULL DEFAULT 0, "
                                                     + "net_dif VARCHAR(100) NOT NULL DEFAULT '0', "
                                                     + "commited BIGINT NOT NULL DEFAULT 0, "
                                                     + "confirmations BIGINT NOT NULL DEFAULT 0, "
                                                     + "reward FLOAT(20, 8) NOT NULL DEFAULT 0, "
                                                     + "payload_hash VARCHAR(250) NOT NULL DEFAULT '')");
	    
            // Indexes
	    UTILS.DB.executeUpdate("CREATE UNIQUE INDEX blocks_hash ON blocks(hash)");
            UTILS.DB.executeUpdate("CREATE UNIQUE INDEX blocks_payload_hash ON blocks(payload_hash)");
	    UTILS.DB.executeUpdate("CREATE INDEX blocks_block ON blocks(block)");
	    UTILS.DB.executeUpdate("CREATE INDEX blocks_signer ON blocks(signer)");
            UTILS.DB.executeUpdate("CREATE INDEX blocks_prev_hash ON blocks(prev_hash)");
            
            // Populate
            if (!this.reorg)
               this.populate();
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void populate() throws Exception
    {
        UTILS.DB.executeUpdate("INSERT INTO blocks "
                                     + "SET hash='0000000000000000000000000000000000000000000000000000000000000000', "
                                         + "block='0', "
                                         + "prev_hash='0000000000000000000000000000000000000000000000000000000000000000', "
                                         + "signer='default', "
                                         + "packets='0', "
                                         + "tstamp='0', "
                                         + "nonce='0', "
                                         + "size='0', "
                                         + "commited='0', "
                                         + "confirmations='0', "
                                         + "net_dif='00000000fc8eb4c424a4ab9659f606c254071192c1abd28ca94ee63f88323bbf', "
                                         + "payload_hash='0000000000000000000000000000000000000000000000000000000000000000'");
    }
}
