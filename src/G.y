%{
import java.util.Vector;
import java.util.Enumeration;
%}

%token IF THEN ELSE ENDIF PRINT INT LOOP TO ID CONSTANT FLOAT STRING COMPARATOR ASIGNATION END GLOBAL BEGIN FROM TOFLOAT EOF BY

/* GRAMATICA */
%%

/* PROGRAMA */
p : declarations exe EOF {table.limpiar();}
	| declarations exe error EOF {Error e = new Error(analyzer.getLine(),analyzer.getMessage(1),"Sintactico"); msj.addError(e);}
	| declarations error EOF {Error e = new Error(analyzer.getLine(),analyzer.getMessage(2),"Sintactico"); msj.addError(e);}
	| error exe EOF {Error e = new Error(analyzer.getLine(),analyzer.getMessage(3),"Sintactico"); msj.addError(e);}
;

declarations : declarations declaration 
			  | declaration
;

ambit_declarations	:	ambit_declarations	ambit_dec_sentence
					|	ambit_dec_sentence
;

ambit_dec_sentence	:	declaration {
										Enumeration e = ((Vector<Token>)$1.obj).elements();
										while (e.hasMoreElements()){
											Token token = (Token)e.nextElement();
											 token.getETS().setLexema(token.getLexema());
										}
									}
                      |	GLOBAL var_list ';' {   Vector<Token>  tokens=   ((Vector<Token>)$2.obj);
					                        for (int j =0; j < tokens.size(); j++)
											{ Token token = tokens.elementAt(j);
											token.getETS().setLexema(token.getLexema()+"_0");
											token.getETS().setType(table.getTSEntry(token.getLexema()+suffix).getType());
											String a = token.getLexema();
											token.setLexema(token.getLexema()+"_0");
											table.setkey(a,token);
												table.remove(token.getLexema()+suffix.substring(2,suffix.length()))	;	
													System.out.println("quiero borrar a " +token.getLexema()+suffix.substring(2,suffix.length() ));
											
											}
					     
					  }
; 

declaration : type var_list ';' 	{
										ES s = new ES(analyzer.getLine(), analyzer.getMessage(201)); msj.addStructure(s);
										Enumeration e = ((Vector<Token>)$2.obj).elements();
										while (e.hasMoreElements()){
											System.out.println("entre");
											Token token = (Token)e.nextElement();
											if (table.hasLexema(token.getLexema()) && table.isDeclared(token.getLexema()) )
											{
											Error er = new Error(analyzer.getLine(),analyzer.getMessage(306),"Semantico");
													msj.addError(er);
											}
											else
											{token.getETS().setType(tipo);
											token.getETS().setLexema(token.getLexema()+suffix);
											String a = token.getLexema();
											token.setLexema(token.getLexema()+suffix);
											table.setkey(a,token); 
											
											System.out.println("setie el tipo de "+ token.getLexema() + " a " + ((Token) $1.obj).getLexema());
										}
										
										}
										$$.obj = $2.obj;
									}
										
			| type var_list error	{Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
			| error var_list ';' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(5),"Sintactico"); msj.addError(e);}
			| type error ';' 			{Error e = new Error(analyzer.getLine(),analyzer.getMessage(6),"Sintactico"); msj.addError(e);}
;

var_list : var_list ',' ID		{	Vector<Token> tokens = (Vector<Token>)$1.obj;
										Token token = (Token)$3.obj;
										token.setType("ID");
										tokens.add(token);
										$$.obj = tokens;									
								}								
			| ID 				{	Vector<Token> tokens = new Vector<Token>();
										Token token = (Token)$1.obj;
										token.setType("ID");
										tokens.add(token);
										$$.obj = tokens;
								}
								
			| var_list  ',' error {Error e = new Error(analyzer.getLine(),analyzer.getMessage(15),"Sintactico"); msj.addError(e);}	
			;			   

				   
type : INT {tipo="INT";}
	 | FLOAT {tipo="FLOAT";}
	 | STRING {tipo="STRING";}
;

/* SENTENCIAS EJECUTABLES */

exe : exe sentence
	| exe ambit
	| sentence
	| ambit
;

sentence :  asignation ";" 
		  | asignation error {Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
		  | selection
		  | iteration
		  | print
		  | conversion
		  | error ';' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(15),"Sintactico"); msj.addError(e);}
