package com.robintegg.j2html.app.generator.source;


import java.util.ArrayList;
import java.util.List;

public final class Builder {

    private final String methodName;
    private List<MethodCall> chainedCalls = new ArrayList<>();

    Builder(String methodName) {
        this.methodName = methodName;
    }

    public Builder withChainedCall(MethodCall chainedCall) {
        chainedCalls.add(chainedCall);
        return this;
    }

    public String printBuilder(String prefix, int indentLevel) {
        String indent = prefix.repeat(indentLevel);
        StringBuilder sb = new StringBuilder();
        sb.append(indent).append(methodName).append("()");
        if (!chainedCalls.isEmpty()) {
            for (MethodCall chainedCall : chainedCalls) {
                sb.append("\n");
                sb.append(chainedCall.printMethod(prefix, indentLevel + 1));
            }
        }
        return sb.toString();
    }
}
