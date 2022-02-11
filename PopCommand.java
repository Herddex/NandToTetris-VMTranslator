package project8;

import static project8.Utils.POP_D;

public class PopCommand {

    /*
    R15 = segment_address + offset
    POP_D
    *R15 = D
     */
    private static final String DYNAMIC_SEGMENT_WRITE = """
            @%d
            D=A
            @%s
            D=M+D
            @R15
            M=D
            """ +
            POP_D +
            """
            @R15
            A=M
            M=D
            """;

    public static String translate(Segment segment, short offset, String inputFileName) throws SyntaxError {
        Utils.validateOffset(segment, offset);

        return switch (segment)
                {
                    case CONSTANT -> throw new SyntaxError("Cannot write to constant segment");

                    case LOCAL -> DYNAMIC_SEGMENT_WRITE.formatted(offset, "LCL");
                    case ARGUMENT -> DYNAMIC_SEGMENT_WRITE.formatted(offset, "ARG");
                    case THIS -> DYNAMIC_SEGMENT_WRITE.formatted(offset, "THIS");
                    case THAT -> DYNAMIC_SEGMENT_WRITE.formatted(offset, "THAT");

                    /*
                    *(5 + offset) = D
                     */
                    case TEMP -> POP_D + """
                    @%d
                    M=D
                    """.formatted(5 + offset);

                    /*
                    fileName.offset = D
                     */
                    case STATIC -> POP_D + """
                    @%s.%d
                    M=D
                    """.formatted(inputFileName, offset);

                    /*
                    THIS = D if offset = 0, THAT = D otherwise
                     */
                    case POINTER -> POP_D + (offset == 0 ? """
                    @THIS
                    M=D
                    """ : """
                    @THAT
                    M=D
                    """);
                };
    }
}
