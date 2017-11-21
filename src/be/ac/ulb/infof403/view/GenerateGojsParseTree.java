package be.ac.ulb.infof403.view;

import be.ac.ulb.infof403.parser.RuleTree;
import java.io.File;

/**
 * 
 */
public class GenerateGojsParseTree extends GenerateViewParseTree {
    
    private static final String HEADER_GOJS = "headerGojs.html";
    private static final String FOOTER_GOJS = "footerGojs.html";
    private static final String BG_COLOR = "#ccc";
    private static final String BORDER_COLOR = "black";
    
    private int _counter;
    
    public GenerateGojsParseTree(final RuleTree tree, final String outputFile) {
        _counter = 0;
        
        String result = getHeader();
        result += "var nodeDataArray = [" + generateTree(tree, -1) + "]";
        result += getFooter();
        writeFile(outputFile , result);
    }
    
    private String generateTree(final RuleTree tree, final int parent) {
        String result = getJsRuleTree(tree, ++_counter, parent);
        final int currentKey = _counter;
        
        for(final RuleTree treeElement : tree.getChildren()) {
            result += generateTree(treeElement, currentKey);
        }
        
        return result;
    }
    
    private String getJsRuleTree(final RuleTree tree, final int counter, final int parent) {
        return "{ key: " + counter + ", text: \"" + tree.getValue().getValue().toString() + "\", "
                + "fill: \"" + BG_COLOR + "\", stroke: \"" + BORDER_COLOR + "\"" + 
                (parent > 0 ? (", parent: " +  parent) : "") + " },";
    }
    
    @Override
    protected String getFooter() {
        return readFile(ASSET_FOLDER + File.separator + FOOTER_GOJS);
    }
    
    @Override
    protected String getHeader() {
        return readFile(ASSET_FOLDER + File.separator + HEADER_GOJS);
    }
    
}
