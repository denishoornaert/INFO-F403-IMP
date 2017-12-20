package be.ac.ulb.infof403.codeGenerator;

import be.ac.ulb.infof403.parser.RuleTree;

public class CodeFactory {
    
    protected static String _generalOutput = "";
    
    private String transform(RuleTree rule) {
        return "";
    }
    
    public static String produce() {
        return "";
    }
    
    public static void write(String input) {
        _generalOutput += input;
    }
    
    public static String getGeneralOutput() {
        return _generalOutput;
    }
    
}
