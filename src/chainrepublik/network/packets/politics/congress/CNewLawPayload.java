package chainrepublik.network.packets.politics.congress;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
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
           
        // Sign
        this.sign();
    }
    
    public boolean isTax(String tax) throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM taxes "
                                          + "WHERE tax='"+tax+"'");
        
        // Has data ?
        if (UTILS.DB.hasData(rs))
            return true;
        else 
            return false;
    }
    
    public boolean isBonus(String bonus) throws Exception
    {
        // Load bonus
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM bonuses "
                                          + "WHERE bonus='"+bonus+"'");
        
        // Has data ?
        if (UTILS.DB.hasData(rs))
            return true;
        else 
            return false;
    }
    
    public boolean isCit(String user, String cou) throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM adr "
                                          + "WHERE name='"+user+"' "
                                            + "AND cou='"+cou+"'");
        
        if (UTILS.DB.hasData(rs))
            return true;
        else
            return false;
    }
    
    public boolean checkPremium(String list) throws Exception
    {
        // Country
        String cou=UTILS.BASIC.getAdrData(this.target_adr, "cou");
        
        // Explode
        List<String> players = Arrays.asList(list.split(","));
        
        // Parse
        for (int a=0; a<=players.size()-1; a++)
        {
            // Is address ?
            if (!UTILS.BASIC.isDomain(players.get(a)))
                throw new Exception("Invalid name, CNewLawPayload.java, 102");
            
            // Citizen ?
            if (!this.isCit(players.get(a), cou))
                return false;
        }
        
        // Return
        return true;
    }
    
    public void check(CBlockPayload block) throws Exception
    {
   	// Super class
   	super.check(block);
        
         // Check energy
       this.checkEnergy();
       
        // ID
        if (UTILS.BASIC.isID(lawID))
            throw new Exception("This ID already exist, CNewLawPayload.java, 102");
        
        // Country
        String cou=UTILS.BASIC.getAdrData(this.target_adr, "cou");
        
        // Is governor ?
        if (!UTILS.BASIC.isCountryOwner(this.target_adr, cou))
        {
            if (!UTILS.BASIC.isGovernor(this.target_adr, cou))
               throw new Exception("Address is not a governor, CNewLawPayload.java, 102");
            
            if (!UTILS.BASIC.isCongressActive(cou))
               throw new Exception("Congress is not active, CNewLawPayload.java, 102");
        }
        
        // Law type
        if (!this.law_type.equals("ID_CHG_TAX") && 
            !this.law_type.equals("ID_CHG_BONUS") && 
            !this.law_type.equals("ID_ADD_PREMIUM") && 
            !this.law_type.equals("ID_REMOVE_PREMIUM") &&
            !this.law_type.equals("ID_DONATION") && 
            !this.law_type.equals("ID_OFICIAL_ART") &&
            !this.law_type.equals("ID_DISTRIBUTE"))
        throw new Exception("Invalid law type, CNewLawPayload.java, 102");
        
        // Already a pending law ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM laws "
                                          + "WHERE adr='"+this.target_adr+"' "
                                            + "AND status='ID_VOTING'");
        
        // Has data
        if (UTILS.DB.hasData(rs))
            throw new Exception("You already have a pending law, CNewLawPayload.java, 102");
        
        // Check new val against law type
        switch (this.law_type)
        {
            // Change tax
            case "ID_OFICIAL_ART" :  // Amount
                                    long artID=Long.parseLong(this.par_1);
                                    
                                    // Load article data
                                    ResultSet rs2=UTILS.DB.executeQuery("SELECT * "
                                                                        + "FROM tweets "
                                                                       + "WHERE tweetID='"+artID+"'");
                                    
                                    // Author
                                    String author=rs2.getString("adr");
                                    
                                    // Author is congressman ?
                                    if (!UTILS.BASIC.isGovernor(author, UTILS.BASIC.getAdrData(author, "cou")))
                                        throw new Exception("Author is not congressman, CNewLawPayload.java, 102");
                                    
                                   break;
                                   
                                   
            // Change tax
            case "ID_CHG_TAX" :  // Amount
                                    double tax_amount=Double.parseDouble(this.par_2);
                                    
                                    // Max default tax
                                    if (tax_amount<0 || tax_amount>25) 
                                      throw new Exception("Invalid tax value, CNewLawPayload.java, 102");
            
                                   // Tax exist ?
                                   if (!this.isTax(this.par_1))
                                       throw new Exception("Invalid tax, CNewLawPayload.java, 102");
                                   
                                   // Product ?
                                   if (!this.par_3.equals(""))
                                      if (!UTILS.BASIC.isProd(par_3))
                                        throw new Exception("Invalid tax product, CNewLawPayload.java, 102"); 
                                   break;
            
            // Change bonus
            case "ID_CHG_BONUS" : // Amount
                                    double bonus_amount=Double.parseDouble(this.par_3);
                                    
                                    // Min default bonus
                                    if (bonus_amount<0) 
                                      throw new Exception("Invalid bonus value, CNewLawPayload.java, 102");
            
                                   // Bonus exist ?
                                   if (!this.isBonus(this.par_1))
                                       throw new Exception("Invalid bonus, CNewLawPayload.java, 102");
                                   
                                   // Product ?
                                   if (!this.par_2.equals(""))
                                      if (!UTILS.BASIC.isProd(par_2))
                                        throw new Exception("Invalid bonus product, CNewLawPayload.java, 102"); 
                                   
                                   break;
            
            // Add premium
            case "ID_ADD_PREMIUM" : if (!this.checkPremium(this.par_1))
                                       throw new Exception("Invalid list, CNewLawPayload.java, 102");
                                    break;
                                    
            // Remove premium
            case "ID_REMOVE_PREMIUM" : if (!this.checkPremium(this.par_1))
                                            throw new Exception("Invalid list, CNewLawPayload.java, 102");
                                        break;
                                        
            // Donation
            case "ID_DONATION" :  // Check address
                                  if (!UTILS.BASIC.isAdr(this.par_1))                  
                                     throw new Exception("Invalid donation address, CNewLawPayload.java, 102");
                                  
                                  // Amount
                                  double donation_amount=Double.parseDouble(this.par_2);
                                  
                                  // Currency
                                  String cur="CRC";
                                  if (!this.par_3.equals(""))
                                  {
                                      // Currency
                                      cur=this.par_3;
                                      
                                      // Valid currency ?
                                      if (UTILS.BASIC.isCur(this.par_3));
                                         throw new Exception("Invalid currency, CNewLawPayload.java, 102");
                                  }
                                      
                                  // State budget
                                  double budget=UTILS.BASIC.getBudget(cou, cur);
                                  
                                  // Only 5% of budget can be donated
                                  if (budget/20<donation_amount)
                                      throw new Exception("Invalid donation amount, CNewLawPayload.java, 102");
                                  
                                  break;
                                  
            // Donation
            case "ID_DISTRIBUTE" :  // Amount
                                    double amount=Double.parseDouble(this.par_1);
                                  
                                    // Currency
                                    cur="CRC";
                                    if (!this.par_3.equals(""))
                                    {
                                        // Currency
                                       cur=this.par_3;
                                      
                                        // Valid currency ?
                                       if (UTILS.BASIC.isCur(this.par_3));
                                           throw new Exception("Invalid currency, CNewLawPayload.java, 102");
                                    }
                                      
                                  // State budget
                                  budget=UTILS.BASIC.getBudget(cou, cur);
                                  
                                  // Only 5% of budget can be donated
                                  if (budget/20<amount)
                                      throw new Exception("Invalid donation amount, CNewLawPayload.java, 102");
                                  
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
