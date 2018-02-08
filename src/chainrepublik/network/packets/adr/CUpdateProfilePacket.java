package chainrepublik.network.packets.adr;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;
import chainrepublik.network.packets.trans.CFeePayload;

public class CUpdateProfilePacket extends CBroadcastPacket
{
    public CUpdateProfilePacket(String fee_adr, 
		                String target_adr, 
		                String img,
                                String description) throws Exception
    {
        // Constructor
        super("ID_UPDATE_PROFILE_PACKET");
        
        // Builds the payload class
	  CUpdateProfilePayload dec_payload=new CUpdateProfilePayload(target_adr, 
                                                                      img,  
                                                                      description);
					
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
					
        // Network fee
	CFeePayload fee=new CFeePayload(fee_adr,  0.0001, "Update profile network fee");
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
   	  if (!this.tip.equals("ID_UPDATE_PROFILE_PACKET")) 
             throw new Exception("Invalid packet type - CUpdateProfilePacket.java");
   	  
          // Deserialize transaction data
   	  CUpdateProfilePayload dec_payload=(CUpdateProfilePayload) UTILS.SERIAL.deserialize(payload);
          
          // Deserialize payload
          CFeePayload fee=(CFeePayload) UTILS.SERIAL.deserialize(fee_payload);
        
          // Check fee
	  if (fee.amount<0.0001)
	      throw new Exception("Invalid fee - CUpdateProfilePacket.java");
          
          // Check payload
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
          foot.add("Address", dec_payload.target_adr);
          foot.add("Image", dec_payload.img);
          foot.add("Description", dec_payload.description);
          foot.write();
   	  
    }
		   
	
  }   

