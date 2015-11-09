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
    0,    0,    0,    0,    3,    3,    5,    4,    4,    4,
    6,    6,    6,    6,    8,    8,    7,    7,    7,    1,
    1,    9,    9,    9,    9,   11,   11,   10,   10,   10,
    2,    2,    2,    2,   12,   12,   12,   12,   12,   12,
   14,   14,   14,   14,   15,   15,   15,   20,   20,   16,
   16,   16,   16,   16,   16,   16,   16,   16,   16,   16,
   16,   23,   23,   21,   21,   21,   13,   13,   13,   13,
   24,   25,   22,   22,   22,   22,   22,   22,   17,   17,
   17,   17,   18,   18,   18,   19,   19,   19,   26,   26,
   26,   27,   27,   27,   27,
};
final static short yylen[] = {                            2,
    3,    4,    3,    3,    2,    1,    3,    1,    1,    3,
    3,    3,    3,    3,    3,    1,    1,    1,    1,    2,
    1,    3,    3,    3,    3,    3,    1,    1,    1,    1,
    2,    2,    1,    1,    1,    1,    1,    1,    1,    2,
    4,    4,    4,    3,    4,    4,    2,    4,    4,   10,
   10,   10,   10,   10,   10,   10,   10,   10,   10,   10,
   10,    2,    1,    3,    3,    1,    4,    3,    4,    4,
    2,    1,    5,    5,    5,    5,    5,    5,    5,    5,
    5,    5,    5,    5,    5,    3,    3,    1,    3,    3,
    1,    1,    1,    2,    1,
};
final static short yydefred[] = {                         0,
    0,   28,   29,   30,    0,    0,   21,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   33,   34,   35,   36,
   37,   38,   39,    0,    0,    0,    0,    0,   20,    0,
   27,    0,    0,    0,   40,    0,    0,    0,    0,    0,
    0,    0,    0,   71,    0,    4,   31,   32,    0,   24,
    0,   47,    0,   17,    0,   18,   19,    0,    0,    0,
    6,    8,    0,    3,    0,    1,   25,   23,   22,   92,
   93,   95,    0,    0,    0,   91,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   26,    0,   66,    0,    0,    0,   16,    0,    0,   72,
   68,    0,    0,    5,    0,    0,    2,   94,   43,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   49,
   48,    0,    0,    0,    0,    0,    0,   42,   41,    0,
    0,   63,    0,   46,   45,    0,   13,    0,   10,   69,
    0,   67,   14,   12,   11,    0,    0,   89,   90,    0,
   82,    0,    0,    0,    0,    0,   81,   80,   79,    0,
    0,    0,    0,    0,   85,   84,   83,    0,   64,   62,
   15,    7,    0,   74,   77,   76,   78,   75,   73,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   52,   53,   54,   55,   56,   57,   58,   59,
   60,    0,   51,   50,
};
final static short yydgoto[] = {                          5,
    6,   15,   60,   61,   99,   62,   63,   98,    7,    8,
   16,   17,   18,   19,   20,   21,   22,   23,   74,   24,
   96,   39,  133,   25,  101,   75,   76,
};
final static short yysindex[] = {                      -169,
  191,    0,    0,    0,    0,  158,    0, -165,  -30,  -35,
   -5, -218, -105,   19, -179,   11,    0,    0,    0,    0,
    0,    0,    0, -187, -127,  -40, -105, -152,    0,   55,
    0,  -27,   44, -170,    0, -136,   44,  -42, -111, -205,
 -126, -162,   44,    0,   36,    0,    0,    0, -114,    0,
   91,    0,   12,    0, -105,    0,    0,    0,   74,  124,
    0,    0, -150,    0,  -39,    0,    0,    0,    0,    0,
    0,    0, -102,   15,   80,    0, -115,  125,  -37,  -99,
  -41,   91,  135,  139,  -85,  -83, -228,  -29,  151,  118,
    0,  -75,    0,  194,  -64,  -60,    0,   17,   20,    0,
    0,   -9,   94,    0,  148,  -23,    0,    0,    0,   44,
   44,   44,   44,  -57,  152,   44,   44,   44,   40,    0,
    0,  154,  -47,  -45,  -19,  -17, -148,    0,    0,  159,
  -44,    0,  174,    0,    0,  -15,    0,  -13,    0,    0,
  -30,    0,    0,    0,    0,   80,   80,    0,    0,  -10,
    0,  126,  143,  160,  214,  -34,    0,    0,    0,    5,
    6,    7,   26, -154,    0,    0,    0,  -30,    0,    0,
    0,    0,    8,    0,    0,    0,    0,    0,    0,   10,
   29,   32,   38,   42, -123,   -1,   14,   21,   34,   46,
   47,   48, -223,   50,   54,   56,   61,   62,   64,   69,
   71, -119, -121, -121, -121, -121, -121, -121, -121, -121,
 -121,  171,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -30,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   31,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  141,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -103,  -18,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -59,    0,    0,    0,    0,    3,   24,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -82,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   57,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   28,    0,  207,    0,    0,    0,  225,  332,    0,
  333,   -2,    9,    0,    0,    0,    0,    0,    2,    0,
  167,    0,    0,    0,   23,   33,   78,
};
final static int YYTABLESIZE=469;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         36,
   36,  110,   73,  111,   38,  110,  179,  111,  110,   36,
  111,  159,   47,  110,  167,  111,   49,   44,   35,   35,
  136,   44,   88,   48,   88,   47,   88,  126,   35,  129,
   36,   69,  201,   28,   40,  145,   48,   41,   79,   81,
   88,  127,   65,   86,   88,   86,   90,   86,   95,   35,
   83,   36,   59,  202,   49,   42,   47,  110,   45,  111,
  136,   86,   84,  138,   87,   70,   87,   48,   87,   50,
   35,   51,   52,  109,   27,  137,    9,   10,  139,  120,
   73,   11,   87,   12,   73,   27,    1,  103,   73,   27,
   30,  132,    2,   86,   77,   14,   46,    3,    4,   31,
   47,  184,   87,   65,   10,  105,   88,  163,   11,  185,
   12,   48,   27,   67,   97,  100,  164,  152,  153,  154,
  156,  112,   14,   66,  140,  142,  113,   86,   53,   10,
  170,   78,  192,   11,   54,   12,  211,   55,   85,   56,
   57,  193,  146,  147,   58,  212,   82,   14,   87,   93,
   91,   94,   44,   44,  114,   44,   44,   44,  131,   44,
  110,   44,  111,  108,   43,  115,  174,   44,  110,  117,
  111,   44,   44,   65,   65,  122,   65,   65,   65,  123,
   65,   61,   65,  175,  124,  110,  125,  111,   65,  148,
  149,  130,   65,   65,   43,  134,   70,   70,  100,  135,
  176,   70,  110,   70,  111,   70,  143,  150,  158,  223,
  151,  166,  157,   80,  118,   70,   70,  165,  100,  160,
   37,  178,   70,   71,   31,   72,  128,  119,   68,   33,
   33,  116,  144,   34,   34,   64,  107,   88,   88,   33,
   88,   88,   88,   34,   88,  161,   88,  162,  121,  171,
   88,  172,   88,  173,  177,   97,   88,   88,   86,   86,
   33,   86,   86,   86,   34,   86,  104,   86,  180,  181,
  182,   86,  186,   86,  187,  194,   97,   86,   86,   87,
   87,   33,   87,   87,   87,   34,   87,  106,   87,  183,
  195,   89,   87,  188,   87,  155,  189,  196,   87,   87,
   70,   71,  190,   72,   70,   71,  191,   72,   70,   71,
  197,   72,   61,   61,  203,   61,   61,   61,  204,   61,
  205,   61,  198,  199,  200,  206,  207,   61,  208,    9,
   10,   61,   61,  209,   11,  210,   12,   29,   27,    0,
   32,    0,    0,    0,    0,    0,    9,   10,   14,  141,
   10,   11,    0,   12,   11,   92,   12,    0,   27,    0,
    0,   93,    0,   94,    0,   14,    0,    0,   14,  213,
  214,  215,  216,  217,  218,  219,  220,  221,  224,  102,
   10,    0,    0,    0,   11,   54,   12,    0,   55,    0,
   56,   57,    0,    0,    0,   58,    9,    9,   14,    0,
    0,    9,    9,    9,    0,    9,    0,    9,    9,    0,
    0,    0,    9,   26,   10,    9,    0,    0,   11,    2,
   12,    0,   27,    0,    3,    4,  222,   10,    0,  168,
   10,   11,   14,   12,   11,   92,   12,    0,   92,    0,
    0,   93,    0,   94,  169,   14,    9,   10,   14,    9,
   10,   11,    0,   12,   11,   13,   12,    0,   92,    0,
    0,    0,    0,    0,    0,   14,    0,    0,   14,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   40,   43,   45,   45,   40,   43,   41,   45,   43,   40,
   45,   59,   15,   43,   59,   45,   44,  123,   59,   59,
   44,  125,   41,   15,   43,   28,   45,  256,   59,   59,
   40,   59,  256,    6,   40,   59,   28,  256,   37,   38,
   59,  270,  125,   41,   43,   43,   45,   45,   51,   59,
  256,   40,   25,  277,   44,  274,   59,   43,   40,   45,
   44,   59,  268,   44,   41,  125,   43,   59,   45,   59,
   59,  259,  260,   59,   44,   59,  256,  257,   59,   82,
   45,  261,   59,  263,   45,  265,  256,   60,   45,   59,
  256,   94,  262,  256,  265,  275,  276,  267,  268,  265,
  103,  256,  265,  256,  257,  256,  125,  256,  261,  264,
  263,  103,  265,   59,  265,  125,  265,  116,  117,  118,
  119,   42,  275,  276,  102,  103,   47,  125,  256,  257,
  133,  268,  256,  261,  262,  263,  256,  265,  265,  267,
  268,  265,  110,  111,  272,  265,  258,  275,  125,  271,
  265,  273,  256,  257,  270,  259,  260,  261,   41,  263,
   43,  265,   45,  266,  270,   41,   41,  271,   43,  269,
   45,  275,  276,  256,  257,   41,  259,  260,  261,   41,
  263,  125,  265,   41,  270,   43,  270,   45,  271,  112,
  113,   41,  275,  276,  270,  260,  256,  257,  125,  260,
   41,  261,   43,  263,   45,  265,   59,  265,  256,  212,
   59,  256,   59,  256,  256,  275,  276,   59,  125,  265,
  256,  256,  265,  266,  265,  268,  256,  269,  256,  270,
  270,  269,  256,  274,  274,  276,  276,  256,  257,  270,
  259,  260,  261,  274,  263,  265,  265,  265,   82,  265,
  269,  265,  271,  264,   41,  265,  275,  276,  256,  257,
  270,  259,  260,  261,  274,  263,   60,  265,  264,  264,
  264,  269,  265,  271,  265,  277,  265,  275,  276,  256,
  257,  270,  259,  260,  261,  274,  263,   63,  265,  264,
  277,  256,  269,  265,  271,  256,  265,  277,  275,  276,
  265,  266,  265,  268,  265,  266,  265,  268,  265,  266,
  277,  268,  256,  257,  265,  259,  260,  261,  265,  263,
  265,  265,  277,  277,  277,  265,  265,  271,  265,  256,
  257,  275,  276,  265,  261,  265,  263,    6,  265,   -1,
    8,   -1,   -1,   -1,   -1,   -1,  256,  257,  275,  256,
  257,  261,   -1,  263,  261,  265,  263,   -1,  265,   -1,
   -1,  271,   -1,  273,   -1,  275,   -1,   -1,  275,  203,
  204,  205,  206,  207,  208,  209,  210,  211,  212,  256,
  257,   -1,   -1,   -1,  261,  262,  263,   -1,  265,   -1,
  267,  268,   -1,   -1,   -1,  272,  256,  257,  275,   -1,
   -1,  261,  262,  263,   -1,  265,   -1,  267,  268,   -1,
   -1,   -1,  272,  256,  257,  275,   -1,   -1,  261,  262,
  263,   -1,  265,   -1,  267,  268,  256,  257,   -1,  256,
  257,  261,  275,  263,  261,  265,  263,   -1,  265,   -1,
   -1,  271,   -1,  273,  271,  275,  256,  257,  275,  256,
  257,  261,   -1,  263,  261,  265,  263,   -1,  265,   -1,
   -1,   -1,   -1,   -1,   -1,  275,   -1,   -1,  275,
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
"ambit_declarations : ambit_declarations ambit_dec_sentence",
"ambit_declarations : ambit_dec_sentence",
"var_list3 : var_list3 ',' ID",
"ambit_dec_sentence : declaration2",
"ambit_dec_sentence : ID",
"ambit_dec_sentence : GLOBAL var_list3 ';'",
"declaration2 : type2 var_list2 ';'",
"declaration2 : type2 var_list2 error",
"declaration2 : error var_list2 ';'",
"declaration2 : type2 error ';'",
"var_list2 : var_list2 ',' ID",
"var_list2 : ID",
"type2 : INT",
"type2 : FLOAT",
"type2 : STRING",
"declarations : declarations declaration",
"declarations : declaration",
"declaration : type var_list ';'",
"declaration : type var_list error",
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
"ambit : abrir_ambit ambit_declarations exe cerrar_ambit",
"ambit : abrir_ambit exe cerrar_ambit",
"ambit : abrir_ambit ambit_declarations error cerrar_ambit",
"ambit : abrir_ambit ambit_declarations exe error",
"abrir_ambit : ID '{'",
"cerrar_ambit : '}'",
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
"factor : ID",
"factor : CONSTANT",
"factor : '-' CONSTANT",
"factor : STRING",
};

