// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.peers;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.CPeer;
import chainrepublik.network.packets.CPacket;

public class CGetPeersPacket extends CPacket
{
    // Serial
   private static final long serialVersionUID = 100L;
   
    public CGetPeersPacket() throws Exception
    {
        super("ID_GET_PEERS_PACKET");
        
        // Hash
        this.hash=UTILS.BASIC.hash(UTILS.BASIC.mtstamp()+this.tip);
    }
    
     public void checkWithPeer(CPeer peer) throws Exception
    {
         // Create response
         CGetPeersResponsePacket packet=new CGetPeersResponsePacket();
         peer.writePacket(packet);
    }
}
