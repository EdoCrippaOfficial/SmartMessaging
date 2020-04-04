package elevati.inc.parser.antlr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import elevati.inc.parser.Message;

public class smartmessageCustomListener extends smartmessageBaseListener {

    private Message currentMessage;
    private List<Message> messages;
    private Set<String> acceptedFormats;
    private String consoleOutput, warnings;

    public smartmessageCustomListener() {
        currentMessage = new Message();
        acceptedFormats = new HashSet<>();
        messages = new ArrayList<>();
        consoleOutput = "";
        warnings = "";
        String[] formats = {"none", "all caps", "capitalization", "lowercase"};
        Collections.addAll(acceptedFormats, formats);
    }

    public String getConsoleOutput() {
        return consoleOutput;
    }

    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public void exitProgram(smartmessageParser.ProgramContext ctx) {
        consoleOutput += warnings;
    }

    @Override
    public void exitPriorita(smartmessageParser.PrioritaContext ctx) {
        if (ctx.NUM() != null) {
            currentMessage.setPriority(Integer.parseInt(ctx.NUM().getText()));
            consoleOutput += "PRIORITA' " + ctx.NUM().getText() + "\n";
        } else {
            currentMessage.setPriority(1);
            warnings += "\nWARNING: unspecified priority. Setting it to \"1\"\n";
        }
    }

    @Override
    public void enterMessage(smartmessageParser.MessageContext ctx) {
        Message newMessage = new Message();
        messages.add(newMessage);
        currentMessage = newMessage;
    }

    @Override
    public void exitInvia(smartmessageParser.InviaContext ctx) {
        consoleOutput += "INVIA" + "\n";
        //messages.clear();
        //currentMessage = null;
    }

    @Override
    public void exitTitolo(smartmessageParser.TitoloContext ctx) {
        if (ctx.TESTO() != null) {
            String text = ctx.TESTO().getText();
            currentMessage.setTitle(removeQuotes(text));
            consoleOutput += "TITOLO " + text + "\n";
        } else {
            currentMessage.setTitle("");
            warnings += "\nWARNING: empty title.";
        }
    }

    @Override
    public void exitCorpo(smartmessageParser.CorpoContext ctx) {
        if (ctx.TESTO() != null) {
            String text = ctx.TESTO().getText();
            currentMessage.setBody(removeQuotes(text));
            consoleOutput += "CORPO " + text + "\n";
        } else {
            currentMessage.setBody("");
            warnings += "\nWARNING: empty body.\n";
        }
    }

    @Override
    public void exitOpzioni(smartmessageParser.OpzioniContext ctx) {
        if (ctx.cc() != null) {
            if (currentMessage.getReceivers().size() == 1) {
                warnings += "\nWARNING: can't enable CC with only one receiver\n";
            }
            currentMessage.setCc(true);
            consoleOutput += "CC TRUE" + "\n";
        } else {
            currentMessage.setCc(false);
            consoleOutput += "CC FALSE" + "\n";
        }
    }

    @Override
    public void exitImg(smartmessageParser.ImgContext ctx) {
        if (ctx.TESTO() != null) {
            String text = ctx.TESTO().getText();
            currentMessage.setImg(removeQuotes(text));
            consoleOutput += "IMG " + text + "\n";
        } else {
            currentMessage.setImg("");
            warnings += "\nWARNING: unspecified image link\n";
        }
    }

    @Override
    public void exitDestinatario(smartmessageParser.DestinatarioContext ctx) {
        if (ctx.TESTO() != null) {
            String text = ctx.TESTO().getText();
            currentMessage.addReceiver(removeQuotes(text));
            consoleOutput += "DESTINATARIO " + text + "\n";
        } else {
            warnings += "\nWARNING: empty receiver\n";
        }
    }

    @Override
    public void exitFormat(smartmessageParser.FormatContext ctx) {
        if (ctx.TESTO() != null) {
            String format = removeQuotes(ctx.TESTO().getText().toLowerCase());
            consoleOutput += "FORMATTAZIONE " + format + "\n";
            if (acceptedFormats.contains(format)) {
                currentMessage.setFormat(format);
            } else {
                warnings += "\nWARNING: " + format + " is not an accepted format type. Ignoring value and setting to \"none\"\n";
                currentMessage.setFormat("none");
            }
        }
    }

    private String removeQuotes(String input){
        return input.substring(1, input.length() - 1);
    }
}
