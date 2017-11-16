package be.ac.ulb.infof403.grammar;

import java.util.HashMap;
import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.Epsilon;
import be.ac.ulb.infof403.LexicalUnit;
import be.ac.ulb.infof403.Symbol;
import java.util.ArrayList;

%%

%public
%class GrammarScanner
%function nextToken
%type Symbol
%unicode

%column
%line

%{//start adding Java code

    private static Grammar _newGrammar = null;

    private HashMap<String, GrammarVariable> _allGrammarVar = new HashMap<>();
    private GrammarVariable _currentGrammarVar;
    private ArrayList<Elem> _allRightElem = new ArrayList<>();

    private void changeCurrentGrammarVar(String varName) {
        if(_allGrammarVar.containsKey(varName)){
            _currentGrammarVar = _allGrammarVar.get(varName);
        } else {
            _currentGrammarVar = new GrammarVariable(varName);
            if(_allGrammarVar.isEmpty()) {
                _newGrammar = new Grammar(_currentGrammarVar);
            }
            _allGrammarVar.put(varName, _currentGrammarVar);
        }
    }

    private void addNewGrammarVar(String varName) {
        GrammarVariable gramVar;
        if(_allGrammarVar.containsKey(varName)){
            gramVar = _allGrammarVar.get(varName);
        } else {
            gramVar = new GrammarVariable(varName);
            _allGrammarVar.put(varName, gramVar);
        }
        _allRightElem.add(gramVar);
    }

    private void addSymbol(Symbol symbol) {
        _allRightElem.add(symbol);
    }

    private void addEpsilon() {
        _allRightElem.add(new Epsilon());
    }

    private void endRule() {
        if(_currentGrammarVar != null && !_allRightElem.isEmpty()) {
            _currentGrammarVar.addRule(_allRightElem);
            _allRightElem.clear();
        }
    }

    private Symbol endOfFile() {
        endRule();
        _newGrammar.addVariables(_allGrammarVar.values());
        return new Symbol(LexicalUnit.EOS);
    }

    public static Grammar getGrammar() {
        return _newGrammar;
    }

%}//end adding Java code


//Extended Regular Expressions

