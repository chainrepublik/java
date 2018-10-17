package chainrepublik.network.packets.politics.congress;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.awt.Point;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

public class CNewLawPayload extends CPayload
{
    // Law ID
    long lawID;
    
    // Law type
    String law_type;
    
    // Par 1
    String par_1;
    
    // Par 2
    String par_2;
    
    // Par 3
    String par_3;
    
    // Expl
    String expl;
    
     // Serial
    private static final long serialVersionUID = 100L;

    
    public CNewLawPayload(String adr, 
                          String law_type, 
                          String par_1, 
                          String par_2, 
                          String par_3, 
                          String expl) throws Exception
    {
        // Superclass
	super(adr);
        
        // Law ID
        this.lawID=UTILS.BASIC.getID();
        
        // Type
        this.law_type=law_type;
        
        // Par 1
        this.par_1=par_1;
        
        // Par 2
        this.par_2=par_2;
        
        // Par 3
        this.par_3=par_3;
        
        // Expl
        this.expl=expl;
        
        // Hash
 	hash=UTILS.BASIC.hash(this.getHash()+
                              this.lawID+
                              this.law_type+
                              this.par_1+
                              this.par_2+
                              this.par_3+
                              this.expl);
   
    }
    
    
    public void check(CBlockPayload block) throws Exception
    {
   	// Super class
   	super.check(block);
        
         // Check for null
        if (this.law_type==null ||
            this.expl==null)
        throw new Exception("Null assertion failed - CDelVotePayload.java, 68");
        
        // Check energy
        this.checkEnergy();
       
        // Citizen address ?
        if (!UTILS.BASIC.isCitAdr(this.target_adr, this.block))
           throw new Exception("Only citizens can do this action - CWorkPayload.java, 68");
       
        // ID
        if (UTILS.BASIC.isID(lawID))
            throw new Exception("This ID already exist, CNewLawPayload.java, 102");
        
        // Country
        String cou=UTILS.BASIC.getAdrData(this.target_adr, "cou");
        
        // Is governor ?
        if (!UTILS.BASIC.isCountryOwner(this.target_adr, cou))
        {
            // Political endorsement
            if (Long.parseLong(UTILS.BASIC.getAdrData(this.target_adr, "pol_endorsed"))<100)
               throw new Exception("Minimum political endorsement is 100, CNewLawPayload.java, 102");
                
            // Governor ?
            if (!UTILS.BASIC.isGovernor(this.target_adr, cou))
               throw new Exception("Address is not a governor, CNewLawPayload.java, 102");
            
            // Congress is active
            if (!UTILS.BASIC.isCongressActive(cou, this.block))
               throw new Exception("Congress is not active, CNewLawPayload.java, 102");
            
            // Rejected law in the last 5 days ?
            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM laws "
                                              + "WHERE adr='"+this.target_adr+"' "
                                                + "AND status='ID_REJECTED' "
                                                + "AND block>"+(this.block-7200));
            
            // Has data
            if (UTILS.DB.hasData(rs))
               throw new Exception("Rejected law found, CNewLawPayload.java, 102");
        }
        
        // Law type
        if (!this.law_type.equals("ID_CHG_TAX") && 
            !this.law_type.equals("ID_CHG_BONUS") && 
            !this.law_type.equals("ID_ADD_PREMIUM") && 
            !this.law_type.equals("ID_REMOVE_PREMIUM") &&
            !this.law_type.equals("ID_DONATION") && 
            !this.law_type.equals("ID_OFICIAL_ART") &&
            !this.law_type.equals("ID_DISTRIBUTE") &&
            !this.law_type.equals("ID_START_WAR") &&
            !this.law_type.equals("ID_MOVE_WEAPONS") &&
            !this.law_type.equals("ID_ATTACK") && 
            !this.law_type.equals("ID_BUY_WEAPONS"))
        throw new Exception("Invalid law type, CNewLawPayload.java, 102");
        
