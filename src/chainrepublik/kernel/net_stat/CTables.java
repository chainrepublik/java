package chainrepublik.kernel.net_stat;

import chainrepublik.kernel.net_stat.tables.*;

public class CTables 
{
    // Actions
    public CActions actions;
    
    // Adata
    public CAData adata;
    
    // Adr
    public CAdr adr;
    
    // Adr trace
    public CAdrTrace adr_trace;
    
    // Adr attr
    public CAdrAttr adr_attr;
    
    // Ads
    public CAds ads;
    
    // Agents Log
    public CAgentsLog agents_log;
    
    // Allow trans
    public CAllowTrans allow_trans;
    
    // Assets
    public CAssets assets;
    
    // Assets mkts
    public CAssetsMkts assets_mkts;
    
    // Assets mkts pos
    public CAssetsMktsPos assets_mkts_pos;
    
    // Assets mkts trades
    public CAssetsMktsTrades assets_mkts_trades;
    
    // Assets owners
    public CAssetsOwners assets_owners;
    
    // Blocks
    public CBlocks blocks;
    
    // Blocks pool
    public CBlocksPool blocks_pool;
    
    // Bonuses
    public CBonuses bonuses;
    
    // Checkpoints
    public CCheckpoints checkpoints;
    
    // Com prods
    public CComProds com_prods;
    
    // Comments
    public CComments comments;
    
    // Companies
    public CCompanies companies;
    
    // Con log
    public CConLog con_log;
    
    // Countries
    public CCountries countries;
    
    // Crons
    public CCrons crons;
    
    // Cron runs
    public CCronRuns cron_runs;
    
    // Del votes
    public CDelVotes del_votes;
    
    // Delegates
    public CDelegates delegates;
    
    // Delegates log
    public CDelegatesLog delegates_log;
    
    // Dividends
    public CDividends dividends;
    
    // Endorsers
    public CEndorsers endorsers;
    
    // ErrorLog
    public CErrorLog error_log;
    
    // Escrowed
    public CEscrowed escrowed;
    
    // Events
    public CEvents events;
    
    // Exchange
    public CExchange exchange;
    
    // Hidden
    public CHidden hidden;
    
    // Items Consumed
    public CItemsConsumed items_consumed;
    
    // Ips
    public CIps ips;
    
    // Laws
    public CLaws laws;
    
    // Laws Votes
    public CLawsVotes laws_votes;
    
    // Mes
    public CMes mes;
    
    // My adr
    public CMyAdr my_adr;
    
    // My trans
    public CMyTrans my_trans;
    
    // Net Stat
    public CNetStatTable net_stat;
    
    // Orgs
    public COrgs orgs;
    
    // Orgs props
    public COrgsProps orgs_props;
    
    // Orgs props votes
    public COrgsPropsVotes orgs_props_votes;
    
    // Out Emails
    public COutReq out_req;
    
    // Packets
    public CPackets packets;
    
    // Peers
    public CPeers peers;
    
    // Peers pool
    public CPeersPool peers_pool;
    
    // Rec Packets
    public CRecPackets rec_packets;
    
    // Ref stats
    public CRefStats ref_stats;
    
    // Rent contracts
    public CRentContracts rent_contracts;
    
    // Rewards
    public CRewards rewards;
    
    // Stocuri
    public CStocuri stocuri;
    
    // Seas
    public CSeas seas;
    
    // Sync
    public CSync sync;
    
    // Sys stats
    public CSysStats sys_stats;
    
    // Status log
    public CStatusLog status_log;
    
    // Taxes
    public CTaxes taxes;
    
    // Tipuri Companii
    public CTipuriCompanii tipuri_companii;
    
    // Tipuri Licente
    public CTipuriLicente tipuri_licente;
    
    // Tipuri Produse
    public CTipuriProduse tipuri_produse;
    
    // Trans
    public CTrans trans;
    
    // Trans Pool
    public CTransPool trans_pool; 
    
    // Tweets
    public CTweets tweets;
    
    // Tweets Follow
    public CTweetsFollow tweets_follow;
    
    // Votes
    public CVotes votes;
    
    // Votes Stats
    public CVotesStats votes_stats;  
    
    // Wars
    public CWars wars;
    
    // Wars Fighters
    public CWarsFighters wars_fighters;
    
    // Web Ops
    public CWebOps web_ops;
    
