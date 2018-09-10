// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.assets.reg_mkts;

import java.sql.ResultSet;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CCloseRegMarketPosPayload  extends CPayload
{
    /// UID
    long orderID;
    
    // Serial
    private static final long serialVersionUID = 100L;
    
    public CCloseRegMarketPosPayload(String adr, long orderID) throws Exception
    {
        // Constructor
        super(adr);
        
        // UID
        this.orderID=orderID;
          
        // Hash
        hash=UTILS.BASIC.hash(this.getHash()+
                              this.orderID);
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
           
        // Load position details
        ResultSet rs_pos=UTILS.DB.executeQuery("SELECT amp.*, "
                                                    + "am.asset, "
                                                    + "am.cur "
                                           + "FROM assets_mkts_pos AS amp "
                                           + "JOIN assets_mkts AS am ON am.mktID=amp.mktID "
                                          + "WHERE amp.orderID='"+this.orderID+"'");
           
        // Has data ?
        if (!UTILS.DB.hasData(rs_pos))
           throw new Exception("Invalid position - CCloseRegMarketPosPayload.java");
           
        // Next
        rs_pos.next();
           
        // Asset symbol
        String asset_symbol=rs_pos.getString("asset");
          
        // Currency symbol
        String cur_symbol=rs_pos.getString("cur");
           
        // Ownership
        if (!this.target_adr.equals(rs_pos.getString("adr")))
        {
            // Is company address ?
            if (!UTILS.BASIC.isCompanyAdr(rs_pos.getString("adr")))
               throw new Exception("Invalid owner - CCloseRegMarketPosPayload.java");
            
            // Company address
            ResultSet rs_com=UTILS.DB.executeQuery("SELECT * "
                                                   + "FROM companies "
                                                  + "WHERE adr='"+rs_pos.getString("adr")+"'");
            
            // Load data
            rs_com.next();
            
            // Owner ?
            if (!rs_com.getString("owner").equals(this.target_adr))
                throw new Exception("Invalid owner - CCloseRegMarketPosPayload.java");
        }
        
        if (rs_pos.getString("tip").equals("ID_SELL"))
            UTILS.ACC.newTrans(rs_pos.getString("adr"), 
                                "none",
                                rs_pos.getDouble("qty"), 
                                asset_symbol, 
                                "", 
                                "", 
                                rs_pos.getDouble("cost"),
                                hash, 
                                this.block,
                                false,
                                "",
                                "");
        else
            UTILS.ACC.newTrans(rs_pos.getString("adr"), 
                               "none",
                               rs_pos.getDouble("qty")*rs_pos.getDouble("price"),
                               cur_symbol, 
                               "", 
                               "", 
                               0,
                               hash, 
                               this.block,
                               false,
                               "",
                               "");
           
        // Hash
        String h=UTILS.BASIC.hash(this.getHash()+
                                  this.orderID);
          
        if (!h.equals(this.hash))
           throw new Exception("Invalid hash - CCloseRegMarketPosPayload.java");
    }
    
    public void commit(CBlockPayload block) throws Exception
    {
        // Constructor
        super.commit(block);
        
        // Load position details
        ResultSet rs_pos=UTILS.DB.executeQuery("SELECT amp.*, "
                                                    + "am.asset, "
                                                    + "am.cur "
                                           + "FROM assets_mkts_pos AS amp "
                                           + "JOIN assets_mkts AS am ON am.mktID=amp.mktID "
                                          + "WHERE amp.orderID='"+this.orderID+"'");
        
        // Next
        rs_pos.next();
        
        // Remove
        UTILS.DB.executeUpdate("DELETE FROM assets_mkts_pos "
                                     + "WHERE orderID='"+this.orderID+"'"); 
        
        // Position type
        UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
        
        // Shares ?
        if (rs_pos.getString("asset").length()==5)
           UTILS.BASIC.checkComOwner(rs_pos.getString("asset"), this.block);
    }        
}
