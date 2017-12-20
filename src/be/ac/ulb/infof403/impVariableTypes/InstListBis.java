package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class InstListBis extends RuleTree {

    public InstListBis(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() {
        System.out.println("[DEBUG] Result: InstListBis");
        if(this._children.size() > 1) { // If not epsilon rule
            System.out.println("[DEBUG]     exec ;)");
            this._children.get(1).getResultVar();
        }
        return "";
    }
    
}