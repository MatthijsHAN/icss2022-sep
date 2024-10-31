package nl.han.ica.icss.ast.boolExpressions;

import nl.han.ica.icss.ast.BoolExpression;

public class Equals extends BoolExpression {
    public Equals() {
        super.Operator = "==";
    }

    @Override
    public String getNodeLabel() {
        return "Equals";
    }

}
