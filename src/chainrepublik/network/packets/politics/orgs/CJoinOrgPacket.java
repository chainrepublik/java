package chainrepublik.network.packets.politics.orgs;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CJoinOrgPacket extends CBroadcastPacket 
{
    // Serial
    private static final long serialVersionUID = 100L;
    
    public CJoinOrgPacket(String fee_adr,
                             String adr,
                             long orgID) throws Exception
    {
        // Constructor
        super(fee_adr, "ID_JOIN_ORG_PACKET");
        
        // Builds the payload class
	CJoinOrgPayload dec_payload=new CJoinOrgPayload(adr,
                                                            orgID);
                              
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
					
        // Network fee
	this.setFee(0.0001, "Join organization network fee");
			   
	// Sign packet
	this.sign();
    }
    
    // Check 
    public void check(CBlockPayload block) throws Exception
    {
	  // Super class
   	  super.check(block);
   	  
   	  // Check type
   	  if (!this.tip.equals("ID_JOIN_ORG_PACKET")) 
             throw new Exception("Invalid packet type - CEndorsePacket.java");
   	  
          // Check fee
	  if (this.fee<0.0001)
	      throw new Exception("Invalid fee - CEndorsePacket.java");
          
          // Deserialize transaction data
   	  CJoinOrgPayload dec_payload=(CJoinOrgPayload) UTILS.SERIAL.deserialize(payload);
          
          // Check payload
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
          foot.add("OrgID", dec_payload.orgID);
          foot.write();
    }
}   
