package com.robintegg.j2html.app.generator.source;

import j2html.rendering.IndentedHtml;
import j2html.tags.DomContent;
import j2html.tags.specialized.DivTag;

import java.io.IOException;

import static j2html.TagCreator.*;

public class HtmlTest {

    public static void main(String[] args) throws IOException {

        DomContent output = div()
                .with(
                        text("For example, "),
                        code("&lt;section&lt;"),
                        text(" should be wrapped as inline.")
                );

        System.out.println( output.render(IndentedHtml.inMemory()) );

    }

}
