package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.Epsilon;
import be.ac.ulb.infof403.parser.RuleTree;

public class InstListBis extends RuleTree {

    public InstListBis(final Elem value) {
        super(value);
    }

    @Override
    public String getRepresentation() {
        String result = "";
        System.out.println("InstListBis value: " + this._value);
        
        if(!(this._value instanceof Epsilon)) {
            // ; <InstList> 
            result = "\n" + this._children.get(0).getRepresentation();
        }
        return result;
    }

}

