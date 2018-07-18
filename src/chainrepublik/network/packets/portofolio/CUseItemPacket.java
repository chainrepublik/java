package chainrepublik.network.packets.portofolio;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CUseItemPacket extends CBroadcastPacket
{
    public CUseItemPacket(String fee_adr,
                          String adr,
                          long itemID) throws Exception
    {
        // Constructor
        super(fee_adr, "ID_USE_ITEM_PACKET");
        
        // Builds the payload class
	CUseItemPayload dec_payload=new CUseItemPayload(adr, 
                                                        itemID);
                              
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
					
        // Network fee
	this.setFee(0.0001, "Use item network fee");
			   
	// Sign packet
	this.sign();
    }
    
    // Check 
    public void check(CBlockPayload block) throws Exception
    {
	  // Super class
   	  super.check(block);
   	  
   	  // Check type
   	  if (!this.tip.equals("ID_USE_ITEM_PACKET")) 
             throw new Exception("Invalid packet type - CUseItemPacket.java");
   	  
          // Deserialize transaction data
   	  CUseItemPayload dec_payload=(CUseItemPayload) UTILS.SERIAL.deserialize(payload);
          
          // Check fee
	  if (this.fee<0.0001)
	      throw new Exception("Invalid fee - CUseItemPacket.java");
          
          // Check payload
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
          foot.add("Address", dec_payload.target_adr);
          foot.add("Item ID", dec_payload.itemID);
          foot.write();
    }
}