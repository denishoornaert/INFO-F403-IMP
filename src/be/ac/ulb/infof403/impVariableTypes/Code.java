package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.Epsilon;
import be.ac.ulb.infof403.parser.RuleTree;

public class Code extends RuleTree {
    
    public Code(final Elem value) {
        super(value);
    }

    @Override
    public String getRepresentation() {
        String result = "";
        if(!(this._value instanceof Epsilon)) {
            // <InstList> 
            result = this._children.get(0).getRepresentation();
        }
        return result;
    }
    
}
