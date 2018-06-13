package chainrepublik.kernel.net_stat.tables;

import java.sql.ResultSet;
import java.sql.Statement;
import chainrepublik.kernel.json.JSONArray;
import chainrepublik.kernel.json.JSONObject;
import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.net_stat.CTable;

public class CAssetsMktsPos extends CTable
{
    public CAssetsMktsPos()
    {
        super("assets_mkts_pos");
    }
    
    // Create
    public void create() throws Exception
    {
        if (!this.tableExist(this.name))
        {
            // Status
            System.out.print("Creating table assets_mkts_pos...");
            
            // Create
            UTILS.DB.executeUpdate("CREATE TABLE assets_mkts_pos(ID BIGINT AUTO_INCREMENT PRIMARY KEY, "
	 	 				              + "adr VARCHAR(250) DEFAULT '', "
	 	 				              + "mktID BIGINT DEFAULT 0, "
	 	 				              + "tip VARCHAR(10) DEFAULT '', "
	 	 				              + "qty DOUBLE(20, 8) DEFAULT 0, "
	 	 				              + "price DOUBLE(20, 8) DEFAULT 0, "
	 	 				              + "orderID BIGINT DEFAULT 0, "
	 	 				              + "expires BIGINT DEFAULT 0)");
	 	 	   
            // Indexes
	    UTILS.DB.executeUpdate("CREATE INDEX assets_mkts_pos_adr ON assets_mkts_pos(adr)");
            UTILS.DB.executeUpdate("CREATE INDEX assets_mkts_pos_tip ON assets_mkts_pos(tip)");  
            UTILS.DB.executeUpdate("CREATE UNIQUE INDEX assets_mkts_pos_orderID ON assets_mkts_pos(orderID)");  
            
            // Populate
            if (!this.reorg)
               this.populate();
            
            // Confirm
            System.out.println("Done.");
        }
    }
    
    public void expired(long block) throws Exception
    {
        // Result
        ResultSet rs;
        
        rs=UTILS.DB.executeQuery("SELECT am.asset, "
                                       + "am.cur, "
                                       + "amp.* "
                                 + "FROM assets_mkts_pos AS amp "
                                 + "JOIN assets_mkts AS am ON amp.mktID=am.mktID "
                                + "WHERE amp.expires<="+block+" "
                                  + "AND amp.expires>0");
       
        
        while (rs.next())
        {
            // Market asset
            String asset=rs.getString("asset");
        
            // Market currency
            String cur=rs.getString("cur");
        
            // Order type
            String type=rs.getString("tip");
        
            // Order qty
            double qty=rs.getDouble("qty");
        
            // Order price
            double price=rs.getDouble("price");
        
            // Order owner
            String owner=rs.getString("adr");
        
            // Refund
            if (type.equals("ID_BUY"))
            {
               if (cur.equals("CRC"))
                   UTILS.DB.executeUpdate("UPDATE adr "
                                           + "SET balance=balance+"+UTILS.FORMAT_8.format(qty*price)+" "
                                         + "WHERE adr='"+owner+"'");
               else
                   UTILS.DB.executeUpdate("UPDATE assets_owners "
                                           + "SET qty=qty+"+UTILS.FORMAT_8.format(qty*price)+" "
                                         + "WHERE owner='"+owner+"' "
                                           + "AND symbol='"+asset+"'");
            }
            else
            {
                UTILS.DB.executeUpdate("UPDATE assets_owners "
                                       + "SET qty=qty+"+UTILS.FORMAT_8.format(qty)+" "
                                     + "WHERE owner='"+owner+"' "
                                       + "AND symbol='"+asset+"'");
                
                UTILS.DB.executeUpdate("UPDATE stocuri "
                                       + "SET qty=qty+"+UTILS.FORMAT_8.format(qty)+" "
                                     + "WHERE adr='"+owner+"' "
                                       + "AND tip='"+asset+"'");
           }
        }
        
        
        // Removes
        UTILS.DB.executeUpdate("DELETE FROM assets_mkts_pos "
                                   + "WHERE expires<="+block+" "
                                     + "AND expires>0");
    }
    
