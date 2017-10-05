package be.ac.ulb.infof403;

import java.util.HashMap;

public class SymbolTable extends HashMap<Object, Integer> {
    
    /**
     * The method checks whether the value of the symbol is already on the HashMap.
     * If so, the position (line number) is not updated as we only want to know
     * where the token has been encountered for the first time.
     * @param symbol
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
