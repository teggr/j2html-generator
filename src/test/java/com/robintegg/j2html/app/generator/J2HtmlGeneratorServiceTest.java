package com.robintegg.j2html.app.generator;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class J2HtmlGeneratorServiceTest {

    J2HtmlGeneratorService service = new J2HtmlGeneratorService();

    @Test
    void shouldOutputSingleTagNoAttributes() {

        String walk = service.generateFromHtml("""
                <h1 />
                """);

        assertThat(walk).isEqualTo("""
                import static j2html.TagCreator.*;
                                
                h1()
                """);

    }

    @Test
    void shouldOutputSingleTextOnlyTagNoAttributes() {

        String walk = service.generateFromHtml("""
                <h1>hello j2html community</h1>
                """);

        assertThat(walk).isEqualTo("""
                import static j2html.TagCreator.*;
                                
                h1("hello j2html community")
                """);

    }

    @Test
    void shouldOutputTwoChildTextOnlyTagsNoAttributes() {

        String walk = service.generateFromHtml("""
                <div>
                    <h1>Title</h1>
                    <p>some text</p>
                </div>
                """);

        assertThat(walk).isEqualTo("""
                import static j2html.TagCreator.*;
                                
                div(
                  h1("Title"),
                  p("some text")
                )
                """);

    }

    @Test
    void shouldOutputNestedTextOnlyChildrenNoAttributes() {

        String walk = service.generateFromHtml("""
                <div>
                    <h1>Title</h1>
                    <p>
                        <a>a link</a>
                    </p>
                </div>
                """);

        assertThat(walk).isEqualTo("""
                import static j2html.TagCreator.*;
                                
                div(
                  h1("Title"),
                  p(
                    a("a link")
                  )
                )
                """);

    }

    @Test
    void shouldOutputSingleTextOnlyTagWithAttribute() {

        String walk = service.generateFromHtml("""
                <h1 class="title"/>
                """);

        assertThat(walk).isEqualTo("""
                import static j2html.TagCreator.*;
                                
                h1()
                  .attr("class", "title")
                """);

    }

    @Test
    void shouldOutputSingleTextOnlyTagWithMultipleAttributes() {

        String walk = service.generateFromHtml("""
                <h1 class="title" id="t1" />
                """);

        assertThat(walk).isEqualTo("""
                import static j2html.TagCreator.*;
                                
                h1()
                  .attr("class", "title")
                  .attr("id", "t1")
                """);

    }

    @Test
    void shouldOutputSingleTagWithTextWithAttribute() {

        String walk = service.generateFromHtml("""
                <h1 class="title">hello j2html community</h1>
                """);

        assertThat(walk).isEqualTo("""
                import static j2html.TagCreator.*;
                                
                h1("hello j2html community")
                  .attr("class", "title")
                """);

    }

    @Test
    void shouldOutputTwoChildTextOnlyTagsWithAttributes() {

        String walk = service.generateFromHtml("""
                <div>
                    <h1 class="title">Title</h1>
                    <p class="content" id="p1">some text</p>
                </div>
                """);

        assertThat(walk).isEqualTo("""
                import static j2html.TagCreator.*;
                                
                div(
                  h1("Title")
                    .attr("class", "title"),
                  p("some text")
                    .attr("class", "content")
                    .attr("id", "p1")
                )
                """);

    }

    @Test
    void shouldOutputNestedTextOnlyChildrenWithAttributes() {

        String walk = service.generateFromHtml("""
                <div>
                    <h1>Title</h1>
                    <p>
                        <a href="/some/url">a link</a>
                    </p>
                </div>
                """);

        assertThat(walk).isEqualTo("""
                import static j2html.TagCreator.*;
                                
                div(
                  h1("Title"),
                  p(
                    a("a link")
                      .attr("href", "/some/url")
                  )
                )
                """);

    }

    @Test
    void shouldOutputContainerWithTextNoAttributes() {

        String walk = service.generateFromHtml("""
                <a>hello <span>j2html community</span></a>
                """);

        assertThat(walk).isEqualTo("""
                import static j2html.TagCreator.*;
                                
                a(
                  text("hello "),
                  span("j2html community")
                )
                """);

    }

    @Test
    void shouldOutputContainerWithTextAndAttributes() {

        String walk = service.generateFromHtml("""
                <a href="/some/url">hello <span>j2html community</span></a>
                """);

        assertThat(walk).isEqualTo("""
                import static j2html.TagCreator.*;
                                
                a()
                  .attr("href", "/some/url")
                  .with(
                    text("hello "),
                    span("j2html community")
                  )
                """);

    }

    @Test
    void shouldOutputMissingFormTableLayout() {

        String walk = service.generateFromHtml("""
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
                """);

        assertThat(walk).isEqualTo("""
                import static j2html.TagCreator.*;
                                
                form()
                     .attr("class", "table rows")
                     .with(
                       p(
                         label("Name")
                           .attr("for", "name"),
                         input("")
                           .attr("type", "text")
                           .attr("id", "name")
                           .attr("name", "name")
                       ),
                       p(
                         label("Address")
                           .attr("for", "adr"),
                         input("")
                           .attr("type", "text")
                           .attr("id", "adr")
                           .attr("name", "adr")
                       )
                     )
                """);

    }

}