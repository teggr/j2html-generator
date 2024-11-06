package com.robintegg.j2html.app.generator;

public class J2HtmlWrapper {
    private final boolean includeImports;
    private final boolean useExtensions;

    public J2HtmlWrapper(boolean includeImports, boolean useExtensions) {
        this.includeImports = includeImports;
        this.useExtensions = useExtensions;
    }

    public String getJ2HtmlCode(String j2HtmlCode) {
        StringBuilder b = new StringBuilder();
        if(includeImports) {
            b.append("import static j2html.TagCreator.*;\n");
            if(useExtensions) {
                b.append("import static dev.rebelcraft.j2html.ext.ExtendedTagCreator.*;\n");
            }
            b.append("\n");
        }
        b.append(j2HtmlCode);
        b.append("\n");
        return b.toString();
    }
}
