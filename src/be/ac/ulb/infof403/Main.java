package be.ac.ulb.infof403;

import be.ac.ulb.infof403.grammar.Grammar;
import be.ac.ulb.infof403.llvm.LlvmFactory;
import be.ac.ulb.infof403.parser.Ll1;
import be.ac.ulb.infof403.parser.UnexpectedSymbolException;
import be.ac.ulb.infof403.scanner.ImpScanner;
import be.ac.ulb.infof403.scanner.ImpSyntaxException;
import java.io.File;
import java.io.IOException;

/**
 * Main class
 */
public class Main {
    
    private static final String DEFAULT_IMP_FILE = "./test/imp/Euclid.imp";
    private static final String DEFAULT_GRAMMAR_FILE = "./test/grammar/UnambiguousIMP.gram";
    private static final String DEFAULT_GOJS_FOLDER = "test/html/";
    private static final String DEFAULT_LATEX_FOLDER = "test/tex/";
    
    private static boolean _debug = false;
    
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
        boolean latex = false;
        String latexOutputFile = DEFAULT_LATEX_FOLDER;
        
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
                    
                case "-gojs":
                case "--gojstree":
                    gojs = true;
                    if(args.length > currentIndex+1 && !args[currentIndex+1].startsWith("-")) {
                        gojsOutputFile += args[++currentIndex];
                    } else {
                        gojsOutputFile += getFileWithoutExtension(getFileName(impFile)) + ".html";
                    }
                    break;
                    
                case "-tex":
                case "--latex":
                    latex = true;
                    if(args.length > currentIndex+1 && !args[currentIndex+1].startsWith("-")) {
                        latexOutputFile += args[++currentIndex];
                    } else {
                        latexOutputFile += getFileWithoutExtension(getFileName(impFile)) + ".tex";
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
        
        TokenList tokenList = null;
        try {
            tokenList = scan(impFile, testImpFile, printScanResult);
        } catch (IOException exception) {
            System.err.println("Error with file IMP: " + exception.getMessage());
        } catch(ImpSyntaxException exception) {
            System.err.println("Error with a token: " + exception.getMessage());
        }
        
        if(tokenList == null) {
            return;
        }
        
        final Grammar grammar = getGrammar(grammarFile);
        if(printTable) {
             grammar.printActionTable();
        }
        
        boolean validParsing = false;
        final Ll1 ll1 = new Ll1(grammar, tokenList);

        try {
            ll1.parse(_debug);
            validParsing = true;
        } catch (UnexpectedSymbolException ex) {
            System.err.println(ex.getMessage());
        }
        
        if(validParsing) {
            // Generate View
            if(latex) {
                ll1.generateLaTexParseTree(latexOutputFile);
            }

            if(gojs) {
                ll1.generateGojsParseTree(gojsOutputFile);
            }
            
            System.out.println("Syntax respected !");
            System.out.println("");
            System.out.println("Grammar: ");
            System.out.println(grammar);
            System.out.println("");
            ll1.printTransitions();
        }
        
        String result = LlvmFactory.getReadIntMethod();
        result += LlvmFactory.getPrintMethod();
        result += ll1.produiceCode();
        System.out.println("Result:\n" + result);
        
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
     * @throws java.io.IOException exception if IMP file have a problem
     * @throws be.ac.ulb.infof403.scanner.ImpSyntaxException error if there is a problem with a token
     */
    private static TokenList scan(final String impFilename, final String testOutputFile,
            final boolean printResult) throws IOException, ImpSyntaxException {
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
            System.out.println("Grammar (factorisation):");
            System.out.println(grammar);
        }
        
        grammar.renumberRule();
        if(_debug) {
            System.out.println("Grammar (RenumberRule (final)):");
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
        System.out.println("  -ps/--printscan\t\tPrint the scan result (like first part of this project)");
        System.out.println("  -gojs/--gojstree [outputFile]\tView Gojs parse tree (output file is store in " + 
                DEFAULT_GOJS_FOLDER + ")");
        System.out.println("  -tex/--latex [outputFile]\tView LaTeX parse tree (output file is store in " + 
                DEFAULT_LATEX_FOLDER + ")");
        System.out.println("  -d/--debug\t\t\tView debug messages");
    }

}
