package be.ac.ulb.infof403.grammar;

import be.ac.ulb.infof403.Elem;
import java.util.ArrayList;

/**
 * Class used by the stree.
 * 
 */
public class Node {
    
    private Elem _value;
    private ArrayList<Node> _children;
    
    public Node(Elem value) {
        _value = value;
        _children = new ArrayList<>();
    }
    
    public void add(ArrayList<Elem> list) {
        this.add(0, list);
    }
    
    public void add(int index, ArrayList<Elem> list) {
        if(index < list.size()) {
            int counter = 0;
            boolean find = false;
            while(counter < _children.size() && !find) {
                if(_children.get(counter).getValue().equals(list.get(index))) {
                    find = true;
                }
                counter++;
            }
            if(!find) {
                _children.add(new Node(list.get(index)));
                _children.get(counter).add(index+1, list);
            }
            else {
                _children.get(counter-1).add(index+1, list);
            }
        }
    }
    
    public Elem getValue() {
        return _value;
    }
    
    public ArrayList<Node> getChildren() {
        return _children;
    }
    
    public int getChildrenNumber() {
        return _children.size();
    }
    
    public void generateRules(Rule list, GrammarVariable currentVar, Grammar grammar) {
        list.add(_value);
        if(_children.isEmpty()) {
            grammar.addRule(currentVar, list);
        }
        else {
            if(_children.size() == 1) {
                _children.get(0).generateRules(list, currentVar, grammar);
            }
            else {
                GrammarVariable var = new GrammarVariable(currentVar.getVarName()+"'");
                list.add(var);
                grammar.addRule(currentVar, list);
                for (Node node : _children) {
                    node.generateRules(new Rule(), var, grammar);
                }
            }
        }
    }
    
}
