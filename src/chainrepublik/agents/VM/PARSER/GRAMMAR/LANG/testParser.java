// Generated from /Users/technicalsupport/Documents/NetBeansProjects/chainrepublik/src/chainrepublik/agents/test.g4 by ANTLR 4.7
package chainrepublik.agents.VM.PARSER.GRAMMAR.LANG;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class testParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, T__48=49, T__49=50, T__50=51, SYS_DATA_COM=52, 
		SYS_DATA_BLOCK=53, SYS_DATA_MES=54, SYS_DATA_TRANS=55, BREAK=56, STR_FUNC=57, 
		MATH_FUNC=58, ARRAY_FUNC=59, CRYPTO_FUNC=60, MATH_OP=61, ASSIGN_OP=62, 
		COMP_OP=63, BITWISE_OP=64, LOGIC_OP=65, INCDEC_OP=66, NOT_OP=67, TRUE=68, 
		FALSE=69, VAR=70, CHAR_SEQ=71, INT=72, EQUAL=73, COL_NAME=74, DB_STR=75, 
		DB_COMP=76, TAB_NAME=77, WS=78;
	public static final int
		RULE_program = 0, RULE_functions = 1, RULE_function_user = 2, RULE_function_init = 3, 
		RULE_function_onTrans = 4, RULE_function_onMes = 5, RULE_function_onBlock = 6, 
		RULE_function_cleanup = 7, RULE_lines = 8, RULE_line = 9, RULE_func_call = 10, 
		RULE_predef_func = 11, RULE_sys_query = 12, RULE_sys_query_comp = 13, 
		RULE_assign = 14, RULE_globals = 15, RULE_global_assign = 16, RULE_global_var = 17, 
		RULE_expr_math = 18, RULE_expr_comp = 19, RULE_stat_if = 20, RULE_stat_for = 21, 
		RULE_stat_while = 22, RULE_stat_switch = 23, RULE_func_params = 24, RULE_params = 25, 
		RULE_var_value = 26, RULE_number = 27, RULE_dec = 28, RULE_var = 29, RULE_var_array = 30, 
		RULE_var_function = 31, RULE_array = 32, RULE_array_var = 33, RULE_string = 34, 
		RULE_bool = 35, RULE_misc_var = 36, RULE_ret = 37, RULE_sys_data = 38;
	public static final String[] ruleNames = {
		"program", "functions", "function_user", "function_init", "function_onTrans", 
		"function_onMes", "function_onBlock", "function_cleanup", "lines", "line", 
		"func_call", "predef_func", "sys_query", "sys_query_comp", "assign", "globals", 
		"global_assign", "global_var", "expr_math", "expr_comp", "stat_if", "stat_for", 
		"stat_while", "stat_switch", "func_params", "params", "var_value", "number", 
		"dec", "var", "var_array", "var_function", "array", "array_var", "string", 
		"bool", "misc_var", "ret", "sys_data"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'Agent'", "'{'", "'}'", "'function'", "'('", "')'", "'function init()'", 
		"'function onTrans()'", "'function onMes()'", "'function onBlock()'", 
		"'function cleanup()'", "';'", "'print'", "'exit'", "'query'", "','", 
		"'balance'", "'send'", "'mes'", "'email'", "'callURL'", "'reject_trans'", 
		"'()'", "'sysinfo'", "'getPrice'", "'trade'", "'dividends'", "'renew'", 
		"'nextRow'", "'getCol'", "'SELECT * FROM '", "' WHERE '", "'var'", "'this'", 
		"'.'", "'if'", "'else'", "'for'", "'while'", "'switch'", "'case'", "':'", 
		"'['", "']'", "'MATH'", "'CRYPTO'", "'return'", "'SYS.BLOCK.'", "'SYS.MES.'", 
		"'SYS.TRANS.'", "'SYS.COM.'", null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, "'!'", "'true'", "'false'", 
		null, null, null, "'='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, "SYS_DATA_COM", "SYS_DATA_BLOCK", "SYS_DATA_MES", 
		"SYS_DATA_TRANS", "BREAK", "STR_FUNC", "MATH_FUNC", "ARRAY_FUNC", "CRYPTO_FUNC", 
		"MATH_OP", "ASSIGN_OP", "COMP_OP", "BITWISE_OP", "LOGIC_OP", "INCDEC_OP", 
		"NOT_OP", "TRUE", "FALSE", "VAR", "CHAR_SEQ", "INT", "EQUAL", "COL_NAME", 
		"DB_STR", "DB_COMP", "TAB_NAME", "WS"
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
	public String getGrammarFileName() { return "test.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public testParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public FunctionsContext functions() {
			return getRuleContext(FunctionsContext.class,0);
		}
		public GlobalsContext globals() {
			return getRuleContext(GlobalsContext.class,0);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			match(T__0);
			setState(79);
			match(T__1);
			setState(81);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__32) {
				{
				setState(80);
				globals();
				}
			}

			setState(83);
			functions();
			setState(84);
			match(T__2);
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

	public static class FunctionsContext extends ParserRuleContext {
		public List<Function_userContext> function_user() {
			return getRuleContexts(Function_userContext.class);
		}
		public Function_userContext function_user(int i) {
			return getRuleContext(Function_userContext.class,i);
		}
		public Function_initContext function_init() {
			return getRuleContext(Function_initContext.class,0);
		}
		public Function_onTransContext function_onTrans() {
			return getRuleContext(Function_onTransContext.class,0);
		}
		public Function_onMesContext function_onMes() {
			return getRuleContext(Function_onMesContext.class,0);
		}
		public Function_onBlockContext function_onBlock() {
			return getRuleContext(Function_onBlockContext.class,0);
		}
		public Function_cleanupContext function_cleanup() {
			return getRuleContext(Function_cleanupContext.class,0);
		}
		public FunctionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functions; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitFunctions(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionsContext functions() throws RecognitionException {
		FunctionsContext _localctx = new FunctionsContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_functions);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(86);
				function_user();
				}
				}
				setState(91);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(93);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(92);
				function_init();
				}
			}

			setState(96);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(95);
				function_onTrans();
				}
			}

			setState(99);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8) {
				{
				setState(98);
				function_onMes();
				}
			}

			setState(102);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__9) {
				{
				setState(101);
				function_onBlock();
				}
			}

			setState(105);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(104);
				function_cleanup();
				}
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

	public static class Function_userContext extends ParserRuleContext {
		public TerminalNode VAR() { return getToken(testParser.VAR, 0); }
		public LinesContext lines() {
			return getRuleContext(LinesContext.class,0);
		}
		public Func_paramsContext func_params() {
			return getRuleContext(Func_paramsContext.class,0);
		}
		public RetContext ret() {
			return getRuleContext(RetContext.class,0);
		}
		public Function_userContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_user; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitFunction_user(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Function_userContext function_user() throws RecognitionException {
		Function_userContext _localctx = new Function_userContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_function_user);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			match(T__3);
			setState(108);
			match(VAR);
			setState(109);
			match(T__4);
			setState(111);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==VAR) {
				{
				setState(110);
				func_params();
				}
			}

			setState(113);
			match(T__5);
			setState(114);
			match(T__1);
			setState(115);
			lines();
			setState(117);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__46) {
				{
				setState(116);
				ret();
				}
			}

			setState(119);
			match(T__2);
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

	public static class Function_initContext extends ParserRuleContext {
		public LinesContext lines() {
			return getRuleContext(LinesContext.class,0);
		}
		public Function_initContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_init; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitFunction_init(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Function_initContext function_init() throws RecognitionException {
		Function_initContext _localctx = new Function_initContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_function_init);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			match(T__6);
			setState(122);
			match(T__1);
			setState(123);
			lines();
			setState(124);
			match(T__2);
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

	public static class Function_onTransContext extends ParserRuleContext {
		public LinesContext lines() {
			return getRuleContext(LinesContext.class,0);
		}
		public Function_onTransContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_onTrans; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitFunction_onTrans(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Function_onTransContext function_onTrans() throws RecognitionException {
		Function_onTransContext _localctx = new Function_onTransContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_function_onTrans);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(126);
			match(T__7);
			setState(127);
			match(T__1);
			setState(128);
			lines();
			setState(129);
			match(T__2);
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

	public static class Function_onMesContext extends ParserRuleContext {
		public LinesContext lines() {
			return getRuleContext(LinesContext.class,0);
		}
		public Function_onMesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_onMes; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitFunction_onMes(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Function_onMesContext function_onMes() throws RecognitionException {
		Function_onMesContext _localctx = new Function_onMesContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_function_onMes);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(131);
			match(T__8);
			setState(132);
			match(T__1);
			setState(133);
			lines();
			setState(134);
			match(T__2);
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

	public static class Function_onBlockContext extends ParserRuleContext {
		public LinesContext lines() {
			return getRuleContext(LinesContext.class,0);
		}
		public Function_onBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_onBlock; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitFunction_onBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Function_onBlockContext function_onBlock() throws RecognitionException {
		Function_onBlockContext _localctx = new Function_onBlockContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_function_onBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(136);
			match(T__9);
			setState(137);
			match(T__1);
			setState(138);
			lines();
			setState(139);
			match(T__2);
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

	public static class Function_cleanupContext extends ParserRuleContext {
		public LinesContext lines() {
			return getRuleContext(LinesContext.class,0);
		}
		public Function_cleanupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_cleanup; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitFunction_cleanup(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Function_cleanupContext function_cleanup() throws RecognitionException {
		Function_cleanupContext _localctx = new Function_cleanupContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_function_cleanup);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			match(T__10);
			setState(142);
			match(T__1);
			setState(143);
			lines();
			setState(144);
			match(T__2);
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

	public static class LinesContext extends ParserRuleContext {
		public List<LineContext> line() {
			return getRuleContexts(LineContext.class);
		}
		public LineContext line(int i) {
			return getRuleContext(LineContext.class,i);
		}
		public LinesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lines; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitLines(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LinesContext lines() throws RecognitionException {
		LinesContext _localctx = new LinesContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_lines);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 13)) & ~0x3f) == 0 && ((1L << (_la - 13)) & ((1L << (T__12 - 13)) | (1L << (T__13 - 13)) | (1L << (T__14 - 13)) | (1L << (T__16 - 13)) | (1L << (T__17 - 13)) | (1L << (T__18 - 13)) | (1L << (T__19 - 13)) | (1L << (T__20 - 13)) | (1L << (T__21 - 13)) | (1L << (T__23 - 13)) | (1L << (T__24 - 13)) | (1L << (T__25 - 13)) | (1L << (T__26 - 13)) | (1L << (T__27 - 13)) | (1L << (T__28 - 13)) | (1L << (T__29 - 13)) | (1L << (T__33 - 13)) | (1L << (T__35 - 13)) | (1L << (T__37 - 13)) | (1L << (T__38 - 13)) | (1L << (T__39 - 13)) | (1L << (VAR - 13)))) != 0)) {
				{
				{
				setState(146);
				line();
				}
				}
				setState(151);
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

	public static class LineContext extends ParserRuleContext {
		public AssignContext assign() {
			return getRuleContext(AssignContext.class,0);
		}
		public Func_callContext func_call() {
			return getRuleContext(Func_callContext.class,0);
		}
		public Stat_ifContext stat_if() {
			return getRuleContext(Stat_ifContext.class,0);
		}
		public Stat_forContext stat_for() {
			return getRuleContext(Stat_forContext.class,0);
		}
		public Stat_whileContext stat_while() {
			return getRuleContext(Stat_whileContext.class,0);
		}
		public Stat_switchContext stat_switch() {
			return getRuleContext(Stat_switchContext.class,0);
		}
		public LineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_line; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitLine(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LineContext line() throws RecognitionException {
		LineContext _localctx = new LineContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_line);
		try {
			setState(162);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(152);
				assign();
				setState(153);
				match(T__11);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(155);
				func_call();
				setState(156);
				match(T__11);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(158);
				stat_if();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(159);
				stat_for();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(160);
				stat_while();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(161);
				stat_switch();
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

	public static class Func_callContext extends ParserRuleContext {
		public Func_callContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func_call; }
	 
		public Func_callContext() { }
		public void copyFrom(Func_callContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class F_predefContext extends Func_callContext {
		public Predef_funcContext predef_func() {
			return getRuleContext(Predef_funcContext.class,0);
		}
		public F_predefContext(Func_callContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitF_predef(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class F_userContext extends Func_callContext {
		public TerminalNode VAR() { return getToken(testParser.VAR, 0); }
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
		}
		public F_userContext(Func_callContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitF_user(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Func_callContext func_call() throws RecognitionException {
		Func_callContext _localctx = new Func_callContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_func_call);
		try {
			setState(170);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__12:
			case T__13:
			case T__14:
			case T__16:
			case T__17:
			case T__18:
			case T__19:
			case T__20:
			case T__21:
			case T__23:
			case T__24:
			case T__25:
			case T__26:
			case T__27:
			case T__28:
			case T__29:
				_localctx = new F_predefContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(164);
				predef_func();
				}
				break;
			case VAR:
				_localctx = new F_userContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(165);
				match(VAR);
				setState(166);
				match(T__4);
				setState(167);
				params();
				setState(168);
				match(T__5);
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

	public static class Predef_funcContext extends ParserRuleContext {
		public Predef_funcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predef_func; }
	 
		public Predef_funcContext() { }
		public void copyFrom(Predef_funcContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class F_get_colContext extends Predef_funcContext {
		public List<Expr_mathContext> expr_math() {
			return getRuleContexts(Expr_mathContext.class);
		}
		public Expr_mathContext expr_math(int i) {
			return getRuleContext(Expr_mathContext.class,i);
		}
		public F_get_colContext(Predef_funcContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitF_get_col(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class F_sysinfoContext extends Predef_funcContext {
		public Sys_queryContext sys_query() {
			return getRuleContext(Sys_queryContext.class,0);
		}
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public F_sysinfoContext(Predef_funcContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitF_sysinfo(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class F_printContext extends Predef_funcContext {
		public Expr_mathContext expr_math() {
			return getRuleContext(Expr_mathContext.class,0);
		}
		public F_printContext(Predef_funcContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitF_print(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class F_tradeContext extends Predef_funcContext {
		public List<Expr_mathContext> expr_math() {
			return getRuleContexts(Expr_mathContext.class);
		}
		public Expr_mathContext expr_math(int i) {
			return getRuleContext(Expr_mathContext.class,i);
		}
		public F_tradeContext(Predef_funcContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitF_trade(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class F_queryContext extends Predef_funcContext {
		public List<Expr_mathContext> expr_math() {
			return getRuleContexts(Expr_mathContext.class);
		}
		public Expr_mathContext expr_math(int i) {
			return getRuleContext(Expr_mathContext.class,i);
		}
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public F_queryContext(Predef_funcContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitF_query(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class F_mesContext extends Predef_funcContext {
		public List<Expr_mathContext> expr_math() {
			return getRuleContexts(Expr_mathContext.class);
		}
		public Expr_mathContext expr_math(int i) {
			return getRuleContext(Expr_mathContext.class,i);
		}
		public F_mesContext(Predef_funcContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitF_mes(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class F_get_priceContext extends Predef_funcContext {
		public List<Expr_mathContext> expr_math() {
			return getRuleContexts(Expr_mathContext.class);
		}
		public Expr_mathContext expr_math(int i) {
			return getRuleContext(Expr_mathContext.class,i);
		}
		public F_get_priceContext(Predef_funcContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitF_get_price(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class F_exitContext extends Predef_funcContext {
		public Expr_mathContext expr_math() {
			return getRuleContext(Expr_mathContext.class,0);
		}
		public F_exitContext(Predef_funcContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitF_exit(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class F_next_rowContext extends Predef_funcContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public F_next_rowContext(Predef_funcContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitF_next_row(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class F_dividendsContext extends Predef_funcContext {
		public Expr_mathContext expr_math() {
			return getRuleContext(Expr_mathContext.class,0);
		}
		public F_dividendsContext(Predef_funcContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitF_dividends(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class F_emailContext extends Predef_funcContext {
		public List<Expr_mathContext> expr_math() {
			return getRuleContexts(Expr_mathContext.class);
		}
		public Expr_mathContext expr_math(int i) {
			return getRuleContext(Expr_mathContext.class,i);
		}
		public F_emailContext(Predef_funcContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitF_email(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class F_reject_transContext extends Predef_funcContext {
		public F_reject_transContext(Predef_funcContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitF_reject_trans(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class F_renewContext extends Predef_funcContext {
		public Expr_mathContext expr_math() {
			return getRuleContext(Expr_mathContext.class,0);
		}
		public F_renewContext(Predef_funcContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitF_renew(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class F_urlContext extends Predef_funcContext {
		public Expr_mathContext expr_math() {
			return getRuleContext(Expr_mathContext.class,0);
		}
		public F_urlContext(Predef_funcContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitF_url(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class F_balanceContext extends Predef_funcContext {
		public List<Expr_mathContext> expr_math() {
			return getRuleContexts(Expr_mathContext.class);
		}
		public Expr_mathContext expr_math(int i) {
			return getRuleContext(Expr_mathContext.class,i);
		}
		public F_balanceContext(Predef_funcContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitF_balance(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class F_sendContext extends Predef_funcContext {
		public List<Expr_mathContext> expr_math() {
			return getRuleContexts(Expr_mathContext.class);
		}
		public Expr_mathContext expr_math(int i) {
			return getRuleContext(Expr_mathContext.class,i);
		}
		public F_sendContext(Predef_funcContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitF_send(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Predef_funcContext predef_func() throws RecognitionException {
		Predef_funcContext _localctx = new Predef_funcContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_predef_func);
		try {
			setState(281);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__12:
				_localctx = new F_printContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(172);
				match(T__12);
				setState(173);
				match(T__4);
				setState(174);
				expr_math(0);
				setState(175);
				match(T__5);
				}
				break;
			case T__13:
				_localctx = new F_exitContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(177);
				match(T__13);
				setState(178);
				match(T__4);
				setState(179);
				expr_math(0);
				setState(180);
				match(T__5);
				}
				break;
			case T__14:
				_localctx = new F_queryContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(182);
				match(T__14);
				setState(183);
				match(T__4);
				setState(184);
				expr_math(0);
				setState(185);
				match(T__15);
				setState(186);
				var();
				setState(187);
				match(T__15);
				setState(188);
				expr_math(0);
				setState(189);
				match(T__5);
				}
				break;
			case T__16:
				_localctx = new F_balanceContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(191);
				match(T__16);
				setState(192);
				match(T__4);
				setState(193);
				expr_math(0);
				setState(194);
				match(T__15);
				setState(195);
				expr_math(0);
				setState(196);
				match(T__5);
				}
				break;
			case T__17:
				_localctx = new F_sendContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(198);
				match(T__17);
				setState(199);
				match(T__4);
				setState(200);
				expr_math(0);
				setState(201);
				match(T__15);
				setState(202);
				expr_math(0);
				setState(203);
				match(T__15);
				setState(204);
				expr_math(0);
				setState(205);
				match(T__15);
				setState(206);
				expr_math(0);
				setState(207);
				match(T__5);
				}
				break;
			case T__18:
				_localctx = new F_mesContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(209);
				match(T__18);
				setState(210);
				match(T__4);
				setState(211);
				expr_math(0);
				setState(212);
				match(T__15);
				setState(213);
				expr_math(0);
				setState(214);
				match(T__15);
				setState(215);
				expr_math(0);
				setState(216);
				match(T__5);
				}
				break;
			case T__19:
				_localctx = new F_emailContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(218);
				match(T__19);
				setState(219);
				match(T__4);
				setState(220);
				expr_math(0);
				setState(221);
				match(T__15);
				setState(222);
				expr_math(0);
				setState(223);
				match(T__15);
				setState(224);
				expr_math(0);
				setState(225);
				match(T__5);
				}
				break;
			case T__20:
				_localctx = new F_urlContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(227);
				match(T__20);
				setState(228);
				match(T__4);
				setState(229);
				expr_math(0);
				setState(230);
				match(T__5);
				}
				break;
			case T__21:
				_localctx = new F_reject_transContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(232);
				match(T__21);
				setState(233);
				match(T__22);
				}
				break;
			case T__23:
				_localctx = new F_sysinfoContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(234);
				match(T__23);
				setState(235);
				match(T__4);
				setState(236);
				sys_query();
				setState(237);
				match(T__15);
				setState(238);
				var();
				setState(239);
				match(T__5);
				}
				break;
			case T__24:
				_localctx = new F_get_priceContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(241);
				match(T__24);
				setState(242);
				match(T__4);
				setState(243);
				expr_math(0);
				setState(244);
				match(T__15);
				setState(245);
				expr_math(0);
				setState(246);
				match(T__5);
				}
				break;
			case T__25:
				_localctx = new F_tradeContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(248);
				match(T__25);
				setState(249);
				match(T__4);
				setState(250);
				expr_math(0);
				setState(251);
				match(T__15);
				setState(252);
				expr_math(0);
				setState(253);
				match(T__15);
				setState(254);
				expr_math(0);
				setState(255);
				match(T__15);
				setState(256);
				expr_math(0);
				setState(257);
				match(T__5);
				}
				break;
			case T__26:
				_localctx = new F_dividendsContext(_localctx);
				enterOuterAlt(_localctx, 13);
				{
				setState(259);
				match(T__26);
				setState(260);
				match(T__4);
				setState(261);
				expr_math(0);
				setState(262);
				match(T__5);
				}
				break;
			case T__27:
				_localctx = new F_renewContext(_localctx);
				enterOuterAlt(_localctx, 14);
				{
				setState(264);
				match(T__27);
				setState(265);
				match(T__4);
				setState(266);
				expr_math(0);
				setState(267);
				match(T__5);
				}
				break;
			case T__28:
				_localctx = new F_next_rowContext(_localctx);
				enterOuterAlt(_localctx, 15);
				{
				setState(269);
				match(T__28);
				setState(270);
				match(T__4);
				setState(271);
				var();
				setState(272);
				match(T__5);
				}
				break;
			case T__29:
				_localctx = new F_get_colContext(_localctx);
				enterOuterAlt(_localctx, 16);
				{
				setState(274);
				match(T__29);
				setState(275);
				match(T__4);
				setState(276);
				expr_math(0);
				setState(277);
				match(T__15);
				setState(278);
				expr_math(0);
				setState(279);
				match(T__5);
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

	public static class Sys_queryContext extends ParserRuleContext {
		public TerminalNode TAB_NAME() { return getToken(testParser.TAB_NAME, 0); }
		public List<Sys_query_compContext> sys_query_comp() {
			return getRuleContexts(Sys_query_compContext.class);
		}
		public Sys_query_compContext sys_query_comp(int i) {
			return getRuleContext(Sys_query_compContext.class,i);
		}
		public Sys_queryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sys_query; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitSys_query(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Sys_queryContext sys_query() throws RecognitionException {
		Sys_queryContext _localctx = new Sys_queryContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_sys_query);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(283);
			match(T__30);
			setState(284);
			match(TAB_NAME);
			setState(285);
			match(T__31);
			setState(287); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(286);
				sys_query_comp();
				}
				}
				setState(289); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__4 || _la==COL_NAME );
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

	public static class Sys_query_compContext extends ParserRuleContext {
		public TerminalNode COL_NAME() { return getToken(testParser.COL_NAME, 0); }
		public TerminalNode DB_COMP() { return getToken(testParser.DB_COMP, 0); }
		public TerminalNode DB_STR() { return getToken(testParser.DB_STR, 0); }
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public Sys_query_compContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sys_query_comp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitSys_query_comp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Sys_query_compContext sys_query_comp() throws RecognitionException {
		Sys_query_compContext _localctx = new Sys_query_compContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_sys_query_comp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(292);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(291);
				match(T__4);
				}
			}

			setState(294);
			match(COL_NAME);
			setState(295);
			match(DB_COMP);
			setState(298);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DB_STR:
				{
				setState(296);
				match(DB_STR);
				}
				break;
			case INT:
				{
				setState(297);
				number();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(301);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(300);
				match(T__5);
				}
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

	public static class AssignContext extends ParserRuleContext {
		public AssignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign; }
	 
		public AssignContext() { }
		public void copyFrom(AssignContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Assign_arrayContext extends AssignContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public TerminalNode ASSIGN_OP() { return getToken(testParser.ASSIGN_OP, 0); }
		public ArrayContext array() {
			return getRuleContext(ArrayContext.class,0);
		}
		public Assign_arrayContext(AssignContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitAssign_array(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Assign_exprContext extends AssignContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public TerminalNode ASSIGN_OP() { return getToken(testParser.ASSIGN_OP, 0); }
		public Expr_mathContext expr_math() {
			return getRuleContext(Expr_mathContext.class,0);
		}
		public Assign_exprContext(AssignContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitAssign_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Assign_inc_decContext extends AssignContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public TerminalNode INCDEC_OP() { return getToken(testParser.INCDEC_OP, 0); }
		public Assign_inc_decContext(AssignContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitAssign_inc_dec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignContext assign() throws RecognitionException {
		AssignContext _localctx = new AssignContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_assign);
		try {
			setState(314);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				_localctx = new Assign_exprContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(303);
				var();
				setState(304);
				match(ASSIGN_OP);
				setState(305);
				expr_math(0);
				}
				break;
			case 2:
				_localctx = new Assign_inc_decContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(307);
				var();
				setState(308);
				match(INCDEC_OP);
				}
				break;
			case 3:
				_localctx = new Assign_arrayContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(310);
				var();
				setState(311);
				match(ASSIGN_OP);
				setState(312);
				array();
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

	public static class GlobalsContext extends ParserRuleContext {
		public List<Global_assignContext> global_assign() {
			return getRuleContexts(Global_assignContext.class);
		}
		public Global_assignContext global_assign(int i) {
			return getRuleContext(Global_assignContext.class,i);
		}
		public GlobalsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_globals; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitGlobals(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GlobalsContext globals() throws RecognitionException {
		GlobalsContext _localctx = new GlobalsContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_globals);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(317); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(316);
				global_assign();
				}
				}
				setState(319); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__32 );
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

	public static class Global_assignContext extends ParserRuleContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public TerminalNode ASSIGN_OP() { return getToken(testParser.ASSIGN_OP, 0); }
		public Var_valueContext var_value() {
			return getRuleContext(Var_valueContext.class,0);
		}
		public Global_assignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_global_assign; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitGlobal_assign(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Global_assignContext global_assign() throws RecognitionException {
		Global_assignContext _localctx = new Global_assignContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_global_assign);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(321);
			match(T__32);
			setState(322);
			var();
			setState(323);
			match(ASSIGN_OP);
			setState(324);
			var_value();
			setState(325);
			match(T__11);
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

	public static class Global_varContext extends ParserRuleContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public Global_varContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_global_var; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitGlobal_var(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Global_varContext global_var() throws RecognitionException {
		Global_varContext _localctx = new Global_varContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_global_var);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(327);
			match(T__33);
			setState(328);
			match(T__34);
			setState(329);
			var();
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

	public static class Expr_mathContext extends ParserRuleContext {
		public Expr_mathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr_math; }
	 
		public Expr_mathContext() { }
		public void copyFrom(Expr_mathContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Expr_funcContext extends Expr_mathContext {
		public Func_callContext func_call() {
			return getRuleContext(Func_callContext.class,0);
		}
		public Expr_funcContext(Expr_mathContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitExpr_func(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_numeContext extends Expr_mathContext {
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public Expr_numeContext(Expr_mathContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitExpr_nume(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_paranContext extends Expr_mathContext {
		public Expr_mathContext expr_math() {
			return getRuleContext(Expr_mathContext.class,0);
		}
		public Expr_paranContext(Expr_mathContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitExpr_paran(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_stringContext extends Expr_mathContext {
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public Expr_stringContext(Expr_mathContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitExpr_string(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_sys_dataContext extends Expr_mathContext {
		public Sys_dataContext sys_data() {
			return getRuleContext(Sys_dataContext.class,0);
		}
		public Expr_sys_dataContext(Expr_mathContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitExpr_sys_data(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_op_exprContext extends Expr_mathContext {
		public List<Expr_mathContext> expr_math() {
			return getRuleContexts(Expr_mathContext.class);
		}
		public Expr_mathContext expr_math(int i) {
			return getRuleContext(Expr_mathContext.class,i);
		}
		public TerminalNode MATH_OP() { return getToken(testParser.MATH_OP, 0); }
		public Expr_op_exprContext(Expr_mathContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitExpr_op_expr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_varContext extends Expr_mathContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public Expr_varContext(Expr_mathContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitExpr_var(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_boolContext extends Expr_mathContext {
		public BoolContext bool() {
			return getRuleContext(BoolContext.class,0);
		}
		public Expr_boolContext(Expr_mathContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitExpr_bool(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_var_funcContext extends Expr_mathContext {
		public Var_functionContext var_function() {
			return getRuleContext(Var_functionContext.class,0);
		}
		public Expr_var_funcContext(Expr_mathContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitExpr_var_func(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expr_mathContext expr_math() throws RecognitionException {
		return expr_math(0);
	}

	private Expr_mathContext expr_math(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Expr_mathContext _localctx = new Expr_mathContext(_ctx, _parentState);
		Expr_mathContext _prevctx = _localctx;
		int _startState = 36;
		enterRecursionRule(_localctx, 36, RULE_expr_math, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(343);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				{
				_localctx = new Expr_paranContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(332);
				match(T__4);
				setState(333);
				expr_math(0);
				setState(334);
				match(T__5);
				}
				break;
			case 2:
				{
				_localctx = new Expr_boolContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(336);
				bool();
				}
				break;
			case 3:
				{
				_localctx = new Expr_funcContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(337);
				func_call();
				}
				break;
			case 4:
				{
				_localctx = new Expr_numeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(338);
				number();
				}
				break;
			case 5:
				{
				_localctx = new Expr_varContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(339);
				var();
				}
				break;
			case 6:
				{
				_localctx = new Expr_stringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(340);
				string();
				}
				break;
			case 7:
				{
				_localctx = new Expr_var_funcContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(341);
				var_function();
				}
				break;
			case 8:
				{
				_localctx = new Expr_sys_dataContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(342);
				sys_data();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(350);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Expr_op_exprContext(new Expr_mathContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_expr_math);
					setState(345);
					if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
					setState(346);
					match(MATH_OP);
					setState(347);
					expr_math(7);
					}
					} 
				}
				setState(352);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Expr_compContext extends ParserRuleContext {
		public Expr_compContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr_comp; }
	 
		public Expr_compContext() { }
		public void copyFrom(Expr_compContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Expr_comp_boolContext extends Expr_compContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public Expr_comp_boolContext(Expr_compContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitExpr_comp_bool(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_comp_logicContext extends Expr_compContext {
		public List<Expr_compContext> expr_comp() {
			return getRuleContexts(Expr_compContext.class);
		}
		public Expr_compContext expr_comp(int i) {
			return getRuleContext(Expr_compContext.class,i);
		}
		public TerminalNode LOGIC_OP() { return getToken(testParser.LOGIC_OP, 0); }
		public Expr_comp_logicContext(Expr_compContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitExpr_comp_logic(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_comp_opContext extends Expr_compContext {
		public List<Expr_mathContext> expr_math() {
			return getRuleContexts(Expr_mathContext.class);
		}
		public Expr_mathContext expr_math(int i) {
			return getRuleContext(Expr_mathContext.class,i);
		}
		public TerminalNode COMP_OP() { return getToken(testParser.COMP_OP, 0); }
		public Expr_comp_opContext(Expr_compContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitExpr_comp_op(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Expr_comp_paranContext extends Expr_compContext {
		public Expr_compContext expr_comp() {
			return getRuleContext(Expr_compContext.class,0);
		}
		public Expr_comp_paranContext(Expr_compContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitExpr_comp_paran(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expr_compContext expr_comp() throws RecognitionException {
		return expr_comp(0);
	}

	private Expr_compContext expr_comp(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Expr_compContext _localctx = new Expr_compContext(_ctx, _parentState);
		Expr_compContext _prevctx = _localctx;
		int _startState = 38;
		enterRecursionRule(_localctx, 38, RULE_expr_comp, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(363);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				{
				_localctx = new Expr_comp_paranContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(354);
				match(T__4);
				setState(355);
				expr_comp(0);
				setState(356);
				match(T__5);
				}
				break;
			case 2:
				{
				_localctx = new Expr_comp_opContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(358);
				expr_math(0);
				setState(359);
				match(COMP_OP);
				setState(360);
				expr_math(0);
				}
				break;
			case 3:
				{
				_localctx = new Expr_comp_boolContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(362);
				var();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(370);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Expr_comp_logicContext(new Expr_compContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_expr_comp);
					setState(365);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(366);
					match(LOGIC_OP);
					setState(367);
					expr_comp(3);
					}
					} 
				}
				setState(372);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Stat_ifContext extends ParserRuleContext {
		public Expr_compContext expr_comp() {
			return getRuleContext(Expr_compContext.class,0);
		}
		public List<LinesContext> lines() {
			return getRuleContexts(LinesContext.class);
		}
		public LinesContext lines(int i) {
			return getRuleContext(LinesContext.class,i);
		}
		public List<LineContext> line() {
			return getRuleContexts(LineContext.class);
		}
		public LineContext line(int i) {
			return getRuleContext(LineContext.class,i);
		}
		public Stat_ifContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stat_if; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitStat_if(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Stat_ifContext stat_if() throws RecognitionException {
		Stat_ifContext _localctx = new Stat_ifContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_stat_if);
		try {
			setState(419);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(373);
				match(T__35);
				setState(374);
				match(T__4);
				setState(375);
				expr_comp(0);
				setState(376);
				match(T__5);
				setState(377);
				match(T__1);
				setState(378);
				lines();
				setState(379);
				match(T__2);
				setState(385);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
				case 1:
					{
					setState(380);
					match(T__36);
					setState(381);
					match(T__1);
					setState(382);
					lines();
					setState(383);
					match(T__2);
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(387);
				match(T__35);
				setState(388);
				match(T__4);
				setState(389);
				expr_comp(0);
				setState(390);
				match(T__5);
				setState(391);
				match(T__1);
				setState(392);
				lines();
				setState(393);
				match(T__2);
				setState(396);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
				case 1:
					{
					setState(394);
					match(T__36);
					setState(395);
					line();
					}
					break;
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(398);
				match(T__35);
				setState(399);
				match(T__4);
				setState(400);
				expr_comp(0);
				setState(401);
				match(T__5);
				setState(402);
				line();
				setState(408);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
				case 1:
					{
					setState(403);
					match(T__36);
					setState(404);
					match(T__1);
					setState(405);
					lines();
					setState(406);
					match(T__2);
					}
					break;
				}
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(410);
				match(T__35);
				setState(411);
				match(T__4);
				setState(412);
				expr_comp(0);
				setState(413);
				match(T__5);
				setState(414);
				line();
				setState(417);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
				case 1:
					{
					setState(415);
					match(T__36);
					setState(416);
					line();
					}
					break;
				}
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

	public static class Stat_forContext extends ParserRuleContext {
		public AssignContext assign() {
			return getRuleContext(AssignContext.class,0);
		}
		public Expr_compContext expr_comp() {
			return getRuleContext(Expr_compContext.class,0);
		}
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public TerminalNode INCDEC_OP() { return getToken(testParser.INCDEC_OP, 0); }
		public LineContext line() {
			return getRuleContext(LineContext.class,0);
		}
		public LinesContext lines() {
			return getRuleContext(LinesContext.class,0);
		}
		public Stat_forContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stat_for; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitStat_for(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Stat_forContext stat_for() throws RecognitionException {
		Stat_forContext _localctx = new Stat_forContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_stat_for);
		try {
			setState(445);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(421);
				match(T__37);
				setState(422);
				match(T__4);
				setState(423);
				assign();
				setState(424);
				match(T__11);
				setState(425);
				expr_comp(0);
				setState(426);
				match(T__11);
				setState(427);
				var();
				setState(428);
				match(INCDEC_OP);
				setState(429);
				match(T__5);
				setState(430);
				line();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(432);
				match(T__37);
				setState(433);
				match(T__4);
				setState(434);
				assign();
				setState(435);
				match(T__11);
				setState(436);
				expr_comp(0);
				setState(437);
				match(T__11);
				setState(438);
				var();
				setState(439);
				match(INCDEC_OP);
				setState(440);
				match(T__5);
				setState(441);
				match(T__1);
				setState(442);
				lines();
				setState(443);
				match(T__2);
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

	public static class Stat_whileContext extends ParserRuleContext {
		public Expr_compContext expr_comp() {
			return getRuleContext(Expr_compContext.class,0);
		}
		public LineContext line() {
			return getRuleContext(LineContext.class,0);
		}
		public LinesContext lines() {
			return getRuleContext(LinesContext.class,0);
		}
		public Stat_whileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stat_while; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitStat_while(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Stat_whileContext stat_while() throws RecognitionException {
		Stat_whileContext _localctx = new Stat_whileContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_stat_while);
		try {
			setState(461);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(447);
				match(T__38);
				setState(448);
				match(T__4);
				setState(449);
				expr_comp(0);
				setState(450);
				match(T__5);
				setState(451);
				line();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(453);
				match(T__38);
				setState(454);
				match(T__4);
				setState(455);
				expr_comp(0);
				setState(456);
				match(T__5);
				setState(457);
				match(T__1);
				setState(458);
				lines();
				setState(459);
				match(T__2);
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

	public static class Stat_switchContext extends ParserRuleContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public List<LinesContext> lines() {
			return getRuleContexts(LinesContext.class);
		}
		public LinesContext lines(int i) {
			return getRuleContext(LinesContext.class,i);
		}
		public List<TerminalNode> BREAK() { return getTokens(testParser.BREAK); }
		public TerminalNode BREAK(int i) {
			return getToken(testParser.BREAK, i);
		}
		public List<Misc_varContext> misc_var() {
			return getRuleContexts(Misc_varContext.class);
		}
		public Misc_varContext misc_var(int i) {
			return getRuleContext(Misc_varContext.class,i);
		}
		public Stat_switchContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stat_switch; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitStat_switch(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Stat_switchContext stat_switch() throws RecognitionException {
		Stat_switchContext _localctx = new Stat_switchContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_stat_switch);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(463);
			match(T__39);
			setState(464);
			match(T__4);
			setState(465);
			var();
			setState(466);
			match(T__5);
			setState(467);
			match(T__1);
			setState(474); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(468);
				match(T__40);
				{
				setState(469);
				misc_var();
				}
				setState(470);
				match(T__41);
				setState(471);
				lines();
				setState(472);
				match(BREAK);
				}
				}
				setState(476); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__40 );
			setState(478);
			match(T__2);
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

	public static class Func_paramsContext extends ParserRuleContext {
		public List<TerminalNode> VAR() { return getTokens(testParser.VAR); }
		public TerminalNode VAR(int i) {
			return getToken(testParser.VAR, i);
		}
		public Func_paramsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func_params; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitFunc_params(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Func_paramsContext func_params() throws RecognitionException {
		Func_paramsContext _localctx = new Func_paramsContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_func_params);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(480);
			match(VAR);
			setState(485);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__15) {
				{
				{
				setState(481);
				match(T__15);
				setState(482);
				match(VAR);
				}
				}
				setState(487);
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

	public static class ParamsContext extends ParserRuleContext {
		public List<Expr_mathContext> expr_math() {
			return getRuleContexts(Expr_mathContext.class);
		}
		public Expr_mathContext expr_math(int i) {
			return getRuleContext(Expr_mathContext.class,i);
		}
		public ParamsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_params; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitParams(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamsContext params() throws RecognitionException {
		ParamsContext _localctx = new ParamsContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_params);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(488);
			expr_math(0);
			setState(493);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__15) {
				{
				{
				setState(489);
				match(T__15);
				setState(490);
				expr_math(0);
				}
				}
				setState(495);
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

	public static class Var_valueContext extends ParserRuleContext {
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public Var_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_value; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitVar_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_valueContext var_value() throws RecognitionException {
		Var_valueContext _localctx = new Var_valueContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_var_value);
		try {
			setState(498);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(496);
				number();
				}
				break;
			case CHAR_SEQ:
				enterOuterAlt(_localctx, 2);
				{
				setState(497);
				string();
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

	public static class NumberContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(testParser.INT, 0); }
		public DecContext dec() {
			return getRuleContext(DecContext.class,0);
		}
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitNumber(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_number);
		try {
			setState(502);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(500);
				match(INT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(501);
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

	public static class DecContext extends ParserRuleContext {
		public List<TerminalNode> INT() { return getTokens(testParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(testParser.INT, i);
		}
		public DecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dec; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitDec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DecContext dec() throws RecognitionException {
		DecContext _localctx = new DecContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_dec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(504);
			match(INT);
			setState(505);
			match(T__34);
			setState(506);
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

	public static class VarContext extends ParserRuleContext {
		public Global_varContext global_var() {
			return getRuleContext(Global_varContext.class,0);
		}
		public Var_arrayContext var_array() {
			return getRuleContext(Var_arrayContext.class,0);
		}
		public TerminalNode VAR() { return getToken(testParser.VAR, 0); }
		public VarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarContext var() throws RecognitionException {
		VarContext _localctx = new VarContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_var);
		try {
			setState(511);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(508);
				global_var();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(509);
				var_array();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(510);
				match(VAR);
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

	public static class Var_arrayContext extends ParserRuleContext {
		public TerminalNode VAR() { return getToken(testParser.VAR, 0); }
		public Expr_mathContext expr_math() {
			return getRuleContext(Expr_mathContext.class,0);
		}
		public Var_arrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_array; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitVar_array(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_arrayContext var_array() throws RecognitionException {
		Var_arrayContext _localctx = new Var_arrayContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_var_array);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(513);
			match(VAR);
			setState(514);
			match(T__42);
			setState(515);
			expr_math(0);
			setState(516);
			match(T__43);
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

	public static class Var_functionContext extends ParserRuleContext {
		public Var_functionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_function; }
	 
		public Var_functionContext() { }
		public void copyFrom(Var_functionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Var_function_varContext extends Var_functionContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public TerminalNode STR_FUNC() { return getToken(testParser.STR_FUNC, 0); }
		public TerminalNode ARRAY_FUNC() { return getToken(testParser.ARRAY_FUNC, 0); }
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
		}
		public Var_function_varContext(Var_functionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitVar_function_var(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Var_function_cryptoContext extends Var_functionContext {
		public TerminalNode CRYPTO_FUNC() { return getToken(testParser.CRYPTO_FUNC, 0); }
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
		}
		public Var_function_cryptoContext(Var_functionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitVar_function_crypto(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Var_function_mathContext extends Var_functionContext {
		public TerminalNode MATH_FUNC() { return getToken(testParser.MATH_FUNC, 0); }
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
		}
		public Var_function_mathContext(Var_functionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitVar_function_math(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_functionContext var_function() throws RecognitionException {
		Var_functionContext _localctx = new Var_functionContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_var_function);
		int _la;
		try {
			setState(543);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__33:
			case VAR:
				_localctx = new Var_function_varContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(518);
				var();
				setState(519);
				match(T__34);
				setState(520);
				_la = _input.LA(1);
				if ( !(_la==STR_FUNC || _la==ARRAY_FUNC) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(521);
				match(T__4);
				setState(523);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__33) | (1L << T__44) | (1L << T__45) | (1L << T__47) | (1L << T__48) | (1L << T__49) | (1L << T__50))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (TRUE - 68)) | (1L << (FALSE - 68)) | (1L << (VAR - 68)) | (1L << (CHAR_SEQ - 68)) | (1L << (INT - 68)))) != 0)) {
					{
					setState(522);
					params();
					}
				}

				setState(525);
				match(T__5);
				}
				break;
			case T__44:
				_localctx = new Var_function_mathContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(527);
				match(T__44);
				setState(528);
				match(T__34);
				setState(529);
				match(MATH_FUNC);
				setState(530);
				match(T__4);
				setState(532);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__33) | (1L << T__44) | (1L << T__45) | (1L << T__47) | (1L << T__48) | (1L << T__49) | (1L << T__50))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (TRUE - 68)) | (1L << (FALSE - 68)) | (1L << (VAR - 68)) | (1L << (CHAR_SEQ - 68)) | (1L << (INT - 68)))) != 0)) {
					{
					setState(531);
					params();
					}
				}

				setState(534);
				match(T__5);
				}
				break;
			case T__45:
				_localctx = new Var_function_cryptoContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(535);
				match(T__45);
				setState(536);
				match(T__34);
				setState(537);
				match(CRYPTO_FUNC);
				setState(538);
				match(T__4);
				setState(540);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__33) | (1L << T__44) | (1L << T__45) | (1L << T__47) | (1L << T__48) | (1L << T__49) | (1L << T__50))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (TRUE - 68)) | (1L << (FALSE - 68)) | (1L << (VAR - 68)) | (1L << (CHAR_SEQ - 68)) | (1L << (INT - 68)))) != 0)) {
					{
					setState(539);
					params();
					}
				}

				setState(542);
				match(T__5);
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

	public static class ArrayContext extends ParserRuleContext {
		public List<Array_varContext> array_var() {
			return getRuleContexts(Array_varContext.class);
		}
		public Array_varContext array_var(int i) {
			return getRuleContext(Array_varContext.class,i);
		}
		public ArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_array; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitArray(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayContext array() throws RecognitionException {
		ArrayContext _localctx = new ArrayContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_array);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(545);
			array_var();
			setState(550);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__15) {
				{
				{
				setState(546);
				match(T__15);
				setState(547);
				array_var();
				}
				}
				setState(552);
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

	public static class Array_varContext extends ParserRuleContext {
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public Array_varContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_array_var; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitArray_var(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Array_varContext array_var() throws RecognitionException {
		Array_varContext _localctx = new Array_varContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_array_var);
		try {
			setState(555);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CHAR_SEQ:
				enterOuterAlt(_localctx, 1);
				{
				setState(553);
				string();
				}
				break;
			case INT:
				enterOuterAlt(_localctx, 2);
				{
				setState(554);
				number();
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

	public static class StringContext extends ParserRuleContext {
		public TerminalNode CHAR_SEQ() { return getToken(testParser.CHAR_SEQ, 0); }
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitString(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_string);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(557);
			match(CHAR_SEQ);
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

	public static class BoolContext extends ParserRuleContext {
		public TerminalNode TRUE() { return getToken(testParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(testParser.FALSE, 0); }
		public BoolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bool; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitBool(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BoolContext bool() throws RecognitionException {
		BoolContext _localctx = new BoolContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_bool);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(559);
			_la = _input.LA(1);
			if ( !(_la==TRUE || _la==FALSE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class Misc_varContext extends ParserRuleContext {
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public Func_callContext func_call() {
			return getRuleContext(Func_callContext.class,0);
		}
		public Misc_varContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_misc_var; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitMisc_var(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Misc_varContext misc_var() throws RecognitionException {
		Misc_varContext _localctx = new Misc_varContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_misc_var);
		try {
			setState(565);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(561);
				string();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(562);
				number();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(563);
				var();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(564);
				func_call();
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

	public static class RetContext extends ParserRuleContext {
		public Expr_mathContext expr_math() {
			return getRuleContext(Expr_mathContext.class,0);
		}
		public RetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ret; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitRet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RetContext ret() throws RecognitionException {
		RetContext _localctx = new RetContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_ret);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(567);
			match(T__46);
			setState(568);
			match(T__4);
			setState(570);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__33) | (1L << T__44) | (1L << T__45) | (1L << T__47) | (1L << T__48) | (1L << T__49) | (1L << T__50))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (TRUE - 68)) | (1L << (FALSE - 68)) | (1L << (VAR - 68)) | (1L << (CHAR_SEQ - 68)) | (1L << (INT - 68)))) != 0)) {
				{
				setState(569);
				expr_math(0);
				}
			}

			setState(572);
			match(T__5);
			setState(573);
			match(T__11);
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

	public static class Sys_dataContext extends ParserRuleContext {
		public TerminalNode SYS_DATA_BLOCK() { return getToken(testParser.SYS_DATA_BLOCK, 0); }
		public TerminalNode SYS_DATA_MES() { return getToken(testParser.SYS_DATA_MES, 0); }
		public TerminalNode SYS_DATA_TRANS() { return getToken(testParser.SYS_DATA_TRANS, 0); }
		public TerminalNode SYS_DATA_COM() { return getToken(testParser.SYS_DATA_COM, 0); }
		public Sys_dataContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sys_data; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof testVisitor ) return ((testVisitor<? extends T>)visitor).visitSys_data(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Sys_dataContext sys_data() throws RecognitionException {
		Sys_dataContext _localctx = new Sys_dataContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_sys_data);
		try {
			setState(583);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__47:
				enterOuterAlt(_localctx, 1);
				{
				setState(575);
				match(T__47);
				setState(576);
				match(SYS_DATA_BLOCK);
				}
				break;
			case T__48:
				enterOuterAlt(_localctx, 2);
				{
				setState(577);
				match(T__48);
				setState(578);
				match(SYS_DATA_MES);
				}
				break;
			case T__49:
				enterOuterAlt(_localctx, 3);
				{
				setState(579);
				match(T__49);
				setState(580);
				match(SYS_DATA_TRANS);
				}
				break;
			case T__50:
				enterOuterAlt(_localctx, 4);
				{
				setState(581);
				match(T__50);
				setState(582);
				match(SYS_DATA_COM);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 18:
			return expr_math_sempred((Expr_mathContext)_localctx, predIndex);
		case 19:
			return expr_comp_sempred((Expr_compContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_math_sempred(Expr_mathContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 6);
		}
		return true;
	}
	private boolean expr_comp_sempred(Expr_compContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3P\u024c\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\3\2\3\2\3\2\5\2T\n\2"+
		"\3\2\3\2\3\2\3\3\7\3Z\n\3\f\3\16\3]\13\3\3\3\5\3`\n\3\3\3\5\3c\n\3\3\3"+
		"\5\3f\n\3\3\3\5\3i\n\3\3\3\5\3l\n\3\3\4\3\4\3\4\3\4\5\4r\n\4\3\4\3\4\3"+
		"\4\3\4\5\4x\n\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3"+
		"\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\7\n\u0096\n"+
		"\n\f\n\16\n\u0099\13\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\5\13\u00a5\n\13\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u00ad\n\f\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\5\r\u011c\n\r\3\16\3\16\3\16\3\16\6\16\u0122\n\16\r\16\16\16\u0123"+
		"\3\17\5\17\u0127\n\17\3\17\3\17\3\17\3\17\5\17\u012d\n\17\3\17\5\17\u0130"+
		"\n\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u013d"+
		"\n\20\3\21\6\21\u0140\n\21\r\21\16\21\u0141\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3"+
		"\24\3\24\3\24\5\24\u015a\n\24\3\24\3\24\3\24\7\24\u015f\n\24\f\24\16\24"+
		"\u0162\13\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\5\25\u016e"+
		"\n\25\3\25\3\25\3\25\7\25\u0173\n\25\f\25\16\25\u0176\13\25\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\5\26\u0184\n\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\5\26\u018f\n\26\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\3\26\3\26\5\26\u019b\n\26\3\26\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\5\26\u01a4\n\26\5\26\u01a6\n\26\3\27\3\27\3\27\3\27\3"+
		"\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3"+
		"\27\3\27\3\27\3\27\3\27\3\27\5\27\u01c0\n\27\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30\u01d0\n\30\3\31\3\31"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\6\31\u01dd\n\31\r\31\16"+
		"\31\u01de\3\31\3\31\3\32\3\32\3\32\7\32\u01e6\n\32\f\32\16\32\u01e9\13"+
		"\32\3\33\3\33\3\33\7\33\u01ee\n\33\f\33\16\33\u01f1\13\33\3\34\3\34\5"+
		"\34\u01f5\n\34\3\35\3\35\5\35\u01f9\n\35\3\36\3\36\3\36\3\36\3\37\3\37"+
		"\3\37\5\37\u0202\n\37\3 \3 \3 \3 \3 \3!\3!\3!\3!\3!\5!\u020e\n!\3!\3!"+
		"\3!\3!\3!\3!\3!\5!\u0217\n!\3!\3!\3!\3!\3!\3!\5!\u021f\n!\3!\5!\u0222"+
		"\n!\3\"\3\"\3\"\7\"\u0227\n\"\f\"\16\"\u022a\13\"\3#\3#\5#\u022e\n#\3"+
		"$\3$\3%\3%\3&\3&\3&\3&\5&\u0238\n&\3\'\3\'\3\'\5\'\u023d\n\'\3\'\3\'\3"+
		"\'\3(\3(\3(\3(\3(\3(\3(\3(\5(\u024a\n(\3(\2\4&()\2\4\6\b\n\f\16\20\22"+
		"\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLN\2\4\4\2;;==\3\2FG"+
		"\2\u0273\2P\3\2\2\2\4[\3\2\2\2\6m\3\2\2\2\b{\3\2\2\2\n\u0080\3\2\2\2\f"+
		"\u0085\3\2\2\2\16\u008a\3\2\2\2\20\u008f\3\2\2\2\22\u0097\3\2\2\2\24\u00a4"+
		"\3\2\2\2\26\u00ac\3\2\2\2\30\u011b\3\2\2\2\32\u011d\3\2\2\2\34\u0126\3"+
		"\2\2\2\36\u013c\3\2\2\2 \u013f\3\2\2\2\"\u0143\3\2\2\2$\u0149\3\2\2\2"+
		"&\u0159\3\2\2\2(\u016d\3\2\2\2*\u01a5\3\2\2\2,\u01bf\3\2\2\2.\u01cf\3"+
		"\2\2\2\60\u01d1\3\2\2\2\62\u01e2\3\2\2\2\64\u01ea\3\2\2\2\66\u01f4\3\2"+
		"\2\28\u01f8\3\2\2\2:\u01fa\3\2\2\2<\u0201\3\2\2\2>\u0203\3\2\2\2@\u0221"+
		"\3\2\2\2B\u0223\3\2\2\2D\u022d\3\2\2\2F\u022f\3\2\2\2H\u0231\3\2\2\2J"+
		"\u0237\3\2\2\2L\u0239\3\2\2\2N\u0249\3\2\2\2PQ\7\3\2\2QS\7\4\2\2RT\5 "+
		"\21\2SR\3\2\2\2ST\3\2\2\2TU\3\2\2\2UV\5\4\3\2VW\7\5\2\2W\3\3\2\2\2XZ\5"+
		"\6\4\2YX\3\2\2\2Z]\3\2\2\2[Y\3\2\2\2[\\\3\2\2\2\\_\3\2\2\2][\3\2\2\2^"+
		"`\5\b\5\2_^\3\2\2\2_`\3\2\2\2`b\3\2\2\2ac\5\n\6\2ba\3\2\2\2bc\3\2\2\2"+
		"ce\3\2\2\2df\5\f\7\2ed\3\2\2\2ef\3\2\2\2fh\3\2\2\2gi\5\16\b\2hg\3\2\2"+
		"\2hi\3\2\2\2ik\3\2\2\2jl\5\20\t\2kj\3\2\2\2kl\3\2\2\2l\5\3\2\2\2mn\7\6"+
		"\2\2no\7H\2\2oq\7\7\2\2pr\5\62\32\2qp\3\2\2\2qr\3\2\2\2rs\3\2\2\2st\7"+
		"\b\2\2tu\7\4\2\2uw\5\22\n\2vx\5L\'\2wv\3\2\2\2wx\3\2\2\2xy\3\2\2\2yz\7"+
		"\5\2\2z\7\3\2\2\2{|\7\t\2\2|}\7\4\2\2}~\5\22\n\2~\177\7\5\2\2\177\t\3"+
		"\2\2\2\u0080\u0081\7\n\2\2\u0081\u0082\7\4\2\2\u0082\u0083\5\22\n\2\u0083"+
		"\u0084\7\5\2\2\u0084\13\3\2\2\2\u0085\u0086\7\13\2\2\u0086\u0087\7\4\2"+
		"\2\u0087\u0088\5\22\n\2\u0088\u0089\7\5\2\2\u0089\r\3\2\2\2\u008a\u008b"+
		"\7\f\2\2\u008b\u008c\7\4\2\2\u008c\u008d\5\22\n\2\u008d\u008e\7\5\2\2"+
		"\u008e\17\3\2\2\2\u008f\u0090\7\r\2\2\u0090\u0091\7\4\2\2\u0091\u0092"+
		"\5\22\n\2\u0092\u0093\7\5\2\2\u0093\21\3\2\2\2\u0094\u0096\5\24\13\2\u0095"+
		"\u0094\3\2\2\2\u0096\u0099\3\2\2\2\u0097\u0095\3\2\2\2\u0097\u0098\3\2"+
		"\2\2\u0098\23\3\2\2\2\u0099\u0097\3\2\2\2\u009a\u009b\5\36\20\2\u009b"+
		"\u009c\7\16\2\2\u009c\u00a5\3\2\2\2\u009d\u009e\5\26\f\2\u009e\u009f\7"+
		"\16\2\2\u009f\u00a5\3\2\2\2\u00a0\u00a5\5*\26\2\u00a1\u00a5\5,\27\2\u00a2"+
		"\u00a5\5.\30\2\u00a3\u00a5\5\60\31\2\u00a4\u009a\3\2\2\2\u00a4\u009d\3"+
		"\2\2\2\u00a4\u00a0\3\2\2\2\u00a4\u00a1\3\2\2\2\u00a4\u00a2\3\2\2\2\u00a4"+
		"\u00a3\3\2\2\2\u00a5\25\3\2\2\2\u00a6\u00ad\5\30\r\2\u00a7\u00a8\7H\2"+
		"\2\u00a8\u00a9\7\7\2\2\u00a9\u00aa\5\64\33\2\u00aa\u00ab\7\b\2\2\u00ab"+
		"\u00ad\3\2\2\2\u00ac\u00a6\3\2\2\2\u00ac\u00a7\3\2\2\2\u00ad\27\3\2\2"+
		"\2\u00ae\u00af\7\17\2\2\u00af\u00b0\7\7\2\2\u00b0\u00b1\5&\24\2\u00b1"+
		"\u00b2\7\b\2\2\u00b2\u011c\3\2\2\2\u00b3\u00b4\7\20\2\2\u00b4\u00b5\7"+
		"\7\2\2\u00b5\u00b6\5&\24\2\u00b6\u00b7\7\b\2\2\u00b7\u011c\3\2\2\2\u00b8"+
		"\u00b9\7\21\2\2\u00b9\u00ba\7\7\2\2\u00ba\u00bb\5&\24\2\u00bb\u00bc\7"+
		"\22\2\2\u00bc\u00bd\5<\37\2\u00bd\u00be\7\22\2\2\u00be\u00bf\5&\24\2\u00bf"+
		"\u00c0\7\b\2\2\u00c0\u011c\3\2\2\2\u00c1\u00c2\7\23\2\2\u00c2\u00c3\7"+
		"\7\2\2\u00c3\u00c4\5&\24\2\u00c4\u00c5\7\22\2\2\u00c5\u00c6\5&\24\2\u00c6"+
		"\u00c7\7\b\2\2\u00c7\u011c\3\2\2\2\u00c8\u00c9\7\24\2\2\u00c9\u00ca\7"+
		"\7\2\2\u00ca\u00cb\5&\24\2\u00cb\u00cc\7\22\2\2\u00cc\u00cd\5&\24\2\u00cd"+
		"\u00ce\7\22\2\2\u00ce\u00cf\5&\24\2\u00cf\u00d0\7\22\2\2\u00d0\u00d1\5"+
		"&\24\2\u00d1\u00d2\7\b\2\2\u00d2\u011c\3\2\2\2\u00d3\u00d4\7\25\2\2\u00d4"+
		"\u00d5\7\7\2\2\u00d5\u00d6\5&\24\2\u00d6\u00d7\7\22\2\2\u00d7\u00d8\5"+
		"&\24\2\u00d8\u00d9\7\22\2\2\u00d9\u00da\5&\24\2\u00da\u00db\7\b\2\2\u00db"+
		"\u011c\3\2\2\2\u00dc\u00dd\7\26\2\2\u00dd\u00de\7\7\2\2\u00de\u00df\5"+
		"&\24\2\u00df\u00e0\7\22\2\2\u00e0\u00e1\5&\24\2\u00e1\u00e2\7\22\2\2\u00e2"+
		"\u00e3\5&\24\2\u00e3\u00e4\7\b\2\2\u00e4\u011c\3\2\2\2\u00e5\u00e6\7\27"+
		"\2\2\u00e6\u00e7\7\7\2\2\u00e7\u00e8\5&\24\2\u00e8\u00e9\7\b\2\2\u00e9"+
		"\u011c\3\2\2\2\u00ea\u00eb\7\30\2\2\u00eb\u011c\7\31\2\2\u00ec\u00ed\7"+
		"\32\2\2\u00ed\u00ee\7\7\2\2\u00ee\u00ef\5\32\16\2\u00ef\u00f0\7\22\2\2"+
		"\u00f0\u00f1\5<\37\2\u00f1\u00f2\7\b\2\2\u00f2\u011c\3\2\2\2\u00f3\u00f4"+
		"\7\33\2\2\u00f4\u00f5\7\7\2\2\u00f5\u00f6\5&\24\2\u00f6\u00f7\7\22\2\2"+
		"\u00f7\u00f8\5&\24\2\u00f8\u00f9\7\b\2\2\u00f9\u011c\3\2\2\2\u00fa\u00fb"+
		"\7\34\2\2\u00fb\u00fc\7\7\2\2\u00fc\u00fd\5&\24\2\u00fd\u00fe\7\22\2\2"+
		"\u00fe\u00ff\5&\24\2\u00ff\u0100\7\22\2\2\u0100\u0101\5&\24\2\u0101\u0102"+
		"\7\22\2\2\u0102\u0103\5&\24\2\u0103\u0104\7\b\2\2\u0104\u011c\3\2\2\2"+
		"\u0105\u0106\7\35\2\2\u0106\u0107\7\7\2\2\u0107\u0108\5&\24\2\u0108\u0109"+
		"\7\b\2\2\u0109\u011c\3\2\2\2\u010a\u010b\7\36\2\2\u010b\u010c\7\7\2\2"+
		"\u010c\u010d\5&\24\2\u010d\u010e\7\b\2\2\u010e\u011c\3\2\2\2\u010f\u0110"+
		"\7\37\2\2\u0110\u0111\7\7\2\2\u0111\u0112\5<\37\2\u0112\u0113\7\b\2\2"+
		"\u0113\u011c\3\2\2\2\u0114\u0115\7 \2\2\u0115\u0116\7\7\2\2\u0116\u0117"+
		"\5&\24\2\u0117\u0118\7\22\2\2\u0118\u0119\5&\24\2\u0119\u011a\7\b\2\2"+
		"\u011a\u011c\3\2\2\2\u011b\u00ae\3\2\2\2\u011b\u00b3\3\2\2\2\u011b\u00b8"+
		"\3\2\2\2\u011b\u00c1\3\2\2\2\u011b\u00c8\3\2\2\2\u011b\u00d3\3\2\2\2\u011b"+
		"\u00dc\3\2\2\2\u011b\u00e5\3\2\2\2\u011b\u00ea\3\2\2\2\u011b\u00ec\3\2"+
		"\2\2\u011b\u00f3\3\2\2\2\u011b\u00fa\3\2\2\2\u011b\u0105\3\2\2\2\u011b"+
		"\u010a\3\2\2\2\u011b\u010f\3\2\2\2\u011b\u0114\3\2\2\2\u011c\31\3\2\2"+
		"\2\u011d\u011e\7!\2\2\u011e\u011f\7O\2\2\u011f\u0121\7\"\2\2\u0120\u0122"+
		"\5\34\17\2\u0121\u0120\3\2\2\2\u0122\u0123\3\2\2\2\u0123\u0121\3\2\2\2"+
		"\u0123\u0124\3\2\2\2\u0124\33\3\2\2\2\u0125\u0127\7\7\2\2\u0126\u0125"+
		"\3\2\2\2\u0126\u0127\3\2\2\2\u0127\u0128\3\2\2\2\u0128\u0129\7L\2\2\u0129"+
		"\u012c\7N\2\2\u012a\u012d\7M\2\2\u012b\u012d\58\35\2\u012c\u012a\3\2\2"+
		"\2\u012c\u012b\3\2\2\2\u012d\u012f\3\2\2\2\u012e\u0130\7\b\2\2\u012f\u012e"+
		"\3\2\2\2\u012f\u0130\3\2\2\2\u0130\35\3\2\2\2\u0131\u0132\5<\37\2\u0132"+
		"\u0133\7@\2\2\u0133\u0134\5&\24\2\u0134\u013d\3\2\2\2\u0135\u0136\5<\37"+
		"\2\u0136\u0137\7D\2\2\u0137\u013d\3\2\2\2\u0138\u0139\5<\37\2\u0139\u013a"+
		"\7@\2\2\u013a\u013b\5B\"\2\u013b\u013d\3\2\2\2\u013c\u0131\3\2\2\2\u013c"+
		"\u0135\3\2\2\2\u013c\u0138\3\2\2\2\u013d\37\3\2\2\2\u013e\u0140\5\"\22"+
		"\2\u013f\u013e\3\2\2\2\u0140\u0141\3\2\2\2\u0141\u013f\3\2\2\2\u0141\u0142"+
		"\3\2\2\2\u0142!\3\2\2\2\u0143\u0144\7#\2\2\u0144\u0145\5<\37\2\u0145\u0146"+
		"\7@\2\2\u0146\u0147\5\66\34\2\u0147\u0148\7\16\2\2\u0148#\3\2\2\2\u0149"+
		"\u014a\7$\2\2\u014a\u014b\7%\2\2\u014b\u014c\5<\37\2\u014c%\3\2\2\2\u014d"+
		"\u014e\b\24\1\2\u014e\u014f\7\7\2\2\u014f\u0150\5&\24\2\u0150\u0151\7"+
		"\b\2\2\u0151\u015a\3\2\2\2\u0152\u015a\5H%\2\u0153\u015a\5\26\f\2\u0154"+
		"\u015a\58\35\2\u0155\u015a\5<\37\2\u0156\u015a\5F$\2\u0157\u015a\5@!\2"+
		"\u0158\u015a\5N(\2\u0159\u014d\3\2\2\2\u0159\u0152\3\2\2\2\u0159\u0153"+
		"\3\2\2\2\u0159\u0154\3\2\2\2\u0159\u0155\3\2\2\2\u0159\u0156\3\2\2\2\u0159"+
		"\u0157\3\2\2\2\u0159\u0158\3\2\2\2\u015a\u0160\3\2\2\2\u015b\u015c\f\b"+
		"\2\2\u015c\u015d\7?\2\2\u015d\u015f\5&\24\t\u015e\u015b\3\2\2\2\u015f"+
		"\u0162\3\2\2\2\u0160\u015e\3\2\2\2\u0160\u0161\3\2\2\2\u0161\'\3\2\2\2"+
		"\u0162\u0160\3\2\2\2\u0163\u0164\b\25\1\2\u0164\u0165\7\7\2\2\u0165\u0166"+
		"\5(\25\2\u0166\u0167\7\b\2\2\u0167\u016e\3\2\2\2\u0168\u0169\5&\24\2\u0169"+
		"\u016a\7A\2\2\u016a\u016b\5&\24\2\u016b\u016e\3\2\2\2\u016c\u016e\5<\37"+
		"\2\u016d\u0163\3\2\2\2\u016d\u0168\3\2\2\2\u016d\u016c\3\2\2\2\u016e\u0174"+
		"\3\2\2\2\u016f\u0170\f\4\2\2\u0170\u0171\7C\2\2\u0171\u0173\5(\25\5\u0172"+
		"\u016f\3\2\2\2\u0173\u0176\3\2\2\2\u0174\u0172\3\2\2\2\u0174\u0175\3\2"+
		"\2\2\u0175)\3\2\2\2\u0176\u0174\3\2\2\2\u0177\u0178\7&\2\2\u0178\u0179"+
		"\7\7\2\2\u0179\u017a\5(\25\2\u017a\u017b\7\b\2\2\u017b\u017c\7\4\2\2\u017c"+
		"\u017d\5\22\n\2\u017d\u0183\7\5\2\2\u017e\u017f\7\'\2\2\u017f\u0180\7"+
		"\4\2\2\u0180\u0181\5\22\n\2\u0181\u0182\7\5\2\2\u0182\u0184\3\2\2\2\u0183"+
		"\u017e\3\2\2\2\u0183\u0184\3\2\2\2\u0184\u01a6\3\2\2\2\u0185\u0186\7&"+
		"\2\2\u0186\u0187\7\7\2\2\u0187\u0188\5(\25\2\u0188\u0189\7\b\2\2\u0189"+
		"\u018a\7\4\2\2\u018a\u018b\5\22\n\2\u018b\u018e\7\5\2\2\u018c\u018d\7"+
		"\'\2\2\u018d\u018f\5\24\13\2\u018e\u018c\3\2\2\2\u018e\u018f\3\2\2\2\u018f"+
		"\u01a6\3\2\2\2\u0190\u0191\7&\2\2\u0191\u0192\7\7\2\2\u0192\u0193\5(\25"+
		"\2\u0193\u0194\7\b\2\2\u0194\u019a\5\24\13\2\u0195\u0196\7\'\2\2\u0196"+
		"\u0197\7\4\2\2\u0197\u0198\5\22\n\2\u0198\u0199\7\5\2\2\u0199\u019b\3"+
		"\2\2\2\u019a\u0195\3\2\2\2\u019a\u019b\3\2\2\2\u019b\u01a6\3\2\2\2\u019c"+
		"\u019d\7&\2\2\u019d\u019e\7\7\2\2\u019e\u019f\5(\25\2\u019f\u01a0\7\b"+
		"\2\2\u01a0\u01a3\5\24\13\2\u01a1\u01a2\7\'\2\2\u01a2\u01a4\5\24\13\2\u01a3"+
		"\u01a1\3\2\2\2\u01a3\u01a4\3\2\2\2\u01a4\u01a6\3\2\2\2\u01a5\u0177\3\2"+
		"\2\2\u01a5\u0185\3\2\2\2\u01a5\u0190\3\2\2\2\u01a5\u019c\3\2\2\2\u01a6"+
		"+\3\2\2\2\u01a7\u01a8\7(\2\2\u01a8\u01a9\7\7\2\2\u01a9\u01aa\5\36\20\2"+
		"\u01aa\u01ab\7\16\2\2\u01ab\u01ac\5(\25\2\u01ac\u01ad\7\16\2\2\u01ad\u01ae"+
		"\5<\37\2\u01ae\u01af\7D\2\2\u01af\u01b0\7\b\2\2\u01b0\u01b1\5\24\13\2"+
		"\u01b1\u01c0\3\2\2\2\u01b2\u01b3\7(\2\2\u01b3\u01b4\7\7\2\2\u01b4\u01b5"+
		"\5\36\20\2\u01b5\u01b6\7\16\2\2\u01b6\u01b7\5(\25\2\u01b7\u01b8\7\16\2"+
		"\2\u01b8\u01b9\5<\37\2\u01b9\u01ba\7D\2\2\u01ba\u01bb\7\b\2\2\u01bb\u01bc"+
		"\7\4\2\2\u01bc\u01bd\5\22\n\2\u01bd\u01be\7\5\2\2\u01be\u01c0\3\2\2\2"+
		"\u01bf\u01a7\3\2\2\2\u01bf\u01b2\3\2\2\2\u01c0-\3\2\2\2\u01c1\u01c2\7"+
		")\2\2\u01c2\u01c3\7\7\2\2\u01c3\u01c4\5(\25\2\u01c4\u01c5\7\b\2\2\u01c5"+
		"\u01c6\5\24\13\2\u01c6\u01d0\3\2\2\2\u01c7\u01c8\7)\2\2\u01c8\u01c9\7"+
		"\7\2\2\u01c9\u01ca\5(\25\2\u01ca\u01cb\7\b\2\2\u01cb\u01cc\7\4\2\2\u01cc"+
		"\u01cd\5\22\n\2\u01cd\u01ce\7\5\2\2\u01ce\u01d0\3\2\2\2\u01cf\u01c1\3"+
		"\2\2\2\u01cf\u01c7\3\2\2\2\u01d0/\3\2\2\2\u01d1\u01d2\7*\2\2\u01d2\u01d3"+
		"\7\7\2\2\u01d3\u01d4\5<\37\2\u01d4\u01d5\7\b\2\2\u01d5\u01dc\7\4\2\2\u01d6"+
		"\u01d7\7+\2\2\u01d7\u01d8\5J&\2\u01d8\u01d9\7,\2\2\u01d9\u01da\5\22\n"+
		"\2\u01da\u01db\7:\2\2\u01db\u01dd\3\2\2\2\u01dc\u01d6\3\2\2\2\u01dd\u01de"+
		"\3\2\2\2\u01de\u01dc\3\2\2\2\u01de\u01df\3\2\2\2\u01df\u01e0\3\2\2\2\u01e0"+
		"\u01e1\7\5\2\2\u01e1\61\3\2\2\2\u01e2\u01e7\7H\2\2\u01e3\u01e4\7\22\2"+
		"\2\u01e4\u01e6\7H\2\2\u01e5\u01e3\3\2\2\2\u01e6\u01e9\3\2\2\2\u01e7\u01e5"+
		"\3\2\2\2\u01e7\u01e8\3\2\2\2\u01e8\63\3\2\2\2\u01e9\u01e7\3\2\2\2\u01ea"+
		"\u01ef\5&\24\2\u01eb\u01ec\7\22\2\2\u01ec\u01ee\5&\24\2\u01ed\u01eb\3"+
		"\2\2\2\u01ee\u01f1\3\2\2\2\u01ef\u01ed\3\2\2\2\u01ef\u01f0\3\2\2\2\u01f0"+
		"\65\3\2\2\2\u01f1\u01ef\3\2\2\2\u01f2\u01f5\58\35\2\u01f3\u01f5\5F$\2"+
		"\u01f4\u01f2\3\2\2\2\u01f4\u01f3\3\2\2\2\u01f5\67\3\2\2\2\u01f6\u01f9"+
		"\7J\2\2\u01f7\u01f9\5:\36\2\u01f8\u01f6\3\2\2\2\u01f8\u01f7\3\2\2\2\u01f9"+
		"9\3\2\2\2\u01fa\u01fb\7J\2\2\u01fb\u01fc\7%\2\2\u01fc\u01fd\7J\2\2\u01fd"+
		";\3\2\2\2\u01fe\u0202\5$\23\2\u01ff\u0202\5> \2\u0200\u0202\7H\2\2\u0201"+
		"\u01fe\3\2\2\2\u0201\u01ff\3\2\2\2\u0201\u0200\3\2\2\2\u0202=\3\2\2\2"+
		"\u0203\u0204\7H\2\2\u0204\u0205\7-\2\2\u0205\u0206\5&\24\2\u0206\u0207"+
		"\7.\2\2\u0207?\3\2\2\2\u0208\u0209\5<\37\2\u0209\u020a\7%\2\2\u020a\u020b"+
		"\t\2\2\2\u020b\u020d\7\7\2\2\u020c\u020e\5\64\33\2\u020d\u020c\3\2\2\2"+
		"\u020d\u020e\3\2\2\2\u020e\u020f\3\2\2\2\u020f\u0210\7\b\2\2\u0210\u0222"+
		"\3\2\2\2\u0211\u0212\7/\2\2\u0212\u0213\7%\2\2\u0213\u0214\7<\2\2\u0214"+
		"\u0216\7\7\2\2\u0215\u0217\5\64\33\2\u0216\u0215\3\2\2\2\u0216\u0217\3"+
		"\2\2\2\u0217\u0218\3\2\2\2\u0218\u0222\7\b\2\2\u0219\u021a\7\60\2\2\u021a"+
		"\u021b\7%\2\2\u021b\u021c\7>\2\2\u021c\u021e\7\7\2\2\u021d\u021f\5\64"+
		"\33\2\u021e\u021d\3\2\2\2\u021e\u021f\3\2\2\2\u021f\u0220\3\2\2\2\u0220"+
		"\u0222\7\b\2\2\u0221\u0208\3\2\2\2\u0221\u0211\3\2\2\2\u0221\u0219\3\2"+
		"\2\2\u0222A\3\2\2\2\u0223\u0228\5D#\2\u0224\u0225\7\22\2\2\u0225\u0227"+
		"\5D#\2\u0226\u0224\3\2\2\2\u0227\u022a\3\2\2\2\u0228\u0226\3\2\2\2\u0228"+
		"\u0229\3\2\2\2\u0229C\3\2\2\2\u022a\u0228\3\2\2\2\u022b\u022e\5F$\2\u022c"+
		"\u022e\58\35\2\u022d\u022b\3\2\2\2\u022d\u022c\3\2\2\2\u022eE\3\2\2\2"+
		"\u022f\u0230\7I\2\2\u0230G\3\2\2\2\u0231\u0232\t\3\2\2\u0232I\3\2\2\2"+
		"\u0233\u0238\5F$\2\u0234\u0238\58\35\2\u0235\u0238\5<\37\2\u0236\u0238"+
		"\5\26\f\2\u0237\u0233\3\2\2\2\u0237\u0234\3\2\2\2\u0237\u0235\3\2\2\2"+
		"\u0237\u0236\3\2\2\2\u0238K\3\2\2\2\u0239\u023a\7\61\2\2\u023a\u023c\7"+
		"\7\2\2\u023b\u023d\5&\24\2\u023c\u023b\3\2\2\2\u023c\u023d\3\2\2\2\u023d"+
		"\u023e\3\2\2\2\u023e\u023f\7\b\2\2\u023f\u0240\7\16\2\2\u0240M\3\2\2\2"+
		"\u0241\u0242\7\62\2\2\u0242\u024a\7\67\2\2\u0243\u0244\7\63\2\2\u0244"+
		"\u024a\78\2\2\u0245\u0246\7\64\2\2\u0246\u024a\79\2\2\u0247\u0248\7\65"+
		"\2\2\u0248\u024a\7\66\2\2\u0249\u0241\3\2\2\2\u0249\u0243\3\2\2\2\u0249"+
		"\u0245\3\2\2\2\u0249\u0247\3\2\2\2\u024aO\3\2\2\2/S[_behkqw\u0097\u00a4"+
		"\u00ac\u011b\u0123\u0126\u012c\u012f\u013c\u0141\u0159\u0160\u016d\u0174"+
		"\u0183\u018e\u019a\u01a3\u01a5\u01bf\u01cf\u01de\u01e7\u01ef\u01f4\u01f8"+
		"\u0201\u020d\u0216\u021e\u0221\u0228\u022d\u0237\u023c\u0249";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}