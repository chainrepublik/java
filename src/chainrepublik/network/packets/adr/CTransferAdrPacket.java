package chainrepublik.network.packets.adr;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CTransferAdrPacket extends CBroadcastPacket
{
  public CTransferAdrPacket(String fee_adr, 
		            String adr, 
		            String to_adr) throws Exception
  {
        super(fee_adr, "ID_TRANSFER_ADR_PACKET");
      
        // Builds the payload class
        CTransferAdrPayload dec_payload=new CTransferAdrPayload(adr, 
	                                                        to_adr);
			
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
			
	// Network fee
	this.setFee(0.01, "Transfer address network fee");
	   
	// Sign packet
	this.sign();
  }
  
  // Check 
  public void check(CBlockPayload block) throws Exception
  {
    // Super class
    super.check(block);
  	  
    // Check type
    if (!this.tip.equals("ID_TRANSFER_ADR_PACKET")) 
  	throw new Exception("Invalid packet type - CSendRefPacket.java");
  	  
    // Check
    CTransferAdrPayload dec_payload=(CTransferAdrPayload) UTILS.SERIAL.deserialize(payload);
    dec_payload.check(block);
    
    // Check fee
    if (this.fee<0.01)
	throw new Exception("Invalid fee - CTransferAdrPacket.java");
          
    // Footprint
    CPackets foot=new CPackets(this);
    foot.add("To Address", dec_payload.to_adr);
    foot.write();
  }

}