    // Web Sys Data
    public CWebSysData web_sys_data;
    
    // Web Users
    public CWebUsers web_users;
    
    // Work Procs
    public CWorkProcs work_procs;
    
    // Workplaces
    public CWorkplaces workplaces;
    
    public CTables()
    {
        // Actions
        this.actions=new CActions();
    
        // Adata
        this.adata=new CAData();
    
        // Adr
        this.adr=new CAdr();
    
        // Adr attr
        this.adr_attr=new CAdrAttr();
        
        // Adr trace
        this.adr_trace=new CAdrTrace();
    
        // Ads
        this.ads=new CAds();
        
        // Agents Log
        this.agents_log=new CAgentsLog();
    
        // Allow trans
        this.allow_trans=new CAllowTrans();
    
        // Assets
        this.assets=new CAssets();
    
        // Assets mkts
        this.assets_mkts=new CAssetsMkts();
    
        // Assets mkts pos
        this.assets_mkts_pos=new CAssetsMktsPos();
        
        // Assets mkts trades
        this.assets_mkts_trades=new CAssetsMktsTrades();
    
        // Assets owners
        this.assets_owners=new CAssetsOwners();
    
        // Blocks
        this.blocks=new CBlocks();
    
        // Blocks pool
        this.blocks_pool=new CBlocksPool();
    
        // Bonuses
        this.bonuses=new CBonuses();
    
        // Checkpoints
        this.checkpoints=new CCheckpoints();
    
        // Com prods
        this.com_prods=new CComProds();
    
        // Comments
        this.comments=new CComments();
    
        // Companies
        this.companies=new CCompanies();
        
        // Crons
        this.crons=new CCrons();
        
        // Cron runs
        this.cron_runs=new CCronRuns();
    
        // Con log
        this.con_log=new CConLog();
    
        // Countries
        this.countries=new CCountries();
    
        // Del votes
        this.del_votes=new CDelVotes();
    
        // Delegates
        this.delegates=new CDelegates();
    
        // Delegates log
        this.delegates_log=new CDelegatesLog();
    
        // Dividends
        this.dividends=new CDividends();
    
        // Endorsers
        this.endorsers=new CEndorsers();
    
        // ErrorLog
        this.error_log=new CErrorLog();
    
        // Escrowed
        this.escrowed=new CEscrowed();
    
        // Events
        this.events=new CEvents();
        
        // Exchange
        this.exchange=new CExchange();
        
        // Hidden
        this.hidden=new CHidden();
    
        // Items Consumed
        this.items_consumed=new CItemsConsumed();
        
        // Ips
        this.ips=new CIps();
    
        // Laws
        this.laws=new CLaws();
    
        // Laws Votes
        this.laws_votes=new CLawsVotes();
    
        // Mes
        this.mes=new CMes();
    
        // My adr
        this.my_adr=new CMyAdr();
    
        // My trans
        this.my_trans=new CMyTrans();
        
        // Orgs
        this.orgs=new COrgs();
        
        // Orgs props
        this.orgs_props=new COrgsProps();
        
        // Orgs props votes
        this.orgs_props_votes=new COrgsPropsVotes();
    
        // Net Stat
        this.net_stat=new CNetStatTable();
    
        // Out Emails
        this.out_req=new COutReq();
    
        // Packets
        this.packets=new CPackets();
    
        // Peers
        this.peers=new CPeers();
    
        // Peers pool
        this.peers_pool=new CPeersPool();
    
        // Rec Packets
        this.rec_packets=new CRecPackets();
    
        // Ref stats
       this.ref_stats=new CRefStats();
    
        // Rent contracts
        this.rent_contracts=new CRentContracts();
    
        // Rewards
        this.rewards=new CRewards();
    
        // Stocuri
        this.stocuri=new CStocuri();
        
        // Status log
        this.status_log=new CStatusLog();
        
        // Sync
        this.sync=new CSync();
        
        // Seas
        this.seas=new CSeas();
        
        // Sys stats
        this.sys_stats=new CSysStats();
    
        // Taxes
        this.taxes=new CTaxes();
    
        // Tipuri Companii
        this.tipuri_companii=new CTipuriCompanii();
    
        // Tipuri Licente
        this.tipuri_licente=new CTipuriLicente();
    
        // Tipuri Produse
        this.tipuri_produse=new CTipuriProduse();
    
        // Trans
        this.trans=new CTrans();
    
        // Trans Pool
        this.trans_pool=new CTransPool(); 
    
        // Tweets
        this.tweets=new CTweets();
    
        // Tweets Follow
        this.tweets_follow=new CTweetsFollow();
    
        // Votes
        this.votes=new CVotes();
    
        // Votes Stats
        this.votes_stats=new CVotesStats();  
    
        // Wars
        this.wars=new CWars();
    
        // Wars Fighters
        this.wars_fighters=new CWarsFighters();
    
        // Web Ops
        this.web_ops=new CWebOps();
    
        // Web Sys Data
        this.web_sys_data=new CWebSysData();
    
        // Web Users
        this.web_users=new CWebUsers();
    
        // Work Procs
        this.work_procs=new CWorkProcs();
    
        // Workplaces
        this.workplaces=new CWorkplaces();
    }
    
