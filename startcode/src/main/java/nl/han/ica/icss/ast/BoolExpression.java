package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.ArrayList;
import java.util.Objects;

public abstract class BoolExpression extends Expression {
    public Expression left;
    public Expression right;

    public BoolExpression() {
    }

    public abstract boolean compareExpressions();
    public ExpressionType getExpressionType() {
        return ExpressionType.BOOL;
    }

    @Override
    public ArrayList<ASTNode> getChildren() {
        ArrayList<ASTNode> children = new ArrayList<>();
        if(left != null)
            children.add(left);
        if(right != null)
            children.add(right);
        return children;
    }

    @Override
    public ASTNode addChild(ASTNode child) {
        if(left == null) {
            left = (Expression) child;
        } else if(right == null) {
            right = (Expression) child;
        }
        return this;
    }
}
