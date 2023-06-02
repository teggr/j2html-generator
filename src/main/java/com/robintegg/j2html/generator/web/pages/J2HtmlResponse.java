package com.robintegg.j2html.generator.web.pages;

import com.robintegg.j2html.templates.J2HtmlTemplate;
import com.robintegg.j2html.templates.TagWriter;
import com.robintegg.j2html.templates.Writer;
import j2html.tags.specialized.CodeTag;
import j2html.tags.specialized.DivTag;
import j2html.tags.specialized.PreTag;
import j2html.tags.specialized.TextareaTag;

import java.util.Map;

import static j2html.TagCreator.*;

public class J2HtmlResponse implements J2HtmlTemplate {
    @Override
    public Writer make(Map<String, Object> model) {
        DivTag divTag = new DivTag()
                .withId("responseTextArea")
                .with(new PreTag()
                        .with(new CodeTag()
                                .withClass("language-java")
                                .withText(model.get("j2html").toString())
                        ));
        return new TagWriter(divTag);
    }

}
