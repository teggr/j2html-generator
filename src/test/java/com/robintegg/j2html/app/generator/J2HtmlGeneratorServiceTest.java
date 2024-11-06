package com.robintegg.j2html.app.generator;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class J2HtmlGeneratorServiceTest {

    J2HtmlGeneratorService service = new J2HtmlGeneratorService();

    @Test
    void shouldOutputSingleTagNoAttributes() {

        String walk = service.generateFromHtml(null, true, null, """
                <h1 />
                """, null, null);

        assertThat(walk).isEqualTo("""
                import static j2html.TagCreator.*;
                                
                h1()
                """);

    }

    @Test
    void shouldOutputSingleTextOnlyTagNoAttributes() {

        String walk = service.generateFromHtml(null, true, null, """
                <h1>hello j2html community</h1>
                """, null, null);

        assertThat(walk).isEqualTo("""
                import static j2html.TagCreator.*;
                                
                h1("hello j2html community")
                """);

    }

    @Test
    void shouldOutputTwoChildTextOnlyTagsNoAttributes() {

        String walk = service.generateFromHtml(null, true, null, """
                <div>
                    <h1>Title</h1>
                    <p>some text</p>
                </div>
                """, null, null);

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

        String walk = service.generateFromHtml(null, true, null, """
                <div>
                    <h1>Title</h1>
                    <p>
                        <a>a link</a>
                    </p>
                </div>
                """, null, null);

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

        String walk = service.generateFromHtml(null, true, null, """
                <h1 class="title"/>
                """, null, null);

        assertThat(walk).isEqualTo("""
                import static j2html.TagCreator.*;
                                
                h1()
                  .withClass("title")
                """);

    }

    @Test
    void shouldOutputSingleTextOnlyTagWithMultipleAttributes() {

        String walk = service.generateFromHtml(null, true, null, """
                <h1 class="title" id="t1" />
                """, null, null);

        assertThat(walk).isEqualTo("""
                import static j2html.TagCreator.*;
                                
                h1()
                  .withClass("title")
                  .withId("t1")
                """);

    }

    @Test
    void shouldOutputSingleTagWithTextWithAttribute() {

        String walk = service.generateFromHtml(null, true, null, """
                <h1 class="title">hello j2html community</h1>
                """, null, null);

        assertThat(walk).isEqualTo("""
                import static j2html.TagCreator.*;
                                
                h1("hello j2html community")
                  .withClass("title")
                """);

    }

    @Test
    void shouldOutputTwoChildTextOnlyTagsWithAttributes() {

        String walk = service.generateFromHtml(null, true, null, """
                <div>
                    <h1 class="title">Title</h1>
                    <p class="content" id="p1">some text</p>
                </div>
                """, null, null);

        assertThat(walk).isEqualTo("""
                import static j2html.TagCreator.*;
                                
                div(
                  h1("Title")
                    .withClass("title"),
                  p("some text")
                    .withClass("content")
                    .withId("p1")
                )
                """);

    }

    @Test
    void shouldOutputNestedTextOnlyChildrenWithAttributes() {

        String walk = service.generateFromHtml(null, true, null, """
                <div>
                    <h1>Title</h1>
                    <p>
                        <a href="/some/url">a link</a>
                    </p>
                </div>
                """, null, null);

        assertThat(walk).isEqualTo("""
                import static j2html.TagCreator.*;
                                
                div(
                  h1("Title"),
                  p(
                    a("a link")
                      .withHref("/some/url")
                  )
                )
                """);

    }

    @Test
    void shouldOutputContainerWithTextNoAttributes() {

        String walk = service.generateFromHtml(null, true, null, """
                <a>hello <span>j2html community</span></a>
                """, null, null);

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

        String walk = service.generateFromHtml(null, true, null, """
                <a href="/some/url">hello <span>j2html community</span></a>
                """, null, null);

        assertThat(walk).isEqualTo("""
                import static j2html.TagCreator.*;
                                
                a()
                  .withHref("/some/url")
                  .with(
                    text("hello "),
                    span("j2html community")
                  )
                """);

    }

    @Test
    void shouldOutputSingleClass() {

        String walk = service.generateFromHtml(null, true, null, """
                <h1 class="title"/>
                """, null, null);

        assertThat(walk).isEqualTo("""
                import static j2html.TagCreator.*;
                                
                h1()
                  .withClass("title")
                """);

    }

    @Test
    void shouldOutputMultipleClasses() {

        String walk = service.generateFromHtml(null, true, null, """
                <h1 class="title other"/>
                """, null, null);

        assertThat(walk).isEqualTo("""
                import static j2html.TagCreator.*;
                                
                h1()
                  .withClasses("title", "other")
                """);

    }

    @Test
    void shouldOutputUnescapedTextComments() {

        //language=html
        String walk = service.generateFromHtml(null, true, null, """
                <div>
                    <!-- this is some comment -->
                </div>
                """, null, null);

        assertThat(walk).isEqualTo("""
                import static j2html.TagCreator.*;
                                
                div(
                  rawHtml("<!-- this is some comment -->")
                )
                """);

    }

    @Test
    void shouldOutputAllEmptyTextBetweenTextChildren() {

        //language=html
        String walk = service.generateFromHtml(null, true, null, """
                <div id='parent'>
                    <div class="col-4 col-sm-6">
                      Level 2: .col-4 .col-sm-6
                    </div>
                </div>
                """, null, null);

        assertThat(walk).isEqualTo("""
                import static j2html.TagCreator.*;
                                
                div()
                  .withId("parent")
                  .with(
                    div("Level 2: .col-4 .col-sm-6")
                      .withClasses("col-4", "col-sm-6")
                  )
                """);

    }

    @Nested
    class MissingCSSSnippets {

        @Test
        void shouldOutputMissingFormTableLayout() {

            String walk = service.generateFromHtml(null, true, null, """
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
                    """, null, null);

            assertThat(walk).isEqualTo("""
                    import static j2html.TagCreator.*;
                                    
                    form()
                      .withClasses("table", "rows")
                      .with(
                        p(
                          label("Name")
                            .attr("for", "name"),
                          input()
                            .withType("text")
                            .withId("name")
                            .withName("name")
                        ),
                        p(
                          label("Address")
                            .attr("for", "adr"),
                          input()
                            .withType("text")
                            .withId("adr")
                            .withName("adr")
                        )
                      )
                    """);

        }

    }

    @Nested
    class BulmaCSSSnippets {

        @Test
        void shouldOutputNameField() {

            String walk = service.generateFromHtml(null, true, null, """
                                    
                     <div class="field">
                      <label class="label">Name</label>
                      <div class="control">
                        <input class="input" type="text" placeholder="Text input">
                      </div>
                    </div>
                     """, null, null);

            assertThat(walk).isEqualTo("""
                    import static j2html.TagCreator.*;
                                    
                    div()
                      .withClass("field")
                      .with(
                        label("Name")
                          .withClass("label"),
                        div()
                          .withClass("control")
                          .with(
                            input()
                              .withClass("input")
                              .withType("text")
                              .attr("placeholder", "Text input")
                          )
                      )
                    """);

        }

        @Test
        void shouldOutputUsernameField() {

            String walk = service.generateFromHtml(null, true, null, """
                                       
                                   
                    <div class="field">
                      <label class="label">Username</label>
                      <div class="control has-icons-left has-icons-right">
                        <input class="input is-success" type="text" placeholder="Text input" value="bulma">
                        <span class="icon is-small is-left">
                          <i class="fas fa-user"></i>
                        </span>
                        <span class="icon is-small is-right">
                          <i class="fas fa-check"></i>
                        </span>
                      </div>
                      <p class="help is-success">This username is available</p>
                    </div>
                                   
                     """, null, null);

            assertThat(walk).isEqualTo("""
                    import static j2html.TagCreator.*;
                                    
                    div()
                      .withClass("field")
                      .with(
                        label("Username")
                          .withClass("label"),
                        div()
                          .withClasses("control", "has-icons-left", "has-icons-right")
                          .with(
                            input()
                              .withClasses("input", "is-success")
                              .withType("text")
                              .attr("placeholder", "Text input")
                              .attr("value", "bulma"),
                            span()
                              .withClasses("icon", "is-small", "is-left")
                              .with(
                                i()
                                  .withClasses("fas", "fa-user")
                              ),
                            span()
                              .withClasses("icon", "is-small", "is-right")
                              .with(
                                i()
                                  .withClasses("fas", "fa-check")
                              )
                          ),
                        p("This username is available")
                          .withClasses("help", "is-success")
                      )
                    """);

        }

        @Test
        void shouldOutputEmailField() {

            String walk = service.generateFromHtml(null, true, null, """
                                       
                                   
                    <div class="field">
                      <label class="label">Email</label>
                      <div class="control has-icons-left has-icons-right">
                        <input class="input is-danger" type="email" placeholder="Email input" value="hello@">
                        <span class="icon is-small is-left">
                          <i class="fas fa-envelope"></i>
                        </span>
                        <span class="icon is-small is-right">
                          <i class="fas fa-exclamation-triangle"></i>
                        </span>
                      </div>
                      <p class="help is-danger">This email is invalid</p>
                    </div>
                                  
                     """, null, null);

            assertThat(walk).isEqualTo("""
                    import static j2html.TagCreator.*;
                                    
                    div()
                      .withClass("field")
                      .with(
                        label("Email")
                          .withClass("label"),
                        div()
                          .withClasses("control", "has-icons-left", "has-icons-right")
                          .with(
                            input()
                              .withClasses("input", "is-danger")
                              .withType("email")
                              .attr("placeholder", "Email input")
                              .attr("value", "hello@"),
                            span()
                              .withClasses("icon", "is-small", "is-left")
                              .with(
                                i()
                                  .withClasses("fas", "fa-envelope")
                              ),
                            span()
                              .withClasses("icon", "is-small", "is-right")
                              .with(
                                i()
                                  .withClasses("fas", "fa-exclamation-triangle")
                              )
                          ),
                        p("This email is invalid")
                          .withClasses("help", "is-danger")
                      )
                    """);

        }

        @Test
        void shouldOutputSubjectSelect() {

            String walk = service.generateFromHtml(null, true, null, """
                                       
                                   
                    <div class="field">
                      <label class="label">Subject</label>
                      <div class="control">
                        <div class="select">
                          <select>
                            <option>Select dropdown</option>
                            <option>With options</option>
                          </select>
                        </div>
                      </div>
                    </div>
                     """, null, null);

            assertThat(walk).isEqualTo("""
                    import static j2html.TagCreator.*;
                                    
                    div()
                      .withClass("field")
                      .with(
                        label("Subject")
                          .withClass("label"),
                        div()
                          .withClass("control")
                          .with(
                            div()
                              .withClass("select")
                              .with(
                                select(
                                  option("Select dropdown"),
                                  option("With options")
                                )
                              )
                          )
                      )
                    """);

        }

        @Test
        void shouldOutputTextArea() {

            String walk = service.generateFromHtml(null, true, null, """
                                      
                                   
                    <div class="field">
                      <label class="label">Message</label>
                      <div class="control">
                        <textarea class="textarea" placeholder="Textarea"></textarea>
                      </div>
                    </div>
                     """, null, null);

            assertThat(walk).isEqualTo("""
                    import static j2html.TagCreator.*;
                                    
                    div()
                      .withClass("field")
                      .with(
                        label("Message")
                          .withClass("label"),
                        div()
                          .withClass("control")
                          .with(
                            textarea()
                              .withClass("textarea")
                              .attr("placeholder", "Textarea")
                          )
                      )
                    """);

        }

        @Test
        void shouldOutputCheckbox() {

            String walk = service.generateFromHtml(null, true, null, """
                                      
                                   
                    <div class="field">
                      <div class="control">
                        <label class="checkbox">
                          <input type="checkbox">
                          I agree to the <a href="#">terms and conditions</a>
                        </label>
                      </div>
                    </div>
                     """, null, null);

            assertThat(walk).isEqualTo("""
                    import static j2html.TagCreator.*;
                                    
                    div()
                      .withClass("field")
                      .with(
                        div()
                          .withClass("control")
                          .with(
                            label()
                              .withClass("checkbox")
                              .with(
                                input()
                                  .withType("checkbox"),
                                text("\\n      I agree to the "),
                                a("terms and conditions")
                                  .withHref("#")
                              )
                          )
                      )
                    """);

        }

        @Test
        void shouldOutputRadio() {

            String walk = service.generateFromHtml(null, true, null, """
                                       
                                   
                    <div class="field">
                      <div class="control">
                        <label class="radio">
                          <input type="radio" name="question">
                          Yes
                        </label>
                        <label class="radio">
                          <input type="radio" name="question">
                          No
                        </label>
                      </div>
                    </div>
                                
                     """, null, null);

            assertThat(walk).isEqualTo("""
                    import static j2html.TagCreator.*;
                                    
                    div()
                      .withClass("field")
                      .with(
                        div()
                          .withClass("control")
                          .with(
                            label()
                              .withClass("radio")
                              .with(
                                input()
                                  .withType("radio")
                                  .withName("question"),
                                text("\\n      Yes\\n    ")
                              ),
                            label()
                              .withClass("radio")
                              .with(
                                input()
                                  .withType("radio")
                                  .withName("question"),
                                text("\\n      No\\n    ")
                              )
                          )
                      )
                    """);

        }

        @Test
        void shouldOutputGroupedButtons() {

            String walk = service.generateFromHtml(null, true, null, """
                                      
                                   
                    <div class="field is-grouped">
                      <div class="control">
                        <button class="button is-link">Submit</button>
                      </div>
                      <div class="control">
                        <button class="button is-link is-light">Cancel</button>
                      </div>
                    </div>
                     """, null, null);

            assertThat(walk).isEqualTo("""
                    import static j2html.TagCreator.*;
                                    
                    div()
                      .withClasses("field", "is-grouped")
                      .with(
                        div()
                          .withClass("control")
                          .with(
                            button("Submit")
                              .withClasses("button", "is-link")
                          ),
                        div()
                          .withClass("control")
                          .with(
                            button("Cancel")
                              .withClasses("button", "is-link", "is-light")
                          )
                      )
                    """);

        }


    }

    @Nested
    class BoostrapExamples {

        @Test
        void shouldHandleNewlines() {


            String walk = service.generateFromHtml(null, true, null, """
                    <div class="container text-center">
                            <div class="row">
                              <div class="col">
                                Column
                              </div>
                              <div class="col">
                                Column
                              </div>
                              <div class="col">
                                Column
                              </div>
                            </div>
                          </div>
                    """, null, null);

            assertThat(walk).isEqualTo("""
                    import static j2html.TagCreator.*;
                                    
                    div()
                      .withClasses("container", "text-center")
                      .with(
                        div()
                          .withClass("row")
                          .with(
                            div("Column")
                              .withClass("col"),
                            div("Column")
                              .withClass("col"),
                            div("Column")
                              .withClass("col")
                          )
                      )
                    """);

        }

    }


}