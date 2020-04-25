// Generated from /home/simone/IdeaProjects/SmartMessaging/smartmessaging/Server/src/main/java/server/parser/SmartMessage.g4 by ANTLR 4.8
package server.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SmartMessageParser}.
 */
public interface SmartMessageListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SmartMessageParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(SmartMessageParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link SmartMessageParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(SmartMessageParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link SmartMessageParser#priorita}.
	 * @param ctx the parse tree
	 */
	void enterPriorita(SmartMessageParser.PrioritaContext ctx);
	/**
	 * Exit a parse tree produced by {@link SmartMessageParser#priorita}.
	 * @param ctx the parse tree
	 */
	void exitPriorita(SmartMessageParser.PrioritaContext ctx);
	/**
	 * Enter a parse tree produced by {@link SmartMessageParser#invia}.
	 * @param ctx the parse tree
	 */
	void enterInvia(SmartMessageParser.InviaContext ctx);
	/**
	 * Exit a parse tree produced by {@link SmartMessageParser#invia}.
	 * @param ctx the parse tree
	 */
	void exitInvia(SmartMessageParser.InviaContext ctx);
	/**
	 * Enter a parse tree produced by {@link SmartMessageParser#message}.
	 * @param ctx the parse tree
	 */
	void enterMessage(SmartMessageParser.MessageContext ctx);
	/**
	 * Exit a parse tree produced by {@link SmartMessageParser#message}.
	 * @param ctx the parse tree
	 */
	void exitMessage(SmartMessageParser.MessageContext ctx);
	/**
	 * Enter a parse tree produced by {@link SmartMessageParser#opzioni}.
	 * @param ctx the parse tree
	 */
	void enterOpzioni(SmartMessageParser.OpzioniContext ctx);
	/**
	 * Exit a parse tree produced by {@link SmartMessageParser#opzioni}.
	 * @param ctx the parse tree
	 */
	void exitOpzioni(SmartMessageParser.OpzioniContext ctx);
	/**
	 * Enter a parse tree produced by {@link SmartMessageParser#titolo}.
	 * @param ctx the parse tree
	 */
	void enterTitolo(SmartMessageParser.TitoloContext ctx);
	/**
	 * Exit a parse tree produced by {@link SmartMessageParser#titolo}.
	 * @param ctx the parse tree
	 */
	void exitTitolo(SmartMessageParser.TitoloContext ctx);
	/**
	 * Enter a parse tree produced by {@link SmartMessageParser#utente}.
	 * @param ctx the parse tree
	 */
	void enterUtente(SmartMessageParser.UtenteContext ctx);
	/**
	 * Exit a parse tree produced by {@link SmartMessageParser#utente}.
	 * @param ctx the parse tree
	 */
	void exitUtente(SmartMessageParser.UtenteContext ctx);
	/**
	 * Enter a parse tree produced by {@link SmartMessageParser#corpo}.
	 * @param ctx the parse tree
	 */
	void enterCorpo(SmartMessageParser.CorpoContext ctx);
	/**
	 * Exit a parse tree produced by {@link SmartMessageParser#corpo}.
	 * @param ctx the parse tree
	 */
	void exitCorpo(SmartMessageParser.CorpoContext ctx);
	/**
	 * Enter a parse tree produced by {@link SmartMessageParser#cc}.
	 * @param ctx the parse tree
	 */
	void enterCc(SmartMessageParser.CcContext ctx);
	/**
	 * Exit a parse tree produced by {@link SmartMessageParser#cc}.
	 * @param ctx the parse tree
	 */
	void exitCc(SmartMessageParser.CcContext ctx);
	/**
	 * Enter a parse tree produced by {@link SmartMessageParser#img}.
	 * @param ctx the parse tree
	 */
	void enterImg(SmartMessageParser.ImgContext ctx);
	/**
	 * Exit a parse tree produced by {@link SmartMessageParser#img}.
	 * @param ctx the parse tree
	 */
	void exitImg(SmartMessageParser.ImgContext ctx);
	/**
	 * Enter a parse tree produced by {@link SmartMessageParser#format}.
	 * @param ctx the parse tree
	 */
	void enterFormat(SmartMessageParser.FormatContext ctx);
	/**
	 * Exit a parse tree produced by {@link SmartMessageParser#format}.
	 * @param ctx the parse tree
	 */
	void exitFormat(SmartMessageParser.FormatContext ctx);
	/**
	 * Enter a parse tree produced by {@link SmartMessageParser#new_mess}.
	 * @param ctx the parse tree
	 */
	void enterNew_mess(SmartMessageParser.New_messContext ctx);
	/**
	 * Exit a parse tree produced by {@link SmartMessageParser#new_mess}.
	 * @param ctx the parse tree
	 */
	void exitNew_mess(SmartMessageParser.New_messContext ctx);
	/**
	 * Enter a parse tree produced by {@link SmartMessageParser#duepunti}.
	 * @param ctx the parse tree
	 */
	void enterDuepunti(SmartMessageParser.DuepuntiContext ctx);
	/**
	 * Exit a parse tree produced by {@link SmartMessageParser#duepunti}.
	 * @param ctx the parse tree
	 */
	void exitDuepunti(SmartMessageParser.DuepuntiContext ctx);
	/**
	 * Enter a parse tree produced by {@link SmartMessageParser#stats}.
	 * @param ctx the parse tree
	 */
	void enterStats(SmartMessageParser.StatsContext ctx);
	/**
	 * Exit a parse tree produced by {@link SmartMessageParser#stats}.
	 * @param ctx the parse tree
	 */
	void exitStats(SmartMessageParser.StatsContext ctx);
	/**
	 * Enter a parse tree produced by {@link SmartMessageParser#exit}.
	 * @param ctx the parse tree
	 */
	void enterExit(SmartMessageParser.ExitContext ctx);
	/**
	 * Exit a parse tree produced by {@link SmartMessageParser#exit}.
	 * @param ctx the parse tree
	 */
	void exitExit(SmartMessageParser.ExitContext ctx);
}