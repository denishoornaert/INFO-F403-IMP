package be.ac.ulb.infof403;

import be.ac.ulb.infof403.grammar.Grammar;
import be.ac.ulb.infof403.grammar.GrammarScanner;
import be.ac.ulb.infof403.scanner.ImpScanner;
import java.io.FileReader;
import java.io.IOException;

/**
 * Main class
 */
public class Main {
    
    private static final String DEFAULT_IMP_FILE = "./test/Euclid.imp";
    private static final String DEFAULT_GRAMMAR_FILE = "./test/Gram.gram";
    
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
        
        boolean printTable = false;
        final String grammarFile;
        if(args.length > 0) {
           grammarFile = args[0];
        } else {
            grammarFile = DEFAULT_GRAMMAR_FILE;
        }
        
        String testImpFile = "";
        final String impFile;
        if(args.length > 1) {
            impFile = args[1];
        } else {
            impFile = DEFAULT_IMP_FILE;
        }
        
        int currentIndex = 2;
        while(args.length > currentIndex) {
            switch(args[currentIndex]) {
                
                case "-table":
                    printTable = true;
                    break;
                
                case "-testscan":
                    if(args.length > currentIndex+1 && !args[currentIndex+1].startsWith("-")) {
                        testImpFile = args[++currentIndex];
                        
                    } else {
                        final String fileNameWitoutExt = impFile.substring(0, impFile.lastIndexOf('.'));
                        testImpFile = fileNameWitoutExt + ".out";
                    }
                    break;
                
                default:
                    System.err.println("Unknow argument (" + args[currentIndex] + ")");
                    return;
                    
            }
            
            ++currentIndex;
        }
        
        final TokenList tokenList = scan(impFile, testImpFile);
        
        
        
//        final String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
//        switch(args[0]) {
//            case "scan":
//                scan(newArgs);
//                break;
//            
//            case "grammar":
//                grammar(newArgs);
//                break;
//        }
    }
    
    private static boolean argsContainsHelp(final String[] args) {
        for(final String arg : args) {
            if(arg.equalsIgnoreCase("-help") || arg.equalsIgnoreCase("-h")) {
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
    private static TokenList scan(final String impFilename, final String testOutputFile) {
        ImpScanner impScanner = null;
        if(!testOutputFile.isEmpty()) {
            impScanner = new ImpScanner(impFilename, testOutputFile);
        } else {
            impScanner = new ImpScanner(impFilename);
        }
        return impScanner.getTokenList();
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
        System.out.println("Command: java -jar INFO-F403-IMP.jar <grammarFile> <IMPFile> [options]");
        System.out.println("");
        System.out.println("\t-help\tPrint this text");
        System.out.println("\tgrammarFile\tThe file that contains the Grammar (default: './test/Gram.gram')");
        System.out.println("\tIMPFile\tThe file with the IMP code (default: './test/Euclid.imp')");
        System.out.println("\t-table\tPrint the action table");
        System.out.println("\t-testscan [filePath]\tTest that the scanner have the good output");
    }

}
