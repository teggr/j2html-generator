package com.robintegg.j2html.app.generator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.springframework.stereotype.Service;

@Service
class J2HtmlGeneratorService implements J2HtmlGenerator {

    @Override
    public String generateFromHtml(boolean includeImports, boolean useExtensions, String htmlText, String template) {
        J2HtmlCodeBuilder walker = new J2HtmlCodeBuilder(useExtensions);
        String trimmedHtml = htmlText.trim();
        Document document = Jsoup.parse(trimmedHtml);
        String j2HtmlCode = walker.walk(startLocation(trimmedHtml, document));
        J2HtmlWrapper j2HtmlWrapper = new J2HtmlWrapper(includeImports, useExtensions, template);
        return j2HtmlWrapper.getJ2HtmlCode(trimmedHtml, j2HtmlCode);
    }

    private Node startLocation(String htmlText, Document document) {
        if (htmlText.contains("<html")) {
            return document;
        } else if (htmlText.contains("<head")) {
            return document.head();
        } else if (htmlText.contains("<body")) {
            return document.body();
        } else {
            return document.body().childNode(0);
        }
    }

}
