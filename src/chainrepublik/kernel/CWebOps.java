// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.kernel;

import chainrepublik.network.packets.CBroadcastPacket;
import java.sql.ResultSet;
import java.util.Timer;
import chainrepublik.network.packets.CPacket;
import chainrepublik.network.packets.adr.*;
import chainrepublik.network.packets.ads.*;
import chainrepublik.network.packets.assets.*;
import chainrepublik.network.packets.assets.reg_mkts.*;
import chainrepublik.network.packets.companies.*;
import chainrepublik.network.packets.exchange.CNewExOffertPacket;
import chainrepublik.network.packets.market.CRentPacket;
import chainrepublik.network.packets.mes.*;
import chainrepublik.network.packets.misc.*;
import chainrepublik.network.packets.politics.congress.CEndorsePacket;
import chainrepublik.network.packets.politics.congress.CNewLawPacket;
import chainrepublik.network.packets.politics.congress.CVoteLawPacket;
import chainrepublik.network.packets.politics.orgs.CJoinOrgPacket;
import chainrepublik.network.packets.politics.orgs.CLeaveOrgPacket;
import chainrepublik.network.packets.politics.orgs.CNewOrgPropPacket;
import chainrepublik.network.packets.politics.orgs.CVoteOrgPropPacket;
import chainrepublik.network.packets.portofolio.*;
import chainrepublik.network.packets.press.*;
import chainrepublik.network.packets.trans.*;
import chainrepublik.network.packets.war.CFightPacket;


public class CWebOps 
{
    // Timer
    Timer timer;
    
   
    public CWebOps()
    {
    
    }
     
