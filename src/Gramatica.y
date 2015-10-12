%{
import java.util.Vector;
import java.util.Enumeration;
%}
  
%token IF THEN ELSE ENDIF PRINT INT LOOP TO ID CONSTANT FLOAT STRING COMPARATOR ASIGNATION END GLOBAL BEGIN FROM TOFLOAT EOF BY

/* GRAMATICA */
%%

/* PROGRAMA */
p : declarations exe EOF {	Terceto a1 = ((Terceto)$1.obj);
							Terceto a2 = ((Terceto)$2.obj);
							terceto = new Terceto ("program",a1,a2);
							terceto.imprimir (0);
						 }
	| declarations exe error EOF {Error e = new Error(analyzer.getLine(),analyzer.getMessage(1),"Sintactico"); msj.addError(e);}
	| declarations error EOF {Error e = new Error(analyzer.getLine(),analyzer.getMessage(2),"Sintactico"); msj.addError(e);}
	| error exe EOF {Error e = new Error(analyzer.getLine(),analyzer.getMessage(3),"Sintactico"); msj.addError(e);}
;

declarations : declarations declaration {	Terceto a1 = ((Terceto)$1.obj);
											Terceto a2 = ((Terceto)$2.obj);
											$$.obj = new Terceto ("daclarations",a1,a2);
										}
			  | declaration
;

declaration : type var_list ';' 	{	ES s = new ES(analyzer.getLine(), analyzer.getMessage(201)); msj.addStructure(s);
										Enumeration e = ((Vector<Token>)vt).elements();
										String lexema = ((Terceto)$1.obj).getValor();
										while (e.hasMoreElements()){
											Token token = (Token)e.nextElement();
											if (table.hasLexema(token.getLexema())&&(!table.get=TSEntry(token.getLexema()).isDeclared())){
													token.getETS().setType(lexema);
													token.getETS().setDeclared();
													token.getETS().setId((short)265);
											}
											else
											{
												Error e = new Error(analyzer.getLine(),analyzer.getMessage(61),"Semantico"); msj.addError(e);
												Terceto.setError();
											}
										}
										vt = new Vector<Token>();
										Terceto a1 = ((Terceto)$1.obj);
										Terceto a2 = ((Terceto)$2.obj);
										Terceto a3 = new Terceto ("declaration",a1,a2);
										a3.setType(lexema);
										$$.obj = a3;
									}					
			| type var_list 		{Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
			| error var_list ';' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(5),"Sintactico"); msj.addError(e);}
			| type error ';' 			{Error e = new Error(analyzer.getLine(),analyzer.getMessage(6),"Sintactico"); msj.addError(e);}
;

var_list : var_list ',' ID		{	Token token = (Token)$3.obj;
									token.setType("ID");
									vt.add(token);
									Terceto a3 = new Terceto (tabla.getTabla().get(token.getLexema()),token.getLexema());
									Terceto a1 = ((Terceto)$1.obj);
									$$.obj = new Terceto ("var_list", a1, a3);								
								}								
			| ID 				{	Vector<Token> tokens = new Vector<Token>();
									Token token = (Token)$1.obj;
									token.setType("ID");
									tokens.add(token);
									vt = tokens;									
									$$.obj = new Terceto (tabla.getTabla().get(token.getLexema()),token.getLexema());
								}
;			   
				   
type : INT {	String lexema = ((Token)$1.obj).getLexema();
				$$.obj = new Terceto (tabla.getTabla().get(lexema), lexema);
		   }
	 | FLOAT {	String lexema = ((Token)$1.obj).getLexema();
				$$.obj = new Terceto (tabla.getTabla().get(lexema), lexema);
			 }	
	 | STRING {	String lexema = ((Token)$1.obj).getLexema();
				$$.obj = new Terceto (tabla.getTabla().get(lexema), lexema);
			  }
;

/* SENTENCIAS EJECUTABLES */

exe : exe sentence
	| exe ambit
	| sentence
	| ambit
;

exe_sentences : exe_sentences sentence  {	Terceto t1 = ((Terceto)$1.obj);
											Terceto t2 = ((Terceto)$2.obj);
											$$.obj = new Terceto ("sentencias",t1,t2);
											$$.obj = new Terceto ("sentencias",t1,t2);
										} 
			  | sentence 
