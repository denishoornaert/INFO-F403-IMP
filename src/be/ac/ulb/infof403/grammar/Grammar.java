package be.ac.ulb.infof403.grammar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * List of rule of a grammar
 */
public class Grammar {
    
    private HashSet<GrammarVariable> _variables;
    private final GrammarVariable _initialState;
    
    public Grammar(final GrammarVariable initialState) {
        _variables = new HashSet<>();
        _variables.add(initialState);
        _initialState = initialState;
    }
    
    public void addVariables(final GrammarVariable... variables) {
        addVariables(new ArrayList<>(Arrays.asList(variables)));
    }
    
    public void addVariables(final Collection<GrammarVariable> allVariables) {
        _variables.addAll(allVariables);
    }
    
    public HashSet<GrammarVariable> getVariables() {
        return _variables;
    }
    
    @Override
	public String toString() {
        String result = "";
        for(final GrammarVariable sym : _variables) {
            result += sym.getStrRules();
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
                if(sym.haveNoRule()) {
                    if(newGrammarVariable.add(sym)) {
                        addVariable = true;
                    }
                } else {
                    if(sym.allRuleComposantTerminal(newGrammarVariable)) {
                        if(newGrammarVariable.add(sym)) {
                            addVariable = true;
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
                sym.removeRuleWithUnproductiveVariable(productiveVariable);
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
                if(accessibleVariable.addAll(var.getAllGrammarVariable())) {
                    addVariable = true;
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
        final HashSet<GrammarVariable> workingList = (HashSet<GrammarVariable>)_variables.clone();
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
    
    private void createURule(final HashSet<GrammarVariable> workingList, 
            final ArrayList<Rule> list, final GrammarVariable u) {
        workingList.add(u);
        for (int i = 1; i < list.size(); i++) {
            u.addRule(list.get(i));
        }
    }
    
    private void createVRule(final HashSet<GrammarVariable> workingList, 
            final ArrayList<Rule> list, final GrammarVariable v) {
        final Rule workingRule = list.get(0);
        workingRule.remove(0);
        workingRule.add(v);
        workingList.add(v);
        v.addRule(workingRule);
    }
    
    public void printFollow(final GrammarVariable gramVar) {
        for(final GrammarVariable currentGramVar : this._variables) {
            // TODO !!
        }
        
    }
    
}
