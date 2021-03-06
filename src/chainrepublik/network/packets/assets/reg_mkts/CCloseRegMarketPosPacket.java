// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.assets.reg_mkts;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CCloseRegMarketPosPacket extends CBroadcastPacket
{
     // Serial
     private static final long serialVersionUID = 100L;
    
     public CCloseRegMarketPosPacket(String fee_adr,
                                    String adr,
                                    long orderID) throws Exception
    {
          super(fee_adr, "ID_REG_ASSET_MARKET_CLOSE_POS_PACKET");
	  
	  // Builds the payload class
	  CCloseRegMarketPosPayload dec_payload=new CCloseRegMarketPosPayload(adr, orderID);
					
	  // Build the payload
	  this.payload=UTILS.SERIAL.serialize(dec_payload);
					
          // Network fee
	  this.setFee(0.0001, "Close asset market position network fee");
			   
	   // Sign packet
	   this.sign();
	}
		
        // Check 
	public void check(CBlockPayload block) throws Exception
	{
	    // Super class
   	  super.check(block);
   	  
   	  // Check type
   	  if (!this.tip.equals("ID_REG_ASSET_MARKET_CLOSE_POS_PACKET")) 
             throw new Exception("Invalid packet type - CCloseRegMarketPosPacket.java");
   	  
          // Deserialize transaction data
   	  CCloseRegMarketPosPayload dec_payload=(CCloseRegMarketPosPayload) UTILS.SERIAL.deserialize(payload);
          
          // Check fee
	  if (this.fee<0.0001)
	      throw new Exception("Invalid fee - CCloseRegMarketPosPacket.java");
          
          // Check payload
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
                  
          foot.add("Address", dec_payload.target_adr);
          foot.add("Order ID", String.valueOf(dec_payload.orderID));
          foot.write();
        }
}   
