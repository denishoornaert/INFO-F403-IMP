package be.ac.ulb.infof403.grammar;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.Symbol;
import be.ac.ulb.infof403.Terminal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * 
 */
public class GrammarVariable extends Elem {
    
    private static int globalCounter = 0;
    
    private final int _counter;
    private final String _varName;
    private ArrayList<Rule> _listRule;
    
    public GrammarVariable() {
        this("");
    }
    
    public GrammarVariable(final String varName) {
        _varName = varName;
        _counter = globalCounter++;
        _listRule = new ArrayList<>();
    }
    
    public String getVarName() {
        return _varName;
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public String getValue() {
        String result = "<";
        if(_varName.isEmpty()) {
            result += _counter;
        } else {
            result += _varName;
        }
        result += ">";
        
        return result;
    }
    
    @Override
    public HashSet<Terminal> first() {
        final HashSet<Terminal> res = new HashSet<>();
        for (final Rule rule : _listRule) {
            Elem firstElem = rule.get(0);
            HashSet<Terminal> listTerminal = firstElem.first();
            res.addAll(listTerminal);
        }
        return res;
    }
    
    public Rule getRuleThatLeadsToSymbol(Symbol sym) {
        Rule res = null;
        int counter = 0;
        boolean found = false;
        while (counter < _listRule.size() && !found) {
            Rule rule = _listRule.get(counter);
            // if sym in the set returned by first, save it and exit the loop
            if(rule.get(0).first().contains(sym)) {
                res = rule;
                found = true;
            }
            counter++;
        }
        return res;
    }
    
    protected boolean allRuleComposantTerminal(final HashSet<GrammarVariable> ignoreVar) {
        for(final Rule rule : _listRule) {
            if(rule.allComposantTerminal(ignoreVar)) {
                return true;
            }
        }
        return false;
    }
    
    protected boolean haveNoRule() {
        return _listRule.isEmpty();
    }
    
    protected String getStrRules() {
        String result = "";
        for(final Rule rule : _listRule) {
            result += "(" + rule.getId() + ")\t" + this.toString() + "\t -> \t " + rule.toString() + "\n";
        }
        return result;
    }
    
    protected void removeRuleWithUnproductiveVariable(final 
            HashSet<GrammarVariable> productiveVariable) {
        final ArrayList<Rule> cloneSymListRule = (ArrayList<Rule>) _listRule.clone();
        for(final Rule rule : cloneSymListRule) {
            if(!rule.allComposantTerminal(productiveVariable)) {
                _listRule.remove(rule);
            }
        }
    }
    
    protected HashSet<GrammarVariable> getAllGrammarVariable() {
        final HashSet<GrammarVariable> result = new HashSet<>();
        for(final Rule rule : _listRule) {
            result.addAll(rule.getAllGrammarVariable());
        }
        return result;
    }
    
    protected boolean ruleContainsGramVar(final GrammarVariable gramVar) {
        for(final Rule rule : _listRule) {
            if(rule.contains(gramVar)) {
                return true;
            }
        }
        return false;
    }
    
    protected boolean isGramVarEndOfAtLeastOneRule(GrammarVariable gramVar) {
        for(final Rule rule : _listRule) {
            final int index = rule.indexOf(gramVar);
            if(index >= 0 && index == (rule.size()-1)) {
                return true;
            }
        }
        return false;
    }
    
    
    protected HashSet<Elem> getDirectFollowed(final GrammarVariable gramVar) {
        final HashSet<Elem> result = new HashSet<>();
        for(final Rule rule : _listRule) {
            final int index = rule.indexOf(gramVar);
            if(index >= 0) {
                if(index < rule.size()-1) {
                    final Elem followedElement = rule.get(index+1);
                    if(followedElement != gramVar) {
                        result.add(followedElement);
                    }
                }
            }
        }
        return result;
    }
    
    protected final ArrayList<Rule> getRules() {
        return _listRule;
    }
    
    public void addRule(final Elem... listElem) {
        addRule(new ArrayList<>(Arrays.asList(listElem)));
    }
    
    public void addRule(final ArrayList<Elem> listElem) {
        _listRule.add(new Rule(listElem));
    }
    
    public void cleanRules() {
        _listRule.clear();
    }
}