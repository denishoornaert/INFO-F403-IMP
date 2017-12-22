package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.codeGenerator.CodeFactory;
import be.ac.ulb.infof403.parser.ErrorConvertToLlvm;
import be.ac.ulb.infof403.parser.RuleTree;

public class Cond extends RuleTree {

    public Cond(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() throws ErrorConvertToLlvm {
        final String resultVar;
        final RuleTree condV = this._children.get(1);
        final String condVResult = condV.getResultVar();
        if(!condVResult.isEmpty()) {
            final String outputResult = " = " + condV.getChildren().get(0).getResultVar() + 
                    " i1 " + this._children.get(0).getResultVar() + ", " + condVResult + "\n";
            resultVar = getNextVariable();
            CodeFactory.write(resultVar + outputResult);
            
        } else {
            resultVar = this._children.get(0).getResultVar();
        }
        
        return resultVar;
    }
    
}