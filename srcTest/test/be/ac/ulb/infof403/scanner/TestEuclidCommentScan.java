package test.be.ac.ulb.infof403.scanner;

import be.ac.ulb.infof403.scanner.ImpScanner;
import be.ac.ulb.infof403.scanner.ImpSyntaxException;
import java.io.IOException;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test Euclid IMP file with comment
 */
public class TestEuclidCommentScan {
    
    private static final String EUCLID_OUT_IMP_FILE = "./test/imp/Euclid.out";
    private static final String EUCLID_COMMENT_IMP_FILE = "./test/imp/EuclidComment.imp";
    private static final String EUCLID_COMMENT_NOT_FINISH_IMP_FILE = "./test/imp/EuclidCommentNotFinish.imp";
    
    @Rule
    public ExpectedException expected = ExpectedException.none();
    
    @Test
    public void testEuclidCommentScan() throws IOException, ImpSyntaxException {
        final ImpScanner impScanner = new ImpScanner(EUCLID_COMMENT_IMP_FILE, false);
        assertTrue(impScanner.testOutput(EUCLID_OUT_IMP_FILE));
    }
    
    @Test
    public void testEuclidCommentNotFinishScan() throws IOException, ImpSyntaxException {
        expected.expect(be.ac.ulb.infof403.scanner.ImpSyntaxException.class);
        expected.expectMessage("File finish without closing comment");
        new ImpScanner(EUCLID_COMMENT_NOT_FINISH_IMP_FILE, false);
    }
    
}
