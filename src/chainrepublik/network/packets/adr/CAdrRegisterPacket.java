// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.adr;

import chainrepublik.kernel.CPackets;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.ads.CNewAdPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import chainrepublik.network.packets.trans.CFeePayload;

public class CAdrRegisterPacket extends CBroadcastPacket 
{
   // Serial
   private static final long serialVersionUID = 1L;
    
   public CAdrRegisterPacket(String fee_adr,
                             String adr,
                             String cou,
                             String name, 
		             String description,
                             String ref_adr, 
                             String node_adr, 
                             String pic,
                             long days) throws Exception
   {
	   // Super class
	   super("ID_ADR_REGISTER_PACKET");
	   
	   // Builds the payload class
	   CAdrRegisterPayload dec_payload=new CAdrRegisterPayload(adr,
                                                                   cou,
                                                                   name, 
		                                                   description,
                                                                   ref_adr, 
                                                                   node_adr, 
                                                                   pic, 
                                                                   days);
			
	   // Build the payload
	   this.payload=UTILS.SERIAL.serialize (dec_payload);
			
	   // Network fee
	   CFeePayload fee=new CFeePayload(fee_adr,  0.0001*days, "Address register network fee");
	   this.fee_payload=UTILS.SERIAL.serialize(fee);
	   
	   // Sign packet
           this.sign();
    }
   
     public void check(CBlockPayload block) throws Exception
     {
	// Super class
	super.check(block);
		   	
	// Check type
	if (!this.tip.equals("ID_ADR_REGISTER_PACKET")) 
	    throw new Exception("Invalid packet type - CAdrRegisterPacket.java");
		  
	// Deserialize
	CAdrRegisterPayload payload=(CAdrRegisterPayload) UTILS.SERIAL.deserialize(this.payload);
        
        // Deserialize payload
        CFeePayload fee=(CFeePayload) UTILS.SERIAL.deserialize(fee_payload);
        
	// Check fee
	if (fee.amount<0.0001)
	   throw new Exception("Invalid fee - CAdrRegisterPacket.java");
		  
	// Check payload
	payload.check(block);
	          
        // Footprint
        CPackets foot=new CPackets(this);
        
        // Footprint data
        foot.add("Name", payload.name);
        foot.add("Description", payload.description);
        foot.add("Pic", payload.pic);
        foot.add("Ref Address", payload.ref_adr);
        foot.add("Node Address", payload.node_adr);
        foot.add("Pic", payload.pic);
        foot.write();
    }
	     
   
}
