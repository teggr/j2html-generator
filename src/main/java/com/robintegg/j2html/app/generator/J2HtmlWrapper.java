package com.robintegg.j2html.app.generator;

public class J2HtmlWrapper {
    private final boolean includeImports;

    public J2HtmlWrapper(boolean includeImports) {
        this.includeImports = includeImports;
    }

    public String getJ2HtmlCode(String j2HtmlCode) {
        StringBuilder b = new StringBuilder();
        if(includeImports) {
            b.append("import static j2html.TagCreator.*;\n");
            b.append("\n");
        }
        b.append(j2HtmlCode);
        b.append("\n");
        return b.toString();
    }
}
