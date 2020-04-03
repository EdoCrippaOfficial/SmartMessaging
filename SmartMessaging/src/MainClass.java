

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainClass {

    public static void main(String[] args) {
        try {
            CharStream input = (CharStream) new ANTLRFileStream("input.txt");
            smartmessageLexer lexer = new smartmessageLexer(input);
            smartmessageParser parser = new smartmessageParser(new CommonTokenStream(lexer));
            parser.addParseListener(new smartmessageCustomListener());
            parser.program();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
