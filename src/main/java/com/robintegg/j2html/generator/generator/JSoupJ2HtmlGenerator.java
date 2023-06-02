package com.robintegg.j2html.generator.generator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Parser;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Primary
@Component
class JSoupJ2HtmlGenerator implements J2HtmlGenerator {


    @Override
    public String generateFromHtml(String htmlText) {

        Document doc = Jsoup.parse(htmlText);

        StringBuilder b = new StringBuilder();

        // is html empty
        if( doc.attributesSize() > 0) {
            b.append( "html(" );
        }

        // is head empty
        Element head = doc.head();
        if(head.attributesSize() > 0 || head.childNodeSize() > 0) {
            // process head
            b.append( "head()" );
        }

        Element body = doc.body();
        if(body.attributesSize() > 0 || body.childNodeSize() > 0) {

            b.append( "body(" );

          // process body
            if(body.attributesSize()==0) {
                //
                return processNodes(body.childNodes());
            }

            b.append( ")" );
        }

        // now recurse through the tree and create the java code
        //

        if( doc.attributesSize() > 0) {
            b.append( ")\n" );
        }

        b.append(";\n");

        return b.toString();

    }

    private String processNodes(List<Node> nodes) {

        List<String> names = new ArrayList<>();

        for (Node node: nodes) {

            names.add( node.nodeName() );

            names.add( processNodes( node.childNodes() ) );

        }

        return names.toString();

    }

}
