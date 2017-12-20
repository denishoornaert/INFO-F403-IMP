package be.ac.ulb.infof403.parser;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.Epsilon;
import be.ac.ulb.infof403.grammar.GrammarVariable;
import be.ac.ulb.infof403.grammar.Rule;
import java.util.ArrayList;

/**
 * Element of the Parsing Tree
 */
public abstract class RuleTree {
    
    protected static String _generalOutput = "";
    
    private static Integer _varIndex = 0;
    private static Integer _id = 0;
    
    protected final Elem _value;
    protected final ArrayList<RuleTree> _children;
    private Integer _ruleUsed;
    
    protected RuleTree(final Elem value) {
        _id++;
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
    
    public Integer getRuleUsed() {
        return _ruleUsed;
    }
    
    public Integer getId() {
        return _id;
    }
    
    protected ArrayList<RuleTree> addChild(final Rule rule) {
        _ruleUsed = rule.getId();
        final ArrayList<RuleTree> allRuleTree = new ArrayList();
        for(final Elem elem : rule) {
            final RuleTree newRuleTree = RuleTreeFactory.getRuleTree(elem);
            _children.add(newRuleTree);
            if(!(elem instanceof Epsilon)) {
                allRuleTree.add(newRuleTree);
            }
        }
        
        return allRuleTree;
    }
    
    public String getResultVar(String param) { return ""; }
    
    public String getResultVar() { return getResultVar(""); }
    
    protected static String getNextVariable() {
        return "%" + (++_varIndex);
    }
    
    protected static String getLastVariable() {
        return "%" + _varIndex;
    }
    
    /**
     * TODO debug
     * @return 
     */
    public static String getGeneralOuput() {
        return _generalOutput;
    }
    
}
