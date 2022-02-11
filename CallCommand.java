package project8;

public class CallCommand {
    public static String translate(String nameOfCaller, short indexOfCall, String nameOfCallee,
                                   short numberOfArguments) {
        String returnAddressLabel = nameOfCaller + "$ret" + indexOfCall;

        return """
                @%s
                D=A
                """.formatted(returnAddressLabel) + Utils.PUSH_D + """
                @LCL
                D=M
                """ + Utils.PUSH_D + """
                @ARG
                D=M
                """ + Utils.PUSH_D + """
                @THIS
                D=M
                """ + Utils.PUSH_D + """
                @THAT
                D=M
                """ + Utils.PUSH_D + """
                @SP
                D=M
                @%d
                D=D-A
                @ARG
                M=D
                @SP
                D=M
                @LCL
                M=D
                """.formatted(5 + numberOfArguments)
                + GoToCommand.translate(nameOfCallee) + """
                (%s)
                """.formatted(returnAddressLabel);
    }
}
