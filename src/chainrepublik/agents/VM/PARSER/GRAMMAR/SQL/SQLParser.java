// Generated from /Users/technicalsupport/Documents/NetBeansProjects/chainrepublik/src/chainrepublik/agents/SQL.g4 by ANTLR 4.7
package chainrepublik.agents.VM.PARSER.GRAMMAR.SQL;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SQLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		COMP=10, USER_COL=11, LOGIC_OP=12, STRING=13, INT=14, TAB_NAME=15, WS=16;
	public static final int
		RULE_statement = 0, RULE_order_cond = 1, RULE_where_logic = 2, RULE_comp_exp = 3, 
		RULE_set_exp = 4, RULE_dec = 5, RULE_num = 6, RULE_val = 7;
	public static final String[] ruleNames = {
		"statement", "order_cond", "where_logic", "comp_exp", "set_exp", "dec", 
		"num", "val"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'SELECT * FROM '", "'WHERE'", "'ORDER BY'", "'INSERT INTO '", "'SET'", 
		"'UPDATE '", "'DELETE FROM '", "','", "'.'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, "COMP", "USER_COL", 
		"LOGIC_OP", "STRING", "INT", "TAB_NAME", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "SQL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SQLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class StatementContext extends ParserRuleContext {
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	 
		public StatementContext() { }
		public void copyFrom(StatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Sel_statContext extends StatementContext {
		public TerminalNode TAB_NAME() { return getToken(SQLParser.TAB_NAME, 0); }
		public Where_logicContext where_logic() {
			return getRuleContext(Where_logicContext.class,0);
		}
		public Order_condContext order_cond() {
			return getRuleContext(Order_condContext.class,0);
		}
		public Sel_statContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitSel_stat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Insert_statContext extends StatementContext {
		public TerminalNode TAB_NAME() { return getToken(SQLParser.TAB_NAME, 0); }
		public Set_expContext set_exp() {
			return getRuleContext(Set_expContext.class,0);
		}
		public Insert_statContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitInsert_stat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Update_statContext extends StatementContext {
		public TerminalNode TAB_NAME() { return getToken(SQLParser.TAB_NAME, 0); }
		public Set_expContext set_exp() {
			return getRuleContext(Set_expContext.class,0);
		}
		public Where_logicContext where_logic() {
			return getRuleContext(Where_logicContext.class,0);
		}
		public Update_statContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitUpdate_stat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Del_statContext extends StatementContext {
		public TerminalNode TAB_NAME() { return getToken(SQLParser.TAB_NAME, 0); }
		public Where_logicContext where_logic() {
			return getRuleContext(Where_logicContext.class,0);
		}
		public Del_statContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitDel_stat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_statement);
		int _la;
		try {
			setState(48);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				_localctx = new Sel_statContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(16);
				match(T__0);
				setState(17);
				match(TAB_NAME);
				setState(20);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__1) {
					{
					setState(18);
					match(T__1);
					setState(19);
					where_logic();
					}
				}

				setState(24);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(22);
					match(T__2);
					setState(23);
					order_cond();
					}
				}

				}
				break;
			case T__3:
				_localctx = new Insert_statContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(26);
				match(T__3);
				setState(27);
				match(TAB_NAME);
				setState(28);
				match(T__4);
				setState(30);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==USER_COL) {
					{
					setState(29);
					set_exp();
					}
				}

				}
				break;
			case T__5:
				_localctx = new Update_statContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(32);
				match(T__5);
				setState(33);
				match(TAB_NAME);
				setState(34);
				match(T__4);
				setState(36);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==USER_COL) {
					{
					setState(35);
					set_exp();
					}
				}

				setState(40);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__1) {
					{
					setState(38);
					match(T__1);
					setState(39);
					where_logic();
					}
				}

				}
				break;
			case T__6:
				_localctx = new Del_statContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(42);
				match(T__6);
				setState(43);
				match(TAB_NAME);
				setState(46);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__1) {
					{
					setState(44);
					match(T__1);
					setState(45);
					where_logic();
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Order_condContext extends ParserRuleContext {
		public List<TerminalNode> USER_COL() { return getTokens(SQLParser.USER_COL); }
		public TerminalNode USER_COL(int i) {
			return getToken(SQLParser.USER_COL, i);
		}
		public Order_condContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_order_cond; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitOrder_cond(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Order_condContext order_cond() throws RecognitionException {
		Order_condContext _localctx = new Order_condContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_order_cond);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50);
			match(USER_COL);
			setState(55);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(51);
				match(T__7);
				setState(52);
				match(USER_COL);
				}
				}
				setState(57);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Where_logicContext extends ParserRuleContext {
		public List<Comp_expContext> comp_exp() {
			return getRuleContexts(Comp_expContext.class);
		}
		public Comp_expContext comp_exp(int i) {
			return getRuleContext(Comp_expContext.class,i);
		}
		public List<TerminalNode> LOGIC_OP() { return getTokens(SQLParser.LOGIC_OP); }
		public TerminalNode LOGIC_OP(int i) {
			return getToken(SQLParser.LOGIC_OP, i);
		}
		public Where_logicContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_where_logic; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitWhere_logic(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Where_logicContext where_logic() throws RecognitionException {
		Where_logicContext _localctx = new Where_logicContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_where_logic);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			comp_exp();
			setState(63);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LOGIC_OP) {
				{
				{
				setState(59);
				match(LOGIC_OP);
				setState(60);
				comp_exp();
				}
				}
				setState(65);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Comp_expContext extends ParserRuleContext {
		public TerminalNode USER_COL() { return getToken(SQLParser.USER_COL, 0); }
		public TerminalNode COMP() { return getToken(SQLParser.COMP, 0); }
		public ValContext val() {
			return getRuleContext(ValContext.class,0);
		}
		public Comp_expContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comp_exp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitComp_exp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Comp_expContext comp_exp() throws RecognitionException {
		Comp_expContext _localctx = new Comp_expContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_comp_exp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			match(USER_COL);
			setState(67);
			match(COMP);
			setState(68);
			val();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Set_expContext extends ParserRuleContext {
		public TerminalNode USER_COL() { return getToken(SQLParser.USER_COL, 0); }
		public TerminalNode COMP() { return getToken(SQLParser.COMP, 0); }
		public ValContext val() {
			return getRuleContext(ValContext.class,0);
		}
		public List<Set_expContext> set_exp() {
			return getRuleContexts(Set_expContext.class);
		}
		public Set_expContext set_exp(int i) {
			return getRuleContext(Set_expContext.class,i);
		}
		public Set_expContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_set_exp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitSet_exp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Set_expContext set_exp() throws RecognitionException {
		Set_expContext _localctx = new Set_expContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_set_exp);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			match(USER_COL);
			setState(71);
			match(COMP);
			setState(72);
			val();
			setState(77);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(73);
					match(T__7);
					setState(74);
					set_exp();
					}
					} 
				}
				setState(79);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DecContext extends ParserRuleContext {
		public List<TerminalNode> INT() { return getTokens(SQLParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(SQLParser.INT, i);
		}
		public DecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dec; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitDec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DecContext dec() throws RecognitionException {
		DecContext _localctx = new DecContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_dec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			match(INT);
			setState(81);
			match(T__8);
			setState(82);
			match(INT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(SQLParser.INT, 0); }
		public DecContext dec() {
			return getRuleContext(DecContext.class,0);
		}
		public NumContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_num; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitNum(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumContext num() throws RecognitionException {
		NumContext _localctx = new NumContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_num);
		try {
			setState(86);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(84);
				match(INT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(85);
				dec();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(SQLParser.INT, 0); }
		public DecContext dec() {
			return getRuleContext(DecContext.class,0);
		}
		public TerminalNode STRING() { return getToken(SQLParser.STRING, 0); }
		public TerminalNode USER_COL() { return getToken(SQLParser.USER_COL, 0); }
		public ValContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_val; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SQLVisitor ) return ((SQLVisitor<? extends T>)visitor).visitVal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValContext val() throws RecognitionException {
		ValContext _localctx = new ValContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_val);
		try {
			setState(92);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(88);
				match(INT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(89);
				dec();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(90);
				match(STRING);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(91);
				match(USER_COL);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\22a\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\3\2\3\2\5\2"+
		"\27\n\2\3\2\3\2\5\2\33\n\2\3\2\3\2\3\2\3\2\5\2!\n\2\3\2\3\2\3\2\3\2\5"+
		"\2\'\n\2\3\2\3\2\5\2+\n\2\3\2\3\2\3\2\3\2\5\2\61\n\2\5\2\63\n\2\3\3\3"+
		"\3\3\3\7\38\n\3\f\3\16\3;\13\3\3\4\3\4\3\4\7\4@\n\4\f\4\16\4C\13\4\3\5"+
		"\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\7\6N\n\6\f\6\16\6Q\13\6\3\7\3\7\3\7\3"+
		"\7\3\b\3\b\5\bY\n\b\3\t\3\t\3\t\3\t\5\t_\n\t\3\t\2\2\n\2\4\6\b\n\f\16"+
		"\20\2\2\2h\2\62\3\2\2\2\4\64\3\2\2\2\6<\3\2\2\2\bD\3\2\2\2\nH\3\2\2\2"+
		"\fR\3\2\2\2\16X\3\2\2\2\20^\3\2\2\2\22\23\7\3\2\2\23\26\7\21\2\2\24\25"+
		"\7\4\2\2\25\27\5\6\4\2\26\24\3\2\2\2\26\27\3\2\2\2\27\32\3\2\2\2\30\31"+
		"\7\5\2\2\31\33\5\4\3\2\32\30\3\2\2\2\32\33\3\2\2\2\33\63\3\2\2\2\34\35"+
		"\7\6\2\2\35\36\7\21\2\2\36 \7\7\2\2\37!\5\n\6\2 \37\3\2\2\2 !\3\2\2\2"+
		"!\63\3\2\2\2\"#\7\b\2\2#$\7\21\2\2$&\7\7\2\2%\'\5\n\6\2&%\3\2\2\2&\'\3"+
		"\2\2\2\'*\3\2\2\2()\7\4\2\2)+\5\6\4\2*(\3\2\2\2*+\3\2\2\2+\63\3\2\2\2"+
		",-\7\t\2\2-\60\7\21\2\2./\7\4\2\2/\61\5\6\4\2\60.\3\2\2\2\60\61\3\2\2"+
		"\2\61\63\3\2\2\2\62\22\3\2\2\2\62\34\3\2\2\2\62\"\3\2\2\2\62,\3\2\2\2"+
		"\63\3\3\2\2\2\649\7\r\2\2\65\66\7\n\2\2\668\7\r\2\2\67\65\3\2\2\28;\3"+
		"\2\2\29\67\3\2\2\29:\3\2\2\2:\5\3\2\2\2;9\3\2\2\2<A\5\b\5\2=>\7\16\2\2"+
		">@\5\b\5\2?=\3\2\2\2@C\3\2\2\2A?\3\2\2\2AB\3\2\2\2B\7\3\2\2\2CA\3\2\2"+
		"\2DE\7\r\2\2EF\7\f\2\2FG\5\20\t\2G\t\3\2\2\2HI\7\r\2\2IJ\7\f\2\2JO\5\20"+
		"\t\2KL\7\n\2\2LN\5\n\6\2MK\3\2\2\2NQ\3\2\2\2OM\3\2\2\2OP\3\2\2\2P\13\3"+
		"\2\2\2QO\3\2\2\2RS\7\20\2\2ST\7\13\2\2TU\7\20\2\2U\r\3\2\2\2VY\7\20\2"+
		"\2WY\5\f\7\2XV\3\2\2\2XW\3\2\2\2Y\17\3\2\2\2Z_\7\20\2\2[_\5\f\7\2\\_\7"+
		"\17\2\2]_\7\r\2\2^Z\3\2\2\2^[\3\2\2\2^\\\3\2\2\2^]\3\2\2\2_\21\3\2\2\2"+
		"\16\26\32 &*\60\629AOX^";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}