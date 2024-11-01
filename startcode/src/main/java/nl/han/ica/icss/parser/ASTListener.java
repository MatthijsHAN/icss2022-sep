package nl.han.ica.icss.parser;


import nl.han.ica.datastructures.HANStack;
import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.boolExpressions.*;
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

	private AST ast;

    private IHANStack<ASTNode> currentContainer;

	public ASTListener() {
		ast = new AST();
        currentContainer = new HANStack<>();
	}
    public AST getAST() {
        return ast;
    }

    // Handle stylesheet
    @Override
    public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
        Stylesheet stylesheet = new Stylesheet();
        currentContainer.push(stylesheet);
    }

    @Override
    public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
        Stylesheet stylesheet = (Stylesheet) currentContainer.pop();
        ast.setRoot(stylesheet);
    }

    // Handle stylerule
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

    // Handle functions
//    @Override
//    public void enterFunction(ICSSParser.FunctionContext ctx) {
//        Function function = new Function();
//        currentContainer.push(function);
//    }
//
//    @Override
//    public void exitFunction(ICSSParser.FunctionContext ctx) {
//        Function function = (Function) currentContainer.pop();
//        currentContainer.peek().addChild(function);
//    }
//
//    @Override
//    public void enterFunctionName(ICSSParser.FunctionNameContext ctx) {
//        FunctionReference functionReference = new FunctionReference(ctx.getText());
//        currentContainer.push(functionReference);
//    }
//
//    @Override
//    public void exitFunctionName(ICSSParser.FunctionNameContext ctx) {
//        FunctionReference functionReference = (FunctionReference) currentContainer.pop();
//        currentContainer.peek().addChild(functionReference);
//    }
//
//    @Override
//    public void enterFunctionReference(ICSSParser.FunctionReferenceContext ctx) {
//        FunctionReference functionReference = new FunctionReference(ctx.getText());
//        currentContainer.push(functionReference);
//    }
//
//    @Override
//    public void exitFunctionReference(ICSSParser.FunctionReferenceContext ctx) {
//        FunctionReference functionReference = (FunctionReference) currentContainer.pop();
//        currentContainer.peek().addChild(functionReference);
//    }
//
//    @Override
//    public void enterFunctionParameters(ICSSParser.FunctionParametersContext ctx) {
//        FunctionParameters functionParameters = new FunctionParameters();
//        currentContainer.push(functionParameters);
//    }
//
//    @Override
//    public void exitFunctionParameters(ICSSParser.FunctionParametersContext ctx) {
//        FunctionParameters functionParameters = (FunctionParameters) currentContainer.pop();
//        currentContainer.peek().addChild(functionParameters);
//    }
//
//    @Override
//    public void enterFunctionCall(ICSSParser.FunctionCallContext ctx) {
//        FunctionCall functionCall = new FunctionCall();
//        currentContainer.push(functionCall);
//    }
//
//    @Override
//    public void exitFunctionCall(ICSSParser.FunctionCallContext ctx) {
//        FunctionCall functionCall = (FunctionCall) currentContainer.pop();
//        currentContainer.peek().addChild(functionCall);
//    }
//
//    // Handle mixins
//    @Override
//    public void enterMixin(ICSSParser.MixinContext ctx) {
//        Mixin mixin = new Mixin();
//        currentContainer.push(mixin);
//    }
//
//    @Override
//    public void exitMixin(ICSSParser.MixinContext ctx) {
//        Mixin mixin = (Mixin) currentContainer.pop();
//        currentContainer.peek().addChild(mixin);
//    }
//
//    @Override
//    public void enterMixinName(ICSSParser.MixinNameContext ctx) {
//        MixinReference mixinReference = new MixinReference(ctx.getText());
//        currentContainer.push(mixinReference);
//    }
//
//    @Override
//    public void exitMixinName(ICSSParser.MixinNameContext ctx) {
//        MixinReference mixinReference = (MixinReference) currentContainer.pop();
//        currentContainer.peek().addChild(mixinReference);
//    }

    // Handle if and else
    @Override
    public void enterOpt(ICSSParser.OptContext ctx) {
        IfClause opt = new IfClause();
        currentContainer.push(opt);
    }

    @Override
    public void exitOpt(ICSSParser.OptContext ctx) {
        IfClause opt = (IfClause) currentContainer.pop();
        currentContainer.peek().addChild(opt);
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

    // Handle variables
    @Override
    public void enterVariableReference(ICSSParser.VariableReferenceContext ctx) {
        VariableReference varReference = new VariableReference(ctx.getText());
        currentContainer.push(varReference);
    }

    @Override
    public void exitVariableReference(ICSSParser.VariableReferenceContext ctx) {
        VariableReference varReference = (VariableReference) currentContainer.pop();
        currentContainer.peek().addChild(varReference);
    }

    @Override
    public void enterVariableName(ICSSParser.VariableNameContext ctx) {
        VariableReference varReference = new VariableReference(ctx.getText());
        currentContainer.push(varReference);
    }

    @Override
    public void exitVariableName(ICSSParser.VariableNameContext ctx) {
        VariableReference varReference = (VariableReference) currentContainer.pop();
        currentContainer.peek().addChild(varReference);
    }


    @Override
    public void enterVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
        VariableAssignment varAssignment = new VariableAssignment();
        currentContainer.push(varAssignment);
    }

    @Override
    public void exitVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
        VariableAssignment varAssignment = (VariableAssignment) currentContainer.pop();
        currentContainer.peek().addChild(varAssignment);
    }

    // Handle selectors
    @Override
    public void enterIdSelector(ICSSParser.IdSelectorContext ctx) {
        IdSelector idSelector = new IdSelector(ctx.getText());
        currentContainer.push(idSelector);
    }

    @Override
    public void exitIdSelector(ICSSParser.IdSelectorContext ctx) {
        IdSelector idSelector = (IdSelector) currentContainer.pop();
        currentContainer.peek().addChild(idSelector);
    }

    @Override
    public void enterClassSelector(ICSSParser.ClassSelectorContext ctx) {
        ClassSelector classSelector = new ClassSelector(ctx.getText());
        currentContainer.push(classSelector);
    }

    @Override
    public void exitClassSelector(ICSSParser.ClassSelectorContext ctx) {
        ClassSelector classSelector = (ClassSelector) currentContainer.pop();
        currentContainer.peek().addChild(classSelector);
    }

    @Override
    public void enterTagSelector(ICSSParser.TagSelectorContext ctx) {
        TagSelector tagSelector = new TagSelector(ctx.getText());
        currentContainer.push(tagSelector);
    }

    @Override
    public void exitTagSelector(ICSSParser.TagSelectorContext ctx) {
        TagSelector tagSelector = (TagSelector) currentContainer.pop();
        currentContainer.peek().addChild(tagSelector);
    }

    // Handle declaration
    @Override
    public void enterDeclaration(ICSSParser.DeclarationContext ctx) {
        Declaration declaration = new Declaration(ctx.getChild(0).getText());
        currentContainer.push(declaration);
    }

    @Override
    public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
        Declaration declaration = (Declaration) currentContainer.pop();
        currentContainer.peek().addChild(declaration);
    }

    // Handle operations
    @Override
    public void enterAdditionOperation(ICSSParser.AdditionOperationContext ctx) {
        AddOperation addOp = new AddOperation();
        currentContainer.push(addOp);
    }

    @Override
    public void exitAdditionOperation(ICSSParser.AdditionOperationContext ctx) {
        AddOperation addOp = (AddOperation) currentContainer.pop();
        currentContainer.peek().addChild(addOp);
    }

    @Override
    public void enterSubtractOperation(ICSSParser.SubtractOperationContext ctx) {
        SubtractOperation subOp = new SubtractOperation();
        currentContainer.push(subOp);
    }

    @Override
    public void exitSubtractOperation(ICSSParser.SubtractOperationContext ctx) {
        SubtractOperation subOp = (SubtractOperation) currentContainer.pop();
        currentContainer.peek().addChild(subOp);
    }

    @Override
    public void enterMultiplyOperation(ICSSParser.MultiplyOperationContext ctx) {
        MultiplyOperation mulOp = new MultiplyOperation();
        currentContainer.push(mulOp);
    }

    @Override
    public void exitMultiplyOperation(ICSSParser.MultiplyOperationContext ctx) {
        MultiplyOperation mulOp = (MultiplyOperation) currentContainer.pop();
        currentContainer.peek().addChild(mulOp);
    }

    @Override
    public void enterDivisionOperation(ICSSParser.DivisionOperationContext ctx) {
        DivisionOperation divOp = new DivisionOperation();
        currentContainer.push(divOp);
    }

    @Override
    public void exitDivisionOperation(ICSSParser.DivisionOperationContext ctx) {
        DivisionOperation divOp = (DivisionOperation) currentContainer.pop();
        currentContainer.peek().addChild(divOp);
    }

    // Handle literals
    @Override
    public void enterColorLiteral(ICSSParser.ColorLiteralContext ctx) {
        ColorLiteral colorLiteral = new ColorLiteral(ctx.getText());
        currentContainer.push(colorLiteral);
    }

    @Override
    public void exitColorLiteral(ICSSParser.ColorLiteralContext ctx) {
        ColorLiteral colorLiteral = (ColorLiteral) currentContainer.pop();
        currentContainer.peek().addChild(colorLiteral);
    }

    @Override
    public void enterPercentageLiteral(ICSSParser.PercentageLiteralContext ctx) {
        PercentageLiteral percentageLiteral = new PercentageLiteral(ctx.getText());
        currentContainer.push(percentageLiteral);
    }

    @Override
    public void exitPercentageLiteral(ICSSParser.PercentageLiteralContext ctx) {
        PercentageLiteral percentageLiteral = (PercentageLiteral) currentContainer.pop();
        currentContainer.peek().addChild(percentageLiteral);
    }

    @Override
    public void enterPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
        PixelLiteral pixelLiteral = new PixelLiteral(ctx.getText());
        currentContainer.push(pixelLiteral);
    }

    @Override
    public void exitPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
        PixelLiteral pixelLiteral = (PixelLiteral) currentContainer.pop();
        currentContainer.peek().addChild(pixelLiteral);
    }

    @Override
    public void enterScalarLiteral(ICSSParser.ScalarLiteralContext ctx) {
        ScalarLiteral scalarLiteral = new ScalarLiteral(ctx.getText());
        currentContainer.push(scalarLiteral);
    }

    @Override
    public void exitScalarLiteral(ICSSParser.ScalarLiteralContext ctx) {
        ScalarLiteral scalarLiteral = (ScalarLiteral) currentContainer.pop();
        currentContainer.peek().addChild(scalarLiteral);
    }

    @Override
    public void enterBoolLiteral(ICSSParser.BoolLiteralContext ctx) {
        BoolLiteral boolLiteral = new BoolLiteral(ctx.getText());
        currentContainer.push(boolLiteral);
    }

    @Override
    public void exitBoolLiteral(ICSSParser.BoolLiteralContext ctx) {
        BoolLiteral boolLiteral = (BoolLiteral) currentContainer.pop();
        currentContainer.peek().addChild(boolLiteral);
    }

    // Handle bool expressions
    @Override public void enterEquals(ICSSParser.EqualsContext ctx) {
        Equals equals = new Equals();
        currentContainer.push(equals);
    }

    @Override public void exitEquals(ICSSParser.EqualsContext ctx) {
        Equals equals = (Equals) currentContainer.pop();
        currentContainer.peek().addChild(equals);
    }

    @Override public void enterNotEquals(ICSSParser.NotEqualsContext ctx) {
        NotEquals notEquals = new NotEquals();
        currentContainer.push(notEquals);
    }

    @Override public void exitNotEquals(ICSSParser.NotEqualsContext ctx) {
        NotEquals notEquals = (NotEquals) currentContainer.pop();
        currentContainer.peek().addChild(notEquals);
    }

    @Override public void enterGreaterOrEquals(ICSSParser.GreaterOrEqualsContext ctx) {
        GreaterOrEquals greaterOrEquals = new GreaterOrEquals();
        currentContainer.push(greaterOrEquals);
    }

    @Override public void exitGreaterOrEquals(ICSSParser.GreaterOrEqualsContext ctx) {
        GreaterOrEquals greaterOrEquals = (GreaterOrEquals) currentContainer.pop();
        currentContainer.peek().addChild(greaterOrEquals);
    }
    @Override public void enterSmallerOrEquals(ICSSParser.SmallerOrEqualsContext ctx) {
        SmallerOrEquals smallerOrEquals = new SmallerOrEquals();
        currentContainer.push(smallerOrEquals);
    }

    @Override public void exitSmallerOrEquals(ICSSParser.SmallerOrEqualsContext ctx) {
        SmallerOrEquals smallerOrEquals = (SmallerOrEquals) currentContainer.pop();
        currentContainer.peek().addChild(smallerOrEquals);
    }

    @Override public void enterGreaterThan(ICSSParser.GreaterThanContext ctx) {
        GreaterThan greaterThan = new GreaterThan();
        currentContainer.push(greaterThan);
    }

    @Override public void exitGreaterThan(ICSSParser.GreaterThanContext ctx) {
        GreaterThan greaterThan = (GreaterThan) currentContainer.pop();
        currentContainer.peek().addChild(greaterThan);
    }

    @Override public void enterSmallerThan(ICSSParser.SmallerThanContext ctx) {
        SmallerThan smallerThan = new SmallerThan();
        currentContainer.push(smallerThan);
    }

    @Override public void exitSmallerThan(ICSSParser.SmallerThanContext ctx) {
        SmallerThan smallerThan = (SmallerThan) currentContainer.pop();
        currentContainer.peek().addChild(smallerThan);
    }
}