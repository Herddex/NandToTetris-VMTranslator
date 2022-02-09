package project7;

public class PushCommand {
    private static final String PUSH_D = """
            @SP
            AM=M+1
            A=A-1
            M=D
            """;

    /*
    D = *(segment_address+offset)
     */
    private static final String DYNAMIC_SEGMENT_READ = """
            @%d
            D=A
            @%s
            A=D+M
            D=M
            """;

    public static String translate(Segment segment, short offset, String inputFileName) throws SyntaxError {
        Utils.validateOffset(segment, offset);

        return switch (segment)
                {
                    /*
                    @offset
                    D=A
                     */
                    case CONSTANT -> """
                            @%d
                            D=A
                            """.formatted(offset);

                    case LOCAL -> DYNAMIC_SEGMENT_READ.formatted(offset, "LCL");
                    case ARGUMENT -> DYNAMIC_SEGMENT_READ.formatted(offset, "ARG");
                    case THIS -> DYNAMIC_SEGMENT_READ.formatted(offset, "THIS");
                    case THAT -> DYNAMIC_SEGMENT_READ.formatted(offset, "THAT");

                    /*
                    D = *(5 + offset)
                     */
                    case TEMP -> """
                    @%d
                    D=M
                    """.formatted(5 + offset);

                    /*
                    D = fileName.offset
                     */
                    case STATIC -> """
                    @%s.%d
                    D=M
                    """.formatted(inputFileName, offset);

                    /*
                    D = THIS if offset = 0, THAT otherwise
                     */
                    case POINTER -> offset == 0 ? """
                    @THIS
                    D=M
                    """ : """
                    @THAT
                    D=M
                    """;
                } + PUSH_D;
    }
}
