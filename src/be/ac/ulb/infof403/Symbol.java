package be.ac.ulb.infof403;

import java.util.HashSet;

public class Symbol extends Terminal {
    
    public static final int UNDEFINED_POSITION = -1;
    public static final Object NO_VALUE = null;
    
    private final LexicalUnit type;
    private final Object value;
    private final int line,column;
    
    public Symbol(final LexicalUnit unit, final int line, final int column,
            final Object value) {
        this.type	= unit;
        this.line	= line+1;
        this.column	= column;
        this.value	= value;
    }
    
    public Symbol(final LexicalUnit unit, final int line, final int column){
        this(unit,line,column,NO_VALUE);
    }
    
    public Symbol(final LexicalUnit unit, final int line){
        this(unit,line,UNDEFINED_POSITION,NO_VALUE);
    }
    
    public Symbol(final LexicalUnit unit){
        this(unit,UNDEFINED_POSITION,UNDEFINED_POSITION,NO_VALUE);
    }
    
    public Symbol(final LexicalUnit unit, final Object value){
        this(unit,UNDEFINED_POSITION,UNDEFINED_POSITION,value);
    }
    
    public boolean isTerminal(){
        return this.type != null;
    }
    
    public boolean isNonTerminal(){
        return this.type == null;
    }
    
    public LexicalUnit getType(){
        return this.type;
    }
    
    @Override
    public Object getValue(){
        return this.value;
    }
    
    public int getLine(){
        return this.line;
    }
    
    public int getColumn(){
        return this.column;
    }
    
    @Override
    public int hashCode(){
        final String value = (this.value != null ? this.value.toString() : "null");
        final String type = (this.type != null ? this.type.toString() : "null");
        return new String(value+"_"+type).hashCode();
    }
    
    @Override
    public String toString(){
        if(this.isTerminal()){
            final String value = (this.value != null ? this.value.toString() : "null");
            final String type = (this.type != null ? this.type.toString() : "null");
            return "token: "+value+"\tlexical unit: "+type;
        }
        return "Non-terminal symbol";
    }
    
    @Override
    public boolean equals(final Object symb) {
        return symb instanceof Symbol && this.type == ((Symbol)symb).type;
    }
    
    @Override
    public HashSet<Terminal> first() {
        final HashSet<Terminal> res = new HashSet<>();
        res.add(this);
        return res;
    }
}
