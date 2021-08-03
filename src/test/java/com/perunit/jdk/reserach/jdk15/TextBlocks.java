package com.perunit.jdk.reserach.jdk15;

public class TextBlocks {

    private static final String html = """
        <html>
            <body>
                <p class="greeting">Hello, world</p>
            </body>
        </html>
        """;

    private static final String query = """
        SELECT "EMP_ID", "LAST_NAME" FROM "EMPLOYEE_TB"
        WHERE "CITY" = 'INDIANAPOLIS'
        ORDER BY "EMP_ID", "LAST_NAME";
        """;

    private static final String code = """
        String text = \"""
            A text block inside a text block
        \""";
        """;

    private static final String oneLine = """
        Lorem ipsum dolor sit amet, consectetur adipiscing \
        elit, sed do eiusmod tempor incididunt ut labore \
        et dolore magna aliqua.\
        """;
}
