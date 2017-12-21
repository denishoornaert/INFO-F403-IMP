package be.ac.ulb.infof403.codeGenerator;

import be.ac.ulb.infof403.parser.RuleTree;
import be.ac.ulb.infof403.view.GenerateGojsParseTree;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CodeFactory {
    
    protected static String _generalOutput = "";
    
    // TODO still usefull ??
    private String transform(RuleTree rule) {
        return "";
    }
    
    // TODO still usefull ??
    public static String produce() {
        return "";
    }
    
    public static void write(String input) {
        _generalOutput += input;
    }
    
    public static String getGeneralOutput() {
        return _generalOutput;
    }
    
    public static void writeCodeTo(final String fileOutput) {
        writeFile(fileOutput, _generalOutput);
    }

    // TODO this is a common method with GenerateViewparseTree.java
    protected static void writeFile(final String fileOutput, final String content) {
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
