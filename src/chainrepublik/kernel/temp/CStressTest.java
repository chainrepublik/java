package chainrepublik.kernel.temp;

import chainrepublik.kernel.CAddress;
import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.adr.CAddAttrPacket;
import chainrepublik.network.packets.adr.CAdrChgCitPacket;
import chainrepublik.network.packets.adr.CAdrRegisterPacket;
import chainrepublik.network.packets.adr.CTransferAdrPacket;
import chainrepublik.network.packets.adr.CTravelPacket;
import chainrepublik.network.packets.adr.CUpdateProfilePacket;
import chainrepublik.network.packets.ads.CNewAdPacket;
import chainrepublik.network.packets.assets.CIssueAssetPacket;
import chainrepublik.network.packets.assets.reg_mkts.CCloseRegMarketPosPacket;
import chainrepublik.network.packets.assets.reg_mkts.CNewRegMarketPacket;
import chainrepublik.network.packets.assets.reg_mkts.CNewRegMarketPosPacket;
import chainrepublik.network.packets.companies.CWorkPacket;
import chainrepublik.network.packets.exchange.CNewExOffertPacket;
import chainrepublik.network.packets.exchange.CRemoveExOffertPacket;
import chainrepublik.network.packets.market.CRentPacket;
import chainrepublik.network.packets.mes.CMesPacket;
import chainrepublik.network.packets.misc.CDelVotePacket;
import chainrepublik.network.packets.misc.CRenewPacket;
import chainrepublik.network.packets.politics.congress.CEndorsePacket;
import chainrepublik.network.packets.politics.orgs.CJoinOrgPacket;
import chainrepublik.network.packets.politics.orgs.CLeaveOrgPacket;
import chainrepublik.network.packets.politics.orgs.CNewOrgPropPacket;
import chainrepublik.network.packets.politics.orgs.CVoteOrgPropPacket;
import chainrepublik.network.packets.portofolio.CConsumeItemPacket;
import chainrepublik.network.packets.portofolio.CDonateItemPacket;
import chainrepublik.network.packets.press.CCommentPacket;
import chainrepublik.network.packets.press.CFollowPacket;
import chainrepublik.network.packets.press.CNewArticlePacket;
import chainrepublik.network.packets.press.CUnfollowPacket;
import chainrepublik.network.packets.press.CVotePacket;
import chainrepublik.network.packets.trans.CEscrowedTransSignPacket;
import chainrepublik.network.packets.trans.CTransPacket;
import java.sql.ResultSet;
import java.util.Random;
import java.util.TimerTask;

public class CStressTest 
{
    long t=0;
    boolean started=false;
    
    public CStressTest()
    {
        
    }
    
    public long getRandArt() throws Exception
    {
       ResultSet rs=UTILS.DB.executeQuery("SELECT tw.* "
                                          + "FROM tweets AS tw "
                                          + "JOIN web_users AS us ON us.adr=tw.adr "
                                         + "WHERE tw.block>"+(UTILS.NET_STAT.last_block-1440)
                                      + " ORDER BY RAND()");
       
        rs.next();
        return rs.getLong("tweetID");  
    }
    
