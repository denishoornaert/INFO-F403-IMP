package be.ac.ulb.infof403.grammar;

import be.ac.ulb.infof403.Elem;

/**
 * 
 */
public class GrammarVariable extends Elem {
    
    private static int globalCounter = 0;
    
    private final int _counter;
    private final String _varName;
    
    public GrammarVariable() {
        this("");
    }
    
    public GrammarVariable(final String varName) {
        _varName = varName;
        _counter = globalCounter++;
    }
    
    public String getVarName() {
        return _varName;
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public String getValue() {
        String result = "<";
        if(_varName.isEmpty()) {
            result += _counter;
        } else {
            result += _varName;
        }
        result += ">";
        
        return result;
    }

    @Override
    public Object getType() {
        return "Var";
    }
    
    @Override
    public boolean equals(Object var) {
        return true; // TODO correct comparison.
    }
    
}