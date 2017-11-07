package be.ac.ulb.infof403.grammar;

import be.ac.ulb.infof403.Elem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
        final ArrayList<GrammarVariable> newGrammarVariable = new ArrayList<>();
        
    }
    
}
