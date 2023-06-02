package com.robintegg.j2html.generator.web.pages;

import com.robintegg.j2html.bulma.BulmaForm;
import com.robintegg.j2html.generator.web.site.SitePage;
import com.robintegg.j2html.htmx.Htmx;
import j2html.tags.DomContent;
import j2html.tags.specialized.DivTag;
import j2html.tags.specialized.FormTag;
import j2html.tags.specialized.ScriptTag;
import j2html.tags.specialized.SectionTag;
import lombok.extern.slf4j.Slf4j;

import static j2html.TagCreator.*;

@Slf4j
public class HomePage extends SitePage {

    @Override
    protected DomContent[] pageBody() {

        FormTag generateForm = BulmaForm.bulmaForm()
                .with(BulmaForm.bulmaTextArea("Raw Html", """
                        <div class="control">
                          <input class="input" type="text" placeholder="Text input">
                        </div>
                        """, null))
                .with(BulmaForm.bulmaGroupedFields(
                        button("Submit").withType("submit").withClass("button is-link"),
                        button("Cancel").withType("reset").withClass("button is-link is-light")
                ));

        Htmx.post(generateForm, "/generate", "#responseTextArea");

        SectionTag with = section()
                .withClass("section")
                .with(
                        div().withClass("container").with(
                                h1("Generate J2Html Code").withClass("title"),
                                p(text("Convert plain Html markup into executable "), strong("J2Html") , text(" java code!")).withClass("subtitle")
                        )
                )
                .with(generateForm)
                .with(new DivTag()
                        .withId("responseTextArea"));

        ScriptTag refreshTag = new ScriptTag()
                .withSrc("/js/com/robintegg/j2html/generator/web/scripts/PrismInitialiser.js");

        return new DomContent[]{
                with,
                refreshTag
        };

    }

}
