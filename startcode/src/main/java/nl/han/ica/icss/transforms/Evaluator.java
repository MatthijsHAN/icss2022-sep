package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.ArrayList;
import java.util.HashMap;

public class Evaluator implements Transform {

    private IHANLinkedList<HashMap<String, Literal>> variableValues;

    public Evaluator() {

    }

    @Override
    public void apply(AST ast) {
        variableValues = new HANLinkedList<>();
        applyStylesheet(ast.root);
    }

    private void applyStylesheet(Stylesheet root) {
        HashMap<String, Literal> map = new HashMap<>();
        variableValues.addFirst(map);

        for (int i = 0; i < root.getChildren().size(); i++) {
            ASTNode child = root.getChildren().get(i);
            if (child instanceof VariableAssignment) {
                applyVariableAssignment((VariableAssignment) child);
                root.removeChild(child);
                i--;
            } else if (child instanceof Stylerule) {
                applyStylerule((Stylerule) child);
            }
        }
        variableValues.removeFirst();
    }

    private void applyVariableAssignment(VariableAssignment node) {
        node.expression = evalExpression(node.expression);
        variableValues.getFirst().put(node.name.name, (Literal) node.expression);
    }

    private Expression applyVariableReference(VariableReference node) {
        Expression variableValue = null;
        for(int i = 0; i < variableValues.getSize(); i++) {
            if(variableValue == null) {
                variableValue = variableValues.get(i).get(node.name);
            } else if (variableValue != null) {
                break;
            }
        }
        return variableValue;
    }

    private void applyStylerule(Stylerule node) {
        applyScopedBlock(node);
    }

    private ArrayList<ASTNode> applyIfClause(IfClause node) {
        applyScopedBlock(node);

        if(applyConditionalExpression(node.conditionalExpression)) {
            return node.body;
        } else if (node.elseClause != null) {
            return node.elseClause.body;
        } else {
            return null;
        }
    }

    private boolean applyConditionalExpression(Expression conditionalExpression) {
        if(conditionalExpression instanceof BoolExpression || conditionalExpression instanceof BoolLiteral || conditionalExpression instanceof VariableReference) {
            return ((BoolLiteral) evalExpression(conditionalExpression)).value;
        } else {
            throw new IllegalStateException("Unexpected content: " + conditionalExpression);
        }
    }

    private void applyDeclaration(Declaration node) {
        node.expression = evalExpression(node.expression);
    }

    private Expression evalExpression(Expression expression) {
        if(expression instanceof Operation) {
            Expression newLeftExpression = evalExpression(((Operation) expression).lhs);
            Expression newRightExpression = evalExpression(((Operation) expression).rhs);
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
        } else if (expression instanceof BoolExpression) {
            return new BoolLiteral(evalExpression(((BoolExpression) expression).left).equals(evalExpression(((BoolExpression) expression).right)));
        } else if (expression instanceof VariableReference) {
            return applyVariableReference((VariableReference) expression);
        } else {
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

    private void applyScopedBlock(ASTNode node) {
        HashMap<String, Literal> map = new HashMap<>();
        variableValues.addFirst(map);

        for (int i = 0; i < node.getChildren().size(); i++) {
            ASTNode child = node.getChildren().get(i);

            if (child instanceof VariableAssignment) {
                applyVariableAssignment((VariableAssignment) child);
                node.removeChild(child);
                i--;
            } else if (child instanceof Declaration) {
                applyDeclaration((Declaration) child);
            } else if (child instanceof IfClause) {
                ArrayList<ASTNode> ifElseClauseBody = applyIfClause((IfClause) child);
                if (ifElseClauseBody != null) {
                    addAllChildren(node, ifElseClauseBody);
                }
                node.removeChild(child);
                i--;
            }
        }
        variableValues.removeFirst();
    }

    private void addAllChildren(ASTNode parent, ArrayList<ASTNode> children) {
        for (ASTNode child : children) {
            parent.addChild(child);
        }
    }
}
