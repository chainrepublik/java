package chainrepublik.network.packets.companies;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CWthFundsPayload extends CPayload
{
    // Company ID
    long comID;
    
    // Amount
    double amount;
    
    public CWthFundsPayload(String adr,
                            long comID,
                            double amount) throws Exception
    {
        // Superclass
	super(adr);
        
        // Workplace ID
        this.comID=comID;
    
        // Amount
        this.amount=amount; 
        
        // Hash
 	hash=UTILS.BASIC.hash(this.getHash()+
 			      this.comID+
                              this.amount);
    }
    
    public void check(CBlockPayload block) throws Exception
    {
   	// Super class
   	super.check(block);
        
        // Com ID 
        ResultSet com_rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM companies "
                                              + "WHERE comID='"+this.comID+"' "
                                                + "AND adr='"+this.target_adr+"'");
        
        // No data
        if (!UTILS.DB.hasData(com_rs))
            throw new Exception("Invalid company ID - CWthFundsPayload.java, 68");
        
        // Next
        com_rs.next();
        
        // Amount
        this.amount=UTILS.BASIC.round(this.amount, 4);
        
        // Amount
        if (this.amount<0.01)
            throw new Exception("Invalid amount - CWthFundsPayload.java, 68");
        
        // Balance
        if (UTILS.ACC.getBalance(com_rs.getString("adr"), "CRC")<amount)
             throw new Exception("Insuficient funds - CWthFundsPayload.java, 68");
        
        // Shareholders
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM assets_owners "
                                          + "WHERE symbol='"+com_rs.getString("symbol")+"' "
                                            + "AND qty>=0.01");
        
        // Hash
        String h=UTILS.BASIC.hash(this.getHash()+
 			          this.comID+
                                  this.amount);
        
        // Same hash
        if (!h.equals(this.hash))
            throw new Exception("Invalid hash - CWthFundsPayload.java, 68");
        
        // Loop
        while (rs.next())
        {
            // Per share
            double per_share=this.amount/10000;
            
            // Amount
            double to_pay=rs.getDouble("qty")*per_share;
            
            // Pay
            UTILS.ACC.newTransfer(com_rs.getString("adr"), 
                                  rs.getString("owner"), 
                                  to_pay, 
                                  "CRC", 
                                  "Company "+com_rs.getString("symbol")+" paid dividends", 
                                  "", 
                                  0,
                                  this.hash, 
                                  this.block,
                                  true,
                                  "ID_DIVIDENDS_TAX",
                                  "");
        }
    }
    
     public void commit(CBlockPayload block) throws Exception
     {
         // Superclass
         super.commit(block);
       
         // Insert dividend
         UTILS.DB.executeUpdate("INSERT INTO dividends "
                                      + "SET comID='"+this.comID+"', "
                                          + "amount='"+this.amount+"', "
                                          + "block='"+this.block+"'");
         
         // Clear trans
         UTILS.ACC.clearTrans(this.hash, "ID_ALL", this.block);
     }
}