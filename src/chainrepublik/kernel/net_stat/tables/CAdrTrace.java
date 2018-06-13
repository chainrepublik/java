/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CAdrTrace extends CTable
{
    public CAdrTrace()
    {
        super("adr_trace");
    }
    
    // Create
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table adr_trace...");
           
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE adr_trace(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                     + "block BIGINT NOT NULL DEFAULT 0, "
                                                     + "hash VARCHAR(250) NOT NULL DEFAULT '', "
				    		     + "balance DOUBLE(20, 8) NOT NULL DEFAULT 0)");
				
            // Indexes
	    UTILS.DB.executeUpdate("CREATE INDEX adr_trace_block ON adr_trace(block)");
            UTILS.DB.executeUpdate("CREATE UNIQUE INDEX adr_trace_hash ON adr_trace(hash)"); 
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Load checkpoint
       UTILS.DB.executeUpdate("DELETE FROM adr_trace "
                                  + "WHERE block>"+block);
    }
}

