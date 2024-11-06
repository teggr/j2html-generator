package com.robintegg.j2html.app.web.pages;

import dev.rebelcraft.j2html.ext.components.Buttons;
import dev.rebelcraft.j2html.ext.forms.ChecksAndRadios;
import dev.rebelcraft.j2html.ext.forms.FormControl;
import dev.rebelcraft.j2html.ext.forms.Select;
import dev.rebelcraft.j2html.ext.layout.Containers;
import dev.rebelcraft.j2html.ext.layout.Grid;
import dev.rebelcraft.j2html.ext.utilities.Spacing;
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

        String msg = (String) model.get("msg");
        String packageName = (String) model.get("packageName");
        boolean includeImports = !model.containsKey("includeImports") || (boolean) model.get("includeImports"); // default true
        String tagLibraryId = (String) model.get("tagLibraryId");
        String template = (String) model.get("template");
        String testName = (String) model.get("testName");

        layout.render(
                "j2html-generator",
                model,
                request,
                response,
                div()
                        .withClass(Containers.container_fluid)
                        .with(
                                h1("Generate j2html code from HTML snippets"),
                                hr(),
                                Partials.showAlertIfMessage(msg),
                                div()
                                        .withClass("row")
                                        .with(

                                                div()
                                                        .withClass(Grid.col)
                                                        .with(
                                                                form()
                                                                        .withAction(links.of("/generate"))
                                                                        .withMethod("post")
                                                                        .attr("hx-post", links.of("/generate"))
                                                                        .attr("hx-target", "#generated-code")
                                                                        .attr("hx-select", "#generated-code-insert")
                                                                        .attr("hx-indicator", "#spinner")
                                                                        .with(
                                                                                div()
                                                                                        .withClass(Spacing.mb_3)
                                                                                        .with(
                                                                                                label("Put your HTML here")
                                                                                                        .withClass(FormControl.form_label)
                                                                                                        .withFor("content"),
                                                                                                textarea()
                                                                                                        .withClass(FormControl.form_control)
                                                                                                        .withStyle("height: 350px;")
                                                                                                        .withId("content")
                                                                                                        .withName("content")
                                                                                                        .withText("<h1>hello j2html community</h1>")
                                                                                        ),
                                                                                div()
                                                                                        .withClass(Spacing.mb_3)
                                                                                        .with(
                                                                                                label("Package Name")
                                                                                                        .attr("for", "packageName")
                                                                                                        .withClass(FormControl.form_label),
                                                                                                input()
                                                                                                        .withClass(FormControl.form_control)
                                                                                                        .withId("packageName")
                                                                                                        .attr("aria-describedby", "packageName")
                                                                                                        .withName("packageName")
                                                                                                        .withValue(packageName)
                                                                                        ),
                                                                                div()
                                                                                        .withClasses(Spacing.mb_3, ChecksAndRadios.form_check)
                                                                                        .with(
                                                                                                input()
                                                                                                        .withType("checkbox")
                                                                                                        .withClass(ChecksAndRadios.form_check_input)
                                                                                                        .withId("includeImports")
                                                                                                        .withName("includeImports")
                                                                                                        .withCondChecked(includeImports),
                                                                                                label("Include Imports")
                                                                                                        .withClass(ChecksAndRadios.form_check_label)
                                                                                                        .attr("for", "includeImports")
                                                                                        ),
                                                                                div()
                                                                                        .withClasses(Spacing.mb_3, ChecksAndRadios.form_check)
                                                                                        .with(
                                                                                                input()
                                                                                                        .withType("checkbox")
                                                                                                        .withClass(ChecksAndRadios.form_check_input)
                                                                                                        .withId("tagLibraryId")
                                                                                                        .withName("tagLibraryId")
                                                                                                        .withCondChecked("j2htmlExtensions".equals(tagLibraryId))
                                                                                                        .withValue("j2htmlExtensions"),
                                                                                                label("Use j2html Extensions Library")
                                                                                                        .withClass(ChecksAndRadios.form_check_label)
                                                                                                        .attr("for", "tagLibraryId")
                                                                                        ),
                                                                                div()
                                                                                        .withClasses(Spacing.mb_3)
                                                                                        .with(
                                                                                                select()
                                                                                                        .withClass(Select.form_select)
                                                                                                        .attr("aria-label", "Select Template")
                                                                                                        .withName("template")
                                                                                                        .with(
                                                                                                                option("Choose a template")
                                                                                                                        .attr("selected")
                                                                                                                        .withCondSelected(template == null),
                                                                                                                option("UI Test")
                                                                                                                        .attr("value", "uitest")
                                                                                                                        .withCondSelected("uitest".equals(template)),
                                                                                                                option("UI Test Snippet")
                                                                                                                        .attr("value", "uitestsnippet")
                                                                                                                        .withCondSelected("uitestsnippet".equals(template))
                                                                                                        )
                                                                                        ),
                                                                                div()
                                                                                        .withClass(Spacing.mb_3)
                                                                                        .with(
                                                                                                label("Test Name")
                                                                                                        .attr("for", "testName")
                                                                                                        .withClass(FormControl.form_label),
                                                                                                input()
                                                                                                        .withClass(FormControl.form_control)
                                                                                                        .withId("testName")
                                                                                                        .attr("aria-describedby", "testName")
                                                                                                        .withName("testName")
                                                                                                        .withValue(testName)
                                                                                        ),
                                                                                button("Generate j2html")
                                                                                        .withClasses(Buttons.btn, Buttons.btn_primary)
                                                                                        .withType("submit")
                                                                        )
                                                        ),
                                                div()
                                                        .withClass(Grid.col)
                                                        .with(
                                                                div().withId("generated-code").with(
                                                                        img()
                                                                                .withId("spinner")
                                                                                .withClass("htmx-indicator")
                                                                                .withSrc("https://raw.githubusercontent.com/n3r4zzurr0/svg-spinners/main/svg-css/90-ring.svg")
                                                                ),
                                                                //language=javascript
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
                                        )
                        )


        ).render(IndentedHtml.into(response.getWriter()));

    }

}
