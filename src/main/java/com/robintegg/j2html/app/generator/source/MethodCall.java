package com.robintegg.j2html.app.generator.source;

import java.util.ArrayList;
import java.util.List;

public final class MethodCall {

    private final String methodName;
    private List<ParameterNode> parameters = new ArrayList<>();

    MethodCall(String methodName) {
        this.methodName = methodName;
    }

    public String printMethod(String prefix, int indentLevel) {
        String indent = prefix.repeat(indentLevel);
        StringBuilder sb = new StringBuilder();
        sb.append(indent).append(".").append(methodName).append("(");

        boolean anyBuilders = parameters.stream().anyMatch(ParameterNode::isBuilder);

        // if all inline then wrap in a join not each

        for (int i = 0; i < parameters.size(); i++) {
            ParameterNode param = parameters.get(i);
            boolean first = i == 0;
            boolean last = i == parameters.size() - 1;
            if (!first) {
                sb.append(",");
            }
            if (anyBuilders) {
                sb.append("\n");
            }
            sb.append(param.printParam(prefix, indentLevel + 1, (anyBuilders && !param.isBuilder())));
        }

        if (anyBuilders) {
            sb.append("\n").append(indent);
        }

        sb.append(")");
        return sb.toString();
    }

    public void addParameter(ParameterNode parameter) {
        parameters.add(parameter);
    }

}
