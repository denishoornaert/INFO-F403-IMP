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
    
    public static void main(final String[] args) {
        
        FileReader file;
        try {
            file = new FileReader(args[0]);
            Scanner scanner = new Scanner(file);
            
            Symbol symbol = null;
            do {
                symbol = scanner.nextToken();
            } while(symbol == null || symbol.getType() != LexicalUnit.END);
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
