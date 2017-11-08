package be.ac.ulb.infof403.grammar;

import be.ac.ulb.infof403.Elem;
import java.util.ArrayList;

public class Stree {
    
    private Node _head;
    private GrammarVariable _variable;
    private Grammar _grammar;
    
    public Stree (GrammarVariable var) {
        _grammar = new Grammar();
        _variable = var;
    }
    
    public void add(ArrayList<Elem> list) {
        if(_head == null) {
            _head = new Node(list.get(0));
        }
        _head.add(1, list);
    }
    
    public void generateRules() {
        Rule list = new Rule();
        _head.generateRules(list, _variable, _grammar);
    }

    public Grammar getSubGrammar() {
        return _grammar;
    }
    
}
