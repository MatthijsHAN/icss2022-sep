package nl.han.ica.icss.parser;


import nl.han.ica.datastructures.HANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.DivisionOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
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
        currentContainer.push(stylesheet); // Push stylesheet to the stack
    }

    @Override
    public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
        Stylesheet stylesheet = (Stylesheet) currentContainer.pop();
        ast.setRoot(stylesheet); // Set as root of the AST
    }

    @Override
    public void enterStylerule(ICSSParser.StyleruleContext ctx) {
        Stylerule stylerule = new Stylerule();
        currentContainer.push(stylerule); // Push stylerule to the stack
    }

    @Override
    public void exitStylerule(ICSSParser.StyleruleContext ctx) {
        Stylerule stylerule = (Stylerule) currentContainer.pop();
        currentContainer.peek().addChild(stylerule); // Link stylerule to parent
    }

    @Override
    public void enterDeclaration(ICSSParser.DeclarationContext ctx) {
        Declaration declaration = new Declaration(ctx.getChild(0).getText()); // Create a new declaration
        currentContainer.push(declaration); // Push declaration to the stack
    }

    @Override
    public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
        Declaration declaration = (Declaration) currentContainer.pop();
        currentContainer.peek().addChild(declaration); // Link declaration to parent
    }

    // Handling expressions for operations
    @Override
    public void enterAdditionOperation(ICSSParser.AdditionOperationContext ctx) {
        AddOperation addOp = new AddOperation(); // Create an AddOperation node
        currentContainer.push(addOp); // Push to stack
    }

    @Override
    public void exitAdditionOperation(ICSSParser.AdditionOperationContext ctx) {
        AddOperation addOp = (AddOperation) currentContainer.pop();
        currentContainer.peek().addChild(addOp); // Link to the parent node
    }

    @Override
    public void enterSubtractOperation(ICSSParser.SubtractOperationContext ctx) {
        SubtractOperation subOp = new SubtractOperation(); // Create SubtractOperation node
        currentContainer.push(subOp); // Push to stack
    }

    @Override
    public void exitSubtractOperation(ICSSParser.SubtractOperationContext ctx) {
        SubtractOperation subOp = (SubtractOperation) currentContainer.pop();
        currentContainer.peek().addChild(subOp); // Link to the parent node
    }

    @Override
    public void enterMultiplyOperation(ICSSParser.MultiplyOperationContext ctx) {
        MultiplyOperation mulOp = new MultiplyOperation(); // Create MultiplyOperation node
        currentContainer.push(mulOp); // Push to stack
    }

    @Override
    public void exitMultiplyOperation(ICSSParser.MultiplyOperationContext ctx) {
        MultiplyOperation mulOp = (MultiplyOperation) currentContainer.pop();
        currentContainer.peek().addChild(mulOp); // Link to the parent node
    }

    @Override
    public void enterDivisionOperation(ICSSParser.DivisionOperationContext ctx) {
        DivisionOperation divOp = new DivisionOperation(); // Create DivisionOperation node
        currentContainer.push(divOp); // Push to stack
    }

    @Override
    public void exitDivisionOperation(ICSSParser.DivisionOperationContext ctx) {
        DivisionOperation divOp = (DivisionOperation) currentContainer.pop();
        currentContainer.peek().addChild(divOp); // Link to the parent node
    }

    // Literal handling
    @Override
    public void enterColorLiteral(ICSSParser.ColorLiteralContext ctx) {
        ColorLiteral colorLiteral = new ColorLiteral(ctx.getText()); // Create ColorLiteral node
        currentContainer.push(colorLiteral); // Push to stack
    }

    @Override
    public void exitColorLiteral(ICSSParser.ColorLiteralContext ctx) {
        ColorLiteral colorLiteral = (ColorLiteral) currentContainer.pop();
        currentContainer.peek().addChild(colorLiteral); // Link to parent
    }

    @Override
    public void enterPercentageLiteral(ICSSParser.PercentageLiteralContext ctx) {
        PercentageLiteral percentageLiteral = new PercentageLiteral(ctx.getText()); // Create PercentageLiteral node
        currentContainer.push(percentageLiteral); // Push to stack
    }

    @Override
    public void exitPercentageLiteral(ICSSParser.PercentageLiteralContext ctx) {
        PercentageLiteral percentageLiteral = (PercentageLiteral) currentContainer.pop();
        currentContainer.peek().addChild(percentageLiteral); // Link to parent
    }

    @Override
    public void enterPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
        PixelLiteral pixelLiteral = new PixelLiteral(ctx.getText()); // Create PixelLiteral node
        currentContainer.push(pixelLiteral); // Push to stack
    }

    @Override
    public void exitPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
        PixelLiteral pixelLiteral = (PixelLiteral) currentContainer.pop();
        currentContainer.peek().addChild(pixelLiteral); // Link to parent
    }

    @Override
    public void enterScalarLiteral(ICSSParser.ScalarLiteralContext ctx) {
        ScalarLiteral scalarLiteral = new ScalarLiteral(ctx.getText()); // Create ScalarLiteral node
        currentContainer.push(scalarLiteral); // Push to stack
    }

    @Override
    public void exitScalarLiteral(ICSSParser.ScalarLiteralContext ctx) {
        ScalarLiteral scalarLiteral = (ScalarLiteral) currentContainer.pop();
        currentContainer.peek().addChild(scalarLiteral); // Link to parent
    }

    @Override
    public void enterBoolLiteral(ICSSParser.BoolLiteralContext ctx) {
        BoolLiteral boolLiteral = new BoolLiteral(ctx.getText()); // Create BoolLiteral node
        currentContainer.push(boolLiteral); // Push to stack
    }

    @Override
    public void exitBoolLiteral(ICSSParser.BoolLiteralContext ctx) {
        BoolLiteral boolLiteral = (BoolLiteral) currentContainer.pop();
        currentContainer.peek().addChild(boolLiteral); // Link to parent
    }

    @Override
    public void enterVariableReference(ICSSParser.VariableReferenceContext ctx) {
        VariableReference varReference = new VariableReference(ctx.getText()); // Create VariableReference node
        currentContainer.push(varReference); // Push to stack
    }

    @Override
    public void exitVariableReference(ICSSParser.VariableReferenceContext ctx) {
        VariableReference varReference = (VariableReference) currentContainer.pop();
        currentContainer.peek().addChild(varReference); // Link to parent
    }

    @Override
    public void enterVariableName(ICSSParser.VariableNameContext ctx) {
        VariableReference varReference = new VariableReference(ctx.getText()); // Create VariableReference node
        currentContainer.push(varReference); // Push to stack
    }

    @Override
    public void exitVariableName(ICSSParser.VariableNameContext ctx) {
        VariableReference varReference = (VariableReference) currentContainer.pop();
        currentContainer.peek().addChild(varReference); // Link to parent
    }

    // Variable Assignment
    @Override
    public void enterVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
        System.out.println(ctx.getText());
        VariableAssignment varAssignment = new VariableAssignment();
        currentContainer.push(varAssignment); // Push VariableAssignment to stack
    }

    @Override
    public void exitVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
        System.out.println(ctx.getText());
        VariableAssignment varAssignment = (VariableAssignment) currentContainer.pop();
        currentContainer.peek().addChild(varAssignment); // Link to parent
    }

    // Selectors handling
    @Override
    public void enterIdSelector(ICSSParser.IdSelectorContext ctx) {
        IdSelector idSelector = new IdSelector(ctx.getText());
        currentContainer.push(idSelector); // Push IdSelector to stack
    }

    @Override
    public void exitIdSelector(ICSSParser.IdSelectorContext ctx) {
        IdSelector idSelector = (IdSelector) currentContainer.pop();
        currentContainer.peek().addChild(idSelector); // Link to parent
    }

    @Override
    public void enterClassSelector(ICSSParser.ClassSelectorContext ctx) {
        ClassSelector classSelector = new ClassSelector(ctx.getText());
        currentContainer.push(classSelector); // Push ClassSelector to stack
    }

    @Override
    public void exitClassSelector(ICSSParser.ClassSelectorContext ctx) {
        ClassSelector classSelector = (ClassSelector) currentContainer.pop();
        currentContainer.peek().addChild(classSelector); // Link to parent
    }

    @Override
    public void enterTagSelector(ICSSParser.TagSelectorContext ctx) {
        TagSelector tagSelector = new TagSelector(ctx.getText());
        currentContainer.push(tagSelector); // Push TagSelector to stack
    }

    @Override
    public void exitTagSelector(ICSSParser.TagSelectorContext ctx) {
        TagSelector tagSelector = (TagSelector) currentContainer.pop();
        currentContainer.peek().addChild(tagSelector); // Link to parent
    }

    // Handle If-Else construct (opt and then)
    @Override
    public void enterOpt(ICSSParser.OptContext ctx) {
        IfClause opt = new IfClause();
        currentContainer.push(opt); // Push Conditional node to stack
    }

    @Override
    public void exitOpt(ICSSParser.OptContext ctx) {
        IfClause opt = (IfClause) currentContainer.pop();
        currentContainer.peek().addChild(opt); // Link Conditional node to parent
    }

    @Override
    public void enterThen(ICSSParser.ThenContext ctx) {
        ElseClause elseClause = new ElseClause();
        currentContainer.push(elseClause); // Push ElseCondition node to stack
    }

    @Override
    public void exitThen(ICSSParser.ThenContext ctx) {
        ElseClause elseClause = (ElseClause) currentContainer.pop();
        currentContainer.peek().addChild(elseClause); // Link ElseCondition to parent
    }



}