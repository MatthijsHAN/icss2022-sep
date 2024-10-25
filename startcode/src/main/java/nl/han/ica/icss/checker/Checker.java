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
        variableTypes.getFirst().put(node.name.name, ExpressionType.UNDEFINED);
        checkExpression(node.expression, node.name.name);

    }

    //Need to check for multiply
    //
//    private void checkExpression(Expression expression, String name) {
//        if(expression instanceof Operation) {
//            Expression leftExpression = ((Operation) expression).lhs;
//            Expression rightExpression = ((Operation) expression).rhs;
//            if((expression instanceof MultiplyOperation || expression instanceof DivisionOperation) && !isScalar(leftExpression) && !isScalar(rightExpression)) {
//                expression.setError("Expressions that " + expression.getNodeLabel() + " need a scaler.");
//            }
//            checkExpression(leftExpression, name);
//            checkExpression(rightExpression, name);
//        } else {
//            ExpressionType currentVariableType = variableTypes.getFirst().get(name);
//            ExpressionType expressionType = ((Literal) expression).getExpressionType();
//            if(currentVariableType != ExpressionType.UNDEFINED) {
//                if (isColorOrBool(currentVariableType) || isColorOrBool(expressionType)) {
//                    expression.setError(expression.getNodeLabel() + " is a invalid type in this expression.");
//                } else if(currentVariableType != expressionType) {
//                    if(currentVariableType == ExpressionType.SCALAR) {
//                        variableTypes.getFirst().replace(name, expressionType);
//                    } else if(expressionType != ExpressionType.SCALAR) {
//                        expression.setError(expression.getNodeLabel() + " is a invalid type in this expression.");
//                    }
//                }
//            } else {
//                variableTypes.getFirst().replace(name, expressionType);
//            }
//        }
//    }

    private void checkExpression(Expression expression, String name) {
        if(expression instanceof Operation) {
            Expression leftExpression = ((Operation) expression).lhs;
            Expression rightExpression = ((Operation) expression).rhs;
            if((expression instanceof MultiplyOperation || expression instanceof DivisionOperation) && !isScalar(leftExpression) && !isScalar(rightExpression)) {
                expression.setError("Expressions that " + expression.getNodeLabel() + " need a scaler.");
            }
            checkExpression(leftExpression, name);
            checkExpression(rightExpression, name);
        } else {
            ExpressionType currentVariableType = variableTypes.getFirst().get(name);
            ExpressionType expressionType = ((Literal) expression).getExpressionType();
            variableTypes.getFirst().replace(name, expressionType);
        }
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
        if(node.property.name.equals("width") || node.property.name.equals("height")) {
            if(!(node.expression instanceof PixelLiteral || node.expression instanceof PercentageLiteral)) {
                node.expression.setError("Property " + node.property.name + " has a invalid type!");
            }
        } else if (node.property.name.equals("color")||node.property.name.equals("background-color")) {
            if(!(node.expression instanceof ColorLiteral)) {
                node.expression.setError("Property " + node.property.name + " has a invalid type!");
            }
        }

    }


}
