// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.blocks;
import java.sql.ResultSet;
import chainrepublik.network.packets.*;
import chainrepublik.kernel.*;


public class CBlockPacket extends CPacket 
{
        // Bloc
        public long block;
        
        // Prev hash
        public String prev_hash;
        
        // Prev hash
        public String payload_hash;
        
	// Signer
        public String signer;
        
        // Timestamp
        public long tstamp;
        
        // Nonce
        public long nonce;
        
        // Dificulty
        public String net_dif;
        
        // Serial
        private static final long serialVersionUID = 100L;
        
	public CBlockPacket(String signer) throws Exception
        {
	   // Constructor
	   super("ID_BLOCK");
           
           // Signer
           this.signer=signer;
              
           // Block
           this.block=UTILS.NET_STAT.last_block+1;
           
           // Tstamp
           this.tstamp=UTILS.BASIC.tstamp();
           
           // Prev hash
           this.prev_hash=UTILS.NET_STAT.last_block_hash;
           
           // Dificulty
           this.net_dif=UTILS.BASIC.formatDif(UTILS.NET_STAT.net_dif.toString(16));
        }
	
        // Sign
	public void sign(String hash) throws Exception
        { 
            // Hash
            this.hash=hash;
            
            // Signature address
            CAddress adr=UTILS.WALLET.getAddress(this.signer);
	    this.sign=adr.sign(hash);
        }
        
        public boolean preCheck() throws Exception
        {
            // Check signature
            CAddress ecc=new CAddress(this.signer);
            if (!ecc.checkSig(hash, this.sign))
            {
                System.out.println("Invalid block signature");
		return false;
            }
            
            // Payload hash
            if (!UTILS.BASIC.isHash(payload_hash))
            {
                System.out.println("Invalid payload signature");
                return false;
            }
            
            String h=UTILS.BASIC.hash(this.payload);
            if (!this.payload_hash.equals(h))
            {
                System.out.println("Invalid payload hash");
                return false;
            }
            
            // More than 25 blocks back ? 
            if (this.block<UTILS.NET_STAT.last_block-25)
            {
                System.out.println("Invalid block number");
                return false;
            }
            
             // Prev hash
             if (!UTILS.BASIC.isHash(prev_hash))
             {
                System.out.println("Invalid prev hash");
                return false;
             }
             
	      // Signer
              if (!UTILS.BASIC.isAdr(this.signer))
              {
                  System.out.println("Invalid signer");
                  return false;
              }
              
              // Net dif
              if (!UTILS.BASIC.isHash(this.net_dif))
              {
                 System.out.println("Invalid net dif");
                 return false;
              }
              
              // Load parent
              ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                                 + "FROM blocks "
                                                + "WHERE hash='"+prev_hash+"'");
              
              // Has data ?
              if (UTILS.DB.hasData(rs))
              {
                  // Next
                  rs.next();
              
                  // Check
                  if (rs.getLong("block")!=(this.block-1))
                  {
                      System.out.println("Prev hash not found");
                      return false;
                  }
                  
                  // Timestamp ?
                  if (this.tstamp<rs.getLong("tstamp"))
                     throw new Exception("Invalid timestamp");
              }
              
              // Hash
              if (!UTILS.MINER_UTILS.checkHash(this.prev_hash, 
                                               this.block, 
                                               this.payload_hash, 
                                               this.signer, 
                                               this.tstamp, 
                                               this.nonce,
                                               this.hash,
                                               this.net_dif))
              {
                 System.out.println("Invalid POW");
                 return false;
              }
              
            // Payload size
            if (this.payload.length>100000)
            {
                System.out.println("Invalid payload length - "+this.payload.length);
                return false;
            }
            
