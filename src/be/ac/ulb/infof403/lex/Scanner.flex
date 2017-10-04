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

EndOfLine        = "\r"?"\n"
Line             = .*{EndOfLine}

// Declare states

%xstate YYINITIAL


%% //Identification of tokens

<YYINITIAL> {
    "begin" 	{return new Symbol(LexicalUnit.BEGIN, yyline, yycolumn, new String(yytext()));}
    <<EOF>>     {return new Symbol(LexicalUnit.EOS,yyline, yycolumn);}
    "\n"        {return null;}
    [^]         {return null;}
}

