package chainrepublik.network.packets.adr;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;

public class CAddAttrPayload extends CPayload
{
    // Attribute
    String attr;
    
    // S1
    String s1;
    
    // S2
    String s2;
    
    // S3
    String s3;
    
    // L1
    long l1;
    
    // L2
    long l2;
    
    // L3
    long l3;
    
    // D1
    double d1;
    
    // D2
    double d2;
    
    // D3
    double d3;
    
    // Days
    long days;
    
    // Serial
    private static final long serialVersionUID = 100L;
    
    public CAddAttrPayload(String adr, 
                           String attr,
                           String s1, 
                           String s2, 
                           String s3,
                           long l1,
                           long l2,
                           long l3,
                           double d1,
                           double d2,
                           double d3,
                           long days) throws Exception
    {
        // Constructor
        super(adr);
        
        // Attr
        this.attr=attr;
        
        // S1
        this.s1=s1;
    
        // S2
        this.s2=s2;
    
        // S3
        this.s3=s3;
    
        // L1
        this.l1=l1;
    
        // L2
        this.l2=l2;
    
        // L3
        this.l3=l3;
    
        // D1
        this.d1=d1;
    
        // D2
        this.d2=d2;
    
        // D3
        this.d3=d3;
    
        // UID
        this.days=days;
        
        // Trust asset
        if (this.attr.equals("ID_TRUST_ASSET"))
        {
            this.s2="";
            this.s3="";
            this.l1=0;
            this.l2=0;
            this.l3=0;
            this.d1=0;
            this.d2=0;
            this.d3=0;
        }
          
        // Hash
        hash=UTILS.BASIC.hash(this.getHash()+
                              this.attr+
                              this.s1+
                              this.s2+
                              this.s3+
                              this.l1+
                              this.l2+
                              this.l3+
                              this.d1+
                              this.d2+
                              this.d3+
                              this.days);
    }
   
   public void check(CBlockPayload block) throws Exception
   {
       // Parent
       super.check(block);
       
       // Check energy
       this.checkEnergy();
       
       // Attribute ?
       if (!this.attr.equals("ID_TRUST_ASSET"))
          throw new Exception("Invalid attribute - CAddAttrPayload.java");
        
        // Has attribute ?
        if (UTILS.BASIC.hasAttr(this.target_adr, this.attr, this.s1))
           throw new Exception("Already has this attribute - CAddAttrPayload.java");
        
        // Days
        if (this.days<1)
            throw new Exception("Invalid days - CAddAttrPayload.java");
        
        // Trust asset
        if (this.attr.equals("ID_TRUST_ASSET"))
        {
            // Check params
            if (!this.s2.equals("") || 
                !this.s3.equals("") ||
                this.l1!=0 || 
                this.l2!=0 || 
                this.l3!=0 || 
                this.d1!=0 || 
                this.d2!=0 || 
                this.d3!=0)
            throw new Exception("Invalid asset - CAddAttrPayload.java");
            
            // Is asset ?
            if (!UTILS.BASIC.isAsset(this.s1))
               throw new Exception("Invalid asset - CAddAttrPayload.java");
        }        
        
        // Hash
        String h=UTILS.BASIC.hash(this.getHash()+
                                   this.attr+
                                   this.s1+
                                   this.s2+
                                   this.s3+
                                   this.l1+
                                   this.l2+
                                   this.l3+
                                   this.d1+
                                   this.d2+
                                   this.d3+
                                   this.days);
        
        // Check hash
        if (!h.equals(this.hash))
            throw new Exception("Invalid hash - CAddAttrPayload.java");
         
    }
    
    public void commit(CBlockPayload block) throws Exception
    {
        // Constructor
        super.commit(block);
        
        // Seal
        UTILS.DB.executeUpdate("INSERT INTO adr_attr "
                                     + "SET adr='"+this.target_adr+"', "
                                         + "attr='"+this.attr+"', "
                                         + "s1='"+this.s1+"', "
                                         + "s2='"+this.s2+"', "
                                         + "s3='"+this.s3+"', "
                                         + "l1='"+this.l1+"', "
                                         + "l2='"+this.l2+"', "
                                         + "l3='"+this.l3+"', "
                                         + "d1='"+this.d1+"', "
                                         + "d2='"+this.d2+"', "
                                         + "d3='"+this.d3+"', "
                                         + "expires='"+(this.block+days*1440)+"'");
       
       // Clear trans
       UTILS.ACC.clearTrans(this.hash, "ID_ALL", this.block);
    }        
}
