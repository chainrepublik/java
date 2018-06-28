package chainrepublik.network.packets.portofolio;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CConsumeItemPayload extends CPayload
{
   // Item ID
   long itemID;
 
   public CConsumeItemPayload(String adr, 
                              long itemID) throws Exception
    {
        // Superclass
	super(adr);
        
        // Type
        this.itemID=itemID;
        
        // Hash
 	hash=UTILS.BASIC.hash(this.getHash()+
 			      this.itemID);
          
    }
    
    public void check(CBlockPayload block) throws Exception
    {
   	// Super class
   	super.check(block);
        
        // Registered
        if (!UTILS.BASIC.isRegistered(this.target_adr))
            throw new Exception("Target address is not registered, CRentPayload.java, 102");
        
        // Item ID
        if (!UTILS.BASIC.isID(itemID))
            throw new Exception("Invalid itemID, CConsumeItemPayload.java, 102");
        
        // Load itemID data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM stocuri "
                                          + "WHERE stocID='"+this.itemID+"' "
                                            + "AND adr='"+this.target_adr+"' "
                                            + "AND qty>=1");
        
        // Has data ?
        if (!UTILS.DB.hasData(rs))
           throw new Exception("Invalid itemID, CConsumeItemPayload.java, 102");
        
        // Next
        rs.next();
        
        // Can consume ?
        if (!UTILS.BASIC.canConsume(this.target_adr, rs.getString("tip")))
           throw new Exception("Item can't be consumed, CConsumeItemPayload.java, 102");
        
        // Already consumed this item in the last 24 hours ?
        rs=UTILS.DB.executeQuery("SELECT * "
                                 + "FROM items_consumed "
                                + "WHERE adr='"+this.target_adr+"' "
                                  + "AND tip='"+rs.getString("tip")+"' "
                                  + "AND block>'"+(this.block-1440)+"'");
        
        // Has data ?
        if (UTILS.DB.hasData(rs))
           throw new Exception("Item has been already consumed in the last 24 hours, CConsumeItemPayload.java, 102");
        
        // Check hash
        String h=UTILS.BASIC.hash(this.getHash()+
 			          this.itemID);
        
        // Hash match ?
        if (!h.equals(this.hash))
           throw new Exception("Invalid hash, CConsumeItemPayload.java, 102");
    }
    
    public void commit(CBlockPayload block) throws Exception
    {
         // Superclass
         super.commit(block);
         
         // Load item data
         ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                            + "FROM stocuri "
                                           + "WHERE stocID='"+this.itemID+"'");
         
         // Next ?
         rs.next();
         
         // Insert consumed
         UTILS.DB.executeUpdate("INSERT INTO items_consumed "
                                      + "SET adr='"+this.target_adr+"', "
                                          + "tip='"+rs.getString("tip")+"', "
                                          + "block='"+this.block+"'");
         
         // Energy
         double energy=UTILS.BASIC.getProdEnergy(this.itemID);
         
         // Update energy
         UTILS.DB.executeUpdate("UPDATE adr "
                                 + "SET energy=energy+"+energy
                               +" WHERE adr='"+this.target_adr+"'");
         
         // Set item as rented
         UTILS.DB.executeUpdate("DELETE FROM stocuri "
                                     + "WHERE stocID='"+this.itemID+"'");
    }
}
