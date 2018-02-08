package chainrepublik.agents.VM.SYSTEM;

import chainrepublik.network.packets.blocks.CBlockPayload;

public class CBlockData 
{
   // Block
   public long BLOCK;
   
   // Block hash
   public String HASH;
        
   // Prev hash
   public String PREV_HASH;
        
   // Prev hash
   public String PAYLOAD_HASH;
        
    // Signer
    public String SIGNER;
        
     // Sign
    public String SIGN;
        
    // Timestamp
    public long TSTAMP;
        
    // Nonce
    public long NONCE;
        
    // Dificulty
    public String NET_DIF;
    
    // Block Payload
    public CBlockPayload BLOCK_PAYLOAD;
    
    public CBlockData(long block, 
                      String hash,
                      String prev_hash, 
                      String payload_hash, 
                      String signer, 
                      String sign, 
                      long tstamp, 
                      long nonce, 
                      String net_dif,
                      CBlockPayload block_payload)
    {
        // Block
        this.BLOCK=block;
        
        // Hash
        this.HASH=hash;
        
        // Prev hash
        this.PREV_HASH=prev_hash;
        
        // Prev hash
        this.PAYLOAD_HASH=payload_hash;
        
        // Signer
        this.SIGNER=signer;
        
        // Sign
        this.SIGN=sign;
        
        // Timestamp
        this.TSTAMP=tstamp;
        
        // Nonce
        this.NONCE=nonce;
        
        // Dificulty
        this.NET_DIF=NET_DIF;
        
        // Block payload
        this.BLOCK_PAYLOAD=block_payload;
    }
    
  
}
