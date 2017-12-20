package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class For extends RuleTree {

    public For(Elem value) {
        super(value);
    }
    
    @Override
    public String getRepresentation() {
        Integer id = this.getId();
        String varname = this._children.get(1).getLocalVariable();
        String result = "startfor"+id+"\n";
        result += "%tmpinc"+id+" = alloca i32\n";
        result += "store i32 "+this._children.get(1).getLocalVariable()+", i32* %tmpinc"+id+"\n";
        result += "br label %startloop"+id+"\n";
        result += "startloop"+id+":\n";
        result += "%i = load i32, i32* %tmpinc"+id+"\n";
        result += "%"+varname+" = icmp slt i32 %i"+id+", 10\n";
        result += "br i1 %"+varname+", label %loop"+id+", label %endloop"+id+"\n";
        result += "loop"+id+":\n";
        result += this._children.get(4).getRepresentation()+"\n";
        return result;
    }

}

