package chainrepublik.kernel.net_stat.tables;

import java.sql.ResultSet;
import java.sql.Statement;
import chainrepublik.kernel.json.JSONArray;
import chainrepublik.kernel.json.JSONObject;
import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CEscrowed extends CTable
{
    public CEscrowed()
    {
        super("escrowed");
    }
    
    // Create
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
         {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            UTILS.DB.executeUpdate("CREATE TABLE escrowed(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
			 	 		       + "trans_hash VARCHAR(250) NOT NULL DEFAULT '', "
			 	 	 	       + "sender_adr VARCHAR(250) NOT NULL DEFAULT '', "
			 	 	 	       + "rec_adr VARCHAR(250) NOT NULL DEFAULT '', "
                                                       + "escrower VARCHAR(250) NOT NULL DEFAULT '', "
                                                       + "amount DOUBLE(20,8) NOT NULL DEFAULT 0, "
                                                       + "cur VARCHAR(10) NOT NULL DEFAULT '', "
                                                       + "expires BIGINT NOT NULL DEFAULT 0, "
                                                       + "block BIGINT NOT NULL DEFAULT 0)");
	
           // Indexes
	    UTILS.DB.executeUpdate("CREATE UNIQUE INDEX escrowed_trans_hash ON escrowed(trans_hash)");
            UTILS.DB.executeUpdate("CREATE INDEX escrowed_sender_adr ON escrowed(sender_adr)");
            UTILS.DB.executeUpdate("CREATE INDEX escrowed_rec_adr ON escrowed(rec_adr)");
            UTILS.DB.executeUpdate("CREATE INDEX escrowed_escrower ON escrowed(escrower)");
            UTILS.DB.executeUpdate("CREATE INDEX escrowed_block ON escrowed(block)"); 
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void expired(long block) throws Exception
    {
       // Load expired
       ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM escrowed "
                                         + "WHERE expires<"+block);
       
       // Remove
       while (rs.next())
       {
            // Return the funds
            UTILS.ACC.newTrans(rs.getString("sender_adr"), 
                               "", 
                               rs.getDouble("amount"), 
                               rs.getString("cur"), 
                               "Escrowed funds returned", 
                               "", 
                               0,
                               UTILS.BASIC.hash(rs.getString("hash")), 
                               block,
                               false,
                               "",
                               "");
       
            // Clear
            UTILS.ACC.clearTrans(UTILS.BASIC.hash(rs.getString("hash")), "ID_ALL", block);
       
            // Remove
            UTILS.DB.executeUpdate("DELETE FROM escrowed "
                                       + "WHERE ID='"+rs.getLong("ID")+"'");
       }
    }
   
   public void reorganize(long block, String chk_hash) throws Exception
    {
       // Load checkpoint
       loadCheckpoint(chk_hash);
    }
}
