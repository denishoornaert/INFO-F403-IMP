package be.ac.ulb.infof403;

/**
 * Exception when there is an error in IMP code
 * 
 */
public class IMPSyntaxException extends Exception {
    
    /**
     * Default constructor
     */
    public IMPSyntaxException() { 
        super(); 
    }
    
    public IMPSyntaxException(final String message) { 
        super(message); 
    }
    
    public IMPSyntaxException(final String message, final Throwable cause) { 
        super(message, cause); 
    }
    
    public IMPSyntaxException(final Throwable cause) { 
        super(cause); 
    }
}
