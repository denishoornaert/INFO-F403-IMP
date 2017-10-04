package be.ac.ulb.infof403;

import java.util.HashMap;

public class SymbolTable extends HashMap<Object, Integer> {
    
    public SymbolTable() {
        super();
    }
    
    public void put(Symbol symbol) {
        this.put(symbol.getValue(), symbol.getLine());
    }
    
    @Override
    public String toString() {
    String res = "";
        for (Entry<Object, Integer> entry : this.entrySet()) {
            res += entry.getKey()+" "+entry.getValue()+"\n";
        }
    return res;
    }
    
}
