package com.robintegg.j2html.bulma;

import j2html.tags.DomContent;
import j2html.tags.specialized.DivTag;
import j2html.tags.specialized.FormTag;

import static j2html.TagCreator.*;

public class BulmaForm {
    public static FormTag bulmaForm(String method, String action) {
        return new FormTag().withMethod(method).withAction(action);
    }

    public static FormTag bulmaForm() {
        return new FormTag();
    }

    public static DomContent bulmaInputText() {
        return div().withClass("field").with(
                label("Name").withClass("label"),
                div().withClass("control").with(
                        input().withClass("input").withType("text").withPlaceholder("Text input")
                )
        );
    }

    public static DomContent bulmaUsername() {
        return div().withClass("field").with(
                label("Username").withClass("label"),
                div().withClass("control has-icons-left has-icons-right").with(
                        input().withClass("input is-success").withType("text").withPlaceholder("Text input").withValue("bulma"),
                        span().withClass("icon is-small is-left").with(
                                i().withClass("fas fa-user")
                        ),
                        span().withClass("icon is-small is-right").with(
                                i().withClass("fas fa-check")
                        )
                ),
                p("This username is available").withClass("help is-success")
        );
    }

    public static DomContent bulmaEmail() {
        return div().withClass("field").with(
                label("Email").withClass("label"),
                div().withClass("control has-icons-left has-icons-right").with(
                        input().withClass("input is-danger").withType("email").withPlaceholder("Email input").withValue("hello@"),
                        span().withClass("icon is-small is-left").with(
                                i().withClass("fas fa-envelope")
                        ),
                        span().withClass("icon is-small is-right").with(
                                i().withClass("fas fa-exclamation-triangle")
                        )
                ),
                p("This email is invalid").withClass("help is-danger")
        );
    }

    public static DomContent bulmaSelect() {
        return div().withClass("field").with(
                label("Subject").withClass("label"),
                div().withClass("control").with(
                        div().withClass("select").with(
                                select().with(
                                        option("Select dropdown"),
                                        option("With options")
                                )
                        )
                )
        );
    }

    public static DomContent bulmaTextArea() {
        String label = "Message";
        String placeholder = "Textarea";
        return bulmaTextArea(label, placeholder, null);
    }

    public static DivTag bulmaTextArea(String label, String placeholder, String textClass) {
        return div().withClass("field").with(
                label(label).withClass("label"),
                div().withClass("control").with(
                        textarea().withName("html")
                                .withClass("textarea")
                                .withClass(textClass)
                                .withPlaceholder(placeholder)
                )
        );
    }

    public static DomContent bulmaCheckBox() {
        return div().withClass("field").with(
                div().withClass("control").with(
                        label(input().withType("checkbox"), text("I agree to the "), a().withHref("#").withText("terms and conditions"))
                                .withClass("checkbox")
                )
        );
    }

    public static DomContent bulmaRadioButton() {
        return div().withClass("field").with(
                div().withClass("control").with(
                        label(input().withType("radio").withName("question"), text("Yes")).withClass("radio"),
                        label(input().withType("radio").withName("question"), text("No")).withClass("radio")
                )
        );
    }

    public static DomContent bulmaGroupedFields(DomContent... fields) {
        DivTag divTag = div()
                .withClass("field is-grouped");
        for (int i = 0; i < fields.length; i++) {
            divTag = divTag.with(
                    div().withClass("control").with(fields[i])
            );
        }
        return divTag;
    }

}