//#line 901 "Gramatica.y"

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
//#line 502 "Parser.java"
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
//#line 14 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(1),"Sintactico"); msj.addError(e);}
break;
case 3:
//#line 15 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(2),"Sintactico"); msj.addError(e);}
break;
case 4:
//#line 16 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(3),"Sintactico"); msj.addError(e);}
break;
case 5:
//#line 19 "Gramatica.y"
{ 
																
												/*				System.out.println("1 estoy en el ambito nivel " + ambit_level + " y me llamo " +name );*/
                                             String suf ="";
											names[ambit_level] = name;
												for (int i = 0 ; i <= ambit_level; i++)
                                           {	/*	//System.out.println("9 en el arreglo tengo " + names[i]);*/
												
												suf = suf + "_" + names[i];}
									suffix = suf;
											
											Vector<Token> tokens = (Vector<Token>)val_peek(1).obj;
											
										
											for (int i =0; i <tokens.size(); i++)
											{
												Token token = tokens.elementAt(i);
												TSEntry e = new TSEntry(token.getId(),token.getLexema() + suffix);
												
												e.setType(tipo);
												System.out.println(" TABLA DE SIMBOLOS");
												table.showTable();
												if ((!table.hasLexema(e.getLexema())) || ((table.hasLexema(e.getLexema())) && (!table.isDeclared(e.getLexema())))){
												/*System.out.println(token.getLexema());*/
													/*System.out.println("999 meti a " + e.getLexema());*/
													
												table.addTSEntry(e.getLexema(), e);}
												else
												{
													/*System.out.println("el siguente lexema ya existia en la tabla tire error: " + e.getLexema());*/
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(306),"Semantico"); 
												msj.addError(er);
												}
										
											}
												

																yyval.obj = val_peek(1).obj; 
																level_up = false;
																}
