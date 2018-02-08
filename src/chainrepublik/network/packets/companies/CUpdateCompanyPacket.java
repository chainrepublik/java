package chainrepublik.network.packets.companies;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;
import chainrepublik.network.packets.trans.CFeePayload;

public class CUpdateCompanyPacket extends CBroadcastPacket
{
     public CUpdateCompanyPacket(String fee_adr,
                                 String adr,
                                 long comID, 
                                 String name, 
                                 String desc, 
                                 String pic, 
                                 String sealed) throws Exception
    {
        // Constructor
        super("ID_UPDATE_COM_PACKET");
        
        // Builds the payload class
	CUpdateCompanyPayload dec_payload=new CUpdateCompanyPayload(adr,
                                                                    comID, 
                                                                    name, 
                                                                    desc, 
                                                                    pic, 
                                                                    sealed);
                              
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
					
        // Network fee
	CFeePayload fee=new CFeePayload(fee_adr, 0.0001, "Update company network fee");
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
   	  if (!this.tip.equals("ID_UPDATE_COM_PACKET")) 
             throw new Exception("Invalid packet type - CUpdateCompanyPacket.java");
   	  
          // Deserialize transaction data
   	  CUpdateCompanyPayload dec_payload=(CUpdateCompanyPayload) UTILS.SERIAL.deserialize(payload);
          
          // Deserialize payload
          CFeePayload fee=(CFeePayload) UTILS.SERIAL.deserialize(fee_payload);
        
          // Check fee
	  if (fee.amount<0.0001)
	      throw new Exception("Invalid fee - CUpdateCompanyPacket.java");
          
          // Check payload
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
          foot.add("Address", dec_payload.target_adr);
          foot.add("Company ID", dec_payload.comID);
          foot.add("Name", dec_payload.name);
          foot.add("Description", dec_payload.desc);
          foot.add("Pic", dec_payload.pic);
          foot.add("Sealed", dec_payload.sealed);
          foot.write();
    }
}   
