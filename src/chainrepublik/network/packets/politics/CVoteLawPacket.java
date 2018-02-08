package chainrepublik.network.packets.politics;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;
import chainrepublik.network.packets.trans.CFeePayload;

public class CVoteLawPacket extends CBroadcastPacket
{
    public CVoteLawPacket(String fee_adr,
                          String adr, 
                          long lawID, 
                          String vote_type) throws Exception
    {
        // Constructor
        super("ID_VOTE_LAW_PACKET");
        
        // Builds the payload class
	CVoteLawPayload dec_payload=new CVoteLawPayload(adr, 
                                                       lawID, 
                                                       vote_type);
                              
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
					
        // Network fee
	CFeePayload fee=new CFeePayload(fee_adr,  0.0001, "Congress las vote network fee");
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
   	  if (!this.tip.equals("ID_VOTE_LAW_PACKET")) 
             throw new Exception("Invalid packet type - CNewLawPacket.java");
   	  
          // Deserialize transaction data
   	  CVoteLawPayload dec_payload=(CVoteLawPayload) UTILS.SERIAL.deserialize(payload);
          
          // Deserialize payload
          CFeePayload fee=(CFeePayload) UTILS.SERIAL.deserialize(fee_payload);
        
          // Check fee
	  if (fee.amount<0.0001)
	      throw new Exception("Invalid fee - CNewLawPacket.java");
          
          // Check payload
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
          foot.add("Address", dec_payload.target_adr);
          foot.add("LawID", dec_payload.lawID);
          foot.add("Vote Type", dec_payload.vote_type);
          foot.write();
    }
}