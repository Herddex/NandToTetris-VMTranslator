package project7;

public class UnaryOperationCommand {
    public static String translate(String commandName) throws SyntaxError {
        return """
                @SP
                A=M-1
                M=%cM
                """.formatted(switch (commandName) {
                    case "neg" -> '-';
                    case "not" -> '!';
                    default -> throw new SyntaxError("Unrecognized unary operator");
        });
    }
}
