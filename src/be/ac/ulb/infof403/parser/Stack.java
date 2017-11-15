package be.ac.ulb.infof403.parser;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.Epsilon;
import be.ac.ulb.infof403.grammar.Rule;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Specific stack used by the parser during the syntax verification and tree construction processes
 * 
 */
public class Stack {
    
    ArrayList<Elem> _stack; // TODO Remy does creating an interface for both Symbol and GrammarVariable is a good idea ??
    
    public Stack() {
        _stack = new ArrayList<>();
    }
    
    public void push(Elem elem) {
        if(!(elem instanceof Epsilon)) {
            _stack.add(elem);
        }
    }
    
    public void push(Rule r) {
        // TODO havn't found any kind of iterator for that... Does one exist ??
        for (int i = r.size()-1; i >= 0; i--) {
            this.push(r.get(i));
        }
    }
    
    public Elem pop() { // since tos exists does returning the removed elem is useful ??
        int lastElemIndex = _stack.size()-1;
        Elem tmp = _stack.get(lastElemIndex);
        _stack.remove(lastElemIndex);
        return tmp;
    }
    
    public Elem tos() { // abbreviatio of Top Of Stack
        int lastElemIndex = _stack.size()-1;
        Elem tmp = _stack.get(lastElemIndex);
        return tmp;
    }
    
    public boolean isEmpty() {
        return _stack.isEmpty();
    }
    
}
