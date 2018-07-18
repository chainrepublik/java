// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.assets.reg_mkts;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CNewRegMarketPacket extends CBroadcastPacket 
{
     public CNewRegMarketPacket(String fee_adr,
                                String adr, 
                                String asset_symbol,
                                String cur_symbol,
                                String name,
                                String desc, 
                                long decimals,
                                long days) throws Exception
   {
	   super(fee_adr, "ID_NEW_ASSET_MKT_PACKET");

	   // Builds the payload class
	   CNewRegMarketPayload dec_payload=new CNewRegMarketPayload(adr, 
                                                                     asset_symbol,
                                                                     cur_symbol,
                                                                     name,
                                                                     desc, 
                                                                     decimals,
                                                                     days); 
           
	   // Build the payload
	   this.payload=UTILS.SERIAL.serialize(dec_payload);
           
	   // Network fee
	  this.setFee(0.0001*days, "New asset market network fee");
	   
	   // Sign packet
	   this.sign();
   }
   
   // Check 
   public void check(CBlockPayload block) throws Exception
   {
         // Super class
   	  super.check(block);
   	  
   	  // Check type
   	  if (!this.tip.equals("ID_NEW_ASSET_MKT_PACKET")) 
             throw new Exception("Invalid packet type - CNewRegMarketPacket.java");
   	  
          // Deserialize transaction data
   	  CNewRegMarketPayload dec_payload=(CNewRegMarketPayload) UTILS.SERIAL.deserialize(payload);
          
          // Check fee
	  if (this.fee<dec_payload.days*0.0001)
	      throw new Exception("Invalid fee - CIssueMoreAssetsPacket.java");
          
          // Check payload
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
                  
          foot.add("Address", dec_payload.target_adr);
          foot.add("Asset Symbol", dec_payload.asset_symbol);
          foot.add("Currency Symbol", dec_payload.cur_symbol);
          foot.add("Title", dec_payload.title);
          foot.add("Description", dec_payload.description);
          foot.add("Decimals", String.valueOf(dec_payload.decimals));
          foot.add("Days", String.valueOf(dec_payload.days));
          foot.write();
   	  
   }
   
   
}