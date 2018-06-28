package chainrepublik.network.packets.portofolio;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;
import chainrepublik.network.packets.trans.CFeePayload;

public class CDonateItemPacket extends CBroadcastPacket   
{
    public CDonateItemPacket(String fee_adr,
                             String adr,
                             long itemID,
                             String rec_adr) throws Exception
    {
        // Constructor
        super(fee_adr, "ID_DONATE_ITEM_PACKET");
        
        // Builds the payload class
	CDonateItemPayload dec_payload=new CDonateItemPayload(adr, 
                                                             itemID,
                                                             rec_adr);
                              
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
					
        // Network fee
	this.setFee(0.0001, "Donate item network fee");
			   
	// Sign packet
	this.sign();
    }
    
    // Check 
    public void check(CBlockPayload block) throws Exception
    {
	  // Super class
   	  super.check(block);
   	  
   	  // Check type
   	  if (!this.tip.equals("ID_DONATE_ITEM_PACKET")) 
             throw new Exception("Invalid packet type - CDonateItemPacket.java");
   	  
          // Deserialize transaction data
   	  CDonateItemPayload dec_payload=(CDonateItemPayload) UTILS.SERIAL.deserialize(payload);
          
          // Check fee
	  if (this.fee<0.0001)
	      throw new Exception("Invalid fee - CDonateItemPacket.java");
          
          // Check payload
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
          foot.add("Address", dec_payload.target_adr);
          foot.add("Item ID", dec_payload.itemID);
          foot.add("Receiver", dec_payload.rec_adr);
          foot.write();
    }
}