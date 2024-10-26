grammar ICSS;

//--- LEXER: ---

// IF support:
IF: 'if';
ELSE: 'else';
EQUALS: '==';
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

//--- PARSER: ---

//Miss later ook toestaan dat een style rule in een if gaat en een if in stylesheet?
stylesheet: (stylerule|variableAssignment)* (stylerule|variableAssignment)* EOF;

//Stylerule
stylerule: selector OPEN_BRACE (stylerulebody)* CLOSE_BRACE;

selector: ID_IDENT     #idSelector
        | CLASS_IDENT  #classSelector
        | LOWER_IDENT  #tagSelector;

stylerulebody: declaration
             | variableAssignment
             | opt;

declaration: propertyName COLON expression SEMICOLON;

propertyName: 'background-color'
            | 'color'
            | 'width'
            | 'height';

//If-Else
opt: IF BOX_BRACKET_OPEN (expression|boolExpression) BOX_BRACKET_CLOSE OPEN_BRACE stylerulebody* CLOSE_BRACE then?;

boolExpression: expression EQUALS expression;

then: ELSE OPEN_BRACE stylerulebody* CLOSE_BRACE;

//Variables
variableAssignment: variableName ASSIGNMENT_OPERATOR expression SEMICOLON;

variableName: CAPITAL_IDENT;

//Calculations
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
       | variableName        #variableReference;


