package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.codeGenerator.CodeFactory;
import be.ac.ulb.infof403.parser.RuleTree;

public class ForBis extends RuleTree {

    public ForBis(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar(String countVar) {
        final Integer id = this.getId();
        
        final String inc;
        final Integer instructionBlockOffset;
        
        if(this._children.size() == 7) {
            inc = this._children.get(1).getResultVar();
            instructionBlockOffset = 5;
        } else {
            inc = "1";
            instructionBlockOffset = 3;
        }
        
//        _generalOutput += "startfor"+id+":\n";
        this._children.get(instructionBlockOffset).getResultVar();
        final String nextVar = getNextVariable();
        String strOutput = nextVar + " = load i32, i32* " + countVar + "\n";
        strOutput += "%inc" + id + " = add i32 " + nextVar + ", " + inc + "\n";
        strOutput += "store i32 %inc"+id+", i32* " + countVar + "\n";
        strOutput += "br label %startloop"+id+"\n";
        strOutput += "endloop"+id+":\n";
        CodeFactory.write(strOutput);
        
        return "";
    }
    
}