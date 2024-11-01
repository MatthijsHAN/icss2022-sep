package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.boolExpressions.Equals;
import nl.han.ica.icss.ast.boolExpressions.NotEquals;
import nl.han.ica.icss.ast.operations.DivisionOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.ArrayList;
import java.util.HashMap;

public class Checker {

    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;
//    private IHANLinkedList<ArrayList<String>> mixins;
//    private ArrayList<String> nestedMixins;
    
    public void check(AST ast) {
        variableTypes = new HANLinkedList<>();
//        mixins = new HANLinkedList<>();
        checkStylesheet(ast.root);
    }

    private void enterNewScope() {
        HashMap<String, ExpressionType> variableMap = new HashMap<>();
        variableTypes.addFirst(variableMap);
//        ArrayList<String> mixinArrayList = new ArrayList<>();
//        mixins.addFirst(mixinArrayList);
    }

    private void exitScope() {
        variableTypes.removeFirst();
//        mixins.removeFirst();
    }

    // Stylesheet checking
    private void checkStylesheet(Stylesheet node) {
        enterNewScope();

        for(ASTNode child : node.getChildren()) {
            if(child instanceof VariableAssignment) {
                checkVariableAssignment((VariableAssignment) child);
            } else if (child instanceof Stylerule) {
                checkStylerule((Stylerule) child);
            }
//            else if (child instanceof Function) {
//                checkFunction((Function) child);
//            } else if (child instanceof Mixin) {
//                checkMixin((Mixin) child);
//            }
        }
        exitScope();
    }

    // Variable checking
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

    // Expression checking
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
        ExpressionType leftExpressionType = checkExpression(boolExpression.left);
        ExpressionType rightExpressionType = checkExpression(boolExpression.right);

        if(!(boolExpression instanceof Equals || boolExpression instanceof NotEquals)) {
            if(isColorOrBool(leftExpressionType) || isColorOrBool(rightExpressionType)) {
                boolExpression.setError("When comparing something with a boolean or color only the operators '==' or '!=' may be used!");
            } else if (leftExpressionType != rightExpressionType) {
                boolExpression.setError("When comparing literals using the operators '<=', '>=', '<' or '>' the literals need to be of the same type!");
            }
        }
        return boolExpression.getExpressionType();
    }

    // Stylerule checking
    private void checkStylerule(Stylerule node) {
        checkScopedBlock(node);
    }

    // Declaration checking
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

    // If/Else Checking
    private void checkIfClause(IfClause node) {
        checkConditionalExpression(node.conditionalExpression);

        checkScopedBlock(node);

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
        checkScopedBlock(node);
    }
    
    // Mixin checking
//    private void checkMixin(Mixin node) {
//
//        for(int i = 0; i < nestedMixins.size(); i++) {
//            if(nestedMixins.get(i).equals(node.name.name)) {
//                node.setError("");
//            }
//        }
//        nestedMixins.add(node.name.name);
//        checkScopedBlock(node);
//    }
//
//    // Function checking
//    private void checkFunction(Function node) {
//
//        for(ASTNode child : node.getChildren()) {
//            if(child instanceof FunctionParameters) {
//                checkFunctionParameters((FunctionParameters) child);
//            } else if(child instanceof Expression){
//                checkExpression((Expression) child);
//            }
//        }
//    }
//
//    private void checkFunctionParameters(FunctionParameters child) {
//    }

    // Extracted methods
    private boolean isColorOrBool(ExpressionType type) {
        return type == ExpressionType.COLOR || type == ExpressionType.BOOL;
    }
    
    private void checkScopedBlock(ASTNode node) {
        enterNewScope();

        for(ASTNode child : node.getChildren()) {
            if(child instanceof VariableAssignment) {
                checkVariableAssignment((VariableAssignment) child);
            } else if(child instanceof Declaration){
                checkDeclaration((Declaration) child);
            } else if (child instanceof IfClause) {
                checkIfClause((IfClause) child);
            }
//            else if (child instanceof Mixin) {
//                checkMixin((Mixin) child);
//            } else if (child instanceof Function) {
//                checkFunction((Function) child);
//            }
        }
        exitScope();
    }
}
