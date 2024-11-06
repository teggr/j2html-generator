package com.robintegg.j2html.app.generator;

public interface J2HtmlGenerator {

    String generateFromHtml(boolean includeImports, String htmlText);

}
