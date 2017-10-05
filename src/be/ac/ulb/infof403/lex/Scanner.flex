package be.ac.ulb.infof403;

%%

%public
%class Scanner
%function nextToken
%type Symbol
%yylexthrow IMPSyntaxException
%unicode

%column
%line

//Extended Regular Expressions

Variable        = [a-zA-Z][a-zA-Z0-9]*
Number          = [0-9]+
OpenComment     = "(*"
CloseComment    = "*)"


// Declare states

%xstate YYINITIAL, COMMENT


%% //Identification of tokens

<YYINITIAL> {
    "begin"        {return new Symbol(LexicalUnit.BEGIN,     yyline, yycolumn, new String(yytext()));}
    "end"          {return new Symbol(LexicalUnit.END,       yyline, yycolumn, new String(yytext()));}
    ";"            {return new Symbol(LexicalUnit.SEMICOLON, yyline, yycolumn, new String(yytext()));}
    ":="           {return new Symbol(LexicalUnit.ASSIGN,    yyline, yycolumn, new String(yytext()));}
    "("            {return new Symbol(LexicalUnit.LPAREN,    yyline, yycolumn, new String(yytext()));}
    ")"            {return new Symbol(LexicalUnit.RPAREN,    yyline, yycolumn, new String(yytext()));}
    "-"            {return new Symbol(LexicalUnit.MINUS,     yyline, yycolumn, new String(yytext()));}
    "-"            {return new Symbol(LexicalUnit.MINUS,     yyline, yycolumn, new String(yytext()));}
    "+"            {return new Symbol(LexicalUnit.PLUS,      yyline, yycolumn, new String(yytext()));}
    "*"            {return new Symbol(LexicalUnit.TIMES,     yyline, yycolumn, new String(yytext()));}
    "/"            {return new Symbol(LexicalUnit.DIVIDE,    yyline, yycolumn, new String(yytext()));}
    "if"           {return new Symbol(LexicalUnit.IF,        yyline, yycolumn, new String(yytext()));}
    "then"         {return new Symbol(LexicalUnit.THEN,      yyline, yycolumn, new String(yytext()));}
    "endif"        {return new Symbol(LexicalUnit.ENDIF,     yyline, yycolumn, new String(yytext()));}
    "else"         {return new Symbol(LexicalUnit.ELSE,      yyline, yycolumn, new String(yytext()));}
    "not"          {return new Symbol(LexicalUnit.NOT,       yyline, yycolumn, new String(yytext()));}
    "and"          {return new Symbol(LexicalUnit.AND,       yyline, yycolumn, new String(yytext()));}
    "or"           {return new Symbol(LexicalUnit.OR,        yyline, yycolumn, new String(yytext()));}
    "="            {return new Symbol(LexicalUnit.EQ,        yyline, yycolumn, new String(yytext()));}
    "<="           {return new Symbol(LexicalUnit.GEQ,       yyline, yycolumn, new String(yytext()));}
    "<"            {return new Symbol(LexicalUnit.GT,        yyline, yycolumn, new String(yytext()));}
    ">="           {return new Symbol(LexicalUnit.LEQ,       yyline, yycolumn, new String(yytext()));}
    ">"            {return new Symbol(LexicalUnit.LT,        yyline, yycolumn, new String(yytext()));}
    "<>"           {return new Symbol(LexicalUnit.NEQ,       yyline, yycolumn, new String(yytext()));}
    "while"        {return new Symbol(LexicalUnit.WHILE,     yyline, yycolumn, new String(yytext()));}
    "do"           {return new Symbol(LexicalUnit.DO,        yyline, yycolumn, new String(yytext()));}
    "done"         {return new Symbol(LexicalUnit.DONE,      yyline, yycolumn, new String(yytext()));}
    "for"          {return new Symbol(LexicalUnit.FOR,       yyline, yycolumn, new String(yytext()));}
    "from"         {return new Symbol(LexicalUnit.FROM,      yyline, yycolumn, new String(yytext()));}
    "by"           {return new Symbol(LexicalUnit.BY,        yyline, yycolumn, new String(yytext()));}
    "to"           {return new Symbol(LexicalUnit.TO,        yyline, yycolumn, new String(yytext()));}
    "read"         {return new Symbol(LexicalUnit.READ,      yyline, yycolumn, new String(yytext()));}
    "print"        {return new Symbol(LexicalUnit.PRINT,     yyline, yycolumn, new String(yytext()));}
    {Variable}     {return new Symbol(LexicalUnit.VARNAME,   yyline, yycolumn, new String(yytext()));}
    {Number}       {return new Symbol(LexicalUnit.NUMBER,    yyline, yycolumn, new String(yytext()));}
    <<EOF>>        {return new Symbol(LexicalUnit.EOS,       yyline, yycolumn);}
    "\n"           {return null;}
    [^]            {return null;}
    {OpenComment}  {yybegin(COMMENT);}
}

<COMMENT> {
    {CloseComment} {yybegin(YYINITIAL);}
    [^]            {}
}