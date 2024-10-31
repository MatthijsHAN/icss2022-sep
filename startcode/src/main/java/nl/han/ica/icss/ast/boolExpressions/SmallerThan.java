package nl.han.ica.icss.ast.boolExpressions;

import nl.han.ica.icss.ast.BoolExpression;

public class SmallerThan extends BoolExpression {
    public SmallerThan() {
        super.Operator = "<";
    }

    @Override
    public String getNodeLabel() {
        return "Smaller than";
    }

}