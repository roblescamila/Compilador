public class AnalizadorLexico {
	
	public static final short EOF = 276;
	
	private int line;
	private String srcCode;
	private TS symbTable;
	private int state;
	private int pos;
	private Token token;
	private SemanticAction SA1, SA2, SA3, SA4, SA5, SA6, SA7, SA8, SA9, SA10, SA11, SA12, SA13;
	private boolean eof;
	private boolean end;
    private Messages msg;
    private SemanticAction[][] matrixSA;
    
							//  esp '\n' '\t'  l   @   d   _   i   +   -   (   )   =   <   >   *   /   ;   ,   .   E   L  otro   {   }   e  eof   '
	private int[][] matrix = {{  0,   0,   0, 12,  1,  2, -1, 12, -1, -1, -1, -1,  7,  8,  7, -1,  9, -1, -1,  4, 13, 13,  -1,  -1, -1, 12,  0,  14}, //estado 0
							  { -1,  -1,  -1, 12, -1, -1, -1, 12, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1,  -1, -1, 12, -1,  -1}, //estado 1
							  { -1,  -1,  -1, -1, -1,  2,  3, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  4,  5, -1,  -1,  -1, -1,  5, -1,  -1}, //estado 2
							  { -1,  -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1,  -1, -1, -1, -1,  -1}, //estado 3
							  { -1,  -1,  -1, -1, -1,  4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  5, -1,  -1,  -1, -1,  5, -1,  -1}, //estado 4
							  { -1,  -1,  -1, -1, -1,  6, -1, -1,  6,  6, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1,  -1, -1, -1, -1,  -1}, //estado 5
							  { -1,  -1,  -1, -1, -1,  6, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1,  -1, -1, -1, -1,  -1}, //estado 6
							  { -1,  -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1,  -1, -1, -1, -1,  -1}, //estado 7
							  { -1,  -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1,  -1, -1, -1, -1,  -1}, //estado 8
							  { -1,  -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 10, -1, -1, -1, -1, -1,  -1,  -1, -1, -1, -1,  -1}, //estado 9
							  { 10,  10,  10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 11, 10, 10, 10, 10, 10,  10,  10, 10, 10, -1,  10}, //estado 10
							  { 10,  10,  10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,  0, 10, 10, 10, 10, 10,  10,  10, 10, 10, -1,  10}, //estado 11
							  { -1,  -1,  -1, 12, 12, 12, -1, 12, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1,  -1, -1, 12, -1,  -1}, //estado 12							
							  { -1,  -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 13, 13,  -1,  -1, -1, -1, -1,  -1}, //estado 13							
	 						  { 14,  -1,  14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14,  14,  14, 14, 14, -1,  15}, //estado 14							
	 						  { -1,  -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1,  -1, -1, -1, -1,  14}, //estado 15							
							  };
	public AnalizadorLexico(String cf, Messages m){
		symbTable = new TS();
        msg = m;
        this.srcCode = cf;
        this.line = 1;
        eof = false;
        end = false;
        pos = 0;
        initSA();
    }
	
	public int yylex(){
		this.state = 0;
		this.token = new Token();
		while (state != -1 && !eof){
			char caracter = srcCode.charAt(pos);                      	
			int simbolo = getColumna(caracter);                                
			token = (matrixSA[state][simbolo]).execute(token, caracter);
			if (!token.getCharAdded())                                      
				pos--;                                                    	
			if (caracter == '\n' && token.getCharAdded())                  
				line++;                                                   
	        pos++;                                                        
	        state = matrix[state][simbolo];                          
	    }
        if (eof && !end){
            msg.SymbolsTable();
            end = true;
            return EOF; 
        }
        if (eof && end)
        	return 0;
        return token.getId().intValue();
    }

    public Token getToken(){
        return token;
    }
	
	public int getLine(){
	    return this.line;
	}
	
	public int getColumna(int caracter) {
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
		if (caracter == ' '){
            eof = true;
            return 26;
        }
        if (caracter == 39)
        	return 27;
        return 22;
	}
	
	private void initSA(){
		matrixSA = new SemanticAction[16][28];
		SA1 = new SA1();
		SA2 = new SA2();
		SA3 = new SA3(symbTable, this, msg);
		SA4 = new SA4(symbTable, msg, this);
		SA5 = new SA5(msg, this);
		SA6 = new SA6(msg, this);
		SA7 = new SA7();
		SA8 = new SA8(msg,this);
		SA9 = new SA9(symbTable, this, msg);
		SA10 = new SA10();
		SA11 = new SA11(symbTable,msg, this);
		SA12 = new SA12(symbTable, this, msg);
		SA13 = new SA13(symbTable, msg, this);

		//estado 0
		matrixSA[0][0] = SA7;
		matrixSA[0][1] = SA7;
		matrixSA[0][2] = SA7;
		matrixSA[0][3] = SA1;
		matrixSA[0][4] = SA1;
		matrixSA[0][5] = SA1;
		matrixSA[0][6] = SA11;
		matrixSA[0][7] = SA1; 
		matrixSA[0][8] = SA8;	
		matrixSA[0][9] = SA8;	
		matrixSA[0][10] = SA8; 
		matrixSA[0][11] = SA8;
		matrixSA[0][12] = SA1;	
		matrixSA[0][13] = SA1;	
		matrixSA[0][14] = SA1;	
		matrixSA[0][15] = SA8;
		matrixSA[0][16] = SA1;
		matrixSA[0][17] = SA8;	
		matrixSA[0][18] = SA8;
		matrixSA[0][19] = SA1;	
		matrixSA[0][20] = SA1;	
		matrixSA[0][21] = SA1;	
		matrixSA[0][22] = SA11; 
		matrixSA[0][23] = SA8;
		matrixSA[0][24] = SA8;	
		matrixSA[0][25] = SA1;	
		matrixSA[0][26] = SA8;
		matrixSA[0][27] = SA10;	

		//estado 1
		for (int i = 0; i < 28; i++)
			matrixSA[1][i] = SA11;
		matrixSA[1][3] = SA2;
		matrixSA[1][7] = SA2;
		matrixSA[1][25] = SA2;
		
		//estado 2
		for (int i = 0; i < 28; i++)
			matrixSA[2][i] = SA11;
		matrixSA[2][5] = SA2;
		matrixSA[2][6] = SA7;
		matrixSA[2][19] = SA2;	
		matrixSA[2][20] = SA2;	
		matrixSA[2][25] = SA2;
		
		//estado 3
		for (int i = 0; i < 28; i++)
			matrixSA[3][i] = SA11;
		matrixSA[3][7] = SA9;
		
		//estado 4
		for (int i = 0; i < 28; i++)
			matrixSA[4][i] = SA3;	
		matrixSA[4][5] = SA2;	
		matrixSA[4][20] = SA2;		
		matrixSA[4][25] = SA2;
		
		//estado 5
		for (int i = 0; i < 28; i++)
			matrixSA[5][i] = SA11;
		matrixSA[5][5] = SA2;
		matrixSA[5][8] = SA2;
		matrixSA[5][9] = SA2;
		
		//estado 6
		for (int i = 0; i < 28; i++)
			matrixSA[6][i] = SA3;	
		matrixSA[6][5] = SA2;	

		//estado 7
		for (int i = 0; i < 28; i++)
			matrixSA[7][i] = SA6;	
		matrixSA[7][12] = SA5;
		
		//estado 8
		for (int i = 0; i < 28; i++)
			matrixSA[8][i] = SA6;	
		matrixSA[8][12] = SA5;	
		matrixSA[8][14] = SA5;
		
		//estado 9
		for (int i = 0; i < 28; i++)
			matrixSA[9][i] = SA6;	
		matrixSA[9][16] = SA10;	

		//estado 10
		for (int i = 0; i < 28; i++)
			matrixSA[10][i] = SA7;	
		
		//estado 11
		for (int i = 0; i < 28; i++)
			matrixSA[11][i] = SA7;	
		
		//estado 12
		for (int i = 0; i < 28; i++)
			matrixSA[12][i] = SA13;
		matrixSA[12][3] = SA2;
		matrixSA[12][4] = SA2;
		matrixSA[12][5] = SA2;
		matrixSA[12][7] = SA2;
		matrixSA[12][25] = SA2;
		
		//estado 13
		for (int i = 0; i < 28; i++)
			matrixSA[13][i] = SA4;
		matrixSA[13][20] = SA2;
		matrixSA[13][21] = SA2;
		
		//estado 14
		for (int i = 0; i < 28; i++)
			matrixSA[14][i] = SA2;
		matrixSA[14][1] = SA11;
		
		//estado 15
		for (int i = 0; i < 28; i++)
			matrixSA[15][i] = SA12;
		matrixSA[15][27] = SA2;
	}
	
	public String getMessage(int n){ 
		switch(n){
	     	
	        //Errores Sintácticos
	        case 1: return "El programa finalizó con errores. Error número 1.";
	        case 2: return "Falta el bloque de sentencias ejecutables. Error número 2.";    
	        case 3: return "Falta el bloque de sentencias declarativas. Error número 3.";
	        case 4: return "Se esperaba un ';'. Error número 4.";
	        case 5: return "Falta el tipo de la declaración. Error número 5.";
	        case 6: return "Sentencia declarativa incorrecta. Error número 6.";
	        case 7: return "Falta el identificador de la asignación. Error número 7.";
	        case 8: return "Falta el identificador de la asignación y se esperaba un ';'. Error número 8.";
	        case 9: return "Bloque de sentencias sin finalizar falta END. Error número 9.";    
	        case 10: return "Bloque de sentencias sin inicializar falta BEGIN. Error número 10.";
	        case 11: return "Falta abrir paréntesis '('. Error número 11.";
	        case 12: return "Falta cerrar paréntesis ')'. Error número 12.";
	        case 13: return "Parámetro del imprimir incorrecto. Error número 13.";
	        case 14: return "Falta palabra reservada 'PRINT'. Error número 14.";  
	        case 15: return "Sentencia incorrecta. Error número 15.";
	        case 16: return "Ámbito sin finalizar falta '}'. Error número 16.";    
	        case 17: return "Falta nombre del ámbito. Error número 17.";
	        case 18: return "Falta LOOP en iteración. Error número 18.";
	        case 19: return "Falta FROM en iteración. Error número 19.";
	        case 20: return "Falta TO en iteración. Error número 20.";
	        case 21: return "Falta BY en iteración. Error número 21.";
	        case 22: return "Falta una variable en iteración. Error número 22.";
	        case 23: return "Falta expresión. Error número 23."; 
	        case 24: return "Falta comparador. Error número 24.";
	
	        //Errores Léxicos
	        case 101: return "Float fuera de rango. Error número 101.";
	        case 102: return "Carácter no identificado. Error número 102.";
	        case 103: return "Construcción de token erróneo. Error número 103.";
	        case 104: return "Constante entero fuera de rango permitido. Error número 104.";
	        case 105: return "Constante fuera de rango permitido. Error número 105.";
	        
	        // Estructuras Sintacticas
	        case 201: return "Sentencia declarativa.";
	        case 202: return "Sentencia de asignación.";
	        case 203: return "Sentencia de selección.";
	        case 204: return "Sentencia de iteración.";
	        case 205: return "Sentencia de impresión de caracteres.";
	        case 206: return "Bloque de sentencias.";
	        case 207: return "Ámbito con nombre.";
	        case 208: return "Conversión explícita.";		
	        
	        //Errores Semanticos
	        case 301: return "Variable no declarada. Error número 301.";
	        case 302: return "No se puede realizar la operacion. Tipos incompatibles. Error número 302.";
	        case 303: return "Construcción de token erróneo. Error número 3103.";
	        case 304: return "Constante entero fuera de rango permitido. Error número 3104.";
	        case 305: return "Constante fuera de rango permitido. Error número 305.";
	        
		}
	    return null;
	}	
	
    public TS getTS(){
        return symbTable;
    }
    
    public void print (){
    	symbTable.showTable();
    }
}