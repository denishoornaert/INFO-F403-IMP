package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class Read extends RuleTree {

    public Read(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() {
        final String strOutput = this._children.get(2).getResultVar() + " = call i32 @readInt()\n";
        _generalOutput += strOutput;
        return "";
    }
    
}