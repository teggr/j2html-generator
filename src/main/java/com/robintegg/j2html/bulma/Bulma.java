package com.robintegg.j2html.bulma;

import j2html.tags.specialized.LinkTag;

import static j2html.TagCreator.link;

public class Bulma
{
    public static LinkTag bulmaStylesheet() {
        return link().withRel("stylesheet").withHref("https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css");
    }

}
