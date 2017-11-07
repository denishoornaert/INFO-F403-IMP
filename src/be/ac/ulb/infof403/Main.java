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
        
        testGrammar1();
        testGrammar2();
    }
    
    //////////// DEBUG ////////////
    
    private static void testGrammar1() {
        /* -=- Grammar 1 -=-
        <init>    -> <A>
        <A>       -> b<init>
                  -> b
        */
        
        final Grammar grammar1 = new Grammar();
        
        // Define variable
        final GrammarVariable initial = new GrammarVariable("init");
        final GrammarVariable A = new GrammarVariable("A");
        
        // Define terminal
        final Symbol b = new Symbol(LexicalUnit.VARNAME, "b");
        
        grammar1.addRule(initial, A);
        grammar1.addRule(A, b, initial);
        grammar1.addRule(A, b);
        
        System.out.println("Grammar1: ");
        System.out.println(grammar1);
        System.out.println("");
    }
    
    private static void testGrammar2() {
        /* -=- Grammar 2 -=-
        <init>    -> <A>
                  -> <B>
        <A>       -> a<B>
                  -> b<init>
                  -> b
        <B>       -> <A><B>
                  -> <B>a
        <C>       -> <A><init>
                  -> b
        */
        
        final Grammar grammar2 = new Grammar();
        
        // Define variable
        final GrammarVariable initial = new GrammarVariable("init");
        final GrammarVariable A = new GrammarVariable("A");
        final GrammarVariable B = new GrammarVariable("B");
        final GrammarVariable C = new GrammarVariable("C");
        
        // Define terminal
        final Symbol a = new Symbol(LexicalUnit.VARNAME, "a");
        final Symbol b = new Symbol(LexicalUnit.VARNAME, "b");
        
        // <init>
        grammar2.addRule(initial, A);
        grammar2.addRule(initial, B);
        // <A>
        grammar2.addRule(A, a, B);
        grammar2.addRule(A, b, initial);
        grammar2.addRule(A, b);
        // <B>
        grammar2.addRule(B, A, B);
        grammar2.addRule(B, B, a);
        // <C>
        grammar2.addRule(C, A, initial);
        grammar2.addRule(C, b);
        
        System.out.println("Grammar2: ");
        System.out.println(grammar2);
        
        grammar2.removeUnproductive();
        System.out.println("Grammar2 Clean: ");
        System.out.println(grammar2);
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
