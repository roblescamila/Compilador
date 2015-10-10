import java.util.Hashtable;

public class SA8 extends SemanticAction {
    
	private Messages ms;
    private AnalizadorLexico al;
    private Hashtable <String, Short> symbols;

    public SA8(Messages m, AnalizadorLexico a){
        ms = m;
        al = a;
        symbols = new Hashtable<String, Short>(); 
        symbols.put("+", new Short((short)'+'));
        symbols.put("-", new Short((short)'-'));
        symbols.put("*", new Short((short)'*'));
        symbols.put(",", new Short((short)','));
        symbols.put(";", new Short((short)';'));
        symbols.put(")", new Short((short)')'));
        symbols.put("(", new Short((short)'('));
        symbols.put("}", new Short((short)'}'));
        symbols.put("{", new Short((short)'{'));
        symbols.put(" ", (short) 277);
    }

    public Token execute(Token token, char c) {
        token = new Token();
        token.addChar(c);
        if (c == ' ')
            ms.token(al.getLine(), "EOF");
        else
        	ms.token(al.getLine(), token.getLexema());
        token.setId((Short)symbols.get(token.getLexema()));
	    return token;
	}
}
