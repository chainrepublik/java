package chainrepublik.network.packets.politics.orgs;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CNewOrgPropPacket extends CBroadcastPacket 
{
     public CNewOrgPropPacket(String fee_adr,
                              String adr, 
                              long orgID,
                              String prop_type, 
                              String par_1,
                              String par_2,
                              String par_3,
                              String par_4,
                              String par_5,
                              String expl) throws Exception
    {
        // Constructor
        super(fee_adr, "ID_NEW_ORG_PROP_PACKET");
        
        // Builds the payload class
	CNewOrgPropPayload dec_payload=new CNewOrgPropPayload(adr, 
                                                             orgID,
                                                             prop_type, 
                                                             par_1,
                                                             par_2,
                                                             par_3,
                                                             par_4,
                                                             par_5,
                                                             expl);
                              
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
					
        // Network fee
	this.setFee(0.0001, "New organization proposal network fee");
			   
	// Sign packet
	this.sign();
    }
    
    // Check 
    public void check(CBlockPayload block) throws Exception
    {
	  // Super class
   	  super.check(block);
   	  
   	  // Check type
   	  if (!this.tip.equals("ID_NEW_ORG_PROP_PACKET")) 
             throw new Exception("Invalid packet type - CEndorsePacket.java");
   	  
          // Deserialize transaction data
   	  CNewOrgPropPayload dec_payload=(CNewOrgPropPayload) UTILS.SERIAL.deserialize(payload);
          
          // Check fee
	  if (this.fee<0.0001)
	      throw new Exception("Invalid fee - CEndorsePacket.java");
          
          // Check payload
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
          foot.add("OrgID", dec_payload.orgID);
          foot.add("Proposal Type", dec_payload.prop_type);
          foot.add("Parameter 1", dec_payload.par_1);
          foot.add("Parameter 2", dec_payload.par_2);
          foot.add("Parameter 3", dec_payload.par_3);
          foot.add("Parameter 4", dec_payload.par_4);
          foot.add("Parameter 5", dec_payload.par_5);
          foot.write();
    }
}   
