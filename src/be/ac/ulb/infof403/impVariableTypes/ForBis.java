package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class ForBis extends RuleTree {

    public ForBis(final Elem value) {
        super(value);
    }

    @Override
    public String getRepresentation() {
        Integer id = this.getId();
        String inc = (this._children.size() == 7) ? this._children.get(1).getLocalVariable() : "1";
        Integer instructionBlockOffset = (this._children.size() == 7) ? 5 : 3;
        String result = "startfor"+id+"\n";
        result += this._children.get(instructionBlockOffset).getRepresentation()+"\n";
        result += "%inc = add i32 %i"+id+", "+inc+"\n";
        result += "store i32 %inc"+id+", i32* %tmpinc"+id+"\n";
        result += "br label %startloop"+id+"\n";
        result += "endloop"+id+":\n";
        return result;
    }
    
    @Override
    public String getResultVar() {
        
    }
    
}