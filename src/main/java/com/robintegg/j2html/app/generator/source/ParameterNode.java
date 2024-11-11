package com.robintegg.j2html.app.generator.source;

public final class ParameterNode {

    private String plainValue;
    private boolean asDomContent;
    private boolean raw;
    private Builder builder;

    ParameterNode(String plainValue, boolean asDomContent, boolean raw) {
        this.plainValue = plainValue;
        this.asDomContent = asDomContent;
        this.raw = raw;
    }

    ParameterNode(Builder builder) {
        this.builder = builder;
    }

    public String printParam(String prefix, int indentLevel) {
        String indent = prefix.repeat(indentLevel);
        StringBuilder sb = new StringBuilder();
        if (plainValue != null) {
            // String insertionValue = strip ? plainValue.strip() : plainValue;
            if( raw ) {
                sb.append(indent).append("rawHtml(\"").append(plainValue).append("\")");
            }
            else if (asDomContent) {
                sb.append(indent).append("text(\"").append(plainValue).append("\")");
            } else {
                sb.append("\"").append(plainValue).append("\"");
            }
        } else if (builder != null) {
            sb.append(builder.printBuilder(prefix, indentLevel));
        }
        return sb.toString();
    }

    public boolean isDomContent() {
        return builder != null || asDomContent;
    }

}
