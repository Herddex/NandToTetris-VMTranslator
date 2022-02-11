package project8;

public class GoToCommand {
    public static String translate(String fullyQualifiedLabel) {
        return """
                @%s
                0;JMP
                """.formatted(fullyQualifiedLabel);
    }
}
