package be.ac.ulb.infof403.codeGenerator;

import be.ac.ulb.infof403.utils.FileController;

public class CodeFactory {
    
    protected static String _generalOutput = "";
    
    
    public static void write(String input) {
        _generalOutput += input;
    }
    
    public static String getGeneralOutput() {
        return _generalOutput;
    }
    
    public static void writeCodeTo(final String fileOutput) {
        FileController.writeFile(fileOutput, _generalOutput);
    }

}
