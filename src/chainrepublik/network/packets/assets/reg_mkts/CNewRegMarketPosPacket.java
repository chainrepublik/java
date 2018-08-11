// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.assets.reg_mkts;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CNewRegMarketPosPacket extends CBroadcastPacket
{
    public CNewRegMarketPosPacket(String fee_adr,
                                  String adr,
                                  long mktID,
                                  String tip,
                                  double price,
                                  double qty,
                                  long days) throws Exception
    {
          super(fee_adr, "ID_NEW_REG_ASSET_MARKET_POS_PACKET");
	  
	  // Builds the payload class
	  CNewRegMarketPosPayload dec_payload=new CNewRegMarketPosPayload(adr,
                                                                          mktID,
                                                                          tip,
                                                                          price,
                                                                          qty,
                                                                          days);
					
	  // Build the payload
	  this.payload=UTILS.SERIAL.serialize(dec_payload);
					
          // Network fee
	  this.setFee(0.0001*days, "New asset market position network fee");
			   
	   // Sign packet
	   this.sign();
	}
		
        // Check 
	public void check(CBlockPayload block) throws Exception
	{
	     // Super class
   	   super.check(block);
   	   
   	   // Check type
   	   if (!this.tip.equals("ID_NEW_REG_ASSET_MARKET_POS_PACKET")) 
                throw new Exception("Invalid packet type - CNewRegMarketPosPacket.java");
   	  
             // Deserialize transaction data
   	     CNewRegMarketPosPayload dec_payload=(CNewRegMarketPosPayload) UTILS.SERIAL.deserialize(payload);
             
             // Check fee
	     if (this.fee<dec_payload.days*0.0001)
	         throw new Exception("Invalid fee - CNewRegMarketPosPacket.java");
          
             // Check payload
             dec_payload.check(block);
             
             // Buy in the name of company ?
             if (!this.adr.equals(dec_payload.target_adr))
             {
                 // Company address ?
                 ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                                    + "FROM companies "
                                                   + "WHERE adr='"+dec_payload.target_adr+"' "
                                                     + "AND owner='"+this.adr+"'");
                 
                 // Has data ?
                 if (!UTILS.DB.hasData(rs))
                    throw new Exception("Invalid fee - CNewRegMarketPosPacket.java");
             }
             
             // Footprint
             CPackets foot=new CPackets(this);
             foot.add("Address", dec_payload.target_adr);
             foot.add("Market ID", String.valueOf(dec_payload.mktID));
             foot.add("OrderID", String.valueOf(dec_payload.orderID));
             foot.add("Type", dec_payload.tip);
             foot.add("Qty", String.valueOf(dec_payload.qty));
             foot.add("Price", String.valueOf(dec_payload.price));
             foot.add("Days", String.valueOf(dec_payload.days));
             foot.write();
   	  
	}
}   
