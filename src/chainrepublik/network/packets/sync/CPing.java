package chainrepublik.network.packets.sync;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.CPeer;
import chainrepublik.network.packets.CPacket;

public class CPing extends CPacket
{
    // Time
    long local_time;
    
    // Software version
    String ver;
    
    public CPing() throws Exception
    {
       // Constructor
       super("ID_PING_PACKET");
       
       // Time
       this.local_time=UTILS.BASIC.tstamp();
       
       // Version
       this.ver=UTILS.STATUS.version;
       
       // Hash
       this.hash=UTILS.BASIC.hash(this.hash()+this.ver);
    }
    
    public void check(CPeer sender) throws Exception
    {
        // Sender
        if (sender.adr!=null
            && !UTILS.BASIC.isIP(sender.adr))
            throw new Exception("Invalid sender");
        
        // Update
        UTILS.DB.executeUpdate("UPDATE peers "
                                + "SET last_seen='"+this.tstamp+"', "
                                    + "ver='"+this.ver+"' "
                              + "WHERE peer='"+sender.adr+"'");
    }
}
