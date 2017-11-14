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
    {BeginVar}     {yybegin(RIGHT_VARIABLE);return null;}
    "if"           {addSymbol(new Symbol(LexicalUnit.IF,        yyline, yycolumn, new String(yytext())));return null;}
    "then"         {addSymbol(new Symbol(LexicalUnit.THEN,      yyline, yycolumn, new String(yytext())));return null;}
    "endif"        {addSymbol(new Symbol(LexicalUnit.ENDIF,     yyline, yycolumn, new String(yytext())));return null;}
    "else"         {addSymbol(new Symbol(LexicalUnit.ELSE,      yyline, yycolumn, new String(yytext())));return null;}
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
