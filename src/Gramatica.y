%{
import java.util.Vector;
import java.util.Enumeration;
%}

%token IF THEN ELSE ENDIF PRINT INT LOOP TO ID CONSTANT FLOAT STRING COMPARATOR ASIGNATION END GLOBAL BEGIN FROM TOFLOAT EOF BY

/* GRAMATICA */
%%

/* PROGRAMA */

p : declarations exe EOF
	| declarations exe error EOF {Error e = new Error(analyzer.getLine(),analyzer.getMessage(1),"Sintactico"); msj.addError(e);}
	| declarations error EOF {Error e = new Error(analyzer.getLine(),analyzer.getMessage(2),"Sintactico"); msj.addError(e);}
	| error exe EOF {Error e = new Error(analyzer.getLine(),analyzer.getMessage(3),"Sintactico"); msj.addError(e);}
;

ambit_declarations	:	ambit_declarations	ambit_dec_sentence { 
																
												//				System.out.println("1 estoy en el ambito nivel " + ambit_level + " y me llamo " +name );
                                             String suf ="";
											names[ambit_level] = name;
												for (int i = 0 ; i <= ambit_level; i++)
                                           {	//	//System.out.println("9 en el arreglo tengo " + names[i]);
												
												suf = suf + "_" + names[i];}
									suffix = suf;
											
											Vector<Token> tokens = (Vector<Token>)$1.obj;
											
										
											for (int i =0; i <tokens.size(); i++)
											{
												Token token = tokens.elementAt(i);
												TSEntry e = new TSEntry(token.getId(),token.getLexema() + suffix);
												
												e.setType(tipo);
												System.out.println(" TABLA DE SIMBOLOS");
												table.showTable();
												if ((!table.hasLexema(e.getLexema())) || ((table.hasLexema(e.getLexema())) && (!table.isDeclared(e.getLexema())))){
												//System.out.println(token.getLexema());
													//System.out.println("999 meti a " + e.getLexema());
													
												table.addTSEntry(e.getLexema(), e);}
												else
												{
													//System.out.println("el siguente lexema ya existia en la tabla tire error: " + e.getLexema());
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(306),"Semantico"); 
												msj.addError(er);
												}
										
											}
												

																$$.obj = $1.obj; 
																level_up = false;
																}
					|	ambit_dec_sentence { 
																String suf ="";
											names[ambit_level] = name;
												for (int i = 0 ; i <= ambit_level; i++)
                                           {//System.out.println("9 en el arreglo tengo " + names[i]);
												
												suf = suf + "_" + names[i];}
									suffix = suf;
											
											Vector<Token> tokens = (Vector<Token>)$1.obj;
											
										
											for (int i =0; i <tokens.size(); i++)
											{
												Token token = tokens.elementAt(i);
												TSEntry e = new TSEntry(token.getId(),token.getLexema() + suffix);
												System.out.println(" TABLA DE SIMBOLOS");
												table.showTable();
												//System.out.println("la tabla tiene el lexema me me devuelve " +table.hasLexema(e.getLexema()) + " y si esta declarada me devuelve " + e.isDeclared() + "el tipo de la variable es "+ e.getType() );
												if ((!table.hasLexema(e.getLexema())) || ((table.hasLexema(e.getLexema())) && (!table.isDeclared(e.getLexema()))))
												{
												e.setType(tipo);
													//System.out.println("888 meti a " + e.getLexema() + " estoy en la iteracion "+ i );
											
												table.addTSEntry(e.getLexema(), e);
												}
												else{
												//System.out.println("el siguente lexema ya existia en la tabla tire error: " + e.getLexema());
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(306),"Semantico"); 
												msj.addError(er);}
										
											}
											//					System.out.println("2 estoy en el ambito nivel " + ambit_level + " y me llamo " +name );

																$$.obj = $1.obj; 
																level_up = false;
																}
;

var_list3 : var_list3 ',' ID {Vector<Token> tokens = (Vector<Token>)$1.obj;

                                $$.obj=tokens;}


ambit_dec_sentence	:	declaration2 { 

										$$.obj = $1.obj; 
										level_up = false;
									}
									
									


| ID {
									    Vector<Token> tokens = new Vector<Token>();
										Token token = (Token)$1.obj;
										$$.obj=tokens;
										
										}
									
									
                      |	GLOBAL var_list3 ';' {  Vector<Token> tokens = (Vector<Token>)$2.obj;
					                            for(int i = 0; i <= tokens.size(); i++)
												{
												Token token = tokens.elementAt(i);
												table.getTSEntry(token.getLexema()+suffix).setLexema(token.getLexema()+"_0");
												table.setKey(token.getLexema()+suffix,token.getLexema()+"_0");
												}
												
												
											}//modificar para que sean globales
