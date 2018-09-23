// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.network.packets.trans;
import chainrepublik.kernel.CAddress;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;



public class CTransPayload extends CPayload 
{
    // Destination
    public String dest;
	
    // Amount
    public double amount;
	
    // Currency
    public String cur;
	
    // Escrower
    public String escrower;
        
    // Message
    public String mes="";
    
    // Mesaage key
    public String mes_key;
        
    // Serial
    private static final long serialVersionUID = 100L;
        
    public CTransPayload(String src, 
                             String dest, 
                             double amount, 
                             String cur, 
                             String mes,
                             String escrower) throws Exception
        {
           // Constructo
           super(src);
          
           // Destination
	   this.dest=dest;
		
	   // Amount
	   this.amount=amount;
           
           // Currency
	   this.cur=cur;
           
           // Message
           this.mes=mes;
                
           // Escrower
           this.escrower=escrower;
           
           // Message ?
           if (!this.mes.equals(""))
           {
               // Generates a key
               String k=UTILS.BASIC.randString(25);
            
               // Subject and message
	       this.mes=UTILS.AES.encrypt(mes, k);
		    
	        // Encrypt key
	        CAddress ecc=new CAddress(dest);
	        this.mes_key=ecc.encrypt(k);
           }
           else
           {
               this.mes_key="";
           }
           
           // Digest
           this.hash=UTILS.BASIC.hash(this.getHash()+
		                        this.dest+
                                        this.amount+
                                        this.cur+
                                        this.mes+
                                        this.escrower); 
           
       }
        
        
    public void check(CBlockPayload block) throws Exception
    {
        // Super class
	super.check(block);
        
        // Check for null
        if (this.cur==null ||
            this.dest==null ||
            this.escrower==null ||
            this.mes==null ||
            this.mes_key==null)
        throw new Exception("Null assertion failed - CTransPayload.java, 68");
	     
	// Free address
        if (UTILS.BASIC.isCompanyAdr(this.target_adr) || 
            UTILS.BASIC.isGovAdr(this.target_adr) ||
            UTILS.BASIC.isOrgAdr(this.target_adr))
            throw new Exception("Source can't spend funds - CTransPayload.java");
             
        // Check dest
	if (!UTILS.BASIC.isAdr(this.dest))
	   throw new Exception("Invalid destination address - CTransPayload.java");
             
        // Source and destination the same
        if (this.target_adr.equals(this.dest))
	    throw new Exception("Source and destination address can't be the same - CTransPayload");
             
        // Check amount
        if (this.cur.equals("CRC"))
        {
	    if (this.amount<0.0001)
	      	throw new Exception("Invalid amount, CTransPayload");
        }
        else
        {
            if (this.amount<0.00000001)
	      throw new Exception("Invalid amount, CTransPayload");
        }
            
        // Message 
        if (this.mes.length()>2500)
            throw new Exception("Invalid amount, CTransPayload");
            
        // Check cur
	if (!this.cur.equals("CRC"))
        {
            // Symbol valid ?
            if (!UTILS.BASIC.isSymbol(this.cur, 5) && 
                !UTILS.BASIC.isSymbol(this.cur, 6))
            throw new Exception("Invalid currency, CTransPayload");
                     
            // Asset exist ?
            if (!UTILS.BASIC.isAsset(this.cur))
               throw new Exception("Invalid currency, CTransPayload");
                
            // Recipient trust asset ?
            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM adr_attr "
                                              + "WHERE adr='"+this.dest+"' "
                                                + "AND attr='ID_TRUST_ASSET' "
                                                + "AND s1='"+this.cur+"'");
                
            // No data ?
            if (!UTILS.DB.hasData(rs))
                throw new Exception("Recipient doesn't trust asset, CTransPayload");
        }
            
        // Check escrower
	if (!this.escrower.equals(""))
        {
           if (!UTILS.BASIC.isAdr(this.escrower))
	      throw new Exception("Invalid escrower, CTransPayload");
           
           // Escrower sender or receiver ?
           if (this.escrower.equals(this.target_adr) || 
               this.escrower.equals(this.dest))
               throw new Exception("Invalid escrower, CTransPayload");
        }
        
	// Check source balance
        double balance=UTILS.ACC.getBalance(this.target_adr, this.cur, block);
                  
        // Insufficient funds
        if (balance<this.amount) 
	   throw new Exception("Insuficient funds, CTransPayload.java ");
            
        // Digest
        String h=UTILS.BASIC.hash(this.getHash()+
		                  this.dest+
                                  this.amount+
                                  this.cur+
                                  this.mes+
                                  this.escrower); 
                  
        // Check hash
        if (!this.hash.equals(h))
           throw new Exception("Invalid hash, CTransPayload");
	    
        // Insert pending transaction
	UTILS.ACC.newTrans(this.target_adr, 
                           this.dest,
                           -this.amount,
                           this.cur, 
                           "Simple transaction", 
                           this.escrower, 
                           0,
                           this.hash, 
                           this.block,
                           false,
                           "",
                           "");
                
        // To destination
        if (this.escrower.equals(""))
        UTILS.ACC.newTrans(this.dest, 
                           this.target_adr,
                           this.amount,
                           this.cur, 
                           "Simple transaction", 
                           this.escrower, 
                           0,
                           this.hash, 
                           this.block,
                           false,
                           "",
                           "");
            
        if (UTILS.WALLET.isMine(this.dest))
        {
            if (!this.mes.equals("")) 
            {
                // Decrypt key
    	        CAddress adr=UTILS.WALLET.getAddress(this.dest);
    	        String dec_key=adr.decrypt(this.mes_key);
    	   
    	        // Decrypt subject
    	        String dec_subject=UTILS.AES.decrypt(this.mes, dec_key);
		   
    	        // Decrypt message
    	        String dec_mes=UTILS.AES.decrypt(mes, dec_key);
    	   
                // Update
                UTILS.DB.executeUpdate("UPDATE my_trans "
                                        + "SET mes='"+UTILS.BASIC.base64_encode(dec_mes)+"' "
                                      + "WHERE hash='"+this.hash+"'");  
            }
        }
    }
       
    public void commit(CBlockPayload block) throws Exception
    { 
        // Commit
        super.commit(block);
             
        // Take coins
        UTILS.ACC.clearTrans(hash, "ID_SEND", this.block);
                
        // Deposit coins
        if (this.escrower.equals(""))
        {
            // Clear transaction for receiver
            UTILS.ACC.clearTrans(hash, "ID_RECEIVE", this.block);
            
            // Check company owner in case of shares transfer
            if (this.cur.length()==5)
               UTILS.BASIC.checkComOwner(this.cur, this.block);
        }
        else
        {
            UTILS.DB.executeUpdate("INSERT INTO escrowed "
                                         + "SET trans_hash='"+this.hash+"', "
                                              + "sender_adr='"+this.target_adr+"', "
                                              + "rec_adr='"+this.dest+"', "
                                              + "escrower='"+this.escrower+"', "
                                              + "amount='"+UTILS.FORMAT_8.format(this.amount)+"', "
                                              + "expires='"+(this.block+43200)+"', "
                                              + "cur='"+this.cur+"', "
                                              + "block='"+this.block+"'");
        }
    }
}
