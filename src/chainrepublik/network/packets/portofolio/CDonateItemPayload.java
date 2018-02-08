package chainrepublik.network.packets.portofolio;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CDonateItemPayload extends CPayload
{
    // Item ID
    long itemID;
    
    // Target adr
    String rec_adr;
    
     // Item ID
    public CDonateItemPayload(String adr, 
                              long itemID,
                              String rec_adr) throws Exception
    {
        // Superclass
	super(adr);
        
        // Type
        this.itemID=itemID;
        
        // Price
        this.rec_adr=rec_adr;
    
        // Hash
 	hash=UTILS.BASIC.hash(this.getHash()+
 			      this.itemID+
                              this.rec_adr);
           
        // Sign
        this.sign();
    }
    
    public void check(CBlockPayload block) throws Exception
    {
   	// Super class
   	super.check(block);
        
         // Check energy
       this.checkEnergy();
        
        // Registered
        if (UTILS.BASIC.isRegistered(this.target_adr))
            throw new Exception("Target address is not registered, CDonateItemPayload.java, 102");
        
        // Receiver same as sender
        if (this.rec_adr.equals(this.target_adr))
            throw new Exception("Sender and receiver are the same, CDonateItemPayload.java, 102");
        
        // Item ID
        if (!UTILS.BASIC.isID(itemID))
            throw new Exception("Invalid itemID, CDonateItemPayload.java, 102");
        
        // Load itemID data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM stocuri "
                                          + "WHERE stocID='"+this.itemID+"' "
                                            + "AND adr='"+this.target_adr+"' "
                                            + "AND qty>=1");
        
        // Has data ?
        if (!UTILS.DB.hasData(rs))
           throw new Exception("Invalid itemID, CDonateItemPayload.java, 102");
        
        // Can receive ?
        if (!UTILS.BASIC.canBuy(this.rec_adr, rs.getString("tip"), 1, block))
           throw new Exception("Item can't be donated, CDonateItemPayload.java, 102");
        
        // Not a company address
        if (UTILS.BASIC.isCompanyAdr(this.target_adr))
            throw new Exception("Companies can't donate items, CDonateItemPayload.java, 102");
        
        // Check hash
        String h=UTILS.BASIC.hash(this.getHash()+
 			          this.itemID+
                                  this.rec_adr);
        
        // Hash match ?
        if (!h.equals(this.hash))
           throw new Exception("This item is rented, CDonateItemPayload.java, 102");
    }
    
    public void commit(CBlockPayload block) throws Exception
    {
         // Superclass
         super.commit(block);
         
         // Set item as rented
         UTILS.DB.executeUpdate("UPDATE stocuri "
                                 + "SET adr='"+this.target_adr+"' "
                               + "WHERE stocID='"+this.itemID+"'");
         
         // Position type
        UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
    }
}
