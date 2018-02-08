package chainrepublik.network.packets.companies;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CNewCompanyPayload extends CPayload
{
    // Address
    String shareholder_adr; 
    
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
    
    // Workplace ID
    long workplaceID;
    
    // Asset ID
    long assetID;
    
    // Asset mkt ID
    long assetMktID;
    
    // Days
    long days;
    
    // ID's
    long ID1, ID2, ID3, ID4, ID5, ID6, ID7, ID8, ID9, ID10;         
                                      
    public CNewCompanyPayload(String adr, 
                              String type, 
                              String name, 
                              String desc, 
                              String symbol, 
                              String cou, 
                              String pic,
                              String shareholder_adr,
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
        
        // Workplace ID
        this.workplaceID=UTILS.BASIC.getID();
        
        // Asset ID
        this.assetID=UTILS.BASIC.getID();
        
        // Asset mkt ID
        this.assetMktID=UTILS.BASIC.getID();
    
        // Days
        this.days=days;
        
        // Shareholder adr
        this.shareholder_adr=shareholder_adr;
        
        // IDs
        this.ID1=UTILS.BASIC.getID();
        this.ID2=UTILS.BASIC.getID();
        this.ID3=UTILS.BASIC.getID();
        this.ID4=UTILS.BASIC.getID();
        this.ID5=UTILS.BASIC.getID();
        this.ID6=UTILS.BASIC.getID();
        this.ID7=UTILS.BASIC.getID();
        this.ID8=UTILS.BASIC.getID();
        this.ID9=UTILS.BASIC.getID();
        this.ID10=UTILS.BASIC.getID();
        
        // Hash
 	hash=UTILS.BASIC.hash(this.getHash()+
 			         this.type+
 			         this.name+
                                 this.desc+
 			         this.symbol+
                                 this.cou+
                                 this.pic+
                                 this.comID+
                                 this.shareholder_adr+
                                 this.workplaceID+
                                 this.assetID+
                                 this.assetMktID+
 			         this.days+
                                 this.ID1+
                                 this.ID2+
                                 this.ID3+
                                 this.ID4+
                                 this.ID5+
                                 this.ID6+
                                 this.ID7+
                                 this.ID8+
                                 this.ID9+
                                 this.ID10);
           
        // Sign
        this.sign();
    }
    
    public void check(CBlockPayload block) throws Exception
    {
   	// Super class
   	super.check(block);
        
        // Registered
        if (UTILS.BASIC.isRegistered(this.target_adr))
            throw new Exception("Target address is already registered, CNewCompanyPayload.java, 102");
        
        // Already a company 
        if (UTILS.BASIC.isCompanyAdr(this.target_adr))
            throw new Exception("Invalid target address, CNewCompanyPayload.java, 105");
            
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
        
        // Pic
        if (!this.pic.equals(""))
          if (!UTILS.BASIC.isPic(this.pic))
            throw new Exception("Invalid pic, CNewCompanyPayload.java, 147");
        
        // Country
        if (!UTILS.BASIC.isCountry(this.cou))
            throw new Exception("Invalid country, CNewCompanyPayload.java, 147");
        
        // Days
        if (this.days<90)
           throw new Exception("Invalid days, CNewCompanyPayload.java, 151");
        
        // IDs exist ?
        if (UTILS.BASIC.isID(this.comID) || 
            UTILS.BASIC.isID(this.workplaceID) || 
            UTILS.BASIC.isID(this.assetID) || 
            UTILS.BASIC.isID(this.assetMktID) || 
            UTILS.BASIC.isID(this.ID1) || 
            UTILS.BASIC.isID(this.ID2) || 
            UTILS.BASIC.isID(this.ID3) || 
            UTILS.BASIC.isID(this.ID4) || 
            UTILS.BASIC.isID(this.ID5) || 
            UTILS.BASIC.isID(this.ID6) || 
            UTILS.BASIC.isID(this.ID7) || 
            UTILS.BASIC.isID(this.ID8) || 
            UTILS.BASIC.isID(this.ID9) ||
            UTILS.BASIC.isID(this.ID10))
        throw new Exception("Invalid ID, CNewCompanyPayload.java, 101");
        
        // Add to array
        ArrayList ar=new ArrayList();
        ar.add(this.comID);
        ar.add(this.workplaceID);
        ar.add(this.assetID);
        ar.add(this.assetMktID);
        ar.add(this.ID1);
        ar.add(this.ID1);
        ar.add(this.ID2);
        ar.add(this.ID3);
        ar.add(this.ID4);
        ar.add(this.ID5);
        ar.add(this.ID6);
        ar.add(this.ID7);
        ar.add(this.ID8);
        ar.add(this.ID9);
        ar.add(this.ID10);
        
        // Find duplicates
        for (int a=0; a<=12; a++)
            for (int b=a+1; b<=13; b++)
                if (ar.get(a)==ar.get(b))
                    throw new Exception("Invalid IDs, CNewCompanyPayload.java, 101");
        
        // Hash
 	String h=UTILS.BASIC.hash(this.getHash()+
 			         this.type+
 			         this.name+
                                 this.desc+
 			         this.symbol+
                                 this.cou+
                                 this.pic+
                                 this.comID+
                                 this.shareholder_adr+
                                 this.workplaceID+
                                 this.assetID+
                                 this.assetMktID+
 			         this.days+
                                 this.ID1+
                                 this.ID2+
                                 this.ID3+
                                 this.ID4+
                                 this.ID5+
                                 this.ID6+
                                 this.ID7+
                                 this.ID8+
                                 this.ID9+
                                 this.ID10);
        
        // Hash match ?
        if (!h.equals(this.hash))
            throw new Exception("Invalid hash, CNewCompanyPayload.java, 113");
   }
    
    public long getID(int index)
    {
       long ret=0;
        
       switch (index)
       {
           // ID 1
           case 1 : ret=ID1; break;
           
           // ID 2
           case 2 : ret=ID2; break;
           
           // ID 3
           case 3 : ret=ID3; break;
           
           // ID 4
           case 4 : ret=ID4; break;
           
           // ID 5
           case 5 : ret=ID5; break;
           
           // ID 6
           case 6 : ret=ID6; break;
           
            // ID 7
           case 7 : ret=ID7; break;
           
           // ID 8
           case 8 : ret=ID8; break;
           
           // ID 9
           case 9 : ret=ID9; break;

           // ID 10
           case 10 : ret=ID10; break;
       }
       
       // Return
       return ret;
    }
    
    public void commit(CBlockPayload block) throws Exception
    {
       // Superclass
       super.commit(block);
       
       // Insert company
       UTILS.DB.executeUpdate("INSERT INTO companies "
                                    + "SET adr='"+this.target_adr+"', "
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
                                           + "AND (type='ID_RAW' "
                                                + "OR type='ID_FINITE')");
       
       // Index
       int i=0;
       
       // Insert product
       while (rs.next())
       {
           // Index
           i++;
           
           // Insert
           UTILS.DB.executeUpdate("INSERT INTO stocuri "
                                        + "SET adr='"+this.target_adr+"', "
                                            + "stocID='"+this.getID(i)+"', "
                                            + "tip='"+rs.getString("prod")+"', "
                                            + "expires=0");
       }
       
       
       // Create share asset
       UTILS.DB.executeUpdate("INSERT INTO assets "
                                    + "SET adr='"+this.target_adr+"', "
                                        + "assetID='"+this.assetID+"', "
                                        + "symbol='"+this.symbol+"', "
                                        + "title='"+this.symbol+" company shares', "
                                        + "description='"+this.symbol+" company shares', "
                                        + "qty='10000', "
                                        + "how_buy='"+UTILS.BASIC.base64_encode("Use the internal asset markets")+"', "
                                        + "how_sell='"+UTILS.BASIC.base64_encode("Use the internal asset markets")+"', "
                                        + "pic='"+UTILS.BASIC.base64_encode(this.pic)+"', "
                                        + "expires='"+(this.block+days*1440)+"'");
       
       // Insert shares
       UTILS.DB.executeUpdate("INSERT INTO assets_owners "
                                    + "SET owner='"+this.shareholder_adr+"', "
                                        + "symbol='"+this.symbol+"', "
                                        + "qty='10000'");
       
       // Creates the market
       UTILS.DB.executeUpdate("INSERT INTO assets_mkts "
                                    + "SET adr='"+this.target_adr+"', "
                                        + "asset='"+this.symbol+"', "
                                        + "cur='CRC', "
                                        + "name='"+this.symbol+" shares market', "
                                        + "description='"+this.symbol+" shares market', "
                                        + "decimals='2', "
                                        + "ask='0', "
                                        + "bid='0', "
                                        + "last_price='0', "
                                        + "mktID='"+this.assetMktID+"', "
                                        + "expires='"+(this.block+this.days*1440)+"'");
       
       
       // Register address
       UTILS.DB.executeUpdate("INSERT INTO adr "
                                    + "SET adr='"+this.target_adr+"', "
                                         + "name='"+this.symbol+"', "
                                         + "cou='"+this.cou+"', "
                                         + "expires='"+(this.block+this.days*1440)+"', "
                                         + "created='"+this.block+"'");
    }
    
}
