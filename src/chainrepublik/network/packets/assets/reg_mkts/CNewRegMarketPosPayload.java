// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.assets.reg_mkts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import chainrepublik.network.packets.trans.CTransPayload;

public class CNewRegMarketPosPayload extends CPayload
{
    // Market symbol
    long mktID;
    
    // Tip
    String tip;
                                   
    // Price
    double price;
                                   
    // Qty
    double qty;
                                   
    // Market days
    long days;
    
    // Order ID
    long orderID;
    
                                         
    public CNewRegMarketPosPayload(String adr,
                                   long mktID,
                                   String tip,
                                   double price,
                                   double qty,
                                   long days) throws Exception
    {
        // Constructor
        super(adr);
        
        // Market symbol
        this.mktID=mktID;
    
        // Tip
        this.tip=tip;
                                   
        // Price
        this.price=price;
        
        // Load market data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM assets_mkts "
                                          + "WHERE mktID='"+this.mktID+"'");
        
        // Next
        rs.next();
        
        // Decimals
        long decimals=rs.getLong("decimals");
         
        // Qty
        this.qty=UTILS.BASIC.round(qty, (int)decimals);
                                   
        // Market days
        this.days=days;
        
        // Order ID
        this.orderID=UTILS.BASIC.getID();
        
        // Hash
        hash=UTILS.BASIC.hash(this.getHash()+
                              this.mktID+
                              this.tip+
                              String.valueOf(this.price)+
                              String.valueOf(this.qty)+
                              String.valueOf(this.days)+
                              this.orderID);
        
