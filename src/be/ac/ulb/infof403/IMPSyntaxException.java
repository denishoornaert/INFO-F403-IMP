package be.ac.ulb.infof403;

public class IMPSyntaxException extends Exception {
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
