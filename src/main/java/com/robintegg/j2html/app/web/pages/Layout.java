package com.robintegg.j2html.app.web.pages;

import dev.rebelcraft.j2html.ext.BootstrapTagCreator;
import dev.rebelcraft.j2html.ext.components.Collapse;
import dev.rebelcraft.j2html.ext.layout.Containers;
import j2html.tags.DomContent;
import j2html.tags.specialized.NavTag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

import static dev.rebelcraft.j2html.ext.BootstrapTagCreator.*;
import static dev.rebelcraft.j2html.ext.components.Navbar.*;
import static j2html.TagCreator.*;

@Component
@RequiredArgsConstructor
public class Layout {

    private final Links links;

    public DomContent render(String title, Map<String, ?> model, HttpServletRequest request, HttpServletResponse response, DomContent... content) {

        return join(document(), html(
                head(
                        utf8Charset(),
                        responsiveViewport(),
                        title(title == null ? "j2html Generator" : title),

                        // bootstrap
                        cdnCSSLink(),

                        // prism
                        link().withRel("stylesheet").withHref("https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/themes/prism-okaidia.min.css"),
                        link().withRel("stylesheet").withHref("https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/plugins/toolbar/prism-toolbar.min.css"),

                        // bootstrap
                        BootstrapTagCreator.cdnBundleMinJSLink(),

                        // prism
                        script().withSrc("https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/prism.min.js"),
                        script().withSrc("https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/plugins/toolbar/prism-toolbar.min.js"),
                        script().withSrc("https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/plugins/autoloader/prism-autoloader.min.js"),
                        script().withSrc("https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/plugins/copy-to-clipboard/prism-copy-to-clipboard.min.js"),
                        script().withSrc("https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/plugins/show-language/prism-show-language.min.js"),
                        script().withSrc("https://cdnjs.cloudflare.com/ajax/libs/prism/1.29.0/plugins/remove-initial-line-feed/prism-remove-initial-line-feed.min.js"),

                        // htmx
                        script().withSrc("https://unpkg.com/htmx.org")
                ),
                body().attr("hx-boost", true)
                        .with(
                                header()
                                        .with(
                                                navbar()
                                        ),
                                main(
                                        content
                                ),
                                footer()
                        )
        ));
    }

    private NavTag navbar() {
        return nav()
                .withClasses(navbar, navbar_expand_md)
                .attr(roleNavigation())
                .with(
                        div().withClass(Containers.container_fluid)
                                .with(
                                        a().withClass(navbar_brand)
                                                .withHref(links.of("/"))
                                                .with(
                                                        img()
                                                                .withSrc("https://j2html.com/img/logo.svg")
                                                                .withAlt("logo")
                                                                //.withWidth("30")
                                                                .withHeight("24")
                                                ),
                                        button().withClass(navbar_toggler)
                                                .withType("button")
                                                .attr(dataBsToggleCollapse())
                                                .attr(dataBsTarget("#navbarSupportedContent"))
                                                .attr(ariaControls("navbarSupportedContent"))
                                                .attr(ariaExpandedFalse())
                                                .attr(ariaLabelToggleNavigation())
                                                .with(
                                                        span().withClass(navbar_toggler_icon)
                                                ),
                                        div().withClasses(Collapse.collapse, navbar_collapse)
                                                .withId("navbarSupportedContent")
                                                .with(
                                                        ul()
                                                                .withClass(navbar_nav)
                                                                .attr("role", "list")
                                                                .with(
                                                                        li().withClass(nav_item).with(
                                                                                a("Generate j2html")
                                                                                        .withClasses(nav_link, active)
                                                                                        .attr(ariaCurrentPage())
                                                                                        .withHref(links.of("/"))
                                                                        ),
                                                                        li().withClass(nav_item).with(
                                                                                a("What is j2html?")
                                                                                        .withClass(nav_link)
                                                                                        .withHref("https://j2html.com")
                                                                                        .withTarget("_blank")
                                                                        )
                                                                )
                                                )

                                )

                );
    }

}
