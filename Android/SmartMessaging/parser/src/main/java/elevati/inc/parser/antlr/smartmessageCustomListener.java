package elevati.inc.parser.antlr;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import elevati.inc.parser.Message;

public class smartmessageCustomListener extends smartmessageBaseListener {

    private Message message;
    private Set<String> acceptedFormats;
    private String consoleOutput, warnings;

    public smartmessageCustomListener() {
        message = new Message();
        acceptedFormats = new HashSet<>();
        consoleOutput = "";
        warnings = "";
        String[] formats = {"\"none\"", "\"all caps\"", "\"capitalization\"", "\"lowercase\""};
        Collections.addAll(acceptedFormats, formats);
    }

    public String getConsoleOutput() {
        return consoleOutput;
    }

    public Message getMessage() {
        return message;
    }

    @Override
    public void exitProgram(smartmessageParser.ProgramContext ctx) {
        consoleOutput += warnings;
    }

    @Override
    public void exitPriorita(smartmessageParser.PrioritaContext ctx) {
        if (ctx.NUM() != null) {
            message.setPriority(Integer.parseInt(ctx.NUM().getText()));
            consoleOutput += "PRIORITA' " + ctx.NUM().getText() + "\n";
        } else {
            message.setPriority(1);
            warnings += "\nWARNING: unspecified priority. Setting it to \"1\"\n";
        }
    }

    @Override
    public void exitInvia(smartmessageParser.InviaContext ctx) {
        consoleOutput += "INVIA" + "\n";
    }

    @Override
    public void exitTitolo(smartmessageParser.TitoloContext ctx) {
        if (ctx.TESTO() != null) {
            String text = ctx.TESTO().getText();
            message.setTitle(text.substring(1, text.length()-1));
            consoleOutput += "TITOLO " + text + "\n";
        } else {
            message.setTitle("");
            warnings += "\nWARNING: empty title.";
        }
    }

    @Override
    public void exitCorpo(smartmessageParser.CorpoContext ctx) {
        if (ctx.TESTO() != null) {
            String text = ctx.TESTO().getText();
            message.setBody(text.substring(1, text.length()-1));
            consoleOutput += "CORPO " + text + "\n";
        } else {
            message.setBody("");
            warnings += "\nWARNING: empty body.\n";
        }
    }

    @Override
    public void exitOpzioni(smartmessageParser.OpzioniContext ctx) {
        if (ctx.cc() != null) {
            message.setCc(true);
            consoleOutput += "CC TRUE" + "\n";
        } else {
            message.setCc(false);
            consoleOutput += "CC FALSE" + "\n";
        }
    }

    @Override
    public void exitImg(smartmessageParser.ImgContext ctx) {
        if (ctx.TESTO() != null) {
            String text = ctx.TESTO().getText();
            message.setImg(text.substring(1, text.length()-1));
            consoleOutput += "IMG " + text + "\n";
        } else {
            message.setImg("");
            warnings += "\nWARNING: unspecified image link\n";
        }
    }

    @Override
    public void exitDestinatario(smartmessageParser.DestinatarioContext ctx) {
        if (ctx.TESTO() != null) {
            String text = ctx.TESTO().getText();
            message.addReceiver(text.substring(1, text.length()-1));
            consoleOutput += "DESTINATARIO " + text + "\n";
        } else {
            warnings += "\nWARNING: empty receiver\n";
        }
    }

    @Override
    public void exitFormat(smartmessageParser.FormatContext ctx) {
        if (ctx.TESTO() != null) {
            String format = ctx.TESTO().getText().toLowerCase();
            consoleOutput += "FORMATTAZIONE " + format + "\n";
            if (acceptedFormats.contains(format)) {
                message.setFormat(format.substring(1, format.length()-1));
            } else {
                warnings += "\nWARNING: " + format + " is not an accepted format type. Ignoring value and setting to \"none\"\n";
                message.setFormat("none");
            }
        }
    }
}
