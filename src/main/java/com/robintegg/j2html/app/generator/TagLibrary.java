package com.robintegg.j2html.app.generator;

public interface TagLibrary {

    String commentMethodName();

    String wrapComment(String comment);

    String[] staticImports();

}