;

sentence : asignation
		  | selection
		  | iteration
		  | print
		  | conversion
		  | error ';' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(15),"Sintactico"); msj.addError(e);}
;

asignation: ID ASIGNATION expression ';' {	ES s = new ES(analyzer.getLine(), analyzer.getMessage(202)); msj.addStructure(s);
											Token token1 = ((Token)$1.obj);
											String lexID = token1.getLexema();
											TSEntry ET = table.getTSEntry(lexID);
											if (!table.hasLexema(lexID) || !ET.isDeclared()){
												Error e = new Error(analyzer.getLine(),analyzer.getMessage(60),"Semantico"); msj.addError(e);
												Terceto.setError();
											}
											Terceto t1 = new Terceto(table.getTable().get(token1.getLexema()),token1.getLexema());
											if (table.getTSEntry(t1.getValue()).isDeclared()){
												a1.setType(table.getTSEntry(t1.getValue()).getType());
											}
											Terceto t3 = ((Terceto)$3.obj);
											if (!t1.getType().equals(t3.getType())){
												Error e = new Error(analyzer.getLine(),analyzer.getMessage(68),"Semantico"); msj.addError(e);
												terceto.setError();
											}
											Terceto dev = new Terceto ("ASIGNATION", t1, t3);
											dev.setType(t1.getType());
											$$.obj = dev;
										}
		  | ID ASIGNATION expression error {Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
		  | error ASIGNATION expression ';' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(7),"Sintactico"); msj.addError(e);}
		  | error ASIGNATION expression {Error e = new Error(analyzer.getLine(),analyzer.getMessage(8),"Sintactico"); msj.addError(e);}
;

selection : selection_simple ELSE bloque ENDIF {	ES s = new ES(analyzer.getLine(), analyzer.getMessage(203)); msj.addStructure(s);
													Terceto sel_simple = ((Terceto)$1.obj);
													Terceto bloque_if = sel_simple.getRight();
													Terceto bloque_else =  ((Terceto)$3.obj);
													bloque_if.setRight(bloque_else);
													$$.obj = sel_simple;
												}
		  | selection_simple ELSE sentence ENDIF {	ES s = new ES(analyzer.getLine(), analyzer.getMessage(203)); msj.addStructure(s);
													Terceto sel_simple = ((Terceto)$1.obj);
													Terceto sentence_si = sel_simple.getRight();
													Terceto sentence_sino =  ((Terceto)$3.obj);
													bloque_si.setRight(sentence_sino);
													$$.obj = sel_simple;
												}
		  | selection_simple ENDIF {ES s = new ES(analyzer.getLine(), analyzer.getMessage(203)); msj.addStructure(s);}
;

selection_simple : IF condition THEN bloque {	Terceto cn = ((Terceto)$2.obj);
												Terceto bl = ((Terceto)$4.obj);
												Terceto bloque = new Terceto ("BLOQUE", bl , null);
												$$.obj = new Terceto ("IF", cn, bloque);
											}
                 | IF condition THEN sentence {	Terceto cn = ((Terceto)$2.obj);
												Terceto sn = ((Terceto)$4.obj);
												Terceto sentence = new Terceto ("SENTENCE", sn , null);
												$$.obj = new Terceto ("IF", cn, sentence);
											  }
;

iteration : LOOP FROM ID ASIGNATION ID TO ID BY ID bloque {ES s = new ES(analyzer.getLine(), analyzer.getMessage(204)); msj.addStructure(s);} //ARBOL
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

bloque: BEGIN bloque_exe_sentences END {	ES s = new ES(analyzer.getLine(), analyzer.getMessage(206)); msj.addStructure(s);
											$$.obj = ((Tercetos)$2.obj);
									   }
	  | BEGIN bloque_exe_sentences error {Error e = new Error(analyzer.getLine(),analyzer.getMessage(9),"Sintactico"); msj.addError(e);}
	  | END {Error e = new Error(analyzer.getLine(),analyzer.getMessage(10),"Sintactico"); msj.addError(e);}
;
 
ambit_declarations	:	ambit_declarations	ambit_dec_sentence //ver arbol
					|	ambit_dec_sentence
