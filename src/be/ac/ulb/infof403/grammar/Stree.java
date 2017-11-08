package be.ac.ulb.infof403.grammar;

import be.ac.ulb.infof403.Elem;
import java.util.ArrayList;

public class Stree {
    
    private ArrayList<Node> _head;
    private GrammarVariable _variable;
    private Grammar _grammar;
    
    public Stree (GrammarVariable var) {
        _head = new ArrayList<>();
        _grammar = new Grammar(var);
        _variable = var;
    }
    
    public void add(ArrayList<Elem> list) {
        int index = 0;
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
    
    public void generateRules() {
        for (Node node : _head) {
            Rule list = new Rule();
            node.generateRules(list, _variable, _grammar);
        }
    }

    public Grammar getSubGrammar() {
        return _grammar;
    }
    
}
