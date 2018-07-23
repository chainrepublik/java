package chainrepublik.network.packets.politics.orgs;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CLeaveOrgPacket  extends CBroadcastPacket 
{
    public CLeaveOrgPacket(String fee_adr,
                           String adr,
                           long orgID) throws Exception
    {
        // Constructor
        super(fee_adr, "ID_LEAVE_PARTY_PACKET");
        
        // Builds the payload class
	CLeaveOrgPayload dec_payload=new CLeaveOrgPayload(adr, orgID);
                              
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
					
        // Network fee
	this.setFee(0.0001, "Leave political party network fee");
			   
	// Sign packet
	this.sign();
    }
    
    // Check 
    public void check(CBlockPayload block) throws Exception
    {
	  // Super class
   	  super.check(block);
   	  
   	  // Check type
   	  if (!this.tip.equals("ID_LEAVE_PARTY_PACKET")) 
             throw new Exception("Invalid packet type - CEndorsePacket.java");
   	  
          // Deserialize transaction data
   	  CLeaveOrgPayload dec_payload=(CLeaveOrgPayload) UTILS.SERIAL.deserialize(payload);
          
          // Check fee
	  if (this.fee<0.0001)
	      throw new Exception("Invalid fee - CEndorsePacket.java");
          
          // Check payload
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
          foot.write();
    }
}
