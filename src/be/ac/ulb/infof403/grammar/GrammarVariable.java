package be.ac.ulb.infof403.grammar;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.Epsilon;
import be.ac.ulb.infof403.Symbol;
import be.ac.ulb.infof403.Terminal;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/**
 * 
 */
public class GrammarVariable extends Elem implements Comparable {
    
    private static int globalCounter = 0;
    
    private final int _counter;
    private final String _varName;
    private HashSet<Rule> _listRule;
    private Grammar _grammar;
    private boolean _marked = false;
    
    public GrammarVariable() {
        this("");
    }
    
    public GrammarVariable(final String varName) {
        _varName = varName;
        _counter = globalCounter++;
        _listRule = new HashSet<>();
        _grammar = null;
    }
    
    protected void setGrammar(Grammar gram) {
        if(_grammar != null) {
            throw new UnknownError("Grammar already defined for " + this.getVarName() +".");
        }
        _grammar = gram;
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
    public HashSet<Terminal> first() { // add marker
        final HashSet<Terminal> res = new HashSet<>();
        if(!_marked) {
            _marked = true;
            for (final Rule rule : _listRule) {
                final Elem firstElem = rule.get(0);
                final HashSet<Terminal> listTerminal = firstElem.first();
                for (final Terminal term : listTerminal) {
                    if(term instanceof Epsilon) {
                        res.addAll(_grammar.follow(this));
                    }
                }
                res.addAll(listTerminal);
            }
            _marked = false;
        }
        return res;
    }
    
    public Rule getRuleThatLeadsToSymbol(final Symbol sym) {
        Rule res = null;
        final Iterator<Rule> iteratorRule = _listRule.iterator();
        
        boolean found = false;
        while (iteratorRule.hasNext() && !found) {
            final Rule rule = iteratorRule.next();
            final Elem elem = rule.get(0);
            // if sym in the set returned by first, save it and exit the loop
            if((elem instanceof Epsilon && _grammar.follow(this).contains(sym)) ||
                    elem.first().contains(sym)) {
                res = rule;
                found = true;
            }
        }
        return res;
    }
    
    // TODO this method looks like the previous one (for some aspects) there might be something to refactore here...
    public HashSet<Elem> getExpectedCharacters() {
        HashSet<Elem> res = new HashSet<>();
        res.addAll(_grammar.follow(this));
        for (Rule rule : _listRule) {
            Elem elem = rule.get(0);
            if(!(elem instanceof Epsilon)) {
                res.addAll(rule.get(0).first());
            }
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
        for(final Rule rule : getRuleOrded()) {
            result += "(" + rule.getId() + ")\t" + this.toString() + "\t -> \t " + rule.toString() + "\n";
        }
        return result;
    }
    
    protected void removeRuleWithUnproductiveVariable(final 
            HashSet<GrammarVariable> productiveVariable) {
        final HashSet<Rule> cloneSymListRule = (HashSet<Rule>) _listRule.clone();
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
    
    protected boolean isGramVarEndOfAtLeastOneRule(final GrammarVariable gramVar) {
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
    
    /**
     * Get all symbol that contains the rules of this
     * 
     * @return all variable
     */
    protected HashSet<Symbol> getAllSymbol() {
        final HashSet<Symbol> result = new HashSet<>();
        for(final Rule rule : getRuleOrded()) {
            result.addAll(rule.getAllSymbol());
        }
        return result;
    }
    
    protected final HashSet<Rule> getRules() {
        return _listRule;
    }
    
    private ArrayList<Rule> getRuleOrded() {
        final ArrayList<Rule> listRule = new ArrayList<>(_listRule);
        Collections.sort(listRule);
        return listRule;
    }
    
    public void addRule(final Elem... listElem) {
        addRule(new ArrayList<>(Arrays.asList(listElem)));
    }
    
    public void addRule(final ArrayList<Elem> listElem) {
        _listRule.add(new Rule(listElem));
    }
    
    public void removeRule(final Rule rule) {
        _listRule.remove(rule);
    }
    
    public void cleanRules() {
        _listRule.clear();
    }
    
    private int getMaxRuleId() {
        final ArrayList<Rule> listRuleOrder = getRuleOrded();
        if(!listRuleOrder.isEmpty()) {
            return listRuleOrder.get(listRuleOrder.size()-1).getId();
        }
        return -1;
    }
    
    
    @Override
    public int compareTo(Object objToCompare) {
        if(objToCompare instanceof GrammarVariable) {
            final GrammarVariable gramVarToCompare = (GrammarVariable) objToCompare;
            final Integer currentId = getMaxRuleId();
            return currentId.compareTo(gramVarToCompare.getMaxRuleId());
        }
        return -1;
    }
    
}