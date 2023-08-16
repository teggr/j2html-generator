package com.robintegg.j2html.app.web.pages;

import j2html.rendering.IndentedHtml;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import java.util.Map;

import static j2html.TagCreator.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class HomePage implements View {

  private final Layout layout;
  private final Links links;

  @Override
  public String getContentType() {
    return MediaType.TEXT_HTML_VALUE;
  }

  @Override
  public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

    layout.render(
        model,
        request,
        response,
        div().withStyle("width: 100%;").with(
            div().withStyle("float:left; width: 50%;").with(
                form()
                    .withAction(links.of("/generate"))
                    .withMethod("post")
                    .attr("hx-post", links.of("/generate"))
                    .attr("hx-target", "#generated-code")
                    .attr("hx-select", "#generated-code-insert")
                    .attr("hx-indicator", "#spinner")
                    .with(
                        label("Put your HTML here").withFor("content"),
                        textarea()
                            .withStyle("display: block; width: 100%;")
                            .withId("content")
                            .withName("content")
                            .withRows("15")
                            .withText("<h1>hello j2html community</h1>"),
                        button("Generate j2html").withType("submit")
                    )
            ),
            div().withStyle("float:left; width: 50%;").with(
                div().withId("generated-code").with(
                    img()
                        .withId("spinner")
                        .withClass("htmx-indicator")
                        .withSrc("https://raw.githubusercontent.com/n3r4zzurr0/svg-spinners/main/svg-css/90-ring.svg")
                ),
                script()
                    .with(rawHtml("""
                        document.addEventListener("htmx:load", (event) => {
                            let code = document.getElementById("generated-code-insert");
                            if(code != null) {
                                Prism.highlightAllUnder(code);
                            }
                        })
                        """))
            )
        ),
        div().withStyle("clear: both;")
    ).render(IndentedHtml.into(response.getWriter()));

  }

}
