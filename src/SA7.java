public class SA7 extends SemanticAction{
	
	public SA7(){}

    public Token execute(Token token, char c) {
        token.readCharAdded();
        return token;
    }
}
