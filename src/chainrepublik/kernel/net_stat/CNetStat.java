// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.kernel.net_stat;
import chainrepublik.kernel.net_stat.tables.CEscrowed;
import chainrepublik.kernel.net_stat.tables.CDelVotes;
import chainrepublik.kernel.net_stat.tables.CAssetsOwners;
import chainrepublik.kernel.net_stat.tables.CDelegates;
import chainrepublik.kernel.net_stat.tables.CAssetsMkts;
import chainrepublik.kernel.net_stat.tables.CVotes;
import chainrepublik.kernel.net_stat.tables.CAssetsMktsPos;
import chainrepublik.kernel.net_stat.tables.CComments;
import chainrepublik.kernel.net_stat.tables.CAssets;
import chainrepublik.kernel.net_stat.tables.CTweetsFollow;
import chainrepublik.kernel.net_stat.tables.CTweets;
import chainrepublik.kernel.net_stat.tables.CAdrAttr;
import java.math.BigInteger;
import chainrepublik.kernel.net_stat.tables.CAds;
import chainrepublik.kernel.net_stat.tables.CAdr;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Timer;
import chainrepublik.kernel.UTILS;

public class CNetStat 
{
    // Tables
    public ArrayList<String> tables=new ArrayList<String>();
    
    // Last block
    public long last_block;
    
    // Last hash
    public String last_block_hash;
    
    // Last tstamp
    public long last_block_tstamp;
    
    // Difficulty
    public BigInteger net_dif;
    
    // SQL log
    String sql_log_status;
   
    // Block confirmation minimum baalance
    public double block_conf_min_balance=1;
    
    // Network time
    public long net_time;
    
    // Process blocks ?
    public boolean consensus=true;
    
    // Timer
    Timer timer;
    
    // Last hash
    public String actual_block_hash="";
    
    // Last tstamp
    public long actual_block_no=0;
    
    // Delegate
    public String delegate="";
    
    // No checkpoints
    public boolean reorg=false;
    
    public CNetStat() throws Exception
    {
        // Online
        UTILS.DB.executeUpdate("UPDATE web_sys_data set uptime='"+UTILS.BASIC.tstamp()+"'");
        
        // Load data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * FROM net_stat");
           
        // Next
        rs.next();
        
           
        // Last block
        this.last_block=rs.getLong("last_block");
           
        // Last hash
        this.last_block_hash=rs.getString("last_block_hash");
           
        // Difficulty
        this.net_dif=new BigInteger(rs.getString("net_dif"), 16);
           
        // SQL log status
        this.sql_log_status=rs.getString("sql_log_status");
           
        // Confirm min balance
        this.block_conf_min_balance=rs.getDouble("block_confirm_min_balance");
       
        // Network time
        this.net_time=UTILS.BASIC.tstamp();
    }
    
     
     public void setDifficulty(BigInteger dif)
     {
         // Set
        this.net_dif=dif;
         
        // Debug
        System.out.println("New difficulty : "+UTILS.BASIC.formatDif(dif.toString(16)));
     }
     
     
}
