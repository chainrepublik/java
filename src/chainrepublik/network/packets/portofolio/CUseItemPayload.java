package chainrepublik.network.packets.portofolio;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CUseItemPayload extends CPayload
{
    // Item ID
    long itemID;
    
    // Serial
    private static final long serialVersionUID = 100L;
    
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
        
    }
    
    public void check(CBlockPayload block) throws Exception
    {
   	// Super class
   	super.check(block);
        
        // Citizen ?
        if (!UTILS.BASIC.isCitAdr(this.target_adr, this.block))
           throw new Exception("Target address is not a citizen, CRentPayload.java, 102");
        
        // Load itemID data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM stocuri "
                                          + "WHERE stocID='"+this.itemID+"' "
                                            + "AND (adr='"+this.target_adr+"' "
                                                 + "OR rented_to='"+this.target_adr+"') "
                                            + "AND qty=1");
        
        // Has data ?
        if (!UTILS.DB.hasData(rs))
           throw new Exception("Invalid itemID, CUseItemPayload.java, 102");
        
        // Next
        rs.next();
        
        // Targte address owner ? ?
        if (rs.getString("adr").equals(this.target_adr) && 
            !rs.getString("rented_to").equals(""))
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
                                 + "AND tip LIKE '%"+prod+"%'");
         
         // Set item as used
         UTILS.DB.executeUpdate("UPDATE stocuri "
                                 + "SET in_use='"+this.block+"' "
                               + "WHERE stocID='"+this.itemID+"'");
         
         // Refresh energy / block
         UTILS.BASIC.refreshEnergy(this.target_adr);
    }
}