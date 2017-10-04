package be.ac.ulb.infof403;

import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Detobel
 */
public class Main {
    
    private static TokenList _tokens;
    
    public static void main(final String[] args) {
        
        _tokens = new TokenList();
        
        FileReader file;
        try {
            file = new FileReader("./test/Euclid.imp");
            final Scanner scanner = new Scanner(file);
            
            Symbol symbol = null;
            while(symbol == null || symbol.getType() != LexicalUnit.EOS) {
                if(symbol != null) {
                    _tokens.add(symbol);
                }
                symbol = scanner.nextToken();
            }
            System.out.println(_tokens);
            
        } catch (IOException | IMPSyntaxException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
