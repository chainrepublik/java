// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets;

import chainrepublik.kernel.CAddress;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CBroadcastPacket extends CPacket 
{	
	 // Block
	 public long block;
         
         // Address
         public String adr;
         
         // Fee
         public double fee;
         
         // Fee expl
         public String fee_expl;
	   
	public CBroadcastPacket(String adr, String tip)  throws Exception
	{
	    // Constructor
	    super(tip);
            
            // Address
            this.adr=adr;
	    
            // Block
            this.block=UTILS.NET_STAT.last_block+1;
	}
	
        public void setFee(double fee, String expl) throws Exception
        {
            // Set fee
            this.fee=fee;
            
            // Expl
            this.fee_expl=expl;
        }
        
        public void checkFee(CBlockPayload block) throws Exception
        {
            // Min fee
            if (this.fee<0.0001)
               throw new Exception("Invalid fee, CBroadcastPAcket.java, line 62");
                
            // Balance
            if (UTILS.ACC.getBalance(this.adr, "CRC", block)<this.fee)
                throw new Exception("Insuficient funds to pay the network fee, CBroadcastPAcket.java, line 62");
            
             // New trans
            UTILS.ACC.newTransfer(this.adr, 
                                  "default", 
                                  fee, 
                                  "CRC", 
                                  this.fee_expl, 
                                  "", 
                                  0, 
                                  UTILS.BASIC.hash(UTILS.BASIC.hash(String.valueOf(this.block))), 
                                  this.block, 
                                  false, 
                                  tip, 
                                  tip);
        }
        
	public void check(CBlockPayload block) throws Exception
	{
            // Parent check
            super.check(block);
            
            // Payload size
            if (this.payload.length>100000)
               throw new Exception("Invalid payload size");
            
             // Check hash
             String h=UTILS.BASIC.hash(this.tip+
                                       this.tstamp+
                                       String.valueOf(this.block)+
				       UTILS.BASIC.hash(this.payload));
	     
             if (!h.equals(this.hash))
	        throw new Exception("Invalid hash - CBroadcastPacket.java");
	    
             // Block number
             if (block!=null)
             {
               if (this.block!=block.block)
                 throw new Exception("Invalid block number - CBroadcastPacket.java");
             }
             else
             {
                // For actual block ?
                if (this.block!=(UTILS.NET_STAT.last_block+1))
                    throw new Exception("Invalid block number - CBroadcastPacket.java");
             }
             
             // Check duplicates
             if (block!=null)
             {
                 // Packet hash
                 long no_packet_hashes=0;
                 String packet_hash=this.hash;
                 
                 // Payload hash
                 long no_payload_hashes=0;
                 String payload_hash=UTILS.BASIC.hash(this.payload);
                 
                 // Parse packets
                 for (int a=0; a<=block.packets.size()-1; a++)
                 {
                     // Load packet
                     CBroadcastPacket packet=block.packets.get(a);
                     
                     // Packet hash
                     if (packet_hash.equals(packet.hash))
                         no_packet_hashes++;
                     
                      
                     // Payload hash
                     if (payload_hash.equals(UTILS.BASIC.hash(packet.payload)))
                         no_payload_hashes++;
                 }
                 
                // check duplicates
                if (no_packet_hashes>1 || 
                     no_payload_hashes>1)
                {
                   System.out.println(no_packet_hashes+", "+no_payload_hashes+", "+this.hash);
                   throw new Exception("Duplicate found - CBroadcastPacket.java");
                }
             }
             
            // Check fee ?
            this.checkFee(block);

            // Payload block
            CPayload payload=(CPayload) UTILS.SERIAL.deserialize(this.payload);
            if (payload.block!=this.block)
                throw new Exception("Invalid payload block - CBroadcastPacket.java"); 
            
            // Address
            if (!this.tip.equals("ID_ADR_REGISTER_PACKET") && 
                !this.tip.equals("ID_NEW_COMPANY_PACKET") &&
                !this.tip.equals("ID_NEW_REG_ASSET_MARKET_POS_PACKET"))
                if (!payload.target_adr.equals(this.adr))
                   throw new Exception("Invalid payload address - CBroadcastPacket.java");
            
	    // Check signature
	    if (this.checkSign()==false)
		throw new Exception("Invalid signature - CBroadcastPacket.java");	
	}
	
	
	public void commit(CBlockPayload block) throws Exception
	{
            // Deserialize transaction data
            CPayload dec_payload=(CPayload) UTILS.SERIAL.deserialize(payload);
            
            try
            {
                // Check
                this.check(block);
                
                // Commit fee
	        if (!this.tip.equals("ID_BLOCK"))
                    UTILS.ACC.clearTrans(UTILS.BASIC.hash(UTILS.BASIC.hash(String.valueOf(this.block))), 
                                         "ID_ALL", 
                                         this.block);
            }
            catch (Exception ex)
            {
               System.out.println(ex.getMessage());
               return;
            }
            
            try
            {
                // Commit
	        dec_payload.commit(block);
                
                // Commited
                this.commited(UTILS.NET_STAT.actual_block_hash, 
                              UTILS.NET_STAT.actual_block_no, 
                              this.hash, 
                              dec_payload.hash);
            }
            catch (Exception ex)
            {
               System.out.println(ex.getMessage());
            }
	}
	
	public void sign() throws Exception
	{
            // Can sign ?
            if (!UTILS.WALLET.isMine(this.adr))
                throw new Exception("We don't own the private key of this address ("+this.adr+"), CBroadcastPacket.java, line 224");
            
            // Packet hash
            this.hash=UTILS.BASIC.hash(this.tip+
                                       this.tstamp+
				       this.block+
				       UTILS.BASIC.hash(this.payload));
            
            // Signature address
            if (UTILS.WALLET.isMine(this.adr))
            {
               CAddress adr=UTILS.WALLET.getAddress(this.adr);
	       this.sign=adr.sign(this.hash);
            }
        }
	
	public void setSign(String sign)
        {
            this.sign=sign;
        }
        
	public boolean checkSign() throws Exception
	{
            CAddress ecc=new CAddress(this.adr);
	    return (ecc.checkSig(hash, this.sign));
	}
        
        // Commited
        public void commited(String block_hash, 
                             long block_no, 
                             String packet_hash, 
                             String payload_hash) throws Exception
        {
            // Block hash
            if (!UTILS.BASIC.isHash(block_hash))
                throw new Exception("Invalid block hash - CBroadcastPacket.java, 216");
            
            // Packet hash
            if (!UTILS.BASIC.isHash(packet_hash))
                throw new Exception("Invalid packet hash - CBroadcastPacket.java, 219");
            
            // Payload hash
            if (!UTILS.BASIC.isHash(payload_hash))
                throw new Exception("Invalid payload hash - CBroadcastPacket.java, 224");
            
             
            // Update
            UTILS.DB.executeUpdate("UPDATE packets "
                                    + "SET block_hash='"+block_hash+"', "
                                        + "block='"+block_no+"' "
                                  + "WHERE packet_hash='"+packet_hash+"' "
                                     + "OR payload_hash='"+payload_hash+"'");
        }
	
	
}
