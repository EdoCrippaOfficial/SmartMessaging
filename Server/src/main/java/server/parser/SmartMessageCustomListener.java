package server.parser;

import org.antlr.v4.runtime.misc.ParseCancellationException;
import server.FirebaseHelper;
import server.Format;
import server.Messaggio;

import java.util.ArrayList;
import java.util.List;


public class SmartMessageCustomListener extends SmartMessageBaseListener {

    private int msgCounter;
    private int priorita = 1;
    private List<Messaggio> messaggi = new ArrayList<>();
    private Messaggio current_msg;


    @Override
    public void exitPriorita(SmartMessageParser.PrioritaContext ctx) {
        priorita = Integer.parseInt(ctx.NUM().getText());
        if (priorita < 1) {
            System.out.println("WARNING: non è possibile utilizzare una priorità minore di 1");
            priorita = 1;
        } else if (priorita > 5) {
            System.out.println("WARNING: non è possibile utilizzare una priorità maggiore di 5");
            priorita = 5;
        }
        System.out.println("PRIORITA' " + priorita + "\n");
    }

    @Override
    public void exitInvia(SmartMessageParser.InviaContext ctx) {
        System.out.println("INVIA\n");
        for (Messaggio mess : messaggi) {
            try {
                FirebaseHelper.getInstance().sendMessage(mess);
            }catch (RuntimeException re){
                re.printStackTrace();
            }
        }
        messaggi.clear();
        current_msg = null;
    }

    @Override
    public void enterMessage(SmartMessageParser.MessageContext ctx) {
        Messaggio new_msg = new Messaggio(priorita, ++msgCounter);
        messaggi.add(new_msg);
        current_msg = new_msg;
        System.out.println("CREATO NUOVO MESSAGGIO: #" + msgCounter);
    }

    @Override
    public void exitDest(SmartMessageParser.DestContext ctx) {
        if (ctx.utente().TESTO() != null) {
            String testo = removeQuotes(ctx.utente().TESTO().getText());
            System.out.println("DESTINATARIO " + testo);
            if (current_msg.destinatari.contains(testo)) {
                System.out.println("WARNING: " + testo + " è già presente nella lista dei destinatari e verrà ignorato");
            } else {
                current_msg.destinatari.add(testo);
            }
        }
    }

    @Override
    public void exitTitolo(SmartMessageParser.TitoloContext ctx) {
        String testo = removeQuotes(ctx.TESTO().getText());
        current_msg.titolo = testo;
        System.out.println("TITOLO " + testo);
    }

    @Override
    public void exitCorpo(SmartMessageParser.CorpoContext ctx) {
        String testo = removeQuotes(ctx.TESTO().getText());
        current_msg.corpo = testo;
        System.out.println("CORPO " + testo);
    }

    @Override
    public void exitOpzioni(SmartMessageParser.OpzioniContext ctx) {
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
    public void exitImg(SmartMessageParser.ImgContext ctx) {
        if (ctx.TESTO() != null){
            String testo = removeQuotes(ctx.TESTO().getText());
            current_msg.img = testo;
            System.out.println("IMG " + testo);
        }
    }

    @Override
    public void exitFormat(SmartMessageParser.FormatContext ctx) {
        if (ctx.TESTO() != null) {
            String testo = removeQuotes(ctx.TESTO().getText());
            System.out.println("FORMATTAZIONE " + testo);
            current_msg.formattazione = Format.fromString(testo);
        }
    }

    @Override
    public void exitStats(SmartMessageParser.StatsContext ctx) {
        if (ctx.utente() != null) {
            String utente = removeQuotes(ctx.utente().TESTO().getText());
            FirebaseHelper.getInstance().printStats(utente);
        } else {
            FirebaseHelper.getInstance().printStats();
        }
    }

    @Override
    public void enterExit(SmartMessageParser.ExitContext ctx) {
        System.out.println("\n***********  PROGRAMMA TERMINATO  ***********");
        System.exit(0);
    }

    private String removeQuotes(String input){
        return input.substring(1, input.length() - 1);
    }
}
