package com.robintegg.j2html.app.generator;

public class J2HtmlWrapper {
    public String getJ2HtmlCode(String j2HtmlCode) {
        StringBuilder b = new StringBuilder();
        b.append("import static j2html.TagCreator.*;\n");
        b.append("\n");
        b.append(j2HtmlCode);
        b.append("\n");
        return b.toString();
    }
}
