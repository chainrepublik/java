package chainrepublik.agents.VM.PARSER;

import chainrepublik.agents.VM.PARSER.GRAMMAR.LANG.testParser;
import chainrepublik.agents.VM.PARSER.GRAMMAR.LANG.testBaseVisitor;
import chainrepublik.agents.VM.PARSER.CGUI;
import chainrepublik.agents.VM.PARSER.GRAMMAR.SQL.SQLParser;
import chainrepublik.agents.VM.PARSER.GRAMMAR.SQL.SQLVisitor;
import chainrepublik.agents.VM.PARSER.GRAMMAR.SQL.SQLLexer;
import chainrepublik.agents.VM.CVM;
import chainrepublik.agents.VM.CVar;
import chainrepublik.agents.VM.PARSER.GRAMMAR.LANG.testParser.ProgramContext;
import chainrepublik.agents.VM.PARSER.PARSE.CParseTrade;
import chainrepublik.kernel.UTILS;
import chainrepublik.kernel.x34.*;
import chainrepublik.network.packets.blocks.CBlockPayload;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;

public class CParser extends testBaseVisitor 
{
    // Program context
    ProgramContext main;
    
    // VM
    CVM VM;
    
    
    
    public CParser(CVM VM)
    {
        this.VM=VM;
    }
    
    
    @Override 
    public Double visitNumber(testParser.NumberContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        return new Double(ctx.getText());
    }
    
    @Override public Void visitProgram(ProgramContext ctx)
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        // Record tree root
        this.main=ctx;
        
        // Visit globals
        if (ctx.globals()!=null)
           visit(ctx.globals());
        
        // Push context
        this.VM.pushContext();
        
        // Visit init
        if (ctx.functions().function_init()!=null)
           visit(ctx.functions().function_init());
        
        // Initial function
        if (!this.VM.start_func.equals(""))
        {
            // Selects function
            switch (this.VM.start_func)
            {
                // On transaction
                case "ID_ON_TRANS" : visit(ctx.functions().function_onTrans()); 
                                     break;
                
                // On message
                case "ID_ON_MES" : visit(ctx.functions().function_onMes()); 
                                   break;
                
                // On block
                case "ID_ON_BLOCK" : visit(ctx.functions().function_onBlock()); 
                                     break;
            }
        }
        
