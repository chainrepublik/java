package chainrepublik.network.packets.politics.orgs;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;
import chainrepublik.network.packets.trans.CFeePayload;

public class CJoinPartyPacket extends CBroadcastPacket 
{
     public CJoinPartyPacket(String fee_adr,
                             String adr,
                             long orgID) throws Exception
    {
        // Constructor
        super(fee_adr, "ID_JOIN_PARTY_PACKET");
        
        // Builds the payload class
	CJoinPartyPayload dec_payload=new CJoinPartyPayload(adr,
                                                            orgID);
                              
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
					
        // Network fee
	this.setFee(0.0001, "Join party network fee");
			   
	// Sign packet
	this.sign();
    }
    
    // Check 
    public void check(CBlockPayload block) throws Exception
    {
	  // Super class
   	  super.check(block);
   	  
   	  // Check type
   	  if (!this.tip.equals("ID_JOIN_PARTY_PACKET")) 
             throw new Exception("Invalid packet type - CEndorsePacket.java");
   	  
          // Check fee
	  if (this.fee<0.0001)
	      throw new Exception("Invalid fee - CEndorsePacket.java");
          
          // Deserialize transaction data
   	  CJoinPartyPayload dec_payload=(CJoinPartyPayload) UTILS.SERIAL.deserialize(payload);
          
          // Check payload
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
          foot.add("OrgID", dec_payload.orgID);
          foot.write();
    }
}   
