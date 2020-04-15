package grammar;

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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		TESTO=10, NUM=11, EXIT=12, LINE_COMMENT=13, WS=14;
	public static final int
		RULE_program = 0, RULE_priorita = 1, RULE_invia = 2, RULE_message = 3, 
		RULE_opzioni = 4, RULE_titolo = 5, RULE_destinatario = 6, RULE_corpo = 7, 
		RULE_cc = 8, RULE_img = 9, RULE_format = 10, RULE_new_mess = 11, RULE_duepunti = 12, 
		RULE_exit = 13;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "priorita", "invia", "message", "opzioni", "titolo", "destinatario", 
			"corpo", "cc", "img", "format", "new_mess", "duepunti", "exit"
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
		public ExitContext exit() {
			return getRuleContext(ExitContext.class,0);
		}
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
			setState(31); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(31);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__7:
					{
					setState(28);
					message();
					}
					break;
				case T__0:
					{
					setState(29);
					priorita();
					}
					break;
				case T__1:
					{
					setState(30);
					invia();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(33); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__7))) != 0) );
			setState(35);
			exit();
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
			setState(37);
			match(T__0);
			setState(38);
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
			setState(40);
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
		public List<DestinatarioContext> destinatario() {
			return getRuleContexts(DestinatarioContext.class);
		}
		public DestinatarioContext destinatario(int i) {
			return getRuleContext(DestinatarioContext.class,i);
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
			setState(42);
			new_mess();
			setState(44); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(43);
				destinatario();
				}
				}
				setState(46); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==TESTO );
			setState(48);
			duepunti();
			setState(49);
			titolo();
			setState(50);
			corpo();
			setState(51);
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
		public FormatContext format() {
			return getRuleContext(FormatContext.class,0);
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
			setState(54);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(53);
				cc();
				}
			}

			setState(57);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(56);
				img();
				}
			}

			setState(60);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(59);
				format();
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
			setState(62);
			match(T__2);
			setState(63);
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

	public static class DestinatarioContext extends ParserRuleContext {
		public TerminalNode TESTO() { return getToken(smartmessageParser.TESTO, 0); }
		public DestinatarioContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_destinatario; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).enterDestinatario(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).exitDestinatario(this);
		}
	}

	public final DestinatarioContext destinatario() throws RecognitionException {
		DestinatarioContext _localctx = new DestinatarioContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_destinatario);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65);
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
		enterRule(_localctx, 14, RULE_corpo);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67);
			match(T__3);
			setState(68);
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
		enterRule(_localctx, 16, RULE_cc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
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
		enterRule(_localctx, 18, RULE_img);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			match(T__5);
			setState(73);
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

	public static class FormatContext extends ParserRuleContext {
		public TerminalNode TESTO() { return getToken(smartmessageParser.TESTO, 0); }
		public FormatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_format; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).enterFormat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).exitFormat(this);
		}
	}

	public final FormatContext format() throws RecognitionException {
		FormatContext _localctx = new FormatContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_format);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			match(T__6);
			setState(76);
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
		enterRule(_localctx, 22, RULE_new_mess);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
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
		enterRule(_localctx, 24, RULE_duepunti);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			match(T__8);
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

	public static class ExitContext extends ParserRuleContext {
		public TerminalNode EXIT() { return getToken(smartmessageParser.EXIT, 0); }
		public ExitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).enterExit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof smartmessageListener ) ((smartmessageListener)listener).exitExit(this);
		}
	}

	public final ExitContext exit() throws RecognitionException {
		ExitContext _localctx = new ExitContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_exit);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			match(EXIT);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\20W\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3\2\6\2\"\n\2\r\2\16\2#\3\2"+
		"\3\2\3\3\3\3\3\3\3\4\3\4\3\5\3\5\6\5/\n\5\r\5\16\5\60\3\5\3\5\3\5\3\5"+
		"\3\5\3\6\5\69\n\6\3\6\5\6<\n\6\3\6\5\6?\n\6\3\7\3\7\3\7\3\b\3\b\3\t\3"+
		"\t\3\t\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17"+
		"\3\17\2\2\20\2\4\6\b\n\f\16\20\22\24\26\30\32\34\2\2\2O\2!\3\2\2\2\4\'"+
		"\3\2\2\2\6*\3\2\2\2\b,\3\2\2\2\n8\3\2\2\2\f@\3\2\2\2\16C\3\2\2\2\20E\3"+
		"\2\2\2\22H\3\2\2\2\24J\3\2\2\2\26M\3\2\2\2\30P\3\2\2\2\32R\3\2\2\2\34"+
		"T\3\2\2\2\36\"\5\b\5\2\37\"\5\4\3\2 \"\5\6\4\2!\36\3\2\2\2!\37\3\2\2\2"+
		"! \3\2\2\2\"#\3\2\2\2#!\3\2\2\2#$\3\2\2\2$%\3\2\2\2%&\5\34\17\2&\3\3\2"+
		"\2\2\'(\7\3\2\2()\7\r\2\2)\5\3\2\2\2*+\7\4\2\2+\7\3\2\2\2,.\5\30\r\2-"+
		"/\5\16\b\2.-\3\2\2\2/\60\3\2\2\2\60.\3\2\2\2\60\61\3\2\2\2\61\62\3\2\2"+
		"\2\62\63\5\32\16\2\63\64\5\f\7\2\64\65\5\20\t\2\65\66\5\n\6\2\66\t\3\2"+
		"\2\2\679\5\22\n\28\67\3\2\2\289\3\2\2\29;\3\2\2\2:<\5\24\13\2;:\3\2\2"+
		"\2;<\3\2\2\2<>\3\2\2\2=?\5\26\f\2>=\3\2\2\2>?\3\2\2\2?\13\3\2\2\2@A\7"+
		"\5\2\2AB\7\f\2\2B\r\3\2\2\2CD\7\f\2\2D\17\3\2\2\2EF\7\6\2\2FG\7\f\2\2"+
		"G\21\3\2\2\2HI\7\7\2\2I\23\3\2\2\2JK\7\b\2\2KL\7\f\2\2L\25\3\2\2\2MN\7"+
		"\t\2\2NO\7\f\2\2O\27\3\2\2\2PQ\7\n\2\2Q\31\3\2\2\2RS\7\13\2\2S\33\3\2"+
		"\2\2TU\7\16\2\2U\35\3\2\2\2\b!#\608;>";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}