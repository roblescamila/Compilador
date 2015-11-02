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

declarations : declarations declaration 
			  | declaration
;

declaration : type var_list ';' 	{ES s = new ES(analyzer.getLine(), analyzer.getMessage(201)); msj.addStructure(s);
											Enumeration e = ((Vector<Token>)$2.obj).elements();
											while (e.hasMoreElements()){
												Token token = (Token)e.nextElement();
												if (token.getETS().getType() == null){
													token.getETS().setType(token.getType());
												}
											}
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
;			   
				   
type : INT 
	 | FLOAT
	 | STRING
;

/* SENTENCIAS EJECUTABLES */

exe : exe sentence
	| exe ambit
	| sentence
	| ambit
;

sentence : asignation
		  | selection
		  | iteration
		  | print
		  | conversion
		  | error ';' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(15),"Sintactico"); msj.addError(e);}
;

asignation: ID ASIGNATION expression ';' {ES s = new ES(analyzer.getLine(), analyzer.getMessage(202)); msj.addStructure(s);}
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
 
ambit_declarations	:	ambit_declarations	ambit_dec_sentence
					|	ambit_dec_sentence
;

ambit_dec_sentence	:	declaration
                      |	GLOBAL var_list ';'
; 
 
ambit : ID '{' ambit_declarations exe '}' {ES s = new ES(analyzer.getLine(), analyzer.getMessage(207)); msj.addStructure(s);}
	  | ID '{' exe '}' {ES s = new ES(analyzer.getLine(), analyzer.getMessage(207)); msj.addStructure(s);}
	  | error '{' ambit_declarations exe '}' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(17),"Sintactico"); msj.addError(e);}
	  | ID '{' ambit_declarations error '}' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(2),"Sintactico"); msj.addError(e);}
	  | ID '{' ambit_declarations exe error {Error e = new Error(analyzer.getLine(),analyzer.getMessage(16),"Sintactico"); msj.addError(e);}
;

condition: '(' expression COMPARATOR expression ')' 
		| error expression COMPARATOR expression ')' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(11),"Sintactico"); msj.addError(e);}
		|  '(' expression COMPARATOR expression error {Error e = new Error(analyzer.getLine(),analyzer.getMessage(12),"Sintactico"); msj.addError(e);}
		|  '(' expression error expression ')' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(24),"Sintactico"); msj.addError(e);}
		|  '(' error COMPARATOR expression ')' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(23),"Sintactico"); msj.addError(e);}
		|  '(' expression COMPARATOR error ')' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(23),"Sintactico"); msj.addError(e);}
;

print: PRINT '(' STRING ')' ';' {ES s = new ES(analyzer.getLine(), analyzer.getMessage(205)); msj.addStructure(s);}
	 | PRINT '(' STRING ')' error {Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
	 | PRINT '(' error ')' ';' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(13),"Sintactico"); msj.addError(e);}
	 | error '(' STRING ')' ';' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(14),"Sintactico"); msj.addError(e);}
;

conversion: TOFLOAT '(' expression ')' ';' {ES s = new ES(analyzer.getLine(), analyzer.getMessage(208)); msj.addStructure(s);}
		  | TOFLOAT '(' expression ')' error {Error e = new Error(analyzer.getLine(),analyzer.getMessage(23),"Sintactico"); msj.addError(e);}
		  | TOFLOAT '(' error ')' ';' {Error e = new Error(analyzer.getLine(),analyzer.getMessage(13),"Sintactico"); msj.addError(e);}
;

expression : expression '+' term
		  | expression '-' term
		  | term
;

term : term '*' factor
		| term '/' factor
		| factor
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
							table.getTable().remove(newLexema);
						}
					}
					else{
						Double f = Double.valueOf(newLexema);								
						if (f.doubleValue() >= 1.17549435E-38 && f.doubleValue() <= 3.40282347E38)
							anda = true;
						else{
							Error er = new Error(analyzer.getLine(),analyzer.getMessage(101),"Lexico"); 
							msj.addError(er);
							table.getTable().remove(newLexema);
						}
					}
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
					$$.obj = table.getTable().get(newLexema);
				}	
	   | ID
	   | '-' CONSTANT	{	String lexema = ((Token)$2.obj).getLexema();
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
									table.getTable().remove(lexema);
								}
							}
							else{	
								Double f = Double.valueOf(newLexema);								
								if (f.doubleValue() <= -1.17549435E-38 && f.doubleValue() >= -3.40282347E38)
									anda = true;
								else{    
									Error er = new Error(analyzer.getLine(),analyzer.getMessage(101),"Lexico"); 
									msj.addError(er);
									table.getTable().remove(lexema);
								}
							}
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
							$$.obj = table.getTable().get(newLexema);
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

public void setLexico(AnalizadorLexico al) {
	analyzer = al;
	table = al.getTS();
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