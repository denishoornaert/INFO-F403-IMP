package be.ac.ulb.infof403.parser;

import be.ac.ulb.infof403.Symbol;

/**
 * Exception throw when the parser encounter a symbol that is not reachable according to the action table.
 * 
 */
public class UnexpectedCharacterException extends Exception {
    
    private String _message;
    
    UnexpectedCharacterException(Symbol symb){  
        super();
        generateMessage(symb);
    }

    private void generateMessage(Symbol symb) {
        _message = "(line : "+symb.getLine()+" col :"+symb.getColumn()+")\tUnexepected character "+"'"+symb.getValue()+"'";
    }
    
    @Override
    public String getMessage(){
        return _message;
    }
    
}
