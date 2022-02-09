package project7;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class VMTranslator {
    public static void main(String[] args) throws SyntaxError {
        if (args.length != 1 || !args[0].endsWith(".vm"))
            System.out.println("You most provide (a path to) a .vm file as the only command line argument.");

        else try {
            String inputFilePath = args[0];
            String outputFilePath = inputFilePath.replaceAll("\\.vm$", ".asm");

            Parser parser = new Parser(inputFilePath);
            PrintWriter printWriter = new PrintWriter(outputFilePath);

            while(true) {
                String nextTranslatedCommand = parser.getNextTranslatedCommand();
                if (nextTranslatedCommand == null)
                    break;
                printWriter.println(nextTranslatedCommand);
            }

            parser.close();
            printWriter.close();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("The given file could not be found");
        }
    }
}
