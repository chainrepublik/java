// Author : Vlad Cristian
// Contact : vcris@gmx.com

package chainrepublik.kernel;

import chainrepublik.network.packets.blocks.CBlockPayload;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CUtils 
{
	public CUtils() 
	{
		// TODO Auto-generated constructor stub
	}
	
        // checks if address is valid
	public boolean isAdr(String adr) throws Exception
	{
            // Default
            if (adr.equals("default"))
                return true;
            
            // Length valid
            if (adr.length()!=120) 
            return false;
	    
            // Hash
            if (!this.isBase64(adr))
                return false;
            
            // Return
	    return true;
	}
	
	
	public boolean adrExist(String adr) throws Exception
	{
            // Checks if address is valid
	    if (this.isAdr(adr)==false)
               return false;
		
	    // Search for address
            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
				               + "FROM adr "
				              + "WHERE adr='"+adr+"'");
          	
	    // Exist ?
	    if (UTILS.DB.hasData(rs)) 
               return true;
            else
               return false;
           
	}
	
        // Timestamp
	public long tstamp() throws Exception 
        { 
            return System.currentTimeMillis()/1000; 
        }
	
        // Mili timestamp
	public long mtstamp() throws Exception 
        { 
            return System.currentTimeMillis(); 
        }
	
        // Hash on string
	public String hash(String hash) throws Exception 
        { 
            return  org.apache.commons.codec.digest.DigestUtils.sha256Hex(hash); 
        }
        
        // Hash on data
       	public String hash(byte[] data) throws Exception 
        { 
           return  org.apache.commons.codec.digest.DigestUtils.sha256Hex(data); 
        }
        
        // Base 64 encode
	public String base64_encode(String s) throws Exception
	{
           if (s.equals("")) return "";
           return new String(org.apache.commons.codec.binary.Base64.encodeBase64(s.getBytes()));
	}
	
        // Base 64 encode on data
	public String base64_encode(byte[] s) throws Exception
	{
           return new String(org.apache.commons.codec.binary.Base64.encodeBase64(s));
	}
	
        // Base 64 decode on string
	public String base64_decode(String s) throws Exception
	{
           if (s==null) return "";
           
           if (!s.equals("")) 
              return new String(org.apache.commons.codec.binary.Base64.decodeBase64(s));	
           else
              return "";
	}
	
        // Base 64 decode on data
	public byte[] base64_decode_data(String s) throws Exception
	{
           return org.apache.commons.codec.binary.Base64.decodeBase64(s);
	}
	
	// Random secure string
        public String randString(int no) throws Exception
	{
	    // Generates a key
	    SecureRandom random = new SecureRandom();
            byte key[] = new byte[no];
	    random.nextBytes(key);
		
	    // Random
	    return this.hash(key).substring(0, no);
	}
	
	// Is IP ? 
        public boolean isIP(String ip) throws Exception
        {
            String PATTERN = "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
            Pattern pattern = Pattern.compile(PATTERN);
            Matcher matcher = pattern.matcher(ip);
            return matcher.matches();
        }
        
        // Round
        public double round(double val, int digits) throws Exception
        {
           long i=Math.round(val*(Math.pow(10, digits)));
           double r=i/(Math.pow(10, digits));
           return r;
        }
    
        // Generates an ID
        public long getID() throws Exception
        {
           return Math.round(System.currentTimeMillis()+Math.random()*1000000);
        }
    
        // Is an asset ?
        public boolean isAsset(String symbol) throws Exception
        {
            // Symbol
            if (!this.isSymbol(symbol, 5) && 
                !this.isSymbol(symbol, 6))
               return false;
       
            // Load data
            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM assets "
                                              + "WHERE symbol='"+symbol+"'");
       
            if (UTILS.DB.hasData(rs))
               return true;
            else
               return false;
        }
    
        // Can spend funds ?
        public boolean canSpend(String adr) throws Exception
        {
            return true;
        }
    
        // chesk for sha256
        public boolean isHash(String hash) throws Exception
        {
	    if (!hash.matches("^[A-Fa-f0-9]{64}$"))
                return false;
	    else 
	        return true;
        }
	
        // Domain name valid ?
        public boolean isDomain(String domain) throws Exception
        {
	    if (!domain.matches("^[0-9a-z]{2,30}$"))
	       return false;
	    else 
	       return true;
        }
	
        // Input is a String ?
        public boolean isString(String str) throws Exception
        {
	    for (int a=0; a<=str.length()-1; a++)
            {
                if (Character.codePointAt(str, 0)<32 || 
                    Character.codePointAt(str, 0)>126)
                return false;
            }
        
            return true;
        }
        
        // Input is a valid signer ?
        public boolean isSymbol(String symbol, int length) throws Exception
        {
            // Check letters
            if (!symbol.matches("[A-Z0-9]{"+length+"}"))
               return false;
            else
               return true;
        }
        
        
        
        // Input is a valid signer ?
        public boolean isCou(String symbol) throws Exception
        {
            // Check letters
            if (!symbol.matches("[A-Z]{2}"))
               return false;
            else
               return true;
        }
        
       // Input is a valid signer ?
        public boolean isCountry(String symbol) throws Exception
        {
            // Check letters
            if (!this.isCou(symbol))
               return false;
            
            // Country exist ?
            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM countries "
                                              + "WHERE code='"+symbol+"'");
            
            // Has data ?
            if (!UTILS.DB.hasData(rs))
                return false;
            else
                return true;
        }
        
        // Valid title ?
        public boolean isTitle(String title) throws Exception
        {
            // String ?
            if (!this.isString(title))
                return false;
        
            // Length
            if (title.length()<2 || title.length()>100)
                return false;
        
            // Passed
            return true;
        }
        
        // Valid description ?
        public boolean isDesc(String desc) throws Exception
        {
            // String ?
            if (!this.isString(desc))
                return false;
        
            // Length
            if (desc.length()<5 || desc.length()>1000)
                return false;
        
            // Passed
            return true;
        }
        
        // Description with predefined length
        public boolean isDesc(String desc, long max_length) throws Exception
        {
            // String ?
            if (!this.isString(desc))
                return false;
        
            // Length
            if (desc.length()<5 || desc.length()>max_length)
                return false;
        
            // Passed
            return true;
        }
        
        // Is link ?
        public boolean isLink(String link) throws Exception
        {
            // Lnegth
            if (link.length()>250)
               return false;
                
	    if (link.matches("^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"))
               return true;
            else 
               return false;
        }
    
        // Is pic ?
        public boolean isPic(String link) throws Exception
        {   
            // Is link
            if (this.isLink(link)==false)
                return false;
        
            // Length
            if (link.endsWith(".jpg")==false && 
                link.endsWith(".jpeg")==false &&
                link.endsWith(".png")==false &&
                link.endsWith(".gif")==false)
            return false;
        
            return true;
        }
        
        // Is email ?
        public boolean isEmail(String email) throws Exception
        {
	     // Lnegth
            if (email.length()>250)
               return false;
                
	    if (email.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"))
               return true;
            else 
               return false;
        }
    
        // Format difficulty
        public String formatDif(String dif)
        {
           long d=64-dif.length();
           for (int a=1; a<=d; a++)
             dif="0"+dif;
           
           return dif;
        }
    
        // Is ID ?
        public boolean isID(long ID) throws Exception
        {
            // Search tweets
            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM tweets "
                                              + "WHERE tweetID='"+ID+"'");
        
            // Has data ?
            if (UTILS.DB.hasData(rs)) 
                return true;
                            
            // Comments
            rs=UTILS.DB.executeQuery("SELECT * "
                                     + "FROM comments "
                                    + "WHERE comID='"+ID+"'");
        
            // Has data ?
            if (UTILS.DB.hasData(rs)) 
                return true;
            
            // Assets
            rs=UTILS.DB.executeQuery("SELECT * "
                                     + "FROM assets "
                                    + "WHERE assetID='"+ID+"'");
        
            // Has data ?
            if (UTILS.DB.hasData(rs)) 
                return true;
        
            // Workplaces
            rs=UTILS.DB.executeQuery("SELECT * "
                                     + "FROM workplaces "
                                    + "WHERE workplaceID='"+ID+"'");
        
            // Has data ?
            if (UTILS.DB.hasData(rs)) 
                return true;
            
            // Companies
            rs=UTILS.DB.executeQuery("SELECT * "
                                     + "FROM companies "
                                    + "WHERE comID='"+ID+"'");
        
            // Has data ?
            if (UTILS.DB.hasData(rs)) 
                return true;
            
            // Laws
            rs=UTILS.DB.executeQuery("SELECT * "
                                     + "FROM laws "
                                    + "WHERE lawID='"+ID+"'");
        
            // Has data ?
            if (UTILS.DB.hasData(rs)) 
                return true;
            
            // Stocuri
            rs=UTILS.DB.executeQuery("SELECT * "
                                     + "FROM stocuri "
                                    + "WHERE stocID='"+ID+"'");
        
            // Has data ?
            if (UTILS.DB.hasData(rs)) 
                return true;
            
            // Stocuri
            rs=UTILS.DB.executeQuery("SELECT * "
                                     + "FROM wars "
                                    + "WHERE warID='"+ID+"'");
        
            // Has data ?
            if (UTILS.DB.hasData(rs)) 
                return true;
        
            // Return
            return false;
    }
    
    // Target valid ?
    public boolean targetValid(String target_type, long targetID) throws Exception
    {
       // Target type
       if (!target_type.equals("ID_TWEET") && 
           !target_type.equals("ID_COM") &&
           !target_type.equals("ID_ASSET") &&
           !target_type.equals("ID_ASSET_MKT"))
        return false;
       
       // Result
       ResultSet rs=null;
       
       // Load  data
       switch (target_type)
       {
            // Blog post
            case "ID_TWEET" : rs=UTILS.DB.executeQuery("SELECT * "
                                                      + "FROM tweets "
                                                      + "WHERE tweetID='"+targetID+"'");
                            break;    
                       
            // Comment
            case "ID_COM" : rs=UTILS.DB.executeQuery("SELECT * "
                                                     + "FROM comments "
                                                    + "WHERE comID='"+targetID+"'");
                                break;    
           
            // Asset                   
            case "ID_ASSET" : rs=UTILS.DB.executeQuery("SELECT * "
                                                       + "FROM assets "
                                                      + "WHERE assetID='"+targetID+"'");
                                break;  
            
            // Asset market
            case "ID_ASSET_MKT" : rs=UTILS.DB.executeQuery("SELECT * "
                                                           + "FROM assets_mkts "
                                                          + "WHERE mktID='"+targetID+"'");
                                  break;  
       }
             
       // Like tweet exist ?
       if (!UTILS.DB.hasData(rs))
           return false;
       
       // Return
       return true;
    }
    
    
    // Get reward amount
    public double getReward(String target) throws Exception
    {
        // Load address
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM adr "
                                          + "WHERE adr='default'");
        
        // Next
        rs.next();
        
        // Undistributed
        double unspend=rs.getDouble("balance");
        
        // Per day
        double per_day=unspend/365/20;
        
        // Reward
        double reward=0;
        
        switch (target)
        {
            // Posts
            case "ID_POST" : reward=per_day*0.2; break;
			
	    // Comments
	    case "ID_COM" : reward=per_day*0.1; break;
			
	    // Assets
	    case "ID_ASSETS" : reward=per_day*0.05; break;
			
	    		
	    // Miners
	    case "ID_MINER" : reward=per_day*0.4/1440; break;
        }
        
        // Return
        return reward;
    }
    
    // Has attribute ?
    public boolean hasAttr(String adr, String attr, String s1) throws Exception
    {
        // Check address
        if (!this.isAdr(adr))
           throw new Exception("Invalid address, CUtils.java - 516");
            
        // Check attribute
        if (!attr.equals("ID_RES_REC") &&  
            !attr.equals("ID_TRUST_ASSET"))
        return false;
        
        // Load data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM adr_attr "
                                          + "WHERE adr='"+adr+"' "
                                            + "AND attr='"+attr+"' "
                                            + "AND s1='"+s1+"'");
       // Has data ?
       if (UTILS.DB.hasData(rs))
           return true;
       else
           return false;
    }
    
    // Vote target
    public void voteTarget(String adr, 
                           String target_type, 
                           long targetID, 
                           long block) throws Exception
    {
        // Check address
        if (!this.isAdr(adr))
            throw new Exception("Invalid target - CUtils.java, 607");
        
       // Target type
       if (!target_type.equals("ID_POST") && 
           !target_type.equals("ID_COM") && 
           !target_type.equals("ID_ASSET") &&
           !target_type.equals("ID_ASSET_MKT"))
        throw new Exception("Invalid target - CUtils.java, 613");
       
        // Voted ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM votes "
                                          + "WHERE adr='"+adr+"' "
                                            + "AND target_type='"+target_type+"' "
                                            + "AND targetID='"+targetID+"'");
                      
        // Has data
        if (!UTILS.DB.hasData(rs))
           UTILS.DB.executeUpdate("INSERT INTO votes "
                                       + "SET target_type='"+target_type+"', "
                                           + "targetID='"+targetID+"', "
                                           + "type='ID_UP', "
                                           + "adr='"+adr+"', "
                                           + "block='"+block+"'");
    }
   
    // Get asset expiration block
    public long getAssetExpireBlock(long assetID) throws Exception
    {
        // Symbol valid ?
        if (!this.isID(assetID))
           throw new Exception("Invalid values - CUtils.java"); 
        
        // Load asset
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM assets "
                                          + "WHERE assetID='"+assetID+"'");
        
        // Next
        rs.next();
            
        // Return
        return (rs.getLong("expire")-10);
    }
    
    // Get asset expiration block
    public long getAssetMktExpireBlock(long mktID) throws Exception
    {
        // Symbol valid ?
        if (!this.isID(mktID))
           throw new Exception("Invalid values - CUtils.java"); 
        
        // Load asset
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM assets_mkts "
                                          + "WHERE assetID='"+mktID+"'");
        
        // Next
        rs.next();
            
        // Return
        return (rs.getLong("expire")-10);
    }
    
    // Valid currency ?
    public boolean isCur(String cur) throws Exception
    {
        if (!cur.equals("CRC"))
        {
            if (!this.isSymbol(cur, 6) && 
                !this.isSymbol(cur, 5))
            {
                return false;
            }
            else
            {
                if (!this.isAsset(cur))
                   return false;
            }
        }
        
        return true;
    }
    
    // Is base64 encoded string ?
    public boolean isBase64(String txt) throws Exception
    {
        if (txt.matches("^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{4})$"))
           return true;
        else 
           return false;
    }
    
    // Input is a string identifier ?
    public boolean isStringID(String str) throws Exception
    {
        // Length
        if (str.length()>50)
            return false;
                
	if (str.matches("^[A-Z0-9_]++$"))
           return true;
        else 
           return false;
    }
    
    public boolean isTableName(String name)
    {
        // Length
        if (name.length()<2 || 
            name.length()>50)
        return false;
        
        // Name
        if (name.matches("^[a-zA-Z_]++$"))
           return true;
        else 
           return false;
    }
    
    public boolean isRegistered(String adr) throws Exception
    {
        // Valid address
        if (!this.isAdr(adr))
            throw new Exception("Invalid address");
        
        // Load data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM adr "
                                          + "WHERE adr='"+adr+"' "
                                            + "AND cou<>''");
        
        // Has data
        if (UTILS.DB.hasData(rs))
            return true;
        else
            return false;
    }
    
    public boolean isCompanyAdr(String adr) throws Exception
    {
        // Adr
        if (!this.isAdr(adr))
            throw new Exception("Invalid address, CUtils.java, 644");
        
        // Load data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM companies "
                                          + "WHERE adr='"+adr+"'");
        
        // Has data
        if (UTILS.DB.hasData(rs))
            return true;
        else
            return false;
    }
    
    public boolean isGovAdr(String adr) throws Exception
    {
        // Adr
        if (!this.isAdr(adr))
            throw new Exception("Invalid address, CUtils.java, 644");
        
        // Load data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM countries "
                                          + "WHERE adr='"+adr+"'");
        
        // Has data
        if (UTILS.DB.hasData(rs))
            return true;
        else
            return false;
    }
    
    public boolean isCitAdr(String adr) throws Exception
    {
        // Adr
        if (!this.isAdr(adr))
            throw new Exception("Invalid address, CUtils.java, 644");
        
        // Not government or citizen adr ?
        if (!this.isGovAdr(adr) && 
            !this.isCompanyAdr(adr) &&
            !this.isOrgAdr(adr) && 
            this.isRegistered(adr))
        return true;
        
        // Not citizen address
        return false;
    }
    
    public boolean isOrgAdr(String adr) throws Exception
    {
       // Adr
        if (!this.isAdr(adr))
            throw new Exception("Invalid address, CUtils.java, 644");
        
        // Company ID
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM orgs "
                                          + "WHERE adr='"+adr+"'");
        
        // Has data
        if (UTILS.DB.hasData(rs))
            return true;
        else
            return false;
    }
    
    public boolean isComAdr(long comID, String adr) throws Exception
    {
        // Adr
        if (!this.isAdr(adr))
            throw new Exception("Invalid address, CUtils.java, 644");
        
        // Company ID
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM companies "
                                          + "WHERE comID='"+comID+"' "
                                            + "AND adr='"+adr+"'");
        
        // Has data
        if (UTILS.DB.hasData(rs))
            return true;
        else
            return false;
    }
    
    public String getComType(long comID) throws Exception
    {
        // Company ID
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM companies "
                                          + "WHERE comID='"+comID+"'");
        
        // Has data
        if (UTILS.DB.hasData(rs))
        {
            // Next
            rs.next();
            
            // Return
            return rs.getString("tip");
        }
        else throw new Exception("Can't find company ID - "+comID);
    }
    
    public String getComAdr(long comID) throws Exception
    {
        // Company ID
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM companies "
                                          + "WHERE comID='"+comID+"'");
        
        // Has data
        if (UTILS.DB.hasData(rs))
        {
            // Next
            rs.next();
            
            // Return
            return rs.getString("adr");
        }
        else throw new Exception("Can't find company ID - "+comID);
    }
    
    public String adrFromName(String domain) throws Exception
    {
        if (this.isDomain(domain))
        {
            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM adr "
                                              + "WHERE name='"+domain+"'");
            
            // Domain
            if (!UTILS.DB.hasData(rs))
                throw new Exception("Invalid domain name");
            
            // Next
            rs.next();
            
            // Return address
            return rs.getString("adr");
        }
        
        else if (this.isAdr(domain))
            return domain;
        
        else throw new Exception("Invalid domain name");
    }
    
    public double getEnergy(String adr) throws Exception
    {
        // Is adr ?
        if (!this.isAdr(adr))
            throw new Exception("Invalid address");
        
        // Load energy
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM adr "
                                          + "WHERE adr='"+adr+"'");
        
        // Has data ?
        if (!UTILS.DB.hasData(rs))
            throw new Exception("Invalid address");
        
        // Next
        rs.next();
        
        // Return
        return rs.getDouble("energy");
    }
    
    public double getEnergy(String adr, CBlockPayload block) throws Exception
    {
        // Is adr ?
        if (!this.isAdr(adr))
            throw new Exception("Invalid address");
        
        // Through transpool ?
        if (block!=null)
            return UTILS.NETWORK.TRANS_POOL.getBalance(adr, "ID_ENERGY");
        else
            return getEnergy(adr);
    }
    
    public void newEvent(String adr, String evt, long block) throws Exception
    {
        // Is adr ?
        if (!this.isAdr(adr))
            throw new Exception("Invalid address");
        
        // Insert
        UTILS.DB.executeUpdate("INSERT INTO events "
                                     + "SET adr='"+adr+"', "
                                         + "evt='"+evt+"', "
                                         + "viewed='0', "
                                         + "block='"+block+"'");
    }
    
    public boolean isMyAdr(String adr) throws Exception
    {
        // Is adr ?
        if (!this.isAdr(adr))
            throw new Exception("Invalid address");
        
        // Result Set
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM my_adr "
                                          + "WHERE adr='"+adr+"'");
        
        // Has data ?
        if (UTILS.DB.hasData(rs))
            return true;
        else
            return false;
    }
    
    public boolean isGovernor(String adr, String cou) throws Exception
    {
        // Address
        if (!UTILS.BASIC.isAdr(adr))
            throw new Exception("Invalid address");
        
        // Country
        if (!UTILS.BASIC.isCountry(cou))
            throw new Exception("Invalid country");
        
        // Address exist ?
        ResultSet rs_adr=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM adr "
                                          + "WHERE adr='"+adr+"' "
                                             + "AND cou='"+cou+"'");
        
        // Exist ?
        if (!UTILS.DB.hasData(rs_adr))
            throw new Exception("Address doesn't exist");
        
        // Address data
        rs_adr.next();
        
        // Has voting rights ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT COUNT(*) AS total "
                                           + "FROM adr "
                                          + "WHERE pol_endorsed>"+rs_adr.getDouble("pol_endorsed")+" "
                                            + "AND cou='"+rs_adr.getString("cou")+"'");
        
        // Able to vote ?
        if (rs.getLong("total")>20)
           return false;
        else
            return true;
    }
    
    public String getAdrData(String adr, String col) throws Exception
    {
        // Is address ?
        if (!UTILS.BASIC.isAdr(adr))
            throw new Exception("Invalid address");
        
        // Column ?
        if (!col.equals("cou") && 
            !col.equals("name") && 
            !col.equals("description") && 
            !col.equals("ref_adr") && 
            !col.equals("node_adr") && 
            !col.equals("balance") && 
            !col.equals("pic") && 
            !col.equals("block") && 
            !col.equals("pol_inf") &&
            !col.equals("energy") &&
            !col.equals("energy_block") &&
            !col.equals("loc") &&
            !col.equals("pol_endorsed") &&
            !col.equals("created") &&
            !col.equals("premium") &&
            !col.equals("travel") &&
            !col.equals("travel_cou") &&
            !col.equals("work") &&
            !col.equals("expires") &&
            !col.equals("mil_unit") &&
            !col.equals("pol_party") &&
            !col.equals("war_points"))
        throw new Exception("Invalid data");
        
        // Load address details
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM adr "
                                          + "WHERE adr='"+adr+"'");
        
        // Has data
        if (UTILS.DB.hasData(rs))
        {
           // Next
           rs.next();
        
           // Return
           return rs.getString(col);
        }
        else return "";
    }
  
    public boolean canBuy(String adr, String prod, double qty, CBlockPayload block) throws Exception
    {
        // Is address ?
        if (!UTILS.BASIC.isAdr(adr))
            throw new Exception("Invalid address");
        
        // Valid product ?
        if (!this.isStringID(prod))
            throw new Exception("Invalid product");
        
        // Address type
        String type=this.getAdrOwnerType(adr);
        
        // Load data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM allow_trans "
                                          + "WHERE receiver_type='"+type+"' "
                                            + "AND prod='"+prod+"' "
                                            + "AND can_buy='YES'");
        
        // Return
        if (UTILS.DB.hasData(rs))
        {
            // Next
            rs.next();
            
            // Is max hold enabled ?
            if (rs.getString("is_limited").equals("YES"))
            {
                // Max hold
                double max_hold=rs.getDouble("max_hold");
                
                // Get stoc
                double stoc=UTILS.ACC.getBalance(adr, prod, block);   
                
                // Check max hold
                if (stoc+qty>max_hold)
                    return false;
            }
            
            // Return
            return true;
        }
        else
            return false;
    }
    
    public boolean canSell(String adr, String prod) throws Exception
    {
        // Is address ?
        if (!UTILS.BASIC.isAdr(adr))
            throw new Exception("Invalid address");
        
        // Valid product ?
        if (!this.isStringID(prod))
            throw new Exception("Invalid product");
        
        // Address type
        String type=this.getAdrOwnerType(adr);
        
        // Load data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM allow_trans "
                                          + "WHERE receiver_type='"+type+"' "
                                            + "AND prod='"+prod+"' "
                                            + "AND can_sell='YES'");
        
        // Return
        if (UTILS.DB.hasData(rs))
            return true;
        else
            return false;
    }
    
    public boolean canRent(String adr, String prod) throws Exception
    {
        // Is address ?
        if (!UTILS.BASIC.isAdr(adr))
            throw new Exception("Invalid address");
        
        // Valid product ?
        if (!this.isStringID(prod))
            throw new Exception("Invalid product");
        
        // Address type
        String type=this.getAdrOwnerType(adr);
        
        // Load data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM allow_trans "
                                          + "WHERE receiver_type='"+type+"' "
                                            + "AND prod='"+prod+"' "
                                            + "AND can_rent='YES'");
        
        // Return
        if (UTILS.DB.hasData(rs))
            return true;
        else
            return false;
    }
    
    public boolean canDonate(String adr, String prod) throws Exception
    {
        // Is address ?
        if (!UTILS.BASIC.isAdr(adr))
            throw new Exception("Invalid address");
        
        // Valid product ?
        if (!this.isStringID(prod))
            throw new Exception("Invalid product");
        
        // Address type
        String type=this.getAdrOwnerType(adr);
        
        // Load data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM allow_trans "
                                          + "WHERE receiver_type='"+type+"' "
                                            + "AND prod='"+prod+"' "
                                            + "AND can_donate='YES'");
        
        // Return
        if (UTILS.DB.hasData(rs))
            return true;
        else
            return false;
    }
    
    public boolean hasProd(String adr, String prod) throws Exception
    {
        // Is address ?
        if (!UTILS.BASIC.isAdr(adr))
            throw new Exception("Invalid address");
        
        // Valid product ?
        if (!this.isStringID(prod))
            throw new Exception("Invalid product");
        
        // Has this product ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM stocuri "
                                          + "WHERE adr='"+adr+"' "
                                            + "AND tip='"+prod+"'");
        
        // Has data ?
        if (UTILS.DB.hasData(rs))
            return true;
        else
            return false;
    }
    
    public String getAdrOwnerType(String adr) throws Exception
    {
        // Is address ?
        if (!UTILS.BASIC.isAdr(adr))
            throw new Exception("Invalid address");
        
        // Is governemt adr ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM countries "
                                          + "WHERE adr='"+adr+"'");
        
        // Has data ?
        if (UTILS.DB.hasData(rs))
            return "ID_GUV";
        
        // Is company adr ?
        rs=UTILS.DB.executeQuery("SELECT * "
                                 + "FROM companies "
                                + "WHERE adr='"+adr+"'");
        
        // Has data ?
        if (UTILS.DB.hasData(rs))
        {
            // Next
            rs.next();
            
            // Return
            return rs.getString("tip");
        }
        
        // Return citizen
        return "ID_CIT";
    }
    
    public boolean isProd(String prod) throws Exception
    {
        // String ID ?
        if (!this.isStringID(prod))
            return false;
        
        // Energy
        if (prod.equals("ID_ENERGY"))
            return true;
        
        // Valid prod
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM tipuri_produse "
                                          + "WHERE prod='"+prod+"'");
        
        // Has data ?
        if (!UTILS.DB.hasData(rs))
            return false;
        else
            return true;
    }
    
    public boolean isLic(String lic) throws Exception
    {
         // String ID ?
        if (!this.isStringID(lic))
            return false;
        
        // Valid prod
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM tipuri_licente "
                                          + "WHERE tip='"+lic+"'");
        
        // Has data ?
        if (!UTILS.DB.hasData(rs))
            return false;
        else
            return true;
    }
    
    public boolean buySplit(String adr, String prod, double amount) throws Exception
    {
        // Address
        if (!UTILS.BASIC.isAdr(adr))
            throw new Exception("Invalid address");
        
        // String ID ?
        if (!this.isStringID(prod))
             throw new Exception("Invalid prod ID");
        
        // Negative
        if (amount<0)
            return false;
        
        // Receiver type
        String rec_type=this.getAdrOwnerType(adr);
        
        // Load data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM allow_trans "
                                          + "WHERE receiver_type='"+rec_type+"' "
                                            + "AND prod='"+prod+"'");
        
        // Next
        rs.next();
        
        // Return
        if (rs.getString("buy_split").equals("YES"))
            return true;
        else
            return false;
    }
    
    public long getCouDist(String cou_1, String cou_2) throws Exception
            
    {
        // Valid countries
        if (!this.isCou(cou_1) || 
            !this.isCou(cou_2))
        throw new Exception("Invalid address");
        
        // Country 1 pos
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM countries "
                                          + "WHERE code='"+cou_1+"'");
        
        // Next
        rs.next();
        
        // Position
        long x_1=rs.getLong("x");
        long y_1=rs.getLong("y");
        
        // Country 2 pos
        rs=UTILS.DB.executeQuery("SELECT * "
                                 + "FROM countries "
                                + "WHERE code='"+cou_2+"'");
        
        // Next
        rs.next();
        
        // Position
        long x_2=rs.getLong("x");
        long y_2=rs.getLong("y");
        
        // Distance
        long dist=Math.round(Math.sqrt(Math.pow(Math.abs(x_1-x_2), 2)+Math.pow(Math.abs(y_1-y_2), 2)));
       
        // Return
        return dist*10;
    }
    
    // Returns an unused user address
    public String getFreeAdr(long userID) throws Exception
    {
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM my_adr "
                                          + "WHERE userID='"+userID+"' "
                                       + "ORDER BY ID ASC");
        
        // Next
        rs.next();
        
        // Next
        while (rs.next())
          if (!UTILS.BASIC.isCompanyAdr(rs.getString("adr")) && 
              !UTILS.BASIC.isRegistered(rs.getString("adr")) &&
              UTILS.WALLET.isMine(rs.getString("adr")))
          return rs.getString("adr");
        
        // None
        return "";
    }
    
    public boolean isEnergyBooster(String prod)
    {
        if (prod.equals("ID_WINE") || 
            prod.equals("ID_CIG_CHURCHILL") || 
            prod.equals("ID_CIG_PANATELA") ||
            prod.equals("ID_CIG_TORPEDO") ||
            prod.equals("ID_CIG_CORONA") ||
            prod.equals("ID_CIG_TORO") ||
            prod.equals("ID_SAMPANIE") || 
            prod.equals("ID_MARTINI") ||
            prod.equals("ID_MOJITO") ||
            prod.equals("ID_MARY") ||
            prod.equals("ID_SINGAPORE") || 
            prod.equals("ID_PINA") ||
            prod.equals("ID_MOJITO") || 
            prod.equals("ID_CROISANT") || 
            prod.equals("ID_HOT_DOG") || 
            prod.equals("ID_PASTA") ||
            prod.equals("ID_BURGER") || 
            prod.equals("ID_BIG_BURGER") || 
            prod.equals("ID_PIZZA"))
            return true;
        else
            return false;
    }
    
   
    public double getProdEnergy(String prod)
    {
        // Energy
        double e=0;
        
        switch (prod)
        {
	    // Cigars
	    case "ID_CIG_CHURCHILL" : e=1; break;
	    case "ID_CIG_PANATELA" : e=2; break;
	    case "ID_CIG_TORPEDO" : e=3; break;
	    case "ID_CIG_CORONA" : e=4; break;
	    case "ID_CIG_TORO" : e=5; break;
			
	    // Cocktails
	    case "ID_SAMPANIE" : e=1.5; break;
	    case "ID_MARTINI" : e=3; break;
	    case "ID_MOJITO" : e=4.5; break;
	    case "ID_MARY" : e=6; break;
            case "ID_SINGAPORE" : e=7.5; break;
	    case "ID_PINA" : e=9; break;
			
	    // Food (bread, meat, tomatoes, salad)
	    case "ID_CROISANT" : e=2; break;
	    case "ID_HOT_DOG" : e=4; break;
	    case "ID_PASTA" : e=6; break;
	    case "ID_BURGER" : e=8; break;
	    case "ID_BIG_BURGER" : e=10; break;
	    case "ID_PIZZA" : e=12; break;
			
	    // -------------------------------- Clothes --------------------------------------
            
            // Sosete
	    case "ID_SOSETE_Q1" : e=1; break;
            case "ID_SOSETE_Q2" : e=2; break;
            case "ID_SOSETE_Q3" : e=3; break;
	    
            // Camasa
            case "ID_CAMASA_Q1" : e=2; break;
            case "ID_CAMASA_Q2" : e=3; break;
            case "ID_CAMASA_Q3" : e=4; break;
	    
            // Ghete
            case "ID_GHETE_Q1" : e=3; break;
            case "ID_GHETE_Q2" : e=4; break;
            case "ID_GHETE_Q3" : e=5; break;
	    
            // Pantaloni
            case "ID_PANTALONI_Q1" : e=4; break;
            case "ID_PANTALONI_Q2" : e=5; break;
            case "ID_PANTALONI_Q3" : e=6; break;
	    
            // Pulover
            case "ID_PULOVER_Q1" : e=5; break;
            case "ID_PULOVER_Q2" : e=6; break;
            case "ID_PULOVER_Q3" : e=7; break;
	    
            // Palton
            case "ID_PALTON_Q1" : e=6; break;
            case "ID_PALTON_Q2" : e=7; break;
            case "ID_PALTON_Q3" : e=8; break;
				
	    // ------------------------------ Bijuterii --------------------------------------
	    
            // Inel
            case "ID_INEL_Q1" : e=1; break;
            case "ID_INEL_Q2" : e=2; break;
            case "ID_INEL_Q3" : e=3; break;
	    
            // Cercel
            case "ID_CERCEL_Q1" : e=2; break;
            case "ID_CERCEL_Q2" : e=3; break;
            case "ID_CERCEL_Q3" : e=4; break;
	    
            // Colier
            case "ID_COLIER_Q1" : e=3; break;
            case "ID_COLIER_Q2" : e=4; break;
            case "ID_COLIER_Q3" : e=5; break;
	    
            // Ceas
            case "ID_CEAS_Q1" : e=4; break;
            case "ID_CEAS_Q2" : e=5; break;
            case "ID_CEAS_Q3" : e=6; break;
	    
            // Bratara
            case "ID_BRATARA_Q1" : e=5; break;
            case "ID_BRATARA_Q2" : e=6; break;
            case "ID_BRATARA_Q3" : e=7; break;
			
	    // Cars
	    case "ID_CAR_Q1" : e=5; break;
	    case "ID_CAR_Q2" : e=10; break;
	    case "ID_CAR_Q3" : e=15; break;
			
	    // Houses
	    case "ID_HOUSE_Q1" : e=10; break;
	    case "ID_HOUSE_Q2" : e=20; break;
	    case "ID_HOUSE_Q3" : e=30; break;
            
            // Wine
            case "ID_WINE" : e=5; break;
            
            // Gift
            case "ID_GIFT" : e=10; break;
	}
        
        // Return
        return e;
    }
    
    
    public boolean isEnergyProd(String prod)
    {
        if (prod.equals("ID_CIG_CHURCHILL")
            || prod.equals("ID_CIG_PANATELA")
            || prod.equals("ID_CIG_TORPEDO")
            || prod.equals("ID_CIG_CORONA")
            || prod.equals("ID_CIG_TORO")
            || prod.equals("ID_SAMPANIE")
            || prod.equals("ID_MARTINI")
            || prod.equals("ID_MOJITO")
            || prod.equals("ID_MARY")
            || prod.equals("ID_SINGAPORE")
            || prod.equals("ID_PINA")
            || prod.equals("ID_CROISANT")
            || prod.equals("ID_HOT_DOG")
            || prod.equals("ID_PASTA")
            || prod.equals("ID_BURGER")
            || prod.equals("ID_BIG_BURGER")
            || prod.equals("ID_PIZZA")
            || prod.equals("ID_SOSETE_Q1")
            || prod.equals("ID_SOSETE_Q2")
            || prod.equals("ID_SOSETE_Q3")
            || prod.equals("ID_CAMASA_Q1")
            || prod.equals("ID_CAMASA_Q2")
            || prod.equals("ID_CAMASA_Q3")
            || prod.equals("ID_GHETE_Q1")
            || prod.equals("ID_GHETE_Q2")
            || prod.equals("ID_GHETE_Q3")
            || prod.equals("ID_PANTALONI_Q1")
            || prod.equals("ID_PANTALONI_Q2")
            || prod.equals("ID_PANTALONI_Q3")
            || prod.equals("ID_PULOVER_Q1")
            || prod.equals("ID_PULOVER_Q2")
            || prod.equals("ID_PULOVER_Q3")
            || prod.equals("ID_PALTON_Q1")
            || prod.equals("ID_PALTON_Q2")
            || prod.equals("ID_PALTON_Q3")
            || prod.equals("ID_INEL_Q1")
            || prod.equals("ID_INEL_Q2")
            || prod.equals("ID_INEL_Q3")
            || prod.equals("ID_CERCEL_Q1")
            || prod.equals("ID_CERCEL_Q2")
            || prod.equals("ID_CERCEL_Q3")
            || prod.equals("ID_COLIER_Q1")
            || prod.equals("ID_COLIER_Q2")
            || prod.equals("ID_COLIER_Q3")
            || prod.equals("ID_CEAS_Q1")
            || prod.equals("ID_CEAS_Q2")
            || prod.equals("ID_CEAS_Q3")
            || prod.equals("ID_BRATARA_Q1")
            || prod.equals("ID_BRATARA_Q2")
            || prod.equals("ID_BRATARA_Q3")
            || prod.equals("ID_CAR_Q1")
            || prod.equals("ID_CAR_Q2")
            || prod.equals("ID_CAR_Q3")
            || prod.equals("ID_HOUSE_Q1")
            || prod.equals("ID_HOUSE_Q2")
            || prod.equals("ID_HOUSE_Q3")
            || prod.equals("ID_GIFT"))
        return true;
        else
        return false;
    }
    
    public boolean isUsable(String prod)
    {
        if (prod.equals("ID_SOSETE_Q1")
            || prod.equals("ID_SOSETE_Q2")
            || prod.equals("ID_SOSETE_Q3")
            || prod.equals("ID_CAMASA_Q1")
            || prod.equals("ID_CAMASA_Q2")
            || prod.equals("ID_CAMASA_Q3")
            || prod.equals("ID_GHETE_Q1")
            || prod.equals("ID_GHETE_Q2")
            || prod.equals("ID_GHETE_Q3")
            || prod.equals("ID_PANTALONI_Q1")
            || prod.equals("ID_PANTALONI_Q2")
            || prod.equals("ID_PANTALONI_Q3")
            || prod.equals("ID_PULOVER_Q1")
            || prod.equals("ID_PULOVER_Q2")
            || prod.equals("ID_PULOVER_Q3")
            || prod.equals("ID_PALTON_Q1")
            || prod.equals("ID_PALTON_Q2")
            || prod.equals("ID_PALTON_Q3")
            || prod.equals("ID_INEL_Q1")
            || prod.equals("ID_INEL_Q2")
            || prod.equals("ID_INEL_Q3")
            || prod.equals("ID_CERCEL_Q1")
            || prod.equals("ID_CERCEL_Q2")
            || prod.equals("ID_CERCEL_Q3")
            || prod.equals("ID_COLIER_Q1")
            || prod.equals("ID_COLIER_Q2")
            || prod.equals("ID_COLIER_Q3")
            || prod.equals("ID_CEAS_Q1")
            || prod.equals("ID_CEAS_Q2")
            || prod.equals("ID_CEAS_Q3")
            || prod.equals("ID_BRATARA_Q1")
            || prod.equals("ID_BRATARA_Q2")
            || prod.equals("ID_BRATARA_Q3")
            || prod.equals("ID_CAR_Q1")
            || prod.equals("ID_CAR_Q2")
            || prod.equals("ID_CAR_Q3")
            || prod.equals("ID_HOUSE_Q1")
            || prod.equals("ID_HOUSE_Q2")
            || prod.equals("ID_HOUSE_Q3"))
        return true;
        else
        return false;
    }
    
    public boolean canConsume(String adr, String item) throws Exception
    {
        // Is address
        if (!this.isAdr(adr))
           throw new Exception("Invalid item ID");
        
        // Is energy booster ?
        if (this.isEnergyBooster(item) && this.isCitAdr(adr))
             return true;
        else
            return false;
    }
    
    public double getProdEnergy(long itemID) throws Exception
    {
        // Is ID
        if (!this.isID(itemID))
            throw new Exception("Invalid item ID");
        
        // Load data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM stocuri "
                                          + "WHERE stocID='"+itemID+"'");
        
        // Has data ?
        if (!UTILS.DB.hasData(rs))
            throw new Exception("Invalid item ID");
        
        // Next
        rs.next();
        
        // Product
        String prod=rs.getString("tip");
        
        // Is energy booster ?
        if (!this.isEnergyBooster(prod))
             throw new Exception("Invalid item ID");
        
        // Returns energy
        return this.getProdEnergy(rs.getString("tip"));
    }
    
    public long hashToLong(String hash)
    {
        String num="";
        
        for (int a=0; a<=hash.length()-1; a++)
            if (hash.charAt(a)=='0' || 
                hash.charAt(a)=='1' || 
                hash.charAt(a)=='2' || 
                hash.charAt(a)=='3' || 
                hash.charAt(a)=='4' || 
                hash.charAt(a)=='5' || 
                hash.charAt(a)=='6' || 
                hash.charAt(a)=='7' || 
                hash.charAt(a)=='8' || 
                hash.charAt(a)=='9')
                if (num.length()<10)
                    num=num+hash.charAt(a);
        
        return Long.parseLong(num);
    }
    
    // Returns the balance of default address
    public long uCoins() throws Exception
    {
        return Math.round(UTILS.ACC.getBalance("default", "CRC"));
    }
    
    public long getRewardPool(String reward) throws Exception
    {
        // Undistributed coins
        long u=this.uCoins();
        
        // Amount
        long amount=0;
        
        // Daily reward
        long daily=Math.round(u*0.05/365);
        
        // Reward type
        switch (reward)
        {
            // Energy 20%
            case "ID_ENERGY" : amount=Math.round(daily*0.1); 
                               break;
                               
            // Miners 20%
            case "ID_MINERS" : amount=Math.round(daily*0.1); 
                               break;
                               
            // Press 10%
            case "ID_PRESS" : amount=Math.round(daily*0.1); 
                               break;
                               
            // Commenters 5%
            case "ID_COMM" : amount=Math.round(daily*0.05); 
                             break;

            // Military 10%
            case "ID_MILITARY" : amount=Math.round(daily*0.1); 
                                 break;
                               
            // Referers 10%
            case "ID_REFS" : amount=Math.round(daily*0.1); 
                             break;
                               
            // Pol influence 5%
            case "ID_POL_INF" : amount=Math.round(daily*0.1); 
                                break;
                               
            // Pol endorement 5%
            case "ID_POL_END" : amount=Math.round(daily*0.05); 
                               break;
                               
            // Military units 5%
            case "ID_MIL_UNITS" : amount=Math.round(daily*0.05); 
                                  break;
                               
            // Political parties 5%
            case "ID_POL_PARTY" : amount=Math.round(daily*0.05); 
                                  break;
                               
            // Country area 5%
            case "ID_COU_AREA" : amount=Math.round(daily*0.05); 
                                 break;
                                 
            // Country area 5%
            case "ID_COU_ENERGY" : amount=Math.round(daily*0.05); 
                                 break;
                             
            // Node operators 5%
            case "ID_NODES" : amount=Math.round(daily*0.10); 
                              break;
        }
        
        // Return
        return amount;
    }
    
    public double getRewardVal(String adr, String reward, long block) throws Exception
    {
        // Value
        double val=0;
        
        // Check reward
        if (!reward.equals("ID_ENERGY") && 
            !reward.equals("ID_REF") && 
            !reward.equals("ID_MILITARY") && 
            !reward.equals("ID_POL_INF") && 
            !reward.equals("ID_POL_END"))
        throw new Exception("Invalid bonus - CUtils.java");
        
        // Energy reward
        if (reward.equals("ID_ENERGY"))
        {
            // Daily pool
            double pool=this.getRewardPool(reward);
            
            // Load paid energy rewards in last 24 hours
            ResultSet rs=UTILS.DB.executeQuery("SELECT SUM(par_f) AS total "
                                               + "FROM rewards "
                                              + "WHERE reward='ID_ENERGY' "
                                                + "AND block>"+(block-1440));
            
            // Next
            rs.next();
            
            // Get data
            double total=Math.round(rs.getDouble("total"));
            
            if (total>0)
            {
               // Per point
               double per_point=UTILS.BASIC.round(pool/total, 4);
            
               // Minimum ?
               if (per_point<0.0001) 
                   per_point=0.0001;
            
               // Maximum ?
               if (per_point>0.01) 
                   per_point=0.01;
            
               // Value
               val=UTILS.BASIC.getEnergy(adr)*per_point;
            }
            else val=UTILS.BASIC.getEnergy(adr)*0.01;
        }    
        
        return UTILS.BASIC.round(val, 4);
    }
    
    public void refreshEnergy(String adr) throws Exception
    {
        // energy
        double e=0;
        
        // Is address
        if (!this.isAdr(adr))
           throw new Exception("Invalid address");
        
        // Load 
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM stocuri "
                                          + "WHERE (adr='"+adr+"' OR rented_to='"+adr+"')"
                                            + "AND (in_use>0 || tip='ID_GIFT')");
        
        // Calculate energy
        while (rs.next())
        {
            // Product
            String prod=rs.getString("tip");
            
            if (this.isEnergyProd(prod) && 
               !this.isEnergyBooster(prod))
                  e=e+this.getProdEnergy(prod);
        }
        
        // Energy per block
        e=e/1440;
        
        // Update energy
        UTILS.DB.executeUpdate("UPDATE adr "
                                + "SET energy_block='"+e+"' "
                              + "WHERE adr='"+adr+"'");
    }
    
    public String stripQuality(String prod) throws Exception
    {
        // String ID ?
        if (!this.isStringID(prod))
            throw new Exception("Invalid address");
        
        // Strip quality
        String str=prod.replace("_Q1", "");
        str=prod.replace("_Q2", "");
        str=prod.replace("_Q3", "");
        
        // Return
        return str;
    }
    
     public void checkNewTransIPN(String status, 
                                  String target_adr, 
                                  String dest, 
                                  double amount, 
                                  String cur, 
                                  String mes, 
                                  String hash, 
                                  long block) throws Exception
         {
            // IPN
            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM ipn "
                                              + "WHERE adr='"+dest+"'");
                 
            if (UTILS.DB.hasData(rs) && UTILS.WALLET.isMine(dest))
            {
                   // Next
                   rs.next();
                   
                   // creates loader thread
                   CLoader loader=new CLoader(rs.getString("web_link"), rs.getString("web_pass"));
                   
                   // Source
                   loader.addParam("src", target_adr);
                   
                   // Dest
                   loader.addParam("dest", dest);
                   
                   // Amount
                   loader.addParam("amount", String.valueOf(amount));
                   
                   // Currency
                   loader.addParam("currency", cur);
                   
                   // Message
                   String message="";
                   if (mes!=null)
                       message=mes;
                    
                   loader.addParam("mes", message);
                   
                   // transaction hash
                   loader.addParam("tx_hash", hash);
                   
                   // Block
                   loader.addParam("block", String.valueOf(block));
                   
                   // Start
                   loader.start();
            }
         
         }
     
     public long userFromAdr(String adr) throws Exception
     {
        // Address valid ?
        if (!this.isAdr(adr))
            throw new Exception("Invalid address");
         
        // My address ?
        if (!UTILS.WALLET.isMine(adr))
            throw new Exception("Invalid address");
        
        // Load data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM my_adr "
                                          + "WHERE adr='"+adr+"'");
        
        // Next
        rs.next();
        
        // Return
        return rs.getLong("userID");
     }
     
     public boolean isWorking(String adr) throws Exception
     {
        // Address valid ?
        if (!this.isAdr(adr))
            throw new Exception("Invalid address"); 
        
        // Load data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM adr "
                                          + "WHERE adr='"+adr+"'");
        
        // Next
        rs.next();
        
        // Return
        if (rs.getLong("work")>0)
            return true;
        else
            return false;
     }
     
     public boolean isTraveling(String adr) throws Exception
     {
        // Address valid ?
        if (!this.isAdr(adr))
            throw new Exception("Invalid address"); 
        
        // Load data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM adr "
                                          + "WHERE adr='"+adr+"'");
        
        // Next
        rs.next();
        
        // Return
        if (rs.getLong("travel"
                + "")>0)
            return true;
        else
            return false;
     }
     
     public long getUserID(String adr) throws Exception
     {
         // Load data
         ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                            + "FROM my_adr "
                                           + "WHERE adr='"+adr+"'");
         
         // Next
         rs.next();
         
         // Return
         return (rs.getLong("userID"));
     }
     
     public boolean isOrg(String type, long ID) throws Exception
     {
        // Org type
        if (!type.equals("ID_MIL_UNIT") && 
             !type.equals("ID_POL_PARTY"))
        throw new Exception("Invalid organisation type"); 
        
        // Load org data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM orgs "
                                          + "WHERE type='"+type+"' "
                                            + "AND orgID='"+ID+"'");
        
        // Has data ?
        if (UTILS.DB.hasData(rs))
            return true;
        else
            return false;
     }
     
     public boolean isTax(String tax) throws Exception
     {
         // String ID ?
         if (!this.isStringID(tax))
             throw new Exception ("Invalid tax, CUtils.java, line 1933");
             
         // Query
         ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                            + "FROM taxes "
                                           + "WHERE tax='"+tax+"'");
         
         // Has data
         if (UTILS.DB.hasData(rs))
             return true;
         else
             return false;
     }
     
     public double getTax(String cou, String tax, String prod) throws Exception
     {
         // Country ?
         if (!this.isCou(cou))
            throw new Exception ("Invalid country, CUtils.java, line 1951");
         
         // Tax
         if (!this.isTax(tax))
              throw new Exception ("Invalid tax, CUtils.java, line 1955");
         
         // Valid product ?
         if (!prod.equals(""))
           if (!this.isProd(prod))
              throw new Exception ("Invalid product, CUtils.java, line 1959");
             
         // Load data
         ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                            + "FROM taxes "
                                           + "WHERE cou='"+cou+"' "
                                             + "AND tax='"+tax+"' "
                                             + "AND prod='"+prod+"'");
         
         // Has data
         if (!UTILS.DB.hasData(rs))
             throw new Exception ("Invalid tax, CUtils.java, line 1959");
         
         // Next
         rs.next();
         
         // Return
         return rs.getDouble("value");
     }
     
     public String getCouAdr(String cou) throws Exception
     {
         // Valid country ?
         if (!this.isCountry(cou))
              throw new Exception ("Invalid country, CUtils.java, line 1959");
             
         // Load data
         ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                            + "FROM countries "
                                           + "WHERE code='"+cou+"'");
         
         // Next
         rs.next();
         
         // Return
         return rs.getString("adr");
     }
     
     public boolean isSealed(String adr) throws Exception
     {
        // Valid address ?
        if (!this.isAdr(adr))
              throw new Exception ("Invalid address, CUtils.java, line 1959");
         
         // Load data
         ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                            + "FROM adr_attr "
                                           + "WHERE adr='"+adr+"' "
                                             + "AND attr='ID_SEALED'");
         
         // Has data
         if (!UTILS.DB.hasData(rs))
             throw new Exception ("Invalid address, CUtils.java, line 1959");
         
         // Has data
         if (UTILS.DB.hasData(rs))
             return true;
         else
             return false;
     }
}