break;
case 6:
//#line 59 "Gramatica.y"
{ 
																String suf ="";
											names[ambit_level] = name;
												for (int i = 0 ; i <= ambit_level; i++)
                                           {/*System.out.println("9 en el arreglo tengo " + names[i]);*/
												
												suf = suf + "_" + names[i];}
									suffix = suf;
											
											Vector<Token> tokens = (Vector<Token>)val_peek(0).obj;
											
										
											for (int i =0; i <tokens.size(); i++)
											{
												Token token = tokens.elementAt(i);
												TSEntry e = new TSEntry(token.getId(),token.getLexema() + suffix);
												System.out.println(" TABLA DE SIMBOLOS");
												table.showTable();
												/*System.out.println("la tabla tiene el lexema me me devuelve " +table.hasLexema(e.getLexema()) + " y si esta declarada me devuelve " + e.isDeclared() + "el tipo de la variable es "+ e.getType() );*/
												if ((!table.hasLexema(e.getLexema())) || ((table.hasLexema(e.getLexema())) && (!table.isDeclared(e.getLexema()))))
												{
												e.setType(tipo);
													/*System.out.println("888 meti a " + e.getLexema() + " estoy en la iteracion "+ i );*/
											
												table.addTSEntry(e.getLexema(), e);
												}
												else{
												/*System.out.println("el siguente lexema ya existia en la tabla tire error: " + e.getLexema());*/
												Error er = new Error(analyzer.getLine(),analyzer.getMessage(306),"Semantico"); 
												msj.addError(er);}
										
											}
											/*					System.out.println("2 estoy en el ambito nivel " + ambit_level + " y me llamo " +name );*/

																yyval.obj = val_peek(0).obj; 
																level_up = false;
																}
