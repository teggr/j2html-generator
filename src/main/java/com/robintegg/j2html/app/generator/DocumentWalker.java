package com.robintegg.j2html.app.generator;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

class DocumentWalker {

    public String walk(Document document) {
        return recursivelyWalkDocumentElements(document.childNode(0), 0);
    }

    private String recursivelyWalkDocumentElements(Node node, int indentationLevel) {

        StringBuilder out = new StringBuilder();

        String tagName = getTagName(node);
        out.append(indent(indentationLevel) + tagName + "(");

        boolean childrenProcessed = false;

        if (nodeHasOnlyTextChildren(node)) {

            out.append("\"" + nodeTextContent(node).trim() + "\"");

            childrenProcessed = true;

        }

        boolean indentClose = false;
        int closeIndent = indentationLevel;
        boolean requireClose = true;
        boolean attributesAdded = false;

        List<Attribute> attributeList = node.attributes().asList();
        int attributesSize = attributeList.size();
        if (attributesSize > 0) {

            attributesAdded = true;

            // close tag
            out.append(")\n");

            for (int i = 0; i < attributesSize; i++) {
                Attribute attribute = attributeList.get(i);
                int attributeIndent = indentationLevel + 2;
                out.append(indent(attributeIndent) + ".attr(\"" + attribute.getKey() + "\"");
                if (attribute.hasDeclaredValue()) {
                    out.append(", \"" + attribute.getValue() + "\")");
                } else {
                    out.append(")");
                }
                if (i < attributesSize - 1) {
                    out.append("\n");
                }
            }

            requireClose = false;
        }

        if (!childrenProcessed) {
            List<Node> nodeChildren = node.childNodes().stream()
                    .filter(not(DocumentWalker::isEmptyTextNode)).collect(Collectors.toList());
            int childNodeSize = nodeChildren.size();
            if (childNodeSize > 0) {

                // always close children
                requireClose = true;

                out.append("\n");

                int childIndentationLevel = indentationLevel + 2;

                if (attributesAdded) {
                    out.append(indent(childIndentationLevel) + ".with(\n");
                    closeIndent = childIndentationLevel;
                    childIndentationLevel = childIndentationLevel + 2;
                }

                for (int i = 0; i < childNodeSize; i++) {
                    Node childNode = nodeChildren.get(i);

                    if (isTextNode(childNode)) {
                        out.append(indent(childIndentationLevel) + "text(\"" + childNodeTextContent(childNode) + "\")");
                    } else {
                        out.append(recursivelyWalkDocumentElements(childNode, childIndentationLevel));
                    }
                    if (i < childNodeSize - 1) {
                        out.append(",\n");
                    }
                }

                // close tag after children
                out.append("\n");

                indentClose = true;

            }
        }

        if (requireClose) {

            if (indentClose) {

                out.append(indent(closeIndent) + ")");

            } else {

                // close tag
                out.append(")");

            }

        }

        return out.toString();

    }

    private String childNodeTextContent(Node childNode) {
        return childNode.attr("#text");
    }

    private static String getTagName(Node node) {
        if (isTextNode(node)) {
            return "text";
        }
        return node.nodeName();
    }


    private static String indent(int indentationLevel) {
        return " ".repeat(indentationLevel);
    }

    private String nodeTextContent(Node node) {
        return node.childNodes().stream()
                .map(n -> n.outerHtml())
                .collect(Collectors.joining());
    }

    private static boolean isEmptyTextNode(Node node) {
        return isTextNode(node) && node.outerHtml().isBlank();
    }

    private boolean nodeHasOnlyTextChildren(Node node) {
        return node.childNodeSize() > 0 &&
                node.childNodes().stream()
                        .allMatch(n -> isTextNode(n));
    }

    private static boolean isTextNode(Node node) {
        return "#text".equals(node.nodeName());
    }


}
