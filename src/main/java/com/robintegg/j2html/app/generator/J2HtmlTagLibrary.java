package com.robintegg.j2html.app.generator;

public class J2HtmlTagLibrary implements TagLibrary {

    @Override
    public String comment(String comment) {
        return "comment(\"" + comment + "\")";
    }

    @Override
    public String[] staticImports() {
        return new String[] {
                "j2html.TagCreator.*",
                "dev.rebelcraft.j2html.ext.ExtendedTagCreator.*"
        };
    }

}
