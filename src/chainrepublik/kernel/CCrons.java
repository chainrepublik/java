// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.kernel;
import java.sql.ResultSet;
import java.util.Timer;
import java.util.TimerTask;


public class CCrons 
{
   // Timer
   Timer timer;
    
   // Task
   RemindTask task;
  
   // Last agent ID
   long last_agentID;
   
   public CCrons()
   {
       // Timer
       timer = new Timer();
       task=new RemindTask();
       timer.schedule(task, 0, 1000);
   }
   
   public void checkWeaponsMove(long block) throws Exception
   {
       UTILS.DB.executeUpdate("UPDATE stocuri "
                               + "SET war_status='ID_READY', "
                                   + "war_arrive=0 "
                             + "WHERE war_status='ID_TRANSIT' "
                               + "AND war_arrive<"+block);
   }
   
   public void endWar(long warID) throws Exception
   {
       // Load war data
       ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM wars "
                                         + "WHERE warID='"+warID+"'");
       
       // Next
       rs.next();
       
       // Attacker
       String attacker=rs.getString("attacker");
       
       // Defender
       String defender=rs.getString("defender");
       
       // Target
       String target=rs.getString("target");
       
       // Attacker adr
       String attacker_adr=UTILS.BASIC.getCouAdr(attacker);
       
       // Defender adr
       String defender_adr=UTILS.BASIC.getCouAdr(defender);
       
       // Attacker winner ?
       if (rs.getLong("attacker_points")>rs.getLong("defender_points"))
       {
           // Occupy target
           UTILS.DB.executeUpdate("UPDATE countries "
                                   + "SET occupied='"+attacker+"' "
                                 + "WHERE code='"+target+"'");
           
           // Take all foreign military equipment
           UTILS.DB.executeUpdate("UPDATE stocuri "
                                   + "SET adr='"+attacker_adr+"' "
                                 + "WHERE war_loc_type='ID_LAND' "
                                   + "AND war_locID='"+target+"' "
                                   + "AND war_status='ID_READY'");
       }
       else
       {
           // Occupy target
           UTILS.DB.executeUpdate("UPDATE countries "
                                   + "SET occupied='"+defender+"' "
                                 + "WHERE code='"+target+"'");
           
           // Take all foreign military equipment
           UTILS.DB.executeUpdate("UPDATE stocuri "
                                   + "SET adr='"+defender_adr+"' "
                                 + "WHERE war_loc_type='ID_LAND' "
                                   + "AND war_locID='"+target+"' "
                                   + "AND status='ID_READY'");
       }
       
