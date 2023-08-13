package com.robintegg.j2html.app.generator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service
public class J2HtmlGeneratorService implements J2HtmlGenerator {

    @Override
    public String generateFromHtml(String htmlText) {
        Handler h = new Handler();
        DocumentWalker walker = new DocumentWalker();
        Document document = Jsoup.parse(htmlText);
        walker.walk( document, h );
        return h.getJ2HtmlCode();
    }

}
