package be.ac.ulb.infof403.grammar;

import be.ac.ulb.infof403.Elem;
import java.util.ArrayList;

/**
 * 
 */
public class Rule {
    
    private final ArrayList<Elem> _allComposant;
    
    public Rule(final ArrayList<Elem> composant) {
        _allComposant = composant;
    }
    
    @Override
	public String toString() {
        String result = "";
        for(Elem elem : _allComposant) {
            result += elem.toString() + " ";
        }
        return result;
    }
    
}
