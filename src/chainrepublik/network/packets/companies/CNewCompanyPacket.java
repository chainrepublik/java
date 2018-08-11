package chainrepublik.network.packets.companies;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CNewCompanyPacket extends CBroadcastPacket
{
    public CNewCompanyPacket(String fee_adr,
                             String adr, 
                             String type, 
                             String name, 
                             String desc, 
                             String symbol, 
                             String cou, 
                             String pic,
                             String com_adr,
                             long days) throws Exception
    {
        // Constructor
        super(fee_adr, "ID_NEW_COMPANY_PACKET");
        
        // Builds the payload class
	CNewCompanyPayload dec_payload=new CNewCompanyPayload(adr, 
                                                              type, 
                                                              name, 
                                                              desc, 
                                                              symbol, 
                                                              cou,
                                                              pic,
                                                              com_adr,
                                                              days);
                              
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
					
        // Network fee
	this.setFee(0.0001, "Company incorporation network fee");
			   
	// Sign packet
	this.sign();
    }
    
    // Check 
    public void check(CBlockPayload block) throws Exception
    {
	  // Super class
   	  super.check(block);
   	  
   	  // Check type
   	  if (!this.tip.equals("ID_NEW_COMPANY_PACKET")) 
             throw new Exception("Invalid packet type - CNewCompanyPacket.java");
   	  
          // Deserialize transaction data
   	  CNewCompanyPayload dec_payload=(CNewCompanyPayload) UTILS.SERIAL.deserialize(payload);
          
          // Check fee
	  if (this.fee<0.0001)
	      throw new Exception("Invalid fee - CNewCompanyPacket.java");
          
          // Check payload
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
          foot.add("Owner Address", dec_payload.target_adr);
          foot.add("Type", dec_payload.type);
          foot.add("Name", dec_payload.name);
          foot.add("Description", dec_payload.desc);
          foot.add("Symbol", dec_payload.symbol);
          foot.add("Country", dec_payload.cou);
          foot.add("Pic", dec_payload.pic);
          foot.add("Company Address", dec_payload.com_adr);
          foot.add("Days", dec_payload.days);
          foot.write();
    }
}   