; 

declaration2 : type2 var_list2 ';' 	{	
										ES s = new ES(analyzer.getLine(), analyzer.getMessage(201)); msj.addStructure(s);
										Enumeration e = ((Vector<Token>)vt).elements();
										String type = ((Token)$1.obj).getLexema();
											tipo= type;
									//	while (e.hasMoreElements()){
										//	Token token = (Token)e.nextElement();
											//tiposdevariables.add(type);
											//System.out.println("Quiero declarar la variable, "+ token.getLexema()+suffix + " y en la tabla tengo ");
										//table.showTable();	
										
									/*if (table.hasLexema(token.getLexema()+suffix) && (table.getTSEntry(token.getLexema()+suffix).isDeclared())){
											Error er = new Error(analyzer.getLine(),analyzer.getMessage(306),"Semantico"); 
												msj.addError(er);	
											}
											else
											if (table.hasLexema(token.getLexema()+suffix))
											{
                                                  token.getETS().setType(type);
												token.getETS().setId((short)266);											
											
											}
											*/									$$.obj = $2.obj;
									
									}
										
			
			| type2 var_list2 error	{Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
			| error var_list2 ';' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(5),"Sintactico"); msj.addError(e);}
			| type2 error ';' 			{Error e = new Error(analyzer.getLine(),analyzer.getMessage(6),"Sintactico"); msj.addError(e);}
;

var_list2 : var_list2 ',' ID		{	
										Vector<Token> tokens = (Vector<Token>)$1.obj;
										Token token = (Token)$3.obj;
										//tokens.add(token);
										TSEntry e = table.getTable().get(token.getLexema());
										String aux= table.getTable().get(token.getLexema()).getLexema();
										e.setLexema( aux+ suffix);
										table.getTable().put(token.getLexema() + suffix,e);
										table.getTable().remove(aux);
										//token.setLexema(token.getLexema() + suffix);
										vt.add(token);
										vt_amb.add(token);		
										$$.obj = tokens;			
									}								
			| ID 				{	
										Vector<Token> tokens = new Vector<Token>();
										Token token = (Token)$1.obj;
										
										if (!level_up){
											ambit_level++;
											level_up = true;
											
											//System.out.println("3 estoy en el ambito nivel " + ambit_level + " y me llamo " +name );
											names[ambit_level] = name;
									//		 System.out.println("300 en el arreglo tengo " + names[ambit_level]);
											String suf = "";
											for (int i = 0; i <= ambit_level; i++){
													
										//	System.out.println("4 estoy en el ambito nivel " + ambit_level + " y me llamo " +name );
													
											suf = suf + "_" + names[i];
											
											}
											suffix = suf;
											
											//System.out.println("4 estoy en el ambito nivel " + ambit_level + " y me llamo "  +name );
											//System.out.println(" el sufijo se llama "+ suffix);
										}									
										TSEntry e = table.getTable().get(token.getLexema());
										String aux =table.getTable().get(token.getLexema()).getLexema();
										e.setLexema( aux+ suffix);
										// table.addTSEntry(token.getLexema() + suffix, e);
										//System.out.println("600 meti a " + e.getLexema());
										table.remove(aux);
										//token.setLexema(token.getLexema() + suffix);
										tokens.add(token);
										vt_amb.add(token);
										vt = tokens;
										
										$$.obj = tokens;
								}
;			   
				   
type2 : INT {tipo = "INT";}
	 | FLOAT {tipo = "FLOAT";}
	 | STRING {tipo = "STRING";}
;

declarations : declarations declaration 
			  | declaration
;

declaration : type var_list ';' 	{
										ES s = new ES(analyzer.getLine(), analyzer.getMessage(201)); msj.addStructure(s);
										
										Enumeration e = ((Vector<Token>)vt).elements();
										String type = ((Token)$1.obj).getLexema();
										tipo=type;
										while (e.hasMoreElements()){
											Token token = (Token)e.nextElement();
											//tiposdevariables.add(type);
										
										//System.out.println("Quiero declarar la variable, "+ token.getLexema()+suffix + " y en la tabla tengo ");
										//table.showTable();	
											/*if (table.hasLexema(token.getLexema()+suffix) && (table.getTSEntry(token.getLexema()+suffix).isDeclared())){
											Error er = new Error(analyzer.getLine(),analyzer.getMessage(306),"Semantico"); 
												msj.addError(er);	
											}
											else
											if (table.hasLexema(token.getLexema()+suffix))
											{
                                                  token.getETS().setType(type);
												token.getETS().setId((short)266);											
											
											}*/
										}
	$$.obj = $2.obj;									
									}
									
															
			| type var_list error	{Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
			| error var_list ';' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(5),"Sintactico"); msj.addError(e);}
			| type error ';' 			{Error e = new Error(analyzer.getLine(),analyzer.getMessage(6),"Sintactico"); msj.addError(e);}
