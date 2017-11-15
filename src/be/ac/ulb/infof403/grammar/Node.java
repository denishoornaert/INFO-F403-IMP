package be.ac.ulb.infof403.grammar;

import be.ac.ulb.infof403.Elem;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Class used by the stree.
 * 
 */
public class Node {
    
    private final Elem _value;
    private final ArrayList<Node> _children;
    
    protected Node(final Elem value, final List<Elem> list) {
        _value = value;
        _children = new ArrayList<>();
        
        add(list);
    }
    
    
    protected void add(final List<Elem> list) {
        if(!list.isEmpty()) { // If list isn't empty
            final Elem firstElem = list.get(0);
            final List<Elem> newList = list.subList(1, list.size());
            
            // Try to find a match node
            int counter = 0;
            boolean find = false;
            while(counter < _children.size() && !find) {
                if(_children.get(counter).getValue().equals(firstElem)) {
                    find = true;
                } else {
                    counter++;
                }
            }
            
            // If we find a match
            if(find) {
                // Test now with the sublist
                _children.get(counter).add(newList);
            }
            else { // Else just add a node to the stree
                _children.add(new Node(firstElem, newList));
            }
            
        }
    }
    
    protected Elem getValue() {
        return _value;
    }
    
    protected HashSet<GrammarVariable> generateRules(final Rule newRule, final GrammarVariable currentVar) {
        final HashSet<GrammarVariable> newGramVars = new HashSet<>();
        
        // Add value to new Rule
        newRule.add(_value);
        
        if(_children.isEmpty()) { // If no children -> :tada: end of the rule
            currentVar.addRule(newRule);
        }
        else {
            // If there is one children
            if(_children.size() == 1) {
                newGramVars.addAll(_children.get(0).generateRules(newRule, currentVar));
            }
            else {
                final GrammarVariable newVar = new GrammarVariable(currentVar.getVarName()+"'");
                newGramVars.add(newVar);
                newRule.add(newVar);
                
                currentVar.addRule(newRule);
                
                for (final Node node : _children) {
                    newGramVars.addAll(node.generateRules(new Rule(), newVar));
                }
            }
        }
        return newGramVars;
    }
    
}
