package be.ac.ulb.infof403;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        openAndInitScannerImpFile(fileName);
        printResult();
        
        if(!testFileName.isEmpty()) {
            testOutput(testFileName);
        }
    }
    
    
    /**
     * Open and initialise the scanner of the IMP file
     * 
     * @param fileName the IMP filename
     */
    private void openAndInitScannerImpFile(final String fileName) {
        final FileReader file;
        try {
            file = new FileReader(fileName);
            final Scanner scanner = new Scanner(file);

            readSymbol(scanner);

        } catch (IOException | IMPSyntaxException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
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
     * @throws IMPSyntaxException if there is an IMP syntax error
     */
    private void readSymbol(final Scanner scanner) throws IOException, IMPSyntaxException {
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
                System.out.println("Les résultats sont équivalent au fichier " + testFile);
            }

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Check that the current IMP file ... TODO 
     * 
     * @param testLineStr
     * @return 
     */
    private boolean checkSameOutput(final List<String> testLineStr) {
        boolean allOk = true;
        final String strToken = _tokens.toString();
        final String[] strSplitToken = strToken.split("\n");

        int i = 0;
        for(final String tokenLine : strSplitToken) {
            final String goodResult = testLineStr.get(i++);
            
            final String noTabTokenLine = removeTabAndSpaces(tokenLine);
            final String noTabGoodResult = removeTabAndSpaces(goodResult);
            
            if(!noTabTokenLine.equals(noTabGoodResult)) {
                printError(noTabGoodResult, noTabTokenLine, (i-1));
                allOk = false;
            }
        }

        i+=2; // Skip space and "Identifiers"
        final String strTable = _table.toString();
        final String[] strSplitTable = strTable.split("\n");
        for(final String tableLine : strSplitTable) {
            final String goodResult = testLineStr.get(i++);
            if(!removeTabAndSpaces(tableLine).equals(removeTabAndSpaces(goodResult))) {
                printError(goodResult, tableLine, (i-1));
                allOk = false;
            }
        }

        return allOk;
    }
    
    private void printError(final String goodResult, final String errorLine, 
            final int indexErrorLine) {
        System.err.println("Error with line: " + indexErrorLine);
        System.err.println("Expeted:   '" + goodResult + "'");
        System.err.println("Recieving: '" + errorLine + "'");
    }
    
    private String removeTabAndSpaces(final String strToTrim) {
        return strToTrim.trim().replaceAll("\t", " ").replaceAll(" +", " ");
    }
    
}
