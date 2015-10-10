//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "Gramatica.y"
import java.util.Vector;
import java.util.Enumeration;
//#line 20 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short IF=257;
public final static short THEN=258;
public final static short ELSE=259;
public final static short ENDIF=260;
public final static short PRINT=261;
public final static short INT=262;
public final static short LOOP=263;
public final static short TO=264;
public final static short ID=265;
public final static short CONSTANT=266;
public final static short FLOAT=267;
public final static short STRING=268;
public final static short COMPARATOR=269;
public final static short ASIGNATION=270;
public final static short END=271;
public final static short GLOBAL=272;
public final static short BEGIN=273;
public final static short FROM=274;
public final static short TOFLOAT=275;
public final static short EOF=276;
public final static short BY=277;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    1,    1,    3,    3,    3,    3,
    5,    5,    4,    4,    4,    2,    2,    2,    2,    8,
    8,    6,    6,    6,    6,    6,    6,    9,    9,    9,
    9,   10,   10,   10,   15,   15,   11,   11,   11,   11,
   11,   11,   11,   11,   11,   11,   11,   11,   18,   18,
   16,   16,   16,   19,   19,   20,   20,    7,    7,    7,
    7,    7,   17,   17,   17,   17,   17,   17,   12,   12,
   12,   12,   13,   13,   13,   14,   14,   14,   21,   21,
   21,   22,   22,   22,   22,
};
final static short yylen[] = {                            2,
    3,    4,    3,    3,    2,    1,    3,    2,    3,    3,
    3,    1,    1,    1,    1,    2,    2,    1,    1,    2,
    1,    1,    1,    1,    1,    1,    2,    4,    4,    4,
    3,    4,    4,    2,    4,    4,   10,   10,   10,   10,
   10,   10,   10,   10,   10,   10,   10,   10,    2,    1,
    3,    3,    1,    2,    1,    1,    3,    5,    4,    5,
    5,    5,    5,    5,    5,    5,    5,    5,    5,    5,
    5,    5,    5,    5,    5,    3,    3,    1,    3,    3,
    1,    1,    1,    2,    1,
};
final static short yydefred[] = {                         0,
    0,   13,   14,   15,    0,    0,    6,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   18,   19,   22,   23,
   24,   25,   26,    0,    0,    0,    0,    5,    0,   12,
    0,    0,    0,   27,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    4,   16,   17,    9,    0,
    0,   34,    3,    0,    1,   10,    7,   83,   82,   85,
    0,    0,    0,   81,    0,    0,    0,   56,    0,   55,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   21,    0,    0,    0,    0,   11,    0,
   53,    0,    0,    0,    2,   84,   30,    0,    0,    0,
    0,    0,    0,    0,   54,    0,    0,    0,    0,    0,
   36,   35,    0,    0,    0,    0,    0,    0,   29,   28,
   59,   20,    0,    0,    0,    0,   50,    0,   33,   32,
    0,    0,   79,   80,    0,   57,   60,   72,    0,    0,
    0,    0,    0,   71,   70,   69,    0,    0,    0,    0,
    0,   61,    0,   58,   75,   74,   73,    0,   51,   49,
    0,   64,   67,   66,   68,   65,   63,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   39,   40,   41,   42,   43,   44,   45,   46,   47,    0,
   38,   37,
};
final static short yydgoto[] = {                          5,
    6,   15,   68,    8,   16,   84,   18,   85,   19,   20,
   21,   22,   23,   62,   24,   94,   39,  128,   69,   70,
   63,   64,
};
final static short yysindex[] = {                      -138,
 -235,    0,    0,    0,    0,  175,    0, -159,  -30,  -35,
   16, -223, -109,   39, -161,   -1,    0,    0,    0,    0,
    0,    0,    0, -134,  -40, -109, -144,    0,   23,    0,
    1,   53, -160,    0,   68, -146,   53,  -42, -131, -178,
 -119, -155,   53, -118,   43,    0,    0,    0,    0, -117,
  188,    0,    0,  -39,    0,    0,    0,    0,    0,    0,
 -113,   44,  -10,    0, -107,  -99,  -99,    0, -118,    0,
  126,  -37, -100,  -41,  188,  143,  148,  -76,  -74, -179,
   -7,   33,  -68,    0,  -57,  141,  172,   12,    0,  -24,
    0,  214,  -44,  -43,    0,    0,    0,   53,   53,   53,
   53,  -38,   17,   82,    0,  170,   53,   53,   53,   49,
    0,    0,  174,  -47,  -13,  -11,    5,  -88,    0,    0,
    0,    0,   -9,   95,  216,  -46,    0,  211,    0,    0,
  -10,  -10,    0,    0,  -45,    0,    0,    0,  147,  160,
  226,  232,  -34,    0,    0,    0,   13,   20,   24,   28,
  -91,    0,  -24,    0,    0,    0,    0,  -24,    0,    0,
   11,    0,    0,    0,    0,    0,    0,   15,   25,   29,
   31,   35,  -85,    4,   36,   51,   55,   56,   57,   67,
 -238,   45,   47,   81,   83,   84,   85,   88,   89,  -70,
 -162, -162, -162, -162, -162, -162, -162, -162, -162,  191,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -24,
    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   22,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  158,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -101,  -18,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    3,   26,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  125,    0,    0,    0,    0,  -78,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   66,
    0,    0,
};
final static short yygindex[] = {                         0,
    0,  287,   80,    0,    7,   48,   93,  -52,    0,    0,
    0,    0,    0,   27,    0,  180,    0,    0,  260,  -27,
  113,   41,
};
final static int YYTABLESIZE=489;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         36,
   36,   98,   61,   99,   38,   98,  167,   99,   98,   36,
   99,  146,  157,   44,   31,   36,  104,  189,   34,   34,
    9,   10,   78,   31,   78,   11,   78,   12,   34,   13,
   36,  100,   41,  124,   34,   98,  101,   99,  190,   14,
   78,  105,   50,   76,   50,   76,   52,   76,   17,   34,
   42,  120,  126,   17,   98,   40,   99,   49,  105,   57,
   50,   76,   47,   72,   74,   12,   77,  121,   77,   81,
   77,   88,   36,  103,   47,  136,  117,   76,   45,    7,
   12,   56,   35,   35,   77,   28,   98,   61,   99,   77,
  118,   34,   35,   61,    9,   10,   29,   61,   93,   11,
   79,   12,   97,   26,   65,   30,   78,   48,   91,   80,
   92,   54,   10,   14,   46,  152,   11,    1,   12,   48,
   26,   71,  111,    2,   51,   52,   75,   76,    3,    4,
   14,   55,  122,  139,  140,  141,  143,   82,   10,  127,
  133,  134,   11,    2,   12,   78,   83,   89,    3,    4,
   77,  122,   96,   67,   31,   31,   14,   31,   31,   31,
   43,   31,  102,   31,  172,   30,  106,  150,  108,   31,
  180,  122,  173,   31,   31,  160,  151,   52,   52,  181,
   52,   52,   52,  113,   52,  199,   52,  162,  114,   98,
   48,   99,   52,  115,  200,  116,   52,   52,   90,   10,
  163,   43,   98,   11,   99,   12,  137,   83,  145,  156,
  131,  132,  125,   73,  109,  129,  130,   14,  161,  154,
   37,  166,   58,   59,   30,   60,  135,  110,  138,   32,
   32,  107,  144,   33,   33,   53,   95,   78,   78,   32,
   78,   78,   78,   33,   78,   32,   78,  211,  119,   33,
   78,  147,   78,  148,  112,   30,   78,   78,   76,   76,
   32,   76,   76,   76,   33,   76,  164,   76,   98,  149,
   99,   76,  165,   76,  155,  174,  168,   76,   76,  175,
  182,   77,   77,  169,   77,   77,   77,  170,   77,  176,
   77,  171,   27,  177,   77,  178,   77,   30,   87,  179,
   77,   77,   32,   86,  142,    0,   33,   58,   59,  191,
   60,  192,  183,   58,   59,    0,   60,   58,   59,    0,
   60,   48,   48,   66,   48,   48,   48,  184,   48,    2,
   48,  185,  186,  187,    3,    4,   48,   90,   10,   67,
   48,   48,   11,  188,   12,  193,   83,  194,  195,  196,
  153,   10,  197,  198,    0,   11,   14,   12,    0,   83,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   14,
  201,  202,  203,  204,  205,  206,  207,  208,  209,  212,
   62,   62,    0,    0,    0,   62,    0,   62,    0,   62,
    0,    0,    0,    0,    0,    0,  123,   10,    0,   62,
   62,   11,    2,   12,    0,   83,    0,    3,    4,    0,
    0,    0,   67,    8,    8,   14,    0,    0,    8,    8,
    8,    0,    8,    0,    8,    8,    0,    0,    0,    8,
   25,   10,    8,    0,    0,   11,    2,   12,    0,   26,
    0,    3,    4,   90,   10,    0,  210,   10,   11,   14,
   12,   11,   83,   12,    0,   83,    0,    0,   91,    0,
   92,   91,   14,   92,    0,   14,  158,   10,    0,   90,
   10,   11,    0,   12,   11,   83,   12,    0,   83,    0,
    0,  159,    0,    0,    0,   14,    0,    0,   14,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   40,   43,   45,   45,   40,   43,   41,   45,   43,   40,
   45,   59,   59,  123,    8,   40,   69,  256,   59,   59,
  256,  257,   41,  125,   43,  261,   45,  263,   59,  265,
   40,   42,  256,   86,   59,   43,   47,   45,  277,  275,
   59,   69,   44,   41,   44,   43,  125,   45,    1,   59,
  274,   59,   41,    6,   43,   40,   45,   59,   86,   59,
   44,   59,   15,   37,   38,   44,   41,  125,   43,   43,
   45,   45,   40,   67,   27,   59,  256,  256,   40,    0,
   59,   59,  123,  123,   59,    6,   43,   45,   45,  268,
  270,   59,  123,   45,  256,  257,  256,   45,   51,  261,
  256,  263,   59,  265,  265,  265,  125,   15,  271,  265,
  273,  256,  257,  275,  276,  125,  261,  256,  263,   27,
  265,  268,   75,  262,  259,  260,  258,  125,  267,  268,
  275,  276,   85,  107,  108,  109,  110,  256,  257,   92,
  100,  101,  261,  262,  263,  265,  265,  265,  267,  268,
  125,  104,  266,  272,  256,  257,  275,  259,  260,  261,
  270,  263,  270,  265,  256,  265,   41,  256,  269,  271,
  256,  124,  264,  275,  276,  128,  265,  256,  257,  265,
  259,  260,  261,   41,  263,  256,  265,   41,   41,   43,
  125,   45,  271,  270,  265,  270,  275,  276,  256,  257,
   41,  270,   43,  261,   45,  263,  125,  265,  256,  256,
   98,   99,   41,  256,  256,  260,  260,  275,  264,  125,
  256,  256,  265,  266,  265,  268,  265,  269,   59,  270,
  270,  269,   59,  274,  274,  276,  276,  256,  257,  270,
  259,  260,  261,  274,  263,  270,  265,  200,  256,  274,
  269,  265,  271,  265,   75,  265,  275,  276,  256,  257,
  270,  259,  260,  261,  274,  263,   41,  265,   43,  265,
   45,  269,   41,  271,   59,  265,  264,  275,  276,  265,
  277,  256,  257,  264,  259,  260,  261,  264,  263,  265,
  265,  264,    6,  265,  269,  265,  271,  265,  256,  265,
  275,  276,  270,   44,  256,   -1,  274,  265,  266,  265,
  268,  265,  277,  265,  266,   -1,  268,  265,  266,   -1,
  268,  256,  257,  256,  259,  260,  261,  277,  263,  262,
  265,  277,  277,  277,  267,  268,  271,  256,  257,  272,
  275,  276,  261,  277,  263,  265,  265,  265,  265,  265,
  256,  257,  265,  265,   -1,  261,  275,  263,   -1,  265,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  275,
  191,  192,  193,  194,  195,  196,  197,  198,  199,  200,
  256,  257,   -1,   -1,   -1,  261,   -1,  263,   -1,  265,
   -1,   -1,   -1,   -1,   -1,   -1,  256,  257,   -1,  275,
  276,  261,  262,  263,   -1,  265,   -1,  267,  268,   -1,
   -1,   -1,  272,  256,  257,  275,   -1,   -1,  261,  262,
  263,   -1,  265,   -1,  267,  268,   -1,   -1,   -1,  272,
  256,  257,  275,   -1,   -1,  261,  262,  263,   -1,  265,
   -1,  267,  268,  256,  257,   -1,  256,  257,  261,  275,
  263,  261,  265,  263,   -1,  265,   -1,   -1,  271,   -1,
  273,  271,  275,  273,   -1,  275,  256,  257,   -1,  256,
  257,  261,   -1,  263,  261,  265,  263,   -1,  265,   -1,
   -1,  271,   -1,   -1,   -1,  275,   -1,   -1,  275,
};
}
final static short YYFINAL=5;
final static short YYMAXTOKEN=277;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"IF","THEN","ELSE","ENDIF","PRINT","INT",
"LOOP","TO","ID","CONSTANT","FLOAT","STRING","COMPARATOR","ASIGNATION","END",
"GLOBAL","BEGIN","FROM","TOFLOAT","EOF","BY",
};
final static String yyrule[] = {
"$accept : p",
"p : declarations exe EOF",
"p : declarations exe error EOF",
"p : declarations error EOF",
"p : error exe EOF",
"declarations : declarations declaration",
"declarations : declaration",
"declaration : type var_list ';'",
"declaration : type var_list",
"declaration : error var_list ';'",
"declaration : type error ';'",
"var_list : var_list ',' ID",
"var_list : ID",
"type : INT",
"type : FLOAT",
"type : STRING",
"exe : exe sentence",
"exe : exe ambit",
"exe : sentence",
"exe : ambit",
"exe_sentences : exe_sentences sentence",
"exe_sentences : sentence",
"sentence : asignation",
"sentence : selection",
"sentence : iteration",
"sentence : print",
"sentence : conversion",
"sentence : error ';'",
"asignation : ID ASIGNATION expression ';'",
"asignation : ID ASIGNATION expression error",
"asignation : error ASIGNATION expression ';'",
"asignation : error ASIGNATION expression",
"selection : selection_simple ELSE bloque ENDIF",
"selection : selection_simple ELSE sentence ENDIF",
"selection : selection_simple ENDIF",
"selection_simple : IF condition THEN bloque",
"selection_simple : IF condition THEN sentence",
"iteration : LOOP FROM ID ASIGNATION ID TO ID BY ID bloque",
"iteration : LOOP FROM ID ASIGNATION ID TO ID BY ID sentence",
"iteration : error FROM ID ASIGNATION ID TO ID BY ID bloque",
"iteration : LOOP error ID ASIGNATION ID TO ID BY ID bloque",
"iteration : LOOP FROM error ASIGNATION ID TO ID BY ID bloque",
"iteration : LOOP FROM ID error ID TO ID BY ID bloque",
"iteration : LOOP FROM ID ASIGNATION error TO ID BY ID bloque",
"iteration : LOOP FROM ID ASIGNATION ID error ID BY ID bloque",
"iteration : LOOP FROM ID ASIGNATION ID TO error BY ID bloque",
"iteration : LOOP FROM ID ASIGNATION ID TO ID error ID bloque",
"iteration : LOOP FROM ID ASIGNATION ID TO ID BY error bloque",
"iteration : LOOP FROM ID ASIGNATION ID TO ID BY ID error",
"bloque_exe_sentences : bloque_exe_sentences sentence",
"bloque_exe_sentences : sentence",
"bloque : BEGIN bloque_exe_sentences END",
"bloque : BEGIN bloque_exe_sentences error",
"bloque : END",
"ambit_declarations : ambit_declarations ambit_dec_sentence",
"ambit_declarations : ambit_dec_sentence",
"ambit_dec_sentence : declaration",
"ambit_dec_sentence : GLOBAL var_list ';'",
"ambit : ID '{' ambit_declarations exe_sentences '}'",
"ambit : ID '{' exe_sentences '}'",
"ambit : error '{' ambit_declarations exe_sentences '}'",
"ambit : ID '{' ambit_declarations error '}'",
"ambit : ID '{' ambit_declarations exe_sentences error",
"condition : '(' expression COMPARATOR expression ')'",
"condition : error expression COMPARATOR expression ')'",
"condition : '(' expression COMPARATOR expression error",
"condition : '(' expression error expression ')'",
"condition : '(' error COMPARATOR expression ')'",
"condition : '(' expression COMPARATOR error ')'",
"print : PRINT '(' STRING ')' ';'",
"print : PRINT '(' STRING ')' error",
"print : PRINT '(' error ')' ';'",
"print : error '(' STRING ')' ';'",
"conversion : TOFLOAT '(' expression ')' ';'",
"conversion : TOFLOAT '(' expression ')' error",
"conversion : TOFLOAT '(' error ')' ';'",
"expression : expression '+' term",
"expression : expression '-' term",
"expression : term",
"term : term '*' factor",
"term : term '/' factor",
"term : factor",
"factor : CONSTANT",
"factor : ID",
"factor : '-' CONSTANT",
"factor : STRING",
};

