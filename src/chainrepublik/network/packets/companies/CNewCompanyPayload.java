package chainrepublik.network.packets.companies;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CNewCompanyPayload extends CPayload
{
    // Address
    String com_adr; 
    
    // Type
    String type;
    
    // Name
    String name;
    
    // Description
    String desc; 
    
    // Symbol
    String symbol;
    
    // Country
    String cou;
    
    // Pic
    String pic;
    
    // Company ID
    long comID;
    
    // Asset ID
    long assetID;
    
    // Asset mkt ID
    long assetMktID;
    
    // Days
    long days;
    
    // ID's
    long stocID;   
    
    // Serial
    private static final long serialVersionUID = 100L;
    
                                      
    public CNewCompanyPayload(String adr, 
                              String type, 
                              String name, 
                              String desc, 
                              String symbol, 
                              String cou, 
                              String pic,
                              String com_adr,
                              long days) throws Exception
    {
        // Superclass
	super(adr);
        
        // Type
        this.type=type;
    
        // Name
        this.name=name;
    
        // Description
        this.desc=desc; 
    
        // Symbol
        this.symbol=symbol;
        
        // Country
        this.cou=cou;
        
        // Pic
        this.pic=pic;
        
        // Company ID
        this.comID=UTILS.BASIC.getID();
        
        // Asset ID
        this.assetID=UTILS.BASIC.getID();
        
        // Asset mkt ID
        this.assetMktID=UTILS.BASIC.getID();
        
        // Stoc ID
        this.stocID=UTILS.BASIC.getID();
    
        // Days
        this.days=days;
        
        // Company address adr
        this.com_adr=com_adr;
        
        // Hash
 	hash=UTILS.BASIC.hash(this.getHash()+
 			      this.type+
 			      this.name+
                              this.desc+
 			      this.symbol+
                              this.cou+
                              this.pic+
                              this.comID+
                              this.com_adr+
                              this.assetID+
                              this.assetMktID+
 			      this.stocID+
                              this.days);
    
    }
    
    public void check(CBlockPayload block) throws Exception
    {
   	// Super class
   	super.check(block);
        
        // Check for null
        if (this.com_adr==null ||
            this.cou==null ||
            this.name==null ||
            this.desc==null ||
            this.pic==null ||
            this.symbol==null ||
            this.target_adr==null)
        throw new Exception("Null assertion failed - CNewCompanyPayload.java, 68");
        
        // Energy
        this.checkEnergy();
        
        // Citizen
        if (!UTILS.BASIC.isCitAdr(this.target_adr, this.block))
           throw new Exception("Target address is not a citizen, CNewCompanyPayload.java, 102");
        
        // Registered owner
        if (UTILS.BASIC.traceAdr(this.com_adr))
            throw new Exception("Company address is not new, CNewCompanyPayload.java, 102");
        
        // Type
        if (!UTILS.BASIC.isStringID(this.type))
            throw new Exception("Invalid type, CNewCompanyPayload.java, 109");
        
        // Type valid
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM tipuri_companii "
                                          + "WHERE tip='"+this.type+"'");
        
        // Has data
        if (!UTILS.DB.hasData(rs))
            throw new Exception("Invalid type, CNewCompanyPayload.java, 118");
        
        // Name
        if (!UTILS.BASIC.isTitle(this.name))
            throw new Exception("Invalid name, CNewCompanyPayload.java, 122");
        
        // Description
        if (!UTILS.BASIC.isDesc(this.desc))
            throw new Exception("Invalid description, CNewCompanyPayload.java, 126");
        
        // Symbol
        if (!UTILS.BASIC.isSymbol(symbol, 5))
            throw new Exception("Invalid symbol, CNewCompanyPayload.java, 130");
        
        // Asset symbol exist ?
        if (UTILS.BASIC.isAsset(symbol))
            throw new Exception("Invalid symbol, CNewCompanyPayload.java, 138");
        
        // Name exist ?
        if (UTILS.BASIC.isName(symbol))
            throw new Exception("Invalid symbol, CNewCompanyPayload.java, 101");
        
        // Pic
        if (!this.pic.equals(""))
          if (!UTILS.BASIC.isPic(this.pic))
            throw new Exception("Invalid pic, CNewCompanyPayload.java, 147");
        
        // Country
        if (!UTILS.BASIC.isCountry(this.cou))
            throw new Exception("Invalid country, CNewCompanyPayload.java, 147");
        
        // Owner in the same country ?
        if (!UTILS.BASIC.getAdrData(this.target_adr, "loc").equals(this.cou))
            throw new Exception("Owner not in the same country, CNewCompanyPayload.java, 147");
        
        // Days
        if (this.days<30)
           throw new Exception("Invalid days, CNewCompanyPayload.java, 151");
        
        // IDs exist ?
        if (UTILS.BASIC.isID(this.comID) || 
            UTILS.BASIC.isID(this.assetID) || 
            UTILS.BASIC.isID(this.assetMktID) ||
            UTILS.BASIC.isID(this.stocID))
        throw new Exception("Invalid ID, CNewCompanyPayload.java, 101");
        
        // Fee
        double fee=UTILS.CONST.com_price*this.days;
        
        // Funds ?
        if (UTILS.ACC.getBalance(this.target_adr, "CRC", block)<fee)
            throw new Exception("Insuficient funds, CNewCompanyPayload.java, 101");
        
        // Transfer
        UTILS.ACC.newTransfer(this.target_adr, 
                              "default", 
                              fee, 
                              "CRC", 
                              "You have paid the incorporation fee for a company", 
                              "", 
                              0, 
                              this.hash, 
                              this.block, 
                              false,
                              "", 
                              "");
        
        // Hash
 	String h=UTILS.BASIC.hash(this.getHash()+
 			         this.type+
 			         this.name+
                                 this.desc+
 			         this.symbol+
                                 this.cou+
                                 this.pic+
                                 this.comID+
                                 this.com_adr+
                                 this.assetID+
                                 this.assetMktID+
                                 this.stocID+
 			         this.days);
        
        // Hash match ?
        if (!h.equals(this.hash))
            throw new Exception("Invalid hash, CNewCompanyPayload.java, 113");
   }
    
    public void commit(CBlockPayload block) throws Exception
    {
       // Superclass
       super.commit(block);
       
       // Insert company
       UTILS.DB.executeUpdate("INSERT INTO companies "
                                    + "SET adr='"+this.com_adr+"', "
                                        + "owner='"+this.target_adr+"', "
                                        + "tip='"+this.type+"', "
                                        + "comID='"+this.comID+"', "
                                        + "name='"+UTILS.BASIC.base64_encode(this.name)+"', "
                                        + "description='"+UTILS.BASIC.base64_encode(this.desc)+"', "
                                        + "symbol='"+this.symbol+"', "                         
                                        + "expires='"+(this.block+this.days*1440)+"'");
       
       // Load stocuri
       ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                          + "FROM com_prods "
                                         + "WHERE com_type='"+this.type+"' "
                                           + "AND type='ID_RAW'");
       
       // Index
       long lastID=this.stocID;
       
       // Insert product
       while (rs.next())
       {
           // Insert
           UTILS.DB.executeUpdate("INSERT INTO stocuri "
                                        + "SET adr='"+this.com_adr+"', "
                                            + "stocID='"+lastID+"', "
                                            + "tip='"+rs.getString("prod")+"', "
                                            + "expires=0, "
                                            + "block='"+this.block+"'");
           
           // Index
           lastID=lastID+1;
       }
       
       // Tools company ?
       if (this.type.equals("ID_COM_TOOLS"))
           UTILS.DB.executeUpdate("INSERT INTO stocuri "
                                        + "SET adr='"+this.com_adr+"', "
                                            + "stocID='"+(lastID+1)+"', "
                                            + "tip='ID_TOOLS_PROD_TOOLS', "
                                            + "expires=0, "
                                            + "qty=1, "
                                            + "block='"+this.block+"'");
       
       // Construction company ?
       if (this.type.equals("ID_BUILD_COM_CONSTRUCTION"))
           UTILS.DB.executeUpdate("INSERT INTO stocuri "
                                        + "SET adr='"+this.com_adr+"', "
                                            + "stocID='"+(lastID+1)+"', "
                                            + "tip='ID_BUILD_COM_CONSTRUCTION', "
                                            + "expires=0, "
                                            + "qty=1, "
                                            + "block='"+this.block+"'");
       
       
       // Create share asset
       UTILS.DB.executeUpdate("INSERT INTO assets "
                                    + "SET adr='"+this.target_adr+"', "
                                        + "assetID='"+this.assetID+"', "
                                        + "symbol='"+this.symbol+"', "
                                        + "title='"+UTILS.BASIC.base64_encode(this.symbol+" company shares")+"', "
                                        + "description='"+UTILS.BASIC.base64_encode(this.symbol+" company shares")+"', "
                                        + "qty='10000', "
                                        + "how_buy='"+UTILS.BASIC.base64_encode("Use the internal asset markets")+"', "
                                        + "how_sell='"+UTILS.BASIC.base64_encode("Use the internal asset markets")+"', "
                                        + "pic='"+UTILS.BASIC.base64_encode(this.pic)+"', "
                                        + "expires='"+(this.block+days*1440)+"'");
       
       // Insert shares
       UTILS.DB.executeUpdate("INSERT INTO assets_owners "
                                    + "SET owner='"+this.target_adr+"', "
                                        + "symbol='"+this.symbol+"', "
                                        + "qty='10000'");
       
       // Creates the market
       UTILS.DB.executeUpdate("INSERT INTO assets_mkts "
                                    + "SET adr='"+this.target_adr+"', "
                                        + "asset='"+this.symbol+"', "
                                        + "cur='CRC', "
                                        + "name='"+UTILS.BASIC.base64_encode(this.symbol+" shares market'")+"', "
                                        + "description='"+UTILS.BASIC.base64_encode(this.symbol+" shares market'")+"', "
                                        + "decimals='2', "
                                        + "ask='0', "
                                        + "bid='0', "
                                        + "last_price='0', "
                                        + "mktID='"+this.assetMktID+"', "
                                        + "expires='"+(this.block+this.days*1440)+"'");
       
       
       // Register address
       UTILS.DB.executeUpdate("INSERT INTO adr "
                                    + "SET adr='"+this.com_adr+"', "
                                         + "name='"+this.symbol+"', "
                                         + "cou='"+this.cou+"', "
                                         + "expires='"+(this.block+this.days*1440)+"', "
                                         + "created='"+this.block+"'");
       
       // Clear transations
       UTILS.ACC.clearTrans(this.hash, "ID_ALL", this.block);
    }
    
}
