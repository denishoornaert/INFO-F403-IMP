package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.codeGenerator.CodeFactory;
import be.ac.ulb.infof403.parser.RuleTree;

public class SimpleCond extends RuleTree {

    public SimpleCond(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() {
        final String elem1 = this._children.get(0).getResultVar();
        final String elem2 = this._children.get(2).getResultVar();
        final String condition = this._children.get(1).getResultVar();
        final String nextVar = getNextVariable();
        String strOutput = nextVar + " = icmp " + condition + " i32 " + elem1 + ", " + elem2 + "\n";
        CodeFactory.write(strOutput);
        
        return nextVar;
    }
    
}