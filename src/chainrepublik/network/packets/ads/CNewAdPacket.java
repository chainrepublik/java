// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.ads;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;


public class CNewAdPacket extends CBroadcastPacket 
{
   public CNewAdPacket(String fee_adr, 
		       String adr, 
		       String title, 
		       String mes, 
		       String link,
                       long hours, 
		       double bid) throws Exception
   {
	   // Super class
	   super(fee_adr, "ID_NEW_AD_PACKET");
	   
	   // Builds the payload class
	   CNewAdPayload dec_payload=new CNewAdPayload(adr, 
			                               title, 
			                               mes, 
			                               link,
                                                       hours, 
			                               bid);
			
	   // Build the payload
	   this.payload=UTILS.SERIAL.serialize(dec_payload);
			
	   // Network fee
	  this.setFee(bid*hours, "Post ad network fee");
	   
	   // Sign packet
           this.sign();
   }
   
   // Check 
   public void check(CBlockPayload block) throws Exception
   {
          // Super class
   	  super.check(block);
   	  
   	  // Check type
   	  if (!this.tip.equals("ID_NEW_AD_PACKET")) 
             throw new Exception("Invalid packet type - CNewAdPacket.java");
   	  
          // Deserialize transaction data
   	  CNewAdPayload dec_payload=(CNewAdPayload) UTILS.SERIAL.deserialize(payload);
          
          // Check fee
	  if (this.fee<dec_payload.hours*0.0001)
	      throw new Exception("Invalid fee - CNewAdPacket.java");
          
          // Check payload
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
                  
          foot.add("Address", dec_payload.target_adr);
          foot.add("Hours", String.valueOf(dec_payload.hours));
          foot.add("Price", String.valueOf(dec_payload.bid));
          foot.add("Title", dec_payload.title);
          foot.add("Message", dec_payload.mes);
          foot.add("Link", dec_payload.link);
          foot.write();
   	  
   }
   
   
}
