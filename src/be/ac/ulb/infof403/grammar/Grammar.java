package be.ac.ulb.infof403.grammar;

import be.ac.ulb.infof403.Elem;
import be.ac.ulb.infof403.Epsilon;
import be.ac.ulb.infof403.LexicalUnit;
import be.ac.ulb.infof403.Symbol;
import be.ac.ulb.infof403.Terminal;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * List of rule of a grammar
 */
public class Grammar {
    
    private HashSet<GrammarVariable> _variables;
    private HashSet<Symbol> _symbols = null;
    private final GrammarVariable _initialState;
    
    public Grammar(final GrammarVariable initialState) {
        _variables = new HashSet<>();
        addVariables(initialState);
        _initialState = initialState;
    }
    
    public final void addVariables(final GrammarVariable... variables) {
        addVariables(new ArrayList<>(Arrays.asList(variables)));
    }
    
    public final void addVariables(final Collection<GrammarVariable> allVariables) {
        for (final GrammarVariable variable : allVariables) {
            if(_variables.add(variable)) {
                variable.setGrammar(this);
            }
        }
    }
    
    public HashSet<GrammarVariable> getVariables() {
        return _variables;
    }
    
    @Override
    public String toString() {
        String result = "";
        for(final GrammarVariable sym : _variables) {
            result += sym.getStrRules();
        }
        return result;
    }
    
    public void removeUseless() {
        removeUnproductive();
        removeInaccessible();
    }
    
    private void removeUnproductive() {
        final HashSet<GrammarVariable> newGrammarVariable = new HashSet<>();
        boolean addVariable = true;
        
        while(addVariable) {
            addVariable = false;
            for(final GrammarVariable sym : _variables) {
                if( (sym.haveNoRule() && newGrammarVariable.add(sym)) || 
                    (sym.allRuleComposantTerminal(newGrammarVariable) && 
                        newGrammarVariable.add(sym)) ) {
                    addVariable = true;
                }
            }
        }
        
        removeRuleWithUnproductiveVariable(newGrammarVariable);
    }
    
    private void removeRuleWithUnproductiveVariable(final HashSet<GrammarVariable> productiveVariable) {
        final HashSet<GrammarVariable> cloneListRule =
                (HashSet<GrammarVariable>) _variables.clone();
        for(final GrammarVariable sym : cloneListRule) {
            if(!productiveVariable.contains(sym)) {
                _variables.remove(sym);
            } else {
                sym.removeRuleWithUnproductiveVariable(productiveVariable);
            }
        }
    }
    
    private void removeInaccessible() {
        final HashSet<GrammarVariable> accessibleVariable = new HashSet<>();
        accessibleVariable.add(_initialState);
        
        boolean addVariable = true;
        while(addVariable) {
            addVariable = false;
            
            final HashSet<GrammarVariable> allAccessibleVar = (HashSet<GrammarVariable>) accessibleVariable.clone();
            for(final GrammarVariable var : allAccessibleVar) {
                if(accessibleVariable.addAll(var.getAllGrammarVariable())) {
                    addVariable = true;
                }
            }
        }
        
        final HashSet<GrammarVariable> cloneListRule =
                (HashSet<GrammarVariable>) _variables.clone();
        for(final GrammarVariable var : cloneListRule) {
            if(!accessibleVariable.contains(var)) {
                _variables.remove(var);
            }
        }
    }
    
    public void factorisation() { // TODO has to be tested
        final HashSet<GrammarVariable> allVariable = (HashSet<GrammarVariable>) _variables.clone();
        for (final GrammarVariable var : allVariable) {
            // Setup of the stree and generation of the factorised rules.
            final Stree s = new Stree(var);
            if(s.addRules(var.getRules())) {
                addVariables(s.generateNewGramVariable());
            }
        }
    }
    
    public void removeLeftRecursion() {
        final HashSet<GrammarVariable> workingList = (HashSet<GrammarVariable>)_variables.clone();
        for (final GrammarVariable key : _variables) {
            final ArrayList<Rule> value = key.getRules();
            Boolean again = true;
            int counter = 0;
            while(counter < value.size() && again) {
                again = true;
                if(key.equals(value.get(0).get(0))) {
                    again = false;
                    final GrammarVariable u = new GrammarVariable(key.getVarName()+"U");
                    final GrammarVariable v = new GrammarVariable(key.getVarName()+"V");
                    final ArrayList<Rule> list = key.getRules();
                    createUVRule(key, u, v);
                    createURule(workingList, list, u);
                    createVRule(workingList, list, v);
                }
                counter++;
            }
        }
        _variables = workingList;
    }
    
