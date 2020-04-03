// Generated from /home/edo/Documents/SmartMessaging/SmartMessaging/src/smartmessage.g4 by ANTLR 4.8
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class smartmessageParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, TESTO=9, 
		NUM=10, LINE_COMMENT=11, WS=12;
	public static final int
		RULE_program = 0, RULE_priorita = 1, RULE_invia = 2, RULE_message = 3, 
		RULE_opzioni = 4, RULE_titolo = 5, RULE_corpo = 6, RULE_cc = 7, RULE_img = 8, 
		RULE_new_mess = 9, RULE_duepunti = 10;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "priorita", "invia", "message", "opzioni", "titolo", "corpo", 
			"cc", "img", "new_mess", "duepunti"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'Priorit\u00E0 '", "'Invia'", "'Titolo '", "'Corpo '", "'CC'", 
			"'Img '", "'Messaggio a'", "':'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, "TESTO", "NUM", 
			"LINE_COMMENT", "WS"
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

	@Override
	public String getGrammarFileName() { return "smartmessage.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public smartmessageParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public List<MessageContext> message() {
			return getRuleContexts(MessageContext.class);
		}
		public MessageContext message(int i) {
			return getRuleContext(MessageContext.class,i);
		}
		public List<PrioritaContext> priorita() {
			return getRuleContexts(PrioritaContext.class);
		}
		public PrioritaContext priorita(int i) {
			return getRuleContext(PrioritaContext.class,i);
		}
		public List<InviaContext> invia() {
			return getRuleContexts(InviaContext.class);
		}
		public InviaContext invia(int i) {
			return getRuleContext(InviaContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).exitProgram(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(25); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(25);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__6:
					{
					setState(22);
					message();
					}
					break;
				case T__0:
					{
					setState(23);
					priorita();
					}
					break;
				case T__1:
					{
					setState(24);
					invia();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(27); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__6))) != 0) );
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

	public static class PrioritaContext extends ParserRuleContext {
		public TerminalNode NUM() { return getToken(smartmessageParser.NUM, 0); }
		public PrioritaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_priorita; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).enterPriorita(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).exitPriorita(this);
		}
	}

	public final PrioritaContext priorita() throws RecognitionException {
		PrioritaContext _localctx = new PrioritaContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_priorita);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(29);
			match(T__0);
			setState(30);
			match(NUM);
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

	public static class InviaContext extends ParserRuleContext {
		public InviaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_invia; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).enterInvia(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).exitInvia(this);
		}
	}

	public final InviaContext invia() throws RecognitionException {
		InviaContext _localctx = new InviaContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_invia);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			match(T__1);
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

	public static class MessageContext extends ParserRuleContext {
		public New_messContext new_mess() {
			return getRuleContext(New_messContext.class,0);
		}
		public DuepuntiContext duepunti() {
			return getRuleContext(DuepuntiContext.class,0);
		}
		public TitoloContext titolo() {
			return getRuleContext(TitoloContext.class,0);
		}
		public CorpoContext corpo() {
			return getRuleContext(CorpoContext.class,0);
		}
		public OpzioniContext opzioni() {
			return getRuleContext(OpzioniContext.class,0);
		}
		public List<TerminalNode> TESTO() { return getTokens(smartmessageParser.TESTO); }
		public TerminalNode TESTO(int i) {
			return getToken(smartmessageParser.TESTO, i);
		}
		public MessageContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_message; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).enterMessage(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).exitMessage(this);
		}
	}

	public final MessageContext message() throws RecognitionException {
		MessageContext _localctx = new MessageContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_message);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			new_mess();
			setState(36); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(35);
				match(TESTO);
				}
				}
				setState(38); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==TESTO );
			setState(40);
			duepunti();
			setState(41);
			titolo();
			setState(42);
			corpo();
			setState(43);
			opzioni();
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

	public static class OpzioniContext extends ParserRuleContext {
		public CcContext cc() {
			return getRuleContext(CcContext.class,0);
		}
		public ImgContext img() {
			return getRuleContext(ImgContext.class,0);
		}
		public OpzioniContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_opzioni; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).enterOpzioni(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).exitOpzioni(this);
		}
	}

	public final OpzioniContext opzioni() throws RecognitionException {
		OpzioniContext _localctx = new OpzioniContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_opzioni);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(45);
				cc();
				}
			}

			setState(49);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(48);
				img();
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

	public static class TitoloContext extends ParserRuleContext {
		public TerminalNode TESTO() { return getToken(smartmessageParser.TESTO, 0); }
		public TitoloContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_titolo; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).enterTitolo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).exitTitolo(this);
		}
	}

	public final TitoloContext titolo() throws RecognitionException {
		TitoloContext _localctx = new TitoloContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_titolo);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			match(T__2);
			setState(52);
			match(TESTO);
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

	public static class CorpoContext extends ParserRuleContext {
		public TerminalNode TESTO() { return getToken(smartmessageParser.TESTO, 0); }
		public CorpoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_corpo; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).enterCorpo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).exitCorpo(this);
		}
	}

	public final CorpoContext corpo() throws RecognitionException {
		CorpoContext _localctx = new CorpoContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_corpo);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			match(T__3);
			setState(55);
			match(TESTO);
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

	public static class CcContext extends ParserRuleContext {
		public CcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).enterCc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).exitCc(this);
		}
	}

	public final CcContext cc() throws RecognitionException {
		CcContext _localctx = new CcContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_cc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
			match(T__4);
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

	public static class ImgContext extends ParserRuleContext {
		public TerminalNode TESTO() { return getToken(smartmessageParser.TESTO, 0); }
		public ImgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_img; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).enterImg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).exitImg(this);
		}
	}

	public final ImgContext img() throws RecognitionException {
		ImgContext _localctx = new ImgContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_img);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(59);
			match(T__5);
			setState(60);
			match(TESTO);
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

	public static class New_messContext extends ParserRuleContext {
		public New_messContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_new_mess; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).enterNew_mess(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).exitNew_mess(this);
		}
	}

	public final New_messContext new_mess() throws RecognitionException {
		New_messContext _localctx = new New_messContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_new_mess);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			match(T__6);
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

	public static class DuepuntiContext extends ParserRuleContext {
		public DuepuntiContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_duepunti; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).enterDuepunti(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).exitDuepunti(this);
		}
	}

	public final DuepuntiContext duepunti() throws RecognitionException {
		DuepuntiContext _localctx = new DuepuntiContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_duepunti);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			match(T__7);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\16E\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\3\2\3\2\3\2\6\2\34\n\2\r\2\16\2\35\3\3\3\3\3\3\3\4\3\4\3\5\3\5"+
		"\6\5\'\n\5\r\5\16\5(\3\5\3\5\3\5\3\5\3\5\3\6\5\6\61\n\6\3\6\5\6\64\n\6"+
		"\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\f\2\2"+
		"\r\2\4\6\b\n\f\16\20\22\24\26\2\2\2?\2\33\3\2\2\2\4\37\3\2\2\2\6\"\3\2"+
		"\2\2\b$\3\2\2\2\n\60\3\2\2\2\f\65\3\2\2\2\168\3\2\2\2\20;\3\2\2\2\22="+
		"\3\2\2\2\24@\3\2\2\2\26B\3\2\2\2\30\34\5\b\5\2\31\34\5\4\3\2\32\34\5\6"+
		"\4\2\33\30\3\2\2\2\33\31\3\2\2\2\33\32\3\2\2\2\34\35\3\2\2\2\35\33\3\2"+
		"\2\2\35\36\3\2\2\2\36\3\3\2\2\2\37 \7\3\2\2 !\7\f\2\2!\5\3\2\2\2\"#\7"+
		"\4\2\2#\7\3\2\2\2$&\5\24\13\2%\'\7\13\2\2&%\3\2\2\2\'(\3\2\2\2(&\3\2\2"+
		"\2()\3\2\2\2)*\3\2\2\2*+\5\26\f\2+,\5\f\7\2,-\5\16\b\2-.\5\n\6\2.\t\3"+
		"\2\2\2/\61\5\20\t\2\60/\3\2\2\2\60\61\3\2\2\2\61\63\3\2\2\2\62\64\5\22"+
		"\n\2\63\62\3\2\2\2\63\64\3\2\2\2\64\13\3\2\2\2\65\66\7\5\2\2\66\67\7\13"+
		"\2\2\67\r\3\2\2\289\7\6\2\29:\7\13\2\2:\17\3\2\2\2;<\7\7\2\2<\21\3\2\2"+
		"\2=>\7\b\2\2>?\7\13\2\2?\23\3\2\2\2@A\7\t\2\2A\25\3\2\2\2BC\7\n\2\2C\27"+
		"\3\2\2\2\7\33\35(\60\63";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}