        // Already a pending law ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM laws "
                                          + "WHERE adr='"+this.target_adr+"' "
                                            + "AND status='ID_VOTING'");
        
        // Has data
        if (UTILS.DB.hasData(rs))
            throw new Exception("You already have a pending law, CNewLawPayload.java, 102");
        
        // Explanations
        if (!UTILS.BASIC.isDesc(this.expl) || 
            this.expl.length()>1000)
        throw new Exception("Invalid explanation, CNewLawPayload.java, 102");
        
        // Check new val against law type
        switch (this.law_type)
        {
            // Change tax
            case "ID_OFICIAL_ART" : // Check
                                    if (!UTILS.LAWS.checkOfArtLaw(Long.parseLong(this.par_1)))
                                        throw new Exception("Check failed, CNewLawPayload.java, 102");
                                    
                                   break;
                                   
                                   
            // Change tax
            case "ID_CHG_TAX" :     // Check
                                    if (!UTILS.LAWS.checkChgTaxLaw(this.par_1, 
                                                                   Double.parseDouble(this.par_2), 
                                                                   par_3))
                                        throw new Exception("Check failed, CNewLawPayload.java, 102");
                                    
                                   break;
            
            // Change bonus
            case "ID_CHG_BONUS" : if (!UTILS.LAWS.checkChgBonusLaw(this.par_1, 
                                                                   this.par_2, 
                                                                   Double.parseDouble(this.par_3)))
                                     throw new Exception("Check failed, CNewLawPayload.java, 102");
                                  
                                   break;
            
            // Add premium
            case "ID_ADD_PREMIUM" : // Check
                                    if (!UTILS.LAWS.checkPremiumLaw(cou, this.par_1))
                                       throw new Exception("Check failed, CNewLawPayload.java, 102"); 
                                       
                                    break;
                                    
            // Remove premium
            case "ID_REMOVE_PREMIUM" : // Check
                                       if (!UTILS.LAWS.checkPremiumLaw(cou, this.par_1))
                                           throw new Exception("Check failed, CNewLawPayload.java, 102"); 
                                       
                                       break;
                                        
            // Donation
            case "ID_DONATION" :  // Check
                                  if (!UTILS.LAWS.checkDonationLaw(cou, this.par_1, Double.parseDouble(this.par_2)))
                                     throw new Exception("Check failed, CNewLawPayload.java, 102"); 
                                  
                                  break;
                                  
            // Donation
            case "ID_DISTRIBUTE" :  if (!UTILS.LAWS.checkDistributeLaw(cou, Double.parseDouble(this.par_1)))
                                       throw new Exception("Check failed, CNewLawPayload.java, 102"); 
                                    
                                    break;
            
            // Start war
            case "ID_START_WAR" : // Check
                                  if (!UTILS.LAWS.checkStartWarLaw(cou, 
                                                                   this.par_1, 
                                                                   this.par_2, 
                                                                   this.block))
                                      throw new Exception("Check failed, CNewLawPayload.java, 102"); 
                                  
                                  break;
            
            // Move equipment
            case "ID_MOVE_WEAPONS" : // Check
                                     double cost=UTILS.LAWS.checkMoveWeaponsLaw(cou, this.par_1, this.par_2, this.par_3);
                                     
                                     if (cost==0)
                                         throw new Exception("Check failed, CNewLawPayload.java, 102"); 
                                     
                                     break;
            
            // Launch attack
            case "ID_ATTACK" : // Check attack params
                               if (!UTILS.LAWS.checkAttackLaw(cou,
                                                              this.par_1, 
                                                              this.par_2, 
                                                              this.par_3))
                                       throw new Exception("Invalid attack params, CNewLawPayload.java, 102");
                               break;
                                      
             // Buy weapons
            case "ID_BUY_WEAPONS" : // Check attack params
                                    if (!UTILS.LAWS.checkBuyLaw(cou,
                                                                this.par_1, 
                                                                this.par_2))
                                    throw new Exception("Invalid buy weapons params, CNewLawPayload.java, 102");
                                      
                                      break;
        }
        
        // Hash
 	String h=UTILS.BASIC.hash(this.getHash()+
                                  this.lawID+
                                  this.law_type+
                                  this.par_1+
                                  this.par_2+
                                  this.par_3+
                                  this.expl);
        
        // Same hash ?
        if (!h.equals(this.hash))
             throw new Exception("Invalid hash, CNewLawPayload.java, 102");
    }
    
    public void commit(CBlockPayload block) throws Exception
    {
        // Superclass
        super.commit(block);
        
        // Status
        String status;
        if (UTILS.BASIC.isCountryOwner(this.target_adr, UTILS.BASIC.getAdrData(this.target_adr, "cou")))
            status="ID_APROVED";
        else
            status="ID_VOTING";
        
        // Country
        String cou=UTILS.BASIC.getAdrData(this.target_adr, "cou");
        
        // Insert endorser
        UTILS.DB.executeUpdate("INSERT INTO laws "
                                     + "SET lawID='"+this.lawID+"', "
                                         + "adr='"+this.target_adr+"', "
                                         + "type='"+this.law_type+"', "
                                         + "par_1='"+UTILS.BASIC.base64_encode(this.par_1)+"', "
                                         + "par_2='"+UTILS.BASIC.base64_encode(this.par_2)+"', "
                                         + "par_3='"+UTILS.BASIC.base64_encode(this.par_3)+"', "
                                         + "expl='"+UTILS.BASIC.base64_encode(this.expl)+"', "
                                         + "country='"+cou+"', "
                                         + "status='"+status+"', "
                                         + "voted_yes='0', "
                                         + "voted_no='0', "
                                         + "block='"+this.block+"'");
        
        // Country owner ?
        if (UTILS.BASIC.isCountryOwner(this.target_adr, cou))
            UTILS.CRONS.implementLaw(this.lawID, this.block);
        
        // Position type
        UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
    }
    
}
