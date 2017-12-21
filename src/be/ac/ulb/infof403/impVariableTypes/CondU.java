package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class CondU extends RuleTree {

    public CondU(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() {
        return this._children.get(0).getResultVar();
    }
    
}