     public void loadWebOps() throws Exception
     { 
         String op;
         
         CBroadcastPacket packet=null;
         
          
       try
       {
           // Result set
           ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                              + "FROM web_ops "
                                             + "WHERE status='ID_PENDING'");
      
           if (UTILS.DB.hasData(rs))
           {
               while (rs.next())
               {
                   // Op
                   op=rs.getString("op");
                   
                   boolean passed=true;
                   
                   if (!op.equals("ID_IMPORT_ADR") && 
                       !op.equals("ID_NEW_ACCOUNT") &&
                       !op.equals("ID_START_MINING") &&
                       !op.equals("ID_STOP_MINING") &&
                       !op.equals("ID_NEW_ADR") &&
                       !op.equals("ID_ADD_PEER") &&
                       !op.equals("ID_REMOVE_PEER") &&
                       !op.equals("ID_CHANGE_NODE_ADR") &&
                       !op.equals("ID_NEW_REGULAR_MKT_POS") &&
                       !op.equals("ID_UPDATE_COMPANY"))
                   {
                       // User valid ?
                       ResultSet rs_user=UTILS.DB.executeQuery("SELECT * "
                                                           + "FROM web_users AS us "
                                                           + "JOIN my_adr AS ma ON ma.userID=us.ID "
                                                          + "WHERE us.ID='"+rs.getString("userID")+"' "
                                                                   + "AND ma.adr='"+rs.getString("target_adr")+"'");
                       
                       if (!UTILS.DB.hasData(rs_user)) 
                         passed=false;
                   }
                   
                  
                   if (passed)
                   {
                       
                   // New account is created.
                   if (op.equals("ID_NEW_ACCOUNT"))
                   {
                       UTILS.WALLET.newAddress(rs.getLong("userID"), 
                                               "Main address");
                       
                       // UserID
                       long userID=rs.getLong("userID");
                       
                       // Load newly created adddress data
                       ResultSet rsn=UTILS.DB.executeQuery("SELECT * "
                                                           + "FROM my_adr "
                                                          + "WHERE userID='"+userID+"'");
                       
                       // Has data
                       if (UTILS.DB.hasData(rsn))
                       {
                           // Next
                           rsn.next();
                           
                           // Address
                           String adr=rsn.getString("adr");
                         
                           // Initial coins 
                           if (!UTILS.STATUS.node_adr.equals(""))
                           {
                               // Register address
                               CAdrRegisterPacket reg_packet=new CAdrRegisterPacket(UTILS.STATUS.node_adr,
                                                                                    adr,
                                                                                    rs.getString("par_2"),
                                                                                    rs.getString("par_1"), 
		                                                                    "",
                                                                                    rs.getString("par_3"),
                                                                                    UTILS.STATUS.node_adr, 
                                                                                    "",
                                                                                    rs.getLong("days"));
                              
                               // Register packet
                               UTILS.NETWORK.broadcast(reg_packet);
                               
                               // Send coins
                               if (UTILS.STATUS.new_acc_reward>0.0001)
                               {
                                     packet=new CTransPacket(UTILS.STATUS.node_adr, 
			                                                 UTILS.STATUS.node_adr,  
			                                                 adr, 
			                                                 UTILS.STATUS.new_acc_reward, 
			                                                 "CRC",
			                                                 "Welcome to ChainRepublik",
                                                                         "",
                                                                         rs.getLong("ID"));
                                     
                                     UTILS.NETWORK.broadcast(packet);
                        
                                    
                               }
                               
                               // Welcome gift ?
                               if (UTILS.ACC.getBalance(UTILS.STATUS.node_adr, "ID_GIFT")>1)
                               {
                                    // New packet
                                     packet=new CGiftPacket(UTILS.STATUS.node_adr, 
                                                                       UTILS.STATUS.node_adr, 
                                                                       adr);
                       
                                    UTILS.NETWORK.broadcast(packet);
                                    
                               }
                           }
                       }
                  }
                   
                   // Consume item
                   if (op.equals("ID_CONSUME_ITEM"))
                   {
                       // New packet
                        packet=new CConsumeItemPacket(rs.getString("fee_adr"),
                                                                        rs.getString("target_adr"),
                                                                        rs.getLong("par_1"));
                       
                       
                       
                   }
                   
                   // Gift
                   if (op.equals("ID_GIFT"))
                   {
                       // New packet
                        packet=new CGiftPacket(rs.getString("fee_adr"),
                                                          rs.getString("target_adr"),
                                                          rs.getString("par_1"));
                       
                       
                       
                   }
                   
                   // Add peer
                   if (op.equals("ID_ADD_PEER")) 
                       UTILS.NETWORK.connectTo(rs.getString("par_1"), rs.getInt("par_2")); 
                   
                   // Remove peer
                   if (op.equals("ID_REMOVE_PEER")) 
                       UTILS.NETWORK.removePeer(rs.getString("par_1"));
                   
                  
                   // Use item
                   if (op.equals("ID_USE_ITEM"))
                   {
                       // Packet
                        packet=new CUseItemPacket(rs.getString("fee_adr"),
                                                                rs.getString("target_adr"),
                                                                rs.getLong("par_1"));
                       
                       
                       
                   }
                   
                   // New transaction
                   if (op.equals("ID_TRANSACTION")) 
                   {
                         packet=new CTransPacket(rs.getString("fee_adr"),
                                                             rs.getString("target_adr"),
                                                             rs.getString("par_1"),
                                                             rs.getDouble("par_2"),
                                                             rs.getString("par_3"),
                                                             rs.getString("par_4"),
                                                             rs.getString("par_5"),
                                                             rs.getLong("ID"));
                        
                        
                   }
                   
                   if (op.equals("ID_REGISTER_ADR"))
                   {
                        packet=new CAdrRegisterPacket(rs.getString("fee_adr"),
                                                      rs.getString("target_adr"),
		                                      rs.getString("par_1"), 
		                                      rs.getString("par_2"),
                                                      rs.getString("par_3"), 
                                                      rs.getString("par_4"),
                                                      rs.getString("par_5"), 
                                                      rs.getString("par_6"),
                                                      rs.getLong("days"));
                    
                       
                   }
                   
                   
                   if (op.equals("ID_NEW_ADR")) 
                       UTILS.WALLET.newAddress(rs.getLong("userID"), 
                                               UTILS.BASIC.base64_decode(rs.getString("par_1")));
                    
                    if (op.equals("ID_REVEAL_PK"))
                    {
                        // Load address
                        CAddress adr=UTILS.WALLET.getAddress(rs.getString("target_adr"));
                        
                        // ID
                        long ID=rs.getLong("ID");
                                
                        // Write PK
                        UTILS.DB.executeUpdate("UPDATE web_ops "
                                                + "SET resp_1='"+adr.getPublic()+"', "
                                                    + "resp_2='"+adr.getPrivate()+"' "
                                              + "WHERE ID='"+ID+"'");
                    }
                    
                    // Escrowed sign
                    if (op.equals("ID_ESCROWED_SIGN"))
                    {
                         packet=new CEscrowedTransSignPacket(rs.getString("fee_adr"),
                                                             rs.getString("target_adr"),
                                                             rs.getString("par_1"),
                                                             rs.getString("par_2"));
                        
                        
                    }
                    
                    // Join political party
                    if (op.equals("ID_JOIN_PARTY"))
                         packet=new CJoinOrgPacket(rs.getString("fee_adr"),
                                                                     rs.getString("target_adr"),
                                                                     rs.getLong("par_1"));
                        
                        
                    // Leave political party
                    if (op.equals("ID_LEAVE_PARTY"))
                         packet=new CLeaveOrgPacket(rs.getString("fee_adr"),
                                                    rs.getString("target_adr"),
                                                    rs.getLong("par_1"));
                    
                    
                    // Leave political party
                    if (op.equals("ID_NEW_ORG_PROP"))
                    {
                         packet=new CNewOrgPropPacket(rs.getString("fee_adr"),
                                                                       rs.getString("target_adr"),
                                                                       rs.getLong("par_1"),
                                                                       rs.getString("par_2"), 
                                                                       rs.getString("par_3"),
                                                                       rs.getString("par_4"),
                                                                       "",
                                                                       "",
                                                                       "",
                                                                       rs.getString("par_5"));
                        
                        
                    }
                    
                    // Leave political party
                    if (op.equals("ID_VOTE_ORG_PROP"))
                    {
                         packet=new CVoteOrgPropPacket(rs.getString("fee_adr"),
                                                                         rs.getString("target_adr"),
                                                                         rs.getLong("par_1"),
                                                                         rs.getString("par_2"));
                        
                        
                    }
                    
                    if (op.equals("ID_VOTE_LAW"))
                    {
                         packet=new CVoteLawPacket(rs.getString("fee_adr"),
                                                                 rs.getString("target_adr"),
                                                                 rs.getLong("par_1"),
                                                                 rs.getString("par_2"));
                        
                        
                    }
                    
                    if (op.equals("ID_ENDORSE_ADR"))
                    {
                         packet=new CEndorsePacket(rs.getString("fee_adr"),
                                                                 rs.getString("target_adr"),
                                                                 rs.getString("par_1"),
                                                                 rs.getString("par_2"));
                        
                        
                    }
                    
                    if (op.equals("ID_FIGHT"))
                    {
                         packet=new CFightPacket(rs.getString("fee_adr"),
                                                             rs.getString("target_adr"),
                                                             rs.getLong("par_1"), 
                                                             rs.getString("par_2"));
                        
                        
                    }
                    
                    if (op.equals("ID_NEW_LAW"))
                    {
                         packet=new CNewLawPacket(rs.getString("fee_adr"),
                                                  rs.getString("target_adr"),
                                                  rs.getString("par_1"), 
                                                  rs.getString("par_2"), 
                                                  rs.getString("par_3"), 
                                                  rs.getString("par_4"), 
                                                  rs.getString("par_5"));
                        
                        
                    }
                    
                   if (op.equals("ID_IMPORT_ADR"))
                   {
                       CAddress adr=new CAddress();
                      
                       adr.importAddress(rs.getLong("par_1"),
                                         rs.getString("par_2"),
                                         rs.getString("par_3"),
                                         rs.getString("par_4"));
                   }
                   
                   if (op.equals("ID_CHANGE_NODE_ADR"))
                   {
                       // Old address
                       String old_adr;
                       
                       // Address
                       CAddress adr=new CAddress();
                      
                       // Import
                       adr.importAddress(1,
                                         rs.getString("par_1"),
                                         rs.getString("par_2"),
                                         rs.getString("par_3"));
                       
                       // Find old address
                       if (rs.getString("par_3").equals("Official node address"))
                       {
                          // Old node address
                          old_adr=UTILS.STATUS.node_adr;
                          
                          // Update 
                          UTILS.DB.executeUpdate("UPDATE web_sys_data "
                                                  + "SET node_adr='"+rs.getString("par_1")+"'");
                          
                          // Update users
                          UTILS.DB.executeUpdate("UPDATE web_users "
                                                  + "SET adr='"+rs.getString("par_1")+"'");
                       }
                       else
                       {
                           // Old adr
                           old_adr=UTILS.STATUS.mining_adr;
                           
                           // Update 
                          UTILS.DB.executeUpdate("UPDATE web_sys_data "
                                                  + "SET mining_adr='"+rs.getString("par_1")+"'");
                       }
                       
                       // Remove old address
                       UTILS.DB.executeUpdate("DELETE FROM my_adr "
                                                  + "WHERE adr='"+old_adr+"'");
                       
                   }
                   
                   if (op.equals("ID_RENEW"))
                   {
                        packet=new CRenewPacket(rs.getString("fee_adr"), 
                                                            rs.getString("target_adr"), 
                                                            rs.getString("par_1"), 
                                                            rs.getLong("par_2"), 
                                                            rs.getLong("days"));
                       
                   }
                   
                   if (op.equals("ID_WORK"))
                   {
                        packet=new CWorkPacket(rs.getString("fee_adr"), 
                                                          rs.getString("target_adr"), 
                                                          rs.getLong("par_1"), 
                                                          rs.getLong("par_2"));
                       
                   }
                   
                   if (op.equals("ID_WTH_FUNDS"))
                   {
                        packet=new CWthFundsPacket(rs.getString("fee_adr"), 
                                                                  rs.getString("target_adr"), 
                                                                  rs.getLong("par_1"), 
                                                                  rs.getDouble("par_2"));
                       
                   }
                   
                   if (op.equals("ID_SHUTDOWN"))
                   {
                       UTILS.DB.executeUpdate("UPDATE web_ops SET status='ID_EXECUTED' WHERE ID='"+rs.getLong("ID")+"'"); 
                       System.exit(0); 
                   }
                   
                   if (op.equals("ID_ADD_ATTR"))
                   {
                       packet=new  CAddAttrPacket(rs.getString("fee_adr"),
		                                                rs.getString("target_adr"),
                                                                rs.getString("par_1"),
                                                                rs.getString("par_2"),
                                                                rs.getString("par_3"),
                                                                rs.getString("par_4"),
                                                                rs.getLong("par_5"),
                                                                rs.getLong("par_6"),
                                                                rs.getLong("par_7"),
                                                                rs.getDouble("par_8"),
                                                                rs.getDouble("par_9"),
                                                                rs.getDouble("par_10"),
                                                                rs.getLong("days"));
                                                                   
                       
                   }
                   
                   if (op.equals("ID_START_MINING")) 
                       UTILS.CBLOCK.startMiners(rs.getInt("par_1")); 
                   
                   if (op.equals("ID_STOP_MINING")) 
                       UTILS.CBLOCK.stopMiners();
                   
                   if (op.equals("ID_NEW_EX_ORDER"))
                       packet=new CNewExOffertPacket(rs.getString("fee_adr"),
		                                     rs.getString("target_adr"),
                                                     rs.getString("par_1"),
                                                     rs.getString("par_2"),
                                                     rs.getLong("par_3"),
                                                     rs.getDouble("par_4"),
                                                     rs.getDouble("par_5"),
                                                     rs.getDouble("par_6"),
                                                     rs.getString("par_7"),
                                                     rs.getString("par_8"),
                                                     rs.getString("par_9"),
                                                     rs.getString("par_10"),
                                                     rs.getLong("days"));
                   
                   
                   if (op.equals("ID_NEW_AD"))
                   {
                        packet=new CNewAdPacket(rs.getString("fee_adr"), 
		                                            rs.getString("target_adr"), 
		                                            rs.getString("par_1"), 
		                                            rs.getString("par_2"), 
		                                            rs.getString("par_3"),
                                                            rs.getLong("par_4"),
                                                            rs.getDouble("par_5"));
                                                
                       
                   }
                   
                   if (op.equals("ID_TRAVEL"))
                   {
                        packet=new CTravelPacket(rs.getString("fee_adr"), 
		                                              rs.getString("target_adr"), 
		                                              rs.getString("par_1"));
                       
                       
                   }
                   
                   if (op.equals("ID_CHG_CIT"))
                   {
                        packet=new CAdrChgCitPacket(rs.getString("fee_adr"), 
		                                                    rs.getString("target_adr"), 
		                                                    rs.getString("par_1"));
                       
                       
                   }
                   
                   
                    // Send message
                   if (op.equals("ID_SEND_MES"))
                   {
                       packet=new CMesPacket(rs.getString("fee_adr"), 
                                                       rs.getString("target_adr"),
		                                       rs.getString("par_1"), 
                                                       rs.getString("par_2"), 
                                                       rs.getString("par_3"));
                                 
                      
                   }  
                   
                   
                   
                   // New regular asset market
                   if (op.equals("ID_VOTE_DELEGATE"))
                   {
                        packet=new CDelVotePacket(rs.getString("fee_adr"), 
                                                                rs.getString("target_adr"),
                                                                rs.getString("par_1"),
                                                                rs.getString("par_2"));
                      
                       
                   }
                   
                    
                   
                   if (op.equals("ID_ISSUE_ASSET"))
                   {
                      packet=new CIssueAssetPacket(rs.getString("fee_adr"), 
                                                                    rs.getString("target_adr"),
                                                                    rs.getString("par_1"),
                                                                    rs.getString("par_2"),
                                                                    rs.getString("par_3"),
                                                                    rs.getString("par_4"),
                                                                    rs.getString("par_5"),
                                                                    rs.getString("par_6"),
                                                                    rs.getString("par_7"),
                                                                    rs.getLong("par_8"),
                                                                    rs.getDouble("par_9"),
                                                                    rs.getString("par_10"),
                                                                    rs.getLong("days"));    
                        
                        
                   }
                   
                   // New regular asset market
                   if (op.equals("ID_NEW_REGULAR_ASSET_MARKET"))
                   {
                        packet=new CNewRegMarketPacket(rs.getString("fee_adr"), 
                                                       rs.getString("target_adr"),
                                                       rs.getString("par_1"),
                                                       rs.getString("par_2"), 
                                                       rs.getString("par_3"),
                                                       rs.getString("par_4"), 
                                                       rs.getLong("par_5"),
                                                       rs.getLong("days"));
                       
                       
                   }
                   
                   // Rent licence
                   if (op.equals("ID_RENT_LIC"))
                   {
                        packet=new CRentLicencePacket(rs.getString("fee_adr"), 
                                                                        rs.getString("target_adr"),
                                                                        rs.getLong("par_1"),
                                                                        rs.getString("par_2"), 
                                                                        rs.getLong("days"));
                              
                        
                   }
                   
                   // New regular asset market
                   if (op.equals("ID_NEW_REGULAR_MKT_POS"))
                        packet=new CNewRegMarketPosPacket(rs.getString("fee_adr"), 
                                                          rs.getString("target_adr"),
                                                          rs.getLong("par_1"), 
                                                          rs.getString("par_2"),
                                                          rs.getDouble("par_3"),
                                                          rs.getDouble("par_4"),
                                                          rs.getLong("days"));
                       
                       
                   
                   
                   // Rent workplace
                   if (op.equals("ID_RENT_WORKPLACE"))
                   {
                       // New workplace
                        packet =new CNewWorkplacePacket(rs.getString("fee_adr"), 
                                                                           rs.getString("target_adr"),
                                                                           rs.getLong("par_1"), 
                                                                           rs.getLong("days"));
                       
                       
                       
                   }
                   
                   // Rent workplace
                   if (op.equals("ID_UPDATE_WORKPLACE"))
                   {
                       // New workplace
                        packet =new CUpdateWorkplacePacket(rs.getString("fee_adr"), 
                                                                                 rs.getString("target_adr"),
                                                                                 rs.getLong("par_1"), 
                                                                                 rs.getString("par_2"), 
                                                                                 rs.getDouble("par_3"), 
                                                                                 rs.getString("par_4"));
                       
                       
                       
                   }
                   
                   // New regular asset market
                   if (op.equals("ID_CLOSE_REGULAR_MKT_POS"))
                   {
                        packet=new CCloseRegMarketPosPacket(rs.getString("fee_adr"), 
                                                                                    rs.getString("target_adr"),
                                                                                    rs.getLong("par_1"));
                       
                       
                   }
                   
                   if (op.equals("ID_NEW_TWEET"))
                         packet=new CNewArticlePacket(rs.getString("fee_adr"), 
                                                      rs.getString("target_adr"),
                                                      rs.getString("par_1"), 
		                                      rs.getString("par_2"), 
                                                      rs.getString("par_3"),
                                                      rs.getString("par_4"),
                                                      rs.getString("par_5"),
                                                      rs.getLong("par_6"),
                                                      rs.getLong("par_7"),
                                                      rs.getLong("days"));
                       
                        
                   
                   // New tweet
                   if (op.equals("ID_NEW_COMMENT"))
                   {
                        packet=new  CCommentPacket(rs.getString("fee_adr"),
                                                                 rs.getString("target_adr"),
		                                                 rs.getString("par_1"),
                                                                 rs.getLong("par_2"),
                                                                 rs.getString("par_3"));
                       
                       
                   }
                   
                   
                    // Update comment status
                   if (op.equals("ID_VOTE"))
                   {
                        packet=new CVotePacket(rs.getString("fee_adr"),
                                                          rs.getString("target_adr"),
		                                          rs.getString("par_1"), 
                                                          rs.getLong("par_2"), 
                                                          rs.getString("par_3"));
                       
                       
                   }
                   
                   // Follow
                   if (op.equals("ID_FOLLOW"))
                   {
                        packet=new CFollowPacket(rs.getString("fee_adr"),
                                                              rs.getString("target_adr"),
		                                              rs.getString("par_1"),
                                                              rs.getLong("days"));
                       
                       
                   }
                   
                   // Set rent price
                   if (op.equals("ID_SET_RENT_PRICE"))
                   {
                        packet=new CSetRentPricePacket(rs.getString("fee_adr"),
                                                                          rs.getString("target_adr"),
		                                                          rs.getLong("par_1"),
                                                                          rs.getDouble("par_2"));
                       
                       
                   }
                   
                   
                   // Rent item
                   if (op.equals("ID_RENT_ITEM"))
                   {
                        packet=new CRentPacket(rs.getString("fee_adr"),
                                                          rs.getString("target_adr"),
		                                          rs.getLong("par_1"),
                                                          rs.getLong("days"));
                       
                       
                   }
                   
                   // Update Company
                   if (op.equals("ID_UPDATE_COMPANY"))
                   {
                        packet=new CUpdateCompanyPacket(rs.getString("fee_adr"),
                                                                            rs.getString("target_adr"),
                                                                            rs.getLong("par_1"),
		                                                            rs.getString("par_2"),
                                                                            rs.getString("par_3"),
                                                                            rs.getString("par_4"));
                       
                       
                   }
                   
                   
                   // Update profile
                   if (op.equals("ID_UPDATE_PROFILE"))
                   {
                        packet=new CUpdateProfilePacket(rs.getString("fee_adr"),
                                                                            rs.getString("target_adr"),
		                                                            rs.getString("par_1"),
                                                                            rs.getString("par_2"));
                       
                       
                   }
                   
                   // Unfollow
                   if (op.equals("ID_UNFOLLOW"))
                   {
                        packet=new CUnfollowPacket(rs.getString("fee_adr"),
                                                                  rs.getString("target_adr"),
		                                                  rs.getString("par_1"));
                       
                       
                   }
                   
                   
                   // Unfollow
                   if (op.equals("ID_DONATE_ITEM"))
                   {
                        packet=new CDonateItemPacket(rs.getString("fee_adr"),
                                                                      rs.getString("target_adr"),
		                                                      rs.getLong("par_1"),
                                                                      rs.getString("par_2"));
                       
                       
                   }
                   
                   // New Company
                    if (op.equals("ID_NEW_COMPANY"))
                    {
                        // Address
                        CAddress adr=new CAddress();
                        adr.generate();
                        
                        // Packet
                         packet=new CNewCompanyPacket(rs.getString("fee_adr"),
                                                      rs.getString("fee_adr"),
                                                      rs.getString("par_1"),
                                                      rs.getString("par_2"),
                                                      rs.getString("par_3"),
                                                      rs.getString("par_4"),
                                                      rs.getString("par_5"), 
                                                      rs.getString("par_6"),
                                                      adr.getPublic(),
                                                      rs.getLong("days"));
                        
                        
                        
                    }
                    
                    if (packet!=null)
                    {
                       // Has signature ?
                       if (packet.sign.equals(""))
                       {
                          if (!rs.getString("packet_sign").equals(""))
                          {
                              // Set signature
                              packet.setSign(rs.getString("packet_sign"));
                              
                              // Broadcast
                              UTILS.NETWORK.broadcast(packet);
                              
                              // Update status
                              UTILS.DB.executeUpdate("UPDATE web_ops "
                                                      + "SET status='ID_EXECUTED' "
                                                    + "WHERE ID='"+rs.getLong("ID")+"'"); 
                          }
                          else
                          {
                              // Update status
                              UTILS.DB.executeUpdate("UPDATE web_ops "
                                                      + "SET status='ID_WAIT_SIGN', "
                                                          + "packet_hash='"+packet.hash+"' "
                                                    + "WHERE ID='"+rs.getLong("ID")+"'"); 
                          }
                       }
                       else
                       {
                           if (!op.equals("ID_NEW_ACCOUNT"))
                              UTILS.NETWORK.broadcast(packet);
                       }
                    }
                   }
                }
           }
           
        
           
           
        }
        catch (Exception e) 
	{ 
		System.out.println("Exception in CWebOps.java file at line 605 - "+e.getMessage());
        }
        finally 
        {
            // Update web ops
            try 
            { 
                UTILS.DB.executeUpdate("UPDATE web_ops "
                                        + "SET status='ID_EXECUTED' "
                                      + "WHERE status='ID_PENDING'"); 
                
                UTILS.DB.executeUpdate("DELETE FROM web_ops "
                                           + "WHERE status='ID_WAIT_SIGN' "
                                             + "AND tstamp<="+(UTILS.BASIC.tstamp()-600));
            } 
            catch (Exception ex) 
            {
                System.out.println(ex.getMessage());
            }
        }
        
       
     }
     
    
}
