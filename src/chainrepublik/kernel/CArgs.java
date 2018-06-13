package chainrepublik.kernel;

public class CArgs 
{
    // Late op
    String late_op="";
    
    // Rhash
    String rhash="";
    
    public CArgs()
    {
        
            
    }
    
    public void load(String[] args) throws Exception
    {
        for (int a=0; a<=args.length-1; a++)
        {
            String arg[]=args[a].split("=");
            this.process(arg[0], arg[1]);
        }
    }
  
  
    public void process(String name, String val) throws Exception
    {
        switch (name)
        {
            // Port
            case "port" :  UTILS.SETTINGS.port=Integer.parseInt(val);
                           break;
                           
             // DB Name
            case "db_name" :  UTILS.SETTINGS.db_name=val;
                              break;
                           
             // DB User
            case "db_user" :  UTILS.SETTINGS.db_user=val; 
                              break;
                           
             // DB Pass
            case "db_pass" :  UTILS.SETTINGS.db_pass=val;
                              break;
                           
             // DB Pass
            case "wallet_pass" :  UTILS.SETTINGS.wallet_pass=val;
                                  break;
                                  
            // Rhash
            case "rhash" : this.rhash=val;
                                  break;
                                  
            // DB debug
            case "db_debug" : UTILS.SETTINGS.db_debug=val;
                                  break;
                                  
            // Late op
            case "op" : this.late_op=val; break;
            
             // Min peers
            case "min_peers" : UTILS.SETTINGS.min_peers=Integer.valueOf(val); 
                               break;
            
            // No sync
            case "seed_mode" : UTILS.SETTINGS.seed_mode=Boolean.valueOf(val); 
                               if (UTILS.SETTINGS.seed_mode) System.out.println("Seed mode is ON.");
                               break;
                             
            // Start mining
            case "start_mining" : UTILS.SETTINGS.start_mining=Boolean.valueOf(val); 
                                  break;
            
            case "reorg_block" : UTILS.SETTINGS.reorg_block=Long.valueOf(val); 
                                 break;
            
            // Sync blocks
            case "sync_blocks" : UTILS.SETTINGS.sync_blocks=Long.valueOf(val); 
            break;
            
            // No sync
            case "auto_shutdown" : UTILS.SETTINGS.autoshutdown=Boolean.valueOf(val); 
                                   if (UTILS.SETTINGS.autoshutdown) System.out.println("Auto shutdown mode is ON.");
                                   break;
              
        }
    }
    
    public void lateOp() throws Exception
    {
        if (this.late_op.equals("full_resync"))
        {
            // Full resync
            UTILS.TABLES.full_resync();
            
            // Message
            System.out.println("Resync initiated. Restart the kernel without op=full_resync."); 
            
            // Exit
            System.exit(0);
        }
        
        if (this.late_op.equals("list_adr"))
        {
            // Full resync
            UTILS.WALLET.list();
            
            // Exit
            System.exit(0);
        }
        
        if (this.late_op.equals("reorg"))
        {
            // Full resync
            UTILS.NETWORK.CONSENSUS.reorganize(UTILS.SETTINGS.reorg_block);
            
            // Exit
            System.exit(0);
        }
    }
    
    
}