            // Return
            return true;
        }
        
	// Check 
	public void check() throws Exception
	{
            // Precheck
            if (!this.preCheck())
                throw new Exception("Invalid block - CBlockPacket.java");
                    
             // Check type
	     if (!this.tip.equals("ID_BLOCK")) 
	   	throw new Exception("Invalid packet type - CBlockPacket.java");
             
	     // Deserialize transaction data
	     CBlockPayload block_payload=(CBlockPayload) UTILS.SERIAL.deserialize(payload);
	     
             // Super class
	     super.check(block_payload);
	     
             // Check
             block_payload.check();
	   	
              // Check signature
	      CAddress ecc=new CAddress(this.signer);
	      if (!ecc.checkSig(hash, this.sign))
                   throw new Exception("Invalid signature - CBlockPacket.java");
        }
	   
	public void commit()  throws Exception
	{
                System.out.println("Commiting block "+this.block+" ("+this.hash+")");
                
                // Set block data
                UTILS.NET_STAT.actual_block_hash=this.hash;
                UTILS.NET_STAT.actual_block_no=this.block;
                
                // Delete
                UTILS.DB.executeUpdate("DELETE FROM trans WHERE block_hash='' OR block_hash='"+this.hash+"'");
                        
                // Deserialize transaction data
	   	        CBlockPayload block_payload=(CBlockPayload) UTILS.SERIAL.deserialize(payload);
	   	
	   	       // Superclass
	           super.commit(block_payload);
                
	   	       // Commit payload
	           block_payload.commit();
	   	 
                // After block
                this.afterBlock(block);
                
                // New block
                UTILS.CBLOCK.newBlock(this.block, 
                                      this.prev_hash, 
                                      this.hash, 
                                      this.tstamp);
                
                // Update confirmations
                this.updateConfirms();
        }
        
        
   public void afterBlock(long block) throws Exception
   {
       // Trans pool
       UTILS.NETWORK.TRANS_POOL.newBlock(this.block);
       
       // Reward
       UTILS.REWARD.reward(block);
                
       // Adr fee
       this.adrFee(block);
       
       // Pay block reward
       this.payReward(this.signer);
       
       // Delegates
       UTILS.DELEGATES.refresh(block);
       
       // Travelers
       UTILS.CRONS.runBlockCrons(block);
       
       // Cleanup
       UTILS.TABLES.expired(block);
        
       // Refresh
       UTILS.TABLES.refresh(block, this.hash);
   }
   
   public void payReward(String adr) throws Exception
   {
        // Reward
        double pool=UTILS.BASIC.getRewardPool("ID_MINERS");
        
        // Reward
        double reward=pool/1440;
        
        // Insert trans
        UTILS.ACC.newTransfer("default", 
                              adr,
                              reward, 
                              "CRC", 
                              "Block reward", 
                              "", 
                              0,
                              hash, 
                              this.block,
                              false,
                              "",
                              "");
        
        // Clear
        UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
        
        // Update reward
        UTILS.DB.executeUpdate("UPDATE blocks "
                                + "SET reward='"+reward+"' "
                              + "WHERE hash='"+this.hash+"'");
    }
   
   public void updateConfirms() throws Exception
   {
       // Confirms
       long confirms=0;
       
       // Block
       long block_no=1;
       
       // Prev hash
       String prev_hash=UTILS.NET_STAT.last_block_hash;
       
       while (confirms<25 && block_no>0)
       {
           // Confirms
           confirms++;
           
           // Load blocks
           ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                              + "FROM blocks "
                                             + "WHERE hash='"+prev_hash+"'");
           
           // Next 
           rs.next();
           
           // Block no
           block_no=rs.getLong("block");
       
           // Update confirmations for blocks
           UTILS.DB.executeUpdate("UPDATE blocks "
                                   + "SET confirmations='"+confirms+"' "
                                 + "WHERE hash='"+rs.getString("hash")+"'");
       
           // New hash
           prev_hash=rs.getString("prev_hash");
       }
   }
   
   
   public void adrFee(long block) throws Exception
   {
       // 100th block
       if (block%1440!=0) return;
       
        // Load data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM adr "
                                          + "WHERE balance<1 "
                                            + "AND balance>=0.0001 "
                                            + "AND expires<"+block);
        
        // Loop
        while (rs.next())
            UTILS.ACC.newTransfer(rs.getString("adr"), 
                                   "default", 
                                   0.0001, 
                                   "CRC", 
                                   "Daily network fee", 
                                   "", 
                                   0, 
                                   UTILS.BASIC.hash(String.valueOf(block)), 
                                   block, 
                                   false, 
                                   "", 
                                   "");
        
        // Clear
        UTILS.ACC.clearTrans(UTILS.BASIC.hash(String.valueOf(block)), "ID_ALL", block);
   }
}