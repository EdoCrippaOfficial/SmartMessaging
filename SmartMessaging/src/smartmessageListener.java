// Generated from /home/simone/IdeaProjects/SmartMessaging/smartmessaging/SmartMessaging/src/smartmessage.g4 by ANTLR 4.8
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link smartmessageParser}.
 */
public interface smartmessageListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link smartmessageParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(smartmessageParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link smartmessageParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(smartmessageParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link smartmessageParser#priorita}.
	 * @param ctx the parse tree
	 */
	void enterPriorita(smartmessageParser.PrioritaContext ctx);
	/**
	 * Exit a parse tree produced by {@link smartmessageParser#priorita}.
	 * @param ctx the parse tree
	 */
	void exitPriorita(smartmessageParser.PrioritaContext ctx);
	/**
	 * Enter a parse tree produced by {@link smartmessageParser#invia}.
	 * @param ctx the parse tree
	 */
	void enterInvia(smartmessageParser.InviaContext ctx);
	/**
	 * Exit a parse tree produced by {@link smartmessageParser#invia}.
	 * @param ctx the parse tree
	 */
	void exitInvia(smartmessageParser.InviaContext ctx);
	/**
	 * Enter a parse tree produced by {@link smartmessageParser#message}.
	 * @param ctx the parse tree
	 */
	void enterMessage(smartmessageParser.MessageContext ctx);
	/**
	 * Exit a parse tree produced by {@link smartmessageParser#message}.
	 * @param ctx the parse tree
	 */
	void exitMessage(smartmessageParser.MessageContext ctx);
	/**
	 * Enter a parse tree produced by {@link smartmessageParser#opzioni}.
	 * @param ctx the parse tree
	 */
	void enterOpzioni(smartmessageParser.OpzioniContext ctx);
	/**
	 * Exit a parse tree produced by {@link smartmessageParser#opzioni}.
	 * @param ctx the parse tree
	 */
	void exitOpzioni(smartmessageParser.OpzioniContext ctx);
	/**
	 * Enter a parse tree produced by {@link smartmessageParser#titolo}.
	 * @param ctx the parse tree
	 */
	void enterTitolo(smartmessageParser.TitoloContext ctx);
	/**
	 * Exit a parse tree produced by {@link smartmessageParser#titolo}.
	 * @param ctx the parse tree
	 */
	void exitTitolo(smartmessageParser.TitoloContext ctx);
	/**
	 * Enter a parse tree produced by {@link smartmessageParser#destinatario}.
	 * @param ctx the parse tree
	 */
	void enterDestinatario(smartmessageParser.DestinatarioContext ctx);
	/**
	 * Exit a parse tree produced by {@link smartmessageParser#destinatario}.
	 * @param ctx the parse tree
	 */
	void exitDestinatario(smartmessageParser.DestinatarioContext ctx);
	/**
	 * Enter a parse tree produced by {@link smartmessageParser#corpo}.
	 * @param ctx the parse tree
	 */
	void enterCorpo(smartmessageParser.CorpoContext ctx);
	/**
	 * Exit a parse tree produced by {@link smartmessageParser#corpo}.
	 * @param ctx the parse tree
	 */
	void exitCorpo(smartmessageParser.CorpoContext ctx);
	/**
	 * Enter a parse tree produced by {@link smartmessageParser#cc}.
	 * @param ctx the parse tree
	 */
	void enterCc(smartmessageParser.CcContext ctx);
	/**
	 * Exit a parse tree produced by {@link smartmessageParser#cc}.
	 * @param ctx the parse tree
	 */
	void exitCc(smartmessageParser.CcContext ctx);
	/**
	 * Enter a parse tree produced by {@link smartmessageParser#img}.
	 * @param ctx the parse tree
	 */
	void enterImg(smartmessageParser.ImgContext ctx);
	/**
	 * Exit a parse tree produced by {@link smartmessageParser#img}.
	 * @param ctx the parse tree
	 */
	void exitImg(smartmessageParser.ImgContext ctx);
	/**
	 * Enter a parse tree produced by {@link smartmessageParser#format}.
	 * @param ctx the parse tree
	 */
	void enterFormat(smartmessageParser.FormatContext ctx);
	/**
	 * Exit a parse tree produced by {@link smartmessageParser#format}.
	 * @param ctx the parse tree
	 */
	void exitFormat(smartmessageParser.FormatContext ctx);
	/**
	 * Enter a parse tree produced by {@link smartmessageParser#new_mess}.
	 * @param ctx the parse tree
	 */
	void enterNew_mess(smartmessageParser.New_messContext ctx);
	/**
	 * Exit a parse tree produced by {@link smartmessageParser#new_mess}.
	 * @param ctx the parse tree
	 */
	void exitNew_mess(smartmessageParser.New_messContext ctx);
	/**
	 * Enter a parse tree produced by {@link smartmessageParser#duepunti}.
	 * @param ctx the parse tree
	 */
	void enterDuepunti(smartmessageParser.DuepuntiContext ctx);
	/**
	 * Exit a parse tree produced by {@link smartmessageParser#duepunti}.
	 * @param ctx the parse tree
	 */
	void exitDuepunti(smartmessageParser.DuepuntiContext ctx);
}