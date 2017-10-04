package jflex;

import java_cup.runtime.Symbol;
import java.io.*;
import java.util.Stack;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import jflex.unicode.UnicodeProperties;

%%

%public
%class LexScan
%implements sym, java_cup.runtime.Scanner
%function next_token

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

