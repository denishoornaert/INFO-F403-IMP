package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class While extends RuleTree {

    public While(final Elem value) {
        super(value);
    }

    @Override
    public String getResultVar() {
        _generalOutput += "br label %startloop\n";
        _generalOutput += "startloop:\n";
        String condVar = this._children.get(1).getResultVar();
        _generalOutput += "br i1 "+condVar+", label %loop, label %endloop\n";
        _generalOutput += "loop:\n";
        this._children.get(3).getResultVar();
        _generalOutput += "br label %startloop\n";
        _generalOutput += "endloop:\n";
        return "";
    }
    
}