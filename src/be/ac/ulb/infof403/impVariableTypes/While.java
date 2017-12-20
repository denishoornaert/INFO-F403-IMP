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
        CodeFactory.write("br label %startloop\n");
        CodeFactory.write("startloop:\n");
        String condVar = this._children.get(1).getResultVar();
        CodeFactory.write("br i1 "+condVar+", label %loop, label %endloop\n");
        CodeFactory.write("loop:\n");
        this._children.get(3).getResultVar();
        CodeFactory.write("br label %startloop\n");
        CodeFactory.write("endloop:\n");
        return "";
    }
    
}