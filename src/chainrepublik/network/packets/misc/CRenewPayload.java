// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.misc;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CRenewPayload extends CPayload
{
    // Address
    String target_type;
    
    // Table
    long targetID;
    
    // Days
    long days;
    
    // Serial
   private static final long serialVersionUID = 100L;
                                           
    public CRenewPayload(String adr, 
                         String target_type,
                         long targetID,
                         long days) throws Exception
    {
        // Constructor
        super(adr);
       
        // Address
        this.target_type=target_type;
    
        // Target ID
        this.targetID=targetID;
        
        // Days
        this.days=days;
    
        // Hash
        hash=UTILS.BASIC.hash(this.getHash()+
                              this.target_type+
                              this.targetID+
			      days);
		
	// Sign
	this.sign();
    }
    
     public void check(CBlockPayload block) throws Exception
     {
        // Fee
        double fee=0;
         
        // Constructor
        super.check(block);
        
         // Check energy
       this.checkEnergy();
        
        // Target type
        if (!this.target_type.equals("ID_ADR")
            && !this.target_type.equals("ID_ASSET")
            && !this.target_type.equals("ID_ASSET_MKT")
            && !this.target_type.equals("ID_ASSET_MKT_POS")
            && !this.target_type.equals("ID_COM")
            && !this.target_type.equals("ID_WORKPLACE")
            && !this.target_type.equals("ID_LIC"))
        throw new Exception("Invalid target type - CRenewPayload.java");
           
        // Address
        if (this.target_type.equals("ID_ADR"))
        {
            // Valid asset
            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM adr "
                                              + "WHERE adr='"+this.target_adr+"'");
            
            // Has data ?
            if (!UTILS.DB.hasData(rs))
                throw new Exception("Invalid address - CRenewPayload.java");
            
            // Next
            rs.next();
            
            // Fee
            fee=UTILS.BASIC.round(this.days*0.0001, 8);
        }
        
        // Assets
        if (this.target_type.equals("ID_ASSET"))
        {
            // Valid asset
            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM assets "
                                              + "WHERE adr="+this.target_adr+" "
                                                + "AND assetID="+this.targetID);
            
            // Has data ?
            if (!UTILS.DB.hasData(rs))
                throw new Exception("Invalid asset - CRenewPayload.java");
            
            // Next
            rs.next();
            
            // Fee
            double tf=1;
            
            if (rs.getDouble("trans_fee")>1) 
                tf=rs.getDouble("trans_fee");
            
            fee=(rs.getLong("days")*0.0001+rs.getLong("qty")*0.0001)*tf;
        }
        
        // Assets market
        if (this.target_type.equals("ID_ASSET_MKT"))
        {
            // Valid asset
            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM assets_mkts "
                                              + "WHERE adr="+this.target_adr+" "
                                                + "AND mktID="+this.targetID);
            
            // Has data ?
            if (!UTILS.DB.hasData(rs))
                throw new Exception("Invalid asset market - CRenewPayload.java");
            
            // Next
            rs.next();
            
            // Over asset expire block ?
            if (rs.getLong("expires")+this.days*1440>UTILS.BASIC.getAssetExpireBlock(rs.getLong("asset")))
                throw new Exception("Invalid expiration date - CRenewPayload.java");
            
            // Fee
            fee=this.days*1440*0.0001;
        }
        
        // Asset market position
        if (this.target_type.equals("ID_ASSET_MKT_POS"))
        {
            // Valid asset
            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM assets_mkts_pos "
                                              + "WHERE adr="+this.target_adr+" "
                                                + "AND orderID="+this.targetID);
            
            // Has data ?
            if (!UTILS.DB.hasData(rs))
                throw new Exception("Invalid asset market pos ID - CRenewPayload.java");
            
            // Next
            rs.next();
            
            // Over asset expire block ?
            if (rs.getLong("expires")+this.days*1440>UTILS.BASIC.getAssetMktExpireBlock(rs.getLong("mktID")))
                throw new Exception("Invalid expiration date - CRenewPayload.java");
            
            // Fee
            fee=this.days*1440*0.0001;
        }
        
        // Company
        if (this.target_type.equals("ID_COMPANY"))
        {
            // Valid company
            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM companies "
                                              + "WHERE adr="+this.target_adr+" "
                                                + "AND comID="+this.targetID);
            
            // Has data ?
            if (!UTILS.DB.hasData(rs))
                throw new Exception("Invalid asset market pos ID - CRenewPayload.java");
            
            // Fee
            fee=this.days*0.01;
        }
        
        // Workplace
        if (this.target_type.equals("ID_WORKPLACE"))
        {
            // Valid company
            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM workplaces "
                                              + "WHERE adr="+this.target_adr+" "
                                                + "AND workplaceID="+this.targetID);
            
            // Has data ?
            if (!UTILS.DB.hasData(rs))
                throw new Exception("Invalid workplace ID - CRenewPayload.java");
            
            // Fee
            fee=this.days*0.01;
        }
        
        // Licence
        if (this.target_type.equals("ID_LIC"))
        {
            // Valid company
            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM stocuri AS st "
                                               + "JOIN tipuri_licente AS tl ON tl.tip=st.tip"
                                              + "WHERE adr="+this.target_adr+" "
                                                + "AND stocID="+this.targetID);
            
            // Has data ?
            if (!UTILS.DB.hasData(rs))
                throw new Exception("Invalid licence ID - CRenewPayload.java");
            
            // Fee
            fee=this.days*0.01;
        }
        
        // Funds
        if (UTILS.ACC.getBalance(this.target_adr, "CRC", block)<fee)
           throw new Exception("Insufficient funds to execute this operation - CRenewPayload.java");
        
        // Check hash
        String h=UTILS.BASIC.hash(this.getHash()+
                                  this.target_type+
                                  this.targetID+
			          days);
           
        if (!h.equals(hash))
           throw new Exception("Invalid hash - CRenewPayload.java");
         
        // Transactions
        UTILS.ACC.newTransfer(this.target_adr, 
                              "default", 
                              fee, 
                              "CRC", 
                              "Renew fee", 
                              "", 
                              0, 
                              hash, 
                              this.block);
    }
	 
    public void commit(CBlockPayload block) throws Exception
    {
        // Table
        String table="";
        
        // Superclass
	super.commit(block);
        
        // Finds table
        switch (this.target_type)
        {
            // Address
            case "ID_ADR" : UTILS.DB.executeUpdate("UPDATE adr "
                                                    + "SET expires=expires+"+(this.days*1440)+" "
                                                  + "WHERE adr='"+this.target_adr+"'");
                            break;
                            
            // Address
            case "ID_ASSET" : UTILS.DB.executeUpdate("UPDATE assets "
                                                      + "SET expires=expires+"+(this.days*1440)+" "
                                                    + "WHERE assetID='"+this.targetID+"'");
                              break;
                            
            // Address
            case "ID_ASSET_MKT" : UTILS.DB.executeUpdate("UPDATE assets_mkts "
                                                          + "SET expires=expires+"+(this.days*1440)+" "
                                                        + "WHERE mktID='"+this.targetID+"'");
                                  break;
                            
            // Address
            case "ID_ASSET_MKT_POS" : UTILS.DB.executeUpdate("UPDATE assets_mkts_pos "
                                                              + "SET expires=expires+"+(this.days*1440)+" "
                                                            + "WHERE orderID='"+this.targetID+"'");
                                      break;
                            
            // Address
            case "ID_COMPANY" : UTILS.DB.executeUpdate("UPDATE companies "
                                                        + "SET expires=expires+"+(this.days*1440)+" "
                                                      + "WHERE comID='"+this.targetID+"'");
                                break;
                            
            // Address
            case "ID_WORKPLACE" : UTILS.DB.executeUpdate("UPDATE workplaces "
                                                          + "SET expires=expires+"+(this.days*1440)+" "
                                                        + "WHERE workplaceID='"+this.targetID+"'");
                                  break;
                            
            // Licence
            case "ID_LIC" : UTILS.DB.executeUpdate("UPDATE stocuri "
                                                    + "SET expires=expires+"+(this.days*1440)+" "
                                                  + "WHERE stocID='"+this.targetID+"'");
                            break;
        }
        
        // Clear
        UTILS.ACC.clearTrans(this.hash, "ID_ALL", this.block);
    }
}


