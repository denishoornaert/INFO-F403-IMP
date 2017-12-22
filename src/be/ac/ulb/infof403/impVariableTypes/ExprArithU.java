package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class ExprArithU extends RuleTree {

    public ExprArithU(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() {
        return this._children.get(0).getResultVar();
    }
    
}
