package chainrepublik.network.packets.market;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;
import chainrepublik.network.packets.trans.CFeePayload;

public class CRentPacket extends CBroadcastPacket
{
  public CRentPacket(String fee_adr,
                     String adr,
                     long itemID,
                     long days) throws Exception
    {
        // Constructor
        super(fee_adr,"ID_RENT_ITEM_PACKET");
        
        // Builds the payload class
	CRentPayload dec_payload=new CRentPayload(adr, 
                                                  itemID, 
                                                  days);
                              
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
					
        // Network fee
	this.setFee(0.0001, "Rent item network fee");
			   
	// Sign packet
	this.sign();
    }
    
    // Check 
    public void check(CBlockPayload block) throws Exception
    {
	  // Super class
   	  super.check(block);
   	  
   	  // Check type
   	  if (!this.tip.equals("ID_RENT_ITEM_PACKET")) 
             throw new Exception("Invalid packet type - CRentPacket.java");
   	  
          // Deserialize transaction data
   	  CRentPayload dec_payload=(CRentPayload) UTILS.SERIAL.deserialize(payload);
          
          // Check fee
	  if (this.fee<0.0001)
	      throw new Exception("Invalid fee - CRentPacket.java");
          
          // Check payload
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
          foot.add("Address", dec_payload.target_adr);
          foot.add("Item ID", dec_payload.itemID);
          foot.add("Days", dec_payload.days);
          foot.write();
    }
}