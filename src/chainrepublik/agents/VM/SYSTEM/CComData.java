package chainrepublik.agents.VM.SYSTEM;

public class CComData 
{
   // Address
   public String ADR;
   
   // Com ID
   public long COMID;
   
   // Name
   public String NAME;
   
   // Code
   public String CODE;
   
   // Symbol
   public String SYMBOL;
   
   // Expires
   public long EXPIRES;
   
   // Pic    
   public String PIC;
   
   // Balance
   public double BALANCE;
   
   // Electricity
   public double ELECTRICITY;
   
   
   public CComData(String adr, 
                    long comID, 
                    String name,
                    String code,
                    String symbol,
                    long expires,
                    String pic,
                    double balance,
                    double electricity)
    {
        // Adr
        this.ADR=adr;
	
        // Com ID
        this.COMID=comID;
	
        // Name
        this.NAME=name;
        
        // Code
        this.CODE=code;
        
        // Symbol
        this.SYMBOL=symbol;
        
        // Expires
        this.EXPIRES=expires;
        
        // Balance
        this.BALANCE=expires;
        
        // Electricity
        this.ELECTRICITY=expires;
    }
    
   
}