;

var_list : var_list ',' ID		{	
									Token token = (Token)$3.obj;
									token.setType("ID");
									String suf = "";
									for (int i = 0 ; i <= ambit_level; i++)
										suf = suf + "_" + names[i];
									suffix = suf;
									TSEntry e = table.getTable().get(token.getLexema());
									e.setType(tipo);
									String aux =table.getTable().get(token.getLexema()).getLexema();
									e.setLexema( aux+ suffix);
									table.showTable();
									if (!table.hasLexema(e.getLexema())){
									//System.out.println("600 meti a " + e.getLexema());
									table.getTable().put(e.getLexema() , e);
									table.getTable().remove(aux);}
									else
									{
									System.out.println("el siguente lexema ya existia en la tabla tire error: " + e.getLexema());
									Error er = new Error(analyzer.getLine(),analyzer.getMessage(306),"Semantico"); 
												msj.addError(er);
									}
									//token.setLexema(token.getLexema()+suffix);
									vt.add(token);
									vt_amb.add(token);
									$$.obj = $1.obj;
								}								
			| ID 				{										
									Vector<Token> tokens = new Vector<Token>();
									Token token = (Token)$1.obj;
									token.setType("ID");
									TSEntry e = table.getTable().get(token.getLexema());
		                            
									String aux = table.getTable().get(token.getLexema()).getLexema();
									e.setLexema( aux + "_0");
									e.setType(tipo);
									table.showTable();
									if (!table.hasLexema(e.getLexema()))
									{table.addTSEntry(e.getLexema() , e); //puede que este mal borrar el guion bajo ero
									//System.out.println("700 meti a " + e.getLexema());
									table.remove(aux);
									}
									else
									{
									System.out.println("el siguente lexema ya existia en la tabla tire error: " + e.getLexema());
									Error er = new Error(analyzer.getLine(),analyzer.getMessage(306),"Semantico"); 
												msj.addError(er);
									}
									//token.setLexema(token.getLexema() + "_0");
									tokens.add(token);
									vt_amb.add(token);
									vt = tokens ;
									$$.obj = $1.obj;
								}
;			   
				   
type : INT {tipo = "INT";}
	 | FLOAT {tipo = "FLOAT";}
	 | STRING{tipo = "STRING";}
;


/* SENTENCIAS EJECUTABLES */

exe : exe sentence
	| exe ambit {
		name = ((Token)$2.obj).getLexema();
											//System.out.println("5 estoy en el ambito nivel " + ambit_level + " y me llamo " +name );
											
											if (ambit_level ==0)
											resetearambitos ();
											else
											{name = names[ambit_level];
											//names[ambit_level] = "";
											}
											} // si esto no anda formatear el arreglo
	| sentence
	| ambit {
				name = ((Token)$1.obj).getLexema();
				//System.out.println(" 6 estoy en el ambito nivel " + ambit_level + " y me llamo " +name );
				names[ambit_level] = name;}
;

sentence : asignation
		  | selection
		  | iteration
		  | print
		  | conversion
		  | error ';' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(15),"Sintactico"); msj.addError(e);}
;

