package nl.han.ica.icss.parser;


import nl.han.ica.datastructures.HANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.selectors.TagSelector;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {
	
	//Accumulator attributes:
	private AST ast;

	//Use this to keep track of the parent nodes when recursively traversing the ast
	//private IHANStack<ASTNode> currentContainer;
    private HANStack<ASTNode> currentContainer;

	public ASTListener() {
		ast = new AST();
        currentContainer = new HANStack<>();
	}
    public AST getAST() {
        return ast;
    }

    @Override
    public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
        Stylesheet stylesheet = new Stylesheet();
        currentContainer.push(stylesheet);
    }

    public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
        Stylesheet stylesheet = (Stylesheet) currentContainer.pop();
        ast.root = stylesheet;
    }

    @Override
    public void enterStylerule(ICSSParser.StyleruleContext ctx) {
        Stylerule stylerule = new Stylerule();
        currentContainer.push(stylerule);
    }

    @Override
    public void exitStylerule(ICSSParser.StyleruleContext ctx) {
        Stylerule stylerule = (Stylerule) currentContainer.pop();
        currentContainer.peek().addChild(stylerule);
    }

//    @Override
//    public void enterTagselector(ICSSParser.TagselectorContext ctx) {
//        TagSelector tagSelector = new TagSelector(ctx.getText());
//        currentContainer.push(tagSelector);
//    }
//
//    @Override
//    public void exitTagselector(ICSSParser.TagselectorContext ctx) {
//        TagSelector tagSelector = (TagSelector) currentContainer.pop();
//        currentContainer.peek().addChild(tagSelector);
//    }

    @Override
    public void enterStylerulebody(ICSSParser.StylerulebodyContext ctx) {
        Declaration declaration = new Declaration(ctx.getChild(0).getText());
        currentContainer.push(declaration);
    }


    @Override
    public void exitStylerulebody(ICSSParser.StylerulebodyContext ctx) {
        Declaration declaration = (Declaration) currentContainer.pop();
        currentContainer.peek().addChild(declaration);
    }

    @Override
    public void enterOpt(ICSSParser.OptContext ctx) {
        IfClause ifClause = new IfClause();
        currentContainer.push(ifClause);
    }

    @Override
    public void exitOpt(ICSSParser.OptContext ctx) {
        IfClause ifClause = (IfClause) currentContainer.pop();
        currentContainer.peek().addChild(ifClause);
    }

    @Override
    public void enterThen(ICSSParser.ThenContext ctx) {
        ElseClause elseClause = new ElseClause();
        currentContainer.push(elseClause);
    }

    @Override
    public void exitThen(ICSSParser.ThenContext ctx) {
        ElseClause elseClause = (ElseClause) currentContainer.pop();
        currentContainer.peek().addChild(elseClause);
    }

//    @Override
//    public void enterClause(ICSSParser.ClauseContext ctx) {
//        ElseClause elseClause = new ElseClause();
//        currentContainer.push(elseClause);
//    }
//
//    @Override
//    public void exitClause(ICSSParser.ClauseContext ctx) {
//        ElseClause elseClause = (ElseClause) currentContainer.pop();
//        IfClause ifClause = (IfClause) currentContainer.peek();
//        ifClause.addChild(elseClause);
//    }

//    @Override
//    public void enterVariable(ICSSParser.VariableContext ctx) {
//        VariableAssignment variableAssignment = new VariableAssignment();
//        currentContainer.push(variableAssignment);
//    }

//    @Override
//    public void exitVariable(ICSSParser.VariableContext ctx) {
//        VariableAssignment variableAssignment = (VariableAssignment) currentContainer.pop();
//        currentContainer.peek().addChild(variableAssignment);
//    }
//
//    @Override
//    public void enterVariablename(ICSSParser.VariablenameContext ctx) {
//        VariableReference variableReference = new VariableReference(ctx.getText());
//        currentContainer.push(variableReference);
//    }
//
//    @Override
//    public void exitVariablename(ICSSParser.VariablenameContext ctx) {
//        VariableReference variableReference = (VariableReference) currentContainer.pop();
//        if (currentContainer.peek() instanceof VariableAssignment) {
//            ((VariableAssignment) currentContainer.peek()).name = variableReference;
//        } else if (currentContainer.peek() instanceof Expression) {
//            ((Expression) currentContainer.peek()).addChild(variableReference);
//        }
//    }

//    @Override
//    public void enterTerm(ICSSParser.TermContext ctx) {
//        Literal literal;
//        if (ctx.COLOR() != null) {
//            literal = new ColorLiteral(ctx.getText());
//        } else if (ctx.PIXELSIZE() != null) {
//            literal = new PixelLiteral(ctx.getText());
//        } else if (ctx.PERCENTAGE() != null) {
//            literal = new PercentageLiteral(ctx.getText());
//        } else if (ctx.SCALAR() != null) {
//            literal = new ScalarLiteral(ctx.getText());
//        } else {
//            literal = new Literal();
//        }
//        currentContainer.push(literal);
//    }
//
//    @Override
//    public void exitTerm(ICSSParser.TermContext ctx) {
//        Literal literal = (Literal) currentContainer.pop();
//        if (currentContainer.peek() instanceof Expression) {
//            ((Expression) currentContainer.peek()).addChild(literal);
//        } else if (currentContainer.peek() instanceof VariableAssignment) {
//            ((VariableAssignment) currentContainer.peek()).expression = literal;
//        }
//    }
//
//    @Override
//    public void enterExpression(ICSSParser.ExpressionContext ctx) {
//        // Check if this is an operation (add/subtract/multiply)
//        if (ctx.ADD() != null) {
//            currentContainer.push(new AddOperation());
//        } else if (ctx.SUBTRACT() != null) {
//            currentContainer.push(new SubtractOperation());
//        } else if (ctx.MULTIPLY() != null) {
//            currentContainer.push(new MultiplyOperation());
//        } else {
//            Expression expr = new Expression();
//            currentContainer.push(expr);
//        }
//    }
//
//    @Override
//    public void exitExpression(ICSSParser.ExpressionContext ctx) {
//        Expression expr = (Expression) currentContainer.pop();
//        if (currentContainer.peek() instanceof VariableAssignment) {
//            ((VariableAssignment) currentContainer.peek()).expression = expr;
//        } else if (currentContainer.peek() instanceof Declaration) {
//            ((Declaration) currentContainer.peek()).expression = expr;
//        } else {
//            currentContainer.peek().addChild(expr);
//        }
//    }
//
//    @Override
//    public void enterFactor(ICSSParser.FactorContext ctx) {
//        // Factor is part of an expression; handle it similarly to terms or operations
//        Expression expr = new Expression();
//        currentContainer.push(expr);
//    }
//
//    @Override
//    public void exitFactor(ICSSParser.FactorContext ctx) {
//        Expression expr = (Expression) currentContainer.pop();
//        if (currentContainer.peek() instanceof Operation) {
//            ((Operation) currentContainer.peek()).addChild(expr);
//        } else if (currentContainer.peek() instanceof Expression) {
//            ((Expression) currentContainer.peek()).addChild(expr);
//        }
//    }


}