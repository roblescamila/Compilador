public class SA11 extends SemanticAction { 

		private Messages m;
	    private AnalizadorLexico al;
		private TS ts;

	    public SA11(TS ts,Messages ms, AnalizadorLexico a) {
	        m = ms;
	        al = a;
			this.ts = ts;

	    }

	    public Token execute(Token token, char c) {
	    	if (entry(c) == 22) 	    	{
	    		token.addChar(c);
	    	 m.token(al.getLine(), "No identificado");
	    	 m.token(al.getLine(), token.getLexema());
	         token.setId((short) 400);
	    	 Error e = new Error(al.getLine(), al.getMessage(102), "Léxico");
	    	  m.addError(e);}
	    	else{	
	    	token.addChar(c);
	    	if (ts.hasLexema(token.getLexema())){
		        token.readCharNotAdded();
		        token.setTSEntry(ts.getTSEntry(token.getLexema()));
		        m.token(al.getLine(), token.getLexema());
		        return token;
		    }
		    else {
		            token.setId((short) 404);
		 
		       
		        m.token(al.getLine(), token.getLexema());
		        token.readCharNotAdded();    
		       
		        }
			Error e = new Error(al.getLine(), al.getMessage(103), "Léxico");
  m.addError(e);}
			return token;
//			
//	    	token = new Token();
//	        int aux = entry(c);
//	        if (aux == 22){
////	            token.readCharAdded();
//	            Error e = new Error(al.getLine(), al.getMessage(102), "Léxico");
//	            ms.addError(e);
//	        }
//	        else{
//		    	  Error e = new Error(al.getLine(), al.getMessage(103), "Léxico");
//		    	  ms.addError(e); 
//		    	  
//	        }
//            token.readCharAdded();
//	        return token;
	    }

		public int entry(int caracter) {
			if ((caracter == 9))
		        return 0;
			if (caracter == '\n')
			    	return 1;
			if ((caracter == 32))
				 return 2;
			if ((caracter >= 97 && caracter <= 122))
				if (caracter != 'e') 
					if	(caracter != 'i')
						return 3;
			if (caracter == '@')
				return 4;
			if (caracter >= 48 && caracter <= 57)
				return 5;
			if (caracter == '_')
				return 6;
			if ((caracter == 'i'))
				return 7;
			if (caracter == '+')
				return 8;
			if (caracter == '-')
				return 9;
			if (caracter == '(')
				return 10;
			if (caracter == ')')
				return 11;
			if (caracter == '=')
				return 12;
	        if (caracter == '<')
	            return 13;  
	        if (caracter == '>')
		        return 14;
	        if (caracter == '*')
	        	return 15;
		    if (caracter == '/')
		        return 16;
		    if (caracter ==';')
				return 17;
		    if (caracter ==',')
				return 18;
		    if (caracter =='.')
				return 19;
		    if ((caracter == 'E'))
				return 20;
			if ((caracter >= 65 && caracter <= 90))
				if ((caracter != 'E'))
	                return 21;
			if (caracter =='{')
		    	return 23;
		    if (caracter =='}')
				return 24;
			if ((caracter =='e'))
	            return 25;
			if (caracter == ' ')
	            return 26;
	        if (caracter == 39)
	        	return 27;
	        return 22;
		}
}