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
   
     public void runCrons(long block, CBlockPayload block_payload) throws Exception
     {
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
                                 + "SET energy=energy+0.0002 "
                               + "WHERE tip='ID_WINE' "
                                 + "AND adr<>'default'");
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
             UTILS.DB.executeUpdate("UPDATE adr "
                                 + "SET pol_inf=pol_inf-pol_inf*0.01"
                               + "WHERE pol_inf>=1");
             
             UTILS.DB.executeUpdate("UPDATE adr "
                                     + "SET pol_inf=0 "
                                   + "WHERE pol_inf<1");
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
