package test.be.ac.ulb.infof403;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Class to simplify output test
 */
public abstract class AbstractTestOutput {

    protected final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    protected final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }
    
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }
    
}
