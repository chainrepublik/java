package chainrepublik.network.packets.portofolio;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CUseItemPayload extends CPayload
{
    // Item ID
    long itemID;
    
    public CUseItemPayload(String adr, 
                           long itemID) throws Exception
    {
        // Superclass
	super(adr);
        
        // Type
        this.itemID=itemID;
        
        // Hash
 	hash=UTILS.BASIC.hash(this.getHash()+
 			      this.itemID);
           
        // Sign
        this.sign();
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
            throw new Exception("Invalid itemID, CUseItemPayload.java, 102");
        
        // Load itemID data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM stocuri "
                                          + "WHERE stocID='"+this.itemID+"' "
                                            + "AND adr='"+this.target_adr+"'");
        
        // Has data ?
        if (!UTILS.DB.hasData(rs))
           throw new Exception("Invalid itemID, CUseItemPayload.java, 102");
        
        // Next
        rs.next();
        
        // Already rented ?
        if (!rs.getString("rented_to").equals(""))
            throw new Exception("This item is rented, CUseItemPayload.java, 102");
        
        // Item can be used ?
        if (!UTILS.BASIC.isUsable(rs.getString("tip")))
           throw new Exception("This item can not be used, CUseItemPayload.java, 102");
        
        // Check hash
        String h=UTILS.BASIC.hash(this.getHash()+
 			           this.itemID);
        
        // Hash match ?
        if (!h.equals(this.hash))
           throw new Exception("This item is rented, CUseItemPayload.java, 102");
    }
    
    public void commit(CBlockPayload block) throws Exception
    {
         // Superclass
         super.commit(block);
         
         // Load item data
         ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                            + "FROM stocuri "
                                           + "WHERE stocID='"+this.itemID+"'");
         
         // Next
         rs.next();
         
         // Strip quality
         String prod=UTILS.BASIC.stripQuality(rs.getString("tip"));
         
         // Stop using similar item
         UTILS.DB.executeUpdate("UPDATE stocuri "
                                 + "SET in_use=0 "
                               + "WHERE adr='"+this.target_adr+"' "
                                 + "AND tip LIKE '%"+prod+"%' "
                                 + "AND in_use>0");
         
         // Set item as used
         UTILS.DB.executeUpdate("UPDATE stocuri "
                                 + "SET in_use='"+this.block+"' "
                               + "WHERE stocID='"+this.itemID+"'");
         
         // Refresh energy / block
         UTILS.BASIC.refreshEnergy(this.target_adr);
    }
}