//#line 208 "Gramatica.y"

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
//#line 466 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 2:
//#line 33 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(1),"Sintactico"); msj.addError(e);}
break;
case 3:
//#line 34 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(2),"Sintactico"); msj.addError(e);}
break;
case 4:
//#line 35 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(3),"Sintactico"); msj.addError(e);}
break;
case 7:
//#line 42 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(201)); msj.addStructure(s);
											Enumeration e = ((Vector<Token>)val_peek(1).obj).elements();
											while (e.hasMoreElements()){
												Token token = (Token)e.nextElement();
												if (token.getETS().getType() == null){
													token.getETS().setType(token.getType());
												}
											}
										}
break;
case 8:
//#line 52 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
break;
case 9:
//#line 53 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(5),"Sintactico"); msj.addError(e);}
break;
case 10:
//#line 54 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(6),"Sintactico"); msj.addError(e);}
break;
case 11:
//#line 57 "Gramatica.y"
{	Vector<Token> tokens = (Vector<Token>)val_peek(2).obj;
										Token token = (Token)val_peek(0).obj;
										token.setType("ID");
										tokens.add(token);
										yyval.obj = tokens;									
								}
break;
case 12:
//#line 63 "Gramatica.y"
{	Vector<Token> tokens = new Vector<Token>();
										Token token = (Token)val_peek(0).obj;
										token.setType("ID");
										tokens.add(token);
										yyval.obj = tokens;
								}
