package chainrepublik.network.packets.adr;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CTravelPayload extends CPayload
{
    // Country
   String cou;
   
   // Serial
   private static final long serialVersionUID = 100L;
   
   public CTravelPayload(String adr,
                          String cou) throws Exception
   {
	   // Superclass
	   super(adr);
	   
	   // Country
	   this.cou=cou;
	   
	   // Hash
 	   hash=UTILS.BASIC.hash(this.getHash()+
 			         cou);
   }
   
   public void check(CBlockPayload block) throws Exception
   {
   	// Super class
    	super.check(block);
        
         // Distance
        double dist=UTILS.BASIC.getCouDist(UTILS.BASIC.getAdrData(this.target_adr, "loc"), this.cou);
       
        // Energy
        double e=UTILS.BASIC.round(dist/1000, 2);
       
        // Minimum
        if (e<0.1) e=0.1;
       
         // Check energy
        this.checkEnergy();
   	
        // Registered ?
        if (!UTILS.BASIC.isRegistered(this.target_adr))
            throw new Exception("Address is not regisstered - CAdrRegisterPayload.java");
        
        // Country
        if (!UTILS.BASIC.isCountry(cou))
             throw new Exception("Invalid name - CAdrRegisterPayload.java");
        
        // Location 
        if (UTILS.BASIC.getAdrData(this.target_adr, "loc").equals(this.cou))
            throw new Exception("Already there - CAdrRegisterPayload.java");
       
        // Required ticket
        String ticket="ID_TRAVEL_TICKET_Q1";
        
        // Check for ticket
        if (dist<1000)
            ticket="ID_TRAVEL_TICKET_Q1";
        
        // Less tha 2000
        else if (dist>=1000 && dist<2000)
            ticket="ID_TRAVEL_TICKET_Q2";
        
        // Less tha 3000
        else if (dist>=2000 && dist<3000)
            ticket="ID_TRAVEL_TICKET_Q3";
        
        // Less tha 4000
        else if (dist>=3000 && dist<4000)
            ticket="ID_TRAVEL_TICKET_Q4";
        
        // Less tha 5000
        else if (dist>=4000)
            ticket="ID_TRAVEL_TICKET_Q5";
            
        // Has ticket ?
        if (UTILS.ACC.getBalance(this.target_adr, ticket)<1)
            throw new Exception("No ticket found - CAdrRegisterPayload.java");
        
        // Check hash
 	String h=UTILS.BASIC.hash(this.getHash()+
 			          cou);
 	    
        if (!this.hash.equals(h)) 
            throw new Exception("Invalid hash - CAdrRegisterPayload.java");
        
        // Consume ticket
        UTILS.ACC.newProdTrans(this.target_adr, 
                               -1, 
                               ticket, 
                               "You have moved to "+this.cou, 
                               hash, 
                               0, 
                               this.block);
 }
   
   public void commit(CBlockPayload block) throws Exception
   {
       // Superclass
       super.commit(block);
       
       // Distance
       double dist=UTILS.BASIC.getCouDist(UTILS.BASIC.getAdrData(this.target_adr, "loc"), this.cou);
       
       // Energy
       double e=UTILS.BASIC.round(dist/1000, 2);
       
       // Minimum
       if (e<0.1) e=0.1;
       
       // Reach destination
       long r=Math.round(dist/10);
       
       // Change cit
       UTILS.DB.executeUpdate("UPDATE adr "
                               + "SET travel='"+(this.block+r)+"', "
                                   + "travel_cou='"+this.cou+"' "
                             + "WHERE adr='"+this.target_adr+"'");
      
       // Clear transactions
       UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
    }
}