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
public class GeneratePage implements View {

  private final Layout layout;

  @Override
  public String getContentType() {
    return MediaType.TEXT_HTML_VALUE;
  }

  @Override
  public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

    String generatedText = (String) model.get("generatedText");

    layout.render(
        model,
        request,
        response,
        div().withId("generated-code-insert").with(
            img()
                .withId("spinner")
                .withClass("htmx-indicator")
                .withSrc("https://raw.githubusercontent.com/n3r4zzurr0/svg-spinners/main/svg-css/90-ring.svg"),
            pre(
                code().withClass("language-java").with(
                    rawHtml(generatedText)
                )
            )
        )

    ).render(IndentedHtml.into(response.getWriter()));

  }

}
