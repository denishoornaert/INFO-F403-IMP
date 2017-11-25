package be.ac.ulb.infof403.parser;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.Symbol;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Stack use for parsing
 */
public class ParseStack extends Stack<RuleTree> {
    
    public ParseStack(final RuleTree firstElemen) {
        this.add(firstElemen);
    }
    
    public boolean tosIsSymbol() {
         return peek().getValue() instanceof Symbol;
    }
    
    public void add(final ArrayList<RuleTree> listRuleTree) {
        for(final RuleTree ruleTree : listRuleTree) {
            add(ruleTree);
        }
    }
    
    @Override
    public String toString() {
        String result = "";
        boolean first = true;
        
        while(!isEmpty()) {
            final RuleTree currentRuleTree = pop();
            final Elem elem = currentRuleTree.getValue();
            
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