asignation: ID ASIGNATION expression ';' {	
//System.out.println(" 1000000000000000000 este es el sufijo en la asignacion " + suffix);

ES es = new ES(analyzer.getLine(), analyzer.getMessage(202)); 
											msj.addStructure(es); 
											String string = ((Token)$1.obj).getLexema();
											String string2 = (String)$3.obj;
											//System.out.println(" 1000000000000000000 mi op1 es  " + string);
											//System.out.println(" 1000000000000000000 mi op2 es  " + string2);
										//table.showTable();
											
											
											TSEntry op2 = table.getTSEntry(string2);
												TSEntry op1 = table.getTSEntry(string+ suffix);
											char c = string2.charAt(0);
											
											
											String  a= suffix;
											int b= ambit_level;
											if((!table.hasLexema(string+ a)) || (!op1.isDeclared()))
											{while ((b != -1)&&((!table.hasLexema(string+ a)) ))
											{ String[] separada = a.split("_"); 
											    for (int i = 1 ;i<=b; i++)
												System.out.println("el split me dividio en "+separada[i]);
												a="";
												for (int i = 1; i<=b; i++)
												a+="_"+separada[i];	
												b--;
												 System.out.println("----------------------- quize trabajar con " + string+ a );
											}
											if (b ==-1)
											{
										   System.out.println("----------------------- quize trabajar con " + string+ a );
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(301),"Semantico1111"); 
												msj.addError(er);
											}
											} 
											op1 = op1 = table.getTSEntry(string+ a);
											Terceto t = new Terceto(s.size(), "=", string+ a, string2);
											if (c == '[') {
												String subst = string2.substring(1,string2.length()-1);
												Terceto op = s.get(subst);
												if (op1.isDeclared() && op1.getType() != op.getType()){	
													Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
													msj.addError(er);
													t.setType("error");
												}
											}
											
											s.add(t);
											$$.obj = t;
										 }
		  | ID ASIGNATION expression error {Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
		  | error ASIGNATION expression ';' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(7),"Sintactico"); msj.addError(e);}
		  | error ASIGNATION expression {Error e = new Error(analyzer.getLine(),analyzer.getMessage(8),"Sintactico"); msj.addError(e);}
;

selection : selection_simple ELSE bloque ENDIF {ES s = new ES(analyzer.getLine(), analyzer.getMessage(203)); msj.addStructure(s);}
		  | selection_simple ELSE sentence ENDIF {ES s = new ES(analyzer.getLine(), analyzer.getMessage(203)); msj.addStructure(s);}
		  | selection_simple ENDIF {ES s = new ES(analyzer.getLine(), analyzer.getMessage(203)); msj.addStructure(s);}
;

selection_simple : IF condition THEN bloque
                 | IF condition THEN sentence
;

