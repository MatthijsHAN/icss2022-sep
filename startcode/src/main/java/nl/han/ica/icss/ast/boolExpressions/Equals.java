package nl.han.ica.icss.ast.boolExpressions;

import nl.han.ica.icss.ast.BoolExpression;
import nl.han.ica.icss.ast.Literal;

public class Equals extends BoolExpression {
    public Equals() {

    }

    @Override
    public boolean compareExpressions() {
        return super.left.equals(super.right);
    }

    @Override
    public String getNodeLabel() {
        return "Equals";
    }

}
