// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.kernel;


import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;

public class CWallet 
{
   CAddressess addresses=new CAddressess();
   
   // Output buffer
   ObjectOutputStream f_out;
   ObjectInputStream f_in;
   
   // Addresses number
   public int adr_no=0;
   
   // Wallet data
   String wallet="";
   
   // Wallet Balance
   public double balance=0;
   
   // Last generated address
   public CAddress last_adr=null; 
   
   // Wallet
   File f;
   
   public CWallet() throws Exception
   {
	// Check if settings file exists
        f=new File(UTILS.WRITEDIR+"wallet.CRC");
        
        // Log 
        System.out.println("Loading wallet from "+UTILS.WRITEDIR+"wallet.CRC");
        	  
	// File exist ?
	if (f.exists()==false)
	{
	    System.out.println("Creating initial wallet....");
			  
	    FileOutputStream out=new FileOutputStream(f);
	    f_out=new ObjectOutputStream(out);
			    
	    // Generate an address
	    CAddress adr=new CAddress();
	    adr.generate();
	    this.addresses.add(new CAdr(adr.getPublic(), adr.getPrivate(), ""));
			    
	    // Insert address
            UTILS.DB.executeUpdate("INSERT INTO my_adr "
                                         + "SET userID='1', "
                                             + "adr='"+adr.getPublic()+"'");
			    
	    // Save wallet
	    save();
        }
	else
        {
            // Display
            System.out.println("Decrypting wallet file...");
        
            // Input Streqm
            byte[] w_enc = FileUtils.readFileToByteArray(f);
            
            // Decrypt
            byte[] w_dec=UTILS.AES.decryptData(w_enc, UTILS.SETTINGS.getWalletPass());
            
            // Deserialize
            this.addresses=(CAddressess) UTILS.SERIAL.deserialize(w_dec);
            
            // Done
            System.out.println("Decrypted. "+this.addresses.adr.size()+" addressess found");
        }
     }
   
     public void save() throws Exception
     {
         String w="";
    	 
         // Encrypt
    	 try
    	 {
             // Serialize
             byte[] wr=UTILS.SERIAL.serialize(this.addresses);
             
             // Encrypt
             byte[] enc=UTILS.AES.encryptData(wr, UTILS.SETTINGS.getWalletPass()); 
             
             // Write to file
             FileUtils.writeByteArrayToFile(f, enc);
         }
    	 catch (Exception ex) 
    	 { 
    	    System.out.println("Exception in CWallet.java file at line 120"); 
    	 }
     }
     
     public void removeAdr(int no) throws Exception
     {
       this.addresses.adr.remove(no);
       save();
     }
     
    
     
      public String newAddress(long userID, String description) throws Exception
      {
        // Generate an address
        CAddress adr=new CAddress();
        
        // Generate
	adr.generate();
		 
	// Add
	this.addresses.add(new CAdr(adr.getPublic(), adr.getPrivate(), ""));
         
        // Insert address
	UTILS.DB.executeUpdate("INSERT INTO my_adr "
                                     + "SET userID='"+userID+"', "
                                         + "adr='"+adr.getPublic()+"', "
                                         + "description='"+UTILS.BASIC.base64_encode(description)+"'");
        
        // Save wallet
	save();
				 
        // Last generated
	this.last_adr=adr;
        
        // Return
        return adr.getPublic();
     }
     
     public void add(CAddress adr) throws Exception
     {
        // Already in wallet ?
        if (this.adrExist(adr))
            return;
            
    	// Add address
        this.addresses.add(new CAdr(adr.getPublic(), adr.getPrivate(), ""));
		 
	// Save wallet
	this.save();
	
        // Last generated
	this.last_adr=adr;
    }
     
     
     public boolean isMine(String adr) throws Exception
     {
         String ad;
         
    	 // Load address
    	 ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                            + "FROM my_adr "
                                           + "WHERE adr='"+adr+"'");
         
         // Has data ?
         if (UTILS.DB.hasData(rs))
             return true;
         else
             return false;
         
     }
     
    
     public CAddress getAddress(String adr) throws Exception
     {
    	 for (int a=0; a<=this.addresses.adr.size()-1; a++)
    	 {
    		 if (this.addresses.adr.get(a).pub_key.equals(adr))
    			 return new CAddress(this.addresses.adr.get(a).pub_key, this.addresses.adr.get(a).priv_key);
    	 }
    	 
    	 throw new Exception("Address doesn't exist");
     }
     
     public void list()
     {
         System.out.println(this.addresses.adr.size()+" total addressess");
         
         for (int a=0; a<=this.addresses.adr.size()-1; a++)
         {
            CAdr adr=this.addresses.adr.get(a);
            System.out.println("Address : "+adr.pub_key);
            System.out.println("-------------------------------------------");
         }
     }
     
     public boolean adrExist(CAddress address)
     {
         for (int a=0; a<=this.addresses.adr.size()-1; a++)
         {
            CAdr adr=this.addresses.adr.get(a);
            if (adr.pub_key.equals(address.getPublic()))
                return true;
         }
         
         return false;
     }
}
