package com.robintegg.j2html.app.generator;

import j2html.TagCreator;
import j2html.utils.EscapeUtil;

public class J2HtmlWrapper {
    private final boolean includeImports;
    private final boolean useExtensions;
    private final String template;

    public J2HtmlWrapper(boolean includeImports, boolean useExtensions, String template) {
        this.includeImports = includeImports;
        this.useExtensions = useExtensions;
        this.template = template;
    }

    public String getJ2HtmlCode(String htmlText, String j2HtmlCode) {
        if("uitest".equals(template)) {
            return UITEST_TEMPLATE
                    .replace("<<INSERT_CODE_HERE>>", j2HtmlCode)
                    .replace("<<INSERT_CONTENT_HERE>>", EscapeUtil.escape(htmlText) );
        } else if("uitestsnippet".equals(template)) {
            return UITEST_SNIPPET_TEMPLATE
                    .replace("<<INSERT_CODE_HERE>>", j2HtmlCode)
                    .replace("<<INSERT_CONTENT_HERE>>", EscapeUtil.escape(htmlText) );
        } else {
            StringBuilder b = new StringBuilder();
            if (includeImports) {
                b.append("import static j2html.TagCreator.*;\n");
                if (useExtensions) {
                    b.append("import static dev.rebelcraft.j2html.ext.ExtendedTagCreator.*;\n");
                }
                b.append("\n");
            }
            b.append(j2HtmlCode);
            b.append("\n");
            return b.toString();
        }
    }

    private static final String UITEST_TEMPLATE = """
        package dev.rebelcraft.j2html.ext.layout;
        
        import dev.rebelcraft.uidocs.UiDocumentation;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.junit.jupiter.api.TestInfo;
        
        import static j2html.TagCreator.*;
        import static dev.rebelcraft.j2html.ext.ExtendedTagCreator.*;
        import static org.junit.jupiter.api.Assertions.assertEquals;
        
        class ExampleTest {
        
            private UiDocumentation uiDocumentation;
        
            @BeforeEach
            void init(TestInfo testInfo) {
                uiDocumentation = new UiDocumentation(testInfo);
            }
        
            @Test
            void testExample() throws Exception {
        
                String renderedHtml = uiDocumentation.render(
                        <<INSERT_CODE_HERE>>
                );
        
                //language=HTML
                assertEquals(\"\"\"
                    <<INSERT_CONTENT_HERE>>
                    \"\"\", renderedHtml);
        
                // document
                uiDocumentation.document(\"test-example\", renderedHtml);
        
                uiDocumentation.documentSource(\"test-example\");
        
            }
        
        }
        """;

    private static final String UITEST_SNIPPET_TEMPLATE = """
        @Test
        void testExample() throws Exception {
    
            String renderedHtml = uiDocumentation.render(
                    <<INSERT_CODE_HERE>>
            );
    
            //language=HTML
            assertEquals(\"\"\"
                <<INSERT_CONTENT_HERE>>
                \"\"\", renderedHtml);
    
            // document
            uiDocumentation.document(\"test-example\", renderedHtml);
    
            uiDocumentation.documentSource(\"test-example\");
    
        }
        """;

}
