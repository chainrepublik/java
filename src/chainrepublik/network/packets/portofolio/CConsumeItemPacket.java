package chainrepublik.network.packets.portofolio;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;
import chainrepublik.network.packets.trans.CFeePayload;

public class CConsumeItemPacket extends CBroadcastPacket
{
    public CConsumeItemPacket(String fee_adr,
                              String adr,
                              long itemID) throws Exception
    {
        // Constructor
        super("ID_CONSUME_ITEM_PACKET");
        
        // Builds the payload class
	CConsumeItemPayload dec_payload=new CConsumeItemPayload(adr, 
                                                                itemID);
                              
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
					
        // Network fee
	CFeePayload fee=new CFeePayload(fee_adr, 0.0001, "Consume item network fee");
	this.fee_payload=UTILS.SERIAL.serialize(fee);
			   
	// Sign packet
	this.sign();
    }
    
    // Check 
    public void check(CBlockPayload block) throws Exception
    {
	  // Super class
   	  super.check(block);
   	  
   	  // Check type
   	  if (!this.tip.equals("ID_CONSUME_ITEM_PACKET")) 
             throw new Exception("Invalid packet type - CConsumeItemPacket.java");
   	  
          // Deserialize transaction data
   	  CConsumeItemPayload dec_payload=(CConsumeItemPayload) UTILS.SERIAL.deserialize(payload);
          
          // Deserialize payload
          CFeePayload fee=(CFeePayload) UTILS.SERIAL.deserialize(fee_payload);
        
          // Check fee
	  if (fee.amount<0.0001)
	      throw new Exception("Invalid fee - CConsumeItemPacket.java");
          
          // Check payload
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
          foot.add("Address", dec_payload.target_adr);
          foot.add("Item ID", dec_payload.itemID);
          foot.write();
    }
}