    public void createTables() throws Exception
    {
        // Actions
        this.actions.create();
    
        // Adata
        this.adata.create();
    
        // Adr
        this.adr.create();
    
        // Adr attr
        this.adr_attr.create();
    
        // Ads
        this.ads.create();
        
        // Adr trace
        this.adr_trace.create();
        
        // Agents Log
        this.agents_log.create();
    
        // Allow trans
        this.allow_trans.create();
    
        // Assets
        this.assets.create();
    
        // Assets mkts
        this.assets_mkts.create();
    
        // Assets mkts pos
        this.assets_mkts_pos.create();
        
        // Assets mkts trades
        this.assets_mkts_trades.create();
    
        // Assets owners
        this.assets_owners.create();
    
        // Blocks
        this.blocks.create();
    
        // Blocks pool
        this.blocks_pool.create();
    
        // Checkpoints
        this.checkpoints.create();
    
        // Com prods
        this.com_prods.create();
    
        // Comments
        this.comments.create();
    
        // Companies
        this.companies.create();
    
        // Con log
        this.con_log.create();
    
        // Countries
        this.countries.create();
        
        // Crons
        this.crons.create();
        
        // Cron Runs
        this.cron_runs.create();
    
        // Del votes
        this.del_votes.create();
    
        // Delegates
        this.delegates.create();
    
        // Delegates log
        this.delegates_log.create();
    
        // Dividends
        this.dividends.create();
    
        // Endorsers
        this.endorsers.create();
    
        // ErrorLog
        this.error_log.create();
    
        // Escrowed
        this.escrowed.create();
    
        // Events
        this.events.create();
        
        // Exchange
        this.exchange.create();
        
        // Hidden
        this.hidden.create();
    
        // Items Consumed
        this.items_consumed.create();
        
        // Ips
        this.ips.create();
    
        // Laws
        this.laws.create();
    
        // Laws Votes
        this.laws_votes.create();
    
        // Mes
        this.mes.create();
    
        // My adr
        this.my_adr.create();
    
        // My trans
        this.my_trans.create();
    
        // Net Stat
        this.net_stat.create();
        
        // Orgs
        this.orgs.create();
        
        // Orgs props
        this.orgs_props.create();
        
        // Orgs props votes
        this.orgs_props_votes.create();
    
        // Out Emails
        this.out_req.create();
    
        // Packets
        this.packets.create();
    
        // Peers
        this.peers.create();
    
        // Peers pool
        this.peers_pool.create();
    
        // Rec Packets
        this.rec_packets.create();
    
        // Ref stats
        this.ref_stats.create();
    
        // Rent contracts
        this.rent_contracts.create();
    
        // Rewards
        this.rewards.create();
    
        // Stocuri
        this.stocuri.create();
        
        // Status Log
        this.status_log.create();
    
        // Sync
        this.sync.create();
        
        // Seas
        this.seas.create();
        
        // Sys stats
        this.sys_stats.create();
    
        // Tipuri Companii
        this.tipuri_companii.create();
    
        // Tipuri Licente
        this.tipuri_licente.create();
    
        // Tipuri Produse
        this.tipuri_produse.create();
        
        // Taxes
        this.taxes.create();
        
        // Bonuses
        this.bonuses.create();
    
        // Trans
        this.trans.create();
    
        // Trans Pool
        this.trans_pool.create(); 
    
        // Tweets
        this.tweets.create();
    
        // Tweets Follow
        this.tweets_follow.create();
    
        // Votes
        this.votes.create();
    
        // Votes Stats
        this.votes_stats.create();  
    
        // Wars
        this.wars.create();
    
        // Wars Fighters
        this.wars_fighters.create();
    
        // Web Ops
        this.web_ops.create();
    
        // Web Sys Data
        this.web_sys_data.create();
    
        // Web Users
        this.web_users.create();
    
        // Work Procs
        this.work_procs.create();
    
        // Workplaces
        this.workplaces.create();
    }
    
