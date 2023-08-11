package com.robintegg.j2html.app.web.pages;

import j2html.tags.DomContent;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import static j2html.TagCreator.*;

public class Layout {

    public static DomContent withContent(Map<String, ?> model, HttpServletRequest request, DomContent... content) {
        return new Layout(model, request, content).getLayout();
    }

    private final Map<String, ?> model;
    private final HttpServletRequest request;
    private final DomContent[] content;

    public Layout(Map<String, ?> model, HttpServletRequest request, DomContent[] content) {
        this.model = model;
        this.request = request;
        this.content = content;
    }

    private DomContent getLayout() {

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
                                                                                a().withHref("/").with(
                                                                                        img()
                                                                                                .withAlt("logo")
                                                                                                .withStyle("height: 25px;")
                                                                                                .withSrc("https://j2html.com/img/logo.svg")
                                                                                )
                                                                        ),
                                                                        li(
                                                                                a("Generate j2html").withHref("/")
                                                                        ),
                                                                        li(
                                                                                a("j2html").withHref("https://j2html.com")
                                                                        )
                                                                )
                                                )
                                ),
                        div().withClass("container").with(
                                h1("j2html Generator"),
                                h2("Generate j2html code from HTML snippets"),
                                hr(),
                                iff(msg != null,
                                        div((String) msg).withClass("box")
                                ),
                                each(content)
                        )
                )
        ));
    }

}
