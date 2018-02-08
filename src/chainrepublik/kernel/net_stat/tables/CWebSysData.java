package chainrepublik.kernel.net_stat.tables;

import chainrepublik.kernel.CAddress;
import chainrepublik.kernel.CWallet;
import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CWebSysData extends CTable
{
    public CWebSysData()
    {
        super("web_sys_data");
    }
    
    public void create() throws Exception
    {
        if (!this.tableExist("web_sys_data"))
        {
            // Status
            System.out.print("Creating table "+this.name+"...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE web_sys_data(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
                                                             +"status VARCHAR(50) NOT NULL DEFAULT '', "
                                                             +"last_ping BIGINT NOT NULL DEFAULT '0', "
                                                             +"free_memory BIGINT NOT NULL DEFAULT '0', "
                                                             +"total_memory BIGINT NOT NULL DEFAULT '0', "
                                                             +"max_memory BIGINT NOT NULL DEFAULT '0', "
                                                             +"procs BIGINT NOT NULL DEFAULT '0', "
                                                             +"threads_no BIGINT NOT NULL DEFAULT '0', "
                                                             +"uptime BIGINT NOT NULL DEFAULT '0', "
                                                             +"mining BIGINT NOT NULL DEFAULT '0', "
                                                             +"hashing_power BIGINT NOT NULL DEFAULT '0', "
                                                             +"mining_threads BIGINT NOT NULL DEFAULT '0', "
                                                             +"mining_adr VARCHAR(250) NOT NULL DEFAULT '', "
                                                             +"node_adr VARCHAR(250) NOT NULL DEFAULT '', "
                                                             +"new_acc_reward FLOAT(9,4) NOT NULL DEFAULT '0', "
                                                             +"root_whitelist_ip VARCHAR(5000) NOT NULL DEFAULT '', "
                                                             +"cpu_1_power FLOAT(20, 8) NOT NULL DEFAULT '0', "
                                                             +"cpu_2_power FLOAT(20, 8) NOT NULL DEFAULT '0', "
                                                             +"cpu_3_power FLOAT(20, 8) NOT NULL DEFAULT '0', "
                                                             +"cpu_4_power FLOAT(20, 8) NOT NULL DEFAULT '0', "
                                                             +"cpu_5_power FLOAT(20, 8) NOT NULL DEFAULT '0', "
                                                             +"cpu_6_power FLOAT(20, 8) NOT NULL DEFAULT '0', "
                                                             +"cpu_7_power FLOAT(20, 8) NOT NULL DEFAULT '0', "
                                                             +"cpu_8_power FLOAT(20, 8) NOT NULL DEFAULT '0', "
                                                             +"cpu_9_power FLOAT(20, 8) NOT NULL DEFAULT '0', "
                                                             +"cpu_10_power FLOAT(20, 8) NOT NULL DEFAULT '0', "
                                                             +"cpu_11_power FLOAT(20, 8) NOT NULL DEFAULT '0', "
                                                             +"cpu_12_power FLOAT(20, 8) NOT NULL DEFAULT '0', "
                                                             +"cpu_13_power FLOAT(20, 8) NOT NULL DEFAULT '0', "
                                                             +"cpu_14_power FLOAT(20, 8) NOT NULL DEFAULT '0', "
                                                             +"cpu_15_power FLOAT(20, 8) NOT NULL DEFAULT '0', "
                                                             +"cpu_16_power FLOAT(20, 8) NOT NULL DEFAULT '0', "
                                                             +"cpu_17_power FLOAT(20, 8) NOT NULL DEFAULT '0', "
                                                             +"cpu_18_power FLOAT(20, 8) NOT NULL DEFAULT '0', "
                                                             +"cpu_19_power FLOAT(20, 8) NOT NULL DEFAULT '0', "
                                                             +"cpu_20_power FLOAT(20, 8) NOT NULL DEFAULT '0', "
                                                             +"cpu_21_power FLOAT(20, 8) NOT NULL DEFAULT '0', "
                                                             +"cpu_22_power FLOAT(20, 8) NOT NULL DEFAULT '0', "
                                                             +"cpu_23_power FLOAT(20, 8) NOT NULL DEFAULT '0', "
                                                             +"cpu_24_power FLOAT(20, 8) NOT NULL DEFAULT '0', "
                                                             +"version VARCHAR(20) NOT NULL DEFAULT '0.0.1', "
                                                             +"engine_status VARCHAR(20) NOT NULL DEFAULT 'ID_ONLINE', "
                                                             +"coin_price DOUBLE(9,4) NOT NULL DEFAULT 0)");
            // Populate
            this.populate();
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void populate() throws Exception
    {
        // Wallet
        CWallet wallet=new CWallet();
        
        // Generate node address
        String node_adr=wallet.newAddress(1, "Official node address");
        
        // Generate mining address
        String mining_adr=wallet.newAddress(1, "Mining address");
        
        // Update
        UTILS.DB.executeUpdate("INSERT INTO web_sys_data "
                                     + "SET status='ID_ONLINE', "
                                          + "node_adr='"+node_adr+"', "
                                          + "mining_adr='"+mining_adr+"', "
                                          + "coin_price=1");
    }
}
