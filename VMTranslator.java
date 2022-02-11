package project8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class VMTranslator {
    public static void main(String[] args) {
        if (args.length != 1)
            System.out.println("You most provide a path to either a .vm file or a directory containing only .vm " +
                    "files as the only command line argument.");

        else try {
            String inputPath = args[0];
            File inputFile = new File(inputPath);

            String outputFilePath;
            List<String> pathsToInputFiles;

            if (inputFile.isFile()) { //it's a single file, so it must have a .vm extension
                if (!inputPath.endsWith(".vm")) {
                    System.out.println("The given file must be a .vm file");
                    return;
                }
                pathsToInputFiles = List.of(inputPath);
                outputFilePath = inputPath.replaceAll("\\.vm$", ".asm");
            } else if (inputFile.isDirectory()) {
                pathsToInputFiles = Arrays.stream(inputFile.listFiles())
                        .filter(file -> file.getName().endsWith(".vm"))
                        .map(File::getPath).toList();
                outputFilePath = new File(inputFile, inputFile.getName() + ".asm").getPath();
            } else {
                System.out.println("Unrecognized file type. Supported types are normal files and normal directories");
                return;
            }

            CodeWriter codeWriter = new CodeWriter(pathsToInputFiles, outputFilePath);
            codeWriter.translate();

        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("The given file(s) could not be found");
        } catch (SyntaxError syntaxError) {
            System.out.println("Syntax error - " + syntaxError.getMessage());
        }
    }
}
