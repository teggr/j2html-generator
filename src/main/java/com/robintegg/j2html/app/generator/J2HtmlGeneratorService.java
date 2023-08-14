package com.robintegg.j2html.app.generator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.springframework.stereotype.Service;

@Service
class J2HtmlGeneratorService implements J2HtmlGenerator {

    @Override
    public String generateFromHtml(String htmlText) {
        DocumentWalker walker = new DocumentWalker();
        Document document = Jsoup.parse(htmlText, Parser.xmlParser());
        String j2HtmlCode = walker.walk(document);
        J2HtmlWrapper j2HtmlWrapper = new J2HtmlWrapper();
        return j2HtmlWrapper.getJ2HtmlCode(j2HtmlCode);
    }

}
