package chainrepublik.kernel;
import java.sql.ResultSet;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.util.Random;

public class CAcc 
{
    public CAcc()
    {
        
    }
    
    public void clearTrans(String hash, String tip, long block) throws Exception
    {
        // Check hash
        if (!UTILS.BASIC.isHash(hash))
            throw new Exception("Invalid hash - CAcc.java, 19");
        
        // Load trans data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM trans "
                                           + "WHERE hash='"+hash+"' "
                                             + "AND status='ID_PENDING'");
                
        // Clear
        while (rs.next())
        {
            if ((rs.getDouble("amount")<0 && (tip.equals("ID_ALL") || tip.equals("ID_SEND"))) ||
                (rs.getDouble("amount")>0 && (tip.equals("ID_ALL") || tip.equals("ID_RECEIVE"))))
            {
                // Asset
                if (!rs.getString("cur").equals("CRC") && 
                    rs.getString("cur").indexOf("_")==-1)
                   this.doAssetTrans(rs.getString("src"), 
                                     rs.getDouble("amount"), 
                                     rs.getString("cur"),
                                     block);
               
                // Product
                if (!rs.getString("cur").equals("CRC") && 
                    rs.getString("cur").indexOf("_")>=0)
                   this.doProdTrans(rs.getString("src"), 
                                     rs.getDouble("amount"), 
                                     rs.getString("cur"),
                                     rs.getDouble("invested"),
                                     block,
                                     hash);
                
                // CRC
                if (rs.getString("cur").equals("CRC"))
                        this.doTrans(rs.getString("src"), 
                                     rs.getDouble("amount"), 
                                     block);
                      
                      // Update trans
                      UTILS.DB.executeUpdate("UPDATE trans "
                                              + "SET status='ID_CLEARED' "
                                            + "WHERE ID='"+rs.getLong("ID")+"'");
                    }
                }
                
             
        }
        
        
        public double newProdTrans(String adr, 
                                   double amount, 
                                   String prod, 
                                   String expl, 
                                   String hash,
                                   double cost,
                                   long block) throws Exception
        {
            // ResultSet
            ResultSet rs;
            
            // Address valid
            if (!UTILS.BASIC.isAdr(adr) && 
                !adr.equals("none")) 
                throw new Exception("Invalid address - CAcc.java, 107");
            
            // Valid currency ?
            if (!UTILS.BASIC.isProd(prod) 
                && !UTILS.BASIC.isLic(prod)) 
                throw new Exception("Invalid product - CAcc.java, 85");
            
            // Valid hash ?
            if (!UTILS.BASIC.isHash(hash)) 
                throw new Exception("Invalid hash - CAcc.java, 124");
            
            // Amount
            if (amount==0)
                throw new Exception("Invalid amount - CAcc.java, 124");
            
            // Owner type
            String owner_type=UTILS.BASIC.getAdrOwnerType(adr);
            
            // Owner citizen ?
            if (owner_type.equals("ID_CIT"))
            {
                if (amount<0)
                {
                    if (!UTILS.BASIC.canSell(adr, prod) && 
                        !UTILS.BASIC.canDonate(adr, prod))
                       throw new Exception("Address can't sell this product - CAcc.java, 124");
                }
                else 
                {
                    if (!UTILS.BASIC.canBuy(adr, prod, amount, null))
                       throw new Exception("Address can't buy this product - CAcc.java, 124");
                }
            }
            
            // Government  ?
            else if (owner_type.equals("ID_GUV"))
                throw new Exception("Invalid product - CAcc.java, 115");
            
            // Company  ?
            else
            {
               rs=UTILS.DB.executeQuery("SELECT * "
                                        + "FROM com_prods "
                                       + "WHERE com_type='"+owner_type+"' "
                                         + "AND prod='"+prod+"'");
               
               // Has data
               if (!UTILS.DB.hasData(rs))
                   throw new Exception("Invalid product - CAcc.java, 127");
            }
            
            // Encode expl
            expl=UTILS.BASIC.base64_encode(expl);
           
            // Trans ID
            long tID=UTILS.BASIC.getID();
                
            // Add trans to trans pool
            if (amount<0)
            {
                // Balance
                double balance=this.getBalance(adr, prod);
                   
                // Funds
                if (balance<Math.abs(amount))
                   throw new Exception("Insufficient funds");
                    
                // Add to pool
	        UTILS.NETWORK.TRANS_POOL.addTrans(adr, 
		    		                  amount, 
		    		                  prod, 
		    		                  hash, 
		    		                  block);
            }
            
            // Mine ?
	    if (UTILS.WALLET.isMine(adr))
            {
	        UTILS.DB.executeUpdate("INSERT INTO my_trans "
                                             + "SET userID='"+this.getAdrUserID(adr)+"', "
                                                 + "adr='"+adr+"', "
                                                 + "amount='"+UTILS.FORMAT_8.format(amount)+"', "
                                                 + "cur='"+prod+"', "
                                                 + "expl='"+expl+"', "
                                                 + "hash='"+hash+"', "
                                                 + "block='"+block+"', "
                                                 + "block_hash='"+UTILS.NET_STAT.actual_block_hash+"', "
                                                 + "tstamp='"+UTILS.BASIC.tstamp()+"'");
            }
            
            // Insert into transactions
            UTILS.DB.executeUpdate("INSERT INTO trans "
                                             + "SET src='"+adr+"', "
                                                 + "amount='"+UTILS.FORMAT_8.format(amount)+"', "
                                                 + "cur='"+prod+"', "
                                                 + "hash='"+hash+"', "
                                                 + "expl='"+expl+"', "
                                                 + "invested='"+cost+"', "
                                                 + "block='"+block+"', "
                                                 + "block_hash='"+UTILS.NET_STAT.actual_block_hash+"', "
                                                 + "tstamp='"+UTILS.BASIC.tstamp()+"', "
                                                 + "status='ID_PENDING'");
            if (amount>0)
                return 0;
            
            // Amount
            double stoc=this.getBalance(adr, prod);
            
            // Percent
            double p=Math.abs(amount*100/stoc);
            
            // Cost
            return p*stoc/100;
        }
        
        
        public void newAssetTrans(String adr, 
                             String adr_assoc, 
                             double amount, 
                             String cur, 
                             String expl, 
                             String escrower, 
                             double cost,
                             String hash, 
                             long block) throws Exception
        {
            // ResultSet
            ResultSet rs;
            
            // Asset fee
            double fee=0;
            
            // Asset fee address
            String fee_adr="";
            
            // Market fee
            double mkt_fee=0;
            
            // Address valid
            if (!UTILS.BASIC.isAdr(adr)) 
                throw new Exception("Invalid address - CAcc.java, 107");
            
            // Address associated address ?
            if (!adr_assoc.equals("")
                && !adr.equals("none"))
              if (!UTILS.BASIC.isAdr(adr_assoc)) 
                throw new Exception("Invalid associated address - CAcc.java, 111");
            
            // Valid currency ?
            if (!UTILS.BASIC.isCur(cur)) 
                throw new Exception("Invalid currency - CAcc.java, 115");
            
            // Valid escrower ?
            if (!escrower.equals(""))
              if (!UTILS.BASIC.isAdr(escrower)) 
                throw new Exception("Invalid escrower - CAcc.java, 120");
            
            // Valid hash ?
            if (!UTILS.BASIC.isHash(hash)) 
                throw new Exception("Invalid hash - CAcc.java, 124");
            
            // Encode expl
            expl=UTILS.BASIC.base64_encode(expl);
           
            // Trans ID
            long tID=UTILS.BASIC.getID();
                
            // Add trans to trans pool
            if (amount<0)
            {
                // Balance
                double balance=this.getBalance(adr, cur);
                   
                // Funds
                if (balance<Math.abs(amount))
                   throw new Exception("Insufficient funds");
                    
                // Add to pool
	        UTILS.NETWORK.TRANS_POOL.addTrans(adr, 
		    		                  amount, 
		    		                  cur, 
		    		                  hash, 
		    		                  block);
            }
                
            // Credit transaction ?
            if (amount>0)
            {
                // Loads asset data
                    rs=UTILS.DB.executeQuery("SELECT * "
                                             + "FROM assets "
                                            + "WHERE symbol='"+cur+"'");
                  
                    // Next
                    rs.next();
                  
                    if (rs.getDouble("trans_fee")>0)
                    {
                        // Fee
                        fee=Math.abs(rs.getDouble("trans_fee")*amount/100);
                  
                        // Fee address
                        fee_adr=rs.getString("trans_fee_adr");
                    }
                
            }
            
            if (fee<0) fee=0;
            
            // Mine ?
	    if (UTILS.WALLET.isMine(adr))
	    {
                // Insert into my transactions
                UTILS.DB.executeUpdate("INSERT INTO my_trans "
                                             + "SET userID='"+this.getAdrUserID(adr)+"', "
                                                 + "adr='"+adr+"', "
                                                 + "adr_assoc='"+adr_assoc+"', "
                                                 + "amount='"+UTILS.FORMAT_8.format(amount)+"', "
                                                 + "cur='"+cur+"', "
                                                 + "expl='"+expl+"', "
                                                 + "escrower='"+escrower+"', "
                                                 + "hash='"+hash+"', "
                                                 + "block='"+block+"', "
                                                 + "block_hash='"+UTILS.NET_STAT.actual_block_hash+"', "
                                                 + "tstamp='"+UTILS.BASIC.tstamp()+"'");
                
                if (fee>0)
                {
                    // Extract the fee
                    UTILS.DB.executeUpdate("INSERT INTO my_trans "
                                             + "SET userID='"+this.getAdrUserID(adr)+"', "
                                                 + "adr='"+adr+"', "
                                                 + "adr_assoc='"+fee_adr+"', "
                                                 + "amount='"+UTILS.FORMAT_8.format(-fee)+"', "
                                                 + "cur='"+cur+"', "
                                                 + "expl='"+expl+"', "
                                                 + "hash='"+hash+"', "
                                                 + "block='"+block+"', "
                                                 + "block_hash='"+UTILS.NET_STAT.actual_block_hash+"', "
                                                 + "tstamp='"+UTILS.BASIC.tstamp()+"'");
                
                    // Deposit the fee
                    if (!fee_adr.equals("default"))
                    UTILS.DB.executeUpdate("INSERT INTO my_trans "
                                             + "SET userID='"+this.getAdrUserID(fee_adr)+"', "
                                                 + "adr='"+fee_adr+"', "
                                                 + "adr_assoc='"+adr+"', "
                                                 + "amount='"+UTILS.FORMAT_8.format(fee)+"', "
                                                 + "cur='"+cur+"', "
                                                 + "expl='"+expl+"', "
                                                 + "hash='"+hash+"', "
                                                 + "block='"+block+"', "
                                                 + "block_hash='"+UTILS.NET_STAT.actual_block_hash+"', "
                                                 + "tstamp='"+UTILS.BASIC.tstamp()+"'");
                }
              
           }
               
            // Insert into transactions
            UTILS.DB.executeUpdate("INSERT INTO trans "
                                             + "SET src='"+adr+"', "
                                                 + "amount='"+UTILS.FORMAT_8.format(amount)+"', "
                                                 + "cur='"+cur+"', "
                                                 + "hash='"+hash+"', "
                                                 + "expl='"+expl+"', "
                                                 + "escrower='"+escrower+"', "
                                                 + "block='"+block+"', "
                                                 + "block_hash='"+UTILS.NET_STAT.actual_block_hash+"', "
                                                 + "tstamp='"+UTILS.BASIC.tstamp()+"', "
                                                 + "status='ID_PENDING'");
            
            if (amount>0 && !adr.equals("default") && fee>0)
            {
                // Insert fee into transactions
                UTILS.DB.executeUpdate("INSERT INTO trans "
                                             + "SET src='"+adr+"', "
                                                 + "amount='"+UTILS.FORMAT_8.format(-fee)+"', "
                                                 + "cur='"+cur+"', "
                                                 + "hash='"+hash+"', "
                                                 + "expl='"+expl+"', "
                                                 + "block='"+block+"', "
                                                 + "block_hash='"+UTILS.NET_STAT.actual_block_hash+"', "
                                                 + "tstamp='"+UTILS.BASIC.tstamp()+"', "
                                                 + "status='ID_PENDING'");
           
            
                // Insert fee into transactions
                UTILS.DB.executeUpdate("INSERT INTO trans "
                                             + "SET src='"+fee_adr+"', "
                                                 + "amount='"+UTILS.FORMAT_8.format(fee)+"', "
                                                 + "cur='"+cur+"', "
                                                 + "hash='"+hash+"', "
                                                 + "expl='"+expl+"', "
                                                 + "block='"+block+"', "
                                                 + "block_hash='"+UTILS.NET_STAT.actual_block_hash+"', "
                                                 + "tstamp='"+UTILS.BASIC.tstamp()+"', "
                                                 + "status='ID_PENDING'");
            }
            
       
            
        }
        
        public void newCRCTrans(String adr, 
                                String adr_assoc, 
                                double amount, 
                                String cur, 
                                String expl, 
                                String escrower, 
                                double cost,
                                String hash, 
                                long block) throws Exception
        {
            // ResultSet
            ResultSet rs;
            
            // Address valid
            if (!UTILS.BASIC.isAdr(adr)) 
                throw new Exception("Invalid address - CAcc.java, 107");
            
            // Address associated address ?
            if (!adr_assoc.equals("")
               && !adr_assoc.equals("none"))
              if (!UTILS.BASIC.isAdr(adr_assoc)) 
                throw new Exception("Invalid associated address - CAcc.java, 111");
            
            // Valid currency ?
            if (!UTILS.BASIC.isCur(cur)) 
                throw new Exception("Invalid currency - CAcc.java, 115");
            
            // Valid escrower ?
            if (!escrower.equals(""))
              if (!UTILS.BASIC.isAdr(escrower)) 
                throw new Exception("Invalid escrower - CAcc.java, 120");
            
            // Valid hash ?
            if (!UTILS.BASIC.isHash(hash)) 
                throw new Exception("Invalid hash - CAcc.java, 124");
            
            // Encode expl
            expl=UTILS.BASIC.base64_encode(expl);
           
            // Trans ID
            long tID=UTILS.BASIC.getID();
                
            // Add trans to trans pool
            if (amount<0)
            {
                // Balance
                double balance=this.getBalance(adr, cur);
                   
                // Funds
                if (balance<Math.abs(amount))
                   throw new Exception("Insufficient funds");
                    
                // Add to pool
	        UTILS.NETWORK.TRANS_POOL.addTrans(adr, 
		    		                  amount, 
		    		                  cur, 
		    		                  hash, 
		    		                  block);
            }
            
            // Mine ?
	    if (UTILS.WALLET.isMine(adr))
	    {
                // Insert into my transactions
                UTILS.DB.executeUpdate("INSERT INTO my_trans "
                                             + "SET userID='"+this.getAdrUserID(adr)+"', "
                                                 + "adr='"+adr+"', "
                                                 + "adr_assoc='"+adr_assoc+"', "
                                                 + "amount='"+UTILS.FORMAT_8.format(amount)+"', "
                                                 + "cur='"+cur+"', "
                                                 + "expl='"+expl+"', "
                                                 + "escrower='"+escrower+"', "
                                                 + "hash='"+hash+"', "
                                                 + "block='"+block+"', "
                                                 + "block_hash='"+UTILS.NET_STAT.actual_block_hash+"', "
                                                 + "tstamp='"+UTILS.BASIC.tstamp()+"'");
           }
               
            // Insert into transactions
            UTILS.DB.executeUpdate("INSERT INTO trans "
                                             + "SET src='"+adr+"', "
                                                 + "amount='"+UTILS.FORMAT_8.format(amount)+"', "
                                                 + "cur='"+cur+"', "
                                                 + "hash='"+hash+"', "
                                                 + "expl='"+expl+"', "
                                                 + "escrower='"+escrower+"', "
                                                 + "block='"+block+"', "
                                                 + "block_hash='"+UTILS.NET_STAT.actual_block_hash+"', "
                                                 + "tstamp='"+UTILS.BASIC.tstamp()+"', "
                                                 + "status='ID_PENDING'");
        }
        
        
        public void newTrans(String adr, 
                             String adr_assoc, 
                             double amount, 
                             String cur, 
                             String expl, 
                             String escrower, 
                             double cost,
                             String hash, 
                             long block) throws Exception
        {
            // CRC ?
            if (cur.equals("CRC"))
                this.newCRCTrans(adr, 
                                 adr_assoc, 
                                 amount, 
                                 cur, 
                                 expl, 
                                 escrower, 
                                 cost,
                                 hash, 
                                 block);
            
            // Asset ?
            else if (UTILS.BASIC.isAsset(cur))
                this.newAssetTrans(adr, 
                                   adr_assoc, 
                                   amount, 
                                   cur, 
                                   expl, 
                                   escrower, 
                                   cost,
                                   hash, 
                                   block);
            
            // Product ?
            else if (UTILS.BASIC.isProd(cur))
                this.newProdTrans(adr, 
                                  amount, 
                                  cur, 
                                  expl, 
                                  hash,
                                  cost,
                                  block);
                
        }
        
        public void newTransfer(String adr, 
                                String dest, 
                                double amount, 
                                String cur, 
                                String expl, 
                                String escrower, 
                                double cost,
                                String hash, 
                                long block) throws Exception
        {
            // CRC ?
            if (cur.equals("CRC"))
            {
                // Substract
                this.newCRCTrans(adr, 
                                 dest, 
                                 -amount, 
                                 cur, 
                                 expl, 
                                 escrower, 
                                 cost,
                                 hash, 
                                 block);
                
                // Deposit
                this.newCRCTrans(dest, 
                                 adr, 
                                 amount, 
                                 cur, 
                                 expl, 
                                 escrower, 
                                 cost,
                                 hash, 
                                 block);
            }
            
            // Asset ?
            else if (UTILS.BASIC.isAsset(cur))
            {
                // Substract
                this.newAssetTrans(adr, 
                                   dest, 
                                   -amount, 
                                   cur, 
                                   expl, 
                                   escrower, 
                                   cost,
                                   hash, 
                                   block);
                
                // Deposit
                this.newAssetTrans(dest, 
                                   adr, 
                                   amount, 
                                   cur, 
                                   expl, 
                                   escrower, 
                                   cost,
                                   hash, 
                                   block);
            }
            
            // Product ?
            else if (UTILS.BASIC.isProd(cur))
            {
                // Substract
                this.newProdTrans(adr, 
                                  -amount, 
                                  cur, 
                                  expl, 
                                  hash,
                                  cost,
                                  block);
                
                // Deposit
                this.newProdTrans(dest, 
                                  amount, 
                                  cur, 
                                  expl, 
                                  hash,
                                  cost,
                                  block);
            }   
        }
        
        // Get address owner
        public long getAdrUserID(String adr) throws Exception
        {
            // Address valid
            if (!UTILS.BASIC.isAdr(adr))
                throw new Exception ("Invalid address - CAcc.java, 278");
            
            // Default address ?
            if (adr.equals("default")) 
                return 0;
            
            // Load source
               ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                                  + "FROM my_adr "
                                                 + "WHERE adr='"+adr+"'"); 
               
               // None ?
               if (!UTILS.DB.hasData(rs)) return 0;
               
               // Next
               rs.next();
            
               // Return
               return rs.getLong("userID");
            
        }
        
        // Transfer assets
        public void doAssetTrans(String adr, 
                                 double amount, 
                                 String cur,
                                 long block) throws Exception
        {
            double balance=0;
            double new_balance=0;
            double invested=0;
            
            // Address valid
            if (!UTILS.BASIC.isAdr(adr))
                throw new Exception ("Invalid address - CAcc.java, 278");
            
            // Currency valid
            if (!UTILS.BASIC.isCur(cur))
                throw new Exception ("Invalid address - CAcc.java, 278");
                    
            // Load asset data
            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM assets "
                                              + "WHERE symbol='"+cur+"'");
                     
            // Next
            rs.next();
                     
            // Load source
            rs=UTILS.DB.executeQuery("SELECT * "
                                     + "FROM assets_owners "
                                    + "WHERE owner='"+adr+"' "
                                     + " AND symbol='"+cur+"'");
		         
            // Address exist ?
            if (!UTILS.DB.hasData(rs))
            {
                // Insert address
                UTILS.DB.executeUpdate("INSERT INTO assets_owners "
                                             + "SET owner='"+adr+"', "
                                                 + "symbol='"+cur+"', "
                                                 + "qty='0', "
                                                 + "block='"+block+"'");
                         
                // New balance
                new_balance=amount;
            }
            else
            {
                // Next
                rs.next();
                     
                // Balance
		balance=rs.getDouble("qty");
		     
                // New balance
                new_balance=balance+amount;
            }
                      
            // Source balance update
            UTILS.DB.executeUpdate("UPDATE assets_owners "
		                    + "SET qty="+new_balance+", "
                                        + "block='"+block+"' "
                                  + "WHERE owner='"+adr+"' "
                                    + "AND symbol='"+cur+"'");
            
             UTILS.DB.executeUpdate("UPDATE web_users "
                                        + "SET unread_trans=unread_trans+1 "
                                      + "WHERE ID='"+getAdrUserID(adr)+"' ");
                 
        }
        
        // Transfer assets
        public void doProdTrans(String adr, 
                                 double amount, 
                                 String prod,
                                 double invested,
                                 long block,
                                 String hash) throws Exception
        {
            double balance=0;
            double new_balance=0;
            
            // New stoc ID
            Random r=new Random();
            long stocID=UTILS.BASIC.hashToLong(hash);
            
            // Already exist ?
            if (UTILS.BASIC.isID(stocID))
                throw new Exception("Invalid generated random number");
            
            // Address valid
            if (!UTILS.BASIC.isAdr(adr))
                throw new Exception ("Invalid address - CAcc.java, 278");
            
            // Currency valid
            if (!UTILS.BASIC.isProd(prod) && 
                !UTILS.BASIC.isLic(prod))
                throw new Exception ("Invalid product - CAcc.java, 278");
            
            // Energy ?
            if (prod.equals("ID_ENERGY"))
            {
                // Update energy
                UTILS.DB.executeUpdate("UPDATE adr "
                                        + "SET energy=energy+"+amount+" "
                                      + "WHERE adr='"+adr+"'");
                
                // Return 
                return;
            }
                     
            // Expire
            long expires=0;
            
            if (amount>=1)
            {
                   // Load product data
                   ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                                      + "FROM tipuri_produse "
                                                     + "WHERE prod='"+prod+"'");
                   
                   // Next
                   rs.next();
                   
                   // Expire
                   if (rs.getLong("expires")>0)
                       expires=block+rs.getLong("expires");
            }
                
            // Load stoc
            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM stocuri "
                                              + "WHERE adr='"+adr+"' "
                                               + " AND tip='"+prod+"'");
              
            // Stoc exist ?
            if (!UTILS.DB.hasData(rs))
            {
                
                
                // Capacity
                long cap=0;
                
                // Insert item
                if (UTILS.BASIC.buySplit(adr, prod, amount))
                {
                    for (int a=1; a<=amount; a++)
                    UTILS.DB.executeUpdate("INSERT INTO stocuri "
                                                  + "SET adr='"+adr+"', "
                                                      + "tip='"+prod+"', "
                                                      + "qty='1', "
                                                      + "invested="+(invested/amount)+", "
                                                      + "expires='"+expires+"', "
                                                      + "stocID='"+(stocID+a)+"', "
                                                      + "block='"+block+"'");
                }
                else
                     UTILS.DB.executeUpdate("INSERT INTO stocuri "
                                                  + "SET adr='"+adr+"', "
                                                      + "tip='"+prod+"', "
                                                      + "qty='0', "
                                                      + "invested="+invested+", "
                                                      + "expires='"+expires+"', "
                                                      + "cap='"+cap+"', "
                                                      + "stocID='"+stocID+"', "
                                                      + "block='"+block+"'");
                         
                   // New balance
                   new_balance=amount;
            }
            else
            {
                // Next
                rs.next();
                     
                // Balance
		balance=rs.getDouble("qty");
		     
                // New balance
                new_balance=balance+amount;
                
                
                // Buy split ?
                if (UTILS.BASIC.buySplit(adr, prod, amount))
                for (int a=1; a<=amount; a++)
                    UTILS.DB.executeUpdate("INSERT INTO stocuri "
                                                  + "SET adr='"+adr+"', "
                                                      + "tip='"+prod+"', "
                                                      + "qty='1', "
                                                      + "invested="+(invested/amount)+", "
                                                      + "expires='"+expires+"', "
                                                      + "stocID='"+stocID+"', "
                                                      + "block='"+block+"'");
            }
                      
            // Source balance update
            if (!UTILS.BASIC.buySplit(adr, prod, amount))
            {
                if (amount<0)
                {
                    // Invested
                    invested=rs.getDouble("invested");
                
                    // Stoc
                    double stoc=this.getBalance(adr, prod);
                    
                    // Percent
                    double p=amount*100/stoc;
                    
                    // Invested
                    invested=p*invested/100;
                }
                 
                // Update 
                UTILS.DB.executeUpdate("UPDATE stocuri "
		                              + "SET qty="+new_balance+", "
                                                  + "block='"+block+"', "
                                                  + "invested=invested+"+invested
                                           + " WHERE adr='"+adr+"' "
                                              + "AND tip='"+prod+"'");
            }
            
             UTILS.DB.executeUpdate("UPDATE web_users "
                                        + "SET unread_trans=unread_trans+1 "
                                      + "WHERE ID='"+getAdrUserID(adr)+"' ");
                 
            }
        
        public void doTrans(String adr, 
                            double amount, 
                            long block) throws Exception
        {
            double balance;
            double new_balance;
            
            // Adr
            if (!UTILS.BASIC.isAdr(adr)) 
                throw new Exception("Invalid address");
            
            // Load source
            ResultSet rs=UTILS.DB.executeQuery("SELECT * FROM adr WHERE adr='"+adr+"'");
		         
                     // Address exist ?
                     if (!UTILS.DB.hasData(rs))
                     {
                        UTILS.DB.executeUpdate("INSERT INTO adr  "
                                                     + "SET adr='"+adr+"', "
                                                         + "balance='0', "
                                                         + "block='"+block+"', "
                                                         + "created='"+block+"'");
                        
                        // New balance
                        new_balance=Double.parseDouble(UTILS.FORMAT_8.format(amount));
                     }
                     else
                     {
                        // Next
                        rs.next();
                     
                        // Balance
		        balance=rs.getDouble("balance");
		     
                        // New balance
                        new_balance=balance+amount;
                     
                        // Format
                        new_balance=Double.parseDouble(UTILS.FORMAT_8.format(new_balance));
                     }
                     
		     // Source balance update
		     UTILS.DB.executeUpdate("UPDATE adr "
		   		             + "SET balance="+UTILS.FORMAT_8.format(new_balance)+", "
		   		                 + "block='"+block+
                                           "' WHERE adr='"+adr+"'");
                     
                      UTILS.DB.executeUpdate("UPDATE web_users "
                                              + "SET unread_trans=unread_trans+1 "
                                            + "WHERE ID='"+getAdrUserID(adr)+"' ");
         }
        
        public long getEnergyProdBalance(String adr, String prod) throws Exception
        {
            // Valid address ?
            if (UTILS.BASIC.isAdr(adr))
                throw new Exception("Invalid address");
            
            // Energy prod ?
            if (!UTILS.BASIC.isEnergyProd(prod))
                throw new Exception("Invalid product");
            
            // Load data
            ResultSet rs=UTILS.DB.executeQuery("SELECT COUNT(*) AS total "
                                               + "FROM stocuri "
                                              + "WHERE adr='"+adr+"' "
                                                + "AND tip='"+prod+"'");
            
            // Next
            rs.next();
            
            // Return
            return rs.getLong("total");
        }
        
        public double getBalance(String adr, String cur) throws Exception
	{
           // Result Set
	   ResultSet rs=null;
           
           // Adr
            if (!UTILS.BASIC.isAdr(adr)) 
                throw new Exception("Invalid address");
            
            // Currency
            if (!UTILS.BASIC.isCur(cur) && 
                !UTILS.BASIC.isProd(cur)) 
                throw new Exception("Invalid currency");
            
            // CRC ?
           if (cur.equals("CRC"))
              rs=UTILS.DB.executeQuery("SELECT * "
                                       + "FROM adr "
                                      + "WHERE adr='"+adr+"'");
            
            // Asset ? 
           if (!cur.equals("CRC") && cur.indexOf("_")==-1)
                rs=UTILS.DB.executeQuery("SELECT * "
                                         + "FROM assets_owners "
                                        + "WHERE owner='"+adr+"' "
                                          + "AND symbol='"+cur+"'");
           
           // Prod ? 
           if (!cur.equals("CRC") && cur.indexOf("_")>0)
           {
              // Energy ?
              if (cur.equals("ID_ENERGY"))
              {
                  // Energy
                  rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM adr "
                                          + "WHERE adr='"+adr+"'");
                  
                  // Next
                  rs.next();
                  
                  // Return
                  return rs.getDouble("energy");
              }
                  
              rs=UTILS.DB.executeQuery("SELECT * "
                                         + "FROM stocuri "
                                        + "WHERE adr='"+adr+"' "
                                          + "AND tip='"+cur+"'");
           
           }
           
            // Has data ?
            if (UTILS.DB.hasData(rs)==true)
            {
                // Next
                rs.next();
                   
                // Balance
                double balance;
                
                // Currency
                if (cur.equals("CRC"))
                   balance=rs.getDouble("balance");
                else
                   balance=rs.getDouble("qty");
                   
                  
                // Return
                return balance;
            } 
            else
            {
                // Return
                return 0;
            }
	}
        
    public double getBalance(String adr, String cur, CBlockPayload block) throws Exception
    {
        if (block==null)
            return UTILS.NETWORK.TRANS_POOL.getBalance(adr, cur);
        else
            return UTILS.ACC.getBalance(adr, cur);
    }
    
    
    public void bugTax(String adr, 
                       String tax, 
                       double income, 
                       String hash, 
                       long block)
    {
        
    }
    
    public void refTax(String adr, 
                       double income, 
                       String hash, 
                       long block)
    {
        
    }
    
    
}
