package be.ac.ulb.infof403.grammar;

import be.ac.ulb.infof403.Elem;
import java.util.ArrayList;

public class Stree {
    
    private final ArrayList<Node> _head;
    private final GrammarVariable _variable;
    private final Grammar _grammar;
    
    public Stree (final GrammarVariable var) {
        _head = new ArrayList<>();
        _grammar = new Grammar(var);
        _variable = var;
    }
    
    public void add(final ArrayList<Elem> list) {
        int index = 0; // TODO neve change ? Why ?
        if(index < list.size()) {
            int counter = 0;
            boolean find = false;
            while(counter < _head.size() && !find) {
                if(_head.get(counter).getValue().equals(list.get(index))) {
                    find = true;
                }
                counter++;
            }
            if(!find) {
                _head.add(new Node(list.get(index)));
                _head.get(counter).add(index+1, list);
            }
            else {
                _head.get(counter-1).add(index+1, list);
            }
        }
    }
    
    public Grammar generateRules() {
        for (final Node node : _head) {
            node.generateRules(new Rule(), _variable, _grammar);
        }
        return _grammar;
    }
    
}
