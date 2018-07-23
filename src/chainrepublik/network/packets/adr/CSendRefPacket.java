package chainrepublik.network.packets.adr;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;
import chainrepublik.network.packets.exchange.CRemoveExOffertPayload;

public class CSendRefPacket extends CBroadcastPacket
{
    // Serial
    private static final long serialVersionUID = 100L;
    
    public CSendRefPacket(String fee_adr, 
		        String adr, 
		        String rec,
                        String ref) throws Exception
    {
        super(fee_adr, "ID_SEND_REF_PACKET");
      
        // Builds the payload class
        CSendRefPayload dec_payload=new CSendRefPayload(adr, 
	                                                rec,
                                                        ref);
			
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
			
	// Network fee
	this.setFee(0.0001, "Send afiliate network fee");
	   
	// Sign packet
	this.sign();
    }
  
    // Check 
    public void check(CBlockPayload block) throws Exception
    {
      // Super class
      super.check(block);
  	  
      // Check type
      if (!this.tip.equals("ID_SEND_REF_PACKET")) 
  	  throw new Exception("Invalid packet type - CSendRefPacket.java");
  	  
      // Check
      CSendRefPayload dec_payload=(CSendRefPayload) UTILS.SERIAL.deserialize(payload);
      dec_payload.check(block);
    
      // Check fee
      if (this.fee<0.0001)
	  throw new Exception("Invalid fee - CSendRefPacket.java");
           
      // Footprint
      CPackets foot=new CPackets(this);
      foot.add("Receiver", dec_payload.rec);
      foot.add("Affiliate", dec_payload.ref);
      foot.write();
    }

}
