package nl.han.ica.icss.ast;

import java.util.Objects;

public class FunctionParameter extends ASTNode {
    public String name;

    public FunctionParameter(String name) {
        super();
        this.name = name;
    }

    @Override
    public String getNodeLabel() {
        return "FunctionParameter (" + name + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FunctionParameter that = (FunctionParameter) o;
        return Objects.equals(name, that.name);
    }

}
