package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class While extends RuleTree {

    public While(final Elem value) {
        super(value);
    }

//    @Override
//    public String getRepresentation() {
//        Integer id = this.getId();
//        String varname = this._children.get(1).getLocalVariable();
//        String result = this._children.get(1).getRepresentation() + "\n";
//        result += "br i1 %"+varname+", label %if"+id+", label %endif"+id+"\n";
//        result += "if"+id+":\n";
//        result += this._children.get(3).getRepresentation() + "\n";
//        result += "br label %endif"+id+"\n";
//        result += "endif"+id+":\n";
//        return result;
//    }
    
    @Override
    public String getResultVar() {
        final Integer id = this.getId();
        return "";
    }
    
}