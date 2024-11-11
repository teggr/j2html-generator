package com.robintegg.j2html.app.generator.source;

import org.junit.jupiter.api.Test;

import static com.robintegg.j2html.app.generator.source.CodeTreeNodeCreator.*;
import static org.assertj.core.api.Assertions.assertThat;

class SourceCodeNodeTest {

    @Test
    void shouldOutputEmptyCode() {

        CodeTree codeTree = codeTree();

        String output = codeTree.printCode("  ");

        assertThat(output).isEqualTo("");

    }

    @Test
    void shouldOutputBuilder() {

        CodeTree codeTree = codeTree();
        codeTree.withBuilder(builder("h1"));

        String output = codeTree.printCode("  ");

        assertThat(output).isEqualTo("h1()");

    }

    @Test
    void shouldOutputBuilderWithChainedMethods() {

        CodeTree codeTree = codeTree();
        Builder builder = builder("h1");
        builder.withChainedCall(methodCall("with"));
        builder.withChainedCall(methodCall("withId"));
        builder.withChainedCall(methodCall("withHef"));
        codeTree.withBuilder(builder);

        String output = codeTree.printCode("  ");

        assertThat(output).isEqualTo("""
                h1()
                  .with()
                  .withId()
                  .withHef()""");

    }

    @Test
    void shouldOutputBuilderWithChainedMethodsAndSingleStringParameter() {

        CodeTree codeTree = codeTree();
        Builder builder = builder("h1");

        MethodCall with = methodCall("with");
        builder.withChainedCall(with);

        MethodCall withId = methodCall("withId");
        withId.addParameter(valueParameterNode("some-id"));
        builder.withChainedCall(withId);

        MethodCall withHef = methodCall("withHef");
        withHef.addParameter(valueParameterNode("http://"));
        builder.withChainedCall(withHef);

        codeTree.withBuilder(builder);

        String output = codeTree.printCode("  ");

        assertThat(output).isEqualTo("""
                h1()
                  .with()
                  .withId("some-id")
                  .withHef("http://")""");

    }

    @Test
    void shouldOutputBuilderWithChainedMethodsAndMultipleStringParameters() {

        CodeTree codeTree = new CodeTree();
        Builder builder = builder("h1");

        MethodCall with = methodCall("with");
        builder.withChainedCall(with);

        MethodCall withId = methodCall("withId");
        withId.addParameter(valueParameterNode("some-id"));
        builder.withChainedCall(withId);

        MethodCall withHef = methodCall("withHef");
        withHef.addParameter(valueParameterNode("http://"));
        withHef.addParameter(valueParameterNode("something else"));
        builder.withChainedCall(withHef);

        codeTree.withBuilder(builder);

        String output = codeTree.printCode("  ");

        assertThat(output).isEqualTo("""
                h1()
                  .with()
                  .withId("some-id")
                  .withHef("http://","something else")""");

    }

    @Test
    void shouldOutputBuilderWithChainedMethodsAndSingleBuilderParameter() {

        CodeTree codeTree =codeTree();
        Builder builder = builder("h1");

        MethodCall withId = methodCall("withId");
        withId.addParameter(valueParameterNode("some-id"));
        builder.withChainedCall(withId);

        MethodCall withHef = methodCall("withHef");
        withHef.addParameter( valueParameterNode("http://"));
        builder.withChainedCall(withHef);

        Builder pb = builder("p");

        MethodCall with = methodCall("with");
        with.addParameter(builderParameterNode(pb));
        builder.withChainedCall(with);

        codeTree.withBuilder(builder);

        String output = codeTree.printCode("  ");

        assertThat(output).isEqualTo("""
                h1()
                  .withId("some-id")
                  .withHef("http://")
                  .with(
                    p()
                  )""");

    }

    @Test
    void shouldOutputBuilderWithChainedMethodsAndMultipleBuilderParameter() {

        CodeTree codeTree = codeTree();
        Builder builder = builder("h1");

        MethodCall withId = methodCall("withId");
        withId.addParameter(valueParameterNode("some-id"));
        builder.withChainedCall(withId);

        MethodCall withHef = methodCall("withHef");
        withHef.addParameter(valueParameterNode("http://"));
        builder.withChainedCall(withHef);

        Builder pb = builder("p");
        Builder div = builder("div");

        MethodCall with = methodCall("with");
        with.addParameter(builderParameterNode(pb));
        with.addParameter(builderParameterNode(div));
        builder.withChainedCall(with);

        codeTree.withBuilder(builder);

        String output = codeTree.printCode("  ");

        assertThat(output).isEqualTo("""
                h1()
                  .withId("some-id")
                  .withHef("http://")
                  .with(
                    p(),
                    div()
                  )""");

    }

    @Test
    void shouldOutputBuilderWithChainedMethodsAndMultipleNestedParameters() {

        CodeTree codeTree = codeTree();
        Builder builder = builder("h1");

        MethodCall withId = methodCall("withId");
        withId.addParameter(valueParameterNode("some-id"));
        builder.withChainedCall(withId);

        MethodCall withHef = methodCall("withHef");
        withHef.addParameter(valueParameterNode("http://"));
        builder.withChainedCall(withHef);

        Builder pb = builder("p");

        Builder div = builder("div");
        MethodCall nestedWith = methodCall("with");
        nestedWith.addParameter(builderParameterNode(builder("a")));
        nestedWith.addParameter(builderParameterNode(builder("hr")));
        div.withChainedCall(nestedWith);

        MethodCall with = methodCall("with");
        with.addParameter(builderParameterNode(pb));
        with.addParameter(builderParameterNode(div));
        builder.withChainedCall(with);

        codeTree.withBuilder(builder);

        String output = codeTree.printCode("  ");

        assertThat(output).isEqualTo("""
                h1()
                  .withId("some-id")
                  .withHef("http://")
                  .with(
                    p(),
                    div()
                      .with(
                        a(),
                        hr()
                      )
                  )""");

    }

    @Test
    void shouldOutputBuilderWithChainedMethodsAndMultipleNestedParameterTypes() {

        CodeTree codeTree = codeTree();
        Builder builder = builder("h1");

        MethodCall withId = methodCall("withId");
        withId.addParameter(valueParameterNode("some-id"));
        builder.withChainedCall(withId);

        MethodCall withHef = methodCall("withHef");
        withHef.addParameter(valueParameterNode("http://"));
        builder.withChainedCall(withHef);

        Builder pb = builder("p");

        Builder div = builder("div");
        MethodCall nestedWith = methodCall("with");
        nestedWith.addParameter(builderParameterNode(builder("a")));
        nestedWith.addParameter(builderParameterNode(builder("hr")));
        div.withChainedCall(nestedWith);

        MethodCall with = methodCall("with");
        with.addParameter(valueParameterNode("start value"));
        with.addParameter(builderParameterNode(pb));
        with.addParameter(valueParameterNode("middle value"));
        with.addParameter(builderParameterNode(div));
        with.addParameter(valueParameterNode("end value"));
        builder.withChainedCall(with);

        codeTree.withBuilder(builder);

        String output = codeTree.printCode("  ");

        assertThat(output).isEqualTo("""
                h1()
                  .withId("some-id")
                  .withHef("http://")
                  .with(
                    text("start value"),
                    p(),
                    text("middle value"),
                    div()
                      .with(
                        a(),
                        hr()
                      ),
                    text("end value")
                  )""");

    }

}