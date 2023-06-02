package com.robintegg.j2html.bulma;

import j2html.tags.DomContent;
import j2html.tags.specialized.DivTag;

import static j2html.TagCreator.div;

public class BulmaComponents {

    public static DivTag container() {
        return div().withClass(BulmaClasses.classes(BulmaClasses.container));
    }

}
