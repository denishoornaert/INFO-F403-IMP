package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class Assign extends RuleTree {
    
    public Assign(final Elem value) {
        super(value);
    }
    
    @Override
    public String getRepresentation() {
        String result = getNextVariable() + " = add i32 " + ", 0\n"; // TODO
        result += "%" + this.getValue().getValue().toString() + " = add i32 " + getLastVariable() + ", 0";
        
        return result;
    }
}
