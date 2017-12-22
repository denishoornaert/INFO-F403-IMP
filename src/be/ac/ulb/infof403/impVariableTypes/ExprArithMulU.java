package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class ExprArithMulU extends RuleTree {

    public ExprArithMulU(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() {
        return this._children.get(0).getResultVar();
    }
    
}