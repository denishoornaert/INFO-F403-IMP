package be.ac.ulb.infof403.grammar;

import be.ac.ulb.infof403.Elem;
import java.util.ArrayList;

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
    
}