    public void refresh(long block, String hash) throws Exception
    {
        // Adata
        this.adata.refresh(block, hash);
    
        // Adr
        this.adr.refresh(block, hash);
        
        // Ads
        this.ads.refresh(block, hash);
        
        // Agents log
        this.agents_log.refresh(block, hash);
    
        // Adr attr
        this.adr_attr.refresh(block, hash);
    
        // Assets
        this.assets.refresh(block, hash);
    
        // Assets mkts
        this.assets_mkts.refresh(block, hash);
    
        // Assets mkts pos
        this.assets_mkts_pos.refresh(block, hash);
        
        // Assets mkts trades
        this.assets_mkts_trades.refresh(block, hash);
        
        // Assets owners
        this.assets_owners.refresh(block, hash);
    
        // Bonuses
        this.bonuses.refresh(block, hash);
    
        // Comments
        this.comments.refresh(block, hash);
    
        // Companies
        this.companies.refresh(block, hash);
    
        // Countries
        this.countries.refresh(block, hash);
        
        // Delegates
        this.delegates.refresh(block, hash);
        
        // Delegates log
        this.delegates_log.refresh(block, hash);
        
        // Del votes
        this.del_votes.refresh(block, hash);
        
        // Dividends
        this.dividends.refresh(block, hash);
        
        // Escrowed
        this.escrowed.refresh(block, hash);
        
        // Exchange
        this.exchange.refresh(block, hash);
        
        // Endorsers
        this.endorsers.refresh(block, hash);
        
        // Events
        this.events.refresh(block, hash);
        
        // Items consumed
        this.items_consumed.refresh(block, hash);
    
        // Laws
        this.laws.refresh(block, hash);
        
        // Laws votes
        this.laws_votes.refresh(block, hash);
    
        // Orgs
        this.orgs.refresh(block, hash);
        
        // Orgs props
        this.orgs_props.refresh(block, hash);
        
        // Orgs props votes
        this.orgs_props_votes.refresh(block, hash);
        
        // Stocuri
        this.stocuri.refresh(block, hash);
        
        // Rent contracts
        this.rent_contracts.refresh(block, hash);
        
        // Rewards
        this.rewards.refresh(block, hash);
        
        // Taxes
        this.taxes.refresh(block, hash);
    
        // Tweets
        this.tweets.refresh(block, hash);
        
        // Tweets follow
        this.tweets_follow.refresh(block, hash);
        
        // Votes
        this.votes.refresh(block, hash);
    
        // Wars
        this.wars.refresh(block, hash);
        
        // Wars fighters
        this.wars_fighters.refresh(block, hash);
        
        // Wark procs
        this.work_procs.refresh(block, hash);
    
        // Workplaces
        this.workplaces.refresh(block, hash);
    }
    
