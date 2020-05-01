// Generated from /home/simone/IdeaProjects/SmartMessaging/smartmessaging/Server/src/main/java/server/parser/SmartMessage.g4 by ANTLR 4.8
package server.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SmartMessageParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, TESTO=11, NUM=12, EXIT=13, LINE_COMMENT=14, WS=15;
	public static final int
		RULE_program = 0, RULE_priorita = 1, RULE_invia = 2, RULE_stats = 3, RULE_message = 4, 
		RULE_new_mess = 5, RULE_dest = 6, RULE_duepunti = 7, RULE_titolo = 8, 
		RULE_corpo = 9, RULE_opzioni = 10, RULE_utente = 11, RULE_cc = 12, RULE_img = 13, 
		RULE_format = 14, RULE_exit = 15;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "priorita", "invia", "stats", "message", "new_mess", "dest", 
			"duepunti", "titolo", "corpo", "opzioni", "utente", "cc", "img", "format", 
			"exit"
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

	@Override
	public String getGrammarFileName() { return "SmartMessage.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SmartMessageParser(TokenStream input) {
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
		public List<StatsContext> stats() {
			return getRuleContexts(StatsContext.class);
		}
		public StatsContext stats(int i) {
			return getRuleContext(StatsContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).exitProgram(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(36); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(36);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__3:
					{
					setState(32);
					message();
					}
					break;
				case T__0:
					{
					setState(33);
					priorita();
					}
					break;
				case T__1:
					{
					setState(34);
					invia();
					}
					break;
				case T__2:
					{
					setState(35);
					stats();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(38); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__3))) != 0) );
			setState(40);
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
		public TerminalNode NUM() { return getToken(SmartMessageParser.NUM, 0); }
		public PrioritaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_priorita; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).enterPriorita(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).exitPriorita(this);
		}
	}

	public final PrioritaContext priorita() throws RecognitionException {
		PrioritaContext _localctx = new PrioritaContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_priorita);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42);
			match(T__0);
			setState(43);
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
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).enterInvia(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).exitInvia(this);
		}
	}

	public final InviaContext invia() throws RecognitionException {
		InviaContext _localctx = new InviaContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_invia);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
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

	public static class StatsContext extends ParserRuleContext {
		public UtenteContext utente() {
			return getRuleContext(UtenteContext.class,0);
		}
		public StatsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stats; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).enterStats(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).exitStats(this);
		}
	}

	public final StatsContext stats() throws RecognitionException {
		StatsContext _localctx = new StatsContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_stats);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			match(T__2);
			setState(49);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TESTO) {
				{
				setState(48);
				utente();
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
		public List<DestContext> dest() {
			return getRuleContexts(DestContext.class);
		}
		public DestContext dest(int i) {
			return getRuleContext(DestContext.class,i);
		}
		public MessageContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_message; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).enterMessage(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).exitMessage(this);
		}
	}

	public final MessageContext message() throws RecognitionException {
		MessageContext _localctx = new MessageContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_message);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			new_mess();
			setState(53); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(52);
				dest();
				}
				}
				setState(55); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==TESTO );
			setState(57);
			duepunti();
			setState(58);
			titolo();
			setState(59);
			corpo();
			setState(60);
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

	public static class New_messContext extends ParserRuleContext {
		public New_messContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_new_mess; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).enterNew_mess(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).exitNew_mess(this);
		}
	}

	public final New_messContext new_mess() throws RecognitionException {
		New_messContext _localctx = new New_messContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_new_mess);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			match(T__3);
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

	public static class DestContext extends ParserRuleContext {
		public UtenteContext utente() {
			return getRuleContext(UtenteContext.class,0);
		}
		public DestContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dest; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).enterDest(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).exitDest(this);
		}
	}

	public final DestContext dest() throws RecognitionException {
		DestContext _localctx = new DestContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_dest);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			utente();
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
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).enterDuepunti(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).exitDuepunti(this);
		}
	}

	public final DuepuntiContext duepunti() throws RecognitionException {
		DuepuntiContext _localctx = new DuepuntiContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_duepunti);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
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

	public static class TitoloContext extends ParserRuleContext {
		public TerminalNode TESTO() { return getToken(SmartMessageParser.TESTO, 0); }
		public TitoloContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_titolo; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).enterTitolo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).exitTitolo(this);
		}
	}

	public final TitoloContext titolo() throws RecognitionException {
		TitoloContext _localctx = new TitoloContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_titolo);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68);
			match(T__5);
			setState(69);
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
		public TerminalNode TESTO() { return getToken(SmartMessageParser.TESTO, 0); }
		public CorpoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_corpo; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).enterCorpo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).exitCorpo(this);
		}
	}

	public final CorpoContext corpo() throws RecognitionException {
		CorpoContext _localctx = new CorpoContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_corpo);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			match(T__6);
			setState(72);
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
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).enterOpzioni(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).exitOpzioni(this);
		}
	}

	public final OpzioniContext opzioni() throws RecognitionException {
		OpzioniContext _localctx = new OpzioniContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_opzioni);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(74);
				cc();
				}
			}

			setState(78);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8) {
				{
				setState(77);
				img();
				}
			}

			setState(81);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__9) {
				{
				setState(80);
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

	public static class UtenteContext extends ParserRuleContext {
		public TerminalNode TESTO() { return getToken(SmartMessageParser.TESTO, 0); }
		public UtenteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_utente; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).enterUtente(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).exitUtente(this);
		}
	}

	public final UtenteContext utente() throws RecognitionException {
		UtenteContext _localctx = new UtenteContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_utente);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83);
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
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).enterCc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).exitCc(this);
		}
	}

	public final CcContext cc() throws RecognitionException {
		CcContext _localctx = new CcContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_cc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
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

	public static class ImgContext extends ParserRuleContext {
		public TerminalNode TESTO() { return getToken(SmartMessageParser.TESTO, 0); }
		public ImgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_img; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).enterImg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).exitImg(this);
		}
	}

	public final ImgContext img() throws RecognitionException {
		ImgContext _localctx = new ImgContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_img);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(87);
			match(T__8);
			setState(88);
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
		public TerminalNode TESTO() { return getToken(SmartMessageParser.TESTO, 0); }
		public FormatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_format; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).enterFormat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).exitFormat(this);
		}
	}

	public final FormatContext format() throws RecognitionException {
		FormatContext _localctx = new FormatContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_format);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			match(T__9);
			setState(91);
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

	public static class ExitContext extends ParserRuleContext {
		public TerminalNode EXIT() { return getToken(SmartMessageParser.EXIT, 0); }
		public ExitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).enterExit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmartMessageListener ) ((SmartMessageListener)listener).exitExit(this);
		}
	}

	public final ExitContext exit() throws RecognitionException {
		ExitContext _localctx = new ExitContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_exit);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\21b\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\3\2\3\2\3\2"+
		"\6\2\'\n\2\r\2\16\2(\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\5\3\5\5\5\64\n\5\3"+
		"\6\3\6\6\68\n\6\r\6\16\69\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t"+
		"\3\n\3\n\3\n\3\13\3\13\3\13\3\f\5\fN\n\f\3\f\5\fQ\n\f\3\f\5\fT\n\f\3\r"+
		"\3\r\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\21\2\2\22\2\4"+
		"\6\b\n\f\16\20\22\24\26\30\32\34\36 \2\2\2Z\2&\3\2\2\2\4,\3\2\2\2\6/\3"+
		"\2\2\2\b\61\3\2\2\2\n\65\3\2\2\2\f@\3\2\2\2\16B\3\2\2\2\20D\3\2\2\2\22"+
		"F\3\2\2\2\24I\3\2\2\2\26M\3\2\2\2\30U\3\2\2\2\32W\3\2\2\2\34Y\3\2\2\2"+
		"\36\\\3\2\2\2 _\3\2\2\2\"\'\5\n\6\2#\'\5\4\3\2$\'\5\6\4\2%\'\5\b\5\2&"+
		"\"\3\2\2\2&#\3\2\2\2&$\3\2\2\2&%\3\2\2\2\'(\3\2\2\2(&\3\2\2\2()\3\2\2"+
		"\2)*\3\2\2\2*+\5 \21\2+\3\3\2\2\2,-\7\3\2\2-.\7\16\2\2.\5\3\2\2\2/\60"+
		"\7\4\2\2\60\7\3\2\2\2\61\63\7\5\2\2\62\64\5\30\r\2\63\62\3\2\2\2\63\64"+
		"\3\2\2\2\64\t\3\2\2\2\65\67\5\f\7\2\668\5\16\b\2\67\66\3\2\2\289\3\2\2"+
		"\29\67\3\2\2\29:\3\2\2\2:;\3\2\2\2;<\5\20\t\2<=\5\22\n\2=>\5\24\13\2>"+
		"?\5\26\f\2?\13\3\2\2\2@A\7\6\2\2A\r\3\2\2\2BC\5\30\r\2C\17\3\2\2\2DE\7"+
		"\7\2\2E\21\3\2\2\2FG\7\b\2\2GH\7\r\2\2H\23\3\2\2\2IJ\7\t\2\2JK\7\r\2\2"+
		"K\25\3\2\2\2LN\5\32\16\2ML\3\2\2\2MN\3\2\2\2NP\3\2\2\2OQ\5\34\17\2PO\3"+
		"\2\2\2PQ\3\2\2\2QS\3\2\2\2RT\5\36\20\2SR\3\2\2\2ST\3\2\2\2T\27\3\2\2\2"+
		"UV\7\r\2\2V\31\3\2\2\2WX\7\n\2\2X\33\3\2\2\2YZ\7\13\2\2Z[\7\r\2\2[\35"+
		"\3\2\2\2\\]\7\f\2\2]^\7\r\2\2^\37\3\2\2\2_`\7\17\2\2`!\3\2\2\2\t&(\63"+
		"9MPS";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}