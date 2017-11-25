package be.ac.ulb.infof403.parser;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.Epsilon;
import be.ac.ulb.infof403.Symbol;
import be.ac.ulb.infof403.TokenList;
import be.ac.ulb.infof403.grammar.Grammar;
import be.ac.ulb.infof403.grammar.GrammarVariable;
import be.ac.ulb.infof403.grammar.Rule;
import java.util.ListIterator;
import java.util.Stack;

/**
 *
 */
public class StackLl1 extends AbstractLl1 {

    public StackLl1(final Grammar grammar, final TokenList tokenList) {
        super(grammar, tokenList);
    }
    
    private void symbolManagement(final Stack<RuleTree> stack) {
        final RuleTree currentRuleTree = stack.pop();
        final Elem currentElem = currentRuleTree.getValue();
        
        if(!currentElem.equals(_symb)) {
            System.err.println("(line: " + _symb.getLine() + ") " + 
                    currentElem.getValue() + " vs " + _symb.getValue());
            throw new IllegalArgumentException(); // TODO create custom error. Something like GrammarError.
        }
        else {
            _transitions.add("M");
            if(_i.hasNext()) {
                _symb = _i.next();
            } else {
                _symb = null;
            }
        }
    }
    
    // TODO what about replacing the 'if(r == null){...}' by a 'catch(nullPointerExeption) {...}' ?? more beautiful ??
    // No... With a "if" we show that a "normal" case when there is a problem :)
    private void variableManagement(final Stack<RuleTree> stack) throws UnexpectedSymbolException {
        final RuleTree ruleTree = stack.pop();
        final Elem elem = ruleTree.getValue();
        
        if(!(elem instanceof GrammarVariable)) {
            System.err.println("Pas normal d'être ici ! " + elem);
            // TODO best error message and exception !
            
        } else {
            final GrammarVariable gramVar = (GrammarVariable) elem;
            final Rule rule = gramVar.getRuleThatLeadsToSymbol(_symb);
            
            if(rule == null) {
                // create custom error
                throw new UnexpectedSymbolException(_symb, gramVar.getExpectedCharacters()); 
            } else {
                _transitions.add(rule.getId().toString());
                final ListIterator<Elem> li = rule.listIterator(rule.size());

                // Iterate in reverse.
                while(li.hasPrevious()) {
                    final Elem previousElem = li.previous();
                    if(!(previousElem instanceof Epsilon)) {
                        stack.add(new RuleTree(previousElem));
                    }
                }
            }
        }
        
    }
    
    
    @Override
    public void parse(final boolean debug) throws UnexpectedSymbolException {
//        final CustomStack stack = new CustomStack();
        final Stack<RuleTree> stack = new Stack<>();
        
        final GrammarVariable grammarInitialVariable = _grammar.getInitialVariable();
        final RuleTree ruleTree = new RuleTree(grammarInitialVariable);
        stack.add(ruleTree);
        
        while (!stack.isEmpty() && _symb != null) {
            
            if(debug) {
                System.out.println("Stack: " + stack);
            }
            
            if(stack.peek().getValue() instanceof Symbol) {
                this.symbolManagement(stack);
                
            } else {
                // _stack.tos() instanceof GrammarVariable 
                // we can do that as we are sure only Symbol and GrammarVariable are stocked in the stack.
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
        
        System.out.println("Result: " + ruleTree);
        
    }
    
    
}
