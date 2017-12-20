package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class ExprArithMul extends RuleTree {
    
    
    public ExprArithMul(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() {
        final String resultVar;
        final RuleTree exprArithMulV = this._children.get(1);
        final String exprArithMulVResultVar = exprArithMulV.getResultVar();
        if(!exprArithMulVResultVar.isEmpty()) {
            final String strOutput = " = " + exprArithMulV.getChildren().get(0).getResultVar() + 
                    " i32 " + this._children.get(0).getResultVar() + ", " + exprArithMulVResultVar + "\n";
            resultVar = getNextVariable();
            _generalOutput += resultVar + strOutput;
            
        } else {
            resultVar = this._children.get(0).getResultVar();
        }
        
        return resultVar;
    }
    
}
