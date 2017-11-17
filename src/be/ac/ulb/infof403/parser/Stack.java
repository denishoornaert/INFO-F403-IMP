package be.ac.ulb.infof403.parser;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.Epsilon;
import be.ac.ulb.infof403.Symbol;
import be.ac.ulb.infof403.grammar.Rule;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Specific stack used by the parser during the syntax verification and tree construction processes
 * 
 */
public class Stack {
    
    private final ArrayList<Elem> _stack; // TODO Remy does creating an interface for both Symbol and GrammarVariable is a good idea ??
    
    public Stack() {
        _stack = new ArrayList<>();
    }
    
    public void push(final Elem elem) {
        if(!(elem instanceof Epsilon)) {
            _stack.add(elem);
        }
    }
    
    public void push(final Rule r) {
        final ListIterator<Elem> li = r.listIterator(r.size());

        // Iterate in reverse.
        while(li.hasPrevious()) {
            this.push(li.previous());
        }
    }
    
    public Elem pop() { // since tos exists does returning the removed elem is useful ??
        final int lastElemIndex = _stack.size()-1;
        final Elem tmp = _stack.get(lastElemIndex);
        _stack.remove(lastElemIndex);
        return tmp;
    }
    
    public Elem tos() { // abbreviatio of Top Of Stack
        final int lastElemIndex = _stack.size()-1;
        final Elem tmp = _stack.get(lastElemIndex);
        return tmp;
    }
    
    public boolean isEmpty() {
        return _stack.isEmpty();
    }
    
    @Override
    public String toString() {
        String result = "";
        boolean first = true;
        for(final Elem elem : _stack) {
            if(!first) {
                result += ", ";
            }
            first = false;
            
            if(elem instanceof Symbol) {
                result += ((Symbol) elem).getType();
            } else {
                result += elem.toString();
            }
        }
        
        return result;
    }
    
}
