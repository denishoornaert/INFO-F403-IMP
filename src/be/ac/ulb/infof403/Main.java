package be.ac.ulb.infof403;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    
    private static final String DEFAULT_IMP_FILE = "./test/Euclid.imp";
    private static TokenList _tokens;
    private static SymbolTable _table;

    public static void main(final String[] args) {
        String fileName = DEFAULT_IMP_FILE; // Default file name
        if(args.length > 0) { // If file specified
            fileName = args[0];
        }
        _tokens = new TokenList();
        _table = new SymbolTable();
        scanImpFile(fileName);
        
        // Make test
        if(args.length > 1 && args[1].equalsIgnoreCase("-test")) {
            final String testFile;
            if(args.length > 2) {
                testFile = args[2];
            } else {
                final String fileNameWitoutExt = fileName.substring(0, fileName.lastIndexOf('.'));
                testFile = fileNameWitoutExt + ".out";
            }
            testOutput(testFile);
        }

    }

    private static void scanImpFile(final String fileName) {
        final FileReader file;
        try {
            file = new FileReader(fileName);
            final Scanner scanner = new Scanner(file);

            readSymbol(scanner);
            System.out.println(_tokens);
            System.out.println("\nIdentifiers");
            System.out.println(_table);

        } catch (IOException | IMPSyntaxException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private static void readSymbol(final Scanner scanner) throws IOException, IMPSyntaxException {
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

    private static void testOutput(final String testFile) {
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

    private static boolean checkSameOutput(final List<String> testLineStr) {
        boolean allOk = true;
        final String strToken = _tokens.toString();
        final String[] strSplitToken = strToken.split("\n");

        int i = 0;
        for(final String tokenLine : strSplitToken) {
            final String goodResult = testLineStr.get(i++);
            if(!tokenLine.equals(goodResult)) {
                System.err.println("Error with line: " + (i-1));
                System.err.println("Expeted:   '" + goodResult + "'");
                System.err.println("Recieving: '" + tokenLine + "'");
                allOk = false;
            }
        }

        i+=2; // Skip space and "Identifiers"
        final String strTable = _table.toString();
        final String[] strSplitTable = strTable.split("\n");
        for(final String tableLine : strSplitTable) {
            final String goodResult = testLineStr.get(i++);
            if(!tableLine.equals(goodResult)) {
                System.err.println("Error with line: " + (i-1));
                System.err.println("Expeted:   '" + goodResult + "'");
                System.err.println("Recieving: '" + tableLine + "'");
                allOk = false;
            }
        }

        return allOk;
    }

}
