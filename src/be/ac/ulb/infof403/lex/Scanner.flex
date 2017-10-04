package be.ac.ulb.infof403;

%%

%public
%class Scanner
%function nextToken
%type Symbol
%unicode

%column
%line

%eofclose

//Extended Regular Expressions

EndOfLine        = "\r"?"\n"
Line             = .*{EndOfLine}

%% //Identification of tokens

{Line}    {System.out.print(yyline+" "+yytext());}


