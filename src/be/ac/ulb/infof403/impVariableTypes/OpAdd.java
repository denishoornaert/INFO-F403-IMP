package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class OpAdd extends RuleTree {

    public OpAdd(final Elem value) {
        super(value);
    }
    
    @Override
    public String getRepresentation() {
        return "add"; // TODO soustraction
    }

}

