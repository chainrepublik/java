// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.mes;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;
import chainrepublik.network.packets.trans.CFeePayload;


public class CMesPacket extends CBroadcastPacket 
{
    // Serial
   private static final long serialVersionUID = 100L;
   
  public CMesPacket(String fee_adr, 
		    String sender_adr, 
		    String receiver_adr, 
		    String subject, 
		    String mes,
                    String packet_sign,
                    String payload_sign) throws Exception
  {
      super("ID_SEND_MES");
      
      
       // Builds the payload class
	  CMesPayload dec_payload=new CMesPayload(sender_adr, 
	                                          receiver_adr, 
	                                          subject, 
	                                          mes);
			
	   // Build the payload
	   this.payload=UTILS.SERIAL.serialize(dec_payload);
			
	   // Network fee
	   CFeePayload fee=new CFeePayload(fee_adr,  0.0001, "Send message network fee");
	   this.fee_payload=UTILS.SERIAL.serialize(fee);
	   
	   // Sign packet
	   this.sign();
     
  }
  
  // Check 
  public void check(CBlockPayload block) throws Exception
  {
      // Super class
  	  super.check(block);
  	  
  	  // Check type
  	  if (!this.tip.equals("ID_SEND_MES")) 
  		throw new Exception("Invalid packet type - CMesPacket.java");
  	  
  	   // Check
  	  CMesPayload dec_payload=(CMesPayload) UTILS.SERIAL.deserialize(payload);
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
                  
          foot.add("Source", dec_payload.target_adr);
          foot.add("Destination", dec_payload.receiver_adr);
          foot.write();
     
  }

}
