package chainrepublik.network.packets.portofolio;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CSetRentPricePacket extends CBroadcastPacket
{
    // Serial
    private static final long serialVersionUID = 100L;
    
    public CSetRentPricePacket(String fee_adr,
                             String adr,
                             long itemID, 
                             double price) throws Exception
    {
        // Constructor
        super(fee_adr, "ID_SET_RENT_PRICE_PACKET");
        
        // Builds the payload class
	CSetRentPricePayload dec_payload=new CSetRentPricePayload(adr, 
                                                                  itemID, 
                                                                  price);
                              
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
					
        // Network fee
	this.setFee(0.0001, "Set rent price network fee");
			   
	// Sign packet
	this.sign();
    }
    
    // Check 
    public void check(CBlockPayload block) throws Exception
    {
	  // Super class
   	  super.check(block);
   	  
   	  // Check type
   	  if (!this.tip.equals("ID_SET_RENT_PRICE_PACKET")) 
             throw new Exception("Invalid packet type - CRentPacket.java");
   	  
          // Deserialize transaction data
   	  CSetRentPricePayload dec_payload=(CSetRentPricePayload) UTILS.SERIAL.deserialize(payload);
          
          // Check fee
	  if (this.fee<0.0001)
	      throw new Exception("Invalid fee - CRentPacket.java");
          
          // Check payload
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
          foot.add("Address", dec_payload.target_adr);
          foot.add("Item ID", dec_payload.itemID);
          foot.add("Price", dec_payload.price);
          foot.write();
    }
}