;

asignation: ID ASIGNATION expression {	
											ES es = new ES(analyzer.getLine(), analyzer.getMessage(202)); 
											msj.addStructure(es); 
											String string = ((Token)$1.obj).getLexema();
											String string2 = (String)$3.obj;
											TSEntry op2 = table.getTSEntry(string2);
											TSEntry op1 = table.getTSEntry(string+ suffix);
											char c = string2.charAt(0);
											String  a= suffix;
											int b= ambit_level;
											if((!table.hasLexema(string+ a)) || (!op1.isDeclared())){
												while ((b != -1)&&((!table.hasLexema(string+ a)) )){ 
													String[] separada = a.split("_"); 
													a="";
													for (int i = 1; i<=b; i++)
														a+="_"+separada[i];	
													b--;
												}
												if (b ==-1){
													Error er = new Error(analyzer.getLine(),analyzer.getMessage(301),"Semantico"); 
													msj.addError(er);
												}
											} 
											op1 = table.getTSEntry(string+ a);
											Terceto t = new Terceto(s.size(), "=", string+ a, string2);
											if (c == '[') {
												String subst = string2.substring(1,string2.length()-1);
												Terceto op = s.get(subst);
												if (table.hasLexema(string+ a)){
													if (op1.isDeclared() && op1.getType() != op.getType()){	// preguntar si esta en la tabla, si esta me fijo si los tipos son compatibles. sino pongo quen o esta declarada.
														Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
														msj.addError(er);
														t.setType("error");
													}
												}
												else{
													Error er = new Error(analyzer.getLine(),analyzer.getMessage(301),"Semantico"); 
													msj.addError(er);
												}
											}
											else
											{
											op2=table.getTSEntry(string2);
											if (table.hasLexema(string)&& table.hasLexema(string2))
											{
											if (op1.getType() != op2.getType())
											{
											Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
														msj.addError(er);
														t.setType("error");
											}
											}
											else
											{
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(301),"Semantico"); 
													msj.addError(er);
											}
										
										 }
										 	s.add(t);
											$$.obj = t;
										 }
		  | error ASIGNATION expression {Error e = new Error(analyzer.getLine(),analyzer.getMessage(7),"Sintactico"); msj.addError(e);}
;

selection : selection_simple else_ bloque cerrar_selection {
															ES s = new ES(analyzer.getLine(), analyzer.getMessage(203)); 
															msj.addStructure(s);
															
															
															}
		  | selection_simple else_ sentence cerrar_selection {ES s = new ES(analyzer.getLine(), analyzer.getMessage(203)); msj.addStructure(s);}
		  | selection_simple cerrar_selection {ES s = new ES(analyzer.getLine(), analyzer.getMessage(203)); msj.addStructure(s);}
;

else_  :  ELSE 	{
					Terceto t = new Terceto(s.size(), "BI", "", "-");
					s.add(t);
					Integer i = pila.remove(0);
					s.get(Integer.toString(i)).setOp2("[" + Integer.toString(s.size()) + "]");
					pila.add(s.size()-1);
				}

selection_simple : IF condition_if THEN bloque
                 | IF condition_if THEN sentence
;
cerrar_selection : ENDIF {
							Integer i = pila.remove(0);
							s.get(Integer.toString(i)).setOp1("[" + Integer.toString(s.size()) + "]");
						 }


condition_if : '(' condition ')' {  
									String a = "[";
									a += Integer.toString(s.size()-1);
									a += "]";
									Terceto t = new Terceto (s.size(), "BF", a, "-");
									s.add(t);      
									pila.add(s.size()-1);
								}
;

condition:  expression COMPARATOR expression  	{
													String op1 = ((String)$1.obj);
													String op = ((Token)$2.obj).getLexema();
													String op2 = ((String)$3.obj);
													Terceto t = new Terceto (s.size(), op, op1, op2);
													s.add(t);
													$$.obj = t;
												}
		
		|   expression ASIGNATION expression  {Error e = new Error(analyzer.getLine(),analyzer.getMessage(24),"Sintactico"); msj.addError(e);}
		|   expression error expression  {Error e = new Error(analyzer.getLine(),analyzer.getMessage(24),"Sintactico"); msj.addError(e);}
		|   error COMPARATOR expression  {Error e = new Error(analyzer.getLine(),analyzer.getMessage(23),"Sintactico"); msj.addError(e);}
		|   expression COMPARATOR error  {Error e = new Error(analyzer.getLine(),analyzer.getMessage(23),"Sintactico"); msj.addError(e);}
