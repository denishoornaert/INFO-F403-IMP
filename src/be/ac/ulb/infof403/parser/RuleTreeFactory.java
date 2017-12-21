package be.ac.ulb.infof403.parser;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.Epsilon;
import be.ac.ulb.infof403.Symbol;
import be.ac.ulb.infof403.grammar.GrammarVariable;
import be.ac.ulb.infof403.impVariableTypes.*;


/**
 * Factory
 */
public class RuleTreeFactory {
    
    public static RuleTree getRuleTree(Elem elem) {
        RuleTree result = null;
        final String ruleTreeName;
        if(elem instanceof GrammarVariable) {
            final GrammarVariable gramVar = (GrammarVariable) elem;
            ruleTreeName = gramVar.getValue();
            
        } else if(elem instanceof Epsilon) {
            // TODO
            ruleTreeName = "Epsilon";
        } else if(elem instanceof Symbol) {
            elem = (Symbol) ((Symbol) elem).clone();
            ruleTreeName = "Symbol";
            // TODO
        } else {
            return result; // TODO ERROR unknow
        }
        
        switch(ruleTreeName) {
            case "<Assign>":
                result = new Assign(elem);
                break;
                
            case "<ExprArith>":
                result = new ExprArith(elem);
                break;
                
            case "<ExprArithU>":
                result = new ExprArithU(elem);
                break;
                
            case "<ExprArithV>":
                result = new ExprArithV(elem);
                break;
                
            case "<ExprArithMul>":
                result = new ExprArithMul(elem);
                break;
                
            case "<ExprArithMulV>":
                result = new ExprArithMulV(elem);
                break;
                
            case "<ExprArithMulU>":
                result = new ExprArithMulU(elem);
                break;
                
            case "<ExprArithAtom>":
                result = new ExprArithAtom(elem);
                break;
                
            case "<OpAdd>":
                result = new OpAdd(elem);
                break;
                
            case "<OpMul>":
                result = new OpMul(elem);
                break;
                
            case "<If>":
                result = new If(elem);
                break;
                
            case "<If'>":
                result = new IfBis(elem);
                break;
                
            case "<For>":
                result = new For(elem);
                break;
                
            case "<For'>":
                result = new ForBis(elem);
                break;
                
            case "<While>":
                result = new While(elem);
                break;
                
            case "<Read>":
                result = new Read(elem);
                break;
                
            case "<Cond>":
                result = new Cond(elem);
                break;
                
            case "<CondV>":
                result = new CondV(elem);
                break;
                
            case "<CondU>":
                result = new CondU(elem);
                break;
                
            case "<CondAnd>":
                result = new CondAnd(elem);
                break;
                
            case "<CondAndV>":
                result = new CondAndV(elem);
                break;
                
            case "<CondAndU>":
                result = new CondAndU(elem);
                break;
                
            case "<Comp>":
                result = new Comp(elem);
                break;
                
            case "<Program>":
                result = new Program(elem);
                break;
                
            case "<Code>":
                result = new Code(elem);
                break;
                
            case "<Instruction>":
                result = new Instruction(elem);
                break;
                
            case "<InstList>":
                result = new InstList(elem);
                break;
                
            case "<InstList'>":
                result = new InstListBis(elem);
                break;
                
            case "<Print>":
                result = new Print(elem);
                break;
                
            case "Symbol":
                result = new VarName(elem);
                break;
                
            case "Epsilon":
                result = new EpsilonVar(elem);
                break;
                
                // TODO
                
            default:
                System.out.println("Unknown element: " + ruleTreeName);
                // TODO ERROR
                break;
                
        }
        
        return result;
    }
    
}