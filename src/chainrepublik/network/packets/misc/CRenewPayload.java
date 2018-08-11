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
	
    }
    
     public void check(CBlockPayload block) throws Exception
     {
        // Fee
        double fee=0;
         
        // Constructor
        super.check(block);
        
         // Check energy
        this.checkEnergy();
       
       // Citizen address ?
        if (!UTILS.BASIC.isCitAdr(this.target_adr, this.block))
           throw new Exception("Only citizens can do this action - CWorkPayload.java, 68");
        
        // Target type
        if (!this.target_type.equals("ID_ADR")
            && !this.target_type.equals("ID_ASSET")
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
       
        // Company ID
        long comID=0;
        
        // Company
        if (this.target_type.equals("ID_COM"))
        {
            // Valid company
            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM companies "
                                              + "WHERE owner='"+this.target_adr+"' "
                                                + "AND comID='"+this.targetID+"'");
            
            // Has data ?
            if (!UTILS.DB.hasData(rs))
                throw new Exception("Invalid asset market pos ID - CRenewPayload.java");
            
            // Next
            rs.next();
            
            // Fee
            fee=this.days*UTILS.CONST.com_price;
            
            // Com ID
            comID=rs.getLong("comID");
        }
        
        // Workplace
        if (this.target_type.equals("ID_WORKPLACE"))
        {
            // Valid company
            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM workplaces AS wp "
                                               + "JOIN companies AS com ON wp.comID=com.comID "
                                              + "WHERE com.owner='"+this.target_adr+"' "
                                                + "AND wp.workplaceID='"+this.targetID+"'");
            
            // Has data ?
            if (!UTILS.DB.hasData(rs))
                throw new Exception("Invalid workplace ID - CRenewPayload.java");
            
            // Next
            rs.next();
            
            // Fee
            fee=this.days*UTILS.CONST.wp_price;
            
            // Com ID
            comID=rs.getLong("comID");
        }
        
        // Licence
        if (this.target_type.equals("ID_LIC"))
        {
            // Valid company
            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM stocuri AS st "
                                               + "JOIN companies AS com ON com.adr=st.adr"
                                              + "WHERE stocID='"+this.targetID+"' "
                                                + "AND com.owner='"+this.target_adr+"'");
            
            // Has data ?
            if (!UTILS.DB.hasData(rs))
                throw new Exception("Invalid licence ID - CRenewPayload.java");
            
            // Next
            rs.next();
            
            // Is licence ?
            if (!UTILS.BASIC.isLic(rs.getString("tip")))
                throw new Exception("Invalid licence ID - CRenewPayload.java");
            
            // Fee
            fee=this.days*UTILS.CONST.lic_price;
            
            // Com ID
            comID=rs.getLong("comID");
        }
        
        // Funds
        if (UTILS.ACC.getBalance(this.target_adr, "CRC", block)<fee)
           throw new Exception("Insufficient funds to execute this operation - CRenewPayload.java");
        
        // Source address
        String adr;
        
        // Source address
        if (this.target_type.equals("ID_COM") || 
            this.target_type.equals("ID_WORKPLACE") || 
            this.target_type.equals("ID_LIC"))
        adr=UTILS.BASIC.getComAdr(comID);
        else
        adr=this.target_adr;
        
        // Check hash
        String h=UTILS.BASIC.hash(this.getHash()+
                                  this.target_type+
                                  this.targetID+
			          days);
           
        if (!h.equals(hash))
           throw new Exception("Invalid hash - CRenewPayload.java");
        
        // Transactions
        UTILS.ACC.newTransfer(adr, 
                              "default", 
                              fee, 
                              "CRC", 
                              "Renew fee", 
                              "", 
                              0, 
                              hash, 
                              this.block,
                              false,
                              "",
                              "");
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
            case "ID_COM" : // Load company data
                            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                                               + "FROM companies "
                                                              + "WHERE comID='"+this.targetID+"'");
                            
                            // Next
                            rs.next();
                            
                            // Renew company
                            UTILS.DB.executeUpdate("UPDATE companies "
                                                        + "SET expires=expires+"+(this.days*1440)+" "
                                                      + "WHERE comID='"+this.targetID+"'");
                            
                            // Renew asset
                            UTILS.DB.executeUpdate("UPDATE assets "
                                                        + "SET expires=expires+"+(this.days*1440)+" "
                                                      + "WHERE symbol='"+rs.getString("symbol")+"'");
                            
                            // Renew asset markets
                            UTILS.DB.executeUpdate("UPDATE assets_mkts "
                                                    + "SET expires=expires+"+(this.days*1440)+" "
                                                  + "WHERE asset='"+rs.getString("asset")+"' "
                                                    + "AND cur='CRC'");
                            
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


