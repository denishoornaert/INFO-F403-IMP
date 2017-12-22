package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.codeGenerator.CodeFactory;
import be.ac.ulb.infof403.parser.ErrorConvertToLlvm;
import be.ac.ulb.infof403.parser.RuleTree;

public class CondV extends RuleTree {

    public CondV(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() throws ErrorConvertToLlvm {
        final String resultVar;
        if(this._children.size() > 1) {
            final RuleTree condV = this._children.get(2);
            final String operator = condV.getChildren().get(0).getResultVar();
            if(!operator.isEmpty()) {
                final String strOutput = " = " + operator + 
                        " i1 " + this._children.get(1).getResultVar() + ", " + condV.getResultVar() + "\n";
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