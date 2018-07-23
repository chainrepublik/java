// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.mes;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;


public class CMesPacket extends CBroadcastPacket 
{
  // Serial
  private static final long serialVersionUID = 100L;
   
  public CMesPacket(String fee_adr, 
		    String sender_adr, 
		    String receiver_adr, 
		    String subject, 
		    String mes) throws Exception
  {
      super(fee_adr, "ID_SEND_MES");
      
      
       // Builds the payload class
       CMesPayload dec_payload=new CMesPayload(sender_adr, 
	                                          receiver_adr, 
	                                          subject, 
	                                          mes);
			
	   // Build the payload
	   this.payload=UTILS.SERIAL.serialize(dec_payload);
			
	   // Network fee
	   this.setFee(0.0001, "Send message network fee");
	   
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
          
          // Check fee
          if (this.fee<0.0001)
	       throw new Exception("Invalid fee - CRemoveExOffertPacket.java");
          
          // Footprint
          CPackets foot=new CPackets(this);
          foot.add("Source", dec_payload.target_adr);
          foot.add("Destination", dec_payload.receiver_adr);
          foot.write();
     
  }

}
