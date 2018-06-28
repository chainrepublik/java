package chainrepublik.network.packets.misc;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;
import chainrepublik.network.packets.trans.CFeePayload;

public class CDelVotePacket extends CBroadcastPacket
{
     // Serial
   private static final long serialVersionUID = 100L;
   
    public CDelVotePacket(String fee_adr,
                          String adr, 
                          String delegate,
                          String type) throws Exception
    {
        super(fee_adr, "ID_VOTE_DEL_PACKET");
       
        // Builds the payload class
        CDelVotePayload dec_payload=new CDelVotePayload(adr, 
                                                        delegate,
                                                        type);
						
        // Build the payload
        this.payload=UTILS.SERIAL.serialize(dec_payload);
          
         
	// Network fee
        this.setFee(0.0001, "Delegate vote network fee");
       
        // Sign packet
        this.sign();
   }
			 
   // Check 
   public void check(CBlockPayload block) throws Exception
   {
	// Super class
        super.check(block);
   	  
        // Check type
   	if (!this.tip.equals("ID_VOTE_DEL_PACKET")) 
             throw new Exception("Invalid packet type - CRenewPacket.java");
   	  
        // Deserialize transaction data
   	CDelVotePayload dec_payload=(CDelVotePayload) UTILS.SERIAL.deserialize(payload);
        
        // Check fee
	if (this.fee<0.0001)
	   throw new Exception("Invalid fee - CRenewPacket.java");
          
        // Check payload
        dec_payload.check(block);
          
        // Footprint
        CPackets foot=new CPackets(this);
        foot.add("Delegate", dec_payload.delegate);
        foot.add("Address", dec_payload.target_adr);
        foot.add("Type", dec_payload.type);
        foot.write();
   }
}
