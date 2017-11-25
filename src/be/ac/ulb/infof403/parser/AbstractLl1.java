package be.ac.ulb.infof403.parser;

import be.ac.ulb.infof403.Symbol;
import be.ac.ulb.infof403.TokenList;
import be.ac.ulb.infof403.grammar.Grammar;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Recursive descent LL(1) which could stackParse for a given grammar an IMP File
 */
public abstract class AbstractLl1 {
    
    protected final Grammar _grammar;
    protected final Iterator<Symbol> _i;
    protected final ArrayList<String> _transitions;
    protected Symbol _symb;
    
    protected AbstractLl1(final Grammar grammar, final TokenList tokenList) {
        _grammar = grammar;
        _i = tokenList.iterator();
        _transitions = new ArrayList<>();
        _symb = _i.next();
    }
    
    public void printTransitions() {
        String result = "Transition: ";
        boolean first = true;
        for(final String transition : _transitions) {
            if(!first) {
                result += ", ";
            }
            first = false;
            result += transition;
        }
        System.out.println(result);
    }
    
    public abstract void parse(boolean debug) throws UnexpectedSymbolException ;
    
}
