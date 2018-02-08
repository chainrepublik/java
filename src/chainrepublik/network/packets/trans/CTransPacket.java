// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.trans;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import java.text.DecimalFormat;

import chainrepublik.network.packets.adr.CAdrRegisterPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
        
public class CTransPacket extends CBroadcastPacket 
{		
    // Serial
    private static final long serialVersionUID = 100L;
   
    public CTransPacket(String fee_adr, 
			String src, 
	                String dest, 
	    	        double amount, 
			String cur,
			String mes,
                        String escrower,
                        long reqID) throws Exception
	{
		// Super class
		super("ID_TRANS_PACKET");
		   
		CTransPayload dec_payload = new CTransPayload(src, 
                                                              dest,
                                                              amount,
                                                              cur,
                                                              mes,
                                                              escrower);
				
		// Build the payload
		this.payload=UTILS.SERIAL.serialize(dec_payload);
				
		// Network fee
                double f=0;
                f=amount*0.0001;
                
                // Min fee ?
                if (f<0.0001) f=0.0001;
	        
                // Fee
                CFeePayload fee=new CFeePayload(fee_adr,  f, "Simple transaction neetwork fee");
	        this.fee_payload=UTILS.SERIAL.serialize(fee);
                
                // Hash
                UTILS.DB.executeUpdate("UPDATE web_ops "
                                        + "SET response='"+dec_payload.hash+"' "
                                      + "WHERE ID='"+reqID+"'");
                
		// Sign packet
		this.sign();
      }
	
	 // Check 
	   public void check(CBlockPayload block) throws Exception
	   {
	      // Super class
	      super.check(block);
	   	
	      // Check type
	      if (!this.tip.equals("ID_TRANS_PACKET")) 
	   	throw new Exception("Invalid packet type - CTransPacket.java");
	   	  
	      // Deserialize transaction data
	      CTransPayload dec_payload=(CTransPayload) UTILS.SERIAL.deserialize(payload);
	      dec_payload.check(block);
	      
              // Deserialize payload
              CFeePayload fee=(CFeePayload) UTILS.SERIAL.deserialize(fee_payload);
        
              if (dec_payload.cur.equals("CRC"))
              {
                     if (fee.amount<0.0001)
                        throw new Exception("Invalid fee - CTransPacket.java");
              }
              else
              {
                     if (fee.amount<(dec_payload.amount*0.0001))
                        throw new Exception("Invalid fee - CTransPacket.java");
              }
	   	  
                  // Footprint
                  CPackets foot=new CPackets(this);
                  
                 foot.add("Source", dec_payload.target_adr);
                 foot.add("Recipient", dec_payload.dest);
                 foot.add("Amount", UTILS.FORMAT_8.format(dec_payload.amount));
                 foot.add("Currency", dec_payload.cur);
                 foot.add("Escrower", dec_payload.escrower);
                 foot.write();
           }
}