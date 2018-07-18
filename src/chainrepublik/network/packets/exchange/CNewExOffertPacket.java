package chainrepublik.network.packets.exchange;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;
import chainrepublik.network.packets.mes.CMesPayload;

public class CNewExOffertPacket extends CBroadcastPacket
{
  public CNewExOffertPacket(String fee_adr, 
		            String adr, 
		            String side,
                            String price_type,
                            long margin,
                            double price,
                            double min,
                            double max,
                            String method,
                            String details,
                            String pay_info,
                            String contact,
                            long days) throws Exception
  {
    super(fee_adr, "ID_NEW_EX_OFFER_PACKET");
      
    // Builds the payload class
    CNewExOffertPayload dec_payload=new CNewExOffertPayload(adr, 
		                                            side,
                                                            price_type,
                                                            margin,
                                                            price,
                                                            min,
                                                            max,
                                                            method,
                                                            details,
                                                            pay_info,
                                                            contact,
                                                            days);
			
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
			
	// Network fee
	this.setFee(0.0001*days, "Add new exchange order network fee");
	   
	// Sign packet
	this.sign();
  }
  
  // Check 
  public void check(CBlockPayload block) throws Exception
  {
    // Super class
    super.check(block);
  	  
    // Check type
    if (!this.tip.equals("ID_NEW_EX_OFFER_PACKET")) 
  	throw new Exception("Invalid packet type - CNewExOffertPacket.java");
  	  
    // Check
    CNewExOffertPayload dec_payload=(CNewExOffertPayload) UTILS.SERIAL.deserialize(payload);
    dec_payload.check(block);
    
    // Check fee
    if (this.fee<0.0001*dec_payload.days)
	throw new Exception("Invalid fee - CNewExOffertPacket.java");
          
    // Footprint
    CPackets foot=new CPackets(this);
    foot.add("Market Side", dec_payload.side);
    foot.add("Price Type", dec_payload.price_type);
    foot.add("Margin", dec_payload.margin);
    foot.add("Price", dec_payload.price);
    foot.add("Min Order Size", dec_payload.min);
    foot.add("Max Order Size", dec_payload.max);
    foot.add("Method", dec_payload.method);
    foot.add("Order Details", dec_payload.details);
    foot.add("Payment Info", dec_payload.pay_info);
    foot.add("Contact Details", dec_payload.details);
    foot.add("Days", dec_payload.days);
    foot.add("Block", dec_payload.block);
    foot.write();
  }

}
