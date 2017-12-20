package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class OpMul extends RuleTree {

    public OpMul(final Elem value) {
        super(value);
    }
    
    @Override
    public String getRepresentation() {
        return "mul"; // TODO division
    }
    
}

