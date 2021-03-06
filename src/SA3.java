public class SA3 extends SemanticAction { 
	
	public static final short CONSTANT = 266; 
	
	private TS ts;
	private AnalizadorLexico al;
	private Messages m;
	
	public SA3(TS ts, AnalizadorLexico al, Messages m) {
		this.ts = ts;
		this.al = al;
		this.m = m;
	}

	public Token execute(Token token, char caracter) {
		token.setId(CONSTANT);
		Float f = Float.valueOf(token.getLexema().replace('e', 'E'));
		token.setLexema(String.valueOf(f));
		if(ts.hasLexema(token.getLexema())) {
			ts.getTSEntry(token.getLexema()).incCounter();
			token.setTSEntry(ts.getTSEntry(token.getLexema()));
		}
		else {
			token.setId(CONSTANT);
			ts.addTSEntry(token.getLexema(), token.getETS());
			ts.getTSEntry(token.getLexema()).setType("FLOAT");
		}
		token.readCharNotAdded();
	
		return token;
	}
}