break;
case 7:
//#line 98 "Gramatica.y"
{Vector<Token> tokens = (Vector<Token>)val_peek(2).obj;

                                yyval.obj=tokens;}
break;
case 8:
//#line 103 "Gramatica.y"
{ 

										yyval.obj = val_peek(0).obj; 
										level_up = false;
									}
break;
case 9:
//#line 112 "Gramatica.y"
{
									    Vector<Token> tokens = new Vector<Token>();
										Token token = (Token)val_peek(0).obj;
										yyval.obj=tokens;
										
										}
break;
case 10:
//#line 120 "Gramatica.y"
{  Vector<Token> tokens = (Vector<Token>)val_peek(1).obj;
					                            for(int i = 0; i <= tokens.size(); i++)
												{
												Token token = tokens.elementAt(i);
												table.getTSEntry(token.getLexema()+suffix).setLexema(token.getLexema()+"_0");
												table.setKey(token.getLexema()+suffix,token.getLexema()+"_0");
												}
												
												
											}
break;
case 11:
//#line 132 "Gramatica.y"
{	
										ES s = new ES(analyzer.getLine(), analyzer.getMessage(201)); msj.addStructure(s);
										Enumeration e = ((Vector<Token>)vt).elements();
										String type = ((Token)val_peek(2).obj).getLexema();
											tipo= type;
									/*	while (e.hasMoreElements()){*/
										/*	Token token = (Token)e.nextElement();*/
											/*tiposdevariables.add(type);*/
											/*System.out.println("Quiero declarar la variable, "+ token.getLexema()+suffix + " y en la tabla tengo ");*/
										/*table.showTable();	*/
										
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
											*/									yyval.obj = val_peek(1).obj;
									
									}
