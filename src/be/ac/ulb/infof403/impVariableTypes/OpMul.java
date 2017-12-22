package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class OpMul extends RuleTree {

    public OpMul(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() {
        final String result;
        
        final String operator = this._children.get(0).getValue().getValue().toString();
        switch(operator) {
            case "*":
                result = "mul";
                break;
                
            case "/":
                result = "sdiv";
                break;
                
            default:
                System.err.println("[ERR] Unknown operator " + operator + " for 'OpMul'"); // TODO create custom error
                result = "";
                break;
        }
        
        return result;
    }
    
}

