package project8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {
    private final String inputFileName;
    private final Scanner scanner;
    private String nameOfCurrentFunction = "";
    private short callCounterOfCurrentFunction = 0;
    private int lineCounter;

    private String errorMessagePrefix()
    {
        return "Line " + lineCounter + ": ";
    }

    private Segment parseSegment(String segment) throws SyntaxError {
        return switch(segment)
        {
            case "local" -> Segment.LOCAL;
            case "argument" -> Segment.ARGUMENT;
            case "this" -> Segment.THIS;
            case "that" -> Segment.THAT;
            case "pointer" -> Segment.POINTER;
            case "temp" -> Segment.TEMP;
            case "constant" -> Segment.CONSTANT;
            case "static" -> Segment.STATIC;
            default -> throw new SyntaxError(
                    "Segment \"" + segment + "\" doest not exist.");
        };
    }

    public Parser(String inputFilePath) throws FileNotFoundException {
        File inputFile = new File(inputFilePath);
        inputFileName = inputFile.getName().replace(".vm", "");
        scanner = new Scanner(inputFile);
        lineCounter = 0;
    }

    //The next translated command, if it exists. Otherwise, null.
    public String getNextTranslatedCommand() throws SyntaxError {
        while(this.scanner.hasNextLine())
        {
            //read the next line and remove comments and leading/trailing whitespace from it:
            String line = scanner.nextLine().replaceFirst("//.*$", "").strip();

            if (!line.isEmpty()) { //if the line actually contains a command

                //Split it into words and check its syntax
                String[] parts = line.split(" ");
                boolean lengthCheckPassed = switch(parts[0]) {
                    case "push", "pop" -> parts.length == 3;
                    case "add", "sub", "neg", "and", "or", "not", "eq", "lt", "gt" -> parts.length == 1;
                    case "label" -> parts.length == 2;
                    default -> true;
                };
                if (!lengthCheckPassed)
                    throw new SyntaxError(errorMessagePrefix() + "Incorrect word count");

                try {
                    String command = switch (parts[0]) {
                        case "push" -> PushCommand.translate(
                                parseSegment(parts[1]), Short.parseShort(parts[2]), inputFileName);
                        case "pop" -> PopCommand.translate(
                                parseSegment(parts[1]), Short.parseShort(parts[2]), inputFileName);
                        case "add", "sub", "and", "or" -> BinaryOperationCommand.translate(parts[0]);
                        case "lt", "eq", "gt" -> ComparisonCommand.translate(parts[0], lineCounter);
                        case "neg", "not" -> UnaryOperationCommand.translate(parts[0]);
                        case "label" -> LabelCommand.translate(
                                Utils.fullyQualifiedLabel(nameOfCurrentFunction, parts[1]));
                        case "if-goto" -> IfGoToCommand.translate(
                                Utils.fullyQualifiedLabel(nameOfCurrentFunction, parts[1]));
                        case "goto" -> GoToCommand.translate(
                                Utils.fullyQualifiedLabel(nameOfCurrentFunction, parts[1]));
                        case "function" -> {
                            nameOfCurrentFunction = parts[1];
                            callCounterOfCurrentFunction = 0;
                            yield FunctionCommand.translate(parts[1], Short.parseShort(parts[2]));
                        }
                        case "return" -> ReturnCommand.translate();
                        case "call" -> {
                            callCounterOfCurrentFunction++;
                            yield CallCommand.translate(nameOfCurrentFunction, callCounterOfCurrentFunction, parts[1],
                                    Short.parseShort(parts[2]));
                        }
                        default -> "NOT IMPLEMENTED\n";
                    };

                    lineCounter++;
                    return "//" + line + "\n" + command;
                } catch (NumberFormatException numberFormatException) {
                    throw new SyntaxError(errorMessagePrefix() + "Numeric argument not a number");
                } catch (SyntaxError syntaxError) {
                    throw new SyntaxError(errorMessagePrefix() + syntaxError.getMessage());
                }
            }
        }
        return null;
    }

    public void close()
    {
        scanner.close();
    }
}
