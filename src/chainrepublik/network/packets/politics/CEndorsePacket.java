package chainrepublik.network.packets.politics;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;
import chainrepublik.network.packets.companies.CNewCompanyPayload;
import chainrepublik.network.packets.trans.CFeePayload;

public class CEndorsePacket extends CBroadcastPacket 
{
     public CEndorsePacket(String fee_adr,
                           String adr,
                           String endorsed,
                           String type) throws Exception
    {
        // Constructor
        super("ID_ENDORSE_ADR_PACKET");
        
        // Builds the payload class
	CEndorsePayload dec_payload=new CEndorsePayload(adr, 
                                                        endorsed,
                                                        type);
                              
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
					
        // Network fee
	CFeePayload fee=new CFeePayload(fee_adr,  0.0001, "Endorse address network fee");
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
   	  if (!this.tip.equals("ID_ENDORSE_ADR_PACKET")) 
             throw new Exception("Invalid packet type - CEndorsePacket.java");
   	  
          // Deserialize transaction data
   	  CEndorsePayload dec_payload=(CEndorsePayload) UTILS.SERIAL.deserialize(payload);
          
          // Deserialize payload
          CFeePayload fee=(CFeePayload) UTILS.SERIAL.deserialize(fee_payload);
        
          // Check fee
	  if (fee.amount<0.0001)
	      throw new Exception("Invalid fee - CEndorsePacket.java");
          
          // Check payload
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
          foot.add("Endorser", dec_payload.target_adr);
          foot.add("Endorsed", dec_payload.endorsed);
          foot.add("Type", dec_payload.type);
          foot.write();
    }
}   
