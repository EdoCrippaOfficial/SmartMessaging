package elevati.inc.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.List;

import elevati.inc.parser.antlr.smartmessageCustomListener;
import elevati.inc.parser.antlr.smartmessageErrorListener;
import elevati.inc.parser.antlr.smartmessageLexer;
import elevati.inc.parser.antlr.smartmessageParser;

public class MessageParser {

    private boolean error;
    private String inputText, consoleText;
    private List<Message> messages;

    public MessageParser(String inputText) {
        this.inputText = inputText;
    }

    public String getConsoleText() {
        return consoleText;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void parse() {
        CharStream input = CharStreams.fromString(inputText);
        smartmessageLexer lexer = new smartmessageLexer(input);
        smartmessageParser parser = new smartmessageParser(new CommonTokenStream(lexer));
        smartmessageCustomListener listener = new smartmessageCustomListener();
        smartmessageErrorListener errorListener = new smartmessageErrorListener();
        lexer.addErrorListener(errorListener);
        parser.addErrorListener(errorListener);
        parser.addParseListener(listener);
        parser.program();
        consoleText = listener.getConsoleOutput();
        messages = listener.getMessages();
        if (parser.getNumberOfSyntaxErrors() != 0) {
            error = true;
            consoleText += errorListener.getErrors();
        }
    }
}
