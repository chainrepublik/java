// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets;

import chainrepublik.kernel.CAddress;
import chainrepublik.kernel.CECC;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.blocks.CBlockPayload;


public class CPayload  implements java.io.Serializable
{
	// Hash
	public String hash="";
	
	// Block target address
        public String target_adr="";
	
	// Signature
	public String sign="";
	
	// Tstamp
	public long tstamp=0;
	
	// Tstamp
        public long block=0;
        
	// Constructor
        public CPayload() throws Exception
	{
           // Time
           this.tstamp=UTILS.BASIC.mtstamp();
           
           // Block
	   this.block=UTILS.NET_STAT.last_block+1;
           
           // Hash
           this.hash=this.getHash();
	}
    
        //  Hash
        public String getHash() throws Exception
        {	
            return UTILS.BASIC.hash(this.target_adr+
                                    this.tstamp+
                                    this.block);
        }
    
        // Constructor
        public CPayload(String adr) throws Exception
        {
    	    // Time
            this.tstamp=UTILS.BASIC.mtstamp();
    	
           // Target address
    	   this.target_adr=adr;
    	
	   // Block
	   this.block=UTILS.NET_STAT.last_block+1;
	   
	   // Hash
	   this.hash=this.getHash();
        }
    
        // Check
        public void check(CBlockPayload block) throws Exception
        {
           // Hash
           if (!UTILS.BASIC.isHash(this.hash)) 
                throw new Exception("Invalid hash - CPayload");
            
           // Target adr
            if (!this.target_adr.equals(""))
               if (!UTILS.BASIC.isAdr(this.target_adr))
                  throw new Exception("Invalid target address - CPayload");
            
            // Sig valid ?
            if (!UTILS.BASIC.isBase64(this.sign))
               throw new Exception("Invalid signature format - CPayload");
            
           // Check sig
           if (!this.checkSig())
              throw new Exception("Invalid signature - CPayload");
           
           // Block number
           if (block!=null)
             if (block.block!=this.block)
               throw new Exception("Invalid block number - CPayload");
           
           // Delete previous trans
           UTILS.DB.executeUpdate("DELETE FROM my_trans WHERE hash='"+this.hash+"'");
           UTILS.DB.executeUpdate("DELETE FROM trans WHERE hash='"+this.hash+"'");
        }
        
        
        public void sign(String sig) throws Exception
        {
            if (!this.target_adr.equals(""))
            {
                if (sig.equals(""))
                {
    	           CAddress adr=UTILS.WALLET.getAddress(this.target_adr);
    	           this.sign=adr.sign(this.hash);
                }
                else this.sign=sig;
            }
        }
        
        public void sign() throws Exception
        {
            if (!this.target_adr.equals(""))
            {
               CAddress adr=UTILS.WALLET.getAddress(this.target_adr);
    	       this.sign=adr.sign(this.hash);
                
            }
        }
    
        public boolean checkSig() throws Exception
        {
    	   CECC ecc=new CECC(this.target_adr);
    	
    	   if (ecc.checkSig(this.hash, this.sign)==true)
    		return true;
    	   else
    		return false;
        }
    
        public void commit(CBlockPayload block) throws Exception
        {
           
        }
        
        public void footprint(String packet_hash) throws Exception
        {
        
        }
        
        public void checkEnergy() throws Exception
        {
           // Registered ?
           if (UTILS.BASIC.isRegistered(this.target_adr))
           {
               // Load address type
               String owner_type=UTILS.BASIC.getAdrOwnerType(this.target_adr);
              
               // Energy
               if (Double.parseDouble(UTILS.BASIC.getAdrData(this.target_adr, "energy"))<0.1)
                     throw new Exception("Not enought energy to execute this action - CPayload.java, Line 85");
               
               // Take energy
               UTILS.ACC.newTrans(this.target_adr, 
                                  "", 
                                  -0.1, 
                                  "ID_ENERGY", 
                                  "You have lost energy executing a game action", 
                                  "", 
                                  0, 
                                  this.hash, 
                                  this.block);
           }
        }
        
        public void checkEnergy(double required) throws Exception
        {
           // Registered ?
           if (UTILS.BASIC.isRegistered(this.target_adr))
           {
               // Load address type
               String owner_type=UTILS.BASIC.getAdrOwnerType(this.target_adr);
              
               // Energy
               if (Double.parseDouble(UTILS.BASIC.getAdrData(this.target_adr, "energy"))<0.1)
                     throw new Exception("Not enought energy to execute this action - CPayload.java, Line 85");
               
               // Take energy
               UTILS.ACC.newTrans(this.target_adr, 
                                  "", 
                                  -required, 
                                  "ID_ENERGY", 
                                  "You have lost energy executing a game action", 
                                  "", 
                                  0, 
                                  this.hash, 
                                  this.block);
           }
        }
        
}