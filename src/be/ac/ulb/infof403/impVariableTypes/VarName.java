package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class VarName extends RuleTree {
    
    public VarName(final Elem elem) {
        super(elem);
    }
    
    @Override
    public String getRepresentation() {
        return (this._value != null ? getNextVariable() + " = add i32 %" + this._value.getValue().toString() + ", 0" : "");
    }
    
}
