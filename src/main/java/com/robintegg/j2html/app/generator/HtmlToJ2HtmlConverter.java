package com.robintegg.j2html.app.generator;

import com.robintegg.j2html.app.generator.source.*;
import org.jsoup.nodes.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.robintegg.j2html.app.generator.source.CodeTreeNodeCreator.*;

public class HtmlToJ2HtmlConverter {

    public static final String INDENT = "  ";
    private final TagLibrary tagLibrary;

    public HtmlToJ2HtmlConverter(TagLibrary tagLibrary) {
        this.tagLibrary = tagLibrary;
    }

    public String convert(Node root) {
        CodeTree codeTree = convertRootElementsToCode(root);
        return codeTree.printCode(INDENT);
    }

    private static CodeTree convertRootElementsToCode(Node root) {

        CodeTree codeTree = codeTree();

//        List<Node> rootNodes = root.childNodes();
//
//        for (Node rootNode : rootNodes) {

            Node rootNode = root;

            if (rootNode instanceof TextNode) {

                // just a text node

            } else if (rootNode instanceof Comment) {

                // just a comment node

            } else if (rootNode instanceof Element) {

                Builder b = convertElementToBuilder((Element) rootNode);

                codeTree.withBuilder(b);

            }

//        }

        return codeTree;

    }

    private static Builder convertElementToBuilder(Element element) {

        String tagName = element.nodeName();

        Builder builder = builder(tagName);

        // load attributes as methods

        Attributes attributes = element.attributes();
        if (!attributes.isEmpty()) {
            attributes.forEach(a -> {
                builder.withChainedCall(convertAttributeToMethodCall(a.getKey(), a.getValue()));
            });

        }

        List<Node> nodes = element.childNodes();

        if (!nodes.isEmpty()) {

            MethodCall with = methodCall("with");

            element.childNodes().forEach(childNode -> {

                if (childNode instanceof TextNode) {
                    if (((TextNode) childNode).isBlank() && (childNode.previousSibling() == null || childNode.previousSibling() instanceof Element)) {
                        // ignore empty text nodes at the start
                    } else if (((TextNode) childNode).isBlank() && (childNode.nextSibling() == null || childNode.nextSibling() instanceof Element)) {
                        // ignore empty text nodes at the end
                    } else {
                        with.withParameter(valueAsDomContentParameterNode(childText((TextNode) childNode)));
                    }
                } else if (childNode instanceof Comment) {
                    with.withParameter(commentParameterNode( "<!--" + ((Comment)childNode).getData() + "-->" ));
                } else if (childNode instanceof Element) {
                    with.withParameter(builderParameterNode(convertElementToBuilder((Element) childNode)));
                }

            });

            if (with.hasParameters()) {
                builder.withChainedCall(with);
            }

        }

        return builder;

    }

    private static String childText(TextNode childNode) {
        return childNode.text().replace("\"", "\\\"");
    }

    private static MethodCall convertAttributeToMethodCall(String key, String value) {
        return switch (key) {
            case "class" -> methodCall("withClasses").withParameters(classList(value));
            case "id" -> methodCall("withId").withParameter(valueParameterNode(value));
            case "href" -> methodCall("withHref").withParameter(valueParameterNode(value));
            case "src" -> methodCall("withSrc").withParameter(valueParameterNode(value));
            case "alt" -> methodCall("withAlt").withParameter(valueParameterNode(value));
            case "type" -> methodCall("withType").withParameter(valueParameterNode(value));
            case "name" -> methodCall("withName").withParameter(valueParameterNode(value));
            default ->
                    methodCall("attr").withParameter(valueParameterNode(key)).withParameter(valueParameterNode(value));
        };
    }

    private static List<ParameterNode> classList(String value) {
        return Stream.of(value.split("\\s"))
                .map(CodeTreeNodeCreator::valueParameterNode)
                .collect(Collectors.toList());
    }

}
