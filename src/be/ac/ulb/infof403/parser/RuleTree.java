package be.ac.ulb.infof403.parser;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.Symbol;
import be.ac.ulb.infof403.grammar.GrammarVariable;
import be.ac.ulb.infof403.grammar.Rule;
import java.util.ArrayList;

/**
 * 
 */
public class RuleTree {
    
    private final Elem _value;
    private final ArrayList<RuleTree> _children;
    private Rule _rule;
    
    public RuleTree(final Elem value) {
        _value = value;
        _children = new ArrayList<>();
    }
    
    protected boolean isGrammarVariable() {
        return _value instanceof GrammarVariable;
    }
    
    protected void addRuleForSymbol(final Symbol symb) throws UnexpectedCharacterException {
        if(!isGrammarVariable()) {
            return;
        }
        final GrammarVariable grammarValue = (GrammarVariable) _value;
        
        _rule = grammarValue.getRuleThatLeadsToSymbol(symb);
        if(_rule == null) {
            // create custom error
            throw new UnexpectedCharacterException(symb, grammarValue.getExpectedCharacters()); 
        }
        else {
            System.out.println("Rule: " + _rule.getId());
            for(final Elem elem : _rule) {
                _children.add(new RuleTree(elem));
            }
        }
    }
    
    protected ArrayList<RuleTree> getChildren() {
        return _children;
    }
    
    protected boolean equalsValue(final Elem elem) {
        return _value.equals(elem);
    }
    
}
