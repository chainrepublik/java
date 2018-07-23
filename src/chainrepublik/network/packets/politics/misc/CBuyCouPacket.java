package chainrepublik.network.packets.politics.misc;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;
import chainrepublik.network.packets.portofolio.CConsumeItemPayload;

public class CBuyCouPacket extends CBroadcastPacket
{
 public CBuyCouPacket(String fee_adr,
                      String adr,
                      String cou) throws Exception
    {
        // Constructor
        super(fee_adr, "ID_BUY_COUNTRY_PACKET");
        
        // Builds the payload class
	CBuyCouPayload dec_payload=new CBuyCouPayload(adr, 
                                                      cou);
                              
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
					
        // Network fee
	this.setFee(0.0001, "Buy a private country");
			   
	// Sign packet
	this.sign();
    }
    
    // Check 
    public void check(CBlockPayload block) throws Exception
    {
	  // Super class
   	  super.check(block);
   	  
   	  // Check type
   	  if (!this.tip.equals("ID_BUY_COUNTRY_PACKET")) 
             throw new Exception("Invalid packet type - CConsumeItemPacket.java");
   	  
          // Deserialize transaction data
   	  CBuyCouPayload dec_payload=(CBuyCouPayload) UTILS.SERIAL.deserialize(payload);
          
          // Check fee
	  if (this.fee<0.0001)
	      throw new Exception("Invalid fee - CConsumeItemPacket.java");
          
          // Check payload
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
          foot.add("Address", dec_payload.target_adr);
          foot.add("Cou", dec_payload.cou);
          foot.write();
    }
}