package com.robintegg.j2html.app.generator;

import org.jsoup.nodes.Node;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HtmlToJ2HtmlConverter {

    public static final String INDENT = "  ";
    private final TagLibrary tagLibrary;

    public HtmlToJ2HtmlConverter(TagLibrary tagLibrary) {
        this.tagLibrary = tagLibrary;
    }

    public String walk(Node root) {
//        SourceCodeNode sourceCodeNode = new CodeNode();
//        convertElementToCode(root, sourceCodeNode);
//        return sourceCodeNode.printSource();
        return "";
    }

    private static void convertElementToCode(Node node) {

//        // Handle LEAF(text) nodes
//        if (node instanceof TextNode) {
//            StringBuilder text = new StringBuilder();
//            text.append("text(\"")
//                    .append(((TextNode) node).text().replace("\"", "\\\""))
//                    .append("\")");
//            parent.addChild(new SourceCodeNode(text.toString()));
//            return;
//        }
//
//        Element element = (Element) node;
//
//        // Map JSoup element to j2html code based on tag
//        SourceCodeNode currentNode = new SourceCodeNode(element.tagName() + "()");
//        parent.addChild(currentNode);
//
//        // Append attributes
//        element.attributes().forEach(attr -> {
//            StringBuilder attrText = new StringBuilder();
//            addAttribute(attrText, attr.getKey(), attr.getValue());
//            currentNode.addChild(new SourceCodeNode(attrText.toString()));
//        });
//
//        List<Node> children = element.childNodes();
//
//        if (!children.isEmpty()) {
//
//            SourceCodeNode childContainer = new SourceCodeNode(".with(");
//            currentNode.addChild(childContainer);
//
//            for (Node child : children) {
//
//                if (child instanceof TextNode) {
//                    if (((TextNode) child).isBlank() && (child.previousSibling() == null || child.previousSibling() instanceof Element)) {
//                        // ignore empty text nodes at the start
//                    } else if (((TextNode) child).isBlank() && (child.nextSibling() == null || child.nextSibling() instanceof Element)) {
//                        // ignore empty text nodes at the end
//                    } else {
//                        StringBuilder childBuilder = new StringBuilder();
//                        childBuilder.append("text(\"").append(((TextNode) child).text().replace("\"", "\\\"")).append("\")");
//                        childContainer.addChild(new SourceCodeNode(childBuilder.toString()));
//                    }
//                } else if (child instanceof Element) {
//                    StringBuilder childBuilder = new StringBuilder();
//                    convertElementToCode((Element) child, childContainer);
//                }
//            }
//
//            SourceCodeNode endchildContainer = new SourceCodeNode(")");
//            currentNode.addChild(endchildContainer);
//
//        }


    }

    private static void addAttribute(StringBuilder builder, String key, String value) {
        switch (key) {
            case "class" -> builder.append(".withClasses(").append(classList(value)).append(")");
            case "id" -> builder.append(".withId(\"").append(value).append("\")");
            case "href" -> builder.append(".withHref(\"").append(value).append("\")");
            case "src" -> builder.append(".withSrc(\"").append(value).append("\")");
            case "alt" -> builder.append(".withAlt(\"").append(value).append("\")");
            case "title" -> builder.append(".withTitle(\"").append(value).append("\")");
            default -> builder.append(".attr(\"").append(key).append("\", \"").append(value).append("\")");
        }
    }

    private static String classList(String value) {
        return Stream.of(value.split("\\s"))
                .map(c -> "\"" + c + "\"")
                .collect(Collectors.joining(","));
    }

}
