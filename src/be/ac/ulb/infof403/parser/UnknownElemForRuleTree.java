package be.ac.ulb.infof403.parser;

import be.ac.ulb.infof403.Elem;

/**
 * Error when we try to instance a RuleTree with an unknow element
 */
public class UnknownElemForRuleTree extends Exception {
    
    protected UnknownElemForRuleTree(final Elem elem) {
        super("No RuleTree for element: " + elem);
    }
    
    protected UnknownElemForRuleTree(final String ruleTreeName) {
        super("Could not found RuleTree with name: " + ruleTreeName);
    }
    
    
}