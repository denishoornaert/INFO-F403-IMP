package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class Comp extends RuleTree {

    public Comp(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() {
        final String result;
        final String operator = this._children.get(0).getValue().getValue().toString().trim();
        switch(operator) {
            case "=":
                result = "eq";
                break;
                
            case ">=":
                result = "sge";
                break;
                
            case ">":
                result = "sgt";
                break;
                
            case "<=":
                result = "sle";
                break;
                
            case "<":
                result = "slt";
                break;
                
            case "<>":
                result = "ne";
                break;
                
            default:
                System.err.println("[ERR] Unknown operator '" + operator + "' for 'Comp'"); // TODO create custom error
                result = "";
                break;
        }
        
        return result;
    }
    
}