// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.press;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;


public class CFollowPacket extends CBroadcastPacket 
{
   // Serial
   private static final long serialVersionUID = 1L;
   
   public CFollowPacket(String fee_adr,
                        String adr, 
		        String follow_adr,
                        long days) throws Exception
   {
	   // Super class
	   super(fee_adr, "ID_FOLLOW_PACKET");
	   
	   // Builds the payload class
	   CFollowPayload dec_payload=new CFollowPayload(adr, 
		                                         follow_adr,
                                                         days);
			
	   // Build the payload
	   this.payload=UTILS.SERIAL.serialize(dec_payload);
			
	   // Network fee
           this.setFee(0.0001*days, "Follow network fee");
	   
	   // Sign packet
           this.sign();
   }
   
   // Check 
   public void check(CBlockPayload block) throws Exception
   {
          // Super class
   	  super.check(block);
   
   	  // Check type
   	  if (!this.tip.equals("ID_FOLLOW_PACKET")) 
             throw new Exception("Invalid packet type - CFollowPacket.java");
   	  
   	  // Deserialize transaction data
   	  CFollowPayload dec_payload=(CFollowPayload) UTILS.SERIAL.deserialize(payload);
          
          // Check fee
	  if (this.fee<0.0001*dec_payload.days)
	      throw new Exception("Invalid fee - CFollowPacket.java");
          
          // Check payload
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
          foot.add("Address", dec_payload.target_adr);
          foot.add("Follows", dec_payload.follow_adr);
          foot.add("Days", dec_payload.days);
          foot.write();
   }
   
     
}
