	public class SA1 extends SemanticAction{ 	
	public Token execute(Token token, char c) {
	   token = new Token();
	   token.addChar(c);
	   return token;
	}
}