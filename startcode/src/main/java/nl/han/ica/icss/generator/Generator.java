package nl.han.ica.icss.generator;


import nl.han.ica.icss.ast.*;

public class Generator {

	public String generate(AST ast) {
        return generateStylesheet(ast.root).toString();
	}

    private StringBuilder generateStylesheet(Stylesheet root) {
        StringBuilder stylesheetString = new StringBuilder();
        for(ASTNode child : root.getChildren()) {
            if (child instanceof Stylerule) {
                stylesheetString.append(generateStylerule((Stylerule) child));
            }
        }
        return stylesheetString;
    }

    private StringBuilder generateStylerule(Stylerule node) {
        StringBuilder styleRuleString = new StringBuilder();
        styleRuleString.append(node.selectors.get(0)).append(" {");

        for(ASTNode child : node.getChildren()) {
            if(child instanceof Declaration){
                styleRuleString.append("\n\t").append(generateDeclaration((Declaration) child));
            }
        }
        styleRuleString.append("\n}\n\n");
        return styleRuleString;
    }

    private StringBuilder generateDeclaration(Declaration node) {
        StringBuilder declarationString = new StringBuilder();
        declarationString.append(node.property.name).append(": ").append(((Literal) node.expression).getTextValue()).append(";");
        return declarationString;
    }


}
