package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class Program extends RuleTree {
    
    public Program(final Elem value) {
        super(value);
    }

    @Override
    public String getRepresentation() {
        // begin <Code> end 
        String result = "define i32 @main() {\n";
        result += this._children.get(1).getRepresentation();
        result += "ret i32 0\n}";
        return result;
    }
    
}
