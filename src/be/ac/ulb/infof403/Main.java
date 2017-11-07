package be.ac.ulb.infof403;

import be.ac.ulb.infof403.grammar.Grammar;
import be.ac.ulb.infof403.grammar.GrammarVariable;
import be.ac.ulb.infof403.scanner.ImpScanner;
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
     * Optimise grammar and scan it
     * 
     * @param args 
     */
    private static void grammar(final String[] args) {
        // Currently only test structure:
        
        /*
        <initial> -> <A>
        <A>       -> b<initial>
                  -> b
        */
        
        final Grammar grammar = new Grammar();
        
        // Define variable
        final GrammarVariable initial = new GrammarVariable();
        final GrammarVariable A = new GrammarVariable();
        
        // Define terminal
        final Symbol a = new Symbol(LexicalUnit.VARNAME, "a");
        final Symbol b = new Symbol(LexicalUnit.VARNAME, "b");
        
        grammar.addRule(initial, A);
        grammar.addRule(A, b, initial);
        grammar.addRule(A, b);
        
        System.out.println("Grammar: ");
        System.out.println(grammar);
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
        System.out.println("--- Options Grammar ---");
        System.out.println("  > java -jar INFO-F403-IMP.jar grammar [-test]");
        System.out.println("  \t-test\t\tTemporary test grammar"); // TODO change when code is finish
    }

}
