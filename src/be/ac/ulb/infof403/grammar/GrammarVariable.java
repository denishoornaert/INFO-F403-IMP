package be.ac.ulb.infof403.grammar;

import be.ac.ulb.infof403.Elem;

/**
 * 
 */
public class GrammarVariable extends Elem {
    
    private static int globalCounter = 0;
    
    private final int _counter;
    
    public GrammarVariable() {
        _counter = globalCounter++;
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public String getValue() {
        return "<" + _counter + ">";
    }
    
}