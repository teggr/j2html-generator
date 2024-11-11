package com.robintegg.j2html.app.generator.source;

import java.util.ArrayList;
import java.util.List;

public final class CodeTree {

    private List<Builder> builders = new ArrayList<>();

    CodeTree() {
    }

    public CodeTree withBuilder(Builder builder) {
        builders.add(builder);
        return this;
    }

    public String printCode(String prefix) {
        int indentLevel = 0;

        StringBuilder sb = new StringBuilder();
        for (Builder builder : builders) {
            sb.append(builder.printBuilder(prefix, indentLevel));
        }
        return sb.toString();
    }

}
