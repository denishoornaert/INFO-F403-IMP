package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class Read extends RuleTree {

    public Read(Elem value) {
        super(value);
    }

    @Override
    public String getRepresentation() {
        String targetVariable = this._children.get(2).getLocalVariable();
        String result = targetVariable+" = call void @readInt()";
        return result;
    }
    
}