    public void reorganize(long block, String hash) throws Exception
    {
        // Actions
        this.actions.reorganize(block, hash);
    
        // Adata
        this.adata.reorganize(block, hash);
    
        // Adr
        this.adr.reorganize(block, hash);
    
        // Adr attr
        this.adr_attr.reorganize(block, hash);
        
        // Adr trace
        this.adr_trace.reorganize(block, hash);
    
        // Ads
        this.ads.reorganize(block, hash);
        
        // Agents Log
        this.agents_log.reorganize(block, hash);
    
        // Allow trans
        this.allow_trans.reorganize(block, hash);
    
        // Assets
        this.assets.reorganize(block, hash);
    
        // Assets mkts
        this.assets_mkts.reorganize(block, hash);
    
        // Assets mkts pos
        this.assets_mkts_pos.reorganize(block, hash);
        
        // Assets mkts trades
        this.assets_mkts_trades.reorganize(block, hash);
    
        // Assets owners
        this.assets_owners.reorganize(block, hash);
    
        // Blocks
        this.blocks.reorganize(block, hash);
    
        // Checkpoints
        this.checkpoints.reorganize(block, hash);
    
        // Com prods
        this.com_prods.reorganize(block, hash);
    
        // Comments
        this.comments.reorganize(block, hash);
    
        // Companies
        this.companies.reorganize(block, hash);
    
        // Con log
        this.con_log.reorganize(block, hash);
    
        // Countries
        this.countries.reorganize(block, hash);
        
        // Crons
        this.crons.reorganize(block, hash);
        
        // Cron Runs
        this.cron_runs.reorganize(block, hash);
    
        // Del votes
        this.del_votes.reorganize(block, hash);
    
        // Delegates
        this.delegates.reorganize(block, hash);
    
        // Delegates log
        this.delegates_log.reorganize(block, hash);
    
        // Dividends
        this.dividends.reorganize(block, hash);
    
        // Endorsers
        this.endorsers.reorganize(block, hash);
    
        // ErrorLog
        this.error_log.reorganize(block, hash);
    
        // Escrowed
        this.escrowed.reorganize(block, hash);
    
        // Events
        this.events.reorganize(block, hash);
        
        // Exchange
        this.exchange.reorganize(block, hash);
        
        // Hidden
        this.hidden.reorganize(block, hash);
    
        // Items Consumed
        this.items_consumed.reorganize(block, hash);
        
        // Ips
        this.ips.reorganize(block, hash);
    
        // Laws
        this.laws.reorganize(block, hash);
    
        // Laws Votes
        this.laws_votes.reorganize(block, hash);
    
        // Mes
        this.mes.reorganize(block, hash);
    
        // My adr
        this.my_adr.reorganize(block, hash);
    
        // My trans
        this.my_trans.reorganize(block, hash);
    
        // Net Stat
        this.net_stat.reorganize(block, hash);
        
        // Orgs
        this.orgs.reorganize(block, hash);
        
        // Orgs props
        this.orgs_props.reorganize(block, hash);
        
        // Orgs props votes
        this.orgs_props_votes.reorganize(block, hash);
    
        // Out Emails
        this.out_req.reorganize(block, hash);
    
        // Packets
        this.packets.reorganize(block, hash);
    
        // Peers
        this.peers.reorganize(block, hash);
    
        // Peers pool
        this.peers_pool.reorganize(block, hash);
    
        // Rec Packets
        this.rec_packets.reorganize(block, hash);
    
        // Ref stats
        this.ref_stats.reorganize(block, hash);
    
        // Rent contracts
        this.rent_contracts.reorganize(block, hash);
    
        // Rewards
        this.rewards.reorganize(block, hash);
    
        // Stocuri
        this.stocuri.reorganize(block, hash);
        
        // Status Log
        this.status_log.reorganize(block, hash);
    
        // Sync
        this.sync.reorganize(block, hash);
        
        // Sys stats
        this.sys_stats.reorganize(block, hash);
    
        // Tipuri Companii
        this.tipuri_companii.reorganize(block, hash);
    
        // Tipuri Licente
        this.tipuri_licente.reorganize(block, hash);
    
        // Tipuri Produse
        this.tipuri_produse.reorganize(block, hash);
        
        // Taxes
        this.taxes.reorganize(block, hash);
        
        // Bonuses
        this.bonuses.reorganize(block, hash);
    
        // Trans
        this.trans.reorganize(block, hash);
    
        // Trans Pool
        this.trans_pool.reorganize(block, hash);
    
        // Tweets
        this.tweets.reorganize(block, hash);
    
        // Tweets Follow
        this.tweets_follow.reorganize(block, hash);
    
        // Votes
        this.votes.reorganize(block, hash);
    
        // Votes Stats
        this.votes_stats.reorganize(block, hash); 
    
        // Wars
        this.wars.reorganize(block, hash);
    
        // Wars Fighters
        this.wars_fighters.reorganize(block, hash);
    
        // Web Ops
        this.web_ops.reorganize(block, hash);
    
        // Web Sys Data
        this.web_sys_data.reorganize(block, hash);
    
        // Web Users
        this.web_users.reorganize(block, hash);
    
        // Work Procs
        this.work_procs.reorganize(block, hash);
    
        // Workplaces
        this.workplaces.reorganize(block, hash);
    }
    
