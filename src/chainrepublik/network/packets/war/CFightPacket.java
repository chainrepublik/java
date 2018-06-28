package chainrepublik.network.packets.war;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.CPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;
import chainrepublik.network.packets.portofolio.CUseItemPayload;
import chainrepublik.network.packets.trans.CFeePayload;

public class CFightPacket extends CBroadcastPacket
{
   public CFightPacket(String fee_adr,
                       String adr,
                       long warID,
                       String type) throws Exception
    {
        // Constructor
        super(fee_adr, "ID_FIGHT_PACKET");
        
        // Builds the payload class
	CFightPayload dec_payload=new CFightPayload(adr, 
                                                    warID,
                                                    type);
                              
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
					
        // Network fee
	this.setFee(0.0001, "Fight network fee");
			   
	// Sign packet
	this.sign();
    }
    
    // Check 
    public void check(CBlockPayload block) throws Exception
    {
	  // Super class
   	  super.check(block);
   	  
   	  // Check type
   	  if (!this.tip.equals("ID_FIGHT_PACKET")) 
             throw new Exception("Invalid packet type - CFightPacket.java");
   	  
          // Deserialize transaction data
   	  CFightPayload dec_payload=(CFightPayload) UTILS.SERIAL.deserialize(payload);
          
          // Check fee
	  if (this.fee<0.0001)
	      throw new Exception("Invalid fee - CFightPacket.java");
          
          // Check payload
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
          foot.add("Address", dec_payload.target_adr);
          foot.add("War ID", dec_payload.warID);
          foot.add("Type", dec_payload.type);
          foot.write();
    }
}