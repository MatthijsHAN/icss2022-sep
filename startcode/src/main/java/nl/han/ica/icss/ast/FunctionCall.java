package nl.han.ica.icss.ast;

import java.util.ArrayList;
import java.util.Objects;

public class FunctionCall extends ASTNode {

    public FunctionReference name;
    public ArrayList<Expression> parameters = new ArrayList<>();

    public FunctionCall() { }

    public FunctionCall(FunctionReference name, ArrayList<Expression> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    @Override
    public String getNodeLabel() {
        return "FunctionCall";
    }

    @Override
    public ArrayList<ASTNode> getChildren() {
        ArrayList<ASTNode> children = new ArrayList<>();
        children.add(name);
        children.addAll(parameters);

        return children;
    }

    @Override
    public ASTNode addChild(ASTNode child) {
        if(name == null) {
            name = (FunctionReference) child;
        } else {
            parameters.add((Expression) child);
        }

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FunctionCall functionCall = (FunctionCall) o;
        return Objects.equals(name, functionCall.name) &&
                Objects.equals(parameters, functionCall.parameters);
    }
}
