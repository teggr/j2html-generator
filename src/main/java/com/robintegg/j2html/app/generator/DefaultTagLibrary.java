package com.robintegg.j2html.app.generator;

public class DefaultTagLibrary implements TagLibrary {

    @Override
    public String comment(String comment) {
        return "rawHtml(\"<!--" + comment + "-->\")";
    }

    @Override
    public String[] staticImports() {
        return new String[] { "j2html.TagCreator.*" };
    }

}
