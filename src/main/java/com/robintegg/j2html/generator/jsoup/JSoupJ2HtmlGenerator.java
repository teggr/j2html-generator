package com.robintegg.j2html.generator.jsoup;

import com.robintegg.j2html.app.generator.J2HtmlGenerator;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Primary
@Component
class JSoupJ2HtmlGenerator implements J2HtmlGenerator {

    @RequiredArgsConstructor
    private static class HtmlNode {
        private final Element element;
        private final List<HtmlNode> children;

        @Override
        public String toString() {
            return element.tagName();
        }

        public void render(GeneratorWriter generatorWriter) {

            generatorWriter.start(element);

            if(!children.isEmpty())
            {
                for(HtmlNode child: children ) {
                    child.render(generatorWriter);
                }
            }

            generatorWriter.end(element);

        }
    }

    private interface GeneratorWriter {
        void start(Element e);

        void end(Element e);

    }

    @Override
    public String generateFromHtml(String htmlText) {

        // build simple tree
        // visit the tree and output the source code

        Document doc = Jsoup.parse(htmlText);

        HtmlNode root = processDocument(doc);

        root.render(new GeneratorWriter() {
            int level = 0;
            @Override
            public void start(Element e) {
                level++;
                for (int i = 0; i < level; i++) {
                    System.out.print("\t");
                }
                System.out.println(e.tagName());
            }

            @Override
            public void end(Element e) {
                System.out.println("\n");
                level--;
            }
        });

        return "";

    }

    private HtmlNode processDocument(Document doc) {

        return processElement(doc);
    }

    private HtmlNode processElement(Element element) {

        // System.out.println(element);

        List<HtmlNode> children = new ArrayList<>();

        Elements allElements = element.children();

        for(int i = 0; i < allElements.size(); i++) {

            Element child = allElements.get(i);

            children.add( processElement(child) );

        }

        return new HtmlNode( element, children );
    }

}
