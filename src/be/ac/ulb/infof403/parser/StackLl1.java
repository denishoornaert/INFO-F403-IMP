package be.ac.ulb.infof403.parser;

import be.ac.ulb.infof403.Symbol;
import be.ac.ulb.infof403.TokenList;
import be.ac.ulb.infof403.grammar.Grammar;
import be.ac.ulb.infof403.grammar.GrammarVariable;
import be.ac.ulb.infof403.grammar.Rule;

/**
 *
 */
public class StackLl1 extends AbstractLl1 {

    public StackLl1(final Grammar grammar, final TokenList tokenList) {
        super(grammar, tokenList);
    }
    
    private void symbolManagement(final Stack stack) {
        if(!stack.tos().equals(_symb)) {
            System.err.println("(line: " + _symb.getLine() + ") " + 
                    stack.tos().getValue()+" vs "+_symb.getValue());
            throw new IllegalArgumentException(); // TODO create custom error. Something like GrammarError.
        }
        else {
            _transitions.add("M");
            stack.pop();
            if(_i.hasNext()) {
                _symb = _i.next();
            } else {
                _symb = null;
            }
        }
    }
    
    // TODO what about replacing the 'if(r == null){...}' by a 'catch(nullPointerExeption) {...}' ?? more beautiful ??
    // No... With a "if" we show that a "normal" case when there is a problem :)
    private void variableManagement(final Stack stack) throws UnexpectedSymbolException {
        final GrammarVariable var = (GrammarVariable)stack.tos();
        
        final Rule r = var.getRuleThatLeadsToSymbol(_symb);
        
        if(r == null) {
            // create custom error
            throw new UnexpectedSymbolException(_symb, var.getExpectedCharacters()); 
        }
        else {
            _transitions.add(r.getId().toString());
            stack.pop();
            stack.push(r);
        }
    }
    
    
    @Override
    public void parse(final boolean debug) throws UnexpectedSymbolException {
        final Stack stack = new Stack();
        stack.push(_grammar.getInitialvariable());
        while (!stack.isEmpty() && _symb != null) {
            if(debug) {
                System.out.println("Stack: " + stack);
            }
            
            if(stack.tos() instanceof Symbol) {
                this.symbolManagement(stack);
            }
            // _stack.tos() instanceof GrammarVariable 
            // we can do that as we are sure only Symbol and GrammarVariable are stocked in the stack.
            else { 
                this.variableManagement(stack);
            }
        }
        
        if(!stack.isEmpty()) {
            System.err.println("Missing some code to end file (" + stack.toString() + ")");
            throw new IllegalArgumentException();
            
        } else if(_i.hasNext()) { // If code is not finish
            // TODO Denis: may be change the message of explanation
            throw new UnexpectedSymbolException(_symb, "Expected end of file"); 
            
        }
    }
    
    
}
