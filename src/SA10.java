public class SA10 extends SemanticAction { 

	public SA10(){}

    public Token execute(Token token, char c) {
        token = new Token();
        token.readCharAdded(); 
        return token;
    }
}