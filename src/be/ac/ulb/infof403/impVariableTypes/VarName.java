package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.Symbol;
import be.ac.ulb.infof403.parser.RuleTree;

public class VarName extends RuleTree {
    
    public VarName(final Elem elem) {
        super(elem);
    }
    
    @Override
    public String getResultVar() {
        String result = "";
        if(this._value != null && this._value instanceof Symbol) {
            final Symbol symbValue = (Symbol) _value;
            
            switch (symbValue.getType()) {
                
                case NUMBER:
//                    result = getNextVariable();
//                    CodeFactory.write(result + " = add i32 " + this._value.getValue().toString() + ", 0\n");
                    result = this._value.getValue().toString();
                    break;
                    
                case VARNAME:
                    result = "%" + this._value.getValue().toString();
                    break;
                    
                default:
                    result = this._value.getValue().toString();
                    break;
            }
        }
        return result;
    }
    
}