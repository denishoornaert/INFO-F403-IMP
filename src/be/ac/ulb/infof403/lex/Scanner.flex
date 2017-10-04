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
    "begin"        {return new Symbol(LexicalUnit.BEGIN, yyline, yycolumn, new String(yytext()));}
    "read"         {return new Symbol(LexicalUnit.READ, yyline, yycolumn, new String(yytext()));}
    "("            {return new Symbol(LexicalUnit.LPAREN, yyline, yycolumn, new String(yytext()));}
    ")"            {return new Symbol(LexicalUnit.LPAREN, yyline, yycolumn, new String(yytext()));}
    ";"            {return new Symbol(LexicalUnit.SEMICOLON, yyline, yycolumn, new String(yytext()));}
    "<>"           {return new Symbol(LexicalUnit.NEQ, yyline, yycolumn, new String(yytext()));}
    "while"        {return new Symbol(LexicalUnit.WHILE, yyline, yycolumn, new String(yytext()));}
    {Variable}     {return new Symbol(LexicalUnit.VARNAME, yyline, yycolumn, new String(yytext()));}
    {Number}       {return new Symbol(LexicalUnit.NUMBER, yyline, yycolumn, new String(yytext()));}
    {OpenComment}  {yybegin(COMMENT);}
    <<EOF>>        {return new Symbol(LexicalUnit.EOS,yyline, yycolumn);}
    "\n"           {return null;}
    [^]            {return null;}
}

<COMMENT> {
    {CloseComment} {yybegin(YYINITIAL);}
    [^]            {}
}