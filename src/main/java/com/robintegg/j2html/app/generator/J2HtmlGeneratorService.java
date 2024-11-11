package com.robintegg.j2html.app.generator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
class J2HtmlGeneratorService implements J2HtmlGenerator {

    private static Map<String, TagLibrary> tagLibrariesById = new HashMap();

    static {
        tagLibrariesById.put("j2htmlExtensions", new J2HtmlTagLibrary());
    }

    @Override
    public String generateFromHtml(String packageName, boolean includeImports, String tagLibraryId, String htmlText, String template, String testName) {

        TagLibrary tagLibrary = tagLibrariesById.getOrDefault(tagLibraryId, new DefaultTagLibrary());

        HtmlToJ2HtmlConverter walker = new HtmlToJ2HtmlConverter(tagLibrary);
        String trimmedHtml = htmlText.trim();
        Document document = Jsoup.parse(trimmedHtml);
        String j2HtmlCode = walker.convert(startLocation(trimmedHtml, document));
        J2HtmlWrapper j2HtmlWrapper = new J2HtmlWrapper(packageName, includeImports, tagLibrary, template, testName);
        return j2HtmlWrapper.getJ2HtmlCode(trimmedHtml, j2HtmlCode);
    }

    private List<Node> startLocation(String htmlText, Document document) {
        if (htmlText.contains("<html")) {
            return Stream.of((Node)document).toList();
        } else if (htmlText.contains("<head")) {
            return Stream.of((Node)document.head()).toList();
        } else if (htmlText.contains("<body")) {
            return Stream.of((Node)document.body()).toList();
        } else {
            return document.body().childNodes();
        }
    }

}
