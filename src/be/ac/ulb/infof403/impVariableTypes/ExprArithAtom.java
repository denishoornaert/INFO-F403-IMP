package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.LexicalUnit;
import be.ac.ulb.infof403.Symbol;
import be.ac.ulb.infof403.codeGenerator.CodeFactory;
import be.ac.ulb.infof403.parser.RuleTree;

public class ExprArithAtom extends RuleTree {

    public ExprArithAtom(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() {
        final RuleTree exprArithAtom = this._children.get(0);
        final String result;
        
        if(exprArithAtom.getValue() instanceof Symbol && 
                ((Symbol) exprArithAtom.getValue()).getType().equals(LexicalUnit.VARNAME)) {
            result = getNextVariable();
            // %2 = load i32, i32* %a
            final String strOutput = result + " = load i32, i32* " + exprArithAtom.getResultVar() + "\n";
            CodeFactory.write(strOutput);
        } else {
            result = exprArithAtom.getResultVar();
        }
        
        return result;
    }
    
}