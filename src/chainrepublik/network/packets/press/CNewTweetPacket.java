// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.press;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.blocks.CBlockPayload;
import chainrepublik.network.packets.trans.CFeePayload;


public class CNewTweetPacket extends CBroadcastPacket 
{
   // Serial
   private static final long serialVersionUID = 1L;
   
   public CNewTweetPacket(String fee_adr, 
		          String adr, 
		          String title,
                          String mes, 
                          String categ,
                          String cou, 
                          long retweet_tweet_ID,
		          String pic,
                          long mil_unit,
                          long pol_party,
                          long days) throws Exception
   {
	   // Super class
	   super("ID_NEW_TWEET_PACKET");
	   
	   // Builds the payload class
	   CNewTweetPayload dec_payload=new CNewTweetPayload(adr, 
                                                             title,
		                                             mes, 
                                                             categ,
                                                             cou,
                                                             pic,
                                                             retweet_tweet_ID,
                                                             mil_unit,
                                                             pol_party,
                                                             days);
			
	   // Build the payload
	   this.payload=UTILS.SERIAL.serialize(dec_payload);
			
	   // Network fee
           CFeePayload fee=new CFeePayload(fee_adr,  0.0001*days, "New article network fee");
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
   	  if (!this.tip.equals("ID_NEW_TWEET_PACKET")) 
   		throw new Exception("Invalid packet type - CNewTweetPacket.java");
   	  
   	  // Deserialize transaction data
   	  CNewTweetPayload dec_payload=(CNewTweetPayload) UTILS.SERIAL.deserialize(payload);
          
          // Check payload
          dec_payload.check(block);
          
          // Deserialize payload
          CFeePayload fee=(CFeePayload) UTILS.SERIAL.deserialize(fee_payload);
          
          // Check fee
	  if (fee.amount<dec_payload.days*0.0001)
	     throw new Exception("Invalid fee - CNewTweetPacket.java");
 
          // Footprint
          CPackets foot=new CPackets(this);
          foot.add("Address", dec_payload.target_adr);
          foot.add("Title", String.valueOf(dec_payload.title));
          foot.add("Message", String.valueOf(dec_payload.mes));
          foot.add("Pic", String.valueOf(dec_payload.pic));
          foot.add("Military Unit", String.valueOf(dec_payload.pol_party));
          foot.add("Political Party", String.valueOf(dec_payload.mil_unit));
          foot.add("Retweet Tweet ID", String.valueOf(dec_payload.retweet_tweet_ID));
          foot.write();
   	
   }
   
  
}
