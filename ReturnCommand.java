package project8;

public class ReturnCommand {
    public static String translate() {
        return """
                @LCL
                D=M
                @5
                D=D-A
                @R15
                AM=D
                D=M
                @R14
                M=D
                """ + Utils.POP_D + """
                @ARG
                A=M
                M=D
                @ARG
                D=M+1
                @SP
                M=D
                @R15
                AM=M+1
                D=M
                @LCL
                M=D
                @R15
                AM=M+1
                D=M
                @ARG
                M=D
                @R15
                AM=M+1
                D=M
                @THIS
                M=D
                @R15
                AM=M+1
                D=M
                @THAT
                M=D
                @R14
                A=M
                0;JMP
                """;
    }
}
