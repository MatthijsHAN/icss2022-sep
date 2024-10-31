package nl.han.ica.icss.ast.boolExpressions;

import nl.han.ica.icss.ast.BoolExpression;

public class GreaterThan extends BoolExpression {
    public GreaterThan() {
        super.Operator = ">";
    }

    @Override
    public String getNodeLabel() {
        return "Greater than";
    }

}