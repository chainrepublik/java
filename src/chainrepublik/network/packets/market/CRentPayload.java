package chainrepublik.network.packets.market;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CRentPayload extends CPayload
{
    // Item ID
    long itemID;
    
    // Days
    long days;
    
    public CRentPayload(String adr, 
                        long itemID, 
                        long days) throws Exception
    {
        // Superclass
	super(adr);
        
        // Type
        this.itemID=itemID;
        
        // Qty
        this.days=days;
    
        // Hash
 	hash=UTILS.BASIC.hash(this.getHash()+
 			      this.itemID+
                              this.days);
    }
    
    public void check(CBlockPayload block) throws Exception
    {
   	// Super class
   	super.check(block);
        
         // Citizen address ?
        if (!UTILS.BASIC.isCitAdr(this.target_adr, this.block))
           throw new Exception("Only citizens can do this action - CWorkPayload.java, 68");
        
        // Days
        if (this.days<1)
            throw new Exception("Invalid days, CRentPayload.java, 102");
        
        // Item ID
        if (!UTILS.BASIC.isID(itemID))
            throw new Exception("Invalid itemID, CRentPayload.java, 102");
        
        // Load itemID data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM stocuri "
                                          + "WHERE stocID='"+this.itemID+"' "
                                            + "AND adr<>'"+this.target_adr+"' "
                                            + "AND qty>=1 "
                                            + "AND expires>"+(this.block+1440)+" "
                                            + "AND rented_expires=0 "
                                            + "AND rent_price>0 "
                                            + "AND in_use=0");
        
        // Has data ?
        if (!UTILS.DB.hasData(rs))
           throw new Exception("Invalid itemID, CRentPayload.java, 102");
        
        // Next
        rs.next();
        
        // Can rent
        if (!UTILS.BASIC.canRent(this.target_adr, rs.getString("tip")))
           throw new Exception("Item can't be rented, CRentPayload.java, 102");
        
        // Price
        double price=rs.getDouble("rent_price")*days;
        
        // Funds ?
        if (UTILS.ACC.getBalance(this.target_adr, "CRC")<price)
            throw new Exception("Insuficient funds, CRentPayload.java, 102");
        
        // Hash
        String h=UTILS.BASIC.hash(this.getHash()+
 			          this.itemID+
                                  this.days);
        
        // Hashes matches ?
        if (!h.equals(this.hash))
           throw new Exception("Invalid hash, CRentPayload.java, 102");
        
        // Transfer funds
        UTILS.ACC.newTransfer(this.target_adr, 
                              rs.getString("adr"), 
                              price, 
                              "CRC", 
                              "You have rented "+rs.getString("tip")+" for "+this.days+" days", 
                              "", 
                              0,
                              hash, 
                              this.block,
                              true,
                              "ID_RENT_TAX",
                              "");
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
         
         // Set item as rented
         UTILS.DB.executeUpdate("UPDATE stocuri "
                                 + "SET rented_to='"+this.target_adr+"', "
                                     + "rented_expires='"+(this.block+this.days*1440)+"', "
                                     + "in_use=0 "
                               + "WHERE stocID='"+this.itemID+"'");
         
         // Insert rent process
         UTILS.DB.executeUpdate("INSERT INTO rent_contracts "
                                      + "SET stocID='"+this.itemID+"', "
                                          + "from_adr='"+rs.getString("adr")+"', "
                                          + "to_adr='"+this.target_adr+"', "
                                          + "price='"+rs.getDouble("rent_price")+"', "
                                          + "expires='"+(this.block+this.days*1440)+"', "
                                          + "block='"+this.block+"'");
         
         // Clear trans
         UTILS.ACC.clearTrans(this.hash, "ID_ALL", this.block);
    }
}
