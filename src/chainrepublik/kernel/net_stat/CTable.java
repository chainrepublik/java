package chainrepublik.kernel.net_stat;


import java.sql.ResultSet;
import chainrepublik.kernel.UTILS;

public class CTable 
{
    // Name
    public String name;
    
    // Reorginizing
    public boolean reorg=false;
    
    public CTable(String name)
    {
       // Name
       this.name=name;
    }
    
    public void create() throws Exception
    {
        
    }
    
    public boolean tableExist(String tab) throws Exception
     {
	ResultSet rs;
      
	rs=UTILS.DB.executeQuery("SELECT * "
                                       + "FROM INFORMATION_SCHEMA.TABLES "
                                      + "WHERE TABLE_NAME='"+tab+"' "
	   		                + "AND TABLE_SCHEMA='"+UTILS.SETTINGS.db_name+"'");
	   
	   
        if (UTILS.DB.hasData(rs))
            return true;
        else
            return false;
    }
    
    public void refresh(long block, String hash) throws Exception
    {
        // Save ?
        if ((block+1)%UTILS.SETTINGS.chk_blocks==0) 
        {
            // Debug
            System.out.print("Refreshing "+this.name+"...");
        
            // Flush
            this.flush(block, hash);
            
            // Status
            System.out.println("Done.");
        }
    }
    
    
    public void flush(long block, String block_hash) throws Exception
    {
          // Load file ?
           UTILS.DB.executeQuery("SELECT * FROM "+this.name+" INTO OUTFILE '"+UTILS.DB.fileLoc+"chk_"+this.name+"_"+block_hash+".table'");
        
           // Checkpoint
           this.checkpoint(block, block_hash);
    }
   
    public void checkpoint(long block, String hash) throws Exception
    {
        // Load data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM checkpoints "
                                          + "WHERE hash='"+hash+"'");
        
        // Hash data
        if (UTILS.DB.hasData(rs))
            return;
        else
            UTILS.DB.executeUpdate("INSERT INTO checkpoints "
                                         + "SET block='"+block+"', "
                                             + "hash='"+hash+"'");
    }
    
   public void expired(long block) throws Exception
   {
       
   }
   
   public void reorganize(long block, String chk_hash) throws Exception
   {
       
   }
    
   public void fromFile(String hash) throws Exception
   {
        // Write to file
        UTILS.DB.executeUpdate("LOAD data INFILE '"+UTILS.DB.fileLoc+"chk_"+this.name+"_"+hash+".table' INTO TABLE "+this.name);
   }
   
   public void loadCheckpoint(String hash) throws Exception
    {
        // Reorganizing
        this.reorg=true;
        
        // Status
        System.out.println("Loading checkpoint for table "+this.name+"...");
        
        // Drop table
        UTILS.DB.executeUpdate("DROP TABLE "+this.name);
        
        // Create table
        this.create();
        
        // From file
        this.fromFile(hash);
        
        // Status
        System.out.println("Done.");
        
        // Reorganizing
        this.reorg=false;
    }
   
   public void fullResync() throws Exception
   {
       // Message
       System.out.println("Dropping tables...");
       
       
       // Drop tables
       if (!this.name.equals("actions") && 
           !this.name.equals("my_adr") && 
           !this.name.equals("web_users") &&
           !this.name.equals("sys_stats") &&
           !this.name.equals("hidden"))
        UTILS.DB.executeUpdate("DROP TABLE "+this.name);
       
       // Done
       System.out.println("Done.");
   }
}
