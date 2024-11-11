package com.robintegg.j2html.app.generator;

public class DefaultTagLibrary implements TagLibrary {

    @Override
    public String commentMethodName() {
        return "rawHtml";
    }

    @Override
    public String wrapComment(String comment) {
        return "<!--" + comment + "-->";
    }

    @Override
    public String[] staticImports() {
        return new String[] { "j2html.TagCreator.*" };
    }

}
