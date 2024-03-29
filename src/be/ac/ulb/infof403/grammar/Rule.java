package be.ac.ulb.infof403.grammar;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.Symbol;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Rule of the grammar
 */
public class Rule extends ArrayList<Elem> implements Comparable {
    
    private static int ruleId = 0;
    
    private int _id;
    
    public Rule(final Elem... elems) {
        this(new ArrayList<>(Arrays.asList(elems)));
    }
    
    public Rule(final ArrayList<Elem> composant) {
        this(++ruleId, composant);
    }
    
    /**
     * Create rule
     * 
     * @param id specific ID (<b>only for test</b>)
     * @param composant element of the rule
     */
    public Rule(final int id, final ArrayList<Elem> composant) {
        super(composant);
        _id = id;
    }
    
    protected void renumber() {
        _id = ++ruleId;
    }
    
    public Integer getId() {
        return _id;
    }
    
    @Override
	public String toString() {
        String result = "";
        for(final Elem elem : this) {
            result += elem.getValue()+ " ";
        }
        return result;
    }
    
    protected boolean allComposantTerminal(final HashSet<GrammarVariable> ignoreVar) {
        for(final Elem elem : this) {
            if(elem instanceof GrammarVariable && !ignoreVar.contains((GrammarVariable) elem)) {
                return false;
            }
        }
        return true;
    }
    
    protected HashSet<GrammarVariable> getAllGrammarVariable() {
        final HashSet<GrammarVariable> allGrammarVar = new HashSet();
        for(final Elem elem : this) {
            if(elem instanceof GrammarVariable) {
                allGrammarVar.add((GrammarVariable) elem);
            }
        }
        return allGrammarVar;
    }
    
    protected HashSet<Symbol> getAllSymbol() {
        final HashSet<Symbol> result = new HashSet<>();
        for(final Elem elem : this) {
            if(elem instanceof Symbol) {
                result.add((Symbol) elem);
            }
        }
        return result;
    }

    @Override
    public int compareTo(final Object objToCompare) {
        if(objToCompare instanceof Rule) {
            final Rule ruleToCompare = (Rule) objToCompare;
            final Integer currentId = getId();
            return currentId.compareTo(ruleToCompare.getId());
        }
        return -1;
    }
    
    /**
     * Reset the rule id (<b>Only for test</b>)
     */
    public static void resetId() {
        ruleId = 0;
    }
    
}
