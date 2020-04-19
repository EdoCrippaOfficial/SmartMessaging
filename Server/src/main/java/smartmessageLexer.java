// Generated from /home/edo/Documents/SmartMessaging/SmartMessaging/src/smartmessage.g4 by ANTLR 4.8
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class smartmessageLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		TESTO=10, NUM=11, EXIT=12, LINE_COMMENT=13, WS=14;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"TESTO", "NUM", "EXIT", "LINE_COMMENT", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'Priorit\u00E0'", "'Invia'", "'Titolo'", "'Corpo'", "'CC'", "'Img'", 
			"'Formattazione'", "'Messaggio a'", "':'", null, null, "'Exit'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, "TESTO", 
			"NUM", "EXIT", "LINE_COMMENT", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
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


	public smartmessageLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "smartmessage.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\20\u0080\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\n\3\n\3\13\3\13\7\13a\n\13\f\13\16\13d\13\13\3\13\3\13\3\f"+
		"\3\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\7\16s\n\16\f\16\16\16v\13"+
		"\16\3\16\3\16\3\17\6\17{\n\17\r\17\16\17|\3\17\3\17\2\2\20\3\3\5\4\7\5"+
		"\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\3\2\6\4\2$$"+
		"^^\3\2\62;\4\2\f\f\17\17\5\2\13\f\17\17\"\"\2\u0082\2\3\3\2\2\2\2\5\3"+
		"\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2"+
		"\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3"+
		"\2\2\2\2\35\3\2\2\2\3\37\3\2\2\2\5(\3\2\2\2\7.\3\2\2\2\t\65\3\2\2\2\13"+
		";\3\2\2\2\r>\3\2\2\2\17B\3\2\2\2\21P\3\2\2\2\23\\\3\2\2\2\25^\3\2\2\2"+
		"\27g\3\2\2\2\31i\3\2\2\2\33n\3\2\2\2\35z\3\2\2\2\37 \7R\2\2 !\7t\2\2!"+
		"\"\7k\2\2\"#\7q\2\2#$\7t\2\2$%\7k\2\2%&\7v\2\2&\'\7\u00e2\2\2\'\4\3\2"+
		"\2\2()\7K\2\2)*\7p\2\2*+\7x\2\2+,\7k\2\2,-\7c\2\2-\6\3\2\2\2./\7V\2\2"+
		"/\60\7k\2\2\60\61\7v\2\2\61\62\7q\2\2\62\63\7n\2\2\63\64\7q\2\2\64\b\3"+
		"\2\2\2\65\66\7E\2\2\66\67\7q\2\2\678\7t\2\289\7r\2\29:\7q\2\2:\n\3\2\2"+
		"\2;<\7E\2\2<=\7E\2\2=\f\3\2\2\2>?\7K\2\2?@\7o\2\2@A\7i\2\2A\16\3\2\2\2"+
		"BC\7H\2\2CD\7q\2\2DE\7t\2\2EF\7o\2\2FG\7c\2\2GH\7v\2\2HI\7v\2\2IJ\7c\2"+
		"\2JK\7|\2\2KL\7k\2\2LM\7q\2\2MN\7p\2\2NO\7g\2\2O\20\3\2\2\2PQ\7O\2\2Q"+
		"R\7g\2\2RS\7u\2\2ST\7u\2\2TU\7c\2\2UV\7i\2\2VW\7i\2\2WX\7k\2\2XY\7q\2"+
		"\2YZ\7\"\2\2Z[\7c\2\2[\22\3\2\2\2\\]\7<\2\2]\24\3\2\2\2^b\7$\2\2_a\n\2"+
		"\2\2`_\3\2\2\2ad\3\2\2\2b`\3\2\2\2bc\3\2\2\2ce\3\2\2\2db\3\2\2\2ef\7$"+
		"\2\2f\26\3\2\2\2gh\t\3\2\2h\30\3\2\2\2ij\7G\2\2jk\7z\2\2kl\7k\2\2lm\7"+
		"v\2\2m\32\3\2\2\2no\7\61\2\2op\7\61\2\2pt\3\2\2\2qs\n\4\2\2rq\3\2\2\2"+
		"sv\3\2\2\2tr\3\2\2\2tu\3\2\2\2uw\3\2\2\2vt\3\2\2\2wx\b\16\2\2x\34\3\2"+
		"\2\2y{\t\5\2\2zy\3\2\2\2{|\3\2\2\2|z\3\2\2\2|}\3\2\2\2}~\3\2\2\2~\177"+
		"\b\17\2\2\177\36\3\2\2\2\6\2bt|\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}