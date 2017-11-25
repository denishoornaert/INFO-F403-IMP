package test.be.ac.ulb.infof403.scanner;

import be.ac.ulb.infof403.scanner.ImpScanner;
import be.ac.ulb.infof403.scanner.ImpSyntaxException;
import java.io.IOException;
import org.junit.Test;

/**
 * Test a IMP file with tabulation
 */
public class TestTabTestScan {
    
    private static final String TAB_IMP_FILE = "./test/imp/TabTest.imp";
    
    @Test
    public void testTabScan() throws IOException, ImpSyntaxException {
        new ImpScanner(TAB_IMP_FILE, false);
        // Error if tab isn't read
    }
    
}
