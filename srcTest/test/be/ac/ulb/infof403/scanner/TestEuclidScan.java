package test.be.ac.ulb.infof403.scanner;

import be.ac.ulb.infof403.SymbolTable;
import be.ac.ulb.infof403.scanner.ImpScanner;
import be.ac.ulb.infof403.scanner.ImpSyntaxException;
import java.io.IOException;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * 
 */
public class TestEuclidScan {
    
    private static final String EUCLID_IMP_FILE = "./test/imp/Euclid.imp";
    private static final String FIBONACCI_IMP_FILE = "./test/imp/Fibonacci.imp";
    
    private static final String EUCLID_OUT_FILE = "./test/imp/Euclid.out";
    private static final String FIBONACCI_OUT_FILE = "./test/imp/Fibonacci.out";
    
    @Rule
    public ExpectedException expected = ExpectedException.none();
    
    @After
    public void afterTest() {
        SymbolTable.reset();
    }
    
    @Test
    public void testEuclidScan() throws IOException, ImpSyntaxException {
        final ImpScanner impScanner = new ImpScanner(EUCLID_IMP_FILE, EUCLID_OUT_FILE, false);
        assertTrue(impScanner.testOutput(EUCLID_OUT_FILE));
    }
    
    @Test
    public void testFailEuclidOutput() throws IOException, ImpSyntaxException {
        expected.expect(be.ac.ulb.infof403.scanner.ImpSyntaxException.class);
        expected.expectMessage("The expected output is not equals to the current output");
        new ImpScanner(FIBONACCI_IMP_FILE, EUCLID_OUT_FILE, false);
    }
    
    @Test
    public void testFailEuclidScan() throws IOException, ImpSyntaxException {
        expected.expect(be.ac.ulb.infof403.scanner.ImpSyntaxException.class);
        expected.expectMessage("The expected output is not equals to the current output");
        new ImpScanner(EUCLID_IMP_FILE, FIBONACCI_OUT_FILE, false);
    }
    
}
