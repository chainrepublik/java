// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.kernel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;
import chainrepublik.network.CPeer;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.util.Arrays;
import java.util.List;

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
       
       // Implement
       switch (rs.getString("type"))
       {
           // Change bonus
           case "ID_CHG_BONUS" :  // Bonus
                                  String bonus=UTILS.BASIC.base64_decode(rs.getString("par_1"));
                                  
                                  // Amount
                                  double bonus_amount=Double.parseDouble(UTILS.BASIC.base64_decode(rs.getString("par_3")));
                                  
                                  // Prod
                                  String bonus_prod=UTILS.BASIC.base64_decode(rs.getString("par_2"));
                                  
                                  // Update
                                  UTILS.DB.executeUpdate("UPDATE bonuses "
                                                          + "SET amount='"+bonus_amount+"' "
                                                        + "WHERE bonus='"+bonus+"' "
                                                          + "AND prod='"+bonus_prod+"' "
                                                          + "AND cou='"+rs.getString("country")+"'");
                                  
                                  break;
           
           case "ID_CHG_TAX" :    // Tax
                                  String tax=UTILS.BASIC.base64_decode(rs.getString("par_1"));
                                  
                                  // Amount
                                  double tax_amount=Double.parseDouble(UTILS.BASIC.base64_decode(rs.getString("par_2")));
                                  
                                  // Prod
                                  String tax_prod=UTILS.BASIC.base64_decode(rs.getString("par_3"));
                                  
                                  // Update
                                  if (tax.equals("ID_SALE_TAX"))
                                  UTILS.DB.executeUpdate("UPDATE taxes "
                                                          + "SET value='"+tax_amount+"' "
                                                        + "WHERE tax='"+tax+"' "
                                                          + "AND prod='"+tax_prod+"' "
                                                          + "AND cou='"+rs.getString("country")+"'");
                                  
                                  else
                                  UTILS.DB.executeUpdate("UPDATE taxes "
                                                          + "SET value='"+tax_amount+"' "
                                                        + "WHERE tax='"+tax+"' "
                                                          + "AND cou='"+rs.getString("country")+"'");
                                  
                                  break;
                                  
           case "ID_ADD_PREMIUM" :   // List
                                     String list=UTILS.BASIC.base64_decode(rs.getString("par_1"));
                                     
                                     // Explode
                                     List<String> players = Arrays.asList(list.split(","));
        
                                     // Parse
                                     for (int a=0; a<=players.size()-1; a++)
                                         UTILS.DB.executeUpdate("UPDATE adr "
                                                                 + "SET premium='"+block+"' "
                                                               + "WHERE name='"+players.get(a)+"'");
        
                                    break;
                                     
           case "ID_REMOVE_PREMIUM" : // List
                                     list=UTILS.BASIC.base64_decode(rs.getString("par_1"));
                                     
                                     // Explode
                                     players = Arrays.asList(list.split(","));
        
                                     // Parse
                                     for (int a=0; a<=players.size()-1; a++)
                                         UTILS.DB.executeUpdate("UPDATE adr "
                                                                 + "SET premium=0 "
                                                               + "WHERE name='"+players.get(a)+"'");
        
                                    break;
                                     
                                     
            case "ID_OFICIAL_ART" : // Article ID
                                    long artID=rs.getLong("tweetID");
                                        
                                    // Update
                                    UTILS.DB.executeUpdate("UPDATE tweets "
                                                                + "SET of_dec='"+block+"' "
                                                              + "WHERE tweetID='"+artID+"'");
                                        
                                    break;
                                    
                                     
           case "ID_DONATION" : // Donation address
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
                                    // Country
                                    String cou=UTILS.BASIC.getAdrData(rs.getString("adr"), "cou");
                                           
                                    // Country address
                                    String cou_adr=UTILS.BASIC.getCouAdr(cou);
                                  
                                    // State budget
                                    double budget=UTILS.BASIC.getBudget(cou, cur);
                                  
                                    // Only 5% of budget can be donated
                                    if (budget/20>amount)
                                    {
                                        // Payment
                                        UTILS.ACC.newTransfer(cou_adr, 
                                                              adr,
                                                              amount, 
                                                              cur, 
                                                              "State budget donation ", 
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
                                    else this.lawErr(lawID, "Innsuficient funds", block);
                                } 
                                else this.lawErr(lawID, "Invalid currency", block);
                                break;
                                
            case "ID_DISTRIBUTE" :  // Distribution amount
                                    amount=Double.parseDouble(UTILS.BASIC.base64_decode(rs.getString("par_2")));
                                
                                    // Currency
                                    cur="CRC";
                                
                                    if (!rs.getString("par_3").equals(""))
                                       cur=UTILS.BASIC.base64_decode(rs.getString("par_3"));
                                      
                                    // Valid currency ?
                                    if (UTILS.BASIC.isCur(cur))
                                    {
                                        // Country
                                        String cou=UTILS.BASIC.getAdrData(rs.getString("adr"), "cou");
                                           
                                        // Country address
                                        String cou_adr=UTILS.BASIC.getCouAdr(cou);
                                  
                                        // State budget
                                        double budget=UTILS.BASIC.getBudget(cou, cur);
                                  
                                        // Only 5% of budget can be donated
                                        if (budget/20>amount)
                                        {
                                           // Number of premium citizens
                                           rs=UTILS.DB.executeQuery("SELECT COUNT(*) AS total "
                                                                    + "FROM adr "
                                                                   + "WHERE cou='' "
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
                                                                        + "WHERE cou='' "
                                                                          + "AND premium>0");
                                                
                                                while (rs.next())
                                                UTILS.ACC.newTransfer(cou_adr, 
                                                                      rs.getString("adr"),
                                                                      per_cit, 
                                                                      cur, 
                                                                      "State budget donation", 
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
                                           else this.lawErr(lawID, "Minimum 0.0001 / citizen required", block);
                                       } 
                                       else this.lawErr(lawID, "Innsuficient funds", block);
                                    } 
                                    else this.lawErr(lawID, "Invalid currency", block);
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
                                    else this.propErr(propID, "Innsuficient funds", block);
                                } 
                                else this.propErr(propID, "Invalid currency", block);
                                
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
           long total=this.getCongressVotingPower(rs.getString("cou"));
           
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
                UTILS.DB.executeUpdate("UPDATE props "
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
                                 + "SET energy=energy-energy*0.0008"
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
             online<UTILS.BASIC.tstamp()-600 && 
             last_block<UTILS.BASIC.tstamp()-600)
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
              
           }
           catch (Exception ex)
           {
               System.out.println(ex.getMessage());
           }
       }
     }
}
