package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.ErrorConvertToLlvm;
import be.ac.ulb.infof403.parser.RuleTree;

public class OpAdd extends RuleTree {

    public OpAdd(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() throws ErrorConvertToLlvm {
        final String result;
        
        final String operator = this._children.get(0).getValue().getValue().toString();
        switch(operator) {
            case "+":
                result = "add";
                break;
                
            case "-":
                result = "sub";
                break;
                
            default:
                throw new ErrorConvertToLlvm(operator);
        }
        
        return result;
    }

}