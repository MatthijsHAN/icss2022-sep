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
stylesheet: (stylerule|variableAssignment)* (stylerule|variableAssignment)* EOF;

//Stylerule
stylerule: selector OPEN_BRACE (stylerulebody)* CLOSE_BRACE;

selector: ID_IDENT     #idSelector
        | CLASS_IDENT  #classSelector
        | LOWER_IDENT  #tagSelector;

stylerulebody: propertyName COLON expression SEMICOLON
             | variableAssignment
             | opt;

propertyName: 'background-color'
            | 'color'
            | 'width'
            | 'height';

//If-Else
opt: IF BOX_BRACKET_OPEN clause BOX_BRACKET_CLOSE OPEN_BRACE stylerulebody* CLOSE_BRACE then?;

clause: variableName
      | expression EQUALS expression;

then: ELSE OPEN_BRACE stylerulebody* CLOSE_BRACE;

//Variables
variableAssignment: variableName ASSIGNMENT_OPERATOR expression SEMICOLON;

variableName: CAPITAL_IDENT;

//Calculations
expression: OPEN_PARENTHESIS expression CLOSE_PARENTHESIS
          | expression (MUL|DIV) expression
          | expression (PLUS|MIN) expression
          | literal;

literal: COLOR               #colorLiteral
       | PERCENTAGE          #percentageLiteral
       | PIXELSIZE           #pixelLiteral
       | MIN? SCALAR         #scalarLiteral
       | TRUE                #boolLiteral
       | FALSE               #boolLiteral
       | variableName        #variableReference;

