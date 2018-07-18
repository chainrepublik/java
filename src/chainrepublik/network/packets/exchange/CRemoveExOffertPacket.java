package chainrepublik.network.packets.exchange;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;
import chainrepublik.network.packets.mes.CMesPayload;

public class CRemoveExOffertPacket extends CBroadcastPacket
{
  public CRemoveExOffertPacket(String fee_adr, 
		               String adr, 
		               long exID) throws Exception
  {
        super(fee_adr, "ID_REMOVE_EX_OFFER_PACKET");
      
        // Builds the payload class
        CRemoveExOffertPayload dec_payload=new CRemoveExOffertPayload(adr, 
	                                                             exID);
			
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
			
	// Network fee
	this.setFee(0.0001, "Remove offer network fee");
	   
	// Sign packet
	this.sign();
  }
  
  // Check 
  public void check(CBlockPayload block) throws Exception
  {
    // Super class
    super.check(block);
  	  
    // Check type
    if (!this.tip.equals("ID_REMOVE_EX_OFFER_PACKET")) 
  	throw new Exception("Invalid packet type - CMesPacket.java");
  	  
    // Check
    CRemoveExOffertPayload dec_payload=(CRemoveExOffertPayload) UTILS.SERIAL.deserialize(payload);
    dec_payload.check(block);
    
    // Check fee
    if (this.fee<0.0001)
	throw new Exception("Invalid fee - CRemoveExOffertPacket.java");
          
    // Footprint
    CPackets foot=new CPackets(this);
    foot.add("Offert ID", dec_payload.exID);
    foot.write();
  }

}
