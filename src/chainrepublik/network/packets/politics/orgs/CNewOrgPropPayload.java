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
        
        // Citizen address ?
        if (!UTILS.BASIC.isCitAdr(this.target_adr, this.block))
           throw new Exception("Only citizens can do this action - CWorkPayload.java, 68");
        
        // Valid orgID
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM orgs "
                                          + "WHERE orgID='"+this.orgID+"'");
        
        // Has data
        if (!UTILS.DB.hasData(rs))
            throw new Exception("Invalid orgID, CNewOrgPropPayload.java, 109");
        
        // Load org data
        rs.next();
        
        // Political party ?
        if (rs.getString("type").equals("ID_POL_PARTY"))
        {
            // Address party
            long party=Long.parseLong(UTILS.BASIC.getAdrData(this.target_adr, "pol_party"));
            
            // Member of organization ?
            if (party!=this.orgID)
               throw new Exception("Not a member of this org, CNewOrgPropPayload.java, 109");
            
            // Address political influence
            double pol_inf=Double.parseDouble(UTILS.BASIC.getAdrData(this.target_adr, "pol_inf"));
            
            // Lower than 1000
            if (pol_inf<1000)
               throw new Exception("You need a minimum 1000 political influence, CNewOrgPropPayload.java, 109"); 
        }
        else
        {
            // Address mil unit
            long mil_unit=Long.parseLong(UTILS.BASIC.getAdrData(this.target_adr, "mil_unit"));
            
            // Member of organization ?
            if (mil_unit!=this.orgID)
               throw new Exception("Not a member of this org, CNewOrgPropPayload.java, 109");
            
            // Address warpoints
            long war_points=Long.parseLong(UTILS.BASIC.getAdrData(this.target_adr, "pol_inf"));
            
            // Lower than 1000
            if (war_points<1000)
               throw new Exception("You need a minimum 1000 war points, CNewOrgPropPayload.java, 109"); 
        }
        
        // Valid propID
        if (UTILS.BASIC.isID(propID))
            throw new Exception("Invalid propID, CNewOrgPropPayload.java, 116");
        
        // Prop type
        if (!this.prop_type.equals("ID_DONATE") && 
            !this.prop_type.equals("ID_CHG_DESC") && 
            !this.prop_type.equals("ID_CHG_AVATAR") && 
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
        
        // Change avatar
        if (this.prop_type.equals("ID_CHG_AVATAR"))
            if (!UTILS.BASIC.isPic(this.par_1))
                 throw new Exception("Invalid avatar, CNewOrgPropPayload.java, 116");
        
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
        
        // Expl
        if (!UTILS.BASIC.isDesc(this.expl) || 
            this.expl.length()>1000 || 
            this.expl.length()<10)
        throw new Exception("Invalid explanation, CNewOrgPropPayload.java, 116");
        
        // Another pending proposal ?
        rs=UTILS.DB.executeQuery("SELECT * "
                                 + "FROM orgs_props "
                                + "WHERE adr='"+this.target_adr+"' "
                                  + "AND orgID='"+this.orgID+"' "
                                  + "AND status='ID_VOTING'");
        
        // Has data ?
        if (UTILS.DB.hasData(rs))
           throw new Exception("Yoy already have a pending proposal, CNewOrgPropPayload.java, 116");
        
        // Rejected proposal in the last 5 days ?
        rs=UTILS.DB.executeQuery("SELECT * "
                                 + "FROM orgs_props "
                                + "WHERE adr='"+this.target_adr+"' "
                                  + "AND orgID='"+this.orgID+"' "
                                  + "AND status='ID_REJECTED' "
                                  + "AND block>"+(this.block-7250));
        
        // Has data ?
        if (UTILS.DB.hasData(rs))
           throw new Exception("You have a rejected proposal in the last 5 days, CNewOrgPropPayload.java, 116");
        
        // Organization have at least 25 members ?
        rs=UTILS.DB.executeQuery("SELECT COUNT(*) AS total "
                                 + "FROM adr "
                                + "WHERE pol_party='"+this.orgID+"' "
                                   + "OR mil_unit='"+this.orgID+"'");
        
        // Next
        rs.next();
        
        // Number
        if (rs.getLong("total")<25)
            throw new Exception("Minimum 25 members required, CNewOrgPropPayload.java, 116");
        
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
