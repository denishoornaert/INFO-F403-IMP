package be.ac.ulb.infof403;

import be.ac.ulb.infof403.grammar.Grammar;
import be.ac.ulb.infof403.grammar.GrammarScanner;
import be.ac.ulb.infof403.scanner.ImpScanner;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Main class
 */
public class Main {
    
    private static final String DEFAULT_IMP_FILE = "./test/Euclid.imp";
    
    /**
     * Main function 
     * 
     * @param args parameters given when the program is executed
     */
    public static void main(final String[] args) {
        if(args.length == 0) {
            System.err.println("Missing argument");
            printHelp();
            return;
        }
        
        final String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
        switch(args[0]) {
            case "scan":
                scan(newArgs);
                break;
            
            case "grammar":
                grammar(newArgs);
                break;
        }
    }
    
    /**
     * Scan a IMP file
     * 
     * @param args informations to scan
     */
    private static void scan(final String[] args) {
        String fileName = DEFAULT_IMP_FILE; // Default file name
        if(args.length > 0) { // If file specified
            fileName = args[0];
        }
        
        // Make test
        String testFile = "";
        if(args.length > 1 && args[1].equalsIgnoreCase("-test")) {
            if(args.length > 2) {
                testFile = args[2];
            } else {
                final String fileNameWitoutExt = fileName.substring(0, fileName.lastIndexOf('.'));
                testFile = fileNameWitoutExt + ".out";
            }
            
        }
        
        if(!testFile.isEmpty()) {
            new ImpScanner(fileName, testFile);
        } else {
            new ImpScanner(fileName);
        }
    }
    
    /**
     * Open and scan grammar file
     * 
     * @param gramFilePath the path to the grammar
     * @return The grammar object of null if not correct
     */
    private static Grammar openAndScanGrammar(final String gramFilePath) {
        Grammar result = null;
        
        boolean allOk = true;
        GrammarScanner gramScanner = null;
        final FileReader file;
        try {
            file = new FileReader(gramFilePath);
            gramScanner = new GrammarScanner(file);

        } catch (IOException exception) {
            System.err.println("Error with grammar file: " + exception.getMessage());
            allOk = false;
        }
        
        if(allOk && gramScanner != null) {
            result = readGrammar(gramScanner);
        }
        
        return result;
    }
    
    private static Grammar readGrammar(final GrammarScanner gramScanner) {
        Symbol symbol = null;
        try {
            while(symbol == null || symbol.getType() != LexicalUnit.EOS) {
                symbol = gramScanner.nextToken();
            }
        } catch (IOException ex) {
            System.err.println("Bug with token Grammar flex: " + ex.getMessage());
        }
        
        return GrammarScanner.getGrammar();
    }
    
    /**
     * optimize grammar and scan it
     * 
     * @param args arguments
     */
    private static void grammar(final String[] args) {
        String gramFileName = "./test/Gram.gram";
        if(args.length >= 1) {
            gramFileName = args[0]; 
        }
        
        final Grammar grammar = openAndScanGrammar(gramFileName);
        
        boolean removeUseless = true;
        boolean factorisation = true;
        for (int i = 1; i < args.length; ++i) {
            switch(args[i]) {
                case "-sru":
                case "-sremoveuseless":
                    removeUseless = false;
                    break;
                    
                case "-sfact":
                case "-sfactorisation":
                    factorisation = false;
                    break;
                    
                case "-":
                    break;
                    
            }
        }
        
        if(removeUseless) {
            grammar.removeUseless();
        }
        
        if(factorisation) {
            grammar.facorisation();
        }
    }
    
    /**
     * Print help message and informations to launch jar
     */
    private static void printHelp() {
        System.out.println("Command: java -jar INFO-F403-IMP.jar <scan/grammar> [options]");
        System.out.println("");
        System.out.println("--- Options Scan ---");
        System.out.println("  > java -jar INFO-F403-IMP.jar scan [inputFile] [outputFile] [-test]");
        System.out.println("  \tinputFile\tThe file with the IMP code (default: './test/Euclid.imp')");
        System.out.println("  \toutputFile\tExpected output of IMP scan");
        System.out.println("  \t-test\t\tAutomaticaly test that output is equals to the output system");
        System.out.println("");
        System.out.println("--- Options Grammar ---");
        System.out.println("  > java -jar INFO-F403-IMP.jar grammar <grammarFile> [options]");
        System.out.println("  \tgrammarFile\tThe file that contains the Grammar (default: './test/Gram.gram')");
        System.out.println("  \t-sremoveuseless\tSkip the remove useless variable");
        System.out.println("  \t-sfactorisation\tSkip the factorise of grammar");
        System.out.println("  \t-test\t\tTemporary test grammar"); // TODO change when code is finish
    }

}
