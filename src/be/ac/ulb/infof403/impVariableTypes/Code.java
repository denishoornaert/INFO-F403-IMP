package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class Code extends RuleTree {
    
    public Code(final Elem value) {
        super(value);
    }

    @Override
    public String getResultVar() {
        System.out.println("[DEBUG] Result: Code");
        if(!(this._children.get(0) instanceof EpsilonVar)) {
            System.out.println("[DEBUG]     exec ;)");
            // <InstList> 
            this._children.get(0).getResultVar();
        }
        return "";
    }
    
}
