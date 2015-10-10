public class SA13 extends SemanticAction{
		
	public static final Short ID = 265;
	
	private TS ts;
	private Messages m;
	private AnalizadorLexico al;
	
	private void verifyStringLength(Token token) {  
		if (token.getLength() > 15) {
	        Warning w = new Warning(al.getLine(), token.getLexema() + " ha sido truncado.");
	        m.addWarning(w);
	        token.truncateId();
		}
	}
	
	public SA13(TS t, Messages m, AnalizadorLexico a){
	    this.ts = t;
	    this.m = m;
	    this.al = a;
	}
	
	public Token execute(Token token, char c) {
		if (ts.hasLexema(token.getLexema())){
	        token.readCharNotAdded();
	        token.setTSEntry(ts.getTSEntry(token.getLexema()));
	        m.token(al.getLine(), token.getLexema());
	        return token;
	    }
	    else {
	       
	        	verifyStringLength(token);
	            token.setId(ID);
	            ts.addTSEntry(token.getLexema(), token.getETS());
	       
	        m.token(al.getLine(), token.getLexema());
	        token.readCharNotAdded();    
	            return token;
	        }
	}
}