public class SA2 extends SemanticAction{ 
	
	public Token execute(Token token, char c) { 
		token.addChar(c);
		return token;
	}
}