;

it_cond : open_loop FROM asignation TO expression BY expression	{
																	Terceto ter = new Terceto (s.size(),"BF", "[" + Integer.toString(s.size()-1) + "]" ,"-");
																	s.add(ter);
																	pila.add(s.size()-1);
																	Terceto t  = (Terceto)$3.obj;
																	if (t.getType() != "INT"){
																		Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
																		msj.addError(er);
																	}
																	String m = (String) $5.obj;
																	String j = (String) $7.obj;
																	String subst;
																	char c = m.charAt(0);
																	if (c == '['){
																		subst = m.substring(1, m.length()-1);
																		if (s.get(subst).getType() != "INT"){
																			Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
																			msj.addError(er);
																		}	
																	}
																	c = j.charAt(0);
																	String op2;
																	if (c == '['){
																		subst = j.substring(1, j.length()-1);
																		if (s.get(subst).getType() != "INT"){
																			Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
																			msj.addError(er);
																		}
																		op2 = "[" + s.get(subst).getId() + "]";
																	}
																	else 
																		op2 = j;
																	Terceto terceto = new Terceto (s.size(), "<=", "[" + t.getId() + "]", op2);
																	s.add(terceto);
																}
		| error FROM asignation TO expression BY expression {Error e = new Error(analyzer.getLine(),analyzer.getMessage(18),"Sintactico"); msj.addError(e);}
		| open_loop error asignation TO expression BY expression {Error e = new Error(analyzer.getLine(),analyzer.getMessage(19),"Sintactico"); msj.addError(e);}
		| open_loop FROM error TO expression BY expression {Error e = new Error(analyzer.getLine(),analyzer.getMessage(22),"Sintactico"); msj.addError(e);}
		| open_loop FROM asignation error expression BY expression {Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
		| open_loop FROM asignation TO error BY expression {Error e = new Error(analyzer.getLine(),analyzer.getMessage(22),"Sintactico"); msj.addError(e);}
		| open_loop FROM asignation TO expression error expression {Error e = new Error(analyzer.getLine(),analyzer.getMessage(21),"Sintactico"); msj.addError(e);}
		| open_loop FROM asignation TO expression BY error {Error e = new Error(analyzer.getLine(),analyzer.getMessage(22),"Sintactico"); msj.addError(e);}
;

iteration : it_cond bloque {
								ES es = new ES(analyzer.getLine(), analyzer.getMessage(204)); 
								msj.addStructure(es);
								Integer i = pila.remove(0);
								s.get(Integer.toString(i)).setOp2("[" + Integer.toString(s.size()) + "]");
								pila.add(s.size()-1);
							}
		  | it_cond sentence {
								ES es = new ES(analyzer.getLine(), analyzer.getMessage(204)); 
								msj.addStructure(es);
								Integer i = pila.remove(0);
								s.get(Integer.toString(i)).setOp2("[" + Integer.toString(s.size()) + "]");
								pila.add(s.size()-1);
							}
;

open_loop : LOOP {
					Terceto t = new Terceto(s.size(), "BI", Integer.toString(s.size()-1), "-");
					s.add(t);
				 }
;
bloque_exe_sentences : bloque_exe_sentences sentence
					 | sentence
;

bloque: BEGIN bloque_exe_sentences END {ES s = new ES(analyzer.getLine(), analyzer.getMessage(206)); msj.addStructure(s);}
	  | BEGIN bloque_exe_sentences error {Error e = new Error(analyzer.getLine(),analyzer.getMessage(9),"Sintactico"); msj.addError(e);}
	  | END {Error e = new Error(analyzer.getLine(),analyzer.getMessage(10),"Sintactico"); msj.addError(e);}
;

ambit :  abrir ambit_declarations exe cerrar {ES s = new ES(analyzer.getLine(), analyzer.getMessage(207)); msj.addStructure(s);}
	  | abrir exe cerrar {ES s = new ES(analyzer.getLine(), analyzer.getMessage(207)); msj.addStructure(s);}
	  
	  |  abrir ambit_declarations error cerrar {Error e = new Error(analyzer.getLine(),analyzer.getMessage(2),"Sintactico"); msj.addError(e);}
	  | abrir ambit_declarations exe error {Error e = new Error(analyzer.getLine(),analyzer.getMessage(16),"Sintactico"); msj.addError(e);}
;

abrir : ID '{'{
				name = ((Token) $1.obj).getLexema();
				Token token =((Token) $1.obj);
				if (token.getETS().isDeclared()){
					Error er = new Error(analyzer.getLine(),analyzer.getMessage(306),"semantico"); 
					msj.addError(er);
				}
				else{
					token.getETS().setType("AMBITO");
					ambit_level++;
					names [ambit_level]=name;
					String suf= "";
					for (int i = 0 ; i <= ambit_level; i++)	
						suf = suf + "_" + names[i];
					suffix = suf;
					System.out.println("abri el ambito "+ suffix );
				}

}
;

cerrar : '}'{
				System.out.println("cerre el ambito "+ names [ambit_level]);
				ambit_level--;
				if (ambit_level == 0)
					resetearambitos();
			}
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
													if (t1.getType().equals("FLOAT")){ 	
														Error er = new Error(analyzer.getLine(),analyzer.getMessage(307),"Semantico");
														msj.addError(er);
														t1.setType("error");
													}
													else
														s.toFloat(t1); //recorre los tercetos convirtiendo valores a float
												}
												else{
													if (!table.hasLexema(lexema)){
														Error er = new Error(analyzer.getLine(),analyzer.getMessage(301),"Semantico");
														msj.addError(er);
														t.setType("error");
													}
													else if (table.getTSEntry(lexema).getType().equals("FLOAT")){ 	
														Error er = new Error(analyzer.getLine(),analyzer.getMessage(307),"Semantico");
														msj.addError(er);
														t.setType("error");
													}
													else 
														table.getTSEntry(lexema).setType("FLOAT");	
												}
												s.add(t);
												$$.obj = t;
											}
		  | TOFLOAT '(' expression ')' error {Error e = new Error(analyzer.getLine(),analyzer.getMessage(23),"Sintactico"); msj.addError(e);}
		  | TOFLOAT '(' error ')' ';' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(13),"Sintactico"); msj.addError(e);}
