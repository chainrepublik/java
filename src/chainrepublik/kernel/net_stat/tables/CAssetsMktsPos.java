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
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='5848895', tip='ID_SELL', qty='100', price='1.0', orderID='1516782914799', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='4450881', tip='ID_SELL', qty='100', price='1.0', orderID='1516783186152', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1718270', tip='ID_SELL', qty='100', price='1.0', orderID='1516783640113', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='9197307', tip='ID_SELL', qty='100', price='1.0', orderID='1516783444335', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='665083', tip='ID_SELL', qty='100', price='1.0', orderID='1516782726042', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='443969', tip='ID_SELL', qty='100', price='1.0', orderID='1516783355015', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='9641930', tip='ID_SELL', qty='100', price='1.0', orderID='1516782799295', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='6369320', tip='ID_SELL', qty='100', price='1.0', orderID='1516782802314', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='5506758', tip='ID_SELL', qty='100', price='1.0', orderID='1516783596999', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='7214917', tip='ID_SELL', qty='100', price='1.0', orderID='1516783513505', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='9586802', tip='ID_SELL', qty='100', price='1.0', orderID='1516783311140', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='2420793', tip='ID_SELL', qty='100', price='1.0', orderID='1516783615969', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='4584674', tip='ID_SELL', qty='100', price='1.0', orderID='1516783191821', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='3798809', tip='ID_SELL', qty='100', price='1.0', orderID='1516783580950', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='8850755', tip='ID_SELL', qty='100', price='1.0', orderID='1516783127454', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='3105746', tip='ID_SELL', qty='100', price='1.0', orderID='1516783693450', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='8106858', tip='ID_SELL', qty='100', price='1.0', orderID='1516783402975', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='8902229', tip='ID_SELL', qty='100', price='1.0', orderID='1516783641530', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='2118149', tip='ID_SELL', qty='100', price='1.0', orderID='1516782970839', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='6346269', tip='ID_SELL', qty='100', price='1.0', orderID='1516782725579', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='4884004', tip='ID_SELL', qty='100', price='1.0', orderID='1516782745101', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1431968', tip='ID_SELL', qty='100', price='1.0', orderID='1516783167318', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='3660574', tip='ID_SELL', qty='100', price='1.0', orderID='1516783053753', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='5230881', tip='ID_SELL', qty='100', price='1.0', orderID='1516783308178', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='3577891', tip='ID_SELL', qty='100', price='1.0', orderID='1516783131559', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='131739', tip='ID_SELL', qty='100', price='1.0', orderID='1516783276591', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='4508983', tip='ID_SELL', qty='100', price='1.0', orderID='1516783593185', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='8868813', tip='ID_SELL', qty='100', price='1.0', orderID='1516783263685', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1750885', tip='ID_SELL', qty='100', price='1.0', orderID='1516783148788', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='3720333', tip='ID_SELL', qty='100', price='1.0', orderID='1516783557599', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='37534509', tip='ID_SELL', qty='100', price='1.0', orderID='1516783381002', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='833631', tip='ID_SELL', qty='100', price='1.0', orderID='1516783181239', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1321476', tip='ID_SELL', qty='100', price='1.0', orderID='1516782832637', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='9379817', tip='ID_SELL', qty='100', price='1.0', orderID='1516783151942', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='5152374', tip='ID_SELL', qty='100', price='1.0', orderID='1516782846606', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='8893708', tip='ID_SELL', qty='100', price='1.0', orderID='1516783502778', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='7206016', tip='ID_SELL', qty='100', price='1.0', orderID='1516783534244', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='8513902', tip='ID_SELL', qty='100', price='1.0', orderID='1516783196058', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='5443182', tip='ID_SELL', qty='100', price='1.0', orderID='1516783249259', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='8776088', tip='ID_SELL', qty='100', price='1.0', orderID='1516783144090', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='7271777', tip='ID_SELL', qty='100', price='1.0', orderID='1516783150642', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1552761', tip='ID_SELL', qty='100', price='1.0', orderID='1516783481132', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='6760102', tip='ID_SELL', qty='100', price='1.0', orderID='1516783473161', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='674218', tip='ID_SELL', qty='100', price='1.0', orderID='1516783559414', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='9243207', tip='ID_SELL', qty='100', price='1.0', orderID='1516783022507', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='4988860', tip='ID_SELL', qty='100', price='1.0', orderID='1516782974279', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='1476656', tip='ID_SELL', qty='100', price='1.0', orderID='1516783363868', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='4367038', tip='ID_SELL', qty='100', price='1.0', orderID='1516783403901', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='4167784', tip='ID_SELL', qty='100', price='1.0', orderID='1516783469416', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='919426', tip='ID_SELL', qty='100', price='1.0', orderID='1516782824457', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='6888956', tip='ID_SELL', qty='100', price='1.0', orderID='1516783016413', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='7968382', tip='ID_SELL', qty='100', price='1.0', orderID='1516783436070', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='6338396', tip='ID_SELL', qty='100', price='1.0', orderID='1516783513897', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='9438869', tip='ID_SELL', qty='100', price='1.0', orderID='1516783128173', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='4242144', tip='ID_SELL', qty='100', price='1.0', orderID='1516783053662', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='3793142', tip='ID_SELL', qty='100', price='1.0', orderID='1516783510355', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='8922712', tip='ID_SELL', qty='100', price='1.0', orderID='1516783692246', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='9278204', tip='ID_SELL', qty='100', price='1.0', orderID='1516782937383', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='2425631', tip='ID_SELL', qty='100', price='1.0', orderID='1516783377557', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='3445577', tip='ID_SELL', qty='100', price='1.0', orderID='1516782831388', expires='0' ");
        UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos SET adr='default', mktID='39736138', tip='ID_SELL', qty='100', price='1.0', orderID='1516783429676', expires='0' ");

    }
    
    
}

