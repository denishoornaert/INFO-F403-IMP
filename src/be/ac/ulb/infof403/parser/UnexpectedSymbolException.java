package be.ac.ulb.infof403.parser;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.Symbol;
import java.util.HashSet;

/**
 * Exception throw when the parser encounter a symbol that is not reachable according to the action table.
 * 
 */
public class UnexpectedSymbolException extends Exception {
    
    private static final String COLOR_RED = "\u001B[31m";
    private static final String COLOR_RESET = "\u001B[0m";
    
    protected UnexpectedSymbolException(final Symbol symb, final HashSet<Elem> elems) {
        super(generateMessage(symb, elems));
    }
    
    protected UnexpectedSymbolException(final Symbol symb, final String message) {
        super(generateMessageSymbol(symb) + "\n\t" + message);
    }
    
    private static String generateMessageSymbol(final Symbol symb) {
        final String message = "(line : " + symb.getLine() + " col :" + symb.getColumn() + 
                ")\t" + COLOR_RED + "Unexepected symbol " + "'" + symb.getValue() 
                + "' of type " + symb.getType() + COLOR_RESET;
        return message;
    }
    
    private static String generateMessage(final Symbol symb, final HashSet<Elem> elems) {
        String message = generateMessageSymbol(symb) +
                "\n\t\t\tThe expected symbol is one of the following : ";
        for (final Elem elem : elems) {
            message += elem.getValue()+" ";
        }
        return message;
    }
    
}
