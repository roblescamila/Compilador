public class SA12 extends SemanticAction{
	
	public static final short STRING = 268; 
	
	private TS ts;
	
	public SA12(TS ts, AnalizadorLexico al, Messages m) {
		this.ts = ts;
	}
	
	public Token execute(Token token, char caracter) {
		String a = token.getLexema();
		a = a.substring(0, token.getLexema().length()-1);
		token.setLexema(a);
		if(ts.hasLexema(token.getLexema())) {
			ts.getTSEntry(token.getLexema()).incCounter();
			token.setTSEntry(ts.getTSEntry(token.getLexema()));
		}
		else {
			token.setId(STRING);
			ts.addTSEntry(token.getLexema(), token.getETS());
			ts.getTSEntry(token.getLexema()).setType("STRING");
		}
		token.readCharNotAdded();
		return token;
		
		
	}
}
