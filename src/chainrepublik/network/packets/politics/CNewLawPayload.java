package chainrepublik.network.packets.politics;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CNewLawPayload extends CPayload
{
    // Law type
    String law_type;
    
    // Bonus
    String bonus;
    
    // Tax
    String tax;
    
    // New value
    double new_val;
    
    // Expl
    String expl;
    
    // Country
    String country;
    
    // Law ID
    long lawID;
    
    public CNewLawPayload(String adr, 
                          String law_type, 
                          String bonus, 
                          String tax, 
                          String expl, 
                          String country, 
                          double new_val) throws Exception
    {
        // Superclass
	super(adr);
        
        // Type
        this.new_val=new_val;
        
        // Bonus
        this.bonus=bonus;
        
        // Tax
        this.tax=tax;
        
        // Expl
        this.expl=expl;
        
        // Country
        this.country=country;
        
        // Law ID
        this.lawID=UTILS.BASIC.getID();
        
        // Hash
 	hash=UTILS.BASIC.hash(this.getHash()+
                              this.bonus+
                              this.tax+
                              this.expl+
                              this.country+
 			      this.new_val+
                              this.lawID);
           
        // Sign
        this.sign();
    }
    
    public boolean isTax(String tax)
    {
        return false;
    }
    
    public boolean isBonus(String tax)
    {
        return false;
    }
    
    public void check(CBlockPayload block) throws Exception
    {
   	// Super class
   	super.check(block);
        
         // Check energy
       this.checkEnergy();
        
        // Registered endorser ?
        if (UTILS.BASIC.isRegistered(this.target_adr))
            throw new Exception("Target address is already registered, CEndorsePayload.java, 102");
        
        // ID
        if (UTILS.BASIC.isID(lawID))
            throw new Exception("This ID already exist, CEndorsePayload.java, 102");
        
        // Load proponent data
        ResultSet rs_prop=UTILS.DB.executeQuery("SELECT * "
                                                + "FROM adr "
                                               + "WHERE adr='"+this.target_adr+"'");
        
        // Load data
        rs_prop.next();
        
        // Is governor ?
        if (!UTILS.BASIC.isGovernor(this.target_adr, rs_prop.getString("cou")))
             throw new Exception("Address is not a governor, CEndorsePayload.java, 102");
        
        // Law type
        if (!this.law_type.equals("ID_CHANGE_TAX") && 
            !this.law_type.equals("ID_CHANGE_BONUS") && 
            !this.law_type.equals("ID_START_WAR"))
        throw new Exception("Invalid law type, CEndorsePayload.java, 102");
        
        // New val
        if (this.new_val<0)
            throw new Exception("Invalid new value, CEndorsePayload.java, 102");
        
        // Already a pending law ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM laws "
                                          + "WHERE adr='"+this.target_adr+"' "
                                            + "AND status='ID_PENDING'");
        
        // Has data
        if (UTILS.DB.hasData(rs))
            throw new Exception("You already have a pending law, CEndorsePayload.java, 102");
        
        // Check new val against law type
        switch (this.law_type)
        {
            // Change tax
            case "ID_CHANGE_TAX" : // Max default tax
                                    if (this.new_val>25) 
                                      throw new Exception("Invalid new value, CEndorsePayload.java, 102");
            
                                   // Tax exist ?
                                   if (!this.isTax(this.tax))
                                       throw new Exception("Invalid tax, CEndorsePayload.java, 102");
                                       
                                   break;
            
            // Change bonus
            case "ID_CHANGE_BONUS" : // Bonus exist ? ?
                                   if (!this.isBonus(this.tax))
                                       throw new Exception("Invalid bonus, CEndorsePayload.java, 102");
                                   
                                   break;
            
            // Start war
            case "ID_START_WAR" : if (!UTILS.BASIC.isCountry(this.country))
                                     throw new Exception("Invalid country, CEndorsePayload.java, 102");
                                  break;
        }
    }
    
    public void commit(CBlockPayload block) throws Exception
    {
        // Superclass
        super.commit(block);
        
        // Insert endorser
        UTILS.DB.executeUpdate("INSERT INTO laws "
                                     + "SET lawID='"+this.lawID+"', "
                                         + "adr='"+this.target_adr+"', "
                                         + "type='"+this.law_type+"', "
                                         + "bonus='"+this.bonus+"', "
                                         + "tax='"+this.tax+"', "
                                         + "expl='"+UTILS.BASIC.base64_encode(this.expl)+"', "
                                         + "new_val='"+this.new_val+"', "
                                         + "country='"+this.country+"', "
                                         + "status='ID_PENDING', "
                                         + "voted_yes='0', "
                                         + "voted_no='0', "
                                         + "block='"+this.block+"'");
        
        // Position type
        UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
    }
    
}
