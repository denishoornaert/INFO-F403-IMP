package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class OpAdd extends RuleTree {

    public OpAdd(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() {
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
                System.err.println("[ERR] Unknown operator '" + operator + "' for 'OpAdd'"); // TODO create custom error
                result = "";
                break;
        }
        
        return result;
    }

}