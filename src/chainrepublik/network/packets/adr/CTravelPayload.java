package chainrepublik.network.packets.adr;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

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
        this.checkEnergy(e);
   	
        // Registered ?
        if (!UTILS.BASIC.isRegistered(this.target_adr, this.block))
            throw new Exception("Address is not registered - CTravelPayload.java");
        
        // Country
        if (!UTILS.BASIC.isCountry(cou))
             throw new Exception("Invalid name - CTravelPayload.java");
        
        // Location 
        if (UTILS.BASIC.getAdrData(this.target_adr, "loc").equals(this.cou))
            throw new Exception("Already there - CTravelPayload.java");
       
        // Required ticket
        String ticket="ID_TRAVEL_TICKET_Q1";
        
        // Check for ticket
        if (dist<1000)
            ticket="ID_TRAVEL_TICKET_Q1";
        
        // Less tha 2000
        else if (dist>=1000 && dist<3000)
            ticket="ID_TRAVEL_TICKET_Q2";
        
        // Less tha 3000
        else if (dist>=3000 && dist<5000)
            ticket="ID_TRAVEL_TICKET_Q3";
        
        // Less tha 4000
        else if (dist>=5000 && dist<7000)
            ticket="ID_TRAVEL_TICKET_Q4";
        
        // Less tha 5000
        else if (dist>=7000)
            ticket="ID_TRAVEL_TICKET_Q5";
            
        // Has ticket ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM stocuri "
                                          + "WHERE adr='"+this.target_adr+"' "
                                            + "AND tip='"+ticket+"' "
                                            + "AND qty>=1");
        
        // Has data ?
        if (!UTILS.DB.hasData(rs))
           throw new Exception("Address has no tickets - CTravelPayload.java");
        
        // Check hash
 	String h=UTILS.BASIC.hash(this.getHash()+
 			          cou);
 	    
        if (!this.hash.equals(h)) 
            throw new Exception("Invalid hash - CTravelPayload.java");
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
       
        // Required ticket
        String ticket="ID_TRAVEL_TICKET_Q1";
        
        // Check for ticket
        if (dist<1000)
            ticket="ID_TRAVEL_TICKET_Q1";
        
        // Less tha 2000
        else if (dist>=1000 && dist<3000)
            ticket="ID_TRAVEL_TICKET_Q2";
        
        // Less tha 3000
        else if (dist>=3000 && dist<5000)
            ticket="ID_TRAVEL_TICKET_Q3";
        
        // Less tha 4000
        else if (dist>=5000 && dist<7000)
            ticket="ID_TRAVEL_TICKET_Q4";
        
        // Less tha 5000
        else if (dist>=7000)
            ticket="ID_TRAVEL_TICKET_Q5";
       
        // Load ticket ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM stocuri "
                                          + "WHERE adr='"+this.target_adr+"' "
                                            + "AND tip='"+ticket+"' "
                                            + "AND qty>=1");
        
        // Next
        rs.next();
        
        // Remove
        UTILS.DB.executeUpdate("DELETE FROM stocuri "
                                   + "WHERE stocID='"+rs.getLong("stocID")+"'");
      
       // Clear transactions
       UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
    }
}