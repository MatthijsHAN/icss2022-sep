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
stylesheet: (stylerule|variable)* (stylerule|variable)*;

//Stylerule
stylerule: tagselector OPEN_BRACE (stylerulebody)* CLOSE_BRACE;
tagselector : ID_IDENT
            | CLASS_IDENT
            | LOWER_IDENT;
stylerulebody: ('background-color'|'color'|'width'|'height') COLON (PERCENTAGE|PIXELSIZE|variablename|term) SEMICOLON
             | variable
             | opt;

//If-Else
opt: IF BOX_BRACKET_OPEN clause BOX_BRACKET_CLOSE OPEN_BRACE (stylerulebody|opt)* CLOSE_BRACE then?;
then: ELSE OPEN_BRACE (stylerulebody|opt)* CLOSE_BRACE;
clause: variablename
      | (factor|term|TRUE|FALSE) EQUALS (factor|term|TRUE|FALSE);

//Variables
variable: variablename ASSIGNMENT_OPERATOR (TRUE
                                           |FALSE
                                           |PIXELSIZE
                                           |PERCENTAGE
                                           |SCALAR
                                           |COLOR
                                           |term) SEMICOLON;
variablename: CAPITAL_IDENT;

//Calculations
term: term (PLUS|MIN) expression
    | expression;

expression: expression (MUL|DIV) factor
          | factor;

factor: SCALAR
      | PIXELSIZE
      | PERCENTAGE
      | variablename
      | OPEN_PARENTHESIS term CLOSE_PARENTHESIS;