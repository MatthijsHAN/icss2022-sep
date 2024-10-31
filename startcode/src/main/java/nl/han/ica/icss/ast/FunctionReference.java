package nl.han.ica.icss.ast;

import java.util.Objects;

public class FunctionReference extends Expression {

    public String name;

    public FunctionReference(String name) {
        this.name = name;
    }

    @Override
    public String getNodeLabel() {
        return "FunctionReference (" + name + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FunctionReference that = (FunctionReference) o;
        return Objects.equals(name, that.name);
    }
}
