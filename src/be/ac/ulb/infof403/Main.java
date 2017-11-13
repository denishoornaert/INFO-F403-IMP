package be.ac.ulb.infof403;

import be.ac.ulb.infof403.grammar.Grammar;
import be.ac.ulb.infof403.grammar.GrammarScanner;
import be.ac.ulb.infof403.grammar.GrammarVariable;
import be.ac.ulb.infof403.grammar.Stree;
import be.ac.ulb.infof403.scanner.ImpScanner;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Main class
 */
public class Main {
    
    private static final String DEFAULT_IMP_FILE = "./test/Euclid.imp";
    
    /**
     * Main function 
     * 
     * @param args parameters given when the program is executed
     */
    public static void main(final String[] args) {
        if(args.length == 0) {
            System.err.println("Missing argument");
            printHelp();
            return;
        }
        
        final String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
        switch(args[0]) {
            case "scan":
                scan(newArgs);
                break;
            
            case "grammar":
                grammar(newArgs);
                break;
                
            case "stree":
                stree_test_with_factorisation();
                stree_test_with_no_factorisation();
                break;
                
            case "first":
                first_test();
                break;
        }
    }
    
    /**
     * Scan a IMP file
     * 
     * @param args informations to scan
     */
    private static void scan(final String[] args) {
        String fileName = DEFAULT_IMP_FILE; // Default file name
        if(args.length > 0) { // If file specified
            fileName = args[0];
        }
        
        // Make test
        String testFile = "";
        if(args.length > 1 && args[1].equalsIgnoreCase("-test")) {
            if(args.length > 2) {
                testFile = args[2];
            } else {
                final String fileNameWitoutExt = fileName.substring(0, fileName.lastIndexOf('.'));
                testFile = fileNameWitoutExt + ".out";
            }
            
        }
        
        if(!testFile.isEmpty()) {
            new ImpScanner(fileName, testFile);
        } else {
            new ImpScanner(fileName);
        }
    }
    
