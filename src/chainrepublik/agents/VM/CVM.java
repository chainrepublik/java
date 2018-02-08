package chainrepublik.agents.VM;

import chainrepublik.agents.VM.PARSER.CGUI;
import chainrepublik.agents.VM.PARSER.CParser;
import chainrepublik.agents.VM.SYSTEM.CSystem;
import chainrepublik.agents.VM.PARSER.GRAMMAR.LANG.testLexer;
import chainrepublik.agents.VM.PARSER.GRAMMAR.LANG.testParser;
import chainrepublik.agents.VM.PARSER.GRAMMAR.LANG.testVisitor;
import chainrepublik.kernel.UTILS;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;


public class CVM 
{
    // Main context
    CVMContext main_context;
    
    // Actual context
    CVMContext actual_context;
    
    // Contexts
    public ArrayList<CVMContext> contexts=new ArrayList();
    
    // Steps
    public long steps;
    
    // Cost
    public double cost=0;
    
    // Start function
    public String start_func;
    
    // System
    public CSystem SYSTEM;
    
    // Company ID
    public long comID=343242;
    
    // Trigger type
    public String trigger="";
    
    // Trigger hash
    public String trigger_hash="";
    
    // Error
    boolean err;
    
    // Error message
    String err_mes;
    
    // Error line
    long line=0;
    
    // Transaction status
    public boolean trans_status=true;
    
    // Constructor
    public CVM(long comID) throws Exception
    {
        // Main context
        main_context=new CVMContext();
        
        // System
        this.SYSTEM=new CSystem();
        
        // ComID
        this.comID=comID;
    }
    
    public void setErr(String mes, long line)
    {
        if (!err)
        {
           // Set VM 
           this.err=true;
        
           // Message
           this.err_mes=mes;
        
           // Line
           this.line=line;
        }
    }
    
