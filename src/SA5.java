import java.util.Hashtable;

public class SA5 extends SemanticAction{

    public static final Short COMPARATOR = 269;
    
    private Messages m;
    private AnalizadorLexico al;
    private Hashtable <String, Short> symbols;

    public SA5(Messages m, AnalizadorLexico a){
        this.m = m;
        this.al = a;
        symbols = new Hashtable<String, Short>(); 
        symbols.put(">=", COMPARATOR);
        symbols.put("<=", COMPARATOR);
        symbols.put("<>", COMPARATOR);
        symbols.put("==", COMPARATOR);
    }

    public Token execute(Token token, char c) {
        token.addChar(c);
        m.token(al.getLine(), token.getLexema());
        token.setId((Short)symbols.get(token.getLexema()));
        return token;
    }
}