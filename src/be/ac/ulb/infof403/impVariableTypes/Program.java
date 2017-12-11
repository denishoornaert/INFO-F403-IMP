package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class Program extends RuleTree {
    
    public Program(final Elem value) {
        super(value);
    }

    @Override
    public String getRepresentation() {
        // TODO create main ect
        // begin <Code> end 
        return "main() {\n" + this._children.get(1).getRepresentation() + "\n}";
    }
    
}
