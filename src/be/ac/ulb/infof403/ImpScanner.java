package be.ac.ulb.infof403;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

/**
 * Scan an IMP file and (option) check if the output is valid
 */
public class ImpScanner {
    
    private TokenList _tokens;
    private SymbolTable _table;
    
    /**
     * Constructor 
     * 
     * @param fileName name of the IMP file which must be scan
     */
    public ImpScanner(final String fileName) {
        this(fileName, "");
    }
    
    /**
     * Constructor
     * 
     * @param fileName name of the IMP file which must be scan
     * @param testFileName file name of that contain the expected output
     */
    public ImpScanner(final String fileName, final String testFileName) {
        _tokens = new TokenList();
        _table = new SymbolTable();
        if(openAndInitScannerImpFile(fileName)) {
            printResult();

            if(!testFileName.isEmpty()) {
                testOutput(testFileName);
            }
        }
    }
    
    
    /**
     * Open and initialise the scanner of the IMP file
     * 
     * @param fileName the IMP filename
     * @return True if all is ok, false if there are an error when file is opening
     * or when a symbol is not good read
     */
    private boolean openAndInitScannerImpFile(final String fileName) {
        Boolean allOk = true;
        final FileReader file;
        try {
            file = new FileReader(fileName);
            final Scanner scanner = new Scanner(file);

            readSymbol(scanner);

        } catch (IOException ex) {
            System.err.println("Error with file IMP: " + ex.getMessage());
            allOk = false;
        } catch(ImpSyntaxException exception) {
            System.err.println("Error with a token: " + exception.getMessage());
            allOk = false;
        }
        
        return allOk;
    }
    
    
    /**
     * Print the result of the scan
     */
    private void printResult() {
        System.out.println(_tokens);
        System.out.println("\nIdentifiers");
        System.out.println(_table);
    }
    

    /**
     * Read all symbol of the IMP File
     * 
     * @param scanner the scanner which will read the file
     * @throws IOException if there is file problem
     * @throws ImpSyntaxException if there is an IMP syntax error
     */
    private void readSymbol(final Scanner scanner) throws IOException, ImpSyntaxException {
        Symbol symbol = null;
        while(symbol == null || symbol.getType() != LexicalUnit.EOS) {
            if(symbol != null) {
                _tokens.add(symbol);
                if (symbol.getType() == LexicalUnit.VARNAME) {
                    _table.put(symbol);
                }
            }
            
            symbol = scanner.nextToken();
        }
    }
    
    
    /**
     * Open an output file and compare with the current result
     * 
     * @param testFile the filename that contains the expeted output
     */
    private void testOutput(final String testFile) {
        final File file;
        try {
            file = new File(testFile);
            final List<String> allLines = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
            if(checkSameOutput(allLines)) {
                System.out.println("The output is equivalent to the test file: " + testFile);
            }

        } catch (IOException ex) {
            System.err.println("Erreur with test file: " + ex.getMessage());
        }
    }
    
    /**
     * Check that the current IMP file have the good output
     * 
     * @param testLineStr list of expected output
     * @return True if all is ok
     */
    private boolean checkSameOutput(final List<String> testLineStr) {
        return checkSameTokenOutput(testLineStr) && checkSameTableOutput(testLineStr);
    }
    
    /**
     * Check that the current IMP file have the same token that the expected output
     * 
     * @param testLineStr list of expected output
     * @return True if all is ok
     */
    private boolean checkSameTokenOutput(final List<String> testLineStr) {
        boolean allOk = true;
        final String[] strSplitToken = _tokens.toString().split("\n");
        int i = 0;
        for(final String tokenLine : strSplitToken) {
            final String goodResult = testLineStr.get(i++);
            
            final String noTabTokenLine = removeTabAndSpaces(tokenLine);
            final String noTabGoodResult = removeTabAndSpaces(goodResult);
            
            if(!noTabTokenLine.equals(noTabGoodResult)) {
                printTestError(noTabGoodResult, noTabTokenLine, (i-1));
                allOk = false;
            }
        }
        return allOk;
    }
    
    /**
     * Check that the current IMP file have the same table that the expected output
     * 
     * @param testLineStr list of expected output
     * @return True if all is ok
     */
    private boolean checkSameTableOutput(final List<String> testLineStr) {
        boolean allOk = true;
        final String[] strSplitToken = _tokens.toString().split("\n");
        int i = strSplitToken.length;
        i+=2; // Skip space and "Identifiers"
        
        final String strTable = _table.toString();
        final String[] strSplitTable = strTable.split("\n");
        for(final String tableLine : strSplitTable) {
            final String goodResult = testLineStr.get(i++);
            if(!removeTabAndSpaces(tableLine).equals(removeTabAndSpaces(goodResult))) {
                printTestError(goodResult, tableLine, (i-1));
                allOk = false;
            }
        }
        return allOk;
    }
    
    /**
     * Print an error when the output is not the same that hoped
     * 
     * @param goodResult the expected output
     * @param errorLine the current output
     * @param indexErrorLine line number
     */
    private void printTestError(final String goodResult, final String errorLine, 
            final int indexErrorLine) {
        System.err.println("Error with line: " + indexErrorLine);
        System.err.println("Expeted:   '" + goodResult + "'");
        System.err.println("Recieving: '" + errorLine + "'");
    }
    
    /**
     * Remove tabulation and space (to ignore them)
     * 
     * @param strToTrim text where we must to remove space and tab
     * @return the modified text
     */
    private String removeTabAndSpaces(final String strToTrim) {
        return strToTrim.trim().replaceAll("\t", " ").replaceAll(" +", " ");
    }
    
}
