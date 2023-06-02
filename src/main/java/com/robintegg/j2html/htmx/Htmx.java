package com.robintegg.j2html.htmx;

import j2html.tags.specialized.ButtonTag;
import j2html.tags.specialized.FormTag;
import j2html.tags.specialized.ScriptTag;

import static j2html.TagCreator.script;

public class Htmx {

    public static ScriptTag htmxScript() {
        return script().withSrc("https://unpkg.com/htmx.org@1.9.2");
    }

    public static void post(ButtonTag submit, String url, String outerHtml) {
        submit.attr("hx-post", url );
        submit.attr("hx-swap", outerHtml);
    }

    public static void post(FormTag form, String url, String target) {
        form.attr("hx-post", url );
        form.attr("hx-target", target);
        form.attr("hx-swap", "outerHTML");
//        form.attr("hx-params", "html");
    }

}
