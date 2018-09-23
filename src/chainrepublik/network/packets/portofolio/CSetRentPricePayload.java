package chainrepublik.network.packets.portofolio;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CSetRentPricePayload extends CPayload
{
   // Item ID
   long itemID;

   // Price
   double price;
   
   // Serial
    private static final long serialVersionUID = 100L;
    
   public CSetRentPricePayload(String adr, 
                               long itemID, 
                               double price) throws Exception
    {
        // Superclass
	super(adr);
        
        // Type
        this.itemID=itemID;
        
        // Price
        this.price=price;
    
        // Hash
 	hash=UTILS.BASIC.hash(this.getHash()+
 			      this.itemID+
                              this.price);
       
    }
    
    public void check(CBlockPayload block) throws Exception
    {
   	// Super class
   	super.check(block);
        
         // Check energy
         this.checkEnergy();
        
        // Citizen address ?
        if (!UTILS.BASIC.isCitAdr(this.target_adr, this.block))
           throw new Exception("Only citizens can do this action - CWorkPayload.java, 68");
      
        // Load itemID data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM stocuri "
                                          + "WHERE stocID='"+this.itemID+"' "
                                            + "AND adr='"+this.target_adr+"' "
                                            + "AND qty=1 "
                                            + "AND expires>"+(this.block+1440));
        
        // Has data ?
        if (!UTILS.DB.hasData(rs))
           throw new Exception("Invalid itemID, CRentPayload.java, 102");
        
        // Next
        rs.next();
        
        // Can rent ?
        if (!UTILS.BASIC.canRent(this.target_adr, rs.getString("tip")))
           throw new Exception("Item can't be rented, CRentPayload.java, 102");
        
        // Price
        if (this.price<0.0001)
           throw new Exception("Invalid price, CRentPayload.java, 102"); 
        
        // Check hash
        String h=UTILS.BASIC.hash(this.getHash()+
 			          this.itemID+
                                  this.price);
        
        // Hash match ?
        if (!h.equals(this.hash))
           throw new Exception("Invalid hash, CRentPayload.java, 102");
    }
    
    public void commit(CBlockPayload block) throws Exception
    {
         // Superclass
         super.commit(block);
         
          // Set item as rented
         UTILS.DB.executeUpdate("UPDATE stocuri "
                                 + "SET rent_price='"+this.price+"' "
                               + "WHERE stocID='"+this.itemID+"'");
         
         // Clear trans
         UTILS.ACC.clearTrans(this.hash, "ID_ALL", this.block);
    }
}
