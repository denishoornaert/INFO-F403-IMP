package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.codeGenerator.CodeFactory;
import be.ac.ulb.infof403.parser.RuleTree;

public class ExprArithMulV extends RuleTree {
    
    public ExprArithMulV(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() {
        System.out.println("[DEBUG] Result: ExprArithMulV");
        final String resultVar;
        if(this._children.size() > 1) {
            final RuleTree exprArithMulV = this._children.get(2);
            final String operator = exprArithMulV.getChildren().get(0).getResultVar();
            if(!operator.isEmpty()) {
                final String strOutput = " = " + operator + 
                        " i32 " + this._children.get(1).getResultVar() + ", " + exprArithMulV.getResultVar() + "\n";
                resultVar = getNextVariable();
                CodeFactory.write(resultVar + strOutput);
                
            } else {
                resultVar = this._children.get(1).getResultVar();
            }
        } else {
            resultVar = "";
        }
        
        return resultVar;
    }
    
}