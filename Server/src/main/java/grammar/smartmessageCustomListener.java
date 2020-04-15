package grammar;

import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.util.ArrayList;
import java.util.List;

public class smartmessageCustomListener extends smartmessageBaseListener {

    private int priorita = 0;
    private List<Messaggio> messaggi = new ArrayList<>();
    private Messaggio current_msg;


    @Override
    public void exitPriorita(smartmessageParser.PrioritaContext ctx) {
        priorita = Integer.parseInt(ctx.NUM().getText());
        System.out.println("PRIORITA' " + ctx.NUM().getText());
    }

    @Override
    public void exitInvia(smartmessageParser.InviaContext ctx) {
        System.out.println("INVIA");
        // Effettivamente inviamo i messaggi salvati
        messaggi.clear();
        current_msg = null;
    }

    @Override
    public void enterMessage(smartmessageParser.MessageContext ctx) {
        Messaggio new_msg = new Messaggio(priorita);
        messaggi.add(new_msg);
        current_msg = new_msg;
        System.out.println("CREATO NUOVO MESSAGGIO");
    }

    @Override
    public void exitTitolo(smartmessageParser.TitoloContext ctx) {
        String testo = removeQuotes(ctx.TESTO().getText());
        current_msg.titolo = testo;
        System.out.println("TITOLO " + testo);
    }

    @Override
    public void exitCorpo(smartmessageParser.CorpoContext ctx) {
        String testo = removeQuotes(ctx.TESTO().getText());
        current_msg.corpo = testo;
        System.out.println("CORPO " + testo);
    }

    @Override
    public void exitOpzioni(smartmessageParser.OpzioniContext ctx) {
        if (ctx.cc() != null){
            if (current_msg.destinatari.size() == 1)
                throw new ParseCancellationException("" +
                        "line " + ctx.start.getLine() + ":" +
                        ctx.start.getCharPositionInLine() + "" +
                        " Non è possibile usare CC se c'è un singolo destinatario");

            current_msg.cc = true;
            System.out.println("CC TRUE");
        } else {
            current_msg.cc = false;
            System.out.println("CC FALSE");
        }
    }

    @Override
    public void exitImg(smartmessageParser.ImgContext ctx) {
        if (ctx.TESTO() != null){
            String testo = removeQuotes(ctx.TESTO().getText());
            current_msg.img = testo;
            System.out.println("IMG " + testo);
        }
    }

    @Override
    public void exitDestinatario(smartmessageParser.DestinatarioContext ctx) {
        if (ctx.TESTO() != null) {
            String testo = removeQuotes(ctx.TESTO().getText());
            current_msg.destinatari.add(testo);
            System.out.println("DESTINATARIO " + testo);
        }
    }

    @Override
    public void exitFormat(smartmessageParser.FormatContext ctx) {
        if (ctx.TESTO() != null) {
            String testo = removeQuotes(ctx.TESTO().getText());
            if (testo.equals("All caps")){
                current_msg.formattazione = Format.ALL_CAPS;
                System.out.println("FORMATTAZIONE " + testo);
            }
        }
    }

    @Override
    public void enterExit(smartmessageParser.ExitContext ctx) {
        System.out.println("\n ***********  PROGRAMMA TERMINATO  ***********");
        System.exit(0);
    }

    private String removeQuotes(String input){
        return input.substring(1, input.length() - 1);
    }
}
