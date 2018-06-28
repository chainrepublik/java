// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.misc;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import java.sql.ResultSet;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CGiftPayload extends CPayload
{
    // Adr
    String adr;
    
    // Serial
    private static final long serialVersionUID = 100L;
                                           
    public CGiftPayload(String adr, 
                         String target_adr) throws Exception
    {
        // Constructor
        super(adr);
       
        // Address
        this.adr=target_adr;
   
        // Hash
        hash=UTILS.BASIC.hash(this.getHash()+
                              this.adr);
	
    }
    
     public void check(CBlockPayload block) throws Exception
     {
        // Registered target adr ?
        if (!UTILS.BASIC.isRegistered(this.target_adr))
            throw new Exception("Unregistered target address");
         
        // Address creation block
        if (!UTILS.BASIC.getAdrData(this.adr, "created").equals(""))
           if (this.block-Long.parseLong(UTILS.BASIC.getAdrData(this.adr, "created"))>1440)
              throw new Exception("Target address was registered over 1440 blocks ago");
        
        // Empty inventory
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM stocuri "
                                          + "WHERE adr='"+this.adr+"'");
        
        // Has data
        if (UTILS.DB.hasData(rs))
           throw new Exception("Non empty inventory");
        
        
        // Sender has gifts ?
        if (UTILS.ACC.getBalance(this.target_adr, "ID_GIFT")<1)
            throw new Exception("Insuficient gifts balance");
        
        // Check hash
        String h=UTILS.BASIC.hash(this.getHash()+
                                  this.adr);
           
        if (!h.equals(hash))
           throw new Exception("Invalid hash - CRenewPayload.java");
         
        // Transactions
        UTILS.ACC.newTransfer(this.target_adr, 
                              this.adr, 
                              1, 
                              "ID_GIFT", 
                              "Welcome gift", 
                              "", 
                              0, 
                              hash, 
                              this.block,
                              false,
                              "",
                              "");
        
    }
	 
    public void commit(CBlockPayload block) throws Exception
    {
        // Superclass
	super.commit(block);
        
        // Increase energy
        UTILS.DB.executeUpdate("UPDATE adr "
                                + "SET energy=25 "
                              + "WHERE adr='"+this.adr+"'");
       
        // Clear
        UTILS.ACC.clearTrans(this.hash, "ID_ALL", this.block);
        
        // Refresh energy
        UTILS.BASIC.refreshEnergy(this.adr);
        
        // Event
        UTILS.BASIC.newEvent(this.adr, "Congratulations. You have received a welcome gift. Your energy has been increased to 25 and you will get up to 10 points of energy / day for the next 30 days.", this.block);
    }
}


