package chainrepublik.kernel.net_stat.consensus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.blocks.CBlockPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;
import chainrepublik.network.packets.sync.CGetBlockPacket;

public class CConsensus 
{
   // Status
   public String status;
   
   // Timer
   Timer timer;
   
   // Chain
   ArrayList <String> chain;
   
   // Actual block hash
   public String block_hash;
   
   // Actual block number
   public long block_number;
   
   // Refresh ?
   public boolean relay=false;
   
   public CConsensus() throws Exception
   {
      UTILS.DB.executeUpdate("DELETE FROM blocks_pool");
      
       // Status
       setStatus("ID_WAITING");
       
        // Timer
       timer = new Timer();
       RemindTask task=new RemindTask();
       timer.schedule(task, 0, 1000);
   }
   
   
   public synchronized void blockReceived(CBlockPacket block) throws Exception
   {
       UTILS.BASIC.startTimer();
       
       try
       {
          // Begin
          UTILS.DB.begin();
          
          // Processing ?
          if (!this.status.equals("ID_WAITING"))
          {
             // Message
             System.out.println("Status is not WAITING...");
             
             // Add to pool
             this.addToPool(block);
             
             // Store
             this.store(block);
             
             // Commit
             UTILS.DB.commit();
             
             // Return
             return;
          }
          
           // Status
           this.setStatus("ID_PROCESSING");
       
           // Block exist ?
           if (this.inBlockchain(block.hash))
           {
              // Print
              System.out.println("Block already in blockchain....");
           
              // Remove from pool
              this.removeFromPool(block.hash, block.block);
           
              // Waiting
              this.setStatus("ID_WAITING");
              
              // Commit
              UTILS.DB.commit();
           
              // Return
              return;
           }
        
          // POW
          if (!block.preCheck()) 
             throw new Exception ("Block precheck failed");
          
           // Store
          this.store(block);
       
          // Broadcast
          if (!UTILS.STATUS.status.equals("ID_SYNC")) 
               UTILS.NETWORK.broadcast(block);
               
          // Unknown parent
          if (!this.inBlockchain(block.prev_hash) &&
              !this.inPool(block.prev_hash)) 
          {
              // Message
              System.out.println("No prev hash found for block "+block.block+"...");
              
              // Add to pool
              this.addToPool(block);
          }
          else
          {
             // Block number
             if (block.block==UTILS.NET_STAT.last_block+1)
             {
                if (UTILS.NET_STAT.last_block_hash.equals(block.prev_hash))
                {
                   // Commit
                   commitBlock(block);
                }
                else 
                {
                   // Status
                   this.setStatus("ID_REORGANIZING");
              
                   // Reorganize blockchain
                   this.reorganize(block.prev_hash);
                
                   // Add to chain
                   this.commitBlock(block);
                }
            }  
            else if (block.block>UTILS.NET_STAT.last_block+1)
            {
                // Message
                System.out.println("Block number bigger than (last_block+1)...");
                
                // Add to pool
                this.addToPool(block);
            }
            else 
            {
                System.out.println("Block number behind or equal to last block...");
                
                if (this.inBlockchain(block.block))
                   this.addBlock(block);
                else
                   this.commitBlock(block);
            }
        }
        
        // Commit
        UTILS.DB.commit();
         
        // Stats
        UTILS.BASIC.checkpoint();
        
         // Status
        this.setStatus("ID_WAITING");
        
        // Cons
        UTILS.DB.clearCons();
       }
       catch (Exception ex)
       {
           // Rollback
           System.out.println("Rolling back block " + block.block + " - " + ex.getMessage());
           
           // Rollback
           UTILS.DB.rollback();
           
           // Remove from pool
           this.removeFromPool(block.hash, block.block);
          
           // New status
           this.setStatus("ID_WAITING");
           
           
          // Throws exception
          throw new Exception(ex);
       }
   }
   
   public void commitBlock(CBlockPacket block) throws Exception
   {
        // Block hash
        this.block_hash=block.hash;
                  
        // Block number
        this.block_number=block.block;
            
        // Check
        block.check();
        
        // Add to chain
        this.addBlock(block);
        
        // Commit
        block.commit();
                  
        // Commited
        this.commited(block.block, block.hash);
        
   }
   
   public void commited(long block, String hash) throws Exception
   {
        // Check hash
        if (!UTILS.BASIC.isHash(hash))
           throw new Exception("Invalid hash - CConsensus.java, 410");
       
        // Reset commited
        UTILS.DB.executeUpdate("UPDATE blocks "
                                + "SET commited=0 "
                              + "WHERE block='"+block+"'");
                
        // Commited
        UTILS.DB.executeUpdate("UPDATE blocks "
                                + "SET commited='"+UTILS.BASIC.tstamp()+"' "
                              + "WHERE hash='"+hash+"'");
   }
   
