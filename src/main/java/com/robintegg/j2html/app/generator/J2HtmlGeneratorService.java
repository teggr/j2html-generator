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
        return convertToJ2Html(htmlText);
    }

    public static String convertToJ2Html(String inputHtml) {
        Document document = Jsoup.parse(inputHtml);
        Element root = document.body().child(0);
        return convert(root, 0);
    }

    private static String convert(Element element, int indentation) {
        StringBuilder output = new StringBuilder();
        output.append(" ".repeat(indentation * 2));
        output.append(element.tagName()).append("(");

        for (Attribute attr : element.attributes()) {
            output.append("attrs(\"").append(attr.getKey()).append("=").append(attr.getValue()).append("\"), ");
        }

        if (element.children().isEmpty()) {
            output.append("),\n");
        } else {
            output.append(",\n");
            for (Element child : element.children()) {
                output.append(convert(child, indentation + 1));
            }
            output.append(" ".repeat(indentation * 2)).append("),\n");
        }

        return output.toString();
    }

}
