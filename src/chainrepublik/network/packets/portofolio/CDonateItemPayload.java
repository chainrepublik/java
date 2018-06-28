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
       
    }
    
    public void check(CBlockPayload block) throws Exception
    {
   	// Super class
   	super.check(block);
        
        // Check energy
        this.checkEnergy();
        
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
        
        // Next
        rs.next();
        
        // Energy product ?
        if (!UTILS.BASIC.isEnergyProd(rs.getString("tip")))
           throw new Exception("Item can't be donated, CDonateItemPayload.java, 102");
        
        // Citizen address
        if (!UTILS.BASIC.isCitAdr(this.target_adr) || 
            !UTILS.BASIC.isCitAdr(this.rec_adr))
        throw new Exception("Only citizens can donate / receive donations, CDonateItemPayload.java, 102");
        
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
                                 + "SET adr='"+this.rec_adr+"', "
                                     + "in_use=0 "
                               + "WHERE stocID='"+this.itemID+"'");
         
         // Event
         UTILS.BASIC.newEvent(this.rec_adr, 
                              "You have received a donation from "+UTILS.BASIC.getAdrData(this.target_adr, "name")+". Check your inventory.", 
                              this.block);
    }
}
