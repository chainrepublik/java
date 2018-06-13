package chainrepublik.network.packets.politics.congress;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;
import chainrepublik.network.packets.trans.CFeePayload;

public class CNewLawPacket extends CBroadcastPacket
{
    public CNewLawPacket(String fee_adr,
                         String adr, 
                         String law_type, 
                         String par_1, 
                         String par_2,
                         String par_3,
                         String expl) throws Exception
    {
        // Constructor
        super("ID_NEW_LAW_PACKET");
        
        // Builds the payload class
	CNewLawPayload dec_payload=new CNewLawPayload(adr, 
                                                     law_type, 
                                                     par_1, 
                                                     par_2, 
                                                     par_3, 
                                                     expl);
                              
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
					
        // Network fee
	CFeePayload fee=new CFeePayload(fee_adr,  0.0001, "Congress law proposal network fee");
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
   	  if (!this.tip.equals("ID_NEW_LAW_PACKET")) 
             throw new Exception("Invalid packet type - CNewLawPacket.java");
   	  
          // Deserialize transaction data
   	  CNewLawPayload dec_payload=(CNewLawPayload) UTILS.SERIAL.deserialize(payload);
          
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
          foot.add("Law Type", dec_payload.law_type);
          foot.add("Paramater 1", dec_payload.par_1);
          foot.add("Paramater 1", dec_payload.par_2);
          foot.add("Paramater 1", dec_payload.par_3);
          foot.add("Explanation", dec_payload.expl);
          foot.write();
    }
}