;

expression : expression '+' term { 	
									String string = (String)$1.obj;
									String string2 = (String)$3.obj;
									TSEntry op1 = table.getTSEntry((String)$1.obj);
									TSEntry op2 = table.getTSEntry((String)$3.obj);
									char c = string.charAt(0);
									char c2 = string2.charAt(0);
									Terceto t = new Terceto(s.size(),"+", (String)$1.obj, (String)$3.obj);
									if ((c == '[')  && (c2 == '[')){
										String subst = string.substring(1,string.length()-1);
										String subst2 = string2.substring(1,string.length()-1);
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
											String subst = string2.substring(1,string.length()-1);
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
										{     if ((table.hasLexema((String)$1.obj)) && (table.hasLexema((String)$3.obj))){
											if (!op1.getType().equals(op2.getType())){ 	
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
												msj.addError(er);
												t.setType("error");
											}
											else
												t.setType(op1.getType());
										}
										else
										{
										Error er = new Error(analyzer.getLine(),analyzer.getMessage(301),"Semantico");
												msj.addError(er);
										}
									}
									s.add(t);
									$$.obj = "[" + t.getId() + "]";
								}
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
										String subst2 = string2.substring(1,string.length()-1);
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
											String subst = string2.substring(1,string.length()-1);
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
										{     if ((table.hasLexema((String)$1.obj)) && (table.hasLexema((String)$3.obj))){
											if (!op1.getType().equals(op2.getType())){ 	
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
												msj.addError(er);
												t.setType("error");
											}
											else
												t.setType(op1.getType());
										}
										else
										{
										Error er = new Error(analyzer.getLine(),analyzer.getMessage(301),"Semantico");
												msj.addError(er);
										}
									}
									s.add(t);
									$$.obj = "[" + t.getId() + "]";
								}
								}
								
		  | term {
					$$.obj = ((String)$1.obj);
				 }
				 
				 |   error '-' term {Error er = new Error(analyzer.getLine(),analyzer.getMessage(25),"Sintactico");
									msj.addError(er);}
                  | expression '-' error {Error er = new Error(analyzer.getLine(),analyzer.getMessage(25),"Sintactico");
									msj.addError(er);}
 | error '+' term {Error er = new Error(analyzer.getLine(),analyzer.getMessage(25),"Sintactico");
									msj.addError(er);}
 | expression '+' error  {Error er = new Error(analyzer.getLine(),analyzer.getMessage(25),"Sintactico");
									msj.addError(er);}
				 
