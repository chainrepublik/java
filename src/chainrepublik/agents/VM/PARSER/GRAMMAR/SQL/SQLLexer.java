// Generated from /Users/technicalsupport/Documents/NetBeansProjects/chainrepublik/src/chainrepublik/agents/SQL.g4 by ANTLR 4.7
package chainrepublik.agents.VM.PARSER.GRAMMAR.SQL;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SQLLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		COMP=10, USER_COL=11, LOGIC_OP=12, STRING=13, INT=14, TAB_NAME=15, WS=16;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"COMP", "USER_COL", "LOGIC_OP", "STRING", "INT", "TAB_NAME", "WS"
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


	public SQLLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "SQL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\22\u00c9\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\n\3"+
		"\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13v\n\13\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\5\f\u00a2\n\f\3\r\3\r\3\r\3\r\3\r\5\r\u00a9\n\r"+
		"\3\16\3\16\6\16\u00ad\n\16\r\16\16\16\u00ae\3\16\3\16\3\17\6\17\u00b4"+
		"\n\17\r\17\16\17\u00b5\3\20\6\20\u00b9\n\20\r\20\16\20\u00ba\3\20\7\20"+
		"\u00be\n\20\f\20\16\20\u00c1\13\20\3\21\6\21\u00c4\n\21\r\21\16\21\u00c5"+
		"\3\21\3\21\2\2\22\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31"+
		"\16\33\17\35\20\37\21!\22\3\2\6\7\2\"#%(*,.ac\177\3\2\62;\4\2C\\c|\5\2"+
		"\13\f\17\17\"\"\2\u00e5\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2"+
		"\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25"+
		"\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2"+
		"\2\2\2!\3\2\2\2\3#\3\2\2\2\5\62\3\2\2\2\78\3\2\2\2\tA\3\2\2\2\13N\3\2"+
		"\2\2\rR\3\2\2\2\17Z\3\2\2\2\21g\3\2\2\2\23i\3\2\2\2\25u\3\2\2\2\27\u00a1"+
		"\3\2\2\2\31\u00a8\3\2\2\2\33\u00aa\3\2\2\2\35\u00b3\3\2\2\2\37\u00b8\3"+
		"\2\2\2!\u00c3\3\2\2\2#$\7U\2\2$%\7G\2\2%&\7N\2\2&\'\7G\2\2\'(\7E\2\2("+
		")\7V\2\2)*\7\"\2\2*+\7,\2\2+,\7\"\2\2,-\7H\2\2-.\7T\2\2./\7Q\2\2/\60\7"+
		"O\2\2\60\61\7\"\2\2\61\4\3\2\2\2\62\63\7Y\2\2\63\64\7J\2\2\64\65\7G\2"+
		"\2\65\66\7T\2\2\66\67\7G\2\2\67\6\3\2\2\289\7Q\2\29:\7T\2\2:;\7F\2\2;"+
		"<\7G\2\2<=\7T\2\2=>\7\"\2\2>?\7D\2\2?@\7[\2\2@\b\3\2\2\2AB\7K\2\2BC\7"+
		"P\2\2CD\7U\2\2DE\7G\2\2EF\7T\2\2FG\7V\2\2GH\7\"\2\2HI\7K\2\2IJ\7P\2\2"+
		"JK\7V\2\2KL\7Q\2\2LM\7\"\2\2M\n\3\2\2\2NO\7U\2\2OP\7G\2\2PQ\7V\2\2Q\f"+
		"\3\2\2\2RS\7W\2\2ST\7R\2\2TU\7F\2\2UV\7C\2\2VW\7V\2\2WX\7G\2\2XY\7\"\2"+
		"\2Y\16\3\2\2\2Z[\7F\2\2[\\\7G\2\2\\]\7N\2\2]^\7G\2\2^_\7V\2\2_`\7G\2\2"+
		"`a\7\"\2\2ab\7H\2\2bc\7T\2\2cd\7Q\2\2de\7O\2\2ef\7\"\2\2f\20\3\2\2\2g"+
		"h\7.\2\2h\22\3\2\2\2ij\7\60\2\2j\24\3\2\2\2kv\4?@\2lm\7@\2\2mv\7?\2\2"+
		"nv\7>\2\2op\7>\2\2pv\7?\2\2qr\7N\2\2rs\7K\2\2st\7M\2\2tv\7G\2\2uk\3\2"+
		"\2\2ul\3\2\2\2un\3\2\2\2uo\3\2\2\2uq\3\2\2\2v\26\3\2\2\2wx\7u\2\2x\u00a2"+
		"\7\63\2\2yz\7u\2\2z\u00a2\7\64\2\2{|\7u\2\2|\u00a2\7\65\2\2}~\7u\2\2~"+
		"\u00a2\7\66\2\2\177\u0080\7u\2\2\u0080\u00a2\7\67\2\2\u0081\u0082\7u\2"+
		"\2\u0082\u00a2\78\2\2\u0083\u0084\7u\2\2\u0084\u00a2\79\2\2\u0085\u0086"+
		"\7u\2\2\u0086\u00a2\7:\2\2\u0087\u0088\7u\2\2\u0088\u00a2\7;\2\2\u0089"+
		"\u008a\7u\2\2\u008a\u008b\7\63\2\2\u008b\u00a2\7\62\2\2\u008c\u008d\7"+
		"p\2\2\u008d\u00a2\7\63\2\2\u008e\u008f\7p\2\2\u008f\u00a2\7\64\2\2\u0090"+
		"\u0091\7p\2\2\u0091\u00a2\7\65\2\2\u0092\u0093\7p\2\2\u0093\u00a2\7\66"+
		"\2\2\u0094\u0095\7p\2\2\u0095\u00a2\7\67\2\2\u0096\u0097\7p\2\2\u0097"+
		"\u00a2\78\2\2\u0098\u0099\7p\2\2\u0099\u00a2\79\2\2\u009a\u009b\7p\2\2"+
		"\u009b\u00a2\7:\2\2\u009c\u009d\7p\2\2\u009d\u00a2\7;\2\2\u009e\u009f"+
		"\7p\2\2\u009f\u00a0\7\63\2\2\u00a0\u00a2\7\62\2\2\u00a1w\3\2\2\2\u00a1"+
		"y\3\2\2\2\u00a1{\3\2\2\2\u00a1}\3\2\2\2\u00a1\177\3\2\2\2\u00a1\u0081"+
		"\3\2\2\2\u00a1\u0083\3\2\2\2\u00a1\u0085\3\2\2\2\u00a1\u0087\3\2\2\2\u00a1"+
		"\u0089\3\2\2\2\u00a1\u008c\3\2\2\2\u00a1\u008e\3\2\2\2\u00a1\u0090\3\2"+
		"\2\2\u00a1\u0092\3\2\2\2\u00a1\u0094\3\2\2\2\u00a1\u0096\3\2\2\2\u00a1"+
		"\u0098\3\2\2\2\u00a1\u009a\3\2\2\2\u00a1\u009c\3\2\2\2\u00a1\u009e\3\2"+
		"\2\2\u00a2\30\3\2\2\2\u00a3\u00a4\7C\2\2\u00a4\u00a5\7P\2\2\u00a5\u00a9"+
		"\7F\2\2\u00a6\u00a7\7Q\2\2\u00a7\u00a9\7T\2\2\u00a8\u00a3\3\2\2\2\u00a8"+
		"\u00a6\3\2\2\2\u00a9\32\3\2\2\2\u00aa\u00ac\7)\2\2\u00ab\u00ad\t\2\2\2"+
		"\u00ac\u00ab\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\u00ac\3\2\2\2\u00ae\u00af"+
		"\3\2\2\2\u00af\u00b0\3\2\2\2\u00b0\u00b1\7)\2\2\u00b1\34\3\2\2\2\u00b2"+
		"\u00b4\t\3\2\2\u00b3\u00b2\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5\u00b3\3\2"+
		"\2\2\u00b5\u00b6\3\2\2\2\u00b6\36\3\2\2\2\u00b7\u00b9\t\4\2\2\u00b8\u00b7"+
		"\3\2\2\2\u00b9\u00ba\3\2\2\2\u00ba\u00b8\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb"+
		"\u00bf\3\2\2\2\u00bc\u00be\t\3\2\2\u00bd\u00bc\3\2\2\2\u00be\u00c1\3\2"+
		"\2\2\u00bf\u00bd\3\2\2\2\u00bf\u00c0\3\2\2\2\u00c0 \3\2\2\2\u00c1\u00bf"+
		"\3\2\2\2\u00c2\u00c4\t\5\2\2\u00c3\u00c2\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5"+
		"\u00c3\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7\u00c8\b\21"+
		"\2\2\u00c8\"\3\2\2\2\13\2u\u00a1\u00a8\u00ae\u00b5\u00ba\u00bf\u00c5\3"+
		"\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}