        // Return
        return null; 
    }
    
    @Override 
    public Void visitAssign_expr (testParser.Assign_exprContext ctx)
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        // Left
        String left;
        
        // Right
        String right;
        
        // Var name
        String var_name;
        
        // Var pos
        int var_pos=0;
        
        // Left double
        double d_left;
        
        // Right double
        double d_right;
        
        try
        {
           // Evaluate left 
           if (ctx.var().var_array()!=null)
           {
               // Var name
               var_name=ctx.var().var_array().VAR().getText();
               
               // Position
               if (ctx.var().var_array().expr_math()!=null)
                  var_pos=Math.round(Float.parseFloat(String.valueOf(visit(ctx.var().var_array().expr_math()))));
           }
           else
           {
               // Var name
               var_name=ctx.var().VAR().getText();
           }
            
           // Evaluate right
           right=String.valueOf(visit(ctx.expr_math()).toString());
        
           // Set
           switch (ctx.ASSIGN_OP().getText())
           {
               // Equal
               case "=" :  this.VM.setVar(var_name, right, var_pos);
                           break;
                           
               // Plus equal
               case "+=" :  // Evaluate right
                            left=visit(ctx.var()).toString();
                            
                            // Both numbers
                            if (this.VM.isNumber(left) && this.VM.isNumber(right))
                            {
                                // Left
                                d_left=Double.valueOf(left);
                                
                                // Right
                                d_right=Double.valueOf(right);
                                
                                // Sum
                                double sum=d_left+d_right;
                                
                                // Assign
                                this.VM.setVar(var_name, String.valueOf(sum), var_pos);
                            }
                            else this.VM.setVar(var_name, left+right, var_pos);
                            
                            break;
                            
               // Minus equal
               case "-=" :  // Evaluate right
                            left=visit(ctx.var()).toString();
                            
                            // Left
                            d_left=Double.valueOf(left);
                                
                            // Right
                            d_right=Double.valueOf(right);
                                
                            // Sum
                            double sum=d_left-d_right;
                                
                            // Assign
                            this.VM.setVar(var_name, String.valueOf(sum), var_pos);
                            
                            break;
                            
               // Mul equal
               case "*=" :  // Evaluate right
                            left=visit(ctx.var()).toString();
                            
                            // Left
                            d_left=Double.valueOf(left);
                                
                            // Right
                            d_right=Double.valueOf(right);
                                
                            // Sum
                            sum=d_left*d_right;
                                
                            // Assign
                            this.VM.setVar(var_name, String.valueOf(sum), var_pos);
                            
                            break;
                            
                // Div equal
               case "/=" :  // Evaluate right
                            left=visit(ctx.var()).toString();
                            
                            // Left
                            d_left=Double.valueOf(left);
                                
                            // Right
                            d_right=Double.valueOf(right);
                                
                            // Sum
                            sum=d_left/d_right;
                                
                            // Assign
                            this.VM.setVar(var_name, String.valueOf(sum), var_pos);
                            
                            break;
           }
           
           
        }
        catch (Exception ex)
        {
            System.out.println("Error in assign expression - "+ex.getMessage());
        }
        
        return null;
    }
    
    @Override 
    public Void visitAssign_inc_dec(testParser.Assign_inc_decContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        // Left
        String left="";
        
        // var name
        String var_name="";
        
        // Position
        int var_pos=0;
        
        try
        {
            // Evaluate left 
           if (ctx.var().var_array()!=null)
           {
               // Var name
               var_name=ctx.var().var_array().VAR().getText();
               
               // Position
               var_pos=Integer.parseInt(String.valueOf(visit(ctx.var().var_array().expr_math())));
               
               // Evaluate left
               left=String.valueOf(visit(ctx.var().var_array()));
           }
           else
           {
               // Var name
               var_name=ctx.var().VAR().getText();
               
               // Evaluate left
               left=String.valueOf(visit(ctx.var()));
           }
           
            // To double
            double d_left=Double.valueOf(left);
        
            // Inc
            if (ctx.INCDEC_OP().getText().equals("++"))
              d_left++;
            else
              d_left--;
            
            // Assign
            this.VM.setVar(var_name, String.valueOf(d_left), var_pos);
        }
        catch (Exception ex)
        {
            System.out.println("Error in INC_DEC_OP statement - "+ex.getMessage());
        }
        
        // Return
        return null;
    }
    
   
    @Override 
    public Double visitExpr_paran (testParser.Expr_paranContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        double expr_math=Double.valueOf(visit(ctx.expr_math()).toString());
        return (expr_math);
    }
    
     @Override 
    public String visitExpr_string (testParser.Expr_stringContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        return (visit(ctx.string()).toString());
    }
    
    @Override 
    public String visitExpr_var (testParser.Expr_varContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        return String.valueOf(visit(ctx.var()));
    }
    
    @Override 
    public Void visitF_print(testParser.F_printContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        System.out.println(visit(ctx.expr_math()));
        return null;
    }
    
    @Override 
    public String visitVar(testParser.VarContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        String res="";
        
        try
        {
           if (ctx.global_var()!=null)
           {
               // Var name
               String var_name=ctx.global_var().var().VAR().getText();
               
               // result
               res=String.valueOf(this.VM.getGlobalVar(var_name).val(0));
           }
           else if (ctx.var_array()!=null)
           {
               // Var name
               String var_name=ctx.var_array().VAR().getText();
               
               // Position
               int var_pos=Math.round(Float.parseFloat(String.valueOf(visit(ctx.var_array().expr_math()))));
               
               // Res
               res=this.VM.getVar(var_name).array[var_pos];
           }
           else if (ctx.VAR()!=null)
           {
               res=this.VM.getVar(ctx.VAR().getText()).s_val;
           }
           
           return res;
        }
        catch (Exception ex)
        {
           System.out.println(ex.getMessage());
        }
        
        return "0"; 
    }
    
    @Override 
    public String visitString(testParser.StringContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        return ctx.CHAR_SEQ().getText().replace("\"", "");
    }
    
    @Override 
    public String visitExpr_op_expr(testParser.Expr_op_exprContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        try
        {
            // Evaluate left
            String left=String.valueOf(visit(ctx.expr_math(0)).toString());
        
            // Evaluate right
            String right=String.valueOf(visit(ctx.expr_math(1)).toString());
            
            // Result
            String res="";
            
            // Operator
            switch (ctx.MATH_OP().getText())
            {
                // Add
                case "+" : if (this.VM.isNumber(left) && this.VM.isNumber(right)) 
                              res=String.valueOf(Double.valueOf(left)+Double.valueOf(right));
                           
                           else 
                              res=(left+right);
                          
                           break;
                           
                // Substract
                case "-" : res=String.valueOf(Double.valueOf(left)-Double.valueOf(right));
                           break;
                           
                // Multiply
                case "*" : res=String.valueOf(Double.valueOf(left)*Double.valueOf(right));
                           break;
                           
                // Division
                case "/" : res=String.valueOf(Double.valueOf(left)/Double.valueOf(right));
                           break;
                           
                // Division
                case "%" : res=String.valueOf(Double.valueOf(left)%Double.valueOf(right));
                           break;
            }
            
            return res;
        }
        catch (Exception ex)
        {
            
        }
        
        return "";
    }
    
    @Override 
    public Void visitStat_if(testParser.Stat_ifContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        // Comp eval
        boolean comp=Boolean.valueOf(String.valueOf(visit(ctx.expr_comp()).toString()));
        
        // True 
        if (comp)
        {
            // Lines
            if (ctx.line(0)!=null)
                visit(ctx.line(0));
            else 
                visit(ctx.lines(0));
        }
            else
        {
            // Lines
            if (ctx.line(1)!=null)
                visit(ctx.line(1));
            
            else if (ctx.lines(1)!=null)
                visit(ctx.lines(1));
        }
                
        return null; 
    }
    
    @Override 
    public String visitExpr_comp_logic(testParser.Expr_comp_logicContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        // Evaluate left
        String left=String.valueOf(visit(ctx.expr_comp(0)).toString());
        
        // Evaluate right
        String right=String.valueOf(visit(ctx.expr_comp(1)).toString());
        
        // Result
        String res="";
        
        switch (ctx.LOGIC_OP().getText())
        {
            case "&&" : if (left.equals("true") && right.equals("true")) 
                           res="true";
                        else
                           res="false";
            
                        break;
                        
            case "||" : if (left.equals("true") || right.equals("true")) 
                           res="true";
                        else
                           res="false";
            
                        break;
        }  
        
        return res;
    }
    
    @Override 
    public String visitExpr_comp_op(testParser.Expr_comp_opContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        // Double left
        double d_left;
        
        // Double right
        double d_right;
        
        // String res
        String res="true";
        
        try
        {
        
        // Evaluate left
        String left=String.valueOf(visit(ctx.expr_math(0)).toString());
        
        // Evaluate right
        String right=String.valueOf(visit(ctx.expr_math(1)).toString());
        
        // Switch
        switch (ctx.COMP_OP().getText())
        {
            // Smaller
            case "<" :  // Double left
                        d_left=Double.valueOf(left);
                
                        // Double right
                        d_right=Double.valueOf(right);
                        
                        // Smaller
                        if (d_left<d_right) 
                            res="true";
                        else
                            res="false";
                        
                        break;
            
            // Smaller or equal
            case "<=" : // Double left
                        d_left=Double.valueOf(left);
                
                        // Double right
                        d_right=Double.valueOf(right);
                        
                        // Smaller
                        if (d_left<=d_right) 
                            res="true";
                        else
                            res="false";
                        
                        break;
            
            // Bigger
            case ">" : // Double left
                        d_left=Double.valueOf(left);
                
                        // Double right
                        d_right=Double.valueOf(right);
                        
                        // Smaller
                        if (d_left>d_right) 
                            res="true";
                        else
                            res="false";
                        
                        break;
            
            // Bigger or equal
            case ">=" : // Double left
                        d_left=Double.valueOf(left);
                
                        // Double right
                        d_right=Double.valueOf(right);
                        
                        // Smaller
                        if (d_left>=d_right) 
                            res="true";
                        else
                            res="false";
                        
                        break;
            
            // Equal
            case "==" : if (this.VM.isNumber(left) && 
                            this.VM.isNumber(right))
                        {
                            // Double left
                            d_left=Double.valueOf(left);
                
                            // Double right
                            d_right=Double.valueOf(right);
                        
                            // Smaller
                            if (d_left==d_right) 
                                res="true";
                            else
                                res="false";
                        }
                        else
                        {
                            // Smaller
                            if (left.equals(right)) 
                                res="true";
                            else
                                res="false";
                        }
                        
                        break;
                        
            case "===" : if ((this.VM.isNumber(left) && !this.VM.isNumber(right)) || 
                             (!this.VM.isNumber(left) && this.VM.isNumber(right)))
                         {
                            res="false";
                         }
                         else
                        {
                            if (this.VM.isNumber(left))
                            {
                               // Double left
                               d_left=Double.valueOf(left);
                
                               // Double right
                               d_right=Double.valueOf(right);
                            
                               // Smaller
                               if (d_left==d_right) 
                                   res="true";
                               else
                                   res="false";
                            }
                            else 
                            {
                                // Smaller
                                if (left.equals(right)) 
                                    res="true";
                                else
                                    res="false";
                            }
                        }
            
                        break;
                        
            case "!=" : if (this.VM.isNumber(left) && 
                            this.VM.isNumber(right))
                        {
                            // Double left
                            d_left=Double.valueOf(left);
                
                            // Double right
                            d_right=Double.valueOf(right);
                        
                            // Smaller
                            if (d_left!=d_right) 
                                res="true";
                            else
                                res="false";
                        }
                        else
                        {
                            // Smaller
                            if (!left.equals(right)) 
                                res="true";
                            else
                                res="false";
                        }
                        
                        break;
                        
            case "!==" : if ((this.VM.isNumber(left) && !this.VM.isNumber(right)) || 
                             (!this.VM.isNumber(left) && this.VM.isNumber(right)))
                         {
                            res="false";
                         }
                         else
                        {
                            if (this.VM.isNumber(left))
                            {
                               // Double left
                               d_left=Double.valueOf(left);
                
                               // Double right
                               d_right=Double.valueOf(right);
                            
                               // Smaller
                               if (d_left!=d_right) 
                                   res="true";
                               else
                                   res="false";
                            }
                            else 
                            {
                                // Smaller
                                if (!left.equals(right)) 
                                    res="true";
                                else
                                    res="false";
                            }
                        }
            
                        break;
        }
        }
        catch (Exception ex)
        {
            System.out.println("Error in comparision expresion");
        }
        
        return res; 
    }
    
    @Override 
    public String visitExpr_comp_paran(testParser.Expr_comp_paranContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        return String.valueOf(visit(ctx.expr_comp())); 
    }
    
    @Override public String visitBool(testParser.BoolContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        if (ctx.TRUE()!=null)
            return "true";
        else
            return "false";
    }
    
    @Override public String visitExpr_comp_bool(testParser.Expr_comp_boolContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        return String.valueOf(visit(ctx.var())); 
    }
    
    @Override public String visitExpr_bool(testParser.Expr_boolContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        String val=String.valueOf(visit(ctx.bool()));
        return val; 
    }
    
    @Override public Void visitStat_for(testParser.Stat_forContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        try
        {
            // Assign
            visit(ctx.assign());
        
            // Loop
            while (Boolean.valueOf(String.valueOf(visit(ctx.expr_comp()))))
            {
                // Visit lines
                if (ctx.line()!=null)
                    visit(ctx.line());
                else
                    visit(ctx.lines());
            
                // Evaluate var
                double var=Double.valueOf(String.valueOf(visit(ctx.var())));
            
                // Increment decrement
                if (ctx.INCDEC_OP().getText().equals("++"))
                    var++;
                else
                   var--;
            
                // Assign
                if (Boolean.valueOf(String.valueOf(visit(ctx.expr_comp()))))
                   this.VM.setVar(ctx.var().getText(), String.valueOf(var), 0);
            }
        }
        catch (Exception ex)
        {
            System.out.println("Error in for loop cycle - "+ex.getMessage());
        }
        
        return null;
    }
    
    @Override public Void visitStat_while(testParser.Stat_whileContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        while (Boolean.valueOf(String.valueOf(visit(ctx.expr_comp()))))
        {
            if (ctx.line()!=null)
                visit(ctx.line());
            else
                visit(ctx.lines());
        }
        
        return null; 
    }
    
    @Override public Void visitStat_switch(testParser.Stat_switchContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        try
        {
        // Eval switch var
        String var=String.valueOf(visit(ctx.var()));
        
        for (int a=0; a<=ctx.misc_var().size()-1; a++)
        {
            // String not null
            String sw_comp=String.valueOf(visit(ctx.misc_var(a)));
            
            // Compare
            if (this.VM.isNumber(var) && 
                this.VM.isNumber(sw_comp))
            {
                double d_var=Double.valueOf(var);
                double d_sw_comp=Double.valueOf(sw_comp);
                
                if (d_var==d_sw_comp)
                    visit(ctx.lines(a));
            }
            
            else if (var.equals(sw_comp))
                visit(ctx.lines(a));
        }
        }
        catch (Exception ex)
        {
            System.out.println("Error in switch statement");
        }
            
        return null;
    }
    
    @Override 
    public String visitMisc_var(testParser.Misc_varContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        if (ctx.number()!=null)
            return String.valueOf(visit(ctx.number()));
        
        else if (ctx.string()!=null)
            return String.valueOf(visit(ctx.string()));
        
        else if (ctx.var()!=null)
            return String.valueOf(visit(ctx.var()));
        
        else if (ctx.func_call()!=null)
            return String.valueOf(visit(ctx.func_call()));
        
        else return "";
    }
    
    @Override public String visitFunction_user(testParser.Function_userContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        return ""; 
    }
    
    @Override 
    public Void visitF_query(testParser.F_queryContext ctx) 
    { 
        try
        {
            // Param 1
            String query=String.valueOf(visit(ctx.expr_math(0)));
            
            // Param 2
            String var=String.valueOf(ctx.var().getText());
            
            // Param 1
            long blocks=Long.parseLong(String.valueOf(visit(ctx.expr_math(1))));
            
            // Input stream
            ANTLRInputStream input=new ANTLRInputStream(query);
        
            // Lexer
            SQLLexer lexer=new SQLLexer(input);
        
            // Tokens
            CommonTokenStream tokens=new CommonTokenStream(lexer);
        
            // Parser
            SQLParser parser=new SQLParser(tokens);
        
            // Parse tree
            ParseTree SQLtree=parser.statement();
        
            // GUI
            CGUI gui=new CGUI();
            gui.show(parser, SQLtree);
            
            // Walker
            SQLVisitor visit=new CSQLParser(this.VM, var, blocks);
        
            // Execute
            visit.visit(SQLtree);
            
            // Return
            return null;
        }
        catch (Exception ex)
        {
            System.out.println("Error in query statement");
        }
        
        return null;
    }
    
    @Override 
    public String visitF_user(testParser.F_userContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        try
        {
        
        // Result
        String res="";
        
        
       
        // Function name
        for (int a=0; a<=this.main.functions().function_user().size()-1; a++)
        {
            // Function ref
            testParser.Function_userContext func=this.main.functions().function_user(a);
            
            // Function name
            String f_name=this.main.functions().function_user(a).VAR().getText();
            
            // Name match ?
            if (f_name.equals(ctx.VAR().getText()))
            {
                // Vars
                ArrayList<CVar> vars=new ArrayList();
                
                // Has parameters ?
                if (func.func_params()!=null)
                {
                   // Function definition parameters
                   long func_def_params_no=func.func_params().VAR().size();
                
                   // Function call parameters
                   long func_call_params_no=ctx.params().expr_math().size();
                        
                   // Parameters match ?
                   if (func_def_params_no!=func_call_params_no)
                   {
                      System.out.println("Parameters number mismatch");
                      return "";
                   }
                   
                   // Create variables
                   for (int b=0; b<=func.func_params().VAR().size()-1; b++)
                   {
                       // Param name
                       String name=String.valueOf(func.func_params().VAR(b).getText());
                    
                       // Param value
                       String val=String.valueOf(visit(ctx.params().expr_math(b)));
                    
                       // New var
                       CVar var=new CVar(name, val);
                       
                       // Add var
                       vars.add(var);
                   }
                   
                   // Create new VM context
                   this.VM.pushContext();
                   
                    // Assign vars
                    for (int c=0; c<=vars.size()-1; c++)
                    {
                       // Retrive var
                       CVar var=vars.get(c);
                       
                       // Value
                       String val=var.val(0);
                       
                       // Set var in new context
                       this.VM.setVar(var.name, val, 0);
                    }
                    
                   // Visit lines
                   visit(func.lines());
                
                   // Visit return statement
                   if (func.ret()!=null)
                   {
                       // Value
                       String val=String.valueOf(visit(func.ret()));
                       
                       // Result
                       res= String.valueOf(val);
                   }
                
                    // Pop VM context
                    this.VM.popContext();
                   
                    // Return 
                    return res;
                }
            }
        }
        }
        
        catch (Exception ex)
        {
            System.out.println("Error in function call");
            return "";
        }
        
        System.out.println("Function "+ctx.VAR().getText()+" not found");
        
        return ""; 
    }
    
    @Override 
    public String visitRet(testParser.RetContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        return String.valueOf(visit(ctx.expr_math())); 
    }
    
    @Override public String visitExpr_func(testParser.Expr_funcContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        return String.valueOf(visit(ctx.func_call())); 
    }
    
    @Override public String visitGlobal_assign(testParser.Global_assignContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        try
        {
           // Var name
           String var_name=ctx.var().getText();
        
           // Var value
           String var_value=String.valueOf(visit(ctx.var_value()));
        
           // Set variable
           this.VM.setGlobalVar(var_name, var_value, 0);
        }
        catch (Exception ex)
        {
            System.out.println("Error in global var eval");
        }
        
        return null; 
    } 
    
    @Override public String visitGlobal_var(testParser.Global_varContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        String res="";
        
        try
        {
           // Variable name
           String var_name=ctx.var().getText();
           
           // Result
           res=String.valueOf(this.VM.getGlobalVar(var_name)); 
        }
        catch (Exception ex)
        {
            System.out.println("Error in global var assign - "+ex.getMessage());
        }
        
        // Return
        return res;
    }
    
    @Override 
    public String visitVar_function_var(testParser.Var_function_varContext ctx) 
    { 
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        // Result
        String res="";
        
        // Variable
        String left=String.valueOf(visit(ctx.var()));
        
        // Variable name
        String var_name=ctx.var().VAR().getText();
        
        try
        {
            
        
        // Select function
        if (ctx.STR_FUNC()!=null)
        {
            switch (ctx.STR_FUNC().getText())
            {
                // Lentgh
                case "length" : res=String.valueOf(left.length()); 
                                break;
                                
                // Index of
                case "indexOf" : if (ctx.params().expr_math().size()>2 || 
                                     ctx.params().expr_math().size()==0)
                                 {
                                    System.out.println("Invalid parameter count for function indexOf");
                                 }
                                 else
                                 {
                                    // Eval parameter 1
                                    String param_1=String.valueOf(visit(ctx.params().expr_math(0)));
                                    
                                    // Eval param 2
                                    if (ctx.params().expr_math(1)==null)
                                    {
                                       // Result
                                       res=String.valueOf(left.indexOf(param_1));
                                    }
                                    else
                                    {
                                       // Start from
                                       int param_2=Math.round(Float.parseFloat(String.valueOf(visit(ctx.params().expr_math(1)))));
                                        
                                       // Result
                                       res=String.valueOf(left.indexOf(param_1, param_2));
                                    }
                                 }
                
                                break;
                                
                // Last index of
                case "lastIndexOf" : if (ctx.params().expr_math().size()>2 || 
                                         ctx.params().expr_math().size()==0)
                                     {
                                        System.out.println("Invalid parameter count for function lastIndexOf");
                                     }
                                     else
                                     {
                                        // Eval parameter 1
                                        String param_1=String.valueOf(visit(ctx.params().expr_math(0)));
                                    
                                        // Eval param 2
                                        if (ctx.params().expr_math(1)==null)
                                        {
                                           // Result
                                           res=String.valueOf(left.lastIndexOf(param_1));
                                        }
                                        else
                                        {
                                           // Start from
                                           int param_2=Math.round(Float.parseFloat(String.valueOf(visit(ctx.params().expr_math(1)))));
                                        
                                           // Result
                                           res=String.valueOf(left.lastIndexOf(param_1, param_2));
                                        }
                                     }
                
                                break;
                                
                // Substring
                case "substring" : if (ctx.params().expr_math().size()>2 || 
                                         ctx.params().expr_math().size()==0)
                                     {
                                        System.out.println("Invalid parameter count for function substring");
                                     }
                                     else
                                     {
                                        // Eval parameter 1
                                        int param_1=Math.round(Float.parseFloat(String.valueOf(visit(ctx.params().expr_math(0)))));
                                    
                                        // Eval param 2
                                        if (ctx.params().expr_math(1)==null)
                                        {
                                           // Result
                                           res=String.valueOf(left.substring(param_1));
                                        }
                                        else
                                        {
                                           // Start from
                                           int param_2=Math.round(Float.parseFloat(String.valueOf(visit(ctx.params().expr_math(1)))));
                                        
                                           // Result
                                           res=String.valueOf(left.substring(param_1, param_2));
                                        }
                                     }
                
                                break;
                                
                // Replace
                case "replace" : if (ctx.params().expr_math().size()!=2)
                                     {
                                        System.out.println("Invalid parameter count for function replace");
                                     }
                                     else
                                     {
                                        // Eval parameter 1
                                        String param_1=String.valueOf(visit(ctx.params().expr_math(0)));
                                    
                                        // Eval parameter 2
                                        String param_2=String.valueOf(visit(ctx.params().expr_math(1)));
                                        
                                        // Result
                                        res=String.valueOf(left.replace(param_1, param_2));
                                     }
                                     
                                break;
                                
                // Replace
                case "toUpperCase" :    // Result
                                        res=left.toUpperCase();
                                     
                                        break;
                                
                // Replace
                case "toLowerCase" :    // Result
                                        res=left.toLowerCase(); 
                                     
                                        break;
                                
                // Replace
                case "concat" :      if (ctx.params().expr_math().size()!=1)
                                     {
                                        System.out.println("Invalid parameter count for function concat");
                                     }
                                     else
                                     {
                                        // Eval parameter 1
                                        String param_1=String.valueOf(visit(ctx.params().expr_math(0)));
                                    
                                        // Result
                                        res=left.concat(param_1);
                                     }
                                     
                                break;
                                
                // Replace
                case "charAt" :      if (ctx.params().expr_math().size()!=1)
                                     {
                                        System.out.println("Invalid parameter count for function charAt");
                                     }
                                     else
                                     {
                                        // Eval parameter 1
                                        int param_1=Math.round(Float.parseFloat(String.valueOf(visit(ctx.params().expr_math(0)))));
                                    
                                        // Result
                                        res=String.valueOf(left.charAt(param_1));
                                     }
                                     
                                break;
                                
                // Replace
                case "charCodeAt" :  if (ctx.params().expr_math().size()!=1)
                                     {
                                        System.out.println("Invalid parameter count for function charAt");
                                     }
                                     else
                                     {
                                        // Eval parameter 1
                                        int param_1=Math.round(Float.parseFloat(String.valueOf(visit(ctx.params().expr_math(0)))));
                                    
                                        // Result
                                        res=String.valueOf((int)left.charAt(param_1));
                                     }
                                     
                                break;
                                
                // Replace
                case "split" :  if (ctx.params().expr_math().size()!=1)
                                {
                                        System.out.println("Invalid parameter count for function charAt");
                                }
                                else
                                {
                                    // Eval parameter 1
                                    String param_1=String.valueOf(visit(ctx.params().expr_math(0)));
                                    
                                    // Result
                                    String[] str=left.split(param_1);
                                    
                                    // Return 
                                    this.VM.toArray(var_name, str);
                                    
                                    // Return
                                    res=String.valueOf(str.length);
                                }
                                     
                                break;
            }
        }
        }
        catch (Exception ex)
        {
            System.out.println("Error in var func expression");
        }
        
        return res; 
    }
    
    @Override 
    public String visitVar_function_math(testParser.Var_function_mathContext ctx) 
    { 
        String res="";
        double param_1=0;
        double param_2=0;
        double param_3=0;
        
        try
        {
           // Step
           this.VM.step(0.0001, ctx.start.getLine());
        
           // Params check
           if (ctx.params()!=null)
           {
               // Param 1
               if (ctx.params().expr_math(0)!=null)
               {
                   if (!this.VM.isNumber(String.valueOf(visit(ctx.params().expr_math(0)))))
                       System.out.println("Invalid parameter 1");
                   else
                       param_1=Double.parseDouble(String.valueOf(visit(ctx.params().expr_math(0))));
               }
               
               // Param 2
               if (ctx.params().expr_math(1)!=null)
               {
                   if (!this.VM.isNumber(String.valueOf(visit(ctx.params().expr_math(1)))))
                       System.out.println("Invalid parameter 2");
                   else
                       param_2=Double.parseDouble(String.valueOf(visit(ctx.params().expr_math(1))));
               }
               
               // Param 3
               if (ctx.params().expr_math(2)!=null)
               {
                   if (!this.VM.isNumber(String.valueOf(visit(ctx.params().expr_math(2)))))
                       System.out.println("Invalid parameter 3");
                   else
                       param_3=Double.parseDouble(String.valueOf(visit(ctx.params().expr_math(2))));
               }
           }
           
           // Option
           switch (ctx.MATH_FUNC().getText())
           {
               // PI
               case "PI" : res=String.valueOf(Math.PI); 
                           break;
                           
               // Round
               case "round" : // Result
                              res=String.valueOf(Math.round(param_1)); 
                              
                              // Break
                              break;
                              
               // Pow
               case "pow" :   // Result
                              res=String.valueOf(Math.pow(param_1, param_2)); 
                              
                              // Break
                              break;
                              
               // Sqrt
               case "sqrt" :   // Result
                              res=String.valueOf(Math.sqrt(param_1)); 
                              
                              // Break
                              break;
                              
                // Abs
               case "abs" :   // Result
                              res=String.valueOf(Math.abs(param_1)); 
                              
                              // Break
                              break;
                              
                // Ceil
               case "ceil" :   // Result
                              res=String.valueOf(Math.ceil(param_1)); 
                              
                              // Break
                              break;
                              
                // Floor
               case "floor" :   // Result
                              res=String.valueOf(Math.floor(param_1)); 
                              
                              // Break
                              break;
                              
                // Sin
               case "sin" :   // Result
                              res=String.valueOf(Math.sin(param_1)); 
                              
                              // Break
                              break;
                              
                // Cos
               case "cos" :   // Result
                              res=String.valueOf(Math.cos(param_1)); 
                              
                              // Break
                              break;
                              
                // Min
               case "min" :   // Result
                              res=String.valueOf(Math.min(param_1, param_2)); 
                              
                              // Break
                              break;
                              
                // Max
               case "max" :   // Result
                              res=String.valueOf(Math.max(param_1, param_2)); 
                              
                              // Break
                              break;
                              
               // Max
               case "tan" :   // Result
                              res=String.valueOf(Math.tan(param_1)); 
                              
                              // Break
                              break;
                              
               // Random
               case "random" :  // Hash
                                String hash=UTILS.BASIC.hash(this.VM.SYSTEM.BLOCK.HASH+this.VM.trigger_hash);
                   
                                // Random number
                                Random r=new Random(UTILS.BASIC.hashToLong(hash)); 
                   
                                // Result
                                res=String.valueOf(r.nextInt((int)(Math.round(param_1))));
                              
                                // Break
                                break;
            }
           
           // Return
           return res;
        }
        catch (Exception ex)
        {
            System.out.println("Error in Math statement");
        }
        
        return ""; 
    }
    
    @Override 
    public String visitVar_function_crypto(testParser.Var_function_cryptoContext ctx) 
    { 
        String res="";
        
        String par_1="";
        
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        try
        {
            switch (ctx.CRYPTO_FUNC().getText())
            {
                case "digest" :   // Text to hash
                                  par_1=String.valueOf(visit(ctx.params().expr_math(0)));
                            
                                  // Hash function
                                  String par_2=String.valueOf(visit(ctx.params().expr_math(1)));
                            
                                  // Select
                                  switch (par_2)
                                  {
                                      // SHA224
                                      case "SHA224" : SHA224 sha224=new SHA224(); 
                                                      res=bytesToHex(sha224.digest(par_1.getBytes()));
                                                      break;
                                                
                                      // SHA256
                                      case "SHA256" : SHA256 sha256=new SHA256(); 
                                                      res=bytesToHex(sha256.digest(par_1.getBytes()));
                                                      break;
                                                
                                      // SHA384
                                      case "SHA384" : SHA384 sha384=new SHA384(); 
                                                      res=bytesToHex(sha384.digest(par_1.getBytes()));
                                                      break;
                                                
                                      // SHA512
                                      case "SHA512" : SHA512 sha512=new SHA512(); 
                                                      res=bytesToHex(sha512.digest(par_1.getBytes()));
                                                      break;
                            }
                            
                            break;
                            
                case "base64_decode" : // Text to hash
                                       par_1=String.valueOf(visit(ctx.params().expr_math(0)));
                                       
                                       // Result
                                       res=UTILS.BASIC.base64_decode(String.valueOf(visit(ctx.params().expr_math(0))));
                                       
                                       break;
                                       
                case "base64_encode" : // Text to hash
                                       par_1=String.valueOf(visit(ctx.params().expr_math(0)));
                                       
                                       // Result
                                       res=UTILS.BASIC.base64_encode(String.valueOf(visit(ctx.params().expr_math(0))));
                                       
                                       break;
            }
        }
        catch (Exception ex)
        {
            System.out.println("Exception in Crypto statement - "+ex.getMessage());
        }
        
        return res;
    }
    
    public static String bytesToHex(byte[] in) 
    {
        final StringBuilder builder = new StringBuilder();
        
        for(byte b : in) 
        {
            builder.append(String.format("%02x", b));
        }
        
        return builder.toString();
    }
    
    
    @Override 
    public String visitF_balance(testParser.F_balanceContext ctx) 
    {
        try
        {
           // Address
           String adr=String.valueOf(visit(ctx.expr_math(0)));
        
           // Currency
           String cur=String.valueOf(visit(ctx.expr_math(1)));
        
           // Address from domain
           adr=UTILS.BASIC.adrFromName(adr);
           
           // Currency
           if (!UTILS.BASIC.isCur(cur))
               throw new Exception("Invalid currency");
           
           // Balance
           double balance=UTILS.ACC.getBalance(adr, cur);
           
           // Balance
           return String.valueOf(balance);
        }
        catch (Exception ex)
        {
            System.out.println("Exception in balance function - "+ex.getMessage());
        }
        
        return "0"; 
    }
    
    @Override public String visitExpr_sys_data(testParser.Expr_sys_dataContext ctx) 
    {
        // Result
        String res="";
        
        // Block
        if (ctx.sys_data().SYS_DATA_BLOCK()!=null)
        {
            switch (ctx.sys_data().SYS_DATA_BLOCK().getText())
                
            {
                // Block
                case "BLOCK" : res=String.valueOf(this.VM.SYSTEM.BLOCK.BLOCK); 
                               break;
        
                // Prev hash
                case "PREV_HASH" : res=String.valueOf(this.VM.SYSTEM.BLOCK.PREV_HASH); 
                                   break;
        
                // Prev hash
                case "PAYLOAD_HASH" : res=String.valueOf(this.VM.SYSTEM.BLOCK.PAYLOAD_HASH); 
                                      break;
        
                // Signer
                case "SIGNER" : res=String.valueOf(this.VM.SYSTEM.BLOCK.SIGNER); 
                                break;
        
                // Sign
                case "SIGN" : res=String.valueOf(this.VM.SYSTEM.BLOCK.SIGN); 
                              break;
        
                // Timestamp
                case "TSTAMP" : res=String.valueOf(this.VM.SYSTEM.BLOCK.TSTAMP);  
                                break;
        
                // Nonce
                case "NONCE" : res=String.valueOf(this.VM.SYSTEM.BLOCK.NONCE); 
                               break;
        
                // Dificulty
                case "NET_DIF" : res=String.valueOf(this.VM.SYSTEM.BLOCK.NET_DIF); 
                                 break;
            }
        }
        
        // Trans
        if (ctx.sys_data().SYS_DATA_TRANS()!=null)
        {
            switch (ctx.sys_data().SYS_DATA_TRANS().getText())
                
            {
                // Source
                case "SRC" : res=String.valueOf(this.VM.SYSTEM.TRANS.SRC); 
                               break;
        
                // Dest
                case "DEST" : res=String.valueOf(this.VM.SYSTEM.TRANS.DEST); 
                                   break;
        
                // Amount
                case "AMOUNT" : res=String.valueOf(this.VM.SYSTEM.TRANS.AMOUNT); 
                                      break;
        
                // Currency
                case "CUR" : res=String.valueOf(this.VM.SYSTEM.TRANS.CUR); 
                                break;
        
                // Escrower
                case "ESCROWER" : res=String.valueOf(this.VM.SYSTEM.TRANS.ESCROWER); 
                              break;
        
                // Message
                case "MES" : res=String.valueOf(this.VM.SYSTEM.TRANS.MES);  
                                break;
        
                // Hash
                case "HASH" : res=String.valueOf(this.VM.SYSTEM.TRANS.HASH); 
                               break;
        
                // Timestamp
                case "TSTAMP" : res=String.valueOf(this.VM.SYSTEM.TRANS.TSTAMP); 
                                 break;
            }
        }
     
        // Company
        if (ctx.sys_data().SYS_DATA_COM()!=null)
        {
            switch (ctx.sys_data().SYS_DATA_COM().getText())
                
            {
                // Address
                case "ADR" : res=String.valueOf(this.VM.SYSTEM.COM.ADR); 
                               break;
        
                // Copany ID
                case "COMID" : res=String.valueOf(this.VM.SYSTEM.COM.COMID); 
                                   break;
        
                // Name
                case "NAME" : res=String.valueOf(this.VM.SYSTEM.COM.NAME); 
                                      break;
        
                // Code
                case "CODE" : res=String.valueOf(this.VM.SYSTEM.COM.CODE); 
                                break;
        
                // Symbol
                case "SYMBOL" : res=String.valueOf(this.VM.SYSTEM.COM.SYMBOL); 
                              break;
        
                // Expires
                case "EXPIRES" : res=String.valueOf(this.VM.SYSTEM.COM.EXPIRES);  
                                break;
        
                // Pic
                case "PIC" : res=String.valueOf(this.VM.SYSTEM.COM.PIC); 
                               break;
        
                // Balance
                case "BALANCE" : res=String.valueOf(this.VM.SYSTEM.COM.BALANCE); 
                                 break;
                                 
                // Electricity
                case "ELECTRICITY" : res=String.valueOf(this.VM.SYSTEM.COM.ELECTRICITY); 
                                 break;
            }
        }
        
        
        // Message
        if (ctx.sys_data().SYS_DATA_MES()!=null)
        {
            switch (ctx.sys_data().SYS_DATA_MES().getText())
                
            {
                // Address
                case "SENDER" : res=String.valueOf(this.VM.SYSTEM.MES.SENDER); 
                                break;
        
                // Copany ID
                case "REC" : res=String.valueOf(this.VM.SYSTEM.MES.REC); 
                             break;
        
                // Name
                case "SUBJ" : res=String.valueOf(this.VM.SYSTEM.MES.SUBJ); 
                              break;
        
                // Code
                case "MES" : res=String.valueOf(this.VM.SYSTEM.MES.MES); 
                             break;
        
                // Symbol
                case "HASH" : res=String.valueOf(this.VM.SYSTEM.MES.HASH); 
                              break;
        
                // Expires
                case "TSTAMP" : res=String.valueOf(this.VM.SYSTEM.MES.TSTAMP);  
                                break;
            }
        }
        
        // Return
        return res;
    }
    
    @Override 
    public String visitF_next_row(testParser.F_next_rowContext ctx) 
    { 
        boolean res;
        
        // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        try
        {
            // Load var
            CVar v=this.VM.getVar(ctx.var().getText());
            
            // Next
            res=v.next();
        }
        catch (Exception ex)
        {
           return "false";    
        }
        
        if (res)
           return "true";
        else
           return "false";
    }
    
    @Override 
    public String visitF_get_col(testParser.F_get_colContext ctx) 
    { 
        String val="";
        
         // Step
        this.VM.step(0.0001, ctx.start.getLine());
        
        try
        {
            // Load var
            CVar v=this.VM.getVar(ctx.expr_math(0).getText());
            
            // Next
            val=v.val(ctx.expr_math(1).getText().replace("\"", ""));
        }
        catch (Exception ex)
        {
           return "false";    
        }
        
        return val;
    }
    
    @Override public String visitF_renew(testParser.F_renewContext ctx) 
    {
        // Days
        long days=0;
        
        // Step
        try
        {
            // Has days ?
            if (ctx.expr_math()==null)
            {
                this.VM.setErr("Invalid parameter in renew function", ctx.getStart().getLine());
                return "false";
            }
                
            // Days
            days=Long.parseLong(String.valueOf(visit(ctx.expr_math())));
            
            // Valid days ?
            if (days<1)
            {
                this.VM.setErr("Invalid days in renew function", ctx.getStart().getLine());
                return "false";
            }
            
            // Company address
            String adr=this.VM.SYSTEM.COM.ADR;
            
            // Balance
            if (UTILS.ACC.getBalance(adr, "CRC", null)<0.0001*days)
            {
                this.VM.setErr("Invalid days in renew function", ctx.getStart().getLine());
                return "false";
            }
            
            if (this.VM.step(0.01*days, ctx.getStart().getLine()))
            {
                    // Transaction
                    UTILS.ACC.newTransfer(adr, 
                                          "default", 
                                          0.0001*days, 
                                          "CRC", 
                                          "You have renewed company licence", 
                                          "", 
                                          0, 
                                          this.VM.trigger_hash, 
                                          this.VM.SYSTEM.BLOCK.BLOCK,
                                          false,
                                          "",
                                          "");
        
                    // Renew
                    UTILS.DB.executeUpdate("UPDATE companies "
                                        + "SET expires=expires+"+(days*1440)+" "
                                      + "WHERE adr='"+adr+"'");
            
                    // Clear trans
                    UTILS.ACC.clearTrans(this.VM.trigger_hash, 
                                     "ID_ALL", 
                                     this.VM.SYSTEM.BLOCK.BLOCK);
            }
        }
        catch (Exception ex)
        {
             this.VM.setErr("Unexpected error in renew function", ctx.getStart().getLine());
        }
        
        
        return "true";
    }
    
    @Override public String visitF_dividends(testParser.F_dividendsContext ctx) 
    { 
        // Days
        double amount=0;
        
        try
        {
            // Has days ?
            if (ctx.expr_math()==null)
            {
                this.VM.setErr("Invalid parameter in dividends function", ctx.getStart().getLine());
                return "false";
            }
            
            // Amount
            amount=Double.parseDouble(String.valueOf(visit(ctx.expr_math())));
           
            // Amount
            if (amount<0.01)
            {
                this.VM.setErr("Minimum dividends amount is 0.01 CRC", ctx.getStart().getLine());
                return "false";
            }
        
            // Balance
            if (UTILS.ACC.getBalance(this.VM.SYSTEM.COM.ADR, "CRC")<amount)
            {
                this.VM.setErr("Insuficient funds", ctx.getStart().getLine());
                return "false";
            }
        
            // Shareholders
            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM assets_owners "
                                              + "WHERE symbol='"+this.VM.SYSTEM.COM.SYMBOL+"' "
                                                + "AND qty>=0.01");
            
            if (this.VM.step(UTILS.DB.getRowsNo(rs)*0.01, ctx.getStart().getLine()))
            {
                // Loop
                while (rs.next())
                {
                    // Per share
                    double per_share=amount/10000;
            
                    // Amount
                    double to_pay=rs.getDouble("qty")*per_share;
            
            
                    // Pay
                    UTILS.ACC.newTransfer(this.VM.SYSTEM.COM.ADR, 
                                          rs.getString("owner"), 
                                          to_pay, 
                                          "CRC", 
                                          "Autonomus corporation "+this.VM.SYSTEM.COM.NAME+" paid dividends", 
                                          "", 
                                          0,
                                          this.VM.SYSTEM.BLOCK.HASH, 
                                          this.VM.SYSTEM.BLOCK.BLOCK,
                                          false,
                                          "",
                                          "");
                }
              }
        }
        catch (Exception ex)
        {
            this.VM.setErr("Unexpected error in dividends function", ctx.getStart().getLine());
            return "false";  
        }
            
    
        // Return
        return "true";
    }
    
    public boolean isComp(String str)
    {
        if (str.equals("=") || 
            str.equals("<") || 
            str.equals(">") || 
            str.equals("<>") || 
            str.equals(">=") || 
            str.equals("<="))
        return true;
        else
        return false;
    }
    
    @Override 
    public String visitF_sysinfo(testParser.F_sysinfoContext ctx) 
    { 
        try
        {
           // Query
           String query=ctx.sys_query().getText();
           
           // ResultSet 
           ResultSet rs=UTILS.DB.executeQuery(query);
           
           // Set variable if enough electricity
           if (this.VM.step(UTILS.DB.getRowsNo(rs)*0.01, ctx.start.getLine()))
              this.VM.toRS(ctx.var().getText(), rs);
        }
        catch (Exception ex)
        {
            this.VM.setErr("Unexpected error in sysinfo function", ctx.getStart().getLine());
            return "false";  
        }
        
        return ""; 
    }

    @Override public String visitF_get_price(testParser.F_get_priceContext ctx) 
    { 
        if (!this.VM.step(0.1, ctx.start.getLine()))
            return "false";
            
        try
        {
            // Product
            String prod=String.valueOf(visit(ctx.expr_math(0)));
            
            // Market side 
            String mkt_side=String.valueOf(visit(ctx.expr_math(1)));
            
            // Product valid ?
            if (!UTILS.BASIC.isStringID(prod))
            {
                this.VM.setErr("Invalid product in getPrice function", ctx.getStart().getLine());
                return "false";
            }
            
            // Market side valid ?
            if (!mkt_side.equals("ID_BID") && 
                !mkt_side.equals("ID_ASK"))
            {
                this.VM.setErr("Invalid product in getPrice function", ctx.getStart().getLine());
                return "false";
            }
            
            // Load market
            ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                               + "FROM assets_mkts "
                                              + "WHERE asset='"+prod+"'");
            
            // Has data ?
            if (UTILS.DB.hasData(rs))
            {
                // Next
                rs.next();
                
                // Mkt ID
                long mktID=rs.getLong("mktID");
                
                // Return price
                if (mkt_side.equals("ID_ASK"))
                    return String.valueOf(rs.getDouble("ask"));
                else
                    return String.valueOf(rs.getDouble("bid"));
            }
            else
            {
                this.VM.setErr("Invalid product in getPrice function", ctx.getStart().getLine());
                return "false";
            }
        }
        catch (Exception ex)
        {
            this.VM.setErr("Unexpected error in getPrice function", ctx.getStart().getLine());
            return "false"; 
        }
    }
    
    @Override public String visitF_send(testParser.F_sendContext ctx) 
    { 
         if (!this.VM.step(0.1, ctx.start.getLine()))
            return "false";
         
        try
        {
            // Sender
            String target_adr=this.VM.SYSTEM.COM.ADR;
            
            // Recipient
            String dest=String.valueOf(visit(ctx.expr_math(0)));
            
            // Amount
            double amount=Double.parseDouble(String.valueOf(visit(ctx.expr_math(1))));
           
            // Currency
            String cur=String.valueOf(visit(ctx.expr_math(2)));
           
            // Mes
            String mes=String.valueOf(visit(ctx.expr_math(3)));
            
             // Check dest
	    if (!UTILS.BASIC.isAdr(dest))
            {
                this.VM.setErr("Invalid destination address", ctx.getStart().getLine());
                return "false";
            }
             
            // Source and destination the same
            if (target_adr.equals(dest))
            {
                this.VM.setErr("Source and destination address can't be the same", ctx.getStart().getLine());
                return "false";
            }
             
            // Check amount
            if (cur.equals("CRC"))
            {
	        if (amount<0.0001)
                {
                    this.VM.setErr("Invalid amount", ctx.getStart().getLine());
                    return "false";
                }
            }
            else
            {
                if (amount<0.00000001)
                {
                    this.VM.setErr("Invalid amount", ctx.getStart().getLine());
                    return "false";
                }
            }
            
            // Message 
            if (mes!=null)
            {
               // Length
               if (mes.length()<5 || 
                   mes.length()>1000)
               {
                   this.VM.setErr("Invalid message length", ctx.getStart().getLine());
                   return "false";
               }
               
               // String
               if (!UTILS.BASIC.isString(mes))
               {
                   this.VM.setErr("Invalid mmessage", ctx.getStart().getLine());
                   return "false";
               }
               
            }
            
            // Check cur
	    if (!cur.equals("CRC"))
            {
                // Symbol valid ?
                if (!UTILS.BASIC.isSymbol(cur, 5) && 
                    !UTILS.BASIC.isSymbol(cur, 6))
                {
                    this.VM.setErr("Invalid currency", ctx.getStart().getLine());
                    return "false";
                }
                     
                // Asset exist ?
                if (!UTILS.BASIC.isAsset(cur))
                {
                    this.VM.setErr("Invalid currency", ctx.getStart().getLine());
                    return "false";
                }
                
                // Recipient trust asset ?
                ResultSet rs=UTILS.DB.executeQuery("SELECT * "
                                                   + "FROM adr_attr "
                                                  + "WHERE adr='"+dest+"' "
                                                    + "AND attr='ID_TRUST_ASSET'");
                
                // No data ?
                if (!UTILS.DB.hasData(rs))
                {
                    this.VM.setErr("Recipient doesn't trust asset", ctx.getStart().getLine());
                    return "false";
                }
            }
            
            // Check source balance
            double balance=UTILS.ACC.getBalance(target_adr, cur, this.VM.SYSTEM.BLOCK.BLOCK_PAYLOAD);
            
            // Fee
            double fee=amount*0.001;
            
            // Min fee ?
            if (fee<0.0001)
                fee=0.0001;
                  
            // Insufficient funds
            if (balance<amount+fee) 
            {
                this.VM.setErr("Insufficient funds", ctx.getStart().getLine());
                return "false";
            }
            
            // Insert pending transaction
	    UTILS.ACC.newTransfer(target_adr, 
                                  dest,
                                  amount,
                                  cur, 
                                  "Transaction to another address "+target_adr, 
                                  "", 
                                  0,
                                  this.VM.trigger_hash, 
                                  this.VM.SYSTEM.BLOCK.BLOCK,
                                  false,
                                  "",
                                  "");
                
            // IPN
            if (UTILS.WALLET.isMine(dest))
                UTILS.BASIC.checkNewTransIPN("confirmed", 
                                             target_adr, 
                                             dest, 
                                             amount, 
                                             cur, 
                                             mes, 
                                             this.VM.trigger_hash, 
                                             this.VM.SYSTEM.BLOCK.BLOCK);
            
            // Clear trans
            UTILS.ACC.clearTrans(this.VM.trigger_hash, "ID_ALL", this.VM.SYSTEM.BLOCK.BLOCK);
           
        }
        catch (Exception ex)
        {
             this.VM.setErr("Unexpected error in send function", ctx.getStart().getLine());
             return "false";
        }
        
        return ""; 
    }
	
    @Override 
    public String visitF_mes(testParser.F_mesContext ctx) 
    { 
        try
        {
            // Step 
            if (this.VM.step(1, ctx.start.getLine()))
            {
                // Recipient
                String rec=String.valueOf(visit(ctx.expr_math(0)));
                
                // Subject
                String subject=String.valueOf(visit(ctx.expr_math(1)));

                // Message
                String mes=String.valueOf(visit(ctx.expr_math(2)));
                
                // Valid recipient
                if (!UTILS.BASIC.isAdr(rec))
                {
                    this.VM.setErr("Invalid recipient", ctx.getStart().getLine());
                    return "false";
                }
                
                // Subject length
                if (subject.length()<2 || 
                    subject.length()>250)
                {
                    this.VM.setErr("Invalid subject lenght in mes function", ctx.getStart().getLine());
                    return "false";
                }
                
                // Subject valid
                if (!UTILS.BASIC.isString(subject))
                {
                    this.VM.setErr("Invalid subject content in mes function", ctx.getStart().getLine());
                    return "false";
                }
                
                // Message length
                if (mes.length()<2 || 
                    mes.length()>250)
                {
                    this.VM.setErr("Invalid message lenght in mes function", ctx.getStart().getLine());
                    return "false";
                }
                
                // Message valid
                if (!UTILS.BASIC.isString(mes))
                {
                    this.VM.setErr("Invalid message content in mes function", ctx.getStart().getLine());
                    return "false";
                }
                
                // Insert message
                if (UTILS.WALLET.isMine(rec))
                {
                    // Insert
                    UTILS.DB.executeUpdate("INSERT INTO mes "
                                                 + "SET from_adr='"+this.VM.SYSTEM.COM.ADR+"', "
                                                     + "to_adr='"+rec+"', "
                                                     + "subject='"+UTILS.BASIC.base64_encode(subject)+"', "
                                                     + "mes='"+UTILS.BASIC.base64_encode(mes)+"', "
                                                     + "status='0', "
                                                     + "tstamp='"+UTILS.BASIC.tstamp()+"', "
                                                     + "tgt='0'");
                    
                    // Unread
                    UTILS.DB.executeUpdate("UPDATE web_users "
                                            + "SET unread_mes=unread_mes+1 "
                                          + "WHERE ID='"+UTILS.BASIC.userFromAdr(rec)+"'");
                }
            }
        }
        catch (Exception ex)
        {
           this.VM.setErr("Unexpected error in send function", ctx.getStart().getLine());
           return "false"; 
        }
        
        return ""; 
    }
    
    @Override public String visitF_email(testParser.F_emailContext ctx) 
    { 
        try
        {
            // Step 
            if (this.VM.step(1, ctx.start.getLine()))
            {
                // Recipient
                String rec=String.valueOf(visit(ctx.expr_math(0)));
                
                // Subject
                String subject=String.valueOf(visit(ctx.expr_math(1)));

                // Message
                String mes=String.valueOf(visit(ctx.expr_math(2)));
                
                // Valid recipient
                if (!UTILS.BASIC.isEmail(rec))
                {
                    this.VM.setErr("Invalid recipient email", ctx.getStart().getLine());
                    return "false";
                }
                
                // Subject length
                if (subject.length()<2 || 
                    subject.length()>250)
                {
                    this.VM.setErr("Invalid subject lenght in mes function", ctx.getStart().getLine());
                    return "false";
                }
                
                // Subject valid
                if (!UTILS.BASIC.isString(subject))
                {
                    this.VM.setErr("Invalid subject content in mes function", ctx.getStart().getLine());
                    return "false";
                }
                
                // Message length
                if (mes.length()<2 || 
                    mes.length()>250)
                {
                    this.VM.setErr("Invalid message lenght in mes function", ctx.getStart().getLine());
                    return "false";
                }
                
                // Message valid
                if (!UTILS.BASIC.isString(mes))
                {
                    this.VM.setErr("Invalid message content in mes function", ctx.getStart().getLine());
                    return "false";
                }
                
                // Insert message
                UTILS.DB.executeUpdate("INSERT INTO out_req "
                                             + "SET tip='ID_EMAIL', "
                                                 + "dest='"+rec+"', "
                                                 + "subject='"+UTILS.BASIC.base64_encode(subject)+"', "
                                                 + "message='"+UTILS.BASIC.base64_encode(mes)+"', "
                                                 + "adr='"+this.VM.SYSTEM.COM.ADR+"', "
                                                 + "tstamp='"+UTILS.BASIC.tstamp()+"', "
                                                 + "status='ID_PENDING'");
                 
            }
        }
        catch (Exception ex)
        {
           this.VM.setErr("Unexpected error in send function", ctx.getStart().getLine());
           return "false"; 
        }
        
        return ""; 
    }
    
    @Override public String visitF_url(testParser.F_urlContext ctx) 
    { 
        try
        {
           // Step 
            if (this.VM.step(1, ctx.start.getLine()))
            {
                // Recipient
                String url=String.valueOf(visit(ctx.expr_math()));
                
                // URL valid ?
                if (!UTILS.BASIC.isLink(url))
                {
                    this.VM.setErr("Invalid URL content in callURL function", ctx.getStart().getLine());
                    return "false";
                }
                
                // Insert
                UTILS.DB.executeUpdate("INSERT INTO out_req "
                                             + "SET tip='ID_EMAIL', "
                                                 + "URL='"+UTILS.BASIC.base64_encode(url)+"', "
                                                 + "adr='"+this.VM.SYSTEM.COM.ADR+"', "
                                                 + "tstamp='"+UTILS.BASIC.tstamp()+"', "
                                                 + "status='ID_PENDING'");
            }
        }
        catch (Exception ex)
        {
            this.VM.setErr("Unexpected error in url function", ctx.getStart().getLine());
            return "false"; 
        }
        
        return ""; 
    }
    
    @Override public String visitF_reject_trans(testParser.F_reject_transContext ctx) 
    { 
        try
        {
            // Reject
            if (this.VM.step(0.1, ctx.start.getLine()))
                this.VM.trans_status=false;
        }
        catch (Exception ex)
        {
            this.VM.setErr("Unexpected error in reject_trans function", ctx.getStart().getLine());
            return "false"; 
        }
        
        return ""; 
    }
    
    @Override 
    public String visitF_trade(testParser.F_tradeContext ctx) 
    {
        // Parser
        CParseTrade parse=new CParseTrade(this.VM, this);
        
        // Return
        return parse.parseTrade(ctx);
    }
}
