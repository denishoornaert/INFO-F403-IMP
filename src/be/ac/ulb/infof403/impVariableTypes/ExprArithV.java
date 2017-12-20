package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class ExprArithV extends RuleTree {

    public ExprArithV(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() {
        System.out.println("[DEBUG] Result: ExprArithV");
        final String resultVar;
        if(this._children.size() > 1) {
            final RuleTree exprArithV = this._children.get(2);
            String strOutput = " = " + exprArithV.getChildren().get(0).getResultVar() + 
                    "i32 " + this._children.get(0).getResultVar() + ", " + exprArithV.getResultVar() + "\n";
            resultVar = getNextVariable();
            _generalOutput += resultVar + strOutput;
            
        } else {
            resultVar = this._children.get(0).getResultVar();
        }
        
        return resultVar;
    }
    
}