    private void createUVRule(final GrammarVariable key, final GrammarVariable u, final GrammarVariable v) {
        key.cleanRules();
        key.addRule(new Rule(u, v));
    }
    
    private void createURule(final HashSet<GrammarVariable> workingList, 
            final ArrayList<Rule> list, final GrammarVariable u) {
        workingList.add(u);
        for (int i = 1; i < list.size(); i++) {
            u.addRule(list.get(i));
        }
    }
    
    private void createVRule(final HashSet<GrammarVariable> workingList, 
            final ArrayList<Rule> list, final GrammarVariable v) {
        final Rule workingRule = list.get(0);
        workingRule.remove(0);
        workingRule.add(v);
        workingList.add(v);
        v.addRule(workingRule);
    }
    
    public void printFollow() {
        for(final GrammarVariable gramVar : _variables) {
            System.out.println("\n\n--------------");
            System.out.print("Follow of " + gramVar + ": ");
            for(final Terminal follow : follow(gramVar)) {
                System.out.print(follow.getValue() + ", ");
            }
            System.out.println("");
        }
    }
    
    public HashSet<Terminal> follow(final GrammarVariable gramVar) {
        return follow(gramVar, new HashSet<>());
    }
    
    private HashSet<Terminal> follow(final GrammarVariable gramVar, 
            final HashSet<GrammarVariable> prevGramVarAlreadyFollow) {
        final HashSet<Terminal> result = new HashSet<>();
        if(prevGramVarAlreadyFollow.contains(gramVar)) {
            return result;
        }
        prevGramVarAlreadyFollow.add(gramVar);
        
        for(final GrammarVariable gramVarContained : _variables) {
            final HashSet<Elem> allFollowedElem = gramVarContained.getDirectFollowed(gramVar);
            
            for(final Elem followedElem : allFollowedElem) {
                if(followedElem instanceof GrammarVariable) {
                    
                    for(final Terminal term : followedElem.first()) {
                        if(term instanceof Epsilon) {
                            final GrammarVariable gramVarFollowedElem = (GrammarVariable) followedElem;
                            result.addAll(follow(gramVarFollowedElem, 
                                    prevGramVarAlreadyFollow));
                        }
                        else {
                            result.add(term);
                        }
                    }
                    
                } else if(followedElem instanceof Terminal) {
                    result.add((Terminal) followedElem);
                }
            }
            
            if(gramVarContained.isGramVarEndOfAtLeastOneRule(gramVar) && gramVarContained != gramVar) {
                result.addAll(follow(gramVarContained, prevGramVarAlreadyFollow));
            }
            
        }
        return result;
    }
    
    private HashSet<Symbol> getAllSymbol() {
        if(_symbols == null) {
            _symbols = new HashSet<>();
            for(final GrammarVariable var : _variables) {
                _symbols.addAll(var.getAllSymbol());
            }
        }
        return _symbols;
    }
    
    public void printActionTable() {
        final HashSet<Symbol> syms = getAllSymbol();
        
        // Header
        System.out.print("\t");
        for (final Symbol sym : syms) {
            System.out.print(sym.getValue()+"\t");
        }
        System.out.println("");
        // Table
        for (final GrammarVariable var : _variables) {
            System.out.print(var.getVarName()+" |\t");
            for (final Symbol sym : syms) {
                final Rule res = var.getRuleThatLeadsToSymbol(sym);
                if(res != null) {
                    System.out.print(res.getId());
                }
                System.out.print("\t");
            }
            System.out.println("");
        }
    }
    
    
    /**
     * Open and scan grammar file
     * 
     * @param gramFilePath the path to the grammar
     * @return The grammar object of null if not correct
     */
    public static Grammar openAndScanGrammar(final String gramFilePath) {
        Grammar result = null;
        
        boolean allOk = true;
        GrammarScanner gramScanner = null;
        final FileReader file;
        try {
            file = new FileReader(gramFilePath);
            gramScanner = new GrammarScanner(file);

        } catch (IOException exception) {
            System.err.println("Error with grammar file: " + exception.getMessage());
            allOk = false;
        }
        
        if(allOk && gramScanner != null) {
            result = readGrammar(gramScanner);
        }
        
        return result;
    }
    
    private static Grammar readGrammar(final GrammarScanner gramScanner) {
        Symbol symbol = null;
        try {
            while(symbol == null || symbol.getType() != LexicalUnit.EOS) {
                symbol = gramScanner.nextToken();
            }
        } catch (IOException ex) {
            System.err.println("Bug with token Grammar flex: " + ex.getMessage());
        }
        
        return GrammarScanner.getGrammar();
    }
    
}
