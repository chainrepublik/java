package chainrepublik.network.packets.companies;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;
import chainrepublik.network.packets.trans.CFeePayload;

public class CUpdateWorkplacePacket extends CBroadcastPacket
{
   public CUpdateWorkplacePacket(String fee_adr,
                                 String adr,
                                 long workplaceID,
                                 String status,
                                 double wage,
                                 String prod) throws Exception
    {
        // Constructor
        super("ID_UPDATE_WORKPLACE_PACKET");
        
        // Builds the payload class
	CUpdateWorkplacePayload dec_payload=new CUpdateWorkplacePayload(adr,
                                                                        workplaceID,
                                                                        status,
                                                                        wage,
                                                                        prod);
                              
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
					
        // Network fee
	CFeePayload fee=new CFeePayload(fee_adr, 0.0001, "Update workplace network fee");
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
   	  if (!this.tip.equals("ID_UPDATE_WORKPLACE_PACKET")) 
             throw new Exception("Invalid packet type - CUpdateWorkplacePacket.java");
   	  
          // Deserialize transaction data
   	  CUpdateWorkplacePayload dec_payload=(CUpdateWorkplacePayload) UTILS.SERIAL.deserialize(payload);
          
          // Deserialize payload
          CFeePayload fee=(CFeePayload) UTILS.SERIAL.deserialize(fee_payload);
        
          // Check fee
	  if (fee.amount<0.0001)
	      throw new Exception("Invalid fee - CUpdateWorkplacePacket.java");
          
          // Check payload
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
          foot.add("Address", dec_payload.target_adr);
          foot.add("Workplace ID", dec_payload.workplaceID);
          foot.add("Status", dec_payload.status);
          foot.add("Wage", dec_payload.wage);
          foot.add("Prod", dec_payload.prod);
          foot.write();
    }
}   