    public void expired(long block) throws Exception
    {
        // Actions
        this.actions.expired(block);
    
        // Adata
        this.adata.expired(block);
    
        // Adr
        this.adr.expired(block);
    
        // Adr attr
        this.adr_attr.expired(block);
    
        // Ads
        this.ads.expired(block);
        
        // Agents Log
        this.agents_log.expired(block);
    
        // Allow trans
        this.allow_trans.expired(block);
    
        // Assets
        this.assets.expired(block);
    
        // Assets mkts
        this.assets_mkts.expired(block);
    
        // Assets mkts pos
        this.assets_mkts_pos.expired(block);
        
        // Assets mkts trades
        this.assets_mkts_trades.expired(block);
    
        // Assets owners
        this.assets_owners.expired(block);
    
        // Blocks
        this.blocks.expired(block);
    
        // Blocks pool
        this.blocks_pool.expired(block);
    
        // Bonuses
        this.bonuses.expired(block);
    
        // Checkpoints
        this.checkpoints.expired(block);
    
        // Com prods
        this.com_prods.expired(block);
    
        // Comments
        this.comments.expired(block);
    
        // Companies
        this.companies.expired(block);
    
        // Con log
        this.con_log.expired(block);
    
        // Countries
        this.countries.expired(block);
        
        // Crons
        this.crons.expired(block);
        
        // Cron Runs
        this.cron_runs.expired(block);
    
        // Del votes
        this.del_votes.expired(block);
    
        // Delegates
        this.delegates.expired(block);
    
        // Delegates log
        this.delegates_log.expired(block);
    
        // Dividends
        this.dividends.expired(block);
    
        // Endorsers
        this.endorsers.expired(block);
    
        // ErrorLog
        this.error_log.expired(block);
    
        // Escrowed
        this.escrowed.expired(block);
    
        // Events
        this.events.expired(block);
        
        // Exchange
        this.exchange.expired(block);
    
        // Items Consumed
        this.items_consumed.expired(block);
    
        // Laws
        this.laws.expired(block);
    
        // Laws Votes
        this.laws_votes.expired(block);
    
        // Mes
        this.mes.expired(block);
    
        // My adr
        this.my_adr.expired(block);
    
        // My trans
        this.my_trans.expired(block);
    
        // Net Stat
        this.net_stat.expired(block);
        
        // Orgs
        this.orgs.expired(block);
        
        // Orgs props
        this.orgs_props.expired(block);
        
        // Orgs props votes
        this.orgs_props_votes.expired(block);
    
        // Out Emails
        this.out_req.expired(block);
    
        // Packets
        this.packets.expired(block);
    
        // Peers
        this.peers.expired(block);
    
        // Peers pool
        this.peers_pool.expired(block);
    
        // Rec Packets
        this.rec_packets.expired(block);
    
        // Ref stats
        this.ref_stats.expired(block);
    
        // Rent contracts
        this.rent_contracts.expired(block);
    
        // Rewards
        this.rewards.expired(block);
    
        // Stocuri
        this.stocuri.expired(block);
        
        // Status Log
        this.status_log.expired(block);
    
        // Sync
        this.sync.expired(block);
        
        // Sys stats
        this.sys_stats.expired(block);
    
        // Taxes
        this.taxes.expired(block);
    
        // Tipuri Companii
        this.tipuri_companii.expired(block);
    
        // Tipuri Licente
        this.tipuri_licente.expired(block);
    
        // Tipuri Produse
        this.tipuri_produse.expired(block);
    
        // Trans
        this.trans.expired(block);
    
        // Trans Pool
        this.trans_pool.expired(block); 
    
        // Tweets
        this.tweets.expired(block);
    
        // Tweets Follow
        this.tweets_follow.expired(block);
    
        // Votes
        this.votes.expired(block);
    
        // Votes Stats
        this.votes_stats.expired(block);  
    
        // Wars
        this.wars.expired(block);
    
        // Wars Fighters
        this.wars_fighters.expired(block);
    
        // Web Ops
        this.web_ops.expired(block);
    
        // Web Sys Data
        this.web_sys_data.expired(block);
    
        // Web Users
        this.web_users.expired(block);
    
        // Work Procs
        this.work_procs.expired(block);
    
        // Workplaces
        this.workplaces.expired(block);
    }
    
