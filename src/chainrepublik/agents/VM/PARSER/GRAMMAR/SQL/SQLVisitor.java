// Generated from /Users/technicalsupport/Documents/NetBeansProjects/chainrepublik/src/chainrepublik/agents/SQL.g4 by ANTLR 4.7
package chainrepublik.agents.VM.PARSER.GRAMMAR.SQL;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SQLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SQLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code sel_stat}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSel_stat(SQLParser.Sel_statContext ctx);
	/**
	 * Visit a parse tree produced by the {@code insert_stat}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsert_stat(SQLParser.Insert_statContext ctx);
	/**
	 * Visit a parse tree produced by the {@code update_stat}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUpdate_stat(SQLParser.Update_statContext ctx);
	/**
	 * Visit a parse tree produced by the {@code del_stat}
	 * labeled alternative in {@link SQLParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDel_stat(SQLParser.Del_statContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#order_cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrder_cond(SQLParser.Order_condContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#where_logic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhere_logic(SQLParser.Where_logicContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#comp_exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComp_exp(SQLParser.Comp_expContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#set_exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSet_exp(SQLParser.Set_expContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#dec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDec(SQLParser.DecContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#num}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNum(SQLParser.NumContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLParser#val}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVal(SQLParser.ValContext ctx);
}