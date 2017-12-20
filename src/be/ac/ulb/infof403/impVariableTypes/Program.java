package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class Program extends RuleTree {
    
    public Program(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() {
        System.out.println("[DEBUG] Result: Program");
        _generalOutput += "define i32 @main() {\n";
        this._children.get(1).getResultVar();
        _generalOutput += "ret i32 0\n}";
        return "";
    }
    
}