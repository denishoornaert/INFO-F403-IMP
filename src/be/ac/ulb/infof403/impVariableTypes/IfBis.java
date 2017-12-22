package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.codeGenerator.CodeFactory;
import be.ac.ulb.infof403.parser.RuleTree;

public class IfBis extends RuleTree {
    
    public IfBis(final Elem value) {
        super(value);
    }

    @Override
    public String getResultVar() {
        final Integer id = getId();
        
        if(this._children.size() == 3) {
            // else <Code> endif
            this._children.get(1).getResultVar();
        }
        String strOutput = "br label %endelse"+id+"\n";
        strOutput += "endelse"+id+":\n";
        CodeFactory.write(strOutput);
        return "";
    }
    
}
