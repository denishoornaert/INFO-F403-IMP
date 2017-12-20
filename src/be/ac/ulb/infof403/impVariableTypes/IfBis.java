package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.parser.RuleTree;

public class IfBis extends RuleTree {
    
    public IfBis(Elem value) {
        super(value);
    }

    @Override
    public String getRepresentation() {
        Integer id = 0; //getFather id TODO
        String result = "";
        if(this._children.size() == 3) {
            // else <Code> endif
            result += this._children.get(1).getRepresentation()+"\n";
            result += "br label %endelse"+id+"\n";
            result += "endelse"+id+":\n";
        }
        return result;
    }
    
}