    public long getRandComment() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM comments AS com "
                                          + "JOIN web_users AS us ON us.adr=com.adr "
                                         + "WHERE com.block>"+(UTILS.NET_STAT.last_block-1440)
                                      + " ORDER BY RAND()");
        rs.next();
        return rs.getLong("comID");  
    }
    
    public long getRandLaw() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM laws "
                                      + "ORDER BY RAND()");
        rs.next();
        return rs.getLong("lawID");  
    }
    
    public String getRandEscrowedHash() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM escrowed "
                                      + "ORDER BY RAND()");
        rs.next();
        return rs.getString("trans_hash");   
    }
    
    public long getRandStoc() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM stocuri "
                                      + "ORDER BY RAND()");
        rs.next();
        return rs.getLong("stocID");   
    }
    
    public long getRandOrg() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM orgs "
                                      + "ORDER BY RAND()");
        rs.next();
        return rs.getLong("orgID");   
    }
    
    public long getRandOrgPropID() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM orgs_props "
                                      + "ORDER BY RAND()");
        rs.next();
        return rs.getLong("propID");   
    }
    
    public String getRandLic() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM tipuri_licence "
                                      + "ORDER BY RAND()");
        rs.next();
        return rs.getString("tip");   
    }
    
    public long getRandCom() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM companies "
                                      + "ORDER BY RAND()");
        rs.next();
        return rs.getLong("comID");   
    }
    
    public long getRandWorkplace() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM workplaces "
                                      + "ORDER BY RAND()");
        rs.next();
        return rs.getLong("workplaceID");   
    }
    
    public long getRandEx() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM exchange "
                                      + "ORDER BY RAND()");
        rs.next();
        return rs.getLong("exID");   
    }
    
    public boolean getRandBool()
    {
        Random r=new Random();
        return r.nextBoolean(); 
    }
    
    
    
    public String getRandCou() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM countries "
                                       + "ORDER BY RAND()");
        rs.next();
        return rs.getString("code");
    }
    
    public String getRandPolParty() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM orgs WHERE type='ID_POL_PARTY' "
                                       + "ORDER BY RAND()");
        rs.next();
        return rs.getString("orgID");
    }
    
    public String getRandMilUnit() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM orgs "
                                          + "WHERE type='ID_MIL_UNIT' "
                                       + "ORDER BY RAND()");
        rs.next();
        return rs.getString("orgID");
    }
    
    public long getRandMkt() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM assets_mkts "
                                       + "ORDER BY RAND()");
        rs.next();
        return rs.getLong("mktID");
    }
    
    public long getRandDays() throws Exception
    {
        Random d=new Random();
        return Math.round(d.nextDouble()*1000);
    }
    
    public String getRandAdr() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM my_adr "
                                       + "ORDER BY RAND()");
        rs.next();
        return rs.getString("adr");
    }
    
    public String getRandGenAdr() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM adr "
                                       + "ORDER BY RAND()");
        rs.next();
        return rs.getString("adr");
    }
    
    public String getRandAsset() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM assets "
                                       + "ORDER BY RAND()");
        rs.next();
        return rs.getString("symbol");
    }
    
    public long getRandAssetID() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM assets "
                                       + "ORDER BY RAND()");
        rs.next();
        return rs.getLong("assetID");
    }
    
    public double getRandAmount() throws Exception
    {
        Random d=new Random();
        return UTILS.BASIC.round(d.nextDouble(), 4);
    }
    
    public long getRandMktPos() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM assets_mkts_pos "
                                       + "ORDER BY RAND()");
        rs.next();
        return rs.getLong("posID");
    }
    
    public void testAddAttr() throws Exception
    {
        // Rand address
        String adr=this.getRandAdr();
        
        // Rand asset
        String asset=this.getRandAsset();
        
        // Rand days
        long days=this.getRandDays();
        
        // Packet
        CAddAttrPacket packet=new CAddAttrPacket(adr, 
		                                 adr, 
		                                 "ID_TRUST_ASSET",
                                                 asset, 
                                                 "", "",
                                                  0, 0, 0, 0, 0, 0, days);
        
        // Send
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void testChgcit() throws Exception
    {
        // Rand address
        String adr=this.getRandAdr();
        
        // Country
        String cou=this.getRandCou(); 
        
        // Packet
        CAdrChgCitPacket packet=new CAdrChgCitPacket(adr,
                                                     adr,
                                                     cou);
        
        // Send
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void testAdrReg() throws Exception
    {
        // Adr
        CAddress a=new CAddress();
        a.generate();
        String adr=a.getPublic();
        
        // Add to wallet
        UTILS.WALLET.add(a);
        
        // Country
        String cou=this.getRandCou();
        
        // Name
        String name=UTILS.BASIC.randString(10);
        
        // Description
        String desc="Description for "+name;
        
        // Ref_adr
        String ref_adr=this.getRandAdr();
        
        // Node adr
        String node_adr=this.getRandAdr();
        
        // Fee adr
        String fee_adr=this.getRandAdr();
        
        // Days
        long days=this.getRandDays();
        
        CAdrRegisterPacket packet=new CAdrRegisterPacket(fee_adr,
                                                         adr,
                                                         cou,
                                                         name, 
		                                         desc,
                                                         ref_adr, 
                                                         node_adr, 
                                                         "",
                                                         days);
        
        // Send 
        UTILS.NETWORK.broadcast(packet);
    }
    
    
    
    public void testTransferAdr() throws Exception
    {
        // Adr
        String adr=this.getRandAdr();
        
        // To adr
        CAddress a=new CAddress();
        a.generate();
        String to_adr=a.getPublic();
        
        // Packet
        CTransferAdrPacket packet=new CTransferAdrPacket(adr, 
		                                      adr, 
		                                      to_adr);
        
        // Send 
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void testTravel() throws Exception
    {
        // Adr
        String adr=this.getRandAdr();
        
        // Country
        String to_cou=this.getRandCou();
        
        // Packet
        CTravelPacket packet=new CTravelPacket(adr,
                                               adr,
                                               to_cou);
        
        // Send 
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void testUpdateProfile() throws Exception
    {
        // Adr
        String adr=this.getRandAdr();
        
        // Packet
        CUpdateProfilePacket packet=new CUpdateProfilePacket(adr, 
		                                             adr, 
		                                             "",
                                                             "Profile updated");
        
        // Send 
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void testAds() throws Exception
    {
        // Adr
        String adr=this.getRandAdr();
        
        // Rand title
        String title=UTILS.BASIC.randString(10);
        
        // Rand mes
        String mes=UTILS.BASIC.randString(55);
         
        // Rand link
         String link="http://www."+UTILS.BASIC.randString(10)+".com";
         
        // Rand bid
        double bid=this.getRandAmount();
        
        // Rand hours
        long hours=this.getRandDays();
        
        CNewAdPacket packet=new CNewAdPacket(adr, 
		                             adr, 
		                             title, 
		                             mes, 
		                             link,
                                             hours, 
		                             bid);
        
        // Send 
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void testIssueAsset() throws Exception
    {
        // Adr
        String adr=this.getRandAdr();
        
        // Name
        String name=UTILS.BASIC.randString(25);
                
        // Desc
        String desc=UTILS.BASIC.randString(25);
        
        // How buy
        String how_buy=UTILS.BASIC.randString(25);
        
        // How sell
        String how_sell=UTILS.BASIC.randString(25);
        
        // Website
        String website="http://www."+UTILS.BASIC.randString(25)+".com";
        
        // Symbol
        String symbol=UTILS.BASIC.randString(6);
        
        // Qty
        long qty=this.getRandDays()*100;
        
        // Trans fee
        double trans_fee=UTILS.BASIC.round(this.getRandAmount(), 4);
        
        // Trans fee adr
        String fee_adr=this.getRandAdr();
        
        // Days
        long days=this.getRandDays();
        
        CIssueAssetPacket packet=new CIssueAssetPacket(adr,
		                                       adr,
                                                       name,
                                                       desc,
                                                       how_buy,
                                                       how_sell,
                                                       website,
                                                       "",
                                                       symbol,
                                                       qty,
                                                       trans_fee,
                                                       fee_adr,
                                                       days);
        
        // Send 
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void testNewAssetMkt() throws Exception
    {
        // Adr
        String adr=this.getRandAdr();
        
        // Asset symbol
        String asset=this.getRandAsset();
        
        // Asset or coin
        String cur_symbol;
        Random r=new Random();
        if (r.nextBoolean())
            cur_symbol=this.getRandAsset();
        else
            cur_symbol="CRC";
        
        // Name
        String name=UTILS.BASIC.randString(25);
                
        // Desc
        String desc=UTILS.BASIC.randString(25);
        
        // Decimals
        long decimals=Math.round(r.nextInt()*8);
        
        // Days
        long days=this.getRandDays();
        
        // New packet
        CNewRegMarketPacket packet=new CNewRegMarketPacket(adr,
                                                           adr, 
                                                           asset,
                                                           cur_symbol,
                                                           name,
                                                           desc, 
                                                           decimals,
                                                           days);
                                
        // Send 
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void testNewMktPos() throws Exception
    {
        // Adr
        String adr=this.getRandAdr();
        
        // Mkt ID
        long mktID=this.getRandMkt();
        
        // Tip
        String tip;
        if (this.getRandBool())
            tip="ID_BUY";
        else
            tip="ID_SELL";
        
        // Price
        double price=this.getRandAmount();
        
        // Qty
        double qty=this.getRandAmount()*5;
        
        // Days
        long days=this.getRandDays();
        
        CNewRegMarketPosPacket packet=new CNewRegMarketPosPacket(adr,
                                                                 adr,
                                                                 mktID,
                                                                 tip,
                                                                 price,
                                                                 qty,
                                                                 days);
        
        // Send 
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void testCloseMktPos() throws Exception
    {
        // Adr
        String adr=this.getRandAdr();
        
        // Mkt ID
        long posID=this.getRandMktPos();
        
        // Packet
        CCloseRegMarketPosPacket packet=new CCloseRegMarketPosPacket(adr,
                                                                     adr,
                                                                     posID);
        
        // Send 
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void testNewExOffer() throws Exception
    {
        // Adr
        String adr=this.getRandAdr();
        
        // Side
        String side="ID_BUY";
        if (this.getRandBool())
            side="ID_BUY";
        else
            side="ID_SELL";
        
        // Price type
        String price_type="ID_FIXED";
        if (this.getRandBool())
            side="ID_FIXED";
        else
            side="ID_VARIABLE";
        
        // Margin
        long margin=Math.round(this.getRandAmount()*10);
        
        // Price
        double price=this.getRandAmount()*5;
        
        // Min
        double min=this.getRandAmount();
        
        // Max
        double max=min*10;
        
        // Method
        String method="";
        int m=Integer.parseInt(String.valueOf(Math.round(this.getRandAmount()*10)));
        
        switch (m)
        {
            case 1 : method="ID_PAYPAL"; break;
            case 2 : method="ID_PAXUM"; break;
            case 3 : method="ID_CARD"; break;
            case 4 : method="ID_WESTERN"; break;
            case 5 : method="ID_ERR"; break;
        }
        
        // Details
        String details=UTILS.BASIC.randString(20);
        
        // Pay info
        String pay_info=UTILS.BASIC.randString(20);
        
        // Contact
        String contact=UTILS.BASIC.randString(20);
        
        // Days
        long days=this.getRandDays();
        
        // Exchange offer
        CNewExOffertPacket packet=new CNewExOffertPacket(adr, 
		                                         adr, 
		                                         side,
                                                         price_type,
                                                         margin,
                                                         price,
                                                         min,
                                                         max,
                                                         method,
                                                         details,
                                                         pay_info,
                                                         contact,
                                                         days);
        
        // Send 
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void testRemoveEx() throws Exception
    {
        // Adr
        String adr=this.getRandAdr();
        
        // Ex ID
        long exID=this.getRandEx();
        
        // Packet
        CRemoveExOffertPacket packet=new CRemoveExOffertPacket(adr, 
		                                               adr, 
		                                               exID);
                               
        // Send 
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void testRent() throws Exception
    {
        // Adr
        String adr=this.getRandAdr();
        
        // Ex ID
        long exID=this.getRandEx();
        
        // Item ID
        long itemID=this.getRandStoc();
        
        // Days
        long days=this.getRandDays();
        
        // Packet
        CRentPacket packet=new CRentPacket(adr,
                                           adr,
                                           itemID,
                                           days);
        
        // Send 
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void testMes() throws Exception
    {
        // Sender
        String sender=this.getRandAdr();
    
        // Rec
        String rec=this.getRandAdr();
        
        // Subject
        String subject=UTILS.BASIC.randString(20);
        
        // Mes
        String mes=UTILS.BASIC.randString(20);
        
        CMesPacket packet=new CMesPacket(sender, 
		                         sender, 
		                         rec, 
		                         subject, 
		                         mes);
        
        // Send 
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void testDelVote() throws Exception
    {
        // Adr
        String adr=this.getRandAdr();
        
        // Delegate
        String delegate=this.getRandAdr();
        
        // Type
        String type="";
        if (this.getRandBool())
            type="ID_UP";
        else
            type="ID_DOWN";
        
        CDelVotePacket packet=new CDelVotePacket(adr,
                                                 adr, 
                                                 delegate,
                                                 type);
        
        // Send 
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void testRenew() throws Exception
    {
        // Sender
        String adr=this.getRandAdr();
        
        // Target type
        String target_type="";
        String targetID="";
        int tt=Integer.valueOf(String.valueOf(Math.round(this.getRandAmount()*5)));
        
        switch (tt)
        {
            case 1 : target_type="ID_ADR"; 
                     targetID=this.getRandAdr();
                     break;
                     
            case 2 : target_type="ID_ASSET"; 
                     targetID=String.valueOf(this.getRandAssetID());
                     break;
                     
            case 3 : target_type="ID_COM"; 
                     targetID=String.valueOf(this.getRandCom());
                     break;
                     
            case 4 : target_type="ID_WORKPLACE"; 
                     targetID=String.valueOf(this.getRandWorkplace());
                     break;
                     
            case 5 : target_type="ID_LIC";
                     targetID=String.valueOf(this.getRandStoc());
                     break;
        }
        
        // Days
        long days=this.getRandDays();
        
        // Packet
        CRenewPacket packet=new CRenewPacket(adr, 
                                             adr, 
                                             target_type, 
                                             Long.parseLong(targetID), 
                                             days);
                        
        // Send 
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void testEndorse() throws Exception
    {
        // Sender
        String adr=this.getRandAdr();
        
        // Endorsed
        String endorsed=this.getRandAdr();
        
        // Type
        String type;
        if (this.getRandBool())
            type="ID_UP";
        else
            type="ID_DOWN";
        
        CEndorsePacket packet=new CEndorsePacket(adr,
                                                 adr,
                                                 endorsed,
                                                 type);
        
        // Send 
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void testJoinOrg() throws Exception
    {
        // Sender
        String adr=this.getRandAdr();
        
        // OrgID
        long orgID=this.getRandOrg();
        
        // Packet
        CJoinOrgPacket packet=new CJoinOrgPacket(adr,
                                                 adr,
                                                 orgID);
        
        // Send 
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void testLeaveOrg() throws Exception
    {
        // Sender
        String adr=this.getRandAdr();
        
        // OrgID
        long orgID=this.getRandOrg();
        
        CLeaveOrgPacket packet=new CLeaveOrgPacket(adr,
                                                   adr,
                                                   orgID);
                             
        // Send 
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void testNewOrgProp() throws Exception
    {
        // Sender
        String adr=this.getRandAdr();
        
        // Org ID
        long orgID=this.getRandOrg();
        
        // Par 1
        String par_1=this.getRandAdr();
        
        // Par 2
        double par_2=this.getRandAmount();
        
        // Expl
        String expl=UTILS.BASIC.randString(25);
        
        CNewOrgPropPacket packet=new CNewOrgPropPacket(adr,
                                                       adr, 
                                                       orgID,
                                                       "ID_DONATE", 
                                                       par_1,
                                                       String.valueOf(par_2),
                                                       "",
                                                       "",
                                                       "",
                                                       expl);
        
        // Send 
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void testVoteOrgProp() throws Exception
    {
        // Sender
        String adr=this.getRandAdr();
        
        // Org ID
        long propID=this.getRandOrgPropID();
        
        // Vote
        String vote="";
        if (this.getRandBool())
            vote="ID_UP";
        else
            vote="ID_DOWN";
        
        CVoteOrgPropPacket packet=new CVoteOrgPropPacket(adr,
                                                         adr, 
                                                         propID,
                                                         vote);
        
        // Send 
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void testTrans() throws Exception
    {
        // Sender
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM adr "
                                          + "WHERE balance>10 "
                                       + "ORDER BY RAND()");
        rs.next();
        String sender=rs.getString("adr");
        
        // Rec
        String rec=this.getRandGenAdr();
        
        // Amount
        double amount=this.getRandAmount();
        
        // Cur
        String cur="";
        if (this.getRandBool())
           cur="CRC";
        else
           cur=this.getRandAsset();
        
        // Mes
        String mes=UTILS.BASIC.randString(20);
        
        // Escrower
        String esc="";
        if (this.getRandBool())
           esc="";
        else
           esc=this.getRandAdr();
        
        CTransPacket packet=new CTransPacket("MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEEpn9tcO9qb55dKKTCroSy6fa8mhyhChMYLdJer+WYnVR8Is9l1864vi9Z9eVXTkk3xo1ARNfY+fM0DWI0Wo7+g==", 
			                     "MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEEpn9tcO9qb55dKKTCroSy6fa8mhyhChMYLdJer+WYnVR8Is9l1864vi9Z9eVXTkk3xo1ARNfY+fM0DWI0Wo7+g==", 
	                                     rec, 
	    	                             amount, 
			                     cur,
			                     mes,
                                             esc,
                                             0);
        
        // Send 
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void testEscrowedSign() throws Exception
    {
        // Adr
        String adr=this.getRandAdr();
        
        // Trans hash
        String trans_hash=this.getRandEscrowedHash();
        
        // Type
        String type="ID_APROVE";
        if (this.getRandBool())
            type="ID_APROVE";
        else
            type="ID_REJECT";
        
        CEscrowedTransSignPacket packet=new CEscrowedTransSignPacket(adr,
                                                                     adr, 
                                                                     trans_hash, 
                                                                     type);
        
         // Send 
         UTILS.NETWORK.broadcast(packet);
    }
    
    
    public void testNewArt() throws Exception
    {
        // Adr
        String adr=this.getRandAdr();
        
        // Title
        String title=UTILS.BASIC.randString(20);
        
        // Desc
        String desc=UTILS.BASIC.randString(20);
        
        // Country
        String cou=this.getRandCou();
        
        // Mil unit
        long mil_unit=0;
        if (this.getRandBool())
            mil_unit=this.getRandOrg();
        else
            mil_unit=0;
        
        // Pol party
        long pol_party=0;
        if (this.getRandBool())
            pol_party=this.getRandOrg();
        else
            pol_party=0;
        
        // Days
        long days=this.getRandDays();
        
        CNewArticlePacket packet=new CNewArticlePacket(adr, 
		                                       adr, 
		                                       title,
                                                       desc, 
                                                       "ID_CRYPTO",
                                                       cou, 
                                                       "",
                                                       mil_unit,
                                                       pol_party,
                                                       days);
        
        // Send 
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void testVote() throws Exception
    {
        // Adr
        String adr=this.getRandAdr();
        
        // Target type
        String target_type="";
        if (this.getRandBool())
            target_type="ID_TWEET";
        else
            target_type="ID_COM";
        
        // Target ID
        long targetID=0;
        if (target_type.equals("ID_TWEET"))
            targetID=this.getRandArt();
        else
            targetID=this.getRandComment();
        
        // Target type
        String type="";
        if (this.getRandBool())
            type="ID_UP";
        else
            type="ID_DOWN";
        
        CVotePacket packet=new CVotePacket(adr,
                                           adr,
                                           target_type,
                                           targetID,
                                           type);
        
        // Send 
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void testNewComment() throws Exception
    {
        // Adr
        String adr=this.getRandAdr();
        
        // Parent type
        String parent_type="";
        long parentID=0;
        int no=Integer.parseInt(String.valueOf(Math.round(this.getRandAmount()*7)));
        
        switch (no)
        {
            case 1 : parent_type="ID_TWEET"; 
                     parentID=this.getRandArt();
                     break;
                     
            case 2 : parent_type="ID_COM"; 
                     parentID=this.getRandComment();
                     break;
                     
            case 3 : parent_type="ID_ASSET"; 
                     parentID=this.getRandAssetID();
                     break;
                     
            case 4 : parent_type="ID_EXCHANGE"; 
                     parentID=this.getRandEx();
                     break;
                     
            case 5 : parent_type="ID_PROP"; 
                     parentID=this.getRandOrgPropID();
                     break;
                     
            case 6 : parent_type="ID_LAW"; 
                     parentID=this.getRandLaw();
                     break;
        }
        
        // Message
        String mes=UTILS.BASIC.randString(25);
        
        CCommentPacket packet=new CCommentPacket(adr,
                                                 adr, 
		                                 parent_type,
                                                 parentID,
		                                 mes);
        
        // Send 
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void testFollow() throws Exception
    {
        // Adr
        String adr=this.getRandAdr();
        
        // Follows
        String follows=this.getRandAdr();
         
        // Days
        long days=this.getRandDays();
        
        CFollowPacket packet=new CFollowPacket(adr,
                                               adr,
		                               follows,
                                               days);
        
        // Send 
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void testArtVotes() throws Exception
    {
        // Address
        String adr=this.getRandAdr();
        
        // Random article
        long artID=this.getRandArt();
        
        // Voted ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM votes "
                                          + "WHERE adr='"+adr+"' "
                                            + "AND target_type='ID_TWEET' "
                                            + "AND targetID='"+artID+"'");
        
        // Has data
        if (!UTILS.DB.hasData(rs))
        {
             CVotePacket packet=new CVotePacket(adr,
                                                adr,
                                                "ID_TWEET",
                                                artID,
                                                "ID_UP");
             
             if (UTILS.STATUS.status.equals("ID_ONLINE"))
                UTILS.NETWORK.broadcast(packet);
        }
    }
    
    public void testComVotes() throws Exception
    {
        // Address
        String adr=this.getRandAdr();
        
        // Random article
        long comID=this.getRandComment();
        
        // Voted ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM votes "
                                          + "WHERE adr='"+adr+"' "
                                            + "AND target_type='ID_COM' "
                                            + "AND targetID='"+comID+"'");
        
        // Has data
        if (!UTILS.DB.hasData(rs))
        {
             CVotePacket packet=new CVotePacket(adr,
                                                adr,
                                                "ID_COM",
                                                comID,
                                                "ID_UP");
             
             if (UTILS.STATUS.status.equals("ID_ONLINE"))
                UTILS.NETWORK.broadcast(packet);
        }
    }
    
    public void testUnfollow() throws Exception
    {
        // Adr
        String adr=this.getRandAdr();
        
        // Follows
        String unfollow=this.getRandAdr();
        
        CUnfollowPacket packet=new CUnfollowPacket(adr,
                                                   adr,
                                                   unfollow);
        
        // Send 
        UTILS.NETWORK.broadcast(packet);
    }
    
    public void StressDonate() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM adr ORDER BY RAND() "
                                          + "LIMIT 0,1");
        
        // Next
        rs.next();
 
        // Adr
        String adr=rs.getString("adr");
        
        // Has cigars
        rs=UTILS.DB.executeQuery("SELECT * "
                                 + "FROM stocuri "
                                + "WHERE adr='"+adr+"' "
                                  + "AND tip='ID_CIG_CHURCHILL'");
        
        // Has cigars
        boolean has_cigars=false;
        if (UTILS.DB.hasData(rs))
            has_cigars=true;
        
        // Has gift
        rs=UTILS.DB.executeQuery("SELECT * "
                                 + "FROM stocuri "
                                + "WHERE adr='"+adr+"' "
                                  + "AND tip='ID_GIFT'");
        
        // Has cigars
        boolean has_gift=false;
        if (UTILS.DB.hasData(rs))
            has_gift=true;
            
        
        // Has cigars
        if (!has_cigars && has_gift)
        {
            // Item ID
            rs=UTILS.DB.executeQuery("SELECT * "
                                     + "FROM stocuri "
                                    + "WHERE adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAELGXABty98wVa/ZfJf9whISSqlwaz01+c7j4bphz4PcTDfeC6zgyllKhPyrp71eb2zLaz4m0JD3m4D99c8exUzQ==' "
                                      + "AND tip='ID_CIG_CHURCHILL' "
                                 + "ORDER BY RAND() "
                                    + "LIMIT 0,1");
            
            rs.next();
            
            long itemID=rs.getLong("stocID");
            
            CDonateItemPacket packet=new CDonateItemPacket("MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAELGXABty98wVa/ZfJf9whISSqlwaz01+c7j4bphz4PcTDfeC6zgyllKhPyrp71eb2zLaz4m0JD3m4D99c8exUzQ==",
                                                           "MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAELGXABty98wVa/ZfJf9whISSqlwaz01+c7j4bphz4PcTDfeC6zgyllKhPyrp71eb2zLaz4m0JD3m4D99c8exUzQ==",
                                                           itemID,
                                                           adr);
            
            UTILS.NETWORK.broadcast(packet);
        }
    }
    
    public void testWork() throws Exception
    {
        // Adr
        String adr=this.getRandAdr();
        
        // Worked in the last 4 hours ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM work_procs "
                                          + "WHERE adr='"+adr+"' "
                                            + "AND block>"+(UTILS.NET_STAT.last_block-180));
        
        // Has data ?
        if (!UTILS.DB.hasData(rs))
        {
            // Random workplace
            long wID=this.getRandWorkplace();
            
            // Work 1 hour
            CWorkPacket packet=new CWorkPacket(adr,
                                               adr,
                                               wID,
                                               60);
            
            UTILS.NETWORK.broadcast(packet);
        }
    }
    
    public void testConsume() throws Exception
    {
        // Adr
        String adr=this.getRandAdr();
        
        // Random item
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM stocuri "
                                          + "WHERE adr='"+adr+"' "
                                       + "ORDER BY RAND()");
        
        if (UTILS.DB.hasData(rs))
        {
            // Next
            rs.next();
            
            // Energy booster ?
            if (UTILS.BASIC.isEnergyBooster(rs.getString("tip")))
            {
                // Conusmed in the last 24 hours ?
                ResultSet rss=UTILS.DB.executeQuery("SELECT * "
                                                + "FROM items_consumed "
                                               + "WHERE adr='"+adr+"' "
                                                 + "AND tip='"+rs.getString("tip")+"'");
            
                // Has data ?
                if (!UTILS.DB.hasData(rss))
                {
                   // Next    
                   rss.next();
                
                   // Packet
                   CConsumeItemPacket packet=new CConsumeItemPacket(adr,
                                                                 adr,
                                                                 rs.getLong("stocID"));
                
                   // Broadcast
                   UTILS.NETWORK.broadcast(packet);
                }
            }
        }
    }
    
    public void testBuy() throws Exception
    {
        // Adr
        String adr=this.getRandAdr();
        
        // Random
        long num=Math.round(Math.random()*35);
        
        // Prod
        String prod="";
        
        // Choose product
        if (num==2) prod="ID_CIG_CHURCILL";
        if (num==3) prod="ID_CIG_PANATELA";
        if (num==4) prod="ID_CIG_TORPEDO";
        if (num==5) prod="ID_CIG_CORONA";
        if (num==6) prod="ID_CIG_TORO";
        if (num==7) prod="ID_SAMPANIE";
        if (num==8) prod="ID_MARTINI";
        if (num==9) prod="ID_MOJITO";
        if (num==10) prod="ID_MARY";
        if (num==11) prod="ID_SINGAPORE";
        if (num==12) prod="ID_CROISANT";
        if (num==13) prod="ID_HOT_DOG";
        if (num==14) prod="ID_PASTA";
        if (num==15) prod="ID_BURGER";
        if (num==16) prod="ID_BIG_BURGER";
        if (num==17) prod="ID_PIZZA";
        
        if (num==18) prod="ID_KNIFE";
        if (num==19) prod="ID_PISTOL";
        if (num==20) prod="ID_REVOLVER";
        if (num==21) prod="ID_SHOTGUN";
        if (num==22) prod="ID_MACHINE_GUN";
        if (num==23) prod="ID_SNIPER";
        if (num==24) prod="ID_GLOVES";
        if (num==25) prod="ID_GOGGLES";
        if (num==26) prod="ID_HELMET";
        if (num==27) prod="ID_BOOTS";
        if (num==28) prod="ID_VEST";
        if (num==29) prod="ID_SHIELD";
        
        if (num==30) prod="ID_TRAVEL_TICKET_Q1";
        if (num==31) prod="ID_TRAVEL_TICKET_Q2";
        if (num==32) prod="ID_TRAVEL_TICKET_Q3";
        
        
        
        
        // Has in stock ?
        if (!prod.equals(""))
        {
            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM stocuri "
                                              + "WHERE adr='"+adr+"' "
                                                + "AND tip='"+prod+"'");
            
            // Has data ?
            if (!UTILS.DB.hasData(rs))
            {
                // Market ID
                rs=UTILS.DB.executeQuery("SELECT * "
                                         + "FROM assets_mkts "
                                        + "WHERE asset='"+prod+"' "
                                          + "AND cur='CRC'");
                
                // Next
                rs.next();
                
                // Mkt ID
                long mktID=rs.getLong("mktID");
                
                // Packet
                CNewRegMarketPosPacket packet=new CNewRegMarketPosPacket(adr,
                                                                         adr,
                                                                         mktID,
                                                                         "ID_BUY",
                                                                         6.0,
                                                                         1.0,
                                                                         1);
                
                
                
                UTILS.NETWORK.broadcast(packet);
            }
        }
    }
    
    public void testGifts() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * FROM adr WHERE node_adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAErETk0mzTE+49hOEZ8svUaJ5Qu5zvGJhq13YiaxWOzXPeIoTqwQeuYAj7RcwFBISnArTUX5o/JTgCiEyBm/j/aA==' ORDER BY RAND()");
        rs.next();
        
        // Has gifts ?
        ResultSet rss=UTILS.DB.executeQuery("SELECT * "
                                 + "FROM stocuri "
                                + "WHERE adr='"+rs.getString("adr")+"' "
                                  + "AND tip='ID_GIFT'");
        
        if (!UTILS.DB.hasData(rss))
        {
            CDonateItemPacket packet=new CDonateItemPacket("MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEEpn9tcO9qb55dKKTCroSy6fa8mhyhChMYLdJer+WYnVR8Is9l1864vi9Z9eVXTkk3xo1ARNfY+fM0DWI0Wo7+g==",
                                                           "MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEEpn9tcO9qb55dKKTCroSy6fa8mhyhChMYLdJer+WYnVR8Is9l1864vi9Z9eVXTkk3xo1ARNfY+fM0DWI0Wo7+g==",
                                                           323222,
                                                           rs.getString("adr"));
            
            UTILS.NETWORK.broadcast(packet);
        }
    }
    
    public void testAdrEndorse() throws Exception
    {
        // Random adr
        String endorser=this.getRandAdr();
        
        // Random party adr
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM adr "
                                          + "WHERE pol_party=1528654998473 "
                                       + "ORDER BY RAND()");
        
        // Next
        rs.next();
        
        // Endorsed
        String endorsed=rs.getString("adr");
        
        // Endorsed ?
        rs=UTILS.DB.executeQuery("SELECT * "
                                 + "FROM endorsers "
                                + "WHERE endorser='"+endorser+"' "
                                  + "AND endorsed='"+endorsed+"'");
        
        // Has data ?
        if (!UTILS.DB.hasData(rs))
        {
            // ALready endorsed 10 adr ?
            rs=UTILS.DB.executeQuery("SELECT COUNT(*) AS total "
                                     + "FROM endorsers "
                                    + "WHERE endorser='"+endorser+"'");
            
            // Next
            rs.next();
            
            // Has data ?
            if (rs.getLong("total")<10)
            {
                CEndorsePacket packet=new CEndorsePacket(endorser,
                                                         endorser,
                                                         endorsed,
                                                         "ID_UP");
                
                UTILS.NETWORK.broadcast(packet);
            }
        }
    }
    
    public void start()
    {
        this.started=true;
    }
    
    public void run() throws Exception
    {
        t++;
       
        if (started)
        {
           //this.testTrans();
           
            if (t%2==0)
            {
              this.testArtVotes();
              this.testComVotes();
            }
               
           if (t%10==0)
           {
               this.testConsume();
               this.testWork();
               this.testBuy();
               this.testGifts();
               this.testAdrEndorse();
           }
      
           
        }
    }
}