break;
case 12:
//#line 159 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
break;
case 13:
//#line 160 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(5),"Sintactico"); msj.addError(e);}
break;
case 14:
//#line 161 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(6),"Sintactico"); msj.addError(e);}
break;
case 15:
//#line 164 "Gramatica.y"
{	
										Vector<Token> tokens = (Vector<Token>)val_peek(2).obj;
										Token token = (Token)val_peek(0).obj;
										/*tokens.add(token);*/
										TSEntry e = table.getTable().get(token.getLexema());
										String aux= table.getTable().get(token.getLexema()).getLexema();
										e.setLexema( aux+ suffix);
										table.getTable().put(token.getLexema() + suffix,e);
										table.getTable().remove(aux);
										/*token.setLexema(token.getLexema() + suffix);*/
										vt.add(token);
										vt_amb.add(token);		
										yyval.obj = tokens;			
									}
break;
case 16:
//#line 178 "Gramatica.y"
{	
										Vector<Token> tokens = new Vector<Token>();
										Token token = (Token)val_peek(0).obj;
										
										if (!level_up){
											ambit_level++;
											level_up = true;
											
											/*System.out.println("3 estoy en el ambito nivel " + ambit_level + " y me llamo " +name );*/
											names[ambit_level] = name;
									/*		 System.out.println("300 en el arreglo tengo " + names[ambit_level]);*/
											String suf = "";
											for (int i = 0; i <= ambit_level; i++){
													
										/*	System.out.println("4 estoy en el ambito nivel " + ambit_level + " y me llamo " +name );*/
													
											suf = suf + "_" + names[i];
											
											}
											suffix = suf;
											
											/*System.out.println("4 estoy en el ambito nivel " + ambit_level + " y me llamo "  +name );*/
											/*System.out.println(" el sufijo se llama "+ suffix);*/
										}									
										TSEntry e = table.getTable().get(token.getLexema());
										String aux =table.getTable().get(token.getLexema()).getLexema();
										e.setLexema( aux+ suffix);
										/* table.addTSEntry(token.getLexema() + suffix, e);*/
										/*System.out.println("600 meti a " + e.getLexema());*/
										table.remove(aux);
										/*token.setLexema(token.getLexema() + suffix);*/
										tokens.add(token);
										vt_amb.add(token);
										vt = tokens;
										
										yyval.obj = tokens;
								}
