package chainrepublik.network.packets.companies;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CRentLicencePacket extends CBroadcastPacket
{
    // Serial
    private static final long serialVersionUID = 100L;
    
    
    public CRentLicencePacket(String fee_adr,
                              String adr, 
                              long comID, 
                              String lic, 
                              long days) throws Exception
    {
        // Constructor
        super(fee_adr, "ID_RENT_LIC_PACKET");
        
        // Builds the payload class
	CRentLicencePayload dec_payload=new CRentLicencePayload(adr, 
                                                                comID, 
                                                                lic, 
                                                                days);
                              
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
					
        // Network fee
	this.setFee(0.0001, "Rent licence network fee");
			   
	// Sign packet
	this.sign();
    }
    
    // Check 
    public void check(CBlockPayload block) throws Exception
    {
	  // Super class
   	  super.check(block);
   	  
   	  // Check type
   	  if (!this.tip.equals("ID_RENT_LIC_PACKET")) 
             throw new Exception("Invalid packet type - CRentLicencePacket.java");
   	  
          // Deserialize transaction data
   	  CRentLicencePayload dec_payload=(CRentLicencePayload) UTILS.SERIAL.deserialize(payload);
          
          // Check fee
	  if (this.fee<0.0001)
	      throw new Exception("Invalid fee - CRentLicencePacket.java");
          
          // Check payload
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
          foot.add("Address", dec_payload.target_adr);
          foot.add("Company ID", dec_payload.comID);
          foot.add("Licence", dec_payload.lic);
          foot.add("Days", dec_payload.days);
          foot.write();
    }
}   
