package project8;

public class IfGoToCommand {
    public static String translate(String fullyQualifiedLabel) {
        //jump to labelName if pop(stack) != false
        return Utils.POP_D + """
                @%s
                D;JNE
                """.formatted(fullyQualifiedLabel);
    }
}