iteration : LOOP FROM ID ASIGNATION ID TO ID BY ID bloque {ES s = new ES(analyzer.getLine(), analyzer.getMessage(204)); msj.addStructure(s);}
		  | LOOP FROM ID ASIGNATION ID TO ID BY ID sentence {ES s = new ES(analyzer.getLine(), analyzer.getMessage(204)); msj.addStructure(s);}
		  | error FROM ID ASIGNATION ID TO ID BY ID bloque {Error e = new Error(analyzer.getLine(),analyzer.getMessage(18),"Sintactico"); msj.addError(e);}
		  | LOOP error ID ASIGNATION ID TO ID BY ID bloque {Error e = new Error(analyzer.getLine(),analyzer.getMessage(19),"Sintactico"); msj.addError(e);}
		  | LOOP FROM error ASIGNATION ID TO ID BY ID bloque {Error e = new Error(analyzer.getLine(),analyzer.getMessage(22),"Sintactico"); msj.addError(e);}
		  | LOOP FROM ID error ID TO ID BY ID bloque {Error e = new Error(analyzer.getLine(),analyzer.getMessage(7),"Sintactico"); msj.addError(e);}
		  | LOOP FROM ID ASIGNATION error TO ID BY ID bloque {Error e = new Error(analyzer.getLine(),analyzer.getMessage(22),"Sintactico"); msj.addError(e);}
		  | LOOP FROM ID ASIGNATION ID error ID BY ID bloque {Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
		  | LOOP FROM ID ASIGNATION ID TO error BY ID bloque {Error e = new Error(analyzer.getLine(),analyzer.getMessage(22),"Sintactico"); msj.addError(e);}
		  | LOOP FROM ID ASIGNATION ID TO ID error ID bloque {Error e = new Error(analyzer.getLine(),analyzer.getMessage(21),"Sintactico"); msj.addError(e);}
		  | LOOP FROM ID ASIGNATION ID TO ID BY error bloque {Error e = new Error(analyzer.getLine(),analyzer.getMessage(22),"Sintactico"); msj.addError(e);}
		  | LOOP FROM ID ASIGNATION ID TO ID BY ID error {Error e = new Error(analyzer.getLine(),analyzer.getMessage(2),"Sintactico"); msj.addError(e);}
;

bloque_exe_sentences : bloque_exe_sentences sentence
					 | sentence
;

bloque: BEGIN bloque_exe_sentences END {ES s = new ES(analyzer.getLine(), analyzer.getMessage(206)); msj.addStructure(s);}
	  | BEGIN bloque_exe_sentences error {Error e = new Error(analyzer.getLine(),analyzer.getMessage(9),"Sintactico"); msj.addError(e);}
	  | END {Error e = new Error(analyzer.getLine(),analyzer.getMessage(10),"Sintactico"); msj.addError(e);}
;

ambit :  abrir_ambit ambit_declarations exe cerrar_ambit {
											ES s = new ES(analyzer.getLine(), analyzer.getMessage(207)); msj.addStructure(s);
											level_up = false;
											
											//System.out.println("7 estoy en el ambito nivel " + ambit_level + " y me llamo " +name );
											/*String suf ="";
											names[ambit_level] = name;
												for (int i = 0 ; i <= ambit_level; i++)
                                           {System.out.println("9 en el arreglo tengo " + names[i]);
												
												suf = suf + "_" + names[i];}
									suffix = suf;
											
											Vector<Token> tokens = (Vector<Token>)$2.obj;
											
										
											for (int i =0; i <tokens.size(); i++)
											{
												Token token = tokens.elementAt(i);
												TSEntry e = new TSEntry(token.getId(),token.getLexema() + suffix);
												
												e.setType(tipo);
												System.out.println(token.getLexema());
													System.out.println("100 meti a " + e.getLexema());
													System.out.println("sufijo " + suffix);
												table.addTSEntry(token.getLexema(), e);
										
											}*/
											tiposdevariables.clear();
											ambit_level--;
											$$.obj = $1.obj;
										  }
	  |  abrir_ambit exe cerrar_ambit {
							ES s = new ES(analyzer.getLine(), analyzer.getMessage(207)); msj.addStructure(s);
							level_up = false;
					//		System.out.println(" 8 estoy en el ambito nivel " + ambit_level + " y me llamo " +name );
							name = ((Token)$1.obj).getLexema();
							names[ambit_level] = name;
							ambit_level--;
							$$.obj = $1.obj;
						}
	  
	  | abrir_ambit ambit_declarations error cerrar_ambit {Error e = new Error(analyzer.getLine(),analyzer.getMessage(2),"Sintactico"); msj.addError(e);}
	  |  abrir_ambit ambit_declarations exe error {Error e = new Error(analyzer.getLine(),analyzer.getMessage(16),"Sintactico"); msj.addError(e);}
;


abrir_ambit : ID '{' {name = ((Token)$1.obj).getLexema();}
cerrar_ambit : '}' {}
condition: '(' expression COMPARATOR expression ')' 
		| error expression COMPARATOR expression ')' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(11),"Sintactico"); msj.addError(e);}
		|  '(' expression COMPARATOR expression error {Error e = new Error(analyzer.getLine(),analyzer.getMessage(12),"Sintactico"); msj.addError(e);}
		|  '(' expression error expression ')' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(24),"Sintactico"); msj.addError(e);}
		|  '(' error COMPARATOR expression ')' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(23),"Sintactico"); msj.addError(e);}
		|  '(' expression COMPARATOR error ')' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(23),"Sintactico"); msj.addError(e);}
;

print: PRINT '(' STRING ')' ';' {	ES es = new ES(analyzer.getLine(), analyzer.getMessage(205)); 
									msj.addStructure(es);
									String lexema = ((Token)$3.obj).getLexema();
									Terceto t = new Terceto (s.size(), "PRINT", lexema, "");
									s.add(t);
									$$.obj = t;
								}
	 | PRINT '(' STRING ')' error {Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
	 | PRINT '(' error ')' ';' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(13),"Sintactico"); msj.addError(e);}
	 | error '(' STRING ')' ';' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(14),"Sintactico"); msj.addError(e);}
;

conversion: TOFLOAT '(' expression ')' ';' {
											ES es = new ES(analyzer.getLine(), analyzer.getMessage(208)); 
											msj.addStructure(es);
											String lexema = (String)$3.obj;
											Terceto t = new Terceto (s.size(), "TOFLOAT", lexema, "-");
											char c = lexema.charAt(0);
											if (c == '[') {
												String subst = lexema.substring(1, lexema.length()-1);
												Terceto t1 = s.get(subst);
												if (!t1.getType().equals("FLOAT")){ 	
													Error er = new Error(analyzer.getLine(),analyzer.getMessage(307),"Semantico");
													msj.addError(er);
													t.setType("error");
												}
												else{
													t.setType("FLOAT");
													//setear todos los ID a float
												}
											}
											else{
												if (!table.hasLexema(lexema)){
													Error er = new Error(analyzer.getLine(),analyzer.getMessage(301),"Semantico");
													msj.addError(er);
													System.out.println("XXXXXXXXX");
													t.setType("error");
												}
												else if (table.getTSEntry(lexema).getType().equals("FLOAT")){ 	
													Error er = new Error(analyzer.getLine(),analyzer.getMessage(307),"Semantico");
													msj.addError(er);
													t.setType("error");
												}
												else {
													table.getTSEntry(lexema).setType("FLOAT");	

											}}
											s.add(t);
											$$.obj = t;
											}
		  | TOFLOAT '(' expression ')' error {Error e = new Error(analyzer.getLine(),analyzer.getMessage(23),"Sintactico"); msj.addError(e);}
		  | TOFLOAT '(' error ')' ';' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(13),"Sintactico"); msj.addError(e);}
