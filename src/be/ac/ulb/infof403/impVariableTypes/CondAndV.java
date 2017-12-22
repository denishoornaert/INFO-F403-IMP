package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.codeGenerator.CodeFactory;
import be.ac.ulb.infof403.parser.ErrorConvertToLlvm;
import be.ac.ulb.infof403.parser.RuleTree;

public class CondAndV extends RuleTree {

    public CondAndV(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() throws ErrorConvertToLlvm {
        final String resultVar;
        if(this._children.size() > 1) {
            final RuleTree condAndV = this._children.get(2);
            final String operator = condAndV.getChildren().get(0).getResultVar();
            if(!operator.isEmpty()) {
                final String strOutput = " = " + operator + 
                        " i1 " + this._children.get(1).getResultVar() + ", " + condAndV.getResultVar() + "\n";
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