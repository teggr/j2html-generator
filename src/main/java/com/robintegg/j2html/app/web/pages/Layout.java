package com.robintegg.j2html.app.web.pages;

import j2html.tags.DomContent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

import static j2html.TagCreator.*;

@Component
@RequiredArgsConstructor
public class Layout {

  private final Links links;

  public DomContent render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response, DomContent... content) {

    String msg = (String) model.get("msg");

    return join(document(), html(
        head(
            meta().withCharset("utf-8"),
            meta().withName("viewport").withContent("width=device-width, initial-scale=1"),
            title("j2html Generator"),
            link().withRel("stylesheet").withHref("https://unpkg.com/missing.css@1.0.9/dist/missing.min.css"),
            style().with(rawHtml("""
                                        
                """)),

            // prism
            link().withRel("stylesheet").withHref("https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/themes/prism-okaidia.min.css"),
            link().withRel("stylesheet").withHref("https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/plugins/toolbar/prism-toolbar.min.css"),

            script().withSrc("https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/prism.min.js"),
            script().withSrc("https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/plugins/toolbar/prism-toolbar.min.js"),
            script().withSrc("https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/plugins/autoloader/prism-autoloader.min.js"),
            script().withSrc("https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/plugins/copy-to-clipboard/prism-copy-to-clipboard.min.js"),
            script().withSrc("https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/plugins/show-language/prism-show-language.min.js"),
            script().withSrc("https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/plugins/remove-initial-line-feed/prism-remove-initial-line-feed.min.js"),

            // htmx
            script().withSrc("https://unpkg.com/htmx.org")
        ),
        body().attr("hx-boost", true).with(
            header().withClass("navbar")
                .with(
                    nav()
                        .attr("aria-label", "Site sections")
                        .with(
                            ul()
                                .attr("role", "list")
                                .with(
                                    li(
                                        a().withHref(links.of("/")).with(
                                            img()
                                                .withAlt("logo")
                                                .withStyle("height: 25px;")
                                                .withSrc("https://j2html.com/img/logo.svg")
                                        )
                                    ),
                                    li(
                                        a("Generate j2html").withHref(links.of("/"))
                                    ),
                                    li(
                                        a("j2html").withHref("https://j2html.com")
                                    )
                                )
                        )
                ),
            div().with(
                h1("Generate j2html code from HTML snippets"),
                hr(),
                iff(msg != null,
                    div((String) msg)
                        .withClasses("container", "box")
                )
            ),
            div().with(
                each(content)
            )
        )
    ));
  }

}
