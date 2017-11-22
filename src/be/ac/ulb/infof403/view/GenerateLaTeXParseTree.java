package be.ac.ulb.infof403.view;

import be.ac.ulb.infof403.parser.RuleTree;
import java.io.File;

/**
 * 
 */
public class GenerateLaTeXParseTree extends GenerateViewParseTree {
    
    private static final String HEADER_LATEX = "headerLaTeX.tex";
    private static final String FOOTER_LATEX = "footerLaTeX.tex";
    
    private int nbrChildren = 0;
    
    public GenerateLaTeXParseTree(final RuleTree tree, final String outputFile) {
        String result = getHeader();
        result += "\\" + generateTree(tree, 0) + ";";
        result += getFooter();
        writeFile(outputFile , result);
        
        System.out.println("/!\\ Warning: LaTeX generator use Tikz and doesn't work with big tree !");
    }
    
    private String generateTree(final RuleTree tree, final int profondeur) {
        String result = "node {" + tree.getValue().getValue().toString() + "}\n";
        
        if(!tree.getChildren().isEmpty()) {
            for(final RuleTree treeElement : tree.getChildren()) {
                result += " child {\n" + generateTree(treeElement, profondeur+1) + "}\n";
                ++nbrChildren;
            }
        }
        
        return result;
    }
    
    
    @Override
    protected String getFooter() {
        return readFile(ASSET_FOLDER + File.separator + FOOTER_LATEX);
    }
    
    @Override
    protected String getHeader() {
        return readFile(ASSET_FOLDER + File.separator + HEADER_LATEX);
    }
    
}
