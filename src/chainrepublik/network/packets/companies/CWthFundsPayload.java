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
    
    // Serial
    private static final long serialVersionUID = 100L;
    
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
        
        // Energy
        this.checkEnergy();
        
        // Citizen address ?
        if (!UTILS.BASIC.isCitAdr(this.target_adr, this.block))
           throw new Exception("Only citizens can do this action - CWorkPayload.java, 68");
        
        // Company addrews
        String com_adr=UTILS.BASIC.getComAdr(this.comID);
        
        // Owns company ?
        if (!UTILS.BASIC.isComOwner(this.target_adr, comID))
            throw new Exception("Invalid company ID - CWthFundsPayload.java, 68");
        
        // Amount
        this.amount=UTILS.BASIC.round(this.amount, 4);
        
        // Amount
        if (this.amount<0.1)
            throw new Exception("Invalid amount - CWthFundsPayload.java, 68");
        
        // Balance
        if (UTILS.ACC.getBalance(com_adr, "CRC")<amount)
             throw new Exception("Insuficient funds - CWthFundsPayload.java, 68");
        
        // Symbol
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM companies "
                                          + "WHERE comID='"+this.comID+"'");
        
        // Next
        rs.next();
        
        // Symbol
        String sym=rs.getString("symbol");
        
        // Shareholders
        rs=UTILS.DB.executeQuery("SELECT * "
                                 + "FROM assets_owners "
                                + "WHERE symbol='"+sym+"' "
                                  + "AND qty>=1");
        
        // Hash
        String h=UTILS.BASIC.hash(this.getHash()+
 			          this.comID+
                                  this.amount);
        
        // Same hash
        if (!h.equals(this.hash))
            throw new Exception("Invalid hash - CWthFundsPayload.java, 68");
        
        // Per share
        double per_share=this.amount/10000;
            
        // Loop
        while (rs.next())
        {
            // Amount
            double to_pay=rs.getDouble("qty")*per_share;
            
            // Pay
            UTILS.ACC.newTransfer(com_adr, 
                                  rs.getString("owner"), 
                                  to_pay, 
                                  "CRC", 
                                  "Company "+sym+" paid dividends", 
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