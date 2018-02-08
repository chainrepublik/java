// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.kernel;

import java.sql.ResultSet;
import java.util.Timer;
import chainrepublik.network.packets.CPacket;
import chainrepublik.network.packets.adr.*;
import chainrepublik.network.packets.ads.*;
import chainrepublik.network.packets.assets.*;
import chainrepublik.network.packets.assets.reg_mkts.*;
import chainrepublik.network.packets.companies.*;
import chainrepublik.network.packets.mes.*;
import chainrepublik.network.packets.misc.*;
import chainrepublik.network.packets.portofolio.*;
import chainrepublik.network.packets.press.*;
import chainrepublik.network.packets.trans.*;


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
                       !op.equals("ID_REMOVE_PEER"))
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
                                    CTransPacket packet=new CTransPacket(UTILS.STATUS.node_adr, 
			                                                 UTILS.STATUS.node_adr,  
			                                                 adr, 
			                                                 UTILS.STATUS.new_acc_reward, 
			                                                 "CRC",
			                                                 "Welcome to ChainRepublik",
                                                                         "",
                                                                         rs.getLong("ID"));
                        
                                    UTILS.NETWORK.broadcast(packet);
                               }
                           }
                       }
                  }
                   
                   // Consume item
                   if (op.equals("ID_CONSUME_ITEM"))
                   {
                       // New packet
                       CConsumeItemPacket packet=new CConsumeItemPacket(rs.getString("fee_adr"),
                                                                        rs.getString("target_adr"),
                                                                        rs.getLong("par_1"));
                       
                       // Broadcast
                       UTILS.NETWORK.broadcast(packet);
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
                       CUseItemPacket packet=new CUseItemPacket(rs.getString("fee_adr"),
                                                                rs.getString("target_adr"),
                                                                rs.getLong("par_1"));
                       
                       // Broadcast
                       UTILS.NETWORK.broadcast(packet);
                   }
                   
                   // New transaction
                   if (op.equals("ID_TRANSACTION")) 
                   {
                        CTransPacket packet=new CTransPacket(rs.getString("fee_adr"),
                                                             rs.getString("target_adr"),
                                                             rs.getString("par_1"),
                                                             rs.getDouble("par_2"),
                                                             rs.getString("par_3"),
                                                             rs.getString("par_4"),
                                                             rs.getString("par_5"),
                                                             rs.getLong("ID"));
                        
                        UTILS.NETWORK.broadcast(packet);
                   }
                   
                   if (op.equals("ID_ADR_REGISTER_PACKET"))
                   {
                       CAdrRegisterPacket packet=new CAdrRegisterPacket(rs.getString("fee_adr"),
                                                                        rs.getString("target_adr"),
		                                                        rs.getString("par_1"), 
		                                                        UTILS.BASIC.base64_decode(rs.getString("par_2")),
                                                                        UTILS.BASIC.base64_decode(rs.getString("par_3")), 
                                                                        rs.getString("par_4"),
                                                                        rs.getString("par_5"), 
                                                                        rs.getString("par_6"),
                                                                        rs.getLong("days"));
                    
                       UTILS.NETWORK.broadcast(packet);
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
                        CEscrowedTransSignPacket packet=new CEscrowedTransSignPacket(rs.getString("fee_adr"),
                                                                                     rs.getString("par_1"),
                                                                                     rs.getString("target_adr"),
                                                                                     rs.getString("par_2"));
                        
                        UTILS.NETWORK.broadcast(packet);
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
                       CRenewPacket packet=new CRenewPacket(rs.getString("fee_adr"), 
                                                            rs.getString("target_adr"), 
                                                            rs.getString("par_1"), 
                                                            rs.getLong("par_2"), 
                                                            rs.getLong("days"));
                       UTILS.NETWORK.broadcast(packet);
                   }
                   
                   if (op.equals("ID_WORK"))
                   {
                       CWorkPacket packet=new CWorkPacket(rs.getString("fee_adr"), 
                                                          rs.getString("target_adr"), 
                                                          rs.getLong("par_1"), 
                                                          rs.getLong("par_2"));
                       UTILS.NETWORK.broadcast(packet);
                   }
                   
                   if (op.equals("ID_SHUTDOWN"))
                   {
                       UTILS.DB.executeUpdate("UPDATE web_ops SET status='ID_EXECUTED' WHERE ID='"+rs.getLong("ID")+"'"); 
                       System.exit(0); 
                   }
                   
                   if (op.equals("ID_ADD_ATTR"))
                   {
                      CAddAttrPacket packet=new  CAddAttrPacket(rs.getString("fee_adr"),
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
                                                                   
                       UTILS.NETWORK.broadcast(packet);
                   }
                   
                   if (op.equals("ID_START_MINING")) 
                   { 
                       UTILS.CBLOCK.startMiners(rs.getInt("par_1")); 
                   }
                   
                   if (op.equals("ID_STOP_MINING")) 
                   { 
                       UTILS.CBLOCK.stopMiners();
                   }
                   
                   if (op.equals("ID_NEW_AD"))
                   {
                       CNewAdPacket packet=new CNewAdPacket(rs.getString("fee_adr"), 
		                                            rs.getString("target_adr"), 
		                                            rs.getString("par_1"), 
		                                            rs.getString("par_2"), 
		                                            rs.getString("par_3"),
                                                            rs.getLong("days"), 
		                                            rs.getDouble("bid"));
                                                
                       UTILS.NETWORK.broadcast(packet);
                   }
                   
                   if (op.equals("ID_TRAVEL"))
                   {
                       CTravelPacket packet=new CTravelPacket(rs.getString("fee_adr"), 
		                                              rs.getString("target_adr"), 
		                                              rs.getString("par_1"));
                       
                       UTILS.NETWORK.broadcast(packet);
                   }
                   
                   if (op.equals("ID_CHG_CIT"))
                   {
                       CAdrChgCitPacket packet=new CAdrChgCitPacket(rs.getString("fee_adr"), 
		                                                    rs.getString("target_adr"), 
		                                                    rs.getString("par_1"));
                       
                       UTILS.NETWORK.broadcast(packet);
                   }
                   
                   
                    // Send message
                   if (op.equals("ID_SEND_MES"))
                   {
                      CMesPacket packet=new CMesPacket(rs.getString("fee_adr"), 
                                                       rs.getString("target_adr"),
		                                       rs.getString("par_1"), 
                                                       rs.getString("par_2"), 
                                                       rs.getString("par_3"),
                                                       rs.getString("packet_sign"),
                                                       rs.getString("payload_sign"));
                                 
                      UTILS.NETWORK.broadcast(packet);
                   }  
                   
                   
                   
                   // New regular asset market
                   if (op.equals("ID_VOTE_DELEGATE"))
                   {
                       CDelVotePacket packet=new CDelVotePacket(rs.getString("fee_adr"), 
                                                                rs.getString("target_adr"),
                                                                rs.getString("par_1"),
                                                                rs.getString("par_2"));
                      
                       UTILS.NETWORK.broadcast(packet);
                   }
                   
                   // Raaw packet
                   if (op.equals("ID_RAW_PACKET"))
                   {
                       // Load data
                       String data=rs.getString("par_1");
                       byte[] decoded=UTILS.BASIC.base64_decode_data(data);
                       CPacket packet=(CPacket) UTILS.SERIAL.deserialize(decoded);
                       
                       // Broadcast
                       UTILS.NETWORK.broadcast(packet);
                   }  
                   
                   if (op.equals("ID_ISSUE_ASSET"))
                   {
                     CIssueAssetPacket packet=new CIssueAssetPacket(rs.getString("fee_adr"), 
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
                        
                        UTILS.NETWORK.broadcast(packet);
                   }
                   
                   // New regular asset market
                   if (op.equals("ID_NEW_REGULAR_ASSET_MARKET"))
                   {
                       CNewRegMarketPacket packet=new CNewRegMarketPacket(rs.getString("fee_adr"), 
                                                                          rs.getString("target_adr"),
                                                                          rs.getString("par_1"),
                                                                          rs.getString("par_2"), 
                                                                          rs.getString("par_3"),
                                                                          rs.getString("par_4"), 
                                                                          rs.getLong("par_5"),
                                                                          rs.getLong("days"));
                       
                       UTILS.NETWORK.broadcast(packet);
                   }
                   
                   // Rent licence
                   if (op.equals("ID_RENT_LIC"))
                   {
                       CRentLicencePacket packet=new CRentLicencePacket(rs.getString("fee_adr"), 
                                                                        rs.getString("target_adr"),
                                                                        rs.getLong("par_1"),
                                                                        rs.getString("par_2"), 
                                                                        rs.getLong("days"));
                              
                        UTILS.NETWORK.broadcast(packet);
                   }
                   
                   // New regular asset market
                   if (op.equals("ID_NEW_REGULAR_MKT_POS"))
                   {
                       CNewRegMarketPosPacket packet=new CNewRegMarketPosPacket(rs.getString("fee_adr"), 
                                                                                rs.getString("target_adr"),
                                                                                rs.getLong("par_1"), 
                                                                                rs.getString("par_2"),
                                                                                rs.getDouble("par_3"),
                                                                                rs.getDouble("par_4"),
                                                                                rs.getLong("days"));
                       
                       UTILS.NETWORK.broadcast(packet);
                   }
                   
                   // Rent workplace
                   if (op.equals("ID_RENT_WORKPLACE"))
                   {
                       // New workplace
                       CNewWorkplacePacket packet =new CNewWorkplacePacket(rs.getString("fee_adr"), 
                                                                           rs.getString("target_adr"),
                                                                           rs.getLong("par_1"), 
                                                                           rs.getLong("days"));
                       
                       // Broadcast
                       UTILS.NETWORK.broadcast(packet);
                   }
                   
                   // Rent workplace
                   if (op.equals("ID_UPDATE_WORKPLACE"))
                   {
                       // New workplace
                       CUpdateWorkplacePacket packet =new CUpdateWorkplacePacket(rs.getString("fee_adr"), 
                                                                                 rs.getString("target_adr"),
                                                                                 rs.getLong("par_1"), 
                                                                                 rs.getString("par_2"), 
                                                                                 rs.getDouble("par_3"), 
                                                                                 rs.getString("par_4"));
                       
                       // Broadcast
                       UTILS.NETWORK.broadcast(packet);
                   }
                   
                   // New regular asset market
                   if (op.equals("ID_CLOSE_REGULAR_MKT_POS"))
                   {
                       CCloseRegMarketPosPacket packet=new CCloseRegMarketPosPacket(rs.getString("fee_adr"), 
                                                                                    rs.getString("target_adr"),
                                                                                    rs.getLong("par_1"));
                       
                       UTILS.NETWORK.broadcast(packet);
                   }
                   
                   if (op.equals("ID_NEW_TWEET"))
                   {
                        CNewTweetPacket packet=new CNewTweetPacket(rs.getString("fee_adr"), 
                                                                   rs.getString("target_adr"),
                                                                   UTILS.BASIC.base64_decode(rs.getString("par_1")), 
		                                                   UTILS.BASIC.base64_decode(rs.getString("par_2")), 
                                                                   rs.getString("par_3"),
                                                                   rs.getString("par_4"),
                                                                   rs.getLong("par_5"),
		                                                   UTILS.BASIC.base64_decode(rs.getString("par_6")),
                                                                   rs.getLong("par_7"),
                                                                   rs.getLong("par_8"),
                                                                   rs.getLong("days"));
                       
                        UTILS.NETWORK.broadcast(packet);
                   }
                   
                   
                   // New tweet
                   if (op.equals("ID_NEW_COMMENT"))
                   {
                       CCommentPacket packet=new  CCommentPacket(rs.getString("fee_adr"),
                                                                 rs.getString("target_adr"),
		                                                 rs.getString("par_1"),
                                                                 rs.getLong("par_2"),
                                                                 rs.getString("par_3"));
                       
                       UTILS.NETWORK.broadcast(packet);
                   }
                   
                   
                    // Update comment status
                   if (op.equals("ID_VOTE"))
                   {
                       CVotePacket packet=new CVotePacket(rs.getString("fee_adr"),
                                                          rs.getString("target_adr"),
		                                          rs.getString("par_1"), 
                                                          rs.getLong("par_2"), 
                                                          rs.getString("par_3"));
                       
                       UTILS.NETWORK.broadcast(packet);
                   }
                   
                   // Follow
                   if (op.equals("ID_FOLLOW"))
                   {
                       CFollowPacket packet=new CFollowPacket(rs.getString("fee_adr"),
                                                              rs.getString("target_adr"),
		                                              rs.getString("par_1"),
                                                              rs.getLong("days"));
                       
                       UTILS.NETWORK.broadcast(packet);
                   }
                   
                   // Update profile
                   if (op.equals("ID_UPDATE_PROFILE"))
                   {
                       CUpdateProfilePacket packet=new CUpdateProfilePacket(rs.getString("fee_adr"),
                                                                            rs.getString("target_adr"),
		                                                            rs.getString("par_1"),
                                                                            rs.getString("par_2"));
                       
                       UTILS.NETWORK.broadcast(packet);
                   }
                   
                   // Unfollow
                   if (op.equals("ID_UNFOLLOW"))
                   {
                       CUnfollowPacket packet=new CUnfollowPacket(rs.getString("fee_adr"),
                                                                  rs.getString("target_adr"),
		                                                  rs.getString("par_1"));
                       
                       UTILS.NETWORK.broadcast(packet);
                   }
                   
                   
                   // New Company
                    if (op.equals("ID_NEW_COMPANY"))
                    {
                        // Load free address
                        String adr=UTILS.BASIC.getFreeAdr(rs.getLong("userID"));
                        
                        // No address found
                        if (adr.equals(""))
                        adr=UTILS.WALLET.newAddress(rs.getLong("userID"), 
                                                    rs.getString("par_3")+" company address");
                        
                        
                        // Packet
                        CNewCompanyPacket packet=new CNewCompanyPacket(rs.getString("fee_adr"),
                                                                       adr,
                                                                       rs.getString("par_1"),
                                                                       rs.getString("par_2"),
                                                                       rs.getString("par_3"),
                                                                       rs.getString("par_4"),
                                                                       rs.getString("par_5"), 
                                                                       rs.getString("par_6"),
                                                                       rs.getString("par_7"),
                                                                       rs.getLong("days"));
                        
                        // Broadcast
                        UTILS.NETWORK.broadcast(packet);
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
                UTILS.DB.executeUpdate("UPDATE web_ops SET status='ID_EXECUTED'"); 
            } 
            catch (Exception ex) 
            {
                System.out.println(ex.getMessage());
            }
        }
        
       
     }
     
    
}
