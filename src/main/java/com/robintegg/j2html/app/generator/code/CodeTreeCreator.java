package com.robintegg.j2html.app.generator.code;

public class CodeTreeCreator {

    public static CodeTree codeTree() {
        return new CodeTree();
    }

    public static BuilderMethodCall builder(String methodName) {
        return new BuilderMethodCall(methodName);
    }

    public static ChainedMethodCall chainedMethodCall(String methodName) {
        return new ChainedMethodCall(methodName);
    }

    public static MethodCall methodCall(String methodName) {
        return new MethodCall(methodName);
    }

    public static PlainTextParameter plainTextParameter(String plainText) {
        return new PlainTextParameter(plainText);
    }

    public static MethodCallParameter methodCallParameter(MethodCall methodCall) {
        return new MethodCallParameter(methodCall);
    }

    public static BuilderMethodCallParameter builderMethodCallParameter(BuilderMethodCall builderMethodCall) {
        return new BuilderMethodCallParameter(builderMethodCall);
    }

}
