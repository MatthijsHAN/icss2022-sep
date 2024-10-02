grammar ICSS;

//--- LEXER: ---

// IF support:
IF: 'if';
ELSE: 'else';
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
SEMICOLON: ';';
COLON: ':';
PLUS: '+';
MIN: '-';
MUL: '*';
ASSIGNMENT_OPERATOR: ':=';




//--- PARSER: ---
stylesheet: stylerule*;

//Stylerule
stylerule: tagselector OPEN_BRACE (insidestylerule)+ CLOSE_BRACE;
tagselector : ID_IDENT
            | CLASS_IDENT
            | LOWER_IDENT
            | CAPITAL_IDENT;
insidestylerule : colordeclaration COLON (COLOR) SEMICOLON
                | sizedeclaration COLON (PIXELSIZE) SEMICOLON;
colordeclaration: 'background-color'|'color';
sizedeclaration: 'width'|'height';

//Variables
variable: CAPITAL_IDENT ASSIGNMENT_OPERATOR (TRUE
                                            |FALSE
                                            |PIXELSIZE
                                            |PERCENTAGE
                                            |SCALAR);