;

ambit_dec_sentence	:	declaration // ver arbol
                      |	GLOBAL var_list ';'
; 
 
ambit : ID '{' ambit_declarations exe_sentences '}' {ES s = new ES(analyzer.getLine(), analyzer.getMessage(207)); msj.addStructure(s);} //arbol
	  | ID '{' exe_sentences '}' {ES s = new ES(analyzer.getLine(), analyzer.getMessage(207)); msj.addStructure(s);}
	  | error '{' ambit_declarations exe_sentences '}' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(17),"Sintactico"); msj.addError(e);}
	  | ID '{' ambit_declarations error '}' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(2),"Sintactico"); msj.addError(e);}
	  | ID '{' ambit_declarations exe_sentences error {Error e = new Error(analyzer.getLine(),analyzer.getMessage(16),"Sintactico"); msj.addError(e);}
;

condition: '(' expression COMPARATOR expression ')'  { 	String lexema = ((Token)$3.obj).getLexema();
														Terceto a1 = ((Terceto)$2.obj);
														Terceto a2 = ((Terceto)$4.obj);
														Terceto comp = new Terceto (lexema,a1,a2);
														if (a1.getType().equals(a2.getType()))
															comp.setType(a1.getType());
														else{
															terceto.setError();
															Error e = new Error(analyzer.getLine(),analyzer.getMessage(72),"Semantico"); msj.addError(e);														
														}
														$$.obj = comp ;
													}
		| error expression COMPARATOR expression ')' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(11),"Sintactico"); msj.addError(e);}
		|  '(' expression COMPARATOR expression error {Error e = new Error(analyzer.getLine(),analyzer.getMessage(12),"Sintactico"); msj.addError(e);}
		|  '(' expression error expression ')' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(24),"Sintactico"); msj.addError(e);}
		|  '(' error COMPARATOR expression ')' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(23),"Sintactico"); msj.addError(e);}
		|  '(' expression COMPARATOR error ')' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(23),"Sintactico"); msj.addError(e);}
;

print: PRINT '(' STRING ')' ';' {	ES s = new ES(analyzer.getLine(), analyzer.getMessage(205)); msj.addStructure(s);
									String lexema = ((Token)$3.obj).getLexema();
									Terceto string = new Terceto (table.getTabla().get(lexema), lexema);
									$$.obj = new Terceto ("PRINT" , string , null);
								}
	 | PRINT '(' STRING ')' error {Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
	 | PRINT '(' error ')' ';' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(13),"Sintactico"); msj.addError(e);}
	 | error '(' STRING ')' ';' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(14),"Sintactico"); msj.addError(e);}
;

conversion: TOFLOAT '(' expression ')' ';' {ES s = new ES(analyzer.getLine(), analyzer.getMessage(208)); msj.addStructure(s);} //falta hacer arbol
		  | TOFLOAT '(' expression ')' error {Error e = new Error(analyzer.getLine(),analyzer.getMessage(23),"Sintactico"); msj.addError(e);}
		  | TOFLOAT '(' error ')' ';' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(13),"Sintactico"); msj.addError(e);}
;

expression : expression '+' term {	Terceto t1 = ((Terceto)$1.obj);
									Terceto t2 = ((Terceto)$3.obj);
									Terceto res = new Terceto ("+",t1,t2);
								  	if ( t1.getType().equals(t2.getType()))
								  		res.setType(a1.getType());
								  	else{
								  	Error e = new Error(analyzer.getLine(),analyzer.getMessage(66),"Semantico"); msj.addError(e);
								  	terceto.setError();
								  	}
										$$.obj = res ;
									}
		  | expression '-' term  {	Terceto t1 = ((Terceto)$1.obj);
									Terceto t2 = ((Terceto)$3.obj);
									Terceto res = new Terceto ("-",t1,t2);
								  	if ( t1.getType().equals(t2.getType()))
								  		res.setType(a1.getType());
								  	else{
								  	Error e = new Error(analyzer.getLine(),analyzer.getMessage(65),"Semantico"); msj.addError(e);
								  	terceto.setError();
								  	}
										$$.obj = res ;
									}
		  | term {$$.obj = ((Terceto)$1.obj);} 
;

