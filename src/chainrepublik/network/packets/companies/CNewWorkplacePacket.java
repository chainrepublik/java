package chainrepublik.network.packets.companies;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;
import chainrepublik.network.packets.trans.CFeePayload;

public class CNewWorkplacePacket extends CBroadcastPacket
{
  public CNewWorkplacePacket(String fee_adr,
                             String adr, 
                             long comID, 
                             long days) throws Exception
    {
        // Constructor
        super("ID_NEW_WORKPLACE_PACKET");
        
        // Builds the payload class
	CNewWorkplacePayload dec_payload=new CNewWorkplacePayload(adr, 
                                                                  comID, 
                                                                  days);
                              
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
					
        // Network fee
	CFeePayload fee=new CFeePayload(fee_adr,  days*0.1, "New workplace network fee");
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
   	  if (!this.tip.equals("ID_NEW_WORKPLACE_PACKET")) 
             throw new Exception("Invalid packet type - CNewWorkplacePacket.java");
   	  
          // Deserialize transaction data
   	  CNewWorkplacePayload dec_payload=(CNewWorkplacePayload) UTILS.SERIAL.deserialize(payload);
          
          // Deserialize payload
          CFeePayload fee=(CFeePayload) UTILS.SERIAL.deserialize(fee_payload);
        
          // Check fee
	  if (fee.amount<dec_payload.days*0.01)
	      throw new Exception("Invalid fee - CNewWorkplacePacket.java");
          
          // Check payload
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
          foot.add("Address", dec_payload.target_adr);
          foot.add("Company ID", dec_payload.comID);
          foot.add("Workplace ID", dec_payload.workID);
          foot.add("Days", dec_payload.days);
          foot.write();
    }
}   
