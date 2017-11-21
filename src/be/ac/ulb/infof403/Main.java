package be.ac.ulb.infof403;

import be.ac.ulb.infof403.grammar.Grammar;
import be.ac.ulb.infof403.parser.Ll1;
import be.ac.ulb.infof403.parser.UnexpectedCharacterException;
import be.ac.ulb.infof403.scanner.ImpScanner;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class
 */
public class Main {
    
    private static final String DEFAULT_IMP_FILE = "./test/imp/Euclid.imp";
    private static final String DEFAULT_GRAMMAR_FILE = "./test/grammar/UnambiguousIMP.gram";
    private static final String DEFAULT_GOJS_FOLDER = "./test/html/";
    
    private static boolean _debug = false;
    private static boolean _stackParsing = false;
    
    /**
     * Main function 
     * 
     * @param args parameters given when the program is executed
     */
    public static void main(final String[] args) {
        if(argsContainsHelp(args)) {
            printHelp();
            return;
        }
        int currentIndex = 0;
        
        boolean printTable = false;
        final String grammarFile;
        if(args.length > 0 && !(args[currentIndex].startsWith("-"))) {
           grammarFile = args[currentIndex];
           ++currentIndex;
        } else {
            grammarFile = DEFAULT_GRAMMAR_FILE;
        }
        
        String testImpFile = "";
        boolean printScanResult = false;
        final String impFile;
        if(args.length > 1 && !(args[currentIndex].startsWith("-"))) {
            impFile = args[currentIndex];
            ++currentIndex;
        } else {
            impFile = DEFAULT_IMP_FILE;
        }
        
        // Default arguments
        boolean gojs = false;
        String gojsOutputFile = DEFAULT_GOJS_FOLDER;
        
        while(args.length > currentIndex) {
            switch(args[currentIndex]) {
                
                case "-ta":
                case "--table":
                    printTable = true;
                    break;
                
                case "-ts":
                case "--testscan":
                    if(args.length > currentIndex+1 && !args[currentIndex+1].startsWith("-")) {
                        testImpFile = args[++currentIndex];
                        
                    } else {
                        final String fileNameWitoutExt = impFile.substring(0, impFile.lastIndexOf('.'));
                        testImpFile = fileNameWitoutExt + ".out";
                    }
                    break;
                
                case "-ps":
                case "--printscan":
                    printScanResult = true;
                    break;
                    
                case "-sp":
                case "--stackparsing":
                    _stackParsing = true;
                    break;
                    
                case "-gojs":
                case "--gojstree":
                    gojs = true;
                    if(args.length > currentIndex+1 && !args[currentIndex+1].startsWith("-")) {
                        gojsOutputFile += args[++currentIndex];
                    } else {
                        gojsOutputFile += getFileWithoutExtension(getFileName(impFile)) + ".html";
                    }
                    break;
                    
                case "-d":
                case "--debug":
                    _debug = true;
                    break;
                    
                default:
                    System.err.println("Unknow argument (" + args[currentIndex] + ")");
                    return;
                    
            }
            
            ++currentIndex;
        }
        
        final TokenList tokenList = scan(impFile, testImpFile, printScanResult);
        final Grammar grammar = getGrammar(grammarFile);
        if(printTable) {
             grammar.printActionTable();
        }
        
        boolean validParsing = false;
        final Ll1 l = new Ll1(grammar, tokenList);
        try {
            if(_stackParsing) {
                l.stackParse(_debug);
            } else {
                l.treeParse(gojs, gojsOutputFile);
                openBrowser(gojsOutputFile);
            }
            validParsing = true;
        } catch (UnexpectedCharacterException ex) {
            System.err.println(ex.getMessage());
        }
        
        if(validParsing) {
            System.out.println("Syntax respected !");
            System.out.println("");
            System.out.println("Grammar: ");
            System.out.println(grammar);
            System.out.println("");
            l.printTransitions();
        }
        
    }
    
    private static void openBrowser(String url) {
        // TODO check on Windows
        url = "file://"+System.getProperty("user.dir")+"/"+url;
        try {
            Desktop.getDesktop().browse(new URL(url).toURI());
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static String getFileName(final String filePath) {
        return new File(filePath).getName();
    }
    
    private static String getFileWithoutExtension(final String fileName) {
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }
    
    private static boolean argsContainsHelp(final String[] args) {
        for(final String arg : args) {
            if(arg.equalsIgnoreCase("--help") || arg.equalsIgnoreCase("-h")) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Scan a IMP file
     * 
     * @param impFilename file with imp source code
     * @param testImpFile expeted output file for imp analyse
     */
    private static TokenList scan(final String impFilename, final String testOutputFile,
            final boolean printResult) {
        final ImpScanner impScanner;
        if(!testOutputFile.isEmpty()) {
            impScanner = new ImpScanner(impFilename, testOutputFile, printResult);
        } else {
            impScanner = new ImpScanner(impFilename, printResult);
        }
        return impScanner.getTokenList();
    }
    
    /**
     * optimize grammar and scan it
     * 
     * @param grammarFilename path to grammar file
     */
    private static Grammar getGrammar(final String grammarFilename) {
        final Grammar grammar = Grammar.openAndScanGrammar(grammarFilename);
        
        if(_debug) {
            System.out.println("Grammar:");
            System.out.println(grammar);
        }
        
        grammar.removeUseless();
        if(_debug) {
            System.out.println("Grammar (removeUseless):");
            System.out.println(grammar);
        }
        
        grammar.removeLeftRecursion();
        if(_debug) {
            System.out.println("Grammar (removeLeftRecursion):");
            System.out.println(grammar);
        }
        
        grammar.factorisation();
        if(_debug) {
            System.out.println("Grammar (factorisation (final)):");
            System.out.println(grammar);
        }
        return grammar;
    }
    
    /**
     * Print help message and informations to launch jar
     */
    private static void printHelp() {
        System.out.println("Command: java -jar INFO-F403-IMP.jar <grammarFile> <IMPFile> [options]");
        System.out.println("  <grammarFile>\t\t\tThe file that contains the Grammar (default: '" + DEFAULT_GRAMMAR_FILE + "')");
        System.out.println("  <IMPFile>\t\t\tThe file with the IMP code (default: '" + DEFAULT_IMP_FILE + "')");
        System.out.println("  -h/--help\t\t\tPrint this text");
        System.out.println("  -ta/--table\t\t\tPrint the action table");
        System.out.println("  -ts/--testscan [filePath]\tTest that the scanner have the good output");
        System.out.println("  -ps/--printscan\t\tPrint the scan result");
        System.out.println("  -sp/--stackparsing\t\tParse IMP file with stack (and not a tree)");
        System.out.println("  -gojs/--gojstree [outputFile]\tView Gojs parse tree (output file is store in " + 
                DEFAULT_GOJS_FOLDER + ")");
        System.out.println("  -d/--debug\t\t\tView debug messages");
    }

}