break;
case 27:
//#line 93 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(15),"Sintactico"); msj.addError(e);}
break;
case 28:
//#line 96 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(202)); msj.addStructure(s);}
break;
case 29:
//#line 97 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
break;
case 30:
//#line 98 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(7),"Sintactico"); msj.addError(e);}
break;
case 31:
//#line 99 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(8),"Sintactico"); msj.addError(e);}
break;
case 32:
//#line 102 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(203)); msj.addStructure(s);}
break;
case 33:
//#line 103 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(203)); msj.addStructure(s);}
break;
case 34:
//#line 104 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(203)); msj.addStructure(s);}
break;
case 37:
//#line 111 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(204)); msj.addStructure(s);}
break;
case 38:
//#line 112 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(204)); msj.addStructure(s);}
break;
case 39:
//#line 113 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(18),"Sintactico"); msj.addError(e);}
break;
case 40:
//#line 114 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(19),"Sintactico"); msj.addError(e);}
break;
case 41:
//#line 115 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(22),"Sintactico"); msj.addError(e);}
break;
case 42:
//#line 116 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(7),"Sintactico"); msj.addError(e);}
break;
case 43:
//#line 117 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(22),"Sintactico"); msj.addError(e);}
break;
case 44:
//#line 118 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
break;
case 45:
//#line 119 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(22),"Sintactico"); msj.addError(e);}
break;
case 46:
//#line 120 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(21),"Sintactico"); msj.addError(e);}
break;
case 47:
//#line 121 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(22),"Sintactico"); msj.addError(e);}
break;
case 48:
//#line 122 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(2),"Sintactico"); msj.addError(e);}
break;
case 51:
//#line 129 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(206)); msj.addStructure(s);}
break;
case 52:
//#line 130 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(9),"Sintactico"); msj.addError(e);}
break;
case 53:
//#line 131 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(10),"Sintactico"); msj.addError(e);}
break;
case 58:
//#line 142 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(207)); msj.addStructure(s);}
break;
case 59:
//#line 143 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(207)); msj.addStructure(s);}
break;
case 60:
//#line 144 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(17),"Sintactico"); msj.addError(e);}
break;
case 61:
//#line 145 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(2),"Sintactico"); msj.addError(e);}
break;
case 62:
//#line 146 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(16),"Sintactico"); msj.addError(e);}
break;
case 64:
//#line 150 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(11),"Sintactico"); msj.addError(e);}
break;
case 65:
//#line 151 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(12),"Sintactico"); msj.addError(e);}
break;
case 66:
//#line 152 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(24),"Sintactico"); msj.addError(e);}
break;
case 67:
//#line 153 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(23),"Sintactico"); msj.addError(e);}
break;
case 68:
//#line 154 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(23),"Sintactico"); msj.addError(e);}
break;
case 69:
//#line 157 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(205)); msj.addStructure(s);}
break;
case 70:
//#line 158 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
break;
case 71:
//#line 159 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(13),"Sintactico"); msj.addError(e);}
break;
case 72:
//#line 160 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(14),"Sintactico"); msj.addError(e);}
break;
case 73:
//#line 163 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(208)); msj.addStructure(s);}
break;
case 74:
//#line 164 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(23),"Sintactico"); msj.addError(e);}
break;
case 75:
//#line 165 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(13),"Sintactico"); msj.addError(e);}
break;
case 84:
//#line 180 "Gramatica.y"
{	String lexema = ((Token)val_peek(0).obj).getLexema();
							TSEntry entry = (TSEntry)table.getTable().get(lexema);
							String newLexema = "-" + lexema;
							if (entry.getRefCounter() == 1){
								if (table.getTable().contains(newLexema))
									((TSEntry)table.getTable().get(newLexema)).incCounter();
								else {	TSEntry newEntry = new TSEntry(CONSTANT, newLexema);
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
							yyval.obj = table.getTable().get(newLexema);
						}
break;
//#line 856 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
