package test.be.ac.ulb.infof403.grammar;

import be.ac.ulb.infof403.TokenList;
import be.ac.ulb.infof403.grammar.Grammar;
import be.ac.ulb.infof403.parser.StackLl1;
import be.ac.ulb.infof403.parser.TreeLl1;
import be.ac.ulb.infof403.parser.UnexpectedCharacterException;
import be.ac.ulb.infof403.scanner.ImpScanner;
import be.ac.ulb.infof403.scanner.ImpSyntaxException;
import java.io.IOException;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test that the grammar not success with two "begin end" programm
 */
public class TestTwiceBegin {
    
    private static final String GRAMMAR_PATH = "./test/grammar/UnambiguousIMP.gram";
    private static final String IMP_PATH = "./test/imp/TwiceBegin.imp";
    
    @Rule
    public ExpectedException expected = ExpectedException.none();
    
    
    @Test
    public void testTwiceBeginStackParsing() throws UnexpectedCharacterException, IOException, ImpSyntaxException {
        final Grammar grammar = Grammar.openAndScanGrammar(GRAMMAR_PATH);
        final TokenList tokenList = new ImpScanner(IMP_PATH, false).getTokenList();
        final StackLl1 ll1 = new StackLl1(grammar, tokenList);
        expected.expect(be.ac.ulb.infof403.parser.UnexpectedCharacterException.class);
        ll1.parse(false);
    }
    
    @Test
    public void testTwiceBeginTreeParsing() throws UnexpectedCharacterException, IOException, ImpSyntaxException {
        final Grammar grammar = Grammar.openAndScanGrammar(GRAMMAR_PATH);
        final TokenList tokenList = new ImpScanner(IMP_PATH, false).getTokenList();
        final TreeLl1 ll1 = new TreeLl1(grammar, tokenList);
        expected.expect(be.ac.ulb.infof403.parser.UnexpectedCharacterException.class);
        ll1.parse(false);
    }
    
    @After
    public void afterTest() {
        be.ac.ulb.infof403.grammar.Rule.resetId();
    }
    
}