    public void full_resync() throws Exception
    {
        // Actions
        this.actions.fullResync();
    
        // Adata
        this.adata.fullResync();
    
        // Adr
        this.adr.fullResync();
    
        // Adr attr
        this.adr_attr.fullResync();
    
        // Ads
        this.ads.fullResync();
        
        // Agents Log
        this.agents_log.fullResync();
    
        // Allow trans
        this.allow_trans.fullResync();
    
        // Assets
        this.assets.fullResync();
    
        // Assets mkts
        this.assets_mkts.fullResync();
    
        // Assets mkts pos
        this.assets_mkts_pos.fullResync();
        
        // Assets mkts trades
        this.assets_mkts_trades.fullResync();
    
        // Assets owners
        this.assets_owners.fullResync();
    
        // Blocks
        this.blocks.fullResync();
    
        // Blocks pool
        this.blocks_pool.fullResync();
    
        // Checkpoints
        this.checkpoints.fullResync();
    
        // Com prods
        this.com_prods.fullResync();
    
        // Comments
        this.comments.fullResync();
    
        // Companies
        this.companies.fullResync();
    
        // Con log
        this.con_log.fullResync();
    
        // Countries
        this.countries.fullResync();
        
        // Crons
        this.crons.fullResync();
        
        // Cron Runs
        this.cron_runs.fullResync();
    
        // Del votes
        this.del_votes.fullResync();
    
        // Delegates
        this.delegates.fullResync();
    
        // Delegates log
        this.delegates_log.fullResync();
    
        // Dividends
        this.dividends.fullResync();
    
        // Endorsers
        this.endorsers.fullResync();
    
        // ErrorLog
        this.error_log.fullResync();
    
        // Escrowed
        this.escrowed.fullResync();
    
        // Events
        this.events.fullResync();
        
        // Exchange
        this.exchange.fullResync();
        
        // Hidden
        this.hidden.fullResync();
    
        // Items Consumed
        this.items_consumed.fullResync();
        
        // Ips
        this.ips.fullResync();
    
        // Laws
        this.laws.fullResync();
    
        // Laws Votes
        this.laws_votes.fullResync();
    
        // Mes
        this.mes.fullResync();
    
        // My adr
        this.my_adr.fullResync();
    
        // My trans
        this.my_trans.fullResync();
    
        // Net Stat
        this.net_stat.fullResync();
        
        // Orgs
        this.orgs.fullResync();
        
        // Orgs props
        this.orgs_props.fullResync();
        
        // Orgs props votes
        this.orgs_props_votes.fullResync();
    
        // Out Emails
        this.out_req.fullResync();
    
        // Packets
        this.packets.fullResync();
    
        // Peers
        this.peers.fullResync();
    
        // Peers pool
        this.peers_pool.fullResync();
    
        // Rec Packets
        this.rec_packets.fullResync();
    
        // Ref stats
        this.ref_stats.fullResync();
    
        // Rent contracts
        this.rent_contracts.fullResync();
    
        // Rewards
        this.rewards.fullResync();
    
        // Stocuri
        this.stocuri.fullResync();
        
        // Status Log
        this.status_log.fullResync();
    
        // Sync
        this.sync.fullResync();
        
        // Sys stats
        this.sys_stats.fullResync();
    
        // Tipuri Companii
        this.tipuri_companii.fullResync();
    
        // Tipuri Licente
        this.tipuri_licente.fullResync();
    
        // Tipuri Produse
        this.tipuri_produse.fullResync();
        
        // Taxes
        this.taxes.fullResync();
        
        // Bonuses
        this.bonuses.fullResync();
    
        // Trans
        this.trans.fullResync();
    
        // Trans Pool
        this.trans_pool.fullResync(); 
    
        // Tweets
        this.tweets.fullResync();
    
        // Tweets Follow
        this.tweets_follow.fullResync();
    
        // Votes
        this.votes.fullResync();
    
        // Votes Stats
        this.votes_stats.fullResync();  
    
        // Wars
        this.wars.fullResync();
    
        // Wars Fighters
        this.wars_fighters.fullResync();
    
        // Web Ops
        this.web_ops.fullResync();
    
        // Web Sys Data
        this.web_sys_data.fullResync();
    
        // Web Users
        this.web_users.fullResync();
    
        // Work Procs
        this.work_procs.fullResync();
    
        // Workplaces
        this.workplaces.fullResync();
    }
}
