package project8;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class CodeWriter {
    private final PrintWriter writer;
    private final List<String> pathsToInputFiles;

    public CodeWriter(List<String> pathsToInputFiles, String outputFilePath) throws FileNotFoundException {
        writer = new PrintWriter(outputFilePath);
        this.pathsToInputFiles = pathsToInputFiles;
    }

    private void writeInitCode() {
        writer.println("""
                @256
                D=A
                @SP
                M=D
                """ + CallCommand.translate("BOOT", (short) 0, "Sys.init", (short) 0));
    }

    private void writeFinishCode() {
        writer.println("@END_OF_PROGRAM\n(END_OF_PROGRAM)\n0;JMP");
    }

    public void translate() throws FileNotFoundException, SyntaxError {
        writeInitCode();

        for (String pathToInputFile : pathsToInputFiles) {
            Parser parser = new Parser(pathToInputFile);
            while(true) {
                String nextTranslatedCommand = parser.getNextTranslatedCommand();
                if (nextTranslatedCommand == null)
                    break;
                writer.println(nextTranslatedCommand);
            }
            parser.close();
        }

        writeFinishCode();
        writer.close();
    }
}
