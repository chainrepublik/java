package chainrepublik.network.packets.companies;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CUpdateWorkplacePayload extends CPayload
{
    // Workplace ID
    long workplaceID;
    
    // Status
    String status;
    
    // Wage
    double wage; 
    
    // Prod
    String prod;
    
    
    public CUpdateWorkplacePayload(String adr,
                                   long workplaceID,
                                   String status,
                                   double wage,
                                   String prod) throws Exception
    {
        // Superclass
	super(adr);
        
        // Workplace ID
        this.workplaceID=workplaceID;
    
        // Status
        this.status=status; 
    
        // Wage
        this.wage=wage;
        
        // Prod
        this.prod=prod;
        
        // Hash
 	hash=UTILS.BASIC.hash(this.getHash()+
 			      this.workplaceID+
                              this.status+
 			      this.wage+
                              this.prod);
           
        // Sign
        this.sign();
    }
    
     public void check(CBlockPayload block) throws Exception
     {
   	// Super class
   	super.check(block);
        
        // Load workplace data
        ResultSet work_rs=UTILS.DB.executeQuery("SELECT * "
                                                + "FROM workplaces "
                                               + "WHERE workplaceID='"+this.workplaceID+"'");
        
        // Has data ?
        if (!UTILS.DB.hasData(work_rs))
            throw new Exception("Invalid workplace ID - CUpdateWorkplacePayload.java, 68");
        
        // Workplace data
        work_rs.next();
        
        // Company ID
        long comID=work_rs.getLong("comID");
        
        // Load company data
        ResultSet com_rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM companies "
                                              + "WHERE comID='"+comID+"' "
                                                + "AND adr='"+this.target_adr+"'");
        
        // Has data ?
        if (!UTILS.DB.hasData(com_rs))
            throw new Exception("Invalid company ID - CUpdateWorkplacePayload.java, 68");
        
        // Company data
        com_rs.next();
        
        // Company balance
        double balance=UTILS.ACC.getBalance(com_rs.getString("adr"), "CRC");
        
        // Find tools
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM com_prods "
                                          + "WHERE com_type='"+com_rs.getString("tip")+"' "
                                            + "AND type='ID_TOOLS' "
                                       + "ORDER BY prod ASC");
        
        // Has data ?
        if (!UTILS.DB.hasData(rs))
            throw new Exception("Invalid entry data - CUpdateWorkplacePayload.java, 68");
        
        // Next
        rs.next();
        
        // Tool
        String tool=rs.getString("prod");
        
        // Find building
        rs=UTILS.DB.executeQuery("SELECT * "
                                 + "FROM com_prods "
                                + "WHERE com_type='"+com_rs.getString("tip")+"' "
                                  + "AND type='ID_BUILDING' "
                             + "ORDER BY prod ASC");
        
         // Has data ?
        if (!UTILS.DB.hasData(rs))
            throw new Exception("Invalid entry data - CUpdateWorkplacePayload.java, 68");
        
        // Next
        rs.next();
        
        // Building
        String build=rs.getString("prod");
        
        // Status
        if (!this.status.equals("ID_FREE") && 
            !this.status.equals("ID_SUSPENDED"))
            throw new Exception("Invalid status - CUpdateWorkplacePayload.java, 73");
        
        // Status active ?
        if (this.status.equals("ID_FREE"))
        {
            // Company has funds ?
            if (balance<work_rs.getDouble("wage") || 
                balance<this.wage)
                throw new Exception("Insuficient funds to execute this operation - CUpdateWorkplacePayload.java, 73");
            
            // Company has tools ?
            if (UTILS.ACC.getBalance(com_rs.getString("adr"), tool, block)<1)
                throw new Exception("Company has no tools - CUpdateWorkplacePayload.java, 73");
            
            // Company has building ?
            if (UTILS.ACC.getBalance(com_rs.getString("adr"), build, block)<1)
                throw new Exception("Company has no building - CUpdateWorkplacePayload.java, 73");
            
            // Occupied
            if (work_rs.getString("status").equals("ID_OCCUPIED"))
                throw new Exception("Workplaces is occupied - CUpdateWorkplacePayload.java, 73");
        }
    
        // Wage
        if (this.wage<0.0001)
             throw new Exception("Invalid wage - CUpdateWorkplacePayload.java, 73");
    
         // Prod
         if (!UTILS.BASIC.isStringID(this.prod))
             throw new Exception("Invalid product - CUpdateWorkplacePayload.java, 73");
         
         // Is finite product ?
         rs=UTILS.DB.executeQuery("SELECT * "
                                  + "FROM com_prods "
                                 + "WHERE com_type='"+com_rs.getString("tip")+"' "
                                   + "AND prod='"+this.prod+"' "
                                   + "AND type='ID_FINITE'");
         
         // Has data
         if (!UTILS.DB.hasData(rs))
             throw new Exception("Invalid product - CUpdateWorkplacePayload.java, 73");
         
         // Production licence exist on stoc ?
         rs=UTILS.DB.executeQuery("SELECT * "
                                  + "FROM stocuri AS st "
                                  + "JOIN tipuri_licente AS tl ON tl.tip=st.tip "
                                 + "WHERE st.adr='"+com_rs.getString("adr")+"' "
                                   + "AND tl.prod='"+this.prod+"' "
                                   + "AND st.qty>=1");
         
         // Has data
         if (!UTILS.DB.hasData(rs))
             throw new Exception("Invalid product - CUpdateWorkplacePayload.java, 73");
         
         // Hash
         String h=UTILS.BASIC.hash(this.getHash()+
 		      	           this.workplaceID+
                                   this.status+
 			           this.wage+
                                   this.prod);
         
         // Hashes match ?
         if (!h.equals(this.hash))
             throw new Exception("Invalid hash - CUpdateWorkplacePayload.java, 73");
     }
     
    public void commit(CBlockPayload block) throws Exception
    {
       // Superclass
       super.commit(block);
       
       // Update
       UTILS.DB.executeUpdate("UPDATE workplaces "
                               + "SET status='"+this.status+"', "
                                   + "wage='"+UTILS.FORMAT_4.format(this.wage)+"', "
                                   + "prod='"+this.prod+"' "
                             + "WHERE workplaceID='"+this.workplaceID+"'");
    }
}
