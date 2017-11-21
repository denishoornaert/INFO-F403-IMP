package be.ac.ulb.infof403.parser;

import be.ac.ulb.infof403.Symbol;
import be.ac.ulb.infof403.TokenList;
import be.ac.ulb.infof403.grammar.Grammar;
import be.ac.ulb.infof403.grammar.GrammarVariable;
import be.ac.ulb.infof403.grammar.Rule;
import be.ac.ulb.infof403.view.GenerateGojsParseTree;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Recursive descent LL(1) which could stackParse for a given grammar an IMP File
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
            if(_i.hasNext()) {
                _symb = _i.next();
            } else {
                _symb = null;
            }
        }
    }
    
    // TODO what about replacing the 'if(r == null){...}' by a 'catch(nullPointerExeption) {...}' ?? more beautiful ??
    // No... With a "if" we show that a "normal" case when there is a problem :)
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
    
    public void stackParse(final boolean debug) throws UnexpectedCharacterException {
        _stack.push(_grammar.getInitialvariable());
        while (!_stack.isEmpty() && _symb != null) {
            if(debug) {
                System.out.println("Stack: " + _stack);
            }
            
            if(_stack.tos() instanceof Symbol) {
                this.symbolManagement();
            }
            // _stack.tos() instanceof GrammarVariable 
            // we can do that as we are sure only Symbol and GrammarVariable are stocked in the stack.
            else { 
                this.variableManagement();
            }
        }
        
        if(!_stack.isEmpty()) {
            System.err.println("Missing some code to end file (" + _stack.toString() + ")");
            throw new IllegalArgumentException();
            
        } else if(_i.hasNext()) { // If code is not finish
            // TODO Denis: may be change the message of explanation
            throw new UnexpectedCharacterException(_symb, "Expected end of file"); 
            
        }
    }
    
    
    public void treeParse(boolean gojs, final String outputFile) throws UnexpectedCharacterException {
        final RuleTree tree = new RuleTree(_grammar.getInitialvariable());
        analyseTree(tree);
        
        if(_i.hasNext()) {
            throw new UnexpectedCharacterException(_symb, "Expected end of file"); 
        } else if(gojs) {
            new GenerateGojsParseTree(tree, outputFile);
        }
    }
    
    private void analyseTree(final RuleTree currentRuleTree) throws UnexpectedCharacterException {
        if(_i.hasNext() || _symb != null) {
            if(currentRuleTree.isGrammarVariable()) {
                _transitions.add(currentRuleTree.addRuleForSymbol(_symb).toString());
                for(final RuleTree nextRuleTree : currentRuleTree.getChildren()) {
                    analyseTree(nextRuleTree);
                }
            } else if(currentRuleTree.equalsValue(_symb)) {
                if(!_i.hasNext()) {
                    _symb = null;
                } else {
                    _symb = _i.next();
                }
            }
        }
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
    
}
