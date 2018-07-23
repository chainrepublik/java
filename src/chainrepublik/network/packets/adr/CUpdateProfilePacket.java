package chainrepublik.network.packets.adr;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CUpdateProfilePacket extends CBroadcastPacket
{
     // Serial
    private static final long serialVersionUID = 100L;
    
    public CUpdateProfilePacket(String fee_adr, 
		                String target_adr, 
		                String img,
                                String description) throws Exception
    {
        // Constructor
        super(fee_adr, "ID_UPDATE_PROFILE_PACKET");
        
        // Builds the payload class
	  CUpdateProfilePayload dec_payload=new CUpdateProfilePayload(target_adr, 
                                                                      img,  
                                                                      description);
					
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
					
        // Network fee
	this.setFee(0.0001, "Update profile network fee");
			   
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
          
          // Check fee
	  if (this.fee<0.0001)
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

