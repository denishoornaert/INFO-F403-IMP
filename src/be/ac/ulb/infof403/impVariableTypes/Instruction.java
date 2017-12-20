package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class Instruction extends RuleTree {
    
    public Instruction(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() {
        System.out.println("[DEBUG] Result: Instruction");
        this._children.get(0).getResultVar(); // Compute but not store
        return "";
    }
    
}