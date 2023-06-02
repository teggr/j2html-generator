package com.robintegg.j2html.generator.web.site;

import com.robintegg.j2html.templates.J2HtmlTemplate;
import com.robintegg.j2html.templates.TagWriter;
import com.robintegg.j2html.templates.Writer;
import j2html.tags.ContainerTag;
import j2html.tags.DomContent;
import j2html.tags.specialized.SectionTag;

import java.util.Map;

import static j2html.TagCreator.*;

public class SitePage implements J2HtmlTemplate {

    @Override
    public Writer make(Map<String, Object> model) {

        ContainerTag html = html(
                Site.getHead(),
                body(pageBody())
        );

        return new TagWriter(html);

    }

    protected DomContent[] pageBody() {
        return new DomContent[]{section("Empty Body")};
    }

}
