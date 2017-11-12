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
    
    private ArrayList<GrammarVariable> _variables;
    private final GrammarVariable _initialState;
    
    public Grammar(final GrammarVariable initialState) {
        _variables = new ArrayList<>();
        _variables.add(initialState);
        _initialState = initialState;
    }
    
    public void addVariables(GrammarVariable... variables) {
        _variables.addAll(Arrays.asList(variables));
    }
    
    public ArrayList<GrammarVariable> getVariables() {
        return _variables;
    }
    
    @Override
	public String toString() {
        String result = "";
        for(GrammarVariable sym : _variables) {
            for(Rule rule : sym.getRules()) {
                result += sym.toString() + "\t -> \t " + rule.toString() + "\n";
            }
        }
        return result;
    }
    
    public void removeUseless() {
        removeUnproductive();
        removeInaccessible();
    }
    
    private void removeUnproductive() {
        final HashSet<GrammarVariable> newGrammarVariable = new HashSet<>();
        boolean addVariable = true;
        
        while(addVariable) {
            addVariable = false;
            for(final GrammarVariable sym : _variables) {
                if(sym.getRules().isEmpty()) {
                    if(newGrammarVariable.add(sym)) {
                        addVariable = true;
                    }
                } else {
                    for(final Rule rule : sym.getRules()) {
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
        final ArrayList<GrammarVariable> cloneListRule =
                (ArrayList<GrammarVariable>) _variables.clone();
        for(final GrammarVariable sym : cloneListRule) {
            if(!productiveVariable.contains(sym)) {
                _variables.remove(sym);
            } else {
                final ArrayList<Rule> cloneSymListRule = (ArrayList<Rule>) sym.getRules().clone();
                for(final Rule rule : cloneSymListRule) {
                    if(!rule.allComposantTerminal(productiveVariable)) {
                        sym.getRules().remove(rule);
                    }
                }
            }
        }
    }
    
    private void removeInaccessible() {
        final HashSet<GrammarVariable> accessibleVariable = new HashSet<>();
        accessibleVariable.add(_initialState);
        
        boolean addVariable = true;
        while(addVariable) {
            addVariable = false;
            
            for(final GrammarVariable var : accessibleVariable) {
                for(final Rule rule : var.getRules()) {
                    if(accessibleVariable.addAll(rule.getAllGrammarVariable())) {
                        addVariable = true;
                    }
                }
            }
        }
        
        final ArrayList<GrammarVariable> cloneListRule =
                (ArrayList<GrammarVariable>) _variables.clone();
        for(final GrammarVariable var : cloneListRule) {
            if(!accessibleVariable.contains(var)) {
                _variables.remove(var);
            }
        }
    }
    
    public void facorisation() { // TODO has to be tested
        for (final GrammarVariable var : _variables) {
            // Setup of the stree and generation of the factorised rules.
            final Stree s = new Stree(var);
            for (final Rule rule : var.getRules()) {
                s.add(rule);
            }
            final Grammar g = s.generateRules();
            // Replacement of the former rule by the (new) one(s).
            //_variables.remove(var);
            //_listRule.put(var, g.getRulesForVariable(var));
            for (GrammarVariable variable : g.getVariables()) {
                System.out.println(variable.getVarName());
                if(!_variables.contains(variable)) {
                    System.out.println("no match");
                    // implicitly add the rules associated to this variable.
                    _variables.add(variable);
                }
            }
        }
    }
    
    public void removeLeftRecursion() {
        final ArrayList<GrammarVariable> workingList = (ArrayList<GrammarVariable>)_variables.clone();
        for (final GrammarVariable key : _variables) {
            final ArrayList<Rule> value = key.getRules();
            Boolean again = true;
            int counter = 0;
            while(counter < value.size() && again) {
                again = true;
                if(key.equals(value.get(0).get(0))) {
                    again = false;
                    final GrammarVariable u = new GrammarVariable(key.getVarName()+"U");
                    final GrammarVariable v = new GrammarVariable(key.getVarName()+"V");
                    final ArrayList<Rule> list = key.getRules();
                    createUVRule(key, u, v);
                    createURule(workingList, list, u);
                    createVRule(workingList, list, v);
                }
                counter++;
            }
        }
        _variables = workingList;
    }
    
    private void createUVRule(final GrammarVariable key, final GrammarVariable u, final GrammarVariable v) {
        key.cleanRules();
        key.addRule(new Rule(u, v));
    }
    
    private void createURule(final ArrayList<GrammarVariable> workingList, 
            final ArrayList<Rule> list, final GrammarVariable u) {
        workingList.add(u);
        for (int i = 1; i < list.size(); i++) {
            u.addRule(list.get(i));
        }
    }
    
    private void createVRule(final ArrayList<GrammarVariable> workingList, 
            final ArrayList<Rule> list, final GrammarVariable v) {
        final Rule workingRule = list.get(0);
        workingRule.remove(0);
        workingRule.add(v);
        workingList.add(v);
        v.addRule(workingRule);
    }
    
}
