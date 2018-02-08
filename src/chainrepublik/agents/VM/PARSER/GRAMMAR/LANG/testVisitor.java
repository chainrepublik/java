// Generated from /Users/technicalsupport/Documents/NetBeansProjects/chainrepublik/src/chainrepublik/agents/test.g4 by ANTLR 4.7
package chainrepublik.agents.VM.PARSER.GRAMMAR.LANG;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link testParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface testVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link testParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(testParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#functions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctions(testParser.FunctionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#function_user}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_user(testParser.Function_userContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#function_init}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_init(testParser.Function_initContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#function_onTrans}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_onTrans(testParser.Function_onTransContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#function_onMes}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_onMes(testParser.Function_onMesContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#function_onBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_onBlock(testParser.Function_onBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#function_cleanup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_cleanup(testParser.Function_cleanupContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#lines}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLines(testParser.LinesContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#line}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLine(testParser.LineContext ctx);
	/**
	 * Visit a parse tree produced by the {@code f_predef}
	 * labeled alternative in {@link testParser#func_call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitF_predef(testParser.F_predefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code f_user}
	 * labeled alternative in {@link testParser#func_call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitF_user(testParser.F_userContext ctx);
	/**
	 * Visit a parse tree produced by the {@code f_print}
	 * labeled alternative in {@link testParser#predef_func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitF_print(testParser.F_printContext ctx);
	/**
	 * Visit a parse tree produced by the {@code f_exit}
	 * labeled alternative in {@link testParser#predef_func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitF_exit(testParser.F_exitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code f_query}
	 * labeled alternative in {@link testParser#predef_func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitF_query(testParser.F_queryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code f_balance}
	 * labeled alternative in {@link testParser#predef_func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitF_balance(testParser.F_balanceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code f_send}
	 * labeled alternative in {@link testParser#predef_func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitF_send(testParser.F_sendContext ctx);
	/**
	 * Visit a parse tree produced by the {@code f_mes}
	 * labeled alternative in {@link testParser#predef_func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitF_mes(testParser.F_mesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code f_email}
	 * labeled alternative in {@link testParser#predef_func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitF_email(testParser.F_emailContext ctx);
	/**
	 * Visit a parse tree produced by the {@code f_url}
	 * labeled alternative in {@link testParser#predef_func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitF_url(testParser.F_urlContext ctx);
	/**
	 * Visit a parse tree produced by the {@code f_reject_trans}
	 * labeled alternative in {@link testParser#predef_func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitF_reject_trans(testParser.F_reject_transContext ctx);
	/**
	 * Visit a parse tree produced by the {@code f_sysinfo}
	 * labeled alternative in {@link testParser#predef_func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitF_sysinfo(testParser.F_sysinfoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code f_get_price}
	 * labeled alternative in {@link testParser#predef_func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitF_get_price(testParser.F_get_priceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code f_trade}
	 * labeled alternative in {@link testParser#predef_func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitF_trade(testParser.F_tradeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code f_dividends}
	 * labeled alternative in {@link testParser#predef_func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitF_dividends(testParser.F_dividendsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code f_renew}
	 * labeled alternative in {@link testParser#predef_func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitF_renew(testParser.F_renewContext ctx);
	/**
	 * Visit a parse tree produced by the {@code f_next_row}
	 * labeled alternative in {@link testParser#predef_func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitF_next_row(testParser.F_next_rowContext ctx);
	/**
	 * Visit a parse tree produced by the {@code f_get_col}
	 * labeled alternative in {@link testParser#predef_func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitF_get_col(testParser.F_get_colContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#sys_query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSys_query(testParser.Sys_queryContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#sys_query_comp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSys_query_comp(testParser.Sys_query_compContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assign_expr}
	 * labeled alternative in {@link testParser#assign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign_expr(testParser.Assign_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assign_inc_dec}
	 * labeled alternative in {@link testParser#assign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign_inc_dec(testParser.Assign_inc_decContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assign_array}
	 * labeled alternative in {@link testParser#assign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign_array(testParser.Assign_arrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#globals}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobals(testParser.GlobalsContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#global_assign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobal_assign(testParser.Global_assignContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#global_var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobal_var(testParser.Global_varContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expr_func}
	 * labeled alternative in {@link testParser#expr_math}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_func(testParser.Expr_funcContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expr_nume}
	 * labeled alternative in {@link testParser#expr_math}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_nume(testParser.Expr_numeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expr_paran}
	 * labeled alternative in {@link testParser#expr_math}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_paran(testParser.Expr_paranContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expr_string}
	 * labeled alternative in {@link testParser#expr_math}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_string(testParser.Expr_stringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expr_sys_data}
	 * labeled alternative in {@link testParser#expr_math}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_sys_data(testParser.Expr_sys_dataContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expr_op_expr}
	 * labeled alternative in {@link testParser#expr_math}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_op_expr(testParser.Expr_op_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expr_var}
	 * labeled alternative in {@link testParser#expr_math}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_var(testParser.Expr_varContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expr_bool}
	 * labeled alternative in {@link testParser#expr_math}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_bool(testParser.Expr_boolContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expr_var_func}
	 * labeled alternative in {@link testParser#expr_math}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_var_func(testParser.Expr_var_funcContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expr_comp_bool}
	 * labeled alternative in {@link testParser#expr_comp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_comp_bool(testParser.Expr_comp_boolContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expr_comp_logic}
	 * labeled alternative in {@link testParser#expr_comp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_comp_logic(testParser.Expr_comp_logicContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expr_comp_op}
	 * labeled alternative in {@link testParser#expr_comp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_comp_op(testParser.Expr_comp_opContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expr_comp_paran}
	 * labeled alternative in {@link testParser#expr_comp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_comp_paran(testParser.Expr_comp_paranContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#stat_if}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat_if(testParser.Stat_ifContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#stat_for}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat_for(testParser.Stat_forContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#stat_while}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat_while(testParser.Stat_whileContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#stat_switch}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat_switch(testParser.Stat_switchContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#func_params}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_params(testParser.Func_paramsContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#params}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParams(testParser.ParamsContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#var_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_value(testParser.Var_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(testParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#dec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDec(testParser.DecContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(testParser.VarContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#var_array}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_array(testParser.Var_arrayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code var_function_var}
	 * labeled alternative in {@link testParser#var_function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_function_var(testParser.Var_function_varContext ctx);
	/**
	 * Visit a parse tree produced by the {@code var_function_math}
	 * labeled alternative in {@link testParser#var_function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_function_math(testParser.Var_function_mathContext ctx);
	/**
	 * Visit a parse tree produced by the {@code var_function_crypto}
	 * labeled alternative in {@link testParser#var_function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_function_crypto(testParser.Var_function_cryptoContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#array}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray(testParser.ArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#array_var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray_var(testParser.Array_varContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#string}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(testParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#bool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBool(testParser.BoolContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#misc_var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMisc_var(testParser.Misc_varContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#ret}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRet(testParser.RetContext ctx);
	/**
	 * Visit a parse tree produced by {@link testParser#sys_data}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSys_data(testParser.Sys_dataContext ctx);
}