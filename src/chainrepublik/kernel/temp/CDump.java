package chainrepublik.kernel.temp;

import chainrepublik.kernel.CAddress;
import chainrepublik.kernel.UTILS;
import java.sql.ResultSet;

public class CDump 
{
    public void dumpAllowTrans() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * FROM allow_trans");
        
        while (rs.next())
            System.out.println("UTILS.DB.executeUpdate(\"INSERT INTO allow_trans SET receiver_type='"+rs.getString("receiver_type")+"', prod='"+rs.getString("prod")+"', can_buy='"+rs.getString("can_buy")+"', can_sell='"+rs.getString("can_sell")+"', max_hold='"+rs.getString("max_hold")+"', is_limited='"+rs.getString("is_limited")+"', can_rent='"+rs.getString("can_rent")+"', buy_split='"+rs.getString("buy_split")+"', can_donate='"+rs.getString("can_donate")+"'\");");
    }
    
    public void dumpAssetsMkts() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * FROM assets_mkts WHERE adr='default'");
        
        while (rs.next())
            System.out.println("UTILS.DB.executeUpdate(\"INSERT INTO assets_mkts SET adr='"+rs.getString("adr")+"', asset='"+rs.getString("asset")+"', cur='"+rs.getString("cur")+"', name='"+rs.getString("name")+"', description='"+rs.getString("description")+"', decimals='"+rs.getString("decimals")+"', expires='"+rs.getString("expires")+"', last_price='"+rs.getString("last_price")+"', ask='"+rs.getString("ask")+"', bid='"+rs.getString("bid")+"', mktID='"+rs.getString("mktID")+"'\");");
    }
    
    public void dumpComProds() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * FROM com_prods");
        
        while (rs.next())
            System.out.println("UTILS.DB.executeUpdate(\"INSERT INTO com_prods SET com_type='"+rs.getString("com_type")+"', prod='"+rs.getString("prod")+"', type='"+rs.getString("type")+"', buy_split='"+rs.getString("buy_split")+"' \");");
    }
    
    public void dumpCountries() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * FROM countries");
        
        while (rs.next())
            System.out.println("UTILS.DB.executeUpdate(\"INSERT INTO countries SET adr='"+rs.getString("adr")+"', private='"+rs.getString("private")+"', owner='default', country='"+rs.getString("country")+"', code='"+rs.getString("code")+"', x='"+rs.getLong("x")+"', y='"+rs.getLong("y")+"', area='"+rs.getLong("area")+"', occupied='"+rs.getString("code")+"' \");");
    }
    
    public void dumpDelegates() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * FROM delegates");
        
        while (rs.next())
            System.out.println("UTILS.DB.executeUpdate(\"INSERT INTO delegates SET adr='"+rs.getString("adr")+"', delegate='"+rs.getString("delegate")+"', type='"+rs.getString("type")+"', power='"+rs.getLong("power")+"', y='"+rs.getLong("y")+"', area='"+rs.getLong("area")+"', occupied='"+rs.getString("occupied")+"' \");");
    }
    
    public void dumpTipuriCompanii() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * FROM tipuri_companii");
        
        while (rs.next())
            System.out.println("UTILS.DB.executeUpdate(\"INSERT INTO tipuri_companii SET tip='"+rs.getString("tip")+"', name='"+rs.getString("name")+"', cladire='"+rs.getString("cladire")+"', utilaje='"+rs.getString("utilaje")+"', pic='"+rs.getString("pic")+"' \");");
    }
    
    public void dumpTipuriLicente() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * FROM tipuri_licente");
        
        while (rs.next())
            System.out.println("UTILS.DB.executeUpdate(\"INSERT INTO tipuri_licente "
                    + "SET tip='"+rs.getString("tip")+"', name='"+rs.getString("name")+"', com_type='"+rs.getString("com_type")+"', price='"+rs.getDouble("price")+"', prod='"+rs.getString("prod")+"' \");");
    }
    
    public void dumpMktsPos() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * FROM assets_mkts_pos");
        
        while (rs.next())
            System.out.println("UTILS.DB.executeUpdate(\"INSERT INTO assets_mkts_pos "
                                                              + "SET adr='"+rs.getString("adr")+"', "
                                                                  + "mktID='"+rs.getLong("mktID")+"', "
                                                                  + "tip='"+rs.getString("tip")+"', "
                                                                  + "qty='"+rs.getLong("qty")+"', "
                                                                  + "price='"+rs.getDouble("price")+"', "
                                                                  + "orderID='"+rs.getLong("orderID")+"', "
                                                                  + "expires='"+rs.getLong("expires")+"' \");");
    }
    
    public void dumpTipuriProduse() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * FROM tipuri_produse");
        
        while (rs.next())
            System.out.println("UTILS.DB.executeUpdate(\"INSERT INTO tipuri_produse "
                                                              + "SET prod='"+rs.getString("prod")+"', "
                                                                  + "work_hours='"+rs.getString("work_hours")+"', "
                                                                  + "prod_1='"+rs.getString("prod_1")+"', "
                                                                  + "prod_1_qty='"+rs.getDouble("prod_1_qty")+"', "
                                                                  + "prod_2='"+rs.getString("prod_2")+"', "
                                                                  + "prod_2_qty='"+rs.getDouble("prod_2_qty")+"', "
                                                                  + "prod_3='"+rs.getString("prod_3")+"', "
                                                                  + "prod_3_qty='"+rs.getDouble("prod_3_qty")+"', "
                                                                  + "prod_4='"+rs.getString("prod_4")+"', "
                                                                  + "prod_4_qty='"+rs.getDouble("prod_4_qty")+"', "
                                                                  + "prod_5='"+rs.getString("prod_5")+"', "
                                                                  + "prod_5_qty='"+rs.getDouble("prod_5_qty")+"', "
                                                                  + "prod_6='"+rs.getString("prod_6")+"', "
                                                                  + "prod_6_qty='"+rs.getDouble("prod_6_qty")+"', "
                                                                  + "prod_7='"+rs.getString("prod_7")+"', "
                                                                  + "prod_7_qty='"+rs.getDouble("prod_7_qty")+"', "
                                                                  + "prod_8='"+rs.getString("prod_8")+"', "
                                                                  + "prod_8_qty='"+rs.getDouble("prod_8_qty")+"', "
                                                                  + "unitate='"+rs.getString("unitate")+"', "
                                                                  + "produced_by='"+rs.getString("produced_by")+"', "
                                                                  + "capacity='"+rs.getLong("capacity")+"', "
                                                                  + "name='"+rs.getString("name")+"', "
                                                                  + "expires='"+rs.getLong("expires")+"', "
                                                                  + "net_cost='"+rs.getDouble("net_cost")+"', "
                                                                  + "damage='"+rs.getLong("damage")+"' \");");
    }
  
    public void populateAssetsMkts() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM tipuri_companii");
        
        while (rs.next())
        {
            // Insert tools
            UTILS.DB.executeUpdate("INSERT INTO assets_mkts "
                                         + "SET adr='default', "
                                             + "mktID='"+UTILS.BASIC.getID()+"', "
                                             + "asset='"+rs.getString("utilaje")+"', "
                                             + "cur='CRC', "
                                             + "name='"+rs.getString("name")+" Production Tools', "
                                             + "description='"+rs.getString("name")+" Production Tools', "
                                             + "decimals=0, "
                                             + "expires=0, "
                                             + "ask=1, "
                                             + "bid=0");
            
            // Insert building
            UTILS.DB.executeUpdate("INSERT INTO assets_mkts "
                                         + "SET adr='default', "
                                             + "mktID='"+UTILS.BASIC.getID()+"', "
                                             + "asset='"+rs.getString("cladire")+"', "
                                             + "cur='CRC', "
                                             + "name='"+rs.getString("name")+" Building', "
                                             + "description='"+rs.getString("name")+" Building', "
                                             + "decimals=0, "
                                             + "expires=0, "
                                             + "ask=1, "
                                             + "bid=0");
        }
        
        // Tipuri_produse
        rs=UTILS.DB.executeQuery("SELECT * "
                                 + "FROM tipuri_produse "
                                + "WHERE prod NOT LIKE '%TOOLS_PROD%' "
                                  + "AND prod NOT LIKE '%BUILD_COM%'");
        
        
        while (rs.next())
            UTILS.DB.executeUpdate("INSERT INTO assets_mkts "
                                         + "SET adr='default', "
                                             + "mktID='"+UTILS.BASIC.getID()+"', "
                                             + "asset='"+rs.getString("prod")+"', "
                                             + "cur='CRC', "
                                             + "name='"+rs.getString("name")+" Market', "
                                             + "description='"+rs.getString("name")+" Market', "
                                             + "decimals=0, "
                                             + "expires=0, "
                                             + "ask=1, "
                                             + "bid=0");
    }
    
    public void populateAssetsMktsPos() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM assets_mkts "
                                          + "WHERE (asset "
                                           + "LIKE '%BUILD_COM%' OR asset LIKE '%TOOLS_PROD%')");
        
        while (rs.next())
            UTILS.DB.executeUpdate("INSERT INTO assets_mkts_pos "
                                         + "SET adr='default', "
                                             + "mktID='"+rs.getLong("mktID")+"', "
                                             + "tip='ID_SELL', "
                                             + "qty='100', "
                                             + "price='1', "
                                             + "orderID='"+UTILS.BASIC.getID()+"', "
                                             + "expires=0");
    }
    
    public void polParties(String cou, long no) throws Exception
    {
        for (int a=1; a<=no; a++)
        {
            CAddress adr=new CAddress();
            adr.generate();
            
            long ID=UTILS.BASIC.getID();
            UTILS.DB.executeUpdate("INSERT INTO orgs "
                                         + "SET orgID='"+ID+"', "
                                             + "type='ID_POL_PARTY', "
                                             + "country='"+cou+"', "
                                             + "avatar='"+ID+"', "
                                             + "adr='"+adr.getPublic()+"'");
        }
    }
    
    public void dumpOrgs() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * FROM orgs");
        
        while (rs.next())
            System.out.println("UTILS.DB.executeUpdate(\"INSERT INTO orgs SET adr='"+rs.getString("adr")+"', orgID='"+rs.getString("orgID")+"', type='"+rs.getString("type")+"', name='"+rs.getString("name")+"', description='"+rs.getString("description")+"', mil_unit_level='"+rs.getString("mil_unit_level")+"', country='"+rs.getString("country")+"', avatar='"+rs.getString("avatar")+"'\");");
    }
    
    public void dumpSeas() throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * FROM seas");
        
        while (rs.next())
            System.out.println("UTILS.DB.executeUpdate(\"INSERT INTO seas SET name='"+rs.getString("name")+"', posX='"+rs.getLong("posX")+"', posY='"+rs.getString("posY")+"', seaID='"+rs.getLong("seaID")+"'\");");
    
    }
    
    
}
