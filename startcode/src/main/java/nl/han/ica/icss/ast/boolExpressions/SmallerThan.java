package nl.han.ica.icss.ast.boolExpressions;

import nl.han.ica.icss.ast.BoolExpression;
import nl.han.ica.icss.ast.Literal;

public class SmallerThan extends BoolExpression {
    public SmallerThan() {

    }

    @Override
    public boolean compareExpressions() {
        return ((int) ((Literal) super.left).getValue()) < ((int) ((Literal) super.right).getValue());
    }

    @Override
    public String getNodeLabel() {
        return "Smaller than";
    }

}