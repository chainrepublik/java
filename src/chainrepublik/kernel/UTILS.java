// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.kernel;

import chainrepublik.agents.VM.CVM;
import chainrepublik.kernel.net_stat.CNetStat;
import chainrepublik.kernel.net_stat.CTables;
import chainrepublik.network.CCurBlock;
import chainrepublik.network.CNetwork;
import java.text.DecimalFormat;

public class UTILS 
{
    // Write directory
    public static String WRITEDIR;    
    
    // Database
    public static CDB DB;
    
    // Settings
    public static CSettings SETTINGS;
    
    // Delegates
    public static CDelegates DELEGATES;
    
    // Utils
    public static CUtils BASIC;
    
    // Wallet
    public static CWallet WALLET;
    
   // AES
    public static CAES AES;
    
    
    // Network
    public static CNetwork NETWORK;
    
    // Serializer
    public static CSerializer SERIAL;
    
    // Status
    public static CStatus STATUS;
    
    // Current block
    public static CCurBlock CBLOCK;
    
    
    // Net stat
    public static CNetStat NET_STAT;
    
    // Log queries
    public static boolean LOG_QUERIES=false;
    
    // Binary options engine
    public static CCrons CRONS;
    
    // Miner utils
    public static CCPUMinerUtils MINER_UTILS;
    
    // Formatter
    public static DecimalFormat FORMAT_2 = new DecimalFormat("#.##");
    public static DecimalFormat FORMAT_4 = new DecimalFormat("#.####");
    public static DecimalFormat FORMAT_8 = new DecimalFormat("#.########");
    
    // Root
    public static ThreadGroup ROOT;
    
    // Web ops
    public static CWebOps WEB_OPS;
    
    // Sync
    public static CSync SYNC;
    
    
    // Accounting
    public static CAcc ACC;
    
    // Arguments
    public static CArgs ARGS;
    
    // Reward
    public static CRewards REWARD;
    
    // Runtime
    public static Runtime runtime = Runtime.getRuntime();
    
    // VM
    public static CVM VM;
    
    // Tables
    public static CTables TABLES;
    
    // Laws
    public static CLaws LAWS;
}
