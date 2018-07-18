package chainrepublik.network.packets.adr;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CTransferAdrPayload extends CPayload
{
    // Receiver
    String to_adr;
    
    // Serial
    private static final long serialVersionUID = 100L;
	
    public CTransferAdrPayload(String adr, 
			       String to_adr)  throws Exception
   {
        // Constructor
        super(adr);
	         
        // Receiver
        this.to_adr=to_adr;
        
        // Hash
	hash=UTILS.BASIC.hash(this.getHash()+
		              this.to_adr);
    }
	
    public boolean hasRecords(String adr, String table, String col) throws Exception
    {
        // Load data
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM "+table+" "
                                          + "WHERE "+col+"='"+adr+"'");
        
        // Has data
        if (UTILS.DB.hasData(rs))
            return true;
        
        // No rows
        return false;
    }
    
    public void replace(String tab, 
                        String col) throws Exception
    {
        UTILS.DB.executeUpdate("UPDATE "+tab+" SET "+col+"='"+this.to_adr+"' WHERE "+col+"='"+this.target_adr+"'");
    }
    
    public boolean isRegistered(String adr) throws Exception
    {
        // Adr
        if (this.hasRecords(this.to_adr, "adr", "adr") || 
            this.hasRecords(this.to_adr, "adr", "ref_adr") || 
            this.hasRecords(this.to_adr, "adr", "node_adr"))
        return true;
        
        // Adr attr
        if (this.hasRecords(this.to_adr, "adr_attr", "adr"))
            return true;
        
        // Ads
        if (this.hasRecords(this.to_adr, "ads", "adr"))
            return true;
        
        // Assets
        if (this.hasRecords(this.to_adr, "assets", "adr"))
            return true;
        
        // Assets
        if (this.hasRecords(this.to_adr, "assets", "adr"))
            return true;
        
        // Assets mkts
        if (this.hasRecords(this.to_adr, "assets_mkts", "adr"))
            return true;
        
        // Assets mkts pos
        if (this.hasRecords(this.to_adr, "assets_mkts_pos", "adr"))
            return true;
        
        // Assets owners
        if (this.hasRecords(this.to_adr, "assets_owners", "owner"))
            return true;
        
        // Blocks
        if (this.hasRecords(this.to_adr, "blocks", "signer"))
            return true;
        
        // Comments
        if (this.hasRecords(this.to_adr, "comments", "adr"))
            return true;
        
        // Companies
        if (this.hasRecords(this.to_adr, "companies", "adr") || 
            this.hasRecords(this.to_adr, "companies", "owner"))
            return true;
        
        // Countries
        if (this.hasRecords(this.to_adr, "countries", "adr") || 
            this.hasRecords(this.to_adr, "countries", "owner"))
            return true;
        
        // Del votes
        if (this.hasRecords(this.to_adr, "del_votes", "delegate") || 
            this.hasRecords(this.to_adr, "del_votes", "adr"))
            return true;
        
        // Delegates
        if (this.hasRecords(this.to_adr, "delegates", "delegate"))
            return true;
        
        // Delegates log
        if (this.hasRecords(this.to_adr, "delegates_log", "delegate"))
            return true;
        
        // Endorsers
        if (this.hasRecords(this.to_adr, "endorsers", "endorser") || 
            this.hasRecords(this.to_adr, "endorsers", "endorsed"))
            return true;
        
        // Escrowed
        if (this.hasRecords(this.to_adr, "escrowed", "sender_adr") || 
            this.hasRecords(this.to_adr, "escrowed", "rec_adr") || 
            this.hasRecords(this.to_adr, "escrowed", "escrower"))
            return true;
        
        // Events
        if (this.hasRecords(this.to_adr, "events", "adr"))
            return true;
        
        // Exchange
        if (this.hasRecords(this.to_adr, "exchange", "adr"))
            return true;
        
        // Items consumed
        if (this.hasRecords(this.to_adr, "items_consumed", "adr"))
            return true;
        
        // Laws
        if (this.hasRecords(this.to_adr, "laws", "adr"))
            return true;
        
        // Laws votes
        if (this.hasRecords(this.to_adr, "laws_votes", "adr"))
            return true;
        
        // Mes
        if (this.hasRecords(this.to_adr, "mes", "from_adr") || 
            this.hasRecords(this.to_adr, "mes", "to_adr"))
            return true;
        
        // My adr
        if (this.hasRecords(this.to_adr, "my_adr", "adr"))
            return true;
        
        // My trans
        if (this.hasRecords(this.to_adr, "my_trans", "adr") || 
            this.hasRecords(this.to_adr, "my_trans", "adr_assoc"))
            return true;
        
        // Orgs
        if (this.hasRecords(this.to_adr, "orgs", "adr"))
            return true;
        
        // Orgs props
        if (this.hasRecords(this.to_adr, "orgs_props", "adr"))
            return true;
        
        // Orgs props votes
        if (this.hasRecords(this.to_adr, "orgs_props_votes", "adr"))
            return true;
        
        // Rent contracts
        if (this.hasRecords(this.to_adr, "rent_contracts", "from_adr") || 
            this.hasRecords(this.to_adr, "rent_contracts", "to_adr"))
            return true;
        
        // Rewards
        if (this.hasRecords(this.to_adr, "rewards", "adr"))
            return true;
        
        // Stocuri
        if (this.hasRecords(this.to_adr, "stocuri", "adr"))
            return true;
        
        // Trans
        if (this.hasRecords(this.to_adr, "trans", "src"))
            return true;
        
        // Trans pool
        if (this.hasRecords(this.to_adr, "trans_pool", "src"))
            return true;
        
        // Tweets
        if (this.hasRecords(this.to_adr, "tweets", "adr"))
            return true;
        
        // Tweets follow
        if (this.hasRecords(this.to_adr, "tweets_follow", "adr") || 
            this.hasRecords(this.to_adr, "tweets_follow", "follows"))
            return true;
       
        // Votes
        if (this.hasRecords(this.to_adr, "votes", "adr"))
            return true;
        
        // War fighters
        if (this.hasRecords(this.to_adr, "wars_fighters", "adr"))
            return true;
        
        // Web ops
        if (this.hasRecords(this.to_adr, "web_ops", "fee_adr") || 
            this.hasRecords(this.to_adr, "web_ops", "target_adr"))
            return true;
        
        // Web sys data
        if (this.hasRecords(this.to_adr, "web_sys_data", "node_adr") || 
            this.hasRecords(this.to_adr, "web_sys_data", "mining_adr"))
            return true;
        
        // Web users
        if (this.hasRecords(this.to_adr, "web_users", "adr"))
            return true;
        
        // Work procs
        if (this.hasRecords(this.to_adr, "work_procs", "adr"))
            return true;
        
        // Return
        return false;
    }
    
    public void transfer(String adr, String to_adr) throws Exception
    {
        // Adr
        this.replace("adr", "adr");
        this.replace("adr", "ref_adr");
        this.replace("adr", "node_adr");
        
        // Adr attr
        this.replace("adr_attr", "adr");
        
        // Ads
        this.replace("ads", "adr");
        
        // Assets
        this.replace("assets", "adr");
        
        // Assets
        this.replace("assets", "adr");
        
        // Assets mkts
        this.replace("assets_mkts", "adr");
        
        // Assets mkts pos
        this.replace("assets_mkts_pos", "adr");
        
        // Assets owners
        this.replace("assets_owners", "owner");
        
        // Blocks
        this.replace("blocks", "signer");
        
        // Comments
        this.replace("comments", "adr");
        
        // Companies
        this.replace("companies", "adr");
        this.replace("companies", "owner");
        
        // Countries
        this.replace("countries", "adr");
        this.replace("countries", "owner");
        
        // Del votes
        this.replace("del_votes", "delegate");
        this.replace("del_votes", "adr");
        
        // Delegates
        this.replace("delegates", "delegate");
        
        // Delegates log
        this.replace("delegates_log", "delegate");
        
        // Endorsers
        this.replace("endorsers", "endorser");
        this.replace("endorsers", "endorsed");
        
        // Escrowed
        this.replace("escrowed", "sender_adr");
        this.replace("escrowed", "rec_adr");
        this.replace("escrowed", "escrower");
        
        // Events
        this.replace("events", "adr");
        
        // Exchange
        this.replace("exchange", "adr");
        
        // Items consumed
        this.replace("items_consumed", "adr");
        
        // Laws
        this.replace("laws", "adr");
        
        // Laws votes
        this.replace("laws_votes", "adr");
        
        // Mes
        this.replace("mes", "from_adr");
        this.replace("mes", "to_adr");
        
        // My adr
        this.replace("my_adr", "adr");
        
        // My trans
        this.replace("my_trans", "adr");
        this.replace("my_trans", "adr_assoc");
        
        // Orgs
        this.replace("orgs", "adr");
        
        // Orgs props
        this.replace("orgs_props", "adr");
        
        // Orgs props votes
        this.replace("orgs_props_votes", "adr");
        
        // Rent contracts
        this.replace("rent_contracts", "from_adr");
        this.replace("rent_contracts", "to_adr");
        
        // Rewards
        this.replace("rewards", "adr");
        
        // Stocuri
        this.replace("stocuri", "adr");
        
        // Trans
        this.replace("trans", "src");
        
        // Trans pool
        this.replace("trans_pool", "src");
        
        // Tweets
        this.replace("tweets", "adr");
        
        // Tweets follow
        this.replace("tweets_follow", "adr");
        this.replace("tweets_follow", "follows");
       
        // Votes
        this.replace("votes", "adr");
        
        // War fighters
        this.replace("wars_fighters", "adr");
       
        // Web ops
        this.replace("web_ops", "fee_adr");
        this.replace("web_ops", "target_adr");
        
        // Web sys data
        this.replace("web_sys_data", "node_adr");
        this.replace("web_sys_data", "mining_adr");
        
        // Web users
        this.replace("web_users", "adr");
        
        // Work procs
        this.replace("work_procs", "adr");
    }
    
    public void check(CBlockPayload block) throws Exception
    {
        // Constructor
        super.check(block);
            
        // Check energy
        this.checkEnergy();
	    	
	// Check
        if (this.isRegistered(this.to_adr))
            throw new Exception("Invalid address, CtransferAdrPayload.java, 240");
        
        // Check hash
	String h=UTILS.BASIC.hash(this.getHash()+
		                  this.to_adr);
	
        // Check hash
        if (!this.hash.equals(h))
	   throw new Exception("Invalid hash - CMesPayload.java");
    }
	   
	public void commit(CBlockPayload block) throws Exception
	{
	    // Superclass
	    super.commit(block);
            
            // Transfer
            this.transfer(this.target_adr, this.to_adr);
        
            // Position type
            UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
        }
    }

