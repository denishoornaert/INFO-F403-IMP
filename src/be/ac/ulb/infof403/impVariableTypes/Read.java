package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.codeGenerator.CodeFactory;
import be.ac.ulb.infof403.parser.RuleTree;

public class Read extends RuleTree {

    public Read(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() {
        final String newVariable = getNextVariable();
        String strOutput = newVariable + " = call i32 @readInt()\n";
        strOutput += "store i32 " + newVariable + ", i32* " + this._children.get(2).getResultVar() + "\n";
        
        CodeFactory.write(strOutput);
        return "";
    }
    
}