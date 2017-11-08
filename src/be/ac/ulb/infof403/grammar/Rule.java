package be.ac.ulb.infof403.grammar;

import be.ac.ulb.infof403.Elem;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * 
 */
public class Rule extends ArrayList<Elem> {
    
    public Rule() {
        super();
    }
    
    public Rule(final ArrayList<Elem> composant) {
        super(composant);
    }
    
    @Override
	public String toString() {
        String result = "";
        for(Elem elem : this) {
            result += elem.getValue()+ " ";
        }
        return result;
    }
    
    public boolean allComposantTerminal(final HashSet<GrammarVariable> ignoreVar) {
        for(Elem elem : this) {
            if(elem instanceof GrammarVariable && !ignoreVar.contains((GrammarVariable) elem)) {
                return false;
            }
        }
        return true;
    }
    
    public HashSet<GrammarVariable> getAllGrammarVariable() {
        final HashSet<GrammarVariable> allGrammarVar = new HashSet();
        for(Elem elem : this) {
            if(elem instanceof GrammarVariable) {
                allGrammarVar.add((GrammarVariable) elem);
            }
        }
        return allGrammarVar;
    }
    
}
