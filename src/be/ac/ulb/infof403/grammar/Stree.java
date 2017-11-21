package be.ac.ulb.infof403.grammar;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.Epsilon;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Stree used to remove left reccursion and composed by {@link Node}
 */
public class Stree {
    
    private final ArrayList<Node> _head;
    private final GrammarVariable _variable;
    
    public Stree (final GrammarVariable var) {
        _head = new ArrayList<>();
        _variable = var;
    }
    
    private ArrayList<Rule> sortSetBySize(final HashSet<Rule> listRule) {
        final ArrayList<Rule> res = new ArrayList<>(listRule);
        final int n = res.size();  
        Rule temp = null;  
        for(int i=0; i < n; i++){  
            for(int j=1; j < (n-i); j++){  
                if(res.get(j-1).size() < res.get(j).size()){ 
                    temp = res.get(j-1);  
                    res.set(j-1, res.get(j));
                    res.set(j, temp);
                }
            }
        }
        return res;
    }
    
    protected boolean addRules(final HashSet<Rule> listRule) {
        boolean newRule = false;
        for (final Rule rule : this.sortSetBySize(listRule)) {
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
        
        if(list.size() > 0) {
            
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
                if(!newList.isEmpty()) {
                    // Test now with the sublist
                    _head.get(counter).add(newList);
                }
                else {
                    final ArrayList<Elem> array = new ArrayList<>();
                    array.add(new Epsilon());
                    _head.get(counter).add(array);
                }
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