;

expression : expression '+' term { 	
									String string = (String)$1.obj;
									String string2 = (String)$3.obj;
									
									
									//System.out.println("YYYYYYYYYYYYYYYYYYYYestoy en la suma y el op1 es "+ string) ;
									//System.out.println("XXXXXXXXXXXXXXXXXXXX estoy en la suma y el  op2 es "+ string2) ;
									//table.showTable();
									TSEntry op1 = table.getTSEntry(string);							
									TSEntry op2 = table.getTSEntry(string2);
									//op1.imprimir();
									char c = string.charAt(0);
									char c2 = string2.charAt(0);
									Terceto t = new Terceto(s.size(),"+", (String)$1.obj, (String)$3.obj);
									if ((c == '[')  && (c2 == '[')){
										String subst = string.substring(1,string.length()-1);
										String subst2 = string2.substring(1,string2.length()-1);
										Terceto op11 = s.get(subst);
										Terceto op22 = s.get(subst2);
										if (!op11.getType().equals(op22.getType())){ 	
											Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
											msj.addError(er);
											t.setType("error");
										}
										else
											t.setType(op11.getType());
									}
									else{
										if (c == '[') {
											String subst = string.substring(1,string.length()-1);
											Terceto op = s.get(subst);
											if (!op.getType().equals(op2.getType())){ 	
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
												msj.addError(er);
												t.setType("error");
											}
											else
												t.setType(op2.getType());
										}
										else if (c2 == '[') {
											String subst = string2.substring(1,string2.length()-1);
											Terceto op = s.get(subst);
											if (!op1.getType().equals(op.getType())){ 	
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
												msj.addError(er);
												t.setType("error");
											}
											else
												t.setType(op1.getType());
										}
										else 
										{ if ((!table.hasLexema(string))  ||(! table.hasLexema(string2)))
										      {Error er = new Error(analyzer.getLine(),analyzer.getMessage(301),"Semantico");
												msj.addError(er);
												t.setType("error");}
												else
											if (!op1.getType().equals(op2.getType())){ 	
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
												msj.addError(er);
												t.setType("error");
											}
											else
												t.setType(op1.getType());
										
										}
									}
									s.add(t);
									$$.obj = "[" + t.getId() + "]";
								}
		  | expression '-' term { 	
									String string = (String)$1.obj;
									String string2 = (String)$3.obj;
									TSEntry op1 = table.getTSEntry((String)$1.obj);
									TSEntry op2 = table.getTSEntry((String)$3.obj);
									char c = string.charAt(0);
									char c2 = string2.charAt(0);
									Terceto t = new Terceto(s.size(),"-", (String)$1.obj, (String)$3.obj);
									if ((c == '[')  && (c2 == '[')){
										String subst = string.substring(1,string.length()-1);
										String subst2 = string2.substring(1,string2.length()-1);
										Terceto op11 = s.get(subst);
										Terceto op22 = s.get(subst2);
										if (!op11.getType().equals(op22.getType())){ 	
											Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
											msj.addError(er);
											t.setType("error");
										}
										else
											t.setType(op11.getType());
									}
									else{
										if (c == '[') {
											String subst = string.substring(1,string.length()-1);
											Terceto op = s.get(subst);
											if (!op.getType().equals(op2.getType())){ 	
											Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
											msj.addError(er);
											t.setType("error");
											}
											else
												t.setType(op2.getType());
										}
										else if (c2 == '[') {
											String subst = string2.substring(1,string2.length()-1);
											Terceto op = s.get(subst);
											if (!op1.getType().equals(op.getType())){ 	
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
												msj.addError(er);
												t.setType("error");
											}
											else
												t.setType(op1.getType());
										}
										else 
										{ if ((!table.hasLexema(string))  ||(! table.hasLexema(string2)))
										      {Error er = new Error(analyzer.getLine(),analyzer.getMessage(301),"Semantico");
												msj.addError(er);
												t.setType("error");}
												else
											if (!op1.getType().equals(op2.getType())){ 	
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
												msj.addError(er);
												t.setType("error");
											}
											else
												t.setType(op1.getType());
										
										}
									}
									s.add(t);
									$$.obj = "[" + t.getId() + "]";
								}
		  | term {
					$$.obj = ((String)$1.obj);
				 }
