package be.ac.ulb.infof403.parser;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.Symbol;
import java.util.HashSet;

/**
 * Exception throw when the parser encounter a symbol that is not reachable according to the action table.
 * 
 */
public class UnexpectedCharacterException extends Exception {
    
    protected UnexpectedCharacterException(final Symbol symb, final HashSet<Elem> elems){  
        super(generateMessage(symb, elems));
    }

    private static String generateMessage(final Symbol symb, final HashSet<Elem> elems) {
        String message = "(line : " + symb.getLine() + " col :" + symb.getColumn() + 
                ")\tUnexepected character " + "'" + symb.getValue() + "'" +
                "\n\t\t\tThe expected character is one of the following : ";
        for (final Elem elem : elems) {
            message += elem.getValue()+" ";
        }
        return message;
    }
    
}
