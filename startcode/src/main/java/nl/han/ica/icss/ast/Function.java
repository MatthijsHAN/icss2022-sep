package nl.han.ica.icss.ast;

import java.util.ArrayList;
import java.util.Objects;

public class Function extends ASTNode {

    public FunctionReference name;
    public ArrayList<FunctionParameter> parameters = new ArrayList<>();
    public Expression body;

    public Function() { }

    public Function(FunctionReference name, ArrayList<FunctionParameter> parameters, Expression body) {
        this.name = name;
        this.parameters = parameters;
        this.body = body;
    }

    @Override
    public String getNodeLabel() {
        return "Function";
    }

    @Override
    public ArrayList<ASTNode> getChildren() {
        ArrayList<ASTNode> children = new ArrayList<>();
        children.add(name);
        children.addAll(parameters);
        children.add(body);

        return children;
    }

    @Override
    public ASTNode addChild(ASTNode child) {
        if(name == null) {
            name = (FunctionReference) child;
        } else if (child instanceof FunctionParameter) {
            parameters.add((FunctionParameter) child);
        } else if(body == null) {
            body = (Expression) child;
        }

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Function function = (Function) o;
        return Objects.equals(name, function.name) &&
                Objects.equals(parameters, function.parameters) &&
                Objects.equals(body, function.body);
    }
}
