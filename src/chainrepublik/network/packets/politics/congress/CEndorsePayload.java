package chainrepublik.network.packets.politics.congress;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CEndorsePayload extends CPayload
{
    // Endorse adr
    String endorsed;
    
    // Type
    String type;
    
    public CEndorsePayload(String adr, 
                           String endorsed,
                           String type) throws Exception
    {
        // Superclass
	super(adr);
        
        // Endorsed
        this.endorsed=endorsed;
        
        // Type
        this.type=type;
        
        // Hash
 	hash=UTILS.BASIC.hash(this.getHash()+
 			      this.endorsed+
                              this.type);
           
        // Sign
        this.sign();
    }
    
    public void check(CBlockPayload block) throws Exception
    {
   	// Super class
   	super.check(block);
        
         // Check energy
       this.checkEnergy();
       
       // Type
       if (!this.type.equals("ID_UP") && 
           !this.type.equals("ID_DOWN"))
           throw new Exception("Invalid type, CEndorsePayload.java, 102");
        
        // Registered endorser ?
        if (!UTILS.BASIC.isRegistered(this.target_adr))
            throw new Exception("Target address is already registered, CEndorsePayload.java, 102");
        
        // Registered endorsed ?
        if (!UTILS.BASIC.isRegistered(this.endorsed))
            throw new Exception("Target address is already registered, CEndorsePayload.java, 102");
        
        // Already endorse ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM endorsers "
                                          + "WHERE endorser='"+this.target_adr+"' "
                                            + "AND endorsed='"+this.endorsed+"' "
                                            + "AND type='"+this.type+"'");
        
        // Has data ?
        if (UTILS.DB.hasData(rs))
            throw new Exception("Endorser already endorses the address, CEndorsePayload.java, 102");
        
        // Already endorsed 10 addressess ?
        rs=UTILS.DB.executeQuery("SELECT COUNT(*) AS total "
                                 + "FROM endorsers "
                                + "WHERE endorser='"+this.target_adr+"'");
        
        // Next
        rs.next();
        
        // More than 10 ?
        if (rs.getLong("total")>=10)
            throw new Exception("Endorser already endorsed maximum addressess, CEndorsePayload.java, 102");
        
        // Load endorser data
        ResultSet endorser_rs=UTILS.DB.executeQuery("SELECT * "
                                                    + "FROM adr "
                                                   + "WHERE adr='"+this.target_adr+"'");
        
        // Next
        endorser_rs.next();
        
        // Load endorsed data
        ResultSet endorsed_rs=UTILS.DB.executeQuery("SELECT * "
                                                    + "FROM adr "
                                                   + "WHERE adr='"+this.endorsed+"'");
        
        // Next
        endorsed_rs.next();
        
        // Same country ?
        if (!endorser_rs.getString("cou").equals(endorsed_rs.getString("cou")))
            throw new Exception("Endorser and endorsed are from different countrues, CEndorsePayload.java, 102");
        
        // Memeber of a political party ?
        if (endorser_rs.getLong("pol_party")==0)
            throw new Exception("Endorser is not member of a political party, CEndorsePayload.java, 102");
        
        // Same party ?
        if (endorser_rs.getLong("pol_party")!=endorsed_rs.getLong("pol_party"))
            throw new Exception("Endorser and endorsed are from different poolitical parties, CEndorsePayload.java, 102");
        
        // Endorser has any political influence ?
        if (endorser_rs.getLong("pol_inf")<25)
            throw new Exception("Not enough political influence, CEndorsePayload.java, 102");
        
        // Hash
 	String h=UTILS.BASIC.hash(this.getHash()+
 		  	          this.endorsed+
                                  this.type);
        
        // Hash match ?
        if (!h.equals(this.hash))
            throw new Exception("Invalid hash, CEndorsePayload.java, 113");
    }
    
    public void commit(CBlockPayload block) throws Exception
    {
        // Superclass
        super.commit(block);
        
        // Already endorse ?
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM endorsers "
                                          + "WHERE endorser='"+this.target_adr+"' "
                                            + "AND endorsed='"+this.endorsed+"' "
                                            + "AND type<>'"+this.type+"'");
       
        // Insert endorser
        if (UTILS.DB.hasData(rs))
            UTILS.DB.executeUpdate("DELETE FROM endorsers "
                                       + "WHERE endorser='"+this.target_adr+"' "
                                         + "AND endorsed='"+this.endorsed+"'");
        else
            UTILS.DB.executeUpdate("INSERT INTO endorsers "
                                         + "SET endorser='"+this.target_adr+"', "
                                             + "endorsed='"+this.endorsed+"', "
                                             + "type='"+this.type+"'");
        
        // Update political endorsement power of endorser
        UTILS.CRONS.refreshPolEndPower(this.target_adr);
        
        // Update political endorsement
        UTILS.CRONS.refreshPolEnd(this.endorsed);
        
        // Position type
        UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
    }
}