    public boolean execute(String code, String func) throws Exception
    {
        // Exection time in ms
        long total=0;
        
        // Status
        String status="";
        
        try
        {
            // Start function
            this.start_func=func;
            
            // Input stream
            ANTLRInputStream input=new ANTLRInputStream(code);
        
            // Lexer
            testLexer lexer=new testLexer(input);
        
            // Tokens
            CommonTokenStream tokens=new CommonTokenStream(lexer);
        
            // Parser
            testParser parser=new testParser(tokens);
            
            // Parse tree
            ParseTree tree=parser.program();
            
            //if (parser.getNumberOfSyntaxErrors()==0)
            //{
                // Walker
                testVisitor visit=new CParser(this);
        
                // GUI
                CGUI gui=new CGUI();
                gui.show(parser, tree);
            
                // Timestamp
                long start=this.mtstamp();
            
                // Execute
                visit.visit(tree);
            
                // Total
                total=this.mtstamp()-start;
                
                // Stats
                System.out.println("Executed in "+total+" miliseconds, "+this.steps+" steps and "+this.cost+" electricity consumed");
            
                // Return
                return true;
            //}
        }
        catch (Exception ex)
        {
            System.out.println("Error executing program - "+ex.getMessage());
        }
        finally
        {
            // Transaction
            UTILS.ACC.newTrans(this.SYSTEM.COM.ADR,
                               "none", 
                               -this.cost,
                               "ID_ELECTRICITY", 
                               "Electricity cost for running the agent ("+this.steps+" computational steps)", 
                               "", 
                               0,
                               this.trigger_hash, 
                               this.SYSTEM.BLOCK.BLOCK);
              
            // Clear
            UTILS.ACC.clearTrans(this.trigger_hash, "ID_ALL", this.SYSTEM.BLOCK.BLOCK);
            
            // Status
            if (this.err)
                status="ID_SUCCESS";
            else
                status="ID_ERR";
            
            // Insert
            UTILS.DB.executeUpdate("INSERT INTO agents_log "
                                         + "SET symbol='"+this.SYSTEM.COM.SYMBOL+"', "
                                             + "input_block_block='"+this.SYSTEM.BLOCK.BLOCK+"', "
                                             + "input_block_hash='"+this.SYSTEM.BLOCK.HASH+"', "
                                             + "input_block_prev_hash='"+this.SYSTEM.BLOCK.PREV_HASH+"', "
                                             + "input_block_payload_hash='"+this.SYSTEM.BLOCK.PAYLOAD_HASH+"', "
                                             + "input_block_signer='"+this.SYSTEM.BLOCK.SIGNER+"', "
                                             + "input_block_sign='"+this.SYSTEM.BLOCK.SIGN+"', "
                                             + "input_block_nonce='"+this.SYSTEM.BLOCK.NONCE+"', "
                                             + "input_block_tstamp='"+this.SYSTEM.BLOCK.TSTAMP+"', "
                                             + "input_block_net_dif='"+this.SYSTEM.BLOCK.NET_DIF+"', "
                                             + "input_mes_sender='"+this.SYSTEM.MES.SENDER+"', "
                                             + "input_mes_rec='"+this.SYSTEM.MES.REC+"', "
                                             + "input_mes_subj='"+UTILS.BASIC.base64_encode(this.SYSTEM.MES.SUBJ)+"', "
                                             + "input_mes_mes='"+UTILS.BASIC.base64_encode(this.SYSTEM.MES.MES)+"', "
                                             + "input_mes_hash='"+this.SYSTEM.MES.HASH+"', "
                                             + "input_mes_tstamp='"+this.SYSTEM.MES.TSTAMP+"', "
                                             + "input_trans_sender='"+this.SYSTEM.TRANS.SRC+"', "
                                             + "input_trans_dest='"+this.SYSTEM.TRANS.DEST+"', "
                                             + "input_trans_amount='"+this.SYSTEM.TRANS.AMOUNT+"', "
                                             + "input_trans_cur='"+this.SYSTEM.TRANS.CUR+"', "
                                             + "input_trans_mes='"+UTILS.BASIC.base64_encode(this.SYSTEM.TRANS.MES)+"', "
                                             + "input_trans_hash='"+this.SYSTEM.TRANS.TSTAMP+"', "
                                             + "input_trans_tstamp='"+this.SYSTEM.TRANS.TSTAMP+"', "
                                             + "status='"+status+"', "
                                             + "err_mes='"+UTILS.BASIC.base64_encode(this.err_mes)+"', "
                                             + "steps='"+this.steps+"', "
                                             + "time='"+total+"', "
                                             + "cost='"+this.cost+"', "
                                             + "block='"+this.SYSTEM.BLOCK.BLOCK+"'");
        }
        
        return false;
    }
    
    public boolean step(double cost, long line)
    {
        // Error found
        if (this.err)
           return false;
        
        try
        {
           // Steps
           steps++;
            
           // Electricity
           if (this.cost+cost>this.SYSTEM.COM.ELECTRICITY)
           {
               this.setErr("Company has run out of electricity", line);
               return false;
           }
           else this.cost=UTILS.BASIC.round(this.cost+cost, 4);
        }
        catch (Exception ex)
        {
            this.setErr("Unexpected error in VM.step function", line);
            return false;
        }
        
        // Return
        return true;
    }
    
    // New context
    public void pushContext()
    {
        // Creates new context
        CVMContext con=new CVMContext(); 
                
        // Push context
        this.contexts.add(con);
        
        // Set context
        this.actual_context=con;
    }
    
    // New context
    public void popContext()
    {
        // Remove
        this.contexts.remove(this.contexts.size()-1);
        
        // Push context
        this.actual_context=this.contexts.get(this.contexts.size()-1);
    }
    
    // Variable exist ?
    public boolean varExist(String name) throws Exception
    {
        for (int a=0; a<=this.actual_context.VARS.size()-1; a++)
        {
            // Get variable
            CVar var=this.actual_context.VARS.get(a);
            
            // Match ?
            if (var.name.equals(name))
                return true;
        }
        
        // Not found
        return false;
    }
    
