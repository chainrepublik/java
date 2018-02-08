package chainrepublik.agents.VM.SYSTEM;

public class CMesData 
{
    // Receiver
    public String SENDER;
    
    // Dest
    public String REC;
	
    // Subj
    public String SUBJ;
	
    // Mes
    public String MES;
    
    // Hash
    public String HASH;
    
    // Tstamp
    public long TSTAMP;
	
    public CMesData(String sender, 
                    String rec, 
                    String subj, 
                    String mes,
                    String hash,
                    long tstamp)
    {
        // Receiver
        this.SENDER=sender;
        
        // Receiver
        this.REC=rec;
	
        // Subj
        this.SUBJ=subj;
	
        // Mes
        this.MES=mes;
        
        // Hash
        this.HASH=hash;
        
        // Tastamp
        this.TSTAMP=tstamp;
    }
    
   
}
