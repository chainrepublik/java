package chainrepublik.agents.VM.SYSTEM;


public class CTransData 
{
    // Source 
    public String SRC;
    
    // Dest 
    public String DEST;
	
    // Amount
    public double AMOUNT;
	
    // Currency
    public String CUR;
	
    // Escrower
    public String ESCROWER;
        
    // Message
    public String MES;
    
    // Hash
    public String HASH;
    
    // Tstamp
    public long TSTAMP;
        
    public CTransData(String src, 
                      String dest,
                      double amount, 
                      String cur, 
                      String escrower, 
                      String mes,
                      String hash,
                      long tstamp)
    {
        // Source 
        this.SRC=src;
        
        // Dest
        this.DEST=dest;
	
        // Amount
        this.AMOUNT=amount;
	
        // Currency
        this.CUR=cur;
	
        // Escrower
        this.ESCROWER=escrower;
        
        // Message
        this.MES=mes;
        
         // Hash
        this.MES=hash;
        
         // Tstamp
        this.TSTAMP=tstamp;
    }
    
    
}
