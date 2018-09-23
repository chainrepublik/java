package chainrepublik.network.packets.politics.orgs;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CVoteOrgPropPacket extends CBroadcastPacket 
{
    // Serial
    private static final long serialVersionUID = 100L;
    
    public CVoteOrgPropPacket(String fee_adr,
                               String adr, 
                               long propID,
                               String vote) throws Exception
    {
        // Constructor
        super(fee_adr, "ID_VOTE_ORG_PROP_PACKET");
        
        // Builds the payload class
	CVoteOrgPropPayload dec_payload=new CVoteOrgPropPayload(adr, 
                                                               propID,
                                                               vote);
                              
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
					
        // Network fee
	this.setFee(0.0001, "Vote organization proposal network fee");
			   
	// Sign packet
	this.sign();
    }
    
    // Check 
    public void check(CBlockPayload block) throws Exception
    {
	  // Super class
   	  super.check(block);
   	  
   	  // Check type
   	  if (!this.tip.equals("ID_VOTE_ORG_PROP_PACKET")) 
             throw new Exception("Invalid packet type - CEndorsePacket.java");
   	  
          // Deserialize transaction data
   	  CVoteOrgPropPayload dec_payload=(CVoteOrgPropPayload) UTILS.SERIAL.deserialize(payload);
          
          // Check fee
	  if (this.fee<0.0001)
	      throw new Exception("Invalid fee - CEndorsePacket.java");
          
          // Check payload
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
          foot.add("Proposal ID", dec_payload.propID);
          foot.add("Vote", dec_payload.vote);
          foot.write();
    }
}   
