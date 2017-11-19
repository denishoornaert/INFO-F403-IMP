package be.ac.ulb.infof403;

import java.util.HashSet;

public class Epsilon extends Terminal {

    public Epsilon() {
        super();
    }
    
    @Override
    public String toString() {
        return "eps";
    }

    @Override
    public Object getValue() {
        return "eps";
    }

    @Override
    public HashSet<Terminal> first() {
        final HashSet<Terminal> res = new HashSet<>();
        res.add(this);
        return res;
    }
    
}
