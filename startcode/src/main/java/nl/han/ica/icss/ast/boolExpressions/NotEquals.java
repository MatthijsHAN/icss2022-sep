package nl.han.ica.icss.ast.boolExpressions;

import nl.han.ica.icss.ast.BoolExpression;
import nl.han.ica.icss.ast.Literal;

public class NotEquals extends BoolExpression {
    public NotEquals() {

    }

    @Override
    public boolean compareExpressions() {
        return !(super.left.equals(super.right));
    }

    @Override
    public String getNodeLabel() {
        return "Doesn't equal";
    }

}
