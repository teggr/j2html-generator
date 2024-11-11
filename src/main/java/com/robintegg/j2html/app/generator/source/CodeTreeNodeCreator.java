package com.robintegg.j2html.app.generator.source;

public class CodeTreeNodeCreator {

    public static CodeTree codeTree() {
        return new CodeTree();
    }

    public static Builder builder(String methodName) {
        return new Builder(methodName);
    }

    public static MethodCall methodCall(String methodName) {
        return new MethodCall(methodName);
    }

    public static ParameterNode valueParameterNode(String value) {
        return new ParameterNode(value, false, false);
    }

    public static ParameterNode valueAsDomContentParameterNode(String value) {
        return new ParameterNode(value, true, false);
    }

    public static ParameterNode commentParameterNode(String value) {
        return new ParameterNode(value, true, true);
    }

    public static ParameterNode builderParameterNode(Builder builder) {
        return new ParameterNode(builder);
    }

}
