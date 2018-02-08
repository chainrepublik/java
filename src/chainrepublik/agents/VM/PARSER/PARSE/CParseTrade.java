package chainrepublik.agents.VM.PARSER.PARSE;

import chainrepublik.agents.VM.CVM;
import chainrepublik.agents.VM.PARSER.CParser;
import chainrepublik.agents.VM.PARSER.GRAMMAR.LANG.testParser;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CParseTrade 
{
    // VM
    CVM VM;
    
    // Parser
    CParser parser;
    
    public CParseTrade(CVM VM, CParser parser)
    {
        // VM
        this.VM=VM;
        
        // Parser
        this.parser=parser;
    }
    
    public String parseTrade(testParser.F_tradeContext ctx)
    {
        try
        {
            // Reject
            if (this.VM.step(1, ctx.start.getLine()))
            {
                // Target address
                String target_adr=this.VM.SYSTEM.COM.ADR;
                
                // Product
                String asset=String.valueOf(parser.visit(ctx.expr_math(0)));
                
                // Market side
                String type=String.valueOf(parser.visit(ctx.expr_math(1)));
                
                // Qty
                double trade_qty=Double.parseDouble(String.valueOf(parser.visit(ctx.expr_math(2))));
                
                // Price
                double price=Double.parseDouble(String.valueOf(parser.visit(ctx.expr_math(3))));
                
                // Expires
                long expire=Long.parseLong(String.valueOf(parser.visit(ctx.expr_math(4))));
                
                // Block
                long block=this.VM.SYSTEM.BLOCK.BLOCK;
                
                // Block payload
                CBlockPayload block_payload=this.VM.SYSTEM.BLOCK.BLOCK_PAYLOAD;
                
                // Currency
                String cur="CRC";

                // Order ID
                long orderID=UTILS.BASIC.hashToLong(this.VM.trigger_hash)+this.VM.steps;
                
                // Check product
                if (!UTILS.BASIC.isStringID(asset))
                {
                     this.VM.setErr("Invalid product in trade function", ctx.getStart().getLine());
                     return "false"; 
                }
                
                // Valid order type
                if (!type.equals("ID_BUY") && 
                    !type.equals("ID_SELL"))
                {
                    this.VM.setErr("Invalid order type in trade function", ctx.getStart().getLine());
                    return "false"; 
                }
                
                // Valid expiring 
                if (expire<5 || 
                    expire>30)
                {
                    this.VM.setErr("Minimum blocks is 5 in trade function", ctx.getStart().getLine());
                    return "false"; 
                }
                
                // Expire
                long expire_block=this.VM.SYSTEM.BLOCK.BLOCK+expire;
                
                // Check marketID
                ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                                   + "FROM assets_mkts "
                                                  + "WHERE asset='"+asset+"'");
            
                if (!UTILS.DB.hasData(rs))
                {
                    this.VM.setErr("Invalid product in trade function", ctx.getStart().getLine());
                    return "false"; 
                }
                    
                // Next
                rs.next();
                
                // Market ID
                long mktID=rs.getLong("mktID");
                
                // Last price
                double last_price=rs.getDouble("last_price");
        
                if (expire_block>rs.getLong("expires") && 
                    rs.getLong("expires")>0)
                {
                    this.VM.setErr("Invalid expiration block in trade function", ctx.getStart().getLine());
                    return "false"; 
                }
                
                // Tip
                if (!type.equals("ID_BUY") && 
                    !type .equals("ID_SELL"))
                {
                    this.VM.setErr("Invalid order type in trade function", ctx.getStart().getLine());
                    return "false";
                }
               
                // Price
                if (price<0.00000001)
                {
                    this.VM.setErr("Invalid price in trade function", ctx.getStart().getLine());
                    return "false";
                }
        
                // Qty
                trade_qty=UTILS.BASIC.round(trade_qty, (int)rs.getLong("decimals"));
        
                if (trade_qty<0.00000001)
                {
                   this.VM.setErr("Invalid qty in trade function", ctx.getStart().getLine());
                   return "false"; 
                }
        
                // Asset balance
                double balance_asset=UTILS.ACC.getBalance(target_adr, rs.getString("asset"), block_payload);
                    
                // Currency balance
                double balance_cur=UTILS.ACC.getBalance(target_adr, rs.getString("cur"), block_payload);
            
                // Sell
                if (type.equals("ID_SELL"))
                {
                    // Can sell ?
                    if (!UTILS.BASIC.canSell(target_adr, asset))
                    {
                        this.VM.setErr("Address is not allowed to sell this product in trade function", ctx.getStart().getLine());
                        return "false"; 
                    }
                     
                    // Asset balance
                    if (balance_asset<trade_qty)
                    {
                        this.VM.setErr("Insuficient assets in trade function", ctx.getStart().getLine());
                        return "false"; 
                    }
                       
                    // Buy orders
                    rs=UTILS.DB.executeQuery("SELECT * "
                                             + "FROM assets_mkts_pos "
                                            + "WHERE mktID='"+mktID+"' "
                                              + "AND tip='ID_BUY' "
                                              + "AND price>="+price+" "
                                         + "ORDER BY price DESC");
                
                    // Sold
                    double remain=trade_qty;
                
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
                                UTILS.ACC.newTransfer(target_adr, 
                                                     rs.getString("adr"),
                                                     qty,
                                                     asset, 
                                                     "New short position on market "+rs.getString("mktID"), 
                                                     "", 
                                                     qty*rs.getDouble("price"),
                                                     this.VM.trigger_hash, 
                                                     this.VM.SYSTEM.BLOCK.BLOCK);
                      
                                // Receive coins
                                UTILS.ACC.newTrans(target_adr,
                                                   "none", 
                                                   qty*rs.getDouble("price"),
                                                   cur, 
                                                   "New short position on market "+rs.getString("mktID"), 
                                                   "", 
                                                   0,
                                                   this.VM.trigger_hash, 
                                                   this.VM.SYSTEM.BLOCK.BLOCK);
                         
                                // Remain
                                remain=remain-qty;
                         }
                    }
                }
                
                // Put the rest on market
                if (remain>0)
                UTILS.ACC.newTrans(target_adr,
                                   "none", 
                                   -remain,
                                   asset, 
                                   "New short position on market "+mktID, 
                                   "", 
                                   0,
                                   this.VM.trigger_hash, 
                                   this.VM.SYSTEM.BLOCK.BLOCK);
        }
        else
        {
            // Asset balance
            if (balance_cur<trade_qty*price)
            {
                this.VM.setErr("Insuficient funds", ctx.getStart().getLine());
                return "false"; 
            }
                
            // Buy orders
            rs=UTILS.DB.executeQuery("SELECT * "
                                     + "FROM assets_mkts_pos "
                                    + "WHERE mktID='"+mktID+"' "
                                      + "AND tip='ID_SELL' "
                                      + "AND price<="+price+" "
                                 + "ORDER BY price ASC");
                
            // Sold
            double remain=trade_qty;
                
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
                         UTILS.ACC.newTransfer(target_adr, 
                                               rs.getString("adr"),
                                               qty*rs.getDouble("price"),
                                               cur, 
                                               "New long position on market "+rs.getString("mktID"), 
                                               "", 
                                               0,
                                               this.VM.trigger_hash, 
                                               this.VM.SYSTEM.BLOCK.BLOCK);
                      
                         // Receive assets
                         UTILS.ACC.newTrans(target_adr,
                                            "none", 
                                            qty,
                                            asset, 
                                            "New long position on market "+rs.getString("mktID"), 
                                            "", 
                                            qty*rs.getDouble("price"),
                                            this.VM.trigger_hash, 
                                            this.VM.SYSTEM.BLOCK.BLOCK);
                         
                        // Remain
                        remain=remain-qty;
                      }
                   }
                }
                
                // Receive coins
                if (remain>0)
                UTILS.ACC.newTrans(target_adr,
                                   "none", 
                                   -remain*price,
                                   cur, 
                                   "New long position on market "+mktID, 
                                   "", 
                                   0,
                                   this.VM.trigger_hash, 
                                   this.VM.SYSTEM.BLOCK.BLOCK);
                
                // Can buy ?
                if (!UTILS.BASIC.canBuy(target_adr, asset, trade_qty, block_payload))
                {
                     this.VM.setErr("Address is not allowed to sell this product in trade function", ctx.getStart().getLine());
                     return "false"; 
                }
              }
            
               // Sold
               double remain=trade_qty;
            
               // Sell
               if (type.equals("ID_SELL"))
               {
                    // Buy orders
                    rs=UTILS.DB.executeQuery("SELECT * "
                                     + "FROM assets_mkts_pos "
                                    + "WHERE mktID='"+mktID+"' "
                                      + "AND tip='ID_BUY' "
                                      + "AND price>="+price+" "
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
                                                    + "SET qty=qty-"+qty+", "
                                                        + "block='"+block+"' "
                                                  + "WHERE orderID='"+rs.getLong("orderID")+"'");
                                }
                         
                                // Remain
                                remain=remain-qty;
                         
                                // Insert order
                                UTILS.DB.executeUpdate("INSERT INTO assets_mkts_trades "
                                                             + "SET mktID='"+mktID+"', "
                                                                 + "orderID='"+orderID+"', "
                                                                 + "buyer='"+target_adr+"', "
                                                                 + "seller='"+rs.getString("adr")+"', "
                                                                 + "qty='"+qty+"', "
                                                                 + "price='"+rs.getDouble("price")+"', "
                                                                 + "block='"+block+"'");
                      }
                   }
                 }
               }
              else
               {
                    // Buy orders
                    rs=UTILS.DB.executeQuery("SELECT * "
                                             + "FROM assets_mkts_pos "
                                            + "WHERE mktID='"+mktID+"' "
                                              + "AND tip='ID_SELL' "
                                              + "AND price<="+price+" "
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
                                                          + "SET qty=qty-"+qty+", "
                                                              + "block='"+block+"' "
                                                        + "WHERE orderID='"+rs.getLong("orderID")+"'");
                               }
                         
                               // Remain
                               remain=remain-qty;
                         
                                // Insert order
                                UTILS.DB.executeUpdate("INSERT INTO assets_mkts_trades "
                                                             + "SET mktID='"+mktID+"', "
                                                                  + "orderID='"+orderID+"', "
                                                                  + "buyer='"+target_adr+"', "
                                                                  + "seller='"+rs.getString("adr")+"', "
                                                                  + "qty='"+qty+"', "
                                                                  + "price='"+rs.getDouble("price")+"', "
                                                                  + "block='"+block+"'");
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
                                    + "WHERE adr='"+target_adr+"' "
                                      + "AND mktID='"+mktID+"' "
                                      + "AND tip='"+type+"' "
                                      + "AND price='"+price+"'");
                    
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
                                                     + "SET adr='"+target_adr+"',"
                                                         + "mktID='"+mktID+"', "
                                                         + "tip='"+type+"', "
                                                         + "qty='"+remain+"', "
                                                         + "price='"+UTILS.FORMAT_8.format(price)+"', "
                                                         + "orderID='"+orderID+"', "
                                                         + "expires='"+expire_block+"', "
                                                         + "block='"+block+"'");
                }
        
                // As / bid
                double ask=0;
                double bid=0;
        
                // Last price
                if (remain!=trade_qty)
                    last_price=price;
                   
                // Refresh ask / bid
                rs=UTILS.DB.executeQuery("SELECT * "
                                         + "FROM assets_mkts_pos "
                                        + "WHERE mktID='"+mktID+"' "
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
                                        + "WHERE mktID='"+mktID+"' "
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
                                      + "WHERE mktID='"+mktID+"'");
        
                // Clear
                UTILS.ACC.clearTrans(this.VM.trigger_hash, "ID_ALL", block);
            }      
        }
        catch (Exception ex)
        {
            this.VM.setErr("Unexpected error in trade function", ctx.getStart().getLine());
            return "false"; 
        }
        
        return "true"; 
    }
    
}
