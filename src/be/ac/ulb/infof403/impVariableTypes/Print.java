package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class Print extends RuleTree {

    public Print(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() {
        _generalOutput += "call void @println(i32 " + this._children.get(2).getResultVar() + ")\n";
        return "";
    }
    
}