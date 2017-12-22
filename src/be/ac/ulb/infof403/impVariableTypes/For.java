package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.codeGenerator.CodeFactory;
import be.ac.ulb.infof403.parser.ErrorConvertToLlvm;
import be.ac.ulb.infof403.parser.RuleTree;

public class For extends RuleTree {

    public For(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() throws ErrorConvertToLlvm {
        final RuleTree forBis = this._children.get(4);
        final Integer id = forBis.getId();
        final String countVar = this._children.get(1).getResultVar();
        
//        String strOutput = "startfor"+id + ":\n";
//        String strOutput = "%tmpinc"+id + " = alloca i32\n";
//        CodeFactory.write(strOutput);
        
        final String startValue = this._children.get(3).getResultVar();
        
        String strOutput = "store i32 " + startValue +", i32* " + countVar + "\n";
        strOutput += "br label %startloop"+id+"\n";
        strOutput += "startloop"+id+":\n";
        CodeFactory.write(strOutput);
        
        
        final Integer condElem;
        final String condVar;
        if(forBis.getChildren().size() == 7) {
            condElem = 3;
        } else {
            condElem = 1;
        }
        condVar = forBis.getChildren().get(condElem).getResultVar();
        
        // Note the "="
        final String nextVar = getNextVariable();
        strOutput = nextVar + " = load i32, i32* " + countVar + "\n";
        strOutput += "%coundRes" + id + " = icmp slt i32 " + nextVar + ", " + condVar + "\n";
        strOutput += "br i1 %coundRes" + id + ", label %loop"+id+", label %endloop"+id+"\n";
        strOutput += "loop"+id+":\n";
        CodeFactory.write(strOutput);
        
        forBis.getResultVar(countVar); // Evaluate but not save
        
        return "";
    }
    
}