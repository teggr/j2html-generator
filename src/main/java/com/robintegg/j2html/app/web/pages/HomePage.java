package com.robintegg.j2html.app.web.pages;

import j2html.rendering.IndentedHtml;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import java.util.Map;

import static j2html.TagCreator.*;

@Component
@Slf4j
public class HomePage implements View {

    @Override
    public String getContentType() {
        return MediaType.TEXT_HTML_VALUE;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Layout.withContent(
                model,
                request,
                form()
                        .withAction("/generate")
                        .withMethod("post")
                        .attr("hx-post", "/generate")
                        .attr("hx-target", "#generated-code")
                        .attr("hx-select", "#generated-code-insert")
                        .attr("hx-indicator", "#spinner")
                        .with(
                                label("Put your HTML here").withFor("content"),
                                textarea()
                                        .withStyle("display: block; width: 100%;")
                                        .withId("content")
                                        .withName("content")
                                        .withText("<h1>hello j2html community</h1>"),
                                button("Generate j2html").withType("submit")
                        ),
                hr(),
                div().withId("generated-code").with(
                        img()
                                .withId("spinner")
                                .withClass("htmx-indicator")
                                .withSrc("https://raw.githubusercontent.com/n3r4zzurr0/svg-spinners/main/svg-css/90-ring.svg")
                )

        ).render(IndentedHtml.into(response.getWriter()));

    }

}
