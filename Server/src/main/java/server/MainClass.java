package server;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import server.parser.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainClass {

    public static void main(String[] args) {
        try {

            /*      COMPILA     */

            CharStream input = CharStreams.fromFileName("input.txt");
            SmartMessageLexer lexer = new SmartMessageLexer(input);
            lexer.removeErrorListeners();
            lexer.addErrorListener(ThrowingErrorListener.INSTANCE);

            SmartMessageParser parser = new SmartMessageParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(ThrowingErrorListener.INSTANCE);

            parser.addParseListener(new SmartMessageBaseListener());
            parser.program();

            System.out.println("\n\n---------------------- Compilazione avvenuta senza errori ---------------------- \n\n");


            /*      ESEGUI     */

            input = CharStreams.fromFileName("input.txt");
            lexer = new SmartMessageLexer(input);
            lexer.removeErrorListeners();
            lexer.addErrorListener(ThrowingErrorListener.INSTANCE);

            parser = new SmartMessageParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(ThrowingErrorListener.INSTANCE);

            parser.addParseListener(new SmartMessageCustomListener());
            parser.program();

        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
