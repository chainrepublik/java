package chainrepublik.network.packets.companies;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.adr.CAddAttrPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import chainrepublik.network.packets.trans.CFeePayload;

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
                             String shareholder_adr,
                             long days) throws Exception
    {
        // Constructor
        super("ID_NEW_COMPANY_PACKET");
        
        // Builds the payload class
	CNewCompanyPayload dec_payload=new CNewCompanyPayload(adr, 
                                                              type, 
                                                              name, 
                                                              desc, 
                                                              symbol, 
                                                              cou,
                                                              pic,
                                                              shareholder_adr,
                                                              days);
                              
	// Build the payload
	this.payload=UTILS.SERIAL.serialize(dec_payload);
					
        // Network fee
	CFeePayload fee=new CFeePayload(fee_adr,  days*0.1, "Company incorporation network fee");
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
   	  if (!this.tip.equals("ID_NEW_COMPANY_PACKET")) 
             throw new Exception("Invalid packet type - CNewCompanyPacket.java");
   	  
          // Deserialize transaction data
   	  CNewCompanyPayload dec_payload=(CNewCompanyPayload) UTILS.SERIAL.deserialize(payload);
          
          // Deserialize payload
          CFeePayload fee=(CFeePayload) UTILS.SERIAL.deserialize(fee_payload);
        
          // Check fee
	  if (fee.amount<dec_payload.days*0.1)
	      throw new Exception("Invalid fee - CNewCompanyPacket.java");
          
          // Check payload
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
          foot.add("Address", dec_payload.target_adr);
          foot.add("Type", dec_payload.type);
          foot.add("Name", dec_payload.name);
          foot.add("Description", dec_payload.desc);
          foot.add("Symbol", dec_payload.symbol);
          foot.add("Country", dec_payload.cou);
          foot.add("Pic", dec_payload.pic);
          foot.add("Shareholder Address", dec_payload.shareholder_adr);
          foot.add("Days", dec_payload.days);
          foot.write();
    }
}   
