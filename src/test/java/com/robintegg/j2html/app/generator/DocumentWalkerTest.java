package com.robintegg.j2html.app.generator;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DocumentWalkerTest {

    @Test
    void shouldOutputPlainH1() {

        String walk = new DocumentWalker().walk(Jsoup.parse("""
                <h1>hello j2html community</h1>
                """, Parser.xmlParser()));

        assertThat(walk).isEqualTo("""
                h1()
                  .with(
                    text("hello j2html community")
                  )
                """);

    }

    @Test
    void shouldOutputTwoChildren() {

        String walk = new DocumentWalker().walk(Jsoup.parse("""
                <div>
                    <h1>Title</h1>
                    <p>some text</p>
                </div>
                """, Parser.xmlParser()));

        assertThat(walk).isEqualTo("""
                div()
                  .with(
                    h1()
                      .with(
                        text("Title")
                      ),
                    p()
                      .with(
                        text("some text")
                      )
                  )
                """);

    }

    @Test
    void shouldOutputAttributesAndChildren() {

        String walk = new DocumentWalker().walk(Jsoup.parse("""
                <form class="table rows">
                     <p>
                         <label for=name>Name</label>
                         <input type=text id=name name=name>
                     </p>
                     <p>
                         <label for=adr>Address</label>
                         <input type=text id=adr name=adr>
                     </p>
                   </form>
                """, Parser.xmlParser()));

        System.out.println(walk);

        assertThat(walk).isEqualTo("""
                form()
                  .attr("class", "table rows")
                  .with(
                    p()
                      .with(
                        label()
                          .attr("for", "name")
                          .with(
                            text("Name")
                          )
                        input()
                          .attr("type", "text")
                          .attr("id", "name")
                          .attr("name", "name")
                      )
                    p()
                      .with(
                        label()
                          .attr("for", "adr")
                          .with(
                            text("Address")
                          )
                        input()
                          .attr("type", "text")
                          .attr("id", "adr")
                          .attr("name", "adr")
                      )
                  )
                """);

    }

}