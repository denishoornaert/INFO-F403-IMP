package be.ac.ulb.infof403.scanner;

/**
 * Exception when there is an error in IMP code
 * 
 */
public class ImpSyntaxException extends Exception {
    
    /**
     * Default constructor
     */
    public ImpSyntaxException() { 
        super(); 
    }
    
    public ImpSyntaxException(final String message) { 
        super(message); 
    }
    
    public ImpSyntaxException(final String message, final Throwable cause) { 
        super(message, cause); 
    }
    
    public ImpSyntaxException(final Throwable cause) { 
        super(cause); 
    }
}
