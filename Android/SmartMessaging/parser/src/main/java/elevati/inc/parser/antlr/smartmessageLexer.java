package elevati.inc.parser.antlr;// Generated from /home/simone/IdeaProjects/SmartMessaging/smartmessaging/SmartMessaging/src/smartmessage.g4 by ANTLR 4.8
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
		TESTO=10, NUM=11, LINE_COMMENT=12, WS=13;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"TESTO", "NUM", "LINE_COMMENT", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'Priorit\u00E0 '", "'Invia'", "'Titolo '", "'Corpo '", "'CC'", 
			"'Img '", "'Formattazione '", "'Messaggio a'", "':'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, "TESTO", 
			"NUM", "LINE_COMMENT", "WS"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\17~\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\n\3\n\3\13\3\13\7\13d\n\13\f\13\16\13g\13\13\3\13\3\13"+
		"\3\f\3\f\3\r\3\r\3\r\3\r\7\rq\n\r\f\r\16\rt\13\r\3\r\3\r\3\16\6\16y\n"+
		"\16\r\16\16\16z\3\16\3\16\2\2\17\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23"+
		"\13\25\f\27\r\31\16\33\17\3\2\6\4\2$$^^\3\2\62;\4\2\f\f\17\17\5\2\13\f"+
		"\17\17\"\"\2\u0080\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13"+
		"\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2"+
		"\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\3\35\3\2\2\2\5\'\3\2\2\2\7-"+
		"\3\2\2\2\t\65\3\2\2\2\13<\3\2\2\2\r?\3\2\2\2\17D\3\2\2\2\21S\3\2\2\2\23"+
		"_\3\2\2\2\25a\3\2\2\2\27j\3\2\2\2\31l\3\2\2\2\33x\3\2\2\2\35\36\7R\2\2"+
		"\36\37\7t\2\2\37 \7k\2\2 !\7q\2\2!\"\7t\2\2\"#\7k\2\2#$\7v\2\2$%\7\u00e2"+
		"\2\2%&\7\"\2\2&\4\3\2\2\2\'(\7K\2\2()\7p\2\2)*\7x\2\2*+\7k\2\2+,\7c\2"+
		"\2,\6\3\2\2\2-.\7V\2\2./\7k\2\2/\60\7v\2\2\60\61\7q\2\2\61\62\7n\2\2\62"+
		"\63\7q\2\2\63\64\7\"\2\2\64\b\3\2\2\2\65\66\7E\2\2\66\67\7q\2\2\678\7"+
		"t\2\289\7r\2\29:\7q\2\2:;\7\"\2\2;\n\3\2\2\2<=\7E\2\2=>\7E\2\2>\f\3\2"+
		"\2\2?@\7K\2\2@A\7o\2\2AB\7i\2\2BC\7\"\2\2C\16\3\2\2\2DE\7H\2\2EF\7q\2"+
		"\2FG\7t\2\2GH\7o\2\2HI\7c\2\2IJ\7v\2\2JK\7v\2\2KL\7c\2\2LM\7|\2\2MN\7"+
		"k\2\2NO\7q\2\2OP\7p\2\2PQ\7g\2\2QR\7\"\2\2R\20\3\2\2\2ST\7O\2\2TU\7g\2"+
		"\2UV\7u\2\2VW\7u\2\2WX\7c\2\2XY\7i\2\2YZ\7i\2\2Z[\7k\2\2[\\\7q\2\2\\]"+
		"\7\"\2\2]^\7c\2\2^\22\3\2\2\2_`\7<\2\2`\24\3\2\2\2ae\7$\2\2bd\n\2\2\2"+
		"cb\3\2\2\2dg\3\2\2\2ec\3\2\2\2ef\3\2\2\2fh\3\2\2\2ge\3\2\2\2hi\7$\2\2"+
		"i\26\3\2\2\2jk\t\3\2\2k\30\3\2\2\2lm\7\61\2\2mn\7\61\2\2nr\3\2\2\2oq\n"+
		"\4\2\2po\3\2\2\2qt\3\2\2\2rp\3\2\2\2rs\3\2\2\2su\3\2\2\2tr\3\2\2\2uv\b"+
		"\r\2\2v\32\3\2\2\2wy\t\5\2\2xw\3\2\2\2yz\3\2\2\2zx\3\2\2\2z{\3\2\2\2{"+
		"|\3\2\2\2|}\b\16\2\2}\34\3\2\2\2\6\2erz\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}