grammar ICSS;

//--- LEXER: ---

// IF support:
IF: 'if';
ELSE: 'else';
EQUALS: '==';
NOT_EQUAL: '!=';
GREATER_OR_EQUAL: '>=';
SMALLER_OR_EQUAL: '<=';
GREATER: '>';
SMALLER: '<';
BOX_BRACKET_OPEN: '[';
BOX_BRACKET_CLOSE: ']';


//Literals
TRUE: 'TRUE';
FALSE: 'FALSE';
PIXELSIZE: [0-9]+ 'px';
PERCENTAGE: [0-9]+ '%';
SCALAR: [0-9]+;


//Color value takes precedence over id idents
COLOR: '#' [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f];

//Specific identifiers for id's and css classes
ID_IDENT: '#' [a-z0-9\-]+;
CLASS_IDENT: '.' [a-z0-9\-]+;

//General identifiers
LOWER_IDENT: [a-z] [a-z0-9\-]*;
CAPITAL_IDENT: [A-Z] [A-Za-z0-9_]*;

//All whitespace is skipped
WS: [ \t\r\n]+ -> skip;

//
OPEN_BRACE: '{';
CLOSE_BRACE: '}';
OPEN_PARENTHESIS: '(';
CLOSE_PARENTHESIS: ')';
SEMICOLON: ';';
COLON: ':';
PLUS: '+';
MIN: '-';
MUL: '*';
DIV: '/';
ASSIGNMENT_OPERATOR: ':=';
AT: '@';

//--- PARSER: ---

//Stylesheet
stylesheet: (stylerule|variableAssignment|mixin|function)* EOF;

//Stylerule
stylerule: selector OPEN_BRACE (stylerulebody)* CLOSE_BRACE;

selector: ID_IDENT     #idSelector
        | CLASS_IDENT  #classSelector
        | LOWER_IDENT  #tagSelector;

stylerulebody: declaration
             | variableAssignment
             | opt
             | mixin
             | mixinCall
             | function;

//Declaration
declaration: propertyName COLON expression SEMICOLON;

propertyName: 'background-color'
            | 'color'
            | 'width'
            | 'height';

//If-Else
opt: IF BOX_BRACKET_OPEN (expression|boolExpression) BOX_BRACKET_CLOSE OPEN_BRACE stylerulebody* CLOSE_BRACE then?;

boolExpression: expression EQUALS expression            #equals
              | expression NOT_EQUAL expression         #notEquals
              | expression GREATER_OR_EQUAL expression  #greaterOrEquals
              | expression SMALLER_OR_EQUAL expression  #smallerOrEquals
              | expression GREATER expression           #greaterThan
              | expression SMALLER expression           #smallerThan;

then: ELSE OPEN_BRACE stylerulebody* CLOSE_BRACE;

//Variables
variableAssignment: variableName ASSIGNMENT_OPERATOR expression SEMICOLON;

variableName: CAPITAL_IDENT;

//Expressions
expression: expression PLUS term          # additionOperation
          | expression MIN term           # subtractOperation
          | term                          # passToTerm;

term: term MUL factor               # multiplyOperation
    | term DIV factor               # divisionOperation
    | factor                        # passToFactor;

factor: OPEN_PARENTHESIS expression CLOSE_PARENTHESIS
      | literal;

literal: COLOR               #colorLiteral
       | PERCENTAGE          #percentageLiteral
       | PIXELSIZE           #pixelLiteral
       | MIN? SCALAR         #scalarLiteral
       | TRUE                #boolLiteral
       | FALSE               #boolLiteral
       | variableName        #variableReference
       | functionCall        #functionReference;

//Code-blocks/ Mixins
mixin: 'mixin' mixinName OPEN_BRACE (stylerulebody)* CLOSE_BRACE;

mixinName: CAPITAL_IDENT;

mixinCall: AT mixinName SEMICOLON;

//Functions
function: 'function' functionName BOX_BRACKET_OPEN functionParameters? BOX_BRACKET_CLOSE OPEN_BRACE expression? CLOSE_BRACE;

functionName: CAPITAL_IDENT;

functionParameters: variableName(','variableName)*;

functionCall: AT functionName BOX_BRACKET_OPEN (expression(','expression)*)? BOX_BRACKET_CLOSE;