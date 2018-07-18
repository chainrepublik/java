// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.misc;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CRenewPacket extends CBroadcastPacket
{
    // Serial
   private static final long serialVersionUID = 100L;
   
    public CRenewPacket(String fee_adr, 
                        String adr, 
                        String target_type, 
                        long targetID, 
                        long days) throws Exception
    {
       super(fee_adr, "ID_RENEW_PACKET");
       
      // Builds the payload class
          CRenewPayload dec_payload=new CRenewPayload(adr, 
                                                      target_type,
                                                      targetID,
                                                      days);
						
          // Build the payload
          this.payload=UTILS.SERIAL.serialize(dec_payload);
          
         
	  // Network fee
	  this.setFee(0.0001*days, "Renew network fee");
       
       
       // Sign packet
       this.sign();
   }
			 
   // Check 
   public void check(CBlockPayload block) throws Exception
   {
	// Super class
        super.check(block);
   	  
        // Check type
   	if (!this.tip.equals("ID_RENEW_PACKET")) 
             throw new Exception("Invalid packet type - CRenewPacket.java");
   	  
        // Deserialize transaction data
   	CRenewPayload dec_payload=(CRenewPayload) UTILS.SERIAL.deserialize(payload);
        
        // Check fee
	if (this.fee<dec_payload.days*0.0001)
	   throw new Exception("Invalid fee - CRenewPacket.java");
          
        // Check payload
        dec_payload.check(block);
          
        // Footprint
        CPackets foot=new CPackets(this);
        foot.add("Address", dec_payload.target_adr);
        foot.add("Target type", dec_payload.target_type);
        foot.add("Target ID", dec_payload.targetID);
        foot.add("Days", dec_payload.days);
        foot.write();
   }
			 
}
