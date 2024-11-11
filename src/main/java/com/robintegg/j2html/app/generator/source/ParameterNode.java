package com.robintegg.j2html.app.generator.source;

public final class ParameterNode {

    private  String plainValue;
    private Builder builder;

    ParameterNode(String plainValue) {
        this.plainValue = plainValue;
    }

    ParameterNode(Builder builder) {
        this.builder = builder;
    }

    public String printParam(String prefix, int indentLevel, boolean wrap) {
        String indent = prefix.repeat(indentLevel);
        StringBuilder sb = new StringBuilder();
        if(plainValue != null ) {
            if(wrap) {
                sb.append(indent).append("text(\"").append(plainValue).append("\")");
            } else {
                sb.append("\"").append(plainValue).append("\"");
            }
        } else if(builder != null) {
            sb.append(builder.printBuilder(prefix, indentLevel));
        }
        return sb.toString();
    }

    public boolean isBuilder() {
        return builder != null;
    }
}
