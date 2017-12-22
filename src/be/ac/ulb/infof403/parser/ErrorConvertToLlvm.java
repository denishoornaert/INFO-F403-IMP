package be.ac.ulb.infof403.parser;

/**
 * Error when we try to convert a RuleTree to an LLVM without success
 */
public class ErrorConvertToLlvm extends Exception {
    
    public ErrorConvertToLlvm(final String symbol) {
        super("Could not convert " + symbol + " to LLVM code");
    }

}