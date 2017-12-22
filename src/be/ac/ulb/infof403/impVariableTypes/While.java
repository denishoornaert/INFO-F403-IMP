package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.codeGenerator.CodeFactory;
import be.ac.ulb.infof403.parser.RuleTree;

public class While extends RuleTree {

    public While(final Elem value) {
        super(value);
    }

    @Override
    public String getResultVar() {
        final Integer id = getId();
        String strOutput = "br label %startloop" + id + "\n";
        strOutput += "startloop" + id + ":\n";
        CodeFactory.write(strOutput);
        
        final String condVar = this._children.get(1).getResultVar();
        strOutput = "br i1 "+condVar+", label %loop" + id + ", label %endloop" + id +"\n";
        strOutput += "loop" + id + ":\n";
        CodeFactory.write(strOutput);
        this._children.get(3).getResultVar();
        
        strOutput = "br label %startloop" + id + "\n";
        strOutput += "endloop" + id + ":\n";
        CodeFactory.write(strOutput);
        return "";
    }
    
}