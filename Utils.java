package project8;

public class Utils {
    public static final String POP_D = """
            @SP
            AM=M-1
            D=M
            """;

    static final String PUSH_D = """
                    @SP
                    AM=M+1
                    A=A-1
                    M=D
                    """;

    public static void validateOffset (Segment segment, short offset) throws SyntaxError {
        if(offset < 0 || switch (segment) { //switch evaluates to true if the offset is invalid for the given segment
            //Because offset is of type short, it is implicitly < 2^15, so no explicit check is needed there
            case TEMP -> offset > 7;
            case POINTER -> offset > 1;
            default -> false;
        })
            throw new SyntaxError("Invalid segment offset");
    }

    public static String fullyQualifiedLabel(String functionName, String labelName) {
        return "%s$%s".formatted(functionName, labelName);
    }
}
