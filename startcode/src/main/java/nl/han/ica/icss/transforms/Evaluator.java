package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;
import java.util.LinkedList;

public class Evaluator implements Transform {

    private IHANLinkedList<HashMap<String, Literal>> variableValues;

    public Evaluator() {
        variableValues = new HANLinkedList<>();
    }

    @Override
    public void apply(AST ast) {
        variableValues = new HANLinkedList<>();
        applyStylesheet((Stylesheet) ast.root);
    }

    private void applyStylesheet(Stylesheet node) {
        for(ASTNode child : node.getChildren()) {
            if(child instanceof VariableAssignment) {
                applyVariableAssignment((VariableAssignment) child);
            } else if (child instanceof Stylerule) {
                applyStylerule((Stylerule) child);
            }
        }
    }

    private void applyVariableAssignment(VariableAssignment node) {
        node.expression = evalExpression(node.expression);
    }

    private void applyStylerule(Stylerule node) {
        for(ASTNode child : node.getChildren()) {
            if(child instanceof VariableAssignment) {
                applyVariableAssignment((VariableAssignment) child);
            } else if(child instanceof Declaration){
                applyDeclaration((Declaration) child);
            }
        }
    }

    private void applyDeclaration(Declaration node) {
        node.expression = evalExpression(node.expression);
    }

    private Expression evalExpression(Expression expression) {
        if(expression instanceof Operation) {
            Expression newLeftExpression = evalExpression(((Operation) expression).lhs);
            Expression newRightExpression = evalExpression(((Operation) expression).rhs);
            System.out.println("Left: " + newLeftExpression + " Right: " + newRightExpression);
            if(checkLiteralTypeIsInt(newLeftExpression) && checkLiteralTypeIsInt(newRightExpression)) {
                ExpressionType newExpressionType = checkExpressionType(((Literal) newLeftExpression).getExpressionType(), ((Literal) newRightExpression).getExpressionType());
                int newExpressionValue;
                int newLeftValue = (Integer) ((Literal) newLeftExpression).getValue();
                int newRightValue = (Integer) ((Literal) newRightExpression).getValue();
                switch (expression.getNodeLabel()) {
                    case "Multiply":
                        newExpressionValue = newLeftValue * newRightValue;
                        break;
                    case "Add":
                        newExpressionValue = newLeftValue + newRightValue;
                        break;
                    case "Subtract":
                        newExpressionValue = newLeftValue - newRightValue;
                        break;
                    case "Divide":
                        newExpressionValue = newLeftValue / newRightValue;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected content: " + expression.getNodeLabel());
                }
                switch (newExpressionType) {
                    case PIXEL:
                        return new PixelLiteral(newExpressionValue);
                    case PERCENTAGE:
                        return new PercentageLiteral(newExpressionValue);
                    case SCALAR:
                        return new ScalarLiteral(newExpressionValue);
                    default:
                        throw new IllegalStateException("Unexpected content: " + newExpressionType);
                }
            } else {
                throw new IllegalStateException("Unexpected expression type in operation!");
            }
        } else {
            System.out.println("Returned: " + expression.getNodeLabel());
            return expression;
        }
    }

    private boolean checkLiteralTypeIsInt(Expression expression) {
        return expression instanceof PixelLiteral || expression instanceof PercentageLiteral || expression instanceof ScalarLiteral;
    }

    private ExpressionType checkExpressionType(ExpressionType leftExpressionType, ExpressionType rightExpressionType) {
        if(leftExpressionType == ExpressionType.SCALAR) {
            return rightExpressionType;
        } else {
            return leftExpressionType;
        }
    }


}