;

term : term '*' factor { 	String string = (String)$1.obj;
							String string2  =(String)$3.obj;
							TSEntry op1 = table.getTSEntry((String)$1.obj);
							TSEntry op2 = table.getTSEntry((String)$3.obj);
							char c = string.charAt(0);
							Terceto viejo;
							Terceto t = new Terceto(s.size(),"*", (String)$1.obj, (String)$3.obj);
							if (c == '[') {
								String subst = string.substring(1,string.length()-1);
								viejo = s.get(subst);
								if (!viejo.getType().equals(op2.getType())){ 	
									Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
									msj.addError(er);
									t.setType("error");
								}
								else
									t.setType(viejo.getType());
							}
							else { if ((!table.hasLexema(string))  ||(! table.hasLexema(string2)))
										      {Error er = new Error(analyzer.getLine(),analyzer.getMessage(301),"Semantico");
												msj.addError(er);
												t.setType("error");}
												else
											if (!op1.getType().equals(op2.getType())){ 	
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
												msj.addError(er);
												t.setType("error");
											}
											else
												t.setType(op1.getType());
										
										}
							s.add(t);
							$$.obj = "[" + t.getId() + "]";
						}
		| term '/' factor { 
							String string = (String)$1.obj;
							String string2  =(String)$3.obj;
							TSEntry op1 = table.getTSEntry((String)$1.obj);
							TSEntry op2 = table.getTSEntry((String)$3.obj);
							char c = string.charAt(0);
							Terceto viejo;
							Terceto t = new Terceto(s.size(),"/", (String)$1.obj, (String)$3.obj);
							if (c == '[') {
								String subst = string.substring(1,string.length()-1);
								viejo = s.get(subst);
								if (!viejo.getType().equals(op2.getType())){ 	
									Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
									msj.addError(er);
									t.setType("error");
								}
								else
									t.setType(viejo.getType());
							}
							else { if ((!table.hasLexema(string))  ||(! table.hasLexema(string2)))
										      {Error er = new Error(analyzer.getLine(),analyzer.getMessage(301),"Semantico");
												msj.addError(er);
												t.setType("error");}
												else
											if (!op1.getType().equals(op2.getType())){ 	
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
												msj.addError(er);
												t.setType("error");
											}
											else
												t.setType(op1.getType());
										
										}
							s.add(t);
							$$.obj = "[" + t.getId() + "]";
						}					
		| factor { 
					String s1 = (String) $1.obj;
					TSEntry entry = table.getTSEntry(s1);
					try {
						if ((!table.hasLexema(s1) || (
						!entry.isDeclared()) && 
						entry.getId() == 265)){
							Error er = new Error(analyzer.getLine(),analyzer.getMessage(301),"Semantico"); 
							msj.addError(er);
						}
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
					yyval.obj = s1;
				}
;

factor : ID { 
			    String lexema = ((Token)$1.obj).getLexema();
				String lex;
				if(table.getTSEntry(lexema).getId() == 265){
					lex = lexema + suffix;
					boolean var_decl = false;
					for (int i = vt_amb.size() - 1; i >= 0; i--){
						if((vt_amb.elementAt(i).getLexema().charAt(0) == lex.charAt(0)) && !var_decl) {
							var_decl = true;
							lex = vt_amb.elementAt(i).getLexema();
						}
					}
				}
				else
					lex = lexema+ suffix;
				$$.obj = lexema+ suffix;
			}
	   | CONSTANT {	String newLexema = ((Token)$1.obj).getLexema();
					TSEntry entry = (TSEntry)table.getTable().get(newLexema);
					boolean anda = false;
					String a =	entry.getType();
					if (a == "INT"){
						Long e = Long.valueOf(newLexema);
						if ( e.longValue() <= Short.MAX_VALUE )
							anda = true;
						else{
							Error er = new Error(analyzer.getLine(),analyzer.getMessage(104),"Lexico"); 
							msj.addError(er);
						}
					}
					else{
						Double f = Double.valueOf(newLexema);								
						if (f.doubleValue() >= 1.17549435E-38 && f.doubleValue() <= 3.40282347E38)
							anda = true;
						else{
							Error er = new Error(analyzer.getLine(),analyzer.getMessage(101),"Lexico"); 
							msj.addError(er);
				
						}
					}
					if (!anda)	table.getTable().remove(newLexema);
					if (anda){
						if (entry.getRefCounter() == 1){
							if (table.getTable().contains(newLexema))
								((TSEntry)table.getTable().get(newLexema)).incCounter();
							else {	 
							TSEntry newEntry = new TSEntry(CONSTANT, newLexema);
								table.addTSEntry(newEntry.getLexema(), newEntry);
								newEntry.setType(a);
							}
						}
						else {
							entry.decCounter();
							if (table.getTable().containsKey(newLexema))
								((TSEntry)table.getTable().get(newLexema)).incCounter();
							else {   
								TSEntry newEntry = new TSEntry(CONSTANT, newLexema);
								table.addTSEntry(newEntry.getLexema(), newEntry);
								newEntry.setType(a);
							}
						}
					}  
					$$.obj = newLexema;
				}	
	   | '-' CONSTANT	{	
							String lexema = ((Token)$2.obj).getLexema();
							TSEntry entry = (TSEntry)table.getTable().get(lexema);
							String newLexema = "-" + lexema;
							boolean anda = false;
							String a =	entry.getType();
							if (a == "INT"){
								Long e = Long.valueOf(newLexema);
								if ( e.longValue() >= Short.MIN_VALUE )
									anda = true;
								else
								{    
									Error er = new Error(analyzer.getLine(),analyzer.getMessage(104),"Lexico"); 
									msj.addError(er);
								
								}
							}
							else {	
								Double f = Double.valueOf(newLexema);								
								if (f.doubleValue() <= -1.17549435E-38 && f.doubleValue() >= -3.40282347E38)
									anda = true;
								else{    
									Error er = new Error(analyzer.getLine(),analyzer.getMessage(101),"Lexico"); 
									msj.addError(er);
									
								}
							}
							if (!anda){
								table.getTable().remove(newLexema);
									table.getTable().remove(lexema);}
							if (anda){
								if (entry.getRefCounter() == 1){   
									if (table.getTable().contains(newLexema)){
										((TSEntry)table.getTable().get(newLexema)).incCounter();
									//table.getTable().remove(lexema);
									}else {	 
											TSEntry newEntry = new TSEntry(CONSTANT, newLexema);
											table.addTSEntry(newEntry.getLexema(), newEntry);
											//System.out.println("400 meti a " + newEntry.getLexema());
											newEntry.setType(a);
											table.getTable().remove(lexema);
									}
									
								}
								else {    
									entry.decCounter();
									if (table.getTable().containsKey(newLexema))
										((TSEntry)table.getTable().get(newLexema)).incCounter();
									else {   
										TSEntry newEntry = new TSEntry(CONSTANT, newLexema);
										table.addTSEntry(newEntry.getLexema(), newEntry);
										//System.out.println("500 meti a " + newEntry.getLexema());
										newEntry.setType(a);
									}
								}
							}  
							$$.obj = newLexema;
						}				
		| STRING
;

%%

void yyerror(String s) {
	if(s.contains("under"))
		System.out.println("par:"+s);
}

AnalizadorLexico analyzer;
Messages msj;
TS table;
Stack s;
Vector<Token> vt = new Vector<Token>() ;
Vector<Token> vt_amb = new Vector<Token>() ;
String suffix = "";
String[] names = new String[100];
boolean level_up = false;
int ambit_level = 0;
String name;
Vector<TSEntry> paranombrar = new Vector<TSEntry>() ;
Vector<String> tiposdevariables  ;
String tipo;

public void setLexico(AnalizadorLexico al, Stack s) {
	analyzer = al;
	table = al.getTS();
	this.s = s;
	names[0] = "0";
	name="";
	tiposdevariables = new Vector<String>();
	tipo ="";
}


public void resetearambitos( )
{
 names = new String[100];
 names[0] = "0";
 name="";
}

public void setMensajes(Messages ms) {
	msj = ms;
}

int yylex()
{
	int val = analyzer.yylex();
	yylval = new ParserVal(analyzer.getToken());
	yylval.ival = analyzer.getLine();
	
	return val;
}
//tenemos que ver como hacer para que anden los anidados, no meter dos veces en la tabla de simbolos. las cosas e hacen bien arriba porque agarra el ID a la vuelta