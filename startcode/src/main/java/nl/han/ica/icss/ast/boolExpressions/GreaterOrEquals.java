package nl.han.ica.icss.ast.boolExpressions;

import nl.han.ica.icss.ast.BoolExpression;

public class GreaterOrEquals extends BoolExpression {
    public GreaterOrEquals() {
        super.Operator = ">=";
    }

    @Override
    public String getNodeLabel() {
        return "Greater than or equals";
    }

}
