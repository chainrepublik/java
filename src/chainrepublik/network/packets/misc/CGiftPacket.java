package chainrepublik.network.packets.misc;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CGiftPacket extends CBroadcastPacket
{
     // Serial
     private static final long serialVersionUID = 100L;
   
    public CGiftPacket(String fee_adr,
                       String adr, 
                       String target_adr) throws Exception
    {
        super(fee_adr, "ID_GIFT_PACKET");
       
        // Builds the payload class
        CGiftPayload dec_payload=new CGiftPayload(adr, 
                                                  target_adr);
						
        // Build the payload
        this.payload=UTILS.SERIAL.serialize(dec_payload);
          
         
	// Network fee
        this.setFee(0.0001, "Gift network fee");
       
        // Sign packet
        this.sign();
   }
			 
   // Check 
   public void check(CBlockPayload block) throws Exception
   {
	// Super class
        super.check(block);
   	  
        // Check type
   	if (!this.tip.equals("ID_GIFT_PACKET")) 
             throw new Exception("Invalid packet type - CGiftPacket.java");
   	  
        // Deserialize transaction data
   	CGiftPayload dec_payload=(CGiftPayload) UTILS.SERIAL.deserialize(payload);
        
        // Check fee
	if (this.fee<0.0001)
	   throw new Exception("Invalid fee - CRenewPacket.java");
          
        // Check payload
        dec_payload.check(block);
          
        // Footprint
        CPackets foot=new CPackets(this);
        foot.add("Address", dec_payload.target_adr);
        foot.add("Target Address", dec_payload.adr);
        foot.write();
   }
}
