package com.robintegg.j2html.app.web.pages;

import dev.rebelcraft.j2html.ext.components.Alert;
import j2html.tags.specialized.DivTag;

import static j2html.TagCreator.div;
import static j2html.TagCreator.iff;

public class Partials {

    public static DivTag showAlertIfMessage(String msg) {
        return iff(msg != null,
                div()
                        .withClasses(Alert.alert, Alert.alert_danger)
                        .attr(Alert.role())
                        .withText(msg)
        );
    }

}
