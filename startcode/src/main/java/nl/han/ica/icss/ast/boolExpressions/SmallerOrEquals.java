package nl.han.ica.icss.ast.boolExpressions;

import nl.han.ica.icss.ast.BoolExpression;

public class SmallerOrEquals extends BoolExpression {
    public SmallerOrEquals() {
        super.Operator = "<=";
    }

    @Override
    public String getNodeLabel() {
        return "Smaller than or equals";
    }

}
