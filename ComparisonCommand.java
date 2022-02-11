package project8;

public class ComparisonCommand {

    public static String translate(String commandName, int lineCounter) throws SyntaxError {
        return ("""
                @SP
                AM=M-1
                D=M
                A=A-1
                D=M-D
                @COMP_TRUE%d
                D;""" +
                switch(commandName) {
                    case "lt" -> "JLT";
                    case "eq" -> "JEQ";
                    case "gt" -> "JGT";
                    default -> throw new SyntaxError("Unrecognized comparison operation");
                } + '\n' + """
                @SP
                A=M-1
                M=0
                @COMP_END%d
                0;JMP
                (COMP_TRUE%d)
                @SP
                A=M-1
                M=-1
                (COMP_END%d)
                """).formatted(lineCounter, lineCounter, lineCounter, lineCounter);
    }
}
