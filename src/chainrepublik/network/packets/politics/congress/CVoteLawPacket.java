package chainrepublik.network.packets.politics.congress;

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
        super(fee_adr, "ID_VOTE_LAW_PACKET");
        
        // Builds the payload class
	CVoteLawPayload dec_payload=new CVoteLawPayload(adr, 
                                                       lawID, 
                                                       vote_type);
                              
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
					
        // Network fee
	this.setFee(0.0001, "Congress las vote network fee");
			   
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
          
          // Check fee
	  if (this.fee<0.0001)
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