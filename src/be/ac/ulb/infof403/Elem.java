package be.ac.ulb.infof403;

import java.util.HashSet;

/**
 * 
 */
public abstract class Elem {
    
    @Override
    public abstract String toString();
    
    public abstract Object getValue();
    
    public abstract HashSet<Symbol> first();
}
