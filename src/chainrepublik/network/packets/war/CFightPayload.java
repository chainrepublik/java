package chainrepublik.network.packets.war;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CFightPayload extends CPayload
{
   // War ID
   long warID;
   
   // Type
   String type;
    
   public CFightPayload(String adr, 
                         long warID, 
                         String type) throws Exception
   {
        // Superclass
	super(adr);
        
        // War ID
        this.warID=warID;
        
        // Type
        this.type=type;
        
        // Hash
 	hash=UTILS.BASIC.hash(this.getHash()+
 			      this.warID+
                              this.type);
    }
    
    public void check(CBlockPayload block) throws Exception
    {
   	// Super class
   	super.check(block);
        
        // Registered
        if (!UTILS.BASIC.isRegistered(this.target_adr))
            throw new Exception("Target address is not registered, CFightPayload.java, 102");
        
        // Address country
        String adr_cou=UTILS.BASIC.getAdrData(this.target_adr, "cou");
        
        // Address location
        String adr_loc=UTILS.BASIC.getAdrData(this.target_adr, "loc");
        
        // Address energy
        double adr_ene=Double.parseDouble(UTILS.BASIC.getAdrData(this.target_adr, "energy"));
        
        // Energy minimum 10
        if (adr_ene<10)
           throw new Exception("Minimum energy is 10, CFightPayload.java, 102");   
        
        // Required energy
        this.checkEnergy(Math.round(adr_ene-5));
        
        // War ID exist ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM wars "
                                         + "WHERE warID='"+this.warID+"' "
                                           + "AND status='ID_ACTIVE'");
       
        // Has data
        if (!UTILS.DB.hasData(rs))
          throw new Exception("Target address is not registered, CFightPayload.java, 102");  
        
        // Next
        rs.next();
        
        // Can't fight against his own country
        if (this.type.equals("ID_AT"))
          if (rs.getString("defender").equals(adr_cou))
             throw new Exception("You can't fight against your own country, CFightPayload.java, 102");  
        
        if (this.type.equals("ID_DE"))
          if (rs.getString("attacker").equals(adr_cou))
             throw new Exception("You can't fight against your own country, CFightPayload.java, 102");  
        
        // Check location
        if (!rs.getString("target").equals(adr_loc))
            throw new Exception("Invalid fighter location, CFightPayload.java, 102");  
        
        // Weapons
        boolean has_attack=false;
        boolean has_defense=false;
        
        // Has at least one attack and defense  weapon
        rs=UTILS.DB.executeQuery("SELECT * "
                                 + "FROM stocuri "
                                + "WHERE (adr='"+this.target_adr+"' OR rented_to='"+this.target_adr+"') "
                                  + "AND in_use>0");
        
        while (rs.next())
        {
           // Attack weapon ?
           if (UTILS.BASIC.isAttackWeapon(rs.getString("tip")))
               has_attack=true;
           
           // Defense weapon ?
           if (UTILS.BASIC.isDefenseWeapon(rs.getString("tip")))
               has_defense=true;
        }
       
        // Attack and defense
        if (!has_attack || !has_defense)
           throw new Exception("No weapon found, CFightPayload.java, 102");  
        
        // Check hash
        String h=UTILS.BASIC.hash(this.getHash()+
 			          this.warID+
                                  this.type);
        
        // Hash match ?
        if (!h.equals(this.hash))
           throw new Exception("Invalid hash, CFightPayload.java, 102");
    }
    
    public void commit(CBlockPayload block) throws Exception
    {
         // Superclass
         super.commit(block);
         
         // Damage
         long damage=0;
         
         // Address energy
        double adr_ene=Double.parseDouble(UTILS.BASIC.getAdrData(this.target_adr, "energy"));
        
        // Address attack
        double attack=UTILS.BASIC.getAdrAttack(this.target_adr);
        
        // Address defense
        double defense=UTILS.BASIC.getAdrDefense(this.target_adr);
         
         // Fight for attacker ?
         if (this.type.equals("ID_AT"))
             damage=Math.round((attack*0.6+defense*0.4)*(adr_ene-5)/100);
         else
             damage=Math.round((attack*0.4+defense*0.6)*(adr_ene-5)/100);
         
         // Load war data
         ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                            + "FROM wars "
                                           + "WHERE warID='"+this.warID+"'");
         
         // Next ?
         rs.next();
         
         // Damage compensation
         long p=Math.round((this.block-rs.getLong("block"))*0.05);
         
         // Adjust damage
         damage=Math.round(damage-damage*p/100);
         
         // Insert fight
         UTILS.DB.executeUpdate("INSERT INTO wars_fighters "
                                      + "SET adr='"+this.target_adr+"', "
                                           + "warID='"+this.warID+"', "
                                           + "damage='"+damage+"', "
                                           + "type='"+this.type+"', "
                                           + "lawID=0, "
                                           + "block='"+this.block+"'");
         
         // Update war
         if (this.type.equals("ID_AT"))
         UTILS.DB.executeUpdate("UPDATE wars "
                                 + "SET attacker_points=attacker_points+"+damage+" "
                               + "WHERE warID='"+this.warID+"'");
         else
         UTILS.DB.executeUpdate("UPDATE wars "
                                 + "SET defender_points=defender_points+"+damage+" "
                               + "WHERE warID='"+this.warID+"'"); 
         
         // Increse mil points
         UTILS.DB.executeUpdate("UPDATE adr "
                                 + "SET war_points=war_points+"+damage+" "
                               + "WHERE adr='"+this.target_adr+"'");
         
         // Clear trans
         UTILS.ACC.clearTrans(this.hash, "ID_ALL", this.block);
    }
}