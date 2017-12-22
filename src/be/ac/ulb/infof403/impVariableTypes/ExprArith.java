package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.codeGenerator.CodeFactory;
import be.ac.ulb.infof403.parser.ErrorConvertToLlvm;
import be.ac.ulb.infof403.parser.RuleTree;

public class ExprArith extends RuleTree {

    public ExprArith(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() throws ErrorConvertToLlvm {
        final String resultVar;
        final RuleTree exprArithV = this._children.get(1);
        final String exprArithVResult = exprArithV.getResultVar();
        if(!exprArithVResult.isEmpty()) {
            final String outputResult = " = " + exprArithV.getChildren().get(0).getResultVar() + 
                    " i32 " + this._children.get(0).getResultVar() + ", " + exprArithVResult + "\n";
            resultVar = getNextVariable();
            CodeFactory.write(resultVar + outputResult);
            
        } else {
            resultVar = this._children.get(0).getResultVar();
        }
        
        return resultVar;
    }
    
}