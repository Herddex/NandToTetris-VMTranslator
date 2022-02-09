package project7;

public class BinaryOperationCommand {
    public static String translate(String commandName) throws SyntaxError {
        char operator = switch(commandName) {
            case "add" -> '+';
            case "sub" -> '-';
            case "and" -> '&';
            case "or" -> '|';
            default -> throw new SyntaxError("Unrecognized binary operation");
        };

        return """
                @SP
                AM=M-1
                D=M
                A=A-1
                """ +
                switch(operator) {
                    case '-' -> "M=M-D\n";
                    case '+', '&', '|' -> "M=D%cM\n".formatted(operator);
                    default -> "";
                };
    }
}
