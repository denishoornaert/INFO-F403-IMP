package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class If extends RuleTree {
    
    public If(Elem value) {
        super(value);
    }

//    @Override
//    public String getRepresentation() {
//        Integer id = this.getId();
//        String varname = this._children.get(1).getLocalVariable();
//        String result = this._children.get(1).getRepresentation() + "\n";
//        result += "br i1 %"+varname+", label %if"+id+", label %endif"+id+"\n";
//        result += "if"+id+":\n";
//        result += this._children.get(3).getRepresentation()+"\n";
//        result += "br label %endif"+id+"\n";
//        result += "endif"+id+":\n";
//        result += this._children.get(4).getRepresentation();
//        return result;
//    }
    
    @Override
    public String getResultVar() {
        final Integer id = this._children.get(4).getId();
        final String varname = this._children.get(1).getResultVar();
        String strOutput = "br i1 %"+varname+", label %if"+id+", label %endif"+id+"\n";
        strOutput += "if"+id+":\n";
        this._children.get(3).getResultVar();
        strOutput += "br label %endif"+id+"\n";
        strOutput += "endif"+id+":\n";
        _generalOutput = strOutput;
        this._children.get(4).getResultVar();
        
        return "";
    }
    
}