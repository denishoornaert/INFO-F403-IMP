package be.ac.ulb.infof403.grammar;

import be.ac.ulb.infof403.Elem;
import java.util.ArrayList;
import java.util.HashSet;

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
            result += elem.getValue()+ " ";
        }
        return result;
    }
    
    public boolean allComposantTerminal(final HashSet<GrammarVariable> ignoreVar) {
        for(Elem elem : _allComposant) {
            if(elem instanceof GrammarVariable && !ignoreVar.contains((GrammarVariable) elem)) {
                return false;
            }
        }
        return true;
    }
    
}
