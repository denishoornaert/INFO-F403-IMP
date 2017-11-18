package be.ac.ulb.infof403.parser;

import be.ac.ulb.infof403.Symbol;
import be.ac.ulb.infof403.TokenList;
import be.ac.ulb.infof403.grammar.Grammar;
import be.ac.ulb.infof403.grammar.GrammarVariable;
import be.ac.ulb.infof403.grammar.Rule;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Recursive descent LL(1) which could parse for a given grammar an IMP File
 */
public class Ll1 {
    
    private final Stack _stack;
    private final Grammar _grammar;
    private final Iterator<Symbol> _i;
    private final ArrayList<String> _transitions;
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
            System.err.println("(line: " + _symb.getLine() + ") " + 
                    _stack.tos().getValue()+" vs "+_symb.getValue());
            throw new IllegalArgumentException(); // TODO create custom error. Something like GrammarError.
        }
        else {
            _transitions.add("M");
            _stack.pop();
            _symb = _i.next();
        }
    }
    
    // TODO what about replacing the 'if(r == null){...}' by a 'catch(nullPointerExeption) {...}' ?? more beautiful ??
    private void variableManagement() throws UnexpectedCharacterException {
        final GrammarVariable var = (GrammarVariable)_stack.tos();
        
        final Rule r = var.getRuleThatLeadsToSymbol(_symb);
        
        if(r == null) {
            // create custom error
            throw new UnexpectedCharacterException(_symb, var.getExpectedCharacters()); 
        }
        else {
            _transitions.add(r.getId().toString());
            _stack.pop();
            _stack.push(r);
        }
    }
    
    public void parse() throws UnexpectedCharacterException {
        _stack.push(_grammar.getInitialvariable());
        while (!_stack.isEmpty() && _i.hasNext()) {
            System.out.println(_stack);
            
            if(_stack.tos() instanceof Symbol) {
                this.symbolManagement();
            }
            // _stack.tos() instanceof GrammarVariable 
            // we can do that as we are sure only Symbol and GrammarVariable are stocked in the stack.
            else { 
                this.variableManagement();
            }
        }
        // TODO send if tokens.size() > 0 after the loop because if not, it means the user can write whatever he wants after a 'end'
        // What about a program like 'begin <code> end begin <code> end'
        System.out.println("Transitions : "+_transitions);
        
    }
    
}
