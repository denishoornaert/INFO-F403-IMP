package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class Assign extends RuleTree {
    
    public Assign(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() {
        System.out.println("[DEBUG] Result: Assign");
        // TODO store and load
        final String tmpOutput = this._children.get(0).getResultVar() + " = add i32 " + 
                this._children.get(2).getResultVar() + ", 0\n";
        _generalOutput += tmpOutput;
        return "";
    }
    
}