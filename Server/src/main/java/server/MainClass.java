package server;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.cli.*;
import server.parser.*;

import java.io.IOException;
import java.nio.file.Paths;

public class MainClass {

    public static void main(String[] args) {

        /*      COMPILA     */
        CharStream input = cmdParser(args);
        SmartMessageLexer lexer = new SmartMessageLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(ThrowingErrorListener.INSTANCE);

        SmartMessageParser parser = new SmartMessageParser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();
        parser.addErrorListener(ThrowingErrorListener.INSTANCE);

        parser.addParseListener(new SmartMessageBaseListener());
        parser.program();

        System.out.println("\n\n*********** Compilazione avvenuta senza errori ***********\n\n");


        /*      ESEGUI     */
        lexer.reset();
        parser.reset();
        lexer = new SmartMessageLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(ThrowingErrorListener.INSTANCE);

        parser = new SmartMessageParser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();
        parser.addErrorListener(ThrowingErrorListener.INSTANCE);

        parser.addParseListener(new SmartMessageCustomListener());
        parser.program();
    }

    private static CharStream cmdParser(String[] args) {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        Option file = Option.builder("f")
                .longOpt("file")
                .argName("file")
                .hasArg()
                .desc( "input file/path to file")
                .build();
        Option string = Option.builder("s")
                .longOpt("string")
                .argName("string")
                .hasArg()
                .desc( "input string")
                .build();
        options.addOption(file)
                .addOption(string)
                .addOption("h", "help", false, "prints this help");
        HelpFormatter formatter = new HelpFormatter();
        String header = "java -jar SmartMessaging.jar <-f|-s>";
        try {
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("h")) {
                formatter.printHelp(header, options);
                System.exit(0);
            }
            int mode = 0;
            int nModes = 0;
            if (line.hasOption("file")) {
                mode = 1;
                nModes++;
            }
            if (line.hasOption("string")) {
                mode = 2;
                nModes++;
            }
            if (nModes > 1) {
                System.out.println("Errore: è possibile specificare una sola tipologia di input <-f|-s >\n");
                formatter.printHelp(header, options);
                System.exit(1);
            }
            if (mode == 1) {
                String path = line.getOptionValue("f");
                if (!path.endsWith(".sms")) {
                    System.out.println("\nWarning: il file specificato non è di tipo Smart Messaging System (.sms)");
                }
                if (path.contains("/") || path.contains("\\")) {
                    return CharStreams.fromPath(Paths.get(path));
                } else {
                    return CharStreams.fromFileName(path);
                }
            } else if (mode == 2) {
                return CharStreams.fromString(line.getOptionValue("s"));
            } else {
                System.out.println("Errore: nessun input specificato\n");
                formatter.printHelp(header, options);
                System.exit(1);
            }
        } catch (ParseException e) {
            System.out.println("Errore: comando malformato\n");
            formatter.printHelp(header, options);
            System.exit(1);
        } catch (IOException e2) {
            System.out.println("Errore: Impossibile trovare il file specificato: \n");
        }
        return null;
    }
}