BeginVar        = "<"
EndVar          = ">"
Separation      = "->"
AllChar         = [a-zA-Z\;\$\(\)\-\_\'\+\*\/]
AllCharSpace    = [a-zA-Z\;\$\(\)\-\_\'\+\*\/\ ]
VarName         = {AllChar}{AllCharSpace}*
Litteral        = {AllChar}+
Epsilon         = "epsilon"|"eps"


// Declare states

%xstate YYINITIAL, RIGHT, LEFT_VARIABLE, RIGHT_VARIABLE


%% //Identification of tokens

<YYINITIAL> {
    {BeginVar}     {yybegin(LEFT_VARIABLE);return null;}
    {Separation}   {yybegin(RIGHT);return null;}
    <<EOF>>        {return endOfFile();}
    " "            {return null;}
    [^]            {return new Symbol(LexicalUnit.VARNAME, "Init: " + yytext());}
}

<RIGHT> {
    {Epsilon}      {addEpsilon();return null;}
    "begin"        {addSymbol(new Symbol(LexicalUnit.BEGIN,     yyline, yycolumn, new String(yytext())));return null;}
    "end"          {addSymbol(new Symbol(LexicalUnit.END,       yyline, yycolumn, new String(yytext())));return null;}
    ";"            {addSymbol(new Symbol(LexicalUnit.SEMICOLON, yyline, yycolumn, new String(yytext())));return null;}
    ":="           {addSymbol(new Symbol(LexicalUnit.ASSIGN,    yyline, yycolumn, new String(yytext())));return null;}
    "("            {addSymbol(new Symbol(LexicalUnit.LPAREN,    yyline, yycolumn, new String(yytext())));return null;}
    ")"            {addSymbol(new Symbol(LexicalUnit.RPAREN,    yyline, yycolumn, new String(yytext())));return null;}
    "-"            {addSymbol(new Symbol(LexicalUnit.MINUS,     yyline, yycolumn, new String(yytext())));return null;}
    "-"            {addSymbol(new Symbol(LexicalUnit.MINUS,     yyline, yycolumn, new String(yytext())));return null;}
    "+"            {addSymbol(new Symbol(LexicalUnit.PLUS,      yyline, yycolumn, new String(yytext())));return null;}
    "*"            {addSymbol(new Symbol(LexicalUnit.TIMES,     yyline, yycolumn, new String(yytext())));return null;}
    "/"            {addSymbol(new Symbol(LexicalUnit.DIVIDE,    yyline, yycolumn, new String(yytext())));return null;}
    "if"           {addSymbol(new Symbol(LexicalUnit.IF,        yyline, yycolumn, new String(yytext())));return null;}
    "then"         {addSymbol(new Symbol(LexicalUnit.THEN,      yyline, yycolumn, new String(yytext())));return null;}
    "endif"        {addSymbol(new Symbol(LexicalUnit.ENDIF,     yyline, yycolumn, new String(yytext())));return null;}
    "else"         {addSymbol(new Symbol(LexicalUnit.ELSE,      yyline, yycolumn, new String(yytext())));return null;}
    "not"          {addSymbol(new Symbol(LexicalUnit.NOT,       yyline, yycolumn, new String(yytext())));return null;}
    "and"          {addSymbol(new Symbol(LexicalUnit.AND,       yyline, yycolumn, new String(yytext())));return null;}
    "or"           {addSymbol(new Symbol(LexicalUnit.OR,        yyline, yycolumn, new String(yytext())));return null;}
    "="            {addSymbol(new Symbol(LexicalUnit.EQ,        yyline, yycolumn, new String(yytext())));return null;}
    "<= "           {addSymbol(new Symbol(LexicalUnit.LEQ,       yyline, yycolumn, new String(yytext())));return null;}
    "< "           {addSymbol(new Symbol(LexicalUnit.GT,        yyline, yycolumn, new String(yytext())));return null;}
    ">="           {addSymbol(new Symbol(LexicalUnit.GEQ,       yyline, yycolumn, new String(yytext())));return null;}
    "> "           {addSymbol(new Symbol(LexicalUnit.LT,        yyline, yycolumn, new String(yytext())));return null;}
    "<> "          {addSymbol(new Symbol(LexicalUnit.NEQ,       yyline, yycolumn, new String(yytext())));return null;}
    "while"        {addSymbol(new Symbol(LexicalUnit.WHILE,     yyline, yycolumn, new String(yytext())));return null;}
    "do"           {addSymbol(new Symbol(LexicalUnit.DO,        yyline, yycolumn, new String(yytext())));return null;}
    "done"         {addSymbol(new Symbol(LexicalUnit.DONE,      yyline, yycolumn, new String(yytext())));return null;}
    "for"          {addSymbol(new Symbol(LexicalUnit.FOR,       yyline, yycolumn, new String(yytext())));return null;}
    "from"         {addSymbol(new Symbol(LexicalUnit.FROM,      yyline, yycolumn, new String(yytext())));return null;}
    "by"           {addSymbol(new Symbol(LexicalUnit.BY,        yyline, yycolumn, new String(yytext())));return null;}
    "to"           {addSymbol(new Symbol(LexicalUnit.TO,        yyline, yycolumn, new String(yytext())));return null;}
    "read"         {addSymbol(new Symbol(LexicalUnit.READ,      yyline, yycolumn, new String(yytext())));return null;}
    "print"        {addSymbol(new Symbol(LexicalUnit.PRINT,     yyline, yycolumn, new String(yytext())));return null;}
    "[Number]"     {addSymbol(new Symbol(LexicalUnit.NUMBER,    yyline, yycolumn, new String(yytext())));return null;}
    {BeginVar}     {yybegin(RIGHT_VARIABLE);return null;}
    {Litteral}     {addSymbol(new Symbol(LexicalUnit.VARNAME,   new String(yytext())));return null;}
    " "            {return null;}
    "\t"           {return null;}
    "\n"           {endRule();yybegin(YYINITIAL);return null;}
    <<EOF>>        {return endOfFile();}
    [^]            {return new Symbol(LexicalUnit.VARNAME, "right: " + yytext());}
}

<LEFT_VARIABLE> {
    {EndVar}       {yybegin(YYINITIAL);return null;}
    {VarName}      {changeCurrentGrammarVar(yytext());return null;}
    <<EOF>>        {return endOfFile();}
    [^]            {return new Symbol(LexicalUnit.VARNAME, "left");}
}

<RIGHT_VARIABLE> {
    {EndVar}       {yybegin(RIGHT);return null;}
    {VarName}      {addNewGrammarVar(yytext());return null;}
    <<EOF>>        {return endOfFile();}
    [^]            {return new Symbol(LexicalUnit.VARNAME, "right var");}
}
