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
            final Scanner scanner = new Scanner(file);
            
            Symbol symbol = null;
            while(symbol == null || symbol.getType() != LexicalUnit.EOS) {
                if(symbol != null) {
                    System.out.println(symbol.toString());
                }
                symbol = scanner.nextToken();
            }
            
        } catch (IOException | IMPSyntaxException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
