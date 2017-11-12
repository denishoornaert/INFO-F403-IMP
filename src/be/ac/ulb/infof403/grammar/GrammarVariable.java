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
        HashSet<Terminal> res = new HashSet<>();
        for (Rule rule : _listRule) {
            res.addAll(rule.get(0).first());
        }
        return res;
    }
    
    public ArrayList<Rule> getRules() {
        return _listRule;
    }
    
    public void addRule(final Elem... listElem) {
        addRule(new ArrayList<>(Arrays.asList(listElem)));
    }
    
    public void addRule(final ArrayList<Elem> listElem) {
        _listRule.add(new Rule(listElem));
    }
    
    public void cleanRules() {
        _listRule = new ArrayList<Rule>();
    }
}