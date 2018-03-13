package chainrepublik.kernel;

import java.sql.ResultSet;

public class CStatus 
{
    // Engine status
    public String status;
    
    // Version
    public String version="0.1.0";
    
    // New accounts reward address
    public String node_adr;
    
    // Mining address
    public String mining_adr;
    
    // New accounts reward
    public double new_acc_reward;
    
    public CStatus() throws Exception
    {
        load(true);
    }
    
    public void setEngineStatus(String status) throws Exception
    {
        // Print
        System.out.println("New engine status : "+status);
        
        // Status
        this.status=status;
        
        // Db
        UTILS.DB.executeUpdate("UPDATE web_sys_data "
                                + "SET status='"+status+"'");
    }
    
    public void load(boolean force_load) throws Exception
    {
        // Every 10 seconds
        if (!force_load)
          if (UTILS.BASIC.tstamp()%10!=0)
            return;
        
        // Load 
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM web_sys_data");
        
        // Next
        rs.next();
        
        // Status
        this.status=rs.getString("status");
        
        // New accounts reward address
        this.node_adr=rs.getString("node_adr");
        
        // Mining address
        this.mining_adr=rs.getString("mining_adr");
    
        // New accounts reward
        this.new_acc_reward=rs.getDouble("new_acc_reward");
        
        // Version
        this.version=rs.getString("version");
    }
}