   public void reorganize(String hash) throws Exception
   {
       // Reorganize event 
       UTILS.BASIC.newEvent("default", 
                            "A blockchain reorganization has started at block "+this.block_number+", block hash : "+hash, 
                            this.block_number);
       
       // Init chain
       this.chain=new ArrayList();
       
       // Add block
       this.chain.add(hash);
       
       // Find a chain up to the last checkpoint
       boolean found=false;
       
       // Check hash
       if (!UTILS.BASIC.isHash(hash))
           throw new Exception("Invalid hash - CConsensus.java, 410");
       
       
       while (!found)
       {
           ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                              + "FROM blocks "
                                             + "WHERE hash='"+hash+"'");
           
           // Next 
           rs.next();
           
           // Parent
           hash=rs.getString("prev_hash");
           
           // Is checkpoint ?
           if (!this.isCheckPoint(hash))
           {
               this.chain.add(hash);
               if (this.chain.size()>110)
               {
                  System.out.println("Chain too long...");
                  System.exit(0);
               }
           }
           else
           {
               found=true;
               this.reorganize(rs.getLong("block")-1);
           }
       }
       
   }
   
   public void reorganize(long block) throws Exception
   {
       // Reorganize event 
       UTILS.BASIC.newEvent("default", 
                            "Reorganizing from block "+block, 
                            this.block_number);
       
       // Message
       System.out.println("Reorganizing from block "+block);
       
       // Load
       ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM blocks "
                                         + "WHERE block='"+block+"'");
       
       // Next
       rs.next();
       
       // Check hash
       if (!UTILS.BASIC.isHash(rs.getString("hash")))
           throw new Exception("Invalid hash - CConsensus.java, 410");
       
       // Update net stat
       UTILS.DB.executeUpdate("UPDATE net_stat "
                               + "SET last_block='"+rs.getLong("block")+"', "
                                    + "last_block_hash='"+rs.getString("hash")+"'");
       
       UTILS.NET_STAT.last_block=rs.getLong("block");
       UTILS.NET_STAT.last_block_hash=rs.getString("hash");
       
       // Reload addresses
       UTILS.TABLES.reorganize(block, rs.getString("hash"));
       
       
       // Delete blocks
       UTILS.DB.executeUpdate("DELETE FROM blocks "
                                  + "WHERE block>"+block);
       
       // Load blocks from chain
       if (this.chain!=null)
       {
          for (int a=this.chain.size()-1; a>=0; a--)
          {
              // Load block
              CBlockPacket b=this.loadBlock(this.chain.get(a), 0);
              
              // Commit
              this.commitBlock(b);
          }
       }
       
       // Reorganize event 
       UTILS.BASIC.newEvent("default", 
                            "Reorganisation complete.", 
                            this.block_number);
       
       System.out.println("Done.");
   }
   
   public boolean isCheckPoint(String hash) throws Exception
   {
       // Check hash
       if (!UTILS.BASIC.isHash(hash))
           throw new Exception("Invalid hash - CConsensus.java, 410");
           
       ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM checkpoints "
                                         + "WHERE hash='"+hash+"'");
        
       // Has data
       if (UTILS.DB.hasData(rs))
            return true;
       else
            return false;
   }
   
  
   public void addToPool(CBlockPacket block) throws Exception
   {
       // Check hash
       if (!UTILS.BASIC.isHash(block.hash))
           throw new Exception("Invalid hash - CConsensus.java, 410");
       
       // Load
       ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM blocks_pool "
                                         + "WHERE hash='"+block.hash+"'");
       
        // Has data
       if (UTILS.DB.hasData(rs))
           return;
       
       // Insert
       UTILS.DB.executeUpdate("INSERT INTO blocks_pool "
                                    + "SET hash='"+block.hash+"', "
                                        + "block='"+block.block+"', "
                                        + "tstamp='"+UTILS.BASIC.tstamp()+"'");
       
       // Debug
       System.out.println("Added to pool - "+block.hash);
   }
   
   public boolean inBlockchain(String hash) throws Exception
   {
       // Found
       boolean found=false;
       
       // Check hash
       if (!UTILS.BASIC.isHash(hash))
           throw new Exception("Invalid hash - CConsensus.java, 410");
       
       // Load
       ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM blocks "
                                         + "WHERE hash='"+hash+"'");
        
       // Exist ?
       if (UTILS.DB.hasData(rs))
        found=true;
       
       // Return
       return found;
    }
   
   public boolean inBlockchain(long block) throws Exception
   {
       // Found
       boolean found=false;
       
       // Load
       ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM blocks "
                                         + "WHERE block='"+block+"'");
        
       // Exist ?
       if (UTILS.DB.hasData(rs))
            found=true;
       
       // Return
       return found;
    }
   
   public void addBlock(CBlockPacket block) throws Exception
   {
        // Deserialize payload
        CBlockPayload block_payload=(CBlockPayload) UTILS.SERIAL.deserialize(block.payload);
        
        // Block hash
        if (!UTILS.BASIC.isHash(block.hash))
           throw new Exception("Invalid hash - CConsensus.java, 508");
        
        // Prev hash
        if (!UTILS.BASIC.isHash(block.prev_hash))
           throw new Exception("Invalid hash - CConsensus.java, 512");
        
        // Signer
        if (!UTILS.BASIC.isAdr(block.signer))
           throw new Exception("Invalid hash - CConsensus.java, 516");
        
        // Payload hash
        if (!UTILS.BASIC.isHash(block.payload_hash))
           throw new Exception("Invalid hash - CConsensus.java, 520");
       
        // Insert
        UTILS.DB.executeUpdate("INSERT INTO blocks "
                                    + "SET hash='"+block.hash+"', "
                                        + "block='"+block.block+"', "
        		                + "prev_hash='"+block.prev_hash+"', "
                                        + "signer='"+block.signer+"', "
                                        + "packets='"+block_payload.packets.size()+"', "
                                        + "tstamp='"+block.tstamp+"', "
                                        + "nonce='"+block.nonce+"', "
                                        + "net_dif='"+block.net_dif+"', "
                                        + "payload_hash='"+block.payload_hash+"', "
                                        + "size='"+block.payload.length+"'");
  
        // Remove
        this.removeFromPool(block.hash, block.block);
       
        // Debug
        System.out.println("Added to blockchain - "+block.hash);
   }
   
   public void removeFromPool(String hash, long block) throws Exception
   {
      // Out
       System.out.println("Removing from pool block "+block+", "+hash);
       
       // Hash
       if (!UTILS.BASIC.isHash(hash))
           throw new Exception("Invalid hash - CConsensus.java, 410");
       
       // Remove from pool
       UTILS.DB.executeUpdate("DELETE FROM blocks_pool "
                                  + "WHERE hash='"+hash+"'"); 
   }
   
   public void store(CBlockPacket block) throws Exception
   {
        FileOutputStream fout = new FileOutputStream(new File(UTILS.WRITEDIR+"blocks/"+block.hash+".block"));
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(block);
        oos.close();
   }
   
   public CBlockPacket loadBlock(String hash, long block) throws Exception
   {
       // Finds the block
       File f = new File(UTILS.WRITEDIR+"blocks/"+hash+".block");
	
       if (f.exists())
       {
            // Read image from disk
	    FileInputStream f_in = new FileInputStream(UTILS.WRITEDIR+"blocks/"+hash+".block");

	    // Read object using ObjectInputStream
	    ObjectInputStream obj_in = new ObjectInputStream (f_in);

	    // Read an object
	    CBlockPacket obj = (CBlockPacket)obj_in.readObject();
            
            // Close
            obj_in.close();
				     
	    // Add block
	    return obj;
	}
       else
       {
           // Message
           System.out.println("Could not find block on disk. Removing from pool.");
           
           // Remove
           this.removeFromPool(hash, block);
           
           // Return
           return null;
       }
   }
   
   public void setStatus(String status)
   {
       // Processing ?
       if (status.equals("ID_PROCESSING"))
           System.out.println("");
       
       // Debug
       System.out.println("-------------------------------- "+status+" -----------------------------------------");
       
       // Waitng ?
       if (status.equals("ID_WAITING"))
           System.out.println("");
       
       // Status
       this.status=status;
   }
   
   class RemindTask extends TimerTask 
     {  
       @Override
       public void run()
       {  
           try
           {
               // Delete from pool expired blocks
               UTILS.DB.executeUpdate("DELETE FROM blocks_pool "
                                          + "WHERE tstamp<"+(UTILS.BASIC.tstamp()-600)+" "
                                            + "AND tstamp>0");
               
               
               // Check pool
               ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                                  + "FROM blocks_pool "
                                              + "ORDER BY block ASC"); 
             
               
               // Has data
               if (UTILS.DB.hasData(rs))
               {
                    // Next
                    rs.next();
                    
                    // Block
                    CBlockPacket b=loadBlock(rs.getString("hash"), rs.getLong("block"));
                      
                    // Not null ?
                    if (b!=null)
                    {
                       if (!inBlockchain(b.prev_hash) && 
                           !inPool(b.prev_hash))
                       {
                           // Request missing block
                           CGetBlockPacket packet=new CGetBlockPacket(b.prev_hash);
               
                           // Broadcast
                           UTILS.NETWORK.broadcast(packet);
                       }
                       else blockReceived(b);
                    }
               }
           }
           catch (Exception ex)
           {
               System.out.println(ex.getMessage());
           }
       }
    }
   
   public boolean inPool(String hash) throws Exception
   {
        // Check pool
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM blocks_pool "
                                          + "WHERE hash='"+hash+"'");
        
        // Has data ?
        if (UTILS.DB.hasData(rs))
            return true;
        else 
            return false;
   }
}
