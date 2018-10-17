package chainrepublik.kernel;

import java.awt.Point;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

public class CLaws 
{
    public void implementOfArt(long artID, long block) throws Exception
    {
       if (this.checkOfArtLaw(artID))
       {
          // Update
          UTILS.DB.executeUpdate("UPDATE tweets "
                                  + "SET of_dec='"+block+"' "
                                + "WHERE tweetID='"+artID+"'");    
       }
    }
    
    public String getLawCou(long lawID) throws Exception
    {
        // Load law data
       ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM laws "
                                         + "WHERE lawID='"+lawID+"'");
       
       // Next
       rs.next();
       
       // Return
       return rs.getString("country");
    }
    
    public void implementMoveWeapons(long lawID, 
                                     String weapons, 
                                     String target_type, 
                                     String targetID,
                                     long block) throws Exception
    {
        // Decode
        weapons=UTILS.BASIC.base64_decode(weapons);
        target_type=UTILS.BASIC.base64_decode(target_type);
        targetID=UTILS.BASIC.base64_decode(targetID);
        
        // Target type
        if (target_type.equals("ID_WEAPON"))
           target_type=this.getTargetType(target_type, Long.parseLong(targetID));
        
        // Check target type
        if (!UTILS.BASIC.isStringID(target_type))
            throw new Exception("Invalid target type, CLaws.java, 48");
        
        // Check target ID
        if (!UTILS.BASIC.isCountry(targetID) && 
            !UTILS.BASIC.isLong(targetID))
        throw new Exception("Invalid target ID, CLaws.java, 48");
        
        // Get country
        String cou=this.getLawCou(lawID);
        
        // Check
        double cost=this.checkMoveWeaponsLaw(cou, weapons, target_type, targetID);
        
        // Check
        if (cost==0)
        {
            this.lawErr(lawID, "Unexpected error", block);
            return;
        }
        
        // Explode
        List<String> wlist = Arrays.asList(weapons.split(","));
        
        // Parse
        for (int a=0; a<=wlist.size()-1; a++)
        {
            // Weapon ID
            long wID=Long.parseLong(wlist.get(a).trim());
            
            // Weapon position
            Point w_pos=UTILS.BASIC.getWeaponPos(wID);
            
            // Target pos
            Point target_pos=UTILS.BASIC.getLocPos(target_type, targetID);
            
            // Distance
            long dist=UTILS.BASIC.getPointDist(w_pos, target_pos);
            
            // Update weapon
            UTILS.DB.executeUpdate("UPDATE stocuri "
                                    + "SET war_loc_type='"+target_type+"', "
                                        + "war_locID='"+targetID+"', "
                                        + "war_status='ID_TRANSIT', "
                                        + "war_arrive='"+(block+dist)+"' "
                                  + "WHERE stocID='"+wID+"'");
            
            // Navy destroyer or aircraft carrier ?
            UTILS.DB.executeUpdate("UPDATE stocuri "
                                    + "SET war_status='ID_TRANSIT', "
                                        + "war_arrive='"+(block+dist)+"' "
                                  + "WHERE war_locID='"+wID+"'");
            
            
            
        }
     
        // Pay
        UTILS.ACC.newTransfer(UTILS.BASIC.getCouAdr(cou), 
                              "default", 
                              cost, 
                              "CRC", 
                              "Weapons movement fee", 
                              "", 
                              0, 
                              UTILS.BASIC.hash(String.valueOf(lawID)), 
                              block, 
                              false, 
                              "", 
                              "");
        
        // Clear
        UTILS.ACC.clearTrans(UTILS.BASIC.hash(String.valueOf(lawID)), "ID_ALL", block);
    }
    
    public void implementBuyWeapons(long lawID, 
                                    String posID, 
                                    String qty,
                                    long block) throws Exception
    {
        // Get country
        String cou=this.getLawCou(lawID);
        
        // Decode
        posID=UTILS.BASIC.base64_decode(posID);
        qty=UTILS.BASIC.base64_decode(qty);
        
        // Check
        if (!this.checkBuyLaw(cou, posID, qty))
        {
            this.lawErr(lawID, "Unexpected error", block);
            return; 
        }
        
        // Load position data
        ResultSet rs=UTILS.DB.executeQuery("SELECT am.*, "
                                                + "amp.adr AS seller_adr, "
                                                + "amp.orderID, "
                                                + "amp.price, "
                                                + "tp.expires AS prod_expires "
                                           + "FROM assets_mkts_pos AS amp "
                                           + "JOIN assets_mkts AS am ON amp.mktID=am.mktID "
                                           + "JOIN tipuri_produse AS tp ON tp.prod=am.asset "
                                          + "WHERE amp.orderID='"+posID+"'");
        
        // Next
        rs.next();
        
        // Take weapons
        UTILS.DB.executeUpdate("UPDATE assets_mkts_pos "
                                + "SET qty=qty-"+qty+" "
                              + "WHERE orderID='"+posID+"'");
        
        
        // Deliver weapons
        for (int a=1; a<=Long.parseLong(qty); a++)
        {
            // Counter
            long b=1;
            
            // Stoc ID
            while (UTILS.BASIC.isID(lawID+b))
                b++;
            
            if (rs.getString("asset").equals("ID_NAVY_DESTROYER") || 
                rs.getString("asset").equals("ID_AIRCRAFT_CARRIER"))
                UTILS.DB.executeUpdate("INSERT INTO stocuri "
                                             + "SET adr='"+UTILS.BASIC.getCouAdr(cou)+"', "
                                                 + "tip='"+rs.getString("asset")+"', "
                                                 + "qty=1, "
                                                 + "expires='"+(block+rs.getLong("prod_expires"))+"', "
                                                 + "stocID='"+(lawID+b)+"', "
                                                 + "war_loc_type='ID_SEA', "
                                                 + "war_locID='1528888939427', "
                                                 + "war_status='ID_READY', "
                                                 + "block='"+block+"'");
            else
                UTILS.DB.executeUpdate("INSERT INTO stocuri "
                                             + "SET adr='"+UTILS.BASIC.getCouAdr(cou)+"', "
                                                 + "tip='"+rs.getString("asset")+"', "
                                                 + "qty=1, "
                                                 + "expires='"+(block+rs.getLong("prod_expires"))+"', "
                                                 + "stocID='"+(lawID+b)+"', "
                                                 + "war_loc_type='ID_LAND', "
                                                 + "war_locID='"+cou+"', "
                                                 + "war_status='ID_READY', "
                                                 + "block='"+block+"'");
        }
        
        // Make payment
        UTILS.ACC.newTransfer(UTILS.BASIC.getCouAdr(cou), 
                              rs.getString("seller_adr"), 
                              Long.parseLong(qty)*rs.getDouble("price"), 
                              "CRC", 
                              "Congress weapons aquisition", 
                              "", 
                              0, 
                              UTILS.BASIC.hash(String.valueOf(lawID)), 
                              block, 
                              false, 
                              "", 
                              "");
        
        // Clear
        UTILS.ACC.clearTrans(UTILS.BASIC.hash(String.valueOf(lawID)), "ID_ALL", block);
        
        // Remove zero qty positions
        UTILS.DB.executeUpdate("DELETE FROM assets_mkts_pos "
                                   + "WHERE qty=0");
    }
    
    public void implementAttack(long lawID, 
                                String list, 
                                String warID, 
                                String side,
                                long block) throws Exception
    {
         // Damage
        long damage=0;
        
         // Get country
        String cou=this.getLawCou(lawID);
        
        // Decode
        list=UTILS.BASIC.base64_decode(list);
        warID=UTILS.BASIC.base64_decode(warID);
        side=UTILS.BASIC.base64_decode(side);
        
        // Check
        if (!this.checkAttackLaw(cou, list, warID, side))
        {
            this.lawErr(lawID, "Unexpected error", block);
            return; 
        }
        
         // Explode
        List<String> weapons = Arrays.asList(list.split(","));
        
        // Parse
        for (int a=0; a<=weapons.size()-1; a++)
        {
            // Weapon ID
            long wID=Long.parseLong(weapons.get(a).trim());
            
            // Load weapon data
            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM stocuri AS st "
                                               + "JOIN tipuri_produse AS tp ON tp.prod=st.tip "
                                              + "WHERE st.stocID='"+wID+"'");
            
            // Next 
            rs.next();
            
            // Total damage
            damage=damage+rs.getLong("damage");
            
            // Remove weapon
            UTILS.DB.executeUpdate("DELETE FROM stocuri "
                                       + "WHERE stocID='"+wID+"'");
        }
        
        // Load war data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM wars "
                                          + "WHERE warID='"+warID+"'");
        
        // Next
        rs.next();
        
        // Damage compensation
        long p=Math.round((block-rs.getLong("block"))*0.05);
         
        // Adjust damage
        damage=Math.round(damage-damage*p/100);
        
        // Apply damage
        if (side.equals("ID_AT"))
            UTILS.DB.executeUpdate("UPDATE wars "
                                    + "SET attacker_points=attacker_points+"+damage+" "
                                  + "WHERE warID='"+Long.parseLong(warID)+"'");
        else
            UTILS.DB.executeUpdate("UPDATE wars "
                                    + "SET defender_points=defender_points+"+damage+" "
                                  + "WHERE warID='"+Long.parseLong(warID)+"'");
        
        // Add fight
        UTILS.DB.executeUpdate("INSERT INTO wars_fighters "
                                     + "SET warID='"+Long.parseLong(warID)+"', "
                                         + "adr='"+UTILS.BASIC.getCouAdr(cou)+"', "
                                         + "damage='"+damage+"', "
                                         + "type='"+side+"', "
                                         + "lawID='"+lawID+"', "
                                         + "block='"+block+"'");
    }
    
    public void implementStartWar(long lawID, 
                                  String defender, 
                                  String target, 
                                  long block) throws Exception
    {
        // Get country
        String cou=this.getLawCou(lawID);
        
        // Check
        if (!this.checkStartWarLaw(cou, 
                                   UTILS.BASIC.base64_decode(defender), 
                                   UTILS.BASIC.base64_decode(target),
                                   block))
        {
            this.lawErr(lawID, "Unexpected error", block);
            return;
        }
        
        // Start war
        UTILS.DB.executeUpdate("INSERT INTO wars "
                                     + "SET warID='"+lawID+"', "
                                         + "attacker='"+cou+"', "
                                         + "defender='"+UTILS.BASIC.base64_decode(defender)+"', "
                                         + "target='"+UTILS.BASIC.base64_decode(target)+"', "
                                         + "attacker_points='0', "
                                         + "defender_points='0', "
                                         + "status='ID_ACTIVE', "
                                         + "lawID='"+lawID+"', "
                                         + "block='"+block+"'");
        
        // Fee
        if (block>25000)
        {
            // Country address
            String cou_adr=UTILS.BASIC.getCouAdr(cou);
                                
            // Payment
            UTILS.ACC.newTransfer(cou_adr, 
                                  "default",
                                  1, 
                                  "CRC", 
                                  "Start war fee (law "+lawID+")", 
                                  "", 
                                  0,
                                  UTILS.BASIC.hash(String.valueOf(block)), 
                                  block,
                                  false,
                                  "",
                                  "");  
            
            // Clear
            UTILS.ACC.clearTrans(UTILS.BASIC.hash(String.valueOf(block)), 
                                                  "ID_ALL", 
                                                   block);
        }
    }
    
    public void lawErr(long lawID, String reason, long block) throws Exception
   {
       // Load law data
       ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM laws "
                                         + "WHERE lawID='"+lawID+"'");
       
       // Next
       rs.next();
       
       // Country address
       String cou_adr=UTILS.BASIC.getCouAdr(rs.getString("country"));
       
       // Post event
       UTILS.BASIC.newEvent(cou_adr, "Law "+lawID+" could not be implemented for reason : "+reason, block);
       
       // Change law status
       UTILS.DB.executeUpdate("UPDATE laws "
                               + "SET status='ID_ERR' "
                             + "WHERE lawID='"+lawID+"'");
   }
   
   public void propErr(long propID, String reason, long block) throws Exception
   {
       // Load law data
       ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM orgs_props "
                                         + "WHERE propID='"+propID+"'");
       
       // Next
       rs.next();
       
       // Country address
       long orgID=rs.getLong(rs.getString("orgID"));
       
       // Load org data
       rs=UTILS.DB.executeQuery("SELECT * "
                                + "FROM orgs "
                               + "WHERE orgID='"+orgID+"'");
       
       // Next
       rs.next();
       
       // Org address
       String adr=rs.getString("adr");
       
       // Post event
       UTILS.BASIC.newEvent(adr, "Proposal "+propID+" could not be implemented for reason : "+reason, block);
       
       // Change law status
       UTILS.DB.executeUpdate("UPDATE orgs_props "
                               + "SET status='ID_ERR' "
                             + "WHERE propID='"+propID+"'");
   }
    
    public void implementChgBonus(String cou, 
                                  String par_1, 
                                  String par_2, 
                                  String par_3) throws Exception
    {
       // Bonus
       String bonus=UTILS.BASIC.base64_decode(par_1);
                                  
       // Amount
       double bonus_amount=Double.parseDouble(UTILS.BASIC.base64_decode(par_3));
                                  
        // Prod
        String bonus_prod=UTILS.BASIC.base64_decode(par_2);
                                  
        // Update
        UTILS.DB.executeUpdate("UPDATE bonuses "
                                + "SET amount='"+bonus_amount+"' "
                              + "WHERE bonus='"+bonus+"' "
                                + "AND prod='"+bonus_prod+"' "
                                + "AND cou='"+cou+"'");    
    }
    
    public void implementChgTax(String cou, 
                                String par_1, 
                                String par_2, 
                                String par_3) throws Exception
    {
        // Tax
        String tax=UTILS.BASIC.base64_decode(par_1);
                                  
        // Amount
        double tax_amount=Double.parseDouble(UTILS.BASIC.base64_decode(par_2));
                                  
        // Prod
        String tax_prod=UTILS.BASIC.base64_decode(par_3);
                                  
        // Update
        if (tax.equals("ID_SALE_TAX"))
        UTILS.DB.executeUpdate("UPDATE taxes "
                                + "SET value='"+tax_amount+"' "
                              + "WHERE tax='"+tax+"' "
                                + "AND prod='"+tax_prod+"' "
                                + "AND cou='"+cou+"'");
                                  
        else
        UTILS.DB.executeUpdate("UPDATE taxes "
                                + "SET value='"+tax_amount+"' "
                              + "WHERE tax='"+tax+"' "
                                + "AND cou='"+cou+"'");
    }
    
    public void implementAddPremium(String cou, 
                                    long block, 
                                    String par_1) throws Exception
    {
        // List
        String list=UTILS.BASIC.base64_decode(par_1);
                                     
        // Explode
        List<String> players = Arrays.asList(list.split(","));
        
        // Parse
        for (int a=0; a<=players.size()-1; a++)
            UTILS.DB.executeUpdate("UPDATE adr "
                                    + "SET premium='"+block+"' "
                                  + "WHERE name='"+players.get(a).trim()+"'");
    }
    
    public void implementRemovePremium(String cou, 
                                       String par_1) throws Exception
    {
        // List
        String list=UTILS.BASIC.base64_decode(par_1);
                                     
        // Explode
        List<String> players = Arrays.asList(list.split(","));
        
        // Parse
        for (int a=0; a<=players.size()-1; a++)
        UTILS.DB.executeUpdate("UPDATE adr "
                                + "SET premium=0 "
                              + "WHERE name='"+players.get(a).trim()+"'");
    }
    
    public void implementDonation(long lawID,
                                  String cou, 
                                  String par_1, 
                                  String par_2,
                                  long block) throws Exception
    {
        // Donation amount
        double amount=Double.parseDouble(UTILS.BASIC.base64_decode(par_2));
        
        // Check 
        if (!this.checkDonationLaw(cou, par_1, amount))
        {
            this.lawErr(lawID, "Unexpected error", block);
            return;
        }
        
        // Country address
        String cou_adr=UTILS.BASIC.getCouAdr(cou);
                                
        // Payment
        UTILS.ACC.newTransfer(cou_adr, 
                              par_1,
                              amount, 
                              "CRC", 
                              "State budget donation (law "+lawID+")", 
                              "", 
                              0,
                              UTILS.BASIC.hash(String.valueOf(block)), 
                              block,
                              false,
                              "",
                              "");   
                                              
        // Clear
        UTILS.ACC.clearTrans(UTILS.BASIC.hash(String.valueOf(block)), 
                                              "ID_ALL", 
                                              block);
                                    
    }
    
    public void implementDistribute(long lawID, 
                                    String cou, 
                                    String par_1, 
                                    long block) throws Exception
    {
        // Distribution amount
        double amount=Double.parseDouble(UTILS.BASIC.base64_decode(par_1));
        
        // Check
        if (!this.checkDistributeLaw(cou, amount))
        {
            this.lawErr(lawID, "Unexpected error", block);
            return;
        }
                                
        // Country address
        String cou_adr=UTILS.BASIC.getCouAdr(cou);
                                  
        // Number of premium citizens
        ResultSet rs=UTILS.DB.executeQuery("SELECT COUNT(*) AS total "
                                           + "FROM adr "
                                          + "WHERE cou='"+cou+"' "
                                            + "AND premium>0");
                                           
        // Next
        rs.next();
                                           
        // Total
        double total=rs.getLong("total");
                                           
        // Per citizen
        double per_cit=UTILS.BASIC.round(amount/total, 4);
                                           
        // Minimum 0.0001 / citien ?
        if (per_cit>=0.0001)
        {
            // Load all premium citizens
            rs=UTILS.DB.executeQuery("SELECT * "
                                     + "FROM adr "
                                    + "WHERE cou='"+cou+"' "
                                      + "AND premium>0");
                                                
            while (rs.next())
            UTILS.ACC.newTransfer(cou_adr, 
                                  rs.getString("adr"),
                                  per_cit, 
                                  "CRC", 
                                  "State budget donation (lawID : "+lawID+")", 
                                  "", 
                                  0,
                                  UTILS.BASIC.hash(String.valueOf(block)), 
                                  block,
                                  false,
                                  "",
                                  ""); 
                                                
            // Clear
            UTILS.ACC.clearTrans(UTILS.BASIC.hash(String.valueOf(block)), 
                                 "ID_ALL", 
                                 block);
        }
    }
    
    public boolean checkOfArtLaw(long artID) throws Exception
    {
         // Load article data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM tweets "
                                          + "WHERE tweetID='"+artID+"'");
        
        // Has data ?
        if (!UTILS.DB.hasData(rs))
            return false;
        
        // Author
        String author=rs.getString("adr");
                                    
        // Author is congressman ?
        if (!UTILS.BASIC.isGovernor(author, UTILS.BASIC.getAdrData(author, "cou")))
            return false;
        else
            return true;
    }
    
    public boolean checkChgTaxLaw(String tax, 
                                  double tax_amount, 
                                  String prod) throws Exception
    {
        // Check target type
        if (!UTILS.BASIC.isStringID(tax))
            throw new Exception("Invalid tax, CLaws.java, 581");
        
         // Max default tax
         if (tax_amount<0 || tax_amount>25) 
            return false;
            
        // Tax exist ?
        if (!UTILS.BASIC.isTax(tax))
            return false;
                                   
        // Product ?
        if (!prod.equals(""))
           if (!UTILS.BASIC.isProd(prod))
              return false; 
        
        // Return
        return true;
    }
    
    public boolean isCit(String user, String cou) throws Exception
    {
        // Check user
        if (!UTILS.BASIC.isName(user))
           throw new Exception("Invalid user, CLaws.java, 604");

        // Check cou
        if (!UTILS.BASIC.isCountry(cou))
           throw new Exception("Invalid country, CLaws.java, 608");
        
        // Load citizen
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM adr "
                                          + "WHERE name='"+user+"' "
                                            + "AND cou='"+cou+"'");
        
        if (UTILS.DB.hasData(rs))
            return true;
        else
            return false;
    }
    
    
    public boolean checkPremiumLaw(String cou, String list) throws Exception
    {
        // Check cou
        if (!UTILS.BASIC.isCountry(cou))
           throw new Exception("Invalid country, CLaws.java, 608");
        
        // Remove spaces
        list=list.replace(" ", "");
        
        // Explode
        List<String> players = Arrays.asList(list.split(","));
        
        // Parse
        for (int a=0; a<=players.size()-1; a++)
        {
            // Is address ?
            if (!UTILS.BASIC.isName(players.get(a).trim()))
                return false;
            
            // Citizen ?
            if (!this.isCit(players.get(a), cou))
                return false;
        }
        
        // Return
        return true;
    }
    
    public boolean checkChgBonusLaw(String bonus, 
                                    String prod, 
                                    double amount) throws Exception
    {
       // Check bonus format
       if (!UTILS.BASIC.isStringID(bonus))
           throw new Exception ("Invalid bonus, CUtils.java, line 1959"); 

       // Check prod format
       if (!UTILS.BASIC.isStringID(prod))
           throw new Exception ("Invalid product, CUtils.java, line 1959"); 
        
       // Min default bonus
       if (amount<0) 
          return false;
            
        // Bonus exist ?
        if (!UTILS.BASIC.isBonus(bonus))
            return false;
                                   
        // Product ?
        if (bonus.equals("ID_BUY_BONUS"))
           if (!UTILS.BASIC.isProd(prod))
             return false;
        
        // Return
        return true;
    }
    
    public boolean checkDonationLaw(String cou, 
                                    String adr, 
                                    double amount) throws Exception
    {
        // Check cou
        if (!UTILS.BASIC.isCountry(cou))
           throw new Exception("Invalid country, CLaws.java, 608");
        
        // Check address
        if (!UTILS.BASIC.isAdr(adr))                  
            return false;
                                  
        // State budget
        double budget=UTILS.BASIC.getBudget(cou, "CRC", null);
                                  
        // Only 5% of budget can be donated
        if (budget/20<amount)
           return false;
        
        // Return 
        return true;
    }
    
    public boolean checkDistributeLaw(String cou, double amount) throws Exception
    {
        // Check cou
        if (!UTILS.BASIC.isCountry(cou))
           throw new Exception("Invalid country, CLaws.java, 608");
        
        // State budget
        double budget=UTILS.BASIC.getBudget(cou, "CRC", null);
                                  
        // Only 25% of budget can be distributed
        if (budget/4<amount)
           return false;
        
        // Return
        return true;
    }
    
    public boolean checkStartWarLaw(String cou, 
                                    String defender, 
                                    String target,
                                    long block) throws Exception
    {
        // Check countries
        if (!UTILS.BASIC.isCountry(defender))
            throw new Exception("Invalid country, CNewLawPayload.java, 307");
                                  
        // Check countries
        if (!UTILS.BASIC.isCountry(target))
            throw new Exception("Invalid country, CNewLawPayload.java, 307");
                                  
        // Attack itself ?
        if (defender.equals(cou))
            throw new Exception("Invalid country, CNewLawPayload.java, 307");
                                  
        // Load attacked country data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM countries "
                                          + "WHERE code='"+target+"'");
        
        // Next
        rs.next();
        
        // Occupied
        String occupied=rs.getString("occupied");
                                  
        // Free country ?
        if (defender.equals(target))
        {
           if (!occupied.equals(target))
             throw new Exception("Invalid country, CNewLawPayload.java, 307");
        }
        else
        {
            if (!occupied.equals(defender))
               throw new Exception("Invalid country, CNewLawPayload.java, 307");
        }
        
        // Already a pending war ?
        rs=UTILS.DB.executeQuery("SELECT * "
                                 + "FROM wars "
                                + "WHERE target='"+target+"' "
                                  + "AND status='ID_ACTIVE'");
                                  
        // Has data ?
        if (UTILS.DB.hasData(rs))
           throw new Exception("Defender already under attack, CNewLawPayload.java, 307");
        
        // Another war in the last 10 days ?
        rs=UTILS.DB.executeQuery("SELECT * "
                                 + "FROM wars "
                                + "WHERE attacker='"+cou+"' "
                                  + "AND target='"+target+"' "
                                  + "AND block>'"+block+"'");
                                  
        // State budget
        double budget=UTILS.BASIC.getBudget(cou, "CRC", null);
                                  
        // Funds ?
        if (budget<1)
           throw new Exception("Insuficient funds, CNewLawPayload.java, 307");
        
        // Return 
        return true;
    }
    
    public boolean ownsWeapon(String cou, long wID) throws Exception
    {
        // Check countries
        if (!UTILS.BASIC.isCountry(cou))
            throw new Exception("Invalid country, CNewLawPayload.java, 307");
        
        // Country address
        String cou_adr=UTILS.BASIC.getCouAdr(cou);
        
        // Load inventory
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM stocuri "
                                          + "WHERE adr='"+cou_adr+"' "
                                            + "AND stocID='"+wID+"' "
                                            + "AND qty>0 "
                                            + "AND war_status='ID_READY'");
        
        // Has data ?
        if (!UTILS.DB.hasData(rs))
            return false;
        
        // Next
        rs.next();
        
        // Ia a weapon ?
        if (!UTILS.BASIC.isStateWeapon(rs.getString("tip")))
            return false;
        
        // Return
        return true;
    }
    
    public long getWeaponsQty(String cou, 
                              String type,
                              String loc_type, 
                              String locID) throws Exception
    {
        // Check countries
        if (!UTILS.BASIC.isCountry(cou))
            throw new Exception("Invalid country, CNewLawPayload.java, 824");
        
        // Check type
        if (!UTILS.BASIC.isStringID(type))
            throw new Exception("Invalid country, CNewLawPayload.java, 828");
        
        // Check countries
        if (!UTILS.BASIC.isStringID(loc_type))
            throw new Exception("Invalid loc type, CNewLawPayload.java, 832");
        
        // Check loc ID
        if (!UTILS.BASIC.isCountry(locID))
            if (!UTILS.BASIC.isLong(locID))
                throw new Exception("Invalid loc ID, CNewLawPayload.java, 837");
        
        // Address
        String adr=UTILS.BASIC.getCouAdr(cou);
        
        // Load data
        ResultSet rs=UTILS.DB.executeQuery("SELECT SUM(qty) AS total "
                                           + "FROM stocuri "
                                          + "WHERE adr='"+adr+"' "
                                            + "AND tip='"+type+"' "
                                            + "AND war_loc_type='"+loc_type+"' "
                                            + "AND war_locID='"+locID+"' "
                                            + "AND war_status='ID_READY'");
        
        // Next
        rs.next();
        
        // Return 
        return rs.getLong("total");
    }
    
    public boolean canMoveWeapon(String cou,
                                 long weaponID, 
                                 String target_type, 
                                 String targetID) throws Exception
    {
        // Check countries
        if (!UTILS.BASIC.isCountry(cou))
            throw new Exception("Invalid country, CNewLawPayload.java, 307");
        
         // Check countries
        if (!UTILS.BASIC.isStringID(target_type))
            throw new Exception("Invalid loc type, CNewLawPayload.java, 832");
        
        // Check loc ID
        if (!UTILS.BASIC.isCountry(targetID))
            if (!UTILS.BASIC.isLong(targetID))
                throw new Exception("Invalid target ID, CNewLawPayload.java, 837");
        
        // Can move
        boolean allow=true;
        
        // Load weapon data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM stocuri "
                                          + "WHERE stocID='"+weaponID+"'");
        
        // Next
        rs.next();
        
        // Weapon
        String weapon=rs.getString("tip");
            
        // Can move to target ?
        switch (weapon)
        {
            // Tanks
            case "ID_TANK" : if (!target_type.equals("ID_LAND")) 
                                allow=false; 
                             break;
            
            // Tank rounds
            case "ID_TANK_ROUND" : if (!target_type.equals("ID_LAND")) 
                                       allow=false; 
            
                                   // Tanks no
                                   long tanks=this.getWeaponsQty(cou, 
                                                                 "ID_TANK", 
                                                                 target_type, 
                                                                 targetID);
                                   
                                   // Rounds
                                   long rounds=this.getWeaponsQty(cou, 
                                                                  "ID_TANK_ROUND", 
                                                                  target_type, 
                                                                  targetID);
                                   
                                   // Max 100 rounds / tank
                                   if (tanks*100<=rounds)
                                       allow=false;
            
                                   break;
            
            // Missile air soil
            case "ID_MISSILE_AIR_SOIL" : if (!target_type.equals("ID_LAND") &&
                                             !target_type.equals("ID_AIRCRAFT_CARRIER")) 
                                         allow=false; 
            
                                         // Tanks no
                                         long aircrafts=this.getWeaponsQty(cou, 
                                                                           "ID_JET_FIGHTER", 
                                                                           target_type, 
                                                                           targetID);
                                   
                                        // Missiles
                                        long missiles=this.getWeaponsQty(cou, 
                                                                         "ID_MISSILE_AIR_SOIL", 
                                                                         target_type, 
                                                                         targetID);
                                   
                                        // Max 25 missiles / fighter
                                        if (aircrafts*25<=missiles)
                                            allow=false;
                                         
                                        break;
                                             
            // Missile soil soil
            case "ID_MISSILE_SOIL_SOIL" :   if (!target_type.equals("ID_NAVY_DESTROYER")) 
                                                allow=false; 
                                            
                                            // Missiles
                                            long missiles_ss=this.getWeaponsQty(cou, 
                                                                                "ID_MISSILE_SOIL_SOIL", 
                                                                                target_type, 
                                                                                targetID);
                                   
                                            // Max 10 rounds / tank
                                            if (missiles_ss>=100)
                                                 allow=false;
                                          
                                          break;  
                                             
            // Missile balistic short
            case "ID_MISSILE_BALISTIC_SHORT" : if (!target_type.equals("ID_LAND")) allow=false; 
                                               break;
            
            // Missile balistic medium
            case "ID_MISSILE_BALISTIC_MEDIUM" : if (!target_type.equals("ID_LAND")) allow=false; 
                                                break;
            
            // Missile balistic long
            case "ID_MISSILE_BALISTIC_LONG" : if (!target_type.equals("ID_LAND")) allow=false; 
                                              break;
            
            // Missile balistic inter
            case "ID_MISSILE_BALISTIC_INTERCONTINENTAL" : if (!target_type.equals("ID_LAND")) allow=false; 
                                                          break;
            
            // Navy destroyer
            case "ID_NAVY_DESTROYER" : if (!target_type.equals("ID_SEA")) allow=false; 
                                       break;
            
            // Aircraft carrier
            case "ID_AIRCRAFT_CARRIER" : if (!target_type.equals("ID_SEA")) allow=false; 
                                         break;
            
            // Jet fighter
            case "ID_JET_FIGHTER" : if (!target_type.equals("ID_LAND") && 
                                        !target_type.equals("ID_AIRCRAFT_CARRIER")) 
                                     allow=false;
                                     
                                    // Move to sea ?
                                    if (target_type.equals("ID_AIRCRAFT_CARRIER"))
                                    {
                                        // Missiles
                                        long fighters=this.getWeaponsQty(cou, 
                                                                        "ID_JET_FIGHTER", 
                                                                        target_type, 
                                                                        targetID);
                                   
                                        // Max 100 jets / carrier 
                                        if (fighters>=100)
                                            allow=false;
                                    }
                                    
                                    break;
        }
        
        return allow;
    }
    
    public boolean checkTarget(String cou, 
                               String target_type, 
                               String targetID) throws Exception
    {
        // Check countries
        if (!UTILS.BASIC.isCountry(cou))
            throw new Exception("Invalid country, CNewLawPayload.java, 307");
        
         // Check countries
        if (!UTILS.BASIC.isStringID(target_type))
            throw new Exception("Invalid loc type, CNewLawPayload.java, 832");
        
        // Check loc ID
        if (!UTILS.BASIC.isCountry(targetID))
            if (!UTILS.BASIC.isLong(targetID))
                throw new Exception("Invalid target ID, CNewLawPayload.java, 837");
        
        // Check target type
        if (!target_type.equals("ID_LAND") && 
            !target_type.equals("ID_SEA") && 
            !target_type.equals("ID_NAVY_DESTROYER") && 
            !target_type.equals("ID_AIRCRAFT_CARRIER"))
        return false;

        // Check land
        if (target_type.equals("ID_LAND"))
        {
             ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM countries "
                                              + "WHERE code='"+targetID+"' "
                                                + "AND occupied='"+cou+"'");
            
            // Has data ?
            if (!UTILS.DB.hasData(rs))
                return false;
        }
        
        // Check sea
        if (target_type.equals("ID_SEA"))
           if (!UTILS.BASIC.isSea(Long.parseLong(targetID)))
               return false;
        
        // Airraft carrier or destroyer
        if (target_type.equals("ID_NAVY_DESTROYER") || 
            target_type.equals("ID_AIRCRAFT_CARRIER"))
        {
            // Country adress;
            String cou_adr=UTILS.BASIC.getCouAdr(cou);
            
            // Load data
            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM stocuri "
                                              + "WHERE adr='"+cou_adr+"' "
                                                + "AND tip='"+target_type+"' "
                                                + "AND stocID='"+targetID+"'");
            
            // Has data ?
            if (!UTILS.DB.hasData(rs))
                return false;
        }
        
        
        // Return
        return true;
    }
    
    public String getTargetType(String target_type, long targetID) throws Exception
    {
        // Target weapon ?
        if (target_type.equals("ID_WEAPON"))
        {
            // Load stoc
            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM stocuri "
                                              + "WHERE stocID='"+targetID+"'");
            
            // Has data?
            if (UTILS.DB.hasData(rs))
            {
                // Next
                rs.next();
                
                // Navy destroyer or aircraft carrier ?
                if (!rs.getString("tip").equals("ID_NAVY_DESTROYER") &&
                    !rs.getString("tip").equals("ID_AIRCRAFT_CARRIER"))
                throw new Exception ("Invalid target type");
                
                // Change target type
                target_type=rs.getString("tip");
                
                // Return
                return target_type;
            }
            throw new Exception ("Invalid target type");
        }
        else return target_type;
    }
    
    public double checkMoveWeaponsLaw(String cou,
                                       String list, 
                                       String target_type, 
                                       String targetID) throws Exception
    {
        // Check countries
        if (!UTILS.BASIC.isCountry(cou))
            throw new Exception("Invalid country, CNewLawPayload.java, 307");
        
         // Check countries
        if (!UTILS.BASIC.isStringID(target_type))
            throw new Exception("Invalid loc type, CNewLawPayload.java, 832");
        
        // Check loc ID
        if (!UTILS.BASIC.isCountry(targetID))
            if (!UTILS.BASIC.isLong(targetID))
                throw new Exception("Invalid target ID, CNewLawPayload.java, 837");
        
        // Cost
        double cost=0;
        
        // Target type
        if (target_type.equals("ID_WEAPON"))
           target_type=this.getTargetType(target_type, Long.parseLong(targetID));
        
        // Check target
        if (!this.checkTarget(cou, target_type, targetID))
            return 0;
        
        // Explode
        List<String> weapons = Arrays.asList(list.split(","));
        
        // Parse
        for (int a=0; a<=weapons.size()-1; a++)
        {
            // Weapon ID
            long wID=Long.parseLong(weapons.get(a).trim());
            
            // Owns weapon ?
            if (!this.ownsWeapon(cou, wID))
                throw new Exception("Invalid weapon ID, CNewLawPayload.java, 102"); 
            
            // Can move it to target
            if (!this.canMoveWeapon(cou, wID, target_type, targetID))
                throw new Exception("Weapon can't be move to position, CNewLawPayload.java, 102"); 
            
            // Weapon position
            Point w_pos=UTILS.BASIC.getWeaponPos(wID);
            
            // Target pos
            Point target_pos=UTILS.BASIC.getLocPos(target_type, targetID);
            
            // Distance
            long dist=UTILS.BASIC.getPointDist(w_pos, target_pos);
            
            // Cost
            cost=cost+dist*0.0001;
        }
        
        // Check balance
        double budget=UTILS.BASIC.getBudget(cou, "CRC", null);
        
        // Funds ?
        if (cost>budget)
           throw new Exception("Insufucuent funds, CNewLawPayload.java, 102"); 
        
        // Return
        return cost;
    }
    
    public boolean canUse(String cou, 
                          String ammo, 
                          String loc_type, 
                          String locID) throws Exception
    {
        if (loc_type=="ID_LAND")
        {
            // Owner
            String adr=UTILS.BASIC.getCouAdr(cou);
            
            // Ammo type
            if (ammo.equals("ID_TANK_ROUND"))
            {
                // Tanks in area ? 
                ResultSet rs_area=UTILS.DB.executeQuery("SELECT * "
                                                        + "FROM stocuri "
                                                       + "WHERE tip='ID_TANK' "
                                                         + "AND adr='"+adr+"' "
                                                         + "AND war_loc_type='"+loc_type+"' "
                                                         + "AND war_locID='"+locID+"'");
            
                // Has data ?
                if (!UTILS.DB.hasData(rs_area))
                   return false;
            }
        
            // Ammo type
            if (ammo.equals("ID_MISSILE_AIR_SOIL") || 
                ammo.equals("ID_MISSILE_SOIL_SOIL"))
            return false;
            
        }
            
        return true;
    }
    
    public boolean checkAttackLaw(String cou,
                                  String par_1, 
                                  String par_2, 
                                  String par_3) throws Exception
    {
        // Check countries
        if (!UTILS.BASIC.isCountry(cou))
            throw new Exception("Invalid country, CNewLawPayload.java, 307");
       
        // Parameters
        String list=par_1;
        long warID=Long.parseLong(par_2);
        String side=par_3;
        
        // List valid
        if (list.length()>10000)
            throw new Exception("List is too long, CNewLawPayload.java, 102");
            
        // Check target
        if (!side.equals("ID_AT") && 
            !side.equals("ID_DE"))
        throw new Exception("Invalid target, CNewLawPayload.java, 102");
        
        // Load war data
        ResultSet rs_war=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM wars "
                                              + "WHERE warID='"+warID+"'");
            
        // Has data ?
        if (!UTILS.DB.hasData(rs_war))
            throw new Exception("Invalid warID, CNewLawPayload.java, 102");
            
        // Next
        rs_war.next();
        
        // Check target
        if (!rs_war.getString("attacker").equals(cou) && 
            !rs_war.getString("defender").equals(cou))
        throw new Exception("Invalid target, CNewLawPayload.java, 102");
        
        // Explode
        List<String> weapons = Arrays.asList(list.split(","));
        
        // Parse
        for (int a=0; a<=weapons.size()-1; a++)
        {
            // Weapon ID
            long wID=Long.parseLong(weapons.get(a).trim());
            
            // Owns weapon ?
            if (!this.ownsWeapon(cou, wID))
                return false;
            
            // Load weapon data
            ResultSet rs_ammo=UTILS.DB.executeQuery("SELECT * "
                                                    + "FROM stocuri "
                                                   + "WHERE stocID='"+wID+"'");
            
            // Next
            rs_ammo.next();
            
            // Ammo name
            String ammo=rs_ammo.getString("tip");
            
            // Is ammo ?
            if (!UTILS.BASIC.isAmmo(ammo))
                throw new Exception("Item is not ammunition, CNewLawPayload.java, 102");
            
            // Can be used ?
            if (!this.canUse(cou, 
                             ammo, 
                             rs_ammo.getString("war_loc_type"), 
                             rs_ammo.getString("war_locID")))
            throw new Exception("Weapon can't be used, CNewLawPayload.java, 102");     
            
            // Get ammo position
            Point ammo_pos=UTILS.BASIC.getLocPos(rs_ammo.getString("war_loc_type"), 
                                                 rs_ammo.getString("war_locID"));
            
            // Target pos
            Point target_pos=UTILS.BASIC.getCouPos(rs_war.getString("target"));
            
            // Weapon range
            long range=UTILS.BASIC.getAmmoRange(ammo);
            
            // Distance between ammo and target
            long dist=UTILS.BASIC.getPointDist(ammo_pos, target_pos);
            
            // In range ?
            if (dist>range)
               throw new Exception("Out of range, CNewLawPayload.java, 102"); 
        }
        
        // Return
        return true;
    }
    
    public boolean checkBuyLaw(String cou, 
                               String par_1, 
                               String par_2) throws Exception
    {
        // Check countries
        if (!UTILS.BASIC.isCountry(cou))
            throw new Exception("Invalid country, CNewLawPayload.java, 1240");
        
        // Convert parameters
        long offerID=Long.parseLong(par_1);
        long qty=Long.parseLong(par_2);
        
        // Load offer ID
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM assets_mkts_pos AS amp "
                                           + "JOIN assets_mkts AS am ON am.mktID=amp.mktID "
                                          + "WHERE amp.orderID='"+offerID+"' "
                                            + "AND amp.qty>=1 "
                                            + "AND amp.tip='ID_SELL'");
        
        // Has data ?
        if (!UTILS.DB.hasData(rs))
            throw new Exception("Invalid market ID, CNewLawPayload.java, 102");
        
        // Next 
        rs.next();
        
        // Asset is congress weapon ?
        if (!UTILS.BASIC.isStateWeapon(rs.getString("asset")))
            throw new Exception("Congress can't buy this product, CNewLawPayload.java, 102");
        
        // Qty
        if (qty>rs.getLong("qty"))
            throw new Exception("Invalid qty, CNewLawPayload.java, 102");
        
        // Price
        double price=qty*rs.getDouble("price");
        
        // Budget
        double budget=UTILS.BASIC.getBudget(cou, "CRC", null);
        
        // Funds ?
        if (price>budget)
           throw new Exception("Insuficient funds, CNewLawPayload.java, 102"); 
        
        // Return
        return true;
    }
    
}
