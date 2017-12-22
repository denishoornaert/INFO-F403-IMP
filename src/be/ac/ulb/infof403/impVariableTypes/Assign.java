package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.codeGenerator.CodeFactory;
import be.ac.ulb.infof403.parser.RuleTree;

public class Assign extends RuleTree {
    
    public Assign(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() {
        final String resultToAsign = this._children.get(2).getResultVar();
        final String tmpOutput = "store i32 " + resultToAsign + ", i32* " + this._children.get(0).getResultVar() + "\n";
        
        CodeFactory.write(tmpOutput);
        return "";
    }
    
}