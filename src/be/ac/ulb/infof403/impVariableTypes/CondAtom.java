package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.codeGenerator.CodeFactory;
import be.ac.ulb.infof403.parser.ErrorConvertToLlvm;
import be.ac.ulb.infof403.parser.RuleTree;

public class CondAtom extends RuleTree {

    public CondAtom(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() throws ErrorConvertToLlvm {
        final String result;
        if(this._children.size() > 1) { // Make a "not"
            final String simpleCond = this._children.get(1).getResultVar();
            result = getNextVariable();
            // %false = add i1 %true, 1
            final String strOutput = result + " = add i1 " + simpleCond + ", 1\n";
            CodeFactory.write(strOutput);
            
        } else {
            result = this._children.get(0).getResultVar();
        }
        return result;
    }
    
}