package project8;

public class LabelCommand {
    public static String translate(String fullyQualifiedLabel) {
        return """
                (%s)
                """.formatted(fullyQualifiedLabel);
    }
}
