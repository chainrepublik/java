package chainrepublik.network.packets.politics.congress;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CEndorsePacket extends CBroadcastPacket 
{
     // Serial
    private static final long serialVersionUID = 100L;
    
    public CEndorsePacket(String fee_adr,
                           String adr,
                           String endorsed,
                           String type) throws Exception
    {
        // Constructor
        super(fee_adr, "ID_ENDORSE_ADR_PACKET");
        
        // Builds the payload class
	CEndorsePayload dec_payload=new CEndorsePayload(adr, 
                                                        endorsed,
                                                        type);
                              
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
					
        // Network fee
	this.setFee( 0.0001, "Endorse address network fee");
			   
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
          
          // Check fee
	  if (this.fee<0.0001)
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
