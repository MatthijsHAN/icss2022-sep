package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.DivisionOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;



public class Checker {

    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;
    
    public void check(AST ast) {
        variableTypes = new HANLinkedList<>();
        checkStylesheet(ast.root);
    }

    //Stylesheet checking
    private void checkStylesheet(Stylesheet node) {
        HashMap<String, ExpressionType> map = new HashMap<>();
        variableTypes.addFirst(map);

        for(ASTNode child : node.getChildren()) {
            if(child instanceof VariableAssignment) {
                checkVariableAssignment((VariableAssignment) child);
            } else if (child instanceof Stylerule) {
                checkStylerule((Stylerule) child);
            }
        }

    }

    //Variable checking
    private void checkVariableAssignment(VariableAssignment node) {
        ExpressionType expressionType = checkExpression(node.expression);
        variableTypes.getFirst().put(node.name.name, expressionType);
    }

    private ExpressionType checkVariableType(VariableReference node) {
        return variableTypes.getFirst().get(node.name);
    }

    private ExpressionType checkExpression(Expression expression) {
        ExpressionType variableType;

        if(expression instanceof Operation) {
            ExpressionType leftExpressionType = checkExpression(((Operation) expression).lhs);
            ExpressionType rightExpressionType = checkExpression(((Operation) expression).rhs);
            if(isColorOrBool(leftExpressionType) || isColorOrBool(rightExpressionType)) {
                expression.setError("Type color or bool can't be used within a operation!");
            } else if ((expression instanceof MultiplyOperation || expression instanceof DivisionOperation)
                        && !(leftExpressionType == ExpressionType.SCALAR)
                        && !(rightExpressionType == ExpressionType.SCALAR)) {
                expression.setError("Expressions that " + expression.getNodeLabel() + " need a scaler.");
            }
            if(leftExpressionType != rightExpressionType) {
                variableType = leftExpressionType;
                if(leftExpressionType == ExpressionType.SCALAR) {
                    variableType = rightExpressionType;
                } else if(rightExpressionType != ExpressionType.SCALAR) {
                    expression.setError("Type pixel and percentage can't be used within the same operation!");
                }
            } else {
                variableType = leftExpressionType;
            }

        } else {
            variableType = ((Literal) expression).getExpressionType();
        }
        return variableType;
    }

    public boolean isColorOrBool(ExpressionType type) {
        return type == ExpressionType.COLOR || type == ExpressionType.BOOL;
    }

    public boolean isScalar(Expression expression) {
        return expression instanceof Literal && ((Literal) expression).getExpressionType() == ExpressionType.SCALAR;
    }

    //Stylerule checking
    private void checkStylerule(Stylerule node) {
        for(ASTNode child : node.getChildren()) {
            if(child instanceof Declaration){
                checkDeclaration((Declaration) child);
            }
        }
    }

    private void checkDeclaration(Declaration node) {
        ExpressionType expression;
        if(node.expression instanceof VariableReference) {
            expression = checkVariableType((VariableReference) node.expression);
        } else {
            expression = ((Literal) node.expression).getExpressionType();
        }
        if(node.property.name.equals("width") || node.property.name.equals("height")) {
            if(!(expression == ExpressionType.PIXEL || expression == ExpressionType.PERCENTAGE)) {
                node.expression.setError("Property " + node.property.name + " has a invalid type!");
            }
        } else if (node.property.name.equals("color")||node.property.name.equals("background-color")) {
            if(!(expression == ExpressionType.COLOR)) {
                node.expression.setError("Property " + node.property.name + " has a invalid type!");
            }
        }

    }


}