       // End war
       UTILS.DB.executeUpdate("UPDATE wars "
                               + "SET status='ID_ENDED' "
                             + "WHERE warID='"+warID+"'");
   }
   
   public void implementLaw(long lawID, long block) throws Exception
   {
       // Set as implemented
       UTILS.DB.executeUpdate("UPDATE laws "
                               + "SET status='ID_APROVED' "
                             + "WHERE lawID='"+lawID+"'");
       
       // Load law data
       ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM laws "
                                         + "WHERE lawID='"+lawID+"'");
       
       // Next
       rs.next();
       
       // Country
       String cou=UTILS.BASIC.getAdrData(rs.getString("adr"), "cou");
       
       // Implement
       switch (rs.getString("type"))
       {
           // Change bonus
           case "ID_CHG_BONUS" :  // Implement
                                  UTILS.LAWS.implementChgBonus(cou, 
                                                               rs.getString("par_1"), 
                                                               rs.getString("par_2"), 
                                                               rs.getString("par_3"));
                                  
                                  break;
           
           // Change tax
           case "ID_CHG_TAX" :    UTILS.LAWS.implementChgTax(cou,
                                                             rs.getString("par_1"), 
                                                             rs.getString("par_2"), 
                                                             rs.getString("par_3"));
                                  
                                  break;
                        
           // Add premium citizens
           case "ID_ADD_PREMIUM" :   UTILS.LAWS.implementAddPremium(cou, 
                                                                    block, 
                                                                    rs.getString("par_1"));
        
                                    break;
                                
           // Remove premium citizens
           case "ID_REMOVE_PREMIUM" : UTILS.LAWS.implementRemovePremium(cou, rs.getString("par_1"));
        
                                    break;
                                     
                                   
            // Make article official
            case "ID_OFICIAL_ART" : // Article ID
                                    long artID=Long.parseLong(UTILS.BASIC.base64_decode(rs.getString("par_1")));
                                    
                                    // Implement
                                    UTILS.LAWS.implementOfArt(artID, block);
                                        
                                    break;
                                    
           // Donation law 
           case "ID_DONATION" : UTILS.LAWS.implementDonation(lawID, 
                                                             cou, 
                                                             UTILS.BASIC.base64_decode(rs.getString("par_1")), 
                                                             rs.getString("par_2"), 
                                                             block);
                                break;
                                
            // Funds distribution
            case "ID_DISTRIBUTE" :  UTILS.LAWS.implementDistribute(lawID, 
                                                                   cou, 
                                                                   rs.getString("par_1"),
                                                                   block);
                                    break;
                                    
            // Start war law
            case "ID_START_WAR" :  UTILS.LAWS.implementStartWar(lawID, 
                                                                rs.getString("par_1"),
                                                                rs.getString("par_2"),
                                                                block);
                                   break;
                                   
            // Move weapons law
            case "ID_MOVE_WEAPONS" :  UTILS.LAWS.implementMoveWeapons(lawID, 
                                                                      rs.getString("par_1"),
                                                                      rs.getString("par_2"),
                                                                      rs.getString("par_3"),
                                                                      block);
                                   break;
                                   
            // Buy weapons
            case "ID_BUY_WEAPONS" :  UTILS.LAWS.implementBuyWeapons(lawID, 
                                                                    rs.getString("par_1"),
                                                                    rs.getString("par_2"),
                                                                    block);
                                   break;
                                   
            // Attack law
            case "ID_ATTACK" :  UTILS.LAWS.implementAttack(lawID, 
                                                           rs.getString("par_1"),
                                                           rs.getString("par_2"),
                                                           rs.getString("par_3"),
                                                           block);
                                   break;
       }
   }
   
   public void implementOrgProp(long propID, long block) throws Exception
   {
       // Set as implemented
       UTILS.DB.executeUpdate("UPDATE orgs_props "
                               + "SET status='ID_APROVED' "
                             + "WHERE propID='"+propID+"'");
       
       // Load law data
       ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM orgs_props "
                                         + "WHERE propID='"+propID+"'");
       
       // Next
       rs.next();
       
       // Implement
       switch (rs.getString("prop_type"))
       {
           // Donate
           case "ID_DONATE" : // Donation address
                                String adr=UTILS.BASIC.base64_decode(rs.getString("par_1"));
                                
                                // Donation amount
                                double amount=Double.parseDouble(UTILS.BASIC.base64_decode(rs.getString("par_2")));
                                
                                // Currency
                                String cur="CRC";
                                
                                if (!rs.getString("par_3").equals(""))
                                   cur=UTILS.BASIC.base64_decode(rs.getString("par_3"));
                                      
                                // Valid currency ?
                                if (UTILS.BASIC.isCur(cur))
                                {
                                    // Org ID
                                    long orgID=rs.getLong("orgID");
                                    
                                    // Load organization data
                                    ResultSet rs_org=UTILS.DB.executeQuery("SELECT * "
                                                                           + "FROM orgs "
                                                                          + "WHERE orgID='"+orgID+"'");
                                    
                                    // Next
                                    rs_org.next();
                                    
                                    // Organization address
                                    String org_adr=rs_org.getString("adr");
                                           
                                    // State budget
                                    double budget=UTILS.ACC.getBalance(org_adr, cur);
                                  
                                    // Only 5% of budget can be donated
                                    if (budget>amount)
                                    {
                                        // Payment
                                        UTILS.ACC.newTransfer(org_adr, 
                                                              adr,
                                                              amount, 
                                                              cur, 
                                                              "Organziation donation ", 
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
                                    else UTILS.LAWS.propErr(propID, "Innsuficient funds", block);
                                } 
                                else UTILS.LAWS.propErr(propID, "Invalid currency", block);
                                
                                break;
           
           // Change description
           case "ID_CHG_DESC" : // Change
                                UTILS.DB.executeUpdate("UPDATE orgs "
                                                        + "SET description='"+rs.getString("par_1")+"' "
                                                      + "WHERE orgID='"+rs.getLong("orgID")+"'");
               
                                break;
           
           // Set oficial declaration
           case "ID_SET_ART_OFFICIAL" : // Update
                                        UTILS.DB.executeUpdate("UPDATE tweets "
                                                                + "SET of_dec='"+block+"' "
                                                              + "WHERE tweetID='"+UTILS.BASIC.base64_decode(rs.getString("par_1"))+"'");
                                        
                                        break;
       }
   }
   
   public void checkLaws(long block) throws Exception
   {
       // Load voting laws
       ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM laws "
                                         + "WHERE block<"+(block-1440)+" "
                                           + "AND status='ID_VOTING'");
       
       // Loop
       while (rs.next())
       {
          if (rs.getLong("voted_yes")>rs.getLong("voted_no"))
              this.implementLaw(rs.getLong("lawID"), block);
          else
              UTILS.DB.executeUpdate("UPDATE laws "
                                      + "SET status='ID_REJECTED' "
                                    + "WHERE lawID='"+rs.getLong("lawID")+"'");
       }
       
       // Load all voting laws
       rs=UTILS.DB.executeQuery("SELECT * "
                                + "FROM laws "
                               + "WHERE status='ID_VOTING'");
       
       while (rs.next())
       {
           // Total pol endorsement
           long total=this.getCongressVotingPower(rs.getString("country"));
           
           // Voted by over 50% 
           if (rs.getLong("voted_yes")>total/2)
               this.implementLaw(rs.getLong("lawID"), block);
       }
   }
   
   public long getCongressVotingPower(String cou) throws Exception
   {
       // Total
       long total=0;
       
       // Load data
       ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM adr "
                                         + "WHERE cou='"+cou+"' "
                                      + "ORDER BY pol_endorsed DESC "
                                         + "LIMIT 0,25");
       
       while (rs.next())
           total=total+Math.round(rs.getDouble("pol_endorsed"));
       
       return total;
    }
   
   public void checkWars(long block) throws Exception
   {
       // Load wars data
       ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM wars "
                                         + "WHERE status='ID_ACTIVE' "
                                           + "AND block<"+(block-1440));
       
       // Parse
       while (rs.next())
           this.endWar(rs.getLong("warID"));
   }
   
   public void checkProps(long block) throws Exception
   {
       // Load voting laws
       ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM orgs_props "
                                         + "WHERE block<"+(block-1440)+" "
                                           + "AND status='ID_VOTING'");
       
       // Loop
       while (rs.next())
       {
           if (rs.getLong("yes")>rs.getLong("no"))
              this.implementOrgProp(rs.getLong("propID"), block);
           else
                UTILS.DB.executeUpdate("UPDATE orgs_props "
                                      + "SET status='ID_REJECTED' "
                                    + "WHERE propID='"+rs.getLong("propID")+"'");
       }
   }
   
   public void updatePolInf(String adr) throws Exception
   {
       // Load data
       ResultSet rs=UTILS.DB.executeQuery("SEELECT SUM(adr.pol_inf) AS total "
                                           + "FROM endorsers AS end "
                                           + "JOIN adr ON adr.adr=end.endorser "
                                           + "WHERE adr='"+adr+"'");
       
       // Update
       UTILS.DB.executeUpdate("UPDATE adr "
                               + "SET pol_inf='"+rs.getDouble("total")+"' "
                             + "WHERE adr='"+adr+"'");
   }
   
     
     public void setStatus()throws Exception
     {
           if (UTILS.BASIC.tstamp()%10!=0)
               return;
               
           // Low memory ?
           if (UTILS.runtime.freeMemory()<2000) 
           {
               System.out.println("Exit virtual machine (run out of memory)");
               System.exit(0);
           }
           
           // Update
           UTILS.DB.executeUpdate("UPDATE web_sys_data "
                                   + "SET last_ping='"+UTILS.BASIC.tstamp()+"', "
                                       + "max_memory='"+UTILS.runtime.maxMemory()+"', "
                                       + "version='0.9.0', "
                                       + "free_memory='"+UTILS.runtime.freeMemory()+"', "
                                       + "total_memory='"+UTILS.runtime.totalMemory()+"', "
                                       + "procs='"+UTILS.runtime.availableProcessors()+"', "
                                       + "threads_no='"+Thread.getAllStackTraces().size()+"'");
           
           // Insert into log
           UTILS.DB.executeUpdate("INSERT INTO status_log "
                                        + "SET total_mem='"+UTILS.runtime.totalMemory()+"', "
                                            + "free_mem='"+UTILS.runtime.freeMemory()+"', "
                                            + "threads='"+Thread.getAllStackTraces().size()+"', "
                                            + "tstamp='"+UTILS.BASIC.tstamp()+"'");
           
           // Delete old records
           UTILS.DB.executeUpdate("DELETE FROM status_log "
                                      + "WHERE tstamp<"+(UTILS.BASIC.tstamp()-86400));
     }
     
     public void checkWorkplaces(long block) throws Exception
     {
        // Free workplaces
        UTILS.DB.executeUpdate("UPDATE workplaces "
                                + "SET status='ID_FREE' "
                              + "WHERE status='ID_OCCUPIED' "
                                + "AND work_ends<="+block);
        
        // Free addressess
        UTILS.DB.executeUpdate("UPDATE adr "
                                + "SET work=0 "
                              + "WHERE work>="+block);
     }
     
     public void checkEnergy(long block) throws Exception
     {
         // Increase energy
         UTILS.DB.executeUpdate("UPDATE adr "
                                 + "SET energy=energy+energy_block "
                               + "WHERE energy_block>0");
         
         // Decrease energy
         UTILS.DB.executeUpdate("UPDATE adr "
                                 + "SET energy=energy-energy*0.0008 "
                               + "WHERE energy>=1");
     }
     
     public void checkWine(long block) throws Exception
     {
         // Increase wine energy
         UTILS.DB.executeUpdate("UPDATE stocuri "
                                 + "SET energy=energy+0.0005 "
                               + "WHERE tip='ID_WINE' "
                                 + "AND adr<>'default' "
                                 + "AND qty=1 "
                                 + "AND energy<99.9995");
     }
     
     public void checkTravelers(long block) throws Exception
     {
         // Set location
         UTILS.DB.executeUpdate("UPDATE adr "
                                 + "SET travel=0, "
                                 + "loc=travel_cou, "                    
                                 + "travel_cou='' "
                               + "WHERE travel<="+block+" "
                                 + "AND travel>0");
     }
     
     public void checkPolInf(long block) throws Exception
     {
         if (block%1440==0)
         {
             // Decrease political influence
             UTILS.DB.executeUpdate("UPDATE adr "
                                 + "SET pol_inf=pol_inf-pol_inf*0.01"
                               + "WHERE pol_inf>=1");
             
             // Set pi to zero
             UTILS.DB.executeUpdate("UPDATE adr "
                                     + "SET pol_inf=0 "
                                   + "WHERE pol_inf<1");
             
             // Exclude from parties memebrs having a pi lower than 25
             UTILS.DB.executeUpdate("UPDATE adr "
                                     + "SET pol_party=0 "
                                   + "WHERE pol_inf<25");
         }
     }
     
     public void refreshPolEnd(String adr) throws Exception
     {
        // Load political endorsed
        ResultSet rs=UTILS.DB.executeQuery("SELECT SUM(power) AS total "
                                             + "FROM endorsers "
                                            + "WHERE endorsed='"+adr+"' "
                                              + "AND type='ID_UP'");
                
        // Next
        rs.next();
                
        // Pol endorsement
        double pol_end_poz=UTILS.BASIC.round(rs.getDouble("total"), 2);
        
        // Load political endorsed
        rs=UTILS.DB.executeQuery("SELECT SUM(power) AS total "
                                 + "FROM endorsers "
                                + "WHERE endorsed='"+adr+"' "
                                  + "AND type='ID_DOWN'");
                
        // Next
        rs.next();
                
        // Pol endorsement
        double pol_end_neg=UTILS.BASIC.round(rs.getDouble("total"), 2);
                
        // Update
        UTILS.DB.executeUpdate("UPDATE adr "
                                + "SET pol_endorsed='"+UTILS.BASIC.round(pol_end_poz-pol_end_neg, 2)+"' "
                              + "WHERE adr='"+adr+"'");
     }
     
     public void refreshPolEndPower(String adr) throws Exception
     {
        // Political influence
        double pol_inf=Double.parseDouble(UTILS.BASIC.getAdrData(adr, "pol_inf"));
                
        // Number of endorsed users
        ResultSet rs=UTILS.DB.executeQuery("SELECT COUNT(*) AS total "
                                           + "FROM endorsers "
                                          + "WHERE endorser='"+adr+"'");
                
        // Total
        rs.next();
                
        // Total
        long total=rs.getLong("total");
                
        // Vote power
        double power=UTILS.BASIC.round(pol_inf/total, 2);
                
        // Update power
        UTILS.DB.executeUpdate("UPDATE endorsers "
                                + "SET power='"+power+"' "
                              + "WHERE endorser='"+adr+"'");
     }
     
     public void checkPolEnd(long block) throws Exception
     {
         if (block%100==0)
         {
             // Reset political endorsement
             UTILS.DB.executeUpdate("UPDATE adr "
                                     + "SET pol_endorsed=0 "
                                   + "WHERE pol_endorsed>0");
             
             
             // Load unique endorsers
             ResultSet rs=UTILS.DB.executeQuery("SELECT DISTINCT(endorser) "
                                                + "FROM endorsers");
            
             // Update endorsement power
             while (rs.next())
              this.refreshPolEndPower(rs.getString("endorser"));
            
             // Load unique endorsed addressess
             rs=UTILS.DB.executeQuery("SELECT DISTINCT(endorsed) "
                                     + "FROM endorsers");
            
             // Update political endorsement
             while (rs.next())
               this.refreshPolEnd(rs.getString("endorsed"));
         }
     }
     
     public void checkWarPoints(long block) throws Exception
     {
         if (block%1440==0)
         {
            UTILS.DB.executeUpdate("UPDATE adr "
                                    + "SET war_points=war_points-war_points*0.01"
                                  + "WHERE war_points>=1");
         
            UTILS.DB.executeUpdate("UPDATE adr "
                                    + "SET war_points=0 "
                                  + "WHERE war_points<1");
         }
     }
     
     public void checkForFreeze() throws Exception
     {
         ResultSet rs=UTILS.DB.executeQuery("SELECT * FROM web_sys_data");
         rs.next();
         long online=rs.getLong("uptime");
         
         rs=UTILS.DB.executeQuery("SELECT * FROM blocks WHERE block='"+UTILS.NET_STAT.last_block+"'");
         rs.next();
         long last_block=rs.getLong("tstamp");
         
         if (UTILS.SETTINGS.autoshutdown && 
             online<UTILS.BASIC.tstamp()-1200 && 
             last_block<UTILS.BASIC.tstamp()-1200)
         {
             System.out.println("System frozen. Exiting.");
             System.exit(0);
         }
     }
     
     public void runBlockCrons(long block) throws Exception
     {
         // energy
         this.checkEnergy(block);
         
         // Check travelers
         this.checkTravelers(block);
         
         // Check wine
         this.checkWine(block);
         
         // check workplaces
         this.checkWorkplaces(block);
         
         // Political influence
         this.checkPolInf(block);
         
         // War points
         this.checkWarPoints(block);
         
         // Political endorsement
         this.checkPolEnd(block);
         
         // Laws
         this.checkLaws(block);
         
         // Orgs props
         this.checkProps(block);
         
         // Check wars
         this.checkWars(block);
         
         // Weapons move
         this.checkWeaponsMove(block);
     }
     
     class RemindTask extends TimerTask 
     {  
       @Override
       public void run()
       {  
           try
           {
              // Load web ops
               UTILS.WEB_OPS.loadWebOps();
               
               // Sync
               if (UTILS.SYNC!=null) 
                   UTILS.SYNC.tick();
               
               // Check for freeze
               if (UTILS.BASIC.tstamp()%5==0)
                   checkForFreeze();
               
               // Status
               setStatus();
               
               // Setup
               UTILS.STATUS.load(false);
               
               // Stress test
               UTILS.STRESS.run();
              
           }
           catch (Exception ex)
           {
               System.out.println(ex.getMessage());
           }
       }
     }
}
