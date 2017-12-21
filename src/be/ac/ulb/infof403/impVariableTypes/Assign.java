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
        System.out.println("[DEBUG] Result: Assign");
        // TODO store and load
        final String resultToAsign = this._children.get(2).getResultVar();
        final String tmpOutput = "store i32 " + resultToAsign + ", i32* " + this._children.get(0).getResultVar() + "\n";
        /*
        store i32 %0, i32* %a
        %2 = load i32, i32* %a
        */
        
        CodeFactory.write(tmpOutput);
        return "";
    }
    
}