term : term '*' factor { 	Terceto t1 = ((Terceto)$1.obj);
							Terceto a2 = ((Terceto)$3.obj);
							Terceto res = new Terceto ("*",a1,a2);
							if ( a1.getTipo().equals(a2.getTipo()))
								res.setTipo(a1.getTipo());
							else{
								Error e = new Error(analyzer.getLine(),analyzer.getMessage(63),"Semantico"); msj.addError(e);
								terceto.setError();
							}
						  $$.obj = res ;
						}
		| term '/' factor { Terceto t1 = ((Terceto)$1.obj);
							Terceto a2 = ((Terceto)$3.obj);
							Terceto res = new Terceto ("*",a1,a2);
							if ( a1.getTipo().equals(a2.getTipo()))
								res.setTipo(a1.getTipo());
							else{
								Error e = new Error(analyzer.getLine(),analyzer.getMessage(63),"Semantico"); msj.addError(e);
								terceto.setError();
							}
						  $$.obj = res ;
						 } 
		| factor {	Terceto t1 = ((Terceto)$1.obj);
					String t = t1.getValue () ;
					TSEntry ETs = table.getTSEntry(t);
					try {
						if ((!table.hasLexema(t) || 
							!ETs.isDeclared())&& ETs.getId()==265){
							Error e = new Error(analyzer.getLine(),analyzer.getMessage(60),"Semantico"); msj.addError(e);
							Terceto.setError();
						}
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
					yyval.obj = t1;
				  }
;

factor : CONSTANT		{ 	String lexema = ((Token)$1.obj).getLexema();
							Terceto t1 = new Terceto(table.getTable().get(lexema), lexema);
							t1.setType ("CONSTANTE"); 
							$$.obj = t1 ;
						}
	   | ID				{	String lexema = ((Token)$1.obj).getLexema();
							Terceto t1 = new Terceto (table.getTable().get(lexema), lexema);
							if ( table.getTSEntry(lexema).isDeclared())
								t1.setType(table.getTSEntry(lexema).getType());
							$$.obj = t1 ;
						}
	   | '-' CONSTANT	{ 	String lexema = ((Token)$2.obj).getLexema();
							TSEntry entry = (TSEntry)table.getTable().get(lexema);
							String newLexema = "-" + lexema;
							if (entry.getRefCounter() == 1){
								if (table.getTable().contains(newLexema))
									((TSEntry)table.getTable().get(newLexema)).incCounter();
								else {	
									TSEntry newEntry = new TSEntry(CONSTANT, newLexema);
									table.addTSEntry(newEntry.getLexema(), newEntry);
									newEntry.setType("CONSTANTE");
								}
								table.getTable().remove(lexema);
							}
							else {
								entry.decCounter();
								if (table.getTable().containsKey(newLexema))
									((TSEntry)table.getTable().get(newLexema)).incCounter();
								else{
									TSEntry newEntry = new TSEntry(CONSTANT, newLexema);
									table.addTSEntry(newEntry.getLexema(), newEntry);
									newEntry.setType("CONSTANTE");
								}
							}
							Terceto t1 = new Terceto(table.getTable().get(lexema),lexema);
							t1.setTipo ("CONSTANTE"); 
							$$.obj = table.getTable().get(newLexema);
						}				
		| STRING 		{ 	String lexema = ((Token)$1.obj).getLexema();
							Terceto t1 = new Terceto(table.getTable().get(lexema), lexema);
							t1.setType ("STRING"); 
							$$.obj = t1 ;
						}
;

%%

void yyerror(String s) {
	if(s.contains("under"))
		System.out.println("par:"+s);
}

AnalizadorLexico analyzer;
Messages msj;
TS table;
Vector<Token> vt = new Vector<Token>() ;
Terceto tree = new Terceto (); ;

public void setLexico(AnalizadorLexico al) {
	analyzer = al;
	table = al.getTS();
}

public void setMessages(Messages ms) {
	msj = ms;
}

public void setTerceto ( Terceto t ){
	terceto = t ;
}

public Terceto getTerceto (){
	return terceto ;
}

int yylex()
{
	int val = analyzer.yylex();
	yylval = new ParserVal(analyzer.getToken());
	yylval.ival = analyzer.getLine();
	
	return val;
}