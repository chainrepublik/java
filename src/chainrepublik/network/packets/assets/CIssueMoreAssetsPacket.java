package chainrepublik.network.packets.assets;

import java.sql.ResultSet;
import java.sql.Statement;
import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;
import chainrepublik.network.packets.trans.CFeePayload;

public class CIssueMoreAssetsPacket extends CBroadcastPacket
{
    public CIssueMoreAssetsPacket(String fee_adr,
		                  String adr,
                                  String symbol,
                                  long qty) throws Exception
   {
	   super(fee_adr, "ID_ISSUE_MORE_ASSETS_PACKET");

	   // Builds the payload class
	   CIssueMoreAssetsPayload dec_payload=new CIssueMoreAssetsPayload(adr,
                                                                           symbol,
                                                                           qty); 
           
	   // Build the payload
	   this.payload=UTILS.SERIAL.serialize(dec_payload);
           
	   // Network fee
	  this.setFee(0.0001*dec_payload.qty, "Issue more assets network fee");
	   
	   // Sign packet
	   this.sign();
   }
   
   // Check 
   public void check(CBlockPayload block) throws Exception
   {
         // Super class
   	  super.check(block);
   	  
   	  // Check type
   	  if (!this.tip.equals("ID_ISSUE_MORE_ASSETS_PACKET")) 
             throw new Exception("Invalid packet type - CIssueMoreAssetsPacket.java");
   	  
          // Deserialize transaction data
   	  CIssueMoreAssetsPayload dec_payload=(CIssueMoreAssetsPayload) UTILS.SERIAL.deserialize(payload);
          
          // Check fee
	  if (this.fee<dec_payload.qty*0.0001)
	      throw new Exception("Invalid fee - CIssueMoreAssetsPacket.java");
          
          // Check payload
          dec_payload.check(block);
          
          // Footprint
          CPackets foot=new CPackets(this);
                  
          foot.add("Address", dec_payload.target_adr);
          foot.add("Symbol", dec_payload.symbol);
          foot.add("Qty", String.valueOf(dec_payload.qty));
          foot.write();
   	  
   }
   
   
}