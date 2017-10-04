package be.ac.ulb.infof403;

/**
 *
 * @author Detobel
 */
public class IMPSyntaxException extends Exception {
    public IMPSyntaxException() { 
        super(); 
    }
    
    public IMPSyntaxException(String message) { 
        super(message); 
    }
    
    public IMPSyntaxException(String message, Throwable cause) { 
        super(message, cause); 
    }
    
    public IMPSyntaxException(Throwable cause) { 
        super(cause); 
    }
}
