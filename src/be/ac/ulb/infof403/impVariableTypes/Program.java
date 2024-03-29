package be.ac.ulb.infof403.impVariableTypes;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.codeGenerator.CodeFactory;
import be.ac.ulb.infof403.llvm.LlvmFactory;
import be.ac.ulb.infof403.parser.ErrorConvertToLlvm;
import be.ac.ulb.infof403.parser.RuleTree;

public class Program extends RuleTree {
    
    public Program(final Elem value) {
        super(value);
    }
    
    @Override
    public String getResultVar() throws ErrorConvertToLlvm {
        CodeFactory.write(LlvmFactory.getReadIntMethod());
        CodeFactory.write(LlvmFactory.getPrintMethod());
        CodeFactory.write("define i32 @main() {\n");
        CodeFactory.write(LlvmFactory.getVariablesAllocation());
        this._children.get(1).getResultVar();
        CodeFactory.write("ret i32 0\n}");
        return "";
    }
    
}