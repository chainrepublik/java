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
	
    public void replace(String tab, 
                        String col) throws Exception
    {
        UTILS.DB.executeUpdate("UPDATE "+tab+" SET "+col+"='"+this.to_adr+"' WHERE "+col+"='"+this.target_adr+"'");
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
        
        // Citizen address ?
        if (!UTILS.BASIC.isCitAdr(this.target_adr, this.block))
           throw new Exception("Only citizens can do this action - CWorkPayload.java, 68");
	    	
	// Check
        if (UTILS.BASIC.traceAdr(this.to_adr))
            throw new Exception("Invalid address, CTransferAdrPayload.java, 240");
        
        // Same address ?
        if (this.target_adr.equals(this.to_adr))
           throw new Exception("Invalid address, CTransferAdrPayload.java, 240");
        
        // Check hash
	String h=UTILS.BASIC.hash(this.getHash()+
		                  this.to_adr);
	
        // Check hash
        if (!this.hash.equals(h))
	   throw new Exception("Invalid hash - CTransferAdrPayload.java");
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

