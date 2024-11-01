package nl.han.ica.icss.ast;

import java.util.ArrayList;
import java.util.Objects;

public class FunctionParameters extends ASTNode {
    public ArrayList<VariableReference> parameters = new ArrayList<>();

    public FunctionParameters() { }

    public FunctionParameters(ArrayList<VariableReference> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String getNodeLabel() {
        return "FunctionParameters";
    }

    @Override
    public ArrayList<ASTNode> getChildren() {
        ArrayList<ASTNode> children = new ArrayList<>();
        children.addAll(parameters);

        return children;
    }

    @Override
    public ASTNode addChild(ASTNode child) {
        parameters.add((VariableReference) child);

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FunctionParameters functionParameters = (FunctionParameters) o;
        return Objects.equals(parameters, functionParameters.parameters);
    }
}
