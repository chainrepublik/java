package chainrepublik.agents.VM.SYSTEM;

import chainrepublik.network.packets.blocks.CBlockPayload;

public class CSystem 
{
    // Block data
    public CBlockData BLOCK=null;
    
    // Mes data
    public CMesData MES=null;
    
    // Trans data
    public CTransData TRANS=null;
    
    // Company data
    public CComData COM=null;
    
    public CSystem()
    {
        
    }
    
    // Load company data
    public void loadComData(String adr, 
                            long comID, 
                            String name,
                            String code,
                            String symbol,
                            long expires,
                            String pic)
    {
        this.COM=new CComData(adr, 
                              comID, 
                              name,
                              code,
                              symbol,
                              expires,
                              pic,
                              0,
                              0);
    }
    
    // Load block data
    public void loadBlockData(long block, 
                              String hash,
                              String prev_hash, 
                              String payload_hash, 
                              String signer, 
                              String sign, 
                              long tstamp, 
                              long nonce, 
                              String net_dif,
                              CBlockPayload block_payload)
    {
        this.BLOCK=new CBlockData(block, 
                                  hash,
                                  prev_hash, 
                                  payload_hash, 
                                  signer, 
                                  sign, 
                                  tstamp, 
                                  nonce, 
                                  net_dif,
                                  block_payload);
    }
    
    // Load trans data
    public void loadMesData(String sender, 
                            String rec, 
                            String subj, 
                            String mes,
                            String hash,
                            long tstamp)
    {
        this.MES=new CMesData(sender, 
                              rec,
                              subj, 
                              mes,
                              hash,
                              tstamp);
    }
    
    // Load mes data
    public void loadTransData(String src, 
                              String dest, 
                              double amount, 
                              String cur, 
                              String escrower, 
                              String mes,
                              String hash,
                              long tstamp)
    {
        this.TRANS=new CTransData(src, 
                                  dest,                  
                                  amount, 
                                  cur, 
                                  escrower, 
                                  mes,
                                  hash,
                                  tstamp);
    }
    
    
}
