package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class Code extends RuleTree {
    
    public Code(final Elem value) {
        super(value);
    }

    @Override
    public String getResultVar() {
        if(!(this._children.get(0) instanceof EpsilonVar)) {
            // <InstList> 
            this._children.get(0).getResultVar();
        }
        return "";
    }
    
}