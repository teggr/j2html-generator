package com.robintegg.j2html.app.generator;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Node;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

class J2HtmlCodeBuilder {

    public String walk(Node root) {
        return recursivelyWalkDocumentElements(root, 0);
    }

    private String recursivelyWalkDocumentElements(Node node, int indentationLevel) {

        StringBuilder out = new StringBuilder();

        String tagName = getTagName(node);
        out.append(indent(indentationLevel) + tagName + "(");

        boolean childrenProcessed = false;
        boolean isInputNode = isInputNode(node);

        if (!isInputNode && nodeHasOnlyTextChildren(node)) {

            out.append("\"" + nodeTextContent(node).strip() + "\"");

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

                if (isClassAttribute(attribute)) {

                    String[] classes = attribute.getValue().split(" ");
                    if(classes.length > 1) {
                        out.append(indent(attributeIndent) + ".withClasses("+ Stream.of(classes).map(c -> "\"" + c + "\"").collect(Collectors.joining(", ")) +")");
                    } else {
                        out.append(indent(attributeIndent) + ".withClass(\"" + attribute.getValue() + "\")");
                    }

                } else if (isIdAttribute(attribute)) {

                    out.append(indent(attributeIndent) + ".withId(\"" + attribute.getValue() + "\")");

                } else if (isNameAttribute(attribute)) {

                    out.append(indent(attributeIndent) + ".withName(\"" + attribute.getValue() + "\")");

                } else if (isTypeAttribute(attribute)) {

                    out.append(indent(attributeIndent) + ".withType(\"" + attribute.getValue() + "\")");

                } else if (isHrefAttribute(attribute)) {

                    out.append(indent(attributeIndent) + ".withHref(\"" + attribute.getValue() + "\")");

                } else {

                    out.append(indent(attributeIndent) + ".attr(\"" + attribute.getKey() + "\"");
                    if (attribute.hasDeclaredValue()) {
                        out.append(", \"" + attribute.getValue() + "\")");
                    } else {
                        out.append(")");
                    }

                }
                if (i < attributesSize - 1) {
                    out.append("\n");
                }
            }

            requireClose = false;
        }

        if (!childrenProcessed) {
            List<Node> nodeChildren = node.childNodes().stream()
                    .filter(not(J2HtmlCodeBuilder::isEmptyTextNode)).collect(Collectors.toList());
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

    private boolean isHrefAttribute(Attribute attribute) {
        return "href".equalsIgnoreCase(attribute.getKey());
    }

    private boolean isNameAttribute(Attribute attribute) {
        return "name".equalsIgnoreCase(attribute.getKey());
    }

    private boolean isTypeAttribute(Attribute attribute) {
        return "type".equalsIgnoreCase(attribute.getKey());
    }

    private boolean isIdAttribute(Attribute attribute) {
        return "id".equalsIgnoreCase(attribute.getKey());
    }

    private boolean isClassAttribute(Attribute attribute) {
        return "class".equalsIgnoreCase(attribute.getKey());
    }

    private boolean isInputNode(Node node) {
        return "input".equals(node.nodeName());
    }

    private String childNodeTextContent(Node childNode) {
        return escapeOuterHtml(childNode.attr("#text"));
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
                .map(html -> html.strip())
                .collect(Collectors.joining(""));
    }

    private static String escapeOuterHtml(String html) {
        return html.replaceAll("\n", "\\\\n");
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
