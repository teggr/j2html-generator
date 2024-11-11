package com.robintegg.j2html.app.generator;

public class J2HtmlTagLibrary implements TagLibrary {

    @Override
    public String commentMethodName() {
        return "comment";
    }

    @Override
    public String wrapComment(String comment) {
        return comment;
    }

    @Override
    public String[] staticImports() {
        return new String[] {
                "j2html.TagCreator.*",
                "dev.rebelcraft.j2html.ext.ExtendedTagCreator.*"
        };
    }

}
