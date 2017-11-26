package test.be.ac.ulb.infof403;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Class to simplify output test
 */
public abstract class AbstractTestOutput {

    protected final ByteArrayOutputStream _outContent = new ByteArrayOutputStream();
    protected final ByteArrayOutputStream _errContent = new ByteArrayOutputStream();
    
    public void setUpStreams() {
        System.setOut(new PrintStream(_outContent));
        System.setErr(new PrintStream(_errContent));
    }
    
    public void cleanUpStreams() {
        System.setOut(System.out);
        System.setErr(System.err);
    }
    
}