;

term : term '*' factor { 	String string = (String)$1.obj;
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
							else  
										{     if ((table.hasLexema((String)$1.obj)) && (table.hasLexema((String)$3.obj))){
											if (!op1.getType().equals(op2.getType())){ 	
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
												msj.addError(er);
												t.setType("error");
											}
											else
												t.setType(op1.getType());
										}
										else
										{
										Error er = new Error(analyzer.getLine(),analyzer.getMessage(301),"Semantico");
												msj.addError(er);
										}
									}
									s.add(t);
									$$.obj = "[" + t.getId() + "]";
								}
								
		| term '/' factor { 
							String string = (String)$1.obj;
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
							else {
						 
										{     if ((table.hasLexema((String)$1.obj)) && (table.hasLexema((String)$3.obj))){
											if (!op1.getType().equals(op2.getType())){ 	
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(302),"Semantico");
												msj.addError(er);
												t.setType("error");
											}
											else
												t.setType(op1.getType());
										}
										else
										{
										Error er = new Error(analyzer.getLine(),analyzer.getMessage(301),"Semantico");
												msj.addError(er);
										}
									}
									s.add(t);
									$$.obj = "[" + t.getId() + "]";
								}
								}					
		| factor { 
					String s1 = (String) $1.obj;
					TSEntry entry = table.getTSEntry(s1);
					yyval.obj = s1;
					}
					
				
				| error '*' factor {Error er = new Error(analyzer.getLine(),analyzer.getMessage(25),"Sintactico");
									msj.addError(er);}
				|  term '*' error {Error er = new Error(analyzer.getLine(),analyzer.getMessage(25),"Sintactico");
									msj.addError(er);}
				|  error '/' factor {Error er = new Error(analyzer.getLine(),analyzer.getMessage(25),"Sintactico");
									msj.addError(er);}
				|  term '/' error {Error er = new Error(analyzer.getLine(),analyzer.getMessage(25),"Sintactico");
									msj.addError(er);}
;

factor : CONSTANT {	String newLexema = ((Token)$1.obj).getLexema();
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
	   | ID { String lexema = ((Token)$1.obj).getLexema();
	   
			  					TSEntry entry = table.getTSEntry(lexema);
			  	String  a= suffix;
											int b= ambit_level;
											if((!table.hasLexema(lexema+ a)) || (!entry.isDeclared()))
											{while ((b != -1)&&((!table.hasLexema(lexema+ a)) ))
											{ String[] separada = a.split("_"); 
											    for (int i = 1 ;i<=b; i++)
											
												a="";
												for (int i = 1; i<=b; i++)
												a+="_"+separada[i];	
												b--;
											
											}
											if (b ==-1)
											{
										   
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(301),"semantico"); 
												msj.addError(er);
											}
					
			  
			  }
			  $$.obj = lexema+a;
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
									table.getTable().remove(lexema);
							}
							if (anda){
								if (entry.getRefCounter() == 1){   
									if (table.getTable().contains(newLexema)){
										((TSEntry)table.getTable().get(newLexema)).incCounter();
									}
									else {	 
											TSEntry newEntry = new TSEntry(CONSTANT, newLexema);
											table.addTSEntry(newEntry.getLexema(), newEntry);
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
String suffix = "_0";
String[] names = new String[100];
boolean level_up = false;
int ambit_level = 0;
String name;
String tipo;
Vector<Integer> pila;

public void setLexico(AnalizadorLexico al) {
	analyzer = al;
	table = al.getTS();
	this.s = s;
	names[0] = "0";
	for (int i = 1; i< 100 ;i++)
	names[i]="";
	name="";
	tipo ="";
	s=new Stack(table);
	pila = new Vector<Integer>();
}

public void imprimirStack(){
	s.imprimir();
}

public void resetearambitos( )
{
 names = new String[100];
 names[0] = "0";
 name="";
 suffix = "_0";
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