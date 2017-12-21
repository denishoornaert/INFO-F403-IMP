package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.codeGenerator.CodeFactory;
import be.ac.ulb.infof403.parser.RuleTree;

public class CondAnd extends RuleTree {

    public CondAnd(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() {
        System.out.println("[DEBUG] Result: CondAnd");
        final String resultVar;
        final RuleTree condAndV = this._children.get(1);
        final String condAndVResult = condAndV.getResultVar();
        if(!condAndVResult.isEmpty()) {
            final String outputResult = " = " + condAndV.getChildren().get(0).getResultVar() + 
                    " i1 " + this._children.get(0).getResultVar() + ", " + condAndVResult + "\n";
            resultVar = getNextVariable();
            CodeFactory.write(resultVar + outputResult);
            
        } else {
            resultVar = this._children.get(0).getResultVar();
        }
        
        return resultVar;
    }
    
}