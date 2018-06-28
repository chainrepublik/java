package chainrepublik.network.packets.politics.orgs;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CNewOrgPropPayload extends CPayload
{
    // OrgID
    long orgID;
    
    // Prop ID
    long propID;
    
    // Law type
    String prop_type;
    
    // Par 1
    String par_1;
    
    // Par 2
    String par_2;
    
    // Par 3
    String par_3;
    
    // Par 4
    String par_4;
    
    // Par 5
    String par_5;
    
    // Expl
    String expl;
    
    public CNewOrgPropPayload(String adr, 
                              long orgID,
                              String prop_type, 
                              String par_1,
                              String par_2,
                              String par_3,
                              String par_4,
                              String par_5,
                              String expl) throws Exception
    {
        // Superclass
	super(adr);
        
        // Org ID
        this.orgID=orgID;
        
        // Prop ID
        this.propID=UTILS.BASIC.getID();
        
        // Prop Type
        this.prop_type=prop_type;
        
        // Par 1
        this.par_1=par_1;
        
        // Par 2
        this.par_2=par_2;
        
        // Par 3
        this.par_3=par_3;
        
        // Par 4
        this.par_4=par_4;
        
        // Par 5
        this.par_5=par_5;
        
        // Expl
        this.expl=expl;
        
        // Hash
 	hash=UTILS.BASIC.hash(this.getHash()+
                              this.orgID+
                              this.propID+
                              this.prop_type+
                              this.par_1+
                              this.par_2+
                              this.par_3+
                              this.par_4+
                              this.par_5+
                              this.expl);
    
    }
    
   
    public void check(CBlockPayload block) throws Exception
    {
   	// Super class
   	super.check(block);
        
        // Check energy
        this.checkEnergy();
        
        // Valid orgID
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM orgs "
                                          + "WHERE orgID='"+this.orgID+"'");
        
        // Has data
        if (!UTILS.DB.hasData(rs))
            throw new Exception("Invalid orgID, CNewOrgPropPayload.java, 109");
        
        // Load org data
        rs.next();
        
        // Valid propID
        if (UTILS.BASIC.isID(propID))
            throw new Exception("Invalid propID, CNewOrgPropPayload.java, 116");
        
        // Prop type
        if (!this.prop_type.equals("ID_DONATE") && 
            !this.prop_type.equals("ID_CHG_DESC") && 
            !this.prop_type.equals("ID_SET_ART_OFFICIAL"))
        throw new Exception("Invalid proposal type, CNewOrgPropPayload.java, 122");
        
        // Donate
        if (this.prop_type.equals("ID_DONATE"))
        {
            // Address
            if (!UTILS.BASIC.isAdr(this.par_1))
                throw new Exception("Invalid donate address, CNewOrgPropPayload.java, 116");
            
            // Parse amount
            double amount=Double.parseDouble(this.par_2);
            
            // Amount
            if (amount<0 || 
                amount>UTILS.ACC.getBalance(rs.getString("adr"), 
                                            "CRC", 
                                            block))
            throw new Exception("Invalid amount, CNewOrgPropPayload.java, 116");
        }
       
        
        // Change description
        if (this.prop_type.equals("ID_CHG_DESC"))
        {
            // Valid ?
            if (!UTILS.BASIC.isDesc(this.par_1))
                 throw new Exception("Invalid new description, CNewOrgPropPayload.java, 116");
            
            // Length
            if (this.par_1.length()>250)
                 throw new Exception("Invalid new description, CNewOrgPropPayload.java, 116");
        }
        
        // Set art official
        if (this.prop_type.equals("ID_SET_ART_OFFICIAL"))
        {
            // Article exist ?
            rs=UTILS.DB.executeQuery("SELECT * "
                                     + "FROM tweets "
                                    + "WHERE tweetID='"+this.par_1+"'");
            
            // Has data ?
            if (!UTILS.DB.hasData(rs))
                throw new Exception("Invalid article ID, CNewOrgPropPayload.java, 116");
            
            // Next
            rs.next();
            
            // Writen by a party / unit member ?
            if (Long.parseLong(UTILS.BASIC.getAdrData(rs.getString("adr"), "pol_party"))!=this.orgID &&
                Long.parseLong(UTILS.BASIC.getAdrData(rs.getString("adr"), "mil_unit"))!=this.orgID)
            throw new Exception("Invalid article ID, CNewOrgPropPayload.java, 116");
        }
       
        // Hash
 	String h=UTILS.BASIC.hash(this.getHash()+
                                  this.orgID+
                                  this.propID+
                                  this.prop_type+
                                  this.par_1+
                                  this.par_2+
                                  this.par_3+
                                  this.par_4+
                                  this.par_5+
                                  this.expl);
        
        // Hash match ?
        if (!this.hash.equals(h))
            throw new Exception("Invalid hash, CNewOrgPropPayload.java, 116");
    }
    
    public void commit(CBlockPayload block) throws Exception
    {
        // Superclass
        super.commit(block);
        
        // Insert proposal
        UTILS.DB.executeUpdate("INSERT INTO orgs_props "
                                     + "SET adr='"+this.target_adr+"', "
                                         + "orgID='"+this.orgID+"', "
                                         + "propID='"+this.propID+"', "
                                         + "prop_type='"+this.prop_type+"', "
                                         + "expl='"+UTILS.BASIC.base64_encode(this.expl)+"', "
                                         + "par_1='"+UTILS.BASIC.base64_encode(this.par_1)+"', "
                                         + "par_2='"+UTILS.BASIC.base64_encode(this.par_2)+"', "
                                         + "par_3='"+UTILS.BASIC.base64_encode(this.par_3)+"', "
                                         + "par_4='"+UTILS.BASIC.base64_encode(this.par_4)+"', "
                                         + "par_5='"+UTILS.BASIC.base64_encode(this.par_5)+"', "
                                         + "block='"+this.block+"', "
                                         + "status='ID_VOTING', "
                                         + "yes=0, "
                                         + "no=0");
        
        // Position type
        UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
    }
    
}