    public void reorganize(long block, String chk_hash) throws Exception
    {
       // Load checkpoint
       loadCheckpoint(chk_hash);
    }
    
    public void populate() throws Exception
    {
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEWYXBhV6aXKqBj/u9921Hbt+yh3izzTEzT1TAsMzfT9A02k2nFuxnTZg4eb3h6Y7esaAXpnq1OK8DrN0oe0vcgg==', mktID='654454654654', tip='ID_SELL', qty='510', price='0.02', orderID='1519028023268', expires='15810' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEZy4rHuPNTVIF0S8bDuvOv/xd8lk51XAepRFMxGMWrrNRNqGy5idyGG49DGvU8qZs93ZonzG544ayfPkiy0OACg==', mktID='1011', tip='ID_SELL', qty='1232', price='0.25', orderID='1519298913041', expires='5492' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEZy4rHuPNTVIF0S8bDuvOv/xd8lk51XAepRFMxGMWrrNRNqGy5idyGG49DGvU8qZs93ZonzG544ayfPkiy0OACg==', mktID='1012', tip='ID_SELL', qty='12', price='0.4', orderID='1519298308856', expires='5496' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEZy4rHuPNTVIF0S8bDuvOv/xd8lk51XAepRFMxGMWrrNRNqGy5idyGG49DGvU8qZs93ZonzG544ayfPkiy0OACg==', mktID='1013', tip='ID_SELL', qty='20', price='1.6', orderID='1519298478026', expires='5497' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEZy4rHuPNTVIF0S8bDuvOv/xd8lk51XAepRFMxGMWrrNRNqGy5idyGG49DGvU8qZs93ZonzG544ayfPkiy0OACg==', mktID='1014', tip='ID_SELL', qty='5', price='0.8', orderID='1519298305125', expires='5497' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEZy4rHuPNTVIF0S8bDuvOv/xd8lk51XAepRFMxGMWrrNRNqGy5idyGG49DGvU8qZs93ZonzG544ayfPkiy0OACg==', mktID='1015', tip='ID_SELL', qty='4', price='1.5', orderID='1519298827910', expires='5498' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEZy4rHuPNTVIF0S8bDuvOv/xd8lk51XAepRFMxGMWrrNRNqGy5idyGG49DGvU8qZs93ZonzG544ayfPkiy0OACg==', mktID='1016', tip='ID_SELL', qty='2', price='1.5', orderID='1519298962308', expires='5498' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEgSWDdz5gagj9+wqKkyx3pbVxSEI/HO/9CNulK2A21bimNviLaSvQL0hY3+8fhpV4HtneIo17Tl0JFi+MXtHnow==', mktID='3232', tip='ID_SELL', qty='5', price='0.5', orderID='1519300882847', expires='5563' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEIJgcAqfDFtc4U7uTHA9BdOyRdwWOZO+QRCCx/diAea2NJcepJUccVYwPaKu+00MqOzFDt/45OWnUdOh3r1/HbA==', mktID='765555', tip='ID_SELL', qty='32', price='4.0', orderID='1519301781573', expires='5580' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEnR5rrMg7mV2hpwg1QJJFHgS1iVs0WlRk11PbDHgPxtiDKVVfezPbPbqTHK6cezVXzBJLuO3yo77jupzwW+i58w==', mktID='454354343', tip='ID_SELL', qty='6', price='1.2', orderID='1519302434557', expires='5603' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAERhB3v1l25mWHAx6c+RNxjYITVOEN5++DwAVVLtu7I/olXVA5LGw5kGBQ+P2R8isXcgbsCMrVDBppaglAZGUL7w==', mktID='90305322', tip='ID_SELL', qty='875', price='0.01', orderID='1519470227606', expires='6074' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAELPTyUhfXMdR4Ly0iBr53leJ5wfk5vxaQrQAbc2XQe+VCg+koyI0ikw9rGZUJpvXJjZb7qDf/D9JOXMFdA7Ew+w==', mktID='4545665', tip='ID_SELL', qty='50', price='0.1', orderID='1519470723234', expires='6092' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAE9z9DHGH9Og1L/49bpt3pNZ0wJ94bKpG28x7cm5trSC4fxlmQDV4Q34eXXR9RfcZzMAn70ozzivRys8wiviGsjg==', mktID='74075874', tip='ID_SELL', qty='50', price='0.1', orderID='1519470860098', expires='6092' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEiixeumhGroIz9g6hQOw8Qs8O4RaxtgRSrLVie8x0OSQfB/sphLpK61wBmltPpMD1ifQtKAmBSb9QlPatfUnJtA==', mktID='5704028', tip='ID_SELL', qty='50', price='0.1', orderID='1519470498893', expires='6093' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEm6CHKqaz/x4z1cjO6YOIvu+vRstrTnB09caSnlmTmEuE9T3DqhuJmIORx6vGm+MnZnpGJeMhnjL8IJMUhXJx+w==', mktID='450998', tip='ID_SELL', qty='1', price='1.0', orderID='1519471173060', expires='6100' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEEjBeu1mQpKHH0F1LeBu8bmIjCi57a2a2fefLBpsS2Pr33aqJp905JV5BybNIr5OmmBK9aBbINRRXFAFVHu9vFw==', mktID='49343022', tip='ID_SELL', qty='1186', price='0.05', orderID='1519471650545', expires='6107' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEa64yOW/5Mu9nHOeJqSfG4wLt+uadYmC7o8ObXeWcmKhYp7+3rMYiyqrX2vY2L5b+A1xt4zW2cZdQt4nvNOFspw==', mktID='76678788', tip='ID_SELL', qty='118', price='4.0', orderID='1519472291747', expires='6131' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAETrbEiNpON6iRewwr2ATHl8Nc86hz5iyHfv38HayhYqh7gd7EBDzury7VGliv+BeUd4jU/WPFHlUiNgcixYEDmQ==', mktID='73870279', tip='ID_SELL', qty='3', price='0.6', orderID='1519472514164', expires='6137' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEe5ULnLJQyn2jz4359P5aGzydO7gaLTDYlUS84/03yn3vftO8uvdfuJai5h83VOenf6GylB51MdwegnO0vr+CNA==', mktID='324444', tip='ID_SELL', qty='11', price='0.06', orderID='1519477013432', expires='6200' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477097411', tip='ID_SELL', qty='100', price='1.0', orderID='1519477076351', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477242963', tip='ID_SELL', qty='100', price='1.0', orderID='1519477468708', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476806816', tip='ID_SELL', qty='100', price='1.0', orderID='1519477396018', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477478078', tip='ID_SELL', qty='100', price='1.0', orderID='1519477429546', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477038120', tip='ID_SELL', qty='100', price='1.0', orderID='1519477454570', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477513337', tip='ID_SELL', qty='100', price='1.0', orderID='1519477267767', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477195117', tip='ID_SELL', qty='100', price='1.0', orderID='1519477021225', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477318872', tip='ID_SELL', qty='100', price='1.0', orderID='1519477131842', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477356894', tip='ID_SELL', qty='100', price='1.0', orderID='1519477273965', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477528854', tip='ID_SELL', qty='100', price='1.0', orderID='1519477217466', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477444121', tip='ID_SELL', qty='100', price='1.0', orderID='1519477386102', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477434993', tip='ID_SELL', qty='100', price='1.0', orderID='1519477201163', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477444186', tip='ID_SELL', qty='100', price='1.0', orderID='1519476948738', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477220026', tip='ID_SELL', qty='100', price='1.0', orderID='1519476955347', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477121665', tip='ID_SELL', qty='100', price='1.0', orderID='1519476801673', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476834447', tip='ID_SELL', qty='100', price='1.0', orderID='1519476865312', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477198834', tip='ID_SELL', qty='100', price='1.0', orderID='1519476878941', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477118824', tip='ID_SELL', qty='100', price='1.0', orderID='1519477703760', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477211103', tip='ID_SELL', qty='100', price='1.0', orderID='1519477230305', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477656505', tip='ID_SELL', qty='100', price='1.0', orderID='1519477572099', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476703722', tip='ID_SELL', qty='100', price='1.0', orderID='1519476785931', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476714121', tip='ID_SELL', qty='100', price='1.0', orderID='1519476945491', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476733611', tip='ID_SELL', qty='100', price='1.0', orderID='1519477032573', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477435884', tip='ID_SELL', qty='100', price='1.0', orderID='1519477056999', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477634000', tip='ID_SELL', qty='100', price='1.0', orderID='1519477176620', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477039389', tip='ID_SELL', qty='100', price='1.0', orderID='1519477311772', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477065762', tip='ID_SELL', qty='100', price='1.0', orderID='1519476707910', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477092724', tip='ID_SELL', qty='100', price='1.0', orderID='1519476862807', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477659348', tip='ID_SELL', qty='100', price='1.0', orderID='1519477440183', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477403677', tip='ID_SELL', qty='100', price='1.0', orderID='1519477023534', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477202571', tip='ID_SELL', qty='100', price='1.0', orderID='1519476996505', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477630186', tip='ID_SELL', qty='100', price='1.0', orderID='1519477597981', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476774651', tip='ID_SELL', qty='100', price='1.0', orderID='1519477503802', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477326486', tip='ID_SELL', qty='100', price='1.0', orderID='1519477126139', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477404760', tip='ID_SELL', qty='100', price='1.0', orderID='1519477648401', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476811746', tip='ID_SELL', qty='100', price='1.0', orderID='1519477260334', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477447535', tip='ID_SELL', qty='100', price='1.0', orderID='1519477678072', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477417796', tip='ID_SELL', qty='100', price='1.0', orderID='1519477188544', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477638756', tip='ID_SELL', qty='100', price='1.0', orderID='1519476885448', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477411438', tip='ID_SELL', qty='100', price='1.0', orderID='1519477024085', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476706591', tip='ID_SELL', qty='100', price='1.0', orderID='1519477308077', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477156378', tip='ID_SELL', qty='100', price='1.0', orderID='1519477099738', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476968835', tip='ID_SELL', qty='100', price='1.0', orderID='1519477082746', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476982885', tip='ID_SELL', qty='100', price='1.0', orderID='1519476990749', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476810666', tip='ID_SELL', qty='100', price='1.0', orderID='1519477112726', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477262718', tip='ID_SELL', qty='100', price='1.0', orderID='1519477088451', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477593126', tip='ID_SELL', qty='100', price='1.0', orderID='1519477478468', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477476756', tip='ID_SELL', qty='100', price='1.0', orderID='1519477538218', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476937518', tip='ID_SELL', qty='100', price='1.0', orderID='1519477642797', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477637293', tip='ID_SELL', qty='100', price='1.0', orderID='1519477116553', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477528598', tip='ID_SELL', qty='100', price='1.0', orderID='1519476784383', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477451281', tip='ID_SELL', qty='100', price='1.0', orderID='1519477067333', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477409176', tip='ID_SELL', qty='100', price='1.0', orderID='1519477117264', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477640586', tip='ID_SELL', qty='100', price='1.0', orderID='1519477352245', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477044957', tip='ID_SELL', qty='100', price='1.0', orderID='1519477414174', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477066790', tip='ID_SELL', qty='100', price='1.0', orderID='1519477141632', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477331736', tip='ID_SELL', qty='100', price='1.0', orderID='1519477007781', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476828163', tip='ID_SELL', qty='100', price='1.0', orderID='1519477516025', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476823273', tip='ID_SELL', qty='100', price='1.0', orderID='1519477672580', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477532772', tip='ID_SELL', qty='100', price='1.0', orderID='1519477243406', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477168896', tip='ID_SELL', qty='100', price='1.0', orderID='1519477132839', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477122273', tip='ID_SELL', qty='100', price='1.0', orderID='1519477510594', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477472458', tip='ID_SELL', qty='100', price='1.0', orderID='1519477655644', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477184700', tip='ID_SELL', qty='100', price='1.0', orderID='1519477628055', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476691419', tip='ID_SELL', qty='100', price='1.0', orderID='1519477695968', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476800883', tip='ID_SELL', qty='100', price='1.0', orderID='1519476895298', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477475120', tip='ID_SELL', qty='100', price='1.0', orderID='1519476800822', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476907968', tip='ID_SELL', qty='100', price='1.0', orderID='1519477048733', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476948770', tip='ID_SELL', qty='100', price='1.0', orderID='1519477656419', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477615672', tip='ID_SELL', qty='100', price='1.0', orderID='1519477446317', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476854132', tip='ID_SELL', qty='100', price='1.0', orderID='1519476995430', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477197527', tip='ID_SELL', qty='100', price='1.0', orderID='1519477444581', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477394574', tip='ID_SELL', qty='100', price='1.0', orderID='1519477056145', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476812488', tip='ID_SELL', qty='100', price='1.0', orderID='1519477255779', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476852135', tip='ID_SELL', qty='100', price='1.0', orderID='1519476902868', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476744379', tip='ID_SELL', qty='100', price='1.0', orderID='1519477038375', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477064100', tip='ID_SELL', qty='100', price='1.0', orderID='1519477443491', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476820865', tip='ID_SELL', qty='100', price='1.0', orderID='1519477566113', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476698503', tip='ID_SELL', qty='100', price='1.0', orderID='1519477698335', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477073842', tip='ID_SELL', qty='100', price='1.0', orderID='1519477673218', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476749860', tip='ID_SELL', qty='100', price='1.0', orderID='1519477331879', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476903669', tip='ID_SELL', qty='100', price='1.0', orderID='1519476810374', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477101723', tip='ID_SELL', qty='100', price='1.0', orderID='1519476719150', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477425573', tip='ID_SELL', qty='100', price='1.0', orderID='1519477632916', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEnq7avEZwFPjCC0pyr7v6irgUtXuRDfA73HJd5QBFqxaUW5bDCm2gK3C9yQet5+LFdl6lTk9c2d79iEMribwW8A==', mktID='1519476903395', tip='ID_SELL', qty='1', price='5.0', orderID='1519477250156', expires='6208' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEOqKDfCw4hfoxPDhdJuEWTRyHr9FXIpID0PS2mykzagk/o2ylYz7DJXMSx26hEUvh3HmoDD8Wsb8VC3+Q+Oebug==', mktID='1519477424175', tip='ID_SELL', qty='2', price='13.0', orderID='1519477197266', expires='6208' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEEdG/q22qA/GR1J7cNAq+t1OKfY1y6PzsHncPdQndZoa9P8pOmWf6vkr621WEGxkLhjhNpzLJsWOpu5/lZ1zKGA==', mktID='1519477499667', tip='ID_SELL', qty='2', price='5.0', orderID='1519478260320', expires='6213' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAERaMwVsXgpF2+CUcr69K65eLjJ3b6FTqaTR203IsVpxy7Ab1En20SFMKnqlwsTSgbKazoD5hpPDh/a/7O3kri0w==', mktID='1519027893537', tip='ID_SELL', qty='95', price='0.1', orderID='1519652004964', expires='6273' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAERaMwVsXgpF2+CUcr69K65eLjJ3b6FTqaTR203IsVpxy7Ab1En20SFMKnqlwsTSgbKazoD5hpPDh/a/7O3kri0w==', mktID='1519027893537', tip='ID_SELL', qty='100', price='0.11', orderID='1519651735641', expires='6274' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAERaMwVsXgpF2+CUcr69K65eLjJ3b6FTqaTR203IsVpxy7Ab1En20SFMKnqlwsTSgbKazoD5hpPDh/a/7O3kri0w==', mktID='1519027893537', tip='ID_SELL', qty='100', price='0.12', orderID='1519651899112', expires='6274' ");

    }
    
    
}

