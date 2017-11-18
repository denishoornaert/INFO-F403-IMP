package be.ac.ulb.infof403.parser;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.Symbol;
import java.util.HashSet;

/**
 * Exception throw when the parser encounter a symbol that is not reachable according to the action table.
 * 
 */
public class UnexpectedCharacterException extends Exception {
    
    private static final String COLOR_RED = "\u001B[31m";
    private static final String COLOR_RESET = "\u001B[0m";
    
    protected UnexpectedCharacterException(final Symbol symb, final HashSet<Elem> elems){  
        super(generateMessage(symb, elems));
    }

    private static String generateMessage(final Symbol symb, final HashSet<Elem> elems) {
        String message = "(line : " + symb.getLine() + " col :" + symb.getColumn() + 
                ")\t" + COLOR_RED + "Unexepected character " + "'" + symb.getValue() 
                + "' of type " + symb.getType() + COLOR_RESET +
                "\n\t\t\tThe expected character is one of the following : ";
        for (final Elem elem : elems) {
            message += elem.getValue()+" ";
        }
        return message;
    }
    
}
