package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

/**
 *
 * @author technicalsupport
 */
public class CAgentsLog extends CTable
{
    public CAgentsLog()
    {
        super("agents_log");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist("agents_log"))
        {
            // Status
            System.out.print("Creating table agents_log...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE agents_log ("
                                 + "ID BIGINT(11) AUTO_INCREMENT PRIMARY KEY, "
                                 + "symbol VARCHAR(10) NOT NULL DEFAULT '', "
                                 + "input_block_block BIGINT(11) NOT NULL, "
                                 + "input_block_hash VARCHAR(250) NOT NULL DEFAULT '', "
                                 + "input_block_prev_hash VARCHAR(250) NOT NULL DEFAULT '', "
                                 + "input_block_payload_hash VARCHAR(250) NOT NULL DEFAULT '', "
                                 + "input_block_signer VARCHAR(250) NOT NULL DEFAULT '', "
                                 + "input_block_sign VARCHAR(250) NOT NULL DEFAULT '', "
                                 + "input_block_nonce VARCHAR(25) NOT NULL DEFAULT '', "
                                 + "input_block_tstamp BIGINT(11) NOT NULL, "
                                 + "input_block_net_dif VARCHAR(250) NOT NULL DEFAULT '', "
                                 + "input_mes_sender VARCHAR(250) NOT NULL DEFAULT '', "
                                 + "input_mes_rec VARCHAR(250) NOT NULL DEFAULT '', "
                                 + "input_mes_subj VARCHAR(1000) NOT NULL DEFAULT '', "
                                 + "input_mes_mes VARCHAR(10000) NOT NULL DEFAULT '', "
                                 + "input_mes_hash VARCHAR(250) NOT NULL DEFAULT '', "
                                 + "input_mes_tstamp BIGINT(11) NOT NULL, "
                                 + "input_trans_sender VARCHAR(250) NOT NULL DEFAULT '', "
                                 + "input_trans_dest VARCHAR(250) NOT NULL DEFAULT '', "
                                 + "input_trans_amount DOUBLE(20, 8) NOT NULL DEFAULT 0, "
                                 + "input_trans_cur VARCHAR(10) NOT NULL DEFAULT '', "
                                 + "input_trans_mes VARCHAR(250) NOT NULL DEFAULT '', "
                                 + "input_trans_hash VARCHAR(250) NOT NULL DEFAULT '', "
                                 + "input_trans_tstamp BIGINT(11) NOT NULL DEFAULT 0, "
                                 + "input_donte_sender VARCHAR(250) NOT NULL DEFAULT '', "
                                 + "input_donte_dest VARCHAR(250) NOT NULL DEFAULT '', "
                                 + "input_donte_itemID BIGINT(11) NOT NULL DEFAULT 0, "
                                 + "input_donte_type VARCHAR(50) NOT NULL DEFAULT '', "
                                 + "input_donte_used DOUBLE(20,8) NOT NULL DEFAULT 0, "
                                 + "input_donte_expires BIGINT(11) NOT NULL DEFAULT 0, "
                                 + "input_donte_qty BIGINT(11) NOT NULL DEFAULT 0, "
                                 + "status VARCHAR(25) NOT NULL DEFAULT '', "
                                 + "err_mes VARCHAR(1000) NOT NULL DEFAULT '', "
                                 + "steps BIGINT(11) NOT NULL DEFAULT 0, "
                                 + "time BIGINT(11) NOT NULL DEFAULT 0, "
                                 + "cost double(20,8) NOT NULL DEFAULT 0, "
                                 + "block BIGINT(11) NOT NULL DEFAULT 0)");
            
            // Indexes
            UTILS.DB.executeUpdate("CREATE INDEX agents_log_symbol ON agents_log(symbol)");
            UTILS.DB.executeUpdate("CREATE INDEX agents_log_block ON agents_log(block)");
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void expired(long block) throws Exception
    {
       UTILS.DB.executeUpdate("DELETE FROM agents_log "
                                  + "WHERE block<"+(block-14400));
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Meesage
       System.out.println("Reorganizing agents_log...");
        
       // Load checkpoint
       UTILS.DB.executeUpdate("DELETE FROM agents_log "
                                  + "WHERE block>"+block);
       
       // Meesage
        System.out.print("Done");
    }
}