package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class Instruction extends RuleTree {
    
    public Instruction(final Elem value) {
        super(value);
    }

    @Override
    public String getRepresentation() {
        return this._children.get(0).getRepresentation();
    }
    
}
