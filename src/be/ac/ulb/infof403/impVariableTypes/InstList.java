package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class InstList extends RuleTree {

    public InstList(final Elem value) {
        super(value);
    }

    @Override
    public String getRepresentation() {
        // <Instruction> <InstList'> 
        return this._children.get(0).getRepresentation() + this._children.get(1).getRepresentation();
    }

}

