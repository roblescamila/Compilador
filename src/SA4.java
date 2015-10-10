import java.util.Hashtable;

public class SA4 extends SemanticAction{
		
	public static final Short IF = 257; 
	public static final Short THEN = 258;
	public static final Short ELSE = 259;
	public static final Short ENDIF = 260;
	public static final Short PRINT = 261;		    
	public static final Short INT = 262;
	public static final Short FLOAT = 267;
	public static final Short GLOBAL = 272;
	public static final Short END = 271;
	public static final Short BEGIN = 273;
	public static final Short BY = 277;
	public static final Short LOOP = 263;
	public static final Short FROM = 274;
	public static final Short TO = 264;
	public static final Short TOFLOAT = 275;
	public static final Short ID = 265;
	public static final Short STRING = 268;
	
	private TS ts;
	private Messages m;
	private AnalizadorLexico al;
	private Hashtable<String, Short> reservedWords;
	
	private void verifyStringLength(Token token) {  
		if (token.getLength() > 15) {
			Warning w = new Warning(al.getLine(), token. getLexema() + " ha sido truncado.");
			m.addWarning(w);
			token.truncateId();
		}
	}
	
	public SA4(TS t, Messages m, AnalizadorLexico a){
	    this.ts = t;
	    this.m = m;
	    this.al = a;
	    reservedWords = new Hashtable<String, Short>();
	    reservedWords.put("IF", IF);
	    reservedWords.put("THEN", THEN);
	    reservedWords.put("ELSE", ELSE);
	    reservedWords.put("ENDIF", ENDIF);
	    reservedWords.put("PRINT", PRINT);
	    reservedWords.put("INT", INT);
	    reservedWords.put("BEGIN", BEGIN);
	    reservedWords.put("END", END);
	    reservedWords.put("GLOBAL", GLOBAL);
	    reservedWords.put("FLOAT", FLOAT);
	    reservedWords.put("LOOP", LOOP);
	    reservedWords.put("FROM", FROM);
	    reservedWords.put("TO", TO);
	    reservedWords.put("BY", BY);
	    reservedWords.put("TOFLOAT", TOFLOAT);
	    reservedWords.put("STRING", STRING);
	}
	
	public Token execute(Token token, char c) {
		if (ts.hasLexema(token.getLexema())){
	        token.readCharNotAdded();
	        token.setTSEntry(ts.getTSEntry(token.getLexema()));
	        m.token(al.getLine(), token.getLexema());
	        return token;
	    }
	    else {
	        if (!ts.isResWord(token.getLexema())){
	        	verifyStringLength(token);
	            token.setId(ID);
	            ts.addTSEntry(token.getLexema(), token.getETS());
	        }
	        else {
	            token.setId((Short)reservedWords.get(token.getLexema()));
	        }
	        m.token(al.getLine(), token.getLexema());
	        token.readCharNotAdded();
	            return token;
	        }
	}
}