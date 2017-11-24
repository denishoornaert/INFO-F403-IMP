package test.be.ac.ulb.infof403.grammar;

import be.ac.ulb.infof403.TokenList;
import be.ac.ulb.infof403.grammar.Grammar;
import be.ac.ulb.infof403.parser.Ll1;
import be.ac.ulb.infof403.parser.UnexpectedCharacterException;
import be.ac.ulb.infof403.scanner.ImpScanner;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test that the grammar not success with two "begin end" programm
 */
public class TestTwiceBegin {
    
    @Rule
    public ExpectedException expected = ExpectedException.none();
    
    @Test
    public void testTwiceBegin() throws UnexpectedCharacterException {
        final Grammar grammar = Grammar.openAndScanGrammar("./test/grammar/UnambiguousIMP.gram");
        final TokenList tokenList = new ImpScanner("./test/imp/TwiceBegin.imp", false).getTokenList();
        final Ll1 ll1 = new Ll1(grammar, tokenList);
        expected.expect(be.ac.ulb.infof403.parser.UnexpectedCharacterException.class);
        ll1.stackParse(false);
    }
    
    @After
    public void afterTest() {
        be.ac.ulb.infof403.grammar.Rule.resetId();
    }
    
}
