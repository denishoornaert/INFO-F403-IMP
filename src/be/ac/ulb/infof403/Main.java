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
        
        
        while(args.length > currentIndex) {
            switch(args[currentIndex]) {
                
                case "--ta":
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
                    
                default:
                    System.err.println("Unknow argument (" + args[currentIndex] + ")");
                    return;
                    
            }
            
            ++currentIndex;
        }
        
        final TokenList tokenList = scan(impFile, testImpFile, printScanResult);
        final Grammar grammar = getGrammar(grammarFile);
        
        
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
     * @param grammarFilename path to grammar file
     */
    private static Grammar getGrammar(final String grammarFilename) {
        final Grammar grammar = openAndScanGrammar(grammarFilename);
        
        grammar.removeUseless();
        grammar.removeLeftRecursion();
        grammar.facorisation();
        
        return grammar;
    }
    
    /**
     * Print help message and informations to launch jar
     */
    private static void printHelp() {
        System.out.println("Command: java -jar INFO-F403-IMP.jar <grammarFile> <IMPFile> [options]");
        System.out.println("  <grammarFile>\t\t\tThe file that contains the Grammar (default: './test/Gram.gram')");
        System.out.println("  <IMPFile>\t\t\tThe file with the IMP code (default: './test/Euclid.imp')");
        System.out.println("  -h/--help\t\t\tPrint this text");
        System.out.println("  -ta/--table\t\t\tPrint the action table");
        System.out.println("  -ts/--testscan [filePath]\tTest that the scanner have the good output");
        System.out.println("  -ps/--printscan\t\tPrint the scan result");
    }

}