        // Sign
        this.sign();
    }
    
     public void check(CBlockPayload block) throws Exception
     {
         // Super class
   	  super.check(block);
          
          // Registered ?
          if (!UTILS.BASIC.isRegistered(this.target_adr))
             throw new Exception("Address not registered - CNewRegMarketPosPayload.java");
          
        // Check marketID
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM assets_mkts "
                                          + "WHERE mktID='"+this.mktID+"'");
            
        if (!UTILS.DB.hasData(rs))
           throw new Exception("Invalid market ID - CNewRegMarketPosPayload.java");
            
        // Next
        rs.next();
        
        // Order expire after market ?
        long expire=this.block+this.days*1440;
        
        if (expire>rs.getLong("expires") && 
            rs.getLong("expires")>0)
           throw new Exception("Invalid expiration date - CNewRegMarketPosPayload.java");
        
        // Tip
        if (!this.tip.equals("ID_BUY") && 
           !this.tip.equals("ID_SELL"))
             throw new Exception("Invalid order type- CNewRegMarketPosPayload.java");
        
        // Can spend
        if (!UTILS.BASIC.canSpend(this.target_adr))
           throw new Exception("Address can't spend coins - CNewRegMarketPosPayload.java");
        
        // Price
        if (this.price<0.00000001)
           throw new Exception("Invalid price - CNewRegMarketPosPayload.java");
        
        // Qty
        this.qty=UTILS.BASIC.round(qty, (int)rs.getLong("decimals"));
        
        if (this.qty<0.00000001)
           throw new Exception("Invalid qty - CNewRegMarketPosPayload.java");
        
        // Market days
        if (this.days<1)
           throw new Exception("Invalid days - CNewRegMarketPosPayload.java");
        
        // Hash
        String h=UTILS.BASIC.hash(this.getHash()+
                                  this.mktID+
                                  this.tip+
                                  String.valueOf(this.price)+
                                  String.valueOf(this.qty)+
                                  String.valueOf(this.days)+
                                  this.orderID);
             
        // Valid hash
        if (!this.hash.equals(h))
           throw new Exception("Invalid hash - CNewRegMarketPosPayload.java");
        
        // Asset
        String asset=rs.getString("asset");
        
        // Cur
        String cur=rs.getString("cur");
        
        // Asset balance
        double balance_asset=UTILS.ACC.getBalance(this.target_adr, rs.getString("asset"), block);
                    
        // Currency balance
        double balance_cur=UTILS.ACC.getBalance(this.target_adr, rs.getString("cur"), block);
            
        // Sell
        if (this.tip.equals("ID_SELL"))
        {
            // Can sell ?
            if (!UTILS.BASIC.canSell(this.target_adr, asset) && 
                asset.length()!=5 && 
                asset.length()!=6)
                throw new Exception("Address is not allowed to sell this product - CNewRegMarketPosPayload.java");
                
            // Asset balance
            if (balance_asset<this.qty)
                throw new Exception("Innsuficient assets - CNewRegMarketPosPayload.java");
                
            // Buy orders
            rs=UTILS.DB.executeQuery("SELECT * "
                                     + "FROM assets_mkts_pos "
                                    + "WHERE mktID='"+this.mktID+"' "
                                      + "AND tip='ID_BUY' "
                                      + "AND price>="+this.price+" "
                                 + "ORDER BY price DESC");
                
            // Sold
            double remain=this.qty;
                
            // Qty
            double qty;
               
            // Has data ?
            if (UTILS.DB.hasData(rs))
            {
                   // Load assets
                   while (rs.next())
                   {
                      if (remain>0)
                      {
                         // Remain
                         if (remain>=rs.getDouble("qty"))
                            qty=rs.getDouble("qty");
                         else
                            qty=remain;
                          
                         // Transfer assets
                         UTILS.ACC.newTransfer(this.target_adr, 
                                                 rs.getString("adr"),
                                                 qty,
                                                 asset, 
                                                 "New short position on market "+rs.getString("mktID"), 
                                                 "", 
                                                 qty*rs.getDouble("price"),
                                                 this.hash, 
                                                 this.block);
                      
                         // Receive coins
                         UTILS.ACC.newTrans(this.target_adr,
                                              "none", 
                                              qty*rs.getDouble("price"),
                                              cur, 
                                              "New short position on market "+rs.getString("mktID"), 
                                              "", 
                                              0,
                                              this.hash, 
                                              this.block);
                         
                         // Income tax ?
                         if (cur.equals("CRC") && 
                             UTILS.BASIC.isProd(asset))
                             UTILS.ACC.bugTax(this.target_adr, 
                                              "ID_SALE_TAX", 
                                              qty*rs.getDouble("price"), 
                                              asset,
                                              this.hash, 
                                              this.block);
                         
                         // Remain
                         remain=remain-qty;
                      }
                   }
                }
                
                // Put the rest on market
                if (remain>0)
                UTILS.ACC.newTrans(this.target_adr,
                                     "none", 
                                     -remain,
                                     asset, 
                                     "New short position on market "+this.mktID, 
                                     "", 
                                     0,
                                     this.hash, 
                                     this.block);
        }
        else
        {
                // Asset balance
                if (balance_cur<this.qty*this.price)
                     throw new Exception("Innsuficient funds - CNewRegMarketPosPayload.java");
                
                // Buy orders
                rs=UTILS.DB.executeQuery("SELECT * "
                                         + "FROM assets_mkts_pos "
                                        + "WHERE mktID='"+this.mktID+"' "
                                          + "AND tip='ID_SELL' "
                                          + "AND price<="+this.price+" "
                                     + "ORDER BY price ASC");
                
               // Sold
               double remain=this.qty;
                
               // Qty
               double qty;
               
                // Has data ?
                if (UTILS.DB.hasData(rs))
                {
                   // Load assets
                   while (rs.next())
                   {
                      if (remain>0)
                      {
                         // Remain
                         if (remain>=rs.getDouble("qty"))
                            qty=rs.getDouble("qty");
                         else
                            qty=remain;
                          
                         // Transfer currency
                         UTILS.ACC.newTransfer(this.target_adr, 
                                                 rs.getString("adr"),
                                                 qty*rs.getDouble("price"),
                                                 cur, 
                                                 "New long position on market "+rs.getString("mktID"), 
                                                 "", 
                                                 0,
                                                 this.hash, 
                                                 this.block);
                         
                         // Income tax ?
                         if (cur.equals("CRC") && 
                             UTILS.BASIC.isProd(asset))
                             UTILS.ACC.bugTax(rs.getString("adr"), 
                                              "ID_SALE_TAX", 
                                              qty*rs.getDouble("price"), 
                                              asset,
                                              this.hash, 
                                              this.block);
                      
                         // Receive assets
                         UTILS.ACC.newTrans(this.target_adr,
                                              "none", 
                                              qty,
                                              asset, 
                                              "New long position on market "+rs.getString("mktID"), 
                                              "", 
                                              qty*rs.getDouble("price"),
                                              this.hash, 
                                              this.block);
                         
                        // Remain
                        remain=remain-qty;
                      }
                   }
                }
                
                // Receive coins
                if (remain>0)
                UTILS.ACC.newTrans(this.target_adr,
                                     "none", 
                                     -remain*this.price,
                                     cur, 
                                     "New long position on market "+this.mktID, 
                                     "", 
                                     0,
                                     this.hash, 
                                     this.block);
                
                // Can buy ?
                if (!UTILS.BASIC.canBuy(this.target_adr, asset, this.qty, block) &&
                    asset.length()!=5 && 
                    asset.length()!=6)
                throw new Exception("Address is not allowed to sell this product - CNewRegMarketPosPayload.java");
            }
            
        
     }
    
    public void commit(CBlockPayload block) throws Exception
    {
        // Super
        super.commit(block);
        
        // Load mkt data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM assets_mkts "
                                          + "WHERE mktID='"+this.mktID+"'");
            
        // Next
        rs.next();
        
        // Asset
        String asset=rs.getString("asset");
        
        // Cur
        String cur=rs.getString("cur");
        
        // Last price
        double last_price=rs.getDouble("last_price");
        
        // Asset balance
        double balance_asset=UTILS.ACC.getBalance(this.target_adr, rs.getString("asset"), block);
                    
        // Currency balance
        double balance_cur=UTILS.ACC.getBalance(this.target_adr, rs.getString("cur"), block);
        
        // Sold
        double remain=this.qty;
            
        // Sell
        if (this.tip.equals("ID_SELL"))
        {
            // Buy orders
            rs=UTILS.DB.executeQuery("SELECT * "
                                     + "FROM assets_mkts_pos "
                                    + "WHERE mktID='"+this.mktID+"' "
                                      + "AND tip='ID_BUY' "
                                      + "AND price>="+this.price+" "
                                 + "ORDER BY price DESC");
                
                
            // Qty
            double qty=0;
               
            // Has data ?
            if (UTILS.DB.hasData(rs))
            {
                   // Load assets
                   while (rs.next())
                   {
                      if (remain>0)
                      {
                         // Remain
                         if (remain>=rs.getDouble("qty"))
                         {
                            // Qty
                            qty=rs.getDouble("qty");
                            
                            // Remove order
                            UTILS.DB.executeUpdate("DELETE FROM assets_mkts_pos "
                                                       + "WHERE orderID='"+rs.getLong("orderID")+"'");
                         }
                         else
                         {
                            // Qty
                            qty=remain;
                            
                            // Update
                            UTILS.DB.executeUpdate("UPDATE assets_mkts_pos "
                                                    + "SET qty=qty-"+qty
                                                 + " WHERE orderID='"+rs.getLong("orderID")+"'");
                         }
                         
                         // Remain
                         remain=remain-qty;
                         
                         // Insert order
                         UTILS.DB.executeUpdate("INSERT INTO assets_mkts_trades "
                                                      + "SET mktID='"+this.mktID+"', "
                                                          + "orderID='"+this.orderID+"', "
                                                          + "buyer='"+this.target_adr+"', "
                                                          + "seller='"+rs.getString("adr")+"', "
                                                          + "qty='"+qty+"', "
                                                          + "price='"+rs.getDouble("price")+"', "
                                                          + "block='"+this.block+"'");
                      }
                   }
                }
        }
        else
        {
            // Buy orders
            rs=UTILS.DB.executeQuery("SELECT * "
                                  + "FROM assets_mkts_pos "
                                 + "WHERE mktID='"+this.mktID+"' "
                                   + "AND tip='ID_SELL' "
                                   + "AND price<="+this.price+" "
                              + "ORDER BY price ASC");
                
               // Qty
               double qty=0;
               
                // Has data ?
                if (UTILS.DB.hasData(rs))
                {
                   // Load assets
                   while (rs.next())
                   {
                      if (remain>0)
                      {
                         // Remain
                         if (remain>=rs.getDouble("qty"))
                         {
                            // Qty
                            qty=rs.getDouble("qty");
                            
                            // Remove order
                            UTILS.DB.executeUpdate("DELETE FROM assets_mkts_pos "
                                                       + "WHERE orderID='"+rs.getLong("orderID")+"'");
                         }
                         else
                         {
                            // Qty
                            qty=remain;
                            
                            // Update
                            UTILS.DB.executeUpdate("UPDATE assets_mkts_pos "
                                                    + "SET qty=qty-"+qty
                                                  + " WHERE orderID='"+rs.getLong("orderID")+"'");
                         }
                         
                         // Remain
                         remain=remain-qty;
                         
                         // Insert order
                         UTILS.DB.executeUpdate("INSERT INTO assets_mkts_trades "
                                                      + "SET mktID='"+this.mktID+"', "
                                                          + "orderID='"+this.orderID+"', "
                                                          + "buyer='"+this.target_adr+"', "
                                                          + "seller='"+rs.getString("adr")+"', "
                                                          + "qty='"+qty+"', "
                                                          + "price='"+rs.getDouble("price")+"', "
                                                          + "block='"+this.block+"'");
                      }
                   }
                }
        }
            
        
        // Receive coins
        if (remain>0)
        {
            // Another order online ?
            rs=UTILS.DB.executeQuery("SELECT * "
                                     + "FROM assets_mkts_pos "
                                    + "WHERE adr='"+this.target_adr+"' "
                                      + "AND mktID='"+this.mktID+"' "
                                      + "AND tip='"+this.tip+"' "
                                      + "AND price='"+this.price+"'");
                    
            if (UTILS.DB.hasData(rs))
            {
                // Next
                rs.next();
                
                // Update
                UTILS.DB.executeUpdate("UPDATE assets_mkts_pos "
                                        + "SET qty=qty+"+remain+" "
                                      + "WHERE orderID='"+rs.getLong("orderID")+"'");
            }
            else
                UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos "
                                             + "SET adr='"+this.target_adr+"',"
                                                 + "mktID='"+this.mktID+"', "
                                                 + "tip='"+this.tip+"', "
                                                 + "qty='"+remain+"', "
                                                 + "price='"+UTILS.FORMAT_8.format(this.price)+"', "
                                                 + "orderID='"+this.orderID+"', "
                                                 + "expires='"+(this.block+(this.days*1440))+"'");
        }
        
        // As / bid
        double ask=0;
        double bid=0;
        
        // Last price
        if (remain!=this.qty)
           last_price=this.price;
                   
        // Refresh ask / bid
        rs=UTILS.DB.executeQuery("SELECT * "
                                 + "FROM assets_mkts_pos "
                                + "WHERE mktID='"+this.mktID+"' "
                                  + "AND tip='ID_SELL' "
                             + "ORDER BY price ASC "
                                + "LIMIT 0,1");
        
        // Has data
        if (UTILS.DB.hasData(rs))
        {
            // Next
            rs.next();
            
            // Ask
            ask=rs.getDouble("price");
        }
        
        // Refresh ask / bid
        rs=UTILS.DB.executeQuery("SELECT * "
                                 + "FROM assets_mkts_pos "
                                + "WHERE mktID='"+this.mktID+"' "
                                  + "AND tip='ID_BUY' "
                             + "ORDER BY price DESC "
                                + "LIMIT 0,1");
        
        // Has data
        if (UTILS.DB.hasData(rs))
        {
            // Next
            rs.next();
            
            // Bid
            bid=rs.getDouble("price");
        }
        
        // Update
        UTILS.DB.executeUpdate("UPDATE assets_mkts "
                                + "SET ask='"+ask+"', "
                                    + "bid='"+bid+"', "
                                    + "last_price='"+last_price+"' "
                              + "WHERE mktID='"+this.mktID+"'");
        
        // Clear
        UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
        
    }      
    
    
}
