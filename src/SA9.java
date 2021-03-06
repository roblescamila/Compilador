public class SA9 extends SemanticAction {

		public static final Short CONSTANT = 266; 
		
		private TS ts;
		private AnalizadorLexico al;
		private Messages ms;
		
		public SA9 (TS ts, AnalizadorLexico al, Messages ms) {
			this.ts = ts;
			this.al = al;
			this.ms = ms;
		}

		public Token execute(Token token, char c) {
			token.setId(CONSTANT); 
			Long e = Long.valueOf(token.getLexema());
			token.setLexema(String.valueOf(e));
			if(ts.hasLexema(token.getLexema())) {
				ts.getTSEntry(token.getLexema()).incCounter();
				token.setTSEntry(ts.getTSEntry(token.getLexema()));
			}
			else {
				token.setId(CONSTANT);
				ts.addTSEntry(token.getLexema(), token.getETS());
				ts.getTSEntry(token.getLexema()).setType("INT");
			}
			token.readCharAdded();
			return token;
		}
}
