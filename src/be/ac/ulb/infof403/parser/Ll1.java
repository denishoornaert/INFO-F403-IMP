package be.ac.ulb.infof403.parser;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.Symbol;
import be.ac.ulb.infof403.TokenList;
import be.ac.ulb.infof403.grammar.Grammar;
import be.ac.ulb.infof403.grammar.GrammarVariable;
import be.ac.ulb.infof403.grammar.Rule;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Recursive descent LL(1) which could parse for a given grammar an IMP File
 */
public class Ll1 {
    
    private final Stack _stack;
    private final Grammar _grammar;
    private final Iterator<Symbol> _i;
    private ArrayList<String> _transitions;
    private Symbol _symb;
    
    public Ll1(final Grammar grammar, final TokenList tokenList) {
        _stack = new Stack();
        _grammar = grammar;
        _i = tokenList.iterator();
        _transitions = new ArrayList<>();
        _symb = _i.next();
    }
    
    private void symbolManagement() {
        if(!_stack.tos().equals(_symb)) {
            throw new IllegalArgumentException(); // TODO create custom error. Something like GrammarError.
        }
        else {
            _transitions.add("M");
            _stack.pop();
            _symb = _i.next();
        }
    }
    
    private void variableManagement() throws UnexpectedCharacterException {
        GrammarVariable var = (GrammarVariable)_stack.tos();
        Rule r = var.getRuleThatLeadsToSymbol(_symb);
        if(r == null) {
            HashSet<Elem> elems = var.getExpectedCharacters();
            throw new UnexpectedCharacterException(_symb, elems); // TODO create custom error. Something like GrammarError.
        }
        else {
            _transitions.add(""+r.getId());
            _stack.pop();
            _stack.push(r);
        }
    }
    
    public void parse() throws UnexpectedCharacterException {
        _stack.push(_grammar.getInitialvariable());
        while (!_stack.isEmpty() && _i.hasNext()) {
            if(_stack.tos() instanceof Symbol) {
                this.symbolManagement();
            }
            else { // _stack.tos() instanceof GrammarVariable // we can do that as we are sure only Symbol and GrammarVariable are stocked in the stack.
                this.variableManagement();
            }
        }
        System.out.println("Transitions : "+_transitions);
        
    }
    
}
