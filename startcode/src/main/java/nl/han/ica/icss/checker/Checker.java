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

//need to make different  levels for the linked list and a way to check every level

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
            } else if (child instanceof IfClause) {
                checkIfClause((IfClause) child);
            }
        }
        variableTypes.removeFirst();
    }

    //Variable checking
    private void checkVariableAssignment(VariableAssignment node) {
        ExpressionType expressionType = checkExpression(node.expression);
        variableTypes.getFirst().put(node.name.name, expressionType);
    }

    private ExpressionType checkVariableType(VariableReference node) {
        ExpressionType variableType = null;
        for(int i = 0; i < variableTypes.getSize(); i++) {
            if(variableType == null) {
                variableType = variableTypes.get(i).get(node.name);
            } else if (variableType != null) {
                break;
            }
        }
        return variableType;
    }

    private ExpressionType checkExpression(Expression expression) {
        ExpressionType variableType;

        if(expression instanceof Operation) {
            ExpressionType leftExpressionType = checkExpression(((Operation) expression).lhs);
            ExpressionType rightExpressionType = checkExpression(((Operation) expression).rhs);
            if(isColorOrBool(leftExpressionType) || isColorOrBool(rightExpressionType)) {
                expression.setError("Type color or bool can't be used within a operation!");
            } else if((expression instanceof MultiplyOperation || expression instanceof DivisionOperation)
                        && !(leftExpressionType == ExpressionType.SCALAR)
                        && !(rightExpressionType == ExpressionType.SCALAR)) {
                expression.setError("Expressions that " + expression.getNodeLabel() + " need a scaler!");
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
        } else if(expression instanceof VariableReference) {
            variableType = checkVariableType((VariableReference) expression);
            if(variableType == null) {
                expression.setError("Expression uses a uninitialised variable!");
            }
        } else {
            variableType = ((Literal) expression).getExpressionType();
        }
        return variableType;
    }

    private ExpressionType checkBoolExpression(BoolExpression boolExpression) {
        ExpressionType leftExpressionType = checkExpression(((BoolExpression) boolExpression).left);
        ExpressionType rightExpressionType = checkExpression(((BoolExpression) boolExpression).right);
        return boolExpression.getExpressionType();
    }

    private boolean isColorOrBool(ExpressionType type) {
        return type == ExpressionType.COLOR || type == ExpressionType.BOOL;
    }

    //Stylerule checking
    private void checkStylerule(Stylerule node) {
        HashMap<String, ExpressionType> map = new HashMap<>();
        variableTypes.addFirst(map);

        for(ASTNode child : node.getChildren()) {
            if(child instanceof VariableAssignment) {
                checkVariableAssignment((VariableAssignment) child);
            } else if(child instanceof Declaration){
                checkDeclaration((Declaration) child);
            } else if (child instanceof IfClause) {
                checkIfClause((IfClause) child);
            }
        }
        variableTypes.removeFirst();
    }

    private void checkDeclaration(Declaration node) {
        ExpressionType expressionType;
        if(node.expression instanceof VariableReference) {
            expressionType = checkVariableType((VariableReference) node.expression);
        } else {
            expressionType = checkExpression(node.expression);
        }
        if(expressionType == null) {
            node.expression.setError("Expression uses a uninitialised variable!");
        } else {
            if (node.property.name.equals("width") || node.property.name.equals("height")) {
                if (!(expressionType == ExpressionType.PIXEL || expressionType == ExpressionType.PERCENTAGE)) {
                    node.expression.setError("Property " + node.property.name + " has a invalid type!");
                }
            } else if (node.property.name.equals("color") || node.property.name.equals("background-color")) {
                if (!(expressionType == ExpressionType.COLOR)) {
                    node.expression.setError("Property " + node.property.name + " has a invalid type!");
                }
            }
        }
    }

    //IF/ELSE Checking
    private void checkIfClause(IfClause node) {
        checkConditionalExpression(node.conditionalExpression);

        HashMap<String, ExpressionType> map = new HashMap<>();
        variableTypes.addFirst(map);

        for(ASTNode child : node.getChildren()) {
            if(child instanceof VariableAssignment) {
                checkVariableAssignment((VariableAssignment) child);
            } else if(child instanceof Declaration){
                checkDeclaration((Declaration) child);
            } else if (child instanceof IfClause) {
                checkIfClause((IfClause) child);
            }
        }
        variableTypes.removeFirst();

        if(node.elseClause != null) {
            checkElseClause(node.elseClause);
        }
    }

    private void checkConditionalExpression(Expression conditionalExpression) {
        ExpressionType expressionType;
        if(conditionalExpression instanceof BoolExpression) {
            expressionType = checkBoolExpression((BoolExpression) conditionalExpression);
        } else {
            expressionType = checkExpression(conditionalExpression);
        }
        if(expressionType != ExpressionType.BOOL) {
            conditionalExpression.setError("If-block uses a non boolean for conditional expression!");
        }
    }

    private void checkElseClause(ElseClause node) {
        HashMap<String, ExpressionType> map = new HashMap<>();
        variableTypes.addFirst(map);

        for(ASTNode child : node.getChildren()) {
            if(child instanceof VariableAssignment) {
                checkVariableAssignment((VariableAssignment) child);
            } else if(child instanceof Declaration){
                checkDeclaration((Declaration) child);
            } else if (child instanceof IfClause) {
                checkIfClause((IfClause) child);
            }
        }

        variableTypes.removeFirst();
    }

}
