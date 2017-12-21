package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.codeGenerator.CodeFactory;
import be.ac.ulb.infof403.parser.RuleTree;

public class If extends RuleTree {
    
    public If(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() {
        final Integer id = this._children.get(4).getId();
        final String varname = this._children.get(1).getResultVar();
        String strOutput = "br i1 "+varname+", label %if"+id+", label %endif"+id+"\n";
        strOutput += "if"+id+":\n";
        CodeFactory.write(strOutput);
        this._children.get(3).getResultVar();
        strOutput = "br label %endif"+id+"\n";
        strOutput += "endif"+id+":\n";
        CodeFactory.write(strOutput);
        this._children.get(4).getResultVar();
        
        return "";
    }
    
}