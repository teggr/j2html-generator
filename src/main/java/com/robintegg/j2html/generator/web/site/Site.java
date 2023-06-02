package com.robintegg.j2html.generator.web.site;

import com.robintegg.j2html.bulma.Bulma;
import com.robintegg.j2html.htmx.Htmx;
import j2html.tags.specialized.HeadTag;

import static j2html.TagCreator.*;

public class Site {

    public static HeadTag getHead() {
        return head(
                meta().withCharset("utf-8"),
                meta().withName("viewport").withContent("width=device-width, initial-scale=1"),
                title("Hello Bulma!"),
                        Bulma.bulmaStylesheet(),

                link().withRel("stylesheet").withHref("https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/themes/prism-okaidia.min.css"),
                link().withRel("stylesheet").withHref("https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/plugins/toolbar/prism-toolbar.min.css"),

                script().withSrc("https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/prism.min.js"),
                script().withSrc("https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/plugins/toolbar/prism-toolbar.min.js"),
                script().withSrc("https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/plugins/autoloader/prism-autoloader.min.js"),
                script().withSrc("https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/plugins/copy-to-clipboard/prism-copy-to-clipboard.min.js"),
                script().withSrc("https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/plugins/show-language/prism-show-language.min.js"),
                script().withSrc("https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/plugins/remove-initial-line-feed/prism-remove-initial-line-feed.min.js"),

                Htmx.htmxScript()
        );
    }

}
