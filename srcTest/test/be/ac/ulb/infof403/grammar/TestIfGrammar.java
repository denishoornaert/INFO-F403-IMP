package test.be.ac.ulb.infof403.grammar;

import be.ac.ulb.infof403.LexicalUnit;
import be.ac.ulb.infof403.Symbol;
import be.ac.ulb.infof403.grammar.Grammar;
import be.ac.ulb.infof403.grammar.GrammarVariable;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestIfGrammar {
    
    private static final String IF_TEST_GRAMMAR = "./test/grammar/IfTest.gram";
    
    @Test
    public void testIfTestGrammar() {
        final Grammar grammar = Grammar.openAndScanGrammar(IF_TEST_GRAMMAR);
        optimiseGrammar(grammar);
        assertEquals(grammar.toString(), createIfTestGrammar().toString());
    }
    
    private Grammar createIfTestGrammar() {
        final GrammarVariable initGramVar = new GrammarVariable("init");
        final GrammarVariable initPrimeGramVar = new GrammarVariable("init'");
        final GrammarVariable codeGramVar = new GrammarVariable("code");
        final GrammarVariable condGramVar = new GrammarVariable("cond");
        
        final Symbol varASymbol = new Symbol(LexicalUnit.VARNAME, "a");
        final Symbol varBSymbol = new Symbol(LexicalUnit.VARNAME, "b");
        
        final Symbol ifSymbol = new Symbol(LexicalUnit.IF, "if");
        final Symbol thenSymbol = new Symbol(LexicalUnit.THEN, "then");
        final Symbol elseSymbol = new Symbol(LexicalUnit.ELSE, "else");
        final Symbol endifSymbol = new Symbol(LexicalUnit.ENDIF, "endif");
        
        /// Add rule ///
        // (6) <init>	 -> 	 if <cond> then <code> <init'> 
        initGramVar.addRule(6, ifSymbol, condGramVar, thenSymbol, codeGramVar, initPrimeGramVar);
        // (8) <init'>	 -> 	 else <code> endif 
        initPrimeGramVar.addRule(8, elseSymbol, codeGramVar, endifSymbol);
        // (10) <init'>	 -> 	 endif 
        initPrimeGramVar.addRule(10, endifSymbol);
        // (3) <code>	 -> 	 a 
        codeGramVar.addRule(3, varASymbol);
        // (4) <cond>	 -> 	 b 
        condGramVar.addRule(4, varBSymbol);
        
        final Grammar ifTestGrammar = new Grammar(initGramVar);
        ifTestGrammar.addVariables(initPrimeGramVar, codeGramVar, condGramVar);
        
        return ifTestGrammar;
    }
    
    private void optimiseGrammar(final Grammar grammar) {
        grammar.removeUseless();
        grammar.removeLeftRecursion();
        grammar.factorisation();
    }
    
    @After
    public void afterTest() {
        be.ac.ulb.infof403.grammar.Rule.resetId();
    }
    
}