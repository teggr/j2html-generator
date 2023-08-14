package com.robintegg.j2html.app.generator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.springframework.stereotype.Service;

@Service
class J2HtmlGeneratorService implements J2HtmlGenerator {

    @Override
    public String generateFromHtml(String htmlText) {
        J2HtmlCodeBuilder walker = new J2HtmlCodeBuilder();
        Document document = Jsoup.parse(htmlText.trim());
        String j2HtmlCode = walker.walk(startLocation(htmlText, document));
        J2HtmlWrapper j2HtmlWrapper = new J2HtmlWrapper();
        return j2HtmlWrapper.getJ2HtmlCode(j2HtmlCode);
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
