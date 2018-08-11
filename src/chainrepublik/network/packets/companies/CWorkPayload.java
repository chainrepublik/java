package chainrepublik.network.packets.companies;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CWorkPayload extends CPayload
{
    // Workplace ID
    long workplaceID;
    
    // Status
    long minutes;
    
    public CWorkPayload(String adr,
                        long workplaceID,
                        long minutes) throws Exception
    {
        // Superclass
	super(adr);
        
        // Workplace ID
        this.workplaceID=workplaceID;
    
        // Minutes
        this.minutes=minutes; 
        
        // Hash
 	hash=UTILS.BASIC.hash(this.getHash()+
 			      this.workplaceID+
                              this.minutes);
    }
    
    public void disableWorkplaces(long comID, String reason) throws Exception
    {
        // Load company data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM companies "
                                          + "WHERE comID='"+comID+"'");
        
        // Next
        rs.next();
        
        // Disable
        UTILS.DB.executeUpdate("UPDATE workplaces "
                                + "SET status='ID_SUSPENDED' "
                              + "WHERE comID='"+comID+"' "
                                + "AND status<>'ID_OCCUPIED'");
        
        // Event for company
        UTILS.BASIC.newEvent(rs.getString("adr"), reason, this.block);
    }
    
    public boolean checkProd(String adr, 
                             double out_qty, 
                             String prod, 
                             double prod_qty,
                             CBlockPayload block) throws Exception
    {
        // Balance
        double prod_available=UTILS.ACC.getBalance(adr, prod, block);
        
        // Required
        double prod_req=prod_qty*out_qty;
        
        
        if (prod_available<prod_req)
            return false;
        else
            return true;
    }
    
     public void check(CBlockPayload block) throws Exception
     {
   	// Super class
   	super.check(block);
        
        // Registered address ?
        if (!UTILS.BASIC.isCitAdr(this.target_adr, this.block))
           throw new Exception("Only citizens can work - CWorkPayload.java, 68");
            
        // Workplace exist ?
        ResultSet work_rs=UTILS.DB.executeQuery("SELECT * "
                                                + "FROM workplaces "
                                               + "WHERE workplaceID='"+this.workplaceID+"' "
                                                 + "AND status='ID_FREE'");
        
        // Has data
        if (!UTILS.DB.hasData(work_rs))
            throw new Exception("Invalid workplace ID - CWorkPayload.java, 68");
        
        
        // Next
        work_rs.next();
        
        // Load company data
        ResultSet com_rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM companies "
                                              + "WHERE comID='"+work_rs.getLong("comID")+"'");
        
        // Has data ?
        if (!UTILS.DB.hasData(com_rs))
            throw new Exception("Invalid workplace ID - CWorkPayload.java, 68");
        
        // Next
        com_rs.next();
        
        // Company adr
        String com_adr=com_rs.getString("adr");
        
        // Minutes
        if (this.minutes<5 || this.minutes>480)
           throw new Exception("Invalid minutes - CWorkPayload.java, 68");
        
        // Energy
        double energy=UTILS.BASIC.getEnergy(this.target_adr);
        
        // Consumed energy
        double req_energy=this.minutes*0.2; 
        
        // Enough energy ?
        if (req_energy>energy)
            throw new Exception("Not enough energy - CWorkPayload.java, 68");
        
        // Find tools
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM com_prods "
                                          + "WHERE com_type='"+com_rs.getString("tip")+"' "
                                            + "AND type='ID_TOOLS' "
                                       + "ORDER BY prod ASC");
        
        // Next
        rs.next();
        
        // Tool
        String tool=rs.getString("prod");
        
        // Company has tools ?
        if (UTILS.ACC.getBalance(com_rs.getString("adr"), tool)<1)
                return;
        
        // Find building
        rs=UTILS.DB.executeQuery("SELECT * "
                                 + "FROM com_prods "
                                + "WHERE com_type='"+com_rs.getString("tip")+"' "
                                  + "AND type='ID_BUILDING' "
                             + "ORDER BY prod ASC");
        
        // Next
        rs.next();
        
        // Tool
        String build=rs.getString("prod");
        
        // Company has building ?
        if (UTILS.ACC.getBalance(com_rs.getString("adr"), build)<1)
                return;
        
        // Load balance
        double com_balance=UTILS.ACC.getBalance(com_rs.getString("adr"), "CRC");
        
        // Salary
        double sal=work_rs.getDouble("wage")/60*this.minutes;
        
        // Round
        sal=Double.parseDouble(UTILS.FORMAT_8.format(sal));
        
        // Company has funds ?
        if (com_balance<sal)
            return;
            
        // Load product data
        ResultSet prod_rs=UTILS.DB.executeQuery("SELECT * "
                                                + "FROM tipuri_produse "
                                               + "WHERE prod='"+work_rs.getString("prod")+"'");
        
        // Next
        prod_rs.next();
        
        // Output product
        String out_prod=work_rs.getString("prod");
        
        // Output qty
        double out_qty=this.minutes/(prod_rs.getDouble("work_hours")*60);
        
        // Check raw material 1
        if (prod_rs.getDouble("prod_1_qty")>0)
           if (!this.checkProd(com_rs.getString("adr"), 
                               out_qty, 
                               prod_rs.getString("prod_1"), 
                               prod_rs.getDouble("prod_1_qty"), 
                               block))
                return;
        
        // Check raw material 2
        if (prod_rs.getDouble("prod_2_qty")>0)
           if (!this.checkProd(com_rs.getString("adr"), 
                               out_qty, 
                               prod_rs.getString("prod_2"), 
                               prod_rs.getDouble("prod_2_qty"), 
                               block))
                return;
        
        // Check raw material 3
        if (prod_rs.getDouble("prod_3_qty")>0)
           if (!this.checkProd(com_rs.getString("adr"), 
                               out_qty, 
                               prod_rs.getString("prod_3"), 
                               prod_rs.getDouble("prod_3_qty"), 
                               block))
                return;
        
        // Check raw material 4
        if (prod_rs.getDouble("prod_4_qty")>0)
           if (!this.checkProd(com_rs.getString("adr"), 
                               out_qty, 
                               prod_rs.getString("prod_4"), 
                               prod_rs.getDouble("prod_4_qty"), 
                               block))
                return;
        
        // Check raw material 5
        if (prod_rs.getDouble("prod_5_qty")>0)
           if (!this.checkProd(com_rs.getString("adr"), 
                               out_qty, 
                               prod_rs.getString("prod_5"), 
                               prod_rs.getDouble("prod_5_qty"), 
                               block))
                return;
        
        // Check raw material 6
        if (prod_rs.getDouble("prod_6_qty")>0)
           if (!this.checkProd(com_rs.getString("adr"), 
                               out_qty, 
                               prod_rs.getString("prod_6"), 
                               prod_rs.getDouble("prod_6_qty"), 
                               block))
                return;
        
        // Check raw material 7
        if (prod_rs.getDouble("prod_7_qty")>0)
           if (!this.checkProd(com_rs.getString("adr"), 
                               out_qty, 
                               prod_rs.getString("prod_7"), 
                               prod_rs.getDouble("prod_7_qty"), 
                               block))
                return;
        
        // Check raw material 8
        if (prod_rs.getDouble("prod_8_qty")>0)
           if (!this.checkProd(com_rs.getString("adr"), 
                               out_qty, 
                               prod_rs.getString("prod_8"), 
                               prod_rs.getDouble("prod_8_qty"), 
                               block))
                return;
        
        // Already working ?
        rs=UTILS.DB.executeQuery("SELECT * "
                                 + "FROM work_procs "
                                + "WHERE adr='"+this.target_adr+"' "
                                  + "AND end>"+this.block);
        
        // Has data ?
        if (UTILS.DB.hasData(rs))
           throw new Exception("You are already working - CWorkPayload.java, 73");
        
        // Worked 8 hours today ?
        rs=UTILS.DB.executeQuery("SELECT SUM(end-start) AS total "
                                 + "FROM work_procs "
                                + "WHERE adr='"+this.target_adr+"'");
        
        // Next
        rs.next();
        
        // Total
        long total=rs.getLong("total");
        
        // More than 8 hours ?
        if (total+this.minutes>480)
            throw new Exception("You can work up to 8 hours a day - CWorkPayload.java, 73");
        
        // Cost
        double cost=0;
        
        // Consume raw 1
        double raw_1_qty=out_qty*prod_rs.getDouble("prod_1_qty");
        
        if (raw_1_qty>0)
        cost=UTILS.ACC.newProdTrans(com_rs.getString("adr"),
                                    -raw_1_qty, 
                                    prod_rs.getString("prod_1"), 
                                    "Raw material used in production process", 
                                    this.hash, 
                                    0,
                                    this.block);
        
        // Consume raw 2
        double raw_2_qty=out_qty*prod_rs.getDouble("prod_2_qty");
        
        if (raw_2_qty>0)
        cost=cost+UTILS.ACC.newProdTrans(com_rs.getString("adr"),
                                         -raw_2_qty, 
                                         prod_rs.getString("prod_2"),
                                         "Raw material used in production process", 
                                         this.hash, 
                                         0,
                                         this.block);
        
        // Consume raw 3
        double raw_3_qty=out_qty*prod_rs.getDouble("prod_3_qty");
        
        if (raw_3_qty>0)
        cost=cost+UTILS.ACC.newProdTrans(com_rs.getString("adr"),
                                         -raw_3_qty, 
                                         prod_rs.getString("prod_3"),
                                         "Raw material used in production process", 
                                         this.hash, 
                                         0,
                                         this.block);
        
        // Consume raw 4
        double raw_4_qty=out_qty*prod_rs.getDouble("prod_4_qty");
        
        if (raw_4_qty>0)
        cost=cost+UTILS.ACC.newProdTrans(com_rs.getString("adr"),
                                         -raw_4_qty, 
                                         prod_rs.getString("prod_4"),
                                         "Raw material used in production process", 
                                         this.hash, 
                                         0,
                                         this.block);
        
        // Consume raw 5
        double raw_5_qty=out_qty*prod_rs.getDouble("prod_5_qty");
        
        if (raw_5_qty>0)
        cost=cost+UTILS.ACC.newProdTrans(com_rs.getString("adr"),
                                         -raw_5_qty, 
                                         prod_rs.getString("prod_5"),
                                         "Raw material used in production process", 
                                         this.hash, 
                                         0,
                                         this.block);
        
        // Consume raw 6
        double raw_6_qty=out_qty*prod_rs.getDouble("prod_6_qty");
        
        if (raw_6_qty>0)
        cost=cost+UTILS.ACC.newProdTrans(com_rs.getString("adr"),
                                         -raw_6_qty, 
                                         prod_rs.getString("prod_6"),
                                         "Raw material used in production process", 
                                         this.hash, 
                                         0,
                                         this.block);
        
        // Consume raw 7
        double raw_7_qty=out_qty*prod_rs.getDouble("prod_7_qty");
        
        if (raw_7_qty>0)
        cost=cost+UTILS.ACC.newProdTrans(com_rs.getString("adr"),
                                         -raw_7_qty, 
                                         prod_rs.getString("prod_7"),
                                         "Raw material used in production process", 
                                         this.hash, 
                                         0,
                                         this.block);
        
        // Consume raw 8
        double raw_8_qty=out_qty*prod_rs.getDouble("prod_8_qty");
        
        if (raw_8_qty>0)
        cost=cost+UTILS.ACC.newProdTrans(com_rs.getString("adr"),
                                         -raw_8_qty, 
                                         prod_rs.getString("prod_8"),
                                         "Raw material used in production process", 
                                         this.hash, 
                                         0,
                                         this.block);
        
        // Costuri
        cost=Double.parseDouble(UTILS.FORMAT_8.format(cost+sal));
        
        // Finite material
        UTILS.ACC.newProdTrans(com_rs.getString("adr"),
                               out_qty, 
                               out_prod, 
                               "Finite material", 
                               this.hash, 
                               cost,
                               this.block);
        
        // Pay salary
        UTILS.ACC.newTransfer(com_rs.getString("adr"), 
                              this.target_adr, 
                              sal, 
                              "CRC", 
                              "Salary payout", 
                              "", 
                              0,
                              this.hash, 
                              this.block,
                              true,
                              "ID_SALARY_TAX",
                              "");
        
        // Consume energy
        UTILS.ACC.newProdTrans(this.target_adr, 
                              -req_energy, 
                              "ID_ENERGY", 
                              "Energy consumption", 
                              this.hash,
                              0,
                              this.block);
     }
     
     public void suspendWorkplaces(long comID, String adr, String reason) throws Exception
     {
         // Suspend workplaces
         UTILS.DB.executeUpdate("UPDATE workplaces "
                                 + "SET status='ID_SUSPENDED' "
                               + "WHERE comID='"+comID+"' "
                                 + "AND status='ID_FREE'");
         
         // New event
         UTILS.BASIC.newEvent(adr, reason, this.block);
     }
     
     public boolean checkCompany(CBlockPayload block) throws Exception
     {
          // Workplace exist ?
        ResultSet work_rs=UTILS.DB.executeQuery("SELECT * "
                                                + "FROM workplaces "
                                               + "WHERE workplaceID='"+this.workplaceID+"' "
                                                 + "AND status='ID_FREE'");
        
        // Has data
        if (!UTILS.DB.hasData(work_rs))
            throw new Exception("Invalid workplace ID - CWorkPayload.java, 68");
        
        
        // Next
        work_rs.next();
        
         // Load company data
        ResultSet com_rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM companies "
                                              + "WHERE comID='"+work_rs.getLong("comID")+"'");
        
        // Has data ?
        if (!UTILS.DB.hasData(com_rs))
            throw new Exception("Invalid workplace ID - CWorkPayload.java, 68");
        
        // Next
        com_rs.next();
        
        // Company adr
        String com_adr=com_rs.getString("adr");
        
        // Company ID
        long comID=com_rs.getLong("comID");
        
         // Find tools
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM com_prods "
                                          + "WHERE com_type='"+com_rs.getString("tip")+"' "
                                            + "AND type='ID_TOOLS' "
                                       + "ORDER BY prod ASC");
        
        // Next
        rs.next();
        
        // Tool
        String tool=rs.getString("prod");
        
        // Company has tools ?
        if (UTILS.ACC.getBalance(com_rs.getString("adr"), tool)<1)
        {
            this.suspendWorkplaces(comID, com_adr, "Company has no production tools. All workplaces have been suspended.");
            return false;
        }
        
        // Find building
        rs=UTILS.DB.executeQuery("SELECT * "
                                 + "FROM com_prods "
                                + "WHERE com_type='"+com_rs.getString("tip")+"' "
                                  + "AND type='ID_BUILDING' "
                             + "ORDER BY prod ASC");
        
        // Next
        rs.next();
        
        // Tool
        String build=rs.getString("prod");
        
        // Company has building ?
        if (UTILS.ACC.getBalance(com_rs.getString("adr"), build)<1)
        {
            this.suspendWorkplaces(comID, com_adr, "Company has no production building. All workplaces have been suspended.");
            return false;
        }
        
        // Load balance
        double com_balance=UTILS.ACC.getBalance(com_rs.getString("adr"), "CRC");
        
        // Salary
        double sal=work_rs.getDouble("wage")/60*this.minutes;
        
        // Round
        sal=Double.parseDouble(UTILS.FORMAT_8.format(sal));
        
        // Company has funds ?
        if (com_balance<sal)
        {
            this.suspendWorkplaces(comID, com_adr, "Company run out of cash. All workplaces have been suspended.");
            return false;
        }
            
        // Load product data
        ResultSet prod_rs=UTILS.DB.executeQuery("SELECT * "
                                                + "FROM tipuri_produse "
                                               + "WHERE prod='"+work_rs.getString("prod")+"'");
        
        // Next
        prod_rs.next();
        
        // Output product
        String out_prod=work_rs.getString("prod");
        
        // Output qty
        double out_qty=this.minutes/(prod_rs.getDouble("work_hours")*60);
        
        // Check raw material 1
        if (prod_rs.getDouble("prod_1_qty")>0)
           if (!this.checkProd(com_rs.getString("adr"), 
                               out_qty, 
                               prod_rs.getString("prod_1"), 
                               prod_rs.getDouble("prod_1_qty"), 
                               block))
        {
            this.suspendWorkplaces(comID, com_adr, "Company run out of raw materials. All workplaces have been suspended.");
            return false;
        }
        
        // Check raw material 2
        if (prod_rs.getDouble("prod_2_qty")>0)
           if (!this.checkProd(com_rs.getString("adr"), 
                               out_qty, 
                               prod_rs.getString("prod_2"), 
                               prod_rs.getDouble("prod_2_qty"), 
                               block))
        {
            this.suspendWorkplaces(comID, com_adr, "Company run out of raw materials. All workplaces have been suspended.");
            return false;
        }
        
        // Check raw material 3
        if (prod_rs.getDouble("prod_3_qty")>0)
           if (!this.checkProd(com_rs.getString("adr"), 
                               out_qty, 
                               prod_rs.getString("prod_3"), 
                               prod_rs.getDouble("prod_3_qty"), 
                               block))
        {
            this.suspendWorkplaces(comID, com_adr, "Company run out of raw materials. All workplaces have been suspended.");
            return false;
        }
        
        // Check raw material 4
        if (prod_rs.getDouble("prod_4_qty")>0)
           if (!this.checkProd(com_rs.getString("adr"), 
                               out_qty, 
                               prod_rs.getString("prod_4"), 
                               prod_rs.getDouble("prod_4_qty"), 
                               block))
        {
            this.suspendWorkplaces(comID, com_adr, "Company run out of raw materials. All workplaces have been suspended.");
            return false;
        }
        
        // Check raw material 5
        if (prod_rs.getDouble("prod_5_qty")>0)
           if (!this.checkProd(com_rs.getString("adr"), 
                               out_qty, 
                               prod_rs.getString("prod_5"), 
                               prod_rs.getDouble("prod_5_qty"), 
                               block))
        {
            this.suspendWorkplaces(comID, com_adr, "Company run out of raw materials. All workplaces have been suspended.");
            return false;
        }
        
        // Check raw material 6
        if (prod_rs.getDouble("prod_6_qty")>0)
           if (!this.checkProd(com_rs.getString("adr"), 
                               out_qty, 
                               prod_rs.getString("prod_6"), 
                               prod_rs.getDouble("prod_6_qty"), 
                               block))
        {
            this.suspendWorkplaces(comID, com_adr, "Company run out of raw materials. All workplaces have been suspended.");
            return false;
        }
        
        // Check raw material 7
        if (prod_rs.getDouble("prod_7_qty")>0)
           if (!this.checkProd(com_rs.getString("adr"), 
                               out_qty, 
                               prod_rs.getString("prod_7"), 
                               prod_rs.getDouble("prod_7_qty"), 
                               block))
        {
            this.suspendWorkplaces(comID, com_adr, "Company run out of raw materials. All workplaces have been suspended.");
            return false;
        }
        
        // Check raw material 8
        if (prod_rs.getDouble("prod_8_qty")>0)
           if (!this.checkProd(com_rs.getString("adr"), 
                               out_qty, 
                               prod_rs.getString("prod_8"), 
                               prod_rs.getDouble("prod_8_qty"), 
                               block))
        {
            this.suspendWorkplaces(comID, com_adr, "Company run out of raw materials. All workplaces have been suspended.");
            return false;
        }
        
        // Passed
        return true;
     }
     
     public void commit(CBlockPayload block) throws Exception
     {
         // Superclass
         super.commit(block);
         
         // Check company
         if (!this.checkCompany(block))
            return;
         
         // Update workplace
         UTILS.DB.executeUpdate("UPDATE workplaces "
                                 + "SET work_ends='"+(this.block+this.minutes)+"', "
                                     + "status='ID_OCCUPIED' "
                                + "WHERE workplaceID='"+this.workplaceID+"'");
         
         // Update work status
         UTILS.DB.executeUpdate("UPDATE adr "
                                 + "SET pol_inf=pol_inf+ "+UTILS.BASIC.round(this.minutes/5, 2)
                               + " WHERE adr='"+this.target_adr+"'");
         
         // Load workplace data
         ResultSet work_rs=UTILS.DB.executeQuery("SELECT * "
                                            + "FROM workplaces "
                                           + "WHERE workplaceID='"+this.workplaceID+"'");
         
         // Next
         work_rs.next();
         
         // Load product data
        ResultSet prod_rs=UTILS.DB.executeQuery("SELECT * "
                                                + "FROM tipuri_produse "
                                               + "WHERE prod='"+work_rs.getString("prod")+"'");
        
        // Next
        prod_rs.next();
        
        // Output product
        String out_prod=work_rs.getString("prod");
        
        // Output qty
        double out_qty=this.minutes/(prod_rs.getDouble("work_hours")*60);
        
         // Salary
        double sal=work_rs.getDouble("wage")/60*this.minutes;
        
        // Round
        sal=Double.parseDouble(UTILS.FORMAT_8.format(sal));
         
         // Insert work process
         UTILS.DB.executeUpdate("INSERT INTO work_procs "
                                      + "SET adr='"+this.target_adr+"', "
                                          + "workplaceID='"+this.workplaceID+"', "
                                          + "comID='"+work_rs.getLong("comID")+"', "
                                          + "start='"+this.block+"', "
                                          + "end='"+(this.block+this.minutes)+"', "
                                          + "output_qty='"+out_qty+"', "
                                          + "output_prod='"+out_prod+"', "
                                          + "salary='"+sal+"', "
                                          + "block='"+this.block+"'");
         
         // Set as working
         UTILS.DB.executeUpdate("UPDATE adr "
                                 + "SET work='"+this.block+this.minutes+"' "
                               + "WHERE adr='"+this.target_adr+"'");
        
        // Load company data
        ResultSet com_rs=UTILS.DB.executeQuery("SELECT com.*, "
                                                    + "tc.utilaje "
                                               + "FROM companies AS com "
                                               + "JOIN tipuri_companii AS tc ON tc.tip=com.tip "
                                              + "WHERE comID='"+work_rs.getLong("comID")+"'");
        
        // Has data ?
        if (!UTILS.DB.hasData(com_rs))
            throw new Exception("Invalid workplace ID - CWorkPayload.java, 68");
        
        // Next
        com_rs.next();
        
        // Company adr
        String com_adr=com_rs.getString("adr");
        
        // Tool
        String tool=com_rs.getString("utilaje");
         
         // Degrade tools
        UTILS.DB.executeUpdate("UPDATE stocuri "
                                + "SET used=used+"+out_qty+" "
                              + "WHERE adr='"+com_adr+"' "
                                + "AND tip='"+tool+"'");
         
         // Clear trans
         UTILS.ACC.clearTrans(this.hash, "ID_ALL", this.block);
     }
}
