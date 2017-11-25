package be.ac.ulb.infof403.parser;

import be.ac.ulb.infof403.TokenList;
import be.ac.ulb.infof403.grammar.Grammar;
import be.ac.ulb.infof403.view.GenerateGojsParseTree;
import be.ac.ulb.infof403.view.GenerateLaTeXParseTree;

/**
 * 
 */
public class TreeLl1 extends AbstractLl1 {
    
    private RuleTree _tree;
    
    public TreeLl1(final Grammar grammar, final TokenList tokenList) {
        super(grammar, tokenList);
    }
    
    @Override
    public void parse(final boolean debug) throws UnexpectedSymbolException {
        _tree = new RuleTree(_grammar.getInitialVariable());
        analyseTree(_tree);
        
        if(_i.hasNext()) {
            throw new UnexpectedSymbolException(_symb, "Expected end of file"); 
        }
    }
    
    public void generateGojsParseTree(final String gojsOutputFile) {
        new GenerateGojsParseTree(_tree, gojsOutputFile);
    }
    
    public void generateLaTexParseTree(final String latexOutputFile) {
        new GenerateLaTeXParseTree(_tree, latexOutputFile);
    }
    
    private void analyseTree(final RuleTree currentRuleTree) throws UnexpectedSymbolException {
        if(_i.hasNext() || _symb != null) {
            if(currentRuleTree.isGrammarVariable()) {
                _transitions.add(currentRuleTree.addRuleForSymbol(_symb).toString());
                for(final RuleTree nextRuleTree : currentRuleTree.getChildren()) {
                    analyseTree(nextRuleTree);
                }
            } else if(currentRuleTree.equalsValue(_symb)) {
                if(!_i.hasNext()) {
                    _symb = null;
                } else {
                    _symb = _i.next();
                }
            }
        }
    }
    
}
