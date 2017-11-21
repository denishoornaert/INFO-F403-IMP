package be.ac.ulb.infof403.view;

import be.ac.ulb.infof403.parser.RuleTree;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 */
public class GenerateGojsParseTree {
    
    private static final String HEADER_GOJS = "headerGojs.html";
    private static final String FOOTER_GOJS = "footerGojs.html";
    private static final String ASSET_FOLDER = "assets";
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
    
    private String getFooter() {
        return readFile(ASSET_FOLDER + File.separator + FOOTER_GOJS);
    }
    
    private String getHeader() {
        return readFile(ASSET_FOLDER + File.separator + HEADER_GOJS);
    }
    
    private String readFile(final String fileName) {
        String result = "";
        try {
            final FileReader fileReader = new FileReader(fileName);
            final BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            while((line = bufferedReader.readLine()) != null) {
                result += line + "\n";
            }   
            bufferedReader.close();
        } catch(IOException ex) {
            System.out.println("Unable to open file '" + fileName + "'");                
        }
        
        return result;
    }
    
    private void writeFile(final String fileOutput, final String content) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileOutput, "UTF-8");
            writer.print(content);
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(GenerateGojsParseTree.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(writer != null) {
            writer.close();
        }
    }
    
}
