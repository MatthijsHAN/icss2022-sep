package nl.han.ica.icss.ast.boolExpressions;

import nl.han.ica.icss.ast.BoolExpression;

public class NotEquals extends BoolExpression {
    public NotEquals() {
        super.Operator = "!=";
    }

    @Override
    public String getNodeLabel() {
        return "Doesn't equal";
    }

}
