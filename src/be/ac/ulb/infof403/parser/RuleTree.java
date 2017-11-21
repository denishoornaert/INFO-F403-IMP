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
    
    /**
     * Create new RuleTree with rule that can leads to symbol (in param) and add to this node in children
     * 
     * @param symb the next symbol
     * @return Symbol Id
     * @throws UnexpectedCharacterException if there isn't any rule
     */
    protected Integer addRuleForSymbol(final Symbol symb) throws UnexpectedCharacterException {
        if(!isGrammarVariable()) {
            return -1;
        }
        final GrammarVariable grammarValue = (GrammarVariable) _value;
        
        _rule = grammarValue.getRuleThatLeadsToSymbol(symb);
        if(_rule == null) {
            // create custom error
            throw new UnexpectedCharacterException(symb, grammarValue.getExpectedCharacters()); 
        }
        else {
            for(final Elem elem : _rule) {
                _children.add(new RuleTree(elem));
            }
        }
        return _rule.getId();
    }
    
    public ArrayList<RuleTree> getChildren() {
        return _children;
    }
    
    public Elem getValue() {
        return _value;
    }
    
    protected boolean equalsValue(final Elem elem) {
        return _value.equals(elem);
    }
    
}
