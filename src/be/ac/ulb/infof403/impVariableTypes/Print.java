package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.codeGenerator.CodeFactory;
import be.ac.ulb.infof403.parser.ErrorConvertToLlvm;
import be.ac.ulb.infof403.parser.RuleTree;

public class Print extends RuleTree {

    public Print(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() throws ErrorConvertToLlvm {
        final String tmpVar = getNextVariable();
        // %2 = load i32, i32* %a
        String strOutput = tmpVar + " = load i32, i32* " + this._children.get(2).getResultVar() + "\n";
        strOutput += "call void @println(i32 " + tmpVar + ")\n";
        CodeFactory.write(strOutput);
        return "";
    }
    
}