    /**
     * Open and scan grammar file
     * 
     * @param gramFilePath the path to the grammar
     * @return The grammar object of null if not correct
     */
    private static Grammar openAndScanGrammar(final String gramFilePath) {
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
    
    /**
     * Optimise grammar and scan it
     * 
     * @param args arguments
     */
    private static void grammar(final String[] args) {
        String gramFileName = "./test/Gram.gram";
        if(args.length >= 1) {
            gramFileName = args[0]; 
        }
        
        final Grammar grammar = openAndScanGrammar(gramFileName);
        
        // temporary TODO remove for final version
        // testGrammar2();
        // testGrammar3();
        // stree_test_with_factorisation();
        // stree_test_with_no_factorisation();
        testActionTable();
        
        boolean removeUseless = false;
        boolean factorisation = false;
        for (int i = 1; i < args.length; ++i) {
            switch(args[i]) {
                case "-ru":
                case "-removeuseless":
                    removeUseless = true;
                    break;
                    
                case "-fact":
                case "-factorisation":
                    factorisation = true;
                    break;
            }
        }
        
        if(removeUseless) {
            grammar.removeUseless();
        }
        
        if(factorisation) {
            grammar.facorisation();
        }
        
        System.out.println("Follow results:\n" + grammar);
        grammar.printFollow();
    }
    
    //////////// DEBUG ////////////
    private static void testGrammar2() {
        /* -=- Grammar 2 -=-
        <init>    -> <A>
                  -> <B>
        <A>       -> a<B>
                  -> b<init>
                  -> b
        <B>       -> <A><B>
                  -> <B>a
        <C>       -> <A><init>
                  -> b
        */
        
        // Define variable
        final GrammarVariable initial = new GrammarVariable("init");
        final GrammarVariable A = new GrammarVariable("A");
        final GrammarVariable B = new GrammarVariable("B");
        final GrammarVariable C = new GrammarVariable("C");
        
        // Define terminal
        final Symbol a = new Symbol(LexicalUnit.VARNAME, "a");
        final Symbol b = new Symbol(LexicalUnit.VARNAME, "b");
        final Grammar grammar2 = new Grammar(initial);
        
        // <init>
        initial.addRule(A);
        initial.addRule(B);
        
        // <A>
        A.addRule(a, B);
        A.addRule(b, initial);
        A.addRule(b);
        
        // <B>
        B.addRule(A, B);
        B.addRule(B, a);
        
        // <C>
        C.addRule(A, initial);
        C.addRule(b);
        
        grammar2.addVariables(A, B, C);
        
        System.out.println("Remove useless");
        System.out.println("Grammar2: ");
        System.out.println(grammar2);
        grammar2.removeUseless();
        System.out.println("Grammar2 Clean: ");
        System.out.println(grammar2);
    }
    
    private static void testGrammar3() {
        /* -=- Grammar 2 -=-
        <E>       -> <E>+<T>
                  -> <T>
        <T>       -> <T>*<P>
                  -> <P>
        <P>       -> ID
        */
        
        // Define variable
        final GrammarVariable E = new GrammarVariable("E");
        final GrammarVariable T = new GrammarVariable("T");
        final GrammarVariable P = new GrammarVariable("P");
        
        // Define terminal
        final Symbol ml = new Symbol(LexicalUnit.TIMES, "*");
        final Symbol pl = new Symbol(LexicalUnit.PLUS, "+");
        final Symbol id = new Symbol(LexicalUnit.VARNAME, "ID");
        
        final Grammar grammar3 = new Grammar(E);
        
        // <E>
        E.addRule(E, pl, T);
        E.addRule(T);
        // <T>
        T.addRule(T, ml, P);
        T.addRule(P);
        // <P>
        P.addRule(id);
        
        grammar3.addVariables(T, P);
        
        System.out.println("remove left recursion");
        System.out.println("Grammar3: ");
        System.out.println(grammar3);
        
        grammar3.removeLeftRecursion();
        System.out.println("Grammar3 Clean: ");
        System.out.println(grammar3);
    }
    
    private static void stree_test_with_factorisation() {
        GrammarVariable cond = new GrammarVariable("Cond");
        GrammarVariable code = new GrammarVariable("Code");
        
        ArrayList<Elem> list1 = new ArrayList<>();
        list1.add(new Symbol(LexicalUnit.IF, "if"));
        list1.add(cond);
        list1.add(new Symbol(LexicalUnit.THEN, "then"));
        list1.add(code);
        list1.add(new Symbol(LexicalUnit.ENDIF, "endif"));
        
        ArrayList<Elem> list2 = new ArrayList<>();
        list2.add(new Symbol(LexicalUnit.IF, "if"));
        list2.add(cond);
        list2.add(new Symbol(LexicalUnit.THEN, "then"));
        list2.add(code);
        list2.add(new Symbol(LexicalUnit.ELSE, "else"));
        list2.add(code);
        list2.add(new Symbol(LexicalUnit.ENDIF, "endif"));
        
        Stree s = new Stree(new GrammarVariable("If"));
        s.add(list1);
        s.add(list2);
        Grammar g = s.generateRules();
        System.out.println(g);
    }
    
    private static void stree_test_with_no_factorisation() {
        GrammarVariable expr = new GrammarVariable("ExprArith");
        
        ArrayList<Elem> list1 = new ArrayList<>();
        list1.add(new Symbol(LexicalUnit.LPAREN, "("));
        list1.add(expr);
        list1.add(new Symbol(LexicalUnit.RPAREN, ")"));
        
        ArrayList<Elem> list2 = new ArrayList<>();
        list2.add(new Symbol(LexicalUnit.MINUS, "-"));
        list2.add(expr);
        
        Stree s = new Stree(new GrammarVariable("If"));
        s.add(list1);
        s.add(list2);
        Grammar g = s.generateRules();
        System.out.println(g);
    }
    
    private static void first_test() {
        /* -=- Grammar -=-
        <S>       -> <Exp>$
        <Exp>     -> <Prod><Exp'>
        <Exp'>    -> +<Prod><Exp'>
                  -> -<Prod><Exp'>
                  -> epsilon
        <Prod>    -> <Atom><prod'>
        <Prod'>   -> *<Atom><Prod'>
                  -> /<Atom><Prod'>
                  -> epsilon
        <Atom>    -> -<Atom>
                  -> Cst
                  -> Id
                  -> (<Exp>)
        */
        
        // Define variable
        final GrammarVariable S = new GrammarVariable("S");
        final GrammarVariable Exp = new GrammarVariable("Exp");
        final GrammarVariable ExpB = new GrammarVariable("Exp'");
        final GrammarVariable Prod = new GrammarVariable("Prod");
        final GrammarVariable ProdB = new GrammarVariable("Prod'");
        final GrammarVariable Atom = new GrammarVariable("Atom");
        
        // Define terminal
        final Symbol dl = new Symbol(LexicalUnit.VARNAME, "$");
        final Symbol ml = new Symbol(LexicalUnit.TIMES, "*");
        final Symbol pl = new Symbol(LexicalUnit.PLUS, "+");
        final Symbol sb = new Symbol(LexicalUnit.MINUS, "-");
        final Symbol dv = new Symbol(LexicalUnit.DIVIDE, "/");
        final Symbol lp = new Symbol(LexicalUnit.LPAREN, "(");
        final Symbol rp = new Symbol(LexicalUnit.RPAREN, ")");
        final Symbol id = new Symbol(LexicalUnit.VARNAME, "Id");
        final Symbol ct = new Symbol(LexicalUnit.VARNAME, "Cst");
        final Epsilon e = new Epsilon();
        
        final Grammar grammar = new Grammar(S);
        
        // <S>
        S.addRule(Exp, dl);
        // <Exp>
        Exp.addRule(Prod, ExpB);
        // <ExpB>
        ExpB.addRule(pl, Prod, ExpB);
        ExpB.addRule(sb, Prod, ExpB);
        ExpB.addRule(e);
        // <Prod>
        Prod.addRule(Atom, ProdB);
        // <ProdB>
        ProdB.addRule(ml, Atom, ProdB);
        ProdB.addRule(dv, Atom, ProdB);
        ProdB.addRule(e);
        // <Atom>
        Atom.addRule(sb, Atom);
        Atom.addRule(ct);
        Atom.addRule(id);
        Atom.addRule(lp, Exp, rp);
        
        grammar.addVariables(Exp, ExpB, Prod, ProdB, Atom); // S already added above
        
        System.out.println("First Set retrieving");
        System.out.println("Grammar : ");
        System.out.println(grammar);
        
        HashSet<Terminal> res = Atom.first();
        System.out.println("First(Atom) result : ");
        for (Terminal re : res) {
            System.out.print(re.getValue()+", ");
        }
        System.out.println("");
        
        res = ProdB.first();
        System.out.println("First(Prod') result : ");
        for (Terminal re : res) {
            System.out.print(re.getValue()+", ");
        }
        System.out.println("");
    }
    
    private static void testActionTable() {
        /* -=- Grammar -=-
        <A>       -> aaa
                  -> <B>bb
                  -> <C>dd
        <B>       -> b
        <C>       -> c
                  -> epsilon
        */
        
        // Define variable
        final GrammarVariable A = new GrammarVariable("A");
        final GrammarVariable B = new GrammarVariable("B");
        final GrammarVariable C = new GrammarVariable("C");
        
        // Define terminal
        final Symbol a = new Symbol(LexicalUnit.VARNAME, "a");
        final Symbol b = new Symbol(LexicalUnit.VARNAME, "b");
        final Symbol c = new Symbol(LexicalUnit.VARNAME, "c");
        final Symbol d = new Symbol(LexicalUnit.VARNAME, "d");
        final Epsilon e = new Epsilon();
        
        final Grammar grammar = new Grammar(A);
        
        // <A>
        A.addRule(a, a, a);
        A.addRule(B, b, b);
        A.addRule(C, d, d);
        // <B>
        B.addRule(b);
        // <C>
        C.addRule(c);
        C.addRule(e);
        
        grammar.addVariables(B, C);
        
        System.out.println("Action table: ");
        grammar.printActionTable(a, b, c, d);
    }
    
    /**
     * Print help message and informations to launch jar
     */
    private static void printHelp() {
        System.out.println("Command: java -jar INFO-F403-IMP.jar <scan/grammar> [options]");
        System.out.println("");
        System.out.println("--- Options Scan ---");
        System.out.println("  > java -jar INFO-F403-IMP.jar scan [inputFile] [outputFile] [-test]");
        System.out.println("  \tinputFile\tThe file with the IMP code (default: './test/Euclid.imp')");
        System.out.println("  \toutputFile\tExpected output of IMP scan");
        System.out.println("  \t-test\t\tAutomaticaly test that output is equals to the output system");
        System.out.println("");
        System.out.println("--- Options Grammar ---");
        System.out.println("  > java -jar INFO-F403-IMP.jar grammar <grammarFile> [options]");
        System.out.println("  \tgrammarFile\tThe file that contains the Grammar (default: './test/Gram.gram')");
        System.out.println("  \t-removeuseless\tRemove useless variable");
        System.out.println("  \t-factorisation\tFactorise the grammar");
        System.out.println("  \t-test\t\tTemporary test grammar"); // TODO change when code is finish
    }

}
