package nl.han.ica.icss.ast;

import java.util.ArrayList;
import java.util.Objects;

public class Mixin extends ASTNode {
    public MixinReference name;
    public ArrayList<ASTNode> body = new ArrayList<>();

    public Mixin() { }

    public Mixin(MixinReference name, ArrayList<ASTNode> body) {
        this.name = name;
        this.body = body;
    }

    @Override
    public String getNodeLabel() {
        return "Mixin";
    }
    @Override
    public ArrayList<ASTNode> getChildren() {
        ArrayList<ASTNode> children = new ArrayList<>();
        children.add(name);
        children.addAll(body);

        return children;
    }

    @Override
    public ASTNode addChild(ASTNode child) {
        if(name == null) {
            name = (MixinReference) child;
        } else
            body.add(child);

        return this;
    }

    @Override
    public ASTNode removeChild(ASTNode child) {
        body.remove(child);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Mixin mixin = (Mixin) o;
        return Objects.equals(name, mixin.name) &&
                Objects.equals(body, mixin.body);
    }
}
