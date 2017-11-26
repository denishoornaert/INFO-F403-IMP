package test.be.ac.ulb.infof403.grammar;

import be.ac.ulb.infof403.grammar.Grammar;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import test.be.ac.ulb.infof403.AbstractTestOutput;

/**
 * 
 */
public class TestActionTable extends AbstractTestOutput {
    
    private static final String ACTION_TABLE_GRAMMAR = "./test/grammar/ActionTable.gram";
    
    @Before
    @Override
    public void setUpStreams() {
        super.setUpStreams();
    }
    
    @After
    @Override
    public void cleanUpStreams() {
        super.cleanUpStreams();
        be.ac.ulb.infof403.grammar.Rule.resetId();
    }
    
    
    @Test
    public void out() {
        final Grammar grammar = Grammar.openAndScanGrammar(ACTION_TABLE_GRAMMAR);
        grammar.printActionTable();
        
        final String expectedResult = 
            "        | (  | Cst | /  | *  | -  | )  | +  | \n" +
            "S       | 1  | 1   |    |    | 1  |    |    | \n" +
            "Exp     | 2  | 2   |    |    | 2  |    |    | \n" +
            "Exp'    |    |     |    |    | 4  | 5  | 3  | \n" +
            "Prod    | 6  | 6   |    |    | 6  |    |    | \n" +
            "Prod'   |    |     | 8  | 7  | 9  | 9  | 9  | \n" +
            "Atom    | 12 | 11  |    |    | 10 |    |    | \n";
        assertEquals(expectedResult, _outContent.toString());
    }
    
}
