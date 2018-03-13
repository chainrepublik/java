// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.kernel;

import java.io.*;
import java.net.Socket;
import java.util.*;
import org.apache.commons.io.FileUtils;

public class CSettings 
{
  // Port
  public int port=10000;
  
  // Chk block
  public long chk_blocks=100;
  
  // Database type
  public String db_engine="mysql";
  
  // Database name
  public String db_name="chainrepublik";
  
  // DB username
  public String db_user="root";
  
  // DB pass
  public String db_pass="";
  
  // Wallet file pass
  public String wallet_pass="wallet";
  
  // Min peers
  public int min_peers=3;
  
  // Db debug
  public String db_debug="false";
  
  // Db debug
  public double min_fee=0.0001;
  
  // Seed
  public boolean seed_mode=false;
  
  // Start mining
  public boolean start_mining=false;
  
  // Sync start block
  public long sync_start_block=0;
  
  
  // Settings
  Properties settings = new Properties();
  
  public CSettings() throws Exception
  { 
      // Write dir
      UTILS.WRITEDIR=this.getAppDataDirectory();
      
      // Check if settings file exists
      File f=new File(UTILS.WRITEDIR+"wallet.ini");
		  
      // File exist ?
      if (f.exists()==false)
      {
          FileUtils.writeStringToFile(f, "", "UTF-8");
      }
      else
      {
          // File input stream
          FileInputStream in=new FileInputStream(UTILS.WRITEDIR+"wallet.ini");
          
          // Load settings
          settings.load(in);
          
          // Port
          if (settings.containsKey("port")) 
              this.port=Integer.parseInt(settings.getProperty("port"));
          
          // Db engine
          if (settings.containsKey("db")) 
              this.db_engine=settings.getProperty("db_engine");
          
          // Db user
          if (settings.containsKey("db_user")) 
              this.db_user=settings.getProperty("db_user");
          
          // Db pass
          if (settings.containsKey("db_pass")) 
              this.db_pass=settings.getProperty("db_pass");
          
          // Close
          in.close();
      }
      
       // Port busy
      this.portBusy();
  }
  
   public boolean portBusy() throws Exception
   {
        try 
        {
            // Connects to local
            Socket s = new Socket("127.0.0.1", this.port);
               
            // Return
            System.out.println("Port busy. Exiting !!!");
            System.exit(0);
        }
        catch(Exception ex) 
        {
            return false;
        }
        
        return false;
    }
  
  public String getWalletPass()
  {
      return this.wallet_pass;
  }
  
   public String getAppDataDirectory()  throws Exception
   {
        String appDataDirectory;
		    
	try 
	{
            appDataDirectory = System.getenv("APPDATA"); 
		        
	    if (appDataDirectory != null) 
	    {
	        appDataDirectory += File.separator + "Wallet" + File.separator;
		        }
		        else 
		        { 
		            appDataDirectory = System.getenv("HOME"); 
		            if (appDataDirectory != null) 
		            {
		                appDataDirectory +=  File.separator + "Wallet" + File.separator;
		            }
		            else
		            { 
		                throw new Exception("Could not access APPDATA or HOME environment variables");
		            }
		        }
		    }
		    catch(Exception e) {
		        e.printStackTrace();
		        appDataDirectory = "";
		    }

		    if (appDataDirectory != null && appDataDirectory.length() > 0) 
		    {
		    	 File f=new File(appDataDirectory);
		         
		    	 if (!f.exists())	
		    	 {
		    	   try 
		           {
		              File dir = new File(appDataDirectory);
		              dir.mkdir();
		              
		              // Blocks directory
		              dir = new File(appDataDirectory+"blocks"+File.separator);
		              dir.mkdir();
		              
		              // Multisig directory
		              dir = new File(appDataDirectory+"multisig"+File.separator);
		              dir.mkdir();
		           }
		           catch(Exception e) 
		           {
		               e.printStackTrace();
		            }
		    	  }
		    }
		    
		    return appDataDirectory;
    }
}