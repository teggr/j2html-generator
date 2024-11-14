package com.robintegg.j2html.app.generator;

import com.robintegg.j2html.app.generator.code.*;
import org.jsoup.nodes.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.robintegg.j2html.app.generator.code.CodeTreeCreator.*;

public class HtmlToJ2HtmlConverter {

    public static final String INDENT = "  ";
    private final TagLibrary tagLibrary;

    public HtmlToJ2HtmlConverter(TagLibrary tagLibrary) {
        this.tagLibrary = tagLibrary;
    }

    public String convert(List<Node> roots) {
        CodeTree codeTree = null;
        if (roots.size() == 1) {
            codeTree = convertRootElementToCode(roots.get(0));
        } else {
            codeTree = convertRootElementsToCode(roots);
        }
        return codeTree.printCode(INDENT);
    }

    private CodeTree convertRootElementsToCode(List<Node> roots) {

        CodeTree codeTree = codeTree();

        MethodCall each = methodCall("each");

        for (Node root : roots) {

            if (root instanceof TextNode) {

                // just a text node
                each.withParameter(
                        methodCallParameter(
                                methodCall("text")
                                        .withParameter(
                                                plainTextParameter(
                                                        childText((TextNode) root)))));

            } else if (root instanceof Comment) {

                // just a comment node
                each.withParameter(
                        methodCallParameter(
                                methodCall(tagLibrary.commentMethodName())
                                        .withParameter(
                                                plainTextParameter(
                                                        tagLibrary.wrapComment(
                                                                childComment((Comment) root))))));

            } else if (root instanceof Element) {

                each.withParameter(
                        builderMethodCallParameter(
                                convertElementToBuilder((Element) root)));

            }

        }

        codeTree.withMethodCall(each);

        return codeTree;

    }

    private CodeTree convertRootElementToCode(Node root) {

        CodeTree codeTree = codeTree();

        if (root instanceof TextNode) {

            // just a text node
            codeTree.withMethodCall(
                    methodCall("text")
                            .withParameter(
                                    plainTextParameter(
                                            childText((TextNode) root))));

        } else if (root instanceof Comment) {

            // just a comment node
            codeTree.withMethodCall(
                    methodCall(tagLibrary.commentMethodName())
                            .withParameter(
                                    plainTextParameter(
                                            tagLibrary.wrapComment(((Comment) root).getData()))));

        } else if (root instanceof Element) {

            codeTree.withBuilderMethodCall(
                    convertElementToBuilder((Element) root));

        }

        return codeTree;

    }

    private BuilderMethodCall convertElementToBuilder(Element element) {

        String tagName = element.nodeName();

        BuilderMethodCall builderMethodCall = builder(tagName);

        Attributes attributes = element.attributes();
        if (!attributes.isEmpty()) {
            attributes.forEach(a -> {
                builderMethodCall.withChainedMethodCall(convertAttributeToMethodCall(a.getKey(), a.getValue()));
            });

        }

        List<Node> nodes = element.childNodes();

        if (!nodes.isEmpty()) {

            ChainedMethodCall with = chainedMethodCall("with");

            element.childNodes().forEach(childNode -> {

                if (childNode instanceof TextNode) {
                    if (((TextNode) childNode).isBlank() && (childNode.previousSibling() == null || childNode.previousSibling() instanceof Element)) {
                        // ignore empty text nodes at the start
                    } else if (((TextNode) childNode).isBlank() && (childNode.nextSibling() == null || childNode.nextSibling() instanceof Element)) {
                        // ignore empty text nodes at the end
                    } else {
                        with.withParameter(methodCallParameter(
                                methodCall("text")
                                        .withParameter(
                                                plainTextParameter(
                                                        childText((TextNode) childNode)))));
                    }
                } else if (childNode instanceof Comment) {
                    with.withParameter(methodCallParameter(
                                    methodCall(tagLibrary.commentMethodName())
                                            .withParameter(
                                                    plainTextParameter(
                                                            tagLibrary.wrapComment(((Comment) childNode).getData())))));
                } else if (childNode instanceof Element) {
                    with.withParameter(
                            builderMethodCallParameter(
                                    convertElementToBuilder((Element) childNode)));
                }

            });

            if (with.hasParameters()) {
                builderMethodCall.withChainedMethodCall(with);
            }

        }

        return builderMethodCall;

    }

    private static String childText(TextNode childNode) {
        return childNode.text().replace("\"", "\\\"");
    }

    private static String childComment(Comment childNode) {
        return childNode.getData().replace("\"", "\\\"");
    }

    private static ChainedMethodCall convertAttributeToMethodCall(String key, String value) {
        return switch (key) {
            case "class" -> chainedMethodCall("withClasses").withParameters(classList(value));
            case "id" -> chainedMethodCall("withId").withParameter(plainTextParameter(value));
            case "href" -> chainedMethodCall("withHref").withParameter(plainTextParameter(value));
            case "src" -> chainedMethodCall("withSrc").withParameter(plainTextParameter(value));
            case "alt" -> chainedMethodCall("withAlt").withParameter(plainTextParameter(value));
            case "type" -> chainedMethodCall("withType").withParameter(plainTextParameter(value));
            case "name" -> chainedMethodCall("withName").withParameter(plainTextParameter(value));
            case "scope" -> chainedMethodCall("withScope").withParameter(plainTextParameter(value));
            case "colspan" -> chainedMethodCall("withColspan").withParameter(plainTextParameter(value));
            case "for" -> chainedMethodCall("withFor").withParameter(plainTextParameter(value));
            case "placeholder" -> chainedMethodCall("withPlaceholder").withParameter(plainTextParameter(value));
            case "rows" -> chainedMethodCall("withRows").withParameter(plainTextParameter(value));
            case "value" -> chainedMethodCall("withValue").withParameter(plainTextParameter(value));
            case "title" -> chainedMethodCall("withTitle").withParameter(plainTextParameter(value));
            case "list" -> chainedMethodCall("withList").withParameter(plainTextParameter(value));
            case "disabled" -> chainedMethodCall("withCondDisabled").withParameter(booleanParameterFromValue(value));
            case "readonly" -> chainedMethodCall("withCondReadonly").withParameter(booleanParameterFromValue(value));
            case "multiple" -> chainedMethodCall("withMultiple").withParameter(booleanParameterFromValue(value));
            default ->
                    chainedMethodCall("attr").withParameter(plainTextParameter(key)).withParameter(plainTextParameter(value));
        };
    }

    private static BooleanParameter booleanParameterFromValue(String value) {
        return booleanParameter( value == null || value.isBlank() || !"false".equalsIgnoreCase(value) );
    }

    private static List<Parameter> classList(String value) {
        return Stream.of(value.split("\\s"))
                .map(CodeTreeCreator::plainTextParameter)
                .collect(Collectors.toList());
    }

}
