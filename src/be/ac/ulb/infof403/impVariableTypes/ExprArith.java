package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class ExprArith extends RuleTree {

    public ExprArith(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() {
        System.out.println("[DEBUG] Result: ExprArith");
        final String resultVar;
        final RuleTree exprArithV = this._children.get(1);
        if(!exprArithV.getResultVar().isEmpty()) {
            String outputResult = " = " + exprArithV.getChildren().get(0).getResultVar() + 
                    "i32 " + this._children.get(0).getResultVar() + ", " + exprArithV.getResultVar() + "\n";
            resultVar = getNextVariable();
            _generalOutput += resultVar + outputResult;
            
        } else {
            resultVar = this._children.get(0).getResultVar();
        }
        
        return resultVar;
    }
    
}