break;
case 17:
//#line 217 "Gramatica.y"
{tipo = "INT";}
break;
case 18:
//#line 218 "Gramatica.y"
{tipo = "FLOAT";}
break;
case 19:
//#line 219 "Gramatica.y"
{tipo = "STRING";}
break;
case 22:
//#line 226 "Gramatica.y"
{
										ES s = new ES(analyzer.getLine(), analyzer.getMessage(201)); msj.addStructure(s);
										
										Enumeration e = ((Vector<Token>)vt).elements();
										String type = ((Token)val_peek(2).obj).getLexema();
										tipo=type;
										while (e.hasMoreElements()){
											Token token = (Token)e.nextElement();
											/*tiposdevariables.add(type);*/
										
										/*System.out.println("Quiero declarar la variable, "+ token.getLexema()+suffix + " y en la tabla tengo ");*/
										/*table.showTable();	*/
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
	yyval.obj = val_peek(1).obj;									
									}
break;
case 23:
//#line 254 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
break;
case 24:
//#line 255 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(5),"Sintactico"); msj.addError(e);}
break;
case 25:
//#line 256 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(6),"Sintactico"); msj.addError(e);}
break;
case 26:
//#line 259 "Gramatica.y"
{	
									Token token = (Token)val_peek(0).obj;
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
									/*System.out.println("600 meti a " + e.getLexema());*/
									table.getTable().put(e.getLexema() , e);
									table.getTable().remove(aux);}
									else
									{
									System.out.println("el siguente lexema ya existia en la tabla tire error: " + e.getLexema());
									Error er = new Error(analyzer.getLine(),analyzer.getMessage(306),"Semantico"); 
												msj.addError(er);
									}
									/*token.setLexema(token.getLexema()+suffix);*/
									vt.add(token);
									vt_amb.add(token);
									yyval.obj = val_peek(2).obj;
								}
break;
case 27:
//#line 286 "Gramatica.y"
{										
									Vector<Token> tokens = new Vector<Token>();
									Token token = (Token)val_peek(0).obj;
									token.setType("ID");
									TSEntry e = table.getTable().get(token.getLexema());
		                            
									String aux = table.getTable().get(token.getLexema()).getLexema();
									e.setLexema( aux + "_0");
									e.setType(tipo);
									table.showTable();
									if (!table.hasLexema(e.getLexema()))
									{table.addTSEntry(e.getLexema() , e); /*puede que este mal borrar el guion bajo ero*/
									/*System.out.println("700 meti a " + e.getLexema());*/
									table.remove(aux);
									}
									else
									{
									System.out.println("el siguente lexema ya existia en la tabla tire error: " + e.getLexema());
									Error er = new Error(analyzer.getLine(),analyzer.getMessage(306),"Semantico"); 
												msj.addError(er);
									}
									/*token.setLexema(token.getLexema() + "_0");*/
									tokens.add(token);
									vt_amb.add(token);
									vt = tokens ;
									yyval.obj = val_peek(0).obj;
								}
break;
case 28:
//#line 315 "Gramatica.y"
{tipo = "INT";}
break;
case 29:
//#line 316 "Gramatica.y"
{tipo = "FLOAT";}
break;
case 30:
//#line 317 "Gramatica.y"
{tipo = "STRING";}
break;
case 32:
//#line 324 "Gramatica.y"
{
		name = ((Token)val_peek(0).obj).getLexema();
											/*System.out.println("5 estoy en el ambito nivel " + ambit_level + " y me llamo " +name );*/
											
											if (ambit_level ==0)
											resetearambitos ();
											else
											{name = names[ambit_level];
											/*names[ambit_level] = "";*/
											}
											}
break;
case 34:
//#line 336 "Gramatica.y"
{
				name = ((Token)val_peek(0).obj).getLexema();
				/*System.out.println(" 6 estoy en el ambito nivel " + ambit_level + " y me llamo " +name );*/
				names[ambit_level] = name;}
break;
case 40:
//#line 347 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(15),"Sintactico"); msj.addError(e);}
break;
case 41:
//#line 350 "Gramatica.y"
{	
/*System.out.println(" 1000000000000000000 este es el sufijo en la asignacion " + suffix);*/

ES es = new ES(analyzer.getLine(), analyzer.getMessage(202)); 
											msj.addStructure(es); 
											String string = ((Token)val_peek(3).obj).getLexema();
											String string2 = (String)val_peek(1).obj;
											/*System.out.println(" 1000000000000000000 mi op1 es  " + string);*/
											/*System.out.println(" 1000000000000000000 mi op2 es  " + string2);*/
										/*table.showTable();*/
											
											
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
											yyval.obj = t;
										 }
