package be.ac.ulb.infof403.grammar;

import be.ac.ulb.infof403.Elem;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Stree {
    
    private final ArrayList<Node> _head;
    private final GrammarVariable _variable;
    
    public Stree (final GrammarVariable var) {
        _head = new ArrayList<>();
        _variable = var;
    }
    
    protected boolean addRules(final HashSet<Rule> listRule) {
        boolean newRule = false;
        for (final Rule rule : listRule) {
            newRule = newRule || add(rule);
        }
        return newRule;
    }
    
    /**
     * Add rule one per wone
     * 
     * @param list element of the rule
     * @return True if one match between rules
     */
    private boolean add(final List<Elem> list) {
        boolean oneMatch = false;
        
        if(list.size() > 1) {
            
            final Elem firstElem = list.get(0);
            final List<Elem> newList = list.subList(1, list.size());
            
            // Try to find a match node
            int counter = 0;
            boolean find = false;
            while(counter < _head.size() && !find) {
                if(_head.get(counter).getValue().equals(firstElem)) {
                    find = true;
                } else {
                    counter++;
                }
            }
            
            // If we find a match
            if(find) {
                // Test now with the sublist
                _head.get(counter).add(newList);
                oneMatch = true;
            }
            else { // Else just add a node to the stree
                _head.add(new Node(firstElem, newList));
            }
            
        }
        
        return oneMatch;
    }
    
    public HashSet<GrammarVariable> generateNewGramVariable() {
        final HashSet<GrammarVariable> result = new HashSet<>();
        _variable.cleanRules();
        for (final Node node : _head) {
            result.addAll(node.generateRules(new Rule(), _variable));
        }
        return result;
    }
    
}
