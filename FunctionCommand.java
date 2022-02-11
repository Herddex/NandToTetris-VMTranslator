package project8;

public class FunctionCommand {
    public static String translate(String functionName, short numberOfLocalVariables) {
        StringBuilder translation = new StringBuilder("""
                (%s)
                @%d
                D=A
                @SP
                AM=D+M
                """.formatted(functionName, numberOfLocalVariables));

        while(numberOfLocalVariables-- > 0) {
            translation.append("""
                    A=A-1
                    M=0
                    """);
        }

        return translation.toString();
    }
}
