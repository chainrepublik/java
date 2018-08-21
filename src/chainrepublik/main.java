package chainrepublik;
import chainrepublik.kernel.*;
import chainrepublik.kernel.net_stat.CNetStat;
import chainrepublik.kernel.net_stat.CTables;
import chainrepublik.kernel.temp.CDump;
import chainrepublik.kernel.temp.CStressTest;
import chainrepublik.network.CCurBlock;
import chainrepublik.network.CNetwork;
import chainrepublik.network.packets.adr.CTravelPacket;
import chainrepublik.network.packets.assets.CIssueAssetPacket;
import chainrepublik.network.packets.assets.reg_mkts.CCloseRegMarketPosPacket;
import chainrepublik.network.packets.assets.reg_mkts.CNewRegMarketPacket;
import chainrepublik.network.packets.companies.CNewCompanyPacket;
import chainrepublik.network.packets.companies.CNewWorkplacePacket;
import chainrepublik.network.packets.companies.CUpdateCompanyPacket;
import chainrepublik.network.packets.companies.CUpdateWorkplacePacket;
import chainrepublik.network.packets.companies.CWorkPacket;
import chainrepublik.network.packets.companies.CWthFundsPacket;
import chainrepublik.network.packets.politics.congress.CEndorsePacket;
import chainrepublik.network.packets.politics.congress.CNewLawPacket;
import chainrepublik.network.packets.politics.congress.CVoteLawPacket;
import chainrepublik.network.packets.politics.orgs.CJoinOrgPacket;
import chainrepublik.network.packets.politics.orgs.CLeaveOrgPacket;
import chainrepublik.network.packets.politics.orgs.CNewOrgPropPacket;
import chainrepublik.network.packets.politics.orgs.CVoteOrgPropPacket;
import chainrepublik.network.packets.portofolio.CConsumeItemPacket;
import chainrepublik.network.packets.portofolio.CDonateItemPacket;
import chainrepublik.network.packets.portofolio.CSetRentPricePacket;
import chainrepublik.network.packets.portofolio.CUseItemPacket;
import chainrepublik.network.packets.press.*;
import chainrepublik.network.packets.war.CFightPacket;
import java.security.Security;
import java.sql.ResultSet;



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
        //dump.polParties("MK", 4);
        
        
       
        UTILS.MINER_UTILS=new CCPUMinerUtils();
        
        // Status
        UTILS.STATUS=new CStatus();
        
        
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
        
        // Laws
        UTILS.LAWS=new CLaws();
        
        // Constants
        UTILS.CONST=new CConst();
        
        // Late operations
        UTILS.ARGS.lateOp();
       
        // Sync
        UTILS.SYNC=new CSync();
        
        // Stress test
        UTILS.STRESS=new CStressTest();
        UTILS.STRESS.start();
        
        if (UTILS.SETTINGS.seed_mode)
            UTILS.STATUS.setEngineStatus("ID_ONLINE");
        else
            UTILS.SYNC.start();
        
        System.out.println("Wallet is up an running...");
        
        UTILS.CBLOCK.startMiners(1);
        
    }
    
    
}
