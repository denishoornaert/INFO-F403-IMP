package be.ac.ulb.infof403.parser;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.Symbol;
import be.ac.ulb.infof403.TokenList;
import be.ac.ulb.infof403.grammar.Grammar;
import be.ac.ulb.infof403.grammar.GrammarVariable;
import be.ac.ulb.infof403.grammar.Rule;

/**
 * Recursive descent LL(1) which could parse for a given grammar an IMP File
 */
public class Ll1 {
    
    private final Stack _stack;
    private final TokenList _tokens;
    private final Grammar _grammar;
    
    public Ll1(final Grammar grammar, final TokenList tokenList) {
        _stack = new Stack();
        _tokens = tokenList;
        _grammar = grammar;
    }
    
    public void parse() {
        _stack.push(_grammar.getInitialvariable());
        int i = 0;
        while (!_stack.isEmpty()) { // and tokens not out of range ??
            Symbol symb = _tokens.get(i);
            if(_stack.tos() instanceof Symbol) {
                if(!_stack.tos().equals(symb)) {
                    System.out.println("@1");
                    //System.out.println(_stack.tos()+" - "+symb);
                    throw new IllegalArgumentException(); // TODO create custom error. Something like GrammarError.
                }
                else {
                    System.out.print("M, ");
                    Elem tmp = _stack.pop();
                    i++;
                }
            }
            else { // _stack.tos() instanceof GrammarVariable
                GrammarVariable var = (GrammarVariable)_stack.tos();
                Rule r = var.getRuleThatLeadsToSymbol(symb);
                if(r == null) {
                    System.out.println("@2");
                    System.out.print(" "+var+" to "+symb);
                    throw new IllegalArgumentException(); // TODO create custom error. Something like GrammarError.
                }
                else {
                    System.out.print(r.getId()+", ");
                    _stack.pop();
                    _stack.push(r);
                }
            }
        }
        System.out.println("");
        System.out.println("Syntax respected !");
    }
    
}
