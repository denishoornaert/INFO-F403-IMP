package be.ac.ulb.infof403;

import java_cup.runtime.Symbol;

%%

%public
%class Scanner

%type Symbol
%unicode

%column
%line

%eofclose

//Extended Regular Expressions

EndOfLine		= "\r"?"\n"
Line				= .*{EndOfLine}

%% //Identification of tokens

{Line}	{System.out.print(yyline+" "+yytext());}


