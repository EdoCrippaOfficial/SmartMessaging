// Generated from /home/simone/IdeaProjects/SmartMessaging/smartmessaging/Server/src/main/java/server/parser/SmartMessage.g4 by ANTLR 4.8
package server.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SmartMessageLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, TESTO=11, NUM=12, EXIT=13, LINE_COMMENT=14, WS=15;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "TESTO", "NUM", "EXIT", "LINE_COMMENT", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'Priorit\u00E0'", "'Invia'", "'Stats'", "'Messaggio a'", "':'", 
			"'Titolo'", "'Corpo'", "'CC'", "'Img'", "'Formattazione'", null, null, 
			"'Exit'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, "TESTO", 
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


	public SmartMessageLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "SmartMessage.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\21\u0088\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f"+
		"\3\f\7\fi\n\f\f\f\16\fl\13\f\3\f\3\f\3\r\3\r\3\16\3\16\3\16\3\16\3\16"+
		"\3\17\3\17\3\17\3\17\7\17{\n\17\f\17\16\17~\13\17\3\17\3\17\3\20\6\20"+
		"\u0083\n\20\r\20\16\20\u0084\3\20\3\20\2\2\21\3\3\5\4\7\5\t\6\13\7\r\b"+
		"\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21\3\2\6\4\2$$^^\3\2\62"+
		";\4\2\f\f\17\17\5\2\13\f\17\17\"\"\2\u008a\2\3\3\2\2\2\2\5\3\2\2\2\2\7"+
		"\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2"+
		"\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2"+
		"\35\3\2\2\2\2\37\3\2\2\2\3!\3\2\2\2\5*\3\2\2\2\7\60\3\2\2\2\t\66\3\2\2"+
		"\2\13B\3\2\2\2\rD\3\2\2\2\17K\3\2\2\2\21Q\3\2\2\2\23T\3\2\2\2\25X\3\2"+
		"\2\2\27f\3\2\2\2\31o\3\2\2\2\33q\3\2\2\2\35v\3\2\2\2\37\u0082\3\2\2\2"+
		"!\"\7R\2\2\"#\7t\2\2#$\7k\2\2$%\7q\2\2%&\7t\2\2&\'\7k\2\2\'(\7v\2\2()"+
		"\7\u00e2\2\2)\4\3\2\2\2*+\7K\2\2+,\7p\2\2,-\7x\2\2-.\7k\2\2./\7c\2\2/"+
		"\6\3\2\2\2\60\61\7U\2\2\61\62\7v\2\2\62\63\7c\2\2\63\64\7v\2\2\64\65\7"+
		"u\2\2\65\b\3\2\2\2\66\67\7O\2\2\678\7g\2\289\7u\2\29:\7u\2\2:;\7c\2\2"+
		";<\7i\2\2<=\7i\2\2=>\7k\2\2>?\7q\2\2?@\7\"\2\2@A\7c\2\2A\n\3\2\2\2BC\7"+
		"<\2\2C\f\3\2\2\2DE\7V\2\2EF\7k\2\2FG\7v\2\2GH\7q\2\2HI\7n\2\2IJ\7q\2\2"+
		"J\16\3\2\2\2KL\7E\2\2LM\7q\2\2MN\7t\2\2NO\7r\2\2OP\7q\2\2P\20\3\2\2\2"+
		"QR\7E\2\2RS\7E\2\2S\22\3\2\2\2TU\7K\2\2UV\7o\2\2VW\7i\2\2W\24\3\2\2\2"+
		"XY\7H\2\2YZ\7q\2\2Z[\7t\2\2[\\\7o\2\2\\]\7c\2\2]^\7v\2\2^_\7v\2\2_`\7"+
		"c\2\2`a\7|\2\2ab\7k\2\2bc\7q\2\2cd\7p\2\2de\7g\2\2e\26\3\2\2\2fj\7$\2"+
		"\2gi\n\2\2\2hg\3\2\2\2il\3\2\2\2jh\3\2\2\2jk\3\2\2\2km\3\2\2\2lj\3\2\2"+
		"\2mn\7$\2\2n\30\3\2\2\2op\t\3\2\2p\32\3\2\2\2qr\7G\2\2rs\7z\2\2st\7k\2"+
		"\2tu\7v\2\2u\34\3\2\2\2vw\7\61\2\2wx\7\61\2\2x|\3\2\2\2y{\n\4\2\2zy\3"+
		"\2\2\2{~\3\2\2\2|z\3\2\2\2|}\3\2\2\2}\177\3\2\2\2~|\3\2\2\2\177\u0080"+
		"\b\17\2\2\u0080\36\3\2\2\2\u0081\u0083\t\5\2\2\u0082\u0081\3\2\2\2\u0083"+
		"\u0084\3\2\2\2\u0084\u0082\3\2\2\2\u0084\u0085\3\2\2\2\u0085\u0086\3\2"+
		"\2\2\u0086\u0087\b\20\2\2\u0087 \3\2\2\2\6\2j|\u0084\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}