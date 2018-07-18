// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.press;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;


public class CCommentPacket extends CBroadcastPacket 
{
   // Serial
   private static final long serialVersionUID = 1L;
   
   public CCommentPacket(String fee_adr,
                          String adr, 
		          String parent_type,
                          long parentID,
		          String mes) throws Exception
   {
	   // Super class
	   super(fee_adr, "ID_COMMENT_PACKET");
	   
	   // Builds the payload class
	   CCommentPayload dec_payload=new CCommentPayload(adr, 
		                                             parent_type, 
                                                             parentID,
		                                             mes);
			
	   // Build the payload
	   this.payload=UTILS.SERIAL.serialize(dec_payload);
			
	   // Network fee
           this.setFee(0.0001, "Post comment network fee");
	   
	   // Sign packet
           this.sign();
   }
   
   // Check 
   public void check(CBlockPayload block) throws Exception
   {
        // Super class
   	super.check(block);
   	  
   	// Check type
   	if (!this.tip.equals("ID_COMMENT_PACKET")) 
   		throw new Exception("Invalid packet type - CTweetMesPacket.java");
   	  
   	// Deserialize transaction data
   	CCommentPayload dec_payload=(CCommentPayload) UTILS.SERIAL.deserialize(payload);
          
        // Check fee
        if (this.fee<0.0001)
	    throw new Exception("Invalid fee - CTweetMesPacket.java"); 
          
        // Check payload
        dec_payload.check(block);
          
        // Footprint
        CPackets foot=new CPackets(this);
        foot.add("Address", dec_payload.target_adr);
        foot.add("Parent Type", dec_payload.parent_type);
        foot.add("Parent ID", String.valueOf(dec_payload.parentID));
        foot.add("Comment ID", String.valueOf(dec_payload.comID));
        foot.add("Mes", dec_payload.mes);
        foot.write();
   	 
   }
   
  
}