break;
case 42:
//#line 402 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
break;
case 43:
//#line 403 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(7),"Sintactico"); msj.addError(e);}
break;
case 44:
//#line 404 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(8),"Sintactico"); msj.addError(e);}
break;
case 45:
//#line 407 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(203)); msj.addStructure(s);}
break;
case 46:
//#line 408 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(203)); msj.addStructure(s);}
break;
case 47:
//#line 409 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(203)); msj.addStructure(s);}
break;
case 50:
//#line 416 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(204)); msj.addStructure(s);}
break;
case 51:
//#line 417 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(204)); msj.addStructure(s);}
break;
case 52:
//#line 418 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(18),"Sintactico"); msj.addError(e);}
break;
case 53:
//#line 419 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(19),"Sintactico"); msj.addError(e);}
break;
case 54:
//#line 420 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(22),"Sintactico"); msj.addError(e);}
break;
case 55:
//#line 421 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(7),"Sintactico"); msj.addError(e);}
break;
case 56:
//#line 422 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(22),"Sintactico"); msj.addError(e);}
break;
case 57:
//#line 423 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
break;
case 58:
//#line 424 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(22),"Sintactico"); msj.addError(e);}
break;
case 59:
//#line 425 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(21),"Sintactico"); msj.addError(e);}
break;
case 60:
//#line 426 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(22),"Sintactico"); msj.addError(e);}
break;
case 61:
//#line 427 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(2),"Sintactico"); msj.addError(e);}
break;
case 64:
//#line 434 "Gramatica.y"
{ES s = new ES(analyzer.getLine(), analyzer.getMessage(206)); msj.addStructure(s);}
break;
case 65:
//#line 435 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(9),"Sintactico"); msj.addError(e);}
break;
case 66:
//#line 436 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(10),"Sintactico"); msj.addError(e);}
break;
case 67:
//#line 439 "Gramatica.y"
{
											ES s = new ES(analyzer.getLine(), analyzer.getMessage(207)); msj.addStructure(s);
											level_up = false;
											
											/*System.out.println("7 estoy en el ambito nivel " + ambit_level + " y me llamo " +name );*/
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
											yyval.obj = val_peek(3).obj;
										  }
break;
case 68:
//#line 471 "Gramatica.y"
{
							ES s = new ES(analyzer.getLine(), analyzer.getMessage(207)); msj.addStructure(s);
							level_up = false;
					/*		System.out.println(" 8 estoy en el ambito nivel " + ambit_level + " y me llamo " +name );*/
							name = ((Token)val_peek(2).obj).getLexema();
							names[ambit_level] = name;
							ambit_level--;
							yyval.obj = val_peek(2).obj;
						}
break;
case 69:
//#line 481 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(2),"Sintactico"); msj.addError(e);}
break;
case 70:
//#line 482 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(16),"Sintactico"); msj.addError(e);}
break;
case 71:
//#line 486 "Gramatica.y"
{name = ((Token)val_peek(1).obj).getLexema();}
break;
case 72:
//#line 487 "Gramatica.y"
{}
break;
case 74:
//#line 489 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(11),"Sintactico"); msj.addError(e);}
break;
case 75:
//#line 490 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(12),"Sintactico"); msj.addError(e);}
break;
case 76:
//#line 491 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(24),"Sintactico"); msj.addError(e);}
break;
case 77:
//#line 492 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(23),"Sintactico"); msj.addError(e);}
break;
case 78:
//#line 493 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(23),"Sintactico"); msj.addError(e);}
break;
case 79:
//#line 496 "Gramatica.y"
{	ES es = new ES(analyzer.getLine(), analyzer.getMessage(205)); 
									msj.addStructure(es);
									String lexema = ((Token)val_peek(2).obj).getLexema();
									Terceto t = new Terceto (s.size(), "PRINT", lexema, "");
									s.add(t);
									yyval.obj = t;
								}
break;
case 80:
//#line 503 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(4),"Sintactico"); msj.addError(e);}
break;
case 81:
//#line 504 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(13),"Sintactico"); msj.addError(e);}
break;
case 82:
//#line 505 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(14),"Sintactico"); msj.addError(e);}
break;
case 83:
//#line 508 "Gramatica.y"
{
											ES es = new ES(analyzer.getLine(), analyzer.getMessage(208)); 
											msj.addStructure(es);
											String lexema = (String)val_peek(2).obj;
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
													/*setear todos los ID a float*/
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
											yyval.obj = t;
											}
