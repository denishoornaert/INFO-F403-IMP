package be.ac.ulb.infof403.parser;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.Symbol;
import java.util.HashSet;

/**
 * Exception throw when the parser encounter a symbol that is not reachable according to the action table.
 * 
 */
public class UnexpectedCharacterException extends Exception {
    
    private String _message;
    
    UnexpectedCharacterException(Symbol symb, HashSet<Elem> elems){  
        super();
        generateMessage(symb, elems);
    }

    private void generateMessage(Symbol symb, HashSet<Elem> elems) {
        _message  = "(line : "+symb.getLine()+" col :"+symb.getColumn()+")\tUnexepected character "+"'"+symb.getValue()+"'";
        _message += "\n\t\t\tThe expected character is one of the following : ";
        for (Elem elem : elems) {
            _message += elem.getValue()+" ";
        }
    }
    
    @Override
    public String getMessage(){
        return _message;
    }
    
}
