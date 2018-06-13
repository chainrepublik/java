package chainrepublik.network.packets.politics.orgs;

import chainrepublik.kernel.UTILS;
import chainrepublik.network.packets.CPayload;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;

public class CVoteOrgPropPayload extends CPayload
{
    // Prop ID
    long propID;
    
    // Law type
    String vote;
    
    public CVoteOrgPropPayload(String adr, 
                              long propID,
                              String vote) throws Exception
    {
        // Superclass
	super(adr);
        
        // Org ID
        this.propID=propID;
        
        // Vote
        this.vote=vote;
        
        // Hash
 	this.hash=UTILS.BASIC.hash(this.getHash()+
                                   this.propID+
                                   this.vote);
        
        // Sign
        this.sign();
    }
    
   
    public void check(CBlockPayload block) throws Exception
    {
   	// Super class
   	super.check(block);
        
        // Check energy
        this.checkEnergy();
        
        // Valid propID
        ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                           + "FROM orgs_props "
                                          + "WHERE propID='"+this.propID+"' "
                                            + "AND status='ID_VOTING'");
        
        // Has data ?
        if (!UTILS.DB.hasData(rs))
            throw new Exception("Invalid proposal ID, CNewOrgPropPayload.java, 116");
        
        // Next
        rs.next();
        
        // Same org ?
        if (Long.parseLong(UTILS.BASIC.getAdrData(rs.getString("adr"), "pol_party"))!=rs.getLong("orgID") &&
            Long.parseLong(UTILS.BASIC.getAdrData(rs.getString("adr"), "mil_unit"))!=rs.getLong("orgID"))
        throw new Exception("Invalid address organization ID, CNewOrgPropPayload.java, 116");
        
        // Already voted ?
        rs=UTILS.DB.executeQuery("SELECT * "
                                 + "FROM orgs_props_votes "
                                + "WHERE adr='"+this.target_adr+"' "
                                  + "AND propID='"+this.propID+"'");
        
        // Has data ?
        if (UTILS.DB.hasData(rs))
            throw new Exception("You already voted this proposal, CNewOrgPropPayload.java, 116");
        
        // Hash
 	String h=UTILS.BASIC.hash(this.getHash()+
                                  this.propID+
                                  this.vote);
        
        // Hash match ?
        if (!this.hash.equals(h))
            throw new Exception("Invalid hash, CVoteOrgPropPayload.java, 116");
        
    }
    
    public void commit(CBlockPayload block) throws Exception
    {
        // Superclass
        super.commit(block);

        // Vote power
        double power=Double.parseDouble(UTILS.BASIC.getAdrData(this.target_adr, "pol_inf"));
        
        // Insert proposal
        UTILS.DB.executeUpdate("INSERT INTO orgs_props_votes "
                                     + "SET adr='"+this.target_adr+"', "
                                         + "propID='"+this.propID+"', "
                                         + "vote_type='"+this.vote+"', "
                                         + "power='"+power+"', "
                                         + "block='"+this.block+"'");
        
        // Yes
        if (this.vote.equals("ID_YES"))
            UTILS.DB.executeUpdate("UPDATE orgs_props "
                                    + "SET yes=yes+"+power+" "
                                  + "WHERE propID='"+this.propID+"'");
        else
            UTILS.DB.executeUpdate("UPDATE orgs_props "
                                    + "SET no=no+"+power+" "
                                  + "WHERE propID='"+this.propID+"'");
        
        // Position type
        UTILS.ACC.clearTrans(hash, "ID_ALL", this.block);
    }
    
}
