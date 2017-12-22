package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class InstList extends RuleTree {

    public InstList(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() {
        // Compute but no result
        this._children.get(0).getResultVar();
        this._children.get(1).getResultVar();
        return "";
    }
    
}