package be.ac.ulb.infof403.grammar;

import be.ac.ulb.infof403.Elem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * List of rule of a grammar
 */
public class Grammar {
    
    private final HashMap<GrammarVariable, ArrayList<Rule>> _listRule;
    
    public Grammar() {
        _listRule = new HashMap<>();
    }
    
    public ArrayList getRuleForVariable(final GrammarVariable variable) {
        return _listRule.get(variable);
    }
    
    public void addRule(final GrammarVariable sym, final Elem... listElem) {
        addRule(sym, new ArrayList<>(Arrays.asList(listElem)));
    }
    
    public void addRule(final GrammarVariable sym, final ArrayList<Elem> listElem) {
        if(!_listRule.containsKey(sym)) {
            _listRule.put(sym, new ArrayList<Rule>());
        }
        _listRule.get(sym).add(new Rule(listElem));
    }
    
    @Override
	public String toString() {
        String result = "";
        for(GrammarVariable sym : _listRule.keySet()) {
            for(Rule rule : _listRule.get(sym)) {
                result += sym.toString() + "\t -> \t " + rule.toString() + "\n";
            }
        }
        
		return result;
	}
    
    public void removeUnproductive() {
        final HashSet<GrammarVariable> newGrammarVariable = new HashSet<>();
        boolean addVariable = true;
        
        while(addVariable) {
            addVariable = false;
            for(GrammarVariable sym : _listRule.keySet()) {
                if(_listRule.get(sym).isEmpty()) {
                    if(newGrammarVariable.add(sym)) {
                        addVariable = true;
                    }
                } else {
                    for(Rule rule : _listRule.get(sym)) {
                        if(rule.allComposantTerminal(newGrammarVariable)) {
                            if(newGrammarVariable.add(sym)) {
                                addVariable = true;
                            }
                            break;
                        }
                    }
                }
            }
        }
        
        removeRuleWithUnproductiveVariable(newGrammarVariable);
    }
    
    private void removeRuleWithUnproductiveVariable(final HashSet<GrammarVariable> productiveVariable) {
        final HashMap<GrammarVariable, ArrayList<Rule>> cloneListRule =
                (HashMap<GrammarVariable, ArrayList<Rule>>) _listRule.clone();
        for(final GrammarVariable sym : cloneListRule.keySet()) {
            if(!productiveVariable.contains(sym)) {
                _listRule.remove(sym);
            } else {
                final ArrayList<Rule> cloneSymListRule = (ArrayList<Rule>) _listRule.get(sym).clone();
                for(final Rule rule : cloneSymListRule) {
                    if(!rule.allComposantTerminal(productiveVariable)) {
                        _listRule.get(sym).remove(rule);
                    }
                }
            }
        }
    }
    
}
