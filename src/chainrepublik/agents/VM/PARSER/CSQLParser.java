package chainrepublik.agents.VM.PARSER;

import chainrepublik.agents.VM.CVM;
import chainrepublik.agents.VM.PARSER.GRAMMAR.SQL.SQLBaseVisitor;
import chainrepublik.agents.VM.PARSER.GRAMMAR.SQL.SQLParser;
import chainrepublik.kernel.UTILS;
import java.sql.ResultSet;
import org.antlr.v4.runtime.misc.Interval;

public class CSQLParser  extends SQLBaseVisitor 
{
    // VM
    CVM VM;
    
    // Var name
    String var;
    
    // Blocks
    long blocks;
    
    public CSQLParser(CVM VM, String rs, long blocks)
    {
        // VM
        this.VM=VM;
        
        // Var
        this.var=rs;
        
        // Blocsk
        this.blocks=blocks;
    }
    
    @Override 
    public String visitSel_stat(SQLParser.Sel_statContext ctx) 
    { 
        try
        {
            // Rewrite query
            String query="SELECT * "
                         + "FROM adata "
                        + "WHERE tab='"+ctx.TAB_NAME()+"' "
                          + "AND aID="+this.VM.comID;
        
            // Where condition ?
            if (ctx.where_logic()!=null)
            {
                String s=ctx.start.getInputStream().toString();
                s=s.substring(query.indexOf(" WHERE ")+5);
                query=query+" AND "+s;
             }

            // Result
            ResultSet rs=UTILS.DB.executeQuery(query);
            
            // Cost
            this.VM.step(UTILS.DB.getRowsNo(rs), 1);
            
            // Set resultset
            this.VM.toRS(var, rs);
        }
        catch (Exception ex)
        {
             this.VM.setErr("Invalid query - "+ctx.start.getInputStream(), 1);
        }
        
        // Return
        return "OK";
    } 
    
    @Override 
    public String visitInsert_stat(SQLParser.Insert_statContext ctx) 
    { 
        // Cost
        if (this.VM.step(ctx.start.getInputStream().toString().length()*0.0001+this.blocks*0.0001, 1))
        {
            try
            {
                // Rewrite query
                String query="INSERT INTO adata "
                               + "SET tab='"+ctx.TAB_NAME()+"', "
                                   + "aID="+this.VM.comID+", "
                                   + "expires="+(this.VM.SYSTEM.BLOCK.BLOCK+this.blocks)+", "
                                  + ctx.set_exp().getText();
        
                // Execute
                UTILS.DB.executeUpdate(query);
            }
            catch (Exception ex)
            {
                this.VM.setErr("Invalid query - "+ctx.start.getInputStream(), 1);
            }
        }
        
        // Return
        return "OK";
    }

    @Override 
    public String visitUpdate_stat(SQLParser.Update_statContext ctx) 
    { 
        try
        {
            // Rewrite query
            String query="UPDATE adata SET "
                                  + ctx.set_exp().getText()
                        +" WHERE aID="+this.VM.comID+" "
                          + "AND tab='"+ctx.TAB_NAME()+"'";
            
            // Where condition ?
            if (ctx.where_logic()!=null)
            {
                String s=ctx.start.getInputStream().toString();
                s=s.substring(query.indexOf(" WHERE ")+5);
                query=query+" AND "+s;
            }
            
            // Execute
            long lines=UTILS.DB.executeUpdate(query);
            
            // Cost
            this.VM.step(lines*0.0001, 1);
        }
        catch (Exception ex)
        {
            this.VM.setErr("Invalid query - "+ctx.start.getInputStream(), 1);
        }
        
        // Return
        return "OK";
    }
    
    @Override 
    public String visitDel_stat(SQLParser.Del_statContext ctx) 
    { 
        try
        {
            // Rewrite query
            String query="DELETE FROM adata "
                             +" WHERE aID="+this.VM.comID+" "
                               + "AND tab='"+ctx.TAB_NAME()+"'";
            
            // Where condition ?
            if (ctx.where_logic()!=null)
            {
                String s=ctx.start.getInputStream().toString();
                s=s.substring(query.indexOf(" WHERE ")+5);
                query=query+" AND "+s;
            }
            
            // Execute
            long lines=UTILS.DB.executeUpdate(query);
            
            // Cost
            this.VM.step(lines*0.0001, 1);
        }
        catch (Exception ex)
        {
             this.VM.setErr("Invalid query - "+ctx.start.getInputStream(), 1);
        }
        
        // Return
        return "OK";
        
    }
}
