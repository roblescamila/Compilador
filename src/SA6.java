import java.util.Hashtable;

public class SA6 extends SemanticAction { 

    public static final Short COMPARATOR = 269;
    public static final Short ASIGNATION = 270;
    
    private Messages ms;
    private AnalizadorLexico al;
    private Hashtable <String, Short> symbols;

    public SA6(Messages m, AnalizadorLexico a){
        ms = m;
        al = a;
        symbols = new Hashtable<String, Short>(); 
        symbols.put("=", ASIGNATION);
        symbols.put("<", COMPARATOR);
        symbols.put(">", COMPARATOR);
        symbols.put("/", new Short((short)'/'));
    }

    public Token execute(Token token, char c) {
        ms.token(al.getLine(), token.getLexema());
        token.readCharNotAdded();
        token.setId((Short)symbols.get(token.getLexema()));
        return token;
    }
}