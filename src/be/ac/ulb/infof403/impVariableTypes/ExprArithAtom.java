package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.LexicalUnit;
import be.ac.ulb.infof403.Symbol;
import be.ac.ulb.infof403.codeGenerator.CodeFactory;
import be.ac.ulb.infof403.parser.ErrorConvertToLlvm;
import be.ac.ulb.infof403.parser.RuleTree;

public class ExprArithAtom extends RuleTree {

    public ExprArithAtom(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() throws ErrorConvertToLlvm {
        final String result;
        
        switch (this._children.size()) {
            case 1:
                // Number or Varnam
                final RuleTree exprArithAtom = this._children.get(0);
                if(exprArithAtom.getValue() instanceof Symbol &&
                        ((Symbol) exprArithAtom.getValue()).getType().equals(LexicalUnit.VARNAME)) {
                    result = getNextVariable();
                    // %2 = load i32, i32* %a
                    final String strOutput = result + " = load i32, i32* " + exprArithAtom.getResultVar() + "\n";
                    CodeFactory.write(strOutput);
                } else {
                    result = exprArithAtom.getResultVar();
                }   
                break;
                
            case 2:
                // - <ExprArithAtom>
                final String resultVar = this._children.get(1).getResultVar();
                result = getNextVariable();
                final String tmpOutput = result + " = sub i32 0, " + resultVar + "\n";
                CodeFactory.write(tmpOutput);
                break;
                
            case 3:
                // With parentheses
                result = this._children.get(1).getResultVar();
                break;
                
            default:
                result = "";
                break;
                
        }
        
        return result;
    }
    
}