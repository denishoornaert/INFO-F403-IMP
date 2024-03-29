package be.ac.ulb.infof403;

import java.util.HashMap;

/**
 * Table of symbol (where a symbol is associated with his line number)
 */
public class SymbolTable extends HashMap<Object, Integer> {
    
    private static SymbolTable _instance = null;
    
    /**
     * Default constructor
     */
    private SymbolTable() {
        super();
    }
    
    public static SymbolTable getInstance() {
        if(_instance == null) {
            _instance = new SymbolTable();
        }
        return _instance;
    }
    
    /**
     * <b>Only for Test</b>
     */
    public static void reset() {
        _instance = null;
    }
    
    /**
     * The method checks whether the value of the symbol is already on the HashMap.<br>
     * If so, the position (line number) is not updated as we only want to know
     * where the token has been encountered for the first time.
     * 
     * @param symbol which must be add to the table
     */
    public void put(final Symbol symbol) {
        if(!this.containsKey(symbol.getValue())) {
            this.put(symbol.getValue(), symbol.getLine());
        }
    }
    
    @Override
    public String toString() {
        String res = "";
        for (final Entry<Object, Integer> entry : this.entrySet()) {
            res += entry.getKey()+"\t"+entry.getValue()+"\n";
        }
        return res;
    }
    
}
