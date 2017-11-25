package be.ac.ulb.infof403.parser;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.Epsilon;
import be.ac.ulb.infof403.grammar.GrammarVariable;
import be.ac.ulb.infof403.grammar.Rule;
import java.util.ArrayList;

/**
 * Element of the Parsing Tree
 */
public class RuleTree {
    
    private final Elem _value;
    private final ArrayList<RuleTree> _children;
    
    public RuleTree(final Elem value) {
        _value = value;
        _children = new ArrayList<>();
    }
    
    protected boolean isGrammarVariable() {
        return _value instanceof GrammarVariable;
    }
    
    public ArrayList<RuleTree> getChildren() {
        return _children;
    }
    
    public Elem getValue() {
        return _value;
    }
    
    protected ArrayList<RuleTree> addChild(final Rule rule) {
        final ArrayList<RuleTree> allRuleTreE = new ArrayList();
        for(final Elem elem : rule) {
            final RuleTree newRuleTree = new RuleTree(elem);
            _children.add(newRuleTree);
            if(!(elem instanceof Epsilon)) {
                allRuleTreE.add(newRuleTree);
            }
        }
        
        return allRuleTreE;
    }
    
}
