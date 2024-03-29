package be.ac.ulb.infof403;

import java.util.ArrayList;

/**
 * List of token
 */
public class TokenList extends ArrayList<Symbol> {

    /**
     * Default constructor
     */
    public TokenList() {
        super();
    }
    
    @Override
    public String toString() {
        String res = "";
        for(final Symbol symbol : this) {
            // concatenation of \n here cause I don't know if I can modify the given Symbol class
            res += symbol.toString()+"\n"; 
        }
        return res;
    }
    
}
