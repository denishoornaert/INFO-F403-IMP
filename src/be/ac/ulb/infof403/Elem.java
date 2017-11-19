package be.ac.ulb.infof403;

import java.util.HashSet;

/**
 * All element that compose grammar and imp file (epsilon, terminal, symbol, ...)
 */
public abstract class Elem {
    
    @Override
    public abstract String toString();
    
    public abstract Object getValue();
    
    public abstract HashSet<Terminal> first();
}
