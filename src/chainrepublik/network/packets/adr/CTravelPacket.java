package chainrepublik.network.packets.adr;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;
import chainrepublik.network.packets.trans.CFeePayload;

public class CTravelPacket extends CBroadcastPacket
{
    // Serial
   private static final long serialVersionUID = 1L;
    
   public CTravelPacket(String fee_adr,
                           String adr,
                           String cou) throws Exception
   {
	   // Super class
	   super("ID_ADR_TRAVEL_PACKET");
	   
	   // Builds the payload class
	   CTravelPayload dec_payload=new CTravelPayload(adr,
                                                           cou);
			
	   // Build the payload
	   this.payload=UTILS.SERIAL.serialize (dec_payload);
			
	   // Network fee
	   CFeePayload fee=new CFeePayload(fee_adr,  0.0001, "Travel network fee");
	   this.fee_payload=UTILS.SERIAL.serialize(fee);
	   
	   // Sign packet
           this.sign();
    }
   
     public void check(CBlockPayload block) throws Exception
     {
	// Super class
	super.check(block);
		   	
	// Check type
	if (!this.tip.equals("ID_ADR_TRAVEL_PACKET")) 
	    throw new Exception("Invalid packet type - CAdrRegisterPacket.java");
		  
	// Deserialize
	CTravelPayload payload=(CTravelPayload) UTILS.SERIAL.deserialize(this.payload);
        
        // Deserialize payload
        CFeePayload fee=(CFeePayload) UTILS.SERIAL.deserialize(fee_payload);
        
	// Check fee
	if (fee.amount<0.0001)
	   throw new Exception("Invalid fee - CAdrRegisterPacket.java");
		  
	// Check payload
	payload.check(block);
	          
        // Footprint
        CPackets foot=new CPackets(this);
        
        // Footprint data
        foot.add("Address", payload.target_adr);
        foot.add("Country", payload.cou);
        foot.write();
    }
	     
   
}