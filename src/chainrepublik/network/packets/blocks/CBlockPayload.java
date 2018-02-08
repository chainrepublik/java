// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.blocks;

import chainrepublik.kernel.CAddress;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CBroadcastPacket;
import chainrepublik.network.packets.CPayload;
import java.util.ArrayList;

import chainrepublik.network.packets.trans.CFeePayload;



public class CBlockPayload extends CPayload
{	
    // Serial
   private static final long serialVersionUID = 100L;
   
	// Packets list
	public ArrayList<CBroadcastPacket> packets=new ArrayList<CBroadcastPacket>();
	
	
	public CBlockPayload(String adr)  throws Exception
	{
            // Constructor
            super(adr);
            
	    // ID
	    this.block=UTILS.NET_STAT.last_block+1;
            
            // Hash
            this.hash();
	}
	
	public boolean exist(CBroadcastPacket packet) throws Exception
	{
            // Check packet hash
            for (int a=0; a<=this.packets.size()-1; a++)
            {
                // Load packet
                CBroadcastPacket p=(CBroadcastPacket)this.packets.get(a);
                
                // Check packet hash
		if (packet.hash.equals(p.hash))
		   return true;
                
                // Check payoad hash
		if (packet.hash.equals(UTILS.BASIC.hash(p.payload)))
		   return true;
                
                // Check fee hash
		if (packet.hash.equals(UTILS.BASIC.hash(p.fee_payload)))
		   return true;
            }
            
            // Not found
            return false;
	}
	
	public void addPacket(CBroadcastPacket packet) throws Exception
	{
            // Packet exist ?
	    if (this.exist(packet)) return;
                
            // Block number
            if (this.block!=packet.block)
               throw new Exception("Invalid bock number"); 
                
            // Add
            if (this.packets.size()<100 && (UTILS.SERIAL.serialize(this.packets).length+packet.payload.length)<250000)
            {
	       this.packets.add(packet); 
            }
            else
            {
               for (int a=0; a<=this.packets.size()-1; a++)
               {
                   // Load packet
                   CBroadcastPacket p=(CBroadcastPacket)this.packets.get(a);
                   
                   CFeePayload fee=(CFeePayload) UTILS.SERIAL.deserialize(p.fee_payload);
                   
                   // Smaller net fee ?
                   if (fee.amount/p.payload.length<fee.amount/packet.payload.length)
                   {
                       // Remove
                       this.packets.remove(a);
                       
                       // Add new packet
                       this.packets.add(packet);
                   }
               }
            }
           
            // Move feeds at the end
            for (int a=0; a<=this.packets.size()-1; a++)
            {
                // Load packet data
                CBroadcastPacket p=(CBroadcastPacket)this.packets.get(a);
                       
                // Feed packet ?
                if (p.tip.equals("ID_FEED_PACKET"))
                {
                    // Remove packet
                    this.packets.remove(a);
                    
                     // Add packet at the end
                    this.packets.add(p);
                }   
            }
            
            // Recalculate hash
            this.hash();
        }
	
	// Hash
	public void hash() throws Exception
	{
	   String hash=UTILS.BASIC.hash(this.getHash()+
                                        UTILS.BASIC.hash(UTILS.SERIAL.serialize(this.packets))); 
           
           // Return
           this.hash=hash;
           
           // Sign
           sign();
	}
	
	
	// Checks the block integrity
	public void check() throws Exception
	{
            //  Check
            super.check(this);
            
            // Check hash
            String h=UTILS.BASIC.hash(this.getHash()+
                                     UTILS.BASIC.hash(UTILS.SERIAL.serialize(this.packets)));
            
            // Hash match ?
            if (!this.hash.equals(h))
                throw new Exception ("Invalid hash, CBlockPayload.java, line 171");
            
            // Check packets
            for (int a=0; a<=this.packets.size()-1; a++)
	    {
                // Packet
		CBroadcastPacket packet=((CBroadcastPacket)this.packets.get(a));
		
                // Check
                packet.check(this);
            }
        }
        
        // Commits all transactions
	public void commit()  throws Exception
	{
            for (int a=0; a<=this.packets.size()-1; a++)
	    {
                // Packet
		CBroadcastPacket p=((CBroadcastPacket)this.packets.get(a));
                   
                // Commit
                p.commit(this);
            }
        }
}