break;
case 84:
//#line 546 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(23),"Sintactico"); msj.addError(e);}
break;
case 85:
//#line 547 "Gramatica.y"
{Error e = new Error(analyzer.getLine(),analyzer.getMessage(13),"Sintactico"); msj.addError(e);}
break;
case 86:
//#line 550 "Gramatica.y"
{ 	
									String string = (String)val_peek(2).obj;
									String string2 = (String)val_peek(0).obj;
									
									
									/*System.out.println("YYYYYYYYYYYYYYYYYYYYestoy en la suma y el op1 es "+ string) ;*/
									/*System.out.println("XXXXXXXXXXXXXXXXXXXX estoy en la suma y el  op2 es "+ string2) ;*/
									/*table.showTable();*/
									TSEntry op1 = table.getTSEntry(string);							
									TSEntry op2 = table.getTSEntry(string2);
									/*op1.imprimir();*/
									char c = string.charAt(0);
									char c2 = string2.charAt(0);
									Terceto t = new Terceto(s.size(),"+", (String)val_peek(2).obj, (String)val_peek(0).obj);
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
									yyval.obj = "[" + t.getId() + "]";
								}
break;
case 87:
//#line 619 "Gramatica.y"
{ 	
									String string = (String)val_peek(2).obj;
									String string2 = (String)val_peek(0).obj;
									TSEntry op1 = table.getTSEntry((String)val_peek(2).obj);
									TSEntry op2 = table.getTSEntry((String)val_peek(0).obj);
									char c = string.charAt(0);
									char c2 = string2.charAt(0);
									Terceto t = new Terceto(s.size(),"-", (String)val_peek(2).obj, (String)val_peek(0).obj);
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
									yyval.obj = "[" + t.getId() + "]";
								}
break;
case 88:
//#line 682 "Gramatica.y"
{
					yyval.obj = ((String)val_peek(0).obj);
				 }
break;
case 89:
//#line 687 "Gramatica.y"
{ 	String string = (String)val_peek(2).obj;
							String string2  =(String)val_peek(0).obj;
							TSEntry op1 = table.getTSEntry((String)val_peek(2).obj);
							TSEntry op2 = table.getTSEntry((String)val_peek(0).obj);
							char c = string.charAt(0);
							Terceto viejo;
							Terceto t = new Terceto(s.size(),"*", (String)val_peek(2).obj, (String)val_peek(0).obj);
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
							yyval.obj = "[" + t.getId() + "]";
						}
break;
case 90:
//#line 722 "Gramatica.y"
{ 
							String string = (String)val_peek(2).obj;
							String string2  =(String)val_peek(0).obj;
							TSEntry op1 = table.getTSEntry((String)val_peek(2).obj);
							TSEntry op2 = table.getTSEntry((String)val_peek(0).obj);
							char c = string.charAt(0);
							Terceto viejo;
							Terceto t = new Terceto(s.size(),"/", (String)val_peek(2).obj, (String)val_peek(0).obj);
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
							yyval.obj = "[" + t.getId() + "]";
						}
break;
case 91:
//#line 758 "Gramatica.y"
{ 
					String s1 = (String) val_peek(0).obj;
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
break;
case 92:
//#line 775 "Gramatica.y"
{ 
			    String lexema = ((Token)val_peek(0).obj).getLexema();
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
				yyval.obj = lexema+ suffix;
			}
break;
case 93:
//#line 792 "Gramatica.y"
{	String newLexema = ((Token)val_peek(0).obj).getLexema();
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
					yyval.obj = newLexema;
				}
break;
case 94:
//#line 839 "Gramatica.y"
{	
							String lexema = ((Token)val_peek(0).obj).getLexema();
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
									/*table.getTable().remove(lexema);*/
									}else {	 
											TSEntry newEntry = new TSEntry(CONSTANT, newLexema);
											table.addTSEntry(newEntry.getLexema(), newEntry);
											/*System.out.println("400 meti a " + newEntry.getLexema());*/
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
										/*System.out.println("500 meti a " + newEntry.getLexema());*/
										newEntry.setType(a);
									}
								}
							}  
							yyval.obj = newLexema;
						}
break;
//#line 1692 "Parser.java"
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
