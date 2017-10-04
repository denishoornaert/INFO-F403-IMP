package be.ac.ulb.infof403;

import java.util.ArrayList;

public class TokenList {
    
    private ArrayList<Symbol> _list;
    
    public TokenList() {
        _list = new ArrayList<>();
    }
    
    public void add(Symbol symbol) {
        _list.add(symbol);
    }
    
    @Override
    public String toString() {
        String res = "";
        for (Symbol symbol : _list) {
            res += symbol.toString()+"\n"; // concatenation of \n here cause I don't know if I can modify the given Symbol class
        }
        return res;
    }
    
}
