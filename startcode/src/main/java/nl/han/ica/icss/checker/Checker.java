package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;



public class Checker {

    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;
    
    public void check(AST ast) {
        variableTypes = new HANLinkedList<>();
        checkStylesheet(ast.root);
    }

    private void checkStylesheet(Stylesheet node) {
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
        checkVariableReference((VariableReference) node.name);
    }

    private void checkVariableReference(VariableReference name) {

    }

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
                node.expression.setError("Property " + node.property.name + " has a invalid type");
            }
        } else if (node.property.name.equals("color")||node.property.name.equals("background-color")) {
            if(!(node.expression instanceof ColorLiteral)) {
                node.expression.setError("Property " + node.property.name + " has a invalid type");
            }
        }

    }


}
