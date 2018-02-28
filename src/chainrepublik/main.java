package chainrepublik;

import chainrepublik.agents.VM.CVM;
import chainrepublik.kernel.*;
import chainrepublik.kernel.net_stat.CNetStat;
import chainrepublik.kernel.net_stat.CTables;
import chainrepublik.kernel.temp.CDump;
import chainrepublik.network.CCurBlock;
import chainrepublik.network.CNetwork;
import chainrepublik.network.packets.misc.CGiftPacket;
import java.io.File;
import java.security.Security;
import org.apache.commons.io.FileUtils;



public class main 
{
    public static void main(String[] args) throws Exception
    {
        // Security provider
	Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        
        // Settings
        CSettings settings=new CSettings();
        UTILS.SETTINGS=settings;
        
        // Args
        UTILS.ARGS=new CArgs();
        UTILS.ARGS.load(args);
        
        // DB
        CDB db=new CDB();
        UTILS.DB=db;
        UTILS.DB.loadFileLoc();
        
        // Utils
        CUtils utils=new CUtils();
        UTILS.BASIC=utils;
        
        // Accounting
        CAcc acc=new CAcc();
        UTILS.ACC=acc;
        
         // Serializer
        UTILS.SERIAL=new CSerializer();
        
        // AES
        UTILS.AES=new CAES();
        
        // Bootstrap sequence
        UTILS.TABLES=new CTables();
        UTILS.TABLES.createTables();
        
        // Dump
        CDump dump=new CDump();
        //dump.test();
        //dump.populateAssetsMktsPos();
        //dump.populateAssetsMkts();
      
        UTILS.MINER_UTILS=new CCPUMinerUtils();
        
        // Status
        UTILS.STATUS=new CStatus();
        
        // ECC
        UTILS.ECC=new CECC();
        
        // Net stat
        UTILS.NET_STAT=new CNetStat();
        
        // Delegates
        UTILS.DELEGATES=new CDelegates();
        
        // Wallet
        UTILS.WALLET=new CWallet();
       
        // Network
        UTILS.NETWORK=new CNetwork();
        UTILS.NETWORK.start();
        
        // Current block
        CCurBlock block=new CCurBlock();
        UTILS.CBLOCK=block;
        
        // Web operations
        UTILS.WEB_OPS=new CWebOps(); 
        
        // Binary Options Engine
        UTILS.CRONS=new CCrons();
    
        // Rewards
        UTILS.REWARD=new CRewards();
        
        // Late operations
        UTILS.ARGS.lateOp();
        
        // Sync
        UTILS.SYNC=new CSync();
        
        if (UTILS.SETTINGS.seed_mode)
                UTILS.STATUS.setEngineStatus("ID_ONLINE");
        else
                UTILS.SYNC.start();
        
        
        
        System.out.println("Wallet is up an running...");
        UTILS.CBLOCK.startMiners(1);
        //UTILS.WALLET.list();
        
        // VM
        //File file = new File("./src/chainrepublik/agents/code.txt"); 
        //String string = FileUtils.readFileToString(file);
        //UTILS.VM=new CVM(343242);
        
        /*CGiftPacket packet=new CGiftPacket("MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEEpn9tcO9qb55dKKTCroSy6fa8mhyhChMYLdJer+WYnVR8Is9l1864vi9Z9eVXTkk3xo1ARNfY+fM0DWI0Wo7+g==",
                                           "MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEEpn9tcO9qb55dKKTCroSy6fa8mhyhChMYLdJer+WYnVR8Is9l1864vi9Z9eVXTkk3xo1ARNfY+fM0DWI0Wo7+g==", 
                                           "MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEl2O6B0Mx9CBYPXCeu+12qRElGE8PnvRIltHM8RYdLJNIUBi822woawtdw4l1xCsR1UJkHFHWNISrnudXt4ncqA==");
        UTILS.NETWORK.broadcast(packet);*/
        
        
    }
    
    
}
