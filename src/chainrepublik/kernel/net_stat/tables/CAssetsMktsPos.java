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
	 	 				              + "adr VARCHAR(250) NOT NULL DEFAULT '', "
	 	 				              + "mktID BIGINT NOT NULL DEFAULT 0, "
	 	 				              + "tip VARCHAR(10) NOT NULL DEFAULT '', "
	 	 				              + "qty DOUBLE(20, 8) NOT NULL DEFAULT 0, "
	 	 				              + "price DOUBLE(20, 8) NOT NULL DEFAULT 0, "
	 	 				              + "orderID BIGINT NOT NULL DEFAULT 0, "
                                                              + "cost DOUBLE(20, 8) NOT NULL DEFAULT 0, "
	 	 				              + "expires BIGINT NOT NULL DEFAULT 0)");
	 	 	   
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
            
            // Asset is share ?
            if (asset.length()==5)
                UTILS.BASIC.checkComOwner(asset, block);
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
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477414762', tip='ID_SELL', qty='100', price='1.0', orderID='1519477632917', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477326487', tip='ID_SELL', qty='100', price='1.0', orderID='1519477632918', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477168897', tip='ID_SELL', qty='100', price='1.0', orderID='1519477632919', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477262719', tip='ID_SELL', qty='100', price='1.0', orderID='1519477632920', expires='0' ");
        
                
        // Churchill cigars
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477113581', tip='ID_SELL', qty='100', price='0.01', orderID='1519477633917', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477113581', tip='ID_SELL', qty='200', price='0.02', orderID='1519477732918', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477113581', tip='ID_SELL', qty='300', price='0.03', orderID='1519477732919', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477113581', tip='ID_SELL', qty='400', price='0.04', orderID='1519477732920', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477113581', tip='ID_SELL', qty='500', price='0.05', orderID='1519477732921', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477113581', tip='ID_SELL', qty='600', price='0.06', orderID='1519477732922', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477113581', tip='ID_SELL', qty='700', price='0.07', orderID='1519477732923', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477113581', tip='ID_SELL', qty='800', price='0.08', orderID='1519477632924', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477113581', tip='ID_SELL', qty='900', price='0.09', orderID='1519477632925', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477113581', tip='ID_SELL', qty='1000', price='0.1', orderID='1519477632926', expires='0' ");
        
        // Panatella cigars
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477449890', tip='ID_SELL', qty='100', price='0.02', orderID='1519477632817', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477449890', tip='ID_SELL', qty='200', price='0.03', orderID='1519477632818', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477449890', tip='ID_SELL', qty='300', price='0.04', orderID='1519477632819', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477449890', tip='ID_SELL', qty='400', price='0.05', orderID='1519477632820', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477449890', tip='ID_SELL', qty='500', price='0.06', orderID='1519477632821', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477449890', tip='ID_SELL', qty='600', price='0.07', orderID='1519477632822', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477449890', tip='ID_SELL', qty='700', price='0.08', orderID='1519477632823', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477449890', tip='ID_SELL', qty='800', price='0.09', orderID='1519477632824', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477449890', tip='ID_SELL', qty='900', price='0.1', orderID='1519477632885', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477449890', tip='ID_SELL', qty='1000', price='0.11', orderID='1519477632826', expires='0' ");
        
        // Torpedo cigars
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476879916', tip='ID_SELL', qty='100', price='0.03', orderID='1519477632777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476879916', tip='ID_SELL', qty='200', price='0.04', orderID='1519477632778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476879916', tip='ID_SELL', qty='300', price='0.05', orderID='1519477632719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476879916', tip='ID_SELL', qty='400', price='0.06', orderID='1519477632720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476879916', tip='ID_SELL', qty='500', price='0.07', orderID='1519477632721', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476879916', tip='ID_SELL', qty='600', price='0.08', orderID='1519477632722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476879916', tip='ID_SELL', qty='700', price='0.09', orderID='1519477632723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476879916', tip='ID_SELL', qty='800', price='0.1', orderID='1519477632724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476879916', tip='ID_SELL', qty='900', price='0.11', orderID='1519477632725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476879916', tip='ID_SELL', qty='1000', price='0.12', orderID='1519477632726', expires='0' ");
        
        // Corona cigars
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476922660', tip='ID_SELL', qty='100', price='0.04', orderID='1519477631777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476922660', tip='ID_SELL', qty='200', price='0.05', orderID='1519477631778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476922660', tip='ID_SELL', qty='300', price='0.06', orderID='1519477631719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476922660', tip='ID_SELL', qty='400', price='0.07', orderID='1519477631720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476922660', tip='ID_SELL', qty='500', price='0.08', orderID='1519477631721', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476922660', tip='ID_SELL', qty='600', price='0.09', orderID='1519477631722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476922660', tip='ID_SELL', qty='700', price='0.1', orderID='1519477631723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476922660', tip='ID_SELL', qty='800', price='0.11', orderID='1519477631724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476922660', tip='ID_SELL', qty='900', price='0.12', orderID='1519477631725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476922660', tip='ID_SELL', qty='1000', price='0.13', orderID='1519477631726', expires='0' ");
        
        // Toro cigars
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477228046', tip='ID_SELL', qty='100', price='0.05', orderID='1519477634777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477228046', tip='ID_SELL', qty='200', price='0.06', orderID='1519477634778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477228046', tip='ID_SELL', qty='300', price='0.07', orderID='1519477634719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477228046', tip='ID_SELL', qty='400', price='0.08', orderID='1519477634720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477228046', tip='ID_SELL', qty='500', price='0.09', orderID='1519477634721', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477228046', tip='ID_SELL', qty='600', price='0.1', orderID='1519477634722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477228046', tip='ID_SELL', qty='700', price='0.11', orderID='1519477634723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477228046', tip='ID_SELL', qty='800', price='0.12', orderID='1519477634724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477228046', tip='ID_SELL', qty='900', price='0.13', orderID='1519477634725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477228046', tip='ID_SELL', qty='1000', price='0.14', orderID='1519477634726', expires='0' ");
              
        // Champaigne 
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476793754', tip='ID_SELL', qty='100', price='0.02', orderID='1519477635777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476793754', tip='ID_SELL', qty='200', price='0.03', orderID='1519477635778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476793754', tip='ID_SELL', qty='300', price='0.04', orderID='1519477635719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476793754', tip='ID_SELL', qty='400', price='0.05', orderID='1519477635720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476793754', tip='ID_SELL', qty='500', price='0.06', orderID='1519477635721', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476793754', tip='ID_SELL', qty='600', price='0.07', orderID='1519477635722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476793754', tip='ID_SELL', qty='700', price='0.08', orderID='1519477635723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476793754', tip='ID_SELL', qty='800', price='0.09', orderID='1519477635724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476793754', tip='ID_SELL', qty='900', price='0.1', orderID='1519477635725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476793754', tip='ID_SELL', qty='1000', price='0.11', orderID='1519477635726', expires='0' ");
             
        // Martini 
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477093884', tip='ID_SELL', qty='100', price='0.03', orderID='1519477135777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477093884', tip='ID_SELL', qty='200', price='0.04', orderID='1519477135778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477093884', tip='ID_SELL', qty='300', price='0.05', orderID='1519477135719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477093884', tip='ID_SELL', qty='400', price='0.06', orderID='1519477135720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477093884', tip='ID_SELL', qty='500', price='0.07', orderID='1519477135721', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477093884', tip='ID_SELL', qty='600', price='0.08', orderID='1519477135722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477093884', tip='ID_SELL', qty='700', price='0.09', orderID='1519477135723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477093884', tip='ID_SELL', qty='800', price='0.1', orderID='1519477135724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477093884', tip='ID_SELL', qty='900', price='0.11', orderID='1519477135725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477093884', tip='ID_SELL', qty='1000', price='0.12', orderID='1519477135726', expires='0' ");
        
        // Mojito 
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477441993', tip='ID_SELL', qty='100', price='0.04', orderID='1519477235777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477441993', tip='ID_SELL', qty='200', price='0.05', orderID='1519477235778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477441993', tip='ID_SELL', qty='300', price='0.06', orderID='1519477235719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477441993', tip='ID_SELL', qty='400', price='0.07', orderID='1519477235720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477441993', tip='ID_SELL', qty='500', price='0.08', orderID='1519477235721', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477441993', tip='ID_SELL', qty='600', price='0.09', orderID='1519477235722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477441993', tip='ID_SELL', qty='700', price='0.1', orderID='1519477235723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477441993', tip='ID_SELL', qty='800', price='0.11', orderID='1519477235724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477441993', tip='ID_SELL', qty='900', price='0.12', orderID='1519477235725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477441993', tip='ID_SELL', qty='1000', price='0.13', orderID='1519477235726', expires='0' ");
        
        // Bloody mary 
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476993131', tip='ID_SELL', qty='100', price='0.05', orderID='1519477335777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476993131', tip='ID_SELL', qty='200', price='0.06', orderID='1519477335778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476993131', tip='ID_SELL', qty='300', price='0.07', orderID='1519477335719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476993131', tip='ID_SELL', qty='400', price='0.08', orderID='1519477335720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476993131', tip='ID_SELL', qty='500', price='0.09', orderID='1519477335721', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476993131', tip='ID_SELL', qty='600', price='0.1', orderID='1519477335722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476993131', tip='ID_SELL', qty='700', price='0.11', orderID='1519477335723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476993131', tip='ID_SELL', qty='800', price='0.12', orderID='1519477335724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476993131', tip='ID_SELL', qty='900', price='0.13', orderID='1519477335725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476993131', tip='ID_SELL', qty='1000', price='0.14', orderID='1519477335726', expires='0' ");
        
        // Singapore sling 
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477369525', tip='ID_SELL', qty='100', price='0.06', orderID='1519477345777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477369525', tip='ID_SELL', qty='200', price='0.07', orderID='1519477345778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477369525', tip='ID_SELL', qty='300', price='0.08', orderID='1519477345719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477369525', tip='ID_SELL', qty='400', price='0.09', orderID='1519477345720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477369525', tip='ID_SELL', qty='500', price='0.1', orderID='1519477344721', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477369525', tip='ID_SELL', qty='600', price='0.11', orderID='1519477345722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477369525', tip='ID_SELL', qty='700', price='0.12', orderID='1519477345723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477369525', tip='ID_SELL', qty='800', price='0.13', orderID='1519477345724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477369525', tip='ID_SELL', qty='900', price='0.14', orderID='1519477345725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477369525', tip='ID_SELL', qty='1000', price='0.15', orderID='1519477345726', expires='0' ");
        
        // Singapore sling 
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477595397', tip='ID_SELL', qty='100', price='0.07', orderID='1519477355777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477595397', tip='ID_SELL', qty='200', price='0.08', orderID='1519477355778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477595397', tip='ID_SELL', qty='300', price='0.09', orderID='1519477355719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477595397', tip='ID_SELL', qty='400', price='0.1', orderID='1519477355720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477595397', tip='ID_SELL', qty='500', price='0.11', orderID='1519477345721', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477595397', tip='ID_SELL', qty='600', price='0.12', orderID='1519477355722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477595397', tip='ID_SELL', qty='700', price='0.13', orderID='1519477355723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477595397', tip='ID_SELL', qty='800', price='0.14', orderID='1519477355724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477595397', tip='ID_SELL', qty='900', price='0.15', orderID='1519477355725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477595397', tip='ID_SELL', qty='1000', price='0.16', orderID='1519477355726', expires='0' ");
        
        // Croissant market
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477526354', tip='ID_SELL', qty='100', price='0.03', orderID='1519477155777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477526354', tip='ID_SELL', qty='200', price='0.04', orderID='1519477155778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477526354', tip='ID_SELL', qty='300', price='0.05', orderID='1519477155719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477526354', tip='ID_SELL', qty='400', price='0.06', orderID='1519477155720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477526354', tip='ID_SELL', qty='500', price='0.07', orderID='1519477145721', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477526354', tip='ID_SELL', qty='600', price='0.08', orderID='1519477155722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477526354', tip='ID_SELL', qty='700', price='0.09', orderID='1519477155723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477526354', tip='ID_SELL', qty='800', price='0.1', orderID='1519477155724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477526354', tip='ID_SELL', qty='900', price='0.11', orderID='1519477155725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477526354', tip='ID_SELL', qty='1000', price='0.12', orderID='1519477155726', expires='0' ");
             
        // Hot dog market
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477665863', tip='ID_SELL', qty='100', price='0.04', orderID='1519477156777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477665863', tip='ID_SELL', qty='200', price='0.05', orderID='1519477156778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477665863', tip='ID_SELL', qty='300', price='0.06', orderID='1519477156719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477665863', tip='ID_SELL', qty='400', price='0.07', orderID='1519477156720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477665863', tip='ID_SELL', qty='500', price='0.08', orderID='1519477145621', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477665863', tip='ID_SELL', qty='600', price='0.09', orderID='1519477156722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477665863', tip='ID_SELL', qty='700', price='0.1', orderID='1519477156723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477665863', tip='ID_SELL', qty='800', price='0.11', orderID='1519477156724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477665863', tip='ID_SELL', qty='900', price='0.12', orderID='1519477156725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477665863', tip='ID_SELL', qty='1000', price='0.13', orderID='1519477156726', expires='0' ");
        
        // Pasta market
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477004359', tip='ID_SELL', qty='100', price='0.05', orderID='1519477157777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477004359', tip='ID_SELL', qty='200', price='0.06', orderID='1519477157778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477004359', tip='ID_SELL', qty='300', price='0.07', orderID='1519477157719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477004359', tip='ID_SELL', qty='400', price='0.08', orderID='1519477157720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477004359', tip='ID_SELL', qty='500', price='0.09', orderID='1519477145722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477004359', tip='ID_SELL', qty='600', price='0.1', orderID='1519477157722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477004359', tip='ID_SELL', qty='700', price='0.11', orderID='1519477157723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477004359', tip='ID_SELL', qty='800', price='0.12', orderID='1519477157724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477004359', tip='ID_SELL', qty='900', price='0.13', orderID='1519477157725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477004359', tip='ID_SELL', qty='1000', price='0.14', orderID='1519477157726', expires='0' ");
             
        // Burger market
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477439954', tip='ID_SELL', qty='100', price='0.06', orderID='1519477158777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477439954', tip='ID_SELL', qty='200', price='0.07', orderID='1519477158778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477439954', tip='ID_SELL', qty='300', price='0.08', orderID='1519477158719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477439954', tip='ID_SELL', qty='400', price='0.09', orderID='1519477158720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477439954', tip='ID_SELL', qty='500', price='0.1', orderID='1519477145821', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477439954', tip='ID_SELL', qty='600', price='0.11', orderID='1519477158722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477439954', tip='ID_SELL', qty='700', price='0.12', orderID='1519477158723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477439954', tip='ID_SELL', qty='800', price='0.13', orderID='1519477158724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477439954', tip='ID_SELL', qty='900', price='0.14', orderID='1519477158725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477439954', tip='ID_SELL', qty='1000', price='0.15', orderID='1519477158726', expires='0' ");
         
        // Big burger market
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477198166', tip='ID_SELL', qty='100', price='0.07', orderID='1519477159777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477198166', tip='ID_SELL', qty='200', price='0.08', orderID='1519477159778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477198166', tip='ID_SELL', qty='300', price='0.09', orderID='1519477159719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477198166', tip='ID_SELL', qty='400', price='0.1', orderID='1519477159720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477198166', tip='ID_SELL', qty='500', price='0.11', orderID='1519477145921', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477198166', tip='ID_SELL', qty='600', price='0.12', orderID='1519477159722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477198166', tip='ID_SELL', qty='700', price='0.13', orderID='1519477159723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477198166', tip='ID_SELL', qty='800', price='0.14', orderID='1519477159724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477198166', tip='ID_SELL', qty='900', price='0.15', orderID='1519477159725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477198166', tip='ID_SELL', qty='1000', price='0.16', orderID='1519477159726', expires='0' ");
        
        // Pizza market
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477320495', tip='ID_SELL', qty='100', price='0.08', orderID='1519477259777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477320495', tip='ID_SELL', qty='200', price='0.09', orderID='1519477259778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477320495', tip='ID_SELL', qty='300', price='0.1', orderID='1519477259719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477320495', tip='ID_SELL', qty='400', price='0.11', orderID='1519477259720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477320495', tip='ID_SELL', qty='500', price='0.12', orderID='1519477245921', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477320495', tip='ID_SELL', qty='600', price='0.13', orderID='1519477259722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477320495', tip='ID_SELL', qty='700', price='0.14', orderID='1519477259723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477320495', tip='ID_SELL', qty='800', price='0.15', orderID='1519477259724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477320495', tip='ID_SELL', qty='900', price='0.16', orderID='1519477259725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477320495', tip='ID_SELL', qty='1000', price='0.17', orderID='1519477259726', expires='0' ");
        
        // Wine market
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476935721', tip='ID_SELL', qty='100', price='0.1', orderID='1519477359777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476935721', tip='ID_SELL', qty='200', price='0.2', orderID='1519477359778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476935721', tip='ID_SELL', qty='300', price='0.3', orderID='1519477359719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476935721', tip='ID_SELL', qty='400', price='0.4', orderID='1519477359720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476935721', tip='ID_SELL', qty='500', price='0.5', orderID='1519477345921', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476935721', tip='ID_SELL', qty='600', price='0.6', orderID='1519477359722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476935721', tip='ID_SELL', qty='700', price='0.7', orderID='1519477359723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476935721', tip='ID_SELL', qty='800', price='0.8', orderID='1519477359724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476935721', tip='ID_SELL', qty='900', price='0.9', orderID='1519477359725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476935721', tip='ID_SELL', qty='1000', price='1', orderID='1519477359726', expires='0' ");
        
        // Socks market
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477503588', tip='ID_SELL', qty='100', price='0.5', orderID='1519477369777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477503588', tip='ID_SELL', qty='200', price='0.6', orderID='1519477369778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477503588', tip='ID_SELL', qty='300', price='0.7', orderID='1519477369719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477503588', tip='ID_SELL', qty='400', price='0.8', orderID='1519477369720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477503588', tip='ID_SELL', qty='500', price='0.9', orderID='1519477365921', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477503588', tip='ID_SELL', qty='600', price='1.0', orderID='1519477369722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477503588', tip='ID_SELL', qty='700', price='1.2', orderID='1519477369723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477503588', tip='ID_SELL', qty='800', price='1.3', orderID='1519477369724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477503588', tip='ID_SELL', qty='900', price='1.4', orderID='1519477369725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477503588', tip='ID_SELL', qty='1000', price='1.5', orderID='1519477369726', expires='0' ");
              
        // Shirt market
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476992011', tip='ID_SELL', qty='100', price='0.9', orderID='1519477469777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476992011', tip='ID_SELL', qty='200', price='1.0', orderID='1519477469778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476992011', tip='ID_SELL', qty='300', price='1.1', orderID='1519477469719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476992011', tip='ID_SELL', qty='400', price='1.2', orderID='1519477469720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476992011', tip='ID_SELL', qty='500', price='1.3', orderID='1519477465921', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476992011', tip='ID_SELL', qty='600', price='1.4', orderID='1519477469722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476992011', tip='ID_SELL', qty='700', price='1.5', orderID='1519477469723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476992011', tip='ID_SELL', qty='800', price='1.6', orderID='1519477469724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476992011', tip='ID_SELL', qty='900', price='1.7', orderID='1519477469725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476992011', tip='ID_SELL', qty='1000', price='1.8', orderID='1519477469726', expires='0' ");
        
        // Boots market
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477460133', tip='ID_SELL', qty='100', price='1.5', orderID='1519478469777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477460133', tip='ID_SELL', qty='200', price='1.6', orderID='1519478469778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477460133', tip='ID_SELL', qty='300', price='1.7', orderID='1519478469719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477460133', tip='ID_SELL', qty='400', price='1.8', orderID='1519478469720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477460133', tip='ID_SELL', qty='500', price='1.9', orderID='1519478465921', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477460133', tip='ID_SELL', qty='600', price='2.0', orderID='1519478469722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477460133', tip='ID_SELL', qty='700', price='2.1', orderID='1519478469723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477460133', tip='ID_SELL', qty='800', price='2.2', orderID='1519478469724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477460133', tip='ID_SELL', qty='900', price='2.3', orderID='1519478469725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477460133', tip='ID_SELL', qty='1000', price='2.4', orderID='1519478469726', expires='0' ");
           
        // Pants market
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476974342', tip='ID_SELL', qty='100', price='1.8', orderID='1519479469777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476974342', tip='ID_SELL', qty='200', price='1.9', orderID='1519479469778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476974342', tip='ID_SELL', qty='300', price='2.0', orderID='1519479469719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476974342', tip='ID_SELL', qty='400', price='2.1', orderID='1519479469720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476974342', tip='ID_SELL', qty='500', price='2.2', orderID='1519479465921', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476974342', tip='ID_SELL', qty='600', price='2.3', orderID='1519479469722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476974342', tip='ID_SELL', qty='700', price='2.4', orderID='1519478499723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476974342', tip='ID_SELL', qty='800', price='2.5', orderID='1519479469724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476974342', tip='ID_SELL', qty='900', price='2.6', orderID='1519479469725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476974342', tip='ID_SELL', qty='1000', price='2.7', orderID='1519479469726', expires='0' ");
        
        // Sweaters market
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476874087', tip='ID_SELL', qty='100', price='2.2', orderID='1519579469777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476874087', tip='ID_SELL', qty='200', price='2.3', orderID='1519579469778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476874087', tip='ID_SELL', qty='300', price='2.4', orderID='1519579469719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476874087', tip='ID_SELL', qty='400', price='2.5', orderID='1519579469720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476874087', tip='ID_SELL', qty='500', price='2.6', orderID='1519579465921', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476874087', tip='ID_SELL', qty='600', price='2.7', orderID='1519579469722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476874087', tip='ID_SELL', qty='700', price='2.8', orderID='1519578499723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476874087', tip='ID_SELL', qty='800', price='2.9', orderID='1519579469724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476874087', tip='ID_SELL', qty='900', price='3.0', orderID='1519579469725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476874087', tip='ID_SELL', qty='1000', price='3.1', orderID='1519579469726', expires='0' ");
        
        // Coats market
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476900023', tip='ID_SELL', qty='100', price='3', orderID='1519679469777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476900023', tip='ID_SELL', qty='200', price='3.1', orderID='1519679469778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476900023', tip='ID_SELL', qty='300', price='3.2', orderID='1519679469719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476900023', tip='ID_SELL', qty='400', price='3.3', orderID='1519679469720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476900023', tip='ID_SELL', qty='500', price='3.4', orderID='1519679465921', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476900023', tip='ID_SELL', qty='600', price='3.5', orderID='1519679469722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476900023', tip='ID_SELL', qty='700', price='3.6', orderID='1519678499723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476900023', tip='ID_SELL', qty='800', price='3.7', orderID='1519679469724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476900023', tip='ID_SELL', qty='900', price='3.8', orderID='1519679469725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476900023', tip='ID_SELL', qty='1000', price='3.9', orderID='1519679469726', expires='0' ");
        
        // Silver rings market
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477161201', tip='ID_SELL', qty='100', price='1', orderID='1519679479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477161201', tip='ID_SELL', qty='200', price='1.1', orderID='1519679479778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477161201', tip='ID_SELL', qty='300', price='1.2', orderID='1519679479719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477161201', tip='ID_SELL', qty='400', price='1.3', orderID='1519679479720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477161201', tip='ID_SELL', qty='500', price='1.4', orderID='1519679467921', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477161201', tip='ID_SELL', qty='600', price='1.5', orderID='1519679479722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477161201', tip='ID_SELL', qty='700', price='1.6', orderID='1519678479723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477161201', tip='ID_SELL', qty='800', price='1.7', orderID='1519679479724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477161201', tip='ID_SELL', qty='900', price='1.8', orderID='1519679479725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477161201', tip='ID_SELL', qty='1000', price='1.9', orderID='1519679769726', expires='0' ");
         
        // Silver earings market
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477343065', tip='ID_SELL', qty='100', price='2', orderID='1518679479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477343065', tip='ID_SELL', qty='200', price='2.1', orderID='1518679479778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477343065', tip='ID_SELL', qty='300', price='2.2', orderID='1518679479719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477343065', tip='ID_SELL', qty='400', price='2.3', orderID='1518679479720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477343065', tip='ID_SELL', qty='500', price='2.4', orderID='1518679467921', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477343065', tip='ID_SELL', qty='600', price='2.5', orderID='1518679479722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477343065', tip='ID_SELL', qty='700', price='2.6', orderID='1518678479723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477343065', tip='ID_SELL', qty='800', price='2.7', orderID='1518679479724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477343065', tip='ID_SELL', qty='900', price='2.8', orderID='1518679479725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477343065', tip='ID_SELL', qty='1000', price='2.9', orderID='1518679769726', expires='0' ");
         
        // Silver pandant market
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476942672', tip='ID_SELL', qty='100', price='3', orderID='1517679479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476942672', tip='ID_SELL', qty='200', price='3.1', orderID='1517679479778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476942672', tip='ID_SELL', qty='300', price='3.2', orderID='1517679479719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476942672', tip='ID_SELL', qty='400', price='3.3', orderID='1517679479720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476942672', tip='ID_SELL', qty='500', price='3.4', orderID='1517679467921', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476942672', tip='ID_SELL', qty='600', price='3.5', orderID='1517679479722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476942672', tip='ID_SELL', qty='700', price='3.6', orderID='1517678479723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476942672', tip='ID_SELL', qty='800', price='3.7', orderID='1517679479724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476942672', tip='ID_SELL', qty='900', price='3.8', orderID='1517679479725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476942672', tip='ID_SELL', qty='1000', price='3.9', orderID='1517679769726', expires='0' ");
        
        // Silver watch market
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476879764', tip='ID_SELL', qty='100', price='4', orderID='1516679479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476879764', tip='ID_SELL', qty='200', price='4.1', orderID='1516679479778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476879764', tip='ID_SELL', qty='300', price='4.2', orderID='1516679479719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476879764', tip='ID_SELL', qty='400', price='4.3', orderID='1516679479720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476879764', tip='ID_SELL', qty='500', price='4.4', orderID='1516679467921', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476879764', tip='ID_SELL', qty='600', price='4.5', orderID='1516679479722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476879764', tip='ID_SELL', qty='700', price='4.6', orderID='1516678479723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476879764', tip='ID_SELL', qty='800', price='4.7', orderID='1516679479724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476879764', tip='ID_SELL', qty='900', price='4.8', orderID='1516679479725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476879764', tip='ID_SELL', qty='1000', price='4.9', orderID='1516679769726', expires='0' ");
          
        // Silver bracelet market
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476777632', tip='ID_SELL', qty='100', price='5', orderID='1516779479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476777632', tip='ID_SELL', qty='200', price='5.1', orderID='1516779479778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476777632', tip='ID_SELL', qty='300', price='5.2', orderID='1516779479719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476777632', tip='ID_SELL', qty='400', price='5.3', orderID='1516779479720', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476777632', tip='ID_SELL', qty='500', price='5.4', orderID='1516779467921', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476777632', tip='ID_SELL', qty='600', price='5.5', orderID='1516779479722', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476777632', tip='ID_SELL', qty='700', price='5.6', orderID='1516778479723', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476777632', tip='ID_SELL', qty='800', price='5.7', orderID='1516779479724', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476777632', tip='ID_SELL', qty='900', price='5.8', orderID='1516779479725', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476777632', tip='ID_SELL', qty='1000', price=5.9', orderID='1516779769726', expires='0' ");
               
        // Cars
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477499667', tip='ID_SELL', qty='10', price='5', orderID='1526779479787', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477499667', tip='ID_SELL', qty='20', price='6', orderID='1526779478787', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477499667', tip='ID_SELL', qty='30', price='7', orderID='1526779477787', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477499667', tip='ID_SELL', qty='40', price='8', orderID='1526779476787', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477499667', tip='ID_SELL', qty='50', price='9', orderID='1526779475787', expires='0' ");
        
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477079521', tip='ID_SELL', qty='10', price='10', orderID='1126779479798', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477079521', tip='ID_SELL', qty='20', price='11', orderID='1226779479798', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477079521', tip='ID_SELL', qty='30', price='12', orderID='1326779479798', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477079521', tip='ID_SELL', qty='40', price='13', orderID='1426779479798', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477079521', tip='ID_SELL', qty='50', price='14', orderID='1526779479798', expires='0' ");
        
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477022696', tip='ID_SELL', qty='100', price='15', orderID='1626779479769', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477022696', tip='ID_SELL', qty='100', price='16', orderID='1726779479769', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477022696', tip='ID_SELL', qty='100', price='17', orderID='1826779479769', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477022696', tip='ID_SELL', qty='100', price='18', orderID='1926779479769', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477022696', tip='ID_SELL', qty='100', price='19', orderID='2026779479769', expires='0' ");
        
        
        // Houses
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477586920', tip='ID_SELL', qty='10', price='25', orderID='2126789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477586920', tip='ID_SELL', qty='20', price='30', orderID='2226789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477586920', tip='ID_SELL', qty='30', price='35', orderID='2326789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477586920', tip='ID_SELL', qty='40', price='40', orderID='2426789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477586920', tip='ID_SELL', qty='50', price='45', orderID='2526789479777', expires='0' ");
        
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477653514', tip='ID_SELL', qty='10', price='50', orderID='2626799479778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477653514', tip='ID_SELL', qty='20', price='55', orderID='2726799479778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477653514', tip='ID_SELL', qty='30', price='60', orderID='2826799479778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477653514', tip='ID_SELL', qty='40', price='65', orderID='2926799479778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477653514', tip='ID_SELL', qty='50', price='70', orderID='3026799479778', expires='0' ");
        
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477536836', tip='ID_SELL', qty='10', price='75', orderID='3126769479719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477536836', tip='ID_SELL', qty='20', price='80', orderID='3226769479719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477536836', tip='ID_SELL', qty='30', price='85', orderID='3326769479719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477536836', tip='ID_SELL', qty='40', price='90', orderID='3426769479719', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477536836', tip='ID_SELL', qty='50', price='95', orderID='3526769479719', expires='0' ");
        
        // Tickets
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477235487', tip='ID_SELL', qty='100', price='0.01', orderID='3626789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477235487', tip='ID_SELL', qty='200', price='0.02', orderID='3726789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477235487', tip='ID_SELL', qty='300', price='0.03', orderID='3826789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477235487', tip='ID_SELL', qty='400', price='0.04', orderID='3926789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477235487', tip='ID_SELL', qty='500', price='0.05', orderID='4026789479777', expires='0' ");
        
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477577288', tip='ID_SELL', qty='100', price='0.02', orderID='4126789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477577288', tip='ID_SELL', qty='200', price='0.03', orderID='4226789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477577288', tip='ID_SELL', qty='300', price='0.04', orderID='4326789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477577288', tip='ID_SELL', qty='400', price='0.05', orderID='4426789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477577288', tip='ID_SELL', qty='500', price='0.06', orderID='4526789479777', expires='0' ");
        
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477080590', tip='ID_SELL', qty='100', price='0.03', orderID='4626789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477080590', tip='ID_SELL', qty='200', price='0.04', orderID='4726789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477080590', tip='ID_SELL', qty='300', price='0.05', orderID='4826789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477080590', tip='ID_SELL', qty='400', price='0.06', orderID='4926789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477080590', tip='ID_SELL', qty='500', price='0.07', orderID='5026789479777', expires='0' ");
        
        // Knifes
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477597441', tip='ID_SELL', qty='100', price='1', orderID='5126789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477597441', tip='ID_SELL', qty='200', price='1.1', orderID='5226789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477597441', tip='ID_SELL', qty='300', price='1.2', orderID='5326789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477597441', tip='ID_SELL', qty='400', price='1.3', orderID='5426789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477597441', tip='ID_SELL', qty='500', price='1.4', orderID='5526789479777', expires='0' ");
        
        // Pistols
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476777200', tip='ID_SELL', qty='100', price='2', orderID='1532422685239', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476777200', tip='ID_SELL', qty='200', price='2.1', orderID='1532422818336', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476777200', tip='ID_SELL', qty='300', price='2.2', orderID='1532422988861', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476777200', tip='ID_SELL', qty='400', price='2.3', orderID='1532422515146', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476777200', tip='ID_SELL', qty='500', price='2.4', orderID='1532422142389', expires='0' ");
           
        // Revolver
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477661551', tip='ID_SELL', qty='100', price='3', orderID='6126789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477661551', tip='ID_SELL', qty='200', price='3.1', orderID='6226789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477661551', tip='ID_SELL', qty='300', price='3.2', orderID='6326789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477661551', tip='ID_SELL', qty='400', price='3.3', orderID='6426789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477661551', tip='ID_SELL', qty='500', price='3.4', orderID='6526789479777', expires='0' ");
        
        // Shotguns
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477491019', tip='ID_SELL', qty='100', price='4', orderID='6225789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477491019', tip='ID_SELL', qty='200', price='4.1', orderID='6325789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477491019', tip='ID_SELL', qty='300', price='4.2', orderID='6425789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477491019', tip='ID_SELL', qty='400', price='4.3', orderID='6525789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477491019', tip='ID_SELL', qty='500', price='4.4', orderID='6625789479777', expires='0' ");
        
        // Shotguns
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477083239', tip='ID_SELL', qty='100', price='5', orderID='6726789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477083239', tip='ID_SELL', qty='200', price='5.1', orderID='6826789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477083239', tip='ID_SELL', qty='300', price='5.2', orderID='6926789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477083239', tip='ID_SELL', qty='400', price='5.3', orderID='7026789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477083239', tip='ID_SELL', qty='500', price='5.4', orderID='7126789479777', expires='0' ");
        
        // Sniper
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476899713', tip='ID_SELL', qty='100', price='6', orderID='7226789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476899713', tip='ID_SELL', qty='200', price='6.1', orderID='7326789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476899713', tip='ID_SELL', qty='300', price='6.2', orderID='7426789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476899713', tip='ID_SELL', qty='400', price='6.3', orderID='7526789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476899713', tip='ID_SELL', qty='500', price='6.4', orderID='7626789479777', expires='0' ");
        
        // Gloves
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476840023', tip='ID_SELL', qty='100', price='1', orderID='5636789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476840023', tip='ID_SELL', qty='200', price='1.1', orderID='6426789479778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476840023', tip='ID_SELL', qty='300', price='1.2', orderID='5656789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476840023', tip='ID_SELL', qty='400', price='1.3', orderID='5666789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476840023', tip='ID_SELL', qty='500', price='1.4', orderID='5676789479777', expires='0' ");
        
        // Goggles 
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477063151', tip='ID_SELL', qty='100', price='2', orderID='5686789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477063151', tip='ID_SELL', qty='200', price='2.1', orderID='5696789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477063151', tip='ID_SELL', qty='300', price='2.2', orderID='5706789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477063151', tip='ID_SELL', qty='400', price='2.3', orderID='5716789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477063151', tip='ID_SELL', qty='500', price='2.4', orderID='5726789479777', expires='0' ");
        
        // Helmets 
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476932277', tip='ID_SELL', qty='100', price='3', orderID='5736789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476932277', tip='ID_SELL', qty='200', price='3.1', orderID='5746789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476932277', tip='ID_SELL', qty='300', price='3.2', orderID='5756789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476932277', tip='ID_SELL', qty='400', price='3.3', orderID='5766789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476932277', tip='ID_SELL', qty='500', price='3.4', orderID='5776789479777', expires='0' ");
        
        // Boots 
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477474137', tip='ID_SELL', qty='100', price='4', orderID='5786789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477474137', tip='ID_SELL', qty='200', price='4.1', orderID='5796789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477474137', tip='ID_SELL', qty='300', price='4.2', orderID='5806789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477474137', tip='ID_SELL', qty='400', price='4.3', orderID='5816789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477474137', tip='ID_SELL', qty='500', price='4.4', orderID='5826789479777', expires='0' ");
        
        
        // Vests 
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476709462', tip='ID_SELL', qty='100', price='5', orderID='5836789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476709462', tip='ID_SELL', qty='200', price='5.1', orderID='5846789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476709462', tip='ID_SELL', qty='300', price='5.2', orderID='5856789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476709462', tip='ID_SELL', qty='400', price='5.3', orderID='5866789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476709462', tip='ID_SELL', qty='500', price='5.4', orderID='5876789479777', expires='0' ");
        
        
        // Shields 
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476977358', tip='ID_SELL', qty='100', price='6', orderID='5886789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476977358', tip='ID_SELL', qty='200', price='6.1', orderID='5896789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476977358', tip='ID_SELL', qty='300', price='6.2', orderID='5906789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476977358', tip='ID_SELL', qty='400', price='6.3', orderID='5916789479777', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476977358', tip='ID_SELL', qty='500', price='6.4', orderID='5926789479777', expires='0' ");
        
        // Navy destroyer 
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477643862', tip='ID_SELL', qty='10', price='50', orderID='1532422601950', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477643862', tip='ID_SELL', qty='10', price='100', orderID='1532422929923', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477643862', tip='ID_SELL', qty='10', price='150', orderID='1532422169728', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477643862', tip='ID_SELL', qty='10', price='200', orderID='1532423067375', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477643862', tip='ID_SELL', qty='10', price='250', orderID='1532422332166', expires='0' ");
        
        // Carrier 
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477254018', tip='ID_SELL', qty='10', price='100', orderID='1532422216421', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477254018', tip='ID_SELL', qty='10', price='200', orderID='1532422453380', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477254018', tip='ID_SELL', qty='10', price='300', orderID='1532422923532', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477254018', tip='ID_SELL', qty='10', price='400', orderID='1532422643541', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477254018', tip='ID_SELL', qty='10', price='500', orderID='1532422276844', expires='0' ");
       
        // Jet fighters 
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477254321', tip='ID_SELL', qty='10', price='25', orderID='1532422799349', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477254321', tip='ID_SELL', qty='10', price='50', orderID='1532422771631', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477254321', tip='ID_SELL', qty='10', price='75', orderID='1532422519227', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477254321', tip='ID_SELL', qty='10', price='100', orderID='1532423032361', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477254321', tip='ID_SELL', qty='10', price='125', orderID='1532422282741', expires='0' ");
       
        
        // Tanks 
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477518123', tip='ID_SELL', qty='10', price='10', orderID='1532422926360', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477518123', tip='ID_SELL', qty='10', price='20', orderID='1532422253353', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477518123', tip='ID_SELL', qty='10', price='30', orderID='1532422569608', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477518123', tip='ID_SELL', qty='10', price='40', orderID='1532423049617', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477518123', tip='ID_SELL', qty='10', price='50', orderID='1532422445814', expires='0' ");
       
        // Air to surface missile 
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477042651', tip='ID_SELL', qty='100', price='10', orderID='1532422391923', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477042651', tip='ID_SELL', qty='100', price='20', orderID='1532422590669', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477042651', tip='ID_SELL', qty='100', price='30', orderID='1532422932591', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477042651', tip='ID_SELL', qty='100', price='40', orderID='1532422617006', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477042651', tip='ID_SELL', qty='100', price='50', orderID='1532423032294', expires='0' ");
       
        
        // Surface to surface missile 
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476783531', tip='ID_SELL', qty='100', price='10', orderID='1532422434566', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476783531', tip='ID_SELL', qty='100', price='20', orderID='1532422174444', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476783531', tip='ID_SELL', qty='100', price='30', orderID='1532422339621', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476783531', tip='ID_SELL', qty='100', price='40', orderID='1532422983305', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476783531', tip='ID_SELL', qty='100', price='50', orderID='1532422782002', expires='0' ");
       
        
        // Balistic short 
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477311216', tip='ID_SELL', qty='100', price='10', orderID='1532423006508', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477311216', tip='ID_SELL', qty='100', price='20', orderID='1532422455799', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477311216', tip='ID_SELL', qty='100', price='30', orderID='1532423066514', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477311216', tip='ID_SELL', qty='100', price='40', orderID='1532422378713', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477311216', tip='ID_SELL', qty='100', price='50', orderID='1532422809996', expires='0' ");
       
        
        // Balistic medium 
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477007292', tip='ID_SELL', qty='100', price='20', orderID='1532422625450', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477007292', tip='ID_SELL', qty='100', price='30', orderID='1532422273525', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477007292', tip='ID_SELL', qty='100', price='40', orderID='1532422242660', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477007292', tip='ID_SELL', qty='100', price='50', orderID='1532422726148', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519477007292', tip='ID_SELL', qty='100', price='60', orderID='1532422304632', expires='0' ");
       
        
        // Balistic long 
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476762574', tip='ID_SELL', qty='100', price='30', orderID='1532422585212', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476762574', tip='ID_SELL', qty='100', price='40', orderID='1532422856441', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476762574', tip='ID_SELL', qty='100', price='50', orderID='1532422875014', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476762574', tip='ID_SELL', qty='100', price='60', orderID='1532422748785', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476762574', tip='ID_SELL', qty='100', price='70', orderID='1532423005759', expires='0' ");
       
        
        // Balistic inter 
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476752808', tip='ID_SELL', qty='100', price='40', orderID='1532422788684', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476752808', tip='ID_SELL', qty='100', price='50', orderID='1532422792900', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476752808', tip='ID_SELL', qty='100', price='60', orderID='1532422733082', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476752808', tip='ID_SELL', qty='100', price='70', orderID='1532422767678', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1519476752808', tip='ID_SELL', qty='100', price='80', orderID='1532423036794', expires='0' ");
       
    }
    
    
}

