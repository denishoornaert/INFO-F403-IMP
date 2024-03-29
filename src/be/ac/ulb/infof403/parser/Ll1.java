package be.ac.ulb.infof403.parser;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.LexicalUnit;
import be.ac.ulb.infof403.Symbol;
import be.ac.ulb.infof403.TokenList;
import be.ac.ulb.infof403.codeGenerator.CodeFactory;
import be.ac.ulb.infof403.grammar.Grammar;
import be.ac.ulb.infof403.grammar.GrammarVariable;
import be.ac.ulb.infof403.grammar.Rule;
import be.ac.ulb.infof403.view.GenerateGojsParseTree;
import be.ac.ulb.infof403.view.GenerateLaTeXParseTree;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Parse TokenList and Grammar with LL(1)
 */
public class Ll1 {
    
    private final Grammar _grammar;
    private final Iterator<Symbol> _i;
    private final ArrayList<String> _transitions;
    private Symbol _symb;
    private RuleTree _tree;
    
    public Ll1(final Grammar grammar, final TokenList tokenList) {
        _grammar = grammar;
        _i = tokenList.iterator();
        _transitions = new ArrayList<>();
        _symb = _i.next();
    }
    
    private void symbolManagement(final ParseStack stack) {
        final RuleTree currentRuleTree = stack.pop();
        final Elem currentElem = currentRuleTree.getValue();
        
        if(!currentElem.equals(_symb)) {
            System.err.println("(line: " + _symb.getLine() + ") " + 
                    currentElem.getValue() + " vs " + _symb.getValue());
            throw new IllegalArgumentException();
        }
        else {
            if(_symb.getType().equals(LexicalUnit.VARNAME) || _symb.getType().equals(LexicalUnit.NUMBER)) {
                ((Symbol) currentElem).setValue(_symb.getValue());
            }
            
            _transitions.add("M");
            if(_i.hasNext()) {
                _symb = _i.next();
            } else {
                _symb = null;
            }
        }
    }
    
    private void variableManagement(final ParseStack stack) throws UnexpectedSymbolException {
        final RuleTree ruleTree = stack.pop();
        final Elem elem = ruleTree.getValue();
        
        if(!(elem instanceof GrammarVariable)) {
            throw new UnexpectedSymbolException(_symb, "Not expected a Variable");
            
        } else {
            final GrammarVariable gramVar = (GrammarVariable) elem;
            final Rule rule = gramVar.getRuleThatLeadsToSymbol(_symb);
            
            if(rule == null) { // create custom error
                throw new UnexpectedSymbolException(_symb, gramVar.getExpectedCharacters()); 
            } else {
                _transitions.add(rule.getId().toString());
                
                final ArrayList<RuleTree> allNewRuleTree = ruleTree.addChild(rule);
                Collections.reverse(allNewRuleTree);
                stack.add(allNewRuleTree);
            }
        }
        
    }
    
    public void parse(final boolean debug) throws UnexpectedSymbolException {
        final GrammarVariable grammarInitialVariable = _grammar.getInitialVariable();
        try {
            _tree = RuleTreeFactory.getRuleTree(grammarInitialVariable);
        } catch (UnknownElemForRuleTree ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage());
        }
        final ParseStack stack = new ParseStack(_tree);
        
        while (!stack.isEmpty() && _symb != null) {
            if(debug) {
                System.out.println("Stack: " + stack.clone());
            }
            
            if(stack.tosIsSymbol()) {
                this.symbolManagement(stack);
                
            } else {
                this.variableManagement(stack);
            }
        }
        
        if(!stack.isEmpty()) {
            System.err.println("Missing some code to end file (" + stack.toString() + ")");
            throw new IllegalArgumentException();
            
        } else if(_i.hasNext()) { // If code is not finish
            throw new UnexpectedSymbolException(_symb, "Expected end of file"); 
        }
    }
    
    public String produiceCode() {
        try {
            _tree.getResultVar();
        } catch (ErrorConvertToLlvm ex) {
            Logger.getLogger(Ll1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return CodeFactory.getGeneralOutput();
    }
    
    public void generateGojsParseTree(final String gojsOutputFile) {
        new GenerateGojsParseTree(_tree, gojsOutputFile);
    }
    
    public void generateLaTexParseTree(final String latexOutputFile) {
        new GenerateLaTeXParseTree(_tree, latexOutputFile);
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
