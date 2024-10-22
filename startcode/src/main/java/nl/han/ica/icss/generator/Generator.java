package nl.han.ica.icss.generator;


import nl.han.ica.icss.ast.AST;
import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Stylerule;
import nl.han.ica.icss.ast.Stylesheet;

public class Generator {

	public String generate(AST ast) {
        return generateStylesheet((Stylesheet) ast.root);
	}

    private String generateStylesheet(Stylesheet root) {
        //later niet alleen eerste kind pakken.
        return generateStylerule((Stylerule) root.getChildren().get(0));
    }

    private String generateStylerule(Stylerule stylerule) {
        String result = stylerule.selectors.get(0) + " {\n";
        //niet alleen eerste van body pakken.
        result += "\t" + generateDeclaration(stylerule.body.get(0));
        result += "}";
        return result;
    }

    private String generateDeclaration(ASTNode astNode) {
        return "temp";
    }


}