    // Variable exist ?
    public boolean globalVarExist(String name) throws Exception
    {
        for (int a=0; a<=this.main_context.VARS.size()-1; a++)
        {
            // Get variable
            CVar var=this.main_context.VARS.get(a);
            
            // Match ?
            if (var.name.equals(name))
                return true;
        }
        
        // Not found
        return false;
    }
    
    public void createVar(String name, String val) throws Exception
    {
        // Create
        CVar var=new CVar(name, val);
        
        // Add
        this.actual_context.VARS.add(var);
    }
    
    public void createGlobalVar(String name, String val) throws Exception
    {
        // Create
        CVar var=new CVar(name, val);
        
        // Add
        this.main_context.VARS.add(var);
    }
    
    // Get variable
    public CVar getVar(String name) throws Exception
    {
        // Variable exist ?
        if (!this.varExist(name))
            throw new Exception("Variable "+name+" was not initialized");
        
        for (int a=0; a<=this.actual_context.VARS.size()-1; a++)
        {
            // Get variable
            CVar var=this.actual_context.VARS.get(a);
            
            // Match ?
            if (var.name.equals(name))
                return var;
        }
        
        // Exeption
        return null;
    }
    
    // Get variable
    public CVar getGlobalVar(String name) throws Exception
    {
        for (int a=0; a<=this.main_context.VARS.size()-1; a++)
        {
            // Get variable
            CVar var=this.main_context.VARS.get(a);
            
            // Match ?
            if (var.name.equals(name))
               return var;
        }
        
        // Exeption
        return null;
    }
    
    // Set variable
    public void setVar(String name, String val, int pos) throws Exception
    {
        // Variable exist ?
        if (!this.varExist(name))
            this.createVar(name, val);
        
        for (int a=0; a<=this.actual_context.VARS.size()-1; a++)
        {
            // Get variable
            CVar var=this.actual_context.VARS.get(a);
            
            // Match ?
            if (var.name.equals(name))
                var.set(val, pos);
        }
    }
    
    // Set variable
    public void toArray(String name, String[] array) throws Exception
    {
        // Variable exist ?
        if (!this.varExist(name))
            this.createVar(name, "");
        
        for (int a=0; a<=this.actual_context.VARS.size()-1; a++)
        {
            // Get variable
            CVar var=this.actual_context.VARS.get(a);
            
            // Match ?
            if (var.name.equals(name))
            {
                // Set array
                var.array=array;
                
                // Set type
                var.type="ID_ARRAY";
                
                // Set string val
                var.s_val="";
            }
        }
    }
    
    // Set variable
    public void toRS(String name, ResultSet rs) throws Exception
    {
        // Variable exist ?
        if (!this.varExist(name))
            this.createVar(name, "");
        
        for (int a=0; a<=this.actual_context.VARS.size()-1; a++)
        {
            // Get variable
            CVar var=this.actual_context.VARS.get(a);
            
            // Match ?
            if (var.name.equals(name))
            {
                // Set array
                var.rs=rs;
                
                // Set type
                var.type="ID_RS";
                
                // Set string val
                var.s_val="";
                
                // Set array to null
                var.array=null;
            }
        }
    }
    
    // Set variable
    public void setGlobalVar(String name, String val, int pos) throws Exception
    {
        // Variable exist ?
        if (!this.globalVarExist(name))
            this.createGlobalVar(name, val);
        
        for (int a=0; a<=this.main_context.VARS.size()-1; a++)
        {
            // Get variable
            CVar var=this.main_context.VARS.get(a);
            
            // Match ?
            if (var.name.equals(name))
                var.set(val, pos);
        }
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
    
    // Domain name valid ?
        public boolean isNumber(String domain) throws Exception
        {
	    if (!domain.matches("^-?\\d+(\\.\\d{0,8})?$"))
	       return false;
	    